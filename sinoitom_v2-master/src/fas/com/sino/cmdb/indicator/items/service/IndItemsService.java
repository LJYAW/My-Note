/*
 * 文件名： IndItemsService.java
 * 
 * 创建日期： 2014-7-26
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.indicator.items.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.sino.base.common.util.ExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.indicator.items.dao.IndItemsDao;
import com.sino.cmdb.indicator.items.dao.IndicatorFieldsDao;
import com.sino.cmdb.indicator.items.entity.IndicatorFields;
import com.sino.cmdb.indicator.items.entity.IndicatorItems;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-7-26
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class IndItemsService {
	
	private static Logger logger = LoggerFactory.getLogger(IndItemsService.class);
	
	private static String objAttNames = "indItemID,indClassCode,indClassName,indGroupID,indGroupName,indItemName,indicatorItem,valueType,length,decimals,measureUnit,remark,status,fieldNames,fieldQty,fieldSpilt,kVSplit";
	private static String jsonAttNames = "id,indClassCode,indClassName,indGroupID,indGroupName,indItemName,indicatorItem,valueType,length,decimals,measureUnit,remark,status,fieldNames,fieldQty,fieldSpilt,kVSplit";
	//private static String excelAttNames = "indItemID@Cmdb_IndicatorClass,indClassCode,indClassName,indGroupID,indGroupName,indicatorItem,indItemName,valueType,length,decimals,measureUnit,remark";//导入映射字段
	private static String excelAttNames = "indItemID,indClassCode,indClassName,indGroupID,indGroupName,indicatorItem,indItemName,valueType,length,decimals,measureUnit,remark";
	@Autowired
	private IndItemsDao indicatorItemsDao;
	
	@Autowired
	private IndicatorFieldsDao indicatorFieldsDao;
	
	@Autowired
	private IndicatorFieldsService indicatorFieldsService;
	
	private String lastMassage = "";
	@Transactional(readOnly = true)
	public String getLastMassage() {
		return lastMassage;
	}
	@Transactional(readOnly = true)
	public List<IndicatorItems> getIndicator() {
		String hql = "from IndicatorItems order by indClassCode,indGroupName";
		return indicatorItemsDao.find(hql);
	}
	@Transactional(readOnly = true)
	public List<IndicatorItems> getIndClassCode() {
		String hql = "from IndicatorItems group by indClassCode";
		return indicatorItemsDao.find(hql);
	}
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<IndicatorItems> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	public List<IndicatorItems> getByIndClassCodeAndGroupID(int classCode,long indGroupdId){
		logger.debug("getByIndClassCode...");
		String hql=" from IndicatorItems where indClassCode=? and indGroupID=? order by indClassCode,indGroupName";
		return indicatorItemsDao.find(hql, classCode,indGroupdId);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final IndicatorItems obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	public IndicatorItems getById(long indItemID){
		logger.debug("enter getById...");
		return indicatorItemsDao.get(Long.parseLong(String.valueOf(indItemID)));
	}
	
	public void  save(IndicatorItems item){
		indicatorItemsDao.save(item);
	}
	public void batchSave(List<IndicatorItems> list){
		indicatorItemsDao.batchSave(list, 10);
	}
	
	public void  saveItemAndFields(IndicatorItems item,List<IndicatorFields> fieldslist){
		indicatorItemsDao.save(item);
		
		List<IndicatorFields> list = new ArrayList<IndicatorFields>();
		
		for (int i = 0; i < fieldslist.size(); i++) {
			IndicatorFields fields = new IndicatorFields();
			fields = fieldslist.get(i);
			fields.setIndItemID(item.getIndItemID());
			fields.setFieldNo(i+1);
			list.add(fields);
		}
		indicatorFieldsDao.batchSave(list, 10);
	}
	
	@Transactional(readOnly = true)
	public IndicatorItems getItems(String id) {
		return indicatorItemsDao.get(Long.parseLong(id));
	}
	
	public void deletes(String [] ids){
		logger.debug("Enter deletes...");
		for(String id:ids){
			delete(Long.parseLong(id));
		}
	}
	
	public void delete(Long id){
		logger.debug("Enter delete...");
		indicatorItemsDao.delete(id);
	}
	
	public void batchDelete(Long id,List<IndicatorFields> list){
		logger.debug("Enter delete...");
		indicatorItemsDao.delete(id);
		
		indicatorFieldsService.batchDelete(list);
	}
	
	public List<IndicatorItems> getByClassCode(int classCode) {
		String hql=" from IndicatorItems where indClassCode=? order by indClassCode,indGroupName";
		return indicatorItemsDao.find(hql, classCode);
	}
	public List<ResItemParam> getAllClassCode() {
		return indicatorItemsDao.getAllClassCode();
	}
	public List<ResItemParam> getTreeByClassCode(int parseInt) {
		return indicatorItemsDao.getByClassCode(parseInt);
	}
	
	public List<IndicatorItems> getByIndItemName(int classCode,Long indGroupID){
		logger.debug("getByIndItemName...");
		String hql=" from IndicatorItems where indClassCode=? and indGroupID=?";
		return indicatorItemsDao.find(hql, classCode,indGroupID);
	}
	public void saveitem(IndicatorItems item){
		
		 logger.debug("Enter save...");
		 indicatorItemsDao.save(item);
		 indicatorItemsDao.flush();
	}
	public void add(IndicatorItems item) throws SQLException{
		
		 logger.debug("Enter add...");
		 item.setIndItemID(null);
		 indicatorItemsDao.save(item);
		 //prodSnmpIndItemDao.addProdSnmpIndItems(item);
		 indicatorItemsDao.flush();
	}
	@Transactional(readOnly = true)
	public List<IndicatorItems> getAllItems(){
		logger.debug("getByIndItem...");
		
		return indicatorItemsDao.getAll();
	}
	public boolean importExcel(InputStream stream) throws SQLException{
		this.lastMassage = "";
		
		ExcelUtil<IndicatorItems> excelUtil = new ExcelUtil<IndicatorItems>(IndicatorItems.class);
		List<IndicatorItems> list = excelUtil.getObjListFrom(stream, excelAttNames);
	
		if( list.isEmpty() ){
			this.lastMassage = "导入失败！请检查导入文件数据！";
			System.out.println("导入失败");
			return false;
		}
		int addNum=0; 
		int updateNum=0;
		for(int i=0; i<list.size(); i++){
			IndicatorItems items = list.get(i);
			if(items.getIndItemID()==null){
				add(items);
				addNum++;
			}else{
				IndicatorItems item =getById(items.getIndItemID());
			if(item!=null){//存在 update
				saveitem(item);
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
	@Transactional(readOnly = true)
	public List<IndicatorItems> getAll() {
		String hql = "from IndicatorItems group by indClassCode order by indClassCode,indGroupName,indicatorItem asc ";
		return indicatorItemsDao.find(hql);
	}
	
	@Transactional(readOnly = true)
	public List<IndicatorItems> getAllArray2D() {
		String hql = "from IndicatorItems where valueType='Array2D' ";
		return indicatorItemsDao.find(hql);
	}
	
	public List<IndicatorItems> getItemsList(int devClassCode,int devTypeCode){
		List<IndicatorItems> list = indicatorItemsDao.getItemsList(devClassCode,devTypeCode);
		return list;
	}
	public List<IndicatorItems> getIndClass(int devClassCode,int devTypeCode){
		List<IndicatorItems> list = indicatorItemsDao.getIndClass(devClassCode,devTypeCode);
		return list;
	}
	public List<IndicatorItems> searchItemsData(int devClassCode,int devTypeCode,Long indGroupID,int indClassCode){
		String hql=" from IndicatorItems where indItemID not in (select indItemID from  DevIndicatorItems where devClassCode=? and devTypeCode=?) and indGroupID=? and indClassCode=?";
		return indicatorItemsDao.find(hql,devClassCode,devTypeCode,indGroupID,indClassCode);
	}

}
