/*
 * 文件名： DevOpOIDAction.java
 * 
 * 创建日期： 2014-12-7
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.operation.devOpOID.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.operation.devOp.entity.DevOperation;
import com.sino.cmdb.operation.devOp.entity.Operation;
import com.sino.cmdb.operation.devOp.service.DevOperationService;
import com.sino.cmdb.operation.devOp.service.OperationService;
import com.sino.cmdb.operation.devOpOID.dao.DevOpOIDDao;
import com.sino.cmdb.operation.devOpOID.entity.DevOpOID;
import com.sino.cmdb.operation.devOpOID.entity.paramDevOpOID;
import com.sino.cmdb.operation.devOpOID.service.DevOpOIDService;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.entity.CmdbVendor;
import com.sino.cmdb.vendor.service.CmdbVendorService;


/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-12-7
 */
@Controller
@RequestMapping(value="/cmdb/devOpOID")
public class DevOpOIDAction {
	private static Logger logger = LoggerFactory.getLogger(DevOpOIDAction.class);
	private String viewPath = "/cmdb/operation/devOpOID";
	
	@Autowired
	private DevOpOIDDao devOpOIDDao;
	
	@Autowired
	private DevOpOIDService devOpOIDService;
	
	@Autowired
	private DevOperationService devOperationService;
	
	@Autowired
	private ResItemService resItemService;
	
	@Autowired
	private CmdbVendorService cmdbVendorService;
	
	@Autowired
	private OperationService operationService;
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("Enter getTree.do...");
		List<ParamTree> total = new ArrayList<ParamTree>();
		ParamTree  t=new ParamTree();
		t.setId("1_1");
		t.setText("设备操作（Snmp）");
		t.setPid("0");
		t.setIsexpand(true);
		total.add(t);
		
