package com.sitech.esb.hb;

import java.math.BigDecimal;


/**
 * HttpSrvinfo entity. @author MyEclipse Persistence Tools
 */
public class HttpSrvinfo extends SrvInfo implements java.io.Serializable {


    // Fields    
     private SoaphttpSystem soaphttpSystem;
     private String saddress;
     private String reqContentType;
     private String acceptType;
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
     
     private String postJoinUp;
     private String getJoinUp;
     private String putJoinUp;
     private String deleteJoinUp;


    // Constructors

    /** default constructor */
    public HttpSrvinfo() {
    }

	/** minimal constructor */
    public HttpSrvinfo(BigDecimal srvId, SrvInfo srvinfo, SoaphttpSystem soaphttpSystem, String saddress, String reqContentType, String reqCharset, String resCharset, Long reqTimeout, Long isInputXml, Long isOutputXml) {
        
        this.soaphttpSystem = soaphttpSystem;
        this.saddress = saddress;
        this.reqContentType = reqContentType;
        this.reqCharset = reqCharset;
        this.resCharset = resCharset;
        this.reqTimeout = reqTimeout;
        this.isInputXml = isInputXml;
        this.isOutputXml = isOutputXml;
    }
    
    /** full constructor */
    public HttpSrvinfo(BigDecimal srvId, SrvInfo srvinfo, SoaphttpSystem soaphttpSystem, String saddress, String reqContentType, String reqCharset, String resCharset, Long reqTimeout, String httpHeaders, Long isInputXml, Long isOutputXml, String retcodeXpath, String retcodeXpath2, String retmsgXpath, String outXpaths, String filterXpaths) {
       
        this.soaphttpSystem = soaphttpSystem;
        this.saddress = saddress;
        this.reqContentType = reqContentType;
        this.reqCharset = reqCharset;
        this.resCharset = resCharset;
        this.reqTimeout = reqTimeout;
        this.httpHeaders = httpHeaders;
        this.isInputXml = isInputXml;
        this.isOutputXml = isOutputXml;
        this.retcodeXpath = retcodeXpath;
        this.retcodeXpath2 = retcodeXpath2;
        this.retmsgXpath = retmsgXpath;
        this.outXpaths = outXpaths;
        this.filterXpaths = filterXpaths;
    }

   
    // Property accessors

    public SoaphttpSystem getSoaphttpSystem() {
        return this.soaphttpSystem;
    }
    
    public void setSoaphttpSystem(SoaphttpSystem soaphttpSystem) {
        this.soaphttpSystem = soaphttpSystem;
    }

    public String getSaddress() {
        return this.saddress;
    }
    
    public void setSaddress(String saddress) {
        this.saddress = saddress;
    }

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

	public String getAcceptType() {
		return acceptType;
	}

	public void setAcceptType(String acceptType) {
		this.acceptType = acceptType;
	}

	public String getPostJoinUp() {
		return postJoinUp;
	}

	public void setPostJoinUp(String postJoinUp) {
		this.postJoinUp = postJoinUp;
	}

	public String getGetJoinUp() {
		return getJoinUp;
	}

	public void setGetJoinUp(String getJoinUp) {
		this.getJoinUp = getJoinUp;
	}
	
	public String getPutJoinUp() {
		return putJoinUp;
	}

	public void setPutJoinUp(String putJoinUp) {
		this.putJoinUp = putJoinUp;
	}

	public String getDeleteJoinUp() {
		return deleteJoinUp;
	}

	public void setDeleteJoinUp(String deleteJoinUp) {
		this.deleteJoinUp = deleteJoinUp;
	}

	public static void main(String[] args) {
		SrvInfo bean = new HttpSrvinfo();
	}
}