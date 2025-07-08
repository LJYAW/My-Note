package com.sino.fas.res.db.service;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.db.dao.ResDataBasesDao;
import com.sino.fas.res.db.entity.ResDataBases;
import com.sino.fas.res.db.mysqlPerformance.entity.SqlCommandResult;
/**
 * @author fengyao
 * @date 2019-10-9上午9:26:42
 * @className ResDataBasesService
 *
 * @Description TODO
 *
 */
@Component
@Transactional
public class ResDataBasesService {
	
	private static Logger logger = LoggerFactory.getLogger(ResDataBasesService.class);
	
	private static String objAttNames = "dbsId,orgId,orgId@OrgOrganization@orgId@orgShortName,resClassCode,resClassName,resTypeCode,resTypeName,hostId,hostName,hostIp,hostIp,dbType,dbVersion,dbName,dbPort,jdbcDriver,jdbcUrl,userName,password,controllerId,controllerIp,controllerPort,auditStatus,verifyStatus,creator,createTime,modifier,modifyTime,accessMode";
	private static String jsonAttNames = "id,orgId,orgName,resClassCode,resClassName,resTypeCode,resTypeName,hostId,hostName,hostIp,hostIp,dbType,dbVersion,dbName,dbPort,jdbcDriver,jdbcUrl,userName,password,controllerId,controllerIp,controllerPort,auditStatus,verifyStatus,creator,createTime,modifier,modifyTime,accessMode";
	
	private static String objResAttNames = "indicatorId,commandId,variableName,value,remarks";
	private static String jsonResAttNames = "id,commandId,variableName,value,remarks";
	
	@Autowired
	private ResDataBasesDao resDataBasesDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ResDataBases> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getSqlCommandResultJsonListStr(final List<SqlCommandResult> resultList) {
		return JsonUtils.getJsonListInfo(resultList, objResAttNames, jsonResAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final ResDataBases obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
//	================================================================================
	@Transactional(readOnly = true)
	public List<ResDataBases> getAll() {
		logger.debug("ResDataBasesService getAll...");
		return resDataBasesDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public ResDataBases getById(Long id){
		logger.debug("ResDataBasesService getById...");
		return resDataBasesDao.get(id);
	}
	
	public void save(ResDataBases resDataBases) {
		logger.debug("ResDataBasesService save...");
		resDataBasesDao.save(resDataBases);
	}
	
	public void delete(Long id) {
		logger.debug("ResDataBasesService delete...", id);
		resDataBasesDao.delete(id);
		resDataBasesDao.flush();
	}

}
