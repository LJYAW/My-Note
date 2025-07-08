/**
 *
 */
package com.sino.ack.devCheckReport.service;

import com.sino.ack.devCheckReport.dao.AckDevCheckReportDao;
import com.sino.ack.devCheckReport.entity.AckDevCheckReport;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.entity.JqPageBean;
import com.sino.base.system.entity.JqPageResponse;
import com.sino.base.system.service.JqPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;

import java.util.List;

/**
 * @author Mr.LP
 * @date 2019-7-11下午2:34:05
 */

@Service
@Transactional
public class AckDevCheckReportService {

	private static String objAttNames = "devChkRptId,ackTaskId,ackTaskId@AckTask@ackTaskId@taskName,taskStartTime,taskEndTime,ackReport,useTime";
	private static String jsonAttNames = "id,ackTaskId,taskName,taskStartTime,taskEndTime,ackReport,useTime";

	@Autowired
	private AckDevCheckReportDao ackDevCheckReportDao;

	@Autowired
	private JqPageService pageService;

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<AckDevCheckReport> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	public List<AckDevCheckReport> getAll() {
		return ackDevCheckReportDao.getAll();
	}

	public AckDevCheckReport getById(Long id) {
		return ackDevCheckReportDao.get(id);
	}

	public List<AckDevCheckReport> getByTaskId(int ackTaskId) {
		String hql = "from AckDevCheckReport where ackTaskId = ? order by taskStartTime desc";
		return ackDevCheckReportDao.find(hql, ackTaskId);
	}

	public AckDevCheckReport getUniqueData(long devChkRptId, String taskStartTime) {
		String hql = " from AckDevCheckReport where devChkRptId=? and taskStartTime=?";
		return ackDevCheckReportDao.findUnique(hql, devChkRptId, taskStartTime);
	}

	public String getHistoryReport(JqPageBean pageBean, int taskId) {
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPageNo(pageBean.getPage());
		pageRequest.setPageSize(pageBean.getRows());
		pageRequest.setOrderBy(pageBean.getSidx());
		pageRequest.setOrderDir(pageBean.getSord());
		String hql = "select new com.sino.ack.devCheckReport.entity.AckReportDisplay(report.devChkRptId, report.taskStartTime, report.taskEndTime, report.useTime, task.taskName) from AckDevCheckReport report left join report.ackTask task where task.ackTaskId = ?";
		Page<AckDevCheckReport> page = ackDevCheckReportDao.findPage(pageRequest, hql, taskId);
		JqPageResponse<AckDevCheckReport> response = new JqPageResponse<AckDevCheckReport>();
		response.setRows(page.getResult());
		response.setPage(page.getPageNo());
		response.setTotal(page.getTotalPages());
		response.setRecords(page.getTotalItems());
		return pageService.getJsonObjStr(response);
	}
}
