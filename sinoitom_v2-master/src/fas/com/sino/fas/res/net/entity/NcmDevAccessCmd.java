package com.sino.fas.res.net.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Ncm_DevAccessCmd")
public class NcmDevAccessCmd {

	private String devAcsCmdId;
	private Integer vendorId;
	private String vendorOid;
	private String osType;
	private String osName;
	private String osVersion;
	private String userAcsPrompt;
	private String passwdPrompt;
	private String userModePrompt;
	private String privModeCmd;
	private String privPasswdPrompt;
	private String privModePrompt;
	private Integer cmdExecMode;
	private String errorCmdPrompt;
	private String logoutCmd;
	private String morePrompt;
	private String moreCmd;
	private String noMoreCmd;
	private Integer status;
	private String remark;
	
	@Id
	public String getDevAcsCmdId() {
		return devAcsCmdId;
	}
	public void setDevAcsCmdId(String devAcsCmdId) {
		this.devAcsCmdId = devAcsCmdId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorOid() {
		return vendorOid;
	}
	public void setVendorOid(String vendorOid) {
		this.vendorOid = vendorOid;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getUserAcsPrompt() {
		return userAcsPrompt;
	}
	public void setUserAcsPrompt(String userAcsPrompt) {
		this.userAcsPrompt = userAcsPrompt;
	}
	public String getPasswdPrompt() {
		return passwdPrompt;
	}
	public void setPasswdPrompt(String passwdPrompt) {
		this.passwdPrompt = passwdPrompt;
	}
	public String getUserModePrompt() {
		return userModePrompt;
	}
	public void setUserModePrompt(String userModePrompt) {
		this.userModePrompt = userModePrompt;
	}
	public String getPrivModeCmd() {
		return privModeCmd;
	}
	public void setPrivModeCmd(String privModeCmd) {
		this.privModeCmd = privModeCmd;
	}
	public String getPrivPasswdPrompt() {
		return privPasswdPrompt;
	}
	public void setPrivPasswdPrompt(String privPasswdPrompt) {
		this.privPasswdPrompt = privPasswdPrompt;
	}
	public String getPrivModePrompt() {
		return privModePrompt;
	}
	public void setPrivModePrompt(String privModePrompt) {
		this.privModePrompt = privModePrompt;
	}
	public Integer getCmdExecMode() {
		return cmdExecMode;
	}
	public void setCmdExecMode(Integer cmdExecMode) {
		this.cmdExecMode = cmdExecMode;
	}
	public String getErrorCmdPrompt() {
		return errorCmdPrompt;
	}
	public void setErrorCmdPrompt(String errorCmdPrompt) {
		this.errorCmdPrompt = errorCmdPrompt;
	}
	public String getLogoutCmd() {
		return logoutCmd;
	}
	public void setLogoutCmd(String logoutCmd) {
		this.logoutCmd = logoutCmd;
	}
	public String getMorePrompt() {
		return morePrompt;
	}
	public void setMorePrompt(String morePrompt) {
		this.morePrompt = morePrompt;
	}
	public String getMoreCmd() {
		return moreCmd;
	}
	public void setMoreCmd(String moreCmd) {
		this.moreCmd = moreCmd;
	}
	public String getNoMoreCmd() {
		return noMoreCmd;
	}
	public void setNoMoreCmd(String noMoreCmd) {
		this.noMoreCmd = noMoreCmd;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
