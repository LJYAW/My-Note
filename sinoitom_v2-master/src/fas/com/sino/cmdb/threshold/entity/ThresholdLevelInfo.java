/*
 * 文件名： ThresholdLevelInfo.java
 * 
 * 创建日期： 2014-10-13
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.cmdb.threshold.entity;

import com.sino.cmdb.severity.entity.Severity;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision$
 *
 * @since 2014-10-13
 */
public class ThresholdLevelInfo {
	public ThresholdLevelInfo(ThresholdLevel hold, Severity s) {
		super();
		this.iD=              hold.getiD();            
		this.threshold_ID=    hold.getThreshold_ID();  
		this.threshold_Name=  hold.getThreshold_Name();
		this.severity_ID=     hold.getSeverity_ID();   
		this.severityEName=   hold.getSeverityEName(); 
		this.severity_Name=   hold.getSeverity_Name(); 
		this.lowValue=        hold.getLowValue();      
		this.highValue=       hold.getHighValue();     
		this.count=           hold.getCount();         
		this.units=           hold.getUnits();         
		this.priority=        hold.getPriority();      
		
		this.severityCode=s.getSeverityCode();
		this.severityName=s.getSeverityName();
		this.alarmColor=  s.getAlarmColor();  
		this.colorValue=  s.getColorValue();  
		this.description= s.getDescription(); 
	}
	private Integer iD;    
	private Integer threshold_ID;
	private String threshold_Name;
	private Integer severity_ID;
	private String severityEName;
	private String severity_Name;
	private Integer lowValue;
	private Integer highValue;
	private Integer count;
	private String units;
	private String priority;
	
	private Integer severityCode;
	private String severityName;
	private String alarmColor;
	private String colorValue;
	private String description;
	
	
	
	public Integer getiD() {
		return iD;
	}
	public void setiD(Integer iD) {
		this.iD = iD;
	}
	public Integer getThreshold_ID() {
		return threshold_ID;
	}
	public void setThreshold_ID(Integer threshold_ID) {
		this.threshold_ID = threshold_ID;
	}
	public String getThreshold_Name() {
		return threshold_Name;
	}
	public void setThreshold_Name(String threshold_Name) {
		this.threshold_Name = threshold_Name;
	}
	public Integer getSeverity_ID() {
		return severity_ID;
	}
	public void setSeverity_ID(Integer severity_ID) {
		this.severity_ID = severity_ID;
	}
	public String getSeverityEName() {
		return severityEName;
	}
	public void setSeverityEName(String severityEName) {
		this.severityEName = severityEName;
	}
	public String getSeverity_Name() {
		return severity_Name;
	}
	public void setSeverity_Name(String severity_Name) {
		this.severity_Name = severity_Name;
	}
	public Integer getLowValue() {
		return lowValue;
	}
	public void setLowValue(Integer lowValue) {
		this.lowValue = lowValue;
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
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Integer getSeverityCode() {
		return severityCode;
	}
	public void setSeverityCode(Integer severityCode) {
		this.severityCode = severityCode;
	}
	public String getSeverityName() {
		return severityName;
	}
	public void setSeverityName(String severityName) {
		this.severityName = severityName;
	}
	public String getAlarmColor() {
		return alarmColor;
	}
	public void setAlarmColor(String alarmColor) {
		this.alarmColor = alarmColor;
	}
	public String getColorValue() {
		return colorValue;
	}
	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
