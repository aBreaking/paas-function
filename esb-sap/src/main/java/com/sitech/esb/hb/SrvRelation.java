package com.sitech.esb.hb;

/**
 * SrvRelation entity provides the base persistence definition of the
 * SrvRelation entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SrvRelation implements java.io.Serializable {

	// Fields

	private Long srvRelId;
	private SrvInfo parentSrv;
	private SrvInfo subSrv;
	private Long subsrvOrder;

	// Constructors

	/** default constructor */
	public  SrvRelation() {
	}

	/** full constructor */
	public  SrvRelation(SrvInfo parentSrv, SrvInfo subSrv, Long subsrvOrder) {
		this.parentSrv = parentSrv;
		this.subSrv = subSrv;
		this.subsrvOrder = subsrvOrder;
	}

	// Property accessors

	public Long getSrvRelId() {
		return this.srvRelId;
	}

	public void setSrvRelId(Long srvRelId) {
		this.srvRelId = srvRelId;
	}

	public Long getSubsrvOrder() {
		return this.subsrvOrder;
	}

	public void setSubsrvOrder(Long subsrvOrder) {
		this.subsrvOrder = subsrvOrder;
	}

	public SrvInfo getParentSrv() {
		return parentSrv;
	}

	public void setParentSrv(SrvInfo parentSrv) {
		this.parentSrv = parentSrv;
	}

	public SrvInfo getSubSrv() {
		return subSrv;
	}

	public void setSubSrv(SrvInfo subSrv) {
		this.subSrv = subSrv;
	}

}