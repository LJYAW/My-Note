/*
 * 文件名： IndItemEventTypeMapAction.java
 * 
 * 创建日期： 2014-10-22
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.indicator.typeMap.web;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springside.modules.utils.Identities;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.system.entity.SysResGroup;
import com.sino.base.system.entity.SysResItem;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.event.entity.EventType;
import com.sino.cmdb.event.service.EventTypeService;
import com.sino.cmdb.indicator.typeMap.entity.IndItemEventTypeMap;
import com.sino.cmdb.indicator.typeMap.entity.ParamIndEventType;
import com.sino.cmdb.indicator.typeMap.service.IndItemEventTypeMapService;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.fas.res.resource.resourceType.entity.ParamResItem;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-10-22
 */
@RequestMapping(value="/cmdb/eventTypeMap")
@Controller
public class IndItemEventTypeMapAction {

	private String viewPath = "/cmdb/indicator/typeMap";
	private String view = "/cmdb/indicator/typeMap";
	private static Logger logger = LoggerFactory.getLogger(IndItemEventTypeMapAction.class);
	
	@Autowired
	private IndItemEventTypeMapService indItemEventTypeMapService;
	
	@Autowired
	private EventTypeService eventTypeService;
	
	@Autowired
	private ResItemService resItemService;
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("Enter getTree.do...");
		List<ParamTree> total = new ArrayList<ParamTree>();
		ParamTree  t=new ParamTree();
		t.setId("1_1");
		t.setText("指标组");
		t.setPid("0");
		t.setIsexpand(true);
		total.add(t);
		
		List<ResItemParam> classCodes=indItemEventTypeMapService.getAllClassCode();
		if(classCodes != null){
			for(int i=0;i<classCodes.size();i++){//设备分类  2级节点
				ParamTree pc=new ParamTree();
				ResItemParam paramClass=classCodes.get(i);
				pc.setId(paramClass.getId()+"_2");
				pc.setPid("1_1");
				pc.setText(paramClass.getText());
				total.add(pc);
				List<ResItemParam> typeCodes=indItemEventTypeMapService.getTreeByClassCode(Integer.parseInt(paramClass.getId()));
				if(!typeCodes.isEmpty()){
					for(int j=0;j<typeCodes.size();j++){//设备类型 3级节点
						ParamTree pt=new ParamTree();
						ResItemParam paramType = typeCodes.get(j);
						pt.setId(paramClass.getId()+"_"+paramType.getId()+"_3");
						pt.setPid(paramClass.getId()+"_2");
						pt.setText(paramType.getText());
						total.add(pt);
					}
				}
			}
		}
		
