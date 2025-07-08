package com.sino.monitor.config.entity;

import java.sql.Timestamp;


public class Mon_TaskMapEntity {
	private Long taskMapDevID;
	
	//机构名称
	private String  orgName;
	//厂商名称
	private String vendorName;
	//设备分类名称
	private String devClassName;
	//设备类型名称
	private String devTypeName;
	//设备IP地址
	private String devIP;
	//访问方式
	private String accessMode;
	//监测标志
	private Integer monFlag;
	
	//任务类型
	private String taskType;
	//任务名称
	private String taskName;
	//操作名称
	private String operation;
	//开始执行时间
	private Timestamp startTime;
	//任务周期
	private Integer intervalHours;
	public Long getTaskMapDevID() {
		return taskMapDevID;
	}
	public void setTaskMapDevID(Long taskMapDevID) {
		this.taskMapDevID = taskMapDevID;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getDevClassName() {
		return devClassName;
	}
	public void setDevClassName(String devClassName) {
		this.devClassName = devClassName;
	}
	public String getDevTypeName() {
		return devTypeName;
	}
	public void setDevTypeName(String devTypeName) {
		this.devTypeName = devTypeName;
	}
	public String getDevIP() {
		return devIP;
	}
	public void setDevIP(String devIP) {
		this.devIP = devIP;
	}
	public String getAccessMode() {
		return accessMode;
	}
	public void setAccessMode(String accessMode) {
		this.accessMode = accessMode;
	}
	public Integer getMonFlag() {
		return monFlag;
	}
	public void setMonFlag(Integer monFlag) {
		this.monFlag = monFlag;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Integer getIntervalHours() {
		return intervalHours;
	}
	public void setIntervalHours(Integer intervalHours) {
		this.intervalHours = intervalHours;
	}
	
}