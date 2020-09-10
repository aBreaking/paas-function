package com.sitech.esb.hb;

import java.util.HashSet;
import java.util.Set;

/**
 * Topic entity provides the base persistence definition of the Topic
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  Topic implements java.io.Serializable {

	// Fields

	private Long topicId;
	private String topicName;
	private String topciChname;
	private String notifyMsgName;
	private String notifyMsgTemplate;
	private Long validFlag;
	private String topicStatusTips;
	private String describle;

	private TopicStatus topicStatus ;
	private Set events = new HashSet();
	private Set clients = new HashSet();
	// Constructors

	/** default constructor */
	public  Topic() {
	}

	/** minimal constructor */
	public  Topic(String topicName, String topciChname,
			String notifyMsgName, String notifyMsgTemplate, Long validFlag,
			TopicStatus topicStatus) {
		this.topicName = topicName;
		this.topciChname = topciChname;
		this.notifyMsgName = notifyMsgName;
		this.notifyMsgTemplate = notifyMsgTemplate;
		this.validFlag = validFlag;
		this.topicStatus = topicStatus;
	}

	/** full constructor */
	public  Topic(String topicName, String topciChname,
			String notifyMsgName, String notifyMsgTemplate, Long validFlag,
			TopicStatus topicStatus, String topicStatusTips, String describle) {
		this.topicName = topicName;
		this.topciChname = topciChname;
		this.notifyMsgName = notifyMsgName;
		this.notifyMsgTemplate = notifyMsgTemplate;
		this.validFlag = validFlag;
		this.topicStatus = topicStatus;
		this.topicStatusTips = topicStatusTips;
		this.describle = describle;
	}

	// Property accessors

	public Long getTopicId() {
		return this.topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public String getTopicName() {
		return this.topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getTopciChname() {
		return this.topciChname;
	}

	public void setTopciChname(String topciChname) {
		this.topciChname = topciChname;
	}

	public String getNotifyMsgName() {
		return this.notifyMsgName;
	}

	public void setNotifyMsgName(String notifyMsgName) {
		this.notifyMsgName = notifyMsgName;
	}

	public String getNotifyMsgTemplate() {
		return this.notifyMsgTemplate;
	}

	public void setNotifyMsgTemplate(String notifyMsgTemplate) {
		this.notifyMsgTemplate = notifyMsgTemplate;
	}

	public Long getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(Long validFlag) {
		this.validFlag = validFlag;
	}

	public TopicStatus getTopicStatus() {
		return this.topicStatus;
	}

	public void setTopicStatus(TopicStatus topicStatus) {
		this.topicStatus = topicStatus;
	}

	public String getTopicStatusTips() {
		return this.topicStatusTips;
	}

	public void setTopicStatusTips(String topicStatusTips) {
		this.topicStatusTips = topicStatusTips;
	}

	public String getDescrible() {
		return this.describle;
	}

	public void setDescrible(String describle) {
		this.describle = describle;
	}

	public Set getEvents() {
		return events;
	}

	public void setEvents(Set events) {
		this.events = events;
	}

	public Set getClients() {
		return clients;
	}

	public void setClients(Set clients) {
		this.clients = clients;
	}

}