		List<ResItemParam> classCodes=devOpOIDDao.getOperation();
		if(classCodes != null){
			for(int i=0;i<classCodes.size();i++){//设备分类  2级节点
				ParamTree pc=new ParamTree();
				ResItemParam paramClass=classCodes.get(i);
				pc.setId(paramClass.getId()+"_2");
				pc.setPid("1_1");
				pc.setText(paramClass.getText());
				total.add(pc);
				List<ResItemParam> typeCodes=devOpOIDDao.getVenderById(Integer.parseInt(paramClass.getId()));
				if(!typeCodes.isEmpty()){
					for(int j=0;j<typeCodes.size();j++){//设备类型 3级节点
						ParamTree pt=new ParamTree();
						ResItemParam paramType = typeCodes.get(j);
						pt.setId(paramClass.getId()+"_"+paramType.getId()+"_3");
						pt.setPid(paramClass.getId()+"_2");
						pt.setText(paramType.getText());
						total.add(pt);
						List<ResItemParam> groupNames=devOpOIDDao.getDevTypeByVendor(Integer.parseInt(paramType.getId()),Integer.parseInt(paramClass.getId()));
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
		return viewPath + "/OIDTab";
		
	}
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map,String id) {
		logger.info( "Enter main.do ..." );
		List<paramDevOpOID> list =new ArrayList<paramDevOpOID>();
		String flag=id.substring(id.lastIndexOf("_")+1, id.length());
		if(flag.equals("1")){
			list = devOpOIDService.getAll();
		}else if(flag.equals("2")){
			String [] typeCodes=id.split("_");
			list = devOpOIDService.getByOperation(Integer.parseInt(typeCodes[0]));
		}else if(flag.equals("3")){
			String [] typeCodes=id.split("_");
			list = devOpOIDService.getByOperationAndVendor(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]));
		}else if(flag.equals("4")){
			String [] typeCodes=id.split("_");
			list = devOpOIDService.getByOperationAndVendorAndDevType(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]),Integer.parseInt(typeCodes[2]));
		}
		
		String jsonStr = devOpOIDService.getParamJsonListStr(list);
		map.put("jsonListData", jsonStr);
		return viewPath+"/main";
	}
	
	
	@RequestMapping(value = "/add.do")
	public String maintain(ModelMap map) {
		logger.info( "Enter add.do ..." );
//		DevOperation  devOperation=devOperationService.getDevOperationById(Integer.valueOf(id));
//		SysResGroup resGroup=resItemService.getSysResGroup("Cmdb_DevCmdType");
//		List<SysResItem> resItems=resItemService.getResCode(resGroup.getResGrpId());
//		String cmdTypeStr=resItemService.getJsonListStr(resItems);
//		map.put("cmdTypeStr", cmdTypeStr);
//		map.put("devOperation", devOperation);
		map.put("action", "add");
		return viewPath+"/add";
	}
	
	@RequestMapping(value="/edit.do")
	public String edit(String id,ModelMap map){
		logger.info( "Enter DevOpOID edit.do ..." );
		DevOpOID opOID=devOpOIDService.getByID(Integer.valueOf(id));
		DevOperation devOp=devOperationService.getDevOperationById(opOID.getDevOpID());
		map.put("opOID", opOID);
		map.put("devOp", devOp);
		map.put("id", id);
		map.put("action", "edit");
		return viewPath+"/add";
	}
	
	@RequestMapping(value="/deletes.do")
	public String delete(String devOpIds,HttpServletResponse response) throws IOException{
		logger.info("Enter deletes.do...");
		String []ids=devOpIds.split(";");
		devOpOIDService.deletes(ids);
		JSONObject jo=new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value="/view.do")
	public String view(String id,ModelMap map){
		logger.info( "Enter DevOpOID edit.do ..." );
		DevOpOID opOID=devOpOIDService.getByID(Integer.valueOf(id));
		DevOperation devOp=devOperationService.getDevOperationById(opOID.getDevOpID());
		Operation operation=operationService.getById(devOp.getOpID());
		CmdbVendor vendor=cmdbVendorService.findVendor(opOID.getSnmpEntID());
		String valueType=resItemService.findResCode("Cmdb_IndValueType", opOID.getValueType());
		map.put("opOID", opOID);
		map.put("devOp", devOp);
		map.put("vendor", vendor);
		map.put("valueType", valueType);
		map.put("operation", operation);
		return viewPath+"/view";
	}
	
	@RequestMapping(value="/getDevOperationBySnmp.do")
	public String getDevOperationBySnmp(String opId,HttpServletResponse response) throws IOException{
		logger.info("Enter getDevOperationBySnmp.do...");
		List<DevOperation> operations=devOperationService.getDevOperationBySnmp(opId);
		JSONObject jo = new JSONObject();
		String jsonStr=devOperationService.getJsonListStr(operations);
		jo.put("operations", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value="/save.do")
	public String save(String action,String id,DevOpOID devOpOID,ModelMap map,HttpServletResponse response){
		logger.info("Enter DevOpOID save.do");
		logger.info("Enter save.do...");
		boolean validate = true;
		if(validate){
			DevOpOID saveDevOpOID = null;
			if("add".equals(action)){
				saveDevOpOID = devOpOID;
				saveDevOpOID.setStatus(0);
				saveDevOpOID.setOidOrder(0);
			}else{
				DevOpOID editDevOpOID =devOpOIDService.getByID(Integer.valueOf(id));
				BeanUtils.copyProperties(devOpOID, editDevOpOID, new String[]{"iD","oidOrder","status"});
				saveDevOpOID=editDevOpOID;
			}
			devOpOIDService.save(saveDevOpOID);

			String saveData = devOpOIDService.getJsonObjStr(saveDevOpOID);
			map.put("saveData", saveData);
			map.put("result", "success");
			map.put("saveDevOpOID", saveDevOpOID);
		}else{
			map.put("result", "error");	
		}
		return viewPath + "/add";

	}
	
	
	@RequestMapping(value="/saveAdd.do")
	public String saveAdd(DevOpOID devOpOID,ModelMap map,HttpServletResponse response) throws IOException{
		logger.info("Enter DevOpOID saveAdd.do");
			DevOpOID saveDevOpOID = null;
			saveDevOpOID = devOpOID;
			saveDevOpOID.setStatus(0);
			saveDevOpOID.setOidOrder(0);
			devOpOIDService.save(saveDevOpOID);
			String saveData = devOpOIDService.getJsonObjStr(saveDevOpOID);
			JSONObject jo = new JSONObject();
			jo.put("saveData", saveData);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
			return null;
	}

}
