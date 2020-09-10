package com.sitech.esb.hb;

/**
 * TxdoRemoteAp entity provides the base persistence definition of the
 * TxdoRemoteAp entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  TxdoRemoteAp implements java.io.Serializable {

	// Fields

	private Long txdoRemoteApId;
	private String txdoRemoteAp;
	private String txdoRemoteApAddr;
	private String wtcApPolicy;
	private Long validFlag;
	private String txdoRemoteWeight;

	// Constructors

	/** default constructor */
	public  TxdoRemoteAp() {
	}

	/** full constructor */
	public  TxdoRemoteAp(TxdoSystem txdoSystem, String txdoRemoteAp,
			String txdoRemoteApAddr, String wtcApPolicy,String txdoRemoteWeight) {
		this.txdoRemoteAp = txdoRemoteAp;
		this.txdoRemoteApAddr = txdoRemoteApAddr;
		this.wtcApPolicy = wtcApPolicy;
		this.txdoRemoteWeight = txdoRemoteWeight;
	}

	// Property accessors
	public String getWtcApPolicy() {
		return this.wtcApPolicy;
	}

	public void setWtcApPolicy(String wtcApPolicy) {
		this.wtcApPolicy = wtcApPolicy;
	}

	public Long getTxdoRemoteApId() {
		return txdoRemoteApId;
	}

	public void setTxdoRemoteApId(Long txdoRemoteApId) {
		this.txdoRemoteApId = txdoRemoteApId;
	}

	public String getTxdoRemoteAp() {
		return txdoRemoteAp;
	}

	public void setTxdoRemoteAp(String txdoRemoteAp) {
		this.txdoRemoteAp = txdoRemoteAp;
	}

	public String getTxdoRemoteApAddr() {
		return txdoRemoteApAddr;
	}

	public void setTxdoRemoteApAddr(String txdoRemoteApAddr) {
		this.txdoRemoteApAddr = txdoRemoteApAddr;
	}

	public Long getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Long validFlag) {
		this.validFlag = validFlag;
	}

	public String getTxdoRemoteWeight() {
		return txdoRemoteWeight;
	}

	public void setTxdoRemoteWeight(String txdoRemoteWeight) {
		this.txdoRemoteWeight = txdoRemoteWeight;
	}

}