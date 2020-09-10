package com.sitech.esb.hb;

import java.util.HashSet;
import java.util.Set;

/**
 * JsClass entity provides the base persistence definition of the
 * JsClass entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  JsClass implements java.io.Serializable {

	// Fields

	private Long jsClassId;
	private JsClass jsParentClass;
	private String className;
	private String classDesc;
	private Set jsClassMembers = new HashSet(0);

	// Constructors

	/** default constructor */
	public  JsClass() {
	}

	/** minimal constructor */
	public  JsClass(String className) {
		this.className = className;
	}

	// Property accessors

	public Long getJsClassId() {
		return this.jsClassId;
	}

	public void setJsClassId(Long jsClassId) {
		this.jsClassId = jsClassId;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassDesc() {
		return this.classDesc;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}

	public JsClass getJsParentClass() {
		return jsParentClass;
	}

	public void setJsParentClass(JsClass jsParentClass) {
		this.jsParentClass = jsParentClass;
	}

	public Set getJsClassMembers() {
		return jsClassMembers;
	}

	public void setJsClassMembers(Set jsClassMembers) {
		this.jsClassMembers = jsClassMembers;
	}

}