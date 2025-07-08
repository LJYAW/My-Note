/**
 *
 */
package com.sino.ack.devCheckReport.entity;

import com.sino.ack.task.entity.AckTask;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Mr.LP
 * @date 2019-7-11下午2:28:22
 */

@Entity
@Table(name = "Ack_DevCheckReport")
public class AckDevCheckReport {

	private Long devChkRptId;
	private long ackTaskStartTimeGMT;
	private Timestamp taskStartTime;
	private Timestamp taskEndTime;
	private String ackReport;
	private Integer useTime;
	private AckTask ackTask;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getDevChkRptId() {
		return devChkRptId;
	}

	public void setDevChkRptId(Long devChkRptId) {
		this.devChkRptId = devChkRptId;
	}

	public String getAckReport() {
		return ackReport;
	}

	public void setAckReport(String ackReport) {
		this.ackReport = ackReport;
	}

	public Timestamp getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(Timestamp taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public Timestamp getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(Timestamp taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public Integer getUseTime() {
		return useTime;
	}

	public void setUseTime(Integer useTime) {
		this.useTime = useTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ackTaskId")
	public AckTask getAckTask() {
		return ackTask;
	}

	public void setAckTask(AckTask ackTask) {
		this.ackTask = ackTask;
	}

	@Column(name="ack_task_start_time_gmt")
	public long getAckTaskStartTimeGMT() {
		return ackTaskStartTimeGMT;
	}

	public void setAckTaskStartTimeGMT(long ackTaskStartTimeGMT) {
		this.ackTaskStartTimeGMT = ackTaskStartTimeGMT;
	}
}
