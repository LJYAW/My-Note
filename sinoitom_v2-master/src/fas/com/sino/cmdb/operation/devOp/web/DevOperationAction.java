package com.sino.cmdb.operation.devOp.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.indicator.device.entity.DevIndicatorItems;
import com.sino.cmdb.operation.devOp.dao.DevOperationDao;
import com.sino.cmdb.operation.devOp.entity.DevOperation;
import com.sino.cmdb.operation.devOp.service.DevOperationService;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

/**
 * 
 *
 * @author <a href="mailto:wangam@sino-bridge.com">王阿明</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2013-2-11
 */
@Controller
@RequestMapping(value = "/cmdb/devOperation")
public class DevOperationAction {
	private static Logger logger = LoggerFactory.getLogger(DevOperationAction.class);
	private String viewPath = "/cmdb/devOperation";
	private String operationViewPath = "/cmdb/operation/devOp";
	
	@Autowired
	private ResItemService resItemService;
	@Autowired
	private DevOperationService devOperationService;
	@Autowired
	private DevOperationDao devOperationDao; 
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("Enter getTree.do...");
		List<ParamTree> total = new ArrayList<ParamTree>();
		ParamTree  t=new ParamTree();
		t.setId("1_1");
		t.setText("操作名称");
		t.setPid("0");
		t.setIsexpand(true);
		total.add(t);
		
