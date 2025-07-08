/*
 * 文件名： VendorProdModelAction.java
 * 
 * 创建日期： 2014-2-12
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.product.prodModel.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sino.base.common.util.ExpExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodDirectory.entity.ProdDirectory;
import com.sino.cmdb.product.prodDirectory.service.ProdDirectoryService;
import com.sino.cmdb.product.prodModel.entity.VendorProdModel;
import com.sino.cmdb.product.prodModel.service.VendorProdModelService;
import com.sino.cmdb.product.prodType.entity.ResItemParam;


/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-2-12
 */
@Controller
@RequestMapping(value="/cmdb/vendorProdModel")
public class VendorProdModelAction {
	
	 private static Logger logger = LoggerFactory.getLogger(VendorProdModelAction.class);
	 
	 private static String viewPath="/cmdb/vendorProdModel";
	 
	 @Autowired
	 private VendorProdModelService vendorProdModelService;
	 @Autowired
	 private ProdDirectoryService prodDirectoryService;
	 
	 @RequestMapping(value="/getTree.do")
	 public String getTree(ModelMap map){
		 logger.info("Enter getTree.do...");
			List<ParamTree> total = new ArrayList<ParamTree>();
			ParamTree  t=new ParamTree();
			t.setId("1_1");
			t.setText("产品系列");
			t.setPid("0");
			t.setIsexpand(true);
			total.add(t);
			
			List<ResItemParam> classCodes=vendorProdModelService.getVendor();
			if(classCodes != null){
				for(int i=0;i<classCodes.size();i++){//设备分类  2级节点
					ParamTree pc=new ParamTree();
					ResItemParam paramClass=classCodes.get(i);
					pc.setId(paramClass.getId()+"_2");
					pc.setPid("1_1");
					pc.setText(paramClass.getText());
					total.add(pc);
					List<ResItemParam> typeCodes=vendorProdModelService.getByVendor(Integer.parseInt(paramClass.getId()));
					if(!typeCodes.isEmpty()){
						for(int j=0;j<typeCodes.size();j++){//设备类型 3级节点
							ParamTree pt=new ParamTree();
							ResItemParam paramType = typeCodes.get(j);
							pt.setId(paramClass.getId()+"_"+paramType.getId()+"_3");
							pt.setPid(paramClass.getId()+"_2");
							pt.setText(paramType.getText());
							total.add(pt);
							List<ResItemParam> groupNames=vendorProdModelService.getByClassCode(Integer.parseInt(paramClass.getId()),Integer.parseInt(paramType.getId()));
							if(!groupNames.isEmpty()){
								for(int k=0;k<groupNames.size();k++){//指标组名称 4级节点
									ParamTree pg=new ParamTree();
									ResItemParam paramGroup = groupNames.get(k);
									pg.setId(paramClass.getId()+"_"+paramType.getId()+"_"+paramGroup.getId()+"_4");
									pg.setText(paramGroup.getText());
									pg.setPid(paramClass.getId()+"_"+paramType.getId()+"_3");
									total.add(pg);
									List<ResItemParam> seriesNames=vendorProdModelService.getBySeries(Integer.parseInt(paramClass.getId()),Integer.parseInt(paramType.getId()),Integer.parseInt(paramGroup.getId()));
									if(!seriesNames.isEmpty()){
										for (int l = 0; l < seriesNames.size(); l++) {
											ParamTree sg=new ParamTree();
											ResItemParam paramSeries = seriesNames.get(l);
											sg.setId(paramClass.getId()+"_"+paramType.getId()+"_"+paramGroup.getId()+"_"+paramSeries.getId()+"_5");
											sg.setText(paramSeries.getText());
											sg.setPid(paramClass.getId()+"_"+paramType.getId()+"_"+paramGroup.getId()+"_4");
											total.add(sg);
										}
									}
								}
							}
						}
					}
				}
			}
			
			JSONArray json = JSONArray.fromObject(total);
			String treeData = json.toString();
			map.put("treeData", treeData);
			return viewPath+"/prodModelTab";
		}
	 
