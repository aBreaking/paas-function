package com.sitech.esb.hb;

/**
 * TopicStatus entity provides the base persistence definition of the
 * TopicStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  TopicStatus implements java.io.Serializable {

	// Fields

	private Long topicStatus;
	private String topicStatusName;
	private String describe;

	// Constructors

	/** default constructor */
	public TopicStatus() {
	}

	/** minimal constructor */
	public TopicStatus(String topicStatusName) {
		this.topicStatusName = topicStatusName;
	}

	/** full constructor */
	public TopicStatus(String topicStatusName, String describe) {
		this.topicStatusName = topicStatusName;
		this.describe = describe;
	}

	// Property accessors

	public Long getTopicStatus() {
		return this.topicStatus;
	}

	public void setTopicStatus(Long topicStatus) {
		this.topicStatus = topicStatus;
	}

	public String getTopicStatusName() {
		return this.topicStatusName;
	}

	public void setTopicStatusName(String topicStatusName) {
		this.topicStatusName = topicStatusName;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}