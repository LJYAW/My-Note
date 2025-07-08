package com.sino.cmdb.vendor.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.PropertyFilter;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.vendor.entity.CmdbProdChkCmds;
import com.sino.cmdb.vendor.entity.CmdbVendor;
import com.sino.cmdb.vendor.entity.VendorOID;
import com.sino.cmdb.vendor.service.CmdbVendorService;
import com.sino.cmdb.vendor.service.VendorOIDService;

/**
 * 
 *
 * @author <a href="mailto:wangam@sino-bridge.com">王阿明</a>
 *
 * @version $Revision: 1.7 $
 *
 * @since 2013-1-13
 */
@Controller
@RequestMapping(value="/cmdb/vendor")
public class CmdbVendorAction {
	private static Logger logger = LoggerFactory.getLogger(CmdbVendorAction.class);
	private String viewPath = "/cmdb/vendor";
	@Autowired
	private CmdbVendorService cmdbVendorService;
	
	@Autowired
	private VendorOIDService vendorOIDService;
	
	@RequestMapping(value = "/mainTab.do")
	public String mainTab(String menuId,ModelMap map) {
		logger.info( "Enter mainTab.do ..." );
		map.put("queryType", "vendor");
		WebFuncUtils.titleContent(menuId,map);
		return "/cmdb/mainTab";
	}
	
	@RequestMapping(value = "/basicTab.do")
	public String basicTab(String menuId,ModelMap map) {
		logger.info( "Enter mainTab.do ..." );
		List<CmdbVendor> vendors=cmdbVendorService.getAll();
		List<ParamTree> treeList=new ArrayList<ParamTree>();
		
		ParamTree tree1=new ParamTree();
		tree1.setPid("0");
		tree1.setId("all");
		tree1.setText("厂商信息");
		tree1.setIsexpand(true);
		treeList.add(tree1);
		
		for(CmdbVendor vendor:vendors){
			ParamTree tree2=new ParamTree();
			if(!"-1".equals(vendor.getVendorID().toString())){
				tree2.setPid("all");
				tree2.setId(vendor.getVendorID().toString());
				tree2.setText(vendor.getDispName());
				treeList.add(tree2);
			}
		}
		
		JSONArray json = JSONArray.fromObject(treeList);
		String treeData = json.toString();
		map.put("treeData", treeData);
		map.put("queryType", "vendor");
		WebFuncUtils.titleContent(menuId,map);
		return "/cmdb/vendorBasicTab";
	}
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("getVendorTree.do...");
		List<CmdbVendor> vendors=cmdbVendorService.getAll();
		List<ParamTree> treeList=new ArrayList<ParamTree>();
		
		ParamTree tree1=new ParamTree();
		tree1.setPid("0");
		tree1.setId("all");
		tree1.setText("厂商信息");
		tree1.setIsexpand(true);
		treeList.add(tree1);
		
		for(CmdbVendor vendor:vendors){
			ParamTree tree2=new ParamTree();
			if(!"-1".equals(vendor.getVendorID().toString())){
				tree2.setPid("all");
				tree2.setId(vendor.getVendorID().toString());
				tree2.setText(vendor.getDispName());
				treeList.add(tree2);
			}
		}
		
