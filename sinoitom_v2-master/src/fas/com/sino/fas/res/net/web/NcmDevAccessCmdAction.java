package com.sino.fas.res.net.web;

import java.io.IOException;
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
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.SysUser;
import com.sino.fas.res.net.entity.NcmDevAccessCmd;
import com.sino.fas.res.net.service.NcmDevAccessCmdService;

@Controller
@RequestMapping(value="/devices/accessCmd")
public class NcmDevAccessCmdAction {

private static Logger logger = LoggerFactory.getLogger(NcmDevAccessCmdAction.class);
	
	private String viewPath = "/fas/res/net/devices/accessCmd";

	@Autowired
	private NcmDevAccessCmdService devAccessCmdService;	
	
	
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<NcmDevAccessCmd> list = devAccessCmdService.getAll();
		String jsonStr = devAccessCmdService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	} 
	
	@RequestMapping(value = "/getData.do")
	public String getData(String vendorId, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		List<NcmDevAccessCmd> list = null;
		if( StringUtil.isNullString(vendorId) ){
			list = devAccessCmdService.getAll();
		}
		else{
			list = devAccessCmdService.getDevAcsCmd(vendorId);
		}
		String jsonStr = devAccessCmdService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}  
	
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		NcmDevAccessCmd accessCmd = new NcmDevAccessCmd();
		map.put("accessCmd", accessCmd);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		NcmDevAccessCmd accessCmd = devAccessCmdService.getById(id);
		map.put("accessCmd", accessCmd);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/save.do")
	public String save(String action, String closeDlg, String id, NcmDevAccessCmd accessCmd, ModelMap map) {
		logger.info( "Enter save.do ..." );
		
		
		boolean validate = true;
		
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		else if( !devAccessCmdService.isAccessCmdUnique( id,accessCmd.getVendorId(),accessCmd.getOsType(),accessCmd.getOsName(),accessCmd.getOsVersion())){  //id, devPrompt.getOsName()
			validate = false;
//			map.put("message", "该操作系统已经存在！");
			map.put("message", "相同版本的定义已经存在！");
		}

		
		if( validate ){
			String jsonStr = "";
			if( "add".equals(action)){
				accessCmd.setVendorOid(".1.3.6.1.4.1."+accessCmd.getVendorId().toString());
				devAccessCmdService.addDevPrompt(accessCmd);		
				map.put("accessCmd", accessCmd);
				jsonStr = devAccessCmdService.getJsonObjStr(accessCmd);
			}
			else{
				NcmDevAccessCmd editAccessCmd = devAccessCmdService.getById(id);
				if( editAccessCmd == null ){
					map.put("result", "error");								
					map.put("message", "该设备访问信息已经被删除！");
					return viewPath+"/edit";
				}
				BeanUtils.copyProperties(accessCmd, editAccessCmd);
				devAccessCmdService.saveDevPrompt(editAccessCmd);			
				map.put("accessCmd", editAccessCmd);
				jsonStr = devAccessCmdService.getJsonObjStr(editAccessCmd);
			}
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("accessCmd", accessCmd);
			map.put("result", "error");			
		}

		map.put("action", action);
		map.put("closeDlg", closeDlg);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<NcmDevAccessCmd> list = devAccessCmdService.searchAccessCmd(filters);
		String jsonStr = devAccessCmdService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}
	
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		devAccessCmdService.deleteAccessCmd(id);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	@RequestMapping(value = "/getPromptByVendorAndOsNameAndOsVersion.do")
	public String getPromptByVendorAndOsNameAndOsVersion(String vendorID,String osName,String osVersion, HttpServletResponse response) throws IOException {
		logger.info( "Enter getPromptByVendorAndOsNameAndOsVersion.do ..." );
		NcmDevAccessCmd prompt=devAccessCmdService.getPromptByVendorAndOsNameAndOsVersion(Integer.parseInt(vendorID), osName, osVersion);
		if(prompt!=null){
			String jsonStr = devAccessCmdService.getJsonObjStr(prompt);
			response.getWriter().print(jsonStr);
		}
		
	    return null;         
	}
}
