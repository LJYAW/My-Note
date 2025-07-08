/*
 * 文件名： IndItemsAction.java
 * 
 * 创建日期： 2014-7-26
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.indicator.items.web;

import com.sino.base.common.BaseAction;
import com.sino.base.common.util.DateUtil;
import com.sino.base.common.util.ExpExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.indicator.group.entity.IndicatorGroup;
import com.sino.cmdb.indicator.items.entity.IndicatorFields;
import com.sino.cmdb.indicator.items.entity.IndicatorItems;
import com.sino.cmdb.indicator.items.service.IndItemsService;
import com.sino.cmdb.indicator.items.service.IndicatorFieldsService;
import com.sino.cmdb.indicator.report.tmplcell.entity.RptTmplCell;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-7-26
 */
@Controller
@RequestMapping(value = "/cmdb/indicatorItems")
public class IndItemsAction extends BaseAction{
	
	private String viewPath = "/cmdb/indicator/items";
	private static Logger logger = LoggerFactory.getLogger(IndItemsAction.class);

	@Autowired
	private IndItemsService indItemsService;
	
	@Autowired
	private ResItemService resItemService;
	
	@Autowired
	private IndicatorFieldsService indicatorFieldsService;
	
	
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
		
		List<ResItemParam> classCodes=indItemsService.getAllClassCode();
		if(classCodes != null){
			for(int i=0;i<classCodes.size();i++){//设备分类  2级节点
				ParamTree pc=new ParamTree();
				ResItemParam paramClass=classCodes.get(i);
				pc.setId(paramClass.getId()+"_2");
				pc.setPid("1_1");
				pc.setText(paramClass.getText());
				total.add(pc);
				List<ResItemParam> typeCodes=indItemsService.getTreeByClassCode(Integer.parseInt(paramClass.getId()));
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
		return viewPath + "/itemTab";
		
	}
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map,String id) {
		logger.info( "Enter main.do ..." );
		List<IndicatorItems> list = new ArrayList<IndicatorItems>();
		String flag=id.substring(id.lastIndexOf("_")+1, id.length());
		if(flag.equals("1")){
			 list = indItemsService.getIndicator();
		}else if(flag.equals("2")){
			String [] typeCodes=id.split("_");
			list = indItemsService.getByClassCode(Integer.parseInt(typeCodes[0]));
		}else if(flag.equals("3")){
			String [] typeCodes=id.split("_");
			list = indItemsService.getByIndClassCodeAndGroupID(Integer.parseInt(typeCodes[0]),Long.parseLong(typeCodes[1]));
		}
		
		String jsonStr = indItemsService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		return viewPath+"/itemslist";
	}  
	
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		IndicatorItems items = new IndicatorItems();
		map.put("items", items);
		return viewPath+"/add";
	}
	
	@RequestMapping(value = "/getData.do")
	public String getData(Integer fieldQty,HttpServletResponse response) throws IOException{
		List<IndicatorFields> fieldslist=new ArrayList<IndicatorFields>();
		String jsonStr;
		
        for (int i = 0; i < fieldQty; i++) {
        	IndicatorFields fields = new IndicatorFields();
        	fields.setFieldNameEn("EN"+i);
        	fields.setFieldName("NAME"+i);
        	fields.setValueType("String");
        	fields.setLength(32);
        	fields.setDecimals(0);
        	fields.setMeasureUnit("");
        	
        	fieldslist.add(fields);
		}
		
		jsonStr=indicatorFieldsService.getJsons(fieldslist);
		response.getWriter().print(jsonStr);
		return null;
	}
	
	
	@RequestMapping(value="/getDataById.do")
	public String getDataById(HttpServletResponse response,String idnitemId) throws IOException{
		logger.info("Enter getDataById.do...");
		IndicatorItems indItem=indItemsService.getById(Integer.parseInt(idnitemId));
		JSONObject jo=new JSONObject();
		jo.put("indItem", indItem);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addSave.do")
	public String addSave(String action, String id,String rowData, IndicatorItems indicatorItems, ModelMap map) {
		logger.info( "Enter addSave.do ..." );
		
		map.put("action", action);
		boolean validate = true;
		if( validate ){
			String saveData ="";
			if( "add".equals(action)){
				if(StringUtils.isNotBlank(rowData)){
					
					@SuppressWarnings("unused")
					List<IndicatorFields> fieldslist=JsonUtils.getDTOList(rowData, IndicatorFields.class);
					
					IndicatorItems saveIndicator = indicatorItems;
					String  resName=resItemService.findResContent("Cmdb_IndicatorClass", String.valueOf(indicatorItems.getIndClassCode()));
					saveIndicator.setStatus(0);
					saveIndicator.setIndClassName(resName);
					
					String name = "";
					for (int i = 0; i < fieldslist.size(); i++) {
						name += fieldslist.get(i).getFieldNameEn() + ",";
					}
					int index = name.lastIndexOf(",");
					name = name.substring(0, index);
					saveIndicator.setFieldNames(name);
					
					indItemsService.saveItemAndFields(saveIndicator,fieldslist);
				}else{
					IndicatorItems saveIndicator = indicatorItems;
					String  resName=resItemService.findResContent("Cmdb_IndicatorClass", String.valueOf(indicatorItems.getIndClassCode()));
					saveIndicator.setStatus(0);
					saveIndicator.setIndClassName(resName);
					indItemsService.save(saveIndicator);
				}
				saveData = indItemsService.getJsonObjStr(indicatorItems);
				
			}
			
			map.put("saveData", saveData);
			map.put("result", "success");
		}else{
			map.put("result", "error");			
		}
		return viewPath+"/add";
	}
	
	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		IndicatorItems items = indItemsService.getItems(id);
		if (null == items){
			items = new IndicatorItems();
		}
		
		List<IndicatorFields> fieldsList = indicatorFieldsService.getListFieldsByIndItemID(Long.parseLong(id));
		String jsonStr = indicatorFieldsService.getJsonListStr(fieldsList);
		map.put("jsonListData", jsonStr);
		
		map.put("items", items);
		map.put("id", id);
		return viewPath+"/edit";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editSave.do")
	public String editSave(String action, String id,String type,String rowData, IndicatorItems indicatorItems, ModelMap map) {
		logger.info( "Enter editSave.do ..." );
		System.out.println(id);
		map.put("action", action);
		boolean validate = true;
		if( validate ){
			String saveData ="";
			List<IndicatorFields> fieldslist = new ArrayList<IndicatorFields>();
			IndicatorItems editIndicator =null;
			if("Array2D".equals(type)){
				if(StringUtils.isNotBlank(rowData)){
//					List<IndicatorFields> list = indicatorFieldsService.getListFieldsByIndItemID(Long.parseLong(id));
//					indicatorFieldsService.batchDelete(list);
					
					fieldslist=JsonUtils.getDTOList(rowData, IndicatorFields.class);
				}
				
				editIndicator = indicatorItems;
				String  resName=resItemService.findResContent("Cmdb_IndicatorClass", String.valueOf(indicatorItems.getIndClassCode()));
				editIndicator.setIndClassName(resName);
				editIndicator.setIndItemID(Long.parseLong(id));	
				editIndicator.setValueType(type);
				editIndicator.setStatus(0);
				
				String name = "";
				for (int i = 0; i < fieldslist.size(); i++) {
					name += fieldslist.get(i).getFieldNameEn() + ",";
				}
				int index = name.lastIndexOf(",");
				name = name.substring(0, index);
				editIndicator.setFieldNames(name);
				
				//indItemsService.save(editIndicator);
//				List<IndicatorFields> Fields = indicatorFieldsService.getListFieldsByIndItemID(Long.parseLong(id));
//				if(!(Fields.size()>0)){
					indItemsService.saveItemAndFields(editIndicator,fieldslist);
//				}
				
				
			}else{
				editIndicator = indicatorItems;
				String  resName=resItemService.findResContent("Cmdb_IndicatorClass", String.valueOf(indicatorItems.getIndClassCode()));
				editIndicator.setIndClassName(resName);
				editIndicator.setStatus(0);
				editIndicator.setIndItemID(Long.parseLong(id));			
				indItemsService.save(editIndicator);				
			}
			saveData = indItemsService.getJsonObjStr(indicatorItems);
				
			
			map.put("saveData", saveData);
			map.put("result", "success");
		}else{
			map.put("result", "error");			
		}
		return viewPath+"/edit";
	}
	
	
	@RequestMapping(value = "/getEditData.do")
	public String getEditData(String itemId,HttpServletResponse response) throws IOException{
		
		List<IndicatorFields> fieldsList = indicatorFieldsService.getListFieldsByIndItemID(Long.parseLong(itemId));
		//String jsonStr = indicatorFieldsService.getJsonListStr(fieldsList);
		
		String jsonStr=indicatorFieldsService.getJsons(fieldsList);
		response.getWriter().print(jsonStr);
		return null;
	}
	
	@RequestMapping(value = "/view.do")
	public String view(String id, ModelMap map) {
		logger.info( "Enter view.do ..." );
		map.put("action", "view");
		IndicatorItems items = indItemsService.getItems(id);
		if (null == items){
			items = new IndicatorItems();
		}
		
		List<IndicatorFields> fieldsList = indicatorFieldsService.getListFieldsByIndItemID(Long.parseLong(id));
		String jsonStr = indicatorFieldsService.getJsonListStr(fieldsList);
		map.put("jsonListData", jsonStr);
		map.put("items", items);
		map.put("id", id);
		
		return viewPath+"/view";
	}
	
//	@RequestMapping(value="/delete.do")
//	public String delete(String indItemIds,ModelMap map, HttpServletResponse response) throws IOException{
//		logger.info("Enter delete.do..");
//		String [] ids=indItemIds.split(",");
//		indItemsService.deletes(ids);
//		JSONObject jo = new JSONObject();
//		jo.put("success", "0");
//		response.setContentType("text/json");
//		response.setCharacterEncoding("UTF-8");
//		response.getWriter().print(jo.toString());
//	    return null; 
//	}
	
	@RequestMapping(value="/delete.do")
	public String delete(String id,ModelMap map, HttpServletResponse response) throws IOException{
		logger.info("Enter delete.do..");
		JSONObject jo = null;
		
		
		if(!id.isEmpty()){
			List<IndicatorFields> list = indicatorFieldsService.getListFieldsByIndItemID(Long.parseLong(id));
			indItemsService.batchDelete(Long.parseLong(id), list);
			jo = new JSONObject();
			jo.put("success", "0");
		}
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	
	//批量审核
		@RequestMapping(value="/saveAudit.do")
		public String saveAudit(String itemsIds, HttpServletResponse response) throws IOException{
					
			List<IndicatorItems> list=new ArrayList<IndicatorItems>();
			if(!itemsIds.isEmpty()){
				String [] ids=itemsIds.split(",");
				for(String id:ids){
					IndicatorItems prodChkCmds=indItemsService.getById(Long.parseLong(id));
					prodChkCmds.setStatus(1);
					list.add(prodChkCmds);
				}
				indItemsService.batchSave(list);
			}
			JSONObject jo = new JSONObject();
			jo.put("success", "0");
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		    return null;
		}
	@RequestMapping(value="/getIndItems.do")
	public String getIndItems(String indGroupID,String indClassCode,HttpServletResponse response) throws IOException{
		logger.info("Enter getIndItems.do");
		List<IndicatorItems> indItemList=indItemsService.getByIndClassCodeAndGroupID(Integer.parseInt(indClassCode),Long.parseLong(indGroupID));
		JSONObject jo = new JSONObject();
		JSONArray indItems = JSONArray.fromObject(indItemList);
		jo.put("indItems", indItems);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	
	@RequestMapping(value="/getIndItemName.do")
	public String getIndItemName(String indClassCode,String indGroupID,HttpServletResponse response) throws IOException{
		logger.info("Enter getIndItemName.do");
		List<IndicatorItems> indicatorList=indItemsService.getByIndItemName(Integer.parseInt(indClassCode),Long.parseLong(indGroupID));
		JSONObject jo = new JSONObject();
		JSONArray indicator = JSONArray.fromObject(indicatorList);
		jo.put("indicator", indicator);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	
	@RequestMapping(value="/getIndicatorItem.do")
	public String getIndicatorItem(String indItemID,HttpServletResponse response) throws IOException{
		logger.info("Enter getIndicatorItem.do");
		IndicatorItems indicator=indItemsService.getById(Long.parseLong(indItemID));
		JSONObject jo = new JSONObject();
		String str = indItemsService.getJsonObjStr(indicator);
		jo.put("indicator", str);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	@RequestMapping(value="/getIndClassCode.do")
	public String getIndClassCode(HttpServletResponse response) throws IOException{
		List<IndicatorItems> indicatorList=indItemsService.getIndClassCode();
		JSONObject jo = new JSONObject();
		JSONArray indicator = JSONArray.fromObject(indicatorList);
		jo.put("indicator", indicator);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	@RequestMapping(value = "/getExportExcelSize.do")
	public void  getExportExcelSize(ModelMap map,HttpServletResponse response) throws IOException {
		logger.info( "Enter exportExcel.do ..." );
		List<IndicatorItems> list = indItemsService.getAllItems();
        response.getWriter().print(list.size());
	}
	@RequestMapping(value = "/exportExcel.do")
	public String  exportExcel(ModelMap map,HttpServletResponse response)  {
		logger.info( "Enter exportExcel.do ..." );
		List<IndicatorItems> list = indItemsService.getAllItems();
		String[] headers = {"指标项ID", "指标分类编码", "指标分类名称","指标组ID","指标组名称","指标项名次","指标项显示名称","取值类型","长度","小数位数","度量单位","备注"};//导出字段名称
		String attr="indItemID,indClassCode,indClassName,indGroupID,indGroupName,indicatorItem,indItemName,valueType,length,decimals,measureUnit,remark";//导出映射字段
		String fileName="指标项_"+DateUtil.getExpTimeShow(new Timestamp(System.currentTimeMillis()))+".xls";//excel文件名

		try {
	          response.setContentType("octets/stream");
	          response.addHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gb2312"), "UTF-8"));
	          OutputStream out = response.getOutputStream();  
	          ExpExcelUtil.expExcel("指标项", headers, list, attr, out);
	          out.close();
	      } catch (Exception e) {
	    	  System.out.println("导出excel出错。。。");
	          e.printStackTrace();  
	      }
		
		return null;
	}
	@RequestMapping(value = "/importGo.do")
	public String importGo(ModelMap map) throws IOException{
		logger.info( "Enter importGo.do ..." );
		
		return viewPath+"/import";
		
	}
	@RequestMapping(value = "/importExcel.do")
	public String importExcel(MultipartFile impFile, ModelMap map) throws IOException, SQLException{     
		logger.info( "Enter importExcel.do ..." );
		if( impFile.getSize()<=0 ){
			map.put("result", "error");
			map.put("message", "上传文件内容为空！");
		}
		else{
			if( indItemsService.importExcel(impFile.getInputStream())){
				map.put("result", "success");
			}
			else{
				map.put("result", "error");
			}			
			map.put("message", indItemsService.getLastMassage());
		}
		return viewPath+"/import";      
	}

}
