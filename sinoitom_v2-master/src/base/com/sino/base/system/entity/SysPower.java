package com.sino.base.system.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * SysPower entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Sys_Power", uniqueConstraints = @UniqueConstraint(columnNames = "PowCode"))
public class SysPower implements java.io.Serializable {

	// Fields

	private String powId;
	private SysPowerGroup sysPowerGroup;
	private String powCode;
	private String powName;
	private Integer powNumber;
	private Boolean isBsPow;
	private Boolean isDvPow;
	private Boolean isRoleSee;
	private Integer state;
	private String description;
	private String remark;
	private Set<SysMenu> sysMenus = new HashSet<SysMenu>(0);
	private Set<SysPowerUrl> sysPowerUrls = new HashSet<SysPowerUrl>(0);
	private Set<SysRole> sysRoles = new HashSet<SysRole>(0);
	private Set<SysUser> sysUsers = new HashSet<SysUser>(0);

	// Constructors

	/** default constructor */
	public SysPower() {
	}

	/** minimal constructor */
	public SysPower(String powId, SysPowerGroup sysPowerGroup, String powCode,
			String powName, Integer powNumber, Boolean isBsPow,
			Boolean isDvPow, Boolean isRoleSee, Integer state) {
		this.powId = powId;
		this.sysPowerGroup = sysPowerGroup;
		this.powCode = powCode;
		this.powName = powName;
		this.powNumber = powNumber;
		this.isBsPow = isBsPow;
		this.isDvPow = isDvPow;
		this.isRoleSee = isRoleSee;
		this.state = state;
	}

	/** full constructor */
	public SysPower(String powId, SysPowerGroup sysPowerGroup, String powCode,
			String powName, Integer powNumber, Boolean isBsPow,
			Boolean isDvPow, Boolean isRoleSee, Integer state,
			String description, String remark, Set<SysMenu> sysMenus,
			Set<SysPowerUrl> sysPowerUrls, Set<SysRole> sysRoles,
			Set<SysUser> sysUsers) {
		this.powId = powId;
		this.sysPowerGroup = sysPowerGroup;
		this.powCode = powCode;
		this.powName = powName;
		this.powNumber = powNumber;
		this.isBsPow = isBsPow;
		this.isDvPow = isDvPow;
		this.isRoleSee = isRoleSee;
		this.state = state;
		this.description = description;
		this.remark = remark;
		this.sysMenus = sysMenus;
		this.sysPowerUrls = sysPowerUrls;
		this.sysRoles = sysRoles;
		this.sysUsers = sysUsers;
	}

	// Property accessors
	@Id
	@Column(name = "PowId", unique = true, nullable = false, length = 36)
	public String getPowId() {
		return this.powId;
	}

	public void setPowId(String powId) {
		this.powId = powId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PowGrpId", nullable = false)
	public SysPowerGroup getSysPowerGroup() {
		return this.sysPowerGroup;
	}

	public void setSysPowerGroup(SysPowerGroup sysPowerGroup) {
		this.sysPowerGroup = sysPowerGroup;
	}

	@Column(name = "PowCode", unique = true, nullable = false, length = 32)
	public String getPowCode() {
		return this.powCode;
	}

	public void setPowCode(String powCode) {
		this.powCode = powCode;
	}

	@Column(name = "PowName", nullable = false, length = 64)
	public String getPowName() {
		return this.powName;
	}

	public void setPowName(String powName) {
		this.powName = powName;
	}

	@Column(name = "PowNumber", nullable = false)
	public Integer getPowNumber() {
		return this.powNumber;
	}

	public void setPowNumber(Integer powNumber) {
		this.powNumber = powNumber;
	}

	@Column(name = "IsBsPow", nullable = false)
	public Boolean getIsBsPow() {
		return this.isBsPow;
	}

	public void setIsBsPow(Boolean isBsPow) {
		this.isBsPow = isBsPow;
	}

	@Column(name = "IsDvPow", nullable = false)
	public Boolean getIsDvPow() {
		return this.isDvPow;
	}

	public void setIsDvPow(Boolean isDvPow) {
		this.isDvPow = isDvPow;
	}

	@Column(name = "IsRoleSee", nullable = false)
	public Boolean getIsRoleSee() {
		return this.isRoleSee;
	}

	public void setIsRoleSee(Boolean isRoleSee) {
		this.isRoleSee = isRoleSee;
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

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinTable(name = "Sys_MenuMapPow", joinColumns = { @JoinColumn(name = "PowId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "MenuId", nullable = false, updatable = false) })
	public Set<SysMenu> getSysMenus() {
		return this.sysMenus;
	}

	public void setSysMenus(Set<SysMenu> sysMenus) {
		this.sysMenus = sysMenus;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysPower")
	public Set<SysPowerUrl> getSysPowerUrls() {
		return this.sysPowerUrls;
	}

	public void setSysPowerUrls(Set<SysPowerUrl> sysPowerUrls) {
		this.sysPowerUrls = sysPowerUrls;
	}

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "sysPowers")
	public Set<SysRole> getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "sysPowers")
	public Set<SysUser> getSysUsers() {
		return this.sysUsers;
	}

	public void setSysUsers(Set<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

}