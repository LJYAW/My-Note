package com.sino.bpm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Bpm_TaskMonRes", uniqueConstraints = @UniqueConstraint(columnNames = "taskMonResId"))
public class BpmTaskMonRes {
	private Integer taskMonResId;
	private Integer taskId;//'任务ID'
	private String taskName;//'任务ID'
	private String orgID;//'机构ID'
	private String orgName;//'机构名称--简称'
	private Long resId;//'资源ID'
	private String resIp;//管理IP地址
	private Long resIpLong;//IP Long
	private String resName;//'资源显示名称'
	private Integer resClassCode;//'资源分类编码'
	private String resClassName;//'资源分类名称'
	private Integer resTypeCode;//'资源类型编码'
	private String resTypeName;//'资源类型名称'
	private Integer vendorID;//'厂商ID'
	private String vendorName;//'厂商名称'
	private String modelOID;//'型号OID'
	private String prodModel;//'产品型号'
	private String osClass;//'操作系统分类'
	private String osType;//'操作系统类型'
	private String osVersion;//'操作系统版本(版本分类，11.X)'
	private String osRelease;//'操作系统发布版本（具体版本）'
	private String osFeature;//'操作系统特征（Standard，Advance等）'
	private String resAcsMode;//资源访问方式(Telnet;SSH;Snmp;PortConnect;Jdbc)
	private String resAcsUserId;//'设备访问用户Id'
	private String indCollMode;//指标采集方式（在维护资源指标采集方式、规则时，作为缺省方式）
	private String snmpCredId;//'snmp证书ID'
	private String userName;//'访问用户'
	private String snmpStr;//'Snmp团体字'
	private Integer flag;//'监测标志(1:监测;0:暂停)'

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getTaskMonResId() {
		return taskMonResId;
	}
	public void setTaskMonResId(Integer taskMonResId) {
		this.taskMonResId = taskMonResId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public String getResIp() {
		return resIp;
	}

	public void setResIp(String resIp) {
		this.resIp = resIp;
	}

	public Long getResIpLong() {
		return resIpLong;
	}

	public void setResIpLong(Long resIpLong) {
		this.resIpLong = resIpLong;
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

	public String getOsClass() {
		return osClass;
	}

	public void setOsClass(String osClass) {
		this.osClass = osClass;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getOsRelease() {
		return osRelease;
	}

	public void setOsRelease(String osRelease) {
		this.osRelease = osRelease;
	}

	public String getOsFeature() {
		return osFeature;
	}

	public void setOsFeature(String osFeature) {
		this.osFeature = osFeature;
	}

	public String getResAcsMode() {
		return resAcsMode;
	}

	public void setResAcsMode(String resAcsMode) {
		this.resAcsMode = resAcsMode;
	}

	public String getResAcsUserId() {
		return resAcsUserId;
	}

	public void setResAcsUserId(String resAcsUserId) {
		this.resAcsUserId = resAcsUserId;
	}

	public String getIndCollMode() {
		return indCollMode;
	}

	public void setIndCollMode(String indCollMode) {
		this.indCollMode = indCollMode;
	}

	public String getSnmpCredId() {
		return snmpCredId;
	}

	public void setSnmpCredId(String snmpCredId) {
		this.snmpCredId = snmpCredId;
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
}
