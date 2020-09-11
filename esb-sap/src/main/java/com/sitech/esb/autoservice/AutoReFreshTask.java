package com.sitech.esb.autoservice;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class AutoReFreshTask implements Runnable {
	private static final Logger log = Logger.getLogger("autojob");
	private String ipport;
	public AutoReFreshTask(String ipport){
		this.ipport = ipport;
	}

	public  void run(){
		String retmsg = doRefreshAll("http://"+ipport+"/esbWS/EsbHelper?refreshCheckMap=true&isAllrefresh=true");
		log.info("全部加载返回信息:"+retmsg);
	}

	public static String doRefreshAll(String path) {
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			PrintWriter out = null;
			// Content-Type实体头用于向接收方指示实体的介质类型，指定HEAD方法送到接收方的实体介质类型，或GET方法发送的请求介质类型
			conn.setRequestProperty("Content-Type", "text/html;charset=GBK");
			// 设置打开与此URLConnection引用的资源的通信链接时使用的指定超时值（以毫秒为单位）
			conn.setConnectTimeout(10000);
			// 将读取超时设置为指定的超时时间，以毫秒为单位。
			// conn.setReadTimeout(60000);
			conn.setRequestMethod("GET");
			// Post 请求不能使用缓存
			conn.setUseCaches(false);
			conn.connect();
			// 获取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			String result = "";
			while ((line = reader.readLine()) != null) {
				result += line;
			}
			reader.close();
			conn.disconnect();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
