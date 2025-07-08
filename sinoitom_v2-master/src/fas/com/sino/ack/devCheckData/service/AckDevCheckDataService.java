/**
 * 
 */
package com.sino.ack.devCheckData.service;

import com.sino.ack.devCheckData.dao.AckDevCheckDataDao;
import com.sino.ack.devCheckData.dao.ParamCheckData;
import com.sino.ack.devCheckData.entity.AckDevCheckData;
import com.sino.base.common.util.JsonUtils;
import com.sino.snmp.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Mr.LP
 * @date 2019-7-11下午2:14:29
 *
 */

@Service
@Transactional
public class AckDevCheckDataService {
	
	
	
	private static Logger logger= LoggerFactory.getLogger(AckDevCheckDataService.class);
	
	private static String objAttNames ="ackDataId,devId,devIp,checkCmdId,checkCmd,indGroupName,indItemID,indItemEnName,indItemName,valueType,indvalue,length,decimals,measureUnit,remark";
	private static String jsonAttNames="id,devId,devIp,checkCmdId,checkCmd,indGroupName,indItemID,indItemEnName,indItemName,valueType,indvalue,length,decimals,measureUnit,remark";

	private static String paramObjAttNames ="chkResult,indItemName,indvalue";
	
	private static String paramJsonAttNames ="chkResult,indItemName,indvalue";
	@Autowired
	private AckDevCheckDataDao ackDevCheckDataDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<AckDevCheckData> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getParamJsonListStr(final List<ParamCheckData> list) {
		return JsonUtils.getJsonListInfo(list, paramObjAttNames, paramJsonAttNames);
	}
	
	public List<AckDevCheckData> getAll() {
		return ackDevCheckDataDao.getAll();
	}
	public AckDevCheckData getById(Long id){
		return ackDevCheckDataDao.get(id);
	}
	
	public List<ParamCheckData> getDevCheckData(Integer ackTaskId,Timestamp ts,String devIp,String checkCmd ){
		logger.debug("Enter getDevCheckData..");
		return ackDevCheckDataDao.getDevCheckData(ackTaskId,ts,devIp,checkCmd);
	}

	public List<ParamCheckData> getDevCheckDataFromColumnBase(Integer ackTaskId,long ackTaskStartTime,String devIp,int checkCmdId ){
		logger.info("Enter AckDevCheckDataService getDevCheckDataFromColumnBase");
		return ackDevCheckDataDao.getDevCheckDataFromColumnBase(ackTaskId,ackTaskStartTime, Util.ip2long(devIp),checkCmdId);
	}
}
