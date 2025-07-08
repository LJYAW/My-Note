package com.sino.cmdb.indicator.items.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.ack.resources.entity.AckResources;
import com.sino.base.common.BaseDao;
import com.sino.cmdb.indicator.items.entity.IndicatorItems;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-7-26
 */
@Component
public class IndItemsDao extends BaseDao<IndicatorItems, Long>{
	
	@SuppressWarnings("unchecked") 
	public List<IndicatorItems> getDataByIds(List<Long> itemIds){
		String hql=" from IndicatorItems where indItemID in (:itemIds) ";
		Query query=this.getSession().createQuery(hql);
		query.setParameterList("itemIds", itemIds); 
		return query.list();
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getAllClassCode() {
		StringBuffer hql=new StringBuffer("select distinct indClassCode,indClassName ");
		hql.append("from IndicatorItems order by indClassCode");
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
		StringBuffer hql=new StringBuffer("select distinct indGroupID,indGroupName ");
		hql.append("from IndicatorItems where indClassCode=:indClassCode order by indGroupName");
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
	
	public List<IndicatorItems> getItemsList(int devClassCode,int devTypeCode) {
		String sql = "from IndicatorItems where indItemID not in (select indItemID from  DevIndicatorItems where devClassCode=:devClassCode and devTypeCode=:devTypeCode )";
		Query query= this.getSession().createQuery(sql);
		query.setParameter("devClassCode", devClassCode); 
		query.setParameter("devTypeCode", devTypeCode); 
		List<IndicatorItems> list = query.list();
		return list;
	} 
	public List<IndicatorItems> getIndClass(int devClassCode,int devTypeCode) {
		String sql = " select distinct indClassCode,indClassName from IndicatorItems where indItemID not in (select indItemID from  DevIndicatorItems where devClassCode=:devClassCode and devTypeCode=:devTypeCode ) ";
		Query query= this.getSession().createQuery(sql);
		query.setParameter("devClassCode", devClassCode); 
		query.setParameter("devTypeCode", devTypeCode); 
		List list=query.list();
		Iterator it  =  list.iterator();
		List<IndicatorItems> itemlist = new ArrayList<IndicatorItems>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			IndicatorItems itemParam = new IndicatorItems();
			itemParam.setIndClassCode(Integer.parseInt(tuple[0].toString()));
			itemParam.setIndClassName(tuple[1].toString());
			itemlist.add(itemParam);
		}
		
		return itemlist;
	}
	
	
}
