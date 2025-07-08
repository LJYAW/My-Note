package com.sino.bpm.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.sino.base.common.util.WebFuncUtils;
import com.sino.bpm.entity.BpmTask;
import com.sino.res.biz.entity.ResBizSystem;
import com.sino.res.biz.service.ResBizSystemService;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sino.bpm.service.BpmTaskService;
import com.sino.cmdb.event.entity.EventType;

@RequestMapping(value="/bpm/task")
@Controller
public class BpmTaskAction {
	private String viewPath = "/bpm/task";
	private static Logger logger= LoggerFactory.getLogger(BpmTaskAction.class);
	
	@Autowired
	private BpmTaskService bpmTaskService;
	@Autowired
	private ResBizSystemService resBizSystemService;
/*
* @author fengyao
* @Description 列表
* @Date 13:43 2020/5/13
* @Param [menuId, map]
* return java.lang.String
**/
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		logger.info("Enter BpmTaskAction main.do ...");
		List<BpmTask> tasks = bpmTaskService.getAll();
		String jsonStr = bpmTaskService.getJsonListStr(tasks);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath + "/main";
	}

	/*
	* @author fengyao
	* @Description 关联业务系统页面
	* @Date 13:43 2020/5/13
	* @Param [id, map]
	* return java.lang.String
	**/
	@RequestMapping(value = "/addTaskRelateBiz.do")
	public String addTaskRelateBiz(Integer id, ModelMap map) {
		logger.info("Enter BpmTaskAction addAckCheckItems.do ...");
//		获取所有“启用”状态未关联的业务系统
		List<ResBizSystem> list = resBizSystemService.getEnableBizs(id);
		String jsonStr = resBizSystemService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		map.put("id", id);
		return viewPath + "/addRes";
	}
	/*
	* @author fengyao
	* @Description 任务关联业务系统，将相关资源信息保存到库
	* @Date 13:44 2020/5/13
	* @Param [taskId, bizIdListStr, map]
	* return java.lang.String
	**/
	@RequestMapping(value="taskRelateBiz.do")
	public String taskRelateBiz(Integer taskId,String bizIdListStr,ModelMap map) throws IOException{
		logger.info("Enter BpmTaskAction taskRelateBiz.do..");
		try {
			if(taskId!=null && StringUtils.isNotBlank(bizIdListStr)){
				bpmTaskService.taskRelateBiz(taskId,bizIdListStr);
				map.put("result", "success");
			}else{
				map.put("result", "error");	
			}
		} catch (Exception e) {
			map.put("result", "error");	
			e.printStackTrace();
		}
		map.put("jsonListData","[{}]");
		return viewPath+"/addRes";
	}
}
