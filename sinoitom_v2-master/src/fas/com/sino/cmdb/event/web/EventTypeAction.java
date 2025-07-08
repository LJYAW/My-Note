/*
 * 文件名： EventTypeAction.java
 * 
 * 创建日期： 2014-9-13
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.event.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.event.entity.EventType;
import com.sino.cmdb.event.service.EventTypeService;
import com.sino.cmdb.indicator.typeMap.entity.IndItemEventTypeMap;
import com.sino.cmdb.indicator.typeMap.service.IndItemEventTypeMapService;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-13
 */

@RequestMapping(value="/cmdb/eventType")
@Controller
public class EventTypeAction {

	private String viewPath = "/cmdb/event/eventType";
	private String view = "/cmdb/indicator/typeMap";
	private static Logger logger= LoggerFactory.getLogger(EventTypeAction.class);
	
	@Autowired
	private EventTypeService eventTypeService;
	
	@Autowired
	private ResItemService resItemService;
	
	@Autowired
	private IndItemEventTypeMapService indItemEventTypeMapService;
	
	@RequestMapping(value = "/mainTab.do")
	public String mainTab(ModelMap map) {
		logger.info( "Enter mainTab.do ..." );
		return "/cmdb/event/mainTab";
	}
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<EventType> event=eventTypeService.getAll();
		String jsonStr = eventTypeService.getJsonListStr(event);
		map.put("jsonListData", jsonStr);
		return viewPath+"/main";
	}
	
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		map.put("action", "add");
		EventType type = new EventType();
		map.put("type", type);
		return viewPath+"/edit";
	}
	@RequestMapping(value = "/getAdd.do")
	public String getAdd(ModelMap map) {
		map.put("action", "add");
		EventType type = new EventType();
		map.put("type", type);
		return view+"/edit";
	}
	@RequestMapping(value="getByClassCode.do")
	public String getByClassCode(String classCode,HttpServletResponse response) throws IOException{
		logger.info("Enter getByClassCode.do..");
		List<EventType> types=eventTypeService.getByClassCode(Integer.valueOf(classCode));
		String jsonStr=eventTypeService.getJsonListStr(types);
		JSONObject jo=new JSONObject();
		jo.put("types", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	
	@RequestMapping(value="/getEventClass.do")
	public String getEventClass(HttpServletResponse response) throws IOException{
		 List<ResItemParam> list=eventTypeService.getEventClass();
		 JSONArray jsArealist = JSONArray.fromObject(list);
		 JSONObject jo=new JSONObject();
		 jo.put("eventClass", jsArealist);
		 response.setContentType("text/json");
		 response.setCharacterEncoding("UTF-8");
		 response.getWriter().print(jo.toString());
		 return null; 
	}
	
	
	@RequestMapping(value = "/save.do")
	public String save(String action, String id, EventType type, ModelMap map) {
		logger.info( "Enter save.do ..." );
		map.put("action", action);
		boolean validate = true;
		if( validate ){
			EventType saveType= null;
			String saveData ="";
			if( "add".equals(action)){
				saveType = type;
				String  resName=resItemService.findResContent("Cmdb_EventClass", String.valueOf(type.getEventClassCode()));
				saveType.setEventClassName(resName);
				saveType.setStatus(0);
				eventTypeService.add(saveType);
				saveData = eventTypeService.getJsonObjStr(saveType);
			}else{
				saveType = type;
				String  resName=resItemService.findResContent("Cmdb_EventClass", String.valueOf(type.getEventClassCode()));
				saveType.setEventClassName(resName);
				saveType.setEventTypeID(Integer.parseInt(id));
				eventTypeService.add(saveType);
				saveData = eventTypeService.getJsonObjStr(saveType);
			}
			map.put("saveData", saveData);
			map.put("result", "success");
			
		}else{
			map.put("result", "error");			
		}
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		EventType type = eventTypeService.getByID(id);
		if(null == type){
			type = new EventType();
		}
		map.put("type", type);
		map.put("id", id);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value="/checkTypeCode.do")
	public String checkTypeCode(String eventTypeCode, HttpServletResponse response) throws IOException{
		logger.info("Enter checkTypeCode.do..");
		EventType type =eventTypeService.getByTypeCode(Integer.valueOf(eventTypeCode));
		JSONObject jo=new JSONObject();
		jo.put("type", type);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	
	@RequestMapping(value="/delete.do")
	public String delete(String id,ModelMap map, HttpServletResponse response) throws IOException{
		logger.info("Enter delete.do..");
		String [] ids=id.split(",");
		eventTypeService.deletes(ids);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null; 
	}	
	
	@RequestMapping(value="/record.do")
	 public void record(String id,HttpServletResponse response) throws IOException{
		 logger.info("Enter delete.do...");
		 String [] ids=id.split(",");
			for(int i=0;i<ids.length;i++){
				EventType type = eventTypeService.getByID(ids[i]);
				type.setStatus(1);
				eventTypeService.add(type);
			}
			response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	 }
	
	
}
