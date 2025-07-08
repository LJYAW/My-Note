package com.sino.ack.devCheckResult.web;

import com.sino.ack.devCheckResult.entity.AckDevCheckResult;
import com.sino.ack.devCheckResult.service.AckDevCheckResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@RequestMapping(value="/ack/devCheckResult")
@Controller
public class AckDevCheckResultAction {

	private static Logger logger= LoggerFactory.getLogger(AckDevCheckResultAction.class);
	
	@Autowired
	private AckDevCheckResultService ackDevCheckResultService;
	
	@RequestMapping(value = "/getDevCheckResult.do")
	public String getDevCheckResult(Integer ackTaskId,String taskStartTime,String devIp, HttpServletResponse response) throws IOException {
		logger.info("Enter getDevCheckResult.do..");
		Timestamp ts = new Timestamp(System.currentTimeMillis());  
		 try {  
	            ts = Timestamp.valueOf(taskStartTime);  
	            System.out.println(ts);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
		List<AckDevCheckResult> list=ackDevCheckResultService.getDevCheckResult(ackTaskId,ts,devIp);
		String jsonStr=ackDevCheckResultService.getJsonListStr(list);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
		return null;
	}
	
	@RequestMapping(value = "/getDevCheckCmdResult.do")
	public String getDevCheckCmdResult(Integer ackTaskId,long startTime,String devIp, Integer checkCmdId,HttpServletResponse response) throws IOException {
		logger.info("Enter getDevCheckCmdResult.do..");
		List<AckDevCheckResult> list=ackDevCheckResultService.getDevCheckResult(ackTaskId,startTime,devIp,checkCmdId);
		String jsonStr=ackDevCheckResultService.getJsonListStr(list);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
		return null;
	}
	
	
}
