package li.wei.domaincheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class DomainCheck {

    private static String ali_wanwang_url = "https://checkapi.aliyun.com/check/checkdomain";
    private static String url_param = "domain={}.com&command=&token=Yfd281e9f18ae5be3837cd9d17cd554af&ua=&currency=&site=&bid=&_csrf_token=&callback=jsonp_1551858200065_76332";

    public static boolean isDomainUsable(String domain){

        String param=url_param.replaceAll("\\{\\}",domain);

        String s = sendPost(ali_wanwang_url, param);
        int parse = parse(s);
        if (parse==0){
            return false;
        }
        return true;
    }

    private static int parse(String s){

        int i = s.indexOf("\"avail\":");
        s = s.substring(i);
        s = s.substring(s.indexOf(":")+1, s.indexOf(","));
        return Integer.parseInt(s);
    }

    public static String sendPost(String url, String param)
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
