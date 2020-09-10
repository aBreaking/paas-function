package com.sitech.esb.hb;

public class SrvCallLimit {
	private Long id;
	private SrvInfo srv;
	private Long flowThreshold;
	private Long effectivePeriod;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SrvInfo getSrv() {
		return srv;
	}
	public void setSrv(SrvInfo srv) {
		this.srv = srv;
	}
	public Long getFlowThreshold() {
		return flowThreshold;
	}
	public void setFlowThreshold(Long flowThreshold) {
		this.flowThreshold = flowThreshold;
	}
	public Long getEffectivePeriod() {
		return effectivePeriod;
	}
	public void setEffectivePeriod(Long effectivePeriod) {
		this.effectivePeriod = effectivePeriod;
	}
	public SrvCallLimit(SrvInfo srv, Long flowThreshold, Long effectivePeriod) {
		super();
		this.srv = srv;
		this.flowThreshold = flowThreshold;
		this.effectivePeriod = effectivePeriod;
	}
	
	
}
