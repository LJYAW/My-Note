package com.sino.cmdb.graph.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.graph.entity.CmdbResNode;
@Component
public class CmdbResNodeDao extends BaseDao<CmdbResNode,Long>{

	public void deleteByGraphId(String graphId){
		String hql=" delete from CmdbResNode where graphId=:graphId ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("graphId", graphId);
		query.executeUpdate();
	}

	public CmdbResNode getResIdByCode(String graphId) {
		String hql="from CmdbResNode where resClassCode=:resClassCode and graphId=:graphId";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("resClassCode", 27);
		query.setParameter("graphId", graphId);
		CmdbResNode resId = (CmdbResNode)query.uniqueResult();
		return resId;
	}

	public List<CmdbResNode> getByBizList(String bizIdListStr) {
		String hql = "from CmdbResNode where graphId in (select graphId from CmdbResNode where resId in (:bizIdListStr))";
		Query query=this.getSession().createQuery(hql);
		List<Long> idList = new ArrayList<>();
		String[] split = bizIdListStr.split(",");
		for(String item : split){
			idList.add(Long.parseLong(item));
		}
		query.setParameterList("bizIdListStr", idList);
		List<CmdbResNode> list = query.list();
		return list;
	}
}
