/*
 * 文件名： NetInfoManageAction.java
 * 
 * 创建日期： 2014-4-18
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.fas.res.dailyMaintain.netInfoManage.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

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
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;

import smartlink.switchmgt.IpHostProfile;
import smartlink.switchmgt.SwitchInfoBase;
import smartlink.switchmgt.SwitchIpMacKey;
import smartlink.switchmgt.SwitchSecurity;
import smartlink.utils.Util;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.fas.res.dailyMaintain.netInfoManage.entity.IpMacBind;
import com.sino.fas.res.dailyMaintain.netInfoManage.entity.IpMacBindInfo;
import com.sino.fas.res.dailyMaintain.netInfoManage.entity.ParamNetQuery;
import com.sino.fas.res.dailyMaintain.netInfoManage.service.IpMacBindService;
import com.sino.fas.res.dailyMaintain.netInfoManage.service.NetInfoManageService;
import com.sino.fas.res.host.entity.IpHost;
import com.sino.fas.res.host.entity.IpHostInfo;
import com.sino.fas.res.host.entity.ParamIpHost;
import com.sino.fas.res.host.entity.VlanInfo;
import com.sino.fas.res.host.service.IpHostService;
import com.sino.fas.res.host.service.VlanService;
import com.sino.fas.res.net.entity.NcmSwitchPortId;
import com.sino.fas.res.net.entity.NcmSwitchPortInfo;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision$
 *
 * @since 2014-4-18
 */
@Controller
@RequestMapping(value = "/fas/res/dailyMaintain/netInfoManage")
public class NetInfoManageAction {
	private static Logger logger = LoggerFactory.getLogger(NetInfoManageAction.class);
	private String viewPath = "/fas/res/dailyMaintain/netInfoManage";
	
	@Autowired
	private NetInfoManageService netInfoManageService;
	
	@Autowired
	private IpHostService ipHostService;
	
	@Autowired
	private IpMacBindService ipMacBindService;
	
