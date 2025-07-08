/*
 * 文件名： VendorProdModelService.java
 * 
 * 创建日期： 2014-2-12
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.product.prodModel.service;

import com.sino.base.common.util.ExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.product.prodModel.dao.VendorProdModelDao;
import com.sino.cmdb.product.prodModel.entity.VendorProdModel;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartlink.utils.Util;

import java.io.InputStream;
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
@Transactional
public class VendorProdModelService {
	
	
    private static Logger logger = LoggerFactory.getLogger(VendorProdModelService.class);
	
	private static String objAttNames ="prodModelID,vendorID,vendorName,prodClassCode,prodClassName,prodTypeID@ProdType@prodTypeID@dispName,prodTypeCode,prodTypeName,prodSeries,prodModelName,prodModelOid,description,status";
	
	private static String jsonAttNames ="id,vendorID,vendorName,prodClassCode,prodClassName,prodTypeID,prodTypeCode,prodTypeName,prodSeries,prodModelName,prodModelOid,description,status";
	
	private static String objMinAttNames = "prodModelID,prodModelName";
	
	private static String jsonMinAttNames = "id,modelName";
	
	@Autowired
	private VendorProdModelDao vendorProdModelDao;
	
	private String lastMassage = "";
	
	private static String excelAttNames = "prodModelID,vendorID,prodClassCode,prodClassName,prodTypeID,prodTypeCode,prodTypeName,prodSeries,prodModelName,prodModelOid,description,status";

	@Transactional(readOnly = true)
	public String getJsonObjStr(final VendorProdModel obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<VendorProdModel> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	public void delete(String id){
		logger.debug("Enter delete...");
		vendorProdModelDao.delete(Long.parseLong(id));
	}
	public void deletes(String [] ids){
		logger.debug("Enter deletes...");
		for(String id:ids){
			delete(id);
		}
	}
	public VendorProdModel getById(String id){
		logger.debug("Enter getById..");
		return vendorProdModelDao.get(Long.parseLong(id));
	}
	
	public List<VendorProdModel> getByVendorAndModelName(String vendorId,String prodModel){
		logger.debug("Enter getByVendorAndModelName...");
		String hql=" from VendorProdModel where vendorID=? and prodModelName=? order by prodModelName ";
		return vendorProdModelDao.find(hql, Integer.parseInt(vendorId),prodModel);
	}
	
	public VendorProdModel getByModelOID(String modelOID){
		String hql=" from VendorProdModel where prodModelOid=?";
		List<VendorProdModel> list=vendorProdModelDao.find(hql, modelOID);
		if(!list.isEmpty()){
			return (VendorProdModel) vendorProdModelDao.find(hql, modelOID).get(0);
		}else{
			return null;
		}
	}
	
	
	public void save(VendorProdModel prodModel){
		logger.debug("Enter save...");
		vendorProdModelDao.save(prodModel);
		vendorProdModelDao.flush();
	}
	public void batchSave(List<VendorProdModel> list){
		vendorProdModelDao.batchSave(list, 10);
		vendorProdModelDao.flush();
	}
	public List<VendorProdModel> getAll(){
		String hql="from VendorProdModel order by vendorID,prodClassCode,prodTypeCode,prodSeries,prodModelName";
    	return vendorProdModelDao.find(hql);
	}
	
	public List<VendorProdModel> getList(){
		String hql="from VendorProdModel order by vendorID,prodClassCode,prodTypeCode,prodModelOid";
		return vendorProdModelDao.find(hql);
	}
	
	public List<VendorProdModel> getAllOrderName(){
		logger.debug("Enter getAll....");
		return vendorProdModelDao.getAll("prodClassName", true);
	}
	
    public List<VendorProdModel> getDataByVendorID(Integer id){
    	String hql="from VendorProdModel where vendorID=? order by vendorID,prodClassCode,prodTypeCode,prodSeries,prodModelName";
    	return vendorProdModelDao.find(hql,id);
    }
    
    @Transactional(readOnly = true)
	public List<VendorProdModel> getVendorDevModel(String vendorId,String devTypeId) {
		String hql = "from VendorProdModel where vendorID=? and prodTypeCode =?  order by prodClassName";
		return vendorProdModelDao.find(hql,Integer.valueOf(vendorId),Integer.valueOf(devTypeId));
	}
    
    @Transactional(readOnly = true)
	public String getJsonMinListStr(final List<VendorProdModel> list) {
		return JsonUtils.getJsonListInfo(list, objMinAttNames, jsonMinAttNames);
	}
    
    
    public List<VendorProdModel> getDataByVendorIDAndDevTypeCode(Integer id,Integer typeCode){
    	
    	String hql="";
    	if(typeCode==-1){
    		hql="from VendorProdModel where vendorID=? order by prodModelName";
    		return vendorProdModelDao.find(hql,id);
    	}else{
    		hql="from VendorProdModel where vendorID=? and prodTypeCode=? order by prodModelName";
    		return vendorProdModelDao.find(hql,id,typeCode);
    	}
    	
    }
    
    public List<VendorProdModel> getData(Integer vendorId,Integer prodClassCode,Integer prodType){
    	String hql="from VendorProdModel where vendorID=? and prodClassCode=? and prodTypeID=? order by vendorID,prodClassCode,prodTypeCode,prodModelName";
    	return vendorProdModelDao.find(hql,vendorId,prodClassCode,prodType);
    }
    
    public List<VendorProdModel> getModelData(Integer vendorId,Integer prodClassCode,Integer prodType,String prodSeries){
    	String hql="from VendorProdModel where vendorID=? and prodClassCode=? and prodTypeID=? and prodSeries=? order by vendorID,prodClassCode,prodTypeCode ";
    	return vendorProdModelDao.find(hql,vendorId,prodClassCode,prodType,prodSeries);
    }


	public List<VendorProdModel> getModelById(String id) {
		String hql=" from VendorProdModel where vendorID=? order by prodModelName";
		return vendorProdModelDao.find(hql,Integer.parseInt(id));
	}
	
	public boolean importExcel(InputStream stream){
		this.lastMassage = "";
		
		ExcelUtil<VendorProdModel> excelUtil = new ExcelUtil<VendorProdModel>(VendorProdModel.class);
		List<VendorProdModel> list = excelUtil.getObjListFrom(stream, excelAttNames);
		if( list==null || list.size()==0 ){
			this.lastMassage = "导入失败！请检查导入文件数据！";
			return false;
		}
		int addNum=0; 
		int updateNum=0;
		for(int i=0; i<list.size(); i++){
			VendorProdModel model = list.get(i);
			VendorProdModel saveProdModel =null;
			VendorProdModel prodModel=getByModelOID(model.getProdModelOid());
			if(prodModel!=null){//存在 update
				saveProdModel=model;
				saveProdModel.setProdModelID(this.toCRC(model.getProdModelOid().trim()));
				save(saveProdModel);
				updateNum++;
			}else{//不存在add
				saveProdModel=model;
				add(saveProdModel);
				addNum++;
			}

		}
		this.lastMassage = String.format("导入完毕！更新%d条记录,新增%d条记录。",updateNum, addNum);
		return true;
	}
	
	@Transactional(readOnly = true)
	public String getLastMassage() {
		return lastMassage;
	}

	public boolean checkCRC(Long oid,String newoid){
		if(oid.equals(Util.getCRC32(newoid))){
			return false;
		}else{
			return true;
		}
		
	}

	public void add(VendorProdModel entity) {
		logger.info("save Entity");
		String trimOid = entity.getProdModelOid().trim();
		entity.setProdModelID(Util.getCRC32(trimOid));
		vendorProdModelDao.save(entity);
		
	}


	public Long toCRC(String modelOID) {
		return Util.getCRC32(modelOID);
	}


	public List<VendorProdModel> getByVendorAndClassCode(int i,
			int j) {
		String hql="from VendorProdModel where vendorID=? and prodClassCode=? order by vendorID,prodClassCode,prodTypeCode,prodSeries,prodModelName";
    	return vendorProdModelDao.find(hql,i,j);
	}


	public List<VendorProdModel> getByVendorAndClassCodeAndTypeCode(
			int parseInt, int parseInt2, int parseInt3) {
		String hql="from VendorProdModel where vendorID=? and prodClassCode=? and prodTypeCode=? order by vendorID,prodClassCode,prodTypeCode,prodSeries,prodModelName";
    	return vendorProdModelDao.find(hql,parseInt,parseInt2,parseInt3);
	}


	public List<VendorProdModel> getByVenAndClassAndTypeAndSeries(int parseInt,
			int parseInt2, int parseInt3, String string) {
		String hql="from VendorProdModel where vendorID=? and prodClassCode=? and prodTypeCode=? and prodSeries=? order by vendorID,prodClassCode,prodTypeCode,prodSeries,prodModelName";
    	return vendorProdModelDao.find(hql,parseInt,parseInt2,parseInt3,string);
	}
	
	public List<ResItemParam> getVendor(){
		return vendorProdModelDao.getVendor();
	}
	
	public List<ResItemParam> getByVendor(int vendorID){
		return vendorProdModelDao.getByVendor(vendorID);
	}
	
	public List<ResItemParam> getByClassCode(int vendorID, int prodClassCode){
		return vendorProdModelDao.getByClassCode(vendorID,prodClassCode);
	}
	
	public List<ResItemParam> getBySeries(int vendorID, int prodClassCode,int typeCode){
		return vendorProdModelDao.getBySeries(vendorID,prodClassCode,typeCode);
	}
	
}
