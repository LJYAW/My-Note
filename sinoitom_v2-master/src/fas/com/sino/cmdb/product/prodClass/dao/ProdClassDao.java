/*
 * 文件名： ProdClassDao.java
 * 
 * 创建日期： 2014-1-15
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.cmdb.product.prodClass.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.product.prodClass.entity.ProdClass;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.entity.ParamVendor;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.2 $
 *
 * @since 2014-1-15
 */
@Component
public class ProdClassDao  extends BaseDao<ProdClass, Integer>{
	
	@SuppressWarnings({ "rawtypes" })
	public List<ParamVendor> getVendorByProdClass(){
		String hql=" select distinct a.vendorID ,b.dispName from ProdClass as a, CmdbVendor as b where a.vendorID=b.vendorID Order by DispName";
		Query query=this.getSession().createQuery(hql.toString()); 
		List list=query.list();
		
		List<ParamVendor> pVendorList=new ArrayList<ParamVendor>();
		
		Iterator it  =  list.iterator();
		while(it.hasNext()){
			Object[] obj=(Object [])it.next();
			ParamVendor pVendor=new ParamVendor();
			pVendor.setVendorID(Integer.parseInt(obj[0].toString()));
			pVendor.setDispName(obj[1].toString());
			pVendorList.add(pVendor);
		}
		
		return pVendorList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getAllVendor() {
		StringBuffer hql=new StringBuffer("select distinct b.vendorID,b.dispName ");
		hql.append("from ProdClass a,CmdbVendor b where a.vendorID = b.vendorID  order by b.vendorID");
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
	
   


}