	@Autowired
	private VlanService vlanService;
	
	
	@RequestMapping(value = "/mainTab.do")
	public String mainTab(ModelMap map) {
		logger.info( "Enter mainTab.do ..." );
		map.put("queryType", "ipMacBind");
		return viewPath+"/mainTab";
	}
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map) {
		logger.info( "Enter main.do ..." );
		return viewPath+"/main";
	}
	
	@RequestMapping(value = "/bindMain.do")
	public String bindMain(ModelMap map) {
		logger.info( "Enter bindMain.do ..." );
		return viewPath+"/bindMain";
	}
	
	
	//Ajax分页查询
	@RequestMapping(value = "/searchAjaxPage.do")
	public String searchAjaxPage(PageRequest pageReq, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("Enter searchAjaxPage.do ...");
		Page<NcmSwitchPortInfo> page = netInfoManageService.searchByAjax(pageReq);
		String jsonStr = netInfoManageService.getDataByAjax(page);
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jsonStr);
		return null;
	}
	
	//Ajax macBind分页查询
	@RequestMapping(value = "/searchBindAjaxPage.do")
	public String searchBindAjaxPage(PageRequest pageReq, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("Enter searchBindAjaxPage.do ...");
		Page<IpMacBindInfo> page = netInfoManageService.searchMacBind(pageReq);
		String jsonStr = netInfoManageService.getBindDataByAjax(page);
		response.setContentType("application/json");  
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jsonStr);
		return null;
	}
	
	//高级Ajax分页查询
	@RequestMapping(value = "/advancedSearch.do")
	public String advancedSearch(PageRequest pageReq,ParamNetQuery netQuery, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("Enter advancedSearch.do ...");
		Page<NcmSwitchPortInfo> page = netInfoManageService.searchByAjaxBy(pageReq,netQuery);
		String jsonStr = netInfoManageService.getDataByAjax(page);
		System.out.println(jsonStr);
		response.setContentType("application/json");  
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jsonStr);
		return null;
	}
	
	//高级Ajax bind分页查询
	@RequestMapping(value = "/advancedBindSearch.do")
	public String advancedBindSearch(PageRequest pageReq,ParamNetQuery netQuery, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("Enter advancedBindSearch.do ...");
		Page<IpMacBindInfo> page = netInfoManageService.searchBindByAjaxBy(pageReq,netQuery);
		String jsonStr = netInfoManageService.getBindDataByAjax(page);
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jsonStr);
		return null;
	}
	
	@RequestMapping(value = "/manage.do")
	public String manage(String ipHostId,String switchId,String indexId, ModelMap map){
		logger.info( "Enter manage.do ..." );
		map.put("action", "manage");
		IpHost ipHost = ipHostService.getIpHost(StringUtil.isNullString(ipHostId)?-1:Long.valueOf(ipHostId));
		NcmSwitchPortId id =new NcmSwitchPortId();
		ParamIpHost phost=new ParamIpHost();
		if(ipHost!=null){
			BeanUtils.copyProperties(ipHost, phost);
			VlanInfo vlaninfo=vlanService.getBy(ipHost.getSwitchId(), ipHost.getIfIndex());
			if(vlaninfo!=null){
				String vlan=vlaninfo.getVlan()!=null?String.valueOf(vlaninfo.getVlan()):"1";
				phost.setVlanNo(Integer.valueOf(vlan));
			}
			id.setSwitchId(ipHost.getSwitchId());
			id.setIfIndex(ipHost.getIfIndex());
		}
		String jsonStr =JsonUtils.getJsonObjValue(id);
		map.put("switchId", switchId);
		map.put("indexId", indexId);
		map.put("id", jsonStr);
		map.put("ipHost", phost);
		return viewPath+"/manage";
	}
	
	@RequestMapping(value = "/manageBind.do")
	public String manageBind(String ipHostId,String switchId,String indexId, ModelMap map){
		logger.info( "Enter manageBind.do ..." );
		map.put("action", "manage");
		IpMacBind ipHost = ipMacBindService.getIpMacBind(StringUtil.isNullString(ipHostId)?-1:Long.valueOf(ipHostId));
		NcmSwitchPortId id =new NcmSwitchPortId();
		ParamIpHost phost=new ParamIpHost();
		if(ipHost!=null){
			BeanUtils.copyProperties(ipHost, phost);
			id.setSwitchId(ipHost.getSwitchId());
			id.setIfIndex(ipHost.getIfIndex());
		}
		String jsonStr =JsonUtils.getJsonObjValue(id);
		map.put("switchId", switchId);
		map.put("indexId", indexId);
		map.put("id", jsonStr);
		map.put("ipHost", phost);
		return viewPath+"/manage";
	}
	
	
	@RequestMapping(value = "/save.do")
	public String save(String switchIp,String flag,String ipHostId, ParamIpHost ipHost, ModelMap map) {
		logger.info( "Enter save.do ..." );
		boolean validate = true;
		String action="";
		if( StringUtils.isBlank(ipHost.getMacAddr()) ){
			validate = false;
			map.put("message", "MAC地址不能为空！");
		}
		String message="";
		if( validate ){
			//mac绑定操作
			SwitchIpMacKey  key =null;
			SwitchSecurity ss=new SwitchSecurity();
			if(flag.endsWith("yes")){
				Hashtable bindingresult=ss.setItfBindIpMac(switchIp,ipHost.getIfIndex(), ipHost.getMacAddr(), ipHost.getIpAddr(),"");
				for(Iterator itr = bindingresult.keySet().iterator(); itr.hasNext();){
					key  = (SwitchIpMacKey ) itr.next();
					Properties resultProps = (Properties) bindingresult.get(key);
			         if(resultProps != null)
			         {
			             String bresult = (String) resultProps.get("result"); // "true" - 表示绑定成功, "false" -表示绑定失败, "testmode" - 表示对接测试阶段, 实际不到交换机部署命令,只是返回命令显示
			             message = (String) resultProps.get("cmdoutput"); //需要将此结果回显到前端界面
			             if(bresult.equals("true")){
			            	 action="add";
			            	 message="绑定成功！——";
			             }else{
			 				 message="绑定失败！——";
			             }
			             message=message+message;
			             
			        }
				}
			}else{
				Hashtable bindingresult=ss.deleteItfBindIpMac(switchIp,ipHost.getIfIndex(), ipHost.getMacAddr(), ipHost.getIpAddr(),"");
				for(Iterator itr = bindingresult.keySet().iterator(); itr.hasNext();){
					key = (SwitchIpMacKey ) itr.next();
					Properties resultProps = (Properties) bindingresult.get(key);
			         if(resultProps != null)
			         {
			             String bresult = (String) resultProps.get("result"); // "true" - 表示绑定成功, "false" -表示绑定失败, "testmode" - 表示对接测试阶段, 实际不到交换机部署命令,只是返回命令显示
			             message = (String) resultProps.get("cmdoutput"); //需要将此结果回显到前端界面
			             if(bresult.equals("true")){
								action="update";
								message="解除绑定成功！——";
							}else{
								message="解除绑定失败！——";
							}
			             message=message+message;
			        }
				}
				
			}
			if(StringUtil.isNullString(message)){
				message="绑定/解绑失败！";
			}
			if("add".equals(action)){//绑定mac
				IpMacBind isbind=ipMacBindService.isIpMacBindUnique(ipHost.getSwitchId(),ipHost.getIfIndex(), ipHost.getMacAddr());
				if(isbind!=null) {
					BeanUtils.copyProperties(ipHost, isbind);
					isbind.setBindStatus(1);
					ipMacBindService.saveIpMacBind(isbind);
				}else{
					Date now = new Date();
					ipHost.setModify_Time(now);
					ipHost.setIpValue(Util.ip2long(ipHost.getIpAddr()));
					IpMacBind host=new IpMacBind();
					BeanUtils.copyProperties(ipHost, host);
					host.setBindStatus(1);
					ipMacBindService.addIpMacBind(host);
				}
			}
			if("update".equals(action)){//解绑mac
				IpMacBind hostedit=ipMacBindService.getIpMacBind(ipHost.getIpMacBindId());
				hostedit.setBindStatus(0);
				ipMacBindService.saveIpMacBind(hostedit);
			}
			
			map.put("bindResult", message);
			map.put("result", "success");
		}
		else{
			map.put("ipHost", ipHost);
			map.put("result", "error");			
		}
		map.put("action", action);
		return viewPath+"/manage";
	}
	
	
	@RequestMapping(value = "/unbinds.do")
	public String unbinds(String ids,ModelMap map,HttpServletResponse response) throws IOException{
		String message="";
		String result="fail";
		String[] id = ids.split(",");
		ArrayList<IpHostProfile>  infolist=new ArrayList<IpHostProfile>();
		List<IpMacBind>  hostlist=new ArrayList<IpMacBind>();
		for(int i=0;i<id.length;i++){
			if(!StringUtil.isNullString(id[i])){
				IpMacBind host=new IpMacBind();
				IpHostInfo info=ipMacBindService.getListIpMacBind(Long.valueOf(id[i]),host);
				if(info!=null){
					IpHostProfile profile=new IpHostProfile();
					profile.setIpHostIpAddr(info.getIpAddr());
					profile.setIpHostMacAddr(info.getMacAddr());
					profile.setSwitchInfoId(info.getInfoPointNo());
					profile.setswitchInterfaceName(info.getSwitchIfIndex());
					profile.setSwitchIp(info.getSwitchIp());
					profile.setSwitchPortIndex(Long.valueOf(info.getIfIndexId()!=null?info.getIfIndexId():"-1"));
					infolist.add(profile);
					hostlist.add(host);
				}
			}
		}
		SwitchInfoBase ss=new SwitchInfoBase();
		StringBuffer sb=new StringBuffer();
		Hashtable bindingresult=ss.setPortMacUnBindings(infolist);
		for(Iterator itr = bindingresult.keySet().iterator(); itr.hasNext();){
			SwitchIpMacKey  key = (SwitchIpMacKey ) itr.next();
			Properties resultProps = (Properties) bindingresult.get(key);
	         if(resultProps != null)
	         {
	             String bresult = (String) resultProps.get("result"); // "true" - 表示绑定成功, "false" -表示绑定失败, "testmode" - 表示对接测试阶段, 实际不到交换机部署命令,只是返回命令显示
	             String bmessage = (String) resultProps.get("cmdoutput"); //需要将此结果回显到前端界面
	             if(bresult.equals("true")){
	            	 sb.append(key.getMacAddr()+":解绑成功！<br/>");
	             }else{
	            	 sb.append(key.getMacAddr()+":解绑失败！<br/>");
	             }
	             sb.append(bmessage);
	        }
		}
		message=sb.toString();
		/*if(flag){
			result="success";
			if(hostlist!=null&&!hostlist.isEmpty()){
				ipMacBindService.multiDels(hostlist);
			}
		}else{
			message="解绑出错，请检查配置！";
		}*/
		JSONObject jo=new JSONObject();
		jo.put("result", result);
		jo.put("message", message);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jo.toString());
		return null;
	}
}
