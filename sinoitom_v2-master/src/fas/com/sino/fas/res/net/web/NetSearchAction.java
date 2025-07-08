package com.sino.fas.res.net.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.fas.res.net.entity.ParamSearch;
import com.sino.net.dev.DiscoveryDeviceResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sino.distributed.controller.ClientRmiController;
import smartlink.controller.ClientConfiguration;
import smartlink.discovery.DiscoveryService;
import smartlink.discovery.TopologyDiscover;
import smartlink.utils.DBQueryUtil;

@Controller
@RequestMapping(value = "/fas/res/net/search")
public class NetSearchAction {
	private static Logger logger = LoggerFactory
			.getLogger(NetSearchAction.class);

	private String viewPath = "/fas/res/net/search";
	private String viewPath2 = "/fas/res/net/devices";

	@RequestMapping(value = "/main.do")
	public String main(ModelMap map) {
		logger.info("Enter main.do ...");

		return viewPath2 + "/devSearch";
	}

	@RequestMapping(value = "/start.do")
	public String start(String searchRange, String snmpRoKey,
			HttpServletResponse response) throws IOException {
		logger.info("Enter start.do ...");

		String scope = searchRange.trim().replace("；", ";");
		String community = snmpRoKey.trim().replace("；", ";");
		if (community.length() == 0) {
			community = WebFuncUtils.getParamValue("SnmpDefaultRoString");
		}

		Properties resourceProps = new Properties();
		resourceProps.setProperty("searchscope", scope);
		resourceProps.setProperty("community", community);
		
		//清除缓存
		DiscoveryService.getInstance().clearDiscoveryResult();
		DiscoveryService.getInstance().clearDiscoveryStatus();
		ClientConfiguration.getSingleInstance().discoverAllDevices(resourceProps);

//		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		return null;
	}
	
	@RequestMapping(value = "/startSearch.do")
	public String startSearch(String seedIp , String defaultSnmpStr,String searchScope,String threadNum,String timeout,String retries,String ifGenTopo,String searchDepth,String genTopoMethod,
			ModelMap map,HttpServletResponse response) throws IOException {
		logger.info("Enter start.do ...");
        String community="";
        String scope="";
		if(!StringUtil.isNullString(searchScope)){
			String [] array=searchScope.split(";");
			for(int i=0;i<array.length;i++){
				if(i==array.length-1){
					scope+=array[i].split("@")[0];
					community+=array[i].split("@")[1];
				}else{
					scope+=array[i].split("@")[0]+";";
					community+=array[i].split("@")[1]+";";
					
				}
			}
		}
		//"192.168.100.1-192.168.100.15;192.168.100.254;192.168.200.0/24;192.168.300.0/255.255.255.0"
		 Properties topoprops = new Properties();
	     topoprops.put("searchscope", scope);
	     topoprops.put("community", community);
	     topoprops.put("snmpversion", "v1");
	     topoprops.put("seedip", seedIp);
	     topoprops.put("defaultsnmpstr", defaultSnmpStr);
	     topoprops.put("threadnum", threadNum);
	     topoprops.put("searchdepth", searchDepth);
	     topoprops.put("snmptimeout",timeout);
	     topoprops.put("snmpretry", retries);
	     topoprops.put("ifgentopo", ifGenTopo);
	     topoprops.put("genTopoMethod", genTopoMethod);
	     
	     System.out.println("searchscope-->"+ scope);
		 System.out.println("community-->"+ community);
		 System.out.println("snmpversion-->"+ "v1");
		 System.out.println("seedip-->"+ seedIp);
		 System.out.println("defaultsnmpstr-->"+ defaultSnmpStr);
		 System.out.println("threadnum-->"+ threadNum);
		 System.out.println("searchdepth-->"+ searchDepth);
		 System.out.println("snmptimeout-->"+timeout);
		 System.out.println("snmpretry-->"+ retries);
		 System.out.println("ifgentopo-->"+ ifGenTopo);
		 System.out.println("genTopoMethod-->"+ genTopoMethod);
	     
	     
//		 TopologyDiscover discovery = new TopologyDiscover();
//		 
//		 discovery.process(topoprops);
		 
		 ClientConfiguration clientconf = new ClientConfiguration();

		 Properties topoinfo = clientconf.topoDiscovery(topoprops);
		
		 map.put("result", "success");
//		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
//		return viewPath+"/devSearch";
		return null;
	}
	
	
	public static void main(String[] args) {
		
		 Properties topoprops = new Properties();
	     topoprops.put("searchscope", "192.168.100.1-192.168.100.15");
	     topoprops.put("community", "cisco");
	     topoprops.put("snmpversion", "v1");
	     topoprops.put("seedip", "192.168.100.254");
	     topoprops.put("defaultsnmpstr", "cisco");
	     topoprops.put("threadnum", "5");
	     topoprops.put("searchdepth", "3");
	     topoprops.put("ifgentopo", "1");
	     topoprops.put("snmptimeout", "1000");
	     topoprops.put("snmpretry", "2");
	     
	     topoprops.put("retries ", "3");
	     topoprops.put("genTopoMethod", "CDP,LLDP,IPROUTE,FDB,ARP");
//	     DBQueryUtil.getInstance().updateJdbUrl("10.1.2.198", "somdb", 13306, "root", "", "com.mysql.jdbc.Driver");
		 TopologyDiscover discovery = new TopologyDiscover();
		 discovery.process(topoprops);
	}
	
	

