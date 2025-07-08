package com.sino.fas.res.net.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Ncm_DevAccessUser")
public class NcmDevAccessUser {

	private String devAcsUserId;
	private String orgId;
	private String acsUserName;
	private String acsUserDescr;
	private String accessTool;
	private Integer accessPort;
	private String userName;
	private String passWord;
	private Integer userType;
	private String privModeCmd;
	private String privModePasswd;
	private Integer status;
	private String creator;
	private Date createTime;
	private String modifier;
	private Date modifyTime;
	
	@Id
	public String getDevAcsUserId() {
		return devAcsUserId;
	}
	public void setDevAcsUserId(String devAcsUserId) {
		this.devAcsUserId = devAcsUserId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getAcsUserName() {
		return acsUserName;
	}
	public void setAcsUserName(String acsUserName) {
		this.acsUserName = acsUserName;
	}
	public String getAcsUserDescr() {
		return acsUserDescr;
	}
	public void setAcsUserDescr(String acsUserDescr) {
		this.acsUserDescr = acsUserDescr;
	}
	public String getAccessTool() {
		return accessTool;
	}
	public void setAccessTool(String accessTool) {
		this.accessTool = accessTool;
	}
	public Integer getAccessPort() {
		return accessPort;
	}
	public void setAccessPort(Integer accessPort) {
		this.accessPort = accessPort;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getPrivModeCmd() {
		return privModeCmd;
	}
	public void setPrivModeCmd(String privModeCmd) {
		this.privModeCmd = privModeCmd;
	}
	public String getPrivModePasswd() {
		return privModePasswd;
	}
	public void setPrivModePasswd(String privModePasswd) {
		this.privModePasswd = privModePasswd;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	
	
}
