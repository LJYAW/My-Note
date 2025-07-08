package com.sino.ack.devCheckItem.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.ack.devCheckItem.entity.AckDevCheckItems;
import com.sino.base.common.BaseDao;

@Component
public class AckDevCheckItemsDao extends BaseDao<AckDevCheckItems, Long> {
	@SuppressWarnings("unchecked")
	public List<Integer> getResTypeCodes(){
		String hql="select distinct resTypeCode from AckDevCheckItems";
		Query query=this.getSession().createQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getResIpByTypeCode(int code){
		String hql="select distinct devIp from AckDevCheckItems where resTypeCode=:code";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("code", code);
		return query.list();
	}

}
