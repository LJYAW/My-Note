/*
 * 文件名： MailServerAction.java
 * 
 * 创建日期： 2014-10-20
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.base.system.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import com.sino.base.system.entity.MailServer;
import com.sino.base.system.service.MailServerService;


/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-10-20
 */
@Controller
@RequestMapping(value = "/system/setup/mailserver")
public class MailServerAction {

	private static String viewPath = "/system/email/smtpserver";
	
	private static Logger logger = LoggerFactory.getLogger(MailServerAction.class);
    
    @Autowired
    private MailServerService mailServerService;
    
//    @Autowired
//	private AlmNotifyByEmailService almNotifyByEmailService ;
    
    @RequestMapping(value="/mainTab.do")
	public String mainTab(ModelMap map){
		map.put("jsonList", "");
		return "/system/email/mainTab";
	}
    
    @RequestMapping(value = "/edit.do")
	public String edit(ModelMap map) {
    	logger.info( "Enter main.do ..." );
    	List<MailServer> list=mailServerService.getMailServerList();
		MailServer mailServer=list.get(0);
		if(null == mailServer){
			mailServer = new MailServer();
		}
		map.put("mailServer", mailServer);
		return viewPath + "/edit";
    }
    
    @RequestMapping(value = "/sendMail.do", method = RequestMethod.GET)
	public String sendMail(Integer mailServerID,HttpServletResponse response) throws IOException{
    	logger.info("sendMail...");
		MailServer mailServer=mailServerService.getById(mailServerID.toString());
    	JSONObject jo=new JSONObject();
    	jo.put("result", mailServerService.sendMail(mailServer));
    	response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().print(jo.toString());
		return null;
    }
    
    @RequestMapping(value = "/sendHtmlMail.do", method = RequestMethod.GET)
	public void sendHtmlMail(String from_Address,String to_Address,String cc_Address,String title,String message,String htmltype,HttpServletResponse response) throws IOException{
    	logger.info("sendMail...");
    	MailServer  mailServer=mailServerService.getById(from_Address);
    	String ti = URLDecoder.decode(URLDecoder.decode(title, "utf-8"), "utf-8");
    	String mess = URLDecoder.decode(URLDecoder.decode(message, "utf-8"), "utf-8");
    	JSONObject jo=new JSONObject();
    	if ("0".equals(htmltype)){
    		jo.put("result", mailServerService.txtMail(mailServer,to_Address,cc_Address,ti,mess));
    	}else {
    		jo.put("result", mailServerService.HTMLMail(mailServer,to_Address,cc_Address,ti,mess));
		}
		response.getWriter().print(jo.toString());
    }
    
//    @RequestMapping(value = "/sendMultiMail.do", method = RequestMethod.GET)
//	public String sendMultiMail(String id,HttpServletResponse response) throws IOException{
//    	logger.info("sendMail...");
//    	
//		AlmNotifyByEmail mail = almNotifyByEmailService.getById(id);
//		MailServer mailServer=mailServerService.getById(mail.getMailServerID().toString());
//		String toAddress = mail.getSendTo();
//		String ccAddress = mail.getCcTo();
//		String ti = mail.getTitleTemplate();
//		String content = mail.getMsgTemplate();
//		Integer mailFormat = mail.getMailFormat();
//		String message = "";
//		if(0 == mailFormat){
//			message = mailServerService.txtMail(mailServer,toAddress,ccAddress,ti,content);
//		}else {
//			message = mailServerService.HTMLMail(mailServer,toAddress,ccAddress,ti,content);
//			
//		}
//    		
//    	JSONObject jo=new JSONObject();
//    	jo.put("result", message);
//    	if(message.contains("发送失败") || message.contains("邮件没有到达")){
//    		jo.put("message", "fail");	
//		}else{
//			jo.put("message", "success");	
//		}
//    	response.setContentType("text/json");  
//		response.setCharacterEncoding("UTF-8"); 
//		response.getWriter().print(jo.toString());
//		return null;
//    }
//    
//    @RequestMapping(value = "/save.do")
//	public String save(MailServer mailServer,String mailServerId,String action,ModelMap map) {
//    	logger.info("save...");
//		mailServer.setMailServerID(Integer.parseInt(mailServerId));
//		mailServerService.saveMailServer(mailServer);
//		AlmConfiguration almconf = new AlmConfiguration();
//		boolean isresult = almconf.updateEventNotification();
//		if("save".equals(action)){
//			map.put("action","save");
//		}else if("sendMail".equals(action)){
//			map.put("action","sendMail");
//		}
//		return viewPath + "/edit";
//	}
    
    @RequestMapping(value="/getAll.do")
	public String getAll(HttpServletResponse response,String idnitemId) throws IOException{
    	logger.info( "Enter getVendorData.do ..." );
		List<MailServer> list = mailServerService.getAll();
		JSONObject jo = new JSONObject();
		JSONArray serverlist = JSONArray.fromObject(list);
		jo.put("serverID", serverlist);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
    
//    @RequestMapping(value="/refreshEmail.do")
//	public String refreshEmail(HttpServletResponse response,String idnitemId) throws IOException{
//    	logger.info( "Enter refreshEmail.do ..." );
//		JSONObject jo = new JSONObject();
//		AlmConfiguration almconf = new AlmConfiguration();
//		boolean isresult = almconf.updateEventNotification();
//		String result="false";
//		if(isresult)
//			result="success";
//		jo.put("result", result);
//		response.setContentType("text/json");
//		response.setCharacterEncoding("UTF-8");
//		response.getWriter().print(jo.toString());
//		return null;
//	}
    
}
