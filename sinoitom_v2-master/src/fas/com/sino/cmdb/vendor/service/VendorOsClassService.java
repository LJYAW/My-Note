/*
 * 文件名： VendorOSTypeService.java
 * 
 * 创建日期： 2014-1-19
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
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
import com.sino.cmdb.vendor.dao.VendorOsClassDao;
import com.sino.cmdb.vendor.entity.CmdbProdChkCmds;
import com.sino.cmdb.vendor.entity.VendorOsClass;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.2 $
 *
 * @since 2014-1-19
 */
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class VendorOsClassService {
	
	private static Logger logger = LoggerFactory.getLogger(VendorOsClassService.class);

	private static String objAttNames = "vendorOSTypeID,vendorID,vendorID@CmdbVendor@vendorID@dispName,osClassCode,osClassName,description,status";

	private static String jsonAttNames = "id,vendorID,vendorName,osClassCode,osClassName,description,status";
	
	@Autowired
	private VendorOsClassDao vendorOSTypeDao;

	@Transactional(readOnly = true)
	public String getJsonObjStr(final VendorOsClass obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<VendorOsClass> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	public List<VendorOsClass> getAll(){
		logger.debug("Enter getAll..");
		return vendorOSTypeDao.getAll();
	}
	public List<VendorOsClass> getJsonVendorOSType(){
		String hql="from VendorOSType GROUP BY vendorName";
		List<VendorOsClass> listItem = vendorOSTypeDao.find(hql);
		return listItem;
	}
	public List<VendorOsClass> getJsonOSTypeName(Integer vendorID){
		String hql="from VendorOSType where vendorID=?";
		List<VendorOsClass> listItem = vendorOSTypeDao.find(hql,vendorID);
		return listItem;
	}
	
	public VendorOsClass getById(int id){
		logger.debug("Enter getById...");
		return vendorOSTypeDao.get(id);
	}
	
	public void save(VendorOsClass osType){
		logger.debug("Enter save..");
		vendorOSTypeDao.save(osType);
	}
	public void batchSave(List<VendorOsClass> list){
		vendorOSTypeDao.batchSave(list, 10);
	}
	public void delete(int id){
		logger.debug("Enter delete...");
		 vendorOSTypeDao.delete(id);
	}
	
	public List<VendorOsClass> getByVendorID(String vendorID){
		logger.debug("Enter getByVendorID..");
		String hql=" from VendorOsClass where vendorID=?";
		return vendorOSTypeDao.find(hql, Integer.parseInt(vendorID));
	}

	

}
