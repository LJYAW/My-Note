package com.sino.base.system.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.SysLog;
import com.sino.base.system.service.LogService;

@Controller
@RequestMapping(value = "/system/log")
public class LogAction {
	private String viewPath = "/system/log";

	private static Logger logger = LoggerFactory.getLogger(LogAction.class);

	@Autowired
	private LogService logService;
	
	
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map,String oprType) {
		logger.info( "Enter main.do ..." );
		List<SysLog> list = logService.getAllLog();
		String jsonStr = logService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		map.put("oprType", oprType);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	}
	@RequestMapping(value = "/mainTab.do")
	public String mainTab(ModelMap map) {
		logger.info( "Enter main.do ..." );
		
		return viewPath+"/mainTab";
	}
	
	@RequestMapping(value = "/view.do")
	public String view(String id, ModelMap map) throws IOException {
		logger.info( "Enter view.do ..." );
		SysLog log = logService.getLog(id);
		map.put("log", log);
		return viewPath+"/view";
	}
	
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		
		logService.deleteLog(id);
		JSONObject jo=new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	
	@RequestMapping(value = "/mulitDelete.do")
	public String mulitDelete(String logList, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		
		logService.deleteLogs(logList.split(";"));
		JSONObject jo=new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	@RequestMapping(value = "/search.do")
	public String search(PageRequest pageReq, HttpServletRequest request,
			HttpServletResponse response,String oprType) throws IOException {
		logger.info("Enter search.do ...");
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(
				request, "filter");
			//List<String> listlogids = logService.getAllLogStr();
//			if (listlogids != null && !listlogids.isEmpty()) {
//				String logid="";
//				for (String logids : listlogids) {
//					logid+=logids+";";
//				}
//				PropertyFilter filter = new PropertyFilter("INS_logId",logid);
//				filters.add(filter);
//			}
//			else {
//				filters = null;
//			}
		PropertyFilter filter = new PropertyFilter("EQI_operateType",oprType);
		filters.add(filter);
		Page<SysLog> page = logService.searchSysLog(pageReq,
				filters);
		//List<SysLog> sysLogs=logService.getByOprType(oprType);
		
		String jsonStr = logService.getJsonPageStr(page);
		response.getWriter().print(jsonStr);
		return null;
	}


}
