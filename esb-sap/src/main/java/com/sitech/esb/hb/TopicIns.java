package com.sitech.esb.hb;

import java.util.HashSet;
import java.util.Set;

/**
 * TopicIns entity provides the base persistence definition of the
 * TopicIns entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  TopicIns implements java.io.Serializable {

	// Fields

	private Long topicInsId;
//	private Topic topic;
//	private EventIns eventIns;
	private Topic topic;
	private EventIns eventIns;
	private String msgContent;
	private Long topicInsStatus;
	private Set topicInsSubscribe  = new HashSet(0);

	// Constructors

	public Set getTopicInsSubscribe() {
		return topicInsSubscribe;
	}

	public void setTopicInsSubscribe(Set topicInsSubscribe) {
		this.topicInsSubscribe = topicInsSubscribe;
	}

	/** default constructor */
	public  TopicIns() {
	}

	/** full constructor */
	public  TopicIns(Topic topic, EventIns eventIns, String msgContent,
			Long topicInsStatus) {
		this.topic = topic;
		this.eventIns = eventIns;
		this.msgContent = msgContent;
		this.topicInsStatus = topicInsStatus;
	}

	// Property accessors

	public Long getTopicInsId() {
		return this.topicInsId;
	}

	public void setTopicInsId(Long topicInsId) {
		this.topicInsId = topicInsId;
	}

	public Topic getTopic() {
		return this.topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public EventIns getEventIns() {
		return this.eventIns;
	}

	public void setEventIns(EventIns eventIns) {
		this.eventIns = eventIns;
	}

	public String getMsgContent() {
		return this.msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Long getTopicInsStatus() {
		return this.topicInsStatus;
	}

	public void setTopicInsStatus(Long topicInsStatus) {
		this.topicInsStatus = topicInsStatus;
	}

}