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
		log.info("ȫ�����ط�����Ϣ:"+retmsg);
	}

	public static String doRefreshAll(String path) {
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			PrintWriter out = null;
			// Content-Typeʵ��ͷ��������շ�ָʾʵ��Ľ������ͣ�ָ��HEAD�����͵����շ���ʵ��������ͣ���GET�������͵������������
			conn.setRequestProperty("Content-Type", "text/html;charset=GBK");
			// ���ô����URLConnection���õ���Դ��ͨ������ʱʹ�õ�ָ����ʱֵ���Ժ���Ϊ��λ��
			conn.setConnectTimeout(10000);
			// ����ȡ��ʱ����Ϊָ���ĳ�ʱʱ�䣬�Ժ���Ϊ��λ��
			// conn.setReadTimeout(60000);
			conn.setRequestMethod("GET");
			// Post ������ʹ�û���
			conn.setUseCaches(false);
			conn.connect();
			// ��ȡ��Ӧ
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
