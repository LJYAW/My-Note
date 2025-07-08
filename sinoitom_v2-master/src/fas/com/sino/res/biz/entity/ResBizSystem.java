package com.sino.res.biz.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Res_BizSystem")
public class ResBizSystem {
	
	private String orgId;
	private String orgName;
	private Long resId;
	private Integer resClassCode;
	private String resClassName;
	private Integer resTypeCode;
	private String resTypeName;
	private Integer bizTypeCode;
	private String bizTypeName;
	private String sysEnName;
	private String sysName;
	private String bizDesc;
	private String accessMode;
	private String accessProt;
	private Integer svrType;
	private String svrIpAddr;
	private Long svrId;
	private String svcName;
	private Integer svcPort;
	private String bizURL;
	private Integer status;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	
	private Integer bizURLStatus;
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Id
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public Integer getResClassCode() {
		return resClassCode;
	}
	public void setResClassCode(Integer resClassCode) {
		this.resClassCode = resClassCode;
	}
	public String getResClassName() {
		return resClassName;
	}
	public void setResClassName(String resClassName) {
		this.resClassName = resClassName;
	}
	public Integer getResTypeCode() {
		return resTypeCode;
	}
	public void setResTypeCode(Integer resTypeCode) {
		this.resTypeCode = resTypeCode;
	}
	public String getResTypeName() {
		return resTypeName;
	}
	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}
	public Integer getBizTypeCode() {
		return bizTypeCode;
	}
	public void setBizTypeCode(Integer bizTypeCode) {
		this.bizTypeCode = bizTypeCode;
	}
	public String getBizTypeName() {
		return bizTypeName;
	}
	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}
	public String getSysEnName() {
		return sysEnName;
	}
	public void setSysEnName(String sysEnName) {
		this.sysEnName = sysEnName;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public String getBizDesc() {
		return bizDesc;
	}
	public void setBizDesc(String bizDesc) {
		this.bizDesc = bizDesc;
	}
	public String getAccessMode() {
		return accessMode;
	}
	public void setAccessMode(String accessMode) {
		this.accessMode = accessMode;
	}
	public String getAccessProt() {
		return accessProt;
	}
	public void setAccessProt(String accessProt) {
		this.accessProt = accessProt;
	}
	public Integer getSvrType() {
		return svrType;
	}
	public void setSvrType(Integer svrType) {
		this.svrType = svrType;
	}
	public String getSvrIpAddr() {
		return svrIpAddr;
	}
	public void setSvrIpAddr(String svrIpAddr) {
		this.svrIpAddr = svrIpAddr;
	}
	public Long getSvrId() {
		return svrId;
	}
	public void setSvrId(Long svrId) {
		this.svrId = svrId;
	}
	public String getSvcName() {
		return svcName;
	}
	public void setSvcName(String svcName) {
		this.svcName = svcName;
	}
	public Integer getSvcPort() {
		return svcPort;
	}
	public void setSvcPort(Integer svcPort) {
		this.svcPort = svcPort;
	}
	public String getBizURL() {
		return bizURL;
	}
	public void setBizURL(String bizURL) {
		this.bizURL = bizURL;
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
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getBizURLStatus() {
		return bizURLStatus;
	}
	public void setBizURLStatus(Integer bizURLStatus) {
		this.bizURLStatus = bizURLStatus;
	}
	
	

}
