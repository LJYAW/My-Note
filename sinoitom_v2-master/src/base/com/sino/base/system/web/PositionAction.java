package com.sino.base.system.web;

import java.io.IOException;
import java.sql.Timestamp;
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


import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.entity.OrgPosition;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.OrganService;
import com.sino.base.system.service.PositionService;

@Controller
@RequestMapping(value = "/system/organ/position")
public class PositionAction {
	private static String viewPath = "/system/organ/position";
	
	private static Logger logger = LoggerFactory.getLogger(PositionAction.class);

	@Autowired
	private PositionService positionService;
	
	@Autowired
	private OrganService organService;
	
	
	@RequestMapping(value = "/main.do")
	public String main(String orgId, ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<OrgPosition> list = positionService.getOrganPosition(orgId);
		String jsonStr = positionService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		OrgOrganization organ = organService.getOrgan(orgId);
		map.put("organ", organ);
		return viewPath+"/main";
	}                                              

	@RequestMapping(value = "/getData.do")
	public String getData(String orgId, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		List<OrgPosition> list = positionService.getOrganPosition(orgId);
		String jsonStr = positionService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}                                                   

	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<OrgPosition> list = positionService.searchPosition(filters);
		String jsonStr = positionService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}

	@RequestMapping(value = "/add.do")
	public String add(String orgId, ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		OrgPosition position = new OrgPosition();
		OrgOrganization organ = organService.getOrgan(orgId);
		position.setOrgOrganization(organ);
		map.put("position", position);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		OrgPosition position = positionService.getPosition(id);
		map.put("position", position);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/save.do")
	public String save(String action, String id, boolean isEditPasswd, OrgPosition position, ModelMap map) {
		logger.info( "Enter save.do ..." );
		map.put("action", action);
		map.put("position", position);
		
		boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		else if( StringUtils.isBlank(position.getPositionName()) ){
			validate = false;
			map.put("message", "职位名称不能为空！");
		}
		
		if( validate ){
			String operator = curUser.getLoginName();
			Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
			position.setModifier(operator);
			position.setModifyTime(now);
			String jsonStr = "";
			if( "add".equals(action)){
				position.setCreator(operator);
				position.setCreateTime(now);
				positionService.addPosition(position);		
				jsonStr = positionService.getJsonObjStr(position);
			}
			else{
				OrgPosition editPosition = positionService.getPosition(id);
				if( editPosition == null ){
					map.put("result", "error");								
					map.put("message", "该职位已经被删除！");
					return viewPath+"/edit";
				}
				BeanUtils.copyProperties(position, editPosition, new String[]{"OrgOrganization","orgEmployees"});
				positionService.savePosition(editPosition);			
				jsonStr = positionService.getJsonObjStr(editPosition);
			}
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("result", "error");			
		}

		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		positionService.deletePosition(id);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	  
}
