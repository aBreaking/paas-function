package com.sitech.esb.hb;


/**
 * HttpSrvinfo entity. @author MyEclipse Persistence Tools
 */

public class HsfSrvinfo extends SrvInfo implements java.io.Serializable {

	// Fields    

	private String groupName;
	private String zookeeperAppName;
	private String protocolType;
	private String loadbalance;
	private SoaphttpSystem soaphttpSystem;
	private String srcServiceName;
	//	private String saddress;
	//	private String reqContentType;
	//	private String acceptType;
	private String reqCharset;
	private String resCharset;
	private Long reqTimeout;
	private String httpHeaders;
	private Long isInputXml;
	private Long isOutputXml;
	private String retcodeXpath;
	private String retcodeXpath2;
	private String retmsgXpath;
	private String outXpaths;
	private String filterXpaths;

	// Property accessors

	public SoaphttpSystem getSoaphttpSystem() {
		return this.soaphttpSystem;
	}

	public void setSoaphttpSystem(SoaphttpSystem soaphttpSystem) {
		this.soaphttpSystem = soaphttpSystem;
	}

	public String getReqCharset() {
		return this.reqCharset;
	}

	public void setReqCharset(String reqCharset) {
		this.reqCharset = reqCharset;
	}

	public String getResCharset() {
		return this.resCharset;
	}

	public void setResCharset(String resCharset) {
		this.resCharset = resCharset;
	}

	public Long getReqTimeout() {
		return this.reqTimeout;
	}

	public void setReqTimeout(Long reqTimeout) {
		this.reqTimeout = reqTimeout;
	}

	public String getHttpHeaders() {
		return this.httpHeaders;
	}

	public void setHttpHeaders(String httpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	public Long getIsInputXml() {
		return this.isInputXml;
	}

	public void setIsInputXml(Long isInputXml) {
		this.isInputXml = isInputXml;
	}

	public Long getIsOutputXml() {
		return this.isOutputXml;
	}

	public void setIsOutputXml(Long isOutputXml) {
		this.isOutputXml = isOutputXml;
	}

	public String getRetcodeXpath() {
		return this.retcodeXpath;
	}

	public void setRetcodeXpath(String retcodeXpath) {
		this.retcodeXpath = retcodeXpath;
	}

	public String getRetcodeXpath2() {
		return this.retcodeXpath2;
	}

	public void setRetcodeXpath2(String retcodeXpath2) {
		this.retcodeXpath2 = retcodeXpath2;
	}

	public String getRetmsgXpath() {
		return this.retmsgXpath;
	}

	public void setRetmsgXpath(String retmsgXpath) {
		this.retmsgXpath = retmsgXpath;
	}

	public String getOutXpaths() {
		return this.outXpaths;
	}

	public void setOutXpaths(String outXpaths) {
		this.outXpaths = outXpaths;
	}

	public String getFilterXpaths() {
		return this.filterXpaths;
	}

	public void setFilterXpaths(String filterXpaths) {
		this.filterXpaths = filterXpaths;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getZookeeperAppName() {
		return zookeeperAppName;
	}

	public void setZookeeperAppName(String zookeeperAppName) {
		this.zookeeperAppName = zookeeperAppName;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public String getLoadbalance() {
		return loadbalance;
	}

	public void setLoadbalance(String loadbalance) {
		this.loadbalance = loadbalance;
	}

	public String getSrcServiceName() {
		return srcServiceName;
	}

	public void setSrcServiceName(String srcServiceName) {
		this.srcServiceName = srcServiceName;
	}

}