/*
 * 文件名： DevIndicatorItemsAction.java
 * 
 * 创建日期： 2014-8-28
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.indicator.device.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import smartlink.controller.NpmConfiguration;

import com.sino.ack.resources.entity.AckResources;
import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.DateUtil;
import com.sino.base.common.util.ExpExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.indicator.cmdCheckItems.entity.CmdbProdCmdChkItems;
import com.sino.cmdb.indicator.cmdCheckItems.service.AckDevCheckItemService;
import com.sino.cmdb.indicator.cmdCheckItems.service.CmdbProdCmdCheckItemsService;
import com.sino.cmdb.indicator.device.entity.DevIndicatorItems;
import com.sino.cmdb.indicator.device.service.DevIndicatorItemsService;
import com.sino.cmdb.indicator.items.dao.IndItemsDao;
import com.sino.cmdb.indicator.items.entity.IndicatorItems;
import com.sino.cmdb.indicator.items.service.IndItemsService;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.entity.CmdbProdChkCmds;


/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-8-28
 */
@RequestMapping(value="/cmdb/indicator/device")
@Controller
public class DevIndicatorItemsAction {
	
	private static Logger logger= LoggerFactory.getLogger(DevIndicatorItemsAction.class);

	private static String viewPath="/cmdb/indicator/device";
	
	@Autowired
	private IndItemsDao indItemsDao;
	
	@Autowired
	private DevIndicatorItemsService devIndicatorItemsService;
	
	@Autowired
	private ResItemService resItemService;
	
	@Autowired
	private CmdbProdCmdCheckItemsService cmdbProdCmdCheckItemsService;
	
	@Autowired
	private AckDevCheckItemService ackDevCheckItemService;
	
	@Autowired
	private IndItemsService indItemsService;
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("Enter getTree.do...");
		List<ParamTree> total = new ArrayList<ParamTree>();
		ParamTree  t=new ParamTree();
		t.setId("1_1");
		t.setText("IT资源指标组");
		t.setPid("0");
		t.setIsexpand(true);
		total.add(t);
		
