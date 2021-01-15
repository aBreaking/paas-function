package com.sitech.esb.jssh.beat;

import com.sitech.esb.jssh.config.JsshLocalHolder;
import com.sitech.esb.jssh.shell.RemoteShellExecutor;

import java.io.*;

/**
 * 远程探测linux文件
 *
 * 它一般会结合定时任务对文件定时去心跳检测内容是否新数行数据写入（注意只是新增）。
 *  当然对文件的心跳检测并不是无休止的，你可以使用heartbeat设置maxNullLineConsecutiveTimes来决定多少次没有数据新加入就取消该文件的检测；
 *
 * 对文件内容可以使用解析器来解析
 * 本工具对文件的读取记录不会丢失，它会将文件的读取记录通过序列话的方式保存在指定位置
 * @author liwei_paas
 * @date 2021/1/12
 */
public class SshFileLineBeat implements FileLineBeat {


    private int keepAliveSecond = 60*60; //文件最大保持心跳检测时间

    /**
     * 单次读取内容数据行数，注意不是大小。
     *  需要跟你实际的网络情况、每行数据大小来设置一个最优值，它（ineOffset）等于 网络每秒传送量/单行数据大小
     *  比如你网络一般，一秒钟传送1M的数据；你的文件中每行的数据有1024字节大小，那么，该值建议就是：1M/1024字节 = 1024
     *
     */
    private int lineOffset = 1*1024;

    private FileLineHandler fileLineHandler; // 处理文件里每行数据的方法

    private String filePath; //要读取的文件（一般是文件的全路径名）

    public SshFileLineBeat() {
    }

    public SshFileLineBeat(String filePath, FileLineHandler fileLineHandler) {
        this.fileLineHandler = fileLineHandler;
        this.filePath = filePath;
    }

    public void heartbeat() throws IOException {
        // 就算一秒读取一次，都这么久了也没读取到内容，也该不要了吧
        heartbeat(Integer.MAX_VALUE);
    }

    /**
     * 对文件进行一次心跳检测，读取文件里的内容，并可以通过fileLineHandler对读到的内容进行处理
     * 每个文件的最大心跳保持时间不会超过MAX_KEEP_ALIVE_TIME，已读过的文件将会被放入垃圾箱FILE_COMPLETE_SET
     * just beat it ,beat it
     * @param maxNullLineConsecutiveTimes 多少次没有读取到数据就被遗弃
     * @throws IOException
     */
    public void heartbeat(int maxNullLineConsecutiveTimes) throws IOException {
        FileRecordCache localFileCache = JsshLocalHolder.getLocalFileCache();
        if (localFileCache.isAbandoned(filePath)){
            // 防止重复读取无效文件
            return;
        }
        FileRecord fileRecord = localFileCache.getOrStartFileRecord(filePath);
        if (System.currentTimeMillis()-fileRecord.getStartReadTimestamp() >= keepAliveSecond*1000){
            //文件已过期的话，也将该文件遗弃
            localFileCache.abandon(filePath);
        }else{
            readAndHandleLine(fileRecord, fileLineHandler);
        }

        if (fileRecord.getNullLineConsecutiveTimes()>=maxNullLineConsecutiveTimes){
            //连续了n次都没有读取到内容，也就abandon该文件了
            localFileCache.abandon(filePath);
        }
    }



    /**
     * 对文件进行分页读取每行数据并处理,默认通过sed+分页的方式读取
     * 如果之前已经读过了，接着上次继续读取；
     * 如果之前没有读过，那么根据文件大小以及最大行 计算出应该读取行数
     *
     * @param fileLineHandler 每行数据的解析工具
     * @param fileRecord 文件记录
     * @return 根据lineNum判断文件是否还有数据并已经处理了
     * @throws IOException
     */
    private void readAndHandleLine(FileRecord fileRecord,FileLineHandler fileLineHandler) throws IOException{
        RemoteShellExecutor remoteShellExecutor = JsshLocalHolder.getLocalShellExecutor();
        final int nlct = fileRecord.getNullLineConsecutiveTimes(); // 连续多少次没有内容
        //先根据文件大小来判断文件有没有更新过，考虑到大文件，通过判断大小比较效率较高
        long fileSize = remoteShellExecutor.getFileSize(filePath);
        if (fileSize <= fileRecord.getFileSize()){
            //说明文件内容没有更新，就不需要再执行后续操作了
            fileRecord.setNullLineConsecutiveTimes(nlct+1);
            return;
        }
        fileRecord.setFileSize(fileSize);

        final long currentTotalLine = remoteShellExecutor.getFileTotalLineNum(filePath); //当前总行数
        final long lastLineNum = fileRecord.getLineNum(); // 上次读到的行
        if (currentTotalLine<=lastLineNum){
            //还有一种极端情况，文件有更新，但内容行数并没有增加，比如最一行追加了内容，这种也不需要考虑
            fileRecord.setNullLineConsecutiveTimes(nlct+1);
            return;
        }

        // 使用什么命令来读取文件内容
        final String command = command2readFileRow(filePath,currentTotalLine,lastLineNum,lineOffset);

        remoteShellExecutor.doExecute(command, out -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(out));
            // 处理当前行数据
            String currentLine = reader.readLine();
            //使用nextLine来判断currentLine是不是最后一行了
            String nextLine = reader.readLine();
            for (long start = lastLineNum+1; currentLine!=null ;start++){
                if (fileLineHandler.handLine(filePath,currentLine, start,nextLine==null)){
                    fileRecord.setLineNum(start); // 记录已经处理过的行号
                }
                currentLine = nextLine;
                nextLine = reader.readLine();
            }
            fileRecord.setNullLineConsecutiveTimes(0); //说明有内容了
        });
    }



    /**
     * 涉及到一个算法，应该使用何种命令来读取文件内容
     * 考虑到大文件>1G以上的文件，应该用怎么样的命令来读取文件比较合理呢...
     *
     * @param filePath 文件
     * @param currentTotalLine 当前该文件的总行数
     * @param lastLineNum 该文件上次的读到了多少行
     * @param offset 分页，每次应该读多少行
     * @return 可执行的命令
     */
    protected String command2readFileRow(String filePath,long currentTotalLine,long lastLineNum,int offset){
        StringBuilder command = new StringBuilder();
        // 默认算法是根据行数增加量 + tail命令来决定 ,如果已经读过了一半的内容，先tail 再head,反之亦然，对大小文件均适用
        if (lastLineNum*2 > currentTotalLine){
            //实际已经读过超过一半了
            command.append("tail -").append(currentTotalLine-lastLineNum).append(" ").append(filePath).append(" | head -").append(offset);
        }else{
            command.append("head -").append(lastLineNum+offset).append(" ").append(filePath).append(" | tail -").append(offset);
        }
        return command.toString();
    }

    public int getKeepAliveSecond() {
        return keepAliveSecond;
    }

    public void setKeepAliveSecond(int keepAliveSecond) {
        this.keepAliveSecond = keepAliveSecond;
    }

    public int getLineOffset() {
        return lineOffset;
    }

    public void setLineOffset(int lineOffset) {
        this.lineOffset = lineOffset;
    }

    public FileLineHandler getFileLineHandler() {
        return fileLineHandler;
    }

    @Override
    public void setFileLineHandler(FileLineHandler fileLineHandler) {
        this.fileLineHandler = fileLineHandler;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
