package com.sitech.esb.jssh.beat;

import com.sitech.esb.jssh.shell.RemoteShellExecutor;

import java.io.*;
import java.util.*;

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
public class SshFileLineBeat implements Externalizable,FileLineBeat {


    /**
     * 记录文件的读取情况，每一次的heartbeat 都会对指定文件进行一次内容读取；
     *  如果文件没有读取完，文件的读取信息（比如已经读到第几行了）将会存放在该FILE_RECORD_MAP里
     */
    private final Map<String,FileRecord> FILE_RECORD_MAP = new HashMap<>();

    /**
     * 用于存放已经被读取过的文件名，防止被重复读
     * FIXME 它应该有设置最大长度，或过期时间
     */
    private final Set<String> FILE_ABANDONMENT_SET = new HashSet<>();

    private RemoteShellExecutor remoteShellExecutor;

    private int maxKeepAliveSecond = 60*60; //文件最大保持心跳检测时间

    /**
     * 单次读取内容数据行数，注意不是大小。
     *  需要跟你实际的网络情况、每行数据大小来设置一个最优值，它（ineOffset）等于 网络每秒传送量/单行数据大小
     *  比如你网络一般，一秒钟传送1M的数据；你的文件中每行的数据有1024bit大小，那么，该值建议就是：1M/1024bit = 1024
     *
     */
    private int lineOffset = 1*1024;

    private FileLineHandler fileLineHandler; // 处理文件里每行数据的方法

    private String filePath; //要读取的文件（一般是文件的全路径名）

    public SshFileLineBeat(RemoteShellExecutor remoteShellExecutor) {
        this.remoteShellExecutor = remoteShellExecutor;
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
        if (FILE_ABANDONMENT_SET.contains(filePath)){
            // 防止重复读取
            return;
        }
        FileRecord fileRecord;
        if (FILE_RECORD_MAP.containsKey(filePath)){
            fileRecord = FILE_RECORD_MAP.get(filePath);
            Long timeDifference = System.currentTimeMillis()-fileRecord.getStartReadTimestamp();
            if (timeDifference/1000 > maxKeepAliveSecond){
                abandon(filePath);
                System.out.println("abandon filePath");
                return;
            }
        }else{
            fileRecord = new FileRecord();
            fileRecord.setStartReadTimestamp(System.currentTimeMillis());
            fileRecord.setFilePath(filePath);
            FILE_RECORD_MAP.put(filePath,fileRecord);
        }
        readAndHandleLine(fileRecord, fileLineHandler);
        if (fileRecord.getNullLineConsecutiveTimes()>=maxNullLineConsecutiveTimes){
            //连续了n次都没有读取到内容，也就abandon该文件了
            abandon(filePath);
        }
    }

    private void abandon(String filePath){
        FILE_RECORD_MAP.remove(filePath);
        FILE_ABANDONMENT_SET.add(filePath);
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
        final int nlct = fileRecord.getNullLineConsecutiveTimes(); // 连续多少次没有内容

        final long currentTotalLine = remoteShellExecutor.getFileTotalLineNum(filePath); //当前总行数
        final long lastLineNum = fileRecord.getLineNum(); // 上次读到的行
        //根据文件大小来判断文件有没有更新过
        if (currentTotalLine <= lastLineNum){
            //说明文件内容没有更新，就不需要再执行后续操作了
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

    /**
     * 将两个缓存里的内容保存到本地，防止工程重启丢失了读取记录
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(FILE_RECORD_MAP);
        out.writeObject(FILE_ABANDONMENT_SET);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        FILE_RECORD_MAP.putAll((Map<String, FileRecord>) in.readObject());
        FILE_ABANDONMENT_SET.addAll((Set<String>) in.readObject());
    }

    public void setLineOffset(int lineOffset) {
        this.lineOffset = lineOffset;
    }

    public void setFileLineHandler(FileLineHandler fileLineHandler) {
        this.fileLineHandler = fileLineHandler;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
