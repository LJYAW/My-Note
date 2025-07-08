/*
 * 文件名： VendorOSNameService.java
 * 
 * 创建日期： 2014-1-19
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
import com.sino.cmdb.vendor.dao.VendorOsTypeDao;
import com.sino.cmdb.vendor.entity.VendorOsType;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision: 1.2 $
 *
 * @since 2014-1-19
 */
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class VendorOsTypeService {
	
	private static Logger logger=LoggerFactory.getLogger(VendorOsTypeService.class);
	
	private static String objAttNames="osTypeID,vendorID,vendorName,osClassCode,osClassName,osType,osTypeName,description,status";
	private static String jsonAttNames="id,vendorID,vendorName,osClassCode,osClassName,osType,osTypeName,description,status";
	
	private static String objMinAttNames = "osTypeID,osType";
	private static String jsonMinAttNames = "id,osName";
	
	@Autowired
	private VendorOsTypeDao vendorOSNameDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<VendorOsType> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final VendorOsType obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	public void delete(int id){
		logger.debug("Enter delete...");
		vendorOSNameDao.delete(id);
	}
	
	public List<VendorOsType> getAll(){
		logger.debug("Enter getAll..");
		return vendorOSNameDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public List<VendorOsType> getAllOrderOSname() {
		return vendorOSNameDao.getAll("osType", true);
	}
	
	@Transactional(readOnly = true)
	public List<VendorOsType> getVendorOS(String vendorId) {
		String hql = "from VendorOsType where vendorID=? order by osType";
		return vendorOSNameDao.find(hql, Integer.parseInt(vendorId));
	}
	
	@Transactional(readOnly = true)
	public String getJsonMinListStr(final List<VendorOsType> list) {
		return JsonUtils.getJsonListInfo(list, objMinAttNames, jsonMinAttNames);
	}
	
	public VendorOsType getById(String id){
		logger.debug("Enter getById...");
		return vendorOSNameDao.get(Integer.parseInt(id));
		
	}
	
	public void save(VendorOsType os){
		logger.debug("Enter save..");
		 vendorOSNameDao.save(os);
	}
	
	public List<VendorOsType> checkOsName(String vendorId,String osName){
		logger.debug("Enter checkOsName...");
		String hql=" from VendorOsType where vendorID=? and osType=?";
		return vendorOSNameDao.find(hql, Integer.parseInt(vendorId),osName);
	}
	
	public List<VendorOsType> getByVendorAndCode(String vendorId,String typeCode){
		logger.debug("Enter getByVendorAndCode...");
		String hql=" from VendorOsType where vendorID=? and osClassCode=?";
		return vendorOSNameDao.find(hql, Integer.parseInt(vendorId),Integer.parseInt(typeCode));
	}
	
	public List<VendorOsType> getByOsClass(String osClass){
		logger.debug("Enter getByVendorAndCode...");
		String hql=" from VendorOsType where  osClassName=?";
		return vendorOSNameDao.find(hql, osClass);
	}
	
	
	
	public  List<VendorOsType> getDataByVendorID(Integer id){
		String hql="from VendorOsType where vendorID=?";
		return vendorOSNameDao.find(hql,id);
	}
	public void batchSave(List<VendorOsType> list){
		vendorOSNameDao.batchSave(list, 10);
	}

}
