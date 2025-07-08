/*
 * 文件名： VendorOSType.java
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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2014-1-19
 */
@Entity
@Table(name = "Cmdb_VendorOsClass",uniqueConstraints = @UniqueConstraint(columnNames = "vendorOsTypeID"))
public class VendorOsClass {
	
	private Integer vendorOSTypeID;
	private Integer vendorID;
	private String  vendorName;
	private Integer osClassCode;
	private String  osClassName;
	private String  description;
	private Integer  status;
	
	
	@Id
	@Column(name="VendorOSTypeID")
	public Integer getVendorOSTypeID() {
		return vendorOSTypeID;
	}
	public void setVendorOSTypeID(Integer vendorOSTypeID) {
		this.vendorOSTypeID = vendorOSTypeID;
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
	
	


}
