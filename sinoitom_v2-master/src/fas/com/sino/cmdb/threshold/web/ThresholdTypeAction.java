/*
 * 文件名： ThresHoldTypeAction.java
 * 
 * 创建日期： 2014-9-10
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.threshold.web;

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

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.threshold.dao.ThresholdTypeDao;
import com.sino.cmdb.threshold.entity.ThresholdType;
import com.sino.cmdb.threshold.service.ThresholdTypeService;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-10
 */
@RequestMapping(value="/cmdb/thresholdType")
@Controller
public class ThresholdTypeAction {

	private String viewPath = "/cmdb/threshold/type";
	private static Logger logger= LoggerFactory.getLogger(ThresholdTypeAction.class);
	
	@Autowired
	private ThresholdTypeService thresholdTypeService;
	
	@Autowired
	private ThresholdTypeDao thresholdTypeDao;
	
	@Autowired
	private ResItemService resItemService;
	
	@RequestMapping(value = "/mainTab.do")
	public String mainTab(String menuId,ModelMap map) {
		logger.info( "Enter mainTab.do ..." );
		WebFuncUtils.titleContent(menuId,map);
		return "/cmdb/threshold/mainTab";
	}
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<ThresholdType> threshold=thresholdTypeService.getAll();
		String jsonStr = thresholdTypeService.getJsonListStr(threshold);
		map.put("jsonListData", jsonStr);
		return viewPath+"/main";
	}
	
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		map.put("action", "add");
		ThresholdType thresholdType = new ThresholdType();
		map.put("thresholdType", thresholdType);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/save.do")
	public String save(String action, String id, ThresholdType thresholdType, ModelMap map) {
		logger.info( "Enter save.do ..." );
		map.put("action", action);
		boolean validate = true;
		if( validate ){
			ThresholdType saveThresholdType= null;
			String saveData ="";
			if( "add".equals(action)){
				saveThresholdType = thresholdType;
				String indobjname=resItemService.findResContent("Cmdb_IndicatorClass", String.valueOf(thresholdType.getIndObjCode()));
				saveThresholdType.setIndObjName(indobjname);
				String inddimname=resItemService.findResContent("Cmdb_IndicatorDim", String.valueOf(thresholdType.getIndDimCode()));
				saveThresholdType.setIndDimName(inddimname);
				saveThresholdType.setStatus(0);
				thresholdTypeService.add(saveThresholdType);
				saveData = thresholdTypeService.getJsonObjStr(saveThresholdType);
			}else{
				saveThresholdType = thresholdType;
				String indobjname=resItemService.findResContent("Cmdb_IndicatorClass", String.valueOf(thresholdType.getIndObjCode()));
				saveThresholdType.setIndObjName(indobjname);
				String inddimname=resItemService.findResContent("Cmdb_IndicatorDim", String.valueOf(thresholdType.getIndDimCode()));
				saveThresholdType.setIndDimName(inddimname);
				saveThresholdType.setThreshTypeID(Integer.parseInt(id));
				saveThresholdType.setStatus(0);
				thresholdTypeService.add(saveThresholdType);
				saveData = thresholdTypeService.getJsonObjStr(saveThresholdType);
			}
			map.put("saveData", saveData);
			map.put("result", "success");
			
		}else{
			map.put("result", "error");			
		}
		return viewPath+"/edit";
	}
	
	@RequestMapping(value="/getAllType.do")
	public List<ThresholdType> getAllType(HttpServletResponse response) throws IOException{
		List<ThresholdType> type=thresholdTypeService.getAll();
		String jsonStr = thresholdTypeService.getJsonListStr(type);
		JSONObject jo=new JSONObject();
		jo.put("type", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	
	@RequestMapping(value="/getByObjCodeAndGroupEnName.do")
	public List<ThresholdType> getByObjCodeAndGroupEnName(String objCode,String indGroupEnName, HttpServletResponse response) throws IOException{
		List<ThresholdType> type=thresholdTypeService.getByObjCodeAndGroupEnName(objCode,indGroupEnName);
		
		String jsonStr = thresholdTypeService.getJsonListStr(type);
		JSONObject jo=new JSONObject();
		jo.put("type", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	
	@RequestMapping(value="/getByObjCode.do")
	public List<ThresholdType> getByObjCode(HttpServletResponse response,String objCode) throws IOException{
		List<ThresholdType> type=thresholdTypeService.getByObjCode(objCode);
		String jsonStr = thresholdTypeService.getJsonListStr(type);
		JSONObject jo=new JSONObject();
		jo.put("code", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	
	@RequestMapping(value="/getByOID.do")
	public List<ThresholdType> getByOID(HttpServletResponse response,String id) throws IOException{
		ThresholdType type=thresholdTypeService.getByID(id);
		String jsonStr = thresholdTypeService.getJsonObjStr(type);
		JSONObject jo=new JSONObject();
		jo.put("code", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	
	@RequestMapping(value="/delete.do")
	public String delete(String id,ModelMap map, HttpServletResponse response) throws IOException{
		logger.info("Enter delete.do..");
		String [] ids=id.split(",");
		thresholdTypeService.deletes(ids);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null; 
	}
	
	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		ThresholdType thresholdType = thresholdTypeService.getByID(id);
		if(null == thresholdType){
			thresholdType = new ThresholdType();
		}
		map.put("thresholdType", thresholdType);
		map.put("id", id);
		return viewPath+"/edit";
	}
	
	
	 @RequestMapping(value="/record.do")
	 public void record(String id,HttpServletResponse response) throws IOException{
		 logger.info("Enter delete.do...");
		 String [] ids=id.split(",");
			for(int i=0;i<ids.length;i++){
				ThresholdType thresholdType = thresholdTypeService.getByID(ids[i]);
				thresholdType.setStatus(1);
				thresholdTypeService.add(thresholdType);
			}
			response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	 }
	
}
