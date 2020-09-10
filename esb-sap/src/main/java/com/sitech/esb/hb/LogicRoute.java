package com.sitech.esb.hb;

/**
 * TxdoRemoteAp entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class LogicRoute implements java.io.Serializable {

	// Constructors

	private Long routeId;
	private String systemName;
	private String routeName;
	private String routeValue;
	private Long weight;
	private Long validFlag;
	private String routeType;
	

	public LogicRoute(){
	}
	
	
	public LogicRoute(Long routeId, String routeName, String routeValue,
			Long weight, Long validFlag, String routeType) {
		
		this.routeId = routeId;
		this.routeName = routeName;
		this.routeValue = routeValue;
		this.weight = weight;
		this.validFlag = validFlag;
		this.routeType = routeType;
	}


	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getRouteValue() {
		return routeValue;
	}

	public void setRouteValue(String routeValue) {
		this.routeValue = routeValue;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public Long getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Long validFlag) {
		this.validFlag = validFlag;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}


	public String getSystemName() {
		return systemName;
	}


	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

}
