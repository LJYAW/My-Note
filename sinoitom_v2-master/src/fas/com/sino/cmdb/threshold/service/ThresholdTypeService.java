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
import com.sino.cmdb.threshold.dao.ThresholdTypeDao;
import com.sino.cmdb.threshold.entity.ThresholdType;

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
public class ThresholdTypeService {
	private static String objAttNames = "threshTypeID,indDimCode,indDimName,indObjCode,indObjName,indGroupID,indGroupEname,indGroupName,threshTypeName,defaultUnit,unitName,description,status";
	private static String jsonAttNames = "id,indDimCode,indDimName,indObjCode,indObjName,indGroupID,indGroupEname,indGroupName,threshTypeName,defaultUnit,unitName,description,status";
	
	private static String objMiniAttNames = "indObjCode,threshTypeID,indObjName";
	private static String jsonMiniAttNames = "indObjCode,threshTypeID,indObjName";
	
	@Autowired
	private ThresholdTypeDao thresholdTypeDao;
	
	@Transactional(readOnly = true)
	public List<ThresholdType> getAll() {
		return thresholdTypeDao.getAll();
	}

	public String getJsonListStr(List<ThresholdType> threshold) {
		return JsonUtils.getJsonListInfo(threshold, objAttNames, jsonAttNames);
	}
	
	public String getMiniJsonListStr(List<ThresholdType> threshold) {
		return JsonUtils.getJsonListInfo(threshold, objMiniAttNames, jsonMiniAttNames);
	}

	public void add(ThresholdType saveThresholdType) {
		thresholdTypeDao.save(saveThresholdType);
		
	}

	public String getJsonObjStr(ThresholdType saveThresholdType) {
		return JsonUtils.getJsonObjInfo(saveThresholdType, objAttNames, jsonAttNames);
	}

	public List<ThresholdType> getByObjCode(String objCode) {
		String hql=" from ThresholdType where indObjCode = ? ";
		return thresholdTypeDao.find(hql,Integer.parseInt(objCode));
	}
	
	public List<ThresholdType> getByObjCodeAndGroupEnName(String objCode,String indGroupEname) {
		String hql=" from ThresholdType where indObjCode = ? and  indGroupEname=?";
		return thresholdTypeDao.find(hql,Integer.parseInt(objCode),indGroupEname);
	}
	

	public ThresholdType getByID(String id) {
		return thresholdTypeDao.get(Integer.parseInt(id));
	}

	
	public void deletes(String [] ids){
		for(String id:ids){
			delete(Integer.parseInt(id));
		}
	}
	
	public void delete(Integer id){
		thresholdTypeDao.delete(id);
	}

	
	
	
}
