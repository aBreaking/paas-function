package com.sitech.esb.hb;

/**
 * SoapAdapterHis entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SoapAdapterHis extends SrvinfoHis implements
		java.io.Serializable {

	// Constructors
	private Long opId;
	private String sserviceName;
	private String swsdl;
	private String swsdlVersion;
	private String sendpoint;
	private Long isSpNamespace;
	private Long isInputXml;
	private Long isOutputXml;
	private String retcodeXpath;
	private String retcodeXpath2;
	private String retmsgXpath;
	private String servicesXml;
	private String srvWsdl;

	// Constructors
	
	//AgentSOAPHis
    private String swsdlAddr;
	private String saddress;
	private String nsTrans;
	private String operationTrans;
	private String reqmsgName;
	private String retmsgName;
	private String paramsMap;
	private String outXpaths;
	private String filterXpaths;
	private SoaphttpSystem soaphttpSystem;
	
	/** default constructor */
	public SoapAdapterHis() {
	}

	public String getSserviceName() {
		return sserviceName;
	}

	public void setSserviceName(String sserviceName) {
		this.sserviceName = sserviceName;
	}

	public String getSwsdl() {
		return swsdl;
	}

	public void setSwsdl(String swsdl) {
		this.swsdl = swsdl;
	}

	public String getSwsdlVersion() {
		return swsdlVersion;
	}

	public void setSwsdlVersion(String swsdlVersion) {
		this.swsdlVersion = swsdlVersion;
	}

	public String getSendpoint() {
		return sendpoint;
	}

	public void setSendpoint(String sendpoint) {
		this.sendpoint = sendpoint;
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

	public String getServicesXml() {
		return servicesXml;
	}

	public void setServicesXml(String servicesXml) {
		this.servicesXml = servicesXml;
	}

	public String getSrvWsdl() {
		return srvWsdl;
	}

	public void setSrvWsdl(String srvWsdl) {
		this.srvWsdl = srvWsdl;
	}

	public String getSwsdlAddr() {
		return swsdlAddr;
	}

	public void setSwsdlAddr(String swsdlAddr) {
		this.swsdlAddr = swsdlAddr;
	}

	public String getSaddress() {
		return saddress;
	}

	public void setSaddress(String saddress) {
		this.saddress = saddress;
	}

	public String getReqmsgName() {
		return reqmsgName;
	}

	public void setReqmsgName(String reqmsgName) {
		this.reqmsgName = reqmsgName;
	}

	public String getRetmsgName() {
		return retmsgName;
	}

	public void setRetmsgName(String retmsgName) {
		this.retmsgName = retmsgName;
	}

	public String getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(String paramsMap) {
		this.paramsMap = paramsMap;
	}

	public String getOutXpaths() {
		return outXpaths;
	}

	public void setOutXpaths(String outXpaths) {
		this.outXpaths = outXpaths;
	}

	public SoaphttpSystem getSoaphttpSystem() {
		return soaphttpSystem;
	}

	public void setSoaphttpSystem(SoaphttpSystem soaphttpSystem) {
		this.soaphttpSystem = soaphttpSystem;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Long getIsSpNamespace() {
		return isSpNamespace;
	}

	public void setIsSpNamespace(Long isSpNamespace) {
		this.isSpNamespace = isSpNamespace;
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

	public String getNsTrans() {
		return nsTrans;
	}

	public void setNsTrans(String nsTrans) {
		this.nsTrans = nsTrans;
	}

	public String getOperationTrans() {
		return operationTrans;
	}

	public void setOperationTrans(String operationTrans) {
		this.operationTrans = operationTrans;
	}

	public String getFilterXpaths() {
		return filterXpaths;
	}

	public void setFilterXpaths(String filterXpaths) {
		this.filterXpaths = filterXpaths;
	}


}
