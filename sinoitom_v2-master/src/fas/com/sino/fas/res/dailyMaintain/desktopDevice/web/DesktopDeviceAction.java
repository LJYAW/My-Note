package com.sino.fas.res.dailyMaintain.desktopDevice.web;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

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
import org.springside.modules.orm.PropertyFilter;

import smartlink.controller.ClientConfiguration;
import smartlink.deviceconfig.action.DeviceConfigure;
import smartlink.discovery.DiscoveryService;
import smartlink.switchmgt.CableInfoBase;
import smartlink.switchmgt.IpHostProfile;
import smartlink.switchmgt.SwitchInfoBase;
import smartlink.switchmgt.SwitchIpMacKey;

import com.sino.base.common.util.DateUtil;
import com.sino.base.common.util.ExpExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.OrganService;
import com.sino.fas.res.dailyMaintain.netInfoManage.service.IpMacBindService;
import com.sino.fas.res.host.entity.IpHost;
import com.sino.fas.res.host.entity.IpHostInfo;
import com.sino.fas.res.host.service.IpHostService;
import com.sino.fas.res.net.entity.NcmDevices;
import com.sino.fas.res.net.entity.NcmSnmpCredentials;
import com.sino.fas.res.net.service.NcmDevicesService;
import com.sino.fas.res.net.service.SnmpCredService;

@Controller
@RequestMapping(value = "/fas/res/dailyMaintain/desktopDevice")
public class DesktopDeviceAction {
	private static Logger logger = LoggerFactory.getLogger(DesktopDeviceAction.class);
	private String viewPath = "/fas/res/dailyMaintain/desktopDevice";
	
	@Autowired
	private IpHostService ipHostService;
	
	@Autowired
	private NcmDevicesService ncmDevicesService;	
	
	@Autowired
	private SnmpCredService snmpService;	
	
	@Autowired
	private OrganService organService;
	
