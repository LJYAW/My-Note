/*
 * 文件名： ResSnmpIndicatorDao.java
 * 
 * 创建日期： 2014-9-4
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.monitor.res.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.BaseDao;
import com.sino.monitor.res.entity.MonResource;
import com.sino.monitor.res.entity.ResSnmpIndicator;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-4
 */
@Component
public class ResSnmpIndicatorDao extends BaseDao<ResSnmpIndicator,Integer> {

	@SuppressWarnings("unchecked")
	public List<ResSnmpIndicator> getDataByIds(List<Integer> ids){
		Query query=this.getSession().createQuery("from ResSnmpIndicator where resIndItemID in (:ids)");  
		query.setParameterList("ids", ids); 
		return query.list();
	}
	
	public void batchSetThtesholdID(List<Integer> resIndIds,Integer thresholdID) {
		String hql = "update ResSnmpIndicator set thresholdID=:thresholdID where resIndItemID in(:resIndIds)";
		Query queryItem = this.getSession().createQuery(hql);
		queryItem.setParameterList("resIndIds", resIndIds);
		queryItem.setParameter("thresholdID", thresholdID);
		queryItem.executeUpdate();
	}
	
	
	public void updateThtesholdID(List<Integer> resIndIds) {
		String hql = "update ResSnmpIndicator set thresholdID=null where resIndItemID in(:resIndIds)";
		Query queryItem = this.getSession().createQuery(hql);
		queryItem.setParameterList("resIndIds", resIndIds);
		queryItem.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public void delete(String ifID,String lineID){
		
		String hql="";
		if(!lineID.equals("null")){
			hql="delete from ResSnmpIndicator where subResID =:ifID or subResID = :lineID";
		}else{
			hql="delete from ResSnmpIndicator where subResID =:ifID";
		}
		
		Query query=this.getSession().createQuery(hql);
		
		if(!lineID.equals("null")){
			query.setParameter("ifID", Long.valueOf(ifID)); 
			query.setParameter("lineID", Long.valueOf(lineID)); 
		}else{
			query.setParameter("ifID", Long.valueOf(ifID)); 
		}
		
	    query.executeUpdate();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Integer> getResTypeCodes(){
		String hql="select distinct resTypeCode from ResSnmpIndicator";
		Query query=this.getSession().createQuery(hql);
		return query.list();
	}
	
	
	public void deleteByResIPs(List<String> resIps){
		logger.debug("Enter deleteByResIPs...");
		String hql=" delete ResSnmpIndicator where resIP in (:resIps) and (IndClassCode=1 or IndClassCode=2)";
		Query query=this.getSession().createQuery(hql);
		query.setParameterList("resIps", resIps); 
		query.executeUpdate();
	}
	
	public void deleteBySubIfIDs(List<Long> ifIds){
		logger.debug("Enter deleteBySubIfIDs...");
		String hql=" delete ResSnmpIndicator where subResID in (:ifIds) and IndClassCode=8";
		Query query=this.getSession().createQuery(hql);
		query.setParameterList("ifIds", ifIds); 
		query.executeUpdate();
	}
	
	public void deleteByLineIDs(List<Long> lineIds){
		logger.debug("Enter deleteByLineIDs...");
		String hql=" delete ResSnmpIndicator where subResID in (:lineIds) and IndClassCode=9";
		Query query=this.getSession().createQuery(hql);
		query.setParameterList("lineIds", lineIds); 
		query.executeUpdate();
	}
	
	
	public void deleteBySubResID(long  id){
		logger.debug("Enter deleteBySubResID...");
		String hql=" delete ResSnmpIndicator where subResID=:id";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("id", id); 
		query.executeUpdate();
	}
	
//	public void updateBySubResID(long lineID) {
//		String hql = "update ResSnmpIndicator set subResType=:subResType,subResID=:subResId,subResName=:subResName,subResInfo=:subResInfo where subResID=:subResID";
//		Query query = this.getSession().createQuery(hql);
//		query.setParameter("subResType", null);
//		query.setParameter("subResId", null);
//		query.setParameter("subResName", null);
//		query.setParameter("subResInfo", null);
//		query.setParameter("subResID", null);
//		query.executeUpdate();
//	}
	
	@SuppressWarnings("unchecked")
	public List<String> getResIpByTypeCode(int code){
		String hql="select distinct resIP from ResSnmpIndicator where resTypeCode=:code";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("code", code);
		return query.list();
	}
}
