package com.sitech.esb.hb;

/**
 * EventSelfProcessStatus generated by MyEclipse Persistence Tools
 */

public class  EventSelfProcessStatus implements
		java.io.Serializable {

	// Fields

	private Long selfProcessStatus;
	private String selfProcessStatusname;

	// Constructors

	/** default constructor */
	public  EventSelfProcessStatus() {
	}

	/** full constructor */
	public  EventSelfProcessStatus(Long selfProcessStatus,
			String selfProcessStatusname) {
		this.selfProcessStatus = selfProcessStatus;
		this.selfProcessStatusname = selfProcessStatusname;
	}

	// Property accessors

	public Long getSelfProcessStatus() {
		return this.selfProcessStatus;
	}

	public void setSelfProcessStatus(Long selfProcessStatus) {
		this.selfProcessStatus = selfProcessStatus;
	}

	public String getSelfProcessStatusname() {
		return this.selfProcessStatusname;
	}

	public void setSelfProcessStatusname(String selfProcessStatusname) {
		this.selfProcessStatusname = selfProcessStatusname;
	}

}