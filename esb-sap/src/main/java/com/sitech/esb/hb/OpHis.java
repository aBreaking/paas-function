package com.sitech.esb.hb;

import java.util.Date;

/**
 * OpHis entity provides the base persistence definition of the OpHis
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  OpHis  implements java.io.Serializable {

	// Fields

	private Long opId;
	private Long htt;
	private Long uty;
	private Date opTime;
	private String opUser;
	private String opUserIp;
	private String serverIp;
	private Long serverPort;
	private String opType;

	// Constructors

	/** default constructor */
	public  OpHis() {
	}

	/** minimal constructor */
	public  OpHis(Date opTime, String opUser, String opUserIp,
			String serverIp, Long serverPort, String opType) {
		this.opTime = opTime;
		this.opUser = opUser;
		this.opUserIp = opUserIp;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.opType = opType;
	}

	/** full constructor */
	public  OpHis(Long htt, Long uty, Date opTime, String opUser,
			String opUserIp, String serverIp, Long serverPort, String opType) {
		this.htt = htt;
		this.uty = uty;
		this.opTime = opTime;
		this.opUser = opUser;
		this.opUserIp = opUserIp;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.opType = opType;
	}

	// Property accessors

	public Long getOpId() {
		return this.opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Long getHtt() {
		return this.htt;
	}

	public void setHtt(Long htt) {
		this.htt = htt;
	}

	public Long getUty() {
		return this.uty;
	}

	public void setUty(Long uty) {
		this.uty = uty;
	}

	public Date getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public String getOpUser() {
		return this.opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public String getOpUserIp() {
		return this.opUserIp;
	}

	public void setOpUserIp(String opUserIp) {
		this.opUserIp = opUserIp;
	}

	public String getServerIp() {
		return this.serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public Long getServerPort() {
		return this.serverPort;
	}

	public void setServerPort(Long serverPort) {
		this.serverPort = serverPort;
	}

	public String getOpType() {
		return this.opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

}