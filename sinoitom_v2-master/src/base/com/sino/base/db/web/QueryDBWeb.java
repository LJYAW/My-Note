package com.sino.base.db.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.PageRequest;

import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.db.entity.DBEntity;
import com.sino.base.db.entity.TableInfo;
import com.sino.base.db.service.DBService;
import com.sino.base.db.service.TableService;
import com.sino.base.system.entity.JqPageBean;


@Controller
@RequestMapping(value = "/system/queryDB")
public class QueryDBWeb {
    private static String viewPath = "/system/dbInfo";
	private static Logger logger = LoggerFactory.getLogger(QueryDBWeb.class);
	@Autowired
	private DBService dbService;
	@Autowired
	private TableService tableService;
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map,String menuId,HttpServletResponse response) throws IOException{
		logger.info( "Enter main.do ..." );
		
		List<DBEntity> dbList=dbService.queryDB();
		String jsonStr = dbService.getJsonListStr(dbList);
		map.put("jsonStr", jsonStr);
		WebFuncUtils.titleContent(menuId,map);//获取菜单名称,并放在map里
		return viewPath +"/dbList";
	}
	@RequestMapping(value = "/getData.do")
	public String getData(String orgId, HttpServletResponse response,PageRequest pageRequest, JqPageBean page) throws IOException{     
		logger.info( "Enter getData.do ..." );
		
//		pageRequest.setPageNo(page.getPage());
//		pageRequest.setPageSize(page.getRows());
//		pageRequest.setOrderBy(page.getSidx());
//		pageRequest.setOrderDir(page.getSord());
		
		
		String jsonStr="{}";
		
		List<DBEntity> dbList=dbService.queryDB();
		jsonStr = dbService.getJsonListStr(dbList);
//		JSONObject jo=new JSONObject();
//		jo.put("jsonlist", jsonStr);
//		response.getWriter().print(jo.toString());
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().print(jsonStr);
	    return null;         
	}
	@RequestMapping(value = "/getTableInfo.do")
	public String getTableInfo(String dbName,String tableName,ModelMap map){
		logger.info( "Enter getTableInfo.do ..." );
		map.put("tableName", tableName);
		map.put("dbName", dbName);
		return viewPath +"/tableInfo";
	}
	@RequestMapping(value = "/getTable.do")
	public String getTable(String dbName,String tableName, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getTable.do ..." );
		List<TableInfo> dbList=tableService.queryTable(dbName,tableName);
		String jsonStr = tableService.getJsonListStr(dbList);
		JSONObject jo=new JSONObject();
		jo.put("jsonlist", jsonStr);
		response.getWriter().print(jo.toString());
	    return null;         
	}
}
