/**
 * 
 */
package com.sino.fas.res.host.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.fas.res.host.entity.ResHosts;

/**
 * @author Mr.LP
 * @date 2019-8-22上午9:56:02
 * @className ResHostsDao
 *
 * @Description TODO
 *
 */

@Component
public class ResHostsDao extends BaseDao<ResHosts,Long>{

	public List<ResHosts> getHostsByOrgId(String orgID) {
		String hql = "from ResHosts where orgID=?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, orgID);
		List<ResHosts> list = query.list();
		return list;
	}
	
	public List<ResHosts> getListByHostIds(List<Long> hostIds){
		String hql = "from ResHosts where hostId in (:hostIds)";
		Query query = this.getSession().createQuery(hql);
		query.setParameterList("hostIds", hostIds);
		List<ResHosts> list = query.list();
		return list;
	}
	public List<ResHosts> getListByIpAddress(Set set){
		String hql = "from ResHosts where ipAddress in (:ipAddress)";
		Query query = this.getSession().createQuery(hql);
		query.setParameterList("ipAddress", set);
		List<ResHosts> list = query.list();
		return list;
	}
	

}
