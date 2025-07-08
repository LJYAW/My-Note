/**
 * 
 */
package com.sino.fas.res.host.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Mr.LP
 * @date 2019-8-21下午2:37:42
 * @className ApmHosts
 *
 * @Description TODO
 *
 */

@Entity
@Table(name = "Apm_Hosts",uniqueConstraints = @UniqueConstraint(columnNames = "hostId"))
public class ApmHosts {
	
//	`hostId` varchar(36) NOT NULL,
//	  `orgID` varchar(36) NOT NULL COMMENT '机构ID',
//	  `ipHostTypeId` int(10) DEFAULT NULL COMMENT '设备类型',
//	  `osName` varchar(32) DEFAULT NULL COMMENT '操作系统名称',
//	  `osClass` varchar(32) DEFAULT NULL COMMENT '操作系统分类',
//	  `osType` varchar(32) DEFAULT NULL COMMENT '操作系统类型',
//	  `osVersion` varchar(32) DEFAULT NULL COMMENT '操作系统版本',
//	  `osRelase` varchar(32) DEFAULT NULL COMMENT '版本详情(如: 12.2(44)SE)',
//	  `accessmode` varchar(32) DEFAULT NULL COMMENT '访问方式',
//	  `ipAddress` varchar(16) DEFAULT NULL COMMENT 'IP地址',
//	  `ipNetMask` varchar(15) DEFAULT NULL COMMENT '子网掩码',
//	  `username` varchar(32) DEFAULT NULL,
//	  `password` varchar(32) DEFAULT NULL,
	
	private String hostId;
	private String orgID;
	private Integer ipHostTypeId;
	private String osName;
	private String osClass;
	private String osType;
	private String osVersion;
	private String osRelase;
	private String accessmode;
	private String ipAddress;
	private String ipNetMask;
	private String username;
	private String password;
	
	private String cpuVendor;
	private String cpuModel;
	private Integer cpuWidth;
	private Integer cpuQty;
	private Integer cpuCoreQty;
	
	private Integer resClassCode;
	private String resClassName;
	private Integer resTypeCode;
	private String resTypeName;
	
	private Long memTotal;
	private Long memFree;
	
	@Id
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	public String getOrgID() {
		return orgID;
	}
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	public Integer getIpHostTypeId() {
		return ipHostTypeId;
	}
	public void setIpHostTypeId(Integer ipHostTypeId) {
		this.ipHostTypeId = ipHostTypeId;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
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
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getOsRelase() {
		return osRelase;
	}
	public void setOsRelase(String osRelase) {
		this.osRelase = osRelase;
	}
	public String getAccessmode() {
		return accessmode;
	}
	public void setAccessmode(String accessmode) {
		this.accessmode = accessmode;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getIpNetMask() {
		return ipNetMask;
	}
	public void setIpNetMask(String ipNetMask) {
		this.ipNetMask = ipNetMask;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCpuVendor() {
		return cpuVendor;
	}
	public void setCpuVendor(String cpuVendor) {
		this.cpuVendor = cpuVendor;
	}
	public String getCpuModel() {
		return cpuModel;
	}
	public void setCpuModel(String cpuModel) {
		this.cpuModel = cpuModel;
	}
	public Integer getCpuWidth() {
		return cpuWidth;
	}
	public void setCpuWidth(Integer cpuWidth) {
		this.cpuWidth = cpuWidth;
	}
	public Integer getCpuQty() {
		return cpuQty;
	}
	public void setCpuQty(Integer cpuQty) {
		this.cpuQty = cpuQty;
	}
	public Integer getCpuCoreQty() {
		return cpuCoreQty;
	}
	public void setCpuCoreQty(Integer cpuCoreQty) {
		this.cpuCoreQty = cpuCoreQty;
	}
	public Integer getResClassCode() {
		return resClassCode;
	}
	public void setResClassCode(Integer resClassCode) {
		this.resClassCode = resClassCode;
	}
	public Long getMemTotal() {
		return memTotal;
	}
	public void setMemTotal(Long memTotal) {
		this.memTotal = memTotal;
	}
	public Long getMemFree() {
		return memFree;
	}
	public void setMemFree(Long memFree) {
		this.memFree = memFree;
	}
	public String getResClassName() {
		return resClassName;
	}
	public void setResClassName(String resClassName) {
		this.resClassName = resClassName;
	}
	public Integer getResTypeCode() {
		return resTypeCode;
	}
	public void setResTypeCode(Integer resTypeCode) {
		this.resTypeCode = resTypeCode;
	}
	public String getResTypeName() {
		return resTypeName;
	}
	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}

	
}
