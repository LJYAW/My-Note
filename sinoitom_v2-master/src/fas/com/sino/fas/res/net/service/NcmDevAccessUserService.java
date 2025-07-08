package com.sino.fas.res.net.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.Identities;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.net.dao.NcmDevAccessUserDao;
import com.sino.fas.res.net.entity.NcmDevAccessUser;

@Service
@Transactional
public class NcmDevAccessUserService {

	private static Logger logger = LoggerFactory.getLogger(NcmDevAccessUserService.class);
	
	private static String objAttNames ="devAcsUserId,orgId@OrgOrganization@orgId@orgName,acsUserName,acsUserDescr,accessTool,accessPort,userName,passWord,userType,privModeCmd,privModePasswd,creator,createTime";
	private static String jsonAttNames ="id,orgName,acsUserName,acsUserDescr,accessTool,accessPort,userName,passWord,userType,privModeCmd,privModePasswd,creator,createTime";
	
	@Autowired
	private NcmDevAccessUserDao devAccessUserDao;
	
	@Transactional(readOnly = true)
	public List<NcmDevAccessUser> getAllByOrgIds(List<String> orgIds) {
		return devAccessUserDao.getDataByOrgIds(orgIds);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final NcmDevAccessUser obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<NcmDevAccessUser> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	public NcmDevAccessUser getById(String id){
		return devAccessUserDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public boolean isDevAccessUnique(String id, String devAccessName) {
		return devAccessUserDao.isIdAttUnique(id, "acsUserName", devAccessName);
	}
	
	@Transactional(readOnly = true)
	public List<NcmDevAccessUser> searchDevAccess(final List<PropertyFilter> filters) {
		return devAccessUserDao.find(filters);
	}
	
	public void addDevAccess(NcmDevAccessUser entity) {
		logger.debug("addDevAccess...");
		entity.setDevAcsUserId(Identities.uuid());
		devAccessUserDao.save(entity);
	}
	
	public void deleteDevAccess(String id) {
		logger.debug("deleteDevAccess(id:{})...", id);
		devAccessUserDao.delete(id);
	}
	
	public void saveDevAccess(NcmDevAccessUser entity) {
		logger.debug("saveDevAccess...");
		devAccessUserDao.save(entity);
	}
	
	public NcmDevAccessUser getAccessByAccessId(String devAcsUserId){
		String hql=" from NcmDevAccessUser where devAcsUserId=? ";
		return devAccessUserDao.findUnique(hql,devAcsUserId);
	}

	public NcmDevAccessUser getByResId(Long resId) {
		NcmDevAccessUser ncmDevAccessUser = devAccessUserDao.getByResId(resId);
		return ncmDevAccessUser;
	}
}
