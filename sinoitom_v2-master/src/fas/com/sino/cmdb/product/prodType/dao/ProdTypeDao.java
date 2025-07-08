/*
 * 文件名： ProdTypeDao.java
 * 
 * 创建日期： 2014-1-16
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.cmdb.product.prodType.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.product.prodType.entity.ProdType;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.2 $
 *
 * @since 2014-1-16
 */
@Component
public class ProdTypeDao extends  BaseDao<ProdType, Integer> {

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getAllVendor() {
		StringBuffer hql=new StringBuffer("select distinct b.vendorID,b.dispName ");
		hql.append("from ProdType a,CmdbVendor b where a.vendorID = b.vendorID  order by b.vendorID,a.prodClassCode");
		Query query=this.getSession().createQuery(hql.toString());  
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
	public List<ResItemParam> getByClassCode(int vendorID) {
		StringBuffer hql=new StringBuffer("select distinct prodClassCode,prodClassName ");
		hql.append("from ProdType where vendorID=:vendorID order by vendorID,prodClassCode");
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameter("vendorID", vendorID);
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
