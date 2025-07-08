/*
 * 文件名： SeverityService.java
 * 
 * 创建日期： 2014-9-12
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.severity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.severity.dao.SeverityDao;
import com.sino.cmdb.severity.entity.Severity;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-12
 */
@Service
@Transactional
public class SeverityService {

	@Autowired
	private SeverityDao severityDao;
	
	private static String objAttNames = "iD,severityCode,severityEName,severityName,alarmColor,colorValue,description";
	private static String jsonAttNames = "iD,severityCode,severityEName,severityName,alarmColor,colorValue,description";
	
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final Severity obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	public String getJsonListStr(List<Severity> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	public List<Severity> getById(String id) {
		String hql=" from Severity where severityCode = ? ";
		return severityDao.find(hql,Integer.parseInt(id));
	}
	
	public List<Severity> getAllExceptZero() {
		String hql=" from Severity where severityCode > ? ";
		return severityDao.find(hql,0);
	}
	
	public Severity getById(int id) {
		return severityDao.get(id);
	}

	public List<Severity> getAll() {
		return severityDao.getAll();
	}
}
