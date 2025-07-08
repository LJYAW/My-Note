/*
 * 文件名： VendorOSNameAction.java
 * 
 * 创建日期： 2014-1-19
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.vendor.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.h2.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.dao.VendorOsTypeDao;
import com.sino.cmdb.vendor.entity.CmdbProdChkCmds;
import com.sino.cmdb.vendor.entity.VendorOsType;
import com.sino.cmdb.vendor.service.VendorOsTypeService;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision: 1.3 $
 *
 * @since 2014-1-19
 */
@Controller
@RequestMapping(value="/cmdb/os")
public class VendorOsTypeAction {
	private static Logger logger=LoggerFactory.getLogger(VendorOsTypeService.class);
	
	private static String viewPath="/cmdb/os";
	
	@Autowired
	private VendorOsTypeService vendorOSNameService;
	
	@Autowired
	private VendorOsTypeDao vendorOSNameDao;
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("Enter getTree.do...");
		List<ResItemParam> classCodes=vendorOSNameDao.getAllVendor();
		List<ParamTree> total = new ArrayList<ParamTree>();
		
		ParamTree  t=new ParamTree();
		t.setPid("0");
		t.setId("all");
		t.setText("操作系统类型");
		t.setIsexpand(true);
		total.add(t);
		for(ResItemParam vendor:classCodes){
			ParamTree tree2=new ParamTree();
			if(!"-1".equals(vendor.getId().toString())){
				tree2.setPid("all");
				tree2.setId(vendor.getId().toString());
				tree2.setText(vendor.getText());
				total.add(tree2);
			}
		}
		
