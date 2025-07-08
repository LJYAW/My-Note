package com.sino.base.system.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.orm.PropertyFilter;

import com.sino.base.common.BaseAction;
import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.OrgEmployee;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.EmployeeService;

@Controller
@RequestMapping(value = "/system/employee")
public class EmployeeAction extends BaseAction{
	private static String viewPath = "/system/employee";
	
	private static Logger logger = LoggerFactory.getLogger(EmployeeAction.class);

	@Autowired
	private EmployeeService employeeService;
		
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		logger.info( "Enter main.do ..." );
		List<OrgEmployee> list = employeeService.getAllEmployee();
		String jsonStr = employeeService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	}                                              

	@RequestMapping(value = "/getData.do")
	public String getData(String orgId, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		List<OrgEmployee> list = null;
		if( orgId == null ){
			list = employeeService.getAllEmployee();
		}
		else{
			list = employeeService.getOrgEmployee(orgId);
		}
		String jsonStr = employeeService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}     
	
	@RequestMapping(value = "/getAllData.do")
	public String getAllData(HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		List<OrgEmployee> list = employeeService.getAllEmployee();
		String jsonStr = employeeService.getJsonListStr(list);
		JSONObject jo = new JSONObject();
		jo.put("jsonData", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}     
	
	@RequestMapping(value = "/getMinData.do")
	public String getMinData(String orgId, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getMinData.do ..." );
		List<OrgEmployee> list = null;
		if( orgId == null ){
			list = employeeService.getAllEmployee();
		}
		else{
			list = employeeService.getOrgEmployee(orgId);
		}
		String jsonStr = employeeService.getJsonMinListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}                                                   

	@RequestMapping(value = "/onSearch.do")
	public String onSearch(ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "onSearch");
		OrgEmployee employee = new OrgEmployee();
		map.put("employee", employee);
		return viewPath+"/search";
	}
	
	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response,ModelMap map) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<OrgEmployee> list = employeeService.searchEmployee(filters);
		String jsonStr = employeeService.getJsonListStr(list);
		
		map.put("action", "search");
		map.put("jsonListData", jsonStr);
		return viewPath + "/search";         
	}
	
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		OrgEmployee employee = new OrgEmployee();
		map.put("employee", employee);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		OrgEmployee employee = employeeService.getEmployee(id);
		map.put("employee", employee);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/save.do")
	public String save(String action, String empId, OrgEmployee employee, ModelMap map) {
		logger.info( "Enter save.do ..." );
		
		boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		else if( StringUtils.isBlank(employee.getName()) ){
			validate = false;
			map.put("message", "职员名称不能为空！");
		}
		
		if( validate ){
			String operator = curUser.getLoginName();
			Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
			employee.setModifier(operator);
			employee.setModifyTime(now);
			String jsonStr = "";
			if( "add".equals(action)){
				employee.setCreator(operator);
				employee.setCreateTime(now);
				employeeService.addEmployee(employee);	
				map.put("employee", employee);
				jsonStr = employeeService.getJsonObjStr(employee);
			}
			else{
				OrgEmployee editEmployee = employeeService.getEmployee(empId);
				if( editEmployee == null ){
					map.put("result", "error");								
					map.put("message", "该职员已经被删除！");
					return viewPath+"/edit";
				}
				BeanUtils.copyProperties(employee, editEmployee, new String[]{"orgPositions"});
				employeeService.saveEmployee(editEmployee);					
				map.put("employee", editEmployee);
				jsonStr = employeeService.getJsonObjStr(editEmployee);
			}
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("employee", employee);
			map.put("result", "error");			
		}

		map.put("action", action);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(",");
			for(int i=0;i<ids.length;i++){
				employeeService.deleteEmployee(ids[i]);
			}
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
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
			if( employeeService.impExcel(impFile.getInputStream()) ){
				map.put("result", "success");
			}
			else{
				map.put("result", "error");
			}			
			map.put("message", employeeService.getLastMassage());
		}
		return viewPath+"/import";      
	}            

}
