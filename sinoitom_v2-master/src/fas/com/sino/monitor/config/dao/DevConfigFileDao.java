package com.sino.monitor.config.dao;

import com.sino.base.common.BaseDao;
import com.sino.monitor.config.entity.DevConfigFile;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DevConfigFileDao extends BaseDao<DevConfigFile,Long> {

	public void deletes(List<Long> ids){
		String hql=" delete DevConfigFile where id in(:ids)";
		Query query=this.getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		query.executeUpdate();
	}
	
	
	public void setBaseLine(long id){
		String hql1=" update DevConfigFile set baseLine=1 where id=:id";
		Query query=this.getSession().createQuery(hql1);
		query.setParameter("id", id);
		query.executeUpdate();
		
		String hql2=" update DevConfigFile set baseLine=0 where id!=:id";
		Query query2=this.getSession().createQuery(hql2);
		query2.setParameter("id", id);
		query2.executeUpdate();
		
	}
}
