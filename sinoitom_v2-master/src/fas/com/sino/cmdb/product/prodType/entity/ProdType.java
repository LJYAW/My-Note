/*
 * 文件名： ProdType.java
 * 
 * 创建日期： 2014-1-16
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.cmdb.product.prodType.entity;

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
 * @since 2014-1-16
 */
@Entity
@Table(name = "Cmdb_VendorProdType",uniqueConstraints = @UniqueConstraint(columnNames = "ProdTypeID"))
public class ProdType {
	
	private Integer prodTypeID;
	private Integer vendorID;
	private Integer prodClassID;
	private Integer prodClassCode;
	private String prodClassName;
	private Integer typeCode;
	private String typeName;
	private String dispName;
	private String description;
	private Integer status;
	
	
	@Id
	@Column(name="ProdTypeID")
	public Integer getProdTypeID() {
		return prodTypeID;
	}
	public void setProdTypeID(Integer prodTypeID) {
		this.prodTypeID = prodTypeID;
	}
	
	@Column(name="VendorID")
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}
	
	@Column(name="ProdClassID")
	public Integer getProdClassID() {
		return prodClassID;
	}
	public void setProdClassID(Integer prodClassID) {
		this.prodClassID = prodClassID;
	}
	
	@Column(name="ProdClassCode")
	public Integer getProdClassCode() {
		return prodClassCode;
	}
	public void setProdClassCode(Integer prodClassCode) {
		this.prodClassCode = prodClassCode;
	}
	
	@Column(name="ProdClassName")
	public String getProdClassName() {
		return prodClassName;
	}
	public void setProdClassName(String prodClassName) {
		this.prodClassName = prodClassName;
	}
	
	@Column(name="TypeCode")
	public Integer getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}
	
	@Column(name="TypeName")
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Column(name="DispName")
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
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
