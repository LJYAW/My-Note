package com.sino.base.system.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ViewUserMenuId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class ViewUserMenuId implements java.io.Serializable {

	// Fields

	private String userId;
	private String menuId;

	// Constructors

	/** default constructor */
	public ViewUserMenuId() {
	}

	/** full constructor */
	public ViewUserMenuId(String userId, String menuId) {
		this.userId = userId;
		this.menuId = menuId;
	}

	// Property accessors

	@Column(name = "UserId", nullable = false, length = 36)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "menuId", nullable = false, length = 36)
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ViewUserMenuId))
			return false;
		ViewUserMenuId castOther = (ViewUserMenuId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getMenuId() == castOther.getMenuId()) || (this
						.getMenuId() != null && castOther.getMenuId() != null && this
						.getMenuId().equals(castOther.getMenuId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getMenuId() == null ? 0 : this.getMenuId().hashCode());
		return result;
	}

}