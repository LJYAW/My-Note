/**
 * 
 */
package com.sino.fas.res.host.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.sql.Timestamp;

/**
 * @author Mr.LP
 * @date 2019-8-21上午9:14:29
 * @className DcmHosts
 *
 * @Description TODO
 *
 */

@Entity
@Table(name = "Dcm_Hosts",uniqueConstraints = @UniqueConstraint(columnNames = "hostID"))
public class DcmHosts {
	
	private String orgID;
	private String hostID;
	private String hostName;
	private String ipAddress;
	private Long ipLong;
	private String ipNetMask;
	private String macAddress;
	private Integer vendorID;
	private String vendorName;
	private Integer prodClassCode;
	private String prodClassName;
	private Integer prodTypeCode;
	private String prodTypeName;
	private Integer prodSeriesID;
	private String prodSeries;
	private String prodModel;
	private String modelOID;
	private String productNO;
	private String productDesc;
	private String productSN;
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
	private String osName;
	private String osVersion;
	private String osRelase;
	private String assetNO;
	private Integer powerStatus;
	private Integer hostStatus;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private String description;
	private String administrator;
	private Integer connStatus;
	
	
	public String getOrgID() {
		return orgID;
	}
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	
	@Id
	public String getHostID() {
		return hostID;
	}
	public void setHostID(String hostID) {
		this.hostID = hostID;
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
	public Integer getProdClassCode() {
		return prodClassCode;
	}
	public void setProdClassCode(Integer prodClassCode) {
		this.prodClassCode = prodClassCode;
	}
	public String getProdClassName() {
		return prodClassName;
	}
	public void setProdClassName(String prodClassName) {
		this.prodClassName = prodClassName;
	}
	public Integer getProdTypeCode() {
		return prodTypeCode;
	}
	public void setProdTypeCode(Integer prodTypeCode) {
		this.prodTypeCode = prodTypeCode;
	}
	public String getProdTypeName() {
		return prodTypeName;
	}
	public void setProdTypeName(String prodTypeName) {
		this.prodTypeName = prodTypeName;
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
	public Integer getCpuQty() {
		return cpuQty;
	}
	public void setCpuQty(Integer cpuQty) {
		this.cpuQty = cpuQty;
	}
	public String getCpuCoreQty() {
		return cpuCoreQty;
	}
	public void setCpuCoreQty(String cpuCoreQty) {
		this.cpuCoreQty = cpuCoreQty;
	}
	public Long getMemorySize() {
		return memorySize;
	}
	public void setMemorySize(Long memorySize) {
		this.memorySize = memorySize;
	}
	public String getMemoryDisplay() {
		return memoryDisplay;
	}
	public void setMemoryDisplay(String memoryDisplay) {
		this.memoryDisplay = memoryDisplay;
	}
	public Long getDiskSize() {
		return diskSize;
	}
	public void setDiskSize(Long diskSize) {
		this.diskSize = diskSize;
	}
	public String getDiskDisplay() {
		return diskDisplay;
	}
	public void setDiskDisplay(String diskDisplay) {
		this.diskDisplay = diskDisplay;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
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
	public String getAssetNO() {
		return assetNO;
	}
	public void setAssetNO(String assetNO) {
		this.assetNO = assetNO;
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
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
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
	public Integer getConnStatus() {
		return connStatus;
	}
	public void setConnStatus(Integer connStatus) {
		this.connStatus = connStatus;
	}
	
	
}
