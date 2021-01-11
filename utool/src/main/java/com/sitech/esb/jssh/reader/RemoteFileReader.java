package com.sitech.esb.jssh.reader;

import com.sitech.esb.jssh.shell.RemoteShellExecutor;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * linux主机文件读取
 * @author liwei_paas
 * @date 2021/1/11
 */
public abstract class RemoteFileReader  {

    private static final Map<String,FileRecord> fileRecordMap = new HashMap<>();

    protected RemoteShellExecutor remoteShellExecutor;

    protected FileRowParser fileRowParser;

    private int rowOffset = 0;

    public static int DEFAULT_READ_SIZE = 1*1024*1024; //单次读取内容数据大小，跟网络、内存大小而定


    public RemoteFileReader(RemoteShellExecutor remoteShellExecutor, FileRowParser fileRowParser) {
        this.remoteShellExecutor = remoteShellExecutor;
        this.fileRowParser = fileRowParser;
    }

    /**
     * 对文件进行分页读取一次,
     * 如果之前已经读过了，接着上次继续读取；
     * 如果之前没有读过，那么根据文件大小以及最大行 计算出应该读取行数
     * @param filePath
     * @return
     * @throws IOException
     */
    public boolean readOnce(String filePath) throws IOException {
        FileRecord fileRecord;
        if (fileRecordMap.containsKey(filePath)){
            fileRecord = fileRecordMap.get(filePath);
        }else{
            fileRecord = new FileRecord();
            fileRecord.setStartReadTime(new Date());
            fileRecord.setOffset(offset(filePath));
            fileRecordMap.put(filePath,fileRecord);
        }
        int start = fileRecord.getLastRow()+1;
        int offset = fileRecord.getOffset();
        readRow(filePath,start,offset);
        fileRecord.setLastRow(start+offset);
        fileRecord.setLastReadTime(new Date());
        fileRecord.setFilePath(filePath);
        return true;
    }

    /**
     * 文件每次应该读取的行数计算。
     * 如果有指定默认值，那么直接使用该值即可
     * 如果没有，那么根据文件大小以及fileRecord算出来,算法为： （指定的单次读取大小 * 当前文件总行数） / 当前文件大小
     * @param filePath
     * @return
     * @throws IOException
     */
    private int offset(String filePath) throws IOException {
        // 获取文件总行数，大文件可能这个速度优点慢
        int offset = rowOffset;
        if (offset==0){
            int fileSize = Integer.parseInt(remoteShellExecutor.executeAndGetSingle("ll " + filePath + " | awk '{print $5}'")); //文件大小
            int totalRow = Integer.parseInt(remoteShellExecutor.executeAndGetSingle("wc -l "+filePath)); // 文件总大小
            offset = (DEFAULT_READ_SIZE*totalRow)/fileSize;
        }
        return offset;
    }

    /**
     * 通过分页的方式读取
     * @param filepath 文件路径
     * @param start 开始行
     * @param offset 每次读取多少行
     * @return 是否读到数据
     */
    abstract void readRow(String filepath, int start, int offset) throws IOException;

    public RemoteShellExecutor getRemoteShellExecutor() {
        return remoteShellExecutor;
    }

    public FileRowParser getFileRowParser() {
        return fileRowParser;
    }
}
