package com.sino.base.system.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysLoginInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Sys_LoginInfo")
public class SysLoginInfo implements java.io.Serializable {

	// Fields

	private String loginInfoId;
	private String loginName;
	private String userName;
	private String clientIp;
	private Long totalLoginNum;
	private Timestamp lastLoginTime;

	// Constructors

	/** default constructor */
	public SysLoginInfo() {
	}

	/** full constructor */
	public SysLoginInfo(String loginInfoId, String loginName, String userName,
			String clientIp, Long totalLoginNum, Timestamp lastLoginTime) {
		this.loginInfoId = loginInfoId;
		this.loginName = loginName;
		this.userName = userName;
		this.clientIp = clientIp;
		this.totalLoginNum = totalLoginNum;
		this.lastLoginTime = lastLoginTime;
	}

	// Property accessors
	@Id
	@Column(name = "LoginInfoId", unique = true, nullable = false, length = 36)
	public String getLoginInfoId() {
		return this.loginInfoId;
	}

	public void setLoginInfoId(String loginInfoId) {
		this.loginInfoId = loginInfoId;
	}

	@Column(name = "LoginName", nullable = false, length = 32)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "UserName", nullable = false, length = 32)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "ClientIp", nullable = false, length = 16)
	public String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@Column(name = "TotalLoginNum", nullable = false)
	public Long getTotalLoginNum() {
		return this.totalLoginNum;
	}

	public void setTotalLoginNum(Long totalLoginNum) {
		this.totalLoginNum = totalLoginNum;
	}

	@Column(name = "LastLoginTime", nullable = false, length = 19)
	public Timestamp getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}