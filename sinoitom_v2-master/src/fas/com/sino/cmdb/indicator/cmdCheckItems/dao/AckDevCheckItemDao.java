package com.sino.cmdb.indicator.cmdCheckItems.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.indicator.cmdCheckItems.entity.AckDevCheckItem;
@Component
public class AckDevCheckItemDao extends BaseDao<AckDevCheckItem,Long>{

//	根据devId查找checkItemId集合
	public List<Integer> getByDevId(Long devId) {
		String hql="select checkItemId from AckDevCheckItem where devId=:devId";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("devId", devId);
		@SuppressWarnings("unchecked")
		List<Integer> list = query.list();
		
		return list;
	}

}
