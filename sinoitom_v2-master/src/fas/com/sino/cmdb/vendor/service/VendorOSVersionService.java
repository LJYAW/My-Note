/*
 * 文件名： VendorOSVersionService.java
 * 
 * 创建日期： 2014-2-13
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.vendor.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.vendor.dao.VendorOSVersionDao;
import com.sino.cmdb.vendor.entity.VendorOSVersion;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-2-13
 */

@Service
@Transactional
public class VendorOSVersionService {
	
	private static Logger logger = LoggerFactory.getLogger(VendorOSVersionService.class);

	private static String objAttNames = "osVersionID,vendorID@CmdbVendor@vendorID@dispName,osClassCode@IT_OSType,osClassName,osType,osVersion,osFeature,description,status";

	private static String jsonAttNames = "id,vendorID,osClassCode,osClassName,osType,osVersion,osFeature,description,status";
	
	@Autowired
	private VendorOSVersionDao vendorOSVersionDao;
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final VendorOSVersion obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<VendorOSVersion> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	public List<VendorOSVersion> getAll(){
		logger.debug("Enter getAll..");
		return vendorOSVersionDao.getAll();
	}
	
	public VendorOSVersion getById(String id){
		logger.debug("Enter getById...");
		return vendorOSVersionDao.get(Integer.parseInt(id));
	}
	
	public void save(VendorOSVersion version){
		logger.debug("Enter save...");
		vendorOSVersionDao.save(version);
	}
	public void batchSave(List<VendorOSVersion> list){
		vendorOSVersionDao.batchSave(list, 10);
	}
	public void delete(String id){
		logger.debug("Enter delete...");
		vendorOSVersionDao.delete(Integer.parseInt(id));
	}
	
	public List<VendorOSVersion> checkVersion(String vendorId,String typeCode,String osType,String osVersion){
		logger.debug("Enter checkVersion...");
		String hql=" from VendorOSVersion where vendorID=? and osClassCode=? and osType=? and osVersion=?";
		return vendorOSVersionDao.find(hql, Integer.parseInt(vendorId),Integer.parseInt(typeCode),osType,osVersion);
	}
    public List<VendorOSVersion> getOSVersionData(Integer vendorID,String osType){
    	String hql="from VendorOSVersion where vendorID=? and osType=?";
    	return vendorOSVersionDao.find(hql,vendorID,osType);
    }
    
    public List<VendorOSVersion> getOSVersionByVendorAndTypeCodeAndOsName(Integer vendorID,Integer typeCode,String osType){
    	String hql="from VendorOSVersion where vendorID=? and osClassCode=? and osType=?";
    	return vendorOSVersionDao.find(hql,vendorID,typeCode,osType);
    }

	public List<VendorOSVersion> getOSVersionById(String id) {
		String hql="from VendorOSVersion where vendorID=? ";
    	return vendorOSVersionDao.find(hql,Integer.parseInt(id));
	}
	
	public List<VendorOSVersion> getOSVersionByVendorId(String id) {
		String hql="from VendorOSVersion where vendorID=? group by osType";
    	return vendorOSVersionDao.find(hql,Integer.parseInt(id));
	}
	public List<VendorOSVersion> getOsVersionByVendorIdAndOsType(String id,String osType) {
		String hql="from VendorOSVersion where vendorID=? and osType=? group by osVersion";
    	return vendorOSVersionDao.find(hql,Integer.parseInt(id),osType);
	}
	
	public List<VendorOSVersion> getOsFeature(String id,String osType,String osVersion ) {
		String hql="from VendorOSVersion where vendorID=? and osType=? and osVersion=?";
    	return vendorOSVersionDao.find(hql,Integer.parseInt(id),osType,osVersion);
	}
	
	public List<VendorOSVersion> getOsVersionByOsClassAndOsType(String osClass,String osType) {
		String hql="from VendorOSVersion where osClassName=? and osType=?";
    	return vendorOSVersionDao.find(hql,osClass,osType);
	}
	
	
}
