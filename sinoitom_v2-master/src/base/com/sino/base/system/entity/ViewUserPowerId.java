package com.sino.base.system.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ViewUserPowerId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class ViewUserPowerId implements java.io.Serializable {

	// Fields

	private String userId;
	private String powId;

	// Constructors

	/** default constructor */
	public ViewUserPowerId() {
	}

	/** full constructor */
	public ViewUserPowerId(String userId, String powId) {
		this.userId = userId;
		this.powId = powId;
	}

	// Property accessors

	@Column(name = "UserId", nullable = false, length = 36)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "PowId", nullable = false, length = 36)
	public String getPowId() {
		return this.powId;
	}

	public void setPowId(String powId) {
		this.powId = powId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ViewUserPowerId))
			return false;
		ViewUserPowerId castOther = (ViewUserPowerId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getPowId() == castOther.getPowId()) || (this
						.getPowId() != null && castOther.getPowId() != null && this
						.getPowId().equals(castOther.getPowId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getPowId() == null ? 0 : this.getPowId().hashCode());
		return result;
	}

}