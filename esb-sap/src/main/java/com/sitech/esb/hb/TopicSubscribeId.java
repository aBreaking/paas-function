package com.sitech.esb.hb;

/**
 * TopicSubscribeId entity provides the base persistence definition of
 * the TopicSubscribeId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  TopicSubscribeId implements java.io.Serializable {

	// Fields

	private Long clientId;
	private Long topicId;

	// Constructors

	/** default constructor */
	public  TopicSubscribeId() {
	}

	/** full constructor */
	public  TopicSubscribeId(Long clientId, Long topicId) {
		this.clientId = clientId;
		this.topicId = topicId;
	}

	// Property accessors

	public Long getClientId() {
		return this.clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getTopicId() {
		return this.topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TopicSubscribeId))
			return false;
		TopicSubscribeId castOther = (TopicSubscribeId) other;

		return ((this.getClientId() == castOther.getClientId()) || (this
				.getClientId() != null
				&& castOther.getClientId() != null && this.getClientId()
				.equals(castOther.getClientId())))
				&& ((this.getTopicId() == castOther.getTopicId()) || (this
						.getTopicId() != null
						&& castOther.getTopicId() != null && this.getTopicId()
						.equals(castOther.getTopicId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getClientId() == null ? 0 : this.getClientId().hashCode());
		result = 37 * result
				+ (getTopicId() == null ? 0 : this.getTopicId().hashCode());
		return result;
	}

}