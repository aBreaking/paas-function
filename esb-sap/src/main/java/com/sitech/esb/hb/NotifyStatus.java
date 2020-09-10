package com.sitech.esb.hb;

/**
 * NotifyStatus entity provides the base persistence definition of the
 * NotifyStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  NotifyStatus implements java.io.Serializable {

	// Fields

	private Long notifyStatus;
	private String notifyStatusName;
	private String describe;

	// Constructors

	/** default constructor */
	public  NotifyStatus() {
	}

	/** minimal constructor */
	public  NotifyStatus(String notifyStatusName) {
		this.notifyStatusName = notifyStatusName;
	}

	/** full constructor */
	public  NotifyStatus(String notifyStatusName, String describe) {
		this.notifyStatusName = notifyStatusName;
		this.describe = describe;
	}

	// Property accessors

	public Long getNotifyStatus() {
		return this.notifyStatus;
	}

	public void setNotifyStatus(Long notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public String getNotifyStatusName() {
		return this.notifyStatusName;
	}

	public void setNotifyStatusName(String notifyStatusName) {
		this.notifyStatusName = notifyStatusName;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}