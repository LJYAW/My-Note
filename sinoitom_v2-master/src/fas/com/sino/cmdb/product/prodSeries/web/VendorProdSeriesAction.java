/*
 * 文件名： VendorProdSeriesAction.java
 * 
 * 创建日期： 2014-2-12
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.product.prodSeries.web;

import com.sino.base.common.util.DateUtil;
import com.sino.base.common.util.ExpExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodSeries.entity.VendorProdSeries;
import com.sino.cmdb.product.prodSeries.service.VendorProdSeriesService;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

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
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-2-12
 */
@Controller
@RequestMapping(value="/cmdb/vendorProdSeries")
public class VendorProdSeriesAction {
	
	private static Logger logger = LoggerFactory.getLogger(VendorProdSeriesAction.class);
	
	private static String viewPath="/cmdb/vendorProdSeries";
	
	@Autowired
	private VendorProdSeriesService vendorProdSeriesService;
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("Enter getTree.do...");
		List<ParamTree> total = new ArrayList<ParamTree>();
		ParamTree  t=new ParamTree();
		t.setId("1_1");
		t.setText("产品类型");
		t.setPid("0");
		t.setIsexpand(true);
		total.add(t);
		
		List<ResItemParam> vendors=vendorProdSeriesService.getTreeByVendor();
		if(vendors != null){
			for(int i=0;i<vendors.size();i++){//设备分类  2级节点
				ParamTree pc=new ParamTree();
				ResItemParam paramClass=vendors.get(i);
				pc.setId(paramClass.getId()+"_2");
				pc.setPid("1_1");
				pc.setText(paramClass.getText());
				total.add(pc);
				List<ResItemParam> classCodes=vendorProdSeriesService.getTreeByVendorAndClass(Integer.parseInt(paramClass.getId()));
				if(!classCodes.isEmpty()){
					for(int j=0;j<classCodes.size();j++){//设备类型 3级节点
						ParamTree pt=new ParamTree();
						ResItemParam paramType = classCodes.get(j);
						pt.setId(paramClass.getId()+"_"+paramType.getId()+"_3");
						pt.setPid(paramClass.getId()+"_2");
						pt.setText(paramType.getText());
						total.add(pt);
						List<ResItemParam> groupNames=vendorProdSeriesService.getTreeByClassCode(Integer.parseInt(paramClass.getId()),Integer.parseInt(paramType.getId()));
						if(!groupNames.isEmpty()){
							for(int k=0;k<groupNames.size();k++){//指标组名称 4级节点
								ParamTree pg=new ParamTree();
								ResItemParam paramGroup = groupNames.get(k);
								pg.setId(paramClass.getId()+"_"+paramType.getId()+"_"+paramGroup.getId()+"_4");
								pg.setText(paramGroup.getText());
								pg.setPid(paramClass.getId()+"_"+paramType.getId()+"_3");
								total.add(pg);
							}
						}
					}
				}
			}
		}
		
