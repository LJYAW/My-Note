/*
 * 文件名： DevOpOIDService.java
 * 
 * 创建日期： 2014-12-7
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.operation.devOpOID.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.operation.devOp.service.DevOperationService;
import com.sino.cmdb.operation.devOpCmd.dao.DevOpCmdDao;
import com.sino.cmdb.operation.devOpCmd.entity.DevOpCmd;
import com.sino.cmdb.operation.devOpCmd.entity.DevOpCmdParam;
import com.sino.cmdb.operation.devOpOID.dao.DevOpOIDDao;
import com.sino.cmdb.operation.devOpOID.entity.DevOpOID;
import com.sino.cmdb.operation.devOpOID.entity.paramDevOpOID;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-12-7
 */
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class DevOpOIDService {
	
	private static Logger logger = LoggerFactory.getLogger(DevOperationService.class);

	private static String objAttNames = "iD,devOpID,devOpName,snmpEntID,modelName,model_OID,oidOrder,objectName,snmpObject,snmpOID,getMethod,defaultThreshold,status,setValue,valueType,units";

	private static String jsonAttNames = "id,devOpID,devOpName,snmpEntID,modelName,model_OID,oidOrder,objectName,snmpObject,snmpOID,getMethod,defaultThreshold,status,setValue,valueType,units";
	
	private static String paramObjAttNames = "iD,dispName,modelName,model_OID,oidOrder,objectName,snmpObject,snmpOID,getMethod,defaultThreshold,status,devTypeName,setValue,valueType";

	private static String paramJsonAttNames = "id,dispName,modelName,model_OID,oidOrder,objectName,snmpObject,snmpOID,getMethod,defaultThreshold,status,devTypeName,setValue,valueType";
	
	
	@Autowired
	private DevOpOIDDao devOpOIDDao;
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final DevOpOID obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<DevOpCmd> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	public List<paramDevOpOID> getAll() {
		return devOpOIDDao.getAllOID();
	}
	
	public void save(DevOpOID devOpOID){
		devOpOIDDao.save(devOpOID);
	}
	
	public DevOpOID getByID(int id){
		return devOpOIDDao.get(id);
	}
	
	public void delete(int id){
		devOpOIDDao.delete(id);
	}
	
	public void deletes(String [] ids){
		for(String id:ids){
			delete(Integer.valueOf(id));
		}
	}

	@Transactional(readOnly = true)
	public String getParamJsonListStr(List<paramDevOpOID> list) {
		return JsonUtils.getJsonListInfo(list, paramObjAttNames, paramJsonAttNames);
	}

	public List<paramDevOpOID> getByOperation(int parseInt) {
		return devOpOIDDao.getByOperation(parseInt);
	}

	public List<paramDevOpOID> getByOperationAndVendor(int parseInt,
			int parseInt2) {
		return devOpOIDDao.getByOperationAndVendor(parseInt,parseInt2);
	}

	public List<paramDevOpOID> getByOperationAndVendorAndDevType(int parseInt,
			int parseInt2, int parseInt3) {
		return devOpOIDDao.getByOperationAndVendorAndDevType(parseInt,parseInt2,parseInt3);
	}
	

}
