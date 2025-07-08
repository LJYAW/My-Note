package com.sino.fas.res.cablingInfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ncm_CablingInfo")
public class NcmCablingInfo {
	
	private String orgId;
	private String cablingInfoID;          //varchar     (36) NOT NULL布线信息ID
	private String infoPointNo;          //varchar     (16) NOT NULL信息点编号
	private String buildingNo;          //varchar     (16) NULL楼号
	private String roomNo;          //varchar     (16) NULL房间号
	private String mediaType;          //varchar     (16) NULL介质类型
	private String cableType;          //varchar     (16) NULL线缆类型
	private Long bandWidth;          //bigint(19) NULL带宽
	private String baudRate;          //varchar     (16) NULL波特率
	private Integer length;          //int(2) NULL长度(米)
	private String cabinetNo;          //varchar     (16) NULL机架号
	private String moduleNo;          //int(1) NULL模块号
	private String modulePort;          //int(1) NULL模块端口号
	
	private Long switchID;          //bigint(19) NULL交换机ID
	private String switchIP;          //varchar     (15) NULL交换机IP
	private Long ifIndex;         //bigint(19) NULL交换机IfIndex
	private String ifName;          //varchar     (64) NULL交换机接口
	private Integer verifyFlag;        //int(1) NULL验证标志(0:未验证;1:已验证)
	
	private String remark;          //varchar     (64) NULL备
	
	@Column(name = "SwitchID")
	public Long getSwitchID() {
		return switchID;
	}
	public void setSwitchID(Long switchID) {
		this.switchID = switchID;
	}
	
	@Column(name = "SwitchIP")
	public String getSwitchIP() {
		return switchIP;
	}
	public void setSwitchIP(String switchIP) {
		this.switchIP = switchIP;
	}
	
	@Column(name = "IfIndex")
	public Long getIfIndex() {
		return ifIndex;
	}
	public void setIfIndex(Long ifIndex) {
		this.ifIndex = ifIndex;
	}
	
	@Column(name = "IfName")
	public String getIfName() {
		return ifName;
	}
	public void setIfName(String ifName) {
		this.ifName = ifName;
	}
	
	@Column(name = "VerifyFlag")
	public Integer getVerifyFlag() {
		return verifyFlag;
	}
	public void setVerifyFlag(Integer verifyFlag) {
		this.verifyFlag = verifyFlag;
	}
	
	
	
	@Column(name = "OrgId")
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Id
	@Column(name = "CablingInfoID", unique = true, nullable = false)
	public String getCablingInfoID() {
		return cablingInfoID;
	}
	public void setCablingInfoID(String cablingInfoID) {
		this.cablingInfoID = cablingInfoID;
	}
	
	@Column(name = "InfoPointNo")
	public String getInfoPointNo() {
		return infoPointNo;
	}
	public void setInfoPointNo(String infoPointNo) {
		this.infoPointNo = infoPointNo;
	}
	
	@Column(name = "BuildingNo")
	public String getBuildingNo() {
		return buildingNo;
	}
	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}
	
	@Column(name = "RoomNo")
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	
	@Column(name = "MediaType")
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	@Column(name = "CableType")
	public String getCableType() {
		return cableType;
	}
	public void setCableType(String cableType) {
		this.cableType = cableType;
	}
	
	@Column(name = "BandWidth")
	public Long getBandWidth() {
		return bandWidth;
	}
	public void setBandWidth(Long bandWidth) {
		this.bandWidth = bandWidth;
	}
	
	@Column(name = "BaudRate")
	public String getBaudRate() {
		return baudRate;
	}
	public void setBaudRate(String baudRate) {
		this.baudRate = baudRate;
	}
	
	@Column(name = "Length")
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	
	@Column(name = "CabinetNo")
	public String getCabinetNo() {
		return cabinetNo;
	}
	public void setCabinetNo(String cabinetNo) {
		this.cabinetNo = cabinetNo;
	}
	
	@Column(name = "ModuleNo")
	public String getModuleNo() {
		return moduleNo;
	}
	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}
	
	@Column(name = "ModulePort")
	public String getModulePort() {
		return modulePort;
	}
	public void setModulePort(String modulePort) {
		this.modulePort = modulePort;
	}
	
	@Column(name = "Remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
