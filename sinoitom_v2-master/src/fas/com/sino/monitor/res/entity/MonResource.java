/*
 * 文件名： MonResource.java
 * 
 * 创建日期： 2014-9-3
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.monitor.res.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-3
 */
@Entity
@Table(name="Mon_Resource" , uniqueConstraints = @UniqueConstraint(columnNames = "ResID"))
public class MonResource {
	
	private String orgID;
	private String orgName;
	private Long resID;
	private Integer vendorID;
	private String vendorName;
	private Integer resClassCode;
	private String resClassName;
	private Integer resTypeCode;
	private String resTypeName;
	private String modelOID;
	private String prodModel;
	private String resIP;
	private Long resIpLong;
	private String resName;
	private String accessMode;
	private Long collector_ID;
	private Integer poll_Interval;
	private String snmpStr;
	private Integer filter_Id;
	private Integer policy_ID;
	private Integer monFlag;
	
	
	@Id
	@Column(name="ResID")
	public Long getResID() {
		return resID;
	}
	public void setResID(Long resID) {
		this.resID = resID;
	}
	
	
	@Column(name="VendorID")
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}
	
	
	
	@Column(name="VendorName")
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	
	@Column(name="ResClassCode")
	public Integer getResClassCode() {
		return resClassCode;
	}
	public void setResClassCode(Integer resClassCode) {
		this.resClassCode = resClassCode;
	}
	
	@Column(name="ResClassName")
	public String getResClassName() {
		return resClassName;
	}
	public void setResClassName(String resClassName) {
		this.resClassName = resClassName;
	}
	
	@Column(name="ResTypeCode")
	public Integer getResTypeCode() {
		return resTypeCode;
	}
	public void setResTypeCode(Integer resTypeCode) {
		this.resTypeCode = resTypeCode;
	}
	
	@Column(name="ResTypeName")
	public String getResTypeName() {
		return resTypeName;
	}
	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}
	
	@Column(name="ModelOID")
	public String getModelOID() {
		return modelOID;
	}
	public void setModelOID(String modelOID) {
		this.modelOID = modelOID;
	}
	
	
	@Column(name="ResIP")
	public String getResIP() {
		return resIP;
	}
	public void setResIP(String resIP) {
		this.resIP = resIP;
	}
	
	@Column(name="ResName")
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	
	@Column(name="AccessMode")
	public String getAccessMode() {
		return accessMode;
	}
	public void setAccessMode(String accessMode) {
		this.accessMode = accessMode;
	}
	
	@Column(name="Collector_ID")
	public Long getCollector_ID() {
		return collector_ID;
	}
	public void setCollector_ID(Long collector_ID) {
		this.collector_ID = collector_ID;
	}
	
	@Column(name="Poll_Interval")
	public Integer getPoll_Interval() {
		return poll_Interval;
	}
	public void setPoll_Interval(Integer poll_Interval) {
		this.poll_Interval = poll_Interval;
	}
	
	@Column(name="SnmpStr")
	public String getSnmpStr() {
		return snmpStr;
	}
	public void setSnmpStr(String snmpStr) {
		this.snmpStr = snmpStr;
	}
	
	@Column(name="Filter_Id")
	public Integer getFilter_Id() {
		return filter_Id;
	}
	public void setFilter_Id(Integer filter_Id) {
		this.filter_Id = filter_Id;
	}
	
	@Column(name="Policy_ID")
	public Integer getPolicy_ID() {
		return policy_ID;
	}
	public void setPolicy_ID(Integer policy_ID) {
		this.policy_ID = policy_ID;
	}
	
	@Column(name="MonFlag")
	public Integer getMonFlag() {
		return monFlag;
	}
	public void setMonFlag(Integer monFlag) {
		this.monFlag = monFlag;
	}
	
	@Column(name="ProdModel")
	public String getProdModel() {
		return prodModel;
	}
	public void setProdModel(String prodModel) {
		this.prodModel = prodModel;
	}
	
	@Column(name="OrgID")
	public String getOrgID() {
		return orgID;
	}
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	
	@Column(name="OrgName")
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@Column(name="ResIpLong")
	public Long getResIpLong() {
		return resIpLong;
	}
	public void setResIpLong(Long resIpLong) {
		this.resIpLong = resIpLong;
	}
	
	
	


}
