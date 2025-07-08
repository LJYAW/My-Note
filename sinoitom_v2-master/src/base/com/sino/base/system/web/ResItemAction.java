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


import com.sino.base.common.Global;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.entity.SysResItem;
import com.sino.base.system.entity.SysResGroup;
import com.sino.base.system.service.ResGroupService;
import com.sino.base.system.service.ResItemService;

@Controller
@RequestMapping(value = "/system/res/item")
public class ResItemAction {
	private String viewPath = "/system/res/item";

	private static Logger logger = LoggerFactory.getLogger(ResItemAction.class);

	@Autowired
	private ResItemService resItemService;
	@Autowired
	private ResGroupService resGroupService;
	
	
	private String getJsonGroupItems(final SysResGroup group, int type){
		String jsonStr;
		List<SysResItem> listItem = resItemService.getGroupResItem(group.getResGrpId());
		if( group.getGrpType() == Global.GROUP_TYPE_TREE ){
			jsonStr = resItemService.getJsonTreeStr(listItem, type);
		}
		else{
			jsonStr = resItemService.getJsonListStr(listItem, type);
		}
		return jsonStr;
	}
	
	@RequestMapping(value = "/main.do")
	public String main(String grpId, ModelMap map) {
		logger.info( "Enter main.do ..." );
		SysResGroup group = resGroupService.getResGroup(grpId);
		map.put("group", group);
		String jsonStr = getJsonGroupItems(group, 0);
		if( group.getGrpType() == Global.GROUP_TYPE_TREE ){
			map.put("jsonTreeData", jsonStr);
			return viewPath+"/main_tree";
		}
		map.put("jsonListData", jsonStr);
		return viewPath+"/main_list";
	}

	@RequestMapping(value = "/getData.do")
	public String getData(String grpId, String grpCode, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		SysResGroup group = null;
		if( !StringUtils.isBlank(grpId) ){
			group = resGroupService.getResGroup(grpId);
		}
		else if( !StringUtils.isBlank(grpCode) ){
			group = resGroupService.getResGroupByCode(grpCode);
		}
		String jsonStr = getJsonGroupItems(group, 0);
	    response.getWriter().print(jsonStr);
	    return null;         
	}
	
	@RequestMapping(value = "/getMinData.do")
	public String getMinData(String grpId, String grpCode, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getMinData.do ..." );
		SysResGroup group = null;
		if( !StringUtils.isBlank(grpId) ){
			group = resGroupService.getResGroup(grpId);
		}
		else if( !StringUtils.isBlank(grpCode) ){
			group = resGroupService.getResGroupByCode(grpCode);
		}
		String jsonStr = getJsonGroupItems(group, 1);
	    response.getWriter().print(jsonStr);
	    return null;         
	}                                                     

	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<SysResItem> listItem = resItemService.searchResItem(filters);
		String jsonStr = resItemService.getJsonListStr(listItem);
		response.getWriter().print(jsonStr);
	    return null;         
	}
	
	@RequestMapping(value = "/add.do")
	public String add(String grpId, String parentId, ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		SysResGroup group = resGroupService.getResGroup(grpId);
		SysResItem item = new SysResItem();
		item.setSysResGroup(group);
		item.setParentId(parentId);
		map.put("item", item);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		SysResItem item = resItemService.getResItem(id);
		map.put("item", item);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/save.do")
	public String save(String action, SysResItem item, ModelMap map) {
		logger.info( "Enter save.do ..." );
		
		boolean validate = true;
		if( StringUtils.isBlank(item.getResName()) ){
			validate = false;
			map.put("message", "资源项名称不能为空！");
		}
		else if( StringUtils.isBlank(item.getResCode()) ){
			validate = false;
			map.put("message", "资源项编码不能为空！");
		}
		else if( !resItemService.isItemCodeUnique(item.getSysResGroup().getResGrpId(),item.getResId(), item.getResCode()) ){
			validate = false;
			map.put("message", "资源项编码已经存在！");
		}	
		
		if (validate) {
			if( "add".equals(action)){
				resItemService.addResItem(item);
			}		
			else{
				resItemService.saveResItem(item);				
			}
			String jsonStr = resItemService.getJsonObjStr(item);
			map.put("saveData", jsonStr);
			map.put("result", "success");
		} else {
			map.put("result", "error");
		}
			
		map.put("item", item);
		map.put("action", action);
		return viewPath+"/edit";
	}                                                
	
	@RequestMapping(value = "/moveUp.do")
	public String moveUp(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter moveUp.do ..." );
		SysResItem item = resItemService.getResItem(id);
		resItemService.moveUpItem(item);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	@RequestMapping(value = "/moveDown.do")
	public String moveDown(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter moveDown.do ..." );
		SysResItem item = resItemService.getResItem(id);
		resItemService.moveDownItem(item);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	@RequestMapping(value = "/moveGo.do")
	public String moveGo(String id, ModelMap map) throws IOException{     
		logger.info( "Enter moveGo.do ..." );
		SysResItem item = resItemService.getResItem(id);
		map.put("item", item);
		List<SysResItem> list = resItemService.findMoveTrees(item);
		String jsonStr = resItemService.getJsonTreeStr(list);
		map.put("jsonTreeData", jsonStr);
		return viewPath+"/move";
	}                                                   

	@RequestMapping(value = "/move.do")
	public String move(String moveId, String targetId, int moveType, HttpServletResponse response) throws IOException{     
		logger.info( "Enter move.do ..." );
		if( resItemService.moveResItem(moveId, targetId, moveType) ){
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
		SysResItem item = resItemService.getResItem(id);
		resItemService.deleteResItem(item);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}


}
