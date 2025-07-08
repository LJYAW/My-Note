package com.sino.base.system.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.Identities;

import com.sino.base.common.util.CommandUtils;
import com.sino.base.common.util.JsonUtils;

import com.sino.base.system.dao.SysParamGroupDao;
import com.sino.base.system.entity.SysParamGroup;

/**
 * 系统参数组管理类
 * 
 * @author sujp
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ParamGroupService {
	private static Logger logger = LoggerFactory.getLogger(ParamGroupService.class);
	
	private static String objAttNames = "paramGrpId,grpName,grpCode,state@paramGrpState,description";
	private static String jsonAttNames = "id,grpName,grpCode,state,description";

	@Autowired
	private SysParamGroupDao paramGroupDao;

	@Transactional(readOnly = true)
	public String getJsonObjStr(final SysParamGroup obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<SysParamGroup> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public List<SysParamGroup> getAllParamGroup() {
		return paramGroupDao.getAll("grpNumber", true);
	}
	
	@Transactional(readOnly = true)
	public List<SysParamGroup> searchParamGroup(final List<PropertyFilter> filters) {
		return paramGroupDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<SysParamGroup> searchParamGroup(final PageRequest page, final List<PropertyFilter> filters) {
		return paramGroupDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public SysParamGroup getParamGroup(String id) {
		return paramGroupDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public SysParamGroup getParamGroupByCode(String code) {
		return paramGroupDao.findUniqueBy("grpCode", code);
	}
	
	public void addParamGroup(SysParamGroup entity) {
		logger.debug("addParamGroup...");
		entity.setParamGrpId(Identities.uuid());
		Integer number = paramGroupDao.getMaxNumber()+1;
		entity.setGrpNumber(number);
		paramGroupDao.save(entity);
	}

	public void saveParamGroup(SysParamGroup entity) {
		logger.debug("saveParamGroup...");
		Integer number = paramGroupDao.getGrpNumber(entity.getParamGrpId());
		if( number == null ){
			number = paramGroupDao.getMaxNumber()+1;
		}
		entity.setGrpNumber(number);
		paramGroupDao.save(entity);
	}
	
	public void moveItem(SysParamGroup item, int movePos){
		String hql = "from SysParamGroup where grpNumber=?";
		SysParamGroup itemFind = paramGroupDao.findUnique(hql, item.getGrpNumber() + movePos );
		if (itemFind != null) {
			Integer number = itemFind.getGrpNumber();
			itemFind.setGrpNumber(item.getGrpNumber());
			item.setGrpNumber(number);
			paramGroupDao.save(itemFind);
			paramGroupDao.save(item);
		}		
	}

	public void moveUpItem(SysParamGroup item) {
		logger.debug("moveUpItem(name:{})...", item.getGrpName());
		moveItem(item, -1);
	}

	public void moveDownItem(SysParamGroup item) {
		logger.debug("moveUpItem(name:{})...", item.getGrpName());
		moveItem(item, 1);
	}

	public void deleteParamGroup(String id) {
		SysParamGroup item = paramGroupDao.get(id);
		logger.debug("deleteParamGroup(name:{})...", item.getGrpName());
		Integer number = item.getGrpNumber();
		String hql = "update SysParamGroup set grpNumber=grpNumber-1 where grpNumber>?";
		paramGroupDao.batchExecute(hql, number);
		paramGroupDao.delete(item);
	}
	
	@Transactional(readOnly = true)
	public boolean isGrpCodeUnique(String grpId, String grpCode) {
		return paramGroupDao.isIdAttUnique(grpId, "grpCode", grpCode);
	}
	
}
