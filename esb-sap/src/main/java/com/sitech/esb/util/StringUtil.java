package com.sitech.esb.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author liwei_paas 
 * @date 2018��4��13�� ����3:54:26
 */
/**
 * @author liwei_paas 
 * @date 2018��4��13�� ����3:54:28
 */

/**
 * @author liwei_paas 
 * @date 2018��4��13�� ����3:54:29
 */
public final class StringUtil {

	public static Properties loadConfig(String fileName) {
		Properties properties = new Properties();
		try {
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			if (classLoader == null) {
				classLoader = StringUtil.class.getClassLoader();
			}
			InputStream is = classLoader.getResourceAsStream(fileName);
			properties.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	public static String getConfigParam(String params, String fileName) {
		String result = "";
		if (org.apache.commons.lang.StringUtils.isEmpty(fileName)
				|| org.apache.commons.lang.StringUtils.isEmpty(params)) {
			return result;
		}
		try {
			Properties properties = loadConfig(fileName);
			result = properties.getProperty(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean hasIsolatedStates(long srvId){
		if(null == System.getProperty("ISOLATED_STATE")){
			return false;
		}
		String isolatedStates = System.getProperty("ISOLATED_STATE");
		String[] ids = isolatedStates.split("\\,");
		String srv_id = String.valueOf(srvId);
		if(ids.length > 0){
			for(String id : ids){
				if(id.equals(srv_id)){
					return true;
				}
			}
		}
		return false;
	} 
	
	public static String checkReplace(String str){
		str=str.replaceAll("&","&amp;");
		str=str.replaceAll("<","&lt;");
	    str=str.replaceAll(">","&gt;");
	    str=str.replaceAll("'","");
	    str=str.replaceAll(";","");
	    str=str.replaceAll("--","");
	    str=str.replaceAll("/","");
	    str=str.replaceAll("%","");
		return str;
	}
	

	
	/**�ж��ַ����Ƿ�Ϊ��ֵ��null
	 * @param str
	 * @return 
	 */
	public static boolean isBlank(String str){
		if(null == str){
			return true;
		}
        return "".equals(str.trim());
    }
	
	
	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}
	
	
	public static void main(String args[]){
		String s = "12";
		System.out.println(isNotBlank(s));
	}
}
