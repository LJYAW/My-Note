/*
 * 文件名： VendorProdModelDao.java
 * 
 * 创建日期： 2014-2-12
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.product.prodModel.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.product.prodModel.entity.VendorProdModel;
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
public class VendorProdModelDao extends BaseDao<VendorProdModel,Long> {

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getAllVendor() {
		StringBuffer hql=new StringBuffer("select distinct b.vendorID,b.dispName ");
		hql.append("from VendorProdModel a,CmdbVendor b where a.vendorID = b.vendorID  order by b.vendorID");
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
		hql.append("from VendorProdModel a,CmdbVendor b where a.vendorID = b.vendorID  order by b.vendorID,a.prodClassCode,a.prodTypeCode,a.prodSeries ");
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
		StringBuffer hql=new StringBuffer("select distinct prodClassCode,prodClassName ");
		hql.append("from VendorProdModel where vendorID =:vendorID  order by vendorID,prodClassCode,prodTypeCode,prodSeries");
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
		StringBuffer hql=new StringBuffer("select distinct prodTypeCode,prodTypeName ");
		hql.append("from VendorProdModel where vendorID =:vendorID and prodClassCode=:prodClassCode  order by vendorID,prodClassCode,prodTypeCode,prodSeries");
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

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getBySeries(int vendorID, int prodClassCode,int typeCode) {
		StringBuffer hql=new StringBuffer("select distinct prodSeries,prodSeries ");
		hql.append("from VendorProdModel where vendorID =:vendorID and prodClassCode=:prodClassCode and prodTypeCode=:typeCode order by vendorID,prodClassCode,prodTypeCode,prodSeries");
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameter("vendorID", vendorID);
		query.setParameter("prodClassCode", prodClassCode);
		query.setParameter("typeCode", typeCode);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ResItemParam> paramList = new ArrayList<ResItemParam>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ResItemParam itemParam = new ResItemParam();
			itemParam.setId(tuple[0].toString());
			itemParam.setText(tuple[0].toString());
			paramList.add(itemParam);
		}
		
		return paramList;
	}

}
