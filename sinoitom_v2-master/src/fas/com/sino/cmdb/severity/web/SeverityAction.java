/*
 * 文件名： SeverityAction.java
 * 
 * 创建日期： 2014-9-12
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.severity.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sino.cmdb.severity.entity.Severity;
import com.sino.cmdb.severity.service.SeverityService;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-12
 */
@RequestMapping(value="/cmdb/severity")
@Controller
public class SeverityAction {

	@Autowired
	private SeverityService severityService;
	
	
	@RequestMapping(value="/getById.do")
	public List<Severity> getById(String id,HttpServletResponse response) throws IOException{
		List<Severity> li=severityService.getById(id);
		String jsonStr = "";
		for (int i = 0; i < li.size(); i++) {
			if(li.get(i).getSeverityCode() == Integer.parseInt(id)){
				jsonStr = severityService.getJsonObjStr(li.get(i));
				break;
			}
		}
		JSONObject jo = new JSONObject();
		jo.put("jsonStr", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	
	@RequestMapping(value="/getAll.do")
	public List<Severity> getAll(HttpServletResponse response) throws IOException{//不包括code为0的数据
		List<Severity> li=severityService.getAllExceptZero();
		String jsonStr = severityService.getJsonListStr(li);
		JSONObject jo = new JSONObject();
		jo.put("jsonStr", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	
	@RequestMapping(value="/getAllAndZero.do")
	public List<Severity> getAllAndZero(HttpServletResponse response) throws IOException{//包括code为0的数据
		List<Severity> li=severityService.getAll();
		String jsonStr = severityService.getJsonListStr(li);
		JSONObject jo = new JSONObject();
		jo.put("jsonStr", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	
}
