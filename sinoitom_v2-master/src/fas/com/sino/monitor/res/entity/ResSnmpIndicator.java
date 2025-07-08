/*
 * 文件名： ResSnmpIndicator.java
 * 
 * 创建日期： 2014-9-4
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.monitor.res.entity;

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
 * @since 2014-9-4
 */
@Entity
@Table(name="Mon_ResSnmpIndicator" , uniqueConstraints = @UniqueConstraint(columnNames = "ResIndItemID"))
public class ResSnmpIndicator {
	
	private Integer resIndItemID;
	private Long resID;
	private String resIP;
	private Integer resClassCode;
	private String resClassName;
	private Integer resTypeCode;
	private String resTypeName;
	private String subResType;
	private Long  subResID;
	private String subResName;
	private String subResInfo;
	private Integer poll_Interval;
	private Integer indClassCode;
	private String indClassName;
	private Long indGroupID;
	private String indGroupName;
	private Long indItemID;
	private String indicatorItem;
	private String indItemName;
	private String snmpObjName;
	private String snmpOID;
	private String getMethod;
	private Integer oidFlag;
	private String valueType;
	private Integer length;
	private Integer decimals;
	private String measureUnit;
	private Integer monFlag;
	private Integer thresholdID;
	
	
	
	@Id
	@Column(name="ResIndItemID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getResIndItemID() {
		return resIndItemID;
	}
	public void setResIndItemID(Integer resIndItemID) {
		this.resIndItemID = resIndItemID;
	}
	
	@Column(name="ResID")
	public Long getResID() {
		return resID;
	}
	public void setResID(Long resID) {
		this.resID = resID;
	}
	
	@Column(name="ResIP")
	public String getResIP() {
		return resIP;
	}
	public void setResIP(String resIP) {
		this.resIP = resIP;
	}
	
	@Column(name="ResClassCode")
	public Integer getResClassCode() {
		return resClassCode;
	}
	public void setResClassCode(Integer resClassCode) {
		this.resClassCode = resClassCode;
	}
	
	@Column(name="ResClassName")
	public String getResClassName() {
		return resClassName;
	}
	public void setResClassName(String resClassName) {
		this.resClassName = resClassName;
	}
	
	@Column(name="ResTypeCode")
	public Integer getResTypeCode() {
		return resTypeCode;
	}
	public void setResTypeCode(Integer resTypeCode) {
		this.resTypeCode = resTypeCode;
	}
	
	@Column(name="ResTypeName")
	public String getResTypeName() {
		return resTypeName;
	}
	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}
	
	@Column(name="Poll_Interval")
	public Integer getPoll_Interval() {
		return poll_Interval;
	}
	public void setPoll_Interval(Integer poll_Interval) {
		this.poll_Interval = poll_Interval;
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
	
	@Column(name="IndGroupID")
	public Long getIndGroupID() {
		return indGroupID;
	}
	public void setIndGroupID(Long indGroupID) {
		this.indGroupID = indGroupID;
	}
	
	@Column(name="IndGroupName")
	public String getIndGroupName() {
		return indGroupName;
	}
	public void setIndGroupName(String indGroupName) {
		this.indGroupName = indGroupName;
	}
	
	@Column(name="IndItemID")
	public Long getIndItemID() {
		return indItemID;
	}
	public void setIndItemID(Long indItemID) {
		this.indItemID = indItemID;
	}
	
	@Column(name="IndicatorItem")
	public String getIndicatorItem() {
		return indicatorItem;
	}
	public void setIndicatorItem(String indicatorItem) {
		this.indicatorItem = indicatorItem;
	}
	
	@Column(name="IndItemName")
	public String getIndItemName() {
		return indItemName;
	}
	public void setIndItemName(String indItemName) {
		this.indItemName = indItemName;
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
	
	@Column(name="ValueType")
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
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
	
	@Column(name="MonFlag")
	public Integer getMonFlag() {
		return monFlag;
	}
	public void setMonFlag(Integer monFlag) {
		this.monFlag = monFlag;
	}
	
	@Column(name="ThresholdID")
	public Integer getThresholdID() {
		return thresholdID;
	}
	public void setThresholdID(Integer thresholdID) {
		this.thresholdID = thresholdID;
	}
	
	@Column(name="SubResType")
	public String getSubResType() {
		return subResType;
	}
	public void setSubResType(String subResType) {
		this.subResType = subResType;
	}
	
	@Column(name="SubResID")
	public Long getSubResID() {
		return subResID;
	}
	public void setSubResID(Long subResID) {
		this.subResID = subResID;
	}
	
	@Column(name="SubResInfo")
	public String getSubResInfo() {
		return subResInfo;
	}
	public void setSubResInfo(String subResInfo) {
		this.subResInfo = subResInfo;
	}
	
	@Column(name="SubResName")
	public String getSubResName() {
		return subResName;
	}
	public void setSubResName(String subResName) {
		this.subResName = subResName;
	}
	
	
	
	


}
