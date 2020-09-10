package com.sitech.esb.sap.autoservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class AutoSrvHttpGet {
	public static String refreshAll(String path) {
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
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
