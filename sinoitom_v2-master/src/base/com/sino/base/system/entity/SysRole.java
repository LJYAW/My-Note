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
import javax.persistence.Table;

/**
 * SysRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Sys_Role")
public class SysRole implements java.io.Serializable {

	// Fields

	private String roleId;
	private String roleName;
	private String roleCode;
	private String description;
	private Integer state;
	private String remark;
	private Set<SysPower> sysPowers = new HashSet<SysPower>(0);
	private Set<SysUser> sysUsers = new HashSet<SysUser>(0);

	// Constructors

	/** default constructor */
	public SysRole() {
	}

	/** minimal constructor */
	public SysRole(String roleId, String roleName, Integer state) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.state = state;
	}

	/** full constructor */
	public SysRole(String roleId, String roleName, String roleCode,
			String description, Integer state, String remark,
			Set<SysPower> sysPowers, Set<SysUser> sysUsers) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleCode = roleCode;
		this.description = description;
		this.state = state;
		this.remark = remark;
		this.sysPowers = sysPowers;
		this.sysUsers = sysUsers;
	}

	// Property accessors
	@Id
	@Column(name = "RoleId", unique = true, nullable = false, length = 36)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "RoleName", nullable = false, length = 32)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "RoleCode", length = 32)
	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Column(name = "Description", length = 128)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "State", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "Remark", length = 128)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinTable(name = "Sys_RoleMapPow", joinColumns = { @JoinColumn(name = "RoleId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "PowId", nullable = false, updatable = false) })
	public Set<SysPower> getSysPowers() {
		return this.sysPowers;
	}

	public void setSysPowers(Set<SysPower> sysPowers) {
		this.sysPowers = sysPowers;
	}

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "sysRoles")
	public Set<SysUser> getSysUsers() {
		return this.sysUsers;
	}

	public void setSysUsers(Set<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

}