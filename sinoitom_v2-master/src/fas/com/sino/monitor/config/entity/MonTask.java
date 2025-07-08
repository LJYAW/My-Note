package com.sino.monitor.config.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Mon_Task")
public class MonTask {

	private String orgId;
	private Integer taskId;
	private String taskName;
	private String taskType;
	private String taskDesc;
	private Integer opID;
	private String operation;
	private Integer runMode;
	private Date startTime;
	private Integer intervalHours;
	private String filePrefix;
	private String fileFormat;
	private String creator;
	private Date createTime;
	private String modifier;
	private Date modifyTime;
	private Integer flag;
	private Integer status;
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Integer getRunMode() {
		return runMode;
	}
	public void setRunMode(Integer runMode) {
		this.runMode = runMode;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Integer getIntervalHours() {
		return intervalHours;
	}
	public void setIntervalHours(Integer intervalHours) {
		this.intervalHours = intervalHours;
	}
	public String getFilePrefix() {
		return filePrefix;
	}
	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}
	public String getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getOpID() {
		return opID;
	}
	public void setOpID(Integer opID) {
		this.opID = opID;
	}
	
	
	
}
