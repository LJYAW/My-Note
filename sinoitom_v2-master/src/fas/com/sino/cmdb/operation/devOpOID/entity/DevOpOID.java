/*
 * 文件名： DevOpOID.java
 * 
 * 创建日期： 2014-12-5
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.operation.devOpOID.entity;

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
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-12-5
 */
@Entity
@Table(name = "Cmdb_DevOpOID",uniqueConstraints = @UniqueConstraint(columnNames = "ID"))
public class DevOpOID {
	private Integer iD;
	private Integer devOpID;
	private String devOpName;
	private Integer snmpEntID;
	private String model_OID;
	private String modelName;
	private String objectName;
	private Integer oidOrder;
	private String snmpObject;
	private String snmpOID;
	private String getMethod;
	private String setValue;
	private String units;
	private String valueType;
	private String defaultThreshold;
	private Integer status;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getiD() {
		return iD;
	}
	public void setiD(Integer iD) {
		this.iD = iD;
	}
	
	@Column(name = "DevOpID")
	public Integer getDevOpID() {
		return devOpID;
	}
	public void setDevOpID(Integer devOpID) {
		this.devOpID = devOpID;
	}
	
	@Column(name = "DevOpName")
	public String getDevOpName() {
		return devOpName;
	}
	public void setDevOpName(String devOpName) {
		this.devOpName = devOpName;
	}
	@Column(name = "SnmpEntID")
	public Integer getSnmpEntID() {
		return snmpEntID;
	}
	public void setSnmpEntID(Integer snmpEntID) {
		this.snmpEntID = snmpEntID;
	}
	
	@Column(name = "Model_OID")
	public String getModel_OID() {
		return model_OID;
	}
	public void setModel_OID(String model_OID) {
		this.model_OID = model_OID;
	}
	
	@Column(name = "ModelName")
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	@Column(name = "ObjectName")
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
	@Column(name = "OidOrder")
	public Integer getOidOrder() {
		return oidOrder;
	}
	public void setOidOrder(Integer oidOrder) {
		this.oidOrder = oidOrder;
	}
	
	@Column(name = "SnmpOID")
	public String getSnmpOID() {
		return snmpOID;
	}
	public void setSnmpOID(String snmpOID) {
		this.snmpOID = snmpOID;
	}
	
	@Column(name = "GetMethod")
	public String getGetMethod() {
		return getMethod;
	}
	public void setGetMethod(String getMethod) {
		this.getMethod = getMethod;
	}
	
	@Column(name = "SetValue")
	public String getSetValue() {
		return setValue;
	}
	public void setSetValue(String setValue) {
		this.setValue = setValue;
	}
	
	@Column(name = "ValueType")
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	@Column(name = "DefaultThreshold")
	public String getDefaultThreshold() {
		return defaultThreshold;
	}
	public void setDefaultThreshold(String defaultThreshold) {
		this.defaultThreshold = defaultThreshold;
	}
	
	@Column(name = "Status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "SnmpObject")
	public String getSnmpObject() {
		return snmpObject;
	}
	public void setSnmpObject(String snmpObject) {
		this.snmpObject = snmpObject;
	}
	@Column(name = "Units")
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	
	

}
