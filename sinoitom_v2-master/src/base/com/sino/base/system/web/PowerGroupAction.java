package com.sino.base.system.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.PropertyFilter;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.SysPowerGroup;
import com.sino.base.system.service.PowerGroupService;

@Controller
@RequestMapping(value = "/system/power/group")
public class PowerGroupAction {
	private String viewPath = "/system/power/group";

	private static Logger logger = LoggerFactory.getLogger(PowerGroupAction.class);

	@Autowired
	private PowerGroupService powGrpService;
	
	
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<SysPowerGroup> list = powGrpService.getAllPowerGroup();
		String jsonStr = powGrpService.getJsonListStr(list);
		map.put("jsonTreeData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	}

	@RequestMapping(value = "/getData.do")
	public String getData(HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		List<SysPowerGroup> list = powGrpService.getAllPowerGroup();
		String jsonStr = powGrpService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}                                                   

	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<SysPowerGroup> list = powGrpService.searchPowerGroup(filters);
		String jsonStr = powGrpService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}

	@RequestMapping(value = "/add.do")
	public String add(String parentId, ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		SysPowerGroup powGrp = new SysPowerGroup();
		powGrp.setParentId(parentId);
		map.put("powGrp", powGrp);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		SysPowerGroup powGrp = powGrpService.getPowerGroup(id);
		map.put("powGrp", powGrp);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/save.do")
	public String save(String action, SysPowerGroup powGrp, ModelMap map) {
		logger.info( "Enter save.do ..." );
		boolean validate = true;
		if( StringUtils.isBlank(powGrp.getGrpName()) ){
			validate = false;
			map.put("message", "权限组名称不能为空！");  
		}
		
		if( validate ){
			if( "add".equals(action)){
				powGrpService.addPowerGroup(powGrp);
			}		
			else{
				powGrpService.savePowerGroup(powGrp);		
			}
			String jsonStr = powGrpService.getJsonObjStr(powGrp);
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("result", "error");			
		}
		
		map.put("powGrp", powGrp);
		map.put("action", action);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/moveGo.do")
	public String moveGo(String id, ModelMap map) throws IOException{     
		logger.info( "Enter moveGo.do ..." );
		SysPowerGroup powGrp = powGrpService.getPowerGroup(id);
		map.put("powGrp", powGrp);
		List<SysPowerGroup> list = powGrpService.findMoveTrees(powGrp);
		String jsonStr = powGrpService.getJsonTreeStr(list);
		map.put("jsonTreeData", jsonStr);
		return viewPath+"/move";
	}                                                   

	@RequestMapping(value = "/move.do")
	public String move(String moveId, String targetId, int moveType, HttpServletResponse response) throws IOException{     
		logger.info( "Enter move.do ..." );
		if( powGrpService.movePowerGroup(moveId, targetId, moveType) ){
			response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		}
		else{
			response.getWriter().print(JsonUtils.JSON_RESULT_FALSE);			
		}
	    return null;         
	}                                                   
	
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		powGrpService.deletePowerGroup(id);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}

}
