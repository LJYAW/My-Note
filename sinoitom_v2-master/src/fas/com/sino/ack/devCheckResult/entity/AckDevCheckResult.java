package com.sino.ack.devCheckResult.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name="Ack_DevCheckResult")
@Entity
public class AckDevCheckResult {

	private Long chkResultId;
	private Integer ackTaskId;
	private Timestamp taskStartTime;
	private Long devId;
	private String devIp;
	private Integer checkCmdId;
	private String checkCmd;
	private String chkResult;
	private long ackTaskStartTimeGMT;
	private Timestamp cmdStartTime;
	private Timestamp cmdEndTime;
	
	@Id
	public Long getChkResultId() {
		return chkResultId;
	}
	public void setChkResultId(Long chkResultId) {
		this.chkResultId = chkResultId;
	}
	public Integer getAckTaskId() {
		return ackTaskId;
	}
	public void setAckTaskId(Integer ackTaskId) {
		this.ackTaskId = ackTaskId;
	}
	public Timestamp getTaskStartTime() {
		return taskStartTime;
	}
	public void setTaskStartTime(Timestamp taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	public Long getDevId() {
		return devId;
	}
	public void setDevId(Long devId) {
		this.devId = devId;
	}
	public String getDevIp() {
		return devIp;
	}
	public void setDevIp(String devIp) {
		this.devIp = devIp;
	}
	public Integer getCheckCmdId() {
		return checkCmdId;
	}
	public void setCheckCmdId(Integer checkCmdId) {
		this.checkCmdId = checkCmdId;
	}
	public String getCheckCmd() {
		return checkCmd;
	}
	public void setCheckCmd(String checkCmd) {
		this.checkCmd = checkCmd;
	}
	public String getChkResult() {
		return chkResult;
	}
	public void setChkResult(String chkResult) {
		this.chkResult = chkResult;
	}
	public Timestamp getCmdEndTime() {
		return cmdEndTime;
	}
	public void setCmdEndTime(Timestamp cmdEndTime) {
		this.cmdEndTime = cmdEndTime;
	}
	public Timestamp getCmdStartTime() {
		return cmdStartTime;
	}
	public void setCmdStartTime(Timestamp cmdStartTime) {
		this.cmdStartTime = cmdStartTime;
	}

	@Column(name="ack_task_start_time_gmt")
	public long getAckTaskStartTimeGMT() {
		return ackTaskStartTimeGMT;
	}

	public void setAckTaskStartTimeGMT(long ackTaskStartTimeGMT) {
		this.ackTaskStartTimeGMT = ackTaskStartTimeGMT;
	}
}
