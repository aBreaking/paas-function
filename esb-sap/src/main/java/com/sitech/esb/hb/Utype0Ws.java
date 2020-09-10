package com.sitech.esb.hb;

import java.sql.Clob;

/**
 *Created by liangpenga
 *Created at Jul 4, 2010 2:34:00 PM
 *
 **/
public class Utype0Ws extends SrvInfo implements java.io.Serializable{
	
	private SiTxdoRouteType siTxdoRouteType;
	private String srcServiceName;
	private String phoneXpath;
	private String workNoXpath;
	private String regionIdXpath;
	
	private TxdoSystem txdoSystem;
	
	public TxdoSystem getTxdoSystem() {
		return txdoSystem;
	}

	public void setTxdoSystem(TxdoSystem txdoSystem) {
		this.txdoSystem = txdoSystem;
	}

	public Utype0Ws(){}
	public Utype0Ws(Long srvId,String srvName,String srvChName){
		super(srvId,srvName,srvChName);
	}
	public SiTxdoRouteType getSiTxdoRouteType() {
		return siTxdoRouteType;
	}
	public void setSiTxdoRouteType(SiTxdoRouteType siTxdoRouteType) {
		this.siTxdoRouteType = siTxdoRouteType;
	}
	public String getSrcServiceName() {
		return srcServiceName;
	}
	public void setSrcServiceName(String srcServiceName) {
		this.srcServiceName = srcServiceName;
	}
	public String getPhoneXpath() {
		return phoneXpath;
	}
	public void setPhoneXpath(String phoneXpath) {
		this.phoneXpath = phoneXpath;
	}
	public String getWorkNoXpath() {
		return workNoXpath;
	}
	public void setWorkNoXpath(String workNoXpath) {
		this.workNoXpath = workNoXpath;
	}
	public String getRegionIdXpath() {
		return regionIdXpath;
	}
	public void setRegionIdXpath(String regionIdXpath) {
		this.regionIdXpath = regionIdXpath;
	}
	
	

}
