package com.sitech.esb.hb;

import java.sql.Clob;
import java.util.HashSet;
import java.util.Set;

/**
 * EventIns entity provides the base persistence definition of the
 * EventIns entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  EventIns implements java.io.Serializable {

	// Fields

	private Long eventInsId;
	//private Long eventId;
	private Event event;
	private String createTime;
	private String sourceLogId;
	private Clob selfProcessInparam;
	private Long sourceClass;
	private String selfProcessResult;
	private Long selfProcessStatus;
	private Set topicIns = new HashSet(0);

	// Constructors

	/** default constructor */
	public  EventIns() {
	}

	/** minimal constructor */
	public  EventIns(Event event, String createTime,
			String sourceLogId, Long sourceClass) {
		this.event = event;
		this.createTime = createTime;
		this.sourceLogId = sourceLogId;
		this.sourceClass = sourceClass;
	}

	/** full constructor */
	public  EventIns(Event event, String createTime,
			String sourceLogId, Clob selfProcessInparam, Long sourceClass,Long selfProcessStatus,String selfProcessResult) {
		this.event = event;
		this.createTime = createTime;
		this.sourceLogId = sourceLogId;
		this.selfProcessInparam = selfProcessInparam;
		this.sourceClass = sourceClass;
		this.selfProcessStatus = selfProcessStatus;
		this.selfProcessResult = selfProcessResult;
	}

	// Property accessors

	public Long getEventInsId() {
		return this.eventInsId;
	}

	public void setEventInsId(Long eventInsId) {
		this.eventInsId = eventInsId;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSourceLogId() {
		return this.sourceLogId;
	}

	public void setSourceLogId(String sourceLogId) {
		this.sourceLogId = sourceLogId;
	}

	public Clob getSelfProcessInparam() {
		return this.selfProcessInparam;
	}


	public Long getSourceClass() {
		return this.sourceClass;
	}

	public void setSourceClass(Long sourceClass) {
		this.sourceClass = sourceClass;
	}

	public String getSelfProcessResult() {
		return selfProcessResult;
	}

	public void setSelfProcessResult(String selfProcessResult) {
		this.selfProcessResult = selfProcessResult;
	}

	public Long getSelfProcessStatus() {
		return selfProcessStatus;
	}

	public void setSelfProcessStatus(Long selfProcessStatus) {
		this.selfProcessStatus = selfProcessStatus;
	}

	public void setSelfProcessInparam(Clob selfProcessInparam) {
		this.selfProcessInparam = selfProcessInparam;
	}

	public Set getTopicIns() {
		return topicIns;
	}

	public void setTopicIns(Set topicIns) {
		this.topicIns = topicIns;
	}

}