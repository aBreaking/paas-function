package com.sitech.esb.hb;

/**
 * SitxdSrvinfoHis entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SitxdSrvinfoHis extends SrvinfoHis implements
		java.io.Serializable {

	// Constructors
	private Long opId;
	private String txdRetnums;
	private String sserviceName;
	private Long phoneInputno;
	private Long opridInputno;
	private Long regionInputno;
	private SiTxdoRouteType routeType;
	private TxdoSystem txdoSystem;
	
	/** default constructor */
	public SitxdSrvinfoHis() {
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getTxdRetnums() {
		return txdRetnums;
	}

	public void setTxdRetnums(String txdRetnums) {
		this.txdRetnums = txdRetnums;
	}

	public String getSserviceName() {
		return sserviceName;
	}

	public void setSserviceName(String sserviceName) {
		this.sserviceName = sserviceName;
	}

	public Long getPhoneInputno() {
		return phoneInputno;
	}

	public void setPhoneInputno(Long phoneInputno) {
		this.phoneInputno = phoneInputno;
	}

	public Long getOpridInputno() {
		return opridInputno;
	}

	public void setOpridInputno(Long opridInputno) {
		this.opridInputno = opridInputno;
	}

	public Long getRegionInputno() {
		return regionInputno;
	}

	public void setRegionInputno(Long regionInputno) {
		this.regionInputno = regionInputno;
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
