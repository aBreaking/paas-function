package com.sitech.esb.hb;

/**
 * SrvEvent entity provides the base persistence definition of the
 * SrvEvent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SrvEvent implements java.io.Serializable {

	// Fields

	private SrvEventId id;
	private Long eventOrder;
	private Long validFlag;
	private Long needSsrvSuc;
	private Event event;
	private SrvInfo srvInfo;
	private ClientInfo clientInfo;

	// Constructors

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public SrvInfo getSrvInfo() {
		return srvInfo;
	}

	public void setSrvInfo(SrvInfo srvInfo) {
		this.srvInfo = srvInfo;
	}

	public Long getNeedSsrvSuc() {
		return needSsrvSuc;
	}

	public void setNeedSsrvSuc(Long needSsrvSuc) {
		this.needSsrvSuc = needSsrvSuc;
	}

	/** default constructor */
	public SrvEvent() {
	}

	/** full constructor */
	public SrvEvent(SrvEventId id, Long eventOrder, Long validFlag,Long needSsrvSuc,Event event,SrvInfo srvInfo) {
		this.id = id;
		this.eventOrder = eventOrder;
		this.validFlag = validFlag;
		this.needSsrvSuc = needSsrvSuc;
		this.event = event;
		this.srvInfo = srvInfo;
	}
	public SrvEvent(SrvEventId id, Long eventOrder, Long validFlag,Long needSsrvSuc) {
		this.id = id;
		this.eventOrder = eventOrder;
		this.validFlag = validFlag;
		this.needSsrvSuc = needSsrvSuc;
		this.event = event;
		this.srvInfo = srvInfo;
	}
	// Property accessors

	public SrvEventId getId() {
		return this.id;
	}

	public void setId(SrvEventId id) {
		this.id = id;
	}

	public Long getEventOrder() {
		return this.eventOrder;
	}

	public void setEventOrder(Long eventOrder) {
		this.eventOrder = eventOrder;
	}

	public Long getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(Long validFlag) {
		this.validFlag = validFlag;
	}

	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

}