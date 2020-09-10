package com.sitech.esb.hb;

/**
 * SrvEventId entity provides the base persistence definition of the
 * SrvEventId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SrvEventId implements java.io.Serializable {

	// Fields

	private Long eventId;
	private Long srvId;
	private Long clientId;

	// Constructors

	/** default constructor */
	public SrvEventId() {
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	/** full constructor */
	public SrvEventId(Long eventId, Long srvId) {
		this.eventId = eventId;
		this.srvId = srvId;
	}

	// Property accessors

	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getSrvId() {
		return this.srvId;
	}

	public void setSrvId(Long srvId) {
		this.srvId = srvId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SrvEventId))
			return false;
		SrvEventId castOther = (SrvEventId) other;

		return ((this.getEventId() == castOther.getEventId()) || (this
				.getEventId() != null
				&& castOther.getEventId() != null && this.getEventId().equals(
				castOther.getEventId())))
				&& ((this.getSrvId() == castOther.getSrvId()) || (this
						.getSrvId() != null
						&& castOther.getSrvId() != null && this.getSrvId()
						.equals(castOther.getSrvId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEventId() == null ? 0 : this.getEventId().hashCode());
		result = 37 * result
				+ (getSrvId() == null ? 0 : this.getSrvId().hashCode());
		return result;
	}

}