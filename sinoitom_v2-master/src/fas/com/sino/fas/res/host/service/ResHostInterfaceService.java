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
import com.sino.fas.res.host.dao.ResHostInterfaceDao;
import com.sino.fas.res.host.entity.ResHostInterface;

/**
 * @author Mr.LP
 * @date 2019-8-27下午5:06:46
 * @className ResHostInterfaceService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ResHostInterfaceService {
	
	private static Logger logger = LoggerFactory.getLogger(ResHostInterfaceService.class);
	
	private static String objAttNames = "hostId,infoId,infoName,macAddress,ipAddress,bcastAddress,maskAddress,ipv6";
	private static String jsonAttNames = "hostId,id,infoName,macAddress,ipAddress,bcastAddress,maskAddress,ipv6";
	
	@Autowired
	private ResHostInterfaceDao resHostInterfaceDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ResHostInterface> list){
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final ResHostInterface obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<ResHostInterface> getAll() {
		return resHostInterfaceDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public ResHostInterface getById(Integer id){
		return resHostInterfaceDao.get(id);
	}
	
	public void save(ResHostInterface resHostInterface) {
		logger.debug("save...");
		resHostInterfaceDao.save(resHostInterface);
	}
	
	public List<ResHostInterface> getByHostId(Long hostId){
		String hql = " from ResHostInterface where hostId = ?";
		return resHostInterfaceDao.find(hql,hostId);
	}
	
	public void deleteList(List<ResHostInterface> interfaceList){
		resHostInterfaceDao.delete(interfaceList);
	}

}
