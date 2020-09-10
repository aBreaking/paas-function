package com.sitech.esb.hb;

/**
 * SoapClzMethod entity provides the base persistence definition of the
 * SoapClzMethod entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SoapClzMethod implements java.io.Serializable {

	// Fields

	private Long soapclzMethodId;
	private String soapclzMethodname;
	private String soapclzMethodDesc;
	
	private SoapClass soapClass;

	// Constructors

	/** default constructor */
	public SoapClzMethod() {
	}

	/** full constructor */
	public SoapClzMethod(Long classId, String soapclzMethodname,
			String soapclzMethodDesc) {
		this.soapclzMethodname = soapclzMethodname;
		this.soapclzMethodDesc = soapclzMethodDesc;
	}

	// Property accessors

	public String getSoapclzMethodname() {
		return this.soapclzMethodname;
	}

	public void setSoapclzMethodname(String soapclzMethodname) {
		this.soapclzMethodname = soapclzMethodname;
	}

	public String getSoapclzMethodDesc() {
		return this.soapclzMethodDesc;
	}

	public void setSoapclzMethodDesc(String soapclzMethodDesc) {
		this.soapclzMethodDesc = soapclzMethodDesc;
	}

	public Long getSoapclzMethodId() {
		return soapclzMethodId;
	}

	public void setSoapclzMethodId(Long soapclzMethodId) {
		this.soapclzMethodId = soapclzMethodId;
	}

	public SoapClass getSoapClass() {
		return soapClass;
	}

	public void setSoapClass(SoapClass soapClass) {
		this.soapClass = soapClass;
	}

}