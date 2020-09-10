package com.sitech.esb.hb;

import java.util.Date;

/**
 * TopicSubscribe entity provides the base persistence definition of the
 * TopicSubscribe entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  TopicSubscribe implements java.io.Serializable {

	// Fields

	private TopicSubscribeId id;
	private Date subscribeBeginTime;
	private Date subscribeEndTime;
	private String notifyAddr;
	private Long notifyTimeout;
	private Long retryPeriod;
	private SubscribeStatus subscribeStatus;
	private String subscribeTips;
	private String describle;
	private Long retryMaxTimes;

	// Constructors

	/** default constructor */
	public  TopicSubscribe() {
	}

	/** minimal constructor */
	public  TopicSubscribe(TopicSubscribeId id,
			Date subscribeBeginTime, Date subscribeEndTime,
			String notifyAddr, Long notifyTimeout, Long retryPeriod,
			SubscribeStatus subscribeStatus, Long retryMaxTimes) {
		this.id = id;
		this.subscribeBeginTime = subscribeBeginTime;
		this.subscribeEndTime = subscribeEndTime;
		this.notifyAddr = notifyAddr;
		this.notifyTimeout = notifyTimeout;
		this.retryPeriod = retryPeriod;
		this.subscribeStatus = subscribeStatus;
		this.retryMaxTimes = retryMaxTimes;
	}

	/** full constructor */
	public  TopicSubscribe(TopicSubscribeId id,
			Date subscribeBeginTime, Date subscribeEndTime,
			String notifyAddr, Long notifyTimeout, Long retryPeriod,
			SubscribeStatus subscribeStatus, String subscribeTips, String describle,
			Long retryMaxTimes) {
		this.id = id;
		this.subscribeBeginTime = subscribeBeginTime;
		this.subscribeEndTime = subscribeEndTime;
		this.notifyAddr = notifyAddr;
		this.notifyTimeout = notifyTimeout;
		this.retryPeriod = retryPeriod;
		this.subscribeStatus = subscribeStatus;
		this.subscribeTips = subscribeTips;
		this.describle = describle;
		this.retryMaxTimes = retryMaxTimes;
	}

	// Property accessors

	public TopicSubscribeId getId() {
		return this.id;
	}

	public void setId(TopicSubscribeId id) {
		this.id = id;
	}

	public Date getSubscribeBeginTime() {
		return this.subscribeBeginTime;
	}

	public void setSubscribeBeginTime(Date subscribeBeginTime) {
		this.subscribeBeginTime = subscribeBeginTime;
	}

	public Date getSubscribeEndTime() {
		return this.subscribeEndTime;
	}

	public void setSubscribeEndTime(Date subscribeEndTime) {
		this.subscribeEndTime = subscribeEndTime;
	}

	public String getNotifyAddr() {
		return this.notifyAddr;
	}

	public void setNotifyAddr(String notifyAddr) {
		this.notifyAddr = notifyAddr;
	}

	public Long getNotifyTimeout() {
		return this.notifyTimeout;
	}

	public void setNotifyTimeout(Long notifyTimeout) {
		this.notifyTimeout = notifyTimeout;
	}

	public Long getRetryPeriod() {
		return this.retryPeriod;
	}

	public void setRetryPeriod(Long retryPeriod) {
		this.retryPeriod = retryPeriod;
	}

	public SubscribeStatus getSubscribeStatus() {
		return this.subscribeStatus;
	}

	public void setSubscribeStatus(SubscribeStatus subscribeStatus) {
		this.subscribeStatus = subscribeStatus;
	}

	public String getSubscribeTips() {
		return this.subscribeTips;
	}

	public void setSubscribeTips(String subscribeTips) {
		this.subscribeTips = subscribeTips;
	}

	public String getDescrible() {
		return this.describle;
	}

	public void setDescrible(String describle) {
		this.describle = describle;
	}

	public Long getRetryMaxTimes() {
		return this.retryMaxTimes;
	}

	public void setRetryMaxTimes(Long retryMaxTimes) {
		this.retryMaxTimes = retryMaxTimes;
	}

}