package com.sino.fas.res.net.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.entity.SysUser;
import com.sino.fas.res.net.entity.NcmDevAccessUser;
import com.sino.fas.res.net.service.NcmDevAccessUserService;

@Controller
@RequestMapping(value="/devices/accessUser")
public class NcmDevAccessUserAction {

	private static Logger logger = LoggerFactory.getLogger(NcmDevAccessUserAction.class);
	
	private String viewPath = "/fas/res/net/devices/accessUser";
	
	
	@Autowired
	private NcmDevAccessUserService devAccessUserService;	
	
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<OrgOrganization> orglist=SystemUtils.getLoginUserSubOrg();
		List<String> orgids=new ArrayList<String>();
		for(OrgOrganization org:orglist){
			orgids.add(org.getOrgId());
		}
		List<NcmDevAccessUser> list = devAccessUserService.getAllByOrgIds(orgids);
		String jsonStr = devAccessUserService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	}     
	
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map,String oprType) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		NcmDevAccessUser devAccess = new NcmDevAccessUser();
		devAccess.setAccessTool("telnet");
		devAccess.setAccessPort(23);
		map.put("devAccess", devAccess);
		map.put("oprType", oprType);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		NcmDevAccessUser devAccess = devAccessUserService.getById(id);
		map.put("devAccess", devAccess);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<NcmDevAccessUser> list = devAccessUserService.searchDevAccess(filters);
		String jsonStr = devAccessUserService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}
	
	@RequestMapping(value = "/getData.do")
	public String getData(HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		List<OrgOrganization> orglist=SystemUtils.getLoginUserSubOrg();
		List<String> orgids=new ArrayList<String>();
		for(OrgOrganization org:orglist){
			orgids.add(org.getOrgId());
		}
		List<NcmDevAccessUser> list = devAccessUserService.getAllByOrgIds(orgids);
		String jsonStr = devAccessUserService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}  
	
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		devAccessUserService.deleteDevAccess(id);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	@RequestMapping(value = "/save.do")
	public String save(String action, String closeDlg, String id, NcmDevAccessUser devAccess,String oprType, ModelMap map) {
		logger.info( "Enter save.do ..." );
		
		boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		else if( !devAccessUserService.isDevAccessUnique(id, devAccess.getAcsUserName())&&"add".equals(action)){
			validate = false;
			map.put("message", "该配置已经存在！");
		}
		
		if( validate ){
			String jsonStr = "";
			String operator = curUser.getLoginName();
			devAccess.setModifier(operator);
			Date now = new Date();
			devAccess.setModifyTime(now);
			if( "add".equals(action)){
				devAccess.setCreator(operator);
				devAccess.setCreateTime(now);
				devAccessUserService.addDevAccess(devAccess);		
				map.put("devAccess", devAccess);
				jsonStr = devAccessUserService.getJsonObjStr(devAccess);
				if("device".equals(oprType)){
					SystemUtils.addLog("添加路由器设备访问信息","成功添加路由器设备访问信息！",1);
				}else if("switch".equals(oprType)){
					SystemUtils.addLog("添加交换机设备访问信息","成功添加交换机设备访问信息！",2);
				}
				
			}
			else{
				NcmDevAccessUser editDevAccess = devAccessUserService.getById(id);
				if( editDevAccess == null ){
					map.put("result", "error");								
					map.put("message", "该配置已经被删除！");
					return viewPath+"/edit";
				}
				BeanUtils.copyProperties(devAccess, editDevAccess);
				devAccessUserService.saveDevAccess(editDevAccess);			
				map.put("devAccess", editDevAccess);
				jsonStr = devAccessUserService.getJsonObjStr(editDevAccess);
				if("device".equals(oprType)){
					SystemUtils.addLog("修改路由器设备访问信息","成功修改路由器设备访问信息！",1);
				}else if("switch".equals(oprType)){
					SystemUtils.addLog("修改交换机设备访问信息","修改添加交换机设备访问信息！",2);
				}
				
			}
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("devAccess", devAccess);
			map.put("result", "error");			
		}

		map.put("action", action);
		map.put("closeDlg", closeDlg);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/getItem.do")
	public String getItem(String id, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getItem.do ..." );
		String jsonStr ="";
		NcmDevAccessUser obj = devAccessUserService.getById(id);
		if(obj!=null)
		 jsonStr = devAccessUserService.getJsonObjStr(obj);
	    response.getWriter().print(jsonStr);
	    return null;         
	}     
	
}
