package com.sitech.esb.jssh.shell;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ShellExecutor 工厂
 * @author liwei_paas
 * @date 2021/1/11
 */
public class ShellExecutorFactory {

    private static Map<String,RemoteShellExecutor> shellExecutorFactory = new HashMap<>();

    public static RemoteShellExecutor login(String remoteHost,String username,String password) throws IOException {
        String key = key(remoteHost,username);
        if (!shellExecutorFactory.containsKey(key)){
            synchronized (shellExecutorFactory){
                if (!shellExecutorFactory.containsKey(key)){
                    RemoteShellExecutor remoteShellExecutor = new RemoteShellExecutor(remoteHost, username, password);
                    boolean login = remoteShellExecutor.login();
                    if (login){
                        shellExecutorFactory.put(key,remoteShellExecutor);
                    }else{
                        throw new IOException("username or password incorrect, remoteHost:"+remoteHost+",username:"+username);
                    }
                }
            }
        }
        RemoteShellExecutor remoteShellExecutor = shellExecutorFactory.get(key);
        if (!remoteShellExecutor.hasLogin()){
            remoteShellExecutor.login();
        }
        return remoteShellExecutor;
    }

    private static String key(String remoteHost,String username){
        return remoteHost+"_"+username;
    }

}
