/*
 * 文件名： ParamThresholdLevel.java
 * 
 * 创建日期： 2014-9-12
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
 * @since 2014-9-12
 */
public class ParamThresholdLevel {
	private Integer number;
	private Integer lowValue;
	private String compareLow;
	private String indiName;
	private String compareHigh;
	private Integer highValue;
	private Integer count;
	private Integer severity_ID;
	private String severity_Name;
	private String severity_EName;
	private String priority;
	
	public String getSeverity_EName() {
		return severity_EName;
	}
	public void setSeverity_EName(String severity_EName) {
		this.severity_EName = severity_EName;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getLowValue() {
		return lowValue;
	}
	public void setLowValue(Integer lowValue) {
		this.lowValue = lowValue;
	}
	public String getCompareLow() {
		return compareLow;
	}
	public void setCompareLow(String compareLow) {
		this.compareLow = compareLow;
	}
	public String getIndiName() {
		return indiName;
	}
	public void setIndiName(String indiName) {
		this.indiName = indiName;
	}
	public String getCompareHigh() {
		return compareHigh;
	}
	public void setCompareHigh(String compareHigh) {
		this.compareHigh = compareHigh;
	}
	public Integer getHighValue() {
		return highValue;
	}
	public void setHighValue(Integer highValue) {
		this.highValue = highValue;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getSeverity_ID() {
		return severity_ID;
	}
	public void setSeverity_ID(Integer severity_ID) {
		this.severity_ID = severity_ID;
	}
	public String getSeverity_Name() {
		return severity_Name;
	}
	public void setSeverity_Name(String severity_Name) {
		this.severity_Name = severity_Name;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	

	
}
