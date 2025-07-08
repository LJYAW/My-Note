package com.sino.fas.res.net.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.PropertyFilter;

import com.sino.base.common.util.SystemUtils;
import com.sino.fas.res.net.entity.NcmPhysicalPort;
import com.sino.fas.res.net.entity.NcmSwitchPort;
import com.sino.fas.res.net.entity.NcmSwitchPortId;
import com.sino.fas.res.net.entity.NcmSwitchPortInfo;
import com.sino.fas.res.net.service.SwitchPortService;

@Controller
@RequestMapping(value = "/fas/res/net/devices/interface")
public class NcmDevIfAction {

	private static Logger logger = LoggerFactory.getLogger(NcmDevIfAction.class);
	
	private String viewPath = "/fas/res/net/devices/interface";
	
	@Autowired
	private SwitchPortService itfService;	
	
	@RequestMapping(value = "/main.do")
	public String main(Long devId, ModelMap map) {
		logger.info( "Enter main.do ..." );
		//List<NcmSwitchPort> list = itfService.getAllSwitchPort(devId);
		List<NcmSwitchPortInfo> list =itfService.getSwitchPortList(devId);
		//String jsonStr = itfService.getJsonTreeStr(list);
		JSONArray jsonStr= JSONArray.fromObject(list);
		map.put("jsonTreeData", jsonStr.toString());
		map.put("deviceId", devId);
		return viewPath+"/main";
	}  
	
	
	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<NcmSwitchPortInfo> list = itfService.getFilterSwitchPortList(filters);
		//String jsonStr = itfService.getJsonListStr(list);
		JSONArray jsonStr= JSONArray.fromObject(list);
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().print(jsonStr.toString());
		SystemUtils.addLog("查询交换机接口","成功查询交换机接口！", 2);
	    return null;         
	}
	
	@RequestMapping(value = "/edit.do")
	public String edit(NcmSwitchPortId id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		NcmSwitchPort itf = itfService.getSwitchPort(id);
		map.put("itf", itf);
		return viewPath+"/edit";
	}
	
//	@RequestMapping(value = "/view.do")
//	public String view(Long id, ModelMap map) throws IOException{     
//		logger.info( "Enter viewMenu.do ..." );
//		NcmPhysicalPort itf = itfService.getDevItf(id);
//		map.put("itf", itf);
//		SystemUtils.addLog("查看路由器接口","成功查看路由器接口信息！",1);
//		return viewPath+"/view";
//	} 
}
