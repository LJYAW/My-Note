package com.sino.monitor.config.web;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.entity.SysUser;
import com.sino.monitor.config.entity.MonTask;
import com.sino.monitor.config.entity.Mon_TaskMapEntity;
import com.sino.monitor.config.entity.TaskMapDevices;
import com.sino.monitor.config.service.MonTaskService;
import com.sino.monitor.config.service.TaskMapDevicesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@RequestMapping(value="/config/devTask")
@Controller
public class TaskMapDevicesAction {
	private static Logger logger= LoggerFactory.getLogger(TaskMapDevicesAction.class);
	public static String viewPath="/monitor/config/devTask";
	
	@Autowired
	private TaskMapDevicesService taskMapDevicesService;
	@Autowired
	private MonTaskService monTaskService;
	
	@RequestMapping(value="/save.do")
	public String save(String devJsonStrs, String taskId, String accessCode, String devOpID, ModelMap map){
		
		List<TaskMapDevices> list= JsonUtils.getDTOList(devJsonStrs, TaskMapDevices.class);
		List<TaskMapDevices> saveData=new ArrayList<TaskMapDevices>();
		
		MonTask task=monTaskService.getById(Integer.parseInt(taskId));
		
		for(int i=0;i<list.size();i++){
			TaskMapDevices devTask=list.get(i);
			devTask.setTaskID(Integer.parseInt(taskId));
			devTask.setAccessMode(accessCode);
			devTask.setMaintainer(task.getCreator());
			devTask.setDevOpID(Integer.parseInt(devOpID));
			saveData.add(devTask);
		}
		if(!saveData.isEmpty()){
			taskMapDevicesService.batchSave(saveData);
		}
		map.put("result", "success");
		return viewPath+"/addDevMain";
	}
	
	
	
	

	@RequestMapping(value = "/getTaskMapDevices.do")
	public String getTaskMapDevices(ModelMap map, String id) throws UnsupportedEncodingException   {
		logger.info( "Enter getTaskMapDevices.do ..." );
		
		List<Mon_TaskMapEntity> list =new ArrayList<Mon_TaskMapEntity>();
		String flag=id.substring(id.lastIndexOf("_")+1, id.length());
		
		 if(flag.equals("1")){
			 String taskId[] = id.split("_");
			list = taskMapDevicesService.getByTaskIdTow(Integer.parseInt(taskId[0]));
			String jsonStr = taskMapDevicesService.getJsonListStrTow(list);
			map.put("jsonStr", jsonStr);
			
		}
		return viewPath+"/main";
	}
	

	
	@RequestMapping(value = "/multiEdit.do")
	public String multiEdit(ModelMap map, String ids) throws UnsupportedEncodingException   {
		logger.info( "Enter multiEdit.do ..." );
		map.put("ids", ids);
		return viewPath+"/multiEdit";
	}
	@RequestMapping(value = "/multiSave.do")
	public String multiSave(String editOption, String ids, String accessMode, String monFlag, String snmpRwStr, String snmpVersion, ModelMap map) {
	    //System.out.println(ids);
		logger.info( "Enter multiSave.do ..." );
		boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		List<String> editOptionList=new ArrayList<String>();
		List<String> idsList=new ArrayList<String>();
		if(!StringUtil.isNullString(editOption)&&!StringUtil.isNullString(ids)){
			String[] editOp = editOption.split(",");
			String[] id =ids.split(",");
			for(int i=0;i<editOp.length;i++){
				editOptionList.add(editOp[i]);
			}
			for(int i=0;i<id.length;i++){
				idsList.add(id[i]);
			}
		}
		
		if( validate ){
			for (int i = 0; i < idsList.size(); i++) {
				TaskMapDevices taskMapDevices = taskMapDevicesService.getTaskMapDevices(Long.valueOf(idsList.get(i)));
				for (int j = 0; j < editOptionList.size(); j++) {
					if(editOptionList.get(j).equals("accessModeOption")){
						taskMapDevices.setAccessMode(accessMode);
					}else if(editOptionList.get(j).equals("monFlagOption")){
						taskMapDevices.setMonFlag(Integer.valueOf(monFlag));
					}else if(editOptionList.get(j).equals("snmpVersionOption")){
						taskMapDevices.setSnmpVersion(snmpVersion);
					}else if(editOptionList.get(j).equals("snmpRwStrOption")){
						taskMapDevices.setSnmpRwStr(snmpRwStr);
					}
					
				}
				taskMapDevicesService.save(taskMapDevices);
				
			}
			
			map.put("result", "success");
		}else{
			map.put("result", "error");
		}
		return viewPath+"/multiEdit";
	}
	@RequestMapping(value="/deletes.do")
	public String deletes(String ids,HttpServletResponse response) throws IOException{
		List<Long> idlist=new ArrayList<Long>();
		String [] array=ids.split(",");
		for(String id:array){
			idlist.add(Long.valueOf(id));
		}
		taskMapDevicesService.deletes(idlist);
		response.getWriter().print("success");
		return null;
	}
	@RequestMapping(value="/view.do")
	public String view(String id, ModelMap map) throws IOException{
		TaskMapDevices taskMapDevices = taskMapDevicesService.getByID(Long.valueOf(id));
		map.put("taskMapDevices", taskMapDevices);
		return viewPath+"/view";
		
	}
	@RequestMapping(value="/monitor.do")
	public String monitor(String ids,String monFlag,HttpServletResponse response) throws IOException{
		List<Long> idlist=new ArrayList<Long>();
		String [] array=ids.split(",");
		for(String id:array){
			idlist.add(Long.valueOf(id));
		}
		Integer mFlag = Integer.valueOf(monFlag);
		
		
		taskMapDevicesService.monitor(idlist, mFlag);
		response.getWriter().print("success");
		return null;
	}
	
}
