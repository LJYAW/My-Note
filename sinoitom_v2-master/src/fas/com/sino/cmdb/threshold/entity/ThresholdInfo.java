/*
 * 文件名： ThresHoldType.java
 * 
 * 创建日期： 2014-9-10
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.threshold.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @since 2014-9-10
 */
@Entity
@Table(name = "Alm_Threshold", uniqueConstraints = @UniqueConstraint(columnNames = "UUID"))
public class ThresholdInfo {

	private String uUID;
	private Integer threshTypeID;
	private Integer threshold_ID;
	private String thresholdName;
	private Integer thresholdLevel;
	private String description;
	private Integer status;
	
	
	@Id
	@Column(name="Threshold_ID") 
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getThreshold_ID() {
		return threshold_ID;
	}
	public void setThreshold_ID(Integer threshold_ID) {
		this.threshold_ID = threshold_ID;
	}
	
	@Column(name="UUID") 
	public String getuUID() {
		return uUID;
	}
	public void setuUID(String uUID) {
		this.uUID = uUID;
	}
	
	@Column(name="ThreshTypeID") 
	public Integer getThreshTypeID() {
		return threshTypeID;
	}
	public void setThreshTypeID(Integer threshTypeID) {
		this.threshTypeID = threshTypeID;
	}
	
	@Column(name="ThresholdName") 
	public String getThresholdName() {
		return thresholdName;
	}
	public void setThresholdName(String thresholdName) {
		this.thresholdName = thresholdName;
	}
	
	@Column(name="ThresholdLevel") 
	public Integer getThresholdLevel() {
		return thresholdLevel;
	}
	public void setThresholdLevel(Integer thresholdLevel) {
		this.thresholdLevel = thresholdLevel;
	}
	
	@Column(name="Description") 
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="Status") 
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
