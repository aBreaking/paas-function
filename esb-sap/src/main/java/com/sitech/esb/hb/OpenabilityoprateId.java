package com.sitech.esb.hb;

/**
 * OpenabilityoprateId entity. @author MyEclipse Persistence Tools
 */

public class OpenabilityoprateId implements java.io.Serializable {

	// Fields

	private String apimethod;
	private String apiversion;

	// Constructors

	/** default constructor  */
	public OpenabilityoprateId() {
	}

	/** full constructor */
	public OpenabilityoprateId(String apimethod, String apiversion) {
		this.apimethod = apimethod;
		this.apiversion = apiversion;
	}

	// Property accessors

	public String getApimethod() {
		return this.apimethod;
	}

	public void setApimethod(String apimethod) {
		this.apimethod = apimethod;
	}

	public String getApiversion() {
		return this.apiversion;
	}

	public void setApiversion(String apiversion) {
		this.apiversion = apiversion;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OpenabilityoprateId))
			return false;
		OpenabilityoprateId castOther = (OpenabilityoprateId) other;

		return ((this.getApimethod() == castOther.getApimethod()) || (this
				.getApimethod() != null && castOther.getApimethod() != null && this
				.getApimethod().equals(castOther.getApimethod())))
				&& ((this.getApiversion() == castOther.getApiversion()) || (this
						.getApiversion() != null
						&& castOther.getApiversion() != null && this
						.getApiversion().equals(castOther.getApiversion())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getApimethod() == null ? 0 : this.getApimethod().hashCode());
		result = 37
				* result
				+ (getApiversion() == null ? 0 : this.getApiversion()
						.hashCode());
		return result;
	}

}
