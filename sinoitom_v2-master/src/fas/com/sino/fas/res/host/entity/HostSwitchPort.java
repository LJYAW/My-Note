package com.sino.fas.res.host.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name = "Ncm_SwitchPort")
public class HostSwitchPort implements java.io.Serializable{
	private static final long serialVersionUID = -6541414915032332813L;
	
	private long switchId;//  Switch_Id                                                bigint(19) NOT NULL
	private long ifIndex;//  IfIndex                                           bigint(19) NOT NULL
	private String ifName;//  IfName                                            varchar(64) NOT NULL
	private Integer protNO;//  Port_NO                                        int(10) NULL
	private Integer availablity;//  Availablity                                                   int(10) NOT NULL
	private Integer multiMacProt;//  MultiMacPort                                           int(10) NOT NULL
	private String location;//  Location                                           varchar(64) NULL
	private String ifAliasName;//  IfAliasName                                  varchar(64) NOT NULL
	private Integer adminStatus;//  Admin_Status                                int(10) NOT NULL
	private Integer operStatus;//  Oper_Status                                    int(10) NOT NULL
	private Long lastChangeTime;//  Last_Change_Time                                  bigint(19) NULL
	private Long protStatusChangeTime;//  PortStatusChange_Time                                         bigint(19) NULL
	
	@EmbeddedId
	private HostSwitchPortId hostSwitchPortId;
	
	@EmbeddedId
	public HostSwitchPortId getHostSwitchPortId() {
		return hostSwitchPortId;
	}
	public void setHostSwitchPortId(HostSwitchPortId hostSwitchPortId) {
		this.hostSwitchPortId = hostSwitchPortId;
	}
	
	@Transient
	public long getSwitchId() {
		return switchId;
	}
	public void setSwitchId(long switchId) {
		this.switchId = switchId;
	}
	
	@Transient
	public long getIfIndex() {
		return ifIndex;
	}
	public void setIfIndex(long ifIndex) {
		this.ifIndex = ifIndex;
	}
	
	@Column(name = "IfName")
	public String getIfName() {
		return ifName;
	}
	public void setIfName(String ifName) {
		this.ifName = ifName;
	}
	
	@Column(name = "Port_NO")
	public Integer getProtNO() {
		return protNO;
	}
	public void setProtNO(Integer protNO) {
		this.protNO = protNO;
	}
	
	@Column(name = "Availablity")
	public Integer getAvailablity() {
		return availablity;
	}
	public void setAvailablity(Integer availablity) {
		this.availablity = availablity;
	}
	
	@Column(name = "MultiMacPort")
	public Integer getMultiMacProt() {
		return multiMacProt;
	}
	public void setMultiMacProt(Integer multiMacProt) {
		this.multiMacProt = multiMacProt;
	}
	
	@Column(name = "Location")
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Column(name = "IfAliasName")
	public String getIfAliasName() {
		return ifAliasName;
	}
	public void setIfAliasName(String ifAliasName) {
		this.ifAliasName = ifAliasName;
	}
	
	@Column(name = "Admin_Status")
	public Integer getAdminStatus() {
		return adminStatus;
	}
	public void setAdminStatus(Integer adminStatus) {
		this.adminStatus = adminStatus;
	}
	
	@Column(name = "Oper_Status")
	public Integer getOperStatus() {
		return operStatus;
	}
	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}
	
	@Column(name = "Last_Change_Time")
	public Long getLastChangeTime() {
		return lastChangeTime;
	}
	public void setLastChangeTime(Long lastChangeTime) {
		this.lastChangeTime = lastChangeTime;
	}
	
	@Column(name = "PortStatusChange_Time")
	public Long getProtStatusChangeTime() {
		return protStatusChangeTime;
	}
	public void setProtStatusChangeTime(Long protStatusChangeTime) {
		this.protStatusChangeTime = protStatusChangeTime;
	}
	
}
