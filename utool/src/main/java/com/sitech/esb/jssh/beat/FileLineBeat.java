package com.sitech.esb.jssh.beat;

import java.io.IOException;

/**
 * 用来探测文件内容是否有更新，可对批量的文件进行探测，类似elk的FileBeat
 * 它一般会结合定时任务对文件定时去心跳检测内容是否新数行数据写入（注意只是新增）。
 *
 * @author liwei_paas
 * @date 2021/1/14
 */
public interface FileLineBeat {

    /**
     * 对文件进行一次心跳检测，读取文件里的内容，并可以通过fileLineHandler对读到的内容进行处理
     */
    void heartbeat() throws IOException;

    /**
     * 要读取的文件（一般是文件的全路径名）
     * @param filePath
     */
    void setFilePath(String filePath);

    /**
     * 处理文件里每行数据的方法
     * @param fileHandler
     */
    void setFileLineHandler(FileLineHandler fileHandler);

}
