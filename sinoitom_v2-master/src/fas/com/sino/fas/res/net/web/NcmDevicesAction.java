package com.sino.fas.res.net.web;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;

import sino.distributed.controller.ClientRmiController;
import smartlink.cmdb.devoperation.CmdbDeviceOperation;
import smartlink.controller.ClientConfiguration;
import smartlink.discovery.DiscoveryService;
import smartlink.discovery.TopologyDiscover;
import smartlink.utils.Util;
//
//import com.sino.alarm.analysis.connUtils.MessageCache;
//import com.sino.alarm.analysis.connUtils.ObtainCmdResult;
//import com.sino.alarm.analysis.connUtils.SSHConnection;
//import com.sino.alarm.analysis.connUtils.TelnetConnection;
//import com.sino.alarm.analysis.entity.ParamAccessor;
import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.DateUtil;
import com.sino.base.common.util.ExpExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.JqPageBean;
import com.sino.base.system.entity.OrgEmployee;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.entity.SysParamItem;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.EmployeeService;
import com.sino.base.system.service.OrganService;
import com.sino.base.system.service.ParamItemService;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.product.prodModel.entity.VendorProdModel;
import com.sino.cmdb.product.prodModel.service.VendorProdModelService;
import com.sino.cmdb.vendor.entity.CmdbVendor;
import com.sino.cmdb.vendor.service.CmdbVendorService;
import com.sino.fas.res.net.connUtils.MessageCache;
import com.sino.fas.res.net.connUtils.ObtainCmdResult;
import com.sino.fas.res.net.connUtils.ParamAccessor;
import com.sino.fas.res.net.connUtils.SSHConnection;
import com.sino.fas.res.net.connUtils.TelnetConnection;
import com.sino.fas.res.net.entity.NcmDevAccessUser;
import com.sino.fas.res.net.entity.NcmDevInterfaces;
import com.sino.fas.res.net.entity.NcmDevices;
import com.sino.fas.res.net.entity.NcmFDBTableInfo;
import com.sino.fas.res.net.entity.NcmSnmpCredentials;
import com.sino.fas.res.net.entity.ScanParam;
import com.sino.fas.res.net.service.FDBTableService;
import com.sino.fas.res.net.service.NcmDevAccessUserService;
import com.sino.fas.res.net.service.NcmDevIfService;
import com.sino.fas.res.net.service.NcmDevicesService;
import com.sino.fas.res.net.service.SnmpCredService;
import com.sino.net.dev.DiscoveryDeviceResult;
import com.sino.snmp.utils.DiscoveryRouterUtil;
import com.sino.snmp.utils.DiscoveryUtil;
import com.sino.snmp.utils.JdbcConnection;
import com.sino.snmp.utils.NetworkUtil;
import com.sino.snmp.utils.SwitchUtil;

@Controller
@RequestMapping(value="/fas/res/net/devices/")
public class NcmDevicesAction {

	private static Logger logger = LoggerFactory.getLogger(NcmDevicesAction.class);
	private String viewPath = "/fas/res/net/devices";
	private String viewPath2 = "/fas/res/net/devices/router";
	private String viewPath3 = "/fas/res/net/devices/firewall";
	@Autowired
	private OrganService organService;
	
	@Autowired
	private NcmDevicesService ncmDeviceService;
	
	@Autowired
	private NcmDevIfService ncmDevIfService;
	
	@Autowired
	private VendorProdModelService vendorProdModelService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ResItemService resItemService;
	
	@Autowired
	private CmdbVendorService cmdbVendorService;
	
	@Autowired
	private SnmpCredService snmpCredService;
	
	@Autowired
	private NcmDevAccessUserService ncmDevAccessUserService;	
	
	@Autowired
	private ParamItemService paramItemService;
	
	@Autowired
	private FDBTableService fDBTableService;
	
	
	 ObtainCmdResult ocr = null;
	 ParamAccessor accessInfo = null;
	 List<String> cmdLine = new ArrayList<String>();
	 String content = "";
	
	@RequestMapping(value = "/main.do")
	public String main(ModelMap map,String orgId,boolean checkBox ,String menuId,String devType) {
		logger.info( "Enter main.do ..." );
		map.put("orgId", orgId);
		map.put("checkBox", checkBox);
		map.put("devType", devType);
		WebFuncUtils.titleContent(menuId,map);
		if(devType.equals("2")){
			return viewPath+"/main";
		}else if(devType.equals("3")){
			return viewPath2+"/main";
		}else if(devType.equals("4")){
			return viewPath3+"/main";
		}
		return null;
	} 
	
