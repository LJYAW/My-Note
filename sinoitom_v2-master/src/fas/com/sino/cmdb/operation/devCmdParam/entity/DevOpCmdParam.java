/*
 * 文件名： DevOpCmdParam.java
 * 
 * 创建日期： 2014-9-15
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.operation.devCmdParam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-15
 */
@Entity
@Table(name = "Cmdb_DevOpCmdParam",uniqueConstraints = @UniqueConstraint(columnNames = "paramID"))
public class DevOpCmdParam {

	private Integer paramID;
	private Long opCmdID;
	private String cmdParameter;
	private String param_Value;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ParamID")
	public Integer getParamID() {
		return paramID;
	}
	public void setParamID(Integer paramID) {
		this.paramID = paramID;
	}
	
	@Column(name = "OpCmdID")
	public Long getOpCmdID() {
		return opCmdID;
	}
	public void setOpCmdID(Long opCmdID) {
		this.opCmdID = opCmdID;
	}
	
	@Column(name = "CmdParameter")
	public String getCmdParameter() {
		return cmdParameter;
	}
	public void setCmdParameter(String cmdParameter) {
		this.cmdParameter = cmdParameter;
	}
	
	@Column(name = "Param_Value")
	public String getParam_Value() {
		return param_Value;
	}
	public void setParam_Value(String param_Value) {
		this.param_Value = param_Value;
	}

	
	
}
