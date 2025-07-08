package com.sino.base.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * SysResItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Sys_ResItem", uniqueConstraints = @UniqueConstraint(columnNames = {
		"ResGrpId", "ResCode" }))
public class SysResItem implements java.io.Serializable {

	// Fields

	private String resId;
	private SysResGroup sysResGroup;
	private String resCode;
	private String resName;
	private Integer resNumber;
	private Integer state;
	private String parentId;
	private String treeCode;
	private String description;
	private String remark;

	// Constructors

	/** default constructor */
	public SysResItem() {
		state = 1;
	}

	/** minimal constructor */
	public SysResItem(String resId, SysResGroup sysResGroup, String resCode,
			String resName, Integer resNumber, Integer state) {
		this.resId = resId;
		this.sysResGroup = sysResGroup;
		this.resCode = resCode;
		this.resName = resName;
		this.resNumber = resNumber;
		this.state = state;
	}

	/** full constructor */
	public SysResItem(String resId, SysResGroup sysResGroup, String resCode,
			String resName, Integer resNumber, Integer state, String parentId,
			String treeCode, String description, String remark) {
		this.resId = resId;
		this.sysResGroup = sysResGroup;
		this.resCode = resCode;
		this.resName = resName;
		this.resNumber = resNumber;
		this.state = state;
		this.parentId = parentId;
		this.treeCode = treeCode;
		this.description = description;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "ResId", unique = true, nullable = false, length = 36)
	public String getResId() {
		return this.resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ResGrpId", nullable = false)
	public SysResGroup getSysResGroup() {
		return this.sysResGroup;
	}

	public void setSysResGroup(SysResGroup sysResGroup) {
		this.sysResGroup = sysResGroup;
	}

	@Column(name = "ResCode", nullable = false, length = 64)
	public String getResCode() {
		return this.resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	@Column(name = "ResName", nullable = false, length = 64)
	public String getResName() {
		return this.resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	@Column(name = "ResNumber")
	public Integer getResNumber() {
		return this.resNumber;
	}

	public void setResNumber(Integer resNumber) {
		this.resNumber = resNumber;
	}

	@Column(name = "State", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "ParentId", length = 36)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "TreeCode", length = 36)
	public String getTreeCode() {
		return this.treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	@Column(name = "Description", length = 128)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "Remark", length = 128)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}