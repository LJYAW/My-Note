/*
 * 文件名： ThresHoldType.java
 * 
 * 创建日期： 2014-9-10
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.threshold.entity;

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
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>

 *
 * @version $Revision$
 *
 * @since 2014-9-10
 */
@Entity
@Table(name = "Cmdb_ThresholdType", uniqueConstraints = @UniqueConstraint(columnNames = "ThreshTypeID"))
public class ThresholdType {
	
	private Integer threshTypeID;
	private String indDimCode;
	private String indDimName;
	private Integer indObjCode;
	private String indObjName;
	private Long indGroupID;
	private String indGroupEname;
	private String indGroupName;
	private String threshTypeName;
	private String defaultUnit;
	private String unitName;
	private String description;
	private Integer status;
	
	@Id
	@Column(name="ThreshTypeID") 
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getThreshTypeID() {
		return threshTypeID;
	}
	public void setThreshTypeID(Integer threshTypeID) {
		this.threshTypeID = threshTypeID;
	}
	
	@Column(name="IndDimCode")
	public String getIndDimCode() {
		return indDimCode;
	}
	public void setIndDimCode(String indDimCode) {
		this.indDimCode = indDimCode;
	}
	
	@Column(name="IndDimName")
	public String getIndDimName() {
		return indDimName;
	}
	public void setIndDimName(String indDimName) {
		this.indDimName = indDimName;
	}
	
	@Column(name="IndObjCode")
	public Integer getIndObjCode() {
		return indObjCode;
	}
	public void setIndObjCode(Integer indObjCode) {
		this.indObjCode = indObjCode;
	}
	
	@Column(name="IndObjName")
	public String getIndObjName() {
		return indObjName;
	}
	public void setIndObjName(String indObjName) {
		this.indObjName = indObjName;
	}
	
	@Column(name="IndGroupID")
	public Long getIndGroupID() {
		return indGroupID;
	}
	public void setIndGroupID(Long indGroupID) {
		this.indGroupID = indGroupID;
	}
	
	@Column(name="IndGroupEname")
	public String getIndGroupEname() {
		return indGroupEname;
	}
	public void setIndGroupEname(String indGroupEname) {
		this.indGroupEname = indGroupEname;
	}
	
	@Column(name="IndGroupName")
	public String getIndGroupName() {
		return indGroupName;
	}
	public void setIndGroupName(String indGroupName) {
		this.indGroupName = indGroupName;
	}
	
	@Column(name="ThreshTypeName")
	public String getThreshTypeName() {
		return threshTypeName;
	}
	public void setThreshTypeName(String threshTypeName) {
		this.threshTypeName = threshTypeName;
	}
	
	@Column(name="DefaultUnit")
	public String getDefaultUnit() {
		return defaultUnit;
	}
	public void setDefaultUnit(String defaultUnit) {
		this.defaultUnit = defaultUnit;
	}
	
	@Column(name="UnitName")
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	@Column(name="Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="Status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}


}
