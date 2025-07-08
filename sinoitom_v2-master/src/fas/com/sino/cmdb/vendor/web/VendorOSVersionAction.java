/*
 * 文件名： VendorOSVersionAction.java
 * 
 * 创建日期： 2014-2-13
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
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.dao.VendorOSVersionDao;
import com.sino.cmdb.vendor.entity.CmdbProdChkCmds;
import com.sino.cmdb.vendor.entity.VendorOSVersion;
import com.sino.cmdb.vendor.service.VendorOSVersionService;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-2-13
 */
@Controller
@RequestMapping(value="/cmdb/vendorOSVersion")
public class VendorOSVersionAction {
	
	private static Logger logger = LoggerFactory.getLogger(VendorOSVersionAction.class);
	
	private static String viewPath="/cmdb/vendorOSVersion";
	
	@Autowired
	private VendorOSVersionService vendorOSVersionService;
	
	@Autowired
	private VendorOSVersionDao vendorOSVersionDao;
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("Enter getTree.do...");
		List<ResItemParam> classCodes=vendorOSVersionDao.getAllVendor();
		List<ParamTree> total = new ArrayList<ParamTree>();
		
		ParamTree  t=new ParamTree();
		t.setPid("0");
		t.setId("all");
		t.setText("操作系统");
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
		return viewPath+"/osVersionTab";
	}
	
	@RequestMapping(value="/main.do")
	public String main(ModelMap map,String id) {
		logger.info("Enter main.do");
		if(id != null && !"".equals(id) && !"all".equals(id)){	
			List<VendorOSVersion> ostypelist=vendorOSVersionService.getOSVersionById(id);
			String jsonStr=vendorOSVersionService.getJsonListStr(ostypelist);
			map.put("jsonListData", jsonStr);
		}else if ("all".equals(id) || "".equals(id)) {
			List<VendorOSVersion> ostypelist=vendorOSVersionService.getAll();
			String jsonStr=vendorOSVersionService.getJsonListStr(ostypelist);
			map.put("jsonListData", jsonStr);
		}
		map.put("vendorId", id);
		return viewPath+"/main";
	}
	
	@RequestMapping(value="/add.do")
	public String add(ModelMap map,String vendorId){
		logger.info("Enter add.do..");
		VendorOSVersion version=new VendorOSVersion();
		if(!StringUtils.isNullOrEmpty(vendorId) && !"all".equals(vendorId)){
			version.setVendorID(Integer.parseInt(vendorId));			
		}
		map.put("action", "add");
		map.put("version", version);
		return viewPath+"/edit";
		
	}
	
	@RequestMapping(value="/edit.do")
	public String edit(String id,ModelMap map){
		logger.debug("Enter edit.do...");
		VendorOSVersion version=vendorOSVersionService.getById(id);
		map.put("id", id);
		map.put("action", "edit");
		map.put("version", version);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value="/delete.do")
	public void delete(String id,HttpServletResponse response) throws IOException{
		logger.info("Enter delete.do...");
		String [] ids=id.split(",");
		for(int i=0;i<ids.length;i++){
			vendorOSVersionService.delete(ids[i]);
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	}
	
	//批量审核
	@RequestMapping(value="/saveAudit.do")
	public String saveAudit(String osVersionIds, HttpServletResponse response) throws IOException{
				
		List<VendorOSVersion> list=new ArrayList<VendorOSVersion>();
		if(!osVersionIds.isEmpty()){
			String [] ids=osVersionIds.split(",");
			for(String id:ids){
				VendorOSVersion prodChkCmds=vendorOSVersionService.getById(id);
				prodChkCmds.setStatus(1);
				list.add(prodChkCmds);
			}
			vendorOSVersionService.batchSave(list);
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
			  return null;
	}
	@RequestMapping(value="/search.do")
	public String search(HttpServletResponse response) throws IOException{
		logger.info("Enter search.do..");
		List<VendorOSVersion> ostypelist=vendorOSVersionService.getAll();
		String jsonStr=vendorOSVersionService.getJsonListStr(ostypelist);
		response.getWriter().print(jsonStr);
	    return null;         
	}
	
	@RequestMapping(value="/save.do")
	public String save(String id,String action,ModelMap map,VendorOSVersion version,String osName,HttpServletResponse response){
		logger.info("Enter save.do..");
		map.put("action", action);
		boolean validate = true;
		if(validate){
			VendorOSVersion saveVersion=null;
			if("add".equals(action)){
				saveVersion=version;
				saveVersion.setStatus(0);
			}else{
				VendorOSVersion editVersion=vendorOSVersionService.getById(id);
				BeanUtils.copyProperties(version, editVersion);
				editVersion.setOsVersionID(Integer.parseInt(id));
				editVersion.setStatus(0);
				saveVersion=editVersion;
			}
			vendorOSVersionService.save(saveVersion);
			String saveData = vendorOSVersionService.getJsonObjStr(saveVersion);
			map.put("saveData", saveData);
			map.put("result", "success");
		}else{
			map.put("result", "error");	
		}
		map.put("version", version);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value="/checkVersion.do")
	public String checkVersion(String vendorId,String typeCode,String osName,String osVersion,HttpServletResponse response) throws IOException{
		logger.info("Enter checkVersion.do...");
		List<VendorOSVersion> versions=vendorOSVersionService.checkVersion(vendorId, typeCode, osName,osVersion);
		JSONObject jo=new JSONObject();
		jo.put("versions", versions);
	    response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value = "/getOSVersion.do")
	public String getOSVersion(String vendorID,String oSName,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getOSVersion.do ..." );
		List<VendorOSVersion> list = vendorOSVersionService.getOSVersionData(Integer.valueOf(vendorID),oSName);
		JSONObject jo = new JSONObject();
		JSONArray vendorOSVersionlist = JSONArray.fromObject(list);
		jo.put("vendorOSVersionlist", vendorOSVersionlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	
	
	@RequestMapping(value = "/getOSVersionByVendorAndCodeAndOsName.do")
	public String getOSVersionByVendorAndCodeAndOsName(String vendorID,String typeCode,String oSName,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getOSVersionByVendorAndCodeAndOsName.do ..." );
		List<VendorOSVersion> list = vendorOSVersionService.getOSVersionByVendorAndTypeCodeAndOsName(Integer.valueOf(vendorID),Integer.valueOf(typeCode),oSName);
		JSONObject jo = new JSONObject();
		JSONArray vendorOSVersionlist = JSONArray.fromObject(list);
		jo.put("vendorOSVersionlist", vendorOSVersionlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	
	//根据VendorId查 操作系统类型（取OSName）
	@RequestMapping(value = "/getOSNameById.do")
	public String getOSNameById(String vendorID,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getOSNameById.do ..." );
		List<VendorOSVersion> list = vendorOSVersionService.getOSVersionByVendorId(vendorID);
		JSONObject jo = new JSONObject();
		JSONArray vendorOSVersionlist = JSONArray.fromObject(list);
		jo.put("vendorOSVersionlist", vendorOSVersionlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	// 根据vendorId 和 OSName 查osversion   
	@RequestMapping(value = "/getOsVersionByVendorIdAndOsType.do")
	public String getOsVersionByVendorIdAndOsType(String vendorID,String osName,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getOsVersionByVendorIdAndOsType.do ..." );
		List<VendorOSVersion> list = vendorOSVersionService.getOsVersionByVendorIdAndOsType(vendorID,osName);
		JSONObject jo = new JSONObject();
		JSONArray vendorOSVersionlist = JSONArray.fromObject(list);
		jo.put("list", vendorOSVersionlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	
	@RequestMapping(value = "/getOsVersionByOsClassAndOsType.do")
	public String getOsVersionByOsClassAndOsType(String osClass,String osType,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getOsVersionByOsClassAndOsType.do ..." );
		List<VendorOSVersion> list = vendorOSVersionService.getOsVersionByOsClassAndOsType(osClass,osType);
		JSONObject jo = new JSONObject();
		JSONArray vendorOSVersionlist = JSONArray.fromObject(list);
		jo.put("list", vendorOSVersionlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	
	
	@RequestMapping(value = "/getOsFeature.do")
	public String getOsFeature(String vendorID,String osName,String osVersion,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getOsFeature.do ..." );
		List<VendorOSVersion> list = vendorOSVersionService.getOsFeature(vendorID,osName,osVersion);
		JSONObject jo = new JSONObject();
		JSONArray vendorOSVersionlist = JSONArray.fromObject(list);
		jo.put("list", vendorOSVersionlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	
}
