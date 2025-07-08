/*
 * 文件名： DevOpOIDDao.java
 * 
 * 创建日期： 2014-12-7
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.operation.devOpOID.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.operation.devOpCmd.entity.DevOpCmdParam;
import com.sino.cmdb.operation.devOpOID.entity.DevOpOID;
import com.sino.cmdb.operation.devOpOID.entity.paramDevOpOID;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-12-7
 */
@Component
public class DevOpOIDDao extends BaseDao<DevOpOID, Integer>{

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getOperation() {
		String hql="select distinct devOpID,devOpName from DevOpOID order by devOpID ";
		Query query=this.getSession().createQuery(hql);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ResItemParam> paramList = new ArrayList<ResItemParam>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ResItemParam itemParam = new ResItemParam();
			itemParam.setId(tuple[0].toString());
			itemParam.setText(tuple[1].toString());
			paramList.add(itemParam);
		}
		
		return paramList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getVenderById(int devOpID) {
		String hql="select distinct a.vendorID,a.dispName from CmdbVendor a,DevOpOID b where a.vendorID = b.snmpEntID and b.devOpID=:devOpID order by a.vendorID ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("devOpID", devOpID);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ResItemParam> paramList = new ArrayList<ResItemParam>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ResItemParam itemParam = new ResItemParam();
			itemParam.setId(tuple[0].toString());
			itemParam.setText(tuple[1].toString());
			paramList.add(itemParam);
		}
		
		return paramList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getDevTypeByVendor(int devOpID,int dOpID) {
		String hql="select distinct a.devTypeCode,a.devTypeName from DevOperation a,DevOpOID b where a.devOpID = b.devOpID and b.snmpEntID=:devOpID and b.devOpID=:dOpID  order by a.devTypeCode ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("devOpID", devOpID);
		query.setParameter("dOpID", dOpID);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ResItemParam> paramList = new ArrayList<ResItemParam>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ResItemParam itemParam = new ResItemParam();
			itemParam.setId(tuple[0].toString());
			itemParam.setText(tuple[1].toString());
			paramList.add(itemParam);
		}
		
