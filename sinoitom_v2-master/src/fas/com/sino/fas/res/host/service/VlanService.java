package com.sino.fas.res.host.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.host.dao.VlanDao;
import com.sino.fas.res.host.entity.VlanInfo;

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class VlanService {
	private static Logger logger = LoggerFactory.getLogger(VlanService.class);
	
	private static String objAttNames = "vlan_Id,switch_Id,ifIndex,vlan,vlan_Name";
	private static String jsonAttNames = "id,switch_Id,ifIndex,vlan,vlan_Name";
	
	@Autowired
	private VlanDao vlanDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<VlanInfo> list){
		logger.debug("getJsonListStr.service");
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final VlanInfo obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public VlanInfo getBy(String id){
		return vlanDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public VlanInfo getBy(Long switchId,Long ifIndex){
		String hql="from VlanInfo where switch_Id=? and ifIndex=?";
		List<VlanInfo> list=vlanDao.find(hql,switchId,ifIndex);
		VlanInfo info=null;
		if(list!=null&&!list.isEmpty()){
			info=list.get(0);
		}
		return info;
	}
	
	
}
