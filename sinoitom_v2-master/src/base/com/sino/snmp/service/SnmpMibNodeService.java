package com.sino.snmp.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.dao.SysParamItemDao;
import com.sino.base.system.entity.SysParamItem;
import com.sino.base.system.service.ParamItemService;
import com.sino.snmp.dao.SnmpMibNodeDao;
import com.sino.snmp.entity.SnmpMibNode;
import com.sino.snmp.utils.ParseMibFilesList2;
import com.sino.snmp.utils.mib.Snmp_MibNode;

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class SnmpMibNodeService {
	private static Logger logger = LoggerFactory.getLogger(SnmpMibNodeService.class);
	
	private static String objAttNames = "parentId,nodeId,moduleName,nodeNo,nodeName,nodeType,syntax,units,access,status,description,sequence,oid,flag";
	private static String jsonAttNames = "parentId,nodeId,moduleName,nodeNo,nodeName,nodeType,syntax,units,access,status,description,sequence,oid,flag";

	@Autowired
	private SnmpMibNodeDao snmpMibNodeDao;

	@Transactional(readOnly = true)
	public String getJsonObjStr(final SnmpMibNode obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<SnmpMibNode> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

//	---------------------------------------业务代码----------------------------------------------
	
	public List<SnmpMibNode> getAllData() {
		logger.info("Enter SnmpMibNodeService getAllData...");
//		从数据库中获取数据
		List<SnmpMibNode> list = snmpMibNodeDao.getAllData();
//		按照oid大小进行排序
	    Collections.sort(list);
		return list;
	}

//	根据moduleName获取列表
	public List<SnmpMibNode> findByModuleName(String fileNameStr) {//比如：RUIJIE-TC/hh3c-oid
		logger.info("Enter SnmpMibNodeService findByModuleName...");
		
		List<SnmpMibNode> list = ParseMibFilesList2.getByModuelName(fileNameStr);
		
		return list;
	}
	
	public List<SnmpMibNode> getDataByPid(long pid){
		List<SnmpMibNode> list =snmpMibNodeDao.getDataByPid(pid);
		Collections.sort(list);
		return list;
	}
	
	
	
}
