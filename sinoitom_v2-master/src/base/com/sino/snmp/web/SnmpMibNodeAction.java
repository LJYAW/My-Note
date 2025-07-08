package com.sino.snmp.web;

import com.adventnet.snmp.beans.SnmpTarget;
import com.adventnet.snmp.snmp2.SnmpOID;
import com.adventnet.snmp.snmp2.SnmpVar;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.snmp.entity.ParamTree;
import com.sino.snmp.entity.SnmpConstants;
import com.sino.snmp.entity.SnmpMibNode;
import com.sino.snmp.entity.SnmpSearch;
import com.sino.snmp.service.SnmpMibNodeService;
import com.sino.snmp.utils.ParseMibFilesList2;
import com.sino.snmp.utils.SnmpUtil;
import com.sino.utils.icmp.Ping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/snmp/mibNode")
public class SnmpMibNodeAction {
	
	private static Logger logger = LoggerFactory.getLogger(SnmpMibNodeAction.class);

	private static String viewPath = "/system/snmp";
	
	@Autowired
	private SnmpMibNodeService snmpMibNodeService;

//	----------------------------------------------------------------------------------------
	
	@RequestMapping(value="/mainTab.do")
	public String mainTab(){
		logger.info("Enter SnmpMibNodeAction mainTab....");
		return viewPath+"/mainTab";
	}
	
//	跳转到mib文件解析
	@RequestMapping(value="/mibFile.do")
	public String mibFile(String menuId,ModelMap map){
		logger.info("Enter SnmpMibNodeAction mibFile....");
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/mibFile/mibFile";
	}
//	选择文件
	@RequestMapping(value="/toSelectFile.do")
	public String toSelectFile(){
		logger.info("Enter SnmpMibNodeAction toSelectFile....");
		return viewPath+"/mibFile/importFile";
	}
	
//	解析oid
	@RequestMapping(value="/selectFile.do")
	public String selectFile(MultipartHttpServletRequest request, ModelMap map){
		
		logger.info("Enter SnmpMibNodeAction selectFile....");
		
		List<String> fileNameList = new ArrayList<String>();
		Iterator<String> fileNames = request.getFileNames();
//		返回的结果：key是文件名，value是该文件所有的节点对象的集合
		Map<String,JSONArray> returnMap = new HashMap<String,JSONArray>();
		
		while(fileNames.hasNext()){
			String fileName = fileNames.next();
			List<MultipartFile> fileList = request.getFiles(fileName);
			
			if(fileList!=null && fileList.size()>0){
				ParseMibFilesList2.parseMethod(fileList);
				
				if(fileList!=null && fileList.size()>0){
					Iterator<MultipartFile> fileIterator = fileList.iterator();
					while(fileIterator.hasNext()){
						MultipartFile multipartFile = fileIterator.next();
//						获取到的文件名，比如：RUIJIE-TC.mib/hh3c-oid.mib
						String originalFilename = multipartFile.getOriginalFilename();
						
						String fileNameStr = originalFilename.trim().split("\\.")[0];

						if(fileNameStr.startsWith("hh3c")){
							fileNameStr = fileNameStr.toUpperCase()+"-MIB";
						}
						List<SnmpMibNode> mibList = snmpMibNodeService.findByModuleName(fileNameStr);
						
						JSONArray ja = JSONArray.fromObject(mibList);
						
						returnMap.put(originalFilename.split("\\.")[0], ja);
						
						fileNameList.add(originalFilename);
					}
				}
				
				JSONObject json = JSONObject.fromObject(returnMap);
				
				map.put("result", "success");
				map.put("message", fileNameList);
				map.put("returnMap", json.toString());
			}else{
				map.put("result", "error");
				map.put("message", "上传文件内容为空！");
			}
		}
		return viewPath+"/mibFile/importFile";
	}
	
//	mib树
	@RequestMapping(value = "/mibTree.do")
	public String mibTree(ModelMap map,String type){
		logger.info("Enter mibTree.do...");
		
		List<ParamTree> total = new ArrayList<ParamTree>();
		long starttime=System.currentTimeMillis();
		List<SnmpMibNode> allData = snmpMibNodeService.getDataByPid(0l);
		for (SnmpMibNode snmpMibNode : allData) {
			if(snmpMibNode.getFlag()!=null && snmpMibNode.getFlag()==1){
				ParamTree pt = new ParamTree();
				pt.setPid(snmpMibNode.getParentId()+"");
				pt.setId(snmpMibNode.getNodeId()+"");
				pt.setText(snmpMibNode.getNodeName()+"("+snmpMibNode.getNodeNo()+")");
				pt.setOid(snmpMibNode.getOid());
				total.add(pt);
			}
		}
		
		JSONArray json = JSONArray.fromObject(total);
		String treeData = json.toString();
		map.put("treeData", treeData);
		long endtime=System.currentTimeMillis();
		System.out.println("MIB数加载消耗时间："+(endtime-starttime)+" ms");
		return viewPath+"/mibTree";
	}
	
