package com.sino.ack.taskMapRes.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sino.ack.resources.entity.AckResources;
import com.sino.ack.resources.entity.ParamSnmpRes;
import com.sino.ack.taskMapRes.entity.AckTaskMapRes;
import com.sino.ack.taskMapRes.entity.ParamTaskMapRes;
import com.sino.ack.taskMapRes.service.AckTaskMapResService;
import com.sino.base.common.util.JsonUtils;

@RequestMapping(value="/ack/taskMapRes")
@Controller
public class AckTaskMapResAction {
	
	private String viewPath = "/ack/taskMapRes";
	private String viewP = "/ack/task";
	private static Logger logger= LoggerFactory.getLogger(AckTaskMapResAction.class);
	
	@Autowired
	private AckTaskMapResService ackTaskMapResService ;
	

	@RequestMapping(value = "/main.do")
	public String main(ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<AckTaskMapRes> ackTasks=ackTaskMapResService.getAll();
		String jsonStr = ackTaskMapResService.getJsonListStr(ackTasks);
		map.put("jsonListData", jsonStr);
		return viewPath+"/main";
	}
	
	
		@RequestMapping(value="/saveAckRes.do")
	public String saveAckRes(Integer ackTaskId,String devJsonStrs,ModelMap map){
		logger.info("Enter save.do..");
		List<AckResources> resList=JsonUtils.getDTOList(devJsonStrs, AckResources.class);
		ackTaskMapResService.saveAckResources(ackTaskId,resList);
		map.put("result", "success");
		map.put("jsonListData", "[]");
//		map.put("action", "addAckResources");
		return viewP+"/addRes";
	}

}
