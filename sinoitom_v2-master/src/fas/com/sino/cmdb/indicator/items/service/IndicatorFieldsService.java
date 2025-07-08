/**
 * 
 */
package com.sino.cmdb.indicator.items.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.indicator.items.dao.IndicatorFieldsDao;
import com.sino.cmdb.indicator.items.entity.IndicatorFields;

/**
 * @author Mr.LP
 * @date 2019-9-19上午10:24:25
 * @className IndicatorFieldsService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class IndicatorFieldsService {
	
private static Logger logger = LoggerFactory.getLogger(IndicatorFieldsService.class);
	
	private static String objAttNames = "indFieldId,indItemID,fieldNo,fieldNameEn,fieldName,valueType,length,decimals,measureUnit";
	private static String jsonAttNames = "id,indItemID,fieldNo,fieldNameEn,fieldName,valueType,length,decimals,measureUnit";
	
	private static String jsonStr = "indFieldId,indItemID,fieldNo,fieldNameEn,fieldName,valueType,length,decimals,measureUnit";
	
	@Autowired
	private IndicatorFieldsDao indicatorFieldsDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<IndicatorFields> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final IndicatorFields obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	//添加时候获取列表使用
	@Transactional(readOnly = true)
	public String getJsons(final List<IndicatorFields> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonStr);
	}
	
	public IndicatorFields getById(Integer indFieldId){
		logger.debug("enter getById...");
		return indicatorFieldsDao.get(indFieldId);
	}
	
	public void save(IndicatorFields fields){
		indicatorFieldsDao.save(fields);
	}
	
	@Transactional(readOnly = true)
	public List<IndicatorFields> getListFieldsByIndItemID(Long indItemID) {
		String hql = "from IndicatorFields where indItemID = ? order by fieldNo";
		return indicatorFieldsDao.find(hql,indItemID);
	}
	
	public void delete(Integer id){
		logger.debug("Enter delete...");
		indicatorFieldsDao.delete(id);
		indicatorFieldsDao.flush();
	}
	
	public void batchDelete(List<IndicatorFields> list){
		logger.debug("Enter delete...");
		indicatorFieldsDao.delete(list);
		indicatorFieldsDao.flush();
	}

}
