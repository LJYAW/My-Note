package com.sino.cmdb.vendor.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Cmdb_ProdChkCmds",uniqueConstraints = @UniqueConstraint(columnNames = "prodChkCmdId"))
public class CmdbProdChkCmds {
	
	private Integer prodChkCmdId;
	private Integer vendorId;    //厂商ID
	private Integer devClassCode;  // 产品分类ID
	private String devClassName ; //产品分类名称
	private Integer devTypeCode ; //产品类型ID（-1代表：所有类型）
	private String devTypeName ; //产品类型名称
	private Double prodModelId ; //产品型号ID（-1代表所有型号）
	private String prodModel ; //产品型号
	private String modelOID ; //Model OID
	private String osType ; //操作系统类型
	private String osVersion ; //操作系统版本
	private String osFeature;  //操作系统特征
	private String checkCmd ; //巡检命令
	private String cmdUsage ; //命令用途
	private String resultSample ; //结果示例
	private String remark ; //备注
	private Integer status ; //状态(1:已审核;0:未审核)
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getProdChkCmdId() {
		return prodChkCmdId;
	}
	public void setProdChkCmdId(Integer prodChkCmdId) {
		this.prodChkCmdId = prodChkCmdId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getDevClassCode() {
		return devClassCode;
	}
	public void setDevClassCode(Integer devClassCode) {
		this.devClassCode = devClassCode;
	}
	public String getDevClassName() {
		return devClassName;
	}
	public void setDevClassName(String devClassName) {
		this.devClassName = devClassName;
	}
	public Integer getDevTypeCode() {
		return devTypeCode;
	}
	public void setDevTypeCode(Integer devTypeCode) {
		this.devTypeCode = devTypeCode;
	}
	public String getDevTypeName() {
		return devTypeName;
	}
	public void setDevTypeName(String devTypeName) {
		this.devTypeName = devTypeName;
	}
	public Double getProdModelId() {
		return prodModelId;
	}
	public void setProdModelId(Double prodModelId) {
		this.prodModelId = prodModelId;
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
	public String getCheckCmd() {
		return checkCmd;
	}
	public void setCheckCmd(String checkCmd) {
		this.checkCmd = checkCmd;
	}
	public String getCmdUsage() {
		return cmdUsage;
	}
	public void setCmdUsage(String cmdUsage) {
		this.cmdUsage = cmdUsage;
	}
	public String getResultSample() {
		return resultSample;
	}
	public void setResultSample(String resultSample) {
		this.resultSample = resultSample;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOsFeature() {
		return osFeature;
	}
	public void setOsFeature(String osFeature) {
		this.osFeature = osFeature;
	}
	
	
	

}
