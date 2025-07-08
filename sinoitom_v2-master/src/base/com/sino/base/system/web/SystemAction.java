package com.sino.base.system.web;

import com.sino.base.common.Global;
import com.sino.base.common.tree.TreeUtils;
import com.sino.base.common.util.*;
import com.sino.base.system.entity.About;
import com.sino.base.system.entity.SysMenu;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.LoginService;
import com.sino.base.system.service.MenuService;
import com.sino.base.system.service.ParamItemService;
import com.sino.base.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.utils.security.Cryptos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 系统操作层，包括登录，跳转首页，下载文件
 *
 * @author .
 * @date 2017年5月3日 上午10:02:29
 */
@Controller
public class SystemAction {
	
	private static Logger logger = LoggerFactory.getLogger(SystemAction.class);
	private static String menuAttNames = "menuId,menuName,menuUrl,treeCode,icon";
	private static String menuJsonNames = "id,name,url,code,icon";
	private static String childAttName = "children";

	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private ParamItemService paramItemService;
	
	/**
	 * 登录后跳转到的处理端，判断用户名是否存在，密码是否正确
	 *
	 * @param loginName 登录名
	 * @param returnUrl 返回的URL路径地址
	 * @param loginPasswd 登录密码
	 * @param request http请求
	 * @param map 返回封装集合
	 * @throws UnsupportedEncodingException 不支持编码异常
	 * @return String
	 */
	@RequestMapping(value = "/login.do")
	public String login(String loginName,String returnUrl, String loginPasswd, HttpServletRequest request, ModelMap map) throws UnsupportedEncodingException {
		if(!StringUtil.isNullString(returnUrl)){
			returnUrl=URLDecoder.decode(returnUrl,"utf-8");
		}
		map.put("returnUrl", returnUrl);
		if( StringUtils.isBlank(loginName) || StringUtils.isBlank(loginPasswd)  ){
			return "forward:/login.jsp";			
		}
		SysUser user = userService.findUserByLoginName(loginName);
		if( user == null ){
			map.put("message", "请输入正确的用户名和密码！");
			return "forward:/login.jsp";
		}
		String aesPasswd = Cryptos.desEncryptToHex(loginPasswd,Global.AES_KEY);
		if( !aesPasswd.equals(user.getLoginPasswd())){
			map.put("message", "请输入正确的用户名和密码！");
			return "forward:/login.jsp";			
		}
		UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), user.getLoginPasswd());
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		logger.info( "User [{}] logged in successfully.", subject.getPrincipal() );
		String clientIp = request.getRemoteAddr();
		SystemUtils.setLoginUser(user, clientIp);
		LoginUtils.ActiveLoginUser(user.getLoginName());
		loginService.AddInfo(loginName, user.getUserName(), clientIp);
		SystemUtils.addLog("系统登陆", "登陆成功！",0);
		if(!StringUtil.isNullString(returnUrl))
			return "redirect:"+returnUrl;
		else
			return "redirect:/index.do";
	}

	/**
	 * index
	 * @Description: 登录成功后跳转到首页处理端，在这里根据用户的角色，获取其权限下的所有菜单
	 * @param map	封装返回值，以供页面获取
	 * @param request
	 * @return: String
	 */
	@RequestMapping(value = "/index.do")
	public String index(ModelMap map, HttpServletRequest request) {
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			return "error/outtime";
		}
		
//		List<SysMenu> listAll = menuService.getAllMenu();	
		List<SysMenu> listAll = menuService.findUserAllMenu(curUser);	

