/*
 * 文件名： MonLineInterface.java
 * 
 * 创建日期： 2014-11-4
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.monitor.intf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-11-4
 */
@Entity
@Table(name="Mon_LineInterface" , uniqueConstraints = @UniqueConstraint(columnNames = "if_ID"))
public class MonLineInterface {

	private String orgName;
	private Integer resType;
	private Long resID;
	private String resIP;
	private Long resIpLong;
	private Long if_ID;
	private Long if_Index;
	private String if_Name;
	private Long if_Speed;
	private String if_BaudRate;
	private String if_IpAddr;
	private Long if_IpLong;
	private Integer monIF;
	private Long lineID;
	private String lineName;
	private Long lineBandwidth;
	private String lineBaudRate;
	private Integer monLinePerf;
	private String toOrgName;
	
	private Long toDeviceID;
	private String toDevceIP;
	private String toIf_Name;
	private String toIf_IpAddr;
	
	private Long toIf_IpAddrLong;
	
	private Integer monLineStatus;
	private Integer poll_Interval;
	private Integer pingMode;
	private Integer agentTypeCode;
	private String  agentTypeName;
	private String  agentIP;
	private Integer policyID;

	private String remoteSvrIp;
	private Integer remoteSvrPort;
	
	private String vrfName;
	
	
	
	@Column(name="ResType")
	public Integer getResType() {
		return resType;
	}
	public void setResType(Integer resType) {
		this.resType = resType;
	}
	
	@Column(name="ResID")
	public Long getResID() {
		return resID;
	}
	public void setResID(Long resID) {
		this.resID = resID;
	}
	
	@Column(name="ResIP")
	public String getResIP() {
		return resIP;
	}
	public void setResIP(String resIP) {
		this.resIP = resIP;
	}
	
	@Id
	@Column(name="IF_ID")
	public Long getIf_ID() {
		return if_ID;
	}
	public void setIf_ID(Long if_ID) {
		this.if_ID = if_ID;
	}
	
	@Column(name="If_Index")
	public Long getIf_Index() {
		return if_Index;
	}
	public void setIf_Index(Long if_Index) {
		this.if_Index = if_Index;
	}
	
	@Column(name="If_Name")
	public String getIf_Name() {
		return if_Name;
	}
	public void setIf_Name(String if_Name) {
		this.if_Name = if_Name;
	}
	
	@Column(name="If_Speed")
	public Long getIf_Speed() {
		return if_Speed;
	}
	public void setIf_Speed(Long if_Speed) {
		this.if_Speed = if_Speed;
	}
	
	@Column(name="If_IpAddr")
	public String getIf_IpAddr() {
		return if_IpAddr;
	}
	public void setIf_IpAddr(String if_IpAddr) {
		this.if_IpAddr = if_IpAddr;
	}
	
	
	@Column(name="If_IpLong")
	public Long getIf_IpLong() {
		return if_IpLong;
	}
	public void setIf_IpLong(Long if_IpLong) {
		this.if_IpLong = if_IpLong;
	}
	
	@Column(name="MonIF")
	public Integer getMonIF() {
		return monIF;
	}
	public void setMonIF(Integer monIF) {
		this.monIF = monIF;
	}
	
	@Column(name="LineID")
	public Long getLineID() {
		return lineID;
	}
	public void setLineID(Long lineID) {
		this.lineID = lineID;
	}
	
	@Column(name="LineName")
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
	@Column(name="LineBandwidth")
	public Long getLineBandwidth() {
		return lineBandwidth;
	}
	public void setLineBandwidth(Long lineBandwidth) {
		this.lineBandwidth = lineBandwidth;
	}
	
	
	@Column(name="MonLinePerf")
	public Integer getMonLinePerf() {
		return monLinePerf;
	}
	public void setMonLinePerf(Integer monLinePerf) {
		this.monLinePerf = monLinePerf;
	}
	
	@Column(name="If_BaudRate")
	public String getIf_BaudRate() {
		return if_BaudRate;
	}
	public void setIf_BaudRate(String if_BaudRate) {
		this.if_BaudRate = if_BaudRate;
	}
	