		return paramList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<paramDevOpOID> getAllOID() {
		StringBuffer hql=new StringBuffer("select a.iD,b.dispName,a.modelName,a.model_OID,a.oidOrder,a.objectName,");
		hql.append(" a.snmpObject,a.snmpOID,a.getMethod,a.defaultThreshold,a.status,c.devTypeName,a.setValue,a.valueType ");
		hql.append(" from DevOpOID a,CmdbVendor b,DevOperation c where b.vendorID = a.snmpEntID and a.devOpID = c.devOpID ");
		hql.append(" order by b.dispName ");
		System.out.println(hql);
		List<paramDevOpOID> paramList = new ArrayList<paramDevOpOID>();
		Query query=this.getSession().createQuery(hql.toString());  
		List list=query.list();
		Iterator it  =  list.iterator();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			paramDevOpOID param = new paramDevOpOID();
			param.setiD(tuple[0].toString());
			param.setDispName(tuple[1].toString());
			param.setModelName(tuple[2].toString());
			param.setModel_OID(tuple[3].toString());
			param.setOidOrder(tuple[4].toString());
			if(null != tuple[5]){
				param.setObjectName(tuple[5].toString());
			}else {
				param.setObjectName("");
			}
			if(null != tuple[6]){
				param.setSnmpObject(tuple[6].toString());
			}else {
				param.setSnmpObject("");
			}
			param.setSnmpOID(tuple[7].toString());
			param.setGetMethod(tuple[8].toString());
			if(null != tuple[9]){
				param.setDefaultThreshold(tuple[9].toString());
			}
			if(null != tuple[10]){
				param.setStatus(tuple[10].toString());
			}else {
				param.setStatus("");
			}
			param.setDevTypeName(tuple[11].toString());
			if(null != tuple[12]){
				param.setSetValue(tuple[12].toString());
			}else {
				param.setSetValue("");
			}
			if(null != tuple[13]){
				param.setValueType(tuple[13].toString());
			}else{
				param.setValueType("");
			}
			paramList.add(param);
		}
		return paramList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<paramDevOpOID> getByOperation(int operation) {
		StringBuffer hql=new StringBuffer("select a.iD,b.dispName,a.modelName,a.model_OID,a.oidOrder,a.objectName,");
		hql.append(" a.snmpObject,a.snmpOID,a.getMethod,a.defaultThreshold,a.status,c.devTypeName,a.setValue,a.valueType");
		hql.append(" from DevOpOID a,CmdbVendor b,DevOperation c where b.vendorID = a.snmpEntID and a.devOpID=:operation and a.devOpID = c.devOpID ");
		hql.append(" order by b.dispName ");
		System.out.println(hql);
		List<paramDevOpOID> paramList = new ArrayList<paramDevOpOID>();
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameter("operation", operation);
		List list=query.list();
		Iterator it  =  list.iterator();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			paramDevOpOID param = new paramDevOpOID();
			param.setiD(tuple[0].toString());
			param.setDispName(tuple[1].toString());
			param.setModelName(tuple[2].toString());
			param.setModel_OID(tuple[3].toString());
			param.setOidOrder(tuple[4].toString());
			if(null != tuple[5]){
				param.setObjectName(tuple[5].toString());
			}else {
				param.setObjectName("");
			}
			if(null != tuple[6]){
				param.setSnmpObject(tuple[6].toString());
			}else {
				param.setSnmpObject("");
			}
			param.setSnmpOID(tuple[7].toString());
			param.setGetMethod(tuple[8].toString());
			if(null != tuple[9]){
				param.setDefaultThreshold(tuple[9].toString());
			}
			
			if(null != tuple[10]){
				param.setStatus(tuple[10].toString());
			}else {
				param.setStatus("");
			}
			param.setDevTypeName(tuple[11].toString());
			if(null != tuple[12]){
				param.setSetValue(tuple[12].toString());
			}else {
				param.setSetValue("");
			}
			if(null != tuple[13]){
				param.setValueType(tuple[13].toString());
			}else{
				param.setValueType("");
			}
			paramList.add(param);
		}
		return paramList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<paramDevOpOID> getByOperationAndVendor(int operation,
			int vendor) {
		StringBuffer hql=new StringBuffer("select a.iD,b.dispName,a.modelName,a.model_OID,a.oidOrder,a.objectName,");
		hql.append(" a.snmpObject,a.snmpOID,a.getMethod,a.defaultThreshold,a.status,c.devTypeName,a.setValue,a.valueType ");
		hql.append(" from DevOpOID a,CmdbVendor b,DevOperation c where b.vendorID = a.snmpEntID and a.devOpID=:operation and a.snmpEntID=:vendor and a.devOpID = c.devOpID ");
		hql.append(" order by b.dispName ");
		System.out.println(hql);
		List<paramDevOpOID> paramList = new ArrayList<paramDevOpOID>();
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameter("operation", operation);
		query.setParameter("vendor", vendor);
		List list=query.list();
		Iterator it  =  list.iterator();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			paramDevOpOID param = new paramDevOpOID();
			param.setiD(tuple[0].toString());
			param.setDispName(tuple[1].toString());
			param.setModelName(tuple[2].toString());
			param.setModel_OID(tuple[3].toString());
			param.setOidOrder(tuple[4].toString());
			if(null != tuple[5]){
				param.setObjectName(tuple[5].toString());
			}else {
				param.setObjectName("");
			}
			if(null != tuple[6]){
				param.setSnmpObject(tuple[6].toString());
			}else {
				param.setSnmpObject("");
			}
			param.setSnmpOID(tuple[7].toString());
			param.setGetMethod(tuple[8].toString());
			if(null != tuple[9]){
				param.setDefaultThreshold(tuple[9].toString());
			}
			if(null != tuple[10]){
				param.setStatus(tuple[10].toString());
			}else {
				param.setStatus("");
			}
			param.setDevTypeName(tuple[11].toString());
			if(null != tuple[12]){
				param.setSetValue(tuple[12].toString());
			}else {
				param.setSetValue("");
			}
			if(null != tuple[13]){
				param.setValueType(tuple[13].toString());
			}else{
				param.setValueType("");
			}
			paramList.add(param);
		}
		return paramList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<paramDevOpOID> getByOperationAndVendorAndDevType(int operation,
			int vendor,int typeCode) {
		StringBuffer hql=new StringBuffer("select a.iD,b.dispName,a.modelName,a.model_OID,a.oidOrder,a.objectName,");
		hql.append(" a.snmpObject,a.snmpOID,a.getMethod,a.defaultThreshold,a.status,c.devTypeName,a.setValue,a.valueType ");
		hql.append(" from DevOpOID a,CmdbVendor b,DevOperation c where b.vendorID = a.snmpEntID and a.devOpID = c.devOpID ");
		hql.append(" and a.snmpEntID=:vendor and a.devOpID=:operation and c.devTypeCode=:typeCode order by b.dispName ");
		System.out.println(hql);
		List<paramDevOpOID> paramList = new ArrayList<paramDevOpOID>();
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameter("operation", operation);
		query.setParameter("vendor", vendor);
		query.setParameter("typeCode", typeCode);
		List list=query.list();
		Iterator it  =  list.iterator();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			paramDevOpOID param = new paramDevOpOID();
			param.setiD(tuple[0].toString());
			param.setDispName(tuple[1].toString());
			param.setModelName(tuple[2].toString());
			param.setModel_OID(tuple[3].toString());
			param.setOidOrder(tuple[4].toString());
			if(null != tuple[5]){
				param.setObjectName(tuple[5].toString());
			}else {
				param.setObjectName("");
			}
			if(null != tuple[6]){
				param.setSnmpObject(tuple[6].toString());
			}else {
				param.setSnmpObject("");
			}
			param.setSnmpOID(tuple[7].toString());
			param.setGetMethod(tuple[8].toString());
			if(null != tuple[9]){
				param.setDefaultThreshold(tuple[9].toString());
			}
			if(null != tuple[10]){
				param.setStatus(tuple[10].toString());
			}else {
				param.setStatus("");
			}
			param.setDevTypeName(tuple[11].toString());
			if(null != tuple[12]){
				param.setSetValue(tuple[12].toString());
			}else {
				param.setSetValue("");
			}
			if(null != tuple[13]){
				param.setValueType(tuple[13].toString());
			}else{
				param.setValueType("");
			}
			paramList.add(param);
		}
		return paramList;
	}

}
