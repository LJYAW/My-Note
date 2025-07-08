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
import com.sino.base.system.entity.SysPower;
import com.sino.base.system.entity.SysPowerGroup;
import com.sino.base.system.service.PowerGroupService;
import com.sino.base.system.service.PowerService;

@Controller
@RequestMapping(value = "/system/power/item")
public class PowerAction {
	private static String viewPath = "/system/power/item";
	
	private static Logger logger = LoggerFactory.getLogger(PowerAction.class);

	@Autowired
	private PowerService powerService;

	@Autowired
	private PowerGroupService powGrpService;

	
	@RequestMapping(value = "/main.do")
	public String main(String grpId, ModelMap map) {
		logger.info( "Enter main.do ..." );
		SysPowerGroup group = powGrpService.getPowerGroup(grpId);
		map.put("group", group);
		List<SysPower> list = powerService.getGroupPower(grpId);
		String jsonStr = powerService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		return viewPath+"/main";
	}                                              

	@RequestMapping(value = "/getData.do")
	public String getData(String grpId, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		List<SysPower> list = powerService.getGroupPower(grpId);
		String jsonStr = powerService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}                                                   

	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<SysPower> list = powerService.searchPower(filters);
		String jsonStr = powerService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}

	@RequestMapping(value = "/add.do")
	public String add(String grpId, ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		SysPowerGroup group = powGrpService.getPowerGroup(grpId);
		SysPower power = new SysPower();
		power.setSysPowerGroup(group);
		power.setIsBsPow(true);
		power.setIsDvPow(false);
		power.setIsRoleSee(true);
		power.setState(1);
		map.put("item", power);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		SysPower power = powerService.getPower(id);
		map.put("item", power);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/save.do")
	public String save(String action, String id, SysPower power, ModelMap map) {
		logger.info( "Enter save.do ..." );

		boolean validate = true;
		if( StringUtils.isBlank(power.getPowName()) ){
			validate = false;
			map.put("message", "权限名称不能为空！");
		}
		else if( StringUtils.isBlank(power.getPowCode()) ){
			validate = false;
			map.put("message", "权限编码不能为空！");
		}
		else if( !powerService.isPowCodeUnique(id, power.getPowCode()) ){
			validate = false;
			map.put("message", "权限编码已经存在！");
		}
		
		if (validate) {
			String jsonStr = "";
			if( "add".equals(action)){
				powerService.addPower(power);
				map.put("item", power);
				jsonStr = powerService.getJsonObjStr(power);
			}		
			else{
				SysPower editPower = powerService.getPower(id);
				if( editPower == null ){
					map.put("result", "error");								
					map.put("message", "该权限已经被删除！");
					return viewPath+"/edit";
				}
				editPower.setPowName(power.getPowName());
				editPower.setPowCode(power.getPowCode());
				editPower.setIsBsPow(power.getIsBsPow());
				editPower.setIsDvPow(power.getIsDvPow());
				editPower.setIsRoleSee(power.getIsRoleSee());
				editPower.setDescription(power.getDescription());
				editPower.setState(power.getState());
				powerService.savePower(editPower);	
				map.put("item", editPower);
				jsonStr = powerService.getJsonObjStr(editPower);
			}
			map.put("saveData", jsonStr);
			map.put("result", "success");
		} else {
			map.put("item", power);
			map.put("result", "error");
		}

		map.put("action", action);		
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/moveUp.do")
	public String moveUp(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter moveUp.do ..." );
		SysPower item = powerService.getPower(id);
		powerService.moveUpItem(item);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	@RequestMapping(value = "/moveDown.do")
	public String moveDown(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter moveDown.do ..." );
		SysPower item = powerService.getPower(id);
		powerService.moveDownItem(item);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	} 
	
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		powerService.deletePower(id);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}

}
