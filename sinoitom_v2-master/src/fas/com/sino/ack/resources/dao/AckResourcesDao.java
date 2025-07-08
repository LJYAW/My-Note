package com.sino.ack.resources.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.ack.resources.entity.AckResources;
import com.sino.base.common.BaseDao;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.monitor.res.entity.MonResource;

@Component
public class AckResourcesDao extends BaseDao<AckResources, Integer>{
	
	
	@SuppressWarnings("unchecked")
	public List<ResItemParam> getResClassCodes(){
		String hql="select distinct resClassCode,resClassName from AckResources order by resClassCode ";
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
	
	
	
	@SuppressWarnings("unchecked")
	public List<ResItemParam> getResTypeCodes(int resClassCode){
		String hql="select distinct resTypeCode,resTypeName from AckResources where resClassCode=:resClassCode order by resTypeCode ";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("resClassCode", resClassCode);
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
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getResNameByTypeCode(int code){
		String hql="select distinct mgmtIP,resName from AckResources where resTypeCode=:code order by resName";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("code", code);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AckResources> getDevicesByIds(List<Long> ids,int vendorID){
		Query query=this.getSession().createQuery("from AckResources where resId in (:ids) and vendorID=:vendorID");  
		query.setParameterList("ids", ids); 
		query.setParameter("vendorID", vendorID); 
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AckResources> getDataByIds(List<Long> ids){
		Query query=this.getSession().createQuery("from AckResources where resId in (:ids)");  
		query.setParameterList("ids", ids); 
		return query.list();
	}

	public List<AckResources> getAckResList(int id) {
		String sql = "from AckResources where resId not in (select resId from AckTaskMapRes where ackTaskId=:id )";
		Query query= this.getSession().createQuery(sql);
		query.setParameter("id", id); 
		List<AckResources> list = query.list();
		return list;
	}
	
	public List<AckResources> searchAckResList(int id) {
		String sql = "from AckResources where resId in (select resId from AckTaskMapRes where ackTaskId=:id ) order by mgmtIpLong";
		Query query= this.getSession().createQuery(sql);
		query.setParameter("id", id); 
		List<AckResources> list = query.list();
		return list;
	}

}
