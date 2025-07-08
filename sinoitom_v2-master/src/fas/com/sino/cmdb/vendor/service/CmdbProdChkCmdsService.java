package com.sino.cmdb.vendor.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.dao.CmdbProdChkCmdsDao;
import com.sino.cmdb.vendor.entity.CmdbProdChkCmds;

@Service
@Transactional
public class CmdbProdChkCmdsService {
	
	private static Logger logger = LoggerFactory.getLogger(CmdbProdChkCmdsService.class);

	private static String objAttNames = "prodChkCmdId,vendorId,vendorId@CmdbVendor@vendorID@vendorName,devClassCode,devClassName,devTypeCode,devTypeName,prodModelId,prodModel,modelOID,osType,osVersion,osFeature,checkCmd,cmdUsage,resultSample,remark,status";

	private static String jsonAttNames = "id,vendorId,vendorName,devClassCode,devClassName,devTypeCode,devTypeName,prodModelId,prodModel,modelOID,osType,osVersion,osFeature,checkCmd,cmdUsage,resultSample,remark,status";

	@Autowired
	private CmdbProdChkCmdsDao cmdbProdChkCmdsDao;
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final CmdbProdChkCmds obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<CmdbProdChkCmds> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	public void save(CmdbProdChkCmds entity){
		logger.info("save Entity");
		cmdbProdChkCmdsDao.save(entity);
	}
	
	@Transactional(readOnly = true)
	public CmdbProdChkCmds getCmdbProdChkCmdsById(Integer id){
		return cmdbProdChkCmdsDao.get(id);
	}
	public void delete(Integer id){
		cmdbProdChkCmdsDao.delete(id);
	}
	
	public void batchSave(List<CmdbProdChkCmds> list){
		cmdbProdChkCmdsDao.batchSave(list, 10);
	}
	
	public List<ResItemParam> getTreeByVendor() {
		return cmdbProdChkCmdsDao.getVendor();
	}
	
	public List<ResItemParam> getTreeVendor(int parseInt) {
		return cmdbProdChkCmdsDao.getByVendor(parseInt);
	}
	
	public List<ResItemParam> getTreeByClassCode(int parseInt, int parseInt2) {
		return cmdbProdChkCmdsDao.getByClassCode(parseInt,parseInt2);
	}
	
	public void deletes(String [] ids){
		logger.debug("Enter deletes...");
		for(String id:ids){
			delete(Integer.parseInt(id));
		}
	}
	
	
	
	
	
	//获取所有
	public List<CmdbProdChkCmds> getAll() {
		List<CmdbProdChkCmds> list = cmdbProdChkCmdsDao.getAll();
		return list;
	}
	
		public List<CmdbProdChkCmds> getByVerdorID(int vendorId) {
			String hql=" from CmdbProdChkCmds where vendorId=?";
			List<CmdbProdChkCmds> list = cmdbProdChkCmdsDao.find(hql, vendorId);
			return list;
		}
		
		
		
		public List<CmdbProdChkCmds> getByVerdorIDAndClassCode(int vendorId,int devClassCode) {
			String hql=" from CmdbProdChkCmds where vendorId=? and devClassCode=?";
			List<CmdbProdChkCmds> list = cmdbProdChkCmdsDao.find(hql, vendorId,devClassCode);
			return list;
		}
		
		public List<CmdbProdChkCmds> getByVerdorIDAndClassCodeAndDevType(int vendorId,int devClassCode,int devType) {
			String hql=" from CmdbProdChkCmds where vendorId=? and devClassCode=? and devTypeCode=?";
			List<CmdbProdChkCmds> list = cmdbProdChkCmdsDao.find(hql, vendorId,devClassCode,devType);
			return list;
		}
		
		public List<CmdbProdChkCmds> getOsFeature(int vendorId,int devClassCode,int devTypeCode,String modelOID,String osType,String osVersion) {
			String hql=" from CmdbProdChkCmds where vendorId=? and devClassCode=? and devTypeCode=? and modelOID=? and osType=? and osVersion=? group by osFeature";
			List<CmdbProdChkCmds> list = cmdbProdChkCmdsDao.find(hql, vendorId,devClassCode,devTypeCode,modelOID,osType,osVersion);
			return list;
		}
		
		public List<CmdbProdChkCmds> getCheckCmdAndCmdUsage(int vendorId,int devClassCode,int devTypeCode,String modelOID,String osType,String osVersion,String osFeature) {
			String hql=" from CmdbProdChkCmds where vendorId=? and devClassCode=? and devTypeCode=? and modelOID=? and osType=? and osVersion=? and osFeature=?";
			List<CmdbProdChkCmds> list = cmdbProdChkCmdsDao.find(hql, vendorId,devClassCode,devTypeCode,modelOID,osType,osVersion,osFeature);
			return list;
		}
	
}