	@Column(name="LineBaudRate")
	public String getLineBaudRate() {
		return lineBaudRate;
	}
	public void setLineBaudRate(String lineBaudRate) {
		this.lineBaudRate = lineBaudRate;
	}
	
	@Column(name="ToOrgName")
	public String getToOrgName() {
		return toOrgName;
	}
	public void setToOrgName(String toOrgName) {
		this.toOrgName = toOrgName;
	}
	
	@Column(name="OrgName")
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@Column(name="ResIpLong")
	public Long getResIpLong() {
		return resIpLong;
	}
	public void setResIpLong(Long resIpLong) {
		this.resIpLong = resIpLong;
	}
	
	
	@Column(name="ToDeviceID")
	public Long getToDeviceID() {
		return toDeviceID;
	}
	public void setToDeviceID(Long toDeviceID) {
		this.toDeviceID = toDeviceID;
	}
	
	@Column(name="ToDevceIP")
	public String getToDevceIP() {
		return toDevceIP;
	}
	public void setToDevceIP(String toDevceIP) {
		this.toDevceIP = toDevceIP;
	}
	
	@Column(name="ToIf_Name")
	public String getToIf_Name() {
		return toIf_Name;
	}
	public void setToIf_Name(String toIf_Name) {
		this.toIf_Name = toIf_Name;
	}
	
	@Column(name="ToIf_IpAddr")
	public String getToIf_IpAddr() {
		return toIf_IpAddr;
	}
	public void setToIf_IpAddr(String toIf_IpAddr) {
		this.toIf_IpAddr = toIf_IpAddr;
	}
	
	@Column(name="MonLineStatus")
	public Integer getMonLineStatus() {
		return monLineStatus;
	}
	public void setMonLineStatus(Integer monLineStatus) {
		this.monLineStatus = monLineStatus;
	}
	
	@Column(name="Poll_Interval")
	public Integer getPoll_Interval() {
		return poll_Interval;
	}
	public void setPoll_Interval(Integer poll_Interval) {
		this.poll_Interval = poll_Interval;
	}
	
	@Column(name="PingMode")
	public Integer getPingMode() {
		return pingMode;
	}
	public void setPingMode(Integer pingMode) {
		this.pingMode = pingMode;
	}
	
	@Column(name="AgentTypeCode")
	public Integer getAgentTypeCode() {
		return agentTypeCode;
	}
	public void setAgentTypeCode(Integer agentTypeCode) {
		this.agentTypeCode = agentTypeCode;
	}
	
	@Column(name="AgentTypeName")
	public String getAgentTypeName() {
		return agentTypeName;
	}
	public void setAgentTypeName(String agentTypeName) {
		this.agentTypeName = agentTypeName;
	}
	
	@Column(name="AgentIP")
	public String getAgentIP() {
		return agentIP;
	}
	public void setAgentIP(String agentIP) {
		this.agentIP = agentIP;
	}
	
	@Column(name="ToIf_IpAddrLong")
	public Long getToIf_IpAddrLong() {
		return toIf_IpAddrLong;
	}
	public void setToIf_IpAddrLong(Long toIf_IpAddrLong) {
		this.toIf_IpAddrLong = toIf_IpAddrLong;
	}
	
	@Column(name="PolicyID")
	public Integer getPolicyID() {
		return policyID;
	}
	public void setPolicyID(Integer policyID) {
		this.policyID = policyID;
	}
	public String getRemoteSvrIp() {
		return remoteSvrIp;
	}
	public void setRemoteSvrIp(String remoteSvrIp) {
		this.remoteSvrIp = remoteSvrIp;
	}
	public Integer getRemoteSvrPort() {
		return remoteSvrPort;
	}
	public void setRemoteSvrPort(Integer remoteSvrPort) {
		this.remoteSvrPort = remoteSvrPort;
	}
	public String getVrfName() {
		return vrfName;
	}
	public void setVrfName(String vrfName) {
		this.vrfName = vrfName;
	}
	
	
	
	

}
