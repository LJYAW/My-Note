package com.sino.fas.res.net.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.fas.res.net.entity.NcmSnmpCredentials;

@Component
public class SnmpCredDao extends BaseDao<NcmSnmpCredentials,String> {
	public List<NcmSnmpCredentials> getSnmpByIds(List snmpIds){
		Query query=this.getSession().createQuery("from NcmSnmpCredentials where snmpCredId in (:snmpIds)");  
		query.setParameterList("snmpIds", snmpIds); 
		return query.list();
	}
}