		JSONArray json = JSONArray.fromObject(total);
		String treeData = json.toString();
		map.put("treeData", treeData);
		return viewPath + "/typeMapTab";
		
	}
	
	@RequestMapping(value="/main.do")
	public String main(ModelMap map,String id){
		logger.info( "Enter main.do ..." );
		List<ParamIndEventType> list = new ArrayList<ParamIndEventType>();
		String flag=id.substring(id.lastIndexOf("_")+1, id.length());
		if(flag.equals("1")){
			 list = indItemEventTypeMapService.getData();
		}else if(flag.equals("2")){
			String [] typeCodes=id.split("_");
			list = indItemEventTypeMapService.getAllByClassCode(Integer.parseInt(typeCodes[0]));
		}else if(flag.equals("3")){
			String [] typeCodes=id.split("_");
			list = indItemEventTypeMapService.getByIndClassCodeAndGroupID(Integer.parseInt(typeCodes[0]),Long.parseLong(typeCodes[1]));
		}
		
		String jsonStr = indItemEventTypeMapService.getParamJsonListStr(list);
		map.put("jsonListData", jsonStr);
		return viewPath+"/main";
	}
	
	@RequestMapping(value="/getData.do")
	public String getData(ModelMap map, HttpServletResponse response) throws IOException{
		logger.info("Enter IndItemEventTypeMap getData.do...");
		List<ParamIndEventType> typeMaps=indItemEventTypeMapService.getData();
		String jsonStr=indItemEventTypeMapService.getParamJsonListStr(typeMaps);
		response.getWriter().print(jsonStr);
	    return null; 
	}
	
	@RequestMapping(value="/delete.do")
	public String delete(String typeMapIds,ModelMap map, HttpServletResponse response) throws IOException{
		logger.info("Enter delete.do..");
		if(!typeMapIds.isEmpty()){
			String [] ids=typeMapIds.split(";");
			indItemEventTypeMapService.deletes(ids);
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	
	
	
	@RequestMapping(value="/relaEventType.do")
	public String relaEventType(String itemsIds,ModelMap map){
		map.put("itemsIds", itemsIds);
		return viewPath+"/relaEventType";
	}
	
	@RequestMapping(value="/saveRelaEventType.do")
	public String saveRelaEventType(String itemsIds,String eventTypeID,ModelMap map, HttpServletResponse response) throws IOException{
		logger.info("Enter saveRelaEventType.do...");
		String [] ids=itemsIds.split(";");
		List<IndItemEventTypeMap> typeMaps=new ArrayList<IndItemEventTypeMap>();
		for(String id:ids){
			IndItemEventTypeMap typeMap=new IndItemEventTypeMap();
			typeMap.setIndItemID(Long.valueOf(id));
			typeMap.setEventTypeID(Integer.valueOf(eventTypeID));
			typeMaps.add(typeMap);
		}
		indItemEventTypeMapService.batchSave(typeMaps);
		map.put("result", "success");
		return viewPath+"/relaEventType";
	}
	
	@RequestMapping(value = "/saveDate.do")
	public String saveDate(IndItemEventTypeMap indItemEventTypeMap,  ModelMap map) {
		logger.info( "Enter save.do ..." );
		indItemEventTypeMapService.add(indItemEventTypeMap);
		
		IndItemEventTypeMap itemEventType = new IndItemEventTypeMap();
		itemEventType.setEventClassCode(indItemEventTypeMap.getEventClassCode());
		itemEventType.setEventTypeID(indItemEventTypeMap.getEventTypeID());
		itemEventType.setIndItemID(indItemEventTypeMap.getIndItemID());
		map.put("result", "success");
		return view+"/edit";
	}
	
	@RequestMapping(value="/getEventClassCode.do")
	public String getEventClassCode(String indItemID,HttpServletResponse response) throws IOException{
		
		List<Integer> codes=indItemEventTypeMapService.getEvengCodeByIndItemID(Long.parseLong(indItemID));
		
		SysResGroup resGroup = resItemService.getSysResGroup("Cmdb_EventClass");

		List<SysResItem> resItems = resItemService.getResCode(resGroup.getResGrpId());
				
		List<ResItemParam> typeCodeList = new ArrayList<ResItemParam>();
		
		for (int i = 0; i < resItems.size(); i++) {
			SysResItem item=resItems.get(i);
			
			for(int j=0;j<codes.size();j++){
				if(item.getResCode().equals(codes.get(j).toString())){
					ResItemParam pResItem = new ResItemParam();
					pResItem.setId(item.getResCode());
					pResItem.setText(item.getResName());
					typeCodeList.add(pResItem);
				}
			}
			
		}
		
		 JSONArray jsArealist = JSONArray.fromObject(typeCodeList);
		 JSONObject jo=new JSONObject();
		 jo.put("eventClass", jsArealist);
		 response.setContentType("text/json");
		 response.setCharacterEncoding("UTF-8");
		 response.getWriter().print(jo.toString());
		 return null; 
	}
	
	
	@RequestMapping(value="/getEventType.do")
	public String getEventType(String classCode,String indItemID,HttpServletResponse response) throws IOException{
		List<EventType> list=new ArrayList<EventType>();
		if(!classCode.equals("undefined")&&!indItemID.equals("undefined")){
			List<EventType> types=eventTypeService.getByClassCode(Integer.valueOf(classCode));
			List<IndItemEventTypeMap> maps=indItemEventTypeMapService.getByIndItemID(Long.parseLong(indItemID));
			for(int i=0;i<types.size();i++){
				EventType type=types.get(i);
				for(int j=0;j<maps.size();j++){
					if(type.getEventTypeID().longValue()==maps.get(j).getEventTypeID().longValue()){
						list.add(type);
					}
				}
			}
		}
		
		String jsonStr=eventTypeService.getJsonListStr(list);
		JSONObject jo=new JSONObject();
		jo.put("types", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
		
	}
}
