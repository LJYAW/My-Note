package com.sino.base.system.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ViewUserMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_UserMenu")
public class ViewUserMenu implements java.io.Serializable {

	// Fields

	private ViewUserMenuId id;

	// Constructors

	/** default constructor */
	public ViewUserMenu() {
	}

	/** full constructor */
	public ViewUserMenu(ViewUserMenuId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "UserId", nullable = false, length = 36)),
			@AttributeOverride(name = "menuId", column = @Column(name = "menuId", nullable = false, length = 36)) })
	public ViewUserMenuId getId() {
		return this.id;
	}

	public void setId(ViewUserMenuId id) {
		this.id = id;
	}

}