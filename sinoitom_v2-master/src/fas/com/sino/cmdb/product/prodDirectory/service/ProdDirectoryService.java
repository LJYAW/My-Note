/*
 * 文件名： ProdTypeService.java
 * 
 * 创建日期： 2014-1-16
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.cmdb.product.prodDirectory.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.product.prodDirectory.dao.ProdDirectoryDao;
import com.sino.cmdb.product.prodDirectory.entity.ProdDirectory;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.3 $
 *
 * @since 2014-1-16
 */
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ProdDirectoryService {
	
private static Logger logger = LoggerFactory.getLogger(ProdDirectoryService.class);

	private static String objAttNames ="prodModelID,vendorID@CmdbVendor@vendorID@dispName,vendorName,productID,prodClassID,prodClassCode,prodClassName,prodTypeID@ProdType@prodTypeID@dispName,prodTypeCode,prodTypeName,prodSeries,prodModelID,prodModelName,prodModelOID,productNo,productName,prodModality,prodPrice,currencyType,description,status";

	private static String jsonAttNames ="prodModelID,vendorID,vendorName,id,prodClassID,prodClassCode,prodClassName,prodTypeID,prodTypeCode,prodTypeName,prodSeries,prodModelID,prodModelName,prodModelOID,productNo,productName,prodModality,prodPrice,currencyType,description,status";

	private static String objPAttNames="id,text";
	private static String jsonPAttNames="id,text";

	@Autowired
	private ProdDirectoryDao prodDirectoryDao;


	public void save(ProdDirectory prod){
		logger.debug("Enter save..");
		prodDirectoryDao.save(prod);
		prodDirectoryDao.flush();
	}
	public void add(ProdDirectory prod){
		logger.debug("Enter add..");
		prod.setProductID(UUID.randomUUID().toString());
		prod.setStatus(0);
		prodDirectoryDao.save(prod);
		prodDirectoryDao.flush();
	}
	
	public List<ProdDirectory> getAll(){
		String hql=" from ProdDirectory order by vendorID,prodClassCode ";
		return prodDirectoryDao.find(hql);
	}
	
	public void delete(String id){
		logger.debug("Enter delete..");
		prodDirectoryDao.delete(id);
		prodDirectoryDao.flush();
	}
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ProdDirectory> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	public List<ResItemParam> getVendor(){
		return prodDirectoryDao.getVendor();
	}

	public List<ResItemParam> getByVendor(int vendorID){
		return prodDirectoryDao.getByVendor(vendorID);
	}

	public List<ResItemParam> getByClassCode(int vendorID, int prodClassCode){
		return prodDirectoryDao.getByClassCode(vendorID,prodClassCode);
	}

	public List<ResItemParam> getBySeries(int vendorID, int prodClassCode,int typeCode){
		return prodDirectoryDao.getBySeries(vendorID,prodClassCode,typeCode);
	}

	public List<ProdDirectory> getProdTypeById(String id) {
		logger.debug("Enter getProdTypeById...");
		String hql=" from ProdDirectory where vendorID=? order by vendorID,prodClassCode ";
		return prodDirectoryDao.find(hql, Integer.parseInt(id));
	}

	public List<ProdDirectory> getByVendorAndClassCode(int i, int j) {
		logger.debug("Enter getByVendorAndClassCode...");
		String hql="from ProdDirectory where vendorID=? and prodClassCode=? order by vendorID,prodClassCode,prodTypeCode,prodSeries,prodModel";
		return prodDirectoryDao.find(hql,i,j);
	}

	public List<ProdDirectory> getDataByVendorID(Integer id){
		logger.debug("Enter getDataByVendorID...");
		String hql="from ProdDirectory where vendorID=? order by vendorID,prodClassCode,prodTypeCode,prodSeries,prodModel";
		return prodDirectoryDao.find(hql,id);
	}

	public List<ProdDirectory> getByVendorAndClassCodeAndTypeCode(int parseInt, int parseInt2, int parseInt3) {
		logger.debug("Enter getByVendorAndClassCodeAndTypeCode...");
		String hql="from ProdDirectory where vendorID=? and prodClassCode=? and prodTypeCode=? order by vendorID,prodClassCode,prodTypeCode,prodSeries,prodModel";
		return prodDirectoryDao.find(hql,parseInt,parseInt2,parseInt3);
	}

	public List<ProdDirectory> getByVenAndClassAndTypeAndSeries(int parseInt, int parseInt2, int parseInt3, String string) {
		logger.debug("Enter getByVenAndClassAndTypeAndSeries...");
		String hql="from ProdDirectory where vendorID=? and prodClassCode=? and prodTypeCode=? and prodSeries=? order by vendorID,prodClassCode,prodTypeCode,prodSeries,prodModel";
		return prodDirectoryDao.find(hql,parseInt,parseInt2,parseInt3,string);
	}

	public String getJsonObjStr(ProdDirectory newProDirectory) {
		return JsonUtils.getJsonObjInfo(newProDirectory,objAttNames,jsonAttNames);
	}

	public ProdDirectory getById(String id) {
		logger.debug("Enter getById..");
		String hql = "from ProdDirectory where productID = ?" ;
		List<ProdDirectory> prodDirectoryList = prodDirectoryDao.find(hql, id);
		if(prodDirectoryList != null){
			return prodDirectoryList.get(0);
		}
		return null;
	}

	public List<ProdDirectory> getProductByNo(String productNo) {
		logger.debug("Enter getProductByNo ...");
		String hql = "from ProdDirectory where productNo = ?";
		List<ProdDirectory> list = prodDirectoryDao.find(hql, productNo);
		return list;
	}
	public void batchSave(List<ProdDirectory> listProdModel) {
		prodDirectoryDao.batchSave(listProdModel, 20);
		prodDirectoryDao.flush();
		
	}
}
