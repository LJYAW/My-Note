package com.sino.fas.res.biz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RfcProtocol entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Rfc_Protocol")
public class RfcProtocol implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5024566380138566801L;
	private Integer protId;
	private String protName;
	private String aliases;
	private String comment;

	// Constructors

	/** default constructor */
	public RfcProtocol() {
	}

	/** minimal constructor */
	public RfcProtocol(Integer protId, String protName) {
		this.protId = protId;
		this.protName = protName;
	}

	/** full constructor */
	public RfcProtocol(Integer protId, String protName, String aliases,
			String comment) {
		this.protId = protId;
		this.protName = protName;
		this.aliases = aliases;
		this.comment = comment;
	}

	// Property accessors
	@Id
	@Column(name = "Prot_ID", unique = true, nullable = false)
	public Integer getProtId() {
		return this.protId;
	}

	public void setProtId(Integer protId) {
		this.protId = protId;
	}

	@Column(name = "Prot_Name", nullable = false, length = 64)
	public String getProtName() {
		return this.protName;
	}

	public void setProtName(String protName) {
		this.protName = protName;
	}

	@Column(name = "Aliases", length = 64)
	public String getAliases() {
		return this.aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	@Column(name = "Comment", length = 128)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}