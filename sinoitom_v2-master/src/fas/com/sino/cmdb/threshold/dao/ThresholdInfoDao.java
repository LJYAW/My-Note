/*
 * 文件名： ThresholdTypeDao.java
 * 
 * 创建日期： 2014-9-10
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.threshold.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.threshold.entity.ParamThresholdInfoAndType;
import com.sino.cmdb.threshold.entity.ThresholdInfo;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-10
 */
@Component
public class ThresholdInfoDao  extends BaseDao<ThresholdInfo,String> {

	@SuppressWarnings({ "rawtypes" })
	public List<ParamThresholdInfoAndType> getAllParam() {
		StringBuffer hql=new StringBuffer("select a.uUID,a.threshTypeID,a.thresholdName,a.thresholdLevel,a.description,b.indObjName,b.indDimName,b.indGroupName,b.threshTypeName,b.defaultUnit,b.unitName,a.status,a.threshold_ID");
		hql.append(" from ThresholdInfo a,ThresholdType b where a.threshTypeID=b.threshTypeID ");
		System.out.println(hql);
		List<ParamThresholdInfoAndType> paramList = new ArrayList<ParamThresholdInfoAndType>();
		Query query=this.getSession().createQuery(hql.toString());  
		List list=query.list();
		Iterator it  =  list.iterator();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ParamThresholdInfoAndType param = new ParamThresholdInfoAndType();
			param.setuUID(tuple[0].toString());
			param.setThreshTypeID(Integer.parseInt(tuple[1].toString()));
			param.setThresholdName(tuple[2].toString());
			param.setThresholdLevel(Integer.parseInt(tuple[3].toString()));
			param.setDescription(tuple[4].toString());
			param.setIndObjName(tuple[5].toString());
			param.setIndDimName(tuple[6].toString());
			param.setIndGroupName(tuple[7].toString());
			param.setThreshTypeName(tuple[8].toString());
			param.setDefaultUnit(tuple[9].toString());
			param.setUnitName(tuple[10].toString());
			if (null == tuple[11]){
				param.setStatus(0);
			}else{
				param.setStatus(Integer.parseInt(tuple[11].toString()));
			}
			param.setThreshold_ID(Integer.parseInt(tuple[12].toString()));
			paramList.add(param);
		}
		return paramList;
	}
	
	
	@SuppressWarnings({ "rawtypes" })
	public List<ParamThresholdInfoAndType> getAllParamByStatus(long indGroupID) {
		StringBuffer hql=new StringBuffer("select a.uUID,a.threshTypeID,a.thresholdName,a.thresholdLevel,a.description,b.indObjName,b.indDimName,b.indGroupName,b.threshTypeName,b.defaultUnit,b.unitName,a.status,a.threshold_ID");
		hql.append(" from ThresholdInfo a,ThresholdType b where a.threshTypeID=b.threshTypeID and a.status=1 and b.indGroupID=:indGroupID");
		System.out.println(hql);
		List<ParamThresholdInfoAndType> paramList = new ArrayList<ParamThresholdInfoAndType>();
		Query query=this.getSession().createQuery(hql.toString());  
		query.setParameter("indGroupID", indGroupID);
		List list=query.list();
		Iterator it  =  list.iterator();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			ParamThresholdInfoAndType param = new ParamThresholdInfoAndType();
			param.setuUID(tuple[0].toString());
			param.setThreshTypeID(Integer.parseInt(tuple[1].toString()));
			param.setThresholdName(tuple[2].toString());
			param.setThresholdLevel(Integer.parseInt(tuple[3].toString()));
			param.setDescription(tuple[4].toString());
			param.setIndObjName(tuple[5].toString());
			param.setIndDimName(tuple[6].toString());
			param.setIndGroupName(tuple[7].toString());
			param.setThreshTypeName(tuple[8].toString());
			param.setDefaultUnit(tuple[9].toString());
			param.setUnitName(tuple[10].toString());
			if (null == tuple[11]){
				param.setStatus(0);
			}else{
				param.setStatus(Integer.parseInt(tuple[11].toString()));
			}
			param.setThreshold_ID(Integer.parseInt(tuple[12].toString()));
			paramList.add(param);
		}
		return paramList;
	}
	

	public void deleteByUUID(String id) {
		String hql = "delete from ThresholdInfo where uuid = :ID ";
		Query queryItem = this.getSession().createQuery(hql);
		queryItem.setParameter("ID", id);
		queryItem.executeUpdate();
	}

}
