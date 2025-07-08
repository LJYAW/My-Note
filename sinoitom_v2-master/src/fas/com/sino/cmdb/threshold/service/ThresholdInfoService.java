/*
 * 文件名： ThresholdTypeService.java
 * 
 * 创建日期： 2014-9-10
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.threshold.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.threshold.dao.ThresholdInfoDao;
import com.sino.cmdb.threshold.entity.ParamThresholdInfoAndType;
import com.sino.cmdb.threshold.entity.ThresholdInfo;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-10
 */
@Service
@Transactional
public class ThresholdInfoService {
	private static String objAttNames = "uUID,threshTypeID,thresholdName,thresholdLevel,description,threshold_ID";
	private static String jsonAttNames = "id,threshTypeID,thresholdName,thresholdLevel,description,threshold_ID";
	
	private static String objParamAttNames = "uUID,threshTypeID,thresholdName,thresholdLevel,description,indObjName,indDimName,indGroupName,threshTypeName,defaultUnit,unitName,status,threshold_ID";
	private static String jsonParamAttNames = "id,threshTypeID,thresholdName,thresholdLevel,description,indObjName,indDimName,indGroupName,threshTypeName,defaultUnit,unitName,status,threshold_ID";
	
	@Autowired
	private ThresholdInfoDao thresholdInfoDao;
	
	@Transactional(readOnly = true)
	public List<ThresholdInfo> getAll() {
		return thresholdInfoDao.getAll();
	}

	public String getJsonListStr(List<ParamThresholdInfoAndType> threshold) {
		return JsonUtils.getJsonListInfo(threshold, objAttNames, jsonAttNames);
	}
	
	public String getThreshJsonListStr(List<ThresholdInfo> threshold) {
		return JsonUtils.getJsonListInfo(threshold, objAttNames, jsonAttNames);
	}
	
	public String getParamJsonListStr(List<ParamThresholdInfoAndType> threshold) {
		return JsonUtils.getJsonListInfo(threshold, objParamAttNames, jsonParamAttNames);
	}

	public List<ParamThresholdInfoAndType> getAllParam() {
		return thresholdInfoDao.getAllParam();
	}
	
	
	
	

	public void add(ThresholdInfo saveThresholdInfo) {
		thresholdInfoDao.save(saveThresholdInfo);
		
	}

	public String getJsonObjStr(ThresholdInfo saveThresholdInfo) {
		return JsonUtils.getJsonObjInfo(saveThresholdInfo, objAttNames, jsonAttNames);
	}

	public ThresholdInfo getByUUID(String uu) {
		String hql=" from  ThresholdInfo where uUID = ? ";
		return thresholdInfoDao.findUnique(hql, uu);
	}

	public ThresholdInfo getByID(String id) {
		String hql=" from  ThresholdInfo where threshold_ID = ? ";
		return thresholdInfoDao.findUnique(hql, Integer.parseInt(id));
	}
	
	public List<ThresholdInfo> getByThreshTypeID(int id) {
		String hql=" from  ThresholdInfo where threshTypeID = ? ";
		return thresholdInfoDao.find(hql,id);
	}
	
	
	public void deletesByUUIDD(String [] ids){
		for(String id:ids){
			delete(id);
		}
	}
	
	public void delete(String id){
		thresholdInfoDao.deleteByUUID(id);
	}
	
	
}
