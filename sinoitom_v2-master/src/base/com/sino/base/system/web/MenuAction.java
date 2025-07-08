package com.sino.base.system.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.JqPageBean;
import com.sino.base.system.entity.SysMenu;
import com.sino.base.system.entity.SysPower;
import com.sino.base.system.entity.SysPowerGroup;
import com.sino.base.system.service.MenuService;
import com.sino.base.system.service.PowerGroupService;
import com.sino.base.system.service.PowerService;

@Controller
@RequestMapping(value = "/system/menu")
public class MenuAction {
	private String viewPath = "/system/menu";

	private static Logger logger = LoggerFactory.getLogger(MenuAction.class);

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private PowerService powerService;
	
	@Autowired
	private PowerGroupService powGrpService;
	
	
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		logger.info( "Enter main.do ..." );
//		List<SysMenu> list = menuService.getAllMenu();
//		
//		
//		String jsonStr = menuService.getJsonTreeStr(list);
//		
//		String jsonStr = menuService.getJsonListStr(list);
//		map.put("jsonTreeData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	}

	@RequestMapping(value = "/getData.do")
	public String getData(PageRequest pageRequest, JqPageBean page,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		
		
		List<SysMenu> list = menuService.getAllMenu();
		String jsonStr = menuService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    
//	    pageRequest.setPageNo(page.getPage());
//		pageRequest.setPageSize(page.getRows());
//		pageRequest.setOrderBy(page.getSidx());
//		pageRequest.setOrderDir(page.getSord());
//	    
//	    String jsonStr="{}";
//		Page<SysMenu> pages=menuService.findAjaxMenus(pageRequest);
//		jsonStr=menuService.getMenuPageList(pages);
//		response.setContentType("text/json");
//		response.setCharacterEncoding("UTF-8");
//		response.getWriter().print(jsonStr);
	    
	    return null;         
	}                                                   

	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<SysMenu> list = menuService.searchMenu(filters);
		String jsonStr = menuService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}

	@RequestMapping(value = "/add.do")
	public String add(String parentId, ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		SysMenu menu = new SysMenu();
		menu.setParentId(parentId);
		map.put("menu", menu);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		SysMenu menu = menuService.getMenu(id);
		map.put("menu", menu);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/save.do")
	public String save(String action, String id, SysMenu menu, ModelMap map) {
		logger.info( "Enter save.do ..." );
		boolean validate = true;
		if( StringUtils.isBlank(menu.getMenuName()) ){
			validate = false;
			map.put("message", "菜单名称不能为空！");
		}
		
		if( validate ){
			String jsonStr = "";
			if( "add".equals(action)){
				menuService.addMenu(menu);
				map.put("menu", menu);
				jsonStr = menuService.getJsonObjStr(menu);
			}		
			else{
				SysMenu editMenu = menuService.getMenu(id);
				if( editMenu == null ){
					map.put("result", "error");			
					map.put("message", "该菜单已经被删除！");
					return viewPath+"/edit";
				}
				editMenu.setMenuName(menu.getMenuName());
				editMenu.setMenuUrl(menu.getMenuUrl());
				editMenu.setParentId(menu.getParentId());
				editMenu.setMenuDesc(menu.getMenuDesc());
				editMenu.setIcon(menu.getIcon());
				editMenu.setState(menu.getState());
				menuService.saveMenu(editMenu);		
				map.put("menu", editMenu);
				jsonStr = menuService.getJsonObjStr(editMenu);
			}
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("menu", menu);
			map.put("result", "error");			
		}
		
		map.put("action", action);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/moveGo.do")
	public String moveGo(String id, ModelMap map) throws IOException{     
		logger.info( "Enter moveGo.do ..." );
//		SysMenu menu = menuService.getMenu(id);
//		map.put("menu", menu);
//		List<SysMenu> list = menuService.findMoveTrees(menu);
//		String jsonStr = menuService.getJsonTreeStr(list);
//		String jsonStr = menuService.getJsonListStr(list);
		map.put("id", id);
		return viewPath+"/move";
	}        
	
	@RequestMapping(value = "/moveGoDatas.do")
	public String moveGoDatas(String id,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		SysMenu menu = menuService.getMenu(id);
		List<SysMenu> list = menuService.findMoveTrees(menu);
		String jsonStr = menuService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}                                                   

	

	@RequestMapping(value = "/move.do")
	public String move(String moveId, String targetId, int moveType, HttpServletResponse response) throws IOException{     
		logger.info( "Enter move.do ..." );
		if( menuService.moveMenu(moveId, targetId, moveType) ){
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
		menuService.deleteMenu(id);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	@RequestMapping(value = "/mapPowerGo.do")
	public String mapPowerGo(String id, ModelMap map) throws IOException{     
		logger.info( "Enter moveGo.do ..." );
		SysMenu menu = menuService.getMenu(id);
		map.put("menu", menu);
		List<SysPowerGroup> list = powGrpService.getAllPowerGroup();
		String jsonStr = powGrpService.getJsonMenuPowChk(list,menu.getSysPowers());
		map.put("jsonTreeData", jsonStr);
		return viewPath+"/mapPower";
	}                                                   

	@RequestMapping(value = "/mapPower.do")
	public String mapPower(String menuId, String[] chkPower, HttpServletResponse response) throws IOException{     
		logger.info( "Enter mapPower.do ..." );
		SysMenu menu = menuService.getMenu(menuId);
		menu.getSysPowers().clear();
		
		if( chkPower != null ){
			for( int i=0; i<chkPower.length; i++ ){
				SysPower power = powerService.getPower(chkPower[i]);
				if( power!=null){
					menu.getSysPowers().add(power);
				}
			}
		}
		menuService.saveMenu(menu);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}                                                   

}