	@Autowired
	private IpMacBindService ipMacBindService;
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map) {
		logger.info( "Enter main.do ..." );
		return viewPath+"/main";
	}
	
	@RequestMapping(value = "/searchMain.do")
	public String searchMain(String organId,String switchId,ModelMap map){
		map.put("orgId", organId);
		map.put("switchId", switchId);
		return viewPath+"/main";
	}
	
	
	
	@RequestMapping(value="/searchDeskPage.do")
	public String searchDeskPage(String orgId,long switchId,PageRequest pageReq, HttpServletResponse response)throws IOException {
		long start = System.currentTimeMillis();
		logger.info("Enter searchDeskPage.do ...");
		List<String> orgIds=organService.getHostSubOrgIds(orgId);
		//Page<IpHostInfo> page = ipHostService.searchByAjax(pageReq,orgIds,switchId);
		Page<IpHostInfo> page = ipHostService.searchDataByAjax(pageReq,orgIds,switchId);
		String jsonStr = ipHostService.getIpHostByAjax(page);
		response.getWriter().print(jsonStr);
		long end = System.currentTimeMillis();
		System.out.println("数据解析时间差---------》" + (end - start));
		return null;
	}
	
	@RequestMapping(value = "/add.do")
	public String add(String id,ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		IpHostInfo ipHostInfo =new IpHostInfo();
		if(!StringUtil.isNullString(id)){
			ipHostInfo=ipHostService.getListIpHost(Long.valueOf(id));
		}
		map.put("ipHostInfo", ipHostInfo);
		return viewPath+"/add";
	}
	
	@RequestMapping(value = "/save.do")
	public String save(String action,String ipHostId, IpHostInfo ipHostInfo, ModelMap map) {
		logger.info( "Enter save.do ..." );
		boolean validate = true;
		boolean flag=false;
		String result="";
		String message="";
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		else if( StringUtils.isBlank(ipHostInfo.getMacAddr()) ){
			validate = false;
			map.put("message", "MAC地址不能为空！");
		}
		else if( StringUtils.isBlank(ipHostInfo.getIpAddr()) ){
			validate = false;
			map.put("message", "IP地址不能为空！");
		}
		/*else if( StringUtils.isBlank(ipHostInfo.getHostName()) ){
			validate = false;
			map.put("message", "设备名称不能为空！");
		}*/
		if( validate ){
			IpHost ipHostEdit= ipHostService.getIpHost(Long.valueOf(ipHostId));
			if( ipHostEdit == null ){
				map.put("result", "error");								
				map.put("message", "该配置已经被删除！");
				return viewPath+"/add";
			}
			ipHostEdit.setHostName(ipHostInfo.getHostName());
			ipHostEdit.setUserId(ipHostInfo.getUserId());
			ipHostEdit.setAssertNO(ipHostInfo.getAssertNO());
			ipHostEdit.setSerialNO(ipHostInfo.getSerialNO());
			ipHostEdit.setOrgId(ipHostInfo.getOrgId());
			
			/*ipHostEdit.setIfIndex(Long.valueOf(ipHostInfo.getIfIndexId()));
			ipHostEdit.setIpAddr(ipHostInfo.getIpAddr());
			ipHostEdit.setMacAddr(ipHostInfo.getMacAddr());
			ipHostEdit.setModify_Time(now);
			ipHostEdit.setIpValue(Util.ip2long(ipHostInfo.getIpAddr()));*/
			
			ipHostService.saveIpHost(ipHostEdit);
			IpHostInfo listInfo =ipHostService.getListIpHost(ipHostEdit.getIpHostId());
			
			ArrayList<IpHostProfile>  infolist=new ArrayList<IpHostProfile>();
			IpHostProfile profile=new IpHostProfile();
			profile.setIpHostIpAddr(listInfo.getIpAddr());
			profile.setIpHostMacAddr(listInfo.getMacAddr());
			profile.setSwitchInfoId(listInfo.getInfoPointNo());
			profile.setswitchInterfaceName(listInfo.getSwitchIfIndex());
			profile.setSwitchIp(listInfo.getSwitchIp());
			profile.setSwitchPortIndex(Long.valueOf(listInfo.getIfIndexId()!=null?listInfo.getIfIndexId():"-1"));
			profile.setVlanId(listInfo.getVlanId());
			infolist.add(profile);
			SwitchInfoBase switchInfoInterface=new SwitchInfoBase();
			Hashtable bindingresult=switchInfoInterface.changeDeviceIpHosts(infolist);
			for(Iterator itr = bindingresult.keySet().iterator(); itr.hasNext();){
				SwitchIpMacKey  key = (SwitchIpMacKey ) itr.next();
				Properties resultProps = (Properties) bindingresult.get(key);
		         if(resultProps != null)
		         {
		             String bresult = (String) resultProps.get("result"); // "true" - 表示绑定成功, "false" -表示绑定失败, "testmode" - 表示对接测试阶段, 实际不到交换机部署命令,只是返回命令显示
		             if(bresult.equals("true")){
		            	 flag=true;
		             }
		            message = (String) resultProps.get("cmdoutput"); //需要将此结果回显到前端界面
		        }
			}
			
			String jsonStr="";
			if(flag){
				result="success";
				jsonStr=JsonUtils.getJsonObjValue(listInfo);
			}else{
				result="error";
				message="添加设备出错，请检查配置！";
			}
			map.put("saveData", jsonStr);
			map.put("result", result);
			map.put("message", message);
			map.put("ipHostInfo", listInfo);
		}
		else{
			map.put("ipHostInfo", ipHostInfo);
			map.put("result", "error");			
		}
		
		map.put("action", action);
		return viewPath+"/add";
	}
	
	@RequestMapping(value = "/swapDevice.do")
	public String swapDevice(String ids,ModelMap map,HttpServletResponse response) throws IOException{
		String message="";
		String result="fail";
		String[] id = ids.split(",");
		ArrayList<IpHostProfile>  infolist=new ArrayList<IpHostProfile>();
		for(int i=0;i<id.length;i++){
			if(!StringUtil.isNullString(id[i])){
				IpHostInfo info=ipHostService.getListIpHost(Long.valueOf(id[i]));
				if(info!=null){
					IpHostProfile profile=new IpHostProfile();
					profile.setIpHostIpAddr(info.getIpAddr());
					profile.setIpHostMacAddr(info.getMacAddr());
					profile.setSwitchInfoId(info.getInfoPointNo());
					profile.setswitchInterfaceName(info.getSwitchIfIndex());
					profile.setSwitchIp(info.getSwitchIp());
					profile.setSwitchPortIndex(Long.valueOf(info.getIfIndexId()!=null?info.getIfIndexId():"-1"));
					profile.setVlanId(info.getVlanId());
					infolist.add(profile);
				}
			}
		}
		SwitchInfoBase switchInfoInterface=new SwitchInfoBase();
		boolean flag=false;
		Hashtable bindingresult=switchInfoInterface.changeDeviceIpHosts(infolist);
		for(Iterator itr = bindingresult.keySet().iterator(); itr.hasNext();){
			SwitchIpMacKey  key = (SwitchIpMacKey ) itr.next();
			Properties resultProps = (Properties) bindingresult.get(key);
	         if(resultProps != null)
	         {
	             String bresult = (String) resultProps.get("result"); // "true" - 表示绑定成功, "false" -表示绑定失败, "testmode" - 表示对接测试阶段, 实际不到交换机部署命令,只是返回命令显示
	             if(bresult.equals("true")){
	            	 flag=true;
	             }
	            message = (String) resultProps.get("cmdoutput"); //需要将此结果回显到前端界面
	        }
		}
		
		if(flag){
			result="success";
		}else{
			message="更换设备出错，请检查配置！";
		}
		JSONObject jo=new JSONObject();
		jo.put("result", result);
		jo.put("message", message);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value = "/swapLocation.do")
	public String swapLocation(String ids,ModelMap map,HttpServletResponse response) throws IOException{
		String message="";
		String result="fail";
		String[] id = ids.split(",");
		ArrayList<IpHostProfile>  infolist=new ArrayList<IpHostProfile>();
		for(int i=0;i<id.length;i++){
			if(!StringUtil.isNullString(id[i])){
				IpHostInfo info=ipHostService.getListIpHost(Long.valueOf(id[i]));
				if(info!=null){
					IpHostProfile profile=new IpHostProfile();
					profile.setIpHostIpAddr(info.getIpAddr());
					profile.setIpHostMacAddr(info.getMacAddr());
					profile.setSwitchInfoId(info.getInfoPointNo());
					profile.setswitchInterfaceName(info.getSwitchIfIndex());
					profile.setSwitchIp(info.getSwitchIp());
					profile.setSwitchPortIndex(Long.valueOf(info.getIfIndexId()!=null?info.getIfIndexId():"-1"));
					profile.setVlanId(info.getVlanId());
					infolist.add(profile);
				}
			}
		}
		SwitchInfoBase switchInfoInterface=new SwitchInfoBase();
		boolean flag=false;
		Hashtable bindingresult=switchInfoInterface.changeDeviceIpHosts(infolist);
		for(Iterator itr = bindingresult.keySet().iterator(); itr.hasNext();){
			SwitchIpMacKey  key = (SwitchIpMacKey ) itr.next();
			Properties resultProps = (Properties) bindingresult.get(key);
	         if(resultProps != null)
	         {
	             String bresult = (String) resultProps.get("result"); // "true" - 表示绑定成功, "false" -表示绑定失败, "testmode" - 表示对接测试阶段, 实际不到交换机部署命令,只是返回命令显示
	             if(bresult.equals("true")){
	            	 flag=true;
	             }
	            message = (String) resultProps.get("cmdoutput"); //需要将此结果回显到前端界面
	        }
		}
		
		if(flag){
			result="success";
		}else{
			message="更换位置出错，请检查配置！";
		}
		JSONObject jo=new JSONObject();
		jo.put("result", result);
		jo.put("message", message);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jo);
		return null;
	}
	
	@RequestMapping(value = "/binds.do")
	public String binds(String ids,ModelMap map,HttpServletResponse response) throws IOException{
		String message="";
		String result="fail";
		String[] id = ids.split(",");
		ArrayList<IpHostProfile>  infolist=new ArrayList<IpHostProfile>();
		List<IpHost>  hostlist=new ArrayList<IpHost>();
		for(int i=0;i<id.length;i++){
			if(!StringUtil.isNullString(id[i])){
				IpHostInfo info=ipHostService.getListIpHost(Long.valueOf(id[i]));
				if(info!=null){
					IpHostProfile profile=new IpHostProfile();
					profile.setIpHostIpAddr(info.getIpAddr());
					profile.setIpHostMacAddr(info.getMacAddr());
					profile.setSwitchInfoId(info.getInfoPointNo());
					profile.setswitchInterfaceName(info.getSwitchIfIndex());
					profile.setSwitchIp(info.getSwitchIp());
					profile.setSwitchPortIndex(Long.valueOf(info.getIfIndexId()!=null?info.getIfIndexId():"-1"));
					profile.setVlanId(info.getVlanId());
					infolist.add(profile);
				}
			}
		}
		
		SwitchInfoBase ss=new SwitchInfoBase();
		StringBuffer sb=new StringBuffer();
		Hashtable bindingresult=ss.setPortMacBindings(infolist);
		for(Enumeration ee = bindingresult.keys(); ee.hasMoreElements();){
			SwitchIpMacKey  key = (SwitchIpMacKey ) ee.nextElement();
			Properties resultProps = (Properties) bindingresult.get(key);
	         if(resultProps != null)
	         {
	             String bresult = (String) resultProps.get("result"); // "true" - 表示绑定成功, "false" -表示绑定失败, "testmode" - 表示对接测试阶段, 实际不到交换机部署命令,只是返回命令显示
	             String bmessage = (String) resultProps.get("cmdoutput"); //需要将此结果回显到前端界面
	             if(bresult.equals("true")){
	            	 sb.append(key.getMacAddr()+":绑定成功！——"+bmessage+"<br/>");
	            	 IpHost host=ipHostService.getIpHostByIfMac(key.getSwitchIp(),key.getSwitchPort(),key.getMacAddr());
	            	 if(host!=null){
	            		 hostlist.add(host);
	            	 }
	             }else{
	            	 sb.append(key.getMacAddr()+":绑定失败！——"+bmessage+"<br/>");
	             }
	        }
		}
		if(hostlist!=null&&!hostlist.isEmpty()){
			ipMacBindService.multiSave(hostlist);
		}
		message=sb.toString();
		if(StringUtil.isNullString(message)){
			message="绑定/解绑失败！";
		}
		JSONObject jo=new JSONObject();
		jo.put("result", result);
		jo.put("message", message);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value = "/unbinds.do")
	public String unbinds(String ids,ModelMap map,HttpServletResponse response) throws IOException{
		String message="";
		String result="fail";
		String[] id = ids.split(",");
		ArrayList<IpHostProfile>  infolist=new ArrayList<IpHostProfile>();
		List<IpHost>  hostlist=new ArrayList<IpHost>();
		for(int i=0;i<id.length;i++){
			if(!StringUtil.isNullString(id[i])){
				IpHostInfo info=ipHostService.getListIpHost(Long.valueOf(id[i]));
				if(info!=null){
					IpHostProfile profile=new IpHostProfile();
					profile.setIpHostIpAddr(info.getIpAddr());
					profile.setIpHostMacAddr(info.getMacAddr());
					profile.setSwitchInfoId(info.getInfoPointNo());
					profile.setswitchInterfaceName(info.getSwitchIfIndex());
					profile.setSwitchIp(info.getSwitchIp());
					profile.setSwitchPortIndex(Long.valueOf(info.getIfIndexId()!=null?info.getIfIndexId():"-1"));
					profile.setVlanId(info.getVlanId());
					infolist.add(profile);
				}
			}
		}
		SwitchInfoBase ss=new SwitchInfoBase();
		StringBuffer sb=new StringBuffer();
		Hashtable bindingresult=ss.setPortMacUnBindings(infolist);
		System.out.println("第1步：》》");
		for(Iterator itr = bindingresult.keySet().iterator(); itr.hasNext();){
			SwitchIpMacKey  key = (SwitchIpMacKey ) itr.next();
			Properties resultProps = (Properties) bindingresult.get(key);
			System.out.println("第2步：》》");
	         if(resultProps != null)
	         {
	             String bresult = (String) resultProps.get("result"); // "true" - 表示绑定成功, "false" -表示绑定失败, "testmode" - 表示对接测试阶段, 实际不到交换机部署命令,只是返回命令显示
	             String bmessage = (String) resultProps.get("cmdoutput"); //需要将此结果回显到前端界面
	             if(bresult.equals("true")){
	            	 sb.append(key.getMacAddr()+":解绑成功！——"+bmessage+"<br/>");
	            	 IpHost host=ipHostService.getIpHostByIfMac(key.getSwitchIp(),key.getSwitchPort(),key.getMacAddr());
	            	 if(host!=null){
	            		 hostlist.add(host);
	            	 }
	             }else{
	            	 sb.append(key.getMacAddr()+":解绑失败！——"+bmessage+"<br/>");
	             }
	             System.out.println("第3步：》》"+sb);
	        }
		}
		message=sb.toString();
		if(hostlist!=null&&!hostlist.isEmpty()){
			ipMacBindService.multiDelete(hostlist);
		}
		if(StringUtil.isNullString(message)){
			message="绑定/解绑失败！";
		}
		System.out.println("第4步：》》"+message);
		JSONObject jo=new JSONObject();
		jo.put("result", result);
		jo.put("message", message);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value = "/reflesh.do")
	public void reflesh(String switchId,String orgId, HttpServletResponse response) throws IOException{     
		logger.info( "Enter reflesh.do ..." );
		boolean res = true;
		boolean flag=!StringUtil.isNullString(switchId)&&!switchId.equals("-1");
		String defaultSnmpRoKey = WebFuncUtils.getParamValue("SnmpDefaultRoString", "public");
		int defaultSnmpPort = Integer.parseInt(WebFuncUtils.getParamValue("SnmpDefaultPort", "161"));
		StringBuilder sb = new StringBuilder();
		ArrayList<String> scopelist=new ArrayList<String>();
		
		String[]  sid=null;
		if(flag){
			sid=new String[1];
			sid[0]=switchId;
		}else{
			List<String> orgIds=organService.getSubOrgIds(orgId);
			List<NcmDevices> list =new ArrayList<NcmDevices>();
			if(orgIds!=null&&!orgIds.isEmpty()){
				list = ncmDevicesService.listSwitchsByOrgIds(orgIds);
				if(list!=null&&!list.isEmpty()){
					sid=new String[list.size()];
					for(int i=0,n=list.size();i<n;i++){
						sid[i]=list.get(i).getDevId().toString();
					}
				}
			}
		}
		
		for( String id:sid ){
			NcmDevices device = ncmDevicesService.getById(Long.parseLong(id));
			if( device != null ){
				String ip = device.getDevIpAddr();
				String gatewayIp =device.getGatewayIP();
				if( StringUtils.isBlank(ip) ){
					res = false;
					sb.append("设备").append(device.getDevName()).append("的IP地址为空！");
					continue;
				}
				String arpGetMode="snmp";
				int arpGet=device.getGetArpMode();
				if(arpGet==2)
					arpGetMode="telnet";
				String snmpRo = defaultSnmpRoKey;
				int snmpPort = defaultSnmpPort;
				if( device.getSnmpCredId() != null ){
					NcmSnmpCredentials snmpCred = snmpService.getSnmpCred(device.getSnmpCredId());
					if( snmpCred != null ){
						snmpRo = snmpCred.getSnmpRoStr();
						snmpPort = snmpCred.getSnmpPort();
					}
				}
				String sc=ip+"@"+snmpRo+"@"+gatewayIp+"@"+snmpRo+"@"+arpGetMode;
			//	System.out.println("get the sc String:>>"+sc);
				scopelist.add(sc);
			}
		}
		
		if(!res){
			String result = String.format(JsonUtils.JSON_RESULT_FALSE_MSGMDL, sb.toString());
			response.getWriter().print(result);
		}
		else{
			//清除缓存
			DiscoveryService.getInstance().clearDiscoveryResult();
			DiscoveryService.getInstance().clearDiscoveryStatus();
			ClientConfiguration.getSingleInstance().refreshSwitch(scopelist);
			response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		}

	}  
	
