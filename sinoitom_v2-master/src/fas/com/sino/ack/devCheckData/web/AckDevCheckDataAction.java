package com.sino.ack.devCheckData.web;

import com.sino.ack.devCheckData.dao.ParamCheckData;
import com.sino.ack.devCheckData.service.AckDevCheckDataService;
import com.sino.ack.devCheckResult.web.AckDevCheckResultAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@RequestMapping(value="/ack/checkData/")
@Controller
public class AckDevCheckDataAction {

	private static Logger logger= LoggerFactory.getLogger(AckDevCheckResultAction.class);
	
	@Autowired
	private AckDevCheckDataService ackDevCheckDataService;
	
	@RequestMapping(value = "/getDevCheckData.do")
	public String getDevCheckData(Integer ackTaskId,String startTime,String devIp,String checkcmd, HttpServletResponse response) throws IOException {
		logger.info("Enter getDevCheckResult.do..");
		Timestamp ts = new Timestamp(System.currentTimeMillis());  
		 try {  
	            ts = Timestamp.valueOf(startTime);  
	            System.out.println(ts);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }

		List<ParamCheckData> list=ackDevCheckDataService.getDevCheckData(ackTaskId,ts,devIp,checkcmd);
		String jsonStr=ackDevCheckDataService.getParamJsonListStr(list);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
		return null;
	}

	@RequestMapping(value = "/getDevCheckDataFromColumnBase.do")
	public String getDevCheckDataFromColumnBase(Integer ackTaskId,long startTime,String devIp,Integer checkCmdId, HttpServletResponse response) throws IOException {
		logger.info("Enter getDevCheckDataFromColumnBase.do..");
		List<ParamCheckData> list=ackDevCheckDataService.getDevCheckDataFromColumnBase(ackTaskId,startTime,devIp,checkCmdId);
		String jsonStr=ackDevCheckDataService.getParamJsonListStr(list);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
		return null;
	}
}
