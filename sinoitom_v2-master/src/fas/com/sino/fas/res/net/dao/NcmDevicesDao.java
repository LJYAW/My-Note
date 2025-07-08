package com.sino.fas.res.net.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.fas.res.net.entity.NcmDevices;
import com.sino.fas.res.net.entity.ParamDevHostMap;

@Component
public class NcmDevicesDao extends BaseDao<NcmDevices,Long>{

	public List<NcmDevices> getDataByDevIds(List<Long> devidlist){
		StringBuffer hql=new StringBuffer("from NcmDevices where devId in (:devidlist)");
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameterList("devidlist", devidlist);
		return query.list();
	} 
	
	//自动巡检-巡检设备页面中用
	@SuppressWarnings("unchecked")
	public List<Integer> getVendorIdBySwitch(){
		String hql="select distinct vendorId from NcmDevices";
		Query query=this.getSession().createQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getVendorIdByDevice(){
		String hql="select distinct vendorId from NcmDevices";
		Query query=this.getSession().createQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<NcmDevices> getSwitchsByIds(List<String> ids){
		Query query=this.getSession().createQuery("from NcmDevices where devId in (:ids) ");  
		query.setParameterList("ids", ids); 
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<NcmDevices> getSwitchsByOrgIds(List<String> orgIds){
		Query query=this.getSession().createQuery("from NcmDevices where orgId in (:orgIds) order by devIpLong ");  
		query.setParameterList("orgIds", orgIds); 
		return query.list();
	}
	
	public List getDeviceByIphostId(Set set){
		Query query=this.getSession().createQuery("select dev,iphost.ipHostId from NcmDevices as dev, IpHost as iphost where devId in (select switchId FROM IpHost where ipHostId in (:resIds))");  
		query.setParameterList("resIds", set); 
		return query.list();
	}
}
