package com.sitech.esb.hb;

import java.sql.Clob;

/**
 *  entity provides the base persistence definition of
 * the SiutypewsSrvinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SiUtypeWsSrvInfo extends SrvInfo implements java.io.Serializable {

	// Fields
	private SiTxdoRouteType siTxdoRouteType;
	private String srcServiceName;
	private Clob inXsd;
    private Clob outXsd;
	private Long isCheckOutXsd;
	private String retCodeXpath;
	private String retCodeXpath2;
	private String retMsgXpath;
	private String phoneXpath;
	private String workNoXpath;
	private String regionIdXpath;
	private String usrIdXpath;
	private String custIdXpath;
	private String acctIdXpath;
	private String partitionXpath;
	
	
	private String outXpaths;
	private String filterXpaths;
	private String fixedValues;
	
	private TxdoSystem txdoSystem;
	
	// Constructors

	/** default constructor */
	public SiUtypeWsSrvInfo() {
		
	}
	
	//ȡ�÷����������Ϣ��
    public SiUtypeWsSrvInfo(Long srvId, String srvName, String srvChName) {
    	super(srvId, srvName, srvChName);
    }

	public SiTxdoRouteType getSiTxdoRouteType() {
		return siTxdoRouteType;
	}

	public void setSiTxdoRouteType(SiTxdoRouteType siTxdoRouteType) {
		this.siTxdoRouteType = siTxdoRouteType;
	}

	public String getSrcServiceName() {
		return srcServiceName;
	}

	public void setSrcServiceName(String srcServiceName) {
		this.srcServiceName = srcServiceName;
	}

	public Long getIsCheckOutXsd() {
		return isCheckOutXsd;
	}

	public void setIsCheckOutXsd(Long isCheckOutXsd) {
		this.isCheckOutXsd = isCheckOutXsd;
	}
/*
	public String getOutXmlRootNode() {
		return outXmlRootNode;
	}

	public void setOutXmlRootNode(String outXmlRootNode) {
		this.outXmlRootNode = outXmlRootNode;
	}
*/
	public String getRetCodeXpath() {
		return retCodeXpath;
	}

	public void setRetCodeXpath(String retCodeXpath) {
		this.retCodeXpath = retCodeXpath;
	}

	public String getRetCodeXpath2() {
		return retCodeXpath2;
	}

	public void setRetCodeXpath2(String retCodeXpath2) {
		this.retCodeXpath2 = retCodeXpath2;
	}

	public String getRetMsgXpath() {
		return retMsgXpath;
	}

	public void setRetMsgXpath(String retMsgXpath) {
		this.retMsgXpath = retMsgXpath;
	}

	public Clob getInXsd() {
		return inXsd;
	}

	public void setInXsd(Clob inXsd) {
		this.inXsd = inXsd;
	}

	public Clob getOutXsd() {
		return outXsd;
	}

	public void setOutXsd(Clob outXsd) {
		this.outXsd = outXsd;
	}

	public TxdoSystem getTxdoSystem() {
		return txdoSystem;
	}

	public void setTxdoSystem(TxdoSystem txdoSystem) {
		this.txdoSystem = txdoSystem;
	}

	public String getRegionIdXpath() {
		return regionIdXpath;
	}

	public void setRegionIdXpath(String regionIdXpath) {
		this.regionIdXpath = regionIdXpath;
	}

	public String getCustIdXpath() {
		return custIdXpath;
	}

	public void setCustIdXpath(String custIdXpath) {
		this.custIdXpath = custIdXpath;
	}

	public String getOutXpaths() {
		return outXpaths;
	}

	public void setOutXpaths(String outXpaths) {
		this.outXpaths = outXpaths;
	}

	public String getFilterXpaths() {
		return filterXpaths;
	}

	public void setFilterXpaths(String filterXpaths) {
		this.filterXpaths = filterXpaths;
	}

	public String getPhoneXpath() {
		return phoneXpath;
	}

	public void setPhoneXpath(String phoneXpath) {
		this.phoneXpath = phoneXpath;
	}

	public String getWorkNoXpath() {
		return workNoXpath;
	}

	public void setWorkNoXpath(String workNoXpath) {
		this.workNoXpath = workNoXpath;
	}

	public String getUsrIdXpath() {
		return usrIdXpath;
	}

	public void setUsrIdXpath(String usrIdXpath) {
		this.usrIdXpath = usrIdXpath;
	}

	public String getAcctIdXpath() {
		return acctIdXpath;
	}

	public void setAcctIdXpath(String acctIdXpath) {
		this.acctIdXpath = acctIdXpath;
	}

	public String getPartitionXpath() {
		return partitionXpath;
	}

	public void setPartitionXpath(String partitionXpath) {
		this.partitionXpath = partitionXpath;
	}

	public String getFixedValues() {
		return fixedValues;
	}

	public void setFixedValues(String fixedValues) {
		this.fixedValues = fixedValues;
	}


}