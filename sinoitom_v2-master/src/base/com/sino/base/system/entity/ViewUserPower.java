package com.sino.base.system.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ViewUserPower entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "View_UserPower")
public class ViewUserPower implements java.io.Serializable {

	// Fields

	private ViewUserPowerId id;

	// Constructors

	/** default constructor */
	public ViewUserPower() {
	}

	/** full constructor */
	public ViewUserPower(ViewUserPowerId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "UserId", nullable = false, length = 36)),
			@AttributeOverride(name = "powId", column = @Column(name = "PowId", nullable = false, length = 36)) })
	public ViewUserPowerId getId() {
		return this.id;
	}

	public void setId(ViewUserPowerId id) {
		this.id = id;
	}

}