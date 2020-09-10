package com.sitech.esb.hb;

import java.util.HashSet;
import java.util.Set;

/**
 * SoaphttpSystem entity provides the base persistence definition of the
 * SoaphttpSystem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SoaphttpSystem implements java.io.Serializable {

	// Fields
	private Long soaphttpSystemId;
	private String systemName;
	private String systemEnName;
	private String epr1;
	private String epr2;
	private String emer1;
	private Long detectTimeout;
	private Long startupDelay;
	private String describle;
	private String eprs;
	private Long isLoadBalance;
	
	private Set srvInfos = new HashSet();
	private Set srvInfoHttps = new HashSet();
	
	// Constructors

	/** default constructor */
	public  SoaphttpSystem() {
	}

	/** minimal constructor */
	public  SoaphttpSystem(String systemName, String epr1,
			Long detectTimeout, Long startupDelay) {
		this.systemName = systemName;
		this.epr1 = epr1;
		this.detectTimeout = detectTimeout;
		this.startupDelay = startupDelay;
	}

	/** full constructor */
	public  SoaphttpSystem(String systemName, String epr1, String epr2,
			Long detectTimeout, Long startupDelay, String describle) {
		this.systemName = systemName;
		this.epr1 = epr1;
		this.epr2 = epr2;
		this.detectTimeout = detectTimeout;
		this.startupDelay = startupDelay;
		this.describle = describle;
	}

	// Property accessors

	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getEpr1() {
		return this.epr1;
	}

	public void setEpr1(String epr1) {
		this.epr1 = epr1;
	}

	public String getEpr2() {
		return this.epr2;
	}

	public void setEpr2(String epr2) {
		this.epr2 = epr2;
	}

	public Long getDetectTimeout() {
		return this.detectTimeout;
	}

	public void setDetectTimeout(Long detectTimeout) {
		this.detectTimeout = detectTimeout;
	}

	public Long getStartupDelay() {
		return this.startupDelay;
	}

	public void setStartupDelay(Long startupDelay) {
		this.startupDelay = startupDelay;
	}

	public String getDescrible() {
		return this.describle;
	}

	public void setDescrible(String describle) {
		this.describle = describle;
	}

	public Long getSoaphttpSystemId() {
		return soaphttpSystemId;
	}

	public void setSoaphttpSystemId(Long soaphttpSystemId) {
		this.soaphttpSystemId = soaphttpSystemId;
	}

	public Set getSrvInfos() {
		return srvInfos;
	}

	public void setSrvInfos(Set srvInfos) {
		this.srvInfos = srvInfos;
	}

	public String getEprs() {
		return eprs;
	}

	public void setEprs(String eprs) {
		this.eprs = eprs;
	}

	public Long getIsLoadBalance() {
		return isLoadBalance;
	}

	public void setIsLoadBalance(Long isLoadBalance) {
		this.isLoadBalance = isLoadBalance;
	}

	public Set getSrvInfoHttps() {
		return srvInfoHttps;
	}

	public void setSrvInfoHttps(Set srvInfoHttps) {
		this.srvInfoHttps = srvInfoHttps;
	}

	public String getEmer1() {
		return emer1;
	}

	public void setEmer1(String emer1) {
		this.emer1 = emer1;
	}

	public String getSystemEnName() {
		return systemEnName;
	}

	public void setSystemEnName(String systemEnName) {
		this.systemEnName = systemEnName;
	}
}