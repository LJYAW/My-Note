/*
 * 文件名： OperationDao.java
 * 
 * 创建日期： 2014-9-13
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.operation.devOp.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.operation.devOp.entity.Operation;

/**
 * 
 * 
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 * 
 * @version $Revision$
 * 
 * @since 2014-9-13
 */
@Component
public class OperationDao extends BaseDao<Operation, Integer> {

	public void batchAudit(List<Integer> opIds) {
		String hql = "update Operation set status=1 where opID in(:opIds)";
		Query queryItem = this.getSession().createQuery(hql);
		queryItem.setParameterList("opIds", opIds);
		queryItem.executeUpdate();
	}
}
