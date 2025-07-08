/*
 * 文件名： ParamThresholdInfoAndType.java
 * 
 * 创建日期： 2014-9-11
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.threshold.entity;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-11
 */
public class ParamThresholdInfoAndType {

	private String uUID;
	private Integer threshTypeID;
	private String thresholdName;
	private Integer thresholdLevel;
	private String description;
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
	private Integer status;
	private Integer threshold_ID;
	
	public Integer getThreshold_ID() {
		return threshold_ID;
	}
	public void setThreshold_ID(Integer threshold_ID) {
		this.threshold_ID = threshold_ID;
	}
	public String getuUID() {
		return uUID;
	}
	public void setuUID(String uUID) {
		this.uUID = uUID;
	}
	public Integer getThreshTypeID() {
		return threshTypeID;
	}
	public void setThreshTypeID(Integer threshTypeID) {
		this.threshTypeID = threshTypeID;
	}
	public String getThresholdName() {
		return thresholdName;
	}
	public void setThresholdName(String thresholdName) {
		this.thresholdName = thresholdName;
	}
	public Integer getThresholdLevel() {
		return thresholdLevel;
	}
	public void setThresholdLevel(Integer thresholdLevel) {
		this.thresholdLevel = thresholdLevel;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIndDimCode() {
		return indDimCode;
	}
	public void setIndDimCode(String indDimCode) {
		this.indDimCode = indDimCode;
	}
	public String getIndDimName() {
		return indDimName;
	}
	public void setIndDimName(String indDimName) {
		this.indDimName = indDimName;
	}
	public Integer getIndObjCode() {
		return indObjCode;
	}
	public void setIndObjCode(Integer indObjCode) {
		this.indObjCode = indObjCode;
	}
	public String getIndObjName() {
		return indObjName;
	}
	public void setIndObjName(String indObjName) {
		this.indObjName = indObjName;
	}
	public Long getIndGroupID() {
		return indGroupID;
	}
	public void setIndGroupID(Long indGroupID) {
		this.indGroupID = indGroupID;
	}
	public String getIndGroupEname() {
		return indGroupEname;
	}
	public void setIndGroupEname(String indGroupEname) {
		this.indGroupEname = indGroupEname;
	}
	public String getIndGroupName() {
		return indGroupName;
	}
	public void setIndGroupName(String indGroupName) {
		this.indGroupName = indGroupName;
	}
	public String getThreshTypeName() {
		return threshTypeName;
	}
	public void setThreshTypeName(String threshTypeName) {
		this.threshTypeName = threshTypeName;
	}
	public String getDefaultUnit() {
		return defaultUnit;
	}
	public void setDefaultUnit(String defaultUnit) {
		this.defaultUnit = defaultUnit;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
