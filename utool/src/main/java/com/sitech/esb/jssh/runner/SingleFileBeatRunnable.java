package com.sitech.esb.jssh.runner;

import com.sitech.esb.jssh.beat.FileLineBeat;
import com.sitech.esb.jssh.beat.FileLineHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * 单个文件解析处理
 * 建议将其放在单个线程里执行
 * @author liwei_paas
 * @date 2021/1/22
 */
public class SingleFileBeatRunnable implements Callable<Integer> {

    private static Logger logger = LoggerFactory.getLogger(SingleFileBeatRunnable.class);

    private String key; // 配置的key
    private String beatName; // beat名
    private String filePath; //文件路径

    public SingleFileBeatRunnable(String key, String beatName, String filePath) {
        this.key = key;
        this.beatName = beatName;
        this.filePath = filePath;
    }

    @Override
    public Integer call() {
        JsshLocalContext.startContext(key);
        logger.info("start filebeat，key->{},beatName->{}",key,beatName);
        FileLineHandler fileLineHandler = JsshLocalContext.getFileLineHandler(beatName);
        FileLineBeat beat = JsshLocalContext.getLocalFileLineBeat(beatName);
        int heartbeat = beat.heartbeat(filePath, fileLineHandler);
        JsshLocalContext.saveLocalCache();
        return heartbeat;
    }
}
