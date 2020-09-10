package com.sitech.esb.hb;

import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SrvinfoHis entity provides the base persistence definition of the
 * SrvinfoHis entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SrvinfoHis implements java.io.Serializable {

	// Fields

	private Long opId;
	private Long srvId;
	private String srvName;
	private String srvChName;
	private String srvVer;
	private String srvModifyTime;
	private String businessUuid;
	private String srvUuid;
	private String srvpubInfo;
	private Long srvOrder;
	private Long isdumpCoremsg;
	private Long isCoreSrv;
	private Long isUnifySrv;
	private Long isneedUpdateuddi;
	private String sreqxmlEncoding;
	private String retxmlEncoding;
	private Long needSaveInparam;
	private Long needSaveOutparam;
	private Long validFlag;
	private String srvDescribe;
	private String opUser;
	private String opUserIp;
	private String srvWsdlAddr;
	private SrvStatus srvStatus;
	private Long needInparamCheck=new Long(0);
	private String inparamCheckLogic;
	private String inparamsDemo;
	private Date opTime;
	private SrvType srvType;
	private Set srvInputs = new HashSet();
	private Set srvOutputs = new HashSet();
	//UtypeSrvinfoHis
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
//	private String fixedValues;
	private SiTxdoRouteType routeType;
	private TxdoSystem txdoSystem;
	
    // Constructors
 
	//Soap_AdapterHis
	
	 private String swsdl;
	 private String swsdlVersion;
	 private String sendpoint;
	 private Long isSpNamespace;
	 private Long isInputXml;
	 private Long isOutputXml;
	 private String servicesXml;
	 private String srvWsdl;
     //AgentSOAPHis
     private String swsdlAddr;
	 private String saddress;
	 private String nsTrans;
	 private String operationTrans;
	 private String reqmsgName;
	 private String retmsgName;
	 private String paramsMap;
	 private SoaphttpSystem soaphttpSystem;
	
	//zhengyq@20110201
		private Long isSwsdlJar;
		private Blob rarzip_content;
		//end zhengyq@20110201
	
	//xmlCOMHis 
	
	private Clob comLogic;
	
   //SITXDSRVHis
	private String txdRetnums;
	private Long phoneInputno;
	private Long opridInputno;
	private Long regionInputno;
  	
	//SITXDCOM-SRV-HIS
	
	private Long usridInputno;
	
	//http_srvinfo_his
	
	private String reqContentType;
	private String reqCharset;
	private String resCharset;
	private Long reqTimeout;
	private String httpHeaders;

	
	
	//utype0_srvinfo_his
	
	
	//selfSoapsrvinfoHis
	private String soperationName;

	/** default constructor */
	public SrvinfoHis() {
	}

	/** minimal constructor */
	public SrvinfoHis(Long srvId, String srvName, String srvChName,
			String businessUuid, String srvUuid, Long isdumpCoremsg,
			Long isneedUpdateuddi, Long needSaveInparam, Long validFlag,
			Long needInparamCheck, String inparamsDemo,SrvType srvType) {
		this.srvId = srvId;
		this.srvName = srvName;
		this.srvChName = srvChName;
		this.businessUuid = businessUuid;
		this.srvUuid = srvUuid;
		this.isdumpCoremsg = isdumpCoremsg;
		this.isneedUpdateuddi = isneedUpdateuddi;
		this.needSaveInparam = needSaveInparam;
		this.validFlag = validFlag;
		this.needInparamCheck = needInparamCheck;
		this.inparamsDemo = inparamsDemo;
		this.srvType = srvType;
	}

	/** full constructor */
	public SrvinfoHis(Long srvId, String srvName, String srvChName,
			String srvVer, String businessUuid, String srvUuid,
			String srvpubInfo, Long srvOrder, Long isdumpCoremsg,
			Long isneedUpdateuddi, String sreqxmlEncoding,
			String retxmlEncoding, Long needSaveInparam, Long validFlag,
			String srvDescribe, String opUser, String opUserIp,
			String srvWsdlAddr,  SrvStatus srvStatus, Long needInparamCheck,
			String inparamCheckLogic, String inparamsDemo, Date opTime,SrvType srvType) {
		this.srvId = srvId;
		this.srvName = srvName;
		this.srvChName = srvChName;
		this.srvVer = srvVer;
		this.businessUuid = businessUuid;
		this.srvUuid = srvUuid;
		this.srvpubInfo = srvpubInfo;
		this.srvOrder = srvOrder;
		this.isdumpCoremsg = isdumpCoremsg;
		this.isneedUpdateuddi = isneedUpdateuddi;
		this.sreqxmlEncoding = sreqxmlEncoding;
		this.retxmlEncoding = retxmlEncoding;
		this.needSaveInparam = needSaveInparam;
		this.validFlag = validFlag;
		this.srvDescribe = srvDescribe;
		this.opUser = opUser;
		this.opUserIp = opUserIp;
		this.srvWsdlAddr = srvWsdlAddr;
		this.srvStatus = srvStatus;
		this.needInparamCheck = needInparamCheck;
		this.inparamCheckLogic = inparamCheckLogic;
		this.inparamsDemo = inparamsDemo;
		this.opTime = opTime;
		this.srvType =srvType;
	}

	// Property accessors

	public Long getOpId() {
		return this.opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Long getSrvId() {
		return this.srvId;
	}

	public void setSrvId(Long srvId) {
		this.srvId = srvId;
	}

	public String getSrvName() {
		return this.srvName;
	}

	public void setSrvName(String srvName) {
		this.srvName = srvName;
	}

	public String getSrvChName() {
		return this.srvChName;
	}

	public void setSrvChName(String srvChName) {
		this.srvChName = srvChName;
	}

	public String getSrvVer() {
		return this.srvVer;
	}

	public void setSrvVer(String srvVer) {
		this.srvVer = srvVer;
	}

	public String getBusinessUuid() {
		return this.businessUuid;
	}

	public void setBusinessUuid(String businessUuid) {
		this.businessUuid = businessUuid;
	}

	public String getSrvUuid() {
		return this.srvUuid;
	}

	public void setSrvUuid(String srvUuid) {
		this.srvUuid = srvUuid;
	}

	public String getSrvpubInfo() {
		return this.srvpubInfo;
	}

	public void setSrvpubInfo(String srvpubInfo) {
		this.srvpubInfo = srvpubInfo;
	}

	public Long getSrvOrder() {
		return this.srvOrder;
	}

	public void setSrvOrder(Long srvOrder) {
		this.srvOrder = srvOrder;
	}

	public Long getIsdumpCoremsg() {
		return this.isdumpCoremsg;
	}

	public void setIsdumpCoremsg(Long isdumpCoremsg) {
		this.isdumpCoremsg = isdumpCoremsg;
	}

	public Long getIsneedUpdateuddi() {
		return this.isneedUpdateuddi;
	}

	public void setIsneedUpdateuddi(Long isneedUpdateuddi) {
		this.isneedUpdateuddi = isneedUpdateuddi;
	}

	public String getSreqxmlEncoding() {
		return this.sreqxmlEncoding;
	}

	public void setSreqxmlEncoding(String sreqxmlEncoding) {
		this.sreqxmlEncoding = sreqxmlEncoding;
	}

	public String getRetxmlEncoding() {
		return this.retxmlEncoding;
	}

	public void setRetxmlEncoding(String retxmlEncoding) {
		this.retxmlEncoding = retxmlEncoding;
	}

	public Long getNeedSaveInparam() {
		return this.needSaveInparam;
	}

	public void setNeedSaveInparam(Long needSaveInparam) {
		this.needSaveInparam = needSaveInparam;
	}

	public Long getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(Long validFlag) {
		this.validFlag = validFlag;
	}

	public String getSrvDescribe() {
		return this.srvDescribe;
	}

	public void setSrvDescribe(String srvDescribe) {
		this.srvDescribe = srvDescribe;
	}

	public String getOpUser() {
		return this.opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public String getOpUserIp() {
		return this.opUserIp;
	}

	public void setOpUserIp(String opUserIp) {
		this.opUserIp = opUserIp;
	}

	public String getSrvWsdlAddr() {
		return this.srvWsdlAddr;
	}

	public void setSrvWsdlAddr(String srvWsdlAddr) {
		this.srvWsdlAddr = srvWsdlAddr;
	}

	public SrvStatus getSrvStatus() {
		return this.srvStatus;
	}

	public void setSrvStatus(SrvStatus srvStatus) {
		this.srvStatus = srvStatus;
	}

	public Long getNeedInparamCheck() {
		return this.needInparamCheck;
	}

	public void setNeedInparamCheck(Long needInparamCheck) {
		this.needInparamCheck = needInparamCheck;
	}

	public String getInparamCheckLogic() {
		return this.inparamCheckLogic;
	}

	public void setInparamCheckLogic(String inparamCheckLogic) {
		this.inparamCheckLogic = inparamCheckLogic;
	}

	public String getInparamsDemo() {
		return this.inparamsDemo;
	}

	public void setInparamsDemo(String inparamsDemo) {
		this.inparamsDemo = inparamsDemo;
	}

	public Date getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
 
	public SrvType getSrvType() {
		return srvType;
	}

   public void setSrvType(SrvType srvType) {
		this.srvType = srvType;
	}
	
	
	//UtypeSrvinfoHis
	
	public String getSserviceName() {
		return this.sserviceName;
	}

	public void setSserviceName(String sserviceName) {
		this.sserviceName = sserviceName;
	}

	public Clob getInxsd() {
		return this.inxsd;
	}

	public void setInxsd(Clob inxsd) {
		this.inxsd = inxsd;
	}

	public Clob getOutxsd() {
		return this.outxsd;
	}

	public void setOutxsd(Clob outxsd) {
		this.outxsd = outxsd;
	}

	public Long getIscheckOutxsd() {
		return this.ischeckOutxsd;
	}

	public void setIscheckOutxsd(Long ischeckOutxsd) {
		this.ischeckOutxsd = ischeckOutxsd;
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

	public String getPhoneXpath() {
		return this.phoneXpath;
	}

	public void setPhoneXpath(String phoneXpath) {
		this.phoneXpath = phoneXpath;
	}

	public String getUsridXpath() {
		return this.usridXpath;
	}

	public void setUsridXpath(String usridXpath) {
		this.usridXpath = usridXpath;
	}

	public String getAcctidXpath() {
		return this.acctidXpath;
	}

	public void setAcctidXpath(String acctidXpath) {
		this.acctidXpath = acctidXpath;
	}

	public String getCustidXpath() {
		return this.custidXpath;
	}

	public void setCustidXpath(String custidXpath) {
		this.custidXpath = custidXpath;
	}

	public String getWorknoXpath() {
		return this.worknoXpath;
	}

	public void setWorknoXpath(String worknoXpath) {
		this.worknoXpath = worknoXpath;
	}

	public String getRegionidXpath() {
		return this.regionidXpath;
	}

	public void setRegionidXpath(String regionidXpath) {
		this.regionidXpath = regionidXpath;
	}

	public String getPartitionXpath() {
		return this.partitionXpath;
	}

	public void setPartitionXpath(String partitionXpath) {
		this.partitionXpath = partitionXpath;
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

	public SiTxdoRouteType getRouteType() {
		return this.routeType;
	}

	public void setRouteType(SiTxdoRouteType routeType) {
		this.routeType = routeType;
	}
	
	public String getSwsdl() {
		return this.swsdl;
	}

	public void setSwsdl(String swsdl) {
		this.swsdl = swsdl;
	}

	public String getSwsdlVersion() {
		return this.swsdlVersion;
	}

	public void setSwsdlVersion(String swsdlVersion) {
		this.swsdlVersion = swsdlVersion;
	}

	public String getSendpoint() {
		return this.sendpoint;
	}

	public void setSendpoint(String sendpoint) {
		this.sendpoint = sendpoint;
	}

	public Long getIsSpNamespace() {
		return this.isSpNamespace;
	}

	public void setIsSpNamespace(Long isSpNamespace) {
		this.isSpNamespace = isSpNamespace;
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

	public String getServicesXml() {
		return this.servicesXml;
	}

	public void setServicesXml(String servicesXml) {
		this.servicesXml = servicesXml;
	}

	public String getSrvWsdl() {
		return this.srvWsdl;
	}

	public void setSrvWsdl(String srvWsdl) {
		this.srvWsdl = srvWsdl;
	}

	
	
	public String getSwsdlAddr() {
		return this.swsdlAddr;
	}

	public void setSwsdlAddr(String swsdlAddr) {
		this.swsdlAddr = swsdlAddr;
	}

	public String getSaddress() {
		return this.saddress;
	}

	public void setSaddress(String saddress) {
		this.saddress = saddress;
	}

	public String getNsTrans() {
		return this.nsTrans;
	}

	public void setNsTrans(String nsTrans) {
		this.nsTrans = nsTrans;
	}

	public String getOperationTrans() {
		return this.operationTrans;
	}

	public void setOperationTrans(String operationTrans) {
		this.operationTrans = operationTrans;
	}

	public String getReqmsgName() {
		return this.reqmsgName;
	}

	public void setReqmsgName(String reqmsgName) {
		this.reqmsgName = reqmsgName;
	}

	public String getRetmsgName() {
		return this.retmsgName;
	}

	public void setRetmsgName(String retmsgName) {
		this.retmsgName = retmsgName;
	}

	public String getParamsMap() {
		return this.paramsMap;
	}

	public void setParamsMap(String paramsMap) {
		this.paramsMap = paramsMap;
	}

	public Clob getComLogic() {
		return this.comLogic;
	}

	public void setComLogic(Clob comLogic) {
		this.comLogic = comLogic;
	}

	
	public String getTxdRetnums() {
		return this.txdRetnums;
	}

	public void setTxdRetnums(String txdRetnums) {
		this.txdRetnums = txdRetnums;
	}

	public Long getPhoneInputno() {
		return this.phoneInputno;
	}

	public void setPhoneInputno(Long phoneInputno) {
		this.phoneInputno = phoneInputno;
	}

	public Long getOpridInputno() {
		return this.opridInputno;
	}

	public void setOpridInputno(Long opridInputno) {
		this.opridInputno = opridInputno;
	}

	public Long getRegionInputno() {
		return this.regionInputno;
	}

	public void setRegionInputno(Long regionInputno) {
		this.regionInputno = regionInputno;
	}

	public Long getUsridInputno() {
		return this.usridInputno;
	}

	public void setUsridInputno(Long usridInputno) {
		this.usridInputno = usridInputno;
	}
  
	//http_srvinfo_his
	

	public String getReqContentType() {
		return this.reqContentType;
	}

	public void setReqContentType(String reqContentType) {
		this.reqContentType = reqContentType;
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

	
	
	public Set getSrvInputs() {
		return srvInputs;
	}

	public void setSrvInputs(Set srvInputs) {
		this.srvInputs = srvInputs;
	}
	public Set getSrvOutputs() {
		return srvOutputs;
	}

	public void setSrvOutputs(Set srvOutputs) {
		this.srvOutputs = srvOutputs;
	}
	
	public String getSoperationName() {
		return this.soperationName;
	}

	public void setSoperationName(String soperationName) {
		this.soperationName = soperationName;
	}

	public TxdoSystem getTxdoSystem() {
		return txdoSystem;
	}

	public void setTxdoSystem(TxdoSystem txdoSystem) {
		this.txdoSystem = txdoSystem;
	}

	public SoaphttpSystem getSoaphttpSystem() {
		return soaphttpSystem;
	}

	public void setSoaphttpSystem(SoaphttpSystem soaphttpSystem) {
		this.soaphttpSystem = soaphttpSystem;
	}

	public String getSrvModifyTime() {
		return srvModifyTime;
	}

	public void setSrvModifyTime(String srvModifyTime) {
		this.srvModifyTime = srvModifyTime;
	}

	public Long getNeedSaveOutparam() {
		return needSaveOutparam;
	}

	public void setNeedSaveOutparam(Long needSaveOutparam) {
		this.needSaveOutparam = needSaveOutparam;
	}

	public Long getIsSwsdlJar() {
		return isSwsdlJar;
	}

	public void setIsSwsdlJar(Long isSwsdlJar) {
		this.isSwsdlJar = isSwsdlJar;
	}

	public Blob getRarzip_content() {
		return rarzip_content;
	}

	public void setRarzip_content(Blob rarzip_content) {
		this.rarzip_content = rarzip_content;
	}

	public Long getIsCoreSrv() {
		return isCoreSrv;
	}

	public void setIsCoreSrv(Long isCoreSrv) {
		this.isCoreSrv = isCoreSrv;
	}

	public Long getIsUnifySrv() {
		return isUnifySrv;
	}

	public void setIsUnifySrv(Long isUnifySrv) {
		this.isUnifySrv = isUnifySrv;
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