/**
 * 
 */
package com.sino.ack.devCheckData.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Mr.LP
 * @date 2019-7-11下午2:00:00
 */

@Entity
@Table(name = "Ack_DevCheckData")
public class AckDevCheckData {
	
	
	
	private Long ackDataId;
	private Integer ackTaskId;
	private Long devId;
	private String devIp;
	private Integer checkCmdId;
	private String checkCmd;
	private String indGroupName;
	private Long indItemID;
	private String indItemEnName;
	private String indItemName;
	private String valueType;
	private String indvalue;
	private Integer length;
	private Integer decimals;
	private String measureUnit;
	private long ackTaskStartTimeGMT;
	private Timestamp taskStartTime;
	private String remark;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getAckDataId() {
		return ackDataId;
	}
	public void setAckDataId(Long ackDataId) {
		this.ackDataId = ackDataId;
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
	public String getIndGroupName() {
		return indGroupName;
	}
	public void setIndGroupName(String indGroupName) {
		this.indGroupName = indGroupName;
	}
	public Long getIndItemID() {
		return indItemID;
	}
	public void setIndItemID(Long indItemID) {
		this.indItemID = indItemID;
	}
	public String getIndItemEnName() {
		return indItemEnName;
	}
	public void setIndItemEnName(String indItemEnName) {
		this.indItemEnName = indItemEnName;
	}
	public String getIndItemName() {
		return indItemName;
	}
	public void setIndItemName(String indItemName) {
		this.indItemName = indItemName;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	public String getIndvalue() {
		return indvalue;
	}
	public void setIndvalue(String indvalue) {
		this.indvalue = indvalue;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getDecimals() {
		return decimals;
	}
	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getTaskStartTime() {
		return taskStartTime;
	}
	public void setTaskStartTime(Timestamp taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	public Integer getAckTaskId() {
		return ackTaskId;
	}
	public void setAckTaskId(Integer ackTaskId) {
		this.ackTaskId = ackTaskId;
	}


	@Column(name="ack_task_start_time_gmt")
	public long getAckTaskStartTimeGMT() {
		return ackTaskStartTimeGMT;
	}

	public void setAckTaskStartTimeGMT(long ackTaskStartTimeGMT) {
		this.ackTaskStartTimeGMT = ackTaskStartTimeGMT;
	}
}
