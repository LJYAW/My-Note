/*
 * 文件名： ProdIndItemService.java
 * 
 * 创建日期： 2014-7-26
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.indicator.product.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.ExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.indicator.group.entity.IndicatorGroup;
import com.sino.cmdb.indicator.product.dao.ProdSnmpIndItemDao;
import com.sino.cmdb.indicator.product.entity.ProdSnmpIndItems;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-7-26
 */
@Service
@Transactional
public class ProdSnmpIndItemService {
	
	private static Logger logger = LoggerFactory.getLogger(ProdSnmpIndItemService.class);
	
	private static String objAttNames ="prodIndItemID,vendorID,vendorID@CmdbVendor@vendorID@dispName,prodModelID,prodModel,modelOID,devClassCode,devClassName,devTypeCode,devTypeName,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemName,indicatorItem,snmpObjName,snmpOID,getMethod,oidFlag,valueType,length,decimals,measureUnit,remark,status";
	private static String jsonAttNames ="id,vendorID,vendorName,prodModelID,prodModel,modelOID,devClassCode,devClassName,devTypeCode,devTypeName,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemName,indicatorItem,snmpObjName,snmpOID,getMethod,oidFlag,valueType,length,decimals,measureUnit,remark,status";		
	private static String excelAttNames="prodIndItemID,vendorID,prodModelID,prodModel,modelOID,devClassCode,devClassName,devTypeCode,devTypeName,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemName,indicatorItem,snmpObjName,snmpOID,getMethod,oidFlag,valueType,length,decimals,measureUnit,remark,status";

