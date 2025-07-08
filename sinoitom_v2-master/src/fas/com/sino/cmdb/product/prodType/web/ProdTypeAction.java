/*
 * 文件名： ProdTypeAction.java
 * 
 * 创建日期： 2014-1-16
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.cmdb.product.prodType.web;

import com.sino.base.common.util.DateUtil;
import com.sino.base.common.util.ExpExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.system.entity.SysResGroup;
import com.sino.base.system.entity.SysResItem;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.product.prodClass.dao.ProdClassDao;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodClass.entity.ProdClass;
import com.sino.cmdb.product.prodClass.service.ProdClassService;
import com.sino.cmdb.product.prodType.entity.ProdType;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.product.prodType.service.ProdTypeService;
import com.sino.cmdb.vendor.entity.ParamVendor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.3 $
 *
 * @since 2014-1-16
 */
@Controller
@RequestMapping(value = "/cmdb/prodType")
public class ProdTypeAction {
	
	private static Logger logger = LoggerFactory.getLogger(ProdTypeAction.class);
	private String viewPath = "/cmdb/prodtype";
	
	@Autowired
	private ProdTypeService prodTypeService;
	
	@Autowired
	private ProdClassDao prodClassDao;
	
	@Autowired
	private ProdClassService prodClassService;
	
	@Autowired
	private ResItemService resItemService;
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("Enter getTree.do...");
		List<ParamTree> total = new ArrayList<ParamTree>();
		ParamTree  t=new ParamTree();
		t.setId("1_1");
		t.setText("产品分类");
		t.setPid("0");
		t.setIsexpand(true);
		total.add(t);
		
		List<ResItemParam> classCodes=prodTypeService.getAllVendor();
		if(classCodes != null){
			for(int i=0;i<classCodes.size();i++){//设备分类  2级节点
				ParamTree pc=new ParamTree();
				ResItemParam paramClass=classCodes.get(i);
				pc.setId(paramClass.getId()+"_2");
				pc.setPid("1_1");
				pc.setText(paramClass.getText());
				total.add(pc);
				List<ResItemParam> typeCodes=prodTypeService.getTreeByClassCode(Integer.parseInt(paramClass.getId()));
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
		return viewPath+"/prodTypeTab";
		
	}
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map,String id) {
		logger.info( "Enter main.do ..." );
		List<ProdType> list = new ArrayList<ProdType>();
		String flag=id.substring(id.lastIndexOf("_")+1, id.length());
		if(flag.equals("1")){
			 list = prodTypeService.getAll();
		}else if(flag.equals("2")){
			String [] typeCodes=id.split("_");
			list = prodTypeService.getProdTypeById(typeCodes[0]);
		}else if(flag.equals("3")){
			String [] typeCodes=id.split("_");
			list = prodTypeService.getByVendorAndClassCode(typeCodes[0],typeCodes[1]);
		}
		
		String jsonStr = prodTypeService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		map.put("id", id);
		return viewPath+"/main";
	}
	
	@RequestMapping(value="/add.do")
	public String add(ModelMap map,String id,String code){
		logger.info( "Enter add.do ..." );
		ProdType prodType =new ProdType();
		if(!StringUtils.isNullOrEmpty(id) && !"all".equals(id)){
			String flag=id.substring(id.lastIndexOf("_")+1, id.length());
			String [] typeCodes=id.split("_");
			if(flag.equals("2")){
				prodType.setVendorID(Integer.parseInt(typeCodes[0]));
			}else if(flag.equals("3")){
				prodType.setVendorID(Integer.parseInt(typeCodes[0]));
				prodType.setProdClassCode(Integer.parseInt(typeCodes[1]));
			}
			
			
		}
		if(!StringUtils.isNullOrEmpty(code) ){
			map.put("pcCode", code);//生成产品分类下拉框的判断，有就赋值，没有就赋值为-1，防止页面saveOK方法报错
		}else {
			map.put("pcCode", -1);
		}
		map.put("prodType", prodType);
		map.put("action", "add");
		return viewPath+"/edit";
	}
	
