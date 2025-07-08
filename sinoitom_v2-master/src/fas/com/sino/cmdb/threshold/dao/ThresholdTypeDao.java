/*
 * 文件名： ThresholdTypeDao.java
 * 
 * 创建日期： 2014-9-10
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.threshold.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.threshold.entity.ParamThresholdInfoAndType;
import com.sino.cmdb.threshold.entity.ThresholdType;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-10
 */
@Component
public class ThresholdTypeDao  extends BaseDao<ThresholdType,Integer> {
	
	@SuppressWarnings({ "rawtypes" })
	public List<ThresholdType> getAllType() {
		String hql="select distinct indObjCode,threshTypeID,indObjName from ThresholdType ";
		Query query=this.getSession().createQuery(hql.toString());
		List list= query.list();
		Iterator it  =  list.iterator();
		List<ThresholdType> li=new ArrayList<ThresholdType>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ThresholdType param = new ThresholdType();
			param.setIndObjCode(Integer.parseInt(tuple[0].toString()));
			param.setThreshTypeID(Integer.parseInt(tuple[1].toString()));
			param.setIndObjName(tuple[2].toString());
			li.add(param);
		}
		return li;
	}

}
