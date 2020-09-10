package com.sitech.esb.hb;

/**
 * TopicSubscribeInsId entity provides the base persistence definition
 * of the TopicSubscribeInsId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  TopicSubscribeInsId implements
		java.io.Serializable {

	// Fields

	private Long topicInsId;
	private Long clientId;

	// Constructors

	/** default constructor */
	public  TopicSubscribeInsId() {
	}

	/** full constructor */
	public  TopicSubscribeInsId(Long topicInsId, Long clientId) {
		this.topicInsId = topicInsId;
		this.clientId = clientId;
	}

	// Property accessors

	public Long getTopicInsId() {
		return this.topicInsId;
	}

	public void setTopicInsId(Long topicInsId) {
		this.topicInsId = topicInsId;
	}

	public Long getClientId() {
		return this.clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TopicSubscribeInsId))
			return false;
		TopicSubscribeInsId castOther = (TopicSubscribeInsId) other;

		return ((this.getTopicInsId() == castOther.getTopicInsId()) || (this
				.getTopicInsId() != null
				&& castOther.getTopicInsId() != null && this.getTopicInsId()
				.equals(castOther.getTopicInsId())))
				&& ((this.getClientId() == castOther.getClientId()) || (this
						.getClientId() != null
						&& castOther.getClientId() != null && this
						.getClientId().equals(castOther.getClientId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTopicInsId() == null ? 0 : this.getTopicInsId()
						.hashCode());
		result = 37 * result
				+ (getClientId() == null ? 0 : this.getClientId().hashCode());
		return result;
	}

}