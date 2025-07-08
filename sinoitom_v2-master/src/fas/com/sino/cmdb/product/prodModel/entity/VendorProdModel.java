/*
 * 文件名： VendorProdModel.java
 * 
 * 创建日期： 2014-2-12
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.product.prodModel.entity;

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
 * @since 2014-2-12
 */
@Entity
@Table(name = "Cmdb_VendorProdModel",uniqueConstraints = @UniqueConstraint(columnNames = "ProdModelID"))
public class VendorProdModel {
	
	private Long prodModelID;		//主键
	private Integer vendorID;		//厂商ID
	private String vendorName;
	private Integer prodClassCode;		//产品分类编码
	private String  prodClassName;		//产品分类名称
	private Integer prodTypeID;		//产品类型ID
	private Integer prodTypeCode;		//型号编码
	private String  prodTypeName;		//产品类型名称
	private String  prodSeries;		//产品系列
	private String  prodModelName;		//产品型号名称
	private String  prodModelOid;		//型号OID
	private String  description;		//型号描述
//	private String  panelName;
	private Integer status;		//状态
	
	
	
	@Id
	@Column(name="ProdModelID")
	public Long getProdModelID() {
		return prodModelID;
	}
	public void setProdModelID(Long prodModelID) {
		this.prodModelID = prodModelID;
	}
	
	@Column(name="VendorID")
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}
	
	@Column(name="ProdClassCode")
	public Integer getProdClassCode() {
		return prodClassCode;
	}
	public void setProdClassCode(Integer prodClassCode) {
		this.prodClassCode = prodClassCode;
	}
	
	@Column(name="ProdTypeID")
	public Integer getProdTypeID() {
		return prodTypeID;
	}
	public void setProdTypeID(Integer prodTypeID) {
		this.prodTypeID = prodTypeID;
	}
	
	
	
	
	@Column(name="Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="ProdClassName")
	public String getProdClassName() {
		return prodClassName;
	}
	public void setProdClassName(String prodClassName) {
		this.prodClassName = prodClassName;
	}
	
	
	
	@Column(name="ProdSeries")
	public String getProdSeries() {
		return prodSeries;
	}
	public void setProdSeries(String prodSeries) {
		this.prodSeries = prodSeries;
	}
	
	@Column(name="Status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer Status) {
		this.status = Status;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public Integer getProdTypeCode() {
		return prodTypeCode;
	}
	public void setProdTypeCode(Integer prodTypeCode) {
		this.prodTypeCode = prodTypeCode;
	}
	public String getProdTypeName() {
		return prodTypeName;
	}
	public void setProdTypeName(String prodTypeName) {
		this.prodTypeName = prodTypeName;
	}
	public String getProdModelName() {
		return prodModelName;
	}
	public void setProdModelName(String prodModelName) {
		this.prodModelName = prodModelName;
	}
	public String getProdModelOid() {
		return prodModelOid;
	}
	public void setProdModelOid(String prodModelOid) {
		this.prodModelOid = prodModelOid;
	}
	
	
	

}
