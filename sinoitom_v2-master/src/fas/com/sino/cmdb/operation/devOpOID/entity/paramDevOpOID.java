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
public class paramDevOpOID {
	
	private String iD;
	private String dispName;
	private String modelName;
	private String model_OID;
	private String oidOrder;
	private String objectName;
	private String snmpObject;
	private String snmpOID;
	private String getMethod;
	private String defaultThreshold;
	private String status;
	private String devTypeName;
	private String setValue;
	private String valueType;
	
	public String getSetValue() {
		return setValue;
	}
	public void setSetValue(String setValue) {
		this.setValue = setValue;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getDevTypeName() {
		return devTypeName;
	}
	public void setDevTypeName(String devTypeName) {
		this.devTypeName = devTypeName;
	}
	public String getiD() {
		return iD;
	}
	public void setiD(String iD) {
		this.iD = iD;
	}
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModel_OID() {
		return model_OID;
	}
	public void setModel_OID(String model_OID) {
		this.model_OID = model_OID;
	}
	public String getOidOrder() {
		return oidOrder;
	}
	public void setOidOrder(String oidOrder) {
		this.oidOrder = oidOrder;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getSnmpObject() {
		return snmpObject;
	}
	public void setSnmpObject(String snmpObject) {
		this.snmpObject = snmpObject;
	}
	public String getSnmpOID() {
		return snmpOID;
	}
	public void setSnmpOID(String snmpOID) {
		this.snmpOID = snmpOID;
	}
	public String getGetMethod() {
		return getMethod;
	}
	public void setGetMethod(String getMethod) {
		this.getMethod = getMethod;
	}
	public String getDefaultThreshold() {
		return defaultThreshold;
	}
	public void setDefaultThreshold(String defaultThreshold) {
		this.defaultThreshold = defaultThreshold;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
