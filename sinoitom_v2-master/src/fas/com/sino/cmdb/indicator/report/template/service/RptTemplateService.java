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
import com.sino.cmdb.indicator.report.template.dao.RptTemplateDao;
import com.sino.cmdb.indicator.report.template.entity.RptTemplate;
import com.sino.cmdb.indicator.report.template.entity.RptTmplFieldsCell;
import com.sino.cmdb.indicator.report.tmplcell.entity.RptTmplCell;
import com.sino.cmdb.indicator.report.tmplcell.service.RptTmplCellService;

/**
 * @author Mr.LP
 * @date 2019-7-22下午5:56:23
 * @className RptTemplateService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class RptTemplateService {
	
private static Logger logger = LoggerFactory.getLogger(RptTemplateService.class);
	
	private static String objAttNames = "tmplId,tmplType,tmplName,tabTitle,status,tableWidth";
	private static String jsonAttNames = "id,tmplType,tmplName,tabTitle,status,tableWidth";
	
	@Autowired
	private RptTemplateDao rptTemplateDao;
	
	@Autowired
	private RptTmplCellService rptTmplCellService;
	
	@Autowired
	private RptTmplFieldsCellService rptTmplFieldsCellService;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<RptTemplate> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final RptTemplate obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public RptTemplate getById(int tmplId){
		logger.debug("enter getById...");
		return rptTemplateDao.get(tmplId);
	}
	
	@Transactional(readOnly = true)
	public List<RptTemplate> getAll() {
		
		return rptTemplateDao.getAll();
	}
	
	public void save(RptTemplate entity){
		rptTemplateDao.save(entity);
	}
	
	public void delete(Integer id){
		rptTemplateDao.delete(id);
	}
	
	public void batchDelete(Integer id){
		if(id!=null){
			rptTemplateDao.delete(id);
			List<RptTmplCell> list = rptTmplCellService.getByTmplId(id);
			List<RptTmplFieldsCell> fieldsList = rptTmplFieldsCellService.getByTmplId(id);
	        if(list!=null&&list.size()>0){
	        	rptTmplCellService.deleteByTmplId(list);
	        }
	        if(fieldsList!=null&&fieldsList.size()>0){
	        	rptTmplFieldsCellService.deleteByTmplId(fieldsList);
	        }
		}
	}

}
