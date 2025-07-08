package com.sino.fas.res.net.entity;

public class ParamSubnetwork {

	
	private Long parentId;
	private Long netId;
	private String subnetName;
	private String subnetAddr;
	private String subnetMask;
	private Integer subnetType;
	private Integer adminStatus;
	private Integer operStatus;
	private String orgId;
	private String orgName;
	
	public Integer getSubnetType() {
		return subnetType;
	}
	public void setSubnetType(Integer subnetType) {
		this.subnetType = subnetType;
	}
	public Integer getAdminStatus() {
		return adminStatus;
	}
	public void setAdminStatus(Integer adminStatus) {
		this.adminStatus = adminStatus;
	}
	public Integer getOperStatus() {
		return operStatus;
	}
	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	private String subnetUsage;
	
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
	public Long getNetId() {
		return netId;
	}
	public void setNetId(Long netId) {
		this.netId = netId;
	}
	public String getSubnetName() {
		return subnetName;
	}
	public void setSubnetName(String subnetName) {
		this.subnetName = subnetName;
	}
	public String getSubnetAddr() {
		return subnetAddr;
	}
	public void setSubnetAddr(String subnetAddr) {
		this.subnetAddr = subnetAddr;
	}
	public String getSubnetMask() {
		return subnetMask;
	}
	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}
	
	
	public String getSubnetUsage() {
		return subnetUsage;
	}
	public void setSubnetUsage(String subnetUsage) {
		this.subnetUsage = subnetUsage;
	}
	
	
}