	@Autowired
	private ProdSnmpIndItemDao prodSnmpIndItemDao;
	private String lastMassage = "";
	@Transactional(readOnly = true)
	public String getLastMassage() {
		return lastMassage;
	}
	@Transactional(readOnly = true)
	public List<ProdSnmpIndItems> getAllProdIndItem() {
		logger.debug("getAllProdIndItem...");
		
		return prodSnmpIndItemDao.getAll();
	}
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ProdSnmpIndItems> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	public void save(ProdSnmpIndItems item){
		
		 logger.debug("Enter save...");
		 prodSnmpIndItemDao.save(item);
		 prodSnmpIndItemDao.flush();
	}
	public void add(ProdSnmpIndItems item) throws SQLException{
		
		 logger.debug("Enter add...");
		 item.setProdIndItemID(null);
		 prodSnmpIndItemDao.save(item);
		 //prodSnmpIndItemDao.addProdSnmpIndItems(item);
		 prodSnmpIndItemDao.flush();
	}
	@Transactional(readOnly = true)
	public String getJsonObjStr(final ProdSnmpIndItems obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	public ProdSnmpIndItems getById(int id){
		logger.debug("Enter getById...");
		return prodSnmpIndItemDao.get(id);
	}
	public void batchSave(List<ProdSnmpIndItems> items){
		logger.debug("Enter batchSave...");
		prodSnmpIndItemDao.batchSave(items, 20);
	}
	
	public void delete(int id){
		logger.debug("Enter delete...");
		prodSnmpIndItemDao.delete(id);
	}
	
	public void batchAudit(List<Integer> ids){
		logger.debug("Enter batchAudit...");
		prodSnmpIndItemDao.batchAudit(ids);
	}
	
	public void deletes(String [] ids){
		logger.debug("Enter deletes...");
		for(String id:ids){
			delete(Integer.parseInt(id));
		}
	}
	
	
	
	public List<ProdSnmpIndItems> getByDevTypeCode(String devTypeCode){
		logger.info("Enter getByDevTypeCode..");
		String hql=" from ProdSnmpIndItems where status=1 and (devTypeCode= -1 or devTypeCode=?) and indClassCode=8 order by indicatorItem";
		return prodSnmpIndItemDao.find(hql, Integer.parseInt(devTypeCode));
	}
	
	public List<ProdSnmpIndItems> getByIndClass(){
		logger.info("Enter getByDevTypeCode..");
		String hql=" from ProdSnmpIndItems where status=1 and indClassCode=9 order by indicatorItem";
		return prodSnmpIndItemDao.find(hql);
	}
	
	
	public List<ProdSnmpIndItems> getIfIndItemsByVendorAndmodelOID(String vendorID,String modelOID){
		logger.info("Enter getByVendorAndMOID..");
		String hql=" from ProdSnmpIndItems where vendorID=? and indClassCode=8 and (modelOID='X' or modelOID=?) order by indicatorItem";
		return prodSnmpIndItemDao.find(hql, Integer.parseInt(vendorID),modelOID);
	}
	
	public List<ProdSnmpIndItems> getIfIndItemsByVendorAndmodelOIDAndItemName(String vendorID,String modelOID){
		logger.info("Enter getIfIndItemsByVendorAndmodelOIDAndItemName..");
		String hql=" from ProdSnmpIndItems where vendorID=? and indClassCode=8 and (modelOID='X' or modelOID=?) and indicatorItem like 'Line_BwUtilRatio' order by indicatorItem";
		return prodSnmpIndItemDao.find(hql, Integer.parseInt(vendorID),modelOID);
	}
	public List<ProdSnmpIndItems> getByVendorId(int vendor) {
		String hql=" from ProdSnmpIndItems where vendorID=? order by vendorID,devClassCode,devTypeCode";
		return prodSnmpIndItemDao.find(hql, vendor);
	}
	public List<ProdSnmpIndItems> getByVendorAndClass(int vendor,int classCode) {
		String hql=" from ProdSnmpIndItems where vendorID=? and devClassCode=? order by vendorID,devClassCode,devTypeCode";
		return prodSnmpIndItemDao.find(hql, vendor,classCode);
	}
	public List<ProdSnmpIndItems> getByVendorAndClassAndType(int vendorID,int devClassCode, int devTypeCode) {
		String hql=" from ProdSnmpIndItems where vendorID=? and devClassCode=? and devTypeCode=? order by vendorID,devClassCode,devTypeCode ";
		return prodSnmpIndItemDao.find(hql, vendorID,devClassCode,devTypeCode);
	}
	public List<ProdSnmpIndItems> getAllOrderByVendor() {
		String hql=" from ProdSnmpIndItems order by vendorID,devClassCode,devTypeCode";
		return prodSnmpIndItemDao.find(hql);
	}
	public List<ResItemParam> getTreeByVendor() {
		return prodSnmpIndItemDao.getVendor();
	}
	public List<ResItemParam> getTreeVendor(int parseInt) {
		return prodSnmpIndItemDao.getByVendor(parseInt);
	}
	public List<ResItemParam> getTreeByClassCode(int parseInt, int parseInt2) {
		return prodSnmpIndItemDao.getByClassCode(parseInt,parseInt2);
	}
	public boolean importExcel(InputStream stream) throws SQLException{
		this.lastMassage = "";
		
		ExcelUtil<ProdSnmpIndItems> excelUtil = new ExcelUtil<ProdSnmpIndItems>(ProdSnmpIndItems.class);
		List<ProdSnmpIndItems> list = excelUtil.getObjListFrom(stream, excelAttNames);
	
		if( list.isEmpty() ){
			this.lastMassage = "导入失败！请检查导入文件数据！";
			System.out.println("导入失败");
			return false;
		}
		int addNum=0; 
		int updateNum=0;
		for(int i=0; i<list.size(); i++){
			ProdSnmpIndItems items = list.get(i);
			if(items.getProdIndItemID()==null){
				add(items);
				addNum++;
			}else{
			ProdSnmpIndItems ProdSnmpIndItems =getById(items.getProdIndItemID());
			if(ProdSnmpIndItems!=null){//存在 update
				save(items);
				updateNum++;
			}else{//不存在add
				add(items);
				addNum++;
			}
			}
		}
		this.lastMassage = String.format("导入完毕！更新%d条记录,新增%d条记录。",updateNum, addNum);
		
		return true;
	}

}
