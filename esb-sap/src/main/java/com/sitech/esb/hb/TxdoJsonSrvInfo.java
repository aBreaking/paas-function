package com.sitech.esb.hb;

import java.sql.Clob;

/**
 *  entity provides the base persistence definition of
 * the SiutypewsSrvinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TxdoJsonSrvInfo extends SrvInfo implements java.io.Serializable {

	// Fields
	private SiTxdoRouteType siTxdoRouteType;
	private String srcServiceName;
	private Clob inJsd;
    private Clob outJsd;
	private Long isCheckOutJsd;
	private String retCodeJpath;
	private String retCodeJpath2;
	private String retMsgJpath;
	private String phoneJpath;
	private String workNoJpath;
	private String regionIdJpath;
	private String usrIdJpath;
	private String custIdJpath;
	private String acctIdJpath;
	private String partitionJpath;
	
	private String outJpaths;
	private String filterJpaths;
	private String fixedValues;
	
	private TxdoSystem txdoSystem;
	
	// Constructors

	/** default constructor */
	public TxdoJsonSrvInfo() {
		
	}
	
	//ȡ�÷����������Ϣ��
    public TxdoJsonSrvInfo(Long srvId, String srvName, String srvChName) {
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

/*
	public String getOutXmlRootNode() {
		return outXmlRootNode;
	}

	public void setOutXmlRootNode(String outXmlRootNode) {
		this.outXmlRootNode = outXmlRootNode;
	}
*/
	

	public String getFixedValues() {
		return fixedValues;
	}

	public void setFixedValues(String fixedValues) {
		this.fixedValues = fixedValues;
	}

	public Clob getInJsd() {
		return inJsd;
	}

	public void setInJsd(Clob inJsd) {
		this.inJsd = inJsd;
	}

	public Clob getOutJsd() {
		return outJsd;
	}

	public void setOutJsd(Clob outJsd) {
		this.outJsd = outJsd;
	}

	public Long getIsCheckOutJsd() {
		return isCheckOutJsd;
	}

	public void setIsCheckOutJsd(Long isCheckOutJsd) {
		this.isCheckOutJsd = isCheckOutJsd;
	}

	public String getRetCodeJpath() {
		return retCodeJpath;
	}

	public void setRetCodeJpath(String retCodeJpath) {
		this.retCodeJpath = retCodeJpath;
	}

	public String getRetCodeJpath2() {
		return retCodeJpath2;
	}

	public void setRetCodeJpath2(String retCodeJpath2) {
		this.retCodeJpath2 = retCodeJpath2;
	}

	public String getRetMsgJpath() {
		return retMsgJpath;
	}

	public void setRetMsgJpath(String retMsgJpath) {
		this.retMsgJpath = retMsgJpath;
	}

	public String getPhoneJpath() {
		return phoneJpath;
	}

	public void setPhoneJpath(String phoneJpath) {
		this.phoneJpath = phoneJpath;
	}

	public String getWorkNoJpath() {
		return workNoJpath;
	}

	public void setWorkNoJpath(String workNoJpath) {
		this.workNoJpath = workNoJpath;
	}

	public String getRegionIdJpath() {
		return regionIdJpath;
	}

	public void setRegionIdJpath(String regionIdJpath) {
		this.regionIdJpath = regionIdJpath;
	}

	public String getUsrIdJpath() {
		return usrIdJpath;
	}

	public void setUsrIdJpath(String usrIdJpath) {
		this.usrIdJpath = usrIdJpath;
	}

	public String getCustIdJpath() {
		return custIdJpath;
	}

	public void setCustIdJpath(String custIdJpath) {
		this.custIdJpath = custIdJpath;
	}

	public String getAcctIdJpath() {
		return acctIdJpath;
	}

	public void setAcctIdJpath(String acctIdJpath) {
		this.acctIdJpath = acctIdJpath;
	}

	public String getPartitionJpath() {
		return partitionJpath;
	}

	public void setPartitionJpath(String partitionJpath) {
		this.partitionJpath = partitionJpath;
	}

	public String getOutJpaths() {
		return outJpaths;
	}

	public void setOutJpaths(String outJpaths) {
		this.outJpaths = outJpaths;
	}

	public String getFilterJpaths() {
		return filterJpaths;
	}

	public void setFilterJpaths(String filterJpaths) {
		this.filterJpaths = filterJpaths;
	}

	public TxdoSystem getTxdoSystem() {
		return txdoSystem;
	}

	public void setTxdoSystem(TxdoSystem txdoSystem) {
		this.txdoSystem = txdoSystem;
	}


}