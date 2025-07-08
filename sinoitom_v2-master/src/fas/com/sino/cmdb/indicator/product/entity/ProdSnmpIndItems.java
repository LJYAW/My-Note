/*
 * 文件名： ProdIndItem.java
 * 
 * 创建日期： 2014-7-26
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.indicator.product.entity;

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
 * @since 2014-7-26
 */
@Entity
@Table(name="Cmdb_ProdSnmpIndItems" , uniqueConstraints = @UniqueConstraint(columnNames = "prodIndItemID"))
public class ProdSnmpIndItems {
	
	private Integer prodIndItemID;
	private Integer vendorID;
	private Long prodModelID;
	private String  prodModel;
	private String  modelOID;
	private Integer devClassCode;
	private String  devClassName;
	private Integer devTypeCode;
	private String  devTypeName;
	private Integer indClassCode;
	private String  indClassName;
	private Long    indGroupID;
	private String  indGroupName;
	private Long    indItemID;
	private String  indItemName;
	private String  indicatorItem;
	private String  snmpObjName;
	private String  snmpOID;
	private String mibFile;
	private String getMethod;
	private Integer oidFlag;
	private String  valueType;
	private Integer length;
	private Integer decimals;
	private String  measureUnit;
	private String  remark;
	private Integer status;
	
	@Id
	@Column(name="ProdIndItemID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getProdIndItemID() {
		return prodIndItemID;
	}
	public void setProdIndItemID(Integer prodIndItemID) {
		this.prodIndItemID = prodIndItemID;
	}
	
	@Column(name="VendorID")
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}
	
	
	
	@Column(name="ProdModelID")
	public Long getProdModelID() {
		return prodModelID;
	}
	public void setProdModelID(Long prodModelID) {
		this.prodModelID = prodModelID;
	}
	
	@Column(name="ProdModel")
	public String getProdModel() {
		return prodModel;
	}
	public void setProdModel(String prodModel) {
		this.prodModel = prodModel;
	}
	
	@Column(name="ModelOID")
	public String getModelOID() {
		return modelOID;
	}
	public void setModelOID(String modelOID) {
		this.modelOID = modelOID;
	}
	
	@Column(name="IndGroupID")
	public Long getIndGroupID() {
		return indGroupID;
	}
	public void setIndGroupID(Long indGroupID) {
		this.indGroupID = indGroupID;
	}
	
	
	
	@Column(name="IndItemID")
	public Long getIndItemID() {
		return indItemID;
	}
	public void setIndItemID(Long indItemID) {
		this.indItemID = indItemID;
	}
	
	@Column(name="IndItemName")
	public String getIndItemName() {
		return indItemName;
	}
	public void setIndItemName(String indItemName) {
		this.indItemName = indItemName;
	}
	@Column(name="IndicatorItem")
	public String getIndicatorItem() {
		return indicatorItem;
	}
	public void setIndicatorItem(String indicatorItem) {
		this.indicatorItem = indicatorItem;
	}
	
	@Column(name="ValueType")
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	
	@Column(name="Remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="IndClassCode")
	public Integer getIndClassCode() {
		return indClassCode;
	}
	public void setIndClassCode(Integer indClassCode) {
		this.indClassCode = indClassCode;
	}
	
	@Column(name="IndClassName")
	public String getIndClassName() {
		return indClassName;
	}
	public void setIndClassName(String indClassName) {
		this.indClassName = indClassName;
	}
	
	@Column(name="DevClassCode")
	public Integer getDevClassCode() {
		return devClassCode;
	}
	public void setDevClassCode(Integer devClassCode) {
		this.devClassCode = devClassCode;
	}
	
	
	@Column(name="DevClassName")
	public String getDevClassName() {
		return devClassName;
	}
	public void setDevClassName(String devClassName) {
		this.devClassName = devClassName;
	}
	
	@Column(name="DevTypeCode")
	public Integer getDevTypeCode() {
		return devTypeCode;
	}
	public void setDevTypeCode(Integer devTypeCode) {
		this.devTypeCode = devTypeCode;
	}
	
	@Column(name="DevTypeName")
	public String getDevTypeName() {
		return devTypeName;
	}
	public void setDevTypeName(String devTypeName) {
		this.devTypeName = devTypeName;
	}
	
	@Column(name="IndGroupName")
	public String getIndGroupName() {
		return indGroupName;
	}
	public void setIndGroupName(String indGroupName) {
		this.indGroupName = indGroupName;
	}
	
	@Column(name="Length")
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	
	@Column(name="Decimals")
	public Integer getDecimals() {
		return decimals;
	}
	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}
	
	@Column(name="MeasureUnit")
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	
	@Column(name="SnmpObjName")
	public String getSnmpObjName() {
		return snmpObjName;
	}
	public void setSnmpObjName(String snmpObjName) {
		this.snmpObjName = snmpObjName;
	}
	
	@Column(name="SnmpOID")
	public String getSnmpOID() {
		return snmpOID;
	}
	public void setSnmpOID(String snmpOID) {
		this.snmpOID = snmpOID;
	}
	
	
	@Column(name="GetMethod")
	public String getGetMethod() {
		return getMethod;
	}
	public void setGetMethod(String getMethod) {
		this.getMethod = getMethod;
	}
	
	@Column(name="OidFlag")
	public Integer getOidFlag() {
		return oidFlag;
	}
	public void setOidFlag(Integer oidFlag) {
		this.oidFlag = oidFlag;
	}
	
	@Column(name="Status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name="MibFile")
	public String getMibFile() {
		return mibFile;
	}
	public void setMibFile(String mibFile) {
		this.mibFile = mibFile;
	}
	


}
