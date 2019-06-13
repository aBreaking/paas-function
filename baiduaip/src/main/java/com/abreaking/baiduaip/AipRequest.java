package com.abreaking.baiduaip;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AipRequest {

    public static final String BAIDU_AIP_URL = "https://aip.baidubce.com/rpc/2.0/unit/bot/chat";
    public static final String BAIDU_ABREAKING_ROBOT_APPID = "15778745";
    public static final String BAIDU_ABREAKING_ROBOT_APIKEY = "klDD26fLI5ur86ZKVmT49OIz";
    public static final String BAIDU_ABREAKING_ROBOT_SECRETKEY = "oh0NWr0ylQV0LRIRIs1PF9SuU0XKCceG";

    public static void main(String[] args) throws Exception {
        String param =  "{\"bot_session\":\"\",\"log_id\":\"7758521\",\"request\":{\"bernard_level\":1,\"client_session\":\"{\\\"client_results\\\":\\\"\\\", \\\"candidate_options\\\":[]}\",\"query\":\"你好\",\"query_info\":{\"asr_candidates\":[],\"source\":\"KEYBOARD\",\"type\":\"TEXT\"},\"updates\":\"\",\"user_id\":\"88888\"},\"bot_id\":\"1057\",\"version\":\"2.0\"}";;
        String token = getAccessToken();
        /*String s = HttpUtil.post(BAIDU_AIP_URL,token,"application/json",param);
        System.out.println(param);*/
        robot(token);
    }

    public static void robot(String accessToken) throws Exception {

        String param = "{'text':'今天天气怎么样'}";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version","2.0");
        jsonObject.put("bot_session","2.0");
        jsonObject.put("bot_id","S15237");
        jsonObject.put("log_id","1");
        JSONObject session = new JSONObject();
        jsonObject.put("session ",session );
        session.put("service_id","S10000");
        session.put("session_id","10086");
        session.put("skill_sessions","");
        JSONArray interactions = new JSONArray();
        session.put("interactions",interactions);
        interactions.put("//interaction_id").put("timestamp").put("request").put("response_list");
        JSONObject request = new JSONObject();
        jsonObject.put("request",request);
        request.put("user_id",BAIDU_ABREAKING_ROBOT_APPID);
        request.put("query","你好呀");
        request.put("query_info","你好呀");
        request.put("bernard_level","1");
        request.put("client_session","1");

        //String s = sendPost(BAIDU_AIP_URL+"?access_token="+accessToken, jsonObject.toString());
        String s = HttpUtil.post(BAIDU_AIP_URL, accessToken, "application/json", jsonObject.toString());

        System.out.println(s);

        //sendPost(BAIDU_AIP_URL,param);
    }

    public static String getAccessToken(){
        String url = "https://aip.baidubce.com/oauth/2.0/token";
        String param = "grant_type=client_credentials&client_id=" + BAIDU_ABREAKING_ROBOT_APIKEY + "&client_secret=" +BAIDU_ABREAKING_ROBOT_SECRETKEY;
        String s = sendGet(url, param);
        JSONObject jsonObject = new JSONObject(s);

        String token = jsonObject.get("access_token").toString();

        return token;

    }

    public static String sendPost(String url, String jsonData)
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
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.connect();
            DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
            dataOutputStream.writeBytes(jsonData);
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
                System.out.println("调用in.close Exception, url=" + url );
            }
        }
        return result.toString();
    }

    public static String sendPostWithParam(String url, String param)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try
        {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);

            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null)
            {
                result.append(line);
            }
        }
        catch (Exception e)
        {
            System.out.println("调用HttpsUtil.sendPost Exception, url=" + url );
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
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String sendGet(String url, String param)
    {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try
        {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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
                if (in != null)
                {
                    in.close();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
}
class HttpUtil {

    public static String post(String requestUrl, String accessToken, String params)
            throws Exception {
        String contentType = "application/x-www-form-urlencoded";
        return HttpUtil.post(requestUrl, accessToken, contentType, params);
    }

    public static String post(String requestUrl, String accessToken, String contentType, String params)
            throws Exception {
        String encoding = "UTF-8";
        if (requestUrl.contains("nlp")) {
            encoding = "GBK";
        }
        return HttpUtil.post(requestUrl, accessToken, contentType, params, encoding);
    }

    public static String post(String requestUrl, String accessToken, String contentType, String params, String encoding)
            throws Exception {
        String url = requestUrl + "?access_token=" + accessToken;
        return HttpUtil.postGeneralUrl(url, contentType, params, encoding);
    }

    public static String postGeneralUrl(String generalUrl, String contentType, String params, String encoding)
            throws Exception {
        URL url = new URL(generalUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(params.getBytes(encoding));
        out.flush();
        out.close();

        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> headers = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : headers.keySet()) {
            System.err.println(key + "--->" + headers.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), encoding));
        String result = "";
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result += getLine;
        }
        in.close();
        System.err.println("result:" + result);
        return result;
    }
}
