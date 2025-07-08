/*
 * 文件名： ParamIpHost.java
 * 
 * 创建日期： 2014-4-29
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.fas.res.host.entity;

import java.util.Date;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision$
 *
 * @since 2014-4-29
 */
public class ParamIpHost {
	private String orgId;
	private Long ipHostId;
	private Long ipMacBindId;
	private Integer ipHostTypeId;
	private String hostName;
	private String ipAddr;
	private long ipValue;
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
	private String infoPointNo;
	private String vlanId;
	private Integer vlanNo;
	private Integer bindSatus;
	
	public Integer getBindSatus() {
		return bindSatus;
	}
	public void setBindSatus(Integer bindSatus) {
		this.bindSatus = bindSatus;
	}
	public Long getIpMacBindId() {
		return ipMacBindId;
	}
	public void setIpMacBindId(Long ipMacBindId) {
		this.ipMacBindId = ipMacBindId;
	}
	public Integer getVlanNo() {
		return vlanNo;
	}
	public void setVlanNo(Integer vlanNo) {
		this.vlanNo = vlanNo;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public Long getIpHostId() {
		return ipHostId;
	}
	public void setIpHostId(Long ipHostId) {
		this.ipHostId = ipHostId;
	}
	public Integer getIpHostTypeId() {
		return ipHostTypeId;
	}
	public void setIpHostTypeId(Integer ipHostTypeId) {
		this.ipHostTypeId = ipHostTypeId;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public long getIpValue() {
		return ipValue;
	}
	public void setIpValue(long ipValue) {
		this.ipValue = ipValue;
	}
	public String getIpNetMask() {
		return ipNetMask;
	}
	public void setIpNetMask(String ipNetMask) {
		this.ipNetMask = ipNetMask;
	}
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
	public Integer getOsType() {
		return osType;
	}
	public void setOsType(Integer osType) {
		this.osType = osType;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getSnmpRoString() {
		return snmpRoString;
	}
	public void setSnmpRoString(String snmpRoString) {
		this.snmpRoString = snmpRoString;
	}
	public String getAssertNO() {
		return assertNO;
	}
	public void setAssertNO(String assertNO) {
		this.assertNO = assertNO;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getModify_Time() {
		return modify_Time;
	}
	public void setModify_Time(Date modify_Time) {
		this.modify_Time = modify_Time;
	}
	public String getSerialNO() {
		return serialNO;
	}
	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}
	public Long getSwitchId() {
		return switchId;
	}
	public void setSwitchId(Long switchId) {
		this.switchId = switchId;
	}
	public Long getIfIndex() {
		return ifIndex;
	}
	public void setIfIndex(Long ifIndex) {
		this.ifIndex = ifIndex;
	}
	public String getInfoPointNo() {
		return infoPointNo;
	}
	public void setInfoPointNo(String infoPointNo) {
		this.infoPointNo = infoPointNo;
	}
	public String getVlanId() {
		return vlanId;
	}
	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
	}
	
}
