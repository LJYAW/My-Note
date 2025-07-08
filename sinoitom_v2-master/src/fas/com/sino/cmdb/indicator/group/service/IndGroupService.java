package com.sino.cmdb.indicator.group.service;


import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.ExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.indicator.group.dao.IndGroupDao;
import com.sino.cmdb.indicator.group.entity.IndicatorGroup;

import com.sino.cmdb.product.prodType.entity.ResItemParam;


import smartlink.utils.Util;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-7-26
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class IndGroupService {
	private static Logger logger = LoggerFactory.getLogger(IndGroupService.class);

	private static String objAttNames = "indGroupID,indClassCode,indClassName,groupEnName,groupName,description,status";
	private static String jsonAttNames = "id,indClassCode,indClassName,groupEnName,groupName,description,status";
	private static String excelAttNames = "indGroupID,indClassCode,indClassName,groupEnName,groupName,description";
	                                     
	@Autowired
	private IndGroupDao indicatorDao;
	private String lastMassage = "";
	@Transactional(readOnly = true)
	public String getLastMassage() {
		return lastMassage;
	}
	@Transactional(readOnly = true)
	public List<IndicatorGroup> getIndicator() {
		String hql = "from IndicatorGroup order by indClassCode";
		return indicatorDao.find(hql);
	}
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<IndicatorGroup> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	public void save(IndicatorGroup entity){
		logger.info("save Entity");
		indicatorDao.save(entity);
		indicatorDao.flush();
	}
	
	public void add(IndicatorGroup entity){
		logger.info("save Entity");
		entity.setIndGroupID(Util.getCRC32(entity.getGroupEnName()));
		indicatorDao.save(entity);
		indicatorDao.flush();
	}
	@Transactional(readOnly = true)
	public String getJsonObjStr(final IndicatorGroup obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	public void deleteIndicator(String id) {
		logger.debug("deleteIndicator(id:{})...", id);
//		indicatorDao.delete(id);
	}
	
	public List<IndicatorGroup> getByIndClassCode(int classCode){
		logger.debug("getByIndClassCode...");
		String hql=" from IndicatorGroup where indClassCode=?";
		return indicatorDao.find(hql, classCode);
	}

	@Transactional(readOnly = true)
	public IndicatorGroup getGroup(String id) {
		return indicatorDao.get(Long.parseLong(id));
	}
	@Transactional(readOnly = true)
	public List<IndicatorGroup> getAllIndicatorGroup() {
		
		return indicatorDao.getAll();
	}
	
	public void deletes(String [] ids){
		logger.debug("Enter deletes...");
		for(String id:ids){
			delete(Long.parseLong(id));
		}
	}
	
	public void delete(Long id){
		logger.debug("Enter delete...");
		indicatorDao.delete(id);
	}
	public void batchSave(List<IndicatorGroup> list){
		indicatorDao.batchSave(list, 10);
	}
	
	public IndicatorGroup getById(Long parseInt) {
		return indicatorDao.get(parseInt);
	}
	public List<ResItemParam> getAllClassCode() {
		return indicatorDao.getAllClassCode();
	}
	public boolean importExcel(InputStream stream){
		this.lastMassage = "";
		
		ExcelUtil<IndicatorGroup> excelUtil = new ExcelUtil<IndicatorGroup>(IndicatorGroup.class);
		List<IndicatorGroup> list = excelUtil.getObjListFrom(stream, excelAttNames);
	
		if( list.isEmpty() ){
			this.lastMassage = "导入失败！请检查导入文件数据！";
			System.out.println("导入失败");
			return false;
		}
		int addNum=0; 
		int updateNum=0;
		for(int i=0; i<list.size(); i++){
			IndicatorGroup group = list.get(i);
			
			IndicatorGroup indicatorGroup =getById(group.getIndGroupID());
	       
			if(indicatorGroup!=null){//存在 update

				
				save(group);
				updateNum++;
			}else{//不存在add
				
				add(group);
				
				addNum++;
			}

		}
		
		this.lastMassage = String.format("导入完毕！更新%d条记录,新增%d条记录。",updateNum, addNum);
		
		return true;
	}

}
