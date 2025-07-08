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

import com.sino.base.common.Global;
import com.sino.base.common.util.JsonUtils;

import com.sino.base.system.dao.SysResGroupDao;
import com.sino.base.system.dao.SysResItemDao;
import com.sino.base.system.entity.SysResGroup;
import com.sino.base.system.entity.SysResItem;

/**
 * 系统资源管理类
 * 
 * @author sujp
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ResGroupService {
	private static Logger logger = LoggerFactory.getLogger(ResGroupService.class);
	
	private static String objAttNames = "resGrpId,grpName,grpCode,state@resGroupState,grpType@resGroupType,itemType@resItemType,description";
	private static String jsonAttNames = "id,grpName,grpCode,state,grpType,itemType,description";

	private static int treeLevelLength = 3;

	@Autowired
	private SysResGroupDao resGroupDao;
	
	@Autowired
	private SysResItemDao resItemDao;

	@Transactional(readOnly = true)
	public String getJsonObjStr(final SysResGroup obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<SysResGroup> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public List<SysResGroup> getAllResGroup() {
		return resGroupDao.getAll("grpNumber", true);
	}
	
	@Transactional(readOnly = true)
	public List<SysResGroup> searchResGroup(final List<PropertyFilter> filters) {
		return resGroupDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<SysResGroup> searchResGroup(final PageRequest page, final List<PropertyFilter> filters) {
		return resGroupDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public SysResGroup getResGroup(String id) {
		return resGroupDao.get(id);
	}

	@Transactional(readOnly = true)
	public SysResGroup getResGroupByCode(String grpCode) {
		return resGroupDao.findUniqueBy("grpCode", grpCode);
	}
	
	public void addResGroup(SysResGroup entity) {
		logger.debug("addResGroup...");
		entity.setResGrpId(Identities.uuid());
		Integer number = resGroupDao.getMaxNumber()+1;
		entity.setGrpNumber(number);
		resGroupDao.save(entity);
	}

	public void saveResGroup(SysResGroup entity) {
		logger.debug("saveResGroup...");
		Integer number = resGroupDao.getGrpNumber(entity.getResGrpId());
		if( number == null ){
			number = resGroupDao.getMaxNumber()+1;
		}
		entity.setGrpNumber(number);
		
		Integer oldGrpType = resGroupDao.getGrpType(entity.getResGrpId());
		if( entity.getGrpType() != oldGrpType){
			String grpId = entity.getResGrpId();
			if( entity.getGrpType() == Global.GROUP_TYPE_LIST ){
				List<SysResItem> list = resItemDao.getGroupAll(grpId, "resNumber", true);
				for( int i=0; i<list.size(); i++ ){
					SysResItem item = list.get(i);
					item.setResNumber(i+1);
					resItemDao.save(item);
				}
			}
			else if( entity.getGrpType() == Global.GROUP_TYPE_TREE ){
				String treeCodeFormat = String.format("%%0%dd", treeLevelLength);
				List<SysResItem> list = resItemDao.getGroupAll(grpId, "treeCode", true);
				for( int i=0; i<list.size(); i++ ){
					SysResItem item = list.get(i);
					item.setTreeCode(String.format(treeCodeFormat, i+1));
					resItemDao.save(item);
				}
			}
		}

		resGroupDao.save(entity);
	}
	
	public void moveItem(SysResGroup item, int movePos){
		String hql = "from SysResGroup where grpNumber=?";
		SysResGroup itemFind = resGroupDao.findUnique(hql, item.getGrpNumber() + movePos );
		if (itemFind != null) {
			Integer number = itemFind.getGrpNumber();
			itemFind.setGrpNumber(item.getGrpNumber());
			item.setGrpNumber(number);
			resGroupDao.save(itemFind);
			resGroupDao.save(item);
		}		
	}

	public void moveUpItem(SysResGroup item) {
		logger.debug("moveUpItem(name:{})...", item.getGrpName());
		moveItem(item, -1);
	}

	public void moveDownItem(SysResGroup item) {
		logger.debug("moveUpItem(name:{})...", item.getGrpName());
		moveItem(item, 1);
	}

	public void deleteResGroup(String id) {
		SysResGroup item = resGroupDao.get(id);
		logger.debug("deleteResGroup(name:{})...", item.getGrpName());
		Integer number = item.getGrpNumber();
		String hql = "update SysResGroup set grpNumber=grpNumber-1 where grpNumber>?";
		resGroupDao.batchExecute(hql, number);
		resGroupDao.delete(item);
	}
	
	@Transactional(readOnly = true)
	public boolean isGrpCodeUnique(String grpId, String grpCode) {
		return resGroupDao.isIdAttUnique(grpId, "grpCode", grpCode);
	}
	
}