		List<ResItemParam> classCodes=devIndicatorItemsService.getTreeByDevClassCodes();
		if(classCodes != null){
			for(int i=0;i<classCodes.size();i++){//设备分类  2级节点
				ParamTree pc=new ParamTree();
				ResItemParam paramClass=classCodes.get(i);
				pc.setId(paramClass.getId()+"_2");
				pc.setPid("1_1");
				pc.setText(paramClass.getText());
				total.add(pc);
				List<ResItemParam> typeCodes=devIndicatorItemsService.getTreeByDevTypeCodes(Integer.parseInt(paramClass.getId()));
				if(!typeCodes.isEmpty()){
					for(int j=0;j<typeCodes.size();j++){//设备类型 3级节点
						ParamTree pt=new ParamTree();
						ResItemParam paramType = typeCodes.get(j);
						pt.setId(paramClass.getId()+"_"+paramType.getId()+"_3");
						pt.setPid(paramClass.getId()+"_2");
						pt.setText(paramType.getText());
						total.add(pt);
						List<ResItemParam> groupNames=devIndicatorItemsService.getTreeByIndGroupName(Integer.parseInt(paramType.getId()));
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
		return viewPath + "/deviceTab";
		
	}
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map,String id) {
		logger.info( "Enter main.do ..." );
		List<DevIndicatorItems> list =new ArrayList<DevIndicatorItems>();
		String flag=id.substring(id.lastIndexOf("_")+1, id.length());
		if(flag.equals("1")){
			 list = devIndicatorItemsService.getAll();
		}else if(flag.equals("2")){
			String [] typeCodes=id.split("_");
			list = devIndicatorItemsService.getByClassCode(Integer.parseInt(typeCodes[0]));
			if(!list.isEmpty()){
				map.put("classCode", typeCodes[0]);
			}
			
		}else if(flag.equals("3")){
			String [] typeCodes=id.split("_");
			list = devIndicatorItemsService.getByClassAndType(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]));
			if(!list.isEmpty()){
				map.put("classCode", typeCodes[0]);
				map.put("typeCode", typeCodes[1]);
				map.put("className", list.get(0).getDevClassName());
				map.put("typeName", list.get(0).getDevTypeName());
			}
			
		}else if(flag.equals("4")){
			String [] typeCodes=id.split("_");
			list = devIndicatorItemsService.getByClassAndTypeAndGroup(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]),Long.parseLong(typeCodes[2]));
			Integer indClassCode=devIndicatorItemsService.getIndClassCodeByIndGroupID(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]),Long.parseLong(typeCodes[2]));
			if(!list.isEmpty()){
				map.put("classCode", typeCodes[0]);
				map.put("typeCode", typeCodes[1]);
				map.put("indGroupID", typeCodes[2]);
				map.put("indClassCode",indClassCode);
				map.put("className", list.get(0).getDevClassName());
				map.put("typeName", list.get(0).getDevTypeName());
				map.put("indGroupName", list.get(0).getIndGroupName());
				
			}
			
		}
		
		String jsonStr = devIndicatorItemsService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		return viewPath+"/main";
	}  
	
	@RequestMapping(value="/add.do")
	public String add(Integer devClassCode,String devClassName,Integer devTypeCode,String devTypeName,Long indGroupID,String indGroupName,Integer indClassCode, ModelMap map){
		logger.info("Enter add.do...");
		try {
			devClassName = new String(devClassName.getBytes("iso-8859-1"),"UTF-8");
			map.put("devClassName", devClassName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			devTypeName = new String(devTypeName.getBytes("iso-8859-1"),"UTF-8");
			map.put("devTypeName", devTypeName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			indGroupName = new String(indGroupName.getBytes("iso-8859-1"),"UTF-8");
			map.put("indGroupName", indGroupName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("action", "add");
//		map.put("devClassCode", devClassCode);
//		map.put("devTypeCode", devTypeCode);
//		map.put("indGroupID", indGroupID);
//		map.put("indClassCode", indClassCode);
		DevIndicatorItems items=new DevIndicatorItems();
		items.setDevClassCode(devClassCode);
		items.setDevTypeCode(devTypeCode);
		items.setIndClassCode(indClassCode);
		items.setIndGroupID(indGroupID);
		map.put("items", items);
		return viewPath+"/edit";
	}
	//关联指标项

	@RequestMapping(value = "/addItems.do")
	public String addItems(Integer devClassCode,String devClassName,Integer devTypeCode,String devTypeName, Long indGroupID,String indGroupName,Integer indClassCode,ModelMap map) throws ParseException {
		logger.info("Enter addItems.do ...");
		List<IndicatorItems> items = indItemsService.getItemsList(devClassCode,devTypeCode);
		String jsonStr = indItemsService.getJsonListStr(items);
		try {
			devClassName = new String(devClassName.getBytes("iso-8859-1"),"UTF-8");
			map.put("devClassName", devClassName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			devTypeName = new String(devTypeName.getBytes("iso-8859-1"),"UTF-8");
			map.put("devTypeName", devTypeName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("jsonListData", jsonStr);
		map.put("devClassCode", devClassCode);
		map.put("devTypeCode", devTypeCode);
		map.put("indGroupID", indGroupID);
		map.put("indClassCode", indClassCode);
		
		return viewPath + "/addItems";
	}
	
	//关联指标项--保存
	@RequestMapping(value="/saveItems.do")
	public String saveItems(Integer devClassCode,String devClassName,Integer devTypeCode,String devTypeName,String indJsonStrs,ModelMap map){
		logger.info("Enter save.do..");
		indJsonStrs = indJsonStrs.replaceAll("id", "indItemID");
		String [] idArray=devClassName.split(",");
		devClassName=idArray[0];
		String [] devTypeNames=devTypeName.split(",");
		devTypeName=devTypeNames[0];
		List<IndicatorItems> itemList=JsonUtils.getDTOList(indJsonStrs, IndicatorItems.class);
		
		devIndicatorItemsService.saveItems(devClassCode,devClassName,devTypeCode,devTypeName,itemList);
		
		map.put("result","success");
		map.put("jsonListData","{}");
		return viewPath+"/addItems";
	}
	//查找监测对象

		@RequestMapping(value = "/getIndClassCode.do")
		public String getIndClassCode(Integer devClassCode,Integer devTypeCode, HttpServletResponse response) throws IOException{
			logger.info("Enter addItems.do ...");
//			List<IndicatorItems> items = indItemsService.getIndClass(devClassCode,devTypeCode);
			List<IndicatorItems> items = indItemsService.getAll();
			String jsonStr = indItemsService.getJsonListStr(items);
			JSONObject jo = new JSONObject();
			
			jo.put("indClass", jsonStr);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		    return null; 
			
		}
		//过滤指标项 列表  
		@RequestMapping(value = "/searchItemsData.do")
		public String searchItemsData(Integer devClassCode,Integer devTypeCode, Long indGroupID,Integer indClassCode,  HttpServletResponse response) throws IOException {
			List<IndicatorItems> items = indItemsService.searchItemsData(devClassCode,devTypeCode,indGroupID,indClassCode);
			String jsonStr = indItemsService.getJsonListStr(items);
			
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonStr);
		    return null; 
			
		} 
	
	@RequestMapping(value="/edit.do")
	public String edit(String id,ModelMap map){
		logger.info("Enter edit.do...");
		DevIndicatorItems items=devIndicatorItemsService.getById(id);
		map.put("items", items);
		map.put("action", "edit");
		map.put("id", id);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value="/delete.do")
	public String delete(String devIndItemdIds,ModelMap map, HttpServletResponse response) throws IOException{
		logger.info("Enter delete.do..");
		if(!devIndItemdIds.isEmpty()){
			String [] ids=devIndItemdIds.split(",");
			devIndicatorItemsService.deletes(ids);
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
	public String saveAudit(String devIndItemdIds, HttpServletResponse response) throws IOException{
		
		List<DevIndicatorItems> list=new ArrayList<DevIndicatorItems>();
		if(!devIndItemdIds.isEmpty()){
			String [] ids=devIndItemdIds.split(",");
			for(String id:ids){
				DevIndicatorItems prodChkCmds=devIndicatorItemsService.getById(id);
				prodChkCmds.setStatus(1);
				list.add(prodChkCmds);
			}
			devIndicatorItemsService.batchSave(list);
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value="/save.do")
	public String save(String action, String id, DevIndicatorItems devIndItems, ModelMap map){
		logger.info("Enter save.do...");
		String saveData="";
		DevIndicatorItems saveDevIndItem=null;
		if("add".equals(action)){
			saveDevIndItem=devIndItems;
			String  resName=resItemService.findResContent("Cmdb_IndicatorClass", String.valueOf(devIndItems.getIndClassCode()));
			saveDevIndItem.setStatus(0);
			saveDevIndItem.setIndClassName(resName);
			
		}else{
			saveDevIndItem=devIndItems;
			saveDevIndItem.setDevIndItemID(Integer.parseInt(id));
			String  resName=resItemService.findResContent("Cmdb_IndicatorClass", String.valueOf(devIndItems.getIndClassCode()));
			saveDevIndItem.setIndClassName(resName);
		}
		devIndicatorItemsService.save(saveDevIndItem);
		saveData = devIndicatorItemsService.getJsonObjStr(saveDevIndItem);
		map.put("saveData", saveData);
		map.put("result", "success");
		return viewPath+"/edit";
	}
	
	
	
	@RequestMapping(value="/relationIndItems.do")
	public String relationIndItems(String indItemsIds,ModelMap map){
		map.put("indItemsIds", indItemsIds);
		return viewPath+"/relationIndItems";
	}
	
	@RequestMapping(value="/saveRelaDevIndItems.do")
	public String save(String indItemsIds,DevIndicatorItems devIndItems,ModelMap map){
		logger.info("Enter save.do..");
		List<Long> indItemIds=new ArrayList<Long>();
		List<DevIndicatorItems> devIndItemList=new ArrayList<DevIndicatorItems>();
		
		String []ids=indItemsIds.split(";");
		for(String id:ids){
			indItemIds.add(Long.parseLong(id));
		}
		
		List<IndicatorItems> items=indItemsDao.getDataByIds(indItemIds);
		
		for(int i=0;i<items.size();i++){
			DevIndicatorItems item=new DevIndicatorItems();
			BeanUtils.copyProperties(devIndItems, item);
			BeanUtils.copyProperties(items.get(i), item);
			devIndItemList.add(item);
		}
		devIndicatorItemsService.batchSave(devIndItemList);
		String saveData = devIndicatorItemsService.getJsonListStr(devIndItemList);
		map.put("saveData", saveData);
		map.put("result", "success");
		return viewPath+"/relationIndItems";
	}
	@RequestMapping(value = "/getExportExcelSize.do")
	public void  getExportExcelSize(ModelMap map,HttpServletResponse response) throws IOException {
		logger.info( "Enter exportExcel.do ..." );
		List<DevIndicatorItems> list = devIndicatorItemsService.getAll();
        response.getWriter().print(list.size());
	}
	@RequestMapping(value = "/exportExcel.do")
	public String  exportExcel(ModelMap map,HttpServletResponse response)  {
		logger.info( "Enter exportExcel.do ..." );
		List<DevIndicatorItems> list = devIndicatorItemsService.getAll();
		String[] headers = {"设备指标项ID", "设备分类编码", "设备分类名称","设备类型编码","设备类型名称","指标分类编码","指标分类名称","指标组ID","指标组名称","指标项ID","指标项名称","指标项","取值类型","取值长度","取值精度","取值单位","备注"};//导出字段名称
		String attr="devIndItemID,devClassCode,devClassName,devTypeCode,devTypeName,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemName,indicatorItem,valueType,length,decimals,measureUnit,remark";//导出映射字段
		String fileName="设备指标项_"+DateUtil.getExpTimeShow(new Timestamp(System.currentTimeMillis()))+".xls";//excel文件名

		try {
	          response.setContentType("octets/stream");
	          response.addHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gb2312"), "UTF-8"));
	          OutputStream out = response.getOutputStream();  
	          ExpExcelUtil.expExcel("设备指标项", headers, list, attr, out);
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
			if( devIndicatorItemsService.importExcel(impFile.getInputStream())){
				map.put("result", "success");
			}
			else{
				map.put("result", "error");
			}			
			map.put("message", devIndicatorItemsService.getLastMassage());
		}
		return viewPath+"/import";      
	}

//	添加设备指标采集命令与解析规则
	@RequestMapping(value = "/toProdCmdCheckItems.do")
	public String toProdCmdCheckItems(){
		
		return viewPath + "/prodCmdCheckItems";
	}
	@RequestMapping(value = "/prodCmdCheckItems.do")
	public String prodCmdCheckItems(CmdbProdCmdChkItems item,ModelMap map){
		cmdbProdCmdCheckItemsService.save(item);
		map.put("result", "success");
		return viewPath + "/prodCmdCheckItems";
	}
	@RequestMapping(value = "/check.do")
	public String check(Long devId,String result,ModelMap map){
		boolean checkCmdParseRule = ackDevCheckItemService.checkCmdParseRule(devId,result);
		map.put("result", checkCmdParseRule);
		return viewPath + "/prodCmdCheckItems";
	}
	
	
}
