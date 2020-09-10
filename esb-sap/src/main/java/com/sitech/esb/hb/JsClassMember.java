package com.sitech.esb.hb;

/**
 * JsClassMember entity provides the base persistence definition of the
 * JsClassMember entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  JsClassMember implements java.io.Serializable {

	// Fields

	private Long jsMemberId;
	private JsClass jsClass;
	private JsClass jsReturnClass;
	private String jsMemberName;
	private Long jsMemberType;
	private Long jsMemberCat;
	private String jsTipsContent;
	private String jsMemberDescribe;

	// Constructors

	/** default constructor */
	public  JsClassMember() {
	}

	// Property accessors

	public Long getJsMemberId() {
		return this.jsMemberId;
	}

	public void setJsMemberId(Long jsMemberId) {
		this.jsMemberId = jsMemberId;
	}

	public String getJsMemberName() {
		return this.jsMemberName;
	}

	public void setJsMemberName(String jsMemberName) {
		this.jsMemberName = jsMemberName;
	}

	public Long getJsMemberType() {
		return this.jsMemberType;
	}

	public void setJsMemberType(Long jsMemberType) {
		this.jsMemberType = jsMemberType;
	}

	public Long getJsMemberCat() {
		return this.jsMemberCat;
	}

	public void setJsMemberCat(Long jsMemberCat) {
		this.jsMemberCat = jsMemberCat;
	}

	public String getJsTipsContent() {
		return this.jsTipsContent;
	}

	public void setJsTipsContent(String jsTipsContent) {
		this.jsTipsContent = jsTipsContent;
	}

	public String getJsMemberDescribe() {
		return this.jsMemberDescribe;
	}

	public void setJsMemberDescribe(String jsMemberDescribe) {
		this.jsMemberDescribe = jsMemberDescribe;
	}

	public JsClass getJsClass() {
		return jsClass;
	}

	public void setJsClass(JsClass jsClass) {
		this.jsClass = jsClass;
	}

	public JsClass getJsReturnClass() {
		return jsReturnClass;
	}

	public void setJsReturnClass(JsClass jsReturnClass) {
		this.jsReturnClass = jsReturnClass;
	}

}