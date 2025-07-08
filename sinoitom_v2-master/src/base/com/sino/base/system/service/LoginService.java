package com.sino.base.system.service;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Identities;

import com.sino.base.system.dao.SysLoginInfoDao;
import com.sino.base.system.entity.SysLoginInfo;


/**
 * 系统日志管理类
 * 
 * @author sujp
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class LoginService {

	private static Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private SysLoginInfoDao loginDao;

	//-- Log Manager --//
	@Transactional(readOnly = true)
	public SysLoginInfo getInfo(String id) {
		logger.debug("getInfo...");
		return loginDao.get(id);
	}

	public void AddInfo(String loginName, String userName, String clientIp) {
		logger.debug("AddInfo...");
		String hql = "from SysLoginInfo where loginName=? and clientIp=?";
		List<SysLoginInfo> infolist=loginDao.find(hql, loginName, clientIp);
		SysLoginInfo info = null;
		if(loginDao!=null&&!infolist.isEmpty()){
			info =infolist.get(0);
		}
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if( info==null ){
			info = new SysLoginInfo();
			info.setLoginInfoId(Identities.uuid());
			info.setLoginName(loginName);
			info.setClientIp(clientIp);
			info.setTotalLoginNum(1L);
		}
		else{
			Long num = info.getTotalLoginNum();
			info.setTotalLoginNum(num+1);
		}
		info.setUserName(userName);
		info.setLastLoginTime(now);
		loginDao.save(info);
	}
	
	@Transactional(readOnly = true)
	public List<SysLoginInfo> getAllInfo() {
		logger.debug("getAllInfo...");
		return loginDao.getAll("loginName", true);
	}

}
