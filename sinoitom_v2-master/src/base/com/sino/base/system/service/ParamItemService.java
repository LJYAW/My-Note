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

import com.sino.base.system.dao.SysParamItemDao;
import com.sino.base.system.entity.SysParamItem;
import com.sino.base.system.entity.SysParamGroup;


/**
 * 系统参数管理类
 * 
 * @author sujp
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ParamItemService {
	private static Logger logger = LoggerFactory.getLogger(ParamItemService.class);
	
	private static String objAttNames = "paramId,paramName,paramCode,paramNumber,valueType@paramValueType,valueText,description";
	private static String jsonAttNames = "id,paramName,paramCode,paramNumber,valueType,valueText,description";

	@Autowired
	private SysParamItemDao paramDao;

	@Transactional(readOnly = true)
	public String getJsonObjStr(final SysParamItem obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<SysParamItem> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public List<SysParamItem> getAllParamItem() {
		return paramDao.getAll("paramNumber", true);
	}
	
	@Transactional(readOnly = true)
	public List<SysParamItem> getGroupParamItem(String grpId) {
		String hql = "from SysParamItem where sysParamGroup.paramGrpId=? order by paramNumber";
		List<SysParamItem> list = paramDao.find(hql, grpId);
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<SysParamItem> getGroupParamItem(SysParamGroup group) {
		return getGroupParamItem(group.getParamGrpId());
	}
	
	@Transactional(readOnly = true)
	public List<SysParamItem> searchParamItem(final List<PropertyFilter> filters) {
		return paramDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<SysParamItem> searchParamItem(final PageRequest page, final List<PropertyFilter> filters) {
		return paramDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public SysParamItem getParamItem(String id) {
		return paramDao.get(id);
	}

	public void addParamItem(SysParamItem entity) {
		logger.debug("saveParamItem...");
		entity.setParamId(Identities.uuid());
		Integer number = paramDao.getMaxNumber(entity.getSysParamGroup().getParamGrpId())+1;
		entity.setParamNumber(number);
		paramDao.save(entity);
	}

	public void saveParamItem(SysParamItem entity) {
		logger.debug("saveParamItem...");
		Integer number = paramDao.getItemNumber(entity.getParamId());
		if( number == null ){
			number = paramDao.getMaxNumber(entity.getSysParamGroup().getParamGrpId())+1;
		}
		entity.setParamNumber(number);
		paramDao.save(entity);
	}
		
	public void moveItem(SysParamItem item, int movePos) {
		String hql = "from SysParamItem where sysParamGroup.paramGrpId=? and paramNumber=?";
		SysParamItem itemFind = paramDao.findUnique(hql, item.getSysParamGroup().getParamGrpId(), item.getParamNumber()+movePos);
		if( itemFind != null ){
			Integer number = itemFind.getParamNumber();
			itemFind.setParamNumber(item.getParamNumber());
			item.setParamNumber(number);		
			paramDao.save(itemFind);
			paramDao.save(item);
		}
	}
	
	public void moveUpItem(SysParamItem item) {
		logger.debug("moveUpItem(name:{})...", item.getParamName());
		moveItem(item, -1);
	}
	
	public void moveDownItem(SysParamItem item) {
		logger.debug("moveDownItem(name:{})...", item.getParamName());
		moveItem(item, 1);
	}

	public void deleteParamItem(String id) {
		SysParamItem item = paramDao.get(id);
		logger.debug("deleteParamItem(name:{})...", item.getParamNumber());
		String grpId = item.getSysParamGroup().getParamGrpId();
		Integer number = item.getParamNumber();
		String hql = "update SysParamItem set paramNumber=paramNumber-1 where sysParamGroup.paramGrpId=? and paramNumber>?";
		paramDao.batchExecute(hql, grpId, number);
		paramDao.delete(item);
	}
	
	@Transactional(readOnly = true)
	public boolean isParamCodeUnique(String paramId, String paramCode) {
		return paramDao.isIdAttUnique(paramId, "paramCode", paramCode);
	}
	
	@Transactional(readOnly = true)
	public String getParamValue(String paramCode) {
		SysParamItem item = paramDao.findUniqueBy("paramCode", paramCode);
		if( item == null ){
			return "";
		}
		return item.getValueText();
	}
	
	public boolean checkParam(String paramCode, String checkWay, String checkValue) throws RuntimeException
	{
		SysParamItem item = paramDao.findUniqueBy("paramCode", paramCode);
		if (item == null){
			return false;
		}

		if (Global.PARAM_TYPE_INT.equals(item.getValueType())) {
			int v1 = Integer.parseInt(item.getValueText());
			int v2 = Integer.parseInt(checkValue);
			if ("==".equals(checkWay)) {
				return v1 == v2;
			} else if (">=".equals(checkWay)) {
				return v1 >= v2;
			} else if (">".equals(checkWay)) {
				return v1 > v2;
			} else if ("<=".equals(checkWay)) {
				return v1 <= v2;
			} else if ("<".equals(checkWay)) {
				return v1 < v2;
			}
		} else if (Global.PARAM_TYPE_FLOAT.equals(item.getValueType())) {
			double v1 = Double.parseDouble(item.getValueText());
			double v2 = Double.parseDouble(checkValue);
			if ("==".equals(checkWay)) {
				return v1 == v2;
			} else if (">=".equals(checkWay)) {
				return v1 >= v2;
			} else if (">".equals(checkWay)) {
				return v1 > v2;
			} else if ("<=".equals(checkWay)) {
				return v1 <= v2;
			} else if ("<".equals(checkWay)) {
				return v1 < v2;
			}
		} else {
			String v1 = item.getValueText();
			String v2 = checkValue;
			int result = v1.compareTo(v2);
			if ("==".equals(checkWay)) {
				return result == 0;
			} else if (">=".equals(checkWay)) {
				return result >= 0;
			} else if (">".equals(checkWay)) {
				return result > 0;
			} else if ("<=".equals(checkWay)) {
				return result <= 0;
			} else if ("<".equals(checkWay)) {
				return result < 0;
			}
		}

		return false;
	}
}
