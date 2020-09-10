package com.sitech.esb.hb;

/**
 * EventTopicId entity provides the base persistence definition of the
 * EventTopicId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  EventTopicId implements java.io.Serializable {

	// Fields

	private Long eventId;
	private Long topicId;

	// Constructors

	/** default constructor */
	public  EventTopicId() {
	}

	/** full constructor */
	public  EventTopicId(Long eventId, Long topicId) {
		this.eventId = eventId;
		this.topicId = topicId;
	}

	// Property accessors

	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
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
		if (!(other instanceof EventTopicId))
			return false;
		EventTopicId castOther = (EventTopicId) other;

		return ((this.getEventId() == castOther.getEventId()) || (this
				.getEventId() != null
				&& castOther.getEventId() != null && this.getEventId().equals(
				castOther.getEventId())))
				&& ((this.getTopicId() == castOther.getTopicId()) || (this
						.getTopicId() != null
						&& castOther.getTopicId() != null && this.getTopicId()
						.equals(castOther.getTopicId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEventId() == null ? 0 : this.getEventId().hashCode());
		result = 37 * result
				+ (getTopicId() == null ? 0 : this.getTopicId().hashCode());
		return result;
	}

}