package com.sitech.esb.hb;

/**
 * SrvCategRel entity provides the base persistence definition of the
 * SrvCategRel entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SrvCategRel implements java.io.Serializable {

	// Fields

	private SrvCategRelId id;

	// Constructors

	/** default constructor */
	public SrvCategRel() {
	}

	/** full constructor */
	public SrvCategRel(SrvCategRelId id) {
		this.id = id;
	}

	// Property accessors

	public SrvCategRelId getId() {
		return this.id;
	}

	public void setId(SrvCategRelId id) {
		this.id = id;
	}

}