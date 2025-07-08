package com.sino.fas.res.host.dao;


import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.fas.res.host.entity.IpHost;
import com.sino.fas.res.host.entity.IpHostInfo;

@Component
public class IpHostDao extends BaseDao<IpHost,Long>{
	@SuppressWarnings("unchecked")
	public List<IpHost> getIphostByOrgId(List<String> orgIds,long sIp){
		//System.out.println("get the sIp:>>"+sIp);
		StringBuffer hql=new StringBuffer("from IpHost where orgId in (:orgIds)");
		boolean flag=sIp!=-1;
		if(flag){
			hql.append(" and switchId =:sIp");
		}
		hql.append(" order by switchId,ifIndex");
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameterList("orgIds", orgIds);
		if(flag) query.setLong("sIp", sIp);
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List getIphosts(int typeCode){
		//System.out.println("get the sIp:>>"+sIp);
		StringBuffer hql=new StringBuffer("select i from IpHost i,NcmDevices s  where  i.switchId=s.devId and i.ipHostTypeId=:typeCode and i.ipHostId not in (select resId from AckResources)");
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameter("typeCode", typeCode);
		return query.list();
	}
}
