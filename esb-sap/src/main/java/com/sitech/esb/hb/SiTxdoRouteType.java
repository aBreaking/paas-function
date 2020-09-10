package com.sitech.esb.hb;

/**
 * SiTxdoRouteType entity provides the base persistence definition of
 * the SiTxdoRouteType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SiTxdoRouteType implements java.io.Serializable {

	// Fields

	private Long routeType;
	private String routeTypeName;
	private Long validFlag;

	// Constructors

	/** default constructor */
	public SiTxdoRouteType() {
	}

	/** full constructor */
	public SiTxdoRouteType(String routeTypeName, Long validFlag) {
		this.routeTypeName = routeTypeName;
		this.validFlag = validFlag;
	}

	// Property accessors

	public Long getRouteType() {
		return this.routeType;
	}

	public void setRouteType(Long routeType) {
		this.routeType = routeType;
	}

	public String getRouteTypeName() {
		return this.routeTypeName;
	}

	public void setRouteTypeName(String routeTypeName) {
		this.routeTypeName = routeTypeName;
	}

	public Long getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(Long validFlag) {
		this.validFlag = validFlag;
	}

}