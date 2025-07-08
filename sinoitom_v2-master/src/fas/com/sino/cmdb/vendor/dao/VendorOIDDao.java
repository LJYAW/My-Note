/*
 * 文件名： VendorOIDDao.java
 * 
 * 创建日期： 2014-8-19
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.vendor.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.entity.ParamVendorOID;
import com.sino.cmdb.vendor.entity.VendorOID;


/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-8-19
 */
@Component
public class VendorOIDDao extends BaseDao<VendorOID, String> {
	@SuppressWarnings({ "rawtypes" })
	public List<ParamVendorOID> getDataById(String id){
		StringBuffer hql=new StringBuffer("select a.vendorID, a.vendorName,a.dispName,b.vendorOID,b.remark,b.status ");
		hql.append("from CmdbVendor a,VendorOID b where a.vendorID=b.vendorID ");
		if(!"all".equals(id) && !"".equals(id)){
			hql.append(" and a.vendorID = :id ");
		}
		hql.append(" order by b.vendorID ");
		Query query=this.getSession().createQuery(hql.toString());  
		if(!"all".equals(id) && !"".equals(id)){
			query.setParameter("id", Integer.parseInt(id));
		}
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ParamVendorOID> paramoids = new ArrayList<ParamVendorOID>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ParamVendorOID pOID= new ParamVendorOID();
			pOID.setVendorID(Integer.parseInt(tuple[0].toString()));
			pOID.setVendorName(tuple[1].toString());
			pOID.setDispName(tuple[2].toString());
			pOID.setVendorOID(tuple[3].toString());
			if(!"".equals(tuple[4]) && null != tuple[4]){
				pOID.setRemark(tuple[4].toString());
			}else{
				pOID.setRemark("");
			}
			if(!"".equals(tuple[5]) && null != tuple[5]){
				pOID.setStatus(Integer.parseInt(tuple[5].toString()));
			}else{
				pOID.setStatus(0);
			}
			paramoids.add(pOID);
		}
		return paramoids;
	}

	
	public void update(VendorOID saveVendorOID) {
				
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getVendorIds(List<String> ids){
		Query query=this.getSession().createQuery("from VendorOID where vendorOID in (:ids) ");  
		query.setParameterList("ids", ids); 
		List<VendorOID> vendorOIDs=(List<VendorOID>)query.list();
		List<Integer> vendorIds=new ArrayList<Integer>();
		if (vendorOIDs != null && !vendorOIDs.isEmpty()) {
			for (VendorOID v : vendorOIDs) {
				vendorIds.add(v.getVendorID());
			}
		}
		return vendorIds;
	}


	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getAllVendor() {
		StringBuffer hql=new StringBuffer("select distinct b.vendorID,b.dispName ");
		hql.append("from VendorOID a,CmdbVendor b where a.vendorID = b.vendorID  order by b.vendorID");
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
