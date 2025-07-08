package com.sino.base.system.web;

import com.sino.base.common.Global;
import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.*;
import com.sino.base.system.service.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.security.Cryptos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 用户层
 *
 * @author .
 * @date 2017年5月3日 上午10:17:58
 */
@Controller
@RequestMapping(value = "/system/user")
public class UserAction {
	private static String viewPath = "/system/user";
	
	private static Logger logger = LoggerFactory.getLogger(UserAction.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private PowerService powerService;
	
	@Autowired
	private PowerGroupService powGrpService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuService menuService;
	
	
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<SysUser> list = null;
		SysUser curUser = SystemUtils.getLoginUser();
//		if( curUser.getUserType() == Global.USER_TYPE_DEV ){
//			list = userService.getAllUser();			
//		}
		if( curUser.getMainOrgId().equals("1")&&curUser.getUserType()>7){
			list = userService.getAllUser();			
		}
		else{
			OrgOrganization mainOrg = SystemUtils.getLoginUserMainOrg();
			list = userService.findAllManageUser(curUser, mainOrg);		
		}
		String jsonStr = userService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	}                                              

	@RequestMapping(value = "/getData.do")
	@ResponseBody
	public String getData(ModelMap map) {     
		logger.info( "Enter getData.do ..." );
		List<SysUser> list = userService.getAllUser();
		String jsonStr = userService.getJsonListStr(list);
	    return jsonStr;         
	}  
	
	@RequestMapping(value = "/getAll.do")
	@ResponseBody
	public String getAll(ModelMap map) {     
		logger.info( "Enter getData.do ..." );
		List<SysUser> list = userService.findByType();
		String jsonStr = userService.getJsonListStr(list);
		return jsonStr;         
	}  
	
