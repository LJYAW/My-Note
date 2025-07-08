package com.sino.fas.res.host.web;

import com.sino.base.common.util.*;
import com.sino.base.system.entity.JqPageBean;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.OrganService;
import com.sino.fas.res.host.entity.IpHost;
import com.sino.fas.res.host.entity.IpHostInfo;
import com.sino.fas.res.host.service.IpHostService;
import com.sino.fas.res.net.entity.NcmDevices;
import com.sino.fas.res.net.entity.NcmSnmpCredentials;
import com.sino.fas.res.net.entity.NcmSwitchPort;
import com.sino.fas.res.net.entity.NcmSwitchPortId;
import com.sino.fas.res.net.service.NcmDevicesService;
import com.sino.fas.res.net.service.SnmpCredService;
import com.sino.fas.res.net.service.SwitchPortService;
import com.sino.snmp.utils.SnmpApp;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;
import smartlink.controller.ClientConfiguration;
import smartlink.discovery.DiscoveryService;
import smartlink.switchmgt.SwitchIpMacKey;
import smartlink.switchmgt.SwitchSecurity;
import smartlink.utils.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping(value = "/fas/res/host/ipHost")
public class IpHostAction {
	private static Logger logger = LoggerFactory.getLogger(IpHostAction.class);
	private String viewPath = "/fas/res/host/ipHost";
	
	@Autowired
	private IpHostService ipHostService;
	
	@Autowired
	private NcmDevicesService ncmDevicesService;
	
	@Autowired
	private SwitchPortService switchPortService;
	
	@Autowired
	private SnmpCredService snmpService;	
	
