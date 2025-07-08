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

import com.sino.base.common.util.CommandUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.SysParamGroup;
import com.sino.base.system.entity.SysParamItem;
import com.sino.base.system.service.ParamGroupService;

@Controller
@RequestMapping(value = "/system/param/group")
public class ParamGroupAction {
	private static String viewPath = "/system/param/group";
	
	private static Logger logger = LoggerFactory.getLogger(ParamGroupAction.class);

	@Autowired
	private ParamGroupService resGroupService;
	
	
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<SysParamGroup> list = resGroupService.getAllParamGroup();
		String jsonStr = resGroupService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	} 
	
	@RequestMapping(value = "/getData.do")
	public String getData(HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		List<SysParamGroup> list = resGroupService.getAllParamGroup();
		String jsonStr = resGroupService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}  
	
	
	@RequestMapping(value = "/toSearch.do")
	public String toSearch(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter toSearch.do ..." );
		return viewPath+"/search";      
	}
	
	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response,ModelMap map) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<SysParamGroup> list = resGroupService.searchParamGroup(filters);
		String jsonStr = resGroupService.getJsonListStr(list);
//		response.getWriter().print(jsonStr);
		map.put("jsonListData", jsonStr);
		map.put("result", "success");
		return viewPath+"/search";       
	}


	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		SysParamGroup resGroup = new SysParamGroup();
		map.put("group", resGroup);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		SysParamGroup resGroup = resGroupService.getParamGroup(id);
		map.put("group", resGroup);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/itemValue.do")
	public String itemValue(String id, String grpCode, ModelMap map,String oprType) {
		logger.info( "Enter itemValue.do ..." );
		SysParamGroup resGroup = null;
		if( id != null ){
			resGroup = resGroupService.getParamGroup(id);
		}
		else if( grpCode != null ){
			resGroup = resGroupService.getParamGroupByCode(grpCode);
		}
		map.put("group", resGroup);
		map.put("oprType", oprType);
		return viewPath+"/itemValue";
	}
	
	@RequestMapping(value = "/paramValue.do")
	public String paramValue(String id, String grpCode, ModelMap map) {
		logger.info( "Enter paramValue.do ..." );
		SysParamGroup resGroup = null;
		if( id != null ){
			resGroup = resGroupService.getParamGroup(id);
		}
		else if( grpCode != null ){
			resGroup = resGroupService.getParamGroupByCode(grpCode);
		}
		map.put("group", resGroup);
		return "/system/bizParam/item/value";
	}
	@RequestMapping(value = "/saveValue.do")
	public String saveValue(String id, HttpServletRequest resquest, ModelMap map,String oprType) {
		logger.info( "Enter saveValue.do ..." );
		SysParamGroup resGroup = resGroupService.getParamGroup(id);
		for (SysParamItem i: resGroup.getSysParamItems()) {
			String val = resquest.getParameter(i.getParamCode());
			if( val != null ){
				i.setValueText(val);
				CommandUtils.checkParamChange(i.getParamCode(), val);
			}
		}
		resGroupService.saveParamGroup(resGroup);
		if("switch".equals(oprType)){
			SystemUtils.addLog("修改交换机搜索参数","成功修改交换机搜索参数！",2);
		}else if("device".equals(oprType)){
			SystemUtils.addLog("修改路由器搜索参数","成功修改路由器搜索参数！",1);
		}
		map.put("group", resGroup);
		map.put("result", "success");
		return viewPath+"/itemValue";
	}

	@RequestMapping(value = "/save.do")
	public String save(String action, SysParamGroup resGroup, ModelMap map) {
		logger.info( "Enter save.do ..." );
		
		boolean validate = true;
		if( StringUtils.isBlank(resGroup.getGrpName()) ){
			validate = false;
			map.put("message", "参数组名称不能为空！");
		}
		else if( StringUtils.isBlank(resGroup.getGrpCode()) ){
			validate = false;
			map.put("message", "参数组编码不能为空！");
		}
		else if( !resGroupService.isGrpCodeUnique(resGroup.getParamGrpId(), resGroup.getGrpCode()) ){
			validate = false;
			map.put("message", "参数组编码已经存在！");
		}
		
		if( validate ){
			if( "add".equals(action)){
				resGroupService.addParamGroup(resGroup);	
			}
			else{
				resGroupService.saveParamGroup(resGroup);					
			}
			String jsonStr = resGroupService.getJsonObjStr(resGroup);
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("result", "error");			
		}

		map.put("group", resGroup);
		map.put("action", action);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/moveUp.do")
	public String moveUp(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter moveUp.do ..." );
		SysParamGroup item = resGroupService.getParamGroup(id);
		resGroupService.moveUpItem(item);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	@RequestMapping(value = "/moveDown.do")
	public String moveDown(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter moveDown.do ..." );
		SysParamGroup item = resGroupService.getParamGroup(id);
		resGroupService.moveDownItem(item);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	} 

	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		resGroupService.deleteParamGroup(id);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}

}
