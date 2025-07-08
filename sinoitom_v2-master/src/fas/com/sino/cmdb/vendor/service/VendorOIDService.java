/*
 * 文件名： VendorOIDService.java
 * 
 * 创建日期： 2014-8-19
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
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
import com.sino.cmdb.vendor.dao.VendorOIDDao;
import com.sino.cmdb.vendor.entity.CmdbProdChkCmds;
import com.sino.cmdb.vendor.entity.ParamVendorOID;
import com.sino.cmdb.vendor.entity.VendorOID;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-8-19
 */
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class VendorOIDService {
	private static Logger logger = LoggerFactory.getLogger(CmdbVendorService.class);

	private static String objAttNames = "vendorOID,vendorID,remark,oidFlag,status";

	private static String jsonAttNames = "id,vendorID,remark,oidFlag,status";

	private static String objParamAttNames = "vendorOID,vendorName,dispName,vendorID,remark,oidFlag,status";
	
	private static String jsonParamAttNames = "id,vendorName,dispName,vendorOID,remark,oidFlag,status";
	
	@Autowired
	private VendorOIDDao vendorOIDDao;
	
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<VendorOID> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	public void save(VendorOID entity){
		logger.info("save Entity");
		vendorOIDDao.save(entity);
	}
	
	public List<ParamVendorOID> getVendorById(String id) {
		logger.debug("Enter getDataByIds...");
		List<ParamVendorOID> dataById = vendorOIDDao.getDataById(id);
		return dataById;
	}
	@Transactional(readOnly = true)
	public String getParamOIDJsonListStr(final List<ParamVendorOID> list) {
		return JsonUtils.getJsonListInfo(list, objParamAttNames, jsonParamAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final VendorOID obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public VendorOID getVendorOIDById(String id){
		return vendorOIDDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public VendorOID getByVendorID(String id){
		String hql=" from VendorOID where vendorID=?";
		return vendorOIDDao.findUnique(hql, Integer.parseInt(id));
	}
	
	@Transactional(readOnly = true)
	public VendorOID getByVendorOID(String oid){
		String hql=" from VendorOID where vendorOID=?";
		return vendorOIDDao.findUnique(hql, oid);
	}
	
	public void delete(String id){
		vendorOIDDao.delete(id);
	}
	public void update(VendorOID saveVendorOID) {
		vendorOIDDao.update(saveVendorOID);
		
	}
	public void batchSave(List<VendorOID> list){
		vendorOIDDao.batchSave(list, 10);
	}
}
