package com.sitech.esb.hb;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EsbServer entity provides the base persistence definition of the
 * EsbServer entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  EsbServer implements java.io.Serializable {

	// Fields

	private Long serverId;
	private String serverIp;
	private Long adminPort;
	private Long serverPort;
	private Date srvLastHeat;
	private String serverDesc;
	private String serverIps;
	private Boolean isUpdating;
	
	private Set txdoWdoms = new HashSet(0);

	// Constructors

	/** default constructor */
	public EsbServer() {
	}

	/** minimal constructor */
	public EsbServer(String serverIp, Long adminPort, Long serverPort) {
		this.serverIp = serverIp;
		this.adminPort = adminPort;
		this.serverPort = serverPort;
	}

	/** full constructor */
	public EsbServer(String serverIp, Long adminPort, Long serverPort, Date srvLastHeat,
			String serverDesc) {
		this.serverIp = serverIp;
		this.adminPort = adminPort;
		this.serverPort = serverPort;
		this.srvLastHeat = srvLastHeat;
		this.serverDesc = serverDesc;
	}

	// Property accessors

	public Long getServerId() {
		return this.serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public String getServerIp() {
		return this.serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public Long getAdminPort() {
		return this.adminPort;
	}

	public void setAdminPort(Long adminPort) {
		this.adminPort = adminPort;
	}

	public Long getServerPort() {
		return this.serverPort;
	}

	public void setServerPort(Long serverPort) {
		this.serverPort = serverPort;
	}

	public Date getSrvLastHeat() {
		return this.srvLastHeat;
	}

	public void setSrvLastHeat(Date srvLastHeat) {
		this.srvLastHeat = srvLastHeat;
	}

	public String getServerDesc() {
		return this.serverDesc;
	}

	public void setServerDesc(String serverDesc) {
		this.serverDesc = serverDesc;
	}

	public Set getTxdoWdoms() {
		return txdoWdoms;
	}

	public void setTxdoWdoms(Set txdoWdoms) {
		this.txdoWdoms = txdoWdoms;
	}

	public String getServerIps() {
		return serverIps;
	}

	public void setServerIps(String serverIps) {
		this.serverIps = serverIps;
	}

	public Boolean getIsUpdating() {
		return isUpdating;
	}

	public void setIsUpdating(Boolean isUpdating) {
		this.isUpdating = isUpdating;
	}
	
	

}