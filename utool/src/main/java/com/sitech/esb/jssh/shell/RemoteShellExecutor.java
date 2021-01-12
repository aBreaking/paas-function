package com.sitech.esb.jssh.shell;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import javax.naming.CannotProceedException;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 登录到远程linux主机上进行操作
 * @author liwei_paas
 * @date 2019/5/29
 */
public class RemoteShellExecutor {

    private Connection connection;

    private String remoteHost;
    private String username;
    private String password;

    private boolean hasLogin;

    public RemoteShellExecutor(String remoteHost,String username,String password)  {
        this.remoteHost = remoteHost;
        this.username = username;
        this.password = password;
    }

    public boolean login() throws IOException {
        connection = new Connection(remoteHost);
        connection.connect();
        hasLogin = true;
        return connection.authenticateWithPassword(username, password);
    }

    /**
     * 执行shell语句，执行成功输出结果，执行失败（如语法错误）输出异常
     * @param command 命令
     * @param handler 输出与错误信息的处理工具
     * @return
     * @throws IOException
     * @throws CannotProceedException command执行异常
     */
    public void doExecute(String command, StandardHandler handler) throws IOException{
        Session session = null;
        InputStream soutInputStream =  null;
        InputStream errInputStream = null;
        try{
            session = connection.openSession();
            session.execCommand(command);
            soutInputStream = new StreamGobbler(session.getStdout());
            errInputStream = new StreamGobbler(session.getStderr());

            List<String> list = getLine(errInputStream);
            if (!list.isEmpty()){
                throw new IOException(list.toString());
            }
            handler.hand(soutInputStream);
        }finally {
            if (session!=null){
                session.close();
            }
            if (errInputStream!=null){
                errInputStream.close();
            }
            if (soutInputStream!=null){
                soutInputStream.close();
            }
        }
    }

    public List<String> execute(String command) throws IOException{
        List<String> list = new ArrayList<>();
        doExecute(command,out -> collectLine(out,list));
        return list;
    }

    public String executeAndGetSingle(String command) throws IOException{
        List<String> list = new ArrayList<>();
        doExecute(command,out -> collectLine(out,list));
        return list.isEmpty()?list.get(0):null;
    }

    public static List<String> getLine(InputStream inputStream) throws IOException {
        List<String> list = new ArrayList<String>();
        collectLine(inputStream,list);
        return list;
    }

    public static void collectLine(InputStream inputStream, Collection<String> collection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine())!=null){
            collection.add(line);
        }
    }

    public boolean hasLogin() {
        return hasLogin;
    }

    public void exit(){
        if (connection!=null){
            connection.close();
            hasLogin = false;
        }
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
