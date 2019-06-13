package com.sitech.esb.shell;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import javax.naming.CannotProceedException;
import java.io.*;
import java.util.ArrayList;
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

    public RemoteShellExecutor(String remoteHost,String username,String password) throws IOException {
        this.remoteHost = remoteHost;
        this.username = username;
        this.password = password;
    }

    public boolean login() throws IOException {
        connection = new Connection(remoteHost);
        connection.connect();
        return connection.authenticateWithPassword(username, password);
    }


    /**
     * 执行shell语句，执行成功输出结果，执行失败（如语法错误）输出异常
     * @param command
     * @return
     * @throws IOException
     * @throws CannotProceedException command执行异常
     */
    public  List<String> exec(String command) throws IOException, CannotProceedException {
        Session session = null;
        InputStream soutInputStream =  null;
        InputStream errInputStream = null;
        try{
            session = connection.openSession();
            session.execCommand(command);
            errInputStream = new StreamGobbler(session.getStderr());
            soutInputStream = new StreamGobbler(session.getStdout());
            List<String> soutline = getLine(soutInputStream);
            List<String> errorLine = getLine(errInputStream);
            if (soutline.isEmpty()&&!errorLine.isEmpty()){
                //说明shell执行异常
                throw new CannotProceedException(errorLine.toString());
            }
            return soutline;
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

    public List<String> getLine(InputStream inputStream) throws IOException {
        List<String> list = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine())!=null){
            list.add(line);
        }
        return list;
    }

    public void exit(){
        if (connection!=null){
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
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
