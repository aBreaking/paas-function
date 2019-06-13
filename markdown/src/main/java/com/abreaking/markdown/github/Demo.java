package com.abreaking.markdown.github;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Demo {
    public static void main(String args[]){
        String url = "https://api.github.com/markdown/raw";
        String param = "Hello world github/linguist#1 **cool**, and #1!";
        String s = sendPost(url, param);
        System.out.println(s);
    }

    public static String sendPost(String url, String param)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try
        {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);

            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.connect();
            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            dataOutputStream.writeBytes(param);
            dataOutputStream.flush();
            dataOutputStream.close();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null)
            {
                result.append(line);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException ex)
            {
                System.out.println("调用in.close Exception, url=" + url + ",param=" + param);
            }
        }
        return result.toString();
    }
}
