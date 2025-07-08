package com.sino.base.system.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

/**
 * SysPowerGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Sys_PowerGroup")
public class SysPowerGroup implements java.io.Serializable {

	// Fields

	private String powGrpId;
	private String grpName;
	private String parentId;
	private String treeCode;
	private Integer state;
	private String description;
	private String remark;
	private Integer level;
	private String isLeaf;
	private String expanded;
	private Set<SysPower> sysPowers = new HashSet<SysPower>(0);

	// Constructors

	/** default constructor */
	public SysPowerGroup() {
	}

	/** minimal constructor */
	public SysPowerGroup(String powGrpId, String grpName, String parentId,
			Integer state) {
		this.powGrpId = powGrpId;
		this.grpName = grpName;
		this.parentId = parentId;
		this.state = state;
	}

	/** full constructor */
	public SysPowerGroup(String powGrpId, String grpName, String parentId,
			String treeCode, Integer state, String description, String remark,
			Set<SysPower> sysPowers) {
		this.powGrpId = powGrpId;
		this.grpName = grpName;
		this.parentId = parentId;
		this.treeCode = treeCode;
		this.state = state;
		this.description = description;
		this.remark = remark;
		this.sysPowers = sysPowers;
	}

	// Property accessors
	@Id
	@Column(name = "PowGrpId", unique = true, nullable = false, length = 36)
	public String getPowGrpId() {
		return this.powGrpId;
	}

	public void setPowGrpId(String powGrpId) {
		this.powGrpId = powGrpId;
	}

	@Column(name = "GrpName", nullable = false, length = 64)
	public String getGrpName() {
		return this.grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	@Column(name = "ParentId", nullable = false, length = 36)
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

	@Column(name = "State", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	@OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "sysPowerGroup")
	@OrderBy(clause = "powNumber")
	public Set<SysPower> getSysPowers() {
		return this.sysPowers;
	}

	public void setSysPowers(Set<SysPower> sysPowers) {
		this.sysPowers = sysPowers;
	}
	
	@Column(name = "level", length = 0)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Column(name = "expanded")
	public String getExpanded() {
		return expanded;
	}

	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}
	
	@Column(name = "isLeaf")
	public String isLeaf() {
		return isLeaf;
	}

	public void setLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

}