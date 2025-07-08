package com.sino.base.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SysPowerUrl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Sys_PowerUrl")
public class SysPowerUrl implements java.io.Serializable {

	// Fields

	private String powUrlId;
	private SysPower sysPower;
	private String urlContent;
	private String remark;

	// Constructors

	/** default constructor */
	public SysPowerUrl() {
	}

	/** minimal constructor */
	public SysPowerUrl(String powUrlId, SysPower sysPower, String urlContent) {
		this.powUrlId = powUrlId;
		this.sysPower = sysPower;
		this.urlContent = urlContent;
	}

	/** full constructor */
	public SysPowerUrl(String powUrlId, SysPower sysPower, String urlContent,
			String remark) {
		this.powUrlId = powUrlId;
		this.sysPower = sysPower;
		this.urlContent = urlContent;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "PowUrlId", unique = true, nullable = false, length = 36)
	public String getPowUrlId() {
		return this.powUrlId;
	}

	public void setPowUrlId(String powUrlId) {
		this.powUrlId = powUrlId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PowId", nullable = false)
	public SysPower getSysPower() {
		return this.sysPower;
	}

	public void setSysPower(SysPower sysPower) {
		this.sysPower = sysPower;
	}

	@Column(name = "UrlContent", nullable = false, length = 256)
	public String getUrlContent() {
		return this.urlContent;
	}

	public void setUrlContent(String urlContent) {
		this.urlContent = urlContent;
	}

	@Column(name = "Remark", length = 128)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}