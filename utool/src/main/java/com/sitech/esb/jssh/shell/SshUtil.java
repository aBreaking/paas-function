package com.sitech.esb.jssh.shell;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * @{USER}
 * @{DATE}
 */
public class SshUtil {

    static String ip = "172.18.231.114";
    static String username = "chnesb";
    static String password = "Chnesb123.com";

    public static void main(String[] args) throws Exception{
        RemoteShellExecutor shellExecutor = ShellExecutorFactory.login(ip, username, password);
        shellExecutor.doExecute("", out -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(out));
            String s = reader.readLine();
            System.out.println(s);
        });
    }

    public static void main02(String[] args) throws IOException {
        RemoteShellExecutor shellExecutor = ShellExecutorFactory.login(ip, username, password);
        shellExecutor.doExecute("sed -n 1,2p /paas/chnesb/domains/esbdomain9/logs/inParam.log", out -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(out));
            String s = reader.readLine();
            System.out.println(s);
        });
    }

    public static void main01(String args[]) throws IOException {
        RemoteShellExecutor shellExecutor = ShellExecutorFactory.login(ip, username, password);
        Scanner scanner = new Scanner(System.in);
        while (true){
            String next = scanner.nextLine();
            System.out.println(next);
            if (next.equals("exit")){
                System.out.println("exit");
                break;
            }
            List<String> list = shellExecutor.execute(next);
            printList(list);
        }

        shellExecutor.exit();
    }

    public static void printList(List<String> list){
        for (String s : list){
            System.out.println(s);
        }
    }

}
