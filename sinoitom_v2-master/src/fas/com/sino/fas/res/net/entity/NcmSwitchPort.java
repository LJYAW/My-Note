package com.sino.fas.res.net.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sino.base.common.Global;

/**
 * NcmSwitchPort entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Ncm_SwitchPort")
public class NcmSwitchPort implements java.io.Serializable {

	// Fields

	private NcmSwitchPortId id;
	private String ifName;
	private Integer portNo;
	private Integer availablity;
	private Integer multiMacPort;
	private String location;
	private String ifAliasName;
	private Integer adminStatus;
	private Integer operStatus;
	private Long lastChangeTime;
	private Long portStatusChangeTime;

	// Constructors

	/** default constructor */
	public NcmSwitchPort() {
	}

	/** minimal constructor */
	public NcmSwitchPort(NcmSwitchPortId id, String ifName,
			Integer availablity, Integer multiMacPort, String ifAliasName,
			Integer adminStatus, Integer operStatus) {
		this.id = id;
		this.ifName = ifName;
		this.availablity = availablity;
		this.multiMacPort = multiMacPort;
		this.ifAliasName = ifAliasName;
		this.adminStatus = adminStatus;
		this.operStatus = operStatus;
	}

	/** full constructor */
	public NcmSwitchPort(NcmSwitchPortId id, String ifName, Integer portNo,
			Integer availablity, Integer multiMacPort, String location,
			String ifAliasName, Integer adminStatus, Integer operStatus,
			Long lastChangeTime, Long portStatusChangeTime) {
		this.id = id;
		this.ifName = ifName;
		this.portNo = portNo;
		this.availablity = availablity;
		this.multiMacPort = multiMacPort;
		this.location = location;
		this.ifAliasName = ifAliasName;
		this.adminStatus = adminStatus;
		this.operStatus = operStatus;
		this.lastChangeTime = lastChangeTime;
		this.portStatusChangeTime = portStatusChangeTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "switchId", column = @Column(name = "Switch_Id", nullable = false)),
			@AttributeOverride(name = "ifIndex", column = @Column(name = "IfIndex", nullable = false)) })
	public NcmSwitchPortId getId() {
		return this.id;
	}

	public void setId(NcmSwitchPortId id) {
		this.id = id;
	}

	@Column(name = "IfName", nullable = false, length = 64)
	public String getIfName() {
		return this.ifName;
	}

	public void setIfName(String ifName) {
		this.ifName = ifName;
	}

	@Column(name = "Port_NO")
	public Integer getPortNo() {
		return this.portNo;
	}

	public void setPortNo(Integer portNo) {
		this.portNo = portNo;
	}

	@Column(name = "Availablity", nullable = false)
	public Integer getAvailablity() {
		return this.availablity;
	}

	public void setAvailablity(Integer availablity) {
		this.availablity = availablity;
	}

	@Column(name = "MultiMacPort", nullable = false)
	public Integer getMultiMacPort() {
		return this.multiMacPort;
	}

	public void setMultiMacPort(Integer multiMacPort) {
		this.multiMacPort = multiMacPort;
	}

	@Column(name = "Location", length = 64)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "IfAliasName", nullable = false, length = 64)
	public String getIfAliasName() {
		return this.ifAliasName;
	}

	public void setIfAliasName(String ifAliasName) {
		this.ifAliasName = ifAliasName;
	}

	@Column(name = "Admin_Status", nullable = false)
	public Integer getAdminStatus() {
		return this.adminStatus;
	}

	public void setAdminStatus(Integer adminStatus) {
		this.adminStatus = adminStatus;
	}

	@Column(name = "Oper_Status", nullable = false)
	public Integer getOperStatus() {
		return this.operStatus;
	}

	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}

	@Column(name = "Last_Change_Time")
	public Long getLastChangeTime() {
		return this.lastChangeTime;
	}

	public void setLastChangeTime(Long lastChangeTime) {
		this.lastChangeTime = lastChangeTime;
	}

	@Column(name = "PortStatusChange_Time")
	public Long getPortStatusChangeTime() {
		return this.portStatusChangeTime;
	}

	public void setPortStatusChangeTime(Long portStatusChangeTime) {
		this.portStatusChangeTime = portStatusChangeTime;
	}

}