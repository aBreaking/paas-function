package com.sitech.esb.hb;

import java.math.BigDecimal;


/**
 * Openabilityoprate entity. @author MyEclipse Persistence Tools
 */

public class Openabilityoprate implements java.io.Serializable {

	// Fields

	private OpenabilityoprateId id;
	private String abilitystatus;
	private String servicename;
	private BigDecimal syncflag;

	// Constructors

	/** default constructor */
	public Openabilityoprate() {
	}

	/** minimal constructor */
	public Openabilityoprate(OpenabilityoprateId id, BigDecimal syncflag) {
		this.id = id;
		this.syncflag = syncflag;
	}

	/** full constructor */
	public Openabilityoprate(OpenabilityoprateId id, String abilitystatus,
			String servicename, BigDecimal syncflag) {
		this.id = id;
		this.abilitystatus = abilitystatus;
		this.servicename = servicename;
		this.syncflag = syncflag;
	}

	// Property accessors

	public OpenabilityoprateId getId() {
		return this.id;
	}

	public void setId(OpenabilityoprateId id) {
		this.id = id;
	}

	public String getAbilitystatus() {
		return this.abilitystatus;
	}

	public void setAbilitystatus(String abilitystatus) {
		this.abilitystatus = abilitystatus;
	}

	public String getServicename() {
		return this.servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public BigDecimal getSyncflag() {
		return this.syncflag;
	}

	public void setSyncflag(BigDecimal syncflag) {
		this.syncflag = syncflag;
	}

}