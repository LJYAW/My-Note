package com.sino.fas.res.net.service;

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
import com.sino.fas.res.net.dao.SnmpCredDao;
import com.sino.fas.res.net.entity.NcmSnmpCredentials;


//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class SnmpCredService {

	private static Logger logger = LoggerFactory.getLogger(SnmpCredService.class);
	
	private static String objAttNames = "snmpCredId,snmpCredName,snmpCredDesc,snmpVersion@snmpCred_snmpVersion,snmpPort,snmpRoStr,snmpRwStr,credName,credDesc,credUserName,credContextName,credAuthProt@CredAuthProtocol,credAuthPassword,credEncryptProt@CredEncryptProtocol,credEncryptPassword,lowerVisable,status,orgId@OrgOrganization@orgId@orgName";
	private static String jsonAttNames = "id,snmpCredName,snmpCredDesc,snmpVersion,snmpPort,snmpRoStr,snmpRwStr,credName,credDesc,credUserName,credContextName,credAuthProt,credAuthPassword,credEncryptProt,credEncryptPassword,lowerVisable,status,organ";
	private static String objMinAttNames = "snmpCredId,snmpCredName";
	private static String jsonMinAttNames = "id,snmpCredName";
	
	public String lastMassage = "";

	@Autowired
	private SnmpCredDao snmpCredDao;

	@Transactional(readOnly = true)
	public String getJsonObjStr(final NcmSnmpCredentials obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<NcmSnmpCredentials> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonMinListStr(final List<NcmSnmpCredentials> list) {
		return JsonUtils.getJsonListInfo(list, objMinAttNames, jsonMinAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonTreeStr(final List<NcmSnmpCredentials> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public List<NcmSnmpCredentials> getAllSnmpCred() {
		return snmpCredDao.getAll("snmpCredName", true);
	}
	public String getJsonSnmpRoStrData(){
		List<NcmSnmpCredentials> list=getAllSnmpCred();
		return getJsonListStr(list);
	}
	@Transactional(readOnly = true)
	public List<NcmSnmpCredentials> searchSnmpCred(final List<PropertyFilter> filters) {
		return snmpCredDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<NcmSnmpCredentials> searchSnmpCred(final PageRequest page, final List<PropertyFilter> filters) {
		return snmpCredDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public NcmSnmpCredentials getSnmpCred(String id) {
		return snmpCredDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public boolean loadSnmpCredAtts(NcmSnmpCredentials entity, String attNames){
		return snmpCredDao.loadObjAtts(entity, attNames);
	}

	public void addSnmpCred(NcmSnmpCredentials entity) {
		logger.debug("addSnmpCred...");
		entity.setSnmpCredId(Identities.uuid());
		snmpCredDao.save(entity);
	}

	public void saveSnmpCred(NcmSnmpCredentials entity) {
		logger.debug("saveSnmpCred...");
		snmpCredDao.save(entity);
	}

	public void deleteSnmpCred(String id) {
		logger.debug("deleteSnmpCred(id:{})...", id);
		snmpCredDao.delete(id);
	}

	@Transactional(readOnly = true)
	public boolean isSnmpCredUnique(String id, String snmpCredName) {
		return snmpCredDao.isIdAttUnique(id, "snmpCredName", snmpCredName);
	}

}
