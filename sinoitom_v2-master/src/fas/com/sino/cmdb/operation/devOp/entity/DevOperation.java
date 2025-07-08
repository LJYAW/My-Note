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
 * @author <a href="mailto:wangam@sino-bridge.com">王阿明</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2013-2-11
 */
@Entity
@Table(name = "Cmdb_DevOperation",uniqueConstraints = @UniqueConstraint(columnNames = "devOpID"))
public class DevOperation {
	private Integer devOpID;	   //int	11	
	private Integer opID;
	private Integer devClassCode;
	private String devClassName;
	private Integer devTypeCode;
	private String devTypeName;
	private Integer accessModeCode;	 //int	1		访问方式编码
	private String accessMode;	     //varchar	8		设备访问方式(1: SNMP;2:CLI)	utf8	utf8_general_ci
	private Integer opTypeCode;
	private String opTypeName;
	private String operation;	     //varchar	64	设备操作	utf8	utf8_general_ci
	private String operateName;
	private String devOpName;
	private String description;	   //varchar	128	操作描述	utf8	utf8_general_ci
	private Integer status;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "DevOpID")
	public Integer getDevOpID() {
		return devOpID;
	}
	public void setDevOpID(Integer devOpID) {
		this.devOpID = devOpID;
	}
	
	@Column(name = "OpID")
	public Integer getOpID() {
		return opID;
	}
	
	public void setOpID(Integer opID) {
		this.opID = opID;
	}
	
	@Column(name = "AccessModeCode")
	public Integer getAccessModeCode() {
		return accessModeCode;
	}
	
	public void setAccessModeCode(Integer accessModeCode) {
		this.accessModeCode = accessModeCode;
	}
	@Column(name = "AccessMode")
	public String getAccessMode() {
		return accessMode;
	}
	public void setAccessMode(String accessMode) {
		this.accessMode = accessMode;
	}
	
	
	@Column(name = "OpTypeCode")
	public Integer getOpTypeCode() {
		return opTypeCode;
	}
	public void setOpTypeCode(Integer opTypeCode) {
		this.opTypeCode = opTypeCode;
	}
	
	@Column(name = "OpTypeName")
	public String getOpTypeName() {
		return opTypeName;
	}
	public void setOpTypeName(String opTypeName) {
		this.opTypeName = opTypeName;
	}
	

	@Column(name = "Operation")
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	@Column(name = "Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Column(name = "DevClassCode")
	public Integer getDevClassCode() {
		return devClassCode;
	}
	public void setDevClassCode(Integer devClassCode) {
		this.devClassCode = devClassCode;
	}
	
	
	@Column(name = "DevClassName")
	public String getDevClassName() {
		return devClassName;
	}
	public void setDevClassName(String devClassName) {
		this.devClassName = devClassName;
	}
	
	@Column(name = "DevTypeCode")
	public Integer getDevTypeCode() {
		return devTypeCode;
	}
	public void setDevTypeCode(Integer devTypeCode) {
		this.devTypeCode = devTypeCode;
	}
	
	@Column(name = "DevTypeName")
	public String getDevTypeName() {
		return devTypeName;
	}
	public void setDevTypeName(String devTypeName) {
		this.devTypeName = devTypeName;
	}
	
	@Column(name = "DevOpName")
	public String getDevOpName() {
		return devOpName;
	}
	public void setDevOpName(String devOpName) {
		this.devOpName = devOpName;
	}
	
	@Column(name = "OperateName")
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	
	@Column(name = "Status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
