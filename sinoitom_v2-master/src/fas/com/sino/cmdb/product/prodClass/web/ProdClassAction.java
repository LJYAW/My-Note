/*
 * 文件名： ProdClassAction.java
 * 
 * 创建日期： 2014-1-15
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.cmdb.product.prodClass.web;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.ExpExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.entity.SysResItem;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.product.prodClass.dao.ProdClassDao;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodClass.entity.ProdClass;
import com.sino.cmdb.product.prodClass.service.ProdClassService;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.entity.CmdbVendor;
import com.sino.cmdb.vendor.service.CmdbVendorService;
import com.sino.fas.res.resource.resourceType.entity.ParamResItem;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.6 $
 *
 * @since 2014-1-15
 */
@Controller
@RequestMapping(value = "/cmdb/prodClass")
public class ProdClassAction {
	
	private static Logger logger = LoggerFactory.getLogger(ProdClassAction.class);
	private String viewPath = "/cmdb/prodclass";
	
	
	@Autowired
	private CmdbVendorService cmdbVendorService;
	
	@Autowired
	private ProdClassService prodClassService;
	
	@Autowired
	private ProdClassDao prodClassDao;
	
	@Autowired
	private ResItemService resItemService;
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("Enter getTree.do...");
		List<ResItemParam> classCodes=prodClassDao.getAllVendor();
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
		return viewPath+"/prodClassTab";
	}
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map,String id) {
		logger.info( "Enter main.do ..." );
		if(id != null && !"".equals(id) && !"all".equals(id)){			
			List<ProdClass> list = prodClassService.getClassByVendor(Integer.parseInt(id));
			String jsonStr = prodClassService.getJsonListStr(list);
			map.put("jsonListData", jsonStr);
		}else if ("all".equals(id) || "".equals(id)) {
			List<ProdClass> list = prodClassService.getAll();
			String jsonStr = prodClassService.getJsonListStr(list);
			map.put("jsonListData", jsonStr);
		}
		map.put("vendorId", id);
		return viewPath+"/main";
	}
	
	@RequestMapping(value="/add.do")
	public String add(ModelMap map,String vendorId){
		logger.info( "Enter add.do ..." );
		ProdClass prodClass =new ProdClass();
		if(!StringUtils.isNullOrEmpty(vendorId) && !"all".equals(vendorId)){
			prodClass.setVendorID(Integer.parseInt(vendorId));
		}
		map.put("prodClass", prodClass);
		map.put("action", "add");
		
		return viewPath+"/edit";
	}
	
	@RequestMapping(value="/edit.do")
	public String edit(ModelMap map,String id){
		logger.info( "Enter add.do ..." );
		ProdClass prodClass =prodClassService.getProdClassById(Integer.parseInt(id));
		map.put("prodClass", prodClass);
		map.put("action", "edit");
		map.put("id", id);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value="/delete.do")
	public void delete(String id,HttpServletResponse response) throws IOException{
		logger.info("Enter delete.do..");
		String []ids=id.split(",");
		for(int i=0;i<ids.length;i++){
			prodClassService.delete(Integer.parseInt(ids[i]));
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	}
	//批量审核
	@RequestMapping(value="/saveAudit.do")
	public String saveAudit(String prodClassIds, HttpServletResponse response) throws IOException{
			
		List<ProdClass> list=new ArrayList<ProdClass>();
		if(!prodClassIds.isEmpty()){
			String [] ids=prodClassIds.split(",");
			for(String id:ids){
				ProdClass prodChkCmds=prodClassService.getProdClassById(Integer.parseInt(id));
				prodChkCmds.setStatus(1);
				list.add(prodChkCmds);
			}
			prodClassService.batchSave(list);
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response,String id) throws IOException {
		logger.info( "Enter search.do ..." );
		List<ProdClass> list = null;
		if(!StringUtils.isNullOrEmpty(id)){
			if(id.equals("all")){
				list = prodClassService.searchProdClass();
			}else{
				list = prodClassService.getClassByVendor(Integer.parseInt(id));
			}
			
		}
		
		String jsonStr = prodClassService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}
	
	@RequestMapping(value="/getResItem.do")
	public SysResItem getResItem(String id,HttpServletResponse response,String resName) throws IOException{
		logger.info("Enter getResItem.do..");
		SysResItem desc=resItemService.findResDesc(resName, String.valueOf(id));
		ParamResItem resItem=new ParamResItem();
		BeanUtils.copyProperties(desc, resItem);
		JSONObject jo=new JSONObject();
		jo.put("result", resItem);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	
	@RequestMapping(value = "/save.do")
	public String save(String action, String id,ProdClass prodClass, ModelMap map) throws JsonParseException, JsonMappingException, IOException {
		logger.info( "Enter save.do ..." );
		map.put("action", action);
		boolean validate = true;
	
		if( validate ){
			ProdClass saveProdClass = null;
			if( "add".equals(action)){
				List<ProdClass> li = prodClassService.checkClassCode(prodClass.getVendorID(),prodClass.getProdClassCode());
				if(li.size() > 0){
					map.put("prodClass", prodClass);
					map.put("result", "error");		
					map.put("id", id);
					map.put("message", "该产品分类已存在，请重新选择！");
					return viewPath+"/edit";
				}
				String  resName=resItemService.findResContent("IT_ResClass", String.valueOf(prodClass.getProdClassCode()));
				String ID=prodClass.getVendorID().toString()+prodClass.getProdClassCode();
				saveProdClass = prodClass;
				saveProdClass.setStatus(0);
				saveProdClass.setProdClassName(resName);
				saveProdClass.setProdClassID(Integer.parseInt(ID));
			}else{
				ProdClass editProdClass = prodClassService.getProdClassById(Integer.valueOf(id));
				String  resName=resItemService.findResContent("IT_ResClass", String.valueOf(prodClass.getProdClassCode()));
				if( editProdClass == null ){
					map.put("prodClass", editProdClass);
					map.put("result", "error");								
					map.put("message", "该portlet已经被删除！");
					return viewPath+"/edit";
				}				
				BeanUtils.copyProperties(prodClass, editProdClass);
				editProdClass.setProdClassName(resName);
				saveProdClass = editProdClass;
			}
			prodClassService.save(saveProdClass);
			String saveData = prodClassService.getJsonObjStr(saveProdClass);
			map.put("saveData", saveData);
			map.put("result", "success");
		}else{
			map.put("result", "error");			
		}
		map.put("prodClass", prodClass);
		return viewPath+"/edit";
	}
	
	
	@RequestMapping(value="/getVendorTree.do")
	public String getVendorTree(ModelMap map){
		logger.info("getVendorTree.do...");
		List<CmdbVendor> vendors=cmdbVendorService.getAll();
		List<ParamTree> treeList=new ArrayList<ParamTree>();
		
		ParamTree tree1=new ParamTree();
		tree1.setPid("0");
		tree1.setId("all");
		tree1.setText("厂商信息");
		treeList.add(tree1);
		
		for(CmdbVendor vendor:vendors){
			ParamTree tree2=new ParamTree();
			tree2.setPid("all");
			tree2.setId(vendor.getVendorID().toString());
			tree2.setText(vendor.getDispName());
			treeList.add(tree2);
		}
		
		JSONArray json = JSONArray.fromObject(treeList);
		String treeData = json.toString();
		map.put("treeData", treeData);
		return "/cmdb/mainTab";
	}
	@RequestMapping(value = "/exportExcel.do")
	public String  exportExcel(ModelMap map,HttpServletResponse response)  {
		logger.info( "Enter exportExcel.do ..." );
		List<ProdClass> list = prodClassService.getAll();
		String[] headers = {"产品分类ID", "厂商ID", "产品分类编码","产品分类名称","分类描述"};//导出字段名称
		String attr="prodClassID,vendorID,prodClassCode,prodClassName,description";//导出映射字段
		String fileName="Cmdb_ProdClass_List"+".xls";//excel文件名

		try {
	          response.setContentType("octets/stream");
	          response.addHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gb2312"), "UTF-8"));
	          OutputStream out = response.getOutputStream();  
	          ExpExcelUtil.expExcel("产品分类", headers, list, attr, out);
	          
	         
	          out.close();
	           
	          
	      } catch (Exception e) {
	    	  System.out.println("导出excel出错。。。");
	          e.printStackTrace();  
	      }
		
		return null;
	}
	@RequestMapping(value = "/getExportExcelSize.do")
	public void  getExportExcelSize(ModelMap map,HttpServletResponse response) throws IOException {
		logger.info( "Enter exportExcel.do ..." );
		List<ProdClass> list = prodClassService.getAll();
		
        response.getWriter().print(list.size());
		
	}
	@RequestMapping(value = "/importGo.do")
	public String importGo(ModelMap map) throws IOException{
		logger.info( "Enter importGo.do ..." );
		
		return viewPath+"/import";
		
	}
	   
	
	@RequestMapping(value = "/importExcel.do")
	public String importExcel(MultipartFile impFile, ModelMap map) throws IOException{     
		logger.info( "Enter importExcel.do ..." );
		if( impFile.getSize()<=0 ){
			map.put("result", "error");
			map.put("message", "上传文件内容为空！");
		}
		else{
			if( prodClassService.importExcel(impFile.getInputStream())){
				map.put("result", "success");
			}
			else{
				map.put("result", "error");
			}			
			map.put("message", prodClassService.getLastMassage());
		}
		return viewPath+"/import";      
	} 

}
