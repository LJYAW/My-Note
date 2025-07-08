package com.sino.ack.task.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sino.ack.devCheckReport.entity.AckDevCheckReport;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Ack_Task")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "ackTaskId")
public class AckTask {
	private Integer ackTaskId;
	private String taskName;
	private String taskDescr;
	private Integer checkCycle;
	private String cycleUnit;
	private Integer pollCycle;
	private Integer taskType;
	private Integer checkTimes;
	private Timestamp firstChkTime;
	private Integer status;
	private Integer tmplId;
	private String tmplName;

	private Set<AckDevCheckReport> reportSet = new HashSet<AckDevCheckReport>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getAckTaskId() {
		return ackTaskId;
	}

	public void setAckTaskId(Integer ackTaskId) {
		this.ackTaskId = ackTaskId;
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

	public Integer getCheckCycle() {
		return checkCycle;
	}

	public void setCheckCycle(Integer checkCycle) {
		this.checkCycle = checkCycle;
	}

	public String getCycleUnit() {
		return cycleUnit;
	}

	public void setCycleUnit(String cycleUnit) {
		this.cycleUnit = cycleUnit;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPollCycle() {
		return pollCycle;
	}

	public void setPollCycle(Integer pollCycle) {
		this.pollCycle = pollCycle;
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

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ackTask")
	//	@JoinTable(name = "Ack_DevCheckReport", joinColumns = { @JoinColumn(name = "ackTaskId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ackTaskId", nullable = false, updatable = false) })
	public Set<AckDevCheckReport> getReportSet() {
		return reportSet;
	}

	public void setReportSet(Set<AckDevCheckReport> reportSet) {
		this.reportSet = reportSet;
	}
}
