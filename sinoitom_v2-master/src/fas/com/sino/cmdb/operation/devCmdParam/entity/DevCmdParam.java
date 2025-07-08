/*
 * 文件名： DevCmdParam.java
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
@Table(name = "Cmdb_DevCmdParam",uniqueConstraints = @UniqueConstraint(columnNames = "CmdParamID"))
public class DevCmdParam {

	private Integer cmdParamID;
	private String paramName;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "CmdParamID")
	public Integer getCmdParamID() {
		return cmdParamID;
	}
	public void setCmdParamID(Integer cmdParamID) {
		this.cmdParamID = cmdParamID;
	}
	
	@Column(name = "ParamName")
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	

}
