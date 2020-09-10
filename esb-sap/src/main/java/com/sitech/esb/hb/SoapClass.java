package com.sitech.esb.hb;

import java.util.HashSet;
import java.util.Set;

/**
 * SoapClass entity provides the base persistence definition of the
 * SoapClass entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SoapClass implements java.io.Serializable {

	// Fields

	private Long classId;
	private String className;
	private java.sql.Blob classContent;
	
	private SoapAdapter soapAdapter;
	
	private Set soapClzMethods = new HashSet();

	// Constructors

	/** default constructor */
	public  SoapClass() {
	}

	/** full constructor */
	public  SoapClass(Long srvId, String className, java.sql.Blob classContent) {
		this.className = className;
		this.classContent = classContent;
	}

	// Property accessors

	public Long getClassId() {
		return this.classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public java.sql.Blob getClassContent() {
		return this.classContent;
	}

	public void setClassContent(java.sql.Blob classContent) {
		this.classContent = classContent;
	}

	public SoapAdapter getSoapAdapter() {
		return soapAdapter;
	}

	public void setSoapAdapter(SoapAdapter soapAdapter) {
		this.soapAdapter = soapAdapter;
	}

	public Set getSoapClzMethods() {
		return soapClzMethods;
	}

	public void setSoapClzMethods(Set soapClzMethods) {
		this.soapClzMethods = soapClzMethods;
	}

}