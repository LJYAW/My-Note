/*
 * 文件名： ParamDevSwitch.java
 * 
 * 创建日期： 2014-9-3
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.ack.resources.entity;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-3
 */
public class ParamSnmpRes {
	
	
	private String orgID;
	private String orgName;
	private Integer vendorID;
	private String vendorName;
	private Integer resClassCode;
	private String resClassName;
	private Integer resTypeCode;
	private String resTypeName;
	private String resModel;
	private String modelOID;
	private Long resID;
	private String resIP;
	private Long resIpLong;
	private String resName;
	private String accessMode;
	private String userName;
	private String osType;
	private String snmpStr;
	private String desc;
	private String devUse;  //设备用途
	
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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
	public String getResModel() {
		return resModel;
	}
	public void setResModel(String resModel) {
		this.resModel = resModel;
	}
	public String getModelOID() {
		return modelOID;
	}
	public void setModelOID(String modelOID) {
		this.modelOID = modelOID;
	}
	public Long getResID() {
		return resID;
	}
	public void setResID(Long resID) {
		this.resID = resID;
	}
	public String getResIP() {
		return resIP;
	}
	public void setResIP(String resIP) {
		this.resIP = resIP;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getAccessMode() {
		return accessMode;
	}
	public void setAccessMode(String accessMode) {
		this.accessMode = accessMode;
	}
	public String getSnmpStr() {
		return snmpStr;
	}
	public void setSnmpStr(String snmpStr) {
		this.snmpStr = snmpStr;
	}
	public String getOrgID() {
		return orgID;
	}
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getResIpLong() {
		return resIpLong;
	}
	public void setResIpLong(Long resIpLong) {
		this.resIpLong = resIpLong;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDevUse() {
		return devUse;
	}
	public void setDevUse(String devUse) {
		this.devUse = devUse;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	

}