		JSONArray json = JSONArray.fromObject(treeList);
		String treeData = json.toString();
		map.put("treeData", treeData);
		return viewPath+"/vendorTab";
	}
	
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map,String id) {
		logger.info( "Enter main.do ..." );
		map.put("id", id);
		if(id != null && !"".equals(id) && !"all".equals(id)){
			List<CmdbVendor> list=cmdbVendorService.getVendorById(id);		
			String jsonStr = cmdbVendorService.getJsonListStr(list);
			map.put("jsonListData", jsonStr);
		}else if ("all".equals(id) || "".equals(id)) {
			List<CmdbVendor> list=cmdbVendorService.getAll();
			String jsonStr = cmdbVendorService.getJsonListStr(list);
			map.put("jsonListData", jsonStr);
		}
		
		map.put("vendorId", id);
		return viewPath+"/main";
	}
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info( "Enter add.do ..." );
		CmdbVendor cmdbVendor =new CmdbVendor();
		map.put("cmdbVendor", cmdbVendor);
		map.put("action", "add");
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		CmdbVendor cmdbVendor = cmdbVendorService.getCmdbVendorById(Integer.valueOf(id));
		map.put("action", "edit");
		map.put("cmdbVendor", cmdbVendor);
		map.put("vendorID", id);
		map.put("id", id);
		return viewPath+"/edit";
	}
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		String[] ids = id.split(",");
		for(int i=0;i<ids.length;i++){
			if(!StringUtil.isNullString(ids[i]));
			cmdbVendorService.delete(Integer.valueOf(ids[i]));
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;
	}
	@RequestMapping(value="/saveAudit.do")
	public String saveAudit(String vendorIds, HttpServletResponse response) throws IOException{
		
		List<CmdbVendor> list=new ArrayList<CmdbVendor>();
		if(!vendorIds.isEmpty()){
			String [] ids=vendorIds.split(",");
			for(String id:ids){
				CmdbVendor vendor=cmdbVendorService.getCmdbVendorById(Integer.parseInt(id));
				vendor.setStatus(1);
				list.add(vendor);
			}
			cmdbVendorService.batchSave(list);
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;
	}
	@RequestMapping(value = "/save.do")
	public String save(String action, String id,CmdbVendor cmdbVendor, ModelMap map) throws JsonParseException, JsonMappingException, IOException {
		logger.info( "Enter save.do ..." );
		map.put("action", action);
		boolean validate = true;
		if( validate ){
			CmdbVendor saveCmdbVendor = null;
			
			VendorOID saveVendorOID = new VendorOID();

			saveVendorOID.setVendorOID(".1.3.6.1.4.1."+cmdbVendor.getVendorID().toString());
			saveVendorOID.setVendorID(cmdbVendor.getVendorID());
			saveVendorOID.setOidFlag(1);
			vendorOIDService.save(saveVendorOID);
			
			if( "add".equals(action)){
				cmdbVendor.setStatus(0);
				saveCmdbVendor = cmdbVendor;
			}else{
				CmdbVendor editCmdbVendor = cmdbVendorService.getCmdbVendorById(Integer.valueOf(id));
				if( editCmdbVendor == null ){
					map.put("cmdbVendor", editCmdbVendor);
					map.put("result", "error");								
					map.put("message", "该portlet已经被删除！");
					return viewPath+"/edit";
				}
				
				List<CmdbVendor> vendorNum = cmdbVendorService.getVendorById(cmdbVendor.getVendorID().toString());
				if(1 <= vendorNum.size() && !cmdbVendor.getVendorID().toString().equals(id)){
					map.put("cmdbVendor", cmdbVendor);
					map.put("result", "error");								
					map.put("message", "厂商标识重复！");
					map.put("id", id);
					return viewPath+"/edit";
				}
						
				BeanUtils.copyProperties(cmdbVendor, editCmdbVendor);
				saveCmdbVendor = editCmdbVendor;
			}
			cmdbVendorService.save(saveCmdbVendor);
			String saveData = cmdbVendorService.getJsonObjStr(saveCmdbVendor);
			map.put("saveData", saveData);
			map.put("result", "success");
		}else{
			map.put("result", "error");			
		}
		map.put("cmdbVendor", cmdbVendor);
		return viewPath+"/edit";
	}
	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<CmdbVendor> list = cmdbVendorService.searchCmdbVendor(filters);
		String jsonStr = cmdbVendorService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}
	@RequestMapping(value="/getVendorID.do")
	public String getVendorID(HttpServletResponse response) throws IOException{
		logger.info("Enter getVendor.do..");
		List<CmdbVendor> cmdbVendorList = cmdbVendorService.getAll();
		String jsonStr=cmdbVendorService.getJsonListStr(cmdbVendorList);
	    response.getWriter().print(jsonStr);
		return null;
	}
	
	@RequestMapping(value="/getVendor.do")
	public String getVendor(HttpServletResponse response) throws IOException{
		logger.info("Enter getVendor.do..");
		int flag=1;
		List<CmdbVendor> vendors=cmdbVendorService.getVendorDispNameList(flag);
		String jsonStr = cmdbVendorService.getJsonMinListStr(vendors);
	    response.getWriter().print(jsonStr);
		return null;
	}
	
	@RequestMapping(value="/getVendorByFlag.do")
	public String getVendorByFlag(HttpServletResponse response) throws IOException{
		logger.info("Enter getVendor.do..");
		int flag=1;
		List<CmdbVendor> vendors=cmdbVendorService.getVendorDispNameList(flag);
		JSONObject jo = new JSONObject();
		JSONArray jsArealist = JSONArray.fromObject(vendors);
		jo.put("vendors", jsArealist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	@RequestMapping(value = "/getVendorData.do")
	public String getVendorData(HttpServletResponse response) throws IOException{     
		logger.info( "Enter getVendorData.do ..." );
		List<CmdbVendor> list = cmdbVendorService.getAll();
		JSONObject jo = new JSONObject();
		JSONArray vendorlist = JSONArray.fromObject(list);
		jo.put("vendorlist", vendorlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	
	@RequestMapping(value="/getVendorItem.do")
	public CmdbVendor getVendorItem(String id,HttpServletResponse response) throws IOException{
		List<CmdbVendor> list = cmdbVendorService.getAll();
		String jsonStr = cmdbVendorService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
		return null;
	}
	
	@RequestMapping(value="/allVendor.do")
	public String allVendor(HttpServletResponse response) throws IOException{
		logger.info("Enter getVendor.do..");
		List<CmdbVendor> vendors=cmdbVendorService.getVendorDispNameList(1);
		JSONObject jo=new JSONObject();
		jo.put("vendors", vendors);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
	    response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value = "/getMinData.do")
	public String getMinData(HttpServletResponse response) throws IOException{     
		logger.info( "Enter getMinData.do ..." );
		List<CmdbVendor> list = cmdbVendorService.getAll();
		String jsonStr = cmdbVendorService.getJsonMinListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}  
	
}