	@Autowired
	private OrganService organService;
	
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		logger.info( "Enter main.do ..." );
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	}
	
	@RequestMapping(value = "/macManage.do")
	public String macManage(String id,String ip,String macIp,String ifIndex,String switchIp,ModelMap map) {
		logger.info( "Enter macManage.do ..." );
		map.put("ip", ip);
		map.put("macIp", macIp);
		map.put("ifIndex", ifIndex);
		map.put("switchIp", switchIp);
		map.put("message", "");
		map.put("id", id);
		return viewPath+"/macManage";
	}
	
	@RequestMapping(value = "/getMainData.do", method = RequestMethod.GET)
	public void getMainData(ModelMap map,HttpServletResponse response) throws IOException {
		logger.info( "Enter getMainData.do ..." );
		
		Long start=System.currentTimeMillis();
		
		List<IpHostInfo> listInfo =ipHostService.getListIpHost();
		JSONArray jsonStr= JSONArray.fromObject(listInfo);
	//	System.out.println("get the json:>>"+jsonStr.toString());
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
	    response.getWriter().print(jsonStr.toString());
	    Long end=System.currentTimeMillis();
	   System.out.println("查询时间-------》"+(end-start));
	}
	
	//Ajax分页查询
	@RequestMapping(value = "/searchAjaxPage.do")
	public String searchAjaxPage(PageRequest pageRequest, JqPageBean page, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		long start=System.currentTimeMillis();
		logger.info("Enter searchAjaxPage.do ...");
		pageRequest.setPageNo(page.getPage());
		pageRequest.setPageSize(page.getRows());
		pageRequest.setOrderBy(page.getSidx());
		pageRequest.setOrderDir(page.getSord());
		
		String jsonStr;
		Page<IpHostInfo> pages = ipHostService.searchByAjax(pageRequest);
		jsonStr = ipHostService.getIpHostByAjax(pages);
		response.getWriter().print(jsonStr);
		long end=System.currentTimeMillis();
		System.out.println("数据解析时间差---------》"+(end-start));
		return null;
	}
	
	//Ajax分页查询
	@RequestMapping(value = "/getHostInfos.do")
	public String getHostInfos(String typeCode, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("Enter getHostInfos.do ...");
		
		List<IpHostInfo> list = ipHostService.getHostInfos(Integer.parseInt(typeCode));
		String jsonStr = ipHostService.getIpHostInfoStr(list);
		response.getWriter().print(jsonStr);
		return null;
	}
	
	
	
	@RequestMapping(value = "/getMainDataBy.do")
	public void getMainDataBy(String orgId,long switchId,ModelMap map,HttpServletResponse response) throws IOException {
		logger.info( "Enter getMainDataBy.do ..." );
		List<String> orgIds=organService.getSubOrgIds(orgId);
		List<IpHostInfo> listInfo =ipHostService.getListIpHost(orgIds, switchId);
		JSONArray jsonStr= JSONArray.fromObject(listInfo);
//		System.out.println("get the json:>>"+jsonStr.toString());
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
	    response.getWriter().print(jsonStr.toString());
	}
	
	@RequestMapping(value = "/getDataByIp.do")
	public void getDataByIp(String clientip,ModelMap map,HttpServletResponse response) throws IOException {
		logger.info( "Enter getDataByIp.do ..." );
		List<IpHostInfo> listInfo =ipHostService.getByIp(clientip);
		JSONArray jsonStr= JSONArray.fromObject(listInfo);
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jsonStr.toString());
	}
	
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		IpHost ipHost=new IpHost();
		map.put("ipHost", ipHost);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/searchView.do")
	public String searchView(ModelMap map) {
		logger.info( "Enter searchView.do ..." );
		return viewPath+"/search";
	}
	
	@RequestMapping(value = "/edit.do")
	public String edit(String ipHostId, ModelMap map){
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		IpHost ipHost = ipHostService.getIpHost(Long.valueOf(ipHostId));
		NcmSwitchPortId id =new NcmSwitchPortId();
		if(ipHost!=null){
			id.setSwitchId(ipHost.getSwitchId());
			id.setIfIndex(ipHost.getIfIndex());
		}
		
		//JSONArray jsonStr= JSONArray.fromObject(id);
		String jsonStr =JsonUtils.getJsonObjValue(id);
		//System.out.println("json对象Id:>"+jsonStr.toString());
		map.put("id", jsonStr);
		map.put("ipHost", ipHost);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/multiEdit.do")
	public String multiEdit(String ipHostIds, ModelMap map){
		logger.info( "Enter multiEdit.do ..." );
		map.put("ipHostIds", ipHostIds);
		return viewPath+"/multiEdit";
	}
	
	@RequestMapping(value = "/view.do")
	public String view(String id, ModelMap map) throws IOException{     
		logger.info( "Enter view.do ..." );
		IpHostInfo ipHostInfo =ipHostService.getListIpHost(Long.valueOf(id));
		map.put("ipHostInfo", ipHostInfo);
		return viewPath+"/view";
	} 
	
	@RequestMapping(value = "/save.do")
	public String save(String action,String ipHostId, IpHost ipHost, ModelMap map) {
		logger.info( "Enter save.do ..." );
		boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		else if( !ipHostService.isIpHostUnique(ipHostId, ipHost.getMacAddr())&& !ipHost.getMacAddr().equals("null")){
			validate = false;
			map.put("message", "该IP设备已经存在！");
		}
		else if( StringUtils.isBlank(ipHost.getMacAddr()) ){
			validate = false;
			map.put("message", "MAC地址不能为空！");
		}
		else if( StringUtils.isBlank(ipHost.getIpAddr()) ){
			validate = false;
			map.put("message", "IP地址不能为空！");
		}
		else if( StringUtils.isBlank(ipHost.getHostName()) ){
			validate = false;
			map.put("message", "设备名称不能为空！");
		}
		
		if( validate ){
			Date now = new Date();
			ipHost.setModify_Time(now);
			ipHost.setIpValue(Util.ip2long(ipHost.getIpAddr()));
			IpHost ipHostEdit=null;
			if( "add".equals(action)){
				ipHostService.addIpHost(ipHost);
				ipHostEdit =ipHost;
			}
			else{
				ipHostEdit = ipHostService.getIpHost(Long.valueOf(ipHostId));
				if( ipHostEdit == null ){
					map.put("result", "error");								
					map.put("message", "该配置已经被删除！");
					return viewPath+"/edit";
				}
				BeanUtils.copyProperties(ipHost, ipHostEdit);
				ipHostService.saveIpHost(ipHostEdit);
				
			}
			IpHostInfo listInfo =ipHostService.getListIpHost(ipHostEdit.getIpHostId());
			String jsonStr=JsonUtils.getJsonObjValue(listInfo);
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("ipHost", ipHost);
			map.put("result", "error");			
		}
		
		map.put("action", action);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/bindMac.do")
	public String bindMac(String flag,String action,String id,String ip,String macIp,String ifIndex,String switchIp,  ModelMap map) {
		logger.info( "Enter bindMac.do ..." );
		boolean validate = true;
		String message="";
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			message="登录超时！";
		}
		if(StringUtil.isNullString(ifIndex)||StringUtil.isNullString(switchIp)){
			validate=false;
			message="IP地址未配置正确，请重新配置！";
		}
		if( validate ){
			SwitchSecurity ss=new SwitchSecurity();
			if(flag.endsWith("yes")){
				Hashtable bindingresult=ss.setItfBindIpMac(switchIp, Long.valueOf(ifIndex), macIp, ip,"");
				for(Iterator itr = bindingresult.keySet().iterator(); itr.hasNext();){
					SwitchIpMacKey  key = (SwitchIpMacKey ) itr.next();
					Properties resultProps = (Properties) bindingresult.get(key);
			         if(resultProps != null)
			         {
			             String bresult = (String) resultProps.get("result"); // "true" - 表示绑定成功, "false" -表示绑定失败, "testmode" - 表示对接测试阶段, 实际不到交换机部署命令,只是返回命令显示
			             message = (String) resultProps.get("cmdoutput"); //需要将此结果回显到前端界面
			        }
				}
			}else{
				Hashtable bindingresult=ss.deleteItfBindIpMac(switchIp, Long.valueOf(ifIndex), macIp, ip,"");
				for(Iterator itr = bindingresult.keySet().iterator(); itr.hasNext();){
					SwitchIpMacKey  key = (SwitchIpMacKey ) itr.next();
					Properties resultProps = (Properties) bindingresult.get(key);
			         if(resultProps != null)
			         {
			             String bresult = (String) resultProps.get("result"); // "true" - 表示绑定成功, "false" -表示绑定失败, "testmode" - 表示对接测试阶段, 实际不到交换机部署命令,只是返回命令显示
			             message = (String) resultProps.get("cmdoutput"); //需要将此结果回显到前端界面
			        }
				}
			}
			IpHostInfo listInfo =ipHostService.getListIpHost(Long.valueOf(id));
			String jsonStr=JsonUtils.getJsonObjValue(listInfo);
			map.put("saveData", jsonStr);
			map.put("result", "success");
		}
		else{
			map.put("result", "error");			
		}
		map.put("message", message);	
		map.put("action", "edit");
		return viewPath+"/macManage";
	}
	
	@RequestMapping(value = "/search.do")
	public String search(PageRequest pageReq,HttpServletRequest resquest, HttpServletResponse response,ModelMap map) throws IOException {
		logger.info( "Enter search.do ..." );
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(resquest, "filter");
		
		//根据交换机ip查询
		for(PropertyFilter filter:filters){
			String[] pname=filter.getPropertyNames();
			for(String p:pname){
				if(p.equals("switchIp")){
					filters.remove(filter);
					Long switchId=ncmDevicesService.getSwitchIdByIp(String.valueOf(filter.getMatchValue()));
					PropertyFilter fil = new PropertyFilter("EQL_switchId",String.valueOf(switchId));
					filters.add(fil);
				}
			}
		}
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
		
		map.put("jsonListData", jsonStr);
		map.put("result", "success");
		return viewPath+"/search";       
		
//		response.getWriter().print(jsonStr.toString());
//	    return null;            
	}
	
	@RequestMapping(value = "/importGo.do")
	public String importGo(ModelMap map) throws IOException{     
		logger.info( "Enter importGo.do ..." );
		return viewPath+"/import";
	}                                                   
	
	@RequestMapping(value = "/importExcel.do")
	public String importExcel(MultipartFile impFile, ModelMap map) throws IOException{     
		logger.info( "Enter importExcel.do ..." );
		if( impFile.getSize()<=0 ){
			map.put("result", "error");
			map.put("message", "上传文件内容为空！");
		}
		else{
			if( ipHostService.impExcel(impFile.getInputStream()) ){
				map.put("result", "success");
			}
			else{
				map.put("result", "error");
			}			
			map.put("message", ipHostService.getLastMassage());
		}
		return viewPath+"/import";      
	}            
	
	@RequestMapping(value = "/multiSave.do")
	public String multiSave(String editOption,String ipHostIds, IpHost ipHost, ModelMap map) {
		logger.info( "Enter multiSave.do ..." );
		boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		List<String> editOptionList=new ArrayList<String>();
		List<String> ipHostIdsList=new ArrayList<String>();
		List<IpHostInfo> ipHostInfoList=new ArrayList<IpHostInfo>();
		
		if(!StringUtil.isNullString(editOption)&&!StringUtil.isNullString(ipHostIds)){
			String[] editOp = editOption.split(",");
			String[] ipHostId =ipHostIds.split(",");
			for(int i=0;i<editOp.length;i++){
				editOptionList.add(editOp[i]);
			}
			for(int i=0;i<ipHostId.length;i++){
				ipHostIdsList.add(ipHostId[i]);
			}
		}
		
		if( validate ){
			for(int i=0;i<ipHostIdsList.size();i++){
				IpHost ipHostInfo =ipHostService.getIpHost(Long.valueOf(ipHostIdsList.get(i)));
				for(int j=0;j<editOptionList.size();j++){
					if(editOptionList.get(j).equals("orgOption")){
						ipHostService.multiSaveIpHost(ipHostInfo, "orgOption", ipHost.getOrgId());
					}else if(editOptionList.get(j).equals("hostTypeOption")){
						ipHostService.multiSaveIpHost(ipHostInfo, "hostTypeOption", String.valueOf(ipHost.getIpHostTypeId()));
					}else if(editOptionList.get(j).equals("snmpRoStrOption")){
						ipHostService.multiSaveIpHost(ipHostInfo, "snmpRoStrOption", ipHost.getSnmpRoString());
					}else if(editOptionList.get(j).equals("ipMaskOption")){
						ipHostService.multiSaveIpHost(ipHostInfo, "ipMaskOption", ipHost.getIpNetMask());
					}else if(editOptionList.get(j).equals("osClassOption")){
						ipHostService.multiSaveIpHost(ipHostInfo, "osClassOption", String.valueOf(ipHost.getOsClass()));
						ipHostService.multiSaveIpHost(ipHostInfo, "osClassOption", String.valueOf(ipHost.getOsClassCode()));
					}else if(editOptionList.get(j).equals("osTypeOption")){
						ipHostService.multiSaveIpHost(ipHostInfo, "osTypeOption", String.valueOf(ipHost.getOsType()));
					}else if(editOptionList.get(j).equals("osVersionOption")){
						ipHostService.multiSaveIpHost(ipHostInfo, "osVersionOption", ipHost.getOsVersion());
					}else if(editOptionList.get(j).equals("osFeatureOption")){
						ipHostService.multiSaveIpHost(ipHostInfo, "osFeatureOption", ipHost.getOsFeature());
					}
					else if(editOptionList.get(j).equals("accessnOption")){
						ipHostService.multiSaveIpHost(ipHostInfo, "accessnOption", ipHost.getDevAccessId());
					}
				}
				IpHostInfo listIpHostInfo =ipHostService.getListIpHost(Long.valueOf(ipHostIdsList.get(i)));
				ipHostInfoList.add(listIpHostInfo);
			}
			
			JSONArray jsonStr= JSONArray.fromObject(ipHostInfoList);
			//System.out.println("获取到的json串是："+jsonStr.toString());
			map.put("saveData", jsonStr.toString());
			map.put("result", "success");
		}else{
			map.put("result", "error");
		}
		
		return viewPath+"/multiEdit";
	}
	
	@RequestMapping(value = "/delete.do")
	public String delete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		String[] ids = id.split(",");
		for(int i=0;i<ids.length;i++){
			if(!StringUtil.isNullString(ids[i]));
			ipHostService.deleteIpHost(Long.valueOf(ids[i]));
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;
	}
	
	@RequestMapping(value = "/ajaxQuerySwitchIf.do")
	public String ajaxQuerySwitchIf(Long id, ModelMap map,HttpServletResponse response) throws IOException{
		logger.info( "Enter ajaxQuerySwitchIf.do ..." );
		List<NcmDevices> switchlist = ncmDevicesService.getAllSwitch();
		List<NcmSwitchPort> switchPortlist =null;
		//System.out.println(id+"switch长度："+switchlist.size());
		if(id!=null&&id!=0L)
			switchPortlist=switchPortService.getAllSwitchPort(id);
		
		JSONObject jo=new JSONObject();
		JSONArray jsSwitch= JSONArray.fromObject(switchlist);
		JSONArray jsPort= JSONArray.fromObject(switchPortlist);
		
		jo.put("listSwitch", jsSwitch.toString());
		jo.put("listPort", jsPort.toString());
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jo);
		return null;
	}
	
	
	@RequestMapping(value = "/isolation.do")
	public String isolation(String ipHostIds,String switchIds,String ifIndexIds,String flag, ModelMap map,HttpServletResponse response) throws IOException {
		logger.info( "Enter isolation.do ..." );
		ArrayList<Long> switchidList =new ArrayList<Long>();
		ArrayList<Long> portidList =new ArrayList<Long>();
		ArrayList<String> iphostidList =new ArrayList<String>();
		ArrayList<Object> snmpidList =new ArrayList<Object>();
		String message="";
		List<IpHostInfo> listIphost =new ArrayList<IpHostInfo>();
		
		//System.out.println("获取到的Ids值是：》"+ipHostIds+"     "+switchIds+"     "+ifIndexIds);
		//获取到的ipHost、设备、接口ID值存放在list中
		if(!StringUtil.isNullString(switchIds)&&!StringUtil.isNullString(ifIndexIds)&&!StringUtil.isNullString(ipHostIds)){
			String[] switchId = switchIds.split(",");
			String[] portId =ifIndexIds.split(",");
			String[] iphostId=ipHostIds.split(",");
			for(int i=0;i<switchId.length;i++){
				switchidList.add(Long.valueOf(switchId[i]));
				portidList.add(Long.valueOf(portId[i]));
				iphostidList.add(iphostId[i]);
			}
		}
		
		//根据设备id获取
		List<NcmDevices> ncmSwitchList =ipHostService.getAllSwitchByIds(switchidList);
		for(int i=0;i<ncmSwitchList.size();i++){
			snmpidList.add(ncmSwitchList.get(i).getSnmpCredId());
		}
		
		StringBuffer logMsg=new StringBuffer();
		boolean IF_ShutDown = true;
		if(ncmSwitchList!=null&&ncmSwitchList.size()>0){
			List<NcmSnmpCredentials> ncmSnmpList=ipHostService.getAllsnmpByIds(snmpidList);
			
			String adminStatus="1";
			String operStatus="1";
			if(flag.equals("no")){
				IF_ShutDown=false;
			}
			
			for(int i=0;i<ncmSnmpList.size();i++){
				SnmpApp.adminSwitchPort(ncmSwitchList.get(i).getDevIpAddr(), ncmSnmpList.get(i).getSnmpPort(),  ncmSnmpList.get(i).getSnmpRwStr(), String.valueOf(portidList.get(i)), IF_ShutDown);
				//System.out.println("ifindex:>>"+portidList.get(i));
				adminStatus =SnmpApp.getPortAdminStatus(ncmSwitchList.get(i).getDevIpAddr(), ncmSnmpList.get(i).getSnmpPort(), ncmSnmpList.get(i).getSnmpRwStr(), String.valueOf(portidList.get(i)));
				operStatus =SnmpApp.getPortOperStatus(ncmSwitchList.get(i).getDevIpAddr(), ncmSnmpList.get(i).getSnmpPort(), ncmSnmpList.get(i).getSnmpRwStr(), String.valueOf(portidList.get(i)));
				
				//更新接口状态
				NcmSwitchPortId id =new NcmSwitchPortId();
				id.setSwitchId(switchidList.get(i));
				id.setIfIndex(portidList.get(i));
				NcmSwitchPort ncmSwitchPort=switchPortService.getSwitchPort(id);
				//获取所有需要隔离的设备接口对象
				IpHostInfo iphostInfo =ipHostService.getIpHostStatus(Long.valueOf(iphostidList.get(i)),adminStatus);
				logMsg.append("对与交换机  （");
				logMsg.append(ncmSwitchList.get(i).getDevIpAddr());
				logMsg.append("） 的端口： （");
				logMsg.append(ncmSwitchPort.getIfName());
				logMsg.append("） 相连的设备 （");
				logMsg.append(iphostInfo!=null?iphostInfo.getIpAddr():"null");
				logMsg.append("） 执行了");
				logMsg.append(IF_ShutDown?"设备隔离":"设备恢复");
				logMsg.append("操作，操作结果：");
				if(ncmSwitchPort!=null&&!StringUtil.isNullString(adminStatus)&&!StringUtil.isNullString(operStatus)){
					switchPortService.saveIfStatus(ncmSwitchPort, Integer.parseInt(adminStatus),Integer.parseInt(operStatus));
					SystemUtils.addLog(IF_ShutDown?"设备隔离":"设备恢复", logMsg+"成功！");
				}else{
					message="Snmp RW String可能设置错误，隔离失败！";
					SystemUtils.addLog(IF_ShutDown?"设备隔离":"设备恢复", logMsg+"失败！可能原因是交换机未正确设置Snmp读写串。");
					break;
				}
				listIphost.add(iphostInfo);
			}
		}else{
			message="隔离失败";
			SystemUtils.addLog(IF_ShutDown?"设备隔离":"设备恢复", "隔离设备失败！");
		}
		
		JSONObject jo=new JSONObject();
		JSONArray jsIphost= JSONArray.fromObject(listIphost);
		
		jo.put("listIphost", jsIphost.toString());
		jo.put("message", message);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8"); 
	    response.getWriter().print(jo);
		return null;
	}
	
	@RequestMapping(value = "/exportExcel.do")
	public String exportExcel(ModelMap map,HttpServletResponse response){
		logger.info("enter exportExcel....");
		List<IpHostInfo> listInfo =ipHostService.getListIpHost();
		String[] headers = {"机构名称", "设备类型", "主机名称","IP地址","MAC地址","使用人","接入交换机","交换机接口"};//导出字段名称
		String attr="orgName,ipHostType,hostName,ipAddr,macAddr,loginName,switchIp,switchIfIndex";//导出映射字段
		int[] exlColWidths=new int[]{4800,4800,4800,4800,4800,4800,4800,4800};//excel 列宽132*36≈4800相当于132px
		String fileName="IpList_"+DateUtil.getExpTimeShow(new Timestamp(System.currentTimeMillis()))+".xls";//excel文件名
	    try {  
	          response.setContentType("octets/stream");
	          response.addHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gb2312"), "UTF-8"));
	          OutputStream out = response.getOutputStream();  
	          ExpExcelUtil.expExcel("IP Host设备信息", headers, exlColWidths, listInfo, attr, out);
	          out.close();
	      } catch (Exception e) {
	    	  System.out.println("导出excel出错。。。");
	          e.printStackTrace();  
	      }
	    return null;
	}
	   @RequestMapping(value = "/reflesh.do")
		public void reflesh(String[] ids, HttpServletResponse response) throws IOException{     
			logger.info( "Enter reflesh.do ..." );
			StringBuilder sb = new StringBuilder();
			boolean res = true;
			String defaultSnmpRoKey = WebFuncUtils.getParamValue("SnmpDefaultRoString", "public");
			int defaultSnmpPort = Integer.parseInt(WebFuncUtils.getParamValue("SnmpDefaultPort", "161"));
			ArrayList<String> scopelist=new ArrayList<String>();
			for(String iphostId :ids){
				IpHost ipHost = ipHostService.getIpHost(Long.valueOf(iphostId));
				String ssId=String.valueOf(ipHost.getSwitchId());
				if(StringUtil.isNullString(ssId)){
					res = false;
					sb.append("MAC地址为_").append(ipHost.getMacAddr()).append("_的IP设备无交换机关联！");
					break;
				}
				NcmDevices device = ncmDevicesService.getById(Long.parseLong(ssId));
				if( device != null ){
					String ip = device.getDevIpAddr();
					if( StringUtils.isBlank(ip) ){
						res = false;
						sb.append("设备").append(device.getDevName()).append("的IP地址为空！");
						continue;
					}
					String snmpRo = defaultSnmpRoKey;
					int snmpPort = defaultSnmpPort;
					if( device.getSnmpCredId() != null ){
						NcmSnmpCredentials snmpCred = snmpService.getSnmpCred(device.getSnmpCredId());
						if( snmpCred != null ){
							snmpRo = snmpCred.getSnmpRoStr();
							snmpPort = snmpCred.getSnmpPort();
						}
					}
					if(!StringUtil.isNullString(ip)){
						String sc=ip+"@"+snmpRo;
					//	System.out.println("get the sc String:>>"+sc);
						scopelist.add(sc);
					}
					
				}else{
					res = false;
					sb.append("MAC地址为_").append(ipHost.getMacAddr()).append("_的IP设备无交换机关联！");
					break;
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
				ClientConfiguration.getSingleInstance().refreshRouter(scopelist);
				response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
			}

		}  
	   
}
