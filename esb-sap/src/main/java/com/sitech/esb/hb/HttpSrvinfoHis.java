package com.sitech.esb.hb;

/**
 * HttpSrvinfoHis entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class HttpSrvinfoHis extends SrvinfoHis implements
		java.io.Serializable {

	// Constructors
	private Long opId;
	private String saddress;
	private String reqContentType;
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
	private SoaphttpSystem soaphttpSystem;

	/** default constructor */
	public HttpSrvinfoHis() {
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getSaddress() {
		return saddress;
	}

	public void setSaddress(String saddress) {
		this.saddress = saddress;
	}

	public String getReqContentType() {
		return reqContentType;
	}

	public void setReqContentType(String reqContentType) {
		this.reqContentType = reqContentType;
	}

	public String getReqCharset() {
		return reqCharset;
	}

	public void setReqCharset(String reqCharset) {
		this.reqCharset = reqCharset;
	}

	public String getResCharset() {
		return resCharset;
	}

	public void setResCharset(String resCharset) {
		this.resCharset = resCharset;
	}

	public Long getReqTimeout() {
		return reqTimeout;
	}

	public void setReqTimeout(Long reqTimeout) {
		this.reqTimeout = reqTimeout;
	}

	public String getHttpHeaders() {
		return httpHeaders;
	}

	public void setHttpHeaders(String httpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	public Long getIsInputXml() {
		return isInputXml;
	}

	public void setIsInputXml(Long isInputXml) {
		this.isInputXml = isInputXml;
	}

	public Long getIsOutputXml() {
		return isOutputXml;
	}

	public void setIsOutputXml(Long isOutputXml) {
		this.isOutputXml = isOutputXml;
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

	public SoaphttpSystem getSoaphttpSystem() {
		return soaphttpSystem;
	}

	public void setSoaphttpSystem(SoaphttpSystem soaphttpSystem) {
		this.soaphttpSystem = soaphttpSystem;
	}


}
