/*
 * 文件名： VendorProdSeriesService.java
 * 
 * 创建日期： 2014-2-12
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.product.prodSeries.service;

import com.sino.base.common.util.ExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.product.prodSeries.dao.VendorProdSeriesDao;
import com.sino.cmdb.product.prodSeries.entity.VendorProdSeries;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-2-12
 */
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class VendorProdSeriesService {
	
	private static Logger logger = LoggerFactory.getLogger(VendorProdSeriesService.class);
	
    private static String objAttNames ="prodSeriesID,vendorID@CmdbVendor@vendorID@dispName,prodClassCode@IT_ResClass,prodTypeID@ProdType@prodTypeID@dispName,prodSeries,description,status";
	
	private static String jsonAttNames="id,vendorID,prodClassCode,prodTypeID,prodSeries,description,status";
	
	private static String excelAttNames = "prodSeriesID,vendorID,prodClassCode,prodTypeID,prodSeries,description";//导入字段
	@Autowired
	private VendorProdSeriesDao vendorProdSeriesDao;
	private String lastMassage = "";
	@Transactional(readOnly = true)
	public String getJsonObjStr(final VendorProdSeries obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<VendorProdSeries> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	public List<VendorProdSeries> getByVendorAndSerName(String vendorId,String seriesName){
		logger.debug("Enter getByVendorAndSerName...");
		String hql=" from VendorProdSeries where vendorID=? and prodSeries=?";
		return vendorProdSeriesDao.find(hql, Integer.parseInt(vendorId),seriesName);
	}
	
	public List<VendorProdSeries> getList(String vendorId,String prodClassMode,String prodTypeId){
		logger.debug("Enter getList...");
		String hql=" from VendorProdSeries where vendorID=? and prodClassCode=? and prodTypeID=? order by prodSeries";
		return vendorProdSeriesDao.find(hql, Integer.parseInt(vendorId),Integer.parseInt(prodClassMode),Integer.parseInt(prodTypeId));
	}
	
	
	public VendorProdSeries getById(String id){
		logger.debug("Enter getById..");
		return vendorProdSeriesDao.get(Integer.parseInt(id));
	}

	public List<VendorProdSeries> getByIds(String ids){
		logger.debug("Enter VendorProdSeriesService getByIds..");
		Collection<Integer> idList = null;
		if(StringUtils.isNotEmpty(ids)){
			if(ids.contains(",")){
				String[] idArray = ids.split(",");
				Integer[] idIntArray = new Integer[idArray.length];
				for (int i = 0 ; i< idArray.length;i++) {
					idIntArray[i] = Integer.parseInt(idArray[i]);
				}
				idList = new ArrayList<Integer>(idArray.length);
				Collections.addAll(idList,idIntArray);
			}
		}
		return vendorProdSeriesDao.get(idList);
	}

	public VendorProdSeries getSeriesById(Integer id){
		logger.debug("Enter getById..");
		return vendorProdSeriesDao.get(id);
	}
	
	public void delete(String id){
		logger.debug("Enter delete...");
		vendorProdSeriesDao.delete(Integer.parseInt(id));
	}
	
	public void deletes(String [] ids){
		logger.debug("Enter deletes...");
		for(String id:ids){
			delete(id);
		}
	}
	
	public void save(VendorProdSeries vendorProdSeries){
		logger.debug("Enter save...");
		vendorProdSeriesDao.save(vendorProdSeries);
		vendorProdSeriesDao.flush();
	}
	public void batchSave(List<VendorProdSeries> list){
		vendorProdSeriesDao.batchSave(list, 10);
	}
	public void add(VendorProdSeries vendorProdSeries){
		logger.debug("Enter save...");
		vendorProdSeries.setProdSeriesID(null);		
		vendorProdSeriesDao.save(vendorProdSeries);
		vendorProdSeriesDao.flush();
	}
	
	public List<VendorProdSeries> getAll(){
		String hql=" from VendorProdSeries order by vendorID,prodClassCode,prodTypeID,prodSeries";
		return vendorProdSeriesDao.find(hql);
	}

	public List<VendorProdSeries> getSeries(String id) {
		logger.debug("Enter getList...");
		String hql=" from VendorProdSeries where vendorID=? order by vendorID,prodClassCode,prodTypeID,prodSeries";
		return vendorProdSeriesDao.find(hql, Integer.parseInt(id));
	}

	public List<VendorProdSeries> getByVendorAndClassCode(String string,
			String string2) {
		logger.debug("Enter getList...");
		String hql=" from VendorProdSeries where vendorID=? and prodClassCode=? order by vendorID,prodClassCode,prodTypeID,prodSeries";
		return vendorProdSeriesDao.find(hql, Integer.parseInt(string),Integer.parseInt(string2));
	}

	public List<VendorProdSeries> getByProdTypeID(String string) {
		String hql=" from VendorProdSeries where prodTypeID=? order by vendorID,prodClassCode,prodTypeID,prodSeries";
		return vendorProdSeriesDao.find(hql, Integer.parseInt(string));
	}

	public List<ResItemParam> getTreeByVendor() {
		return vendorProdSeriesDao.getVendor();
	}

	public List<ResItemParam> getTreeByVendorAndClass(int parseInt) {
		return vendorProdSeriesDao.getByVendor(parseInt);
	}

	public List<ResItemParam> getTreeByClassCode(int parseInt, int parseInt2) {
		return vendorProdSeriesDao.getByClassCode(parseInt,parseInt2);
	}
	@Transactional(readOnly = true)
	public String getLastMassage() {
		return lastMassage;
	}
	public boolean importExcel(InputStream stream){
		this.lastMassage = "";
		
		ExcelUtil<VendorProdSeries> excelUtil = new ExcelUtil<VendorProdSeries>(VendorProdSeries.class);
		List<VendorProdSeries> list = excelUtil.getObjListFrom(stream, excelAttNames);
	
		if( list.isEmpty() ){
			this.lastMassage = "导入失败！请检查导入文件数据！";
			System.out.println("导入失败");
			return false;
		}
		int addNum=0; 
		int updateNum=0;
		for(int i=0; i<list.size(); i++){
			VendorProdSeries model = list.get(i);
			if(model.getProdSeriesID()==null){
				add(model);
				addNum++;
			}else{
			VendorProdSeries seriesModel =getSeriesById(model.getProdSeriesID());
	       
			if(seriesModel!=null){//存在 update

				
				save(model);
				updateNum++;
			}else{//不存在add
				
				add(model);
				
				addNum++;
			}
		  }

		}
		
		this.lastMassage = String.format("导入完毕！更新%d条记录,新增%d条记录。",updateNum, addNum);
		
		return true;
	}


}
