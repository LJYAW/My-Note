/*
 * 文件名： TresholdLevel.java
 * 
 * 创建日期： 2014-9-12
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
 * @since 2014-9-12
 */
@Entity
@Table(name = "Alm_ThresholdLevel", uniqueConstraints = @UniqueConstraint(columnNames = "ID"))
public class ThresholdLevel {

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
	private Integer notification_Id;
	
	@Id
	@Column(name="iD") 
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getiD() {
		return iD;
	}
	public void setiD(Integer iD) {
		this.iD = iD;
	}
	
	@Column(name="threshold_ID") 
	public Integer getThreshold_ID() {
		return threshold_ID;
	}
	public void setThreshold_ID(Integer threshold_ID) {
		this.threshold_ID = threshold_ID;
	}
	
	@Column(name="threshold_Name") 
	public String getThreshold_Name() {
		return threshold_Name;
	}
	public void setThreshold_Name(String threshold_Name) {
		this.threshold_Name = threshold_Name;
	}
	
	@Column(name="severity_ID") 
	public Integer getSeverity_ID() {
		return severity_ID;
	}
	public void setSeverity_ID(Integer severity_ID) {
		this.severity_ID = severity_ID;
	}
	
	@Column(name="severityEName") 
	public String getSeverityEName() {
		return severityEName;
	}
	public void setSeverityEName(String severityEName) {
		this.severityEName = severityEName;
	}
	
	@Column(name="severity_Name") 
	public String getSeverity_Name() {
		return severity_Name;
	}
	public void setSeverity_Name(String severity_Name) {
		this.severity_Name = severity_Name;
	}
	
	@Column(name="lowValue") 
	public Integer getLowValue() {
		return lowValue;
	}
	public void setLowValue(Integer lowValue) {
		this.lowValue = lowValue;
	}
	
	@Column(name="highValue") 
	public Integer getHighValue() {
		return highValue;
	}
	public void setHighValue(Integer highValue) {
		this.highValue = highValue;
	}
	
	@Column(name="count") 
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	@Column(name="units") 
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	
	@Column(name="priority") 
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	@Column(name="notification_Id") 
	public Integer getNotification_Id() {
		return notification_Id;
	}
	public void setNotification_Id(Integer notification_Id) {
		this.notification_Id = notification_Id;
	}
	
	

}
