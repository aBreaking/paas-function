package com.sitech.esb.hb;

/**
 * SrvCategRelId entity provides the base persistence definition of the
 * SrvCategRelId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  SrvCategRelId implements java.io.Serializable {

	// Fields

	private Long srvCategId;
	private Long srvId;

	// Property accessors

	public Long getSrvCategId() {
		return this.srvCategId;
	}

	public void setSrvCategId(Long srvCategId) {
		this.srvCategId = srvCategId;
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
		if (!(other instanceof SrvCategRelId))
			return false;
		SrvCategRelId castOther = (SrvCategRelId) other;

		return ((this.getSrvCategId() == castOther.getSrvCategId()) || (this
				.getSrvCategId() != null
				&& castOther.getSrvCategId() != null && this.getSrvCategId()
				.equals(castOther.getSrvCategId())))
				&& ((this.getSrvId() == castOther.getSrvId()) || (this
						.getSrvId() != null
						&& castOther.getSrvId() != null && this.getSrvId()
						.equals(castOther.getSrvId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getSrvCategId() == null ? 0 : this.getSrvCategId()
						.hashCode());
		result = 37 * result
				+ (getSrvId() == null ? 0 : this.getSrvId().hashCode());
		return result;
	}

}