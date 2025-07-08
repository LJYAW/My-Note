/*
 * 文件名： ResourceTypeDao.java
 * 
 * 创建日期： 2013-12-21
 *
 * Copyright(C) 2013, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.fas.res.resource.resourceType.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.tree.TreeDao;
import com.sino.fas.res.resource.resourceType.entity.ResourceType;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.3 $
 *
 * @since 2013-12-21
 */
@Component
public class ResourceTypeDao extends TreeDao<ResourceType, String>{

	public long getCounts(String hql){
		Query query=this.getSession().createQuery(hql.toString());  
		 return ((Long)query.uniqueResult());  
	}
}
