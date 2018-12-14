package com.sitech.paas.bull.call;
import java.io.*;
import java.net.*;

/**
 * 通用http发送方法
 */
public class HttpUtils{
    
    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param esbUrl 发送请求的 URL
     * @param json 请求参数，请求参数应该是 json 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String esbUrl, String json) {
        StringBuffer sbf = new StringBuffer();
        try {
            URL url = new URL(esbUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setConnectTimeout(5000);
            connection.setInstanceFollowRedirects(true);
            connection.addRequestProperty("role", "Admin");
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            if (!"".equals(json)) {
                out.writeBytes(json);
            }
            out.flush();
            out.close();

            BufferedReader reader = null;

            if(connection.getResponseCode()==200){
                //调用成功，正常调用结果
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }else if(connection.getResponseCode()==500){
                //esb服务调用异常的调用结果
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String lines;
            while ((lines = reader.readLine()) != null) {
                //TODO 还有乱码的问题
                sbf.append(lines);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sbf.toString();
    }

}