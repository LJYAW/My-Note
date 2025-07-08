package com.sino.ack.taskMapRes.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ack_TaskMapRes")
public class AckTaskMapRes {
	private Long taskResId;
	private Integer ackTaskId;
	private Long resId;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getTaskResId() {
		return taskResId;
	}
	public void setTaskResId(Long taskResId) {
		this.taskResId = taskResId;
	}
	public Integer getAckTaskId() {
		return ackTaskId;
	}
	public void setAckTaskId(Integer ackTaskId) {
		this.ackTaskId = ackTaskId;
	}
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	
	

}
