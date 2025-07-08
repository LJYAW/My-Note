package com.sino.monitor.config.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.monitor.config.dao.TaskMapDevicesDao;
import com.sino.monitor.config.entity.Mon_TaskMapEntity;
import com.sino.monitor.config.entity.ParamDevTask;
import com.sino.monitor.config.entity.TaskMapDevices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskMapDevicesService {

	private static String objAttName="taskMapDevID,taskID,devID,orgID,orgName,maintainer,vendorID,vendorName,devClassCode,devClassName,devTypeCode,devTypeName,devIP,devIpLong,devName,accessMode,snmpVersion,snmpRwStr,devAccessID,devPromptID,policyID,monFlag";
	private static String jsonAttName="taskMapDevID,taskID,devID,orgID,orgName,maintainer,vendorID,vendorName,devClassCode,devClassName,devTypeCode,devTypeName,devIP,devIpLong,devName,accessMode,snmpVersion,snmpRwStr,devAccessID,devPromptID,policyID,monFlag";
	
	
	private static String paramObjAttName="taskMapDevID,taskID,taskName,taskType,startTime,devID,orgID,orgName,maintainer,vendorID,vendorName,devClassCode,devClassName,devTypeCode,devTypeName,devIP";
	
	private static String paramJsonAttName="taskMapDevID,taskID,taskName,taskType,startTime,devID,orgID,orgName,maintainer,vendorID,vendorName,devClassCode,devClassName,devTypeCode,devTypeName,devIP";
    
	private static String paramObjAttNameTow="taskMapDevID,orgName,vendorName,devClassName,devTypeName,devIP,accessMode,monFlag,taskType,taskName,operation,startTime,intervalHours";
	
	private static String paramJsonAttNameTow="taskMapDevID,orgName,vendorName,devClassName,devTypeName,devIP,accessMode,monFlag,taskType,taskName,operation,startTime,intervalHours";
	
	@Autowired
	private TaskMapDevicesDao taskMapDevicesDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<TaskMapDevices> list) {
		return JsonUtils.getJsonListInfo(list, objAttName, jsonAttName);
	}
	@Transactional(readOnly = true)
	public String getJsonListStrTow(final List<Mon_TaskMapEntity> list) {
		return JsonUtils.getJsonListInfo(list, paramObjAttNameTow, paramJsonAttNameTow);
	}
	
	
	@Transactional(readOnly = true)
	public String getParamJsonListStr(final List<ParamDevTask> list) {
		return JsonUtils.getJsonListInfo(list, paramObjAttName, paramJsonAttName);
	}
	
	
	public TaskMapDevices getByID(long id){
		return taskMapDevicesDao.get(id);
	}
	
	public void batchSave(List<TaskMapDevices> list){
		taskMapDevicesDao.batchSave(list, 20);
	}
	
	public void save(TaskMapDevices taskMapDevices){
		taskMapDevicesDao.save(taskMapDevices);
	}
	public TaskMapDevices getByTaskIDAndDevID(int taskId, long devId){
		String hql=" from TaskMapDevices where taskID=? and devID=?";
		return taskMapDevicesDao.findUnique(hql, taskId,devId);
	}
	
	public List<String> getDeviceIpByTaskIds(List<Integer> ids){
		return taskMapDevicesDao.getDeviceIpByTaskIds(ids);
	}
	
	public List<TaskMapDevices> getByTaskID(int taskId){
		String hql=" from TaskMapDevices where taskID=?";
		return taskMapDevicesDao.find(hql, taskId);
	}
	public List<Mon_TaskMapEntity> getByTaskIdTow(int taskId) {
		//String hql=" from TaskMapDevices where taskID=?";
		return taskMapDevicesDao.getByTaskID(taskId);
	}
	@Transactional(readOnly = true)
	public List<TaskMapDevices> getAllTaskMapDevices(){
		
		return taskMapDevicesDao.getAll();
	}
	@Transactional(readOnly = true)
	public TaskMapDevices getTaskMapDevices(Long id){
		
		return taskMapDevicesDao.get(id);
	}
	public void deletes(List<Long> ids){
		taskMapDevicesDao.deletes(ids);
	}
	
	public List<String> getAllOrgIds(){
		return taskMapDevicesDao.getAllOrgIds();
	}
	
	public List<ResItemParam> getDevClassByOrgID(String orgID){
		return taskMapDevicesDao.getDevClassByOrgID(orgID);
	}
	public List<ResItemParam> getDevTypeByOrgIDAndClassCode(String orgID, int devClassCode){
		return taskMapDevicesDao.getDevTypeByOrgIDAndClassCode(orgID,devClassCode);
	}
	
	public List<TaskMapDevices> getParams(String orgID, int flag){
		String hql=" from TaskMapDevices where orgID=? and monFlag=?";
		return taskMapDevicesDao.find(hql, orgID,flag);
	}
	
	public List<TaskMapDevices> getParams(String orgID, int devClassCode, int flag){
		String hql=" from TaskMapDevices where orgID=? and monFlag=? and devClassCode=?";
		return taskMapDevicesDao.find(hql, orgID,flag,devClassCode);
	}
	
	public List<TaskMapDevices> getParams(String orgID, int devClassCode, int devTypeCode, int flag){
		String hql=" from TaskMapDevices where orgID=? and monFlag=? and devClassCode=? and devTypeCode=?";
		return taskMapDevicesDao.find(hql, orgID,flag,devClassCode,devTypeCode);
	}
	public void monitor(List<Long> ids,Integer monFlag){
	
		 taskMapDevicesDao.monitor(ids, monFlag);
	}
	
}
