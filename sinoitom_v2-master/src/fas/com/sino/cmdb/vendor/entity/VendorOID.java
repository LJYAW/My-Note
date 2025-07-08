/*
 * 文件名： VendorOID.java
 * 
 * 创建日期： 2014-8-19
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
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
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-8-19
 */
@Entity
@Table(name = "Cmdb_VendorOID",uniqueConstraints = @UniqueConstraint(columnNames = "VendorID"))
public class VendorOID {
	private String vendorOID;	//varchar	20	厂商SnmpOID
	private Integer vendorID;	//int	2	厂商ID  
	private String remark;		//varchar	128	备注
	private Integer oidFlag;	//int	2	厂商ID  
	private Integer status;	//int	2	厂商ID  
	
	
	@Id
	@Column(name = "VendorOID")
	public String getVendorOID() {
		return vendorOID;
	}
	public void setVendorOID(String vendorOID) {
		this.vendorOID = vendorOID;
	}
	
	@Column(name = "VendorID")
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "oidFlag")
	public Integer getOidFlag() {
		return oidFlag;
	}
	public void setOidFlag(Integer oidFlag) {
		this.oidFlag = oidFlag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
	

}
