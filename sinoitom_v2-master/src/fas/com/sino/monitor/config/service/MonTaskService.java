package com.sino.monitor.config.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.monitor.config.dao.MonTaskDao;
import com.sino.monitor.config.dao.TaskMapDevicesDao;
import com.sino.monitor.config.entity.MonTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class MonTaskService {

	private static Logger logger = LoggerFactory.getLogger(MonTaskService.class);
	
	private static String objAttName="orgId,orgId@OrgOrganization@orgId@orgName,taskId,taskName,taskType,taskDesc,operation,runMode,startTime,intervalHours,filePrefix,fileFormat,creator,createTime,modifier,modifyTime,flag,status";
	private static String jsnonAttName="orgId,orgName,id,taskName,taskType,taskDesc,operation,runMode,startTime,intervalHours,filePrefix,fileFormat,creator,createTime,modifier,modifyTime,flag,status";
	
	@Autowired
	private MonTaskDao monTaskDao;
	
	@Autowired
	private TaskMapDevicesDao taskMapDevicesDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<MonTask> list) {
		return JsonUtils.getJsonListInfo(list, objAttName, jsnonAttName);
	}
	
	public List<MonTask> getAllByOrgCode(String treeCode){
		String hql=" from MonTask where orgId in (select orgId from OrgOrganization where  treeCode like ?)";
		return monTaskDao.find(hql, treeCode+"%");
	}
	
	public void save(MonTask task){
		monTaskDao.save(task);
	}
	
	public void batchSave(List<MonTask> tasks){
		monTaskDao.batchSave(tasks, 10);
	}
	public MonTask getById(int id){
		return monTaskDao.get(id);
	}
	
	public void deletes(List<Integer> ids){
		monTaskDao.deletes(ids);
		
		taskMapDevicesDao.deletesByTaskIds(ids);
	}
	
	public List<MonTask> getByIds(List<Integer> ids){
		return monTaskDao.getByIds(ids);
	}
	public List<ResItemParam> getTree(String orgId) {
		return monTaskDao.getAllTaskType(orgId);
	}
	public List<ResItemParam> getTreeByTaskType(String taskType, String orgId) {
		return monTaskDao.getAllTaskName(taskType,orgId);
	}
	public List<MonTask> getByTaskID(int taskId){
		String hql=" from MonTask where taskID=?";
		return monTaskDao.find(hql, taskId);
	}
	public List<MonTask> getBytaskType(String taskType, String orgId){
		String hql=" from MonTask where taskType=? and orgId=?";
		return monTaskDao.find(hql, taskType,orgId);
	}
	
	
	public List<MonTask> getAll(){
		return monTaskDao.getAll();
	}
}
