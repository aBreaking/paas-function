package com.sitech.esb.hb;

import java.sql.Clob;

/**
 * UtypeSrvinfoHis entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class UtypeSrvinfoHis extends SrvinfoHis implements
		java.io.Serializable {

	// Constructors
	private Long opId;
	private String sserviceName;
	private Clob inxsd;
	private Clob outxsd;
	private Long ischeckOutxsd;
	private String retcodeXpath;
	private String retcodeXpath2;
	private String retmsgXpath;
	private String phoneXpath;
	private String usridXpath;
	private String acctidXpath;
	private String custidXpath;
	private String worknoXpath;
	private String regionidXpath;
	private String partitionXpath;
	private String outXpaths;
	private String filterXpaths;
	///private String fixedValues;
	private SiTxdoRouteType routeType;
	private TxdoSystem txdoSystem;
	
	/** default constructor */
	public UtypeSrvinfoHis() {
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

	public Clob getOutxsd() {
		return outxsd;
	}

	public void setOutxsd(Clob outxsd) {
		this.outxsd = outxsd;
	}

	public Long getIscheckOutxsd() {
		return ischeckOutxsd;
	}

	public void setIscheckOutxsd(Long ischeckOutxsd) {
		this.ischeckOutxsd = ischeckOutxsd;
	}

	public String getRetcodeXpath() {
		return retcodeXpath;
	}

	public void setRetcodeXpath(String retcodeXpath) {
		this.retcodeXpath = retcodeXpath;
	}

	public String getRetcodeXpath2() {
		return retcodeXpath2;
	}

	public void setRetcodeXpath2(String retcodeXpath2) {
		this.retcodeXpath2 = retcodeXpath2;
	}

	public String getRetmsgXpath() {
		return retmsgXpath;
	}

	public void setRetmsgXpath(String retmsgXpath) {
		this.retmsgXpath = retmsgXpath;
	}

	public String getPhoneXpath() {
		return phoneXpath;
	}

	public void setPhoneXpath(String phoneXpath) {
		this.phoneXpath = phoneXpath;
	}

	public String getUsridXpath() {
		return usridXpath;
	}

	public void setUsridXpath(String usridXpath) {
		this.usridXpath = usridXpath;
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

	public String getPartitionXpath() {
		return partitionXpath;
	}

	public void setPartitionXpath(String partitionXpath) {
		this.partitionXpath = partitionXpath;
	}

	public String getOutXpaths() {
		return outXpaths;
	}

	public void setOutXpaths(String outXpaths) {
		this.outXpaths = outXpaths;
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
/*
	public String getFixedValues() {
		return fixedValues;
	}

	public void setFixedValues(String fixedValues) {
		this.fixedValues = fixedValues;
	}
*/

}
