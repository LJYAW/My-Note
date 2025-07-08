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

import com.sino.base.system.dao.SysPowerDao;
import com.sino.base.system.entity.SysPower;
import com.sino.base.system.entity.SysPowerGroup;


/**
 * 系统权限管理类
 * 
 * @author sujp
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class PowerService {
	private static Logger logger = LoggerFactory.getLogger(PowerService.class);
	
	private static String objAttNames = "powId,powName,powCode,powNumber,isBsPow,isDvPow,isRoleSee,state@powerState,description";
	private static String jsonAttNames = "id,powName,powCode,powNumber,isBsPow,isDvPow,isRoleSee,state,description";

	@Autowired
	private SysPowerDao powerDao;

	@Transactional(readOnly = true)
	public String getJsonObjStr(final SysPower obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<SysPower> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public List<SysPower> getAllPower() {
		return powerDao.getAll("powNumber", true);
	}
	
	@Transactional(readOnly = true)
	public List<SysPower> findUserAllPower(String userId)
	{
		String hql = "from SysPower where state=1 and powId in ( select id.powId from ViewUserPower where id.userId=? ) order by sysPowerGroup.powGrpId,powNumber";
		List<SysPower> list = powerDao.find(hql, userId);
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<SysPower> getGroupPower(String grpId) {
		String hql = "from SysPower where sysPowerGroup.powGrpId=? order by powNumber";
		List<SysPower> list = powerDao.find(hql, grpId);
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<SysPower> getGroupPower(SysPowerGroup group) {
		return getGroupPower(group.getPowGrpId());
	}
	
	@Transactional(readOnly = true)
	public List<SysPower> searchPower(final List<PropertyFilter> filters) {
		return powerDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<SysPower> searchPower(final PageRequest page, final List<PropertyFilter> filters) {
		return powerDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public SysPower getPower(String id) {
		return powerDao.get(id);
	}

	public void addPower(SysPower entity) {
		logger.debug("savePower...");
		entity.setPowId(Identities.uuid());
		Integer number = powerDao.getMaxNumber(entity.getSysPowerGroup().getPowGrpId())+1;
		entity.setPowNumber(number);
		powerDao.save(entity);
	}

	public void savePower(SysPower entity) {
		logger.debug("savePower...");
		powerDao.save(entity);
	}
		
	public void moveItem(SysPower item, int movePos) {
		String hql = "from SysPower where sysPowerGroup.powGrpId=? and powNumber=?";
		SysPower itemFind = powerDao.findUnique(hql, item.getSysPowerGroup().getPowGrpId(), item.getPowNumber()+movePos);
		if( itemFind != null ){
			Integer number = itemFind.getPowNumber();
			itemFind.setPowNumber(item.getPowNumber());
			item.setPowNumber(number);		
			powerDao.save(itemFind);
			powerDao.save(item);
		}
	}
	
	public void moveUpItem(SysPower item) {
		logger.debug("moveUpItem(name:{})...", item.getPowName());
		moveItem(item, -1);
	}
	
	public void moveDownItem(SysPower item) {
		logger.debug("moveDownItem(name:{})...", item.getPowName());
		moveItem(item, 1);
	}

	public void deletePower(String id) {
		SysPower item = powerDao.get(id);
		logger.debug("deletePower(name:{})...", item.getPowNumber());
		String grpId = item.getSysPowerGroup().getPowGrpId();
		Integer number = item.getPowNumber();
		String hql = "update SysPower set powNumber=powNumber-1 where sysPowerGroup.powGrpId=? and powNumber>?";
		powerDao.batchExecute(hql, grpId, number);
		powerDao.delete(item);
	}
	
	@Transactional(readOnly = true)
	public boolean isPowCodeUnique(String powId, String powCode) {
		return powerDao.isIdAttUnique(powId, "powCode", powCode);
	}
	
}
