package com.sitech.esb.hb;

import java.sql.Blob;

/**
 * CommonskeletonSrvinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CommonskeletonSrvinfo extends SrvInfo implements java.io.Serializable {

	// Fields

	private Long srvId;
	private String serviceCode;
	private Blob res_content;
	private SoaphttpSystem soaphttpSystem;
	private Long isSoap;
	// Constructors

	/** default constructor */
	public CommonskeletonSrvinfo() {
	}

	/** full constructor */
	public CommonskeletonSrvinfo(Long srvId, String targetWsdl, String reqXslt,
			String retXslt, String logicJavaXslt, String isreqstring,
			String isretstring,Blob res_content) {
		this.srvId = srvId;
		/*this.targetWsdl = targetWsdl;
		this.reqXslt = reqXslt;
		this.retXslt = retXslt;
		this.logicJavaXslt = logicJavaXslt;
		this.isreqstring = isreqstring;
		this.isretstring = isretstring;*/
		this.res_content=res_content;
	}


	public Long getSrvId() {
		return this.srvId;
	}

	public void setSrvId(Long srvId) {
		this.srvId = srvId;
	}



	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public Blob getres_content() {
		return this.res_content;
	}

	public void setres_content(Blob res_content) {
		this.res_content = res_content;
	}
	
	public SoaphttpSystem getSoaphttpSystem() {
		return soaphttpSystem;
	}

	public void setSoaphttpSystem(SoaphttpSystem soaphttpSystem) {
		this.soaphttpSystem = soaphttpSystem;
	}

	public Long getIsSoap() {
		return isSoap;
	}

	public void setIsSoap(Long isSoap) {
		this.isSoap = isSoap;
	}

}