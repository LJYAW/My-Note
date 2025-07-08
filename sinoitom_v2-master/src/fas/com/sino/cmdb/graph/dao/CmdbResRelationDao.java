package com.sino.cmdb.graph.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.graph.entity.CmdbResRelation;

@Component
public class CmdbResRelationDao extends BaseDao<CmdbResRelation,Long>{

	public void deleteLineByGraphId(String graphId){
		String hql=" delete from CmdbResRelation where graphId=:graphId ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("graphId", graphId);
		query.executeUpdate();
	}

}
