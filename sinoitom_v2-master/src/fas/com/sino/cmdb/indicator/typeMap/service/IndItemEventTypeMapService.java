/*
 * 文件名： IndItemEventTypeMapService.java
 * 
 * 创建日期： 2014-10-22
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.indicator.typeMap.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.event.entity.EventType;
import com.sino.cmdb.indicator.typeMap.dao.IndItemEventTypeMapDao;
import com.sino.cmdb.indicator.typeMap.entity.IndItemEventTypeMap;
import com.sino.cmdb.indicator.typeMap.entity.ParamIndEventType;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-10-22
 */
@Service
@Transactional
public class IndItemEventTypeMapService {

	private static Logger logger = LoggerFactory.getLogger(IndItemEventTypeMapService.class);
	
	private static String objAttNames ="id,indItemID,eventTypeID,eventClassCode";
	private static String jsonAttNames ="id,indItemID,eventTypeID,eventClassCode";
	
	private static String paramObjAttNames ="id,indItemID,eventTypeID,indClassName,indGroupName,indicatorItem,indItemName,eventClassName,eventTypeCode,eventTypeName";
	private static String paramJsonAttNames ="id,indItemID,eventTypeID,indClassName,indGroupName,indicatorItem,indItemName,eventClassName,eventTypeCode,eventTypeName";
	
	@Autowired
	private IndItemEventTypeMapDao indItemEventTypeMapDao;
	
	
	@Transactional(readOnly = true)
	public List<IndItemEventTypeMap> getAll() {
		logger.debug("getAll...");
		return indItemEventTypeMapDao.getAll();
	}
	
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<IndItemEventTypeMap> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	

	@Transactional(readOnly = true)
	public String getObjListStr(final IndItemEventTypeMap obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getParamJsonListStr(final List<ParamIndEventType> list) {
		return JsonUtils.getJsonListInfo(list, paramObjAttNames, paramJsonAttNames);
	}
	
	public void batchSave(List<IndItemEventTypeMap> mpas){
		indItemEventTypeMapDao.batchSave(mpas, 20);
	}
	
	public void add(IndItemEventTypeMap mpas){
		indItemEventTypeMapDao.save(mpas);
	}
	public List<ParamIndEventType> getData(){
		return indItemEventTypeMapDao.getData();
	}
	
	public void deletes(String [] ids){
		logger.debug("Enter deletes...");
		for(String id:ids){
			delete(Integer.parseInt(id));
		}
	}
	
	public void delete(int id){
		logger.debug("Enter delete...");
		indItemEventTypeMapDao.delete(id);
	}


	public List<ParamIndEventType> getAllByClassCode(int id) {
		return indItemEventTypeMapDao.getAllByClassCode(id);
	}

	public List<IndItemEventTypeMap> getByIndItemID(long indItemID){
		String hql=" from IndItemEventTypeMap where indItemID=?";
		return indItemEventTypeMapDao.find(hql, indItemID);
	}
	
	public List<Integer> getEvengCodeByIndItemID(long indItemID){
		String hql="select distinct eventClassCode  from IndItemEventTypeMap where indItemID=?";
		return indItemEventTypeMapDao.find(hql, indItemID);
	}

	public List<ParamIndEventType> getByIndClassCodeAndGroupID(int parseInt,
			long parseLong) {
		return indItemEventTypeMapDao.getByIndClassCodeAndGroupID(parseInt,parseLong);
	}


	public List<ResItemParam> getAllClassCode() {
		return indItemEventTypeMapDao.getAllClassCode();
	}


	public List<ResItemParam> getTreeByClassCode(int parseInt) {
		return indItemEventTypeMapDao.getByClassCode(parseInt);
	}
	
}
