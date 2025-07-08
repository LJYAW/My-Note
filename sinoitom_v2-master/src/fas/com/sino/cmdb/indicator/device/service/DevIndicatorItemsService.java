/*
 * 文件名： DevIndicatorItemsService.java
 * 
 * 创建日期： 2014-8-28
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.indicator.device.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.ack.devCheckItem.entity.AckDevCheckItems;
import com.sino.ack.resources.entity.AckResources;
import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.ExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.indicator.cmdCheckItems.entity.CmdbProdCmdChkItems;
import com.sino.cmdb.indicator.device.dao.DevIndicatorItemsDao;
import com.sino.cmdb.indicator.device.entity.DevIndicatorItems;
import com.sino.cmdb.indicator.items.entity.IndicatorItems;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-8-28
 */
@Service
@Transactional
public class DevIndicatorItemsService {

	private static Logger logger = LoggerFactory.getLogger(DevIndicatorItemsService.class);
	private static String objAttNames ="devIndItemID,devClassCode,devClassName,devTypeCode,devTypeName,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemName,indicatorItem,valueType,length,decimals,measureUnit,remark,status";
	private static String jsonAttNames ="id,devClassCode,devClassName,devTypeCode,devTypeName,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemName,indicatorItem,valueType,length,decimals,measureUnit,remark,status";		
	private static String excelAttNames = "devIndItemID,devClassCode,devClassName,devTypeCode,devTypeName,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemName,indicatorItem,valueType,length,decimals,measureUnit,remark";
	@Autowired
	private DevIndicatorItemsDao devIndicatorItemsDao;
	private String lastMassage = "";
	@Transactional(readOnly = true)
	public String getLastMassage() {
		return lastMassage;
	}
	
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<DevIndicatorItems> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final DevIndicatorItems obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	public void delete(int id){
		logger.debug("Enter delete...");
		devIndicatorItemsDao.delete(id);
	}
	
	public void deletes(String [] ids){
		logger.debug("Enter deletes...");
		for(String id:ids){
			delete(Integer.parseInt(id));
		}
	}
	
	public List<DevIndicatorItems> getAll(){
		logger.debug("Enter getAll...");
		String hql=" from DevIndicatorItems order by devTypeCode";
		return devIndicatorItemsDao.find(hql);
	}
	public DevIndicatorItems getById(String id){
		logger.debug("Enter getById...");
		return devIndicatorItemsDao.get(Integer.parseInt(id));
	}
	public DevIndicatorItems getByItemsId(Integer id){
		logger.debug("Enter getById...");
		return devIndicatorItemsDao.get(id);
	}
	
	public void save(DevIndicatorItems item){
		logger.debug("save...");
		devIndicatorItemsDao.save(item);
	}
	
	public void batchSave(List<DevIndicatorItems> items){
		devIndicatorItemsDao.batchSave(items, 20);
	}

	@Transactional(readOnly = true)
	public List<DevIndicatorItems> getByTypeCode(int code){
		logger.debug("Enter getByTypeCode...");
		String hql=" from DevIndicatorItems where devTypeCode=?";
		return devIndicatorItemsDao.find(hql, code);
	}

	@Transactional(readOnly = true)
	public List<DevIndicatorItems> getByClassAndTypeAndGroup(int parseInt,int code,Long resIP) {
		logger.debug("Enter getByClassAndTypeAndGroup...");
		String hql=" from DevIndicatorItems where devClassCode=? and devTypeCode=? and indGroupID=? order by devClassCode,devTypeCode,indGroupID ";
		return devIndicatorItemsDao.find(hql,parseInt, code,resIP);
	}

	@Transactional(readOnly = true)
	public List<DevIndicatorItems> getByClassCode(int parseInt) {
		logger.debug("Enter getByClassCode...");
		String hql=" from DevIndicatorItems where devClassCode=? order by devTypeCode";
		return devIndicatorItemsDao.find(hql, parseInt);
	}
	
	@Transactional(readOnly = true)
	public Integer getIndClassCodeByIndGroupID(int parseInt,int code,long indGroupID) {
		logger.debug("Enter getIndClassCodeByIndGroupID...");
		String hql="select indClassCode from DevIndicatorItems where devClassCode=? and devTypeCode=?and indGroupID=? group by indClassCode";
		List<Integer> list=devIndicatorItemsDao.find(hql, parseInt,code,indGroupID);
		
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
		
		
	}
	@Transactional(readOnly = true)
	public List<DevIndicatorItems> getByClassAndType(int parseInt,int parseLong) {
		logger.debug("Enter getByClassAndType...");
		String hql=" from DevIndicatorItems where devClassCode=? and devTypeCode=? order by devClassCode,devTypeCode ";
		return devIndicatorItemsDao.find(hql, parseInt,parseLong);
	}
	
