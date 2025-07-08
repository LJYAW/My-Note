package com.sino.topo.graph.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.topo.graph.entity.TopoGraph;
@Component
public class TopoGraphManageDao extends BaseDao<TopoGraph,Long> {
	
	public void updata(Long id){
		String hql=new String(" update TopoGraph set flag = 0 where flag = :flag ");
		Query query=this.getSession().createQuery(hql);
		query.setParameter("flag", 1);
		query.executeUpdate();
		String hql1 = new String ("update TopoGraph set flag = 1 where graphId= :id");
		Query query1=this.getSession().createQuery(hql1);
		query1.setParameter("id", id);
		query1.executeUpdate();
		
	}
}
