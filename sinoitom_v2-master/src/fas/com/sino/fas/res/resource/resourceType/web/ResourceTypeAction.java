/*
 * 文件名： ResourceTypeAction.java
 * 
 * 创建日期： 2013-12-21
 *
 * Copyright(C) 2013, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.fas.res.resource.resourceType.web;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.entity.SysResGroup;
import com.sino.base.system.entity.SysResItem;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.ResItemService;
import com.sino.fas.res.resource.resourceType.entity.ParamResItem;
import com.sino.fas.res.resource.resourceType.entity.ResourceType;
import com.sino.fas.res.resource.resourceType.service.ResourceTypeService;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.8 $
 *
 * @since 2013-12-21
 */
@Controller
@RequestMapping(value = "/fas/res/resource/resourceType")
public class ResourceTypeAction implements ServletContextAware{
	
	private static Logger logger = LoggerFactory.getLogger(ResourceTypeAction.class);
	private String viewPath = "/fas/res/resource/resourceType";
	
	@Autowired
	private ResourceTypeService resourceTypeService;
	
	@Autowired
	private ResItemService resItemService;
	
	private ServletContext servletContext;
	public void setServletContext(ServletContext servletContext) { //实现接口中的setServletContext方法
		  this.servletContext = servletContext;
		 }

	@RequestMapping(value = "/mainTab.do")
	public String mainTab(ModelMap map) {
		logger.info( "Enter mainTab.do ..." );
		return "/fas/res/resource/mainTab";
	}
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map) {
		logger.info( "Enter main.do ..." );
		return viewPath+"/main";
	}
	
	@RequestMapping(value="/getObjName.do")
	public String getPayMode(HttpServletResponse response) throws IOException{
		logger.info("Enter getPayMode.do...");
		SysResGroup resGroup=resItemService.getSysResGroup("resResourceEntity");
		
		List<SysResItem> resItems=resItemService.getResCode(resGroup.getResGrpId());
		List<ParamResItem> typeCodeList=new ArrayList<ParamResItem>();
		for(int i=0;i<resItems.size();i++){
			ParamResItem pResItem=new ParamResItem();
			BeanUtils.copyProperties(resItems.get(i), pResItem);
			typeCodeList.add(pResItem);
		}
		JSONObject jo = new JSONObject();
		JSONArray jsArealist = JSONArray.fromObject(typeCodeList);
		jo.put("objName", jsArealist);
		//System.out.println(jo.toString());
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value="/getData.do")
	public void getData(ModelMap map, HttpServletResponse response,String unitId) throws IOException{
		logger.info("Enter getDate.do...");
		List<ResourceType> list=resourceTypeService.getListData();
		//JSONObject jo = new JSONObject();
		String jsonStr=resourceTypeService.getJsonTreeStr(list);
		//jo.put("jsonlist", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
	}
	
	@RequestMapping(value="/add.do")
	public String add(String parentId, ModelMap map){
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		if(!StringUtil.isNullString(parentId)){
			ResourceType parentResType=resourceTypeService.getByParentId(parentId);
			map.put("parentResType", parentResType);
		}
		ResourceType resType = new ResourceType();
		String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
		String path = this.servletContext.getRealPath("/icon/devImg")+FILE_SEPARATOR;
		StringBuilder imgsb = new StringBuilder();
		File file = new File(path);
        File [] files = file.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            File file1 = files[i];
            imgsb.append(file1.getName());
            if(i<files.length-1){
            	imgsb.append(",");
            }
            //file1.getName();   //根据后缀判断
        }
        map.put("imgsb", imgsb.toString());
		resType.setParentId(parentId);
		map.put("resType", resType);
		map.put("parentId", parentId);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value="/edit.do")
	public String edit(String id, ModelMap map){
		logger.info("Enter edit.do..");
		ResourceType resType=resourceTypeService.getById(id);
		if(!StringUtil.isNullString(resType.getParentId())){
			ResourceType parentResType=resourceTypeService.getByParentId(resType.getParentId());
			map.put("parentResType", parentResType);
		}
		String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
		String path = this.servletContext.getRealPath("/icon/devImg")+FILE_SEPARATOR;
		StringBuilder imgsb = new StringBuilder();
		File file = new File(path);
        File [] files = file.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            File file1 = files[i];
            imgsb.append(file1.getName());
            if(i<files.length-1){
            	imgsb.append(",");
            }
            //file1.getName();   //根据后缀判断
        }
        map.put("imgsb", imgsb.toString());
		map.put("resType", resType);
		map.put("id", id);
		map.put("action", "edit");
		return viewPath+"/edit";
	}
	
	@RequestMapping(value="/delete.do")
	public void delete(String id,HttpServletResponse response) throws IOException{
		logger.info("Enter delete.do...");
		resourceTypeService.deletes(id.split(";"));
		JSONObject jo=new JSONObject();
		jo.put("result", "success");
		response.getWriter().print(jo.toString());
	}
	@RequestMapping(value="/save.do")
	public String save(String action,ResourceType resType,String parentId,String id,String pDispName, ModelMap map){
		logger.info( "Enter save.do ..." );

		boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			return "error/outtime";
		}
		if(validate){
			String jsonStr="";
			if("add".equals(action)){
				resourceTypeService.add(resType, parentId);
				map.put("resType", resType);
				jsonStr = resourceTypeService.getJsonObjStr(resType);
			}else if("edit".equals(action)){
				ResourceType editResType=resourceTypeService.getById(id);
				if( editResType == null ){
					map.put("result", "error");			
					map.put("message", "该机构已经被删除！");
					return viewPath+"/edit";
				}
				
				BeanUtils.copyProperties(resType, editResType, new String[]{"classID","nodeFlag","objectCount","parentID","treeCode"});
				resourceTypeService.save(editResType);
				map.put("resType", editResType);
				jsonStr=resourceTypeService.getJsonObjStr(resType);
			}
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		map.put("action", action);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/move.do")
	public String move(String moveId, String targetId, int moveType, HttpServletResponse response) throws IOException{     
		logger.info( "Enter move.do ..." );
		String result="fail";
		String jsonStr="";
		if( resourceTypeService.moveResType(moveId, targetId, moveType) ){
			
			List<ResourceType> list = resourceTypeService.getListData();			
			jsonStr= resourceTypeService.getJsonTreeStr(list);
			result="success";
		}
		JSONObject jo=new JSONObject();
		jo.put("result", result);
		jo.put("jsonlist", jsonStr);
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().print(jo.toString());
	    return null;         
	}
	@RequestMapping(value = "/getTypeData.do")
	public String getTypeData(String id,String treeCode, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getServerData.do ..." );
		String jsonStr = resourceTypeService.getJsonServerTree(treeCode);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
	    return null;         
	}
	
	@RequestMapping(value = "/getTypeDataBy.do")
	public String getTypeDataBy(String disName, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getTypeDataBy.do ..." );
		String pathName = URLDecoder.decode(disName, "utf-8");
		String jsonStr = resourceTypeService.getJsonTreeByName(pathName);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
	    return null;         
	}
}
