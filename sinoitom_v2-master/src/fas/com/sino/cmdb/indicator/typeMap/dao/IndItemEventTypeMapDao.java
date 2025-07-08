/*
 * 文件名： IndItemEventTypeMapDao.java
 * 
 * 创建日期： 2014-10-22
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.indicator.typeMap.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.indicator.typeMap.entity.IndItemEventTypeMap;
import com.sino.cmdb.indicator.typeMap.entity.ParamIndEventType;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-10-22
 */
@Component
public class IndItemEventTypeMapDao extends BaseDao<IndItemEventTypeMap,Integer> {

	public List<ParamIndEventType> getData(){
		String hql="SELECT m.id,m.indItemID,m.eventTypeID,i.indClassName,i.indGroupName,i.indicatorItem,i.indItemName,t.eventClassName,t.eventTypeCode,t.eventTypeName " +
				"FROM IndItemEventTypeMap AS m,IndicatorItems AS i,EventType AS t " +
				"where m.eventTypeID=t.eventTypeID and m.indItemID=i.indItemID ";
		
		Query query=this.getSession().createQuery(hql);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ParamIndEventType> paramType=new ArrayList<ParamIndEventType>();
		while(it.hasNext()){
			Object [] temp=(Object [])it.next();
			ParamIndEventType type=new ParamIndEventType();
			type.setId((Integer)temp[0]);
		    type.setIndItemID((Long)temp[1]);
		    type.setEventTypeID((Integer)temp[2]);
		    type.setIndClassName((String)temp[3]);
			type.setIndGroupName((String)temp[4]);
			type.setIndicatorItem((String)temp[5]);
			type.setIndItemName((String)temp[6]);
			type.setEventClassName((String)temp[7]);
			type.setEventTypeCode((Integer)temp[8]);
			type.setEventTypeName((String)temp[9]);
			paramType.add(type);
		}
		return paramType;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getAllClassCode() {
		StringBuffer hql=new StringBuffer("select distinct b.indClassCode,b.indClassName ");
		hql.append("from IndItemEventTypeMap a, IndicatorItems b where a.indItemID = b.indItemID order by indClassCode");
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
	public List<ResItemParam> getByClassCode(int indClassCode) {
		StringBuffer hql=new StringBuffer("select distinct b.indGroupID,b.indGroupName ");
		hql.append("from IndItemEventTypeMap a, IndicatorItems b where a.indItemID = b.indItemID and b.indClassCode=:indClassCode order by indClassCode");
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameter("indClassCode", indClassCode);
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
	public List<ParamIndEventType> getAllByClassCode(int indClassCode) {
		String hql="SELECT m.id,m.indItemID,m.eventTypeID,i.indClassName,i.indGroupName,i.indicatorItem,i.indItemName,t.eventClassName,t.eventTypeCode,t.eventTypeName " +
				"FROM IndItemEventTypeMap AS m,IndicatorItems AS i,EventType AS t " +
				"where m.eventTypeID=t.eventTypeID and m.indItemID=i.indItemID and i.indClassCode=:indClassCode order by i.indClassCode,i.indGroupName";
		
		Query query=this.getSession().createQuery(hql);
		query.setParameter("indClassCode", indClassCode);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ParamIndEventType> paramType=new ArrayList<ParamIndEventType>();
		while(it.hasNext()){
			Object [] temp=(Object [])it.next();
			ParamIndEventType type=new ParamIndEventType();
			type.setId((Integer)temp[0]);
		    type.setIndItemID((Long)temp[1]);
		    type.setEventTypeID((Integer)temp[2]);
		    type.setIndClassName((String)temp[3]);
			type.setIndGroupName((String)temp[4]);
			type.setIndicatorItem((String)temp[5]);
			type.setIndItemName((String)temp[6]);
			type.setEventClassName((String)temp[7]);
			type.setEventTypeCode((Integer)temp[8]);
			type.setEventTypeName((String)temp[9]);
			paramType.add(type);
		}
		return paramType;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ParamIndEventType> getByIndClassCodeAndGroupID(int indClassCode,
			long indGroupID) {
		String hql="SELECT m.id,m.indItemID,m.eventTypeID,i.indClassName,i.indGroupName,i.indicatorItem,i.indItemName,t.eventClassName,t.eventTypeCode,t.eventTypeName " +
				"FROM IndItemEventTypeMap AS m,IndicatorItems AS i,EventType AS t " +
				"where m.eventTypeID=t.eventTypeID and m.indItemID=i.indItemID and i.indClassCode=:indClassCode and indGroupID=:indGroupID order by i.indClassCode,i.indGroupName";
		
		Query query=this.getSession().createQuery(hql);
		query.setParameter("indClassCode", indClassCode);
		query.setParameter("indGroupID", indGroupID);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ParamIndEventType> paramType=new ArrayList<ParamIndEventType>();
		while(it.hasNext()){
			Object [] temp=(Object [])it.next();
			ParamIndEventType type=new ParamIndEventType();
			type.setId((Integer)temp[0]);
		    type.setIndItemID((Long)temp[1]);
		    type.setEventTypeID((Integer)temp[2]);
		    type.setIndClassName((String)temp[3]);
			type.setIndGroupName((String)temp[4]);
			type.setIndicatorItem((String)temp[5]);
			type.setIndItemName((String)temp[6]);
			type.setEventClassName((String)temp[7]);
			type.setEventTypeCode((Integer)temp[8]);
			type.setEventTypeName((String)temp[9]);
			paramType.add(type);
		}
		return paramType;
	}
}