	@RequestMapping(value = "/getDataByOrgId.do")
	public String getDataByOrgId(String orgId,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getDataByOrgId.do ..." );
		List<SysUser> list = userService.getByOrgId(orgId);
		String jsonStr = userService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}  

	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<SysUser> list = userService.searchUser(filters);
		String jsonStr = userService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}

	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		SysUser user = new SysUser();
		map.put("user", user);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		SysUser user = userService.getUser(id);
		map.put("user", user);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/save.do")
	public String save(String action, String id, boolean isEditPasswd, SysUser user, ModelMap map) {
		logger.info( "Enter save.do ..." );
		
		boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		else if( StringUtils.isBlank(user.getLoginName()) ){
			validate = false;
			map.put("message", "用户账号不能为空！");
		}
		else if( !userService.isLoginNameUnique(id, user.getLoginName()) ){
			validate = false;
			map.put("message", "用户账号已经存在！");
		}
		else if( ("add".equals(action) && StringUtils.isBlank(user.getLoginPasswd())) 
			|| ("edit".equals(action) && isEditPasswd &&  StringUtils.isBlank(user.getLoginPasswd())) ){
			validate = false;
			map.put("message", "用户密码不能为空！");
		}
		
		if( validate ){
			String operator = curUser.getLoginName();
			Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
			user.setModifier(operator);
			user.setModifyTime(now);
			String jsonStr = "";
			if( "add".equals(action)){
				user.setLoginPasswd(Cryptos.desEncryptToHex(user.getLoginPasswd(),Global.AES_KEY));
				user.setCreator(operator);
				user.setCreatorType(curUser.getUserType());
				user.setCreateTime(now);
				user.setLastLoginTime(now);
				userService.addUser(user);		
				map.put("user", user);
				jsonStr = userService.getJsonObjStr(user);
			}
			else{
				SysUser editUser = userService.getUser(id);
				if( editUser == null ){
					map.put("user", user);
					map.put("result", "error");								
					map.put("message", "该用户已经被删除！");
					return viewPath+"/edit";
				}
				if( isEditPasswd ){
					user.setLoginPasswd(Cryptos.desEncryptToHex(user.getLoginPasswd(),Global.AES_KEY));
					BeanUtils.copyProperties(user, editUser, new String[]{"sysRoles","orgOrganizations","sysPowers"});
				}else{
					user.setLoginPasswd(editUser.getLoginPasswd());
					BeanUtils.copyProperties(user, editUser, new String[]{"sysRoles","orgOrganizations","sysPowers"});
					
					
				}
				
				userService.saveUser(editUser);			
				map.put("user", editUser);
				jsonStr = userService.getJsonObjStr(editUser);
			}
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("user", user);
			map.put("result", "error");			
		}

		map.put("action", action);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		SysUser curUser = SystemUtils.getLoginUser();
		if( id.equals(curUser.getUserId()) ){
			response.getWriter().printf(JsonUtils.JSON_RESULT_FALSE_MSGMDL, "不能删除当前登录用户！");
			return null;
		}
		userService.deleteUser(id);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	@RequestMapping(value = "/mapPowerGo.do")
	public String mapPowerGo(String id, ModelMap map) throws IOException{     
		logger.info( "Enter mapPowerGo.do ..." );
		SysUser user = userService.getUser(id);
		map.put("user", user);
		List<SysPowerGroup> list = powGrpService.getAllPowerGroup();
		String jsonStr = powGrpService.getJsonUserPowChk(list, user.getSysPowers(), user.getUserType());
		map.put("jsonTreeData", jsonStr);
		return viewPath+"/mapPower";
	}                                                   

	@RequestMapping(value = "/mapPower.do")
	public String mapPower(String userId, String[] chkPower, HttpServletResponse response) throws IOException{     
		logger.info( "Enter mapPower.do ..." );
		SysUser user = userService.getUser(userId);
		user.getSysPowers().clear();
		if( chkPower != null ){
			for( int i=0; i<chkPower.length; i++ ){
				SysPower power = powerService.getPower(chkPower[i]);
				if( power!=null){
					user.getSysPowers().add(power);
				}
			}
		}
		userService.saveUser(user);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}   

	@RequestMapping(value = "/mapRoleGo.do")
	public String mapRoleGo(String id, ModelMap map) throws IOException{     
		logger.info( "Enter mapRoleGo.do ..." );
		SysUser user = userService.getUser(id);
		map.put("user", user);
		List<SysRole> list = roleService.getAllUserRole(user.getUserType());
		String jsonStr = roleService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		ArrayList<String> mapId = new ArrayList<String>();
		Iterator<SysRole> it = user.getSysRoles().iterator();
		while (it.hasNext()){ 
			SysRole role = it.next();
			mapId.add(role.getRoleId());
		}
		String jsonMap = JsonUtils.getJsonObjValue(mapId);
		map.put("jsonMapData", jsonMap);

		return viewPath+"/mapRole";
	}                                                   

	@RequestMapping(value = "/mapRole.do")
	public String mapRole(String userId, String[] mapId, HttpServletResponse response) throws IOException{     
		logger.info( "Enter mapRole.do ..." );
		SysUser user = userService.getUser(userId);
		user.getSysRoles().clear();
		if( mapId != null ){
			for( int i=0; i<mapId.length; i++ ){
				SysRole role = roleService.getRole(mapId[i]);
				if( role!=null){
					user.getSysRoles().add(role);
				}
			}
		}
		userService.saveUser(user);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}   
	
	@RequestMapping(value = "/viewMenu.do")
	public String viewMenu(String id, ModelMap map) throws IOException{     
		logger.info( "Enter viewMenu.do ..." );
		SysUser user = userService.getUser(id);
		map.put("user", user);

		List<SysMenu> list = menuService.findUserAllMenu(user);
		String jsonStr = menuService.getJsonTreeStr(list);
		map.put("jsonTreeData", jsonStr);
		return viewPath+"/viewMenu";
	}   
	
	/**
	 * setupGo
	 * @Description: 个人设置页面跳转
	 * @param map
	 * @return: String
	 */
	@RequestMapping(value = "/setupGo.do")
	public String setupGo(ModelMap map) {
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			return "error/outtime";
		}
		
		map.put("curUser", curUser);
		return viewPath+"/setup";
	}
	
	/**
	 * setup
	 * @Description: 个人设置修改功能
	 * @param userId 用户ID
	 * @param isEditPasswd 密码是否修改
	 * @param loginPasswd 用户登录密码
	 * @param loginMenuStyle 用户登录菜单风格
	 * @param loginSkinName 用户登录皮肤名称
	 * @param mobile 用户手机
	 * @param email 用户邮箱
	 * @param map 封装返回集合
	 * @return: String
	 */
	@RequestMapping(value = "/setup.do")
	public String setup(String userId, boolean isEditPasswd, String loginPasswd, String loginMenuStyle, 
			String loginSkinName, String mobile, String email,  ModelMap map) {
		logger.info( "Enter UserAction setup.do ..." );
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null || !userId.equals(curUser.getUserId()) ){
			return "error/outtime";
		}

		boolean validate = true;
		if( isEditPasswd &&  StringUtils.isBlank(loginPasswd) ){
				validate = false;
				map.put("message", "用户密码不能为空！");
		}
		
		if( validate ){
			if( isEditPasswd ){
				curUser.setLoginPasswd(Cryptos.desEncryptToHex(loginPasswd, Global.AES_KEY));
			}
			String oldMenuType = curUser.getLoginMenuStyle();
			if( loginMenuStyle != null ){
				curUser.setLoginMenuStyle(loginMenuStyle);
			}
			if( loginSkinName != null ){
				curUser.setLoginSkinName(loginSkinName);
			}
			if( mobile != null ){
				curUser.setMobile(mobile);
			}
			if( email != null ){
				curUser.setEmail(email);
			}
			userService.saveUser(curUser);
			map.put("result", "success");
			
			if( loginMenuStyle != null && !oldMenuType.equals(loginMenuStyle) ){
				map.put("MenuStyleChange", "true");				
			}
		}
		else{
			map.put("result", "error");			
		}
		
		map.put("curUser", curUser);
		return viewPath+"/setup";
	}

	@RequestMapping("/getAllPoUser.do")
	public void getAllPoUser(HttpServletResponse response){
		logger.info("Enter UserAction getAllPoUser.do...");
		//TODO 后期会变，这个方法是在采购单时获取采购员所用
		try {
			List<SysUser> list = userService.getAllPoUser();
			String jsonListStr = userService.getJsonListStr(list);
			JSONObject jo = new JSONObject();
			jo.put("users",jsonListStr);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/checkUserName.do")
	public String checkUserName(String loginName,HttpServletResponse response) throws IOException{
		boolean flag=false;
		if(!StringUtil.isNullString(loginName)){
			SysUser curUser =userService.findUserByLoginName(loginName);
			if(curUser!=null){
				flag=true;
			}
		}
		
		response.getWriter().print(flag);
		return null;
	}
}
