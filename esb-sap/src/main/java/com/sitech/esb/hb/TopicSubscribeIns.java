package com.sitech.esb.hb;

/**
 * TopicSubscribeIns entity provides the base persistence definition of
 * the TopicSubscribeIns entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  TopicSubscribeIns implements java.io.Serializable {

	// Fields

	private TopicSubscribeInsId id;
	private Long notifyStatus;
	private String notifyTime;
	private Long alreadyRetryTimes;
	private ClientInfo client;
	private TopicIns topicIns;

	// Constructors

	public ClientInfo getClient() {
		return client;
	}

	public void setClient(ClientInfo client) {
		this.client = client;
	}

	public TopicIns getTopicIns() {
		return topicIns;
	}

	public void setTopicIns(TopicIns topicIns) {
		this.topicIns = topicIns;
	}

	/** default constructor */
	public  TopicSubscribeIns() {
	}

	/** minimal constructor */
	public  TopicSubscribeIns(TopicSubscribeInsId id, Long notifyStatus,
			Long alreadyRetryTimes) {
		this.id = id;
		this.notifyStatus = notifyStatus;
		this.alreadyRetryTimes = alreadyRetryTimes;
	}

	/** full constructor */
	public  TopicSubscribeIns(TopicSubscribeInsId id, Long notifyStatus,
			String notifyTime, Long alreadyRetryTimes) {
		this.id = id;
		this.notifyStatus = notifyStatus;
		this.notifyTime = notifyTime;
		this.alreadyRetryTimes = alreadyRetryTimes;
	}

	// Property accessors

	public TopicSubscribeInsId getId() {
		return this.id;
	}

	public void setId(TopicSubscribeInsId id) {
		this.id = id;
	}

	public Long getNotifyStatus() {
		return this.notifyStatus;
	}

	public void setNotifyStatus(Long notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public String getNotifyTime() {
		return this.notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public Long getAlreadyRetryTimes() {
		return this.alreadyRetryTimes;
	}

	public void setAlreadyRetryTimes(Long alreadyRetryTimes) {
		this.alreadyRetryTimes = alreadyRetryTimes;
	}

}