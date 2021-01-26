package com.sitech.esb.jbeat.beat;

import com.sitech.esb.jbeat.shell.RemoteShellExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;

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

    private static Logger logger = LoggerFactory.getLogger(SshFileLineBeat.class);

    private RemoteShellExecutor remoteShellExecutor;

    private int keepAliveSecond = 60*60; //文件最大保持心跳检测时间

    private int maxNullLineConsecutiveTimes = Integer.MAX_VALUE; //多少次没有读取到数据就被遗弃

    private FileRecord fileRecord;

    public SshFileLineBeat() {
    }

    /**
     *
     * @param filePath 要读取的文件（一般是文件的全路径名）
     * @param handler 处理文件里每行数据的方法
     */
    public int heartbeat(String filePath,FileLineHandler handler) {
        // 就算一秒读取一次，都这么久了也没读取到内容，也该不要了吧
       return heartbeat(filePath,handler,maxNullLineConsecutiveTimes);
    }

    /**
     * 对文件进行一次心跳检测，读取文件里的内容，并可以通过fileLineHandler对读到的内容进行处理
     * 每个文件的最大心跳保持时间不会超过MAX_KEEP_ALIVE_TIME，已读过的文件将会被放入垃圾箱FILE_COMPLETE_SET
     * just beat it ,beat it
     * @param maxNullLineConsecutiveTimes 多少次没有读取到数据就被遗弃
     * @throws IOException
     */
    public int heartbeat(String filePath,FileLineHandler handler,int maxNullLineConsecutiveTimes)  {
        fileRecord.setLastReadTime(new Date());
        if (System.currentTimeMillis()-fileRecord.getStartReadTimestamp() >= keepAliveSecond*1000){
            //文件已过期的话，也将该文件遗弃
            logger.info("{}已经过期，后续不再读取",filePath);
            return STATUS_ABANDON_BY_EXPIRE;
        }
        try {
            int status = readAndHandleLine(fileRecord, handler);
            if (status==STATUS_NO_UPDATE){
                //连续了n次都没有读取到内容，也就abandon该文件了
                if (fileRecord.getNullLineConsecutiveTimes()>=maxNullLineConsecutiveTimes){
                    logger.error("{}已经超过连续{}次没有读取到内容，将其抛弃，后续不再读取",filePath,maxNullLineConsecutiveTimes);
                    return STATUS_ABANDON_NO_UPDATE;
                }
            }
            return status;
        } catch (IOException e) {
            // 记录失败原因
            fileRecord.addErrorLog(e);
            logger.error(filePath+"读取或批处理失败",e);
            return STATUS_EXCEPTION;
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
    private int readAndHandleLine(FileRecord fileRecord,FileLineHandler fileLineHandler) throws IOException{
        String filePath = fileRecord.getFilePath();
        final int nlct = fileRecord.getNullLineConsecutiveTimes(); // 连续多少次没有内容

        /*先根据文件大小来判断文件有没有更新过 有个问题，考虑到大文件，通过判断大小比较效率较高，但是有个问题：如果刚开始读该文件，并没有读完，文件不更新*/

        final long currentTotalLine = remoteShellExecutor.getFileTotalLineNum(filePath); //当前总行数
        fileRecord.setLineTotal(currentTotalLine);
        final long lastLineNum = fileRecord.getLineNum(); // 上次读到的行
        logger.info("{}上次读取到的行号为:{}",filePath,lastLineNum);
        if (currentTotalLine<=lastLineNum){
            //还有一种极端情况，文件有更新，但内容行数并没有增加，比如最一行追加了内容，这种也不需要考虑
            fileRecord.setNullLineConsecutiveTimes(nlct+1);
            logger.info("{}文件行数无更新",filePath);
            return STATUS_NO_UPDATE;
        }

        int lineOffset = fileLineHandler.lineOffset();
        // 使用什么命令来读取文件内容
        final String command = command2readFileRow(filePath,currentTotalLine,lastLineNum,lineOffset);
        logger.info("准备使用`{}`命令来获取文件内容",command);

        remoteShellExecutor.doExecute(command, out -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(out));
            // 处理当前行数据
            String currentLine = reader.readLine();
            //使用nextLine来判断currentLine是不是最后一行了
            String nextLine = reader.readLine();
            for (long start = lastLineNum+1; currentLine!=null ;start++){
                if (fileLineHandler.handLine(filePath,currentLine, start,nextLine==null)){
                    logger.info("批处理一次完成，当前的行号为{}",start);
                    fileRecord.setLineNum(start); // 记录已经处理过的行号
                }
                currentLine = nextLine;
                nextLine = reader.readLine();
            }
            fileRecord.setNullLineConsecutiveTimes(0); //说明有内容了
        });
        return STATUS_NORMAL;
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


    public void setKeepAliveSecond(int keepAliveSecond) {
        this.keepAliveSecond = keepAliveSecond;
    }

    public void setMaxNullLineConsecutiveTimes(int maxNullLineConsecutiveTimes) {
        this.maxNullLineConsecutiveTimes = maxNullLineConsecutiveTimes;
    }

    public void setRemoteShellExecutor(RemoteShellExecutor remoteShellExecutor) {
        this.remoteShellExecutor = remoteShellExecutor;
    }

    public RemoteShellExecutor getRemoteShellExecutor() {
        return remoteShellExecutor;
    }

    public int getKeepAliveSecond() {
        return keepAliveSecond;
    }

    public int getMaxNullLineConsecutiveTimes() {
        return maxNullLineConsecutiveTimes;
    }

    public FileRecord getFileRecord() {
        return fileRecord;
    }

    public void setFileRecord(FileRecord fileRecord) {
        this.fileRecord = fileRecord;
    }
}