	/*
	 * @RequestMapping(value = "/message.do") public String
	 * message(HttpServletResponse response) throws IOException{ logger.info(
	 * "Enter message.do ..." );
	 * 
	 * Properties propResult =
	 * ResourceDiscoveryInterface.getInstance().getDiscoveryInformation
	 * ("device");
	 * 
	 * String data = JsonUtils.propToJson(propResult);
	 * response.getWriter().print(data); return null; }
	 */

	@RequestMapping(value = "/message.do")
	public String message(HttpServletResponse response) throws IOException {
		logger.info("Enter message.do ...");

		ArrayList discoveryResults = DiscoveryService.getInstance().getDiscoveryResults();
		List<ParamSearch> listsearch=new ArrayList<ParamSearch>();
		String fstatus="false";
		for(int j = 0; j< discoveryResults.size(); j++)
		{
				fstatus=DiscoveryService.getInstance().getDeviceDiscoveryStatus();
				ParamSearch search=new ParamSearch();
		        Properties discoveryInfo = (Properties) discoveryResults.get(j);
		        String deviceIp = (String) discoveryInfo.get("DEVICE_IP");
		        search.setDeviceIp(deviceIp);
		        String status = (String) discoveryInfo.get("STATUS");
		         if(status != null )
		         {
		        	 search.setStatus(status);
		         }
		         String deviceName = (String) discoveryInfo.get("DEVICE_NAME");
		         if(deviceName != null )
		         {
		        	 search.setDeviceName(deviceName);
		         }
		         String deviceType = (String) discoveryInfo.get("DEVICE_TYPE");
		         if(deviceType != null )
		         {
		        	 search.setDeviceType(deviceType);
		         }        
		         String  deviceVersion = (String) discoveryInfo.get("DEVICE_VERSION");
		         if(deviceVersion != null )
		         {
		        	 search.setDeviceVersion(deviceVersion);
		         }          
		         String  discoveryType = (String) discoveryInfo.get("DISCOVERY_TYPE");
		         if(discoveryType != null )
		         {
		        	 search.setDiscoveryType(discoveryType);
		         }                   
		         String  interfaceNum = (String) discoveryInfo.get("DEVICE_INTERFACE_NUM");
		         if(interfaceNum != null )
		         {
		        	 search.setInterfaceNum(interfaceNum);
		         }
		         listsearch.add(search);
		         
		}
		//DiscoveryService.getInstance().clearDiscoveryResult();
		//String jsonstr="{\"status\":\"false\",\"result\":[{\"deviceIp\":\"10.1.1.247\",\"deviceName\":\"H3C\",\"deviceType\":\"Switch\",\"deviceVersion\":\"H3C Software Version 3.10\",\"discoveryType\":\"Switch discovery finished\",\"interfaceNum\":\"59\",\"status\":\"Success\"},{\"deviceIp\":\"10.1.1.248\",\"deviceName\":\"SW1-3560rj\",\"deviceType\":\"Switch\",\"deviceVersion\":\"Cisco IOS Version 12.2(44)SE\",\"discoveryType\":\"Switch discovery finished\",\"interfaceNum\":\"31\",\"status\":\"Success\"},{\"deviceIp\":\"10.1.1.249\",\"deviceName\":\"Sino-3560E\",\"deviceType\":\"Switch\",\"deviceVersion\":\"Cisco IOS Version 12.2(53)SE2\",\"discoveryType\":\"Switch discovery finished\",\"interfaceNum\":\"37\",\"status\":\"Success\"},{\"deviceIp\":\"10.1.1.250\",\"deviceName\":\"BJ-B11-CS3560P-2\",\"deviceType\":\"Switch\",\"deviceVersion\":\"Cisco IOS Version 12.2(25)SEB4\",\"discoveryType\":\"Switch discovery finished\",\"interfaceNum\":\"55\",\"status\":\"Success\"},{\"deviceIp\":\"10.1.1.251\",\"deviceName\":\"BJ-JF-CS2950-1\",\"deviceType\":\"Switch\",\"deviceVersion\":\"Cisco IOS Version 12.1(22)EA4\",\"discoveryType\":\"Switch discovery finished\",\"interfaceNum\":\"27\",\"status\":\"Success\"},{\"deviceIp\":\"10.1.1.252\",\"deviceName\":\"BJ-JF-CS3550-2\",\"deviceType\":\"Switch\",\"deviceVersion\":\"Cisco IOS Version 12.2(25)SEE4\",\"discoveryType\":\"Switch discovery finished\",\"interfaceNum\":\"31\",\"status\":\"Success\"},{\"deviceIp\":\"10.1.1.253\",\"deviceName\":\"BJ-JF-CS3550-1\",\"deviceType\":\"Switch\",\"deviceVersion\":\"Cisco IOS Version 12.2(25)SEE4\",\"discoveryType\":\"Switch discovery finished\",\"interfaceNum\":\"30\",\"status\":\"Success\"},{\"deviceIp\":\"10.1.1.254\",\"deviceName\":\"BJ-JF-CS3560P-1.sino-bridge.com\",\"deviceType\":\"Switch\",\"deviceVersion\":\"Cisco IOS Version 12.2(44)SE\",\"discoveryType\":\"Switch discovery finished\",\"interfaceNum\":\"69\",\"status\":\"Success\"}]}";
		JSONObject jo=new JSONObject();
		JSONArray jsonlist= JSONArray.fromObject(listsearch);
		jo.put("status", fstatus);
		jo.put("result", jsonlist);
		System.out.println("get the json:>>"+jo.toString());
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jo.toString());
		return null;
	}
	
	
	@RequestMapping(value="/outputLogs.do")
	public String outputLogs(HttpServletResponse response) throws IOException{
		
		 ClientConfiguration clientconf = new ClientConfiguration();

		 Properties topoInfo = clientconf.getToplogyOutput();
		 
		 String status = (String) topoInfo.get("status");  // 0 - 表示正在进行, 1 - 表示搜索结束
		 
		 StringBuffer output = (StringBuffer) topoInfo.get("result");
		 
		
		 if( output!=null&&output.length()>0){
			 String outputstr = output.toString();
			 outputstr = outputstr.replaceAll("\n", "</br>");
			 System.out.println("------------>"+outputstr+"status----->"+status);
			 
			 JSONObject jo=new JSONObject();
			 jo.put("outputstr", outputstr);
			 jo.put("status", status);
			 response.setContentType("text/json");  
			 response.setCharacterEncoding("UTF-8"); 
			 response.getWriter().print(jo.toString());
		 }
		
		 return null;
		
	}
	
	@RequestMapping(value="/discoverDerviceOutputLogs.do")
	public String discoverDerviceOutputLogs(HttpServletResponse response) throws IOException{
		 
		 String output =DiscoveryDeviceResult.getInstance().getDiscoveryResults();
		 //RMI
//		ClientRmiController contr=new ClientRmiController();
//		String output=contr.searchDeviceMessageOutput();
		 if( output!=null&&output.length()>0){
			 String outputstr = output;
			 outputstr = outputstr.replaceAll("\n", "</br>");
//			 System.out.println("------------>"+outputstr+"status----->"+status);
			 JSONObject jo=new JSONObject();
			 jo.put("outputstr", outputstr);
			 response.setContentType("text/json");  
			 response.setCharacterEncoding("UTF-8"); 
			 response.getWriter().print(jo.toString());
		 }
		
		 return null;
		
	}
}
