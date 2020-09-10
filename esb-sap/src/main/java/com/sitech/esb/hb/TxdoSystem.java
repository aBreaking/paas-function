package com.sitech.esb.hb;

import java.util.HashSet;
import java.util.Set;

/**
 * TxdoSystem entity provides the base persistence definition of the
 * TxdoSystem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  TxdoSystem implements java.io.Serializable {

	// Fields

	private Long txdoSystemId;
	private String txdoSystemName;
	private String wtcApPolicy;
	private String routeSrv;
	private Long systemFlag;
	private Long wtcBlockTime;
	
	private Long validFlag;
	private Set txdoWTCRoutes = new HashSet(0);
	private Set txdoWdoms = new HashSet(0);

	// Constructors

	/** default constructor */
	public  TxdoSystem() {
	}

	/** full constructor */
	public  TxdoSystem(String txdoSystemName,
			 String wtcApPolicy, String txdoSrvSeq) {
		this.txdoSystemName = txdoSystemName;
		this.wtcApPolicy = wtcApPolicy;
	}

	// Property accessors

	public Long getTxdoSystemId() {
		return this.txdoSystemId;
	}

	public void setTxdoSystemId(Long txdoSystemId) {
		this.txdoSystemId = txdoSystemId;
	}

	public String getTxdoSystemName() {
		return this.txdoSystemName;
	}

	public void setTxdoSystemName(String txdoSystemName) {
		this.txdoSystemName = txdoSystemName;
	}


	public String getWtcApPolicy() {
		return this.wtcApPolicy;
	}

	public void setWtcApPolicy(String wtcApPolicy) {
		this.wtcApPolicy = wtcApPolicy;
	}

	public Long getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Long validFlag) {
		this.validFlag = validFlag;
	}

	public Set getTxdoWTCRoutes() {
		return txdoWTCRoutes;
	}

	public void setTxdoWTCRoutes(Set txdoWTCRoutes) {
		this.txdoWTCRoutes = txdoWTCRoutes;
	}

	public String getRouteSrv() {
		return routeSrv;
	}

	public void setRouteSrv(String routeSrv) {
		this.routeSrv = routeSrv;
	}

	public Long getSystemFlag() {
		return systemFlag;
	}

	public void setSystemFlag(Long systemFlag) {
		this.systemFlag = systemFlag;
	}

	public Long getWtcBlockTime() {
		return wtcBlockTime;
	}

	public void setWtcBlockTime(Long wtcBlockTime) {
		this.wtcBlockTime = wtcBlockTime;
	}

	public Set getTxdoWdoms() {
		return txdoWdoms;
	}

	public void setTxdoWdoms(Set txdoWdoms) {
		this.txdoWdoms = txdoWdoms;
	}

}