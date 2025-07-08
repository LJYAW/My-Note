/*
 * 文件名： ThresholdLevelDao.java
 * 
 * 创建日期： 2014-9-12
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.threshold.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.threshold.entity.ThresholdLevel;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-12
 */
@Component
public class ThresholdLevelDao extends BaseDao<ThresholdLevel,Integer>{

	public void deleteByThresholdID(Integer id) {
		String hql = "delete from ThresholdLevel where threshold_ID = :ID ";
		Query queryItem = this.getSession().createQuery(hql);
		queryItem.setParameter("ID", id);
		queryItem.executeUpdate();
	}

}
