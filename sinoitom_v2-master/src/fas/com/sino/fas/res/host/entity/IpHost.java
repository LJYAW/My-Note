package com.sino.fas.res.host.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ncm_IpHost")
public class IpHost {
	private String orgId;
	private Long ipHostId;
	private Integer ipHostTypeId;
	private String hostName;
	private String ipAddr;
	private long ipValue;
	private String ipNetMask;
	private String macAddr;
	private Integer osClassCode;
	private String osClass;
	private String osType;
	private String osVersion;
	private String osFeature;
	private String snmpRoString;
	private String assertNO;
	private String userId;
	private Date modify_Time;
	private String serialNO;
	private Long switchId;
	private Long ifIndex;
	private String infoPointNo;
	
	private String devAccessId;
	
	
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
	@Column(name = "IpHost_ID", unique = true, nullable = false)
	public Long getIpHostId() {
		return ipHostId;
	}
	public void setIpHostId(Long ipHostId) {
		this.ipHostId = ipHostId;
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
	public long getIpValue() {
		return ipValue;
	}
	public void setIpValue(long ipValue) {
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
	
	public String getDevAccessId() {
		return devAccessId;
	}
	public void setDevAccessId(String devAccessId) {
		this.devAccessId = devAccessId;
	}
	
	public String getOsFeature() {
		return osFeature;
	}
	public void setOsFeature(String osFeature) {
		this.osFeature = osFeature;
	}
	public String getOsClass() {
		return osClass;
	}
	public void setOsClass(String osClass) {
		this.osClass = osClass;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public Integer getOsClassCode() {
		return osClassCode;
	}
	public void setOsClassCode(Integer osClassCode) {
		this.osClassCode = osClassCode;
	}
	
	
	
	
}
