/**
 * 
 */
package com.sino.cmdb.indicator.report.template.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.indicator.report.template.dao.RptTmplFieldsCellDao;
import com.sino.cmdb.indicator.report.template.entity.RptTmplFieldsCell;
import com.sino.cmdb.indicator.report.tmplcell.entity.RptTmplCell;

/**
 * @author Mr.LP
 * @date 2019-9-23下午3:56:06
 * @className RptTmplFieldsCellService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class RptTmplFieldsCellService {
	
	private static Logger logger = LoggerFactory.getLogger(RptTmplFieldsCellService.class);
	
	private static String objAttNames = "tmplId,tmplFieldsCellId,fieldNameEn,fieldName,valueType,borderStyle,tableWidth,height,color,font,size,isBold,valuePos,indItemID";
	private static String jsonAttNames = "tmplId,id,fieldNameEn,fieldName,valueType,borderStyle,tableWidth,height,color,font,size,isBold,valuePos,indItemID";

	@Autowired
	private RptTmplFieldsCellDao rptTmplFieldsCellDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<RptTmplFieldsCell> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final RptTmplFieldsCell obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public RptTmplFieldsCell getById(int id){
		logger.debug("enter getById...");
		return rptTmplFieldsCellDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public List<RptTmplFieldsCell> getAll() {
		
		return rptTmplFieldsCellDao.getAll();
	}
	
	public void save(RptTmplFieldsCell entity){
		rptTmplFieldsCellDao.save(entity);
	}
	
	public void delete(Integer id){
		rptTmplFieldsCellDao.delete(id);
	}
	
	public void deleteByTmplId(List<RptTmplFieldsCell> list){
		rptTmplFieldsCellDao.delete(list);
	}
	
	public List<RptTmplFieldsCell> getByTmplId(int tmplId) {
		String hql=" from RptTmplFieldsCell where tmplId = ? ";
		return rptTmplFieldsCellDao.find(hql, tmplId);
	}
	
}