//		String menuStyle =  curUser.getLoginMenuStyle();
		String menuStyle =  "index2";
		if( Global.MENU_STYLE_2.equals(menuStyle) ){
			String treeHtml = TreeUtils.getHtmlTreeStr(listAll, "menuName", "menuUrl", "icon","menuId");
			map.put("menuHtmlData", treeHtml);	
			
			
			
			
			
		}
		else if( Global.MENU_STYLE_3.equals(menuStyle) ){
			List<SysMenu> listTop = menuService.getTopMenu(listAll);
			map.put("topMenuList", listTop);		
			String treeData = "[]";
			if( listTop!=null && listTop.size()>0 ){
				String treeCode = listTop.get(0).getTreeCode();
				List<SysMenu> leftMenu = menuService.findUserSubMenu(curUser, treeCode);
				treeData = JsonUtils.getJsonTreeInfo(leftMenu, menuAttNames, menuJsonNames, childAttName);
				request.getSession().setAttribute(Global.CURRENT_LEFT_MENU_ROOT_CODE, treeCode);
			}
			map.put("treeMenuData", treeData);					
		}
		else if( Global.MENU_STYLE_5.equals(menuStyle) ){
			List<SysMenu> listTop = menuService.getTopMenu(listAll);
			map.put("topMenuList", listTop);		
			String treeData = "[]";
			if( listTop!=null && listTop.size()>0 ){
				String treeCode = listTop.get(0).getTreeCode();
				List<SysMenu> leftMenu = menuService.findUserSubMenu(curUser, treeCode);
				treeData = JsonUtils.getJsonTreeInfo(leftMenu, menuAttNames, menuJsonNames, childAttName);
				request.getSession().setAttribute(Global.CURRENT_LEFT_MENU_ROOT_CODE, treeCode);
				map.put("titleNum", leftMenu.size());
				map.put("onlyTopMenu", leftMenu.get(0));
			}
			map.put("treeMenuData", treeData);
		}
		else {
			menuStyle = Global.MENU_STYLE_DEFAULT; 
			String treeData = JsonUtils.getJsonTreeInfo(listAll, menuAttNames, menuJsonNames, childAttName);
			map.put("treeMenuData", treeData);					
		}
		

		return "/"+menuStyle;
	}
	
	/**
	 * subMenu
	 * @Description: 菜单节点跳转
	 * @param treeCode	菜单树编码
	 * @param response	响应
	 * @throws IOException	抛出IO异常
	 * @return: String
	 */
	@RequestMapping(value = "/subMenu.do")
	public String subMenu(ModelMap map,String treeCode,HttpServletRequest request, HttpServletResponse response) throws IOException{     
		logger.info( "Enter subMenu.do ..." );
		SysUser curUser = SystemUtils.getLoginUser();
		try {
			if( curUser == null ){
			    response.getWriter().print(JsonUtils.JSON_RESULT_OUTTIME);
			    return null;         
			}
			List<SysMenu> list = menuService.findUserSubMenu(curUser, treeCode);
			request.getSession().setAttribute("onlyTopMenu", list.get(0));
			String treeData = JsonUtils.getJsonTreeInfo(list, menuAttNames, menuJsonNames, childAttName);				
			response.getWriter().print(treeData);
			map.put("titleNum", list.size());
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}         
		return null;
	}  
	
	/**
	 * layoutLeftMiddleOnly
	 * @Description: 新版页面跳转后，iframe加载页面
	 * @param map
	 * @param treeCode
	 * @param request
	 * @param response
	 * @throws IOException
	 * @return: String
	 */
	@RequestMapping(value = "/layoutLeftMiddleOnly.do")
	public String layoutLeftMiddleOnly(ModelMap map,HttpServletRequest request, HttpServletResponse response) throws IOException{     
		logger.info( "Enter layoutLeftMiddleOnly.do ..." );
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			response.getWriter().print(JsonUtils.JSON_RESULT_OUTTIME);
			return null;         
		}
		List<SysMenu> listAll = menuService.findUserAllMenu(curUser);
		List<SysMenu> listTop = menuService.getTopMenu(listAll);
		String treeData = "[]";
		if( listTop!=null && listTop.size()>0 ){
			String treeCode = listTop.get(0).getTreeCode();
			List<SysMenu> leftMenu = menuService.findUserSubMenu(curUser, treeCode);
			if(leftMenu.size() > 1){
				map.put("onlyTopMenu", leftMenu.get(0));
				leftMenu.remove(0);
				treeData = JsonUtils.getJsonTreeInfo(leftMenu, menuAttNames, menuJsonNames, childAttName);
				request.getSession().setAttribute(Global.CURRENT_LEFT_MENU_ROOT_CODE, treeCode);
				map.put("titleNum", leftMenu.size());
			}else{
				map.put("onlyTopMenu", leftMenu.get(0));
				map.put("titleNum", leftMenu.size());
				treeData = JsonUtils.getJsonTreeInfo(leftMenu, menuAttNames, menuJsonNames, childAttName);
				request.getSession().setAttribute(Global.CURRENT_LEFT_MENU_ROOT_CODE, treeCode);
			}
		}
		map.put("treeMenuData", treeData);
		return "/layoutLeftMiddleOnly";         
	}    
	
	
	/**
	 * subNewMenu
	 * @Description: 新跳转方式
	 * @param map
	 * @param treeCode
	 * @param response
	 * @throws IOException
	 * @return: String
	 */
	@RequestMapping(value = "/subNewMenu.do")
	public String subNewMenu(ModelMap map,String treeCode,HttpServletResponse response) throws IOException{     
		logger.info( "Enter subNewMenu.do ..." );
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			response.getWriter().print(JsonUtils.JSON_RESULT_OUTTIME);
			return null;         
		}
		List<SysMenu> list = menuService.findUserSubMenu(curUser, treeCode);
		if(list.size() > 1){
			map.put("onlyTopMenu", list.get(0));
			map.put("titleNum", list.size());
			list.remove(0);
			String treeData = JsonUtils.getJsonTreeInfo(list, menuAttNames, menuJsonNames, childAttName);	
			map.put("treeMenuData", treeData);
		}else{
			map.put("onlyTopMenu", list.get(0));
			String treeData = JsonUtils.getJsonTreeInfo(list, menuAttNames, menuJsonNames, childAttName);	
			map.put("treeMenuData", treeData);
			map.put("titleNum", list.size());
		}
		return "/layoutLeftMiddleOnly";        
	}                                                   
	

	/**
	 * main
	 * @Description: 跳转到系统首页
	 * @return: String
	 */
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map) {
		logger.info( "Enter subMenu.do ..." );
		return "/main";
	}
	
	/**
	 * translate
	 * @Description: 样式5中转跳转页面
	 * @param url 跳转到中转页面后需要加载的路径
	 * @param treeCode 拼接导航头的树编码
	 * @param map 返回参数
	 * @return: String
	 */
	@RequestMapping(value = "/translate.do")
	public String translate(@RequestParam(value="url") String url,String treeCode,ModelMap map) {
		logger.info( "Enter translate.do ..." );
		SysUser user = SystemUtils.getLoginUser();
		List<SysMenu> menu = menuService.findUserSubMenu(user, treeCode);
		StringBuilder builder = new StringBuilder();
		for (int i = 0 ; i< menu.size();i++) {
			if(i != menu.size()-1){
				builder.append(menu.get(i).getMenuName()+" >> ");
			}else{
				builder.append(menu.get(i).getMenuName());
			}
		}
		map.put("menuTitle", builder);
		map.put("url", url);
		return "/translate";
	}

	
	/**
	 * online
	 * @Description: 查询当前在线用户数
	 * @throws IOException IO异常
	 * @return: String
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/online.do")
	public String online(HttpServletResponse response)  throws IOException{
		logger.info( "Enter online.do ..." );
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
		    response.getWriter().print(JsonUtils.JSON_RESULT_OUTTIME);
		    return null;         
		}
		Map<String,Object> map = new TreeMap<String,Object>();
		LoginUtils.ActiveLoginUser(curUser.getLoginName());
		map.put("onlineNum", LoginUtils.GetLoginUserNum());
		//Hashtable ht = QueryDetailsCommon.getInstance().getCurrentFlowSpeed();
		//map.put("flowSpeed", ht.get("speed"));
		//map.put("flowColor", ht.get("color"));
		String data = JsonUtils.mapToJson(map);
	    response.getWriter().print(data);
	    return null;         
	}
	
//	@RequestMapping(value = "/getDeadline.do")
//	public String getDeadline(HttpServletResponse response)  throws IOException{
//		logger.info( "Enter getDeadline.do ..." );
//		Map<String,Object> map = new TreeMap<String,Object>();
//		License li=licenseService.getLicenseInfo();
//		String deadline="-1";
//		if(li!=null&&!StringUtil.isNullString(li.getDeadline())){
//			deadline=li.getDeadline();
//		}
//		System.out.println("获取到的参数是："+deadline +"  "+li.getDeadline());
//		map.put("deadline", deadline);
//		String data = JsonUtils.mapToJson(map);
//	    response.getWriter().print(data);
//	    return null;         
//	}
	
	/**
	 * checkLogin
	 * @Description: iframe动态修改url后，无法经过拦截器，也就无法判断是否登录成功；通过ajax请求，拦截后能判断
	 * @param response
	 * @param treeCode
	 * @throws IOException
	 * @return: String
	 */
	@RequestMapping(value = "/checkLogin.do")
	public String checkLogin(HttpServletResponse response,String treeCode)  throws IOException{
		logger.info( "Enter checkLogin.do ..." );
		String data = "{login:true}";
	    response.getWriter().print(data);
	    return null;         
	}

	/**
	 * logout
	 * @Description: 退出系统操作
	 * @return: String
	 */
	@RequestMapping(value = "/logout.do")
	public String logout( HttpServletRequest request) {
		SysUser curUser = SystemUtils.getLoginUser();
		loginService.AddInfo(curUser.getLoginName(), curUser.getUserName(), SystemUtils.getLoginUserIp());
		SystemUtils.addLog("退出系统", "成功退出系统！",0);
		LoginUtils.RemoveLoginUser(curUser.getLoginName());
		SecurityUtils.getSubject().logout();
		return "redirect:/login.jsp";
	}                                              
	
	/**
	 * download
	 * @Description: 下载文件操作
	 * @param name	下载的文件名
	 * @throws Exception 
	 * @return: void
	 */
	@RequestMapping(value = "/download.do")
	public void download(String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if( DownloadUtils.downloadName(name, request, response) ){
    		SystemUtils.addLog("下载文件", "下载"+name+"文件成功！");
        }
	}
	
	@RequestMapping(value = "/onAbout.do")
	public String onAbout(ModelMap map) {
		logger.info( "Enter onAbout.do ..." );
		About about=new About();
//		License li=licenseService.getLicenseInfo();
		about.setSysName(paramItemService.getParamValue("SystemName"));
		about.setVersion(paramItemService.getParamValue("SystemVersion"));
		about.setDeveloper(paramItemService.getParamValue("Developer"));
		about.setCopyright(paramItemService.getParamValue("SystemCopyRight"));
//		if(li!=null){
//			about.setLicenseUser(li.getLicenseUser());
//			about.setDeviceNum(li.getNetworkNum());
//			about.setDeadline(li.getDeadDate());
//		}
		String jsonStr=JsonUtils.bean2json(about);
		map.put("jsonStr", jsonStr);
		return "/about";
	}
}
