package com.sino.base.system.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.PropertyFilter;


import com.sino.base.common.util.CommandUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.entity.SysParamItem;
import com.sino.base.system.entity.SysParamGroup;
import com.sino.base.system.service.ParamGroupService;
import com.sino.base.system.service.ParamItemService;

@Controller
@RequestMapping(value = "/system/param/item")
public class ParamItemAction {
	private static String viewPath = "/system/param/item";
	
	private static Logger logger = LoggerFactory.getLogger(ParamItemAction.class);

	@Autowired
	private ParamItemService paramService;

	@Autowired
	private ParamGroupService powGrpService;

	
	@RequestMapping(value = "/main.do")
	public String main(String grpId, ModelMap map) {
		logger.info( "Enter main.do ..." );
		SysParamGroup group = powGrpService.getParamGroup(grpId);
		map.put("group", group);
		List<SysParamItem> list = paramService.getGroupParamItem(grpId);
		String jsonStr = paramService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		return viewPath+"/main";
	}   
	
	@RequestMapping(value = "/bizMain.do")
	public String bizMain(String grpId, ModelMap map) {
		logger.info( "Enter bizMain.do ..." );
		SysParamGroup group = powGrpService.getParamGroup(grpId);
		map.put("group", group);
		List<SysParamItem> list = paramService.getGroupParamItem(grpId);
		String jsonStr = paramService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		return "/system/bizParam/item/main";
	}   
	
//	@RequestMapping(value = "/updateParam.do")
//	public void updateParam(String grpCode, String grpId,ModelMap map,HttpServletResponse response) throws IOException {
//		logger.info( "Enter bizMain.do ..." );
//		boolean isflag=false;
//		ClientConfiguration client =new ClientConfiguration();
//		if(("PingParam").equalsIgnoreCase(grpCode)){
//			List<SysParamItem> list = paramService.getGroupParamItem(grpId);
//			int PingPacketCount=-1;
//			int PingPacketSize =-1;
//			int PingPacketTimeout =-1;
//			int PingDelay =-1;
//			for(SysParamItem item:list){
//				if(("PingPacketCount").equals(item.getParamCode())){
//					PingPacketCount=Integer.valueOf(item.getValueText());
//				}
//				if(("PingPacketSize").equals(item.getParamCode())){
//					PingPacketSize=Integer.valueOf(item.getValueText());				
//				}
//				if(("PingPacketTimeout").equals(item.getParamCode())){
//					PingPacketTimeout=Integer.valueOf(item.getValueText());
//				}
//				if(("PingDelay").equals(item.getParamCode())){
//					PingDelay=Integer.valueOf(item.getValueText());
//				}
//			}
//			System.out.println("get the pingParam:"+PingPacketCount+"  "+ PingPacketSize+"  "+PingPacketTimeout+"  "+ PingDelay);
//			isflag=client.updatePingParameter(PingPacketCount, PingPacketSize, PingPacketTimeout, PingDelay);
//		}else{
//			List<SysParamItem> list = paramService.getGroupParamItem(grpId);
//			int EventBuffer_Timeout=-1;
//			int MailAttachedConversationCount =-1;
//			for(SysParamItem item:list){
//				if(("EventBuffer_Timeout").equals(item.getParamCode())){
//					EventBuffer_Timeout=Integer.valueOf(item.getValueText());
//				}
//				if(("MailAttachedConversationCount").equals(item.getParamCode())){
//					MailAttachedConversationCount=Integer.valueOf(item.getValueText());
//				}
//			}
//			System.out.println("get the pingParam:"+EventBuffer_Timeout+"   count:>>>"+MailAttachedConversationCount);
//			isflag=client.updateAlmEventSuppressBufferTimeout(EventBuffer_Timeout);
//			
//			AlmConfiguration almconf = new  AlmConfiguration();
//		    boolean isresult = almconf.updateEmailAttachedConversationCount(MailAttachedConversationCount);
//		}
//		if(isflag){
//			response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
//		}else{
//			response.getWriter().print(JsonUtils.JSON_RESULT_FALSE);
//		}
//	}   

	@RequestMapping(value = "/getData.do")
	public String getData(String grpId, HttpServletResponse response) throws IOException{     
		logger.info( "Enter getData.do ..." );
		List<SysParamItem> list = paramService.getGroupParamItem(grpId);
		String jsonStr = paramService.getJsonListStr(list);
	    response.getWriter().print(jsonStr);
	    return null;         
	}                                                   

	@RequestMapping(value = "/search.do")
	public String search(HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<SysParamItem> list = paramService.searchParamItem(filters);
		String jsonStr = paramService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}

	@RequestMapping(value = "/add.do")
	public String add(String grpId, ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		SysParamGroup group = powGrpService.getParamGroup(grpId);
		SysParamItem param = new SysParamItem();
		param.setSysParamGroup(group);
		map.put("item", param);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		SysParamItem param = paramService.getParamItem(id);
		map.put("item", param);
		return viewPath+"/edit";
	}

	@RequestMapping(value = "/save.do")
	public String save(String action, SysParamItem param, ModelMap map) {
		logger.info( "Enter save.do ..." );

		boolean validate = true;
		if( StringUtils.isBlank(param.getParamName()) ){
			validate = false;
			map.put("message", "参数名称不能为空！");
		}
		else if( StringUtils.isBlank(param.getParamCode()) ){
			validate = false;
			map.put("message", "参数编码不能为空！");
		}
		else if( !paramService.isParamCodeUnique(param.getParamId(), param.getParamCode()) ){
			validate = false;
			map.put("message", "参数编码已经存在！");
		}
		
		if (validate) {
			if( "add".equals(action)){
				paramService.addParamItem(param);
			}		
			else{
				paramService.saveParamItem(param);		
				CommandUtils.checkParamChange(param.getParamCode(), param.getValueText());
			}
			String jsonStr = paramService.getJsonObjStr(param);
			map.put("saveData", jsonStr);
			map.put("result", "success");
		} else {
			map.put("result", "error");
		}
		map.put("item", param);
		map.put("action", action);
		
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/moveUp.do")
	public String moveUp(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter moveUp.do ..." );
		SysParamItem item = paramService.getParamItem(id);
		paramService.moveUpItem(item);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	@RequestMapping(value = "/moveDown.do")
	public String moveDown(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter moveDown.do ..." );
		SysParamItem item = paramService.getParamItem(id);
		paramService.moveDownItem(item);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	} 
	
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		paramService.deleteParamItem(id);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	@RequestMapping(value="/getItemByGroupCode.do")
	public String getItemByGroupCode(HttpServletResponse response) throws IOException{
		SysParamGroup group=powGrpService.getParamGroupByCode("netSearchParam");
		List<SysParamItem> items=paramService.getGroupParamItem(group.getParamGrpId());
		String jsonStr = paramService.getJsonListStr(items);
		response.getWriter().print(jsonStr);
		return null;
	}

}
