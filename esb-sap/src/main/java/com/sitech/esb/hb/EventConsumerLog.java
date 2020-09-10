package com.sitech.esb.hb;

/**
 * EventConsumerLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EventConsumerLog implements java.io.Serializable {

	// Fields

	private Long eventConsumerLogId;
	private String serverIp;
	private Integer serverPort;
	private String eventProducer;
	private String eventProducerEpr;
	private String topicName;
	private String notifyMsgName;
	private java.util.Date eventGenTime;
	private String msgContent;
	private String processResult;
	private String errorMsg;

	// Constructors

	/** default constructor */
	public EventConsumerLog() {
	}

	/** mini constructor */
	public EventConsumerLog(Long eventConsumerLogId, String serverIp, Integer serverPort, String eventProducer,
			String eventProducerEpr, String topicName, String notifyMsgName, java.util.Date eventGenTime,
			String msgContent, String processResult) {
		this.eventConsumerLogId = eventConsumerLogId;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.eventProducer = eventProducer;
		this.eventProducerEpr = eventProducerEpr;
		this.topicName = topicName;
		this.notifyMsgName = notifyMsgName;
		this.eventGenTime = eventGenTime;
		this.msgContent = msgContent;
		this.processResult = processResult;
	}

	/** full constructor */
	public EventConsumerLog(Long eventConsumerLogId, String serverIp, Integer serverPort, String eventProducer,
			String eventProducerEpr, String topicName, String notifyMsgName, java.util.Date eventGenTime,
			String msgContent, String processResult, String errorMsg) {
		this.eventConsumerLogId = eventConsumerLogId;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.eventProducer = eventProducer;
		this.eventProducerEpr = eventProducerEpr;
		this.topicName = topicName;
		this.notifyMsgName = notifyMsgName;
		this.eventGenTime = eventGenTime;
		this.msgContent = msgContent;
		this.processResult = processResult;
		this.errorMsg = errorMsg;
	}

	// Property accessors

	public Long getEventConsumerLogId() {
		return this.eventConsumerLogId;
	}

	public void setEventConsumerLogId(Long eventConsumerLogId) {
		this.eventConsumerLogId = eventConsumerLogId;
	}

	public String getEventProducer() {
		return this.eventProducer;
	}

	public void setEventProducer(String eventProducer) {
		this.eventProducer = eventProducer;
	}

	public String getEventProducerEpr() {
		return this.eventProducerEpr;
	}

	public void setEventProducerEpr(String eventProducerEpr) {
		this.eventProducerEpr = eventProducerEpr;
	}

	public String getTopicName() {
		return this.topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getNotifyMsgName() {
		return this.notifyMsgName;
	}

	public void setNotifyMsgName(String notifyMsgName) {
		this.notifyMsgName = notifyMsgName;
	}

	public java.util.Date getEventGenTime() {
		return this.eventGenTime;
	}

	public void setEventGenTime(java.util.Date eventGenTime) {
		this.eventGenTime = eventGenTime;
	}

	public String getMsgContent() {
		return this.msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getProcessResult() {
		return processResult;
	}

	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}

}