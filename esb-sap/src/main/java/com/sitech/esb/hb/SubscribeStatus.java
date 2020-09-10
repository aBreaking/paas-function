package com.sitech.esb.hb;

/**
 * SubscribeStatus entity provides the base persistence definition of
 * the SubscribeStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SubscribeStatus implements java.io.Serializable {

	// Fields

	private Long subscribeStatus;
	private String subscribeStatusName;
	private String descrbe;

	// Constructors

	/** default constructor */
	public  SubscribeStatus() {
	}

	/** minimal constructor */
	public  SubscribeStatus(String subscribeStatusName) {
		this.subscribeStatusName = subscribeStatusName;
	}

	/** full constructor */
	public  SubscribeStatus(String subscribeStatusName, String descrbe) {
		this.subscribeStatusName = subscribeStatusName;
		this.descrbe = descrbe;
	}

	// Property accessors

	public Long getSubscribeStatus() {
		return this.subscribeStatus;
	}

	public void setSubscribeStatus(Long subscribeStatus) {
		this.subscribeStatus = subscribeStatus;
	}

	public String getSubscribeStatusName() {
		return this.subscribeStatusName;
	}

	public void setSubscribeStatusName(String subscribeStatusName) {
		this.subscribeStatusName = subscribeStatusName;
	}

	public String getDescrbe() {
		return this.descrbe;
	}

	public void setDescrbe(String descrbe) {
		this.descrbe = descrbe;
	}

}