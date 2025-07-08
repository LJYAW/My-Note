package com.sino.base.system.web;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.SysPower;
import com.sino.base.system.entity.SysPowerGroup;
import com.sino.base.system.entity.SysRole;
import com.sino.base.system.service.PowerGroupService;
import com.sino.base.system.service.PowerService;
import com.sino.base.system.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.PropertyFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 
 * 角色管理层，包含角色添加，角色修改，角色权限变更
 * 
 * @author .
 * @date 2017年5月3日 上午10:41:08
 */
@Controller
@RequestMapping(value = "/system/role")
public class RoleAction {
	private static String viewPath = "/system/role";
	
	private static Logger logger = LoggerFactory.getLogger(RoleAction.class);

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PowerService powerService;
	
	@Autowired
	private PowerGroupService powGrpService;
	
	
	/**
	 * main
	 * 角色页面跳转
	 * @param map
	 * @return: String
	 */
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<SysRole> list = roleService.getAllRole();
		String jsonStr = roleService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	}                                              

	/**
	 * getData
	 * 刷新数据
	 * @param response
	 * @throws IOException IO异常
	 * @return: String
	 */
	@RequestMapping(value = "/getData.do")
	public String getData(HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		List<SysRole> list = roleService.getAllRole();
		String jsonStr = roleService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}                                                   

	/**
	 * search
	 * 角色搜索
	 * @param resquest
	 * @param response
	 * @throws IOException IO异常
	 * @return: String
	 */
	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<SysRole> list = roleService.searchRole(filters);
		String jsonStr = roleService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}

	/**
	 * add
	 * 添加角色
	 * @param map
	 * @return: String
	 */
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		SysRole role = new SysRole();
		map.put("role", role);
		return viewPath+"/edit";
	}

	/**
	 * edit
	 * 编辑角色
	 * @param id 角色id
	 * @param map
	 * @return: String
	 */
	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		SysRole role = roleService.getRole(id);
		map.put("role", role);
		return viewPath+"/edit";
	}

	/**
	 * save
	 * 保存角色信息，成功或者失败
	 * @param action 判断是添加角色还是修改角色
	 * @param id 角色id
	 * @param role 角色封装信息
	 * @param map
	 * @return: String
	 */
	@RequestMapping(value = "/save.do")
	public String save(String action, String id, SysRole role, ModelMap map) {
		logger.info( "Enter save.do ..." );
		
		boolean validate = true;
		if( StringUtils.isBlank(role.getRoleName()) ){
			validate = false;
			map.put("message", "角色名称不能为空！");
		}
		if( !roleService.isRoleNameUnique(id, role.getRoleName()) ){
			validate = false;
			map.put("message", "角色名称已经存在！");
		}
		
		if( validate ){
			String jsonStr = "";
			if( "add".equals(action)){
				roleService.addRole(role);		
				map.put("role", role);
				jsonStr = roleService.getJsonObjStr(role);
			}
			else{
				SysRole editRole = roleService.getRole(id);
				editRole.setRoleName(role.getRoleName());
				editRole.setRoleCode(role.getRoleCode());
				editRole.setState(role.getState());
				editRole.setDescription(role.getDescription());
				roleService.saveRole(editRole);			
				map.put("role", editRole);
				jsonStr = roleService.getJsonObjStr(editRole);
			}
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("role", role);
			map.put("result", "error");			
		}

		map.put("action", action);
		return viewPath+"/edit";
	}
	
	/**
	 * delete
	 * 删除角色
	 * @param id
	 * @param response
	 * @throws IOException
	 * @return: String
	 */
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		roleService.deleteRole(id);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	/**
	 * mapPowerGo
	 * 获得所有角色权限，并跳转到角色关联权限页面
	 * @param id
	 * @param map
	 * @throws IOException
	 * @return: String
	 */
	@RequestMapping(value = "/mapPowerGo.do")
	public String mapPowerGo(String id, ModelMap map) throws IOException{     
		logger.info( "Enter moveGo.do ..." );
		SysRole role = roleService.getRole(id);
		map.put("role", role);
		List<SysPowerGroup> list = powGrpService.getAllPowerGroup();
		String jsonStr = powGrpService.getJsonRolePowChk(list,role.getSysPowers());
		map.put("jsonTreeData", jsonStr);
		return viewPath+"/mapPower";
	}                                                   

	/**
	 * mapPower
	 * 获得所有权限，并跳转
	 * @param roleId
	 * @param chkPower
	 * @param response
	 * @throws IOException
	 * @return: String
	 */
	@RequestMapping(value = "/mapPower.do")
	public String mapPower(String roleId, String[] chkPower, HttpServletResponse response) throws IOException{     
		logger.info( "Enter mapPower.do ..." );
		SysRole role = roleService.getRole(roleId);
		role.getSysPowers().clear();
		if( chkPower != null ){
			for( int i=0; i<chkPower.length; i++ ){
				SysPower power = powerService.getPower(chkPower[i]);
				if( power!=null){
					role.getSysPowers().add(power);
				}
			}
		}
		roleService.saveRole(role);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}   
}
