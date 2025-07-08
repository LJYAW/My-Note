/**
 * 
 */
package com.sino.ack.devCheckReport.entity;

import java.sql.Timestamp;

/**
 * @author Mr.LP
 * @date 2019-7-11下午3:07:21
 *
 */
public class ParamAckTaskAndReport {
	
	private Integer ackTaskId;
	private String taskName;
	private String taskDescr;
	private Integer checkCycle;
	private String cycleUnit;
	private Integer pollCycle;
	private Integer taskType;
	private Integer checkTimes;

	private Long devChkRptId;
	private Timestamp taskStartTime;

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

	public Integer getPollCycle() {
		return pollCycle;
	}

	public void setPollCycle(Integer pollCycle) {
		this.pollCycle = pollCycle;
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

	public Timestamp getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(Timestamp taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public Long getDevChkRptId() {
		return devChkRptId;
	}

	public void setDevChkRptId(Long devChkRptId) {
		this.devChkRptId = devChkRptId;
	}
	
	
	

}
