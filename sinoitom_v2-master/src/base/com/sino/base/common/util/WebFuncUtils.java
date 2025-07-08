package com.sino.base.common.util;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.sino.base.system.entity.SysMenu;
import com.sino.base.system.service.MenuService;
import com.sino.base.system.service.ParamItemService;
import com.sino.base.system.service.ResItemService;
import com.sino.base.common.SampleResItem;

public class WebFuncUtils {

	private static ResItemService resService = ServiceUtils.getResService();
	private static ParamItemService paramService = ServiceUtils.getParamService();
	private static MenuService menuService = ServiceUtils.getMenuService();
	
    public static String getResValue(String grpCode, String resCode){
		return resService.findResContent(grpCode, resCode);
	}
    
    public static String getResCode(String grpCode, String resValue){
		return resService.findResCode(grpCode, resValue);
	}
    
    public static String getResValue(String grpCode, String resCode, String defaultValue){
		String value = resService.findResContent(grpCode, resCode);
		if( value==null || "".equals(value) ){
			return defaultValue;
		}
		return value;
	}
    
    public static String getParamValue(String paramCode){
		return paramService.getParamValue(paramCode);
	}
    
    public static String getParamValue(String paramCode, String defaultValue){
    	String value = paramService.getParamValue(paramCode);	
		if( value==null || "".equals(value) ){
			return defaultValue;
		}
		return value;
	}
    
    public static String getObjColValue(String objName, String col1Name, String col2Name, String col1Value){		
		return getObjColValue(objName, col1Name, col2Name, col1Value, "");	
	}
	
    public static String getObjColValue(String objName, String col1Name, String col2Name, String col1Value, String defaultValue){		
		String filter= col1Name+"='"+col1Value+"'";
		List<SampleResItem> items = null;
		if ( objName!= null && col1Name!= null && col2Name!= null ){
			items = resService.findSampleResItems(objName, col2Name, col1Name, filter, null);
		}

		if( items==null || items.size()==0 || items.get(0).getName()==null){
			return defaultValue;
		}
		
		return items.get(0).getName();	
	}
    
    public static String getResJsonList(String grpCode, String filter, String jsonCols){
    	return resService.getResJsonList(grpCode, filter, jsonCols);
	}
    
    public static String getResJsonTree(String grpCode, String filter, String jsonCols, String childName){
    	return resService.getResJsonTree(grpCode, filter, jsonCols, childName);
	}
    
    public static String getObjJsonList(String grpCode, String filter, String objCols, String jsonCols){
    	return resService.getObjJsonList(grpCode, filter, objCols, jsonCols);
	}
    
    public static String getObjJsonTree(String grpCode, String filter, String objCols, String jsonCols, String childName){
    	return resService.getObjJsonTree(grpCode, filter, objCols, jsonCols, childName);
	}
    
    public static String long2Ip(Long ip){
    	if( ip==null || ip==0 ){
    		return "";
    	}
    	return NetworkUtils.str_addr(ip);
	}
    
    
    public static void titleContent(String menuId,ModelMap map){
    	String content = "";
    	String webPath = "";
    	SysMenu  menu=null;
    	if(!StringUtil.isNullString(menuId))
    		menu= menuService.getMenu(menuId);
    	if( menu != null ){
    		if( !StringUtil.isBlank(content) ){
    			if( !StringUtil.isBlank(menu.getMenuUrl()) ){
    				content = "<a onclick=\"gotoURL('"+ webPath+"/"+menu.getMenuUrl()+"')\" href=\"#\">" + menu.getMenuName() + "</a> > " + content;
    			}
    			else{
        			content = menu.getMenuName() + " > " + content;
    			}
    		}
    		else{
    			content = menu.getMenuName();
    			if(!StringUtil.isNullString(menu.getMenuDesc()))
    				content=content+"  ("+menu.getMenuDesc()+")";
    		}
 	    	while( menu != null && !StringUtil.isBlank(menu.getParentId()) ){
	    		menu = menuService.getMenu(menu.getParentId());
	    		if( menu != null ){
	    			if( !StringUtil.isBlank(menu.getMenuUrl()) ){
	    				content = "<a onclick=\"gotoURL('"+ webPath+"/"+menu.getMenuUrl()+"')\" href=\"#\">" + menu.getMenuName() + "</a> > " + content;
	    			}
	    			else{
	    				content = menu.getMenuName() + " > " + content;
	    			}
	    		}
	    	}
    	}
    	if( content.length()>0 ){
    		content = "<div id=\"page-title\">"+"当前功能："+content+"</div>";
    	}
    	map.put("titleContent", content);
    	//return content;
	}
 
}