		JSONArray json = JSONArray.fromObject(total);
		String treeData = json.toString();
		map.put("treeData", treeData);
		return viewPath+"/osTab";
	}
	
	@RequestMapping(value="/main.do")
	public String main(ModelMap map,String id) {
		logger.info("Enter main.do");	
		if(id != null && !"".equals(id) && !"all".equals(id)){
			List<VendorOsType> list=vendorOSNameService.getDataByVendorID(Integer.parseInt(id));			
			String jsonStr = vendorOSNameService.getJsonListStr(list);
			map.put("jsonListData", jsonStr);
		}else if ("all".equals(id) || "".equals(id)) {
			List<VendorOsType> list=vendorOSNameService.getAll();
			String jsonStr = vendorOSNameService.getJsonListStr(list);
			map.put("jsonListData", jsonStr);
		}
		map.put("vendorId", id);
		return viewPath+"/main";
	}
	
	@RequestMapping(value="/search.do")
	public String search(HttpServletResponse response) throws IOException{
		logger.info("Enter search.do..");
		List<VendorOsType> oslist=vendorOSNameService.getAll();
		String jsonStr=vendorOSNameService.getJsonListStr(oslist);
		response.getWriter().print(jsonStr);
		return null;
	}
	
	@RequestMapping(value="/add.do")
	public String add(ModelMap map,String vendorId){
		logger.info("Enter add.do..");
		VendorOsType os=new VendorOsType();
		if(!StringUtils.isNullOrEmpty(vendorId) && !"all".equals(vendorId)){			
			os.setVendorID(Integer.parseInt(vendorId));
		}
		map.put("action", "add");
		map.put("os", os);
		return viewPath+"/edit";
		
	}
	
	@RequestMapping(value="/edit.do")
	public String edit(String id,ModelMap map){
		logger.debug("Enter edit.do...");
		VendorOsType os=vendorOSNameService.getById(id);
		map.put("aciton", "edit");
		map.put("id", id);
		map.put("os", os);
		return viewPath+"/edit";
	}
	
	
	@RequestMapping(value="/delete.do")
	public void delete(String id,HttpServletResponse resppnse) throws IOException{
		logger.info("Enter delete.do..");
		String [] ids=id.split(",");
		for(int i=0;i<ids.length;i++){
			vendorOSNameService.delete(Integer.parseInt(ids[i]));
		}
		resppnse.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	}
	//批量审核
	@RequestMapping(value="/saveAudit.do")
	public String saveAudit(String OSIds, HttpServletResponse response) throws IOException{
				
		List<VendorOsType> list=new ArrayList<VendorOsType>();
		if(!OSIds.isEmpty()){
			String [] ids=OSIds.split(",");
			for(String id:ids){
				VendorOsType prodChkCmds=vendorOSNameService.getById(id);
				prodChkCmds.setStatus(1);
				list.add(prodChkCmds);
			}
			vendorOSNameService.batchSave(list);
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	@RequestMapping(value="/save.do")
	public String save(ModelMap map,String action,String id,HttpServletResponse response,String vendorName,VendorOsType os,String osClassName){
		logger.info("Enter save.do...");
		map.put("action", action);
		boolean validate = true; 
		
		if(validate){
			VendorOsType saveOs=null;
			if(action.equals("add")){
				saveOs=os;
				saveOs.setStatus(0);
				saveOs.setOsClassName(osClassName);
				saveOs.setVendorName(vendorName);
				//saveOs.setVendorOSID(Integer.parseInt(os.getVendorID().toString()+os.getOsTypeCode()));
			}else{
				VendorOsType editOs=vendorOSNameService.getById(id);
				BeanUtils.copyProperties(os, editOs);
				editOs.setOsClassName(osClassName);
				editOs.setVendorName(vendorName);
				editOs.setOsTypeID(Integer.parseInt(id));
				editOs.setStatus(0);
				saveOs=editOs;
			}
			vendorOSNameService.save(saveOs);
			String saveData = vendorOSNameService.getJsonObjStr(saveOs);
			map.put("saveData", saveData);
			map.put("result", "success");
		}else{
			map.put("result", "error");	
		}
		 map.put("os", os);
		 return viewPath+"/edit";
	}
	@RequestMapping(value = "/getOSNameData.do")
	public String getOSNameData(String id,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getOSNameData.do ..." );
		List<VendorOsType> list = vendorOSNameService.getAll();
		JSONObject jo = new JSONObject();
		JSONArray oSNamelist = JSONArray.fromObject(list);
		jo.put("oSNamelist", oSNamelist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	
	
	@RequestMapping(value="/checkOsName.do")
	public List<VendorOsType> checkOsName(String vendorId,String osName,HttpServletResponse response) throws IOException{
		logger.info("Enter checkOsName.do...");
		List<VendorOsType> osNames=vendorOSNameService.checkOsName(vendorId,osName);
		JSONObject jo=new JSONObject();
		jo.put("osNames", osNames);
	    response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value = "/getOSNameByVendorID.do")
	public String getOSNameByVendorID(String id,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getOSNameData.do ..." );
		if(!StringUtil.isNullString(id)&&!id.equals("undefined")){
			List<VendorOsType> list = vendorOSNameService.getDataByVendorID(Integer.valueOf(id));
			
			JSONObject jo = new JSONObject();
			JSONArray oSNamelist = JSONArray.fromObject(list);
			jo.put("oSNamelist", oSNamelist);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		}

	    return null;         
	}
	
	@RequestMapping(value="/getByVendorAndCode.do")
	public String getByVendorAndCode(String vendorId,String typeCode,HttpServletResponse response) throws IOException{
		logger.info("Enter getByVendorAndCode.do...");
		List<VendorOsType> osNames=vendorOSNameService.getByVendorAndCode(vendorId,typeCode);
		JSONObject jo=new JSONObject();
		jo.put("osNames", osNames);
	    response.getWriter().print(jo.toString());
	    return null;
	}
	
	@RequestMapping(value = "/getMinData.do")
	public String getMinData(String vendorId, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getMinData.do ..." );
		List<VendorOsType> list = null;
		if( StringUtil.isNullString(vendorId) ){
			list = vendorOSNameService.getAllOrderOSname();
		}
		else{
			list = vendorOSNameService.getVendorOS(vendorId);
		}
		String jsonStr = vendorOSNameService.getJsonMinListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}
	
	@RequestMapping(value="/getByOsClass.do")
	public String getByOsClass(String osClass,HttpServletResponse response) throws IOException{
		logger.info("Enter getByVendorAndCode.do...");
		List<VendorOsType> osTypes=vendorOSNameService.getByOsClass(osClass);
		JSONObject jo=new JSONObject();
		jo.put("osTypes", osTypes);
	    response.getWriter().print(jo.toString());
	    return null;
	}

}
