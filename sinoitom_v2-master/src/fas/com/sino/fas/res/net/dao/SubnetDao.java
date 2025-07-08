package com.sino.fas.res.net.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.fas.res.net.entity.NcmSubNetwork;

@Component
public class SubnetDao extends BaseDao<NcmSubNetwork,Long> {
	
	public void deleteSubNetwork(NcmSubNetwork network){
		String hql="delete NcmSubNetwork where hostIpStart>=:start and hostIpEnd<=:end ";
		Query query=this.getSession().createQuery(hql);  
		query.setParameter("start", network.getHostIpStart()); 
		query.setParameter("end", network.getHostIpEnd()); 
		query.executeUpdate();
	}

}
