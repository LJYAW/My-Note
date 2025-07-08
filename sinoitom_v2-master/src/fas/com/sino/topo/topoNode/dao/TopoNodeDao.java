package com.sino.topo.topoNode.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.topo.topoNode.entity.TopoNode;

@Component
public class TopoNodeDao extends BaseDao<TopoNode,Long> {
	//
	public void deleteByGraphId(long graphId){
		String hql=" delete from TopoNode where graphId=:graphId ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("graphId", graphId);
		query.executeUpdate();
	}
}
