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
package com.sino.cmdb.product.prodType.service;

import com.sino.base.common.util.ExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.product.prodType.dao.ProdTypeDao;
import com.sino.cmdb.product.prodType.entity.ProdType;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
public class ProdTypeService {
	
private static Logger logger = LoggerFactory.getLogger(ProdTypeService.class);
	
	private static String objAttNames ="prodTypeID,vendorID,vendorID@CmdbVendor@vendorID@dispName,prodClassID,prodClassCode,prodClassName,typeCode,typeName,dispName,status";
	
	private static String jsonAttNames ="id,vendorID,vendorName,prodClassID,prodClassCode,prodClassName,typeCode,typeName,dispName,status";

	
	private static String objPAttNames="id,text";
	private static String jsonPAttNames="id,text";
	private static String excelAttNames = "prodTypeID,vendorID,prodClassID,prodClassCode,prodClassName,typeCode,typeName,dispName,description";//导入映射字段
	
	@Autowired
	private ProdTypeDao prodTypeDao;
	private String lastMassage = "";
	public void save(ProdType prod){
		logger.debug("Enter save..");
		prodTypeDao.save(prod);
		prodTypeDao.flush();
	}
	public void batchSave(List<ProdType> list){
		prodTypeDao.batchSave(list, 10);
	}
	public void add(ProdType prod){
		logger.debug("Enter save..");
		prod.setProdTypeID(prod.getProdTypeID());
		prodTypeDao.save(prod);
		prodTypeDao.flush();
	}
	
	public List<ProdType> getAll(){
		String hql=" from ProdType order by vendorID,prodClassCode,typeCode ";
		return prodTypeDao.find(hql);
	}
	
	public void delete(int id){
		 logger.debug("Enter delete..");
		 prodTypeDao.delete(id);
	}
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ProdType> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonListParamRes(final List<ResItemParam> list) {
		return JsonUtils.getJsonListInfo(list, objPAttNames, jsonPAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<ProdType> getSelectedProdType(String vendorId,String classId){
		logger.debug("Enter getSelectedProdType..");
		String hql=" from ProdType where vendorID=? and prodClassID=? order by typeCode";
		return prodTypeDao.find(hql, Integer.parseInt(vendorId),Integer.parseInt(classId));
	}
	
	
	public List<ProdType> getByVendorAndClassCode(String vendorId,String prodClassCode){
		logger.debug("Enter getByVendorAndClassCode.do..");
		String hql=" from ProdType where vendorID=? and prodClassCode=? order by vendorID,prodClassCode,typeCode ";
		return prodTypeDao.find(hql, Integer.parseInt(vendorId),Integer.parseInt(prodClassCode));
	}
	
	
	public void batchSave(String jsonStr,String vendorId,String classId,String prodClassId,String prodClassName,String desc){
		
		logger.debug("Enter save...");
        List<ResItemParam> pItems=JsonUtils.getDTOList(jsonStr, ResItemParam.class);
		
		//清空之前关联的类型
		List<ProdType> prodTypes=getSelectedProdType(vendorId,prodClassId);
		List<ProdType> types=new ArrayList<ProdType>();
		
		if(!prodTypes.isEmpty()){
			for(int i=0;i<prodTypes.size();i++){
				this.delete(prodTypes.get(i).getProdTypeID());
			}
		}
		
		prodTypeDao.flush();
		//清空后重新关联
		//   
		for(int i=0;i<pItems.size();i++){
			ProdType type=new ProdType();
			type.setProdTypeID(Integer.parseInt(vendorId+classId+pItems.get(i).getId()));
			type.setVendorID(Integer.parseInt(vendorId));
			type.setProdClassID(Integer.parseInt(prodClassId));
			type.setProdClassCode(Integer.parseInt(classId));
			type.setDispName(pItems.get(i).getText());
			type.setProdClassName(prodClassName);
			type.setDescription(desc);
			type.setTypeCode(Integer.parseInt(pItems.get(i).getId()));
			type.setStatus(0);
			types.add(type);
		}
		prodTypeDao.batchSave(types, 10);
	}

	
	public List<ProdType> getProdTypeById(String id) {
		logger.debug("Enter getByVendorAndClassCode.do..");
		String hql=" from ProdType where vendorID=? order by vendorID,prodClassCode,typeCode ";
		return prodTypeDao.find(hql, Integer.parseInt(id));
	}
	
	public ProdType getByID(Integer id){
		String hql=" from ProdType where prodTypeID=? ";
		return prodTypeDao.findUnique(hql, id);
	}

	public List<ResItemParam> getAllVendor() {
		return prodTypeDao.getAllVendor();
	}

	public List<ResItemParam> getTreeByClassCode(int parseInt) {
		return prodTypeDao.getByClassCode(parseInt);
	}
	@Transactional(readOnly = true)
	public String getLastMassage() {
		return lastMassage;
	}
	public boolean importExcel(InputStream stream){
		this.lastMassage = "";
		
		ExcelUtil<ProdType> excelUtil = new ExcelUtil<ProdType>(ProdType.class);
		List<ProdType> list = excelUtil.getObjListFrom(stream, excelAttNames);
	
		if( list.isEmpty() ){
			this.lastMassage = "导入失败！请检查导入文件数据！";
			System.out.println("导入失败");
			return false;
		}
		int addNum=0; 
		int updateNum=0;
		for(int i=0; i<list.size(); i++){
			ProdType model = list.get(i);
			ProdType prodModel =getByID(model.getProdTypeID());
			if(prodModel!=null){//存在 update
				save(model);
				updateNum++;
			}else{//不存在add
				add(model);
				addNum++;
			}
		}
		
		this.lastMassage = String.format("导入完毕！更新%d条记录,新增%d条记录。",updateNum, addNum);
		return true;
	}


}
