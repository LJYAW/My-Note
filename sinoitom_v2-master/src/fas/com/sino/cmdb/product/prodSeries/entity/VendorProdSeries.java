/*
 * 文件名： VendorProdSeries.java
 * 
 * 创建日期： 2014-2-12
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.product.prodSeries.entity;

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
@Table(name = "Cmdb_VendorProdSeries",uniqueConstraints = @UniqueConstraint(columnNames = "prodSeriesID"))
public class VendorProdSeries {
	
	private Integer prodSeriesID;
	private Integer vendorID;
	private Integer prodClassCode;
	private Integer prodTypeID;
	private String prodSeries;
	private String description;
	private Integer status;
	
	
	@Id
	@Column(name="ProdSeriesID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getProdSeriesID() {
		return prodSeriesID;
	}
	public void setProdSeriesID(Integer prodSeriesID) {
		this.prodSeriesID = prodSeriesID;
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
	
	@Column(name="ProdSeries")
	public String getProdSeries() {
		return prodSeries;
	}
	public void setProdSeries(String prodSeries) {
		this.prodSeries = prodSeries;
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
