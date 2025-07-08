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
import com.sino.base.system.entity.SysResGroup;
import com.sino.base.system.service.ResGroupService;

@Controller
@RequestMapping(value = "/system/res/group")
public class ResGroupAction {
	private static String viewPath = "/system/res/group";
	
	private static Logger logger = LoggerFactory.getLogger(ResGroupAction.class);

	@Autowired
	private ResGroupService resGroupService;
	
	
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<SysResGroup> list = resGroupService.getAllResGroup();
		String jsonStr = resGroupService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	}                                              

	@RequestMapping(value = "/getData.do")
	public String getData(HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		List<SysResGroup> list = resGroupService.getAllResGroup();
		String jsonStr = resGroupService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}                                                   
	
//	跳转到查询
	@RequestMapping(value = "/toSearch.do")
	public String toSearch(ModelMap map) {
		logger.info( "Enter toSearch.do ..." );
		return viewPath+"/search";   
	}
	
//	查询
	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response,ModelMap map) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<SysResGroup> list = resGroupService.searchResGroup(filters);
		String jsonStr = resGroupService.getJsonListStr(list);
		map.put("jsonStr", jsonStr);       
		map.put("action", "search"); 
		return viewPath+"/search";
	}

	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		SysResGroup resGroup = new SysResGroup();
		map.put("group", resGroup);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		SysResGroup resGroup = resGroupService.getResGroup(id);
		map.put("group", resGroup);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/save.do")
	public String save(String action, SysResGroup resGroup, ModelMap map) {
		logger.info( "Enter save.do ..." );
		
		boolean validate = true;
		if( StringUtils.isBlank(resGroup.getGrpName()) ){
			validate = false;
			map.put("message", "资源组名称不能为空！");
		}
		else if( StringUtils.isBlank(resGroup.getGrpCode()) ){
			validate = false;
			map.put("message", "资源组编码不能为空！");
		}
		else if( !resGroupService.isGrpCodeUnique(resGroup.getResGrpId(), resGroup.getGrpCode()) ){
			validate = false;
			map.put("message", "资源组编码已经存在！");
		}
		
		if( validate ){
			if( "add".equals(action)){
				resGroupService.addResGroup(resGroup);
			}		
			else{
				resGroupService.saveResGroup(resGroup);							
			}
			String jsonStr = resGroupService.getJsonObjStr(resGroup);
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("result", "error");			
		}

		map.put("action", action);
		map.put("group", resGroup);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/moveUp.do")
	public String moveUp(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter moveUp.do ..." );
		SysResGroup item = resGroupService.getResGroup(id);
		resGroupService.moveUpItem(item);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	@RequestMapping(value = "/moveDown.do")
	public String moveDown(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter moveDown.do ..." );
		SysResGroup item = resGroupService.getResGroup(id);
		resGroupService.moveDownItem(item);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	} 

	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		String[] split = id.split(",");
		for (String str : split) {
			resGroupService.deleteResGroup(str);
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}

}
