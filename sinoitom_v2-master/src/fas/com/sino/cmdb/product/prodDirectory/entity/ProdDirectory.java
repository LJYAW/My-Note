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
package com.sino.cmdb.product.prodDirectory.entity;

import javax.persistence.*;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">赵琛</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2017年11月08日16:29:45
 */
@Entity
@Table(name = "Cmdb_VendorProduct",uniqueConstraints = @UniqueConstraint(columnNames = "productID"))
public class ProdDirectory {

	private String vendorID;
	private String vendorName;
	private String productID;
	private Integer prodClassID;
	private Integer prodClassCode;
	private String prodClassName;
	private Integer prodTypeID;
	private Integer prodTypeCode;
	private String prodTypeName;
	private String prodSeriesID;
	private String prodSeries;
	private String prodModelID;
	private String prodModelName;
	private String prodModelOID;
	private String productNo;
	private String productName;
	private Integer prodModality;
	private String prodPrice;
	private String currencyType;
	private String description;
	private Integer status;
	private String remark;

	public String getVendorID() {
		return vendorID;
	}

	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Id
	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public Integer getProdClassID() {
		return prodClassID;
	}

	public void setProdClassID(Integer prodClassID) {
		this.prodClassID = prodClassID;
	}

	public String getProdClassName() {
		return prodClassName;
	}

	public void setProdClassName(String prodClassName) {
		this.prodClassName = prodClassName;
	}

	public Integer getProdTypeID() {
		return prodTypeID;
	}

	public void setProdTypeID(Integer prodTypeID) {
		this.prodTypeID = prodTypeID;
	}

	public String getProdTypeName() {
		return prodTypeName;
	}

	public void setProdTypeName(String prodTypeName) {
		this.prodTypeName = prodTypeName;
	}

	public String getProdSeries() {
		return prodSeries;
	}

	public void setProdSeries(String prodSeries) {
		this.prodSeries = prodSeries;
	}

	public String getProdModelID() {
		return prodModelID;
	}

	public void setProdModelID(String prodModelID) {
		this.prodModelID = prodModelID;
	}

	public String getProdModelName() {
		return prodModelName;
	}

	public void setProdModelName(String prodModelName) {
		this.prodModelName = prodModelName;
	}

	public String getProdModelOID() {
		return prodModelOID;
	}

	public void setProdModelOID(String prodModelOID) {
		this.prodModelOID = prodModelOID;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getProdModality() {
		return prodModality;
	}

	public void setProdModality(Integer prodModality) {
		this.prodModality = prodModality;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProdSeriesID() {
		return prodSeriesID;
	}

	public void setProdSeriesID(String prodSeriesID) {
		this.prodSeriesID = prodSeriesID;
	}

	public Integer getProdClassCode() {
		return prodClassCode;
	}

	public void setProdClassCode(Integer prodClassCode) {
		this.prodClassCode = prodClassCode;
	}

	public Integer getProdTypeCode() {
		return prodTypeCode;
	}

	public void setProdTypeCode(Integer prodTypeCode) {
		this.prodTypeCode = prodTypeCode;
	}

	public String getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(String prodPrice) {
		this.prodPrice = prodPrice;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
}
