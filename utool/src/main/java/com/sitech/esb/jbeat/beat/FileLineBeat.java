package com.sitech.esb.jbeat.beat;


/**
 * 用来探测文件内容是否有更新，可对批量的文件进行探测，类似elk的FileBeat
 * 它一般会结合定时任务对文件定时去心跳检测内容是否新数行数据写入（注意只是新增）。
 *
 * @author liwei_paas
 * @date 2021/1/14
 */
public interface FileLineBeat {

    int STATUS_NORMAL = 0; // filebeat正常状态
    int STATUS_ABANDON = 1; // 已经无效了
    int STATUS_NO_UPDATE = 2; // 没有新内容
    int STATUS_EXCEPTION = 3; // 出现异常了
    int STATUS_ABANDON_BY_EXPIRE = 11; // 已经过期了被抛弃
    int STATUS_ABANDON_NO_UPDATE = 12; // 连续很多次没有读取到内容被抛弃了


    /**
     * 对文件进行一次心跳检测，读取文件里的内容，并可以通过fileLineHandler对读到的内容进行处理
     * @param filePath 要读取的文件（一般是文件的全路径名）
     * @param handler 处理文件里每行数据的方法
     * @return heatbeat的状态
     */
    int heartbeat(String filePath,FileLineHandler handler);

}
