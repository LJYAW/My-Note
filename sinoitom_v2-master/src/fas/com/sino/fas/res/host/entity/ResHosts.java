/**
 * 
 */
package com.sino.fas.res.host.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Mr.LP
 * @date 2019-8-22上午9:39:22
 * @className ResHosts
 *
 * @Description TODO
 *
 */

@Entity
@Table(name = "Res_Hosts")
public class ResHosts {
	
	private String orgID;
	private Long hostId;
	private Integer resClassCode;
	private String resClassName;
	private Integer resTypeCode;
	private String resTypeName;
	private String hostName;
	private String ipAddress;
	private Long ipLong;
	private String ipNetMask;
	private String macAddress;
	private String assetNO;
	
	private Integer vendorID;
	private String vendorName;
	private String brand;
	private Integer prodSeriesID;
	private String prodSeries;
	private String prodModel;
	private String modelOID;
	private String productNO;
	private String productDesc;
	private String productSN;
	private String osName;
	private String osClass;
	private String osType;
	private String osVersion;
	private String osVersionType;
	private String osRelease;
	
	private String osFeature;
	private String cpuStruct;
	private String cpuVendor;
	private String cpuModel;
	private String cpuDesc;
	private String cpuWidth;
	private Integer cpuQty;
	private String cpuCoreQty;
	private Long memorySize;
	private String memoryDisplay;
	private Long diskSize;
	private String diskDisplay;
	private Integer powerStatus;
	private Integer hostStatus;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private String description;
	private String administrator;
	private Integer connStatus;
	
	private String devAcsUserId;
	
	private String accessmode;
	private Integer accessPort;
	
	private String osStruct;
	
	private Integer hostType;	//主机类型
	private Long homedHostId;	//宿主机ＩＤ
	private String homedHostIp;	//宿主机IP
	
	private String purpose;	//用途

	private String virtualizeSoft;
	private String vmName;

	
	public String getOrgID() {
		return orgID;
	}
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	
	@Id
	public Long getHostId() {
		return hostId;
	}
	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}
	public Integer getResClassCode() {
		return resClassCode;
	}
	public void setResClassCode(Integer resClassCode) {
		this.resClassCode = resClassCode;
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
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Long getIpLong() {
		return ipLong;
	}
	public void setIpLong(Long ipLong) {
		this.ipLong = ipLong;
	}
	public String getIpNetMask() {
		return ipNetMask;
	}
	public void setIpNetMask(String ipNetMask) {
		this.ipNetMask = ipNetMask;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getAssetNO() {
		return assetNO;
	}
	public void setAssetNO(String assetNO) {
		this.assetNO = assetNO;
	}
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Integer getProdSeriesID() {
		return prodSeriesID;
	}
	public void setProdSeriesID(Integer prodSeriesID) {
		this.prodSeriesID = prodSeriesID;
	}
	public String getProdSeries() {
		return prodSeries;
	}
	public void setProdSeries(String prodSeries) {
		this.prodSeries = prodSeries;
	}
	public String getProdModel() {
		return prodModel;
	}
	public void setProdModel(String prodModel) {
		this.prodModel = prodModel;
	}
	public String getModelOID() {
		return modelOID;
	}
	public void setModelOID(String modelOID) {
		this.modelOID = modelOID;
	}
	public String getProductNO() {
		return productNO;
	}
	public void setProductNO(String productNO) {
		this.productNO = productNO;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getProductSN() {
		return productSN;
	}
	public void setProductSN(String productSN) {
		this.productSN = productSN;
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
	
	public String getOsRelease() {
		return osRelease;
	}
	public void setOsRelease(String osRelease) {
		this.osRelease = osRelease;
	}
	public String getOsFeature() {
		return osFeature;
	}
	public void setOsFeature(String osFeature) {
		this.osFeature = osFeature;
	}
	public String getCpuStruct() {
		return cpuStruct;
	}
	public void setCpuStruct(String cpuStruct) {
		this.cpuStruct = cpuStruct;
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
	public String getCpuDesc() {
		return cpuDesc;
	}
	public void setCpuDesc(String cpuDesc) {
		this.cpuDesc = cpuDesc;
	}
	public String getCpuWidth() {
		return cpuWidth;
	}
	public void setCpuWidth(String cpuWidth) {
		this.cpuWidth = cpuWidth;
	}
	public String getCpuCoreQty() {
		return cpuCoreQty;
	}
	public void setCpuCoreQty(String cpuCoreQty) {
		this.cpuCoreQty = cpuCoreQty;
	}
	public String getMemoryDisplay() {
		return memoryDisplay;
	}
	public void setMemoryDisplay(String memoryDisplay) {
		this.memoryDisplay = memoryDisplay;
	}
	public String getDiskDisplay() {
		return diskDisplay;
	}
	public void setDiskDisplay(String diskDisplay) {
		this.diskDisplay = diskDisplay;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAdministrator() {
		return administrator;
	}
	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}
	public Integer getCpuQty() {
		return cpuQty;
	}
	public void setCpuQty(Integer cpuQty) {
		this.cpuQty = cpuQty;
	}
	public Long getMemorySize() {
		return memorySize;
	}
	public void setMemorySize(Long memorySize) {
		this.memorySize = memorySize;
	}
	public Long getDiskSize() {
		return diskSize;
	}
	public void setDiskSize(Long diskSize) {
		this.diskSize = diskSize;
	}
	public Integer getPowerStatus() {
		return powerStatus;
	}
	public void setPowerStatus(Integer powerStatus) {
		this.powerStatus = powerStatus;
	}
	public Integer getHostStatus() {
		return hostStatus;
	}
	public void setHostStatus(Integer hostStatus) {
		this.hostStatus = hostStatus;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getConnStatus() {
		return connStatus;
	}
	public void setConnStatus(Integer connStatus) {
		this.connStatus = connStatus;
	}
	public String getAccessmode() {
		return accessmode;
	}
	public void setAccessmode(String accessmode) {
		this.accessmode = accessmode;
	}
	public String getDevAcsUserId() {
		return devAcsUserId;
	}
	public void setDevAcsUserId(String devAcsUserId) {
		this.devAcsUserId = devAcsUserId;
	}
	public String getOsVersionType() {
		return osVersionType;
	}
	public void setOsVersionType(String osVersionType) {
		this.osVersionType = osVersionType;
	}
	public String getOsStruct() {
		return osStruct;
	}
	public void setOsStruct(String osStruct) {
		this.osStruct = osStruct;
	}
	public Integer getHostType() {
		return hostType;
	}
	public void setHostType(Integer hostType) {
		this.hostType = hostType;
	}
	public Long getHomedHostId() {
		return homedHostId;
	}
	public void setHomedHostId(Long homedHostId) {
		this.homedHostId = homedHostId;
	}
	public String getHomedHostIp() {
		return homedHostIp;
	}
	public void setHomedHostIp(String homedHostIp) {
		this.homedHostIp = homedHostIp;
	}
	public Integer getAccessPort() {
		return accessPort;
	}
	public void setAccessPort(Integer accessPort) {
		this.accessPort = accessPort;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getVirtualizeSoft() {
		return virtualizeSoft;
	}

	public void setVirtualizeSoft(String virtualizeSoft) {
		this.virtualizeSoft = virtualizeSoft;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
}
