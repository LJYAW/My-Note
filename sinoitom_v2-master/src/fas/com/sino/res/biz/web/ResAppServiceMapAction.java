package com.sino.res.biz.web;

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

import com.sino.ack.resources.entity.AckResources;
import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.host.entity.ParamResAppService;
import com.sino.fas.res.host.entity.ResAppService;
import com.sino.res.biz.dao.ResAppServiceMapDao;
import com.sino.res.biz.entity.ResAppServiceMap;
import com.sino.res.biz.entity.ResBizSystem;
import com.sino.res.biz.service.ResAppServiceMapService;

@RequestMapping(value = "/res/biz/resAppServiceMap")
@Controller
public class ResAppServiceMapAction {


	private String viewPath = "/res/biz";
	private static Logger logger = LoggerFactory.getLogger(ResAppServiceMapAction.class);
	
	@Autowired
	private ResAppServiceMapService resAppServiceMapService;
	
	
	@RequestMapping(value="/saveAppService.do")
	public String saveAppService(Long bizAppId,String rowData,String idList,ModelMap map){
		logger.info("Enter save.do..");
		List<ResAppServiceMap> mapList=resAppServiceMapService.getByBizAppId(bizAppId);
		List<ParamResAppService> resList=JsonUtils.getDTOList(rowData, ParamResAppService.class);
		resAppServiceMapService.saveAppService(bizAppId,resList,mapList);
		map.put("result", "success");
		map.put("jsonListData", "[]");
//		map.put("action", "addAckResources");
		return viewPath+"/setService";
	}
	

}
