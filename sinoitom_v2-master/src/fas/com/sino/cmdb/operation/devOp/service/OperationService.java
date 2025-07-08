/*
 * 文件名： OperationService.java
 * 
 * 创建日期： 2014-9-13
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.operation.devOp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.operation.devOp.dao.OperationDao;
import com.sino.cmdb.operation.devOp.entity.DevOperation;
import com.sino.cmdb.operation.devOp.entity.Operation;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-13
 */
@Service
@Transactional
public class OperationService {

	private static Logger logger = LoggerFactory.getLogger(OperationService.class);

	private static String objAttNames = "opID,opType,opType@Cmdb_OpType,operation,operationName,status,description";

	private static String jsonAttNames = "id,opType,opName,operation,operationName,status,description";
	
	@Autowired
	private OperationDao operationDao;
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final Operation obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<Operation> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	public List<Operation> getAll(){
		logger.debug("Enter getAll...");
		return operationDao.getAll();
	}
	
	public Operation getById(int id){
		return operationDao.get(id);
	}
	
	public void save(Operation operation){
		logger.debug("Enter save...");
		operationDao.save(operation);
	}
	
	public void batchSave(List<Integer> opIds){
		logger.debug("Enter batchSave...");
		operationDao.batchAudit(opIds);
	}
	
	public void delete(int id){
		logger.debug("Enter delete....");
		operationDao.delete(id);
	}
	
	public List<Operation> getAuditData(){
		String hql=" from Operation where status=1";
		return operationDao.find(hql);
	}
	
	public List<Operation> getAuditDataAndOpType(String opType){
		String hql=" from Operation where opType=? and status=1";
		return operationDao.find(hql,opType);
	}
	
}
