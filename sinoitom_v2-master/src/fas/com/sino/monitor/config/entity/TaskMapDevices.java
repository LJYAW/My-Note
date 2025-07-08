package com.sino.monitor.config.entity;

import javax.persistence.*;

@Entity
@Table(name="Mon_TaskMapDevices")
public class TaskMapDevices {

	private Long taskMapDevID;
	private Integer taskID;
	private Long devID;
	private String orgID;
	private String orgName;
	private String maintainer;
	private Integer vendorID;
	private String vendorName;
	private Integer devClassCode;
	private String devClassName;
	private Integer devTypeCode;
	private String devTypeName;
	private String 	devIP;
	private Long devIpLong;
	private String devName;
	private String accessMode;
	private String snmpVersion;
	private String snmpRwStr;
	private String devAccessID;
	private String devPromptID;
	private Integer policyID;
	private Integer monFlag;
	private Integer devOpID;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	public Long getTaskMapDevID() {
		return taskMapDevID;
	}
	public void setTaskMapDevID(Long taskMapDevID) {
		this.taskMapDevID = taskMapDevID;
	}
	public Integer getTaskID() {
		return taskID;
	}
	public void setTaskID(Integer taskID) {
		this.taskID = taskID;
	}
	public Long getDevID() {
		return devID;
	}
	public void setDevID(Long devID) {
		this.devID = devID;
	}
	public String getOrgID() {
		return orgID;
	}
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getMaintainer() {
		return maintainer;
	}
	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
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
	public String getDevIP() {
		return devIP;
	}
	public void setDevIP(String devIP) {
		this.devIP = devIP;
	}
	public Long getDevIpLong() {
		return devIpLong;
	}
	public void setDevIpLong(Long devIpLong) {
		this.devIpLong = devIpLong;
	}
	public String getDevName() {
		return devName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}
	
	public String getAccessMode() {
		return accessMode;
	}
	public void setAccessMode(String accessMode) {
		this.accessMode = accessMode;
	}
	public String getSnmpVersion() {
		return snmpVersion;
	}
	public void setSnmpVersion(String snmpVersion) {
		this.snmpVersion = snmpVersion;
	}
	public String getSnmpRwStr() {
		return snmpRwStr;
	}
	public void setSnmpRwStr(String snmpRwStr) {
		this.snmpRwStr = snmpRwStr;
	}
	public String getDevAccessID() {
		return devAccessID;
	}
	public void setDevAccessID(String devAccessID) {
		this.devAccessID = devAccessID;
	}
	public String getDevPromptID() {
		return devPromptID;
	}
	public void setDevPromptID(String devPromptID) {
		this.devPromptID = devPromptID;
	}
	public Integer getPolicyID() {
		return policyID;
	}
	public void setPolicyID(Integer policyID) {
		this.policyID = policyID;
	}
	public Integer getMonFlag() {
		return monFlag;
	}
	public void setMonFlag(Integer monFlag) {
		this.monFlag = monFlag;
	}
	public Integer getDevOpID() {
		return devOpID;
	}
	public void setDevOpID(Integer devOpID) {
		this.devOpID = devOpID;
	}
	
	
	

}
