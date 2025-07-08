package com.sino.bpm.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Bpm_Task", uniqueConstraints = @UniqueConstraint(columnNames = "taskId"))
public class BpmTask {
	private Integer taskId;//巡检任务ID
	private Integer taskClass;//任务分类（1：性能采集；2：巡检）
	private Integer taskType;//任务类型（0：循环任务；1：单次任务；2：多次任务）
	private String taskName;//任务名称
	private String taskDescr;//任务描述
	private Integer pollCycle;//巡检周期
	private String cycleUnit;//巡检周期单位（分:minute、小时:hour、天:day、周:week、月:month）
	private Integer pollCycleSecs;//轮询周期（单位：秒，根据页面选择信息自动计算出来）
	private Integer checkTimes;//巡检次数（0：无限制；N：巡检N次）
	private Timestamp firstChkTime;//首次巡检时间
	private Integer tmplId;//模板ID
	private String tmplName;//模板名称
	private Integer status;//任务状态（0：停用；1：启用；2：执行中）
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getTaskClass() {
		return taskClass;
	}
	public void setTaskClass(Integer taskClass) {
		this.taskClass = taskClass;
	}
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDescr() {
		return taskDescr;
	}
	public void setTaskDescr(String taskDescr) {
		this.taskDescr = taskDescr;
	}
	public Integer getPollCycle() {
		return pollCycle;
	}
	public void setPollCycle(Integer pollCycle) {
		this.pollCycle = pollCycle;
	}
	public String getCycleUnit() {
		return cycleUnit;
	}
	public void setCycleUnit(String cycleUnit) {
		this.cycleUnit = cycleUnit;
	}
	public Integer getPollCycleSecs() {
		return pollCycleSecs;
	}
	public void setPollCycleSecs(Integer pollCycleSecs) {
		this.pollCycleSecs = pollCycleSecs;
	}
	public Integer getCheckTimes() {
		return checkTimes;
	}
	public void setCheckTimes(Integer checkTimes) {
		this.checkTimes = checkTimes;
	}
	public Timestamp getFirstChkTime() {
		return firstChkTime;
	}
	public void setFirstChkTime(Timestamp firstChkTime) {
		this.firstChkTime = firstChkTime;
	}
	public Integer getTmplId() {
		return tmplId;
	}
	public void setTmplId(Integer tmplId) {
		this.tmplId = tmplId;
	}
	public String getTmplName() {
		return tmplName;
	}
	public void setTmplName(String tmplName) {
		this.tmplName = tmplName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
