package com.sitech.esb.hb;

/**
 * SrvTimeoutCfg entity provides the base persistence definition of the
 * SrvTimeoutCfg entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SrvTimeoutCfg implements java.io.Serializable {

	// Fields

	private Long cfgId;
	private Long alertTime;
	private Long averageTime;
	private Long timeDuration;
	private Long recDuration;

	// Constructors

	/** default constructor */
	public SrvTimeoutCfg() {
	}

	/** full constructor */
	public SrvTimeoutCfg(Long alertTime, Long averageTime,
			Long timeDuration, Long recDuration) {
		this.alertTime = alertTime;
		this.averageTime = averageTime;
		this.timeDuration = timeDuration;
		this.recDuration = recDuration;
	}

	// Property accessors

	public Long getCfgId() {
		return this.cfgId;
	}

	public void setCfgId(Long cfgId) {
		this.cfgId = cfgId;
	}

	public Long getAlertTime() {
		return this.alertTime;
	}

	public void setAlertTime(Long alertTime) {
		this.alertTime = alertTime;
	}

	public Long getAverageTime() {
		return this.averageTime;
	}

	public void setAverageTime(Long averageTime) {
		this.averageTime = averageTime;
	}

	public Long getTimeDuration() {
		return this.timeDuration;
	}

	public void setTimeDuration(Long timeDuration) {
		this.timeDuration = timeDuration;
	}

	public Long getRecDuration() {
		return this.recDuration;
	}

	public void setRecDuration(Long recDuration) {
		this.recDuration = recDuration;
	}

}