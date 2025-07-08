package com.sino.monitor.config.dao;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.monitor.config.entity.Mon_TaskMapEntity;
import com.sino.monitor.config.entity.TaskMapDevices;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class TaskMapDevicesDao extends BaseDao<TaskMapDevices,Long> {
	
	public void deletes(List<Long> ids){
		String hql=" delete TaskMapDevices where taskMapDevID in(:ids)";
		Query query=this.getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		query.executeUpdate();
	}
	
	public void deletesByTaskIds(List<Integer> ids){
		String hql=" delete TaskMapDevices where taskID in(:ids)";
		Query query=this.getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		query.executeUpdate();
	}
	public void monitor(List<Long> ids,Integer monFlag){
		String hql="update TaskMapDevices set monFlag=:monFlag where taskMapDevID in (:ids) ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("monFlag", monFlag);
		query.setParameterList("ids", ids);
		query.executeUpdate();
	}
	
	public List<String> getDeviceIpByTaskIds(List<Integer> ids){
		String hql=" select devIP from  TaskMapDevices where taskID in(:ids)";
		Query query=this.getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		return query.list();
	}
	

	@SuppressWarnings({ "rawtypes" })
	public List<String> getAllOrgIds(){
		List<String> orgIds=new ArrayList<String>();
		String sql=" select distinct orgID from TaskMapDevices ";
		Query query=this.getSession().createQuery(sql);
		List list=query.list();
		Iterator it  =  list.iterator();
		while(it.hasNext()){
			Object tuple  =   (Object) it.next(); 
			orgIds.add(tuple.toString());
		}	
		return orgIds;
	}
	
	
	@SuppressWarnings({ "rawtypes" })
	public List<ResItemParam> getDevClassByOrgID(String orgID){
		String hql = "select distinct devClassCode,devClassName from TaskMapDevices where orgID=:orgID";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("orgID", orgID);
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
	public List<ResItemParam> getDevTypeByOrgIDAndClassCode(String orgID, int devClassCode){
		String hql = "select distinct devTypeCode,devTypeName from TaskMapDevices where orgID=:orgID and devClassCode=:devClassCode";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("orgID", orgID);
		query.setParameter("devClassCode", devClassCode);
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
	public List<Mon_TaskMapEntity> getByTaskID(int taskId) {
		StringBuffer hql=new StringBuffer("select b.taskMapDevID,b.orgName,b.vendorName,b.devClassName,b.devTypeName,b.devIP,b.accessMode,b.monFlag," +
				"a.taskType,a.taskName,a.operation,a.startTime,a.intervalHours ");
		hql.append("from MonTask a,TaskMapDevices b where a.taskId=:taskId and b.taskID=:taskId");
		Query query=this.getSession().createQuery(hql.toString());
		
		query.setParameter("taskId", taskId);
		List list=query.list();
		Iterator it  =  list.iterator();
		List<Mon_TaskMapEntity> paramList = new ArrayList<Mon_TaskMapEntity>();
		while(it.hasNext()){
			Object[] tuple  =  (Object[]) it.next(); 
			Mon_TaskMapEntity itemParam = new Mon_TaskMapEntity();
			itemParam.setTaskMapDevID(Long.valueOf(tuple[0].toString()));
			itemParam.setOrgName(tuple[1].toString());
			itemParam.setVendorName(tuple[2].toString());
			itemParam.setDevClassName(tuple[3].toString());
			itemParam.setDevTypeName(tuple[4].toString());
			itemParam.setDevIP(tuple[5].toString());
			itemParam.setAccessMode(tuple[6].toString());
			itemParam.setMonFlag(Integer.valueOf(tuple[7].toString()) );
			itemParam.setTaskType(tuple[8].toString());
			itemParam.setTaskName(tuple[9].toString());
			itemParam.setOperation(tuple[10].toString());
			itemParam.setStartTime(Timestamp.valueOf(tuple[11].toString()));
			itemParam.setIntervalHours(Integer.valueOf(tuple[12].toString()));
			
			paramList.add(itemParam);
		}
		return paramList;
	}
	
}