	public List<ResItemParam> getTreeByDevClassCodes() {
		return devIndicatorItemsDao.getDevClassCodes();
	}

	public List<ResItemParam> getTreeByDevTypeCodes(int parseInt) {
		return devIndicatorItemsDao.getDevTypeCodes(parseInt);
	}

	public List<ResItemParam> getTreeByIndGroupName(int parseInt) {
		return devIndicatorItemsDao.getIndGroupName(parseInt);
	}
	public void saveitem(DevIndicatorItems item){
		
		 logger.debug("Enter save...");
		 devIndicatorItemsDao.save(item);
		 devIndicatorItemsDao.flush();
	}
	public void add(DevIndicatorItems item) throws SQLException{
		
		 logger.debug("Enter add...");
		 item.setDevIndItemID(null);
		 devIndicatorItemsDao.save(item);
		
		 devIndicatorItemsDao.flush();
	}
	
	public List<DevIndicatorItems> getIndGroupList(int devClassCode,int devTypeCode,int indClassCode){
		String hql="from DevIndicatorItems where devClassCode=? and devTypeCode=? and indClassCode=? GROUP BY IndGroupID";
		List<DevIndicatorItems> list=devIndicatorItemsDao.find(hql, devClassCode,devTypeCode,indClassCode);
		return list;
	}
	
	public List<DevIndicatorItems> getIndItemList(int devClassCode,int devTypeCode,int indClassCode,long indGroupID){
		String hql="from DevIndicatorItems where devClassCode=? and devTypeCode=? and indClassCode=? and indGroupID=? order by indItemName";
		List<DevIndicatorItems> list=devIndicatorItemsDao.find(hql, devClassCode,devTypeCode,indClassCode,indGroupID);
		return list;
	}
	
	
	public boolean importExcel(InputStream stream) throws SQLException{
		this.lastMassage = "";
		
		ExcelUtil<DevIndicatorItems> excelUtil = new ExcelUtil<DevIndicatorItems>(DevIndicatorItems.class);
		List<DevIndicatorItems> list = excelUtil.getObjListFrom(stream, excelAttNames);
	
		if( list.isEmpty() ){
			this.lastMassage = "导入失败！请检查导入文件数据！";
			System.out.println("导入失败");
			return false;
		}
		int addNum=0; 
		int updateNum=0;
		for(int i=0; i<list.size(); i++){
			DevIndicatorItems items = list.get(i);
			if(items.getDevIndItemID()==null){
				add(items);
				addNum++;
			}else{
			DevIndicatorItems item =getByItemsId(items.getDevIndItemID());
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

	public void saveItems(Integer devClassCode,String devClassName,Integer devTypeCode,String devTypeName,List<IndicatorItems> itemList){
		List<DevIndicatorItems> deviceList=new ArrayList<DevIndicatorItems>();
		
		
			
			for(int i=0;i<itemList.size();i++){
				IndicatorItems item=itemList.get(i);
				
				DevIndicatorItems device=new DevIndicatorItems();
//				BeanUtils.copyProperties(item, ind);
				device.setDevClassCode(devClassCode);
				device.setDevClassName(devClassName);
				device.setDevTypeCode(devTypeCode);
				device.setDevTypeName(devTypeName);
				device.setIndClassCode(item.getIndClassCode());
				device.setIndClassName(item.getIndClassName());
				device.setIndGroupID(item.getIndGroupID());
				device.setIndGroupName(item.getIndGroupName());
				device.setIndItemID(item.getIndItemID());
				device.setIndItemName(item.getIndItemName());
				device.setIndicatorItem(item.getIndicatorItem());
				device.setValueType(item.getValueType());
				device.setLength(item.getLength());
				device.setDecimals(item.getDecimals());
				device.setMeasureUnit(item.getMeasureUnit());
				device.setRemark(item.getRemark());
				deviceList.add(device);
					
				
			}
			devIndicatorItemsDao.batchSave(deviceList, 20);
	}
}
