/*
 * 文件名： OperationAction.java
 * 
 * 创建日期： 2014-9-13
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.operation.devOp.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.cmdb.operation.devOp.dao.OperationDao;
import com.sino.cmdb.operation.devOp.entity.Operation;
import com.sino.cmdb.operation.devOp.service.OperationService;

/**
 * 
 * 
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 * 
 * @version $Revision$
 * 
 * @since 2014-9-13
 */
@RequestMapping(value = "/cmdb/operation")
@Controller
public class OperationAction {

	private static Logger logger = LoggerFactory
			.getLogger(OperationAction.class);

	private static String viewPath = "cmdb/operation/cmdbOp";

	@Autowired
	private OperationService operationService;

	@Autowired
	private OperationDao operationDao;

	@RequestMapping(value = "/main.do")
	public String main(ModelMap map) {
		logger.info("Enter main.do...");
		List<Operation> operations = operationService.getAll();
		String jsonStr = operationService.getJsonListStr(operations);
		map.put("jsonStr", jsonStr);
		return viewPath + "/main";
	}

	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info("Enter add.do..");
		Operation operation = new Operation();
		map.put("action", "add");
		map.put("operation", operation);
		return viewPath + "/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info("Enter edit.do..");
		Operation operation = operationService.getById(Integer.parseInt(id));
		map.put("action", "edit");
		map.put("operation", operation);
		map.put("id", id);
		return viewPath + "/edit";
	}
	
	@RequestMapping(value = "/deletes.do")
	public String deletes(String opIds, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		String[] ids = opIds.split(";");
		for(int i=0;i<ids.length;i++){
			if(!StringUtil.isNullString(ids[i]));
			operationService.delete(Integer.valueOf(ids[i]));
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;
	}

	@RequestMapping(value = "/save.do")
	public String save(String id, Operation operation, String action,
			ModelMap map) {
		logger.info("Enter save.do...");
		Operation saveOperation = null;
		if ("add".equals(action)) {
			saveOperation = operation;
			saveOperation.setStatus(0);
			operationService.save(saveOperation);
		}
		else {
			saveOperation = operationService.getById(Integer.parseInt(id));
			saveOperation.setDescription(operation.getDescription());
			saveOperation.setOperation(operation.getOperation());
			saveOperation.setOperationName(operation.getOperationName());
			saveOperation.setOpType(operation.getOpType());
			operationService.save(saveOperation);
		}

		String saveData = operationService.getJsonObjStr(saveOperation);
		map.put("saveData", saveData);
		map.put("result", "success");
		map.put("saveOperation", saveOperation);
		return viewPath + "/edit";
	}

	@RequestMapping(value = "/batchAudit.do")
	public String batchAudit(String opIds, ModelMap map,
			HttpServletResponse response) throws IOException {
		logger.info("Enter CmdbOperation batchAudit.do...");
		List<Integer> operIds = new ArrayList<Integer>();
		String[] arrOpIds = opIds.split(";");
		for (String id : arrOpIds) {
			operIds.add(Integer.parseInt(id));
		}
		operationService.batchSave(operIds);
		operationDao.flush();
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value="/getOperationData.do")
	public String getOperationData(String opType,HttpServletResponse response) throws IOException{
		logger.info("Enter getOperationData.do...");
		List<Operation> operations=null;
		if(!StringUtil.isNullString(opType)){
			 operations=operationService.getAuditDataAndOpType(opType);
		}else{
			 operations=operationService.getAuditData();
		}
		
		JSONObject jo = new JSONObject();
		String jsonStr=operationService.getJsonListStr(operations);
		jo.put("operations", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
}
