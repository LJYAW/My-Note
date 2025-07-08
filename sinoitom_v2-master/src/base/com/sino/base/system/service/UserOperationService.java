package com.sino.base.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.dao.UserOperationDao;
import com.sino.base.system.entity.SysUserOperation;

@Service
@Transactional
public class UserOperationService {

	private static Logger logger = LoggerFactory.getLogger(UserOperationService.class);
	private static String objAttNames ="Id,userName,funMenu,operation,sqlSentence,retCode,message,errorCause,opTime";
	private static String jsonAttNames ="Id,userName,funMenu,operation,sqlSentence,retCode,message,errorCause,opTime";
	
	@Autowired
	private UserOperationDao userOperationDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<SysUserOperation> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	public void save(SysUserOperation so){
		logger.debug("Enter UserOperationService save...");
		userOperationDao.save(so);
	}
	
	@Transactional(readOnly = true)
	public String getUserOperaPageList(final Page<SysUserOperation> page) {
		return JsonUtils.getJQJsonPageInfo(page, objAttNames, jsonAttNames);
	}
	
	
	public SysUserOperation getById(long id){
		return userOperationDao.get(id);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	public Page<SysUserOperation> findUserOperPage(PageRequest pageReq){
		String hql=" from SysUserOperation order by opTime desc";
		Map params=new HashMap();
		Page pageinfo=userOperationDao.findAjaxPage(pageReq,hql.toString(), params);
		return pageinfo;
	}
}
