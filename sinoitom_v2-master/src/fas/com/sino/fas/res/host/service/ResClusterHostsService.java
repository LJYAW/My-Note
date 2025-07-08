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
import com.sino.fas.res.host.dao.ResClusterHostsDao;
import com.sino.fas.res.host.entity.ResClusterHosts;

/**
 * @author Mr.LP
 * @date 2019-12-11上午11:02:34
 * @className ResClusterHostsService
 * @version V1.0
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ResClusterHostsService {
	
private static Logger logger = LoggerFactory.getLogger(ResClusterHostsService.class);
	
	private static String objAttNames = "id,clusterId,hostId";
	private static String jsonAttNames = "id,clusterId,hostId";
	
	@Autowired
	private ResClusterHostsDao resClusterHostsDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ResClusterHosts> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final ResClusterHosts obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	public void batchSave(List<ResClusterHosts> list){
		logger.debug("batchSave...");
		resClusterHostsDao.batchSave(list, list.size());
	}

	public List<ResClusterHosts> getByClusterId(Long clusterId){
		String hql = " from ResClusterHosts where clusterId = ?";
		return resClusterHostsDao.find(hql,clusterId);
	}
	
	public List<ResClusterHosts> getByHostId(Long hostId){
		String hql = " from ResClusterHosts where hostId = ?";
		return resClusterHostsDao.find(hql,hostId);
	}
	
	public ResClusterHosts getByClusterIdAndHostId(Long clusterId,Long hostId){
		String hql = " from ResClusterHosts where clusterId = ? and hostId =?";
		return resClusterHostsDao.findUnique(hql,clusterId,hostId);
	}
	
	public void delete(Long id){
		resClusterHostsDao.delete(id);
	}

}
