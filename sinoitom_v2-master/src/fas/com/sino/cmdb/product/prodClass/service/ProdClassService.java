/*
 * 文件名： ProdClassService.java
 * 
 * 创建日期： 2014-1-15
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.cmdb.product.prodClass.service;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.sino.base.common.util.ExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.product.prodClass.dao.ProdClassDao;
import com.sino.cmdb.product.prodClass.entity.ProdClass;



/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.3 $
 *
 * @since 2014-1-15
 */
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ProdClassService {
	
	private static Logger logger = LoggerFactory.getLogger(ProdClassService.class);
	
	private static String objAttNames ="prodClassID,vendorID,vendorID@CmdbVendor@vendorID@dispName,prodClassCode,prodClassName,description,status";
	
	private static String jsonAttNames ="id,vendorID,dispName,prodClassCode,prodClassName,description,status";
	
	private static String excelAttNames="prodClassID,vendorID,prodClassCode,prodClassName,description";
	@Autowired
	private ProdClassDao prodClassDao;
	private String lastMassage = "";
	public void save(ProdClass prod){
		logger.debug("Enter save..");
		prodClassDao.save(prod);
		prodClassDao.flush();
	}
	public void add(ProdClass prod) {
		logger.info("save Entity");
		prod.setProdClassCode(prod.getProdClassID());
		prodClassDao.save(prod);
		prodClassDao.flush();
	}
	public void delete(int id){
		logger.debug("Enter delete..");
		prodClassDao.delete(id);
	}
	
	
	
	
	@Transactional(readOnly = true)
	public ProdClass getProdClassById(Integer id){
		return prodClassDao.get(id);
	}
	
	public void batchSave(List<ProdClass> list){
		prodClassDao.batchSave(list, 10);
	}
	@Transactional(readOnly = true)
	public List<ProdClass> searchProdClass() {
		return prodClassDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final ProdClass obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ProdClass> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<ProdClass> getClassByVendor(int id){
		logger.debug("Enter getClassByVendor");
		String hql=" from ProdClass where vendorID=? order by vendorID,prodClassCode";
		return prodClassDao.find(hql, id);
	}
	
	public List<ProdClass> getAll() {
		logger.debug("Enter getClassByVendor");
		String hql=" from ProdClass order by vendorID,prodClassCode";
		return prodClassDao.find(hql);
	}

	public List<ProdClass> checkClassCode(Integer vendorID,Integer prodClassCode) {
		String hql=" from ProdClass where vendorID=? and prodClassCode=?";
		return prodClassDao.find(hql,vendorID,prodClassCode);
	}
	@Transactional(readOnly = true)
	public String getLastMassage() {
		return lastMassage;
	}
	public boolean importExcel(InputStream stream){
		this.lastMassage = "";
		
		ExcelUtil<ProdClass> excelUtil = new ExcelUtil<ProdClass>(ProdClass.class);
		List<ProdClass> list = excelUtil.getObjListFrom(stream, excelAttNames);
	
		if( list.isEmpty() ){
			this.lastMassage = "导入失败！请检查导入文件数据！";
			System.out.println("导入失败");
			return false;
		}
		int addNum=0; 
		int updateNum=0;
		for(int i=0; i<list.size(); i++){
			ProdClass model = list.get(i);
			
			ProdClass prodModel =getProdClassById(model.getProdClassID());
	       
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
