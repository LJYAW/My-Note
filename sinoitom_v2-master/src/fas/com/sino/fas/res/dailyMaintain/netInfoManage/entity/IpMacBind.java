package com.sino.fas.res.dailyMaintain.netInfoManage.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ncm_IpMacBind")
public class IpMacBind {
	private String orgId;
	private Long ipMacBindId;
	private Integer ipHostTypeId;
	private String hostName;
	private String ipAddr;
	private Long ipValue;
	private String ipNetMask;
	private String macAddr;
	private Integer osType;
	private String osVersion;
	private String snmpRoString;
	private String assertNO;
	private String userId;
	private Date modify_Time;
	private String serialNO;
	private Long switchId;
	private Long ifIndex;
	private Integer vlanNo;
	private String infoPointNo;
	private Integer bindStatus;
	
	@Column(name = "BindStatus")
	public Integer getBindStatus() {
		return bindStatus;
	}
	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
	@Column(name = "InfoPointNo")
	public String getInfoPointNo() {
		return infoPointNo;
	}
	public void setInfoPointNo(String infoPointNo) {
		this.infoPointNo = infoPointNo;
	}
	
	@Column(name = "Org_ID")
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Id
	@Column(name = "IpMacBind_ID", unique = true, nullable = false)
	public Long getIpMacBindId() {
		return ipMacBindId;
	}
	public void setIpMacBindId(Long ipMacBindId) {
		this.ipMacBindId = ipMacBindId;
	}
	
	@Column(name = "VlanNo")
	public Integer getVlanNo() {
		return vlanNo;
	}
	public void setVlanNo(Integer vlanNo) {
		this.vlanNo = vlanNo;
	}
	@Column(name = "IpHostType_ID")
	public Integer getIpHostTypeId() {
		return ipHostTypeId;
	}
	public void setIpHostTypeId(Integer ipHostTypeId) {
		this.ipHostTypeId = ipHostTypeId;
	}
	
	@Column(name = "HostName")
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	@Column(name = "IpAddress")
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	
	@Column(name = "IpValue")
	public Long getIpValue() {
		return ipValue;
	}
	public void setIpValue(Long ipValue) {
		this.ipValue = ipValue;
	}
	
	@Column(name = "IpNetMask")
	public String getIpNetMask() {
		return ipNetMask;
	}
	public void setIpNetMask(String ipNetMask) {
		this.ipNetMask = ipNetMask;
	}
	
	@Column(name = "MacAddress")
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
	
	@Column(name = "OS_Type")
	public Integer getOsType() {
		return osType;
	}
	public void setOsType(Integer osType) {
		this.osType = osType;
	}
	
	@Column(name = "OS_Version")
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	
	@Column(name = "SnmpRoString")
	public String getSnmpRoString() {
		return snmpRoString;
	}
	public void setSnmpRoString(String snmpRoString) {
		this.snmpRoString = snmpRoString;
	}
	
	@Column(name = "Assert_NO")
	public String getAssertNO() {
		return assertNO;
	}
	public void setAssertNO(String assertNO) {
		this.assertNO = assertNO;
	}
	
	@Column(name = "User_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "LastChange_Time")
	public Date getModify_Time() {
		return modify_Time;
	}
	public void setModify_Time(Date modify_Time) {
		this.modify_Time = modify_Time;
	}
	
	@Column(name = "Serial_NO")
	public String getSerialNO() {
		return serialNO;
	}
	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}
	
	@Column(name = "SwitchID")
	public Long getSwitchId() {
		return switchId;
	}
	public void setSwitchId(Long switchId) {
		this.switchId = switchId;
	}
	
	@Column(name = "SwitchIfIndex")
	public Long getIfIndex() {
		return ifIndex;
	}
	public void setIfIndex(Long ifIndex) {
		this.ifIndex = ifIndex;
	}
}
