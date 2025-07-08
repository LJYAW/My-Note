/*
 * 文件名： IpMacBindInfo.java
 * 
 * 创建日期： 2014-5-4
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.fas.res.dailyMaintain.netInfoManage.entity;

import java.util.ArrayList;
import java.util.List;

import com.sino.fas.res.cablingInfo.entity.NcmCablingInfo;
import com.sino.fas.res.net.entity.IpMacMap;
import com.sino.fas.res.net.entity.NcmDevices;
import com.sino.fas.res.net.entity.NcmSwitchPort;
import com.sino.fas.res.net.entity.NcmSwitchPortId;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision$
 *
 * @since 2014-5-4
 */
public class IpMacBindInfo {
	private NcmSwitchPortId id;
	private String switchName;
	private String switchIp;
	private String loginName;
	private String serialNO;
	private Integer ipHostTypeId;
	private Integer vlanNo;
	private Integer iadminStatus;
	private Integer ioperStatus;
	private String hostId;
	private String hostIP;
	private String hostMac;
	
	private Long switchId;
	private Long ifIndex;
	private String ifName;
	private Integer portNo;
	private String availablity;
	private Integer multiMacPort;
	private String location;
	private String ifAliasName;
	private String adminStatus;
	private String operStatus;
	
	private Long lastChangeTime;
	private Long portStatusChangeTime;
	private String macAddr;
	private String ipAddr;
	private Integer isoStatus;
	private Integer bindStatus;
	private String infoPointNo;
	private List<IpMacMap> ipMacMap=new ArrayList<IpMacMap>();
	
	
	public IpMacBindInfo() {
		super();
	}
	
	public IpMacBindInfo(NcmDevices ncmSwitch,NcmSwitchPort ncmSwitchPort,NcmCablingInfo cabinfo,IpMacBind ipHost) {
		super();
		this.id = ncmSwitchPort.getId();
		this.switchId = ncmSwitchPort.getId().getSwitchId();
		this.ifIndex = ncmSwitchPort.getId().getIfIndex();
		this.ifName = ncmSwitchPort.getIfName();
		this.portNo = ncmSwitchPort.getPortNo();
		this.multiMacPort = ncmSwitchPort.getMultiMacPort();
		this.location = ncmSwitchPort.getLocation();
		this.ifAliasName = ncmSwitchPort.getIfAliasName();
		this.iadminStatus = ncmSwitchPort.getAdminStatus();
		this.ioperStatus = ncmSwitchPort.getOperStatus();
		this.lastChangeTime = ncmSwitchPort.getLastChangeTime();
		this.portStatusChangeTime = ncmSwitchPort.getPortStatusChangeTime();
		this.isoStatus=ncmSwitchPort.getAdminStatus();
		
		this.switchIp=ncmSwitch.getDevIpAddr();
		this.switchName=ncmSwitch.getDevName();
		this.loginName="";
		this.hostId=String.valueOf(ipHost.getIpMacBindId());
		this.hostMac=ipHost.getMacAddr();
		this.hostIP=ipHost.getIpAddr();
		this.serialNO=ipHost.getSerialNO();
		this.ipHostTypeId=ipHost.getIpHostTypeId();
		this.vlanNo=ipHost.getVlanNo();
		this.bindStatus=ipHost.getBindStatus();
		this.infoPointNo=cabinfo!=null&&cabinfo.getInfoPointNo()!=null?cabinfo.getInfoPointNo():"";
		//this.vlan=vlaninfo!=null&&vlaninfo.getVlan()!=null?vlaninfo.getVlan():null;
		//this.vlanName=vlaninfo!=null&&vlaninfo.getVlan_Name()!=null?vlaninfo.getVlan_Name():"";
	}
	
