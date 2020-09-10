package com.sitech.esb.hb;

import java.util.Date;
/**
 * 没有使用原来的OpHis，而是新建OpRecords来记录服务发布、更新、删除和应用的发布、更新、删除及
 * 源系统的的配置、修改、删除。 
 */
public class OpRecord implements java.io.Serializable {
	private Long recordID;
	private Date opTime;//操作时间
	private String opUser;//操作用户
	private String opUserIP;//用户ip
	private String serverIP;//服务端ip
	private Long serverPort;//服务端port
	private String opDescription;//操作描述
	public OpRecord() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OpRecord(Long recordID, Date opTime, String opUser,
			String opUserIP, String serverIP, Long serverPort,
			String opDescription) {
		super();
		this.recordID = recordID;
		this.opTime = opTime;
		this.opUser = opUser;
		this.opUserIP = opUserIP;
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.opDescription = opDescription;
	}
	public Long getRecordID() {
		return recordID;
	}
	public void setRecordID(Long recordID) {
		this.recordID = recordID;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public String getOpUser() {
		return opUser;
	}
	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}
	public String getOpUserIP() {
		return opUserIP;
	}
	public void setOpUserIP(String opUserIP) {
		this.opUserIP = opUserIP;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public Long getServerPort() {
		return serverPort;
	}
	public void setServerPort(Long serverPort) {
		this.serverPort = serverPort;
	}
	public String getOpDescription() {
		return opDescription;
	}
	public void setOpDescription(String opDescription) {
		this.opDescription = opDescription;
	}
	
	
}
