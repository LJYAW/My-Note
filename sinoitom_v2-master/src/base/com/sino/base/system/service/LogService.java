package com.sino.base.system.service;

import java.util.ArrayList;
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
import com.sino.base.system.dao.SysLogDao;
import com.sino.base.system.entity.SysLog;


/**
 * 系统日志管理类
 * 
 * @author sujp
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class LogService {

	private static Logger logger = LoggerFactory.getLogger(LogService.class);

	private static String objAttNames = "logId,logTitle,logLevel,logContent,userAccount,userName,organName,createTimeValue,clientAddress";
	private static String jsonAttNames = "id,logTitle,logLevel,logContent,userAccount,userName,organName,createTime,clientAddress";

	@Autowired
	private SysLogDao logDao;
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final SysLog obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<SysLog> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	@Transactional(readOnly = true)
	public String getJsonPageStr(final Page<SysLog> page) {
		return JsonUtils.getJsonPageInfo(page, objAttNames, jsonAttNames);
	}
	@Transactional(readOnly = true)
	public List<SysLog> getAllLog() {
		return logDao.getAll("createTimeValue", false);
	}
	
	@Transactional(readOnly = true)
	public List<SysLog> getAllUserLog(String userId) {
		String hql = "from SysLog where userId=? order by createTimeValue desc";
		return logDao.find(hql);
	}
	
	@Transactional(readOnly = true)
	public List<SysLog> getByOprType(String oprType) {
		String hql = "from SysLog where operateType=? ";
		return logDao.find(hql);
	}
	
	@Transactional(readOnly = true)
	public List<SysLog> searchLog(final List<PropertyFilter> filters) {
		return logDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<SysLog> searchLog(final PageRequest page, final List<PropertyFilter> filters) {
		return logDao.findPage(page, filters);
	}
	
	@Transactional(readOnly = true)
	public SysLog getLog(String id) {
		return logDao.get(id);
	}

	public void addLog(SysLog entity) {
		logger.debug("addLog...");
		entity.setLogId(Identities.uuid());
		logDao.save(entity);
	}
	public void saveLog(SysLog entity) {
		logger.debug("saveLog...");
		logDao.save(entity);
	}

	public void deleteLog(String id) {
		logger.debug("deleteLog(id:{})...", id);
		logDao.delete(id);
	}
	
	public void deleteLogs(String [] ids) {
		logger.debug("deleteLog(id:{})...", ids);
		for(String id:ids){
			logDao.delete(id);
		}
		
	}
	
	@Transactional(readOnly = true)
	public Page<SysLog> searchSysLog(final PageRequest page,
			final List<PropertyFilter> filters) {
		if (page != null && page.getOrderBy() != null) {
			String realOrderBy = JsonUtils.getObjAttName(page.getOrderBy(),
					objAttNames, jsonAttNames);
			page.setOrderBy(realOrderBy);
		}
		return logDao.findPage(page, filters);
	}
	public List<String> getAllLogStr() {
		List<String> listLogStr = new ArrayList<String>();
		List<SysLog> listLog = getAllLog();
		if (listLog != null && !listLog.isEmpty()) {
			for (SysLog list : listLog) {
				listLogStr.add(list.getLogId());
			}
		}
		return listLogStr;
	}
}