	@RequestMapping(value="/edit.do")
	public String edit(ModelMap map,String vendorId,String code,String id){
		logger.info( "Enter add.do ..." );
		ProdType prodType =new ProdType();
		if(!StringUtils.isNullOrEmpty(vendorId) && !"all".equals(vendorId)){
			prodType.setVendorID(Integer.parseInt(vendorId));
		}
		if(!StringUtils.isNullOrEmpty(code) ){
			map.put("pcCode", code);//生成产品分类下拉框的判断，有就赋值，没有就赋值为-1，防止页面saveOK方法报错
		}else {
			map.put("pcCode", -1);
		}
		map.put("prodType", prodType);
		map.put("action", "edit");
		map.put("id", id);
		map.put("vendorID", vendorId);
		return viewPath+"/edit";
	}
	
	
	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		//List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<ProdType> list = prodTypeService.getAll();
		String jsonStr = prodTypeService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}
	
	@RequestMapping(value="/getCodeByVendor.do")
	public List<ProdClass> getCodeByVendorId(HttpServletResponse response,String vendorId) throws IOException{
		logger.info("Enter getCodeByVendorId....");
		if(!StringUtil.isNullString(vendorId)&&!vendorId.equals("undefined")){
			List<ProdClass> pClass=prodClassService.getClassByVendor(Integer.parseInt(vendorId));
			JSONObject jo=new JSONObject();
			jo.put("pClass", pClass);
		    response.getWriter().print(jo.toString());
		}
		
		return null;
	}
	
	@RequestMapping(value="/getVendor.do")
	public String getVendor(HttpServletResponse response) throws IOException{
		logger.info("Enter getVendor.do..");
		List<ParamVendor> vendors=prodClassDao.getVendorByProdClass();
		JSONObject jo=new JSONObject();
		jo.put("vendors", vendors);
	    response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value="/getProdTypeByVendorAndClassCode.do")
	public String getProdTypeByVendorAndClassCode(HttpServletResponse response,String vendorId,String prodClassCode) throws IOException{
		logger.info("Enter getProdTypeByVendorAndClassCode.do...");
		List<ProdType> types=prodTypeService.getByVendorAndClassCode(vendorId, prodClassCode);
		JSONObject jo=new JSONObject();
		jo.put("prodType", types);
	    response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value="/getResItem.do")
	public SysResItem getResItem(String groupCode,HttpServletResponse response,String vendorId,String classId) throws IOException{
		logger.info("Enter getResItem.do..");
		
		List<ResItemParam> pItems=new ArrayList<ResItemParam>();
		List<ProdType> prodTypes=prodTypeService.getSelectedProdType(vendorId,classId);
		
		if(!StringUtil.isNullString(groupCode)){
			 SysResGroup resGroup= resItemService.getSysResGroup(groupCode);
			 if(resGroup!=null){
				 List<SysResItem> resItems=resItemService.getResCode(resGroup.getResGrpId());
					
					for(int i=0;i<resItems.size();i++){
						if(!"-1".equals(resItems.get(i).getResCode())&&!"0".equals(resItems.get(i).getResCode())){
							ResItemParam resItem=new ResItemParam();
							resItem.setId(resItems.get(i).getResCode());
							resItem.setText(resItems.get(i).getResName());
							pItems.add(resItem);
						}
						
					}
			 }
		}
		
		for(int i=0;i<prodTypes.size();i++){
			
			for(int j=0;j<pItems.size();j++){
				if(Integer.parseInt(pItems.get(j).getId())==prodTypes.get(i).getTypeCode()){
					pItems.remove(pItems.get(j));
				}
			}
		}
		
		
		JSONObject jo = new JSONObject();
		//JSONArray jsArealist = JSONArray.fromObject(pItems);
		String jsonStr=prodTypeService.getJsonListParamRes(pItems);
		jo.put("pItems", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value="/getResItemByCode.do")
	public String getResItemByCode(String groupCode,HttpServletResponse response) throws IOException{
		logger.info("Enter getResItemByCode.do..");
		
		List<ResItemParam> pItems=new ArrayList<ResItemParam>();
		
		if(!StringUtil.isNullString(groupCode)){
			 SysResGroup resGroup= resItemService.getSysResGroup(groupCode);
			 if(resGroup!=null){
				 List<SysResItem> resItems=resItemService.getResCode(resGroup.getResGrpId());
					
					for(int i=0;i<resItems.size();i++){
						if(!"-1".equals(resItems.get(i).getResCode())&&!"0".equals(resItems.get(i).getResCode())){
							ResItemParam resItem=new ResItemParam();
							resItem.setId(resItems.get(i).getResCode());
							resItem.setText(resItems.get(i).getResName());
							pItems.add(resItem);
						}
						
					}
			 }
		}
		
		JSONObject jo = new JSONObject();
		//JSONArray jsArealist = JSONArray.fromObject(pItems);
		String jsonStr=prodTypeService.getJsonListParamRes(pItems);
		jo.put("pItems", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	
	
	
	
	
	@RequestMapping(value="/getTypeDataBygroupCode.do")
	public String getTypeDataBygroupCode(String groupCode,HttpServletResponse response) throws IOException{
		List<ResItemParam> pItems=new ArrayList<ResItemParam>();
		SysResGroup resGroup=resItemService.getSysResGroup(groupCode);
		 if(resGroup!=null){
			 List<SysResItem> resItems=resItemService.getResCode(resGroup.getResGrpId());
			 
				for(int i=0;i<resItems.size();i++){
					ResItemParam resItem=new ResItemParam();
					resItem.setId(resItems.get(i).getResCode());
					resItem.setText(resItems.get(i).getResName());
					pItems.add(resItem);
				}
		 }
			JSONObject jo = new JSONObject();
			String jsonStr=prodTypeService.getJsonListParamRes(pItems);
			jo.put("types", jsonStr);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
			return null;
	}
	
	@RequestMapping(value="/getTypeByCodeExceptZero.do")
	public String getTypeByCodeExceptZero(String groupCode,HttpServletResponse response) throws IOException{
		List<ResItemParam> pItems=new ArrayList<ResItemParam>();
		SysResGroup resGroup=resItemService.getSysResGroup(groupCode);
		 if(resGroup!=null){
			 List<SysResItem> resItems=resItemService.getResCodeExceptZero(resGroup.getResGrpId());
			 
				for(int i=0;i<resItems.size();i++){
					ResItemParam resItem=new ResItemParam();
					resItem.setId(resItems.get(i).getResCode());
					resItem.setText(resItems.get(i).getResName());
					pItems.add(resItem);
				}
		 }
			JSONObject jo = new JSONObject();
			String jsonStr=prodTypeService.getJsonListParamRes(pItems);
			jo.put("types", jsonStr);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
			return null;
	}
	
	
	@RequestMapping(value="/selectedProductType.do")
	public List<ProdType> selectedProductType(String vendorId,String classId,HttpServletResponse response) throws IOException{
		logger.info(" Enter selectedProductType.do...");
		List<ProdType> prodTypes=prodTypeService.getSelectedProdType(vendorId,classId);
		List<ResItemParam> pItems=new ArrayList<ResItemParam>();
		
		for(int i=0;i<prodTypes.size();i++){
			ResItemParam resItem=new ResItemParam();
			resItem.setId(prodTypes.get(i).getTypeCode().toString());
			resItem.setText(prodTypes.get(i).getDispName());
			pItems.add(resItem);
		}
		JSONObject jo = new JSONObject();
		String jsonStr=prodTypeService.getJsonListParamRes(pItems);
		jo.put("prodType", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
		
	}
	
	@RequestMapping(value="/save.do")
	public String add(ModelMap map,String jsonStr,String vendorId,String classId,String prodClassId,String prodClassName,String desc,String action,String id){
		logger.info("Enter add.do...");
		
		if ("edit".equals(action)){
			ProdType pType = prodTypeService.getByID(Integer.parseInt(id));
			if(null != pType){
				if(!vendorId.equals(pType.getVendorID()) || !prodClassId.equals(pType.getProdClassID())){//判断修改的时候是否选择了别的厂商或者别的产品分类
					this.prodTypeService.delete(Integer.parseInt(id));//如果有就删除这条记录
				}
			}
			
		}
		
		prodTypeService.batchSave(jsonStr,vendorId,classId,prodClassId,prodClassName,desc);
		
		map.put("saveData", "{}");
		map.put("result", "success");
		map.put("pcCode", -1);//给prodClassCode生成下拉框的方法赋值，防止页面saveOK方法失效
		return viewPath+"/edit";
	}
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		String[] ids = id.split(",");
		for(int i=0;i<ids.length;i++){
			if(!StringUtil.isNullString(ids[i]));
			prodTypeService.delete(Integer.valueOf(ids[i]));
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;
	}
	//批量审核
	@RequestMapping(value="/saveAudit.do")
	public String saveAudit(String prodTypeIds, HttpServletResponse response) throws IOException{
				
		List<ProdType> list=new ArrayList<ProdType>();
		if(!prodTypeIds.isEmpty()){
		String [] ids=prodTypeIds.split(",");
			for(String id:ids){
				ProdType prodChkCmds=prodTypeService.getByID(Integer.parseInt(id));
				prodChkCmds.setStatus(1);
				list.add(prodChkCmds);
			}
			prodTypeService.batchSave(list);
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;
	}
	@RequestMapping(value = "/exportExcel.do")
	public String  exportExcel(ModelMap map,HttpServletResponse response)  {
		logger.info( "Enter exportExcel.do ..." );
		List<ProdType> list = prodTypeService.getAll();
		String[] headers = {"设备类型ID", "厂商ID", "产品分类ID","产品分类","产品分类名称","类型编码","类型名称","显示名称","描述"};//导出字段名称
		String attr="prodTypeID,vendorID,prodClassID,prodClassCode,prodClassName,typeCode,typeName,dispName,description";//导出映射字段
		String fileName="产品类型"+DateUtil.getExpTimeShow(new Timestamp(System.currentTimeMillis()))+".xls";//excel文件名

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
		List<ProdType> list = prodTypeService.getAll();
		
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
			if( prodTypeService.importExcel(impFile.getInputStream())){
				map.put("result", "success");
			}
			else{
				map.put("result", "error");
			}			
			map.put("message", prodTypeService.getLastMassage());
		}
		return viewPath+"/import";      
	} 

}
