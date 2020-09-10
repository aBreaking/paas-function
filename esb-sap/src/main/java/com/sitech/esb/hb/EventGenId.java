package com.sitech.esb.hb;

/**
 * EventGenId entity provides the base persistence definition of the
 * EventGenId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  EventGenId implements java.io.Serializable {

	// Fields

	private Long eventConsumerId;
	private Long eventId;

	// Constructors

	/** default constructor */
	public EventGenId() {
	}

	/** full constructor */
	public EventGenId(Long eventConsumerId, Long eventId) {
		this.eventConsumerId = eventConsumerId;
		this.eventId = eventId;
	}

	// Property accessors

	public Long getEventConsumerId() {
		return this.eventConsumerId;
	}

	public void setEventConsumerId(Long eventConsumerId) {
		this.eventConsumerId = eventConsumerId;
	}

	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EventGenId))
			return false;
		EventGenId castOther = (EventGenId) other;

		return ((this.getEventConsumerId() == castOther.getEventConsumerId()) || (this
				.getEventConsumerId() != null
				&& castOther.getEventConsumerId() != null && this
				.getEventConsumerId().equals(castOther.getEventConsumerId())))
				&& ((this.getEventId() == castOther.getEventId()) || (this
						.getEventId() != null
						&& castOther.getEventId() != null && this.getEventId()
						.equals(castOther.getEventId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getEventConsumerId() == null ? 0 : this.getEventConsumerId()
						.hashCode());
		result = 37 * result
				+ (getEventId() == null ? 0 : this.getEventId().hashCode());
		return result;
	}

}