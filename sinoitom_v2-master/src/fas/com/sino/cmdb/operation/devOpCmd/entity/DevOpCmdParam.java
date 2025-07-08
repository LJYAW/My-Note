/*
 * 文件名： DevOpCmdParam.java
 * 
 * 创建日期： 2014-12-5
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.operation.devOpCmd.entity;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-12-5
 */
public class DevOpCmdParam {

	private String devOpID;
	private String opCmdID;
	private String devTypeName;
	private String operateName;
	private String snmpEntID;
	private String dispName;
	private String osID;
	private String modelOID;
	private String devModel;
	private String osName;
	private String osVersion;
	private String cmdOrder;
	private String cmdTypeCode;
	private String cmdType;
	private String command;
	private String expectPrompt;
	private String paramFlag;
	private String description;
	private Integer status;
	public String getOpCmdID() {
		return opCmdID;
	}
	public void setOpCmdID(String opCmdID) {
		this.opCmdID = opCmdID;
	}
	
	public String getDevOpID() {
		return devOpID;
	}
	public void setDevOpID(String devOpID) {
		this.devOpID = devOpID;
	}
	public String getDevTypeName() {
		return devTypeName;
	}
	public void setDevTypeName(String devTypeName) {
		this.devTypeName = devTypeName;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public String getSnmpEntID() {
		return snmpEntID;
	}
	public void setSnmpEntID(String snmpEntID) {
		this.snmpEntID = snmpEntID;
	}
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	public String getOsID() {
		return osID;
	}
	public void setOsID(String osID) {
		this.osID = osID;
	}
	public String getModelOID() {
		return modelOID;
	}
	public void setModelOID(String modelOID) {
		this.modelOID = modelOID;
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
	public String getCmdOrder() {
		return cmdOrder;
	}
	public void setCmdOrder(String cmdOrder) {
		this.cmdOrder = cmdOrder;
	}
	public String getCmdTypeCode() {
		return cmdTypeCode;
	}
	public void setCmdTypeCode(String cmdTypeCode) {
		this.cmdTypeCode = cmdTypeCode;
	}
	public String getCmdType() {
		return cmdType;
	}
	public void setCmdType(String cmdType) {
		this.cmdType = cmdType;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getExpectPrompt() {
		return expectPrompt;
	}
	public void setExpectPrompt(String expectPrompt) {
		this.expectPrompt = expectPrompt;
	}
	public String getParamFlag() {
		return paramFlag;
	}
	public void setParamFlag(String paramFlag) {
		this.paramFlag = paramFlag;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDevModel() {
		return devModel;
	}
	public void setDevModel(String devModel) {
		this.devModel = devModel;
	}
	
	

}
