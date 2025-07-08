/*
 * 文件名： VendorProdSeriesDao.java
 * 
 * 创建日期： 2014-2-12
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.product.prodSeries.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.product.prodSeries.entity.VendorProdSeries;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-2-12
 */
@Component
public class VendorProdSeriesDao extends BaseDao<VendorProdSeries,Integer>{

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getAllVendor() {
		StringBuffer hql=new StringBuffer("select distinct b.vendorID,b.dispName ");
		hql.append("from VendorProdSeries a,CmdbVendor b where a.vendorID = b.vendorID  order by b.vendorID");
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
	public List<ResItemParam> getVendor() {
		StringBuffer hql=new StringBuffer("select distinct b.vendorID,b.dispName ");
		hql.append("from VendorProdSeries a,CmdbVendor b where a.vendorID = b.vendorID  order by b.vendorID,a.prodClassCode,a.prodTypeID");
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
	public List<ResItemParam> getByVendor(int vendorID) {
		StringBuffer hql=new StringBuffer("select distinct c.prodClassCode,c.prodClassName ");
		hql.append("from VendorProdSeries a,CmdbVendor b,ProdType c where a.vendorID = b.vendorID and a.prodClassCode = c.prodClassCode  ");
		hql.append("and a.vendorID=:vendorID order by b.vendorID,a.prodClassCode,a.prodTypeID");
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

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getByClassCode(int vendorID, int prodClassCode) {
		StringBuffer hql=new StringBuffer("select distinct c.typeCode,c.dispName ");
		hql.append("from VendorProdSeries a,ProdType c where a.prodTypeID = c.prodTypeID ");
		hql.append("and a.vendorID=:vendorID and c.prodClassCode=:prodClassCode order by c.vendorID,c.prodClassCode,c.typeCode");
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameter("vendorID", vendorID);
		query.setParameter("prodClassCode", prodClassCode);
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
