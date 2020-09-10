package com.sitech.esb.hb;

import com.sitech.esb.hb.TxdoSystem;

/**
 * TxdoWTCRoute entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TxdoWTCRoute implements java.io.Serializable {

	// Fields

	private Long txdoRouteId;
	private String routePartition;
	private String tdomSeq;
	private Long loadPolicy;
	private Long validFlag;
	
	private TxdoSystem txdoSystem;

	// Constructors

	/** default constructor */
	public TxdoWTCRoute() {
	}

	/** minimal constructor */
	public TxdoWTCRoute(String routePartition, String tdomSeq, Long loadPolicy,
			Long validFlag) {
		this.routePartition = routePartition;
		this.tdomSeq = tdomSeq;
		this.loadPolicy = loadPolicy;
		this.validFlag = validFlag;
	}

	// Property accessors

	public Long getTxdoRouteId() {
		return this.txdoRouteId;
	}

	public void setTxdoRouteId(Long txdoRouteId) {
		this.txdoRouteId = txdoRouteId;
	}

	public String getRoutePartition() {
		return this.routePartition;
	}

	public void setRoutePartition(String routePartition) {
		this.routePartition = routePartition;
	}

	public String getTdomSeq() {
		return this.tdomSeq;
	}

	public void setTdomSeq(String tdomSeq) {
		this.tdomSeq = tdomSeq;
	}

	public Long getLoadPolicy() {
		return this.loadPolicy;
	}

	public void setLoadPolicy(Long loadPolicy) {
		this.loadPolicy = loadPolicy;
	}

	public Long getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(Long validFlag) {
		this.validFlag = validFlag;
	}

	public TxdoSystem getTxdoSystem() {
		return txdoSystem;
	}

	public void setTxdoSystem(TxdoSystem txdoSystem) {
		this.txdoSystem = txdoSystem;
	}

}