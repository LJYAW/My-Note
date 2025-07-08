/**
 * 
 */
package com.sino.fas.res.host.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.host.dao.ApmHostsDao;
import com.sino.fas.res.host.entity.ApmHosts;

/**
 * @author Mr.LP
 * @date 2019-8-21下午2:41:19
 * @className ApmHostsService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ApmHostsService {
	
private static Logger logger = LoggerFactory.getLogger(ApmHostsService.class);
	
	private static String objAttNames = "hostId,orgID,ipHostTypeId,osName,osClass,osType,osVersion,osRelase,accessmode,ipAddress,ipNetMask,username,password,cpuVendor,cpuModel,cpuWidth,cpuQty,cpuCoreQty,resClassCode,memTotal,memFree,resClassName,resTypeCode,resTypeName";
	private static String jsonAttNames = "id,orgID,ipHostTypeId,osName,osClass,osType,osVersion,osRelase,accessmode,ipAddress,ipNetMask,username,password,cpuVendor,cpuModel,cpuWidth,cpuQty,cpuCoreQty,resClassCode,memTotal,memFree,resClassName,resTypeCode,resTypeName";
	
	@Autowired
	private ApmHostsDao apmHostsDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ApmHosts> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final ApmHosts obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<ApmHosts> getAll() {
		return apmHostsDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public ApmHosts getById(String id){
		return apmHostsDao.get(id);
	}
	
	public void save(ApmHosts apmHosts) {
		logger.debug("save...");
		apmHostsDao.save(apmHosts);
	}
	

}
