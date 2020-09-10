package com.sitech.esb.hb;

/**
 * TxdoWdom entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TxdoWdom implements java.io.Serializable {

	// Fields

	private Long wdomId;
	private String wdomName;
	private String wdomAddress;
	private Long validFlag;
	
	private TxdoSystem txdoSystem;
	private String esbServer;

	// Constructors

	/** default constructor */
	public TxdoWdom() {
	}

	/** full constructor */
	public TxdoWdom(Long txdoSystemid, String wdomName, String wdomAddress,
			Long validFlag) {
		this.wdomName = wdomName;
		this.wdomAddress = wdomAddress;
		this.validFlag = validFlag;
	}

	// Property accessors

	public Long getWdomId() {
		return this.wdomId;
	}

	public void setWdomId(Long wdomId) {
		this.wdomId = wdomId;
	}

	public String getWdomName() {
		return this.wdomName;
	}

	public void setWdomName(String wdomName) {
		this.wdomName = wdomName;
	}

	public String getWdomAddress() {
		return this.wdomAddress;
	}

	public void setWdomAddress(String wdomAddress) {
		this.wdomAddress = wdomAddress;
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

	public String getEsbServer() {
		return esbServer;
	}

	public void setEsbServer(String esbServer) {
		this.esbServer = esbServer;
	}

	

}