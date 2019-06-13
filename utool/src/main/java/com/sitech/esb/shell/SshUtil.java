package com.sitech.esb.shell;

import javax.naming.CannotProceedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

/**
 * @{USER}
 * @{DATE}
 */
public class SshUtil {

    public static void main(String args[]) throws IOException {
        RemoteShellExecutor shellExecutor = new RemoteShellExecutor("172.21.11.65", "chnesb", "chnesb");
        System.out.println("正在登录");
        shellExecutor.login();
        System.out.println("登录成功");

        Scanner scanner = new Scanner(System.in);
        while (true){

            System.out.print("input>");
            String next = scanner.nextLine();
            System.out.println(next);
            if (next.equals("exit")){
                System.out.println("exit");
                break;
            }
            try {
                List<String> list = shellExecutor.exec(next);
                printList(list);
            } catch (CannotProceedException e) {
                System.err.println(e.getMessage());
            }
        }

        shellExecutor.exit();
    }

    public static void printList(List<String> list){
        for (String s : list){
            System.out.println(s);
        }
    }

}
