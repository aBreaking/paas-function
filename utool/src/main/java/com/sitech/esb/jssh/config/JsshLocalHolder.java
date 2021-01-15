package com.sitech.esb.jssh.config;

import com.sitech.esb.jssh.beat.FileRecordCache;
import com.sitech.esb.jssh.shell.RemoteShellExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author liwei_paas
 * @date 2021/1/15
 */
public class JsshLocalHolder {

    /**
     * ssh的执行工具，它
     */
    private static ThreadLocal<RemoteShellExecutor> LOCAL_SHELL_EXECUTOR = new ThreadLocal<>();

    private static Map<String,FileRecordCache> LOCAL_FILE_CACHE = new HashMap<>();

    public static RemoteShellExecutor getLocalShellExecutor(){
        return null;
    }

    public static FileRecordCache getLocalFileCache(){
        return null;
    }

    private static void initRemoteShellExecutor(){

    }

    private static FileRecordCache initFileRecordCache(){
        Thread currentThread = Thread.currentThread();
        String name = currentThread.getName();

        FileRecordCache fileRecordCache = new FileRecordCache();
        return fileRecordCache;
    }


}
