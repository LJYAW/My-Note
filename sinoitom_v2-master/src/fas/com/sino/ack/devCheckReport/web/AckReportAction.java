package com.sino.ack.devCheckReport.web;

import com.sino.ack.devCheckReport.entity.AckDevCheckReport;
import com.sino.ack.devCheckReport.service.AckDevCheckReportService;
import com.sino.ack.resources.entity.AckResources;
import com.sino.ack.resources.service.AckResourcesService;
import com.sino.ack.task.entity.AckTask;
import com.sino.ack.task.service.AckTaskService;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.JqPageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RequestMapping(value = "/ack/report")
@Controller
public class AckReportAction {
	private String viewPath = "/ack/report";
	private static Logger logger = LoggerFactory.getLogger(AckReportAction.class);

	@Autowired
	private AckTaskService ackTaskService;

	@Autowired
	private AckResourcesService ackResourcesService;

	@Autowired
	private AckDevCheckReportService ackDevCheckReportService;

	@RequestMapping(value = "/main.do")
	public String main(String menuId, ModelMap map) {

		logger.info("Enter main.do ...");
		List<AckTask> ackTasks = ackTaskService.getAll();
		String jsonStr = ackTaskService.getJsonListStr(ackTasks);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId, map);
		return viewPath + "/main";
	}


	@RequestMapping(value = "/viewRowReport.do")
	public String viewRowReport(ModelMap map, int id) {
		logger.info("Enter viewRowReport.do ...");
		map.put("taskId", id);
		return viewPath + "/devReport";
	}

	/**
	 * 服务器分页
	 */
	@RequestMapping(value = "/getHistoryReport.do")
	public void getHistoryReport(JqPageBean pageBean, HttpServletResponse response, int taskId) {
		logger.info("Enter getHistoryReport.do ...");
		try {
			String jsonStr = ackDevCheckReportService.getHistoryReport(pageBean, taskId);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().write(jsonStr);
		} catch (Exception e) {
			logger.error("{} getHistoryReport failed, cause by {}", AckReportAction.class, e.getMessage());
		}
	}


	@RequestMapping(value = "/viewDetailReport.do")
	public String viewDetailReport(ModelMap map, long id) {
		logger.info("Enter viewDetailReport.do ...");
		map.put("id", id);
		return viewPath + "/htmReport";
	}

	@RequestMapping(value = "/showHtmReport.do")
	public String showHtmReport(ModelMap map, long id, HttpServletResponse response) throws IOException {
		logger.info("Enter showHtmReport.do ...");
		AckDevCheckReport report = ackDevCheckReportService.getById(id);

		String htmlReport = report.getAckReport();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(htmlReport);
		return null;
	}

	@RequestMapping(value = "/downloadCPWord.do")
	public String downloadCPWord(long id, HttpServletResponse response) throws Exception {
		AckDevCheckReport report = ackDevCheckReportService.getById(id);
		String content = report.getAckReport();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		String now = df.format(new Date()); //系统当前时间
		String fileName = "任务报告" + now + ".doc";

		fileName = new String(fileName.getBytes(), "ISO-8859-1");   //名称中有中文时候如此设置
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

		response.setContentType("application/octet-stream");
		ServletOutputStream out = response.getOutputStream();
		out.write(content.getBytes("UTF-8"));
		out.flush();
		return null;
	}

	//	@RequestMapping(value="/downloadCPPDF.do")
	//    public String downloadCPPDF(long id,HttpServletResponse response) throws Exception
	//    {
	//
	//		AckDevCheckReport report = new AckDevCheckReport();
	//        report = ackDevCheckReportService.getById(id);
	//
	//        String content = report.getAckReport();
	//
	//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
	//        String now = df.format(new Date()); //系统当前时间
	//        String fileName = "巡检报告"+now+".pdf";
	//
	////        response.addHeader("Content-Disposition","attachment; filename=rename.pdf");
	//
	//        fileName = new String(fileName.getBytes(), "ISO-8859-1");   //名称中有中文时候如此设置
	//        response.setHeader("Content-Disposition", "attachment;fileName="+ fileName);
	//
	//        response.setContentType("application/octet-stream");
	//
	//        ServletOutputStream out = response.getOutputStream();
	//        HtmlConvertUtil.convert2PDFString(out,content);
	//        out.flush();
	//        return null;
	//    }

	private String getHtml(String htmReport) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("<html> \r\n");
		sb.append("<head> \r\n");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/> \r\n");
		sb.append("<title>巡检报告</title> \r\n");
		sb.append("<style type=\"text/css\"> \r\n");
		sb.append("@page { \r\n");
		sb.append("size: 1000px 1000px; \r\n");
		sb.append("} \r\n");
		sb.append("</style> \r\n");
		sb.append("</head> \r\n");
		sb.append("<body style=\"font-family:SimSun;font-size:22px;\"> \r\n");  //字体倾斜：font-style:italic;
		//        sb.append("<div align=\"center\" style=\"font-size:25px; margin-left:auto; margin-right:auto; margin-top:20px; margin-bottom:20px;\">任务报告</div> \r\n");
		sb.append("<div align=\"center\" >").append("<h2>巡检报告</h2>").append("</div> \r\n");
		sb.append(htmReport);
		sb.append("</body> \r\n");
		sb.append("</html> \r\n");

		return sb.toString();
	}


	@RequestMapping(value = "/viewHisCmdResult.do")
	public String viewHisCmdResult(long id, ModelMap map) {
		map.put("id", id);
		return viewPath + "/hisCmdResult/mainTab";
	}

	@RequestMapping(value = "/cmdOutputRet.do")
	public String cmdOutputRet(long id, ModelMap map) {
		showCheckReportDetail(id, map);
		return viewPath + "/hisCmdResult/cmdOutputRet";
	}

	@RequestMapping(value = "/parseResult.do")
	public String parseResult(long id, ModelMap map) {
		showCheckReportDetail(id, map);
		return viewPath + "/hisCmdResult/parseResult";
	}

	private void showCheckReportDetail(long id, ModelMap map) {
		AckDevCheckReport report = ackDevCheckReportService.getById(id);
		if (report != null) {
			AckTask ackTask = ackTaskService.getById(report.getAckTask().getAckTaskId());
			map.put("report", report);
			map.put("ackTask", ackTask);
			String tsStr = "";
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				tsStr = sdf.format(report.getTaskStartTime());
				System.out.println(tsStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("taskStartTime", tsStr);
		}
	}

	@RequestMapping(value = "/getAckResource.do")
	public String getAckResource(Integer ackTaskId, HttpServletResponse response) throws IOException {
		logger.info("Enter getAckResource.do ...");
		List<AckResources> list = ackResourcesService.searchAckResList(ackTaskId);
		String jsonStr = ackResourcesService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
		return null;
	}
}
