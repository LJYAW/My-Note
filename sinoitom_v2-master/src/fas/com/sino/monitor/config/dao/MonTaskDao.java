package com.sino.monitor.config.dao;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.monitor.config.entity.MonTask;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class MonTaskDao extends BaseDao<MonTask,Integer> {

	public void deletes(List<Integer> ids){
		String hql=" delete MonTask where taskId in(:ids)";
		Query query=this.getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		query.executeUpdate();
	}
	
	public List<MonTask> getByIds(List<Integer> ids){
		String hql=" from  MonTask where taskId in(:ids)";
		Query query=this.getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		return query.list();
	}
	
	
	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getAllTaskType(String orgId){
		String hql = "select distinct taskType from MonTask where orgId=:orgId";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("orgId", orgId);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<ResItemParam> paramList = new ArrayList<ResItemParam>();
		
		while(it.hasNext()){
			
			Object tuple  =   (Object) it.next(); 
			ResItemParam itemParam = new ResItemParam();
			itemParam.setId(tuple.toString());
			itemParam.setText(tuple.toString());
			paramList.add(itemParam);
		}
		return paramList;
		
	}
	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getAllTaskName(String taskType , String orgId){
		String hql = "select  taskId,taskName from MonTask where TaskType=:taskType and orgId=:orgId";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("taskType", taskType);
		query.setParameter("orgId", orgId);
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