	 @RequestMapping(value="/main.do")
	 public String main(ModelMap map,String id) {
		 logger.info( "Enter main.do ..." );
			List<VendorProdModel> list = new ArrayList<VendorProdModel>();
			String flag=id.substring(id.lastIndexOf("_")+1, id.length());
			if(flag.equals("1")){
				 list = vendorProdModelService.getAll();
			}else if(flag.equals("2")){
				String [] typeCodes=id.split("_");
				list = vendorProdModelService.getDataByVendorID(Integer.parseInt(typeCodes[0]));
			}else if(flag.equals("3")){
				String [] typeCodes=id.split("_");
				list = vendorProdModelService.getByVendorAndClassCode(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]));
			}else if(flag.equals("4")){
				String [] typeCodes=id.split("_");
				list = vendorProdModelService.getByVendorAndClassCodeAndTypeCode(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]),Integer.parseInt(typeCodes[2]));
			}else if(flag.equals("5")){
				String [] typeCodes=id.split("_");
				list = vendorProdModelService.getByVenAndClassAndTypeAndSeries(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]),Integer.parseInt(typeCodes[2]),typeCodes[3]);
			}
			
			String jsonStr = vendorProdModelService.getJsonListStr(list);
			map.put("jsonListData", jsonStr);
			map.put("id", id);
			return viewPath+"/main";
	 }
	 
	 @RequestMapping(value="/add.do")
	 public String add(ModelMap map,String id){
		 logger.info("Enter add.do..");
		 VendorProdModel prodModel=new VendorProdModel();
		 if(!StringUtils.isNullOrEmpty(id) && !"all".equals(id)){
			 String flag=id.substring(id.lastIndexOf("_")+1, id.length());
			 String [] typeCodes=id.split("_");
			 if(flag.equals("2")){
				 prodModel.setVendorID(Integer.parseInt(typeCodes[0]));
			 }else if(flag.equals("3")){
				 prodModel.setVendorID(Integer.parseInt(typeCodes[0]));
				 prodModel.setProdClassCode(Integer.parseInt(typeCodes[1]));
			 }else if(flag.equals("4")||flag.equals("5")){
				 prodModel.setVendorID(Integer.parseInt(typeCodes[0]));
				 prodModel.setProdClassCode(Integer.parseInt(typeCodes[1]));
				 prodModel.setProdTypeCode(Integer.parseInt(typeCodes[2]));
			 }
		 }
		 map.put("prodModel", prodModel);
		 map.put("action", "add");
		 return viewPath+"/edit";
	 }
	 
	 @RequestMapping(value="/edit.do")
	 public String edit(String id,ModelMap map){
		 logger.info("Enter edit.do...");
		 VendorProdModel prodModel=vendorProdModelService.getById(id);
		 map.put("action", "edit");
		 map.put("id", id);
		 map.put("prodModel", prodModel);
		 return viewPath+"/edit";
	 }
	 
	 @RequestMapping(value="/delete.do")
	 public String delete(String id,HttpServletResponse response) throws IOException{
		 logger.info("Enter delete.do...");
		 logger.info("Enter delete.do..");
			if(!id.isEmpty()){
				String [] ids=id.split(",");
				vendorProdModelService.deletes(ids);
			}
			JSONObject jo = new JSONObject();
			jo.put("success", "0");
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		    return null; 
	 }
	 
	//批量审核
	@RequestMapping(value="/saveAudit.do")
	public String saveAudit(String prodModelIds, HttpServletResponse response) throws IOException{
				
		List<VendorProdModel> list=new ArrayList<VendorProdModel>();
		if(!prodModelIds.isEmpty()){
			String [] ids=prodModelIds.split(",");
			for(String id:ids){
				VendorProdModel prodChkCmds=vendorProdModelService.getById(id);
				prodChkCmds.setStatus(1);
				list.add(prodChkCmds);
			}
			vendorProdModelService.batchSave(list);
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}	 
	
	 
	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest,
			HttpServletResponse response) throws IOException {
		logger.info("Enter search.do ...");
		// List<PropertyFilter> filters =
		// PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<VendorProdModel> list = vendorProdModelService.getAll();
		String jsonStr = vendorProdModelService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
		return null;
	}
	
	@RequestMapping(value="/checkProdModel.do")
	public String checkProdModel(String vendorId,String prodModel,HttpServletResponse response) throws IOException{
		logger.info("Enter checkProdModel.do...");
		List<VendorProdModel> models=vendorProdModelService.getByVendorAndModelName(vendorId,prodModel);
		JSONObject jo=new JSONObject();
		jo.put("models", models);
	    response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value="/save.do")
	public String save(ModelMap map,String id,String action,VendorProdModel prodModel,HttpServletResponse response){
		logger.info("Enter save.do..");
		map.put("action", action);
		boolean validate = true;
		if(validate){
			
//			//判断要添加或修改的OID是否符合规范：.1.3.6.1.4.1. + 数字
//			if(!prodModel.getModelOID().matches("^([.]1[.]3[.]6[.]1[.]4[.]1[.])([0-9]+)$")){
//				map.put("prodModel", prodModel);
//				map.put("result", "error");								
//				map.put("message", "OID不符合规范，请重新添加！");
//				return viewPath+"/edit";
//			}
			
			VendorProdModel saveProdModel=null;
			if("add".equals(action)){
				saveProdModel=prodModel;
				saveProdModel.setStatus(0);
				vendorProdModelService.add(saveProdModel);
				String saveData = vendorProdModelService.getJsonObjStr(saveProdModel);
				map.put("saveData", saveData);
			}else{
				saveProdModel=prodModel;
				if(vendorProdModelService.checkCRC(Long.parseLong(id), prodModel.getProdModelOid())){//更改OID
					saveProdModel.setProdModelID(vendorProdModelService.toCRC(prodModel.getProdModelOid()));
					vendorProdModelService.delete(id);
				}else{//没有更改OID
					saveProdModel.setProdModelID(Long.parseLong(id));
				}
				vendorProdModelService.save(saveProdModel);
				String saveData = vendorProdModelService.getJsonObjStr(saveProdModel);
				map.put("saveData", saveData);
			}
			map.put("result", "success");
		}else{
			map.put("result", "error");	
		}
		map.put("prodModel", prodModel);
		return viewPath+"/edit";
	}
	@RequestMapping(value = "/getModelOIDByVendorID.do")
	public String getModelOIDByVendorID(String id,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getModelOIDByVendorID.do ..." );
		List<VendorProdModel> list = vendorProdModelService.getDataByVendorID(Integer.valueOf(id));
		JSONObject jo = new JSONObject();
		JSONArray modelOIDlist = JSONArray.fromObject(list);
		jo.put("modelOIDlist", modelOIDlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}	
	
	
	@RequestMapping(value = "/getModelOIDByVendorIDAndDevTypeCode.do")
	public String getModelOIDByVendorIDAndDevTypeCode(String id,String devTypeCode,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getModelOIDByVendorIDAndDevTypeCode.do ..." );
		List<VendorProdModel> list = vendorProdModelService.getDataByVendorIDAndDevTypeCode(Integer.valueOf(id),Integer.valueOf(devTypeCode));
		JSONObject jo = new JSONObject();
		JSONArray modelOIDlist = JSONArray.fromObject(list);
		jo.put("modelOIDlist", modelOIDlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}	
	
	
	@RequestMapping(value="/getData.do")
	public String getData(String vendorId,String prodClassMode,String prodTypeId,HttpServletResponse response) throws IOException{
		List<VendorProdModel> list = null;
		if(prodTypeId!=null&&prodTypeId!=""&&!prodTypeId.equals("undefined")){
			list = vendorProdModelService.getData(Integer.parseInt(vendorId), Integer.parseInt(prodClassMode), Integer.parseInt(prodTypeId));
		}
		JSONObject jo = new JSONObject();
		JSONArray modelOIDlist = JSONArray.fromObject(list);
		jo.put("modelOIDlist", modelOIDlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	
	
	@RequestMapping(value="/getModelData.do")
	public String getModelData(String vendorId,String prodClassMode,String prodTypeId,String prodSeries,HttpServletResponse response) throws IOException{
		
		List<VendorProdModel> list = vendorProdModelService.getModelData(Integer.parseInt(vendorId), Integer.parseInt(prodClassMode), Integer.parseInt(prodTypeId),prodSeries);
		JSONObject jo = new JSONObject();
		JSONArray modelOIDlist = JSONArray.fromObject(list);
		jo.put("modelOIDlist", modelOIDlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	
	
	@RequestMapping(value="/getDataByVendorID.do")
	public String getDataByVendorID(String vendorId,HttpServletResponse response) throws IOException{
		
		List<VendorProdModel> list = vendorProdModelService.getDataByVendorID(Integer.parseInt(vendorId));
		JSONObject jo = new JSONObject();
		JSONArray modelOIDlist = JSONArray.fromObject(list);
		jo.put("modelOIDlist", modelOIDlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	
	
	
	@RequestMapping(value = "/multiEdit.do")
	public String multiEdit(String ids, ModelMap map){
		logger.info( "Enter multiEdit.do ..." );
		map.put("ids", ids);
		if (-1 != ids.indexOf(",")){
			String id=ids.substring(0, ids.indexOf(","));
			VendorProdModel prodModel=vendorProdModelService.getById(id);
			map.put("prodModel", prodModel);
			map.put("vid", prodModel.getVendorID());
		}else{
			VendorProdModel prodModel=vendorProdModelService.getById(ids);
			map.put("prodModel", prodModel);
			map.put("vid", prodModel.getVendorID());
		}
		
		return viewPath+"/multiEdit";
	}
	
	@RequestMapping(value = "/multiSave.do")
	public String multiSave(String ids,String checkClassCode,String checkTypeID,String checkSeriesID,VendorProdModel prodModel,String vid, ModelMap map) {
		
		logger.info( "Enter multiSave.do ..." );
		
		String [] multiIds=ids.split(",");
		List<VendorProdModel> listProdModel = new ArrayList<VendorProdModel>();
		for(int i=0;i<multiIds.length;i++){
			VendorProdModel pModel = new VendorProdModel();
			VendorProdModel subVendorProdModel = vendorProdModelService.getById(multiIds[i]);
			pModel = subVendorProdModel;
			if(!StringUtil.isNullString(checkClassCode)){ //勾选产品分类checkBox
				pModel.setProdClassCode(prodModel.getProdClassCode());
				pModel.setProdClassName(prodModel.getProdClassName());
			}
			if(!StringUtil.isNullString(checkTypeID)){ //勾选产品类型checkBox
				pModel.setProdTypeID(prodModel.getProdTypeID());
				pModel.setProdTypeCode(prodModel.getProdTypeCode());
				pModel.setProdTypeCode(prodModel.getProdTypeCode());
			}
			if(!StringUtil.isNullString(checkSeriesID)){ //勾选产品系列checkBox
				pModel.setProdSeries(prodModel.getProdSeries());
			}
			vendorProdModelService.save(pModel);
			listProdModel.add(pModel);
		}
		JSONArray jsonStr= JSONArray.fromObject(listProdModel);
		map.put("saveData", jsonStr.toString());
		map.put("result", "success");
		map.put("action", "multiSave");
		map.put("vid", vid);
		return viewPath+"/multiEdit";
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
			if( vendorProdModelService.importExcel(impFile.getInputStream())){
				map.put("result", "success");
			}
			else{
				map.put("result", "error");
			}			
			map.put("message", vendorProdModelService.getLastMassage());
		}
		return viewPath+"/import";      
	} 
	
	@RequestMapping(value = "/getMinData.do")
	public String getMinData(String vendorId,String devTypeId, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getMinData.do ..." );
		List<VendorProdModel> list = null;
		if(StringUtil.isNullString(devTypeId))
			devTypeId="-1";
		if( StringUtil.isNullString(vendorId)){
			list = vendorProdModelService.getAllOrderName();
		}
		else{
			list = vendorProdModelService.getVendorDevModel(vendorId,devTypeId);
		}
		String jsonStr = vendorProdModelService.getJsonMinListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}              

	
	@RequestMapping(value = "/exportExcel.do")
	public String exportExcel(ModelMap map,HttpServletResponse response){
		logger.info("enter exportExcel....");
		
		List<VendorProdModel> list=vendorProdModelService.getList();
		String[] headers = {"主键", "厂商ID", "产品分类编码","产品分类名称","产品类型ID","型号编码","产品类型名称","产品系列","产品型号","型号OID","型号描述","状态"};//导出字段名称
		String attr="prodModelID,vendorID,prodClassCode,prodClassName,prodTypeID,prodTypeCode,prodTypeName,prodSeries,prodModelName,prodModelOid,description,status";//导出映射字段
		String fileName="Cmdb_VendorProdModel_List"+".xls";//excel文件名

		try {
	          response.setContentType("octets/stream");
	          response.addHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gb2312"), "UTF-8"));
	          OutputStream out = response.getOutputStream();  
	          ExpExcelUtil.expExcel("产品型号信息", headers, list, attr, out);

	          out.close();
	      } catch (Exception e) {
	    	  System.out.println("导出excel出错。。。");
	          e.printStackTrace();  
	      }
	    return null;
	}
	
	
}
