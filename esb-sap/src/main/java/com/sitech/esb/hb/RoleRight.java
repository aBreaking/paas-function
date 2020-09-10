package com.sitech.esb.hb;

/**
 * RoleRight entity provides the base persistence definition of the
 * RoleRight entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  RoleRight implements java.io.Serializable {

	// Fields

	private RoleRightId id;

	// Constructors

	/** default constructor */
	public  RoleRight() {
	}

	/** full constructor */
	public  RoleRight(RoleRightId id) {
		this.id = id;
	}

	// Property accessors

	public RoleRightId getId() {
		return this.id;
	}

	public void setId(RoleRightId id) {
		this.id = id;
	}

}