package com.sino.ack.resources.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ack_Resources")
public class AckResources {
	private Integer ackResId;
	private String orgID;
	private String orgName;
	private Long resId;
	private String resName;
	private Integer resClassCode;
	private String resClassName;
	private Integer resTypeCode;
	private String resTypeName;
	private Integer vendorID;
	private String vendorName;
	private String modelOID;
	private String prodModel;
	private String osClass;
	private String osType;
	private String osVersion;
	private String osFeature;
	private String osRelease;
	private String mgmtIP;
	private Long mgmtIpLong;
	private String devAcsUserId;
	private String snmpCredId;
	private String accessMode;
	private String userName;
	private String snmpStr;
	private Integer flag;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getAckResId() {
		return ackResId;
	}
	public void setAckResId(Integer ackResId) {
		this.ackResId = ackResId;
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
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
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
	public String getModelOID() {
		return modelOID;
	}
	public void setModelOID(String modelOID) {
		this.modelOID = modelOID;
	}
	public String getProdModel() {
		return prodModel;
	}
	public void setProdModel(String prodModel) {
		this.prodModel = prodModel;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getMgmtIP() {
		return mgmtIP;
	}
	public void setMgmtIP(String mgmtIP) {
		this.mgmtIP = mgmtIP;
	}
	public Long getMgmtIpLong() {
		return mgmtIpLong;
	}
	public void setMgmtIpLong(Long mgmtIpLong) {
		this.mgmtIpLong = mgmtIpLong;
	}
	public String getAccessMode() {
		return accessMode;
	}
	public void setAccessMode(String accessMode) {
		this.accessMode = accessMode;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSnmpStr() {
		return snmpStr;
	}
	public void setSnmpStr(String snmpStr) {
		this.snmpStr = snmpStr;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getOsFeature() {
		return osFeature;
	}
	public void setOsFeature(String osFeature) {
		this.osFeature = osFeature;
	}
	public String getOsRelease() {
		return osRelease;
	}
	public void setOsRelease(String osRelease) {
		this.osRelease = osRelease;
	}
	public String getOsClass() {
		return osClass;
	}
	public void setOsClass(String osClass) {
		this.osClass = osClass;
	}
	public String getDevAcsUserId() {
		return devAcsUserId;
	}
	public void setDevAcsUserId(String devAcsUserId) {
		this.devAcsUserId = devAcsUserId;
	}
	public String getSnmpCredId() {
		return snmpCredId;
	}
	public void setSnmpCredId(String snmpCredId) {
		this.snmpCredId = snmpCredId;
	}
	
	
	

}
