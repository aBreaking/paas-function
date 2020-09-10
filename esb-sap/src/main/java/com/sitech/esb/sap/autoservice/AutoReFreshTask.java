package com.sitech.esb.sap.autoservice;

import org.apache.log4j.Logger;

public class AutoReFreshTask implements Runnable {
	private static final Logger log = Logger.getLogger("autojob");
	private String ipport;
	public AutoReFreshTask(String ipport){
		this.ipport = ipport;
	}
	@Override
	public  void run(){
		String retmsg = AutoSrvHttpGet.refreshAll("http://"+ipport+"/esbWS/EsbHelper?refreshCheckMap=true&isAllrefresh=true");
		log.info("全部加载返回信息:"+retmsg);
	}
}
