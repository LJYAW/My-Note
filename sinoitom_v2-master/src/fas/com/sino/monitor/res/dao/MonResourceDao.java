/*
 * 文件名： MonResourceDao.java
 * 
 * 创建日期： 2014-9-3
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.monitor.res.dao;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.monitor.res.entity.MonResource;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-3
 */
@Component
public class MonResourceDao extends BaseDao<MonResource,Long> {

	@SuppressWarnings("unchecked")
	public List<Integer> getVendorIdByRes(){
		String hql="select distinct vendorID from MonResource";
		Query query=this.getSession().createQuery(hql);
		return query.list();
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<MonResource> getDevicesByIds(List<Long> ids,int vendorID){
		Query query=this.getSession().createQuery("from MonResource where resID in (:ids) and vendorID=:vendorID");  
		query.setParameterList("ids", ids); 
		query.setParameter("vendorID", vendorID); 
		return query.list();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<MonResource> getDataByIds(List<Long> ids){
		Query query=this.getSession().createQuery("from MonResource where resID in (:ids)");  
		query.setParameterList("ids", ids); 
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ResItemParam> getResTypeCodes(){
		String hql="select distinct resTypeCode,resTypeName from MonResource order by resTypeCode ";
		Query query = this.getSession().createQuery(hql);
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
	
	@SuppressWarnings("unchecked")
	public List<String> getResIpByTypeCode(int code){
		String hql="select distinct resIP from MonResource where resTypeCode=:code order by resIP";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("code", code);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getResNameByTypeCode(int code){
		String hql="select distinct resIP,resName from MonResource where resTypeCode=:code order by resName";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("code", code);
		return query.list();
	}
	
}
