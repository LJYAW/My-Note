/*
 * 文件名： DevIndicatorItemsDao.java
 * 
 * 创建日期： 2014-8-28
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.indicator.device.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.indicator.device.entity.DevIndicatorItems;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-8-28
 */
@Component
public class DevIndicatorItemsDao extends BaseDao<DevIndicatorItems,Integer> {
	
	@SuppressWarnings("unchecked") 
	public List<DevIndicatorItems> getDataByIds(List<Integer> itemIds){
		String hql=" from DevIndicatorItems where devIndItemID in (:itemIds) ";
		Query query=this.getSession().createQuery(hql);
		query.setParameterList("itemIds", itemIds); 
		return query.list();
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getDevTypeCodes(int code) {
		String hql="select distinct devTypeCode,devTypeName from DevIndicatorItems where devClassCode=:code";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("code", code);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ResItemParam> paramList = new ArrayList<ResItemParam>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ResItemParam itemParam = new ResItemParam();
			itemParam.setId(tuple[0].toString());
			itemParam.setText(tuple[1].toString());
			paramList.add(itemParam);
		}
		
		return paramList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getIndGroupName(int code) {
		String hql="select distinct indGroupName,indGroupID from DevIndicatorItems where devTypeCode=:code";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("code", code);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ResItemParam> paramList = new ArrayList<ResItemParam>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ResItemParam itemParam = new ResItemParam();
			itemParam.setId(tuple[1].toString());
			itemParam.setText(tuple[0].toString());
			paramList.add(itemParam);
		}
		
		return paramList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getDevClassCodes() {
		String hql="select distinct devClassCode,devClassName from DevIndicatorItems order by devClassCode asc,devTypeCode asc  ";
		Query query=this.getSession().createQuery(hql);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ResItemParam> paramList = new ArrayList<ResItemParam>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ResItemParam itemParam = new ResItemParam();
			itemParam.setId(tuple[0].toString());
			itemParam.setText(tuple[1].toString());
			paramList.add(itemParam);
		}
		
		return paramList;
	}

}
