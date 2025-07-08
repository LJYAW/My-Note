package com.sino.base.system.entity;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Sys_User", uniqueConstraints = @UniqueConstraint(columnNames = "LoginName"))
public class SysUser implements java.io.Serializable {

	// Fields

	private String userId;
	private Integer userType;
	private String loginName;
	private String loginPasswd;
	private String userName;
	private String email;
	private String mobile;
	private String description;
	private String mainOrgId;
	private String loginSkinName;
	private String loginMenuStyle;
	private Timestamp lastLoginTime;
	private Integer state;
	private String creator;
	private Integer creatorType;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private String remark;
	private Set<SysRole> sysRoles = new HashSet<SysRole>(0);
	private Set<OrgOrganization> orgOrganizations = new HashSet<OrgOrganization>(0);
	private Set<SysPower> sysPowers = new HashSet<SysPower>(0);

	// Constructors

	/** default constructor */
	public SysUser() {
	}

	/** minimal constructor */
	public SysUser(String userId, Integer userType, String loginName,
			String loginPasswd, Timestamp lastLoginTime, Integer state,
			String creator, Integer creatorType, Timestamp createTime,
			String modifier, Timestamp modifyTime) {
		this.userId = userId;
		this.userType = userType;
		this.loginName = loginName;
		this.loginPasswd = loginPasswd;
		this.lastLoginTime = lastLoginTime;
		this.state = state;
		this.creator = creator;
		this.creatorType = creatorType;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
	}

	/** full constructor */
	public SysUser(String userId, Integer userType, String loginName,
			String loginPasswd, String userName, String email, String mobile,
			String description, String mainOrgId, String loginSkinName,
			String loginMenuStyle, Timestamp lastLoginTime, Integer state,
			String creator, Integer creatorType, Timestamp createTime,
			String modifier, Timestamp modifyTime, String remark,
			Set<SysRole> sysRoles, Set<OrgOrganization> orgOrganizations,
			Set<SysPower> sysPowers) {
		this.userId = userId;
		this.userType = userType;
		this.loginName = loginName;
		this.loginPasswd = loginPasswd;
		this.userName = userName;
		this.email = email;
		this.mobile = mobile;
		this.description = description;
		this.mainOrgId = mainOrgId;
		this.loginSkinName = loginSkinName;
		this.loginMenuStyle = loginMenuStyle;
		this.lastLoginTime = lastLoginTime;
		this.state = state;
		this.creator = creator;
		this.creatorType = creatorType;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.remark = remark;
		this.sysRoles = sysRoles;
		this.orgOrganizations = orgOrganizations;
		this.sysPowers = sysPowers;
	}

	// Property accessors
	@Id
	@Column(name = "UserId", unique = true, nullable = false, length = 36)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "UserType", nullable = false)
	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	@Column(name = "LoginName", unique = true, nullable = false, length = 32)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "LoginPasswd", nullable = false, length = 128)
	public String getLoginPasswd() {
		return this.loginPasswd;
	}

	public void setLoginPasswd(String loginPasswd) {
		this.loginPasswd = loginPasswd;
	}

	@Column(name = "UserName", length = 32)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "Email", length = 128)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "Mobile", length = 32)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "Description", length = 128)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "MainOrgId", length = 36)
	public String getMainOrgId() {
		return this.mainOrgId;
	}

	public void setMainOrgId(String mainOrgId) {
		this.mainOrgId = mainOrgId;
	}

	@Column(name = "LoginSkinName", length = 32)
	public String getLoginSkinName() {
		return this.loginSkinName;
	}

	public void setLoginSkinName(String loginSkinName) {
		this.loginSkinName = loginSkinName;
	}

	@Column(name = "LoginMenuStyle", length = 32)
	public String getLoginMenuStyle() {
		return this.loginMenuStyle;
	}

	public void setLoginMenuStyle(String loginMenuStyle) {
		this.loginMenuStyle = loginMenuStyle;
	}

	@Column(name = "LastLoginTime", nullable = false, length = 23)
	public Timestamp getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column(name = "State", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "Creator", nullable = false, length = 32)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CreatorType", nullable = false)
	public Integer getCreatorType() {
		return this.creatorType;
	}

	public void setCreatorType(Integer creatorType) {
		this.creatorType = creatorType;
	}

	@Column(name = "CreateTime", nullable = false, length = 23)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "Modifier", nullable = false, length = 32)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "ModifyTime", nullable = false, length = 23)
	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "Remark", length = 128)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinTable(name = "Sys_UserMapRole", joinColumns = { @JoinColumn(name = "UserId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "RoleId", nullable = false, updatable = false) })
	public Set<SysRole> getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinTable(name = "Sys_UserMapOrg", joinColumns = { @JoinColumn(name = "UserId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "OrgId", nullable = false, updatable = false) })
	public Set<OrgOrganization> getOrgOrganizations() {
		return this.orgOrganizations;
	}

	public void setOrgOrganizations(Set<OrgOrganization> orgOrganizations) {
		this.orgOrganizations = orgOrganizations;
	}

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinTable(name = "Sys_UserMapPow", joinColumns = { @JoinColumn(name = "UserId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "PowId", nullable = false, updatable = false) })
	public Set<SysPower> getSysPowers() {
		return this.sysPowers;
	}

	public void setSysPowers(Set<SysPower> sysPowers) {
		this.sysPowers = sysPowers;
	}

}