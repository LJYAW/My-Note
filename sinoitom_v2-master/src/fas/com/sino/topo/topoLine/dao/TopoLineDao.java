/*
 * 文件名： TopoLineDao.java
 * 
 * 创建日期： 2015-1-26
 *
 * Copyright(C) 2015, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.topo.topoLine.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.topo.topoLine.entity.TopoLine;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision$
 *
 * @since 2015-1-26
 */
@Component
public class TopoLineDao extends BaseDao<TopoLine,Long>{

	public void deleteLineByGraphId(long graphId){
		String hql=" delete from TopoLine where graphId=:graphId ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("graphId", graphId);
		query.executeUpdate();
	}
	
}
