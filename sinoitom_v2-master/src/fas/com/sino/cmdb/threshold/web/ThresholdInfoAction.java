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
import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.utils.Identities;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.event.entity.EventType;
import com.sino.cmdb.severity.entity.Severity;
import com.sino.cmdb.severity.service.SeverityService;
import com.sino.cmdb.threshold.entity.ParamThresholdInfoAndType;
import com.sino.cmdb.threshold.entity.ParamThresholdLevel;
import com.sino.cmdb.threshold.entity.ThresholdInfo;
import com.sino.cmdb.threshold.entity.ThresholdLevel;
import com.sino.cmdb.threshold.service.ThresholdInfoService;
import com.sino.cmdb.threshold.service.ThresholdLevelService;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-10
 */
@RequestMapping(value="/cmdb/thresholdInfo")
@Controller
public class ThresholdInfoAction {

	private String viewPath = "/cmdb/threshold/info";
	private static Logger logger= LoggerFactory.getLogger(ThresholdInfoAction.class);
	
	
	@Autowired
	private ThresholdInfoService thresholdInfoService;
	
	@Autowired
	private ThresholdLevelService thresholdLevelService;
	
	@Autowired
	private SeverityService severityService;
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<ParamThresholdInfoAndType> threshold=thresholdInfoService.getAllParam();
		String jsonStr = thresholdInfoService.getParamJsonListStr(threshold);
		map.put("jsonListData", jsonStr);
		return viewPath+"/main";
	}
	
	
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		map.put("action", "add");
		ThresholdInfo thresholdInfo = new ThresholdInfo();
		map.put("thresholdInfo", thresholdInfo);
		return viewPath+"/edit";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save.do")
	public String save(String action, String id, ThresholdInfo thresholdInfo, String gridNewData,String defaultUnit,String thName,String descript, ModelMap map) {
		logger.info( "Enter save.do ..." );
		if( thresholdInfo.getThresholdLevel() == -1 ){
			map.put("result", "error");								
			map.put("message", "请选择阀值级数！");
			return viewPath+"/edit";
		}
		map.put("action", action);
		String uu=Identities.uuid();
			ThresholdInfo saveThresholdInfo= null;
			String saveData ="";
				saveThresholdInfo = thresholdInfo;
				saveThresholdInfo.setStatus(1);
				saveThresholdInfo.setuUID(uu);
				saveThresholdInfo.setThresholdName(thName);
				saveThresholdInfo.setDescription(descript);
				thresholdInfoService.add(saveThresholdInfo);
				saveData = thresholdInfoService.getJsonObjStr(saveThresholdInfo);
				
			
			List<ThresholdLevel> itemList=JsonUtils.getDTOList(gridNewData, ThresholdLevel.class);
			ThresholdInfo hold = thresholdInfoService.getByUUID(uu);
			if(null != hold){
				for (int i = 0; i < itemList.size(); i++) {
					itemList.get(i).setThreshold_ID(hold.getThreshold_ID());
					itemList.get(i).setThreshold_Name(thresholdInfo.getThresholdName());
					itemList.get(i).setUnits(defaultUnit);
					thresholdLevelService.add(itemList.get(i));
				}
				map.put("saveData", saveData);
				map.put("result", "success");
			}else {
				map.put("result", "error");		
				map.put("message","数据库操作异常");
			}
			
		return viewPath+"/edit";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveInfo.do")
	public String saveInfo(String action, String id,String uuid, ThresholdInfo thresholdInfo, String gridNewData,String defaultUnit,String thName,String descript, ModelMap map) {
		logger.info( "Enter save.do ..." );
		map.put("action", action);
		ThresholdInfo saveThresholdInfo= null;
		String saveData ="";
		saveThresholdInfo = thresholdInfo;
		saveThresholdInfo.setuUID(uuid);
		saveThresholdInfo.setThreshold_ID(Integer.parseInt(id));
		saveThresholdInfo.setThresholdName(thName);
		saveThresholdInfo.setDescription(descript);
		thresholdInfoService.add(saveThresholdInfo);
		saveData = thresholdInfoService.getJsonObjStr(saveThresholdInfo);
		
		thresholdLevelService.deleteByThresholdID(Integer.parseInt(id));
			
		List<ThresholdLevel> itemList=JsonUtils.getDTOList(gridNewData, ThresholdLevel.class);
		ThresholdInfo hold = thresholdInfoService.getByUUID(uuid);
		if(null != hold){
			for (int i = 0; i < itemList.size(); i++) {
				itemList.get(i).setThreshold_ID(Integer.parseInt(id));
				itemList.get(i).setThreshold_Name(thresholdInfo.getThresholdName());
				itemList.get(i).setUnits(defaultUnit);
				thresholdLevelService.add(itemList.get(i));
			}
			map.put("saveData", saveData);
			map.put("result", "success");
			map.put("info", thresholdInfo);
			map.put("jsonListData", "editdone");
			
		}else {
			map.put("result", "error");		
			map.put("message","数据库操作异常");
		}
			
		return viewPath+"/editThresholdInfo";
	}
	
	
	@RequestMapping(value = "/batchAdd.do")
	public String batchAdd(ModelMap map,HttpServletResponse response ,String num) throws IOException {
		logger.info("Enter batchAdd.do ...");
		List<ParamThresholdLevel> list=new ArrayList<ParamThresholdLevel>();
		if(num!=null){
			int subNum=Integer.parseInt(num);
			if(subNum > 0){
				for(int i=0;i<subNum;i++){
					ParamThresholdLevel p=new ParamThresholdLevel();
					
					p.setLowValue(50+i*10);
					p.setCompareLow("<=");
					p.setIndiName("指标值");
					p.setCompareHigh("<");
					p.setHighValue(60+i*10);
					p.setCount(1);
					p.setSeverity_ID(i+1);
					
					Integer in=i+1;
					List<Severity> li=severityService.getById(in.toString());
					Severity se =new Severity();
					for (int j = 0; j < li.size(); j++) {
						if(li.get(j).getSeverityCode() == i+1){
							se = li.get(j);
							break;
						}
					}
					p.setSeverity_Name(se.getSeverityName());
					p.setSeverity_EName(se.getSeverityEName());
					p.setNumber(se.getSeverityCode());
					p.setPriority(se.getPriority());
					list.add(p);
				}
			}
			String jsonStr = thresholdLevelService.getJsonListStr(list);
			JSONObject jo = new JSONObject();
			jo.put("jsonData", jsonStr);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		}
		return null;
	}
	
	
	@RequestMapping(value = "/edit.do")
	public String edit(String id,String uuid, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		ThresholdInfo info = thresholdInfoService.getByID(id);
		if(null == info){
			info = new ThresholdInfo();
		}
		List<ThresholdLevel> le = thresholdLevelService.getByThresholdId(id); 
		if(null == le){
			le = new ArrayList<ThresholdLevel>();
		}
		List<ParamThresholdLevel> level = new ArrayList<ParamThresholdLevel>();
		
		for (int i = 0; i < le.size(); i++) {
			ThresholdLevel l = le.get(i);
			ParamThresholdLevel param = new ParamThresholdLevel();
			param.setLowValue(Integer.parseInt(l.getLowValue().toString()));
			param.setCompareLow("<=");
			param.setIndiName("指标值");
			param.setCompareHigh("<");
			param.setHighValue(Integer.parseInt(l.getHighValue().toString()));
			param.setCount(l.getCount());
			param.setSeverity_ID(Integer.parseInt(l.getSeverity_ID().toString()));
			param.setSeverity_Name(l.getSeverity_Name());
			param.setSeverity_EName(l.getSeverityEName());
			param.setNumber(i+1);
			param.setPriority(l.getPriority());
			level.add(param);
		}
		String jsonStr = thresholdLevelService.getJsonListStr(level);
		map.put("jsonListData", jsonStr);
		map.put("info", info);
		map.put("id", id);
		map.put("uuid", uuid);
		return viewPath+"/editThresholdInfo";
	}
	
	@RequestMapping(value = "/detail.do")
	public String detail(ModelMap map,String id,String uuid) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		ThresholdInfo info = thresholdInfoService.getByID(id);
		if(null == info){
			info = new ThresholdInfo();
		}
		List<ThresholdLevel> le = thresholdLevelService.getByThresholdId(id); 
		if(null == le){
			le = new ArrayList<ThresholdLevel>();
		}
		List<ParamThresholdLevel> level = new ArrayList<ParamThresholdLevel>();
		
		for (int i = 0; i < le.size(); i++) {
			ThresholdLevel l = le.get(i);
			ParamThresholdLevel param = new ParamThresholdLevel();
			param.setLowValue(Integer.parseInt(l.getLowValue().toString()));
			param.setCompareLow("<=");
			param.setIndiName("指标值");
			param.setCompareHigh("<");
			param.setHighValue(Integer.parseInt(l.getHighValue().toString()));
			param.setCount(l.getCount());
			param.setSeverity_ID(Integer.parseInt(l.getSeverity_ID().toString()));
			param.setSeverity_Name(l.getSeverity_Name());
			param.setSeverity_EName(l.getSeverityEName());
			param.setNumber(i+1);
			param.setPriority(l.getPriority());
			level.add(param);
		}
		String jsonStr = thresholdLevelService.getJsonListStr(level);
		map.put("jsonListData", jsonStr);
		map.put("info", info);
		map.put("id", id);
		map.put("uuid", uuid);
		return viewPath+"/detail";
	}
	
	@RequestMapping(value="/delete.do")
	public String delete(String id,ModelMap map, HttpServletResponse response) throws IOException{
		logger.info("Enter delete.do..");
		String [] ids=id.split(",");
		for (int i = 0; i < ids.length; i++) {
			ThresholdInfo info = thresholdInfoService.getByUUID(ids[i]);
			thresholdLevelService.deleteByThresholdID(info.getThreshold_ID());
		}
		thresholdInfoService.deletesByUUIDD(ids);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null; 
	}
	
	@RequestMapping(value="/getByThresholdType.do")
	public String getByThresholdType(String threshTypeID,HttpServletResponse response) throws IOException{
		logger.info("Enter getByClassCode.do..");
		if(!threshTypeID.equals("undefined")){
			List<ThresholdInfo> thresh=thresholdInfoService.getByThreshTypeID(Integer.valueOf(threshTypeID));
			String jsonStr=thresholdInfoService.getThreshJsonListStr(thresh);
			JSONObject jo=new JSONObject();
			jo.put("thresh", jsonStr);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		  
		}
		  return null; 
	}
	
}