	public IpMacBindInfo(NcmDevices ncmSwitch,NcmSwitchPort ncmSwitchPort,NcmCablingInfo cabinfo) {
		super();
		this.id = ncmSwitchPort.getId();
		this.switchId = ncmSwitchPort.getId().getSwitchId();
		this.ifIndex = ncmSwitchPort.getId().getIfIndex();
		this.ifName = ncmSwitchPort.getIfName();
		this.portNo = ncmSwitchPort.getPortNo();
		this.multiMacPort = ncmSwitchPort.getMultiMacPort();
		this.location = ncmSwitchPort.getLocation();
		this.ifAliasName = ncmSwitchPort.getIfAliasName();
		this.iadminStatus = ncmSwitchPort.getAdminStatus();
		this.ioperStatus = ncmSwitchPort.getOperStatus();
		this.lastChangeTime = ncmSwitchPort.getLastChangeTime();
		this.portStatusChangeTime = ncmSwitchPort.getPortStatusChangeTime();
		this.isoStatus=ncmSwitchPort.getAdminStatus();
		
		this.switchIp=ncmSwitch.getDevIpAddr();
		this.switchName=ncmSwitch.getDevName();
		this.loginName="";
		this.hostId="";
		this.hostIP="";
		this.hostMac="";
		this.serialNO=null;
		this.ipHostTypeId=null;
		this.vlanNo=null;
		this.infoPointNo=cabinfo!=null&&cabinfo.getInfoPointNo()!=null?cabinfo.getInfoPointNo():"";
		//this.vlan=vlaninfo!=null&&vlaninfo.getVlan()!=null?vlaninfo.getVlan():null;
	}
	
	public String getInfoPointNo() {
		return infoPointNo;
	}

	public void setInfoPointNo(String infoPointNo) {
		this.infoPointNo = infoPointNo;
	}
	public Integer getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
	public Integer getVlanNo() {
		return vlanNo;
	}

	public void setVlanNo(Integer vlanNo) {
		this.vlanNo = vlanNo;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	public String getHostMac() {
		return hostMac;
	}
	public void setHostMac(String hostMac) {
		this.hostMac = hostMac;
	}
	public String getHostIP() {
		return hostIP;
	}
	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}
	public String getSwitchIp() {
		return switchIp;
	}
	public void setSwitchIp(String switchIp) {
		this.switchIp = switchIp;
	}
	public Integer getIadminStatus() {
		return iadminStatus;
	}
	public void setIadminStatus(Integer iadminStatus) {
		this.iadminStatus = iadminStatus;
	}
	public Integer getIoperStatus() {
		return ioperStatus;
	}
	public void setIoperStatus(Integer ioperStatus) {
		this.ioperStatus = ioperStatus;
	}
	public String getSwitchName() {
		return switchName;
	}
	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getSerialNO() {
		return serialNO;
	}
	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}
	public Integer getIpHostTypeId() {
		return ipHostTypeId;
	}
	public void setIpHostTypeId(Integer ipHostTypeId) {
		this.ipHostTypeId = ipHostTypeId;
	}
	public List<IpMacMap> getIpMacMap() {
		return ipMacMap;
	}
	public void setIpMacMap(List<IpMacMap> ipMacMap) {
		this.ipMacMap = ipMacMap;
	}
	public Integer getIsoStatus() {
		return isoStatus;
	}
	public void setIsoStatus(Integer isoStatus) {
		this.isoStatus = isoStatus;
	}
	public String getAvailablity() {
		return availablity;
	}
	public void setAvailablity(String availablity) {
		this.availablity = availablity;
	}
	public String getAdminStatus() {
		return adminStatus;
	}
	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}
	public String getOperStatus() {
		return operStatus;
	}
	public void setOperStatus(String operStatus) {
		this.operStatus = operStatus;
	}

	public NcmSwitchPortId getId() {
		return id;
	}
	public void setId(NcmSwitchPortId id) {
		this.id = id;
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
	public String getIfName() {
		return ifName;
	}
	public void setIfName(String ifName) {
		this.ifName = ifName;
	}
	public Integer getPortNo() {
		return portNo;
	}
	public void setPortNo(Integer portNo) {
		this.portNo = portNo;
	}
	public Integer getMultiMacPort() {
		return multiMacPort;
	}
	public void setMultiMacPort(Integer multiMacPort) {
		this.multiMacPort = multiMacPort;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getIfAliasName() {
		return ifAliasName;
	}
	public void setIfAliasName(String ifAliasName) {
		this.ifAliasName = ifAliasName;
	}
	public Long getLastChangeTime() {
		return lastChangeTime;
	}
	public void setLastChangeTime(Long lastChangeTime) {
		this.lastChangeTime = lastChangeTime;
	}
	public Long getPortStatusChangeTime() {
		return portStatusChangeTime;
	}
	public void setPortStatusChangeTime(Long portStatusChangeTime) {
		this.portStatusChangeTime = portStatusChangeTime;
	}
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
}
