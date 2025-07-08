/*
 * 文件名： Serverity.java
 * 
 * 创建日期： 2014-9-12
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.severity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "Cmdb_Severity", uniqueConstraints = @UniqueConstraint(columnNames = "ID"))
public class Severity {

	private Integer iD;
	private Integer severityCode;
	private String severityEName;
	private String severityName;
	private String alarmColor;
	private String colorValue;
	private String description;
	private String priority;
	
	@Id
	@Column(name="ID") 
	public Integer getiD() {
		return iD;
	}
	public void setiD(Integer iD) {
		this.iD = iD;
	}
	
	@Column(name="SeverityCode") 
	public Integer getSeverityCode() {
		return severityCode;
	}
	public void setSeverityCode(Integer severityCode) {
		this.severityCode = severityCode;
	}
	
	@Column(name="SeverityEName") 
	public String getSeverityEName() {
		return severityEName;
	}
	public void setSeverityEName(String severityEName) {
		this.severityEName = severityEName;
	}
	
	@Column(name="SeverityName") 
	public String getSeverityName() {
		return severityName;
	}
	public void setSeverityName(String severityName) {
		this.severityName = severityName;
	}
	
	@Column(name="AlarmColor") 
	public String getAlarmColor() {
		return alarmColor;
	}
	public void setAlarmColor(String alarmColor) {
		this.alarmColor = alarmColor;
	}
	
	@Column(name="ColorValue") 
	public String getColorValue() {
		return colorValue;
	}
	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}
	
	@Column(name="Description") 
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="Priority") 
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	
	
}
