/*
 * 文件名： Operation.java
 * 
 * 创建日期： 2014-9-13
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.operation.devOp.entity;

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
 * @since 2014-9-13
 */
@Entity
@Table(name = "Cmdb_Operation",uniqueConstraints = @UniqueConstraint(columnNames = "opID"))
public class Operation {
	
	private Integer opID;
	private String opType;
	private String operation;
	private String operationName;
	private Integer status;
	private String description;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "OpID")
	public Integer getOpID() {
		return opID;
	}
	public void setOpID(Integer opID) {
		this.opID = opID;
	}
	
	@Column(name = "Operation")
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Column(name = "OperationName")
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
	@Column(name = "Status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	@Column(name = "Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	
	


}
