package com.sino.ack.devCheckResult.service;

import com.sino.ack.devCheckResult.dao.AckDevCheckResultDao;
import com.sino.ack.devCheckResult.entity.AckDevCheckResult;
import com.sino.base.common.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class AckDevCheckResultService {

	
	private static Logger logger= LoggerFactory.getLogger(AckDevCheckResultService.class);
	
	private static String objAttNames ="chkResultId,ackTaskId,taskStartTime,devId,devIp,checkCmdId,checkCmd,chkResult,cmdEndTime";
	private static String jsonAttNames="id,ackTaskId,taskStartTime,devId,devIp,checkCmdId,checkCmd,chkResult,cmdEndTime";
	
	@Autowired
	private  AckDevCheckResultDao ackDevCheckResultDao;
	
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<AckDevCheckResult> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	
	public List<AckDevCheckResult> getDevCheckResult(Integer ackTaskId,Timestamp ts,String devIp ){
		logger.debug("Enter getDevCheckResult..");
		String hql=" from AckDevCheckResult where ackTaskId=? and taskStartTime=? and devIp=? group by checkCmd";
		return ackDevCheckResultDao.find(hql, ackTaskId,ts,devIp);
	}
	
	public List<AckDevCheckResult> getDevCheckResult(Integer ackTaskId,long ackTaskStartTimeGMT,String devIp,int checkCmdId ){
		logger.debug("Enter getDevCheckResult..");
		String hql=" from AckDevCheckResult where ackTaskId=? and ackTaskStartTimeGMT=? and devIp=? and checkCmdId=? group by checkCmd";
		return ackDevCheckResultDao.find(hql, ackTaskId,ackTaskStartTimeGMT,devIp,checkCmdId);
	}
	
}
