package com.sino.fas.res.net.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Ncm_DevInterfaces")
public class NcmDevInterfaces {
	
	private Long devId;
	private Long intfId;
	private Long ifIndex;
	private Integer ifType;
	private String ifName;
	private String ifAlias;
	private String ifDisplay;
	private String ifDescr;
	private Double ifSpeed;
	private String baudRate;
	private String ifPhysAddress;
	private Integer ifMTU;
	private Integer ifAdminStatus;
	private Integer ifOperStatus;
	private String ipAddr;
	private String netMask;
	private String netAddr;
	private Integer isTrunk;
	private Long updateTime;
	public Long getDevId() {
		return devId;
	}
	public void setDevId(Long devId) {
		this.devId = devId;
	}
	@Id
	public Long getIntfId() {
		return intfId;
	}
	public void setIntfId(Long intfId) {
		this.intfId = intfId;
	}
	public Long getIfIndex() {
		return ifIndex;
	}
	public void setIfIndex(Long ifIndex) {
		this.ifIndex = ifIndex;
	}
	public Integer getIfType() {
		return ifType;
	}
	public void setIfType(Integer ifType) {
		this.ifType = ifType;
	}
	public String getIfName() {
		return ifName;
	}
	public void setIfName(String ifName) {
		this.ifName = ifName;
	}
	public String getIfAlias() {
		return ifAlias;
	}
	public void setIfAlias(String ifAlias) {
		this.ifAlias = ifAlias;
	}
	public String getIfDisplay() {
		return ifDisplay;
	}
	public void setIfDisplay(String ifDisplay) {
		this.ifDisplay = ifDisplay;
	}
	public String getIfDescr() {
		return ifDescr;
	}
	public void setIfDescr(String ifDescr) {
		this.ifDescr = ifDescr;
	}
	public Double getIfSpeed() {
		return ifSpeed;
	}
	public void setIfSpeed(Double ifSpeed) {
		this.ifSpeed = ifSpeed;
	}
	public String getBaudRate() {
		return baudRate;
	}
	public void setBaudRate(String baudRate) {
		this.baudRate = baudRate;
	}
	public String getIfPhysAddress() {
		return ifPhysAddress;
	}
	public void setIfPhysAddress(String ifPhysAddress) {
		this.ifPhysAddress = ifPhysAddress;
	}
	public Integer getIfMTU() {
		return ifMTU;
	}
	public void setIfMTU(Integer ifMTU) {
		this.ifMTU = ifMTU;
	}
	public Integer getIfAdminStatus() {
		return ifAdminStatus;
	}
	public void setIfAdminStatus(Integer ifAdminStatus) {
		this.ifAdminStatus = ifAdminStatus;
	}
	public Integer getIfOperStatus() {
		return ifOperStatus;
	}
	public void setIfOperStatus(Integer ifOperStatus) {
		this.ifOperStatus = ifOperStatus;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getNetMask() {
		return netMask;
	}
	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}
	public String getNetAddr() {
		return netAddr;
	}
	public void setNetAddr(String netAddr) {
		this.netAddr = netAddr;
	}
	public Integer getIsTrunk() {
		return isTrunk;
	}
	public void setIsTrunk(Integer isTrunk) {
		this.isTrunk = isTrunk;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	
	

	

}
