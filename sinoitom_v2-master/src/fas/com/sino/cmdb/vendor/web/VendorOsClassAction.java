/*
 * 文件名： VendorOSTypeAction.java
 * 
 * 创建日期： 2014-1-19
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.dao.VendorOsClassDao;
import com.sino.cmdb.vendor.entity.CmdbProdChkCmds;
import com.sino.cmdb.vendor.entity.VendorOsClass;
import com.sino.cmdb.vendor.service.VendorOsClassService;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.2 $
 *
 * @since 2014-1-19
 */
@Controller
@RequestMapping(value="/cmdb/ostype")
public class VendorOsClassAction {
	
	private static Logger logger = LoggerFactory.getLogger(VendorOsClassAction.class);
	private String viewPath = "/cmdb/ostype";
	
	@Autowired
	private VendorOsClassService vendorOSTypeService;
	
	@Autowired
	private ResItemService resItemService;
	
	@Autowired
	private VendorOsClassDao vendorOSTypeDao;
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("Enter getTree.do...");
		List<ResItemParam> classCodes=vendorOSTypeDao.getAllVendor();
		List<ParamTree> total = new ArrayList<ParamTree>();
		
		ParamTree  t=new ParamTree();
		t.setPid("0");
		t.setId("all");
		t.setText("厂商信息");
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
		return viewPath+"/osTypeTab";
	}
	
	@RequestMapping(value="/main.do")
	public String main(ModelMap map,String id) {
		logger.info("Enter main.do..");
		if(id != null && !"".equals(id) && !"all".equals(id)){	
			List<VendorOsClass> list=vendorOSTypeService.getByVendorID(id);
			String jsonStr = vendorOSTypeService.getJsonListStr(list);
			map.put("jsonListData", jsonStr);
		}else if ("all".equals(id) || "".equals(id)) {
			List<VendorOsClass> list=vendorOSTypeService.getAll();
			String jsonStr = vendorOSTypeService.getJsonListStr(list);
			map.put("jsonListData", jsonStr);
		}

		map.put("vendorId", id);
		return viewPath+"/main";
	}
	
	@RequestMapping(value="/search.do")
	public String search(HttpServletResponse response) throws IOException{
		logger.info("Enter search.do..");
		List<VendorOsClass> ostypelist=vendorOSTypeService.getAll();
		String jsonStr=vendorOSTypeService.getJsonListStr(ostypelist);
		response.getWriter().print(jsonStr);
	    return null;         
	}
	
	@RequestMapping(value="/add.do")
	public String add(ModelMap map,String vendorId){
		logger.info("Enter add.do..");
		VendorOsClass osType=new VendorOsClass();
		if(!StringUtils.isNullOrEmpty(vendorId) && !"all".equals(vendorId)){			
			osType.setVendorID(Integer.parseInt(vendorId));
		}
		map.put("action", "add");
		map.put("osType", osType);
		return viewPath+"/edit";
		
	}
	
	@RequestMapping(value="/edit.do")
	public String edit(String id,ModelMap map){
		logger.info("Enter edit.do...");
		VendorOsClass osType=vendorOSTypeService.getById(Integer.parseInt(id));
		map.put("action", "edit");
		map.put("osType", osType);
		map.put("id", id);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value="/delete.do")
	public void delete(String id,HttpServletResponse response) throws IOException{
		logger.info("Enter delete.do..");
		String [] ids=id.split(",");
		for(int i=0;i<ids.length;i++){
			vendorOSTypeService.delete(Integer.parseInt(ids[i]));
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	}
	//批量审核
	@RequestMapping(value="/saveAudit.do")
	public String saveAudit(String osTypeIds, HttpServletResponse response) throws IOException{
				
		List<VendorOsClass> list=new ArrayList<VendorOsClass>();
		if(!osTypeIds.isEmpty()){
			String [] ids=osTypeIds.split(",");
			for(String id:ids){
				VendorOsClass prodChkCmds=vendorOSTypeService.getById(Integer.parseInt(id));
				prodChkCmds.setStatus(1);
				list.add(prodChkCmds);
			}
			vendorOSTypeService.batchSave(list);
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	@RequestMapping(value="/getOsTypeByVendorID.do")
	public String getOsTypeByVendorID(HttpServletResponse response,String vendorID) throws IOException{
		logger.info("Enter getOsType.do..");
		List<VendorOsClass> osTypes=vendorOSTypeService.getByVendorID(vendorID);
		JSONObject jo = new JSONObject();
		JSONArray jsArealist = JSONArray.fromObject(osTypes);
		jo.put("osTypes", jsArealist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	

	@RequestMapping(value="/save.do")
	public String save(ModelMap map,String action,String id,HttpServletResponse response,VendorOsClass osType,String vendorName){
		logger.info("Enter save.do..");
		map.put("action", action);
	    boolean validate = true;
	    
	    if(validate){
	    	VendorOsClass saveOsType=null;
	    	if(action.equals("add")){
	    		String  resName=resItemService.findResContent("IT_OSType", String.valueOf(osType.getOsClassCode()));
	    		saveOsType=osType;
	    		saveOsType.setStatus(0);
	    		saveOsType.setOsClassName(resName);
	    		saveOsType.setVendorName(vendorName);
	    		saveOsType.setVendorOSTypeID(Integer.parseInt(osType.getVendorID().toString()+osType.getOsClassCode()));
	    	}else{
	    		VendorOsClass editOsType=vendorOSTypeService.getById(Integer.parseInt(id));
	    		if( editOsType == null ){
					map.put("osType", editOsType);
					map.put("result", "error");								
					map.put("message", "该portlet已经被删除！");
					return viewPath+"/edit";
				}	
	    		String  resName=resItemService.findResContent("IT_OSType", String.valueOf(osType.getOsClassCode()));
	    		editOsType.setOsClassName(resName);
	    		editOsType.setVendorName(vendorName);
	    		saveOsType=editOsType;
	    	}
	    	
	    	vendorOSTypeService.save(saveOsType);
	    	String saveData = vendorOSTypeService.getJsonObjStr(saveOsType);
			map.put("saveData", saveData);
			map.put("result", "success");
	    }else{
	    	map.put("result", "error");	
	    }
	    map.put("osType", osType);
		return viewPath+"/edit";
	}
	@RequestMapping(value = "/getOSVendorData.do")
	public String getOSVendorData(String id,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getOSVendorData.do ..." );
		List<VendorOsClass> list = vendorOSTypeService.getJsonVendorOSType();
		JSONObject jo = new JSONObject();
		JSONArray osvendorlist = JSONArray.fromObject(list);
		jo.put("osvendorlist", osvendorlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	@RequestMapping(value = "/getOSTypeNameData.do")
	public String getOSTypeNameData(String vendorID,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getOSTypeNameData.do ..." );
		List<VendorOsClass> list = vendorOSTypeService.getJsonOSTypeName(Integer.valueOf(vendorID));
		JSONObject jo = new JSONObject();
		JSONArray ostypenamelist = JSONArray.fromObject(list);
		jo.put("ostypenamelist", ostypenamelist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}

}
