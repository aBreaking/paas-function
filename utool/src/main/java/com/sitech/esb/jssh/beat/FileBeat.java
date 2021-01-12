package com.sitech.esb.jssh.beat;


import com.sitech.esb.jssh.handler.FileLineHandler;
import com.sitech.esb.jssh.shell.RemoteShellExecutor;
import com.sitech.esb.jssh.shell.ShellExecutorFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用来探测文件内容是否有更新，可对批量的文件进行探测
 * 对文件内容可以使用解析器来解析
 * @author liwei_paas
 * @date 2021/1/12
 */
public class FileBeat {

    public static int MAX_KEEP_ALIVE_TIME = 1; //文件最大保持检测时间

    public static int DEFAULT_READ_DATA_SIZE = 1*1024*1024; //单次读取内容数据大小，跟网络、内存大小而定

    private static final Map<String,FileRecord> fileRecordMap = new HashMap<>();

    private int rowOffset = 0;

    FileLineHandler fileLineHandler;

    public void listen(String filePath) throws IOException {
        FileRecord fileRecord;
        if (fileRecordMap.containsKey(filePath)){
            fileRecord = fileRecordMap.get(filePath);
            Long timeDifference = System.currentTimeMillis()-fileRecord.getStartReadTimestamp();
            if (timeDifference/1000 > MAX_KEEP_ALIVE_TIME){
                fileRecordMap.remove(filePath);
                return;
            }
        }else{
            fileRecord = new FileRecord();
            fileRecord.setStartReadTimestamp(System.currentTimeMillis());
            fileRecord.setOffset(offset(filePath));
            fileRecordMap.put(filePath,fileRecord);
        }
        FileReader fileReader = new FileReader();
        fileReader.readAndHandleLine(fileRecord,fileLineHandler);
        //是否考虑：如果连接超过n次都没从文件里读取到内容，也将该文件删除
    }

    /**
     * 文件每次应该读取的行数计算。
     * 如果有指定默认值，那么直接使用该值即可
     * 如果没有，那么根据文件大小以及fileRecord算出来,算法为： （指定的单次读取大小 * 当前文件总行数） / 当前文件大小
     * @param filePath
     * @return
     * @throws IOException
     */
    private int offset(String filePath) {
        // 获取文件总行数，大文件可能这个速度优点慢
        int offset = rowOffset;
        if (offset!=0){
            return offset;
        }
        RemoteShellExecutor remoteShellExecutor = ShellExecutorFactory.getLoggedShellExecutor();
        try{
            int fileSize = Integer.parseInt(remoteShellExecutor.executeAndGetSingle("ls -lt " + filePath + " | awk '{print $5}'")); //文件大小
            if (fileSize > 1*1024*1024*1024){ //文件过大读取行数可能会超时，所以就不用换再去读行数了，直接给定一个值把
                return DEFAULT_READ_DATA_SIZE/512;
            }else{
                int totalRow = Integer.parseInt(remoteShellExecutor.executeAndGetSingle("wc -l "+filePath)); // 文件总行数
                return  (DEFAULT_READ_DATA_SIZE*totalRow)/fileSize;
            }
        }catch (IOException e){
            return 1024;
        }

    }

}
