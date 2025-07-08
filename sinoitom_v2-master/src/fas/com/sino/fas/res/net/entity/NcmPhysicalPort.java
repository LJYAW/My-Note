package com.sino.fas.res.net.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * NcmPhysicalPort entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Ncm_PhysicalPort")
public class NcmPhysicalPort implements java.io.Serializable {

	// Fields

	private Long portId;
	private Long deviceId;
	private String interfaceName;
	private String interfaceDisplay;
	private String ifName;
	private Long ifIndex;
	private Long ifIndexSnmp;
	private Long ifSpeed;
	private String baudRate;
	private String ifPhysAddress;
	private String ifAlias;
	private String ifIpAddr;
	private String ifMask;
	private String ifClasses;
	private Integer ifMtu;
	private Integer isFilter;
	private Integer isFlow;
	private Integer sampleRatio;
	private Integer adminStatus;
	private Integer operStatus;
	private String creator;
	private Long createTime;
	private String modifier;
	private Long modifyTime;

	// Constructors

	/** default constructor */
	public NcmPhysicalPort() {
	}

	/** minimal constructor */
	public NcmPhysicalPort(Long portId, Long deviceId, String interfaceName,
			String interfaceDisplay, Long ifIndex, Long ifIndexSnmp,
			Long ifSpeed, String baudRate, Integer isFilter, Integer isFlow,
			Integer sampleRatio, Integer adminStatus, Integer operStatus,
			Long createTime) {
		this.portId = portId;
		this.deviceId = deviceId;
		this.interfaceName = interfaceName;
		this.interfaceDisplay = interfaceDisplay;
		this.ifIndex = ifIndex;
		this.ifIndexSnmp = ifIndexSnmp;
		this.ifSpeed = ifSpeed;
		this.baudRate = baudRate;
		this.isFilter = isFilter;
		this.isFlow = isFlow;
		this.sampleRatio = sampleRatio;
		this.adminStatus = adminStatus;
		this.operStatus = operStatus;
		this.createTime = createTime;
	}

	/** full constructor */
	public NcmPhysicalPort(Long portId, Long deviceId, String interfaceName,
			String interfaceDisplay, String ifName, Long ifIndex,
			Long ifIndexSnmp, Long ifSpeed, String baudRate,
			String ifPhysAddress, String ifAlias, String ifIpAddr,
			String ifMask, String ifClasses, Integer ifMtu, Integer isFilter,
			Integer isFlow, Integer sampleRatio, Integer adminStatus,
			Integer operStatus, String creator, Long createTime,
			String modifier, Long modifyTime) {
		this.portId = portId;
		this.deviceId = deviceId;
		this.interfaceName = interfaceName;
		this.interfaceDisplay = interfaceDisplay;
		this.ifName = ifName;
		this.ifIndex = ifIndex;
		this.ifIndexSnmp = ifIndexSnmp;
		this.ifSpeed = ifSpeed;
		this.baudRate = baudRate;
		this.ifPhysAddress = ifPhysAddress;
		this.ifAlias = ifAlias;
		this.ifIpAddr = ifIpAddr;
		this.ifMask = ifMask;
		this.ifClasses = ifClasses;
		this.ifMtu = ifMtu;
		this.isFilter = isFilter;
		this.isFlow = isFlow;
		this.sampleRatio = sampleRatio;
		this.adminStatus = adminStatus;
		this.operStatus = operStatus;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
	}

	// Property accessors
	@Id
	@Column(name = "Port_ID", unique = true, nullable = false)
	public Long getPortId() {
		return this.portId;
	}

	public void setPortId(Long portId) {
		this.portId = portId;
	}

	@Column(name = "Device_ID", nullable = false)
	public Long getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "Interface_Name", nullable = false, length = 128)
	public String getInterfaceName() {
		return this.interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	@Column(name = "Interface_Display", nullable = false, length = 128)
	public String getInterfaceDisplay() {
		return this.interfaceDisplay;
	}

	public void setInterfaceDisplay(String interfaceDisplay) {
		this.interfaceDisplay = interfaceDisplay;
	}

	@Column(name = "If_Name", length = 128)
	public String getIfName() {
		return this.ifName;
	}

	public void setIfName(String ifName) {
		this.ifName = ifName;
	}

	@Column(name = "If_Index", nullable = false)
	public Long getIfIndex() {
		return this.ifIndex;
	}

	public void setIfIndex(Long ifIndex) {
		this.ifIndex = ifIndex;
	}

	@Column(name = "If_IndexSnmp", nullable = false)
	public Long getIfIndexSnmp() {
		return this.ifIndexSnmp;
	}

	public void setIfIndexSnmp(Long ifIndexSnmp) {
		this.ifIndexSnmp = ifIndexSnmp;
	}

	@Column(name = "If_Speed", nullable = false)
	public Long getIfSpeed() {
		return this.ifSpeed;
	}

	public void setIfSpeed(Long ifSpeed) {
		this.ifSpeed = ifSpeed;
	}

	@Column(name = "BaudRate", nullable = false, length = 32)
	public String getBaudRate() {
		return this.baudRate;
	}

	public void setBaudRate(String baudRate) {
		this.baudRate = baudRate;
	}

	@Column(name = "If_Phys_Address", length = 128)
	public String getIfPhysAddress() {
		return this.ifPhysAddress;
	}

	public void setIfPhysAddress(String ifPhysAddress) {
		this.ifPhysAddress = ifPhysAddress;
	}

	@Column(name = "If_Alias", length = 128)
	public String getIfAlias() {
		return this.ifAlias;
	}

	public void setIfAlias(String ifAlias) {
		this.ifAlias = ifAlias;
	}

	@Column(name = "If_IpAddr", length = 16)
	public String getIfIpAddr() {
		return this.ifIpAddr;
	}

	public void setIfIpAddr(String ifIpAddr) {
		this.ifIpAddr = ifIpAddr;
	}

	@Column(name = "If_Mask", length = 16)
	public String getIfMask() {
		return this.ifMask;
	}

	public void setIfMask(String ifMask) {
		this.ifMask = ifMask;
	}

	@Column(name = "If_Classes", length = 128)
	public String getIfClasses() {
		return this.ifClasses;
	}

	public void setIfClasses(String ifClasses) {
		this.ifClasses = ifClasses;
	}

	@Column(name = "If_MTU")
	public Integer getIfMtu() {
		return this.ifMtu;
	}

	public void setIfMtu(Integer ifMtu) {
		this.ifMtu = ifMtu;
	}

	@Column(name = "Is_Filter", nullable = false)
	public Integer getIsFilter() {
		return this.isFilter;
	}

	public void setIsFilter(Integer isFilter) {
		this.isFilter = isFilter;
	}

	@Column(name = "Is_Flow", nullable = false)
	public Integer getIsFlow() {
		return this.isFlow;
	}

	public void setIsFlow(Integer isFlow) {
		this.isFlow = isFlow;
	}

	@Column(name = "Sample_Ratio", nullable = false)
	public Integer getSampleRatio() {
		return this.sampleRatio;
	}

	public void setSampleRatio(Integer sampleRatio) {
		this.sampleRatio = sampleRatio;
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