package com.sino.topo.objectType.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.topo.objectType.entity.TopoObjectType;

@Component
public class TopoObjectTypeDao extends BaseDao<TopoObjectType,String>{

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getAllClassCode() {
		StringBuffer hql=new StringBuffer("select distinct classCode,className ");
		hql.append("from TopoObjectType order by classCode");
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
	public List<ResItemParam> getClassCodes() {
		String hql="select distinct classCode ,className from TopoObjectType order by classCode";
		
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
	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getTypeCodes(int code) {
		
		String hql="select distinct typeCode,typeName from TopoObjectType where ClassCode=:code";
		
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
}