	@RequestMapping(value="/getSwitchTree.do")
	public String getSwitchTree(ModelMap map,String menuId){
		map.put("menuId", menuId);
		return viewPath+"/orgTree";
	}
	
	@RequestMapping(value="/getRouterTree.do")
	public String getRouterTree(ModelMap map,String menuId){
		map.put("menuId", menuId);
		return viewPath2+"/orgTree";
	}
	
	@RequestMapping(value="/getFirewallTree.do")
	public String getFirewallTree(ModelMap map,String menuId){
		map.put("menuId", menuId);
		return viewPath3+"/orgTree";
	}
	
	//搜索交换机
	@RequestMapping(value = "/scanGo.do")
	public String scanGo(ModelMap map) {
		logger.info( "Enter scanGo.do ..." );
		map.put("queryType", "scan");
		return viewPath+"/scanTab";
	}
	
	
	//搜索路由器
	@RequestMapping(value = "/routerscan.do")
	public String scan(ModelMap map) {
		logger.info( "Enter scan.do ..." );
		return viewPath2+"/scan";
	}
	
	@RequestMapping(value = "/scanScope.do")
	public String scanScope(ModelMap map) {
		logger.info( "Enter scanScope.do ..." );
		return viewPath+"/scanScope";
	}
	
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map,String orgId) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		NcmDevices device = new NcmDevices();
		if(!StringUtil.isNullString(orgId)){
			device.setOrgId(orgId);
		}
		map.put("device", device);
		return viewPath+"/edit";
	}
	
	
	@RequestMapping(value = "/edit.do")
	public String edit(Long id, String topoEdit,ModelMap map) {  //topoEdit topo管理中的修改
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		NcmDevices device = ncmDeviceService.getById(id);
		if(device!=null){
			VendorProdModel prodModel=vendorProdModelService.getByModelOID(device.getModelOid());
			map.put("prodModel", prodModel);
			map.put("device", device);
		}
		if(!StringUtil.isNullString(topoEdit)){
			map.put("topoEdit", topoEdit);
		}
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		
		String[] ids = id.split(",");
		for(int i=0;i<ids.length;i++){
			if(!StringUtil.isNullString(ids[i]));{
				ncmDeviceService.deleteDevice(Long.valueOf(ids[i].trim()));
			}
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		SystemUtils.addLog("删除交换机设备", "成功删除交换机设备！",2);
	    return null;
	}
	
	@RequestMapping(value = "/save.do")
	public String save(String action, String closeDlg, Long id, NcmDevices device, String topoEditFlag,ModelMap map) {
		logger.info( "Enter save.do ..." );
		boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		else if(!StringUtil.isNullString(device.getDevAccessId())&&StringUtil.isNullString(device.getOsName())){
			validate = false;
			map.put("message", "操作系統不能为空！");
		}
		else if( !ncmDeviceService.isDeviceUnique(id, device.getDevName(), device.getDevIpAddr())&&"add".equals(action) ){
			validate = false;
			map.put("message", "该设备已经存在！");
		}
		System.out.println("获取到的Id是：》》"+id);
		if( validate ){
			String operator = curUser.getLoginName();
			device.setModifier(operator);
			Date now = new Date(System.currentTimeMillis());
//			Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			device.setPermitAccess(1);
			device.setSnmpEnabled(1);
			device.setModifyTime(now);
			String jsonStr = "";
			if( "add".equals(action)){
				device.setCreator(operator);
				device.setCreateTime(now);
				device.setDevIpLong(Util.ip2long(device.getDevIpAddr()));
				ncmDeviceService.addSwitch(device);		
				map.put("device", device);
				jsonStr = ncmDeviceService.getJsonObjStr(device);
				SystemUtils.addLog("添加设备", "成功添加设备！",2);
			}
			else{
				NcmDevices editSwitch = ncmDeviceService.getById(id);
				if( editSwitch == null ){
					map.put("result", "error");								
					map.put("message", "该设备已经被删除！");
					return viewPath+"/edit";
				}
				BeanUtils.copyProperties(device, editSwitch);
				ncmDeviceService.saveSwitch(editSwitch);			
				map.put("switch", editSwitch);
				jsonStr = ncmDeviceService.getJsonObjStr(editSwitch);
				SystemUtils.addLog("修改设备", "成功设备！",2);
			}
			
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			if("edit".endsWith(action)){
				device.setDevId(id);
			}
			map.put("device", device);
			map.put("result", "error");			
		}

		map.put("action", action);
		map.put("closeDlg", closeDlg);
		map.put("topoEditFlag", topoEditFlag);//topo管理中的修改flag
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/search.do")
	public String search(String queryType,HttpServletRequest resquest, HttpServletResponse response,String orgId,boolean checkBox,String devType,PageRequest pageRequest, JqPageBean page) throws IOException {
		logger.info( "Enter search.do ..." );
		List<NcmDevices> list =new ArrayList<NcmDevices>();
		OrgOrganization organ=null;
		String jsonStr="{}";
		pageRequest.setPageNo(page.getPage());
		pageRequest.setPageSize(page.getRows());
		
		if(page.getSidx().equals("orgName")){
			pageRequest.setOrderBy("b."+page.getSidx());
		}else{
			if(page.getSidx().equals("vendorName")){
				page.setSidx("vendorId");
			}else if(page.getSidx().equals("typeName")||(page.getSidx().equals("prodSeries"))||(page.getSidx().equals("prodModel"))){
				page.setSidx("devTypeCode");
			}else if(page.getSidx().equals("devAccess")){
				page.setSidx("devAccessId");
			}
			pageRequest.setOrderBy("a."+page.getSidx());
		}
		
		pageRequest.setOrderDir(page.getSord());
		
		if(!StringUtil.isNullString(queryType)){
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
			if(checkBox){
				if(!StringUtil.isNullString(orgId)){
					List<String> ordIds=organService.getIdsByOrgId(orgId);
					String bioid="";
					for (String bioids : ordIds) {
						bioid+=bioids+";";
					}
					PropertyFilter filter=new PropertyFilter("INS_orgId",bioid);
					PropertyFilter filter2=new PropertyFilter("EQS_devTypeCode",devType);
					filters.add(filter);
					filters.add(filter2);
				}
			}else{
				PropertyFilter filter=new PropertyFilter("EQS_orgId",orgId);
				PropertyFilter filter2=new PropertyFilter("EQS_devTypeCode",devType);
				filters.add(filter);
				filters.add(filter2);
			}
			list = ncmDeviceService.searchSwitch(filters);

		}else{
			if(!StringUtil.isNullString(orgId)){
				
				 organ=organService.getOrgan(orgId);
				 if(organ!=null){
//					 if(checkBox){
//						 list = ncmDeviceService.getDeviesListByOrgCode(organ.getTreeCode(),devType);
//						 ncmDeviceService.searchByAjaxPage(pageRequest,checkBox,organ.getTreeCode(),devType);
//					 }else{
//						 list = ncmDeviceService.getDeviesListByCode(organ.getTreeCode(),devType);
//						 
//					 }
					 
					 Page<NcmDevices> pages= ncmDeviceService.searchByAjaxPage(pageRequest,checkBox,organ.getTreeCode(),devType);
					 jsonStr = ncmDeviceService.getDevicePageList(pages);
				 }
				 
				 
				}
			
			}
	
		
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().print(jsonStr);
	    return null;         
	}
	
	@RequestMapping(value = "/ifDetail.do")
	public String ifDetail(ModelMap map,String id,String name) {
		logger.info( "Enter ifDetail.do ..." );
		if(StringUtil.isNullString(name)){
			NcmDevices device = ncmDeviceService.getById(Long.parseLong(id));
			if(device!=null){
				name=device.getDevName();
				map.put("devName", name);
			}
		}
		map.put("switchId", id);
		return viewPath+"/ifDetail";
	}
	
	@RequestMapping(value = "/ajaxIfDetail.do")
	public String ajaxIfDetail(ModelMap map,String id,HttpServletResponse response) throws IOException {
		logger.info( "Enter ajaxIfDetail.do ..." );
		List<NcmDevInterfaces> list=ncmDevIfService.getAllSnmpIfByDevId(Long.valueOf(id));
		String jsonstr =ncmDevIfService.getPortJsonListStr(list);
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jsonstr);
		return null;
	}
	
	@RequestMapping(value="/scan.do")
	public String scan(){
		logger.info("Enter scan.do ...");
		return viewPath+"/scan";
	}
	
	
	@RequestMapping(value = "/startSearch.do")
	public String startSearch(String selectMode,String searchScope,ModelMap map,HttpServletResponse response) throws IOException {
			
		logger.info("Enter startSearch.do ...");
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
	     
	     System.out.println("searchscope-->"+ scope);
		 System.out.println("community-->"+ community);
		 System.out.println("snmpversion-->"+ "v1");
	     
		 
		 //RMI
//		 ClientConfiguration clientconf = new ClientConfiguration();
//		 Properties topoinfo = clientconf.topoDiscovery(topoprops);
		 
		 //local
		  DiscoveryUtil util=new DiscoveryUtil();
		  util.searchDevices(topoprops);
		
		 map.put("result", "success");
		 return null;
	}
	
	@RequestMapping(value = "/scanScopeQuery.do")
	public String scanScopeQuery(String scope, HttpServletResponse response) throws IOException{  
		logger.info( "Enter scanScopeQuery.do ..." );
		String scopeIp = scope.trim().replace("；", ";");
//		Properties searchMsg;
		String result="success";
		ArrayList<String> scopelist=new ArrayList<String>();
		if(!StringUtil.isNullString(scopeIp)){
			String[] scopearr=scopeIp.split(";");
			for(String sc:scopearr){
				System.out.println("获取到的scope是：》》"+sc);
				if(!StringUtil.isNullString(sc))
					scopelist.add(sc);
			}
		}
		//清除缓存
//		DiscoveryService.getInstance().clearDiscoveryResult();
//		DiscoveryService.getInstance().clearDiscoveryStatus();
//		searchMsg=ClientConfiguration.getSingleInstance().discoverAllSwitchs(scopelist);
		
		//DiscoveryDeviceResult.getInstance().clearDiscoveryResult();
		SwitchUtil.discoverAllSwitchs(scopelist);
		
		//RMI
//		ClientRmiController contr=new ClientRmiController();
//		contr.discoverAllSwitchs(scopelist);
		
		/*if(StringUtil.isNullString(searchMsg))
			result ="fail";*/
//		if(searchMsg==null)
//			result="fail";
		JSONObject jo=new JSONObject();
		jo.put("result",result);
		//jo.put("searchMsg", searchMsg);
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().print(jo);
		SystemUtils.addLog("交换机范围搜索", "成功搜索交换机设备！",2);
	    return null;         
	}
	
	
	@RequestMapping(value = "/scanQuery.do")
	public String scanQuery(@ModelAttribute("ScanParam")ScanParam scanParam, HttpServletResponse response) throws IOException{  
		logger.info( "Enter scanQuery.do ..." );
		System.out.println("获取到的arp是："+scanParam.getArpGetMethod()+" 网关团体字：》》》"+scanParam.getGatewayStr());
		String scope = scanParam.getSearchIp().trim().replace("；", ";");
		String community = scanParam.getSnmpRoKey().trim().replace("；", ";");
		String gatewayStr=scanParam.getGatewayStr().trim();
		String arpMethod=StringUtil.isNullString(scanParam.getArpGetMethod())?"snmp":scanParam.getArpGetMethod();
		String gatewayIp=scanParam.getGatewayIp().trim();
		
		if( community.length() == 0 ){
			community = WebFuncUtils.getParamValue("SnmpDefaultRoString");
		}
		int defaultSnmpPort = Integer.parseInt(WebFuncUtils.getParamValue("SnmpDefaultPort", "161"));
		System.out.println("获取到的刷新参数是：scope>>"+scope+"  community>>"+community+"  defaultSnmpPort>>"+defaultSnmpPort);
		//ClientConfiguration.getSingleInstance().discoverDevice(scope, community, defaultSnmpPort,scanParam.getGatewayIp(),gatewayStr,scanParam.getArpGetMethod());
		ArrayList<String> scopelist=new ArrayList<String>();
		if(!StringUtil.isNullString(scope)){
			String sc=scope+"#"+community+"#"+gatewayIp+"#"+gatewayStr+"#"+arpMethod;
			System.out.println("get the sc String:>>"+sc);
			scopelist.add(sc);
		}
		//清除缓存
//		DiscoveryService.getInstance().clearDiscoveryResult();
//		DiscoveryService.getInstance().clearDiscoveryStatus();
//		ClientConfiguration.getSingleInstance().discoverAllSwitchs(scopelist);
		
		
		//local
		DiscoveryDeviceResult.getInstance().clearDiscoveryResult();
		SwitchUtil.discoverAllSwitchs(scopelist);
		
		//RMI
//		ClientRmiController contr=new ClientRmiController();
//		contr.discoverAllSwitchs(scopelist);
//		SwitchUtil.searchSwitchPortMacInfo(scanParam.getSearchIp(), community, 161, scanParam.getGatewayIp(), gatewayStr, 1);
		
//		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		SystemUtils.addLog("交换机单点搜索", "成功搜索交换机设备！",2);
	    return null;         
	}
	
	
	//搜索路由器
	@RequestMapping(value = "/routerScan.do")
	public String scan(String searchIp, String snmpRoKey, HttpServletResponse response) throws IOException{  
		logger.info( "Enter scan.do ..." );

		
		String scope = searchIp.trim().replace("，", ",");
		String community = snmpRoKey.trim().replace("，", ",");
		if( community.length() == 0 ){
			community = WebFuncUtils.getParamValue("SnmpDefaultRoString");
		}
		
		Properties resourceProps = new Properties();
		resourceProps.setProperty("searchscope", scope);
		resourceProps.setProperty("community", community);
		
		//LOCAL
		DiscoveryRouterUtil.discoverAllRouters(resourceProps);
		
		//RMI
//		ClientRmiController contr=new ClientRmiController();
//		contr.discoverAllRouters(resourceProps);
		SystemUtils.addLog("搜索路由器", "成功搜索路由器！",1);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
	//导出
	@RequestMapping(value = "/exportExcel.do")
	public String exportExcel(String ids,ModelMap map,HttpServletResponse response) throws IOException{     
		logger.info( "Enter exportExcel.do ..." );
		String[] idSplit = ids.split(",");
		List<NcmDevices> listsub = new ArrayList<NcmDevices>();
		List<Long> idlist=new ArrayList<Long>();
		for (String id : idSplit) {
//			NcmSwitch switch1 = ncmDeviceService.getSwitch(Long.parseLong(id));
//			listsub.add(switch1);
			idlist.add(Long.parseLong(id));
		}
		if(!idlist.isEmpty()){
			listsub=ncmDeviceService.getDataByDevIds(idlist);
		}
		
		
		String[] headers = {"机构名称","设备名称","IP地址","设备厂商","设备类型","产品系列","设备型号","缺省网关","网关Snmp"};//导出字段名称
		String attr="orgId@OrgOrganization@orgId@orgName,devName,devIpAddr,vendorId@CmdbVendor@vendorID@dispName,modelOid@VendorProdModel@prodModelOid@prodTypeName,modelOid@VendorProdModel@prodModelOid@prodSeries,modelOid@VendorProdModel@prodModelOid@prodModelName,gatewayIP,gatewaySnmp";//导出映射字段
		int[] exlColWidths=new int[]{4800,6800,4800,4800,4800,4800,4800,4800,4800};//excel 列宽132*36≈4800相当于132px
		String fileName="switch_"+DateUtil.getExpTimeShow(new Timestamp(System.currentTimeMillis()))+".xls";//excel文件名
	    try {  
	          response.setContentType("octets/stream");
	          response.addHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gb2312"), "UTF-8"));
	          OutputStream out = response.getOutputStream();  
	          ExpExcelUtil.expExcel("设备信息", headers, exlColWidths, listsub, attr, out);
	          out.close();
	      } catch (Exception e) {
	    	  System.out.println("导出excel出错。。。");
	          e.printStackTrace();  
	      }
	    return null;
	}
	
	@RequestMapping(value = "/view.do")
	public String view(Long id, ModelMap map) throws IOException{     
		logger.info( "Enter view.do ..." );
		NcmDevices device = ncmDeviceService.getById(id);
		if(device!=null){
			OrgOrganization organ=organService.getOrgan(device.getOrgId());
			VendorProdModel prodModel=vendorProdModelService.getByModelOID(device.getModelOid());
			OrgEmployee employee=employeeService.getEmployee(device.getContactId());
			String  adminStatus=resItemService.findResContent("Subnet_AdminStatus", String.valueOf(device.getAdminStatus()));
			String  operStatus=resItemService.findResContent("Subnet_OperStatus", String.valueOf(device.getOpStatus()));
			String  devType=resItemService.findResContent("dev_Type", String.valueOf(device.getGatewayType()));
			
			CmdbVendor vendor=cmdbVendorService.getCmdbVendorById(device.getVendorId());
			NcmSnmpCredentials cred=snmpCredService.getSnmpCred(device.getSnmpCredId());
			
			if(cred!=null){
				
				String  snmpVersion=resItemService.findResContent("snmpCred_snmpVersion", String.valueOf(cred.getSnmpVersion()));
				map.put("snmpVersion", snmpVersion);
			}
			NcmDevAccessUser access=ncmDevAccessUserService.getAccessByAccessId(device.getDevAccessId());
			if(access!=null){
				String  userPrivilegeName=resItemService.findResContent("dev_UserPrivilege", String.valueOf(access.getUserType()));
				map.put("userPrivilegeName", userPrivilegeName);
			}
			
			String arpModeName="";
			if(device.getGetArpMode()==1){
				arpModeName="snmp";
			}else if(device.getGetArpMode()==1){
				arpModeName="telnet";
			}else{
				arpModeName="ssh";
			}
			
			String macModeName="";
			if(device.getGetMacMode()==1){
				macModeName="Snmp";
			}else if(device.getGetMacMode()==2){
				macModeName="CLI";
			}
			
			map.put("device", device);
			map.put("organ", organ);
			map.put("prodModel", prodModel);
			map.put("employee", employee);
			map.put("adminStatus", adminStatus);
			map.put("operStatus", operStatus);
			map.put("vendor", vendor);
			map.put("cred", cred);
			map.put("access", access);
			map.put("devType", devType);
			map.put("arpModeName", arpModeName);
			map.put("macModeName", macModeName);
		}
		
		SystemUtils.addLog("查看交换机设备", "成功查看交换机设备！",2);
		return viewPath+"/view";
	}
	
	@RequestMapping(value="/onAccessTest.do")
	public String onAccessTest(ModelMap map,String id){
		map.put("id", id);
		return viewPath+"/accessTest";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value = "/accessTest.do")
	public String accessTest(String id,HttpServletResponse response) throws IOException{     
		logger.info( "Enter accessTest.do ..." );
		List<Properties> list =new ArrayList<Properties>();
		if(!StringUtil.isNullString(id)){
			ArrayList devList =new ArrayList();
			NcmDevices device=ncmDeviceService.getById(Long.valueOf(id));
			Properties devProps = new Properties();
			devProps.put("DeviceIp", device.getDevIpAddr());
			devList.add(devProps);
			
//			Hashtable deviceResult =CmdbDeviceOperation.getInstance().getDeviceValidationProcess(devList);  //liuzong method
			
			NetworkUtil util=new NetworkUtil();  // cuilei  local method
			Hashtable deviceResult =util.getDeviceValidationProcess(devList);
			
//			ClientRmiController controller=new ClientRmiController();
//			Hashtable deviceResult =controller.onAccessCheck(devList);
			
			for(Enumeration e = deviceResult.keys(); e.hasMoreElements();)
			{
				String deviceIp = (String) e.nextElement();
				Properties validResults = (Properties) deviceResult.get(deviceIp);
				if(validResults != null)
				{
					Properties pro=new Properties();
					String errorCode = validResults.get("Error_Code").toString().replace("\n", "</br>"); //有错误时，errorCode可以从知识库获取提示信息， 没有错误时errorCode为空
				    String errorMessage = validResults.get("Error_Message").toString().replace("\n", "</br>"); //有错误时，errorMessage为英文提示信息， 没有错误时errorMessage为空    
				    String processMessage = validResults.get("Process_Message").toString().replace("\n", "</br>"); //显示系统验证执行过程
				    boolean validResult = Boolean.parseBoolean(validResults.get("Result").toString());
				    pro.put("ip", deviceIp);
				    pro.put("errorCode", errorCode);
				    pro.put("errorMessage", errorMessage);
				    pro.put("processMessage", processMessage);
				    pro.put("validResult", validResult);
				    list.add(pro);
				}
			}
		}
		
		JSONObject jo=new JSONObject();
		JSONArray ja=JSONArray.fromObject(list);
		System.out.println(ja.toString());
		jo.put("result", ja.toString());
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jo.toString());
	    return null;         
	} 
	
//	@RequestMapping(value="/onBatchExeCmd.do")
//	public String onBatchExeCmd(Long id,ModelMap map) {
////		NcmDevice device = deviceService.getDevice(id);
//		
//		accessInfo = devAccessService.findAccessInfo(id);
//		map.put("deviceIpAddr", accessInfo.getResIP());
//		map.put("agent_IP", accessInfo.getAgentIP());
//		
//		return viewPath+"/batchExeCmd";
//	}
	
//	批量执行命令
	@RequestMapping(value="/batchExeCmd.do")
	public String batchExeCmd(String ip,String content,HttpServletResponse response) throws IOException{
		logger.info("Enter NcmDevicesAction batchExeCmd.do...");
		
		StringBuilder errorSb = new StringBuilder();
		if(accessInfo!=null){
			if(StringUtils.isBlank(ip)){
				errorSb.append("ip为空,无法查询</br>");
			}else if(StringUtils.isBlank(accessInfo.getUserName())){
				errorSb.append("用户名为空,无法查询</br>");
			}else if(StringUtils.isBlank(accessInfo.getPassWord())){
				errorSb.append("密码为空,无法查询</br>");
			}else if(accessInfo.getAccessPort()==null){
				errorSb.append("端口号为空,无法查询</br>");
			}else if(StringUtils.isBlank(accessInfo.getAccessTool())){
				errorSb.append("查询方式为空,无法查询</br>");
			}else{
//				以上都不为空，开始获取连接对象
//				设置超时时间循环尝试，看能否ping通
				boolean flag = false;
				int timeOut = 3000;//超时时间设为3秒
				int retry = 3;//重连次数
				InetAddress address;
				accessInfo.setLoginIp(ip);
				this.content = content;
				
				try {
					address = InetAddress.getByName(ip);
					for (int i = 0; i < retry; i++) {
						try {
							if (address.isReachable(timeOut)) {
								flag = true;
								logger.info(ip+"第"+(i+1) + "次 尝试ping成功！");
//							创建连接对象
								ocr = new ObtainCmdResult(ip,accessInfo.getUserName() , accessInfo.getPassWord(), accessInfo.getEnablePassWord(), accessInfo.getAccessPort(),accessInfo.getCmdPrompt(),accessInfo.getPrivUserCmd());
								break;
							}else{
								flag = false;
								logger.warn(ip+"第"+(i+1) + "次 尝试ping失败！");
								errorSb.append(ip+" ping不通，请检查线路</br>");
							}
						}
						catch (IOException e) {
							e.printStackTrace();
							errorSb.append(ip+"连接超时</br>");
						}
					}
					
					if(flag ==false){
						errorSb.append(ip+"连接超时</br>");
					}
				}
				catch (UnknownHostException e) {
					e.printStackTrace();
				}

			}
		}else{
			errorSb.append("没有查询到该线路上设备的远程登陆信息</br>");
		}
		JSONObject jo=new JSONObject();
		jo.put("errorSb", errorSb.toString());
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().print(jo.toString());
		return null;
	}
	
	//跳转到检查故障的页面
	@RequestMapping(value="/checkLineCase.do")
	public String checkLineCase(ModelMap map){
		logger.info("Enter NcmDevicesAction checkLineCase.do...");
		map.put("ip", accessInfo.getLoginIp());
		return viewPath+"/checkLineCaseInfo";
	}
	
//	输出信息
	@RequestMapping(value="/exeCmds.do")
	public String exeCmds(HttpServletResponse response) throws IOException{
		logger.info("Enter AlmEventRawData exeCmds.do...");
//		MessageCache.getInstance().clearMessage();
//		long s=System.currentTimeMillis();
//		JSONObject jo=new JSONObject();
//		StringBuilder sb = new StringBuilder();
//		String ip =accessInfo.getLoginIp();
//		
//		SSHConnection sshInstance =null;
//		TelnetConnection telInstance = null;
//		
//		if(ocr==null){
//			ocr = new ObtainCmdResult(ip,accessInfo.getUserName() , accessInfo.getPassWord(), accessInfo.getEnablePassWord(), accessInfo.getAccessPort(),accessInfo.getCmdPrompt(),accessInfo.getPrivUserCmd());
//		}else {
//			cmdLine = new ArrayList<String>();
//			if(StringUtils.isBlank(content)){
////				构建命令行
//				cmdLine.add("show interface "+accessInfo.getIf_Name());
//				List<SysParamItem> list = paramItemService.getGroupParamItemByGroupName("CheckDevStatus");
//				for (SysParamItem sysParamItem : list) {
//					cmdLine.add(sysParamItem.getValueText());
//				}
//				cmdLine.add("ping "+accessInfo.getDestIP());
//			}else{
//				cmdLine = new ArrayList<String>();
//				String[] split = content.split("\n");
//				for (String string : split) {
//					String[] split2 = string.split(";");
//					for (String string2 : split2) {
//						cmdLine.add(string2);
//					}
//				}
//				this.content = "";
//			}
//			
//			System.out.println("要执行的命令"+cmdLine.toString());
//			
////			获取连接
//			if(accessInfo.getAccessTool()!=null && accessInfo.getAccessTool().equals("SSH")){
//				System.out.println("SSH连接");
//				sshInstance = ocr.getSSHInstance();
//				if(sshInstance==null){
//					sb.append(ip+" ssh 服务没有开启!");
//				}else{
//					try {
//						ocr.verfyLineAccident(cmdLine, sshInstance, telInstance);
//					}
//					catch (Exception e1) {
//						sb.append(ip+" ssh 没有连接成功!");
//						e1.printStackTrace();
//					}
//				}
//			}
//			if(accessInfo.getAccessTool()!=null && accessInfo.getAccessTool().equals("Telnet")){
//				System.out.println("Telnet连接");
//				try {
//					telInstance = ocr.getTelnetInstance();
//					if(telInstance==null){
//						sb.append(ip+" telnet 服务没有开启!");
//					}else{
//						ocr.verfyLineAccident(cmdLine, sshInstance, telInstance);
//					}
//				}
//				catch (Exception e1) {
//					sb.append(ip+" telnet 没有连接成功!");
//					e1.printStackTrace();
//				}
//			}
//		}
//		
//		long e=System.currentTimeMillis();
//		System.out.println("共耗时："+(e-s)+"毫秒");
//		
//		jo.put("connError", sb.toString());
//		response.setContentType("text/json");  
//		response.setCharacterEncoding("UTF-8"); 
//		response.getWriter().print(jo.toString());
//		
//		return null;
//	}
//	
//	@RequestMapping(value="/outputLogs.do")
//	public String outputLogs(HttpServletResponse response) throws IOException{
//		StringBuffer output=MessageCache.getInstance().getMessage();
//		
//		if( output!=null&&output.length()>0){
//			 String outputstr = output.toString();
//			 
//			 JSONObject jo=new JSONObject();
//			 jo.put("outputstr", outputstr);
//			 response.setContentType("text/json");  
//			 response.setCharacterEncoding("UTF-8"); 
//			 response.getWriter().print(jo.toString());
//		 }
		return null;
	}
	
	@RequestMapping(value = "/reflesh.do")
	public void reflesh(String[] ids, HttpServletResponse response) throws IOException{     
		logger.info( "Enter reflesh.do ..." );

		boolean res = true;
		String defaultSnmpRoKey = WebFuncUtils.getParamValue("SnmpDefaultRoString", "public");
		int defaultSnmpPort = Integer.parseInt(WebFuncUtils.getParamValue("SnmpDefaultPort", "161"));
		StringBuilder sb = new StringBuilder();
		ArrayList<String> scopelist=new ArrayList<String>();
		for( String id: ids){
			NcmDevices device = ncmDeviceService.getById(Long.parseLong(id));
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
				if(arpGet==3)
					arpGetMode="ssh";
				String snmpRo = defaultSnmpRoKey;
				int snmpPort = defaultSnmpPort;
				if( device.getSnmpCredId() != null ){
					NcmSnmpCredentials snmpCred = snmpCredService.getSnmpCred(device.getSnmpCredId());
					if( snmpCred != null ){
						snmpRo = snmpCred.getSnmpRoStr();
						snmpPort = snmpCred.getSnmpPort();
					}
				}
				if(device.getDevTypeCode()==2||device.getDevTypeCode()==5){
					String sc=ip+"@"+snmpRo+"@"+gatewayIp+"@"+snmpRo+"@"+arpGetMode;
					System.out.println("get the sc String:>>"+sc);
					scopelist.add(sc);
				}else if(device.getDevTypeCode()==3){
					String sc=ip+"@"+snmpRo;
					System.out.println("get the sc String:>>"+sc);
					scopelist.add(sc);
				}
				
			}
		}
		
		if(!res){
			String result = String.format(JsonUtils.JSON_RESULT_FALSE_MSGMDL, sb.toString());
			response.getWriter().print(result);
		}
		else{
			//清除缓存
//			DiscoveryService.getInstance().clearDiscoveryResult();
//			DiscoveryService.getInstance().clearDiscoveryStatus();
//			ClientConfiguration.getSingleInstance().refreshSwitch(scopelist);
			ClientRmiController contr=new ClientRmiController();
			contr.refreshDevices(scopelist);
			
			response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		}
		SystemUtils.addLog("交换机Snmp刷新", "成功刷新Snmp！",2);

	} 
	
	@RequestMapping(value = "/sRefresh.do")
	public String sRefresh(ModelMap map) {
		logger.info( "Enter sRefresh.do ..." );
		return viewPath+"/snmpRefresh";
	}
	
	@RequestMapping(value = "/fdbTable.do")
	public String fdbTable(ModelMap map,String id,String name) {
		logger.info( "Enter fdbTable.do ..." );
		map.put("switchId", id);
		map.put("switchName", name);
		return viewPath+"/fdbTable";
	}
	
	@RequestMapping(value = "/ajaxFDBTable.do")
	public String ajaxFDBTable(ModelMap map,String id,HttpServletResponse response) throws IOException {
		logger.info( "Enter ajaxFDBTable.do ..." );
		List<NcmFDBTableInfo> list=fDBTableService.getAllFDBTable(Long.valueOf(id));
		String jsonstr =fDBTableService.getJsonListStr(list);
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jsonstr);
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(JdbcConnection.getInstance().getDeviceResourceUniqId());;
	}
}
