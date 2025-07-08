package com.sino.fas.res.net.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * NcmSubNetwork entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Ncm_SubNetwork", uniqueConstraints = @UniqueConstraint(columnNames = "SubnetAddr"))
public class NcmSubNetwork implements java.io.Serializable {

	// Fields

	private Long netId;
	private String orgId;
	private String orgName;
	private String subnetAddr;
	private String subnetMask;
	private Long subnetSize;
	private Long hostIpStart;
	private Long hostIpEnd;
	private String subnetName;
	private String subnetDesc;
	private String vlanName;
	private Integer subnetType;
	private String subnetUsage;
	private Integer isCollection;
	private Integer adminStatus;
	private Integer operStatus;
	private Long taskId;
	private Long scanTime;
	private Long scanDetails;
	private String creator;
	private Long createTime;
	private String modifier;
	private Long modifyTime;

	public String netIpMask;
	
	// Constructors

	/** default constructor */
	public NcmSubNetwork() {
	}

	/** minimal constructor */
	public NcmSubNetwork(Long netId, String orgId, String orgName,
			String subnetAddr, String subnetMask, Long subnetSize,
			Long hostIpStart, Long hostIpEnd, Integer subnetType,
			Integer isCollection, Integer adminStatus, Integer operStatus,
			Long createTime) {
		this.netId = netId;
		this.orgId = orgId;
		this.orgName = orgName;
		this.subnetAddr = subnetAddr;
		this.subnetMask = subnetMask;
		this.subnetSize = subnetSize;
		this.hostIpStart = hostIpStart;
		this.hostIpEnd = hostIpEnd;
		this.subnetType = subnetType;
		this.isCollection = isCollection;
		this.adminStatus = adminStatus;
		this.operStatus = operStatus;
		this.createTime = createTime;
	}

	/** full constructor */
	public NcmSubNetwork(Long netId, String orgId, String orgName,
			String subnetAddr, String subnetMask, Long subnetSize,
			Long hostIpStart, Long hostIpEnd, String subnetName,
			String subnetDesc, String vlanName, Integer subnetType,
			String subnetUsage, Integer isCollection, Integer adminStatus,
			Integer operStatus, Long taskId, Long scanTime, Long scanDetails,
			String creator, Long createTime, String modifier, Long modifyTime) {
		this.netId = netId;
		this.orgId = orgId;
		this.orgName = orgName;
		this.subnetAddr = subnetAddr;
		this.subnetMask = subnetMask;
		this.subnetSize = subnetSize;
		this.hostIpStart = hostIpStart;
		this.hostIpEnd = hostIpEnd;
		this.subnetName = subnetName;
		this.subnetDesc = subnetDesc;
		this.vlanName = vlanName;
		this.subnetType = subnetType;
		this.subnetUsage = subnetUsage;
		this.isCollection = isCollection;
		this.adminStatus = adminStatus;
		this.operStatus = operStatus;
		this.taskId = taskId;
		this.scanTime = scanTime;
		this.scanDetails = scanDetails;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
	}
	

	// Property accessors
	@Id
	@Column(name = "Net_ID", unique = true, nullable = false)
	public Long getNetId() {
		return this.netId;
	}

	public void setNetId(Long netId) {
		this.netId = netId;
	}

	@Column(name = "Org_ID", nullable = false, length = 36)
	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(name = "OrgName", nullable = false, length = 64)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "SubnetAddr", unique = true, nullable = false, length = 15)
	public String getSubnetAddr() {
		return this.subnetAddr;
	}

	public void setSubnetAddr(String subnetAddr) {
		this.subnetAddr = subnetAddr;
	}

	@Column(name = "SubnetMask", nullable = false, length = 15)
	public String getSubnetMask() {
		return this.subnetMask;
	}

	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}

	@Column(name = "Subnet_Size", nullable = false)
	public Long getSubnetSize() {
		return this.subnetSize;
	}

	public void setSubnetSize(Long subnetSize) {
		this.subnetSize = subnetSize;
	}

	@Column(name = "HostIpStart", nullable = false)
	public Long getHostIpStart() {
		return this.hostIpStart;
	}

	public void setHostIpStart(Long hostIpStart) {
		this.hostIpStart = hostIpStart;
	}

	@Column(name = "HostIpEnd", nullable = false)
	public Long getHostIpEnd() {
		return this.hostIpEnd;
	}

	public void setHostIpEnd(Long hostIpEnd) {
		this.hostIpEnd = hostIpEnd;
	}

	@Column(name = "Subnet_Name", length = 64)
	public String getSubnetName() {
		return this.subnetName;
	}

	public void setSubnetName(String subnetName) {
		this.subnetName = subnetName;
	}

	@Column(name = "Subnet_Desc", length = 128)
	public String getSubnetDesc() {
		return this.subnetDesc;
	}

	public void setSubnetDesc(String subnetDesc) {
		this.subnetDesc = subnetDesc;
	}

	@Column(name = "Vlan_Name", length = 64)
	public String getVlanName() {
		return this.vlanName;
	}

	public void setVlanName(String vlanName) {
		this.vlanName = vlanName;
	}

	@Column(name = "Subnet_Type", nullable = false)
	public Integer getSubnetType() {
		return this.subnetType;
	}

	public void setSubnetType(Integer subnetType) {
		this.subnetType = subnetType;
	}

	@Column(name = "Subnet_Usage", length = 128)
	public String getSubnetUsage() {
		return this.subnetUsage;
	}

	public void setSubnetUsage(String subnetUsage) {
		this.subnetUsage = subnetUsage;
	}

	@Column(name = "Is_Collection", nullable = false)
	public Integer getIsCollection() {
		return this.isCollection;
	}

	public void setIsCollection(Integer isCollection) {
		this.isCollection = isCollection;
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

	@Column(name = "Task_ID")
	public Long getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	@Column(name = "Scan_Time")
	public Long getScanTime() {
		return this.scanTime;
	}

	public void setScanTime(Long scanTime) {
		this.scanTime = scanTime;
	}

	@Column(name = "Scan_Details")
	public Long getScanDetails() {
		return this.scanDetails;
	}

	public void setScanDetails(Long scanDetails) {
		this.scanDetails = scanDetails;
	}

	@Column(name = "Creator", length = 32)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "Create_Time", nullable = false)
	public Long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	@Column(name = "Modifier", length = 32)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "Modify_Time")
	public Long getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

}