	@RequestMapping(value = "/mibTreeByPId.do")
	public String mibTreeByPId(ModelMap map,String prentId,HttpServletResponse response) throws IOException{
		logger.info("Enter mibTree.do...");
		
		List<ParamTree> total = new ArrayList<ParamTree>();
		long starttime=System.currentTimeMillis();
		
		List<SnmpMibNode> allData = snmpMibNodeService.getDataByPid(Long.parseLong(prentId));
		for (SnmpMibNode snmpMibNode : allData) {
			if(snmpMibNode.getFlag()!=null && snmpMibNode.getFlag()==1){
				ParamTree pt = new ParamTree();
				pt.setPid(snmpMibNode.getParentId()+"");
				pt.setId(snmpMibNode.getNodeId()+"");
				pt.setText(snmpMibNode.getNodeName()+"("+snmpMibNode.getNodeNo()+")");
				pt.setOid(snmpMibNode.getOid());
				total.add(pt);
			}
		}
		
		JSONArray json = JSONArray.fromObject(total);
		String treeData = json.toString();
		response.setContentType("text/json");  
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().print(treeData);
		long endtime=System.currentTimeMillis();
		System.out.println("MIB数加载消耗时间："+(endtime-starttime)+" ms");
		return null;
	}
	
	@RequestMapping(value="/mibBrowser.do")
	public String mibBrowser(String menuId,ModelMap map){
		logger.info("Enter SnmpMibNodeAction mibBrowser....");
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/mibBrowser/mibBrowser";
	}
	
