package com.sino.fas.res.net.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.fas.res.net.entity.NcmDevAccessUser;

@Component
public class NcmDevAccessUserDao extends BaseDao<NcmDevAccessUser,String>{

	public List<NcmDevAccessUser> getDataByOrgIds(List<String> orgIds){
		StringBuffer hql=new StringBuffer("from NcmDevAccessUser where orgId in (:orgIds)");
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameterList("orgIds", orgIds);
		return query.list();
	}

    public NcmDevAccessUser getByResId(Long resId) {
		String hql = "from NcmDevAccessUser where devAcsUserId=(select devAccessId from NcmDevices where devId=:devId) ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("devId",resId);
		NcmDevAccessUser ncmDevAccessUser = (NcmDevAccessUser) query.uniqueResult();
		return ncmDevAccessUser;
    }
}
