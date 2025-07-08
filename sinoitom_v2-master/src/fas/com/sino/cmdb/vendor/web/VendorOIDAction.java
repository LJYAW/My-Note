/*
 * 文件名： VendorOIDAction.java
 * 
 * 创建日期： 2014-8-19
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.vendor.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.h2.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.dao.VendorOIDDao;
import com.sino.cmdb.vendor.entity.ParamVendorOID;
import com.sino.cmdb.vendor.entity.VendorOID;
import com.sino.cmdb.vendor.service.VendorOIDService;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-8-19
 */
@Controller
@RequestMapping(value="/cmdb/vendorOID")
public class VendorOIDAction {
	private static Logger logger = LoggerFactory.getLogger(VendorOIDAction.class);
	private String viewPath = "/cmdb/vendorOID";
	
	@Autowired
	private VendorOIDService vendorOIDService;
	
	@Autowired
	private VendorOIDDao vendorOIDDao;
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("Enter getTree.do...");
		List<ResItemParam> classCodes=vendorOIDDao.getAllVendor();
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
		return viewPath+"/vendorOID";
	}
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map,String id) {
		logger.info( "Enter main.do ..." );
		List<ParamVendorOID> list=vendorOIDService.getVendorById(id);	
		String jsonStr = vendorOIDService.getParamOIDJsonListStr(list);
		map.put("jsonListData", jsonStr);
		map.put("vendorId", id);
		return viewPath+"/main";
	}
	
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map,String vendorId) {
		logger.info( "Enter add.do ..." );
		VendorOID cmdbVendor =new VendorOID();
		if(!StringUtils.isNullOrEmpty(vendorId) && !"all".equals(vendorId)){
			cmdbVendor.setVendorID(Integer.parseInt(vendorId));
		}
		map.put("oid", cmdbVendor);
		map.put("Venid", vendorId);
		map.put("action", "add");
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/save.do")
	public String save(String action, String id,VendorOID oid, ModelMap map) throws JsonParseException, JsonMappingException, IOException {
		logger.info( "Enter save.do ..." );
		map.put("action", action);
		boolean validate = true;
		if( validate ){
			VendorOID saveVendorOID = null;
			
			//判断要添加或修改的OID是否符合规范：.1.3.6.1.4.1. + 数字
			if(!oid.getVendorOID().matches("^([.]1[.]3[.]6[.]1[.]4[.]1[.])([0-9]+)$")){
				map.put("oid", oid);
				map.put("result", "error");								
				map.put("message", "OID不符合规范，请重新添加！");
				return viewPath+"/edit";
			}
			
			if( "add".equals(action)){
				VendorOID venOid = vendorOIDService.getVendorOIDById(oid.getVendorOID());
				if(null != venOid){
					map.put("oid", oid);
					map.put("result", "error");								
					map.put("message", "该OID重复，请重新添加！");
					return viewPath+"/edit";
				}
				oid.setStatus(0);
				saveVendorOID = oid;
				vendorOIDService.save(saveVendorOID);
			}else{
				if(!id.equals(oid.getVendorOID())){
					VendorOID venOid = vendorOIDService.getVendorOIDById(oid.getVendorOID());
					if(null != venOid){
						map.put("oid", oid);
						map.put("result", "error");								
						map.put("message", "该OID重复，请重新添加！");
						return viewPath+"/edit";
					}
				}
				saveVendorOID = oid;
				vendorOIDService.update(saveVendorOID);
				vendorOIDService.delete(id);
				
			}
			vendorOIDService.save(saveVendorOID);
			String saveData = vendorOIDService.getJsonObjStr(saveVendorOID);
			map.put("saveData", saveData);
			map.put("result", "success");
		}else{
			map.put("result", "error");			
		}
		map.put("oid", oid);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		VendorOID vendorOid = vendorOIDService.getVendorOIDById(id);
		map.put("action", "edit");
		map.put("oid", vendorOid);
		map.put("id", id);
		map.put("Venid", vendorOid.getVendorID());
		return viewPath+"/edit";
	}
	
	
	@RequestMapping(value = "/delete.do")
	public String delete(String oid, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		String[] ids = oid.split(",");
		for(int i=0;i<ids.length;i++){
			if(!StringUtil.isNullString(ids[i]));
			vendorOIDService.delete(ids[i]);
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;
	}
	//批量审核
		@RequestMapping(value="/saveAudit.do")
		public String saveAudit(String oids, HttpServletResponse response) throws IOException{
				
			List<VendorOID> list=new ArrayList<VendorOID>();
			if(!oids.isEmpty()){
				String [] ids=oids.split(",");
				for(String id:ids){
					VendorOID prodChkCmds=vendorOIDService.getByVendorOID(id);
					prodChkCmds.setStatus(1);
					list.add(prodChkCmds);
				}
				vendorOIDService.batchSave(list);
			}
			JSONObject jo = new JSONObject();
			jo.put("success", "0");
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
			return null;
		}
	
}