	@RequestMapping(value="/selectDevice.do")
	public String selectDevice(){
		logger.info("Enter SnmpMibNodeAction selectDevice....");
		return viewPath+"/mibBrowser/selectDevice";
	}
	
	
	@RequestMapping(value="/initParam.do")
	@ResponseBody
	public String  initParam(SnmpSearch snmpSearch,HttpServletResponse response) throws IOException{
		logger.info("Enter SnmpMibNodeAction initParam....");
		
		SnmpConstants.Security_Level = snmpSearch.getSecurity_level();
		SnmpConstants.Host=snmpSearch.getIp_address();
		 if(snmpSearch.getSnmp_versionV1()==1){
			 SnmpConstants.Version=snmpSearch.getSnmp_version();
		 }
		 if(snmpSearch.getSnmp_versionV3()>0){
			 SnmpConstants.Version=snmpSearch.getSnmp_versionV3();
		 }
		
		 SnmpConstants.Community=snmpSearch.getRo_community_string();
		 SnmpConstants.SnmpUser=snmpSearch.getUsername();
		 
		 if(SnmpConstants.Security_Level==1){
			 SnmpConstants.Auth_Protocol=snmpSearch.getAuth_protocol();//HMAC_MD5_96
			 SnmpConstants.SnmpPasswd= snmpSearch.getAuth_password();
		 }else if(SnmpConstants.Security_Level==3){
			 SnmpConstants.Auth_Protocol=snmpSearch.getAuth_protocol();//HMAC_MD5_96
			 SnmpConstants.Auth_Password= snmpSearch.getAuth_password();
			 SnmpConstants.Privacy_Protocol= snmpSearch.getPrivacy_protocol();//加密算法
			 SnmpConstants.Privacy_Password= snmpSearch.getPrivacy_password();//私有密码
		 }
		 
		 JSONObject jo = JSONObject.fromObject(snmpSearch);
		return jo.toString();
	}
	
	
	@RequestMapping(value="/snmpTest.do")
	public String snmpTest(SnmpSearch snmpSearch,HttpServletResponse response) throws IOException{
		logger.info("Enter SnmpMibNodeAction snmpTest....");
		
		String result="";
		if (!Ping.isAlive(snmpSearch.getIp_address())) {
			System.out.println(snmpSearch.getIp_address() + "is not alive,please check it!");
			result=snmpSearch.getIp_address() + "is not alive,please check it!";
			response.setCharacterEncoding("UTF-8"); 
		    response.getWriter().print(result);
			return null;
		}
		
		
		Properties props = new Properties();
		
		int level = snmpSearch.getSecurity_level();
		
		 props.setProperty("security_level", String.valueOf(level));
		 
		 if(snmpSearch.getSnmp_versionV1()==1){
			 int version = snmpSearch.getSnmp_version();
			 props.setProperty("snmp_version", String.valueOf(version));
		 }else{
			 props.setProperty("snmp_version", "");
		 }
		
		 props.setProperty("community", snmpSearch.getRo_community_string());
		 props.setProperty("rw_community_string", snmpSearch.getRw_community_string());
		 props.setProperty("snmp_versionV3", String.valueOf(snmpSearch.getSnmp_versionV3()));
		 props.setProperty("security_level", String.valueOf(snmpSearch.getSecurity_level()));//AuthNoPriv
		 props.setProperty("userName",snmpSearch.getUsername());
		 
		 if(level==1){
			 props.setProperty("auth_protocol", snmpSearch.getAuth_protocol());//HMAC_MD5_96
			 props.setProperty("passwd", snmpSearch.getAuth_password());
		 }else if(level==3){
			 props.setProperty("auth_protocol", snmpSearch.getAuth_protocol());//HMAC_MD5_96
			 props.setProperty("passwd", snmpSearch.getAuth_password());
			 props.put("privacy_protocol", snmpSearch.getPrivacy_protocol());//加密算法
			 props.put("privacy_password", snmpSearch.getPrivacy_password());//私有密码
		 }
			
		
		SnmpTarget snmpTarget=new SnmpTarget();
		System.out.print(props.getProperty("snmp_version"));
		if(snmpSearch.getSnmp_versionV1()==1){ 
			if(snmpSearch.getSnmp_version()==0){//v1
				snmpTarget=SnmpUtil.GetV1SnmpParameter(snmpTarget, snmpSearch.getIp_address(), snmpSearch.getRo_community_string());
			}else{ //v2c
				snmpTarget=SnmpUtil.GetV2cSnmpParameter(snmpTarget, snmpSearch.getIp_address(), snmpSearch.getRo_community_string());
			}
			
		} 
		if(snmpSearch.getSnmp_versionV3()==3){ //v3
			snmpTarget=SnmpUtil.GetV3SnmpParameter(props, snmpTarget, snmpSearch.getIp_address());
		}
		
		SnmpVar snmpvar = snmpTarget.snmpGet(new SnmpOID(".1.3.6.1.2.1.1.2.0")); //SNMPv2-MIB:sysObjectID
        if(snmpvar == null)
        {
        	result=SnmpConstants.Host + "'s snmp is not working normally! \nplease check if it's not configured or blocked by firewall rules!";
        	snmpTarget.releaseResources();
        	return null;
        } 
        else 
        	result="   Snmp test pass!";
		
        response.setCharacterEncoding("UTF-8"); 
		response.getWriter().print(result);
		return null;
	}
}
