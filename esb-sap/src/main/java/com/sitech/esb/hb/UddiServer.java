package com.sitech.esb.hb;

import java.util.Date;

/**
 * UddiServer entity provides the base persistence definition of the
 * UddiServer entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  UddiServer implements java.io.Serializable {

	// Fields

	private Long uddisrvId;
	private String uddisrvIp;
	private Long uddiAdminPort;
	private Long uddisrvPort;
	private Date uddiLastHeat;
	private String uddisrvDesc;

	// Constructors

	/** default constructor */
	public UddiServer() {
	}

	/** minimal constructor */
	public UddiServer(String uddisrvIp, Long uddiAdminPort, Long uddisrvPort) {
		this.uddisrvIp = uddisrvIp;
		this.uddisrvPort = uddisrvPort;
		this.uddiAdminPort = uddiAdminPort;
	}

	/** full constructor */
	public UddiServer(String uddisrvIp, Long uddiAdminPort, Long uddisrvPort, Date uddiLastHeat,
			String uddisrvDesc) {
		this.uddisrvIp = uddisrvIp;
		this.uddiAdminPort = uddiAdminPort;
		this.uddisrvPort = uddisrvPort;
		this.uddiLastHeat = uddiLastHeat;
		this.uddisrvDesc = uddisrvDesc;
	}

	// Property accessors

	public Long getUddisrvId() {
		return this.uddisrvId;
	}

	public void setUddisrvId(Long uddisrvId) {
		this.uddisrvId = uddisrvId;
	}

	public String getUddisrvIp() {
		return this.uddisrvIp;
	}

	public void setUddisrvIp(String uddisrvIp) {
		this.uddisrvIp = uddisrvIp;
	}

	public Long getUddiAdminPort() {
		return this.uddiAdminPort;
	}

	public void setUddiAdminPort(Long uddiAdminPort) {
		this.uddiAdminPort = uddiAdminPort;
	}

	public Long getUddisrvPort() {
		return this.uddisrvPort;
	}

	public void setUddisrvPort(Long uddisrvPort) {
		this.uddisrvPort = uddisrvPort;
	}

	public Date getUddiLastHeat() {
		return this.uddiLastHeat;
	}

	public void setUddiLastHeat(Date uddiLastHeat) {
		this.uddiLastHeat = uddiLastHeat;
	}

	public String getUddisrvDesc() {
		return this.uddisrvDesc;
	}

	public void setUddisrvDesc(String uddisrvDesc) {
		this.uddisrvDesc = uddisrvDesc;
	}

}