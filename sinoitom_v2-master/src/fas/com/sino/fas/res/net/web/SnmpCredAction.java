package com.sino.fas.res.net.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.entity.SysUser;
import com.sino.fas.res.net.entity.NcmSnmpCredentials;
import com.sino.fas.res.net.service.SnmpCredService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.PropertyFilter;

@Controller
@RequestMapping(value = "/fas/res/net/snmp/cred")
public class SnmpCredAction {
	private static Logger logger = LoggerFactory.getLogger(SnmpCredAction.class);
	
	private String viewPath = "/fas/res/net/snmp/cred";

	@Autowired
	private SnmpCredService snmpCredService;	
	
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<NcmSnmpCredentials> list = snmpCredService.getAllSnmpCred();
		String jsonStr = snmpCredService.getJsonTreeStr(list);
		map.put("jsonTreeData", jsonStr);
		return viewPath+"/main";
	}                                              

	@RequestMapping(value = "/getData.do")
	public String getData(HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		List<NcmSnmpCredentials> list = snmpCredService.getAllSnmpCred();
		String jsonStr = snmpCredService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}     
	
	@RequestMapping(value = "/getMinData.do")
	public String getMinData(HttpServletResponse response) throws IOException{     
		logger.info( "Enter getMinData.do ..." );
		List<NcmSnmpCredentials> list = snmpCredService.getAllSnmpCred();
		String jsonStr = snmpCredService.getJsonMinListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	} 
	
	
	@RequestMapping(value = "/getSnmpCredData.do")
	public String getSnmpCredData(HttpServletResponse response) throws IOException{     
		logger.info( "Enter getMinData.do ..." );
		List<NcmSnmpCredentials> list = snmpCredService.getAllSnmpCred();
		JSONObject jo = new JSONObject();
		JSONArray credlist = JSONArray.fromObject(list);
		jo.put("credlist", credlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;       
	} 
	
	
	
	 
	
	@RequestMapping(value = "/getItem.do")
	public String getItem(String id, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getItem.do ..." );
		NcmSnmpCredentials obj = snmpCredService.getSnmpCred(id);
		if(obj==null)
			obj =new NcmSnmpCredentials();
		String jsonStr = snmpCredService.getJsonObjStr(obj);
	    response.getWriter().print(jsonStr);
	    return null;         
	}                                                   

	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<NcmSnmpCredentials> list = snmpCredService.searchSnmpCred(filters);
		String jsonStr = snmpCredService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}

	@RequestMapping(value = "/add.do")
	public String add(ModelMap map,String oprType) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		NcmSnmpCredentials snmpCred = new NcmSnmpCredentials();
		snmpCred.setSnmpPort(161);
		snmpCred.setSnmpVersion(2);
		map.put("snmpCred", snmpCred);
		map.put("oprType", oprType);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		NcmSnmpCredentials snmpCred = snmpCredService.getSnmpCred(id);
		map.put("snmpCred", snmpCred);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/save.do")
	public String save(String action, String closeDlg, String id, NcmSnmpCredentials snmpCred,String oprType, ModelMap map) {
		logger.info( "Enter save.do ..." );
		
		boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		else if( !snmpCredService.isSnmpCredUnique(id, snmpCred.getSnmpCredName()) ){
			validate = false;
			map.put("message", "该配置已经存在！");
		}

		
		if( validate ){
			String jsonStr = "";
			String operator = curUser.getLoginName();
			snmpCred.setModifier(operator);
			Date now = new Date();
			snmpCred.setModifyTime(now.getTime());
			if( "add".equals(action)){
				snmpCred.setCreator(operator);
				snmpCred.setCreateTime(now.getTime());
				snmpCredService.addSnmpCred(snmpCred);		
				map.put("snmpCred", snmpCred);
				jsonStr = snmpCredService.getJsonObjStr(snmpCred);
				if("device".equals(oprType)){
					SystemUtils.addLog("添加路由器SNMP信息","成功添加路由器SNMP信息！",1);
				}else if("switch".equals(oprType)){
					SystemUtils.addLog("添加交换机SNMP信息","成功添加交换机SNMP信息！",2);
				}
				
			}
			else{
				NcmSnmpCredentials editSnmpCred = snmpCredService.getSnmpCred(id);
				if( editSnmpCred == null ){
					map.put("result", "error");								
					map.put("message", "该配置已经被删除！");
					return viewPath+"/edit";
				}
				BeanUtils.copyProperties(snmpCred, editSnmpCred);
				snmpCredService.saveSnmpCred(editSnmpCred);			
				map.put("snmpCred", editSnmpCred);
				jsonStr = snmpCredService.getJsonObjStr(editSnmpCred);
				if("device".equals(oprType)){
					SystemUtils.addLog("修改路由器SNMP信息","成功修改路由器SNMP信息！",1);
				}else if("switch".equals(oprType)){
					SystemUtils.addLog("修改交换机SNMP信息","成功修改交换机SNMP信息！",2);
				}
				
			}
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("snmpCred", snmpCred);
			map.put("result", "error");			
		}

		map.put("action", action);
		map.put("closeDlg", closeDlg);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		snmpCredService.deleteSnmpCred(id);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	@RequestMapping(value = "/view.do")
	public String view(String id, ModelMap map) throws IOException{     
		logger.info( "Enter view.do ..." );
		NcmSnmpCredentials snmpCred = snmpCredService.getSnmpCred(id);
		map.put("snmpCred", snmpCred);

		return viewPath+"/view";
	} 
	@RequestMapping(value = "/getSnmpRoStrData.do")
	public String getSnmpRoStrData(String id,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getSnmpRoStrData.do ..." );
		List<NcmSnmpCredentials> list = snmpCredService.getAllSnmpCred();
		JSONObject jo = new JSONObject();
		JSONArray snmpRoStrlist = JSONArray.fromObject(list);
		jo.put("snmpRoStrlist", snmpRoStrlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;         
	}
}