		List<ResItemParam> classCodes=devOperationDao.getDevClassCodes();
		if(classCodes != null){
			for(int i=0;i<classCodes.size();i++){//snmp 或 cli  2级节点
				ParamTree pc=new ParamTree();
				ResItemParam paramClass=classCodes.get(i);
				pc.setId(paramClass.getId()+"_2");
				pc.setPid("1_1");
				pc.setText(paramClass.getText());
				total.add(pc);
				List<ResItemParam> typeCodes=devOperationDao.getDevTypeCodes(Integer.parseInt(paramClass.getId()));
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
		return operationViewPath + "/devOpTab";
		
	}
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map,String id) {
		logger.info( "Enter main.do ..." );
		List<DevOperation> list =new ArrayList<DevOperation>();
		String flag=id.substring(id.lastIndexOf("_")+1, id.length());
		if(flag.equals("1")){
			 list = devOperationService.getAll();
		}else if(flag.equals("2")){
			String [] typeCodes=id.split("_");
			list = devOperationService.getByClassCode(Integer.parseInt(typeCodes[0]));
		}else if(flag.equals("3")){
			String [] typeCodes=id.split("_");
			list = devOperationService.getByClassAndType(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]));
		}
		
		String jsonStr = devOperationService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		return operationViewPath+"/main";
	}
	
	
	@RequestMapping(value = "/getDevOpMain.do")
	public String getDevOpMain(ModelMap map) {
		logger.info( "Enter main.do ..." );
		return operationViewPath+"/main";
	}
	
	
	@RequestMapping(value = "/opCmdMain.do")
	public String opCmdMain(ModelMap map) {
		logger.info( "Enter main.do ..." );
		return "/cmdb/operation/mainTab";
	}
	
	
	
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info( "Enter add.do ..." );
		DevOperation devOperation =new DevOperation();
		map.put("devOperation", devOperation);
		map.put("action", "add");
		return operationViewPath+"/edit";
	}
	
	@RequestMapping(value = "/addDevOP.do")
	public String addDevOP(ModelMap map) {
		logger.info( "Enter addDevOP.do ..." );
		DevOperation devOperation =new DevOperation();
		map.put("devOperation", devOperation);
		map.put("action", "add");
		return operationViewPath+"/edit";
	}
	
	
	
	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		DevOperation devOperation = devOperationService.getDevOperationById(Integer.valueOf(id));
		map.put("action", "edit");
		map.put("devOperation", devOperation);
		map.put("id", id);
		return operationViewPath+"/edit";
	}
	
	@RequestMapping(value = "/editDevOP.do")
	public String editDevOP(String id, ModelMap map) {
		logger.info( "Enter editDevOP.do ..." );
		DevOperation devOperation = devOperationService.getDevOperationById(Integer.valueOf(id));
		map.put("action", "edit");
		map.put("devOperation", devOperation);
		map.put("id", id);
		return operationViewPath+"/edit";
	}
	
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		String[] ids = id.split(",");
		for(int i=0;i<ids.length;i++){
			if(!StringUtil.isNullString(ids[i]));
			devOperationService.delete(Integer.valueOf(ids[i]));
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;
	}
	
	@RequestMapping(value = "/deletes.do")
	public String deletes(String devOpIds, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		String[] ids = devOpIds.split(";");
		for(int i=0;i<ids.length;i++){
			if(!StringUtil.isNullString(ids[i]));
			devOperationService.delete(Integer.valueOf(ids[i]));
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;
	}
	
	@RequestMapping(value = "/batchAudit.do")
	public String batchAudit(String devOpIds, ModelMap map,
			HttpServletResponse response) throws IOException {
		logger.info("Enter  batchAudit.do...");
		List<Integer> operIds = new ArrayList<Integer>();
		String[] arrOpIds = devOpIds.split(";");
		for (String id : arrOpIds) {
			operIds.add(Integer.parseInt(id));
		}
		devOperationService.batchSave(operIds);
		devOperationDao.flush();
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	
	@RequestMapping(value = "/save.do")
	public String save(String action, String id,DevOperation devOperation, ModelMap map) throws JsonParseException, JsonMappingException, IOException {
		logger.info( "Enter save.do ..." );
		map.put("action", action);
		boolean validate = true;
		if( validate ){
			DevOperation saveDevOperation = null;
			if( "add".equals(action)){
				String accessMode=resItemService.findResContent("Cmdb_DevAccessMode", String.valueOf(devOperation.getAccessModeCode())); 
				devOperation.setAccessMode(accessMode);
				devOperation.setDescription(devOperation.getDescription().replace(",", ""));
				devOperation.setStatus(0);
				saveDevOperation = devOperation;
			}else{
				DevOperation editDevOperation = devOperationService.getDevOperationById(Integer.valueOf(id));
				if( editDevOperation == null ){
					map.put("devOperation", editDevOperation);
					map.put("result", "error");								
					map.put("message", "该portlet已经被删除！");
					return viewPath+"/edit";
				}				
				BeanUtils.copyProperties(devOperation, editDevOperation);
				saveDevOperation = editDevOperation;
			}
			devOperationService.save(saveDevOperation);
			String saveData = devOperationService.getJsonObjStr(saveDevOperation);
			map.put("saveData", saveData);
			map.put("result", "success");
		}else{
			map.put("result", "error");			
		}
		map.put("devOperation", devOperation);
		return viewPath+"/edit";
	}
	
	
	@RequestMapping(value="/saveDevOP.do")
	public String saveDevOP(String action, String id,DevOperation devOperation, ModelMap map){
		logger.info("Enter saveDevOP.do..");
		map.put("action", action);
		boolean validate = true;
		if( validate ){
			DevOperation saveDevOperation = null;
			if( "add".equals(action)){
				String accessMode=resItemService.findResContent("Cmdb_DevAccessMode", String.valueOf(devOperation.getAccessModeCode())); 
				devOperation.setAccessMode(accessMode);
				String opTypeName=resItemService.findResContent("Cmdb_DevOpType", String.valueOf(devOperation.getOpTypeCode()));
				devOperation.setOpTypeName(opTypeName);
				devOperation.setDescription(devOperation.getDescription().replace(",", ""));
				saveDevOperation = devOperation;
			}else{
				DevOperation editDevOperation = devOperationService.getDevOperationById(Integer.valueOf(id));
				String accessMode=resItemService.findResContent("Cmdb_DevAccessMode", String.valueOf(devOperation.getAccessModeCode())); 
				devOperation.setAccessMode(accessMode);
				String opTypeName=resItemService.findResContent("Cmdb_DevOpType", String.valueOf(devOperation.getOpTypeCode()));
				devOperation.setOpTypeName(opTypeName);
				devOperation.setDescription(devOperation.getDescription().replace(",", ""));
				if( editDevOperation == null ){
					map.put("devOperation", editDevOperation);
					map.put("result", "error");								
					map.put("message", "该portlet已经被删除！");
					return viewPath+"/edit";
				}				
				BeanUtils.copyProperties(devOperation, editDevOperation);
				saveDevOperation = editDevOperation;
			}
			devOperationService.save(saveDevOperation);
			String saveData = devOperationService.getJsonObjStr(saveDevOperation);
			map.put("saveData", saveData);
			map.put("result", "success");
		}else{
			map.put("result", "error");			
		}
		map.put("devOperation", devOperation);
		return operationViewPath+"/edit";
	}
	
	
	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<DevOperation> list = devOperationService.searchDevOperation(filters);
		String jsonStr = devOperationService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}
	
	@RequestMapping(value="/getByParams.do")
	public String getByParams(int opID,int devClassCode,int devTypeCode,String accessMode,HttpServletResponse response) throws IOException{
		logger.info( "Enter getByParams.do ..." );
		List<DevOperation> list=devOperationService.getByParams(opID, devClassCode, devTypeCode, accessMode);
		String jsonStr = devOperationService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;     
	}
}