		JSONArray json = JSONArray.fromObject(total);
		String treeData = json.toString();
		map.put("treeData", treeData);
		return viewPath+"/prodSeriesTab";
	}
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map,String id) {
		logger.info( "Enter main.do ..." );
		List<VendorProdSeries> list = new ArrayList<VendorProdSeries>();
		String flag=id.substring(id.lastIndexOf("_")+1, id.length());
		if(flag.equals("1")){
			 list = vendorProdSeriesService.getAll();
		}else if(flag.equals("2")){
			String [] typeCodes=id.split("_");
			list = vendorProdSeriesService.getSeries(typeCodes[0]);
		}else if(flag.equals("3")){
			String [] typeCodes=id.split("_");
			list = vendorProdSeriesService.getByVendorAndClassCode(typeCodes[0],typeCodes[1]);
		}else if(flag.equals("4")){
			String [] typeCodes=id.split("_");
			list = vendorProdSeriesService.getByProdTypeID(typeCodes[0]+typeCodes[1]+typeCodes[2]);
		}
		
		String jsonStr = vendorProdSeriesService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		map.put("id", id);
		return viewPath+"/main";
	}
	
	@RequestMapping(value="/add.do")
	public String add(ModelMap map,String id){
		VendorProdSeries series=new VendorProdSeries();
		if(!StringUtils.isNullOrEmpty(id) && !"all".equals(id)){	
			String flag=id.substring(id.lastIndexOf("_")+1, id.length());
			String [] typeCodes=id.split("_");
			if(flag.equals("2")){
				series.setVendorID(Integer.parseInt(typeCodes[0]));
			}else if(flag.equals("3")){
				series.setVendorID(Integer.parseInt(typeCodes[0]));
				series.setProdClassCode(Integer.parseInt(typeCodes[1]));
			}
			else if(flag.equals("4")){
				series.setVendorID(Integer.parseInt(typeCodes[0]));
				series.setProdClassCode(Integer.parseInt(typeCodes[1]));
				series.setProdTypeID(Integer.parseInt(typeCodes[2]));
			}
			
		}
		map.put("series", series);
		map.put("action", "add");
		return viewPath+"/edit";
	}
	
	@RequestMapping(value="/edit.do")
	public String edit(String id,ModelMap map){
		logger.debug("Enter edit.do...");
		VendorProdSeries series=vendorProdSeriesService.getById(id);
		map.put("action", "edit");
		map.put("series", series);
		map.put("id", id);
		return viewPath+"/edit";
		
	}
	
	@RequestMapping(value="/delete.do")
	public void deletes(String id,HttpServletResponse response) throws IOException{
		logger.info("Enter delete.do...");
		String [] ids=id.split(",");
		for(int i=0;i<ids.length;i++){
			vendorProdSeriesService.delete(ids[i]);
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	}
	//批量审核
	@RequestMapping(value="/saveAudit.do")
	public String saveAudit(String prodSeriesIds, HttpServletResponse response) throws IOException{
				
		List<VendorProdSeries> list=new ArrayList<VendorProdSeries>();
		if(!prodSeriesIds.isEmpty()){
			String [] ids=prodSeriesIds.split(",");
			for(String id:ids){
				VendorProdSeries prodChkCmds=vendorProdSeriesService.getById(id);
				prodChkCmds.setStatus(1);
				list.add(prodChkCmds);
			}
			vendorProdSeriesService.batchSave(list);
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		//List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<VendorProdSeries> list = vendorProdSeriesService.getAll();
		String jsonStr = vendorProdSeriesService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}
	
	@RequestMapping(value="/getData.do")
	public List<VendorProdSeries> getData(String vendorId,String prodClassMode,String prodTypeId,HttpServletResponse response) throws IOException{
		logger.info("Enter getData.do...");
		List<VendorProdSeries> seriesList=vendorProdSeriesService.getList(vendorId, prodClassMode,prodTypeId);
		JSONObject jo=new JSONObject();
		jo.put("seriesList", seriesList);
	    response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value="/checkProdSeries.do")
	public String checkProdSeries(String vendorId,String seriesName,HttpServletResponse response) throws IOException{
		logger.info("Enter checkProdSeries.do...");
		List<VendorProdSeries> serieslist=vendorProdSeriesService.getByVendorAndSerName(vendorId,seriesName);
		JSONObject jo=new JSONObject();
		jo.put("serieslist", serieslist);
	    response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value="/save.do")
	public String save(String id,String action,ModelMap map,VendorProdSeries series,HttpServletResponse response){
		logger.info("Enter save.do..");
		map.put("action", action);
		boolean validate = true;
		if(validate){
			VendorProdSeries saveSeries=null;
			if("add".equals(action)){
				saveSeries=series;
				saveSeries.setStatus(0);
				
			}else{
				VendorProdSeries editSeries=vendorProdSeriesService.getById(id);
				if( editSeries == null ){
					map.put("series", editSeries);
					map.put("result", "error");								
					map.put("message", "该portlet已经被删除！");
					return viewPath+"/edit";
				}	
				BeanUtils.copyProperties(series, editSeries);
				editSeries.setProdSeriesID(Integer.parseInt(id));
				saveSeries=editSeries;
			}
			vendorProdSeriesService.save(saveSeries);
			String saveData = vendorProdSeriesService.getJsonObjStr(saveSeries);
			map.put("saveData", saveData);
			map.put("result", "success");
		}else{
			map.put("result", "error");	
		}
		map.put("series", series);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/getExportExcelSize.do")
	public void  getExportExcelSize(ModelMap map,HttpServletResponse response,String treeId) throws IOException {
		logger.info( "Enter VendorProdSeriesAction exportExcel.do ..." );
		List<VendorProdSeries> list = new ArrayList<VendorProdSeries>();
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(treeId)){
			String[] trees = treeId.split("_");
			String [] typeCodes = treeId.split("_");
			int treeTier = trees.length;

			switch (treeTier){
				case 1:
					list = vendorProdSeriesService.getAll();
					break;
				case 2:
					list = vendorProdSeriesService.getSeries(typeCodes[0]);
					break;
				case 3:
					list = vendorProdSeriesService.getByVendorAndClassCode(typeCodes[0],typeCodes[1]);
					break;
				case 4:
					list = vendorProdSeriesService.getByProdTypeID(typeCodes[0]+typeCodes[1]+typeCodes[2]);
					break;
				default:
					break;
			}

		}
		response.getWriter().print(list.size());
	}

	@RequestMapping(value = "/exportExcel.do")
	public String  exportExcel(ModelMap map,HttpServletResponse response,String ids,String treeId)  {
		logger.info( "Enter exportExcel.do ..." );
		List<VendorProdSeries> list = new ArrayList<VendorProdSeries>();
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(ids)){
			list = vendorProdSeriesService.getByIds(ids);
		}else if(org.apache.commons.lang3.StringUtils.isNotEmpty(treeId)){
			String[] trees = treeId.split("_");
			String [] typeCodes = treeId.split("_");
			int treeTier = trees.length;

			switch (treeTier){
				case 1:
					list = vendorProdSeriesService.getAll();
					break;
				case 2:
					list = vendorProdSeriesService.getSeries(typeCodes[0]);
					break;
				case 3:
					list = vendorProdSeriesService.getByVendorAndClassCode(typeCodes[0],typeCodes[1]);
					break;
				case 4:
					list = vendorProdSeriesService.getByProdTypeID(typeCodes[0]+typeCodes[1]+typeCodes[2]);
					break;
				default:
					break;
			}
		}

		String[] headers = {"产品系列ID", "厂商ID","产品分类","产品类型ID","产品型号","型号描述"};//导出字段名称
		String attr="prodSeriesID,vendorID,prodClassCode,prodTypeID,prodSeries,description";//导出映射字段
		String fileName="产品系列"+DateUtil.getExpTimeShow(new Timestamp(System.currentTimeMillis()))+".xls";//excel文件名
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
			if( vendorProdSeriesService.importExcel(impFile.getInputStream())){
				map.put("result", "success");
			}
			else{
				map.put("result", "error");
			}			
			map.put("message", vendorProdSeriesService.getLastMassage());
		}
		return viewPath+"/import";      
	} 

}
