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

import com.sino.base.common.util.JsonUtils;

import com.sino.base.system.dao.OrgPositionDao;
import com.sino.base.system.entity.OrgPosition;
import com.sino.base.system.entity.OrgOrganization;


/**
 * 职位管理类
 * 
 * @author sujp
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class PositionService {
	private static Logger logger = LoggerFactory.getLogger(PositionService.class);
	
	private static String objAttNames = "positionId,positionName,dutyCode,positionLevel@positionLevel,description,qualification,state";
	private static String jsonAttNames = "id,posName,dutyCode,posLevel,description,qualification,state";

	@Autowired
	private OrgPositionDao positionDao;

	@Transactional(readOnly = true)
	public String getJsonObjStr(final OrgPosition obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<OrgPosition> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public List<OrgPosition> getAllPosition() {
		return positionDao.getAll("orgOrganization.orgId", true);
	}
	
	@Transactional(readOnly = true)
	public List<OrgPosition> getOrganPosition(String grpId) {
		String hql = "from OrgPosition where orgOrganization.orgId=? order by positionLevel";
		List<OrgPosition> list = positionDao.find(hql, grpId);
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<OrgPosition> getOrganPosition(OrgOrganization organ) {
		return getOrganPosition(organ.getOrgId());
	}
	
	@Transactional(readOnly = true)
	public List<OrgPosition> searchPosition(final List<PropertyFilter> filters) {
		return positionDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<OrgPosition> searchPosition(final PageRequest page, final List<PropertyFilter> filters) {
		return positionDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public OrgPosition getPosition(String id) {
		return positionDao.get(id);
	}

	public void addPosition(OrgPosition entity) {
		logger.debug("savePosition...");
		entity.setPositionId(Identities.uuid());
		positionDao.save(entity);
	}

	public void savePosition(OrgPosition entity) {
		logger.debug("savePosition...");
		positionDao.save(entity);
	}

	public void deletePosition(String id) {
		logger.debug("deletePosition(id:{})...", id);
		positionDao.delete(id);
	}

}
