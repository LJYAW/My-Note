/*
 * 文件名： VendorOSName.java
 * 
 * 创建日期： 2014-1-19
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
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
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.2 $
 *
 * @since 2014-1-19
 */
@Entity
@Table(name = "Cmdb_VendorOsType",uniqueConstraints = @UniqueConstraint(columnNames = "osTypeID"))
public class VendorOsType {
	
	private Integer osTypeID;
	private Integer vendorID;
	private String vendorName;
	private Integer osClassCode;
	private String osClassName;
	private String osType;
	private String osTypeName;
	private String description;
	private Integer status;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getOsTypeID() {
		return osTypeID;
	}
	public void setOsTypeID(Integer osTypeID) {
		this.osTypeID = osTypeID;
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
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getOsTypeName() {
		return osTypeName;
	}
	public void setOsTypeName(String osTypeName) {
		this.osTypeName = osTypeName;
	}
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
	
	
	
	


}
