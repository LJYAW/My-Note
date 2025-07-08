package com.sino.ack.task.web;

import com.alibaba.fastjson.JSON;
import com.sino.ack.resources.entity.AckResources;
import com.sino.ack.resources.service.AckResourcesService;
import com.sino.ack.task.entity.AckTask;
import com.sino.ack.task.service.AckTaskService;
import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sinobridge.nms.controller.ack.AutoCheckController;
import com.sinobridge.nms.core.collection.ack.pojo.SchedulerResult;
import com.sinobridge.nms.core.model.ResponseMessage;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@RequestMapping(value = "/ack/task")
@Controller
public class AckTaskAction {
	private String viewPath = "/ack/task";
	private static Logger logger = LoggerFactory.getLogger(AckTaskAction.class);

	@Autowired
	private AckTaskService ackTaskService;

	@Autowired
	private AckResourcesService ackResourcesService;

	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		logger.info("Enter main.do ...");
		List<AckTask> ackTasks = ackTaskService.getAll();
		String jsonStr = ackTaskService.getJsonListStr(ackTasks);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath + "/main";
	}


	//关联巡检设备
	@RequestMapping(value = "/addAckResources.do")
	public String addAckResources(Integer id, ModelMap map) throws ParseException {
		logger.info("Enter addAckCheckItems.do ...");
		//		List<AckResources> ackResources=ackResourcesService.getAll();
		List<AckResources> ackResources = ackResourcesService.getAckResList(id);
		String jsonStr = ackResourcesService.getJsonListStr(ackResources);
		map.put("jsonListData", jsonStr);
		map.put("id", id);
		return viewPath + "/addRes";
	}


	/**
	 * 查看巡检设备
	 * @param id
	 * @param map
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/searchAckRes.do")
	public String searchAckRes(Integer id, ModelMap map) {
		logger.info("Enter searchAckRes.do ...");
		List<AckResources> ackResources = ackResourcesService.searchAckResList(id);
		String jsonStr = ackResourcesService.getJsonListStr(ackResources);
		map.put("jsonListData", jsonStr);
		map.put("id", id);
		return viewPath + "/searchRes";
	}

	//巡检任务----添加
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info("Enter add.do ...");
		AckTask ackTask = new AckTask();
		map.put("ackTask", ackTask);
		map.put("action", "add");
		return viewPath + "/add";
	}

	@RequestMapping(value = "/save.do")
	public String save(String action, AckTask ackTask, ModelMap map) {
		logger.info("Enter save.do...");

		String cycleUnit = ackTask.getCycleUnit();
		if (cycleUnit.equals("minute")) {

			ackTask.setPollCycle(ackTask.getCheckCycle() * 60);

		} else if (cycleUnit.equals("hour")) {

			ackTask.setPollCycle(ackTask.getCheckCycle() * 60 * 60);
		} else if (cycleUnit.equals("day")) {

			ackTask.setPollCycle(ackTask.getCheckCycle() * 60 * 60 * 24);
		} else if (cycleUnit.equals("week")) {

			ackTask.setPollCycle(ackTask.getCheckCycle() * 60 * 60 * 24 * 7);
		} else if (cycleUnit.equals("month")) {
			ackTask.setPollCycle(ackTask.getCheckCycle() * 60 * 60 * 24 * 7 * 30);

		}
		ackTask.setStatus(0);
		ackTaskService.save(ackTask);
		map.put("result", "success");

		map.put("ackTask", ackTask);
		map.put("action", action);
		return viewPath + "/add";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(Integer id, ModelMap map) {
		logger.info("Enter edit.do...");
		AckTask ackTask = ackTaskService.getById(id);
		map.put("action", "edit");
		map.put("id", id);
		map.put("ackTask", ackTask);
		return viewPath + "/edit";
	}

	@RequestMapping(value = "/editSave.do")
	public String editSave(Integer id, AckTask ackTask, ModelMap map) {
		logger.info("Enter save.do...");
		AckTask Task = ackTaskService.getById(id);
		String cycleUnit = ackTask.getCycleUnit();
		if (cycleUnit.equals("minute")) {

			ackTask.setPollCycle(ackTask.getCheckCycle() * 60);

		} else if (cycleUnit.equals("hour")) {

			ackTask.setPollCycle(ackTask.getCheckCycle() * 60 * 60);
		} else if (cycleUnit.equals("day")) {

			ackTask.setPollCycle(ackTask.getCheckCycle() * 60 * 60 * 24);
		} else if (cycleUnit.equals("week")) {

			ackTask.setPollCycle(ackTask.getCheckCycle() * 60 * 60 * 24 * 7);
		} else if (cycleUnit.equals("month")) {
			ackTask.setPollCycle(ackTask.getCheckCycle() * 60 * 60 * 24 * 7 * 30);

		}
		BeanUtils.copyProperties(ackTask, Task);
		ackTaskService.save(Task);
		map.put("result", "success");

		map.put("ackTask", ackTask);
		return viewPath + "/edit";
	}

	@RequestMapping(value = "/delete.do")
	public void delete(String ackTaskIds, HttpServletResponse response) throws IOException {
		logger.info("Enter delete.do..");
		if (!ackTaskIds.isEmpty()) {
			String[] ids = ackTaskIds.split(";");
			ackTaskService.deletes(ids);
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	}

	/**
	 * 启动任务
	 * @param ids ids
	 * @throws IOException 远程调用异常
	 */
	@RequestMapping(value = "/start.do")
	public void start(@RequestBody ArrayList<Integer> ids, HttpServletResponse response) throws IOException {
		logger.info("ackTaskAction enter start method");
		String result;
		if(ids.size() != 0){
			AutoCheckController ackRemoteService = AutoCheckController.getInstance();
			if(ids.size() == 1){
				ResponseMessage<SchedulerResult> re = ackRemoteService
						.schedulerAckJob(ids.get(0));
				result = JSON
						.toJSONString(re);
			}else{
				ResponseMessage<List<SchedulerResult>> re = ackRemoteService.schedulerAckJob(ids);
				result = JSON
						.toJSONString(re);
			}
		}else{
			result = JSON.toJSONString(ResponseMessage.error("传入的id不能为空"));
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(result);}

	/**
	 * 启动所有任务
	 * @throws IOException 远程调用异常
	 */
	@RequestMapping(value = "/startAll.do")
	public void startAll(HttpServletResponse response) throws IOException {
		logger.info("ackTaskAction enter startAll method");
		AutoCheckController ackRemoteService = AutoCheckController.getInstance();
		ResponseMessage<List<SchedulerResult>> listResponseMessage = ackRemoteService.schedulerAllAckJob();
		String result = JSON
				.toJSONString(listResponseMessage);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(result);
	}

	/**
	 * 停止任务
	 * @param ids ids
	 * @throws IOException 远程调用异常
	 */
	@RequestMapping(value = "/stop.do")
	public void stop(@RequestBody ArrayList<Integer> ids, HttpServletResponse response) throws IOException {
		logger.info("ackTaskAction enter stop method");
		String result;
		if(ids.size() != 0){
			AutoCheckController ackRemoteService = AutoCheckController.getInstance();
			if(ids.size() == 1){
				ResponseMessage<SchedulerResult> re = ackRemoteService
						.unSchedulerAckJob(false, ids.get(0));
				result = JSON
						.toJSONString(re);
			}else{
				ResponseMessage<List<SchedulerResult>> re = ackRemoteService.unSchedulerAckJob(false, ids);
				result = JSON
						.toJSONString(re);
			}
		}else{
			result = JSON.toJSONString(ResponseMessage.error("传入的id不能为空"));
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(result);
	}


	/**
	 * 停止所有任务
	 * @throws IOException 远程调用异常
	 */
	@RequestMapping(value = "/stopAll.do")
	public void stopAll(HttpServletResponse response) throws IOException {
		logger.info("ackTaskAction enter stopAll method");
		AutoCheckController ackRemoteService = AutoCheckController.getInstance();
		ResponseMessage<List<SchedulerResult>> listResponseMessage = ackRemoteService.unSchedulerAllAckJob(false);
		String result = JSON
				.toJSONString(listResponseMessage);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(result);
	}

	/**
	 * 重启任务
	 * @param ids ids
	 * @throws IOException 远程调用异常
	 */
	@RequestMapping(value = "/restart.do")
	public void restart(@RequestBody ArrayList<Integer> ids,  HttpServletResponse response) throws IOException {
		logger.info("ackTaskAction enter restart method");
		String result;
		if(ids.size() != 0){
			AutoCheckController ackRemoteService = AutoCheckController.getInstance();
			if(ids.size() == 1){
				ResponseMessage<SchedulerResult> re = ackRemoteService
						.reSchedulerAckJob(ids.get(0));
				result = JSON
						.toJSONString(re);
			}else{
				ResponseMessage<List<SchedulerResult>> re = ackRemoteService.reSchedulerAckJob(ids);
				result = JSON
						.toJSONString(re);
			}
		}else{
			result = JSON.toJSONString(ResponseMessage.error("传入的id不能为空"));
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(result);
	}

	/**
	 * 重启所有任务
	 * @throws IOException 远程调用异常
	 */
	@RequestMapping(value = "/restartAll.do")
	public void restartAll(HttpServletResponse response) throws IOException {
		logger.info("ackTaskAction enter restartAll method");
		AutoCheckController ackRemoteService = AutoCheckController.getInstance();
		ResponseMessage<List<SchedulerResult>> listResponseMessage = ackRemoteService.reSchedulerAllAckJob();
		String result = JSON
				.toJSONString(listResponseMessage);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(result);
	}
}
