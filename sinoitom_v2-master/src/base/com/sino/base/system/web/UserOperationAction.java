package com.sino.base.system.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;

import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.JqPageBean;
import com.sino.base.system.entity.SysUserOperation;
import com.sino.base.system.service.UserOperationService;

@RequestMapping(value="/system/userOperation")
@Controller
public class UserOperationAction {

private static String viewPath = "/system/operation";
	
	private static Logger logger = LoggerFactory.getLogger(UserOperationAction.class);
	
	@Autowired
	private UserOperationService userOperationService;
	
	
	@RequestMapping(value="/main.do")
	public String main(String menuId,ModelMap map){
		logger.info("Enter UserOperationAction main.....");
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	}
	
	@RequestMapping(value = "/searchAjaxPage.do")
	public String searchAjaxPage(PageRequest pageRequest, JqPageBean page, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("Enter searchAjaxPage.do ...");
		pageRequest.setPageNo(page.getPage());
		pageRequest.setPageSize(page.getRows());
		pageRequest.setOrderBy(page.getSidx());
		pageRequest.setOrderDir(page.getSord());
		
		String jsonStr="{}";
		
		Page<SysUserOperation> pages = userOperationService.findUserOperPage(pageRequest);
		jsonStr = userOperationService.getUserOperaPageList(pages);
		response.getWriter().print(jsonStr);
		return null;
	}
	
	@RequestMapping(value = "/onView.do")
	public String onView(long id,ModelMap map){
		SysUserOperation oper=userOperationService.getById(id);
		map.put("oper", oper);
		
		Timestamp ts = new Timestamp(oper.getOpTime().getTime());
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 方法一
			tsStr = sdf.format(ts);
			System.out.println(tsStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		map.put("optime", tsStr);
		return viewPath+"/view";
	}
	
	
}
