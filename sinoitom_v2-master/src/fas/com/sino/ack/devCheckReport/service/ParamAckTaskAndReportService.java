/**
 * 
 */
package com.sino.ack.devCheckReport.service;

import com.sino.ack.devCheckReport.entity.ParamAckTaskAndReport;
import com.sino.base.common.util.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mr.LP
 * @date 2019-7-11下午3:08:59
 */

@Service
@Transactional
public class ParamAckTaskAndReportService {
	
	private static String objAttNames ="devChkRptId,taskName,taskDescr,checkCycle,cycleUnit,taskType,checkTimes,pollCycle,taskStartTime,ackTaskId";
	private static String jsonAttNames="devChkRptId,taskName,taskDescr,checkCycle,cycleUnit,taskType,checkTimes,pollCycle,taskStartTime,ackTaskId";


	@Transactional(readOnly = true)
	public String getJsonListStr(final List<ParamAckTaskAndReport> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
}
