package com.sitech.esb.hb;



/**
 * SocketSrvinfo entity. @author MyEclipse Persistence Tools
 */

public class SocketSrvinfo extends SrvInfo implements
		java.io.Serializable {

	// Fields

	private Long srvId;
	private String socketIp;
	private Long socketPort;
	private Long socketType; //zhangqinga@20111212
	// Constructors

	//zhangqinga@20111212
	public Long getSocketType() {
		return socketType;
	}

	public void setSocketType(Long socketType) {
		this.socketType = socketType;
	}
	//ended zhangqinga@20111212
	/** default constructor */
	public SocketSrvinfo() {
	}

	/** full constructor */
	public SocketSrvinfo(Long srvId, String socketIp,
			Long socketPort) {
		this.srvId = srvId;
		this.socketIp = socketIp;
		this.socketPort = socketPort;
	}

	// Property accessors

	public Long getSrvId() {
		return this.srvId;
	}

	public void setSrvId(Long srvId) {
		this.srvId = srvId;
	}

	public String getSocketIp() {
		return this.socketIp;
	}

	public void setSocketIp(String socketIp) {
		this.socketIp = socketIp;
	}

	public Long getSocketPort() {
		return this.socketPort;
	}

	public void setSocketPort(Long socketPort) {
		this.socketPort = socketPort;
	}

}