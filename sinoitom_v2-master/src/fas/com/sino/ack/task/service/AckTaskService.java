package com.sino.ack.task.service;

import com.sino.ack.task.dao.AckTaskDao;
import com.sino.ack.task.entity.AckTask;
import com.sino.base.common.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AckTaskService {
	
	private static String objAttNames ="ackTaskId,taskName,taskDescr,checkCycle,cycleUnit,taskType,checkTimes,firstChkTime,status,pollCycle,tmplId,tmplName";
	private static String jsonAttNames="id,taskName,taskDescr,checkCycle,cycleUnit,taskType,checkTimes,firstChkTime,status,pollCycle,tmplId,tmplName";

	@Autowired
	private AckTaskDao ackTaskDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<AckTask> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	public List<AckTask> getAll() {
		return ackTaskDao.getAll();
	}
	public void save(AckTask entity){
		ackTaskDao.save(entity);
	}
	public AckTask getById(Integer id){
		return ackTaskDao.get(id);
	}
	public void deletes(String [] ids){
		for(String id:ids){
			delete(Integer.parseInt(id));
		}
	}
	public void delete(Integer id){
		ackTaskDao.delete(id);
	}
	
}
