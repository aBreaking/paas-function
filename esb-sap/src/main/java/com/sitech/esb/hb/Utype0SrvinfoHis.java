package com.sitech.esb.hb;

/**
 * Utype0SrvinfoHis entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Utype0SrvinfoHis extends SrvinfoHis implements
		java.io.Serializable {

	// Constructors

	private Long opId;
	private String sserviceName;
	private String phoneXpath;
	private String worknoXpath;
	private String regionidXpath;
	private SiTxdoRouteType routeType;
	private TxdoSystem txdoSystem;

	/** default constructor */
	public Utype0SrvinfoHis() {
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getSserviceName() {
		return sserviceName;
	}

	public void setSserviceName(String sserviceName) {
		this.sserviceName = sserviceName;
	}

	public String getPhoneXpath() {
		return phoneXpath;
	}

	public void setPhoneXpath(String phoneXpath) {
		this.phoneXpath = phoneXpath;
	}

	public String getWorknoXpath() {
		return worknoXpath;
	}

	public void setWorknoXpath(String worknoXpath) {
		this.worknoXpath = worknoXpath;
	}

	public String getRegionidXpath() {
		return regionidXpath;
	}

	public void setRegionidXpath(String regionidXpath) {
		this.regionidXpath = regionidXpath;
	}

	public SiTxdoRouteType getRouteType() {
		return routeType;
	}

	public void setRouteType(SiTxdoRouteType routeType) {
		this.routeType = routeType;
	}

	public TxdoSystem getTxdoSystem() {
		return txdoSystem;
	}

	public void setTxdoSystem(TxdoSystem txdoSystem) {
		this.txdoSystem = txdoSystem;
	}

}
