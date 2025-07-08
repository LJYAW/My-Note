package com.sino.base.system.web;

import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 
 * 系统设置跳转功能
 *
 * @author .
 * @date 2017年5月3日 上午10:07:26
 *
 */
@Controller
@RequestMapping(value = "/system/setup")
public class SetupAction {
	private static String viewPath = "/system/setup";
	
	private static Logger logger = LoggerFactory.getLogger(SetupAction.class);
                                      
	
	/**
	 * setupGo
	 * @Description: 设置页面跳转
	 * @param map 封装返回值类型
	 * @return: String 跳转路径
	 */
	@RequestMapping(value = "/main.do")
	public String setupGo(ModelMap map) {
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			return "error/outtime";
		}
		return viewPath+"/main";
	}
	
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/sysItfList.do")
//	public String sysItfList(HttpServletResponse response)  throws IOException{
//		logger.info( "Enter sysItfList.do ..." );
//		
//		List<NetInfo> itfNameList =new ArrayList<NetInfo>();
//		LinkedHashMap itfNameLink = ClientConfiguration.getSingleInstance().getApplianceInterfaceList();
//		if(itfNameLink!=null&&!itfNameLink.isEmpty()){
//			Iterator itData =itfNameLink.keySet().iterator();
//			while(itData!=null&&itData.hasNext()){
//				String key =(String) itData.next();
//				System.out.println("获取到的网卡名称是："+key);
//				NetInfo net =new NetInfo();
//				net.setName(key);
//				itfNameList.add(net);
//			}
//		}
//		String data = JsonUtils.getJsonListInfo(itfNameList, "name", "name");
//		System.out.println("获取到的json串是："+data);
//		response.setContentType("text/json");  
//		response.setCharacterEncoding("UTF-8"); 
//	    response.getWriter().print(data);
//	    return null;         
//	}
	
//	@RequestMapping(value = "/sysItfInfo.do")
//	public String sysItfInfo(String interfaceName, HttpServletResponse response)  throws IOException{
//		logger.info( "Enter sysItfInfo.do ..." );
//
//		//Properties confProps = new Properties();
//		//confProps.setProperty("type", "ipaddr");   // 类型固定为 "ipaddr"
//		//confProps.setProperty("interfacename", interfaceName);  // 要显示的接口名称
//		NetworkInterface info =new NetworkInterface();
//		NetIpInfo entity =new NetIpInfo();
//		LinkedHashMap itfNameLink = ClientConfiguration. getSingleInstance().getApplianceInterfaceList();
//		if(itfNameLink!=null&&!itfNameLink.isEmpty()){
//			Iterator itData =itfNameLink.keySet().iterator();
//			while(itData!=null&&itData.hasNext()){
//				String key =(String) itData.next();
//				info =(NetworkInterface) itfNameLink.get(key);
//				if(key.equals(interfaceName)){
//					entity.setDnsIpaddr(info.getDnsClient());
//					entity.setGateway(info.getGateWay());
//					entity.setStatus(info.getInterfaceStatus());
//					entity.setIpAddr(info.getIPAddress());
//					entity.setMacAddr(info.getMacAddr());
//					entity.setIpMask(info.getNetMask());
//				}
//			}
//		}
//		String data = JsonUtils.getJsonObjInfo(entity, "dnsIpaddr,gateway,status,macAddr,ipMask,ipAddr", "dnsIpaddr,gateway,status,macAddr,ipMask,ipAddr");
//		//String data = JsonUtils.propToJson(currentConf);
//		response.setContentType("text/json");  
//		response.setCharacterEncoding("UTF-8"); 
//	    response.getWriter().print(data);
//	    return null;         
//	}
	
//	@RequestMapping(value = "/sysItfSet.do")
//	public String sysItfSet(String interfacename, String ipAddr, 
//			String ipMask, String gateway, String dnsIpaddr,String name, ModelMap map) throws IOException {
//		logger.info( "Enter sysItfSet.do ..." );
//		SysUser curUser = SystemUtils.getLoginUser();
//		if( curUser == null ){
//			return "error/outtime";
//		}
//
//		boolean validate = true;
//		if( !NetworkUtils.isValidMask(ipMask) ){
//			validate = false;
//			map.put("message", "子网掩码不正确！");
//		}
//		System.out.println("validate 值："+validate);
//		System.out.println("获取到的参数是ipAddr："+ipAddr+"  interfacename:"+name+"   ipMask:"+ipMask+"  gateway:"+gateway+"  dnsipaddr:"+dnsIpaddr);
//		
//		if( validate ){
//			Properties confProps = new Properties();
//			confProps.setProperty("type", "ipaddr");
//			confProps.setProperty("interfacename", name);
//			confProps.setProperty("ipAddr", ipAddr);
//			confProps.setProperty("ipMask", ipMask);
//			confProps.setProperty("gateway", gateway);
//			confProps.setProperty("dnsipaddr", dnsIpaddr);
//
//			ClientConfiguration.getSingleInstance().updateApplianceConfigure(confProps);
//			
//			map.put("result", "success");
//		}
//		else{
//			map.put("result", "error");			
//		}
//		
//		map.put("interfacename", name);
//		
//		return viewPath+"/main";
//	}

//	@RequestMapping(value = "/onRestart.do")
//	public String onRestart(ModelMap map) {
//		ClientConfiguration  ccf=new ClientConfiguration();
//		ccf.applianceRestart();
//		return null;
//	}
//	
//	@RequestMapping(value = "/onRestartBas.do")
//	public String onRestartBas(ModelMap map) {
//		ClientConfiguration  ccf=new ClientConfiguration();
//		ccf.RestartBas();
//		return null;
//	}
//	
//	@RequestMapping(value = "/onClose.do")
//	public String onClose(ModelMap map) {
//		ClientConfiguration  ccf=new ClientConfiguration();
//		ccf.appliancePowerOff();
//		return null;
//	}
}
