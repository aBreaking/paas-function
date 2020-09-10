package com.sitech.esb.hb;

/**
 * RoleRightId entity provides the base persistence definition of the
 * RoleRightId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class  RoleRightId implements java.io.Serializable {

	// Fields

	private Long rightId;
	private Long roleId;

	// Constructors

	/** default constructor */
	public  RoleRightId() {
	}

	/** full constructor */
	public  RoleRightId(Long rightId, Long roleId) {
		this.rightId = rightId;
		this.roleId = roleId;
	}

	// Property accessors

	public Long getRightId() {
		return this.rightId;
	}

	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RoleRightId))
			return false;
		RoleRightId castOther = (RoleRightId) other;

		return ((this.getRightId() == castOther.getRightId()) || (this
				.getRightId() != null
				&& castOther.getRightId() != null && this.getRightId().equals(
				castOther.getRightId())))
				&& ((this.getRoleId() == castOther.getRoleId()) || (this
						.getRoleId() != null
						&& castOther.getRoleId() != null && this.getRoleId()
						.equals(castOther.getRoleId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRightId() == null ? 0 : this.getRightId().hashCode());
		result = 37 * result
				+ (getRoleId() == null ? 0 : this.getRoleId().hashCode());
		return result;
	}

}