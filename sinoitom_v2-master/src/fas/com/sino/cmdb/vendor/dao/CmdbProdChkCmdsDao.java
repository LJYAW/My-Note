package com.sino.cmdb.vendor.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.entity.CmdbProdChkCmds;

@Component
public class CmdbProdChkCmdsDao extends BaseDao<CmdbProdChkCmds, Integer> {
	
	
	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getVendor() {
		StringBuffer hql=new StringBuffer("select distinct a.vendorID,a.dispName ");
		hql.append("from CmdbVendor a,CmdbProdChkCmds b where a.vendorID=b.vendorId order by b.vendorId asc,b.devClassCode asc,b.devTypeCode asc");
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
	public List<ResItemParam> getByVendor(int vendor) {
		String hql="select distinct devClassCode,devClassName from CmdbProdChkCmds where vendorId=:vendor";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("vendor", vendor);
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
	public List<ResItemParam> getByClassCode(int code,int vendor) {
		String hql="select distinct devTypeCode,devTypeName from CmdbProdChkCmds where devClassCode=:code and vendorId=:vendor";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("code", code);
		query.setParameter("vendor", vendor);
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
