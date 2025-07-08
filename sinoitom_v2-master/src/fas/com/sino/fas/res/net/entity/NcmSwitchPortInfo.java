package com.sino.fas.res.net.entity;

import java.util.ArrayList;
import java.util.List;

import com.sino.fas.res.cablingInfo.entity.NcmCablingInfo;
import com.sino.fas.res.host.entity.IpHost;
import com.sino.fas.res.host.entity.VlanInfo;

public class NcmSwitchPortInfo {
	
	private NcmSwitchPortId id;
	private String switchName;
	private String switchIp;
	private String loginName;
	private String serialNO;
	private Integer ipHostTypeId;
	private Long vlanId;
	private String vlanName;
	private Long vlan;
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
	private String infoPointNo;
	private List<IpMacMap> ipMacMap=new ArrayList<IpMacMap>();
	
	
	public NcmSwitchPortInfo() {
		super();
	}
	public NcmSwitchPortInfo(NcmSwitchPortId id, Long switchId, Long ifIndex,
			String ifName, Integer portNo, String availablity,
			Integer multiMacPort, String location, String ifAliasName,
			String adminStatus, String operStatus, Long lastChangeTime,
			Long portStatusChangeTime, String macAddr, String ipAddr) {
		super();
		this.id = id;
		this.switchId = switchId;
		this.ifIndex = ifIndex;
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
		this.macAddr = macAddr;
		this.ipAddr = ipAddr;
	}
	
	public NcmSwitchPortInfo(NcmSwitchPort ncmSwitchPort,String availablity,String adminStatus,String operStatus,List<IpMacMap> ipMacMap) {
		super();
		this.id = ncmSwitchPort.getId();
		this.switchId = ncmSwitchPort.getId().getSwitchId();
		this.ifIndex = ncmSwitchPort.getId().getIfIndex();
		this.ifName = ncmSwitchPort.getIfName();
		this.portNo = ncmSwitchPort.getPortNo();
		this.availablity = availablity;
		this.multiMacPort = ncmSwitchPort.getMultiMacPort();
		this.location = ncmSwitchPort.getLocation();
		this.ifAliasName = ncmSwitchPort.getIfAliasName();
		this.adminStatus = adminStatus;
		this.operStatus = operStatus;
		this.lastChangeTime = ncmSwitchPort.getLastChangeTime();
		this.portStatusChangeTime = ncmSwitchPort.getPortStatusChangeTime();
		this.ipMacMap=ipMacMap;
		this.isoStatus=ncmSwitchPort.getAdminStatus();
	}
	
	public NcmSwitchPortInfo(NcmDevices ncmSwitch,NcmSwitchPort ncmSwitchPort,VlanInfo vlaninfo,NcmCablingInfo cabinfo,IpHost ipHost) {
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
		this.hostId=String.valueOf(ipHost.getIpHostId());
		this.hostMac=ipHost.getMacAddr();
		this.hostIP=ipHost.getIpAddr();
		this.serialNO=ipHost.getSerialNO();
		this.ipHostTypeId=ipHost.getIpHostTypeId();
		this.infoPointNo=cabinfo!=null&&cabinfo.getInfoPointNo()!=null?cabinfo.getInfoPointNo():"";
		this.vlanId=vlaninfo!=null&&vlaninfo.getVlan_Id()!=null?vlaninfo.getVlan_Id():null;
		this.vlan=vlaninfo!=null&&vlaninfo.getVlan()!=null?vlaninfo.getVlan():null;
		this.vlanName=vlaninfo!=null&&vlaninfo.getVlan_Name()!=null?vlaninfo.getVlan_Name():"";
	}
	
	public NcmSwitchPortInfo(NcmDevices ncmSwitch,NcmSwitchPort ncmSwitchPort,VlanInfo vlaninfo,NcmCablingInfo cabinfo) {
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
		this.infoPointNo=cabinfo!=null&&cabinfo.getInfoPointNo()!=null?cabinfo.getInfoPointNo():"";
		this.vlanId=vlaninfo!=null&&vlaninfo.getVlan_Id()!=null?vlaninfo.getVlan_Id():null;
		this.vlan=vlaninfo!=null&&vlaninfo.getVlan()!=null?vlaninfo.getVlan():null;
		this.vlanName=vlaninfo!=null&&vlaninfo.getVlan_Name()!=null?vlaninfo.getVlan_Name():"";
	}
	
	public String getInfoPointNo() {
		return infoPointNo;
	}
	public void setInfoPointNo(String infoPointNo) {
		this.infoPointNo = infoPointNo;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	public Long getVlan() {
		return vlan;
	}
	public void setVlan(Long vlan) {
		this.vlan = vlan;
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
	public Long getVlanId() {
		return vlanId;
	}
	public void setVlanId(Long vlanId) {
		this.vlanId = vlanId;
	}
	public String getVlanName() {
		return vlanName;
	}
	public void setVlanName(String vlanName) {
		this.vlanName = vlanName;
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
