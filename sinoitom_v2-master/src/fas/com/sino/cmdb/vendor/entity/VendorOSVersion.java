/*
 * 文件名： VendorOSVersion.java
 * 
 * 创建日期： 2014-2-13
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.vendor.entity;

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
 * @since 2014-2-13
 */
@Entity
@Table(name = "Cmdb_VendorOsVersion",uniqueConstraints = @UniqueConstraint(columnNames = "osVersionID"))
public class VendorOSVersion {
	
	private Integer osVersionID;
	private Integer vendorID;
	private Integer osClassCode;
	private String osClassName;
	private String osType;
	private String osFeature;
	private String osVersion;
	private String description;
	private Integer status;
	
	
	@Id
	@Column(name="OsVersionID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getOsVersionID() {
		return osVersionID;
	}
	public void setOsVersionID(Integer osVersionID) {
		this.osVersionID = osVersionID;
	}
	
	@Column(name="VendorID")
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}
	
	public Integer getOsClassCode() {
		return osClassCode;
	}
	public void setOsClassCode(Integer osClassCode) {
		this.osClassCode = osClassCode;
	}
	
	public String getOsClassName() {
		return osClassName;
	}
	public void setOsClassName(String osClassName) {
		this.osClassName = osClassName;
	}
	@Column(name="OsVersion")
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	
	@Column(name="Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getOsFeature() {
		return osFeature;
	}
	public void setOsFeature(String osFeature) {
		this.osFeature = osFeature;
	}
	
	
	


}