//	@RequestMapping(value = "/setupMsg.do")
//	public String setupMsg(String switchIp,ModelMap map,HttpServletResponse response) throws IOException{     
//		logger.info( "Enter reflesh.do ..." );
//		String message="配置出错！";
//		String key=switchIp;
//		StringBuffer value=new StringBuffer("");
//		if(!StringUtil.isNullString(switchIp)){
//			SwitchInfoBase base = new SwitchInfoBase();
//			
//			value = base.getDeviceConfigure(switchIp);
//		}
//		if(value.length()>0){
//			 map.put("value", value.toString().replaceAll("\n","<br>"));
//			 message="";
//		}
//		 map.put("key", key);
//		
//		 map.put("message", message);
//		 
//		 return viewPath+"/setupMsg";
//	}  
	
	
	@RequestMapping(value = "/setupMsg.do")
	public String setupMsg(String switchIp,ModelMap map,HttpServletResponse response) throws IOException{     
		logger.info( "Enter reflesh.do ..." );
		String message="配置出错！";
		String key=switchIp;
//		StringBuffer value=new StringBuffer("");
		String value="";
		 JSONObject jo=new JSONObject();
		if(!StringUtil.isNullString(switchIp)){
//			SwitchInfoBase base = new SwitchInfoBase();
//			
//			value = base.getDeviceConfigure(switchIp);
			 Properties configresult = DeviceConfigure.getInstance().getDeviceCurrentConfigure(switchIp);
			  message= configresult.getProperty("result"); //result = 0表示成功,200-表示未知错误
			    if(!StringUtil.isNullString(message)&&message.equals("200")){
			    	message="200-位置错误";
			    }
			  value= configresult.getProperty("message");
		}
		if(!StringUtil.isNullString(value)){
//			 map.put("value", value.toString().replaceAll("\n","<br>"));
			 jo.put("value", value.replaceAll("\n","<br>"));
//			 message="";
		}else{
			jo.put("value", "未获取到配置文件");
		}
//		 map.put("key", key);
		
//		 map.put("message", message);
		 
		 
		 jo.put("key", key);
		 jo.put("message", message);
		 response.setContentType("text/json");
		 response.setCharacterEncoding("UTF-8"); 
		 response.getWriter().print(jo.toString());
		 return null;
	}  
	
	
	@RequestMapping(value="/setupMsgMain.do")
	public String setupMsgMain(String switchIp,ModelMap map){
		map.put("switchIp", switchIp);
		return viewPath+"/setupMsg"; 
	}
	
	
	
	@RequestMapping(value = "/refreshInfoPoint.do")
	public void refreshInfoPoint(String ids, HttpServletResponse response) throws IOException{     
		logger.info( "Enter refreshInfoPoint.do ..." );
		String message="";
		String result="fail";
		String[] id = ids.split(",");
		ArrayList<IpHostProfile>  infolist=new ArrayList<IpHostProfile>();
		for(int i=0;i<id.length;i++){
			if(!StringUtil.isNullString(id[i])){
				IpHostInfo info=ipHostService.getListIpHost(Long.valueOf(id[i]));
				if(info!=null){
					IpHostProfile profile=new IpHostProfile();
					profile.setIpHostId(info.getIpHostId());
					profile.setSwitchId(info.getSwitchId());
					profile.setSwitchPortIndex(Long.valueOf(info.getIfIndexId()!=null?info.getIfIndexId():"-1"));
					infolist.add(profile);
				}
			}
		}
		CableInfoBase switchInfoInterface=new CableInfoBase();
		boolean flag=switchInfoInterface.refreshInfoPoint(infolist);
		if(flag){
			result="success";
		}else{
			message="更换设备出错，请检查配置！";
		}
		JSONObject jo=new JSONObject();
		jo.put("result", result);
		jo.put("message", message);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jo.toString());
   }
	
	@RequestMapping(value = "/search.do")
	public String search(PageRequest pageReq,HttpServletRequest resquest, HttpServletResponse response) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		List<IpHost> list = ipHostService.searchInfo(filters);
