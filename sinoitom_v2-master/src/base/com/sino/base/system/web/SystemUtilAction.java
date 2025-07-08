package com.sino.base.system.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sino.distributed.controller.ClientRmiController;
import smartlink.systemadmin.SystemManagement;

import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.SnmpInfo;
import com.sino.snmp.utils.SortedOidValue;
import com.sino.snmp.utils.SortedOidValue.OidValue;
import com.sino.snmp.utils.mib.MibBrowser;

@Controller
@RequestMapping(value = "/system/util")
public class SystemUtilAction {
	private static Logger logger = LoggerFactory.getLogger(SystemUtilAction.class);
	private String viewPath = "/system/util";
	
	@RequestMapping(value = "/snmpUtil.do")
	public String snmpUtil(String menuId,ModelMap map) {
		logger.info( "Enter snmpUtil.do ..." );
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/snmpUtil";
	}
	
	@RequestMapping(value = "/pingUtil.do")
	public String pingUtil(ModelMap map,String menuId) {
		logger.info( "Enter pingUtil.do ..." );
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/pingUtil";
	}
	
	@RequestMapping(value = "/snmpUtilMain.do")
	public String snmpUtilMain(String menuId,ModelMap map) {
		logger.info( "Enter snmpUtilMain.do ..." );
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/snmpUtilMain";
	} 
	
	
	@RequestMapping(value = "/ping.do")
	public String ping(String pingIp, HttpServletResponse response) throws IOException{  
		logger.info( "Enter snmpGet.do ..." );
		String result="失败！";
		boolean flag =true;
		if(StringUtil.isNullString(pingIp)){
			flag =false;
			result="ip不能为空！";
		}
		StringBuffer pingresult=new StringBuffer();
		if(flag){
			SystemManagement sysmgt = new SystemManagement();
			pingresult = sysmgt.getPingResult(pingIp);
			if(pingresult!=null&&!StringUtil.isNullString(pingresult.toString())){
				result="成功！";
			}
		}
		JSONObject jo=new JSONObject();
		jo.put("pingResult", pingresult.toString().replace("\n", "<br/>"));
		jo.put("result", result);
		System.out.println("get the pingResult:>>>"+jo.toString());
	    response.getWriter().print(jo.toString());
	    return null;         
	} 
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/snmpGet.do")
	public String snmpGet(int snmpVersion,int timeout,int snmpPort,String devIp, String snmpComStr,String snmpOid, HttpServletResponse response) throws IOException{  
		logger.info( "Enter snmpGet.do ..." );
		System.out.println("获取到的参数：snmpVersion>>"+snmpVersion+"timeout>>"+timeout+"snmpPort>>"+snmpPort+"ip>>"+devIp +"  str>>"+snmpComStr +"  oid>>"+snmpOid);
		String result="未采集到数据，可能是Snmp设置问题或该设备不支持该OID，请检查确认!";
		boolean flag =true;
		List<SnmpInfo> listsnmp = null;
		try {
			if(StringUtil.isNullString(devIp)){
				flag =false;
				result="设备ip不能为空！";
			}
			if(StringUtil.isNullString(snmpComStr)){
				flag =false;
				result="缺省团体字不能为空！";
			}
			if(StringUtil.isNullString(snmpOid)){
				flag =false;
				result="snmpOid不能为空！";
			}
			TreeMap table=new TreeMap();
			if(flag){
				String ip = devIp.trim().replace("；", ";");
				String comstr = snmpComStr.trim().replace("；", ";");
				String oid = snmpOid.trim().replace("；", ";");
				if( comstr.length() == 0 ){
					comstr = WebFuncUtils.getParamValue("SnmpDefaultRoString");
				}
			table =MibBrowser.snmpget(ip, comstr, snmpPort,snmpVersion, oid);
				//RMI
//				ClientRmiController contr=new ClientRmiController();
//				table =contr.snmpGet(devIp, comstr, snmpPort, oid);
			}
			listsnmp = new ArrayList<SnmpInfo>();
			for (Iterator it = table.entrySet().iterator(); it.hasNext(); )
			{
				Entry entry = (Entry) it.next();
				SnmpInfo snmp=new SnmpInfo();
				snmp.setSnmpKey(String.valueOf(entry.getKey()));
				snmp.setSnmpVal(String.valueOf(entry.getValue()));
				listsnmp.add(snmp);
			}
		} catch (Exception e) {
			result="连接超时，请检查确认!";
			e.printStackTrace();
		}
		//SortUtils.sort(listsnmp,"getSnmpKey#1");//自定义list排序
		JSONArray jssnmp= JSONArray.fromObject(listsnmp);
		JSONObject jo=new JSONObject();
		jo.put("listSnmp", jssnmp.toString());
		jo.put("result", result);
//		System.out.println("json 串是：》》"+jo.toString());
	    response.getWriter().print(jo);
	    return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/snmpWalk.do")
	public String snmpWalk(int snmpVersion,int timeout,int snmpPort,String devIp, String snmpComStr,String snmpOid, HttpServletResponse response) throws IOException{
		logger.info( "Enter snmpWalk.do ..." );
		System.out.println("获取到的参数：snmpVersion>>"+snmpVersion+"timeout>>"+timeout+"snmpPort>>"+snmpPort+"ip>>"+devIp +"  str>>"+snmpComStr +"  oid>>"+snmpOid);
		String result="未采集到数据，可能是Snmp设置问题或该设备不支持该OID，请检查确认!";
		boolean flag =true;
		List<SnmpInfo> listsnmp = null;
		try {
			if(StringUtil.isNullString(devIp)){
				flag =false;
				result="设备ip不能为空！";
			}
			if(StringUtil.isNullString(snmpComStr)){
				flag =false;
				result="缺省团体字不能为空！";
			}
			if(StringUtil.isNullString(snmpOid)){
				flag =false;
				result="snmpOid不能为空！";
			}
			TreeMap table=new TreeMap();
			if(flag){
				String ip = devIp.trim().replace("；", ";");
				String comstr = snmpComStr.trim().replace("；", ";");
				String oid = snmpOid.trim().replace("；", ";");
				if( comstr.length() == 0 ){
					comstr = WebFuncUtils.getParamValue("SnmpDefaultRoString");
				}

			table =MibBrowser.snmpWalk(ip, comstr, snmpPort, snmpVersion,oid);
				
				//RMI
//				ClientRmiController contr=new ClientRmiController();
//				table=contr.snmpWalk(devIp, comstr, snmpPort, oid);
				
			}
			
			
			TreeMap<Double, OidValue> treeMap = new TreeMap<Double, OidValue>();
			treeMap = SortedOidValue.sortOidValue(snmpOid, table);
			List<Map.Entry<Double, OidValue>> oidValueTreeMapList = new ArrayList<Map.Entry<Double, OidValue>>(
					treeMap.entrySet());
			listsnmp = new ArrayList<SnmpInfo>();
			for (int i = 0; i < oidValueTreeMapList.size(); i++) {
				SnmpInfo info =new SnmpInfo();
				Double key = oidValueTreeMapList.get(i).getKey();
				String oid = oidValueTreeMapList.get(i).getValue().getOid();
				String value = oidValueTreeMapList.get(i).getValue().getValue();
				System.out.println("key:>>"+key +"   oid:>>"+oid);
				info.setSortKey(key);
				info.setSnmpKey(oid);
				info.setSnmpVal(value);
				listsnmp.add(info);
			}
		} catch (Exception e) {
			result="连接超时！";
			e.printStackTrace();
		}
		//SortUtils.sort(listsnmp,"getSortKey#1");//自定义list排序
		JSONArray jssnmp= JSONArray.fromObject(listsnmp);
		JSONObject jo=new JSONObject();
		jo.put("listSnmp", jssnmp.toString());
		jo.put("result", result);
	    response.getWriter().print(jo);
		return null;
	}  
	
	
	
	
}