//		List<IpHostInfo> listInfo =new ArrayList<IpHostInfo>();
//		if(list.size()>0){
//			for(int i=0;i<list.size();i++){
//				IpHostInfo listIpHostInfo =ipHostService.getListIpHost(list.get(i).getIpHostId());
//				listInfo.add(listIpHostInfo);
//			}
//		}
		
		List<IpHostInfo> listInfo=ipHostService.getPageResult(list);
		
		Page<IpHostInfo> page = new Page<IpHostInfo>(pageReq);
		//Page page = ipHostService.searchIpHost(pageReq, filters);
		page.setResult(listInfo);
		page.setTotalItems(listInfo.size());
		String jsonStr = ipHostService.getIpHostByAjax(page);
		//System.out.println("----->"+jsonStr);
		//JSONArray jsonStr= JSONArray.fromObject(listInfo);
		response.getWriter().print(jsonStr.toString());
	    return null;         
	}
	
//	导出
	@RequestMapping(value = "/exportExcel.do")
	public String exportExcel(String rows,ModelMap map,HttpServletResponse response) throws IOException{     
		logger.info( "Enter exportExcel.do ..." );
		
//		String rowsStr=new String(rows.trim().getBytes("ISO-8859-1"), "UTF-8"); 
		
		@SuppressWarnings("unchecked")
		List<IpHostInfo> desktopDeviceList = JsonUtils.getDTOList(rows, IpHostInfo.class);
		
		String[] headers = {"机构名称","使用人","设备类型","IP地址","MAC地址","信息点编号","接入交换机","交换机接口","Vlan","Vlan名称","接口状态"};//导出字段名称
		String attr="orgName,loginName,ipHostType,ipAddr,macAddr,infoNo,switchIp,switchIfIndex,vlanId,vlanName,adminStatus";//导出映射字段
		int[] exlColWidths=new int[]{4800,6800,4800,4800,4800,4800,4800,4800,4800};//excel 列宽132*36≈4800相当于132px
		String fileName="desktopDevice_"+DateUtil.getExpTimeShow(new Timestamp(System.currentTimeMillis()))+".xls";//excel文件名
	    try {  
	          response.setContentType("octets/stream");
	          response.addHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gb2312"), "UTF-8"));
	          OutputStream out = response.getOutputStream();  
	          ExpExcelUtil.expExcel("桌面设备信息", headers, exlColWidths, desktopDeviceList, attr, out);
	          out.close();
	      } catch (Exception e) {
	    	  System.out.println("导出excel出错。。。");
	          e.printStackTrace();  
	      }
	    return null;
	}
	
}
