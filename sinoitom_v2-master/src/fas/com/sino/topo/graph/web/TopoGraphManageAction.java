package com.sino.topo.graph.web;

import com.sino.base.common.LoadProperties;
import com.sino.base.common.util.*;
import com.sino.cmdb.graph.entity.LinkParam;
import com.sino.cmdb.graph.entity.TopoParam;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.snmp.entity.SnmpSearch;
import com.sino.topo.discovery.TopoDiscovery;
import com.sino.topo.discovery.TopoDiscoveryCache;
import com.sino.topo.graph.entity.ParamTopoGraph;
import com.sino.topo.graph.entity.TopoGraph;
import com.sino.topo.graph.entity.UniLineResult;
import com.sino.topo.graph.entity.UniWayLine;
import com.sino.topo.graph.service.TopoGraphManageService;
import com.sino.topo.graph.service.TopoGraphService;
import com.sino.topo.objectType.entity.TopoObjectType;
import com.sino.topo.objectType.service.TopoObjectTypeService;
import com.sino.topo.topoLine.entity.TopoLine;
import com.sino.topo.topoLine.service.TopoLineService;
import com.sino.topo.topoNode.entity.TopoNode;
import com.sino.topo.topoNode.service.TopoNodeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sino.distributed.controller.ClientRmiController;
import smartlink.utils.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@RequestMapping(value = "/topo/graphManage")
@Controller
public class TopoGraphManageAction {

	private static String viewPath = "/topo/topoGraphManage";
	
	private static Logger logger = LoggerFactory.getLogger(TopoGraphManageAction.class);
    
	
	@Autowired
	private TopoGraphManageService topoGraphManageSrvice;
	
	@Autowired
	private TopoGraphService topoGraphService;
	
	@Autowired
	private TopoObjectTypeService topoObjectTypeService;
	
	@Autowired
	private TopoNodeService topoNodeService;
	
	@Autowired
	private TopoLineService topoLineService;
	
	@RequestMapping(value = "/main.do")
	public String index(String menuId,ModelMap map)  {
		
		logger.info( "Enter TopoGraphManageAction main.do ..." );
		List<TopoGraph> list = topoGraphManageSrvice.getAllTopoGraph();
		String jsonStr = topoGraphManageSrvice.getJsonTopoGraphListStr(list);
		map.put("jsonData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/index";
	}
	
	
	@RequestMapping(value = "/topoSearch.do")
	public String topoSearch(ModelMap map) {
		logger.info("Enter topoSearch.do ...");

		return viewPath + "/topoSearch";
	}
	
	@RequestMapping(value = "/discoveryTopo.do")
	public String discoveryTopo(ModelMap map) {
		logger.info("Enter main.do ...");

		return viewPath + "/discoveryTopo";
	}
	
	
	@RequestMapping(value = "/getAllData.do")
	public String getAllData(HttpServletResponse response) throws IOException{  
		
		logger.info( "Enter getAllData.do ..." );
		List<ParamTopoGraph> list1 = new ArrayList<ParamTopoGraph>();
		List<TopoGraph> list = topoGraphManageSrvice.getAllTopoGraph();
		for (TopoGraph topoGraph : list) {
			ParamTopoGraph topoGraph_two = new ParamTopoGraph();
			BeanUtils.copyProperties(topoGraph, topoGraph_two);
//			topoGraph_two.setSeedNodeIp(topoGraph.getSeedNodeIp());
			
			topoGraph_two.setCreateDate(DateUtil.getTimeShow(topoGraph.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			list1.add(topoGraph_two);
			
		}
		String jsonStr = topoGraphManageSrvice.getJsonParamTopoGraphListStr(list1);
		JSONObject jo = new JSONObject();
		jo.put("jsonData", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
	
	
	@RequestMapping(value = "/edit.do")
	public String edit(Long id, ModelMap map) throws IOException{
		
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		TopoGraph topoGraph = topoGraphManageSrvice.getTopoGraphByID(id);
		ParamTopoGraph topoGraph_two = new ParamTopoGraph();
		BeanUtils.copyProperties(topoGraph, topoGraph_two);
//		topoGraph_two.setSeedNodeIp(Util.str_addr(topoGraph.getSeedNodeId()-topoGraph.getGraphId()));
		topoGraph_two.setCreateDate(DateUtil.getTimeShow(topoGraph.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		map.put("topoGraph", topoGraph_two);
		return viewPath+"/edit";         
	}
	@RequestMapping(value = "/save.do")
	public String save(String action, String id,String editName, String time,ParamTopoGraph topoGraph, ModelMap map,
			MultipartHttpServletRequest request) throws IOException{
        logger.info( "Enter save.do ..." );
		String path=request.getSession().getServletContext().getRealPath("/");//取得服务器绝对路径
//		String imgpath = path+"icon/bgpic/";  //上传路径
		String imgpath = LoadProperties.UPLOAD_BGIMG_URL; //上传路径
		//获取图片名称
		MultipartFile file = (MultipartFile) request.getFile("picture");// 把文件读入
		String imgName = file.getOriginalFilename();
		if(!StringUtil.isNullString(editName)){
			imgName=editName;
		}
		if (!file.isEmpty()) {
				if (!StringUtil.isNullString(imgName)) {
					String filePath = imgpath;
					File dirPath = new File(filePath);
					if (!dirPath.exists()) {
						dirPath.mkdir();
					}

					filePath = filePath + "/" + imgName;
					FileOutputStream fos = new FileOutputStream(filePath);
					fos.write(file.getBytes());
					fos.close();
				}
		}
		
		TopoGraph editTopoGraph = topoGraphManageSrvice.getTopoGraphByID(Long.parseLong(id));
		BeanUtils.copyProperties(topoGraph, editTopoGraph);
		
		editTopoGraph.setSeedNodeId(Long.parseLong(id)+Util.ip2long(topoGraph.getSeedNodeIp()));
		
		if (StringUtil.isNullString(editName)) {
			editTopoGraph.setBgPicture(imgName);
		} else {

			editTopoGraph.setBgPicture(editName);
			topoGraphManageSrvice.editPictureName(imgpath,editName, imgName);

		}
		editTopoGraph.setCreateTime(Timestamp.valueOf(time));
		topoGraphManageSrvice.saveTopoGraph(editTopoGraph);
	    String jsonStr = topoGraphManageSrvice.getJsonTopoGraphObjStr(editTopoGraph);
	    map.put("saveData", jsonStr);
	    map.put("result", "success");
		map.put("action", action);
		
		return viewPath+"/edit";        
	}
	@RequestMapping(value = "/delete.do")
	public String delete(Long id, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		
		topoGraphManageSrvice.deleteTopoGraph(id);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		return null;         
	}
	@RequestMapping(value = "/mulitDelete.do")
	public String mulitDelete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter mulitDelete.do ..." );
		
		topoGraphManageSrvice.deleteTopoGraph(id.split(";"));
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	@RequestMapping(value = "/changeDefault")
	public String changeDefault(String id,HttpServletResponse response) throws IOException {
		logger.info( "Enter changeDefault ..." );
		topoGraphManageSrvice.settingTopoDefault(Long.parseLong(id));
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	@RequestMapping(value = "/editbgPicture.do")
	public String editbgPicture(Long id, ModelMap map) throws IOException{
		
		logger.info( "Enter editbgPicture.do ..." );
		map.put("action", "edit");
		TopoGraph topoGraph = topoGraphManageSrvice.getTopoGraphByID(id);
		map.put("topoGraph", topoGraph);
		return viewPath+"/editbgPicture";         
	}

	@RequestMapping(value = "/savebgPicture.do")
	public String savebgPicture(String action, String id,String editName, TopoGraph topoGraph,
			ModelMap map,MultipartHttpServletRequest request) throws IOException {
		
		logger.info("Enter savebgPicture.do ...");
		
		String path=request.getSession().getServletContext().getRealPath("/");//取得服务器绝对路径
//		String imgpath = path+"icon/bgpic/";  //上传路径
		String imgpath =LoadProperties.UPLOAD_BGIMG_URL;
		//获取图片名称
		MultipartFile file = (MultipartFile) request.getFile("picture");// 把文件读入
		String imgName = file.getOriginalFilename();
		if(!StringUtil.isNullString(editName)){
			imgName=editName;
		}
		if (!file.isEmpty()) {
				if (!StringUtil.isNullString(imgName)) {
					String filePath = imgpath;
					File dirPath = new File(filePath);
					if (!dirPath.exists()) {
						dirPath.mkdir();
					}

					filePath = filePath + "/" + imgName;
					FileOutputStream fos = new FileOutputStream(filePath);
					fos.write(file.getBytes());
					fos.close();
				}
		}
		
		TopoGraph editTopoGraph = topoGraphManageSrvice.getTopoGraphByID(Long.parseLong(id));
		
		
		if (StringUtil.isNullString(editName)) {
			editTopoGraph.setBgPicture(imgName);
		} else {
			
			editTopoGraph.setBgPicture(editName);
		}
		
		BeanUtils.copyProperties(topoGraph, editTopoGraph);
		topoGraphManageSrvice.saveTopoGraph(editTopoGraph);
		String jsonStr = topoGraphManageSrvice.getJsonTopoGraphObjStr(editTopoGraph);
	    map.put("saveData", jsonStr);
		map.put("result", "success");
		map.put("action", action);
		
		return viewPath+"/editbgPicture";
      }
	//展示背景图
	@RequestMapping(value = "/showbgPicture.do")
	public String showbgPicture(Long id, ModelMap map) throws IOException{
		
		logger.info( "Enter edit.do ..." );
		map.put("action", "showbgPicture");
		TopoGraph topoGraph = topoGraphManageSrvice.getTopoGraphByID(id);
		map.put("id", id);
		map.put("topoGraph", topoGraph);
		map.put("result", "success");
		return viewPath+"/showbgPicture";         
	}
	
	
	@RequestMapping(value = "/checkFileExists.do")
	public String checkFileExists(String picName, String editName,
			HttpServletRequest request,HttpServletResponse response) throws IOException {

		logger.info("Enter savebgPicture.do ...");
		String path=request.getSession().getServletContext().getRealPath("/");//取得服务器绝对路径
//		String imgpath = path+"icon/bgpic/";  //上传路径
		String imgpath =LoadProperties.UPLOAD_BGIMG_URL;
		if(!StringUtil.isNullString(picName)){
			picName=new String(picName.getBytes("iso-8859-1"),"utf-8");
			if(picName.contains("\\")){
				picName=picName.substring(picName.lastIndexOf("\\")+1,picName.length());
			}
			
		}
		
		JSONObject jo = new JSONObject();
		if (StringUtil.isNullString(editName)
				&& !StringUtil.isNullString(picName)) {
			File dirPath = new File(imgpath + picName);

			if (!dirPath.exists()) {
				
				jo.put("result", "success");
			} else {
				jo.put("result", "error");
			}
		}
		if (!StringUtil.isNullString(editName)
				&& StringUtil.isNullString(picName)) {
			File dirPath = new File(imgpath + editName);
			if (!dirPath.exists()) {
				System.out.println("文件不存在");
				jo.put("result", "success");
			} else {
				jo.put("result", "error");
			}
		}
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;

	}
	
	
	@RequestMapping(value="/showGraphTopo.do")
	public String showGraphTopo(long graphId,ModelMap map){
		
		StringBuilder imgsb = new StringBuilder();
		StringBuilder codesb = new StringBuilder();

		TopoObjectType obj;
//		获取左侧所有的资源分类名称
		List<ResItemParam> plist = topoObjectTypeService
				.getAllClassCode();
//		获取左侧所有的资源
		List<TopoObjectType> list = topoObjectTypeService.getAll();

//		将分类名称用,连接起来
		for (int i = 0; i < plist.size(); i++) {
			codesb.append(plist.get(i).getId());
			if (i < plist.size() - 1) {
				codesb.append(",");
			}
		}

//		每个资源的图片
		for (int i = 0; i < list.size(); i++) {
			obj = list.get(i);
			String str = obj.getObjIcon() + "-" + obj.getClassCode()
					+ "|" + obj.getClassCode() + "#"
					+ obj.getClassName() + "#" + obj.getTypeCode()
					+ "#" + obj.getObjName() + "#" + obj.getObjIcon()
					+ "#" + obj.getObjTypeID()+"#"+obj.getObjType();
			imgsb.append(str);
			if (i < list.size() - 1) {
				imgsb.append(",");
			}
		}
		
		map.put("imgsb", imgsb.toString());
		map.put("codesb", codesb.toString());
		map.put("plist", JSONArray.fromObject(plist));
		
//		获取画布
		TopoGraph graph=topoGraphManageSrvice.getTopoGraphByID(graphId);
		
		map.put("topoGraph", graph);
//		return viewPath+"/graphTopo";
		return viewPath+"/itopo";
	}
	
	@RequestMapping(value="/showDefaultTopo.do")
	public String showDefaultTopo(ModelMap map){
		logger.info("Enter showDefaultTopo.do ...");
		TopoGraph graph=topoGraphService.getByFlag(1);
		if(graph!=null){
			map.put("graphID", graph.getGraphId());
		}
		return viewPath+"/defaultTopo";
	}
	
	
	//新的搜索topo方法
	@RequestMapping(value = "/searchFullTopo.do")
	public String searchFullTopo(SnmpSearch snmpSearch) throws Exception{
		
		
		long startTime=System.currentTimeMillis();
		
		Properties topoprops = new Properties();
		
		int level = snmpSearch.getSecurity_level();
		
		 topoprops.setProperty("seedip", snmpSearch.getIp_address());
		 topoprops.setProperty("netRange", snmpSearch.getNetRange());
		 topoprops.setProperty("max_Hop", String.valueOf(snmpSearch.getMax_hop()));
		 topoprops.setProperty("snmpPort", String.valueOf(snmpSearch.getSnmpPort()));
		 topoprops.setProperty("timeout", String.valueOf(snmpSearch.getTimeout()));
		 topoprops.setProperty("security_level", String.valueOf(level));
		 
		 topoprops.setProperty("topoMethod", snmpSearch.getTopoMethod());
		 
		 if(snmpSearch.getSnmp_versionV1()==1){
			 int version = snmpSearch.getSnmp_version();
			 topoprops.setProperty("snmp_version", String.valueOf(version));
		 }else{
			 topoprops.setProperty("snmp_version", "");
		 }
		
		 topoprops.setProperty("community", snmpSearch.getRo_community_string());
		 topoprops.setProperty("rw_community_string", snmpSearch.getRw_community_string());
		 topoprops.setProperty("snmp_versionV3", String.valueOf(snmpSearch.getSnmp_versionV3()));
		 topoprops.setProperty("security_level", String.valueOf(snmpSearch.getSecurity_level()));//AuthNoPriv
		 topoprops.setProperty("userName",snmpSearch.getUsername());
		 
		 if(level==1){
			 topoprops.setProperty("auth_protocol", snmpSearch.getAuth_protocol());//HMAC_MD5_96
			 topoprops.setProperty("passwd", snmpSearch.getAuth_password());
		 }else if(level==3){
			 topoprops.setProperty("auth_protocol", snmpSearch.getAuth_protocol());//HMAC_MD5_96
			 topoprops.setProperty("passwd", snmpSearch.getAuth_password());
			 topoprops.put("privacy_protocol", snmpSearch.getPrivacy_protocol());//加密算法
			 topoprops.put("privacy_password", snmpSearch.getPrivacy_password());//私有密码
		 }
		 
		
//		 
//		 if(!StringUtil.isNullString(snmpSearch.getPrivacy_protocol())&&!StringUtil.isNullString(snmpSearch.getPrivacy_password())){
//			 topoprops.put("privacy_protocol", snmpSearch.getPrivacy_protocol());//加密算法
//			 topoprops.put("privacy_password", snmpSearch.getPrivacy_password());//私有密码
//		 }
		
		
		 //local
		 TopoDiscovery discover=new TopoDiscovery();
		 discover.searchFullTopo(topoprops);
		 
		 //RMI
//		 ClientRmiController contr=new ClientRmiController();
//	     contr.discoveryFullTopo(topoprops);
		 
		 long endTime=System.currentTimeMillis();
		 
		 System.out.println("Topo搜索时间共计时间："+(endTime-startTime)/1000+"秒");
		 
		 return null;
		
	}
	
	// ip route search
//	@RequestMapping(value = "/searchTopo.do")
//	public String searchTopo(String seedIp , String defaultSnmpStr,String scan_data,String threadNum,String timeout,String retries,String ifGenTopo,String searchDepth,String genTopoMethod){
//		
//		 Properties topoprops = new Properties();
//		 topoprops.put("snmpversion", "v1");
//	     topoprops.put("seedip", seedIp);
//	     topoprops.put("defaultsnmpstr", defaultSnmpStr);
//	     topoprops.put("ifgentopo", "1");
//	     topoprops.put("genTopoMethod", "ipRoute");
//	     topoprops.put("scan_data", scan_data);
//	     
//	     ClientRmiController contr=new ClientRmiController();
//	     contr.topoDiscoveryIpRoute(topoprops);
//		return null;
//	}
	
	// ip addrtab search ah-icbc
	@RequestMapping(value = "/searchTopo.do")
	public String searchTopo(String seedIp , String defaultSnmpStr,String scan_data,String scan_data2,String threadNum,String timeout,String retries,String ifGenTopo,String searchDepth,String genTopoMethod,String areaNetIp,String areaNetMask,HttpServletResponse response) throws IOException{
		
		Properties topoprops = new Properties();
		topoprops.put("snmpversion", "v1");
		topoprops.put("seedip", seedIp);
		topoprops.put("defaultsnmpstr", defaultSnmpStr);
		topoprops.put("ifgentopo", ifGenTopo);
		topoprops.put("genTopoMethod", "ipRoute");
		topoprops.put("scan_data", scan_data);
		topoprops.put("scan_data2", scan_data2);
		topoprops.put("searchDepth", searchDepth);
		topoprops.put("areaNetIp", areaNetIp);
		topoprops.put("areaNetMask", areaNetMask);

		ClientRmiController contr = new ClientRmiController();
		contr.topoDiscoveryIpRoute(topoprops);
	    
		return null;
	}
	
	@RequestMapping(value="/outputLogs.do")
	public String outputLogs(HttpServletResponse response) throws IOException{
		 
		ClientRmiController contr=new ClientRmiController();
		String output= contr.searchTopoMessageOutput();
		 if( output!=null&&output.length()>0){
			 String outputstr = output.toString();
			 outputstr = outputstr.replaceAll("\n", "</br>");
			 
			 JSONObject jo=new JSONObject();
			 jo.put("outputstr", outputstr);
			 response.setContentType("text/json");  
			 response.setCharacterEncoding("UTF-8"); 
			 response.getWriter().print(jo.toString());
		 }
		
		 return null;
		
	}
	
	@RequestMapping(value = "/autoLoad.do")
	public String autoLoad(Long graphId,HttpServletResponse response) throws IOException {

		List<UniWayLine> unilinelist = new ArrayList<UniWayLine>();

		List<TopoNode> retNodelist = new ArrayList<TopoNode>();
		TopoGraph graph = null;
		if(graphId!=null){
			 graph = topoGraphService.getByID(graphId);
		}else{
			 graph = topoGraphService.getByFlag(1);
		}
		
		List<TopoNode> nodelist = topoNodeService.getByGraphID(graph
				.getGraphId());
		List<TopoLine> linelist = topoLineService.getByGraphID(graph
				.getGraphId());

		long rootNodeId = graph.getSeedNodeId();

		
		for (int i = 0; i < linelist.size(); i++) {
			TopoLine line = linelist.get(i);
			line.setFlag(0);
		}
		unilinelist = findSubNode(linelist, nodelist, retNodelist, rootNodeId,
				0, unilinelist);

		int depth = 1;
		int[] levelNodes = new int[10];
		int count = 0;
		for (int i = 0; i < UniWayLine.getSortList(unilinelist).size(); i++) {//计算每层节点的个数
//			System.out.println("depth==" + unilinelist.get(i).getDepth() + "subnodeId=="
//					+ unilinelist.get(i).getNodeAIP()+"------->"+unilinelist.get(i).getNodeBIP());
			if (unilinelist.get(i).getDepth() == depth) {
				count++;
			}

			else if (unilinelist.get(i).getDepth() > depth) {
				levelNodes[depth] = count;
				depth = unilinelist.get(i).getDepth();
				count = 1;

			}

		}
		levelNodes[depth] = count;

		String graphStr = topoGraphService.getJsonObjStr(graph);

		List<TopoNode> nodes = new ArrayList<TopoNode>();

		long endTime = System.currentTimeMillis();

		long startTime = endTime - 3600 * 1000;

		for (TopoNode node : nodelist) {
//			int devStatus = alarmAnalysisService.getCountByDevId(node.getDevID()==null?-1:node.getDevID(), startTime, endTime);
//			node.setDevStatus(devStatus);
			nodes.add(node);
		}
		String nodeStr = topoNodeService.getJsonListStr(nodes);

		String lineStr = topoLineService.getJsonListStr(linelist);

		UniLineResult result = new UniLineResult();
		result.setDepth(depth);
		result.setLineArray(levelNodes);
		result.setUnilinelist(unilinelist);

		// JSONObject jo = JSONObject.fromObject(result);
		JSONObject jo = new JSONObject();
		jo.put("result", result);
		jo.put("graphStr", graphStr);
		jo.put("nodeStr", nodeStr);
		jo.put("lineStr", lineStr);
		// jo.put("textStr", textStr);
		// jo.put("nodeTextRows", nodeTextStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;

	}
	
	public List<UniWayLine> findSubNode(List<TopoLine> linelist,
			List<TopoNode> nodelist, List<TopoNode> retNodelist, long nodeId,
			int depth, List<UniWayLine> unilinelist) {

		Set<Long> nodeSet = new TreeSet<Long>();

		for (int i = 0; i < linelist.size(); i++) {
			UniWayLine uniline = new UniWayLine();
			TopoLine line = linelist.get(i);
//			System.out.println(line.getNodeAName()+"---->"+line.getNodeBName());
			
			if ((line.getNodeAId() == nodeId) && (line.getFlag() == 0)) {
				uniline.setNodeFrom(nodeId);
				uniline.setNodeAIP(line.getNodeAName());
				uniline.setIf_from(line.getIfIndexA()==null?0:line.getIfIndexA());
				uniline.setNodeTo(line.getNodeBId());
				uniline.setNodeBIP(line.getNodeBName());
				uniline.setIf_to(line.getIfIndexB()==null?0:line.getIfIndexB());
				uniline.setIf_fromName(line.getIfNameA()==null?"":line.getIfNameA());
				uniline.setIf_toName(line.getIfNameB()==null?"":line.getIfNameB());
				uniline.setDepth(depth + 1);
				unilinelist.add(uniline);
				line.setFlag(1);
				nodeSet.add(line.getNodeBId());
			} else if ((line.getNodeBId() == nodeId) && (line.getFlag() == 0)) {
				uniline.setNodeFrom(nodeId);
				uniline.setNodeAIP(line.getNodeBName());
				
				uniline.setIf_from(line.getIfIndexB()==null?0:line.getIfIndexB());
				uniline.setNodeTo(line.getNodeAId());
				uniline.setNodeBIP(line.getNodeAName());
				uniline.setIf_to(line.getIfIndexA()==null?0:line.getIfIndexA());
				uniline.setIf_fromName(line.getIfNameB()==null?"":line.getIfNameB());
				uniline.setIf_toName(line.getIfNameA()==null?"":line.getIfNameA());
				uniline.setDepth(depth + 1);
				unilinelist.add(uniline);
				line.setFlag(1);
				nodeSet.add(line.getNodeAId());
			}
		}

		if (nodeSet.size() > 0) {
			depth++;
			Iterator iter = nodeSet.iterator();
			while (iter.hasNext()) {
				long subnodeId = (Long) iter.next();
//				System.out.println("depth==" + depth + "subnodeId=="
//						+ subnodeId);
				findSubNode(linelist, nodelist, retNodelist, subnodeId, depth,
						unilinelist);
			}
		}

		return unilinelist;

	}
	
	@RequestMapping(value = "/getData.do")
	public String getData(String graphID,ModelMap map, HttpServletResponse response)
			throws IOException {
		logger.info("Enter IndItemEventTypeMap getData.do...");

		// 获取默认graph
		String nodeStr = "";
		String lineStr = "";
		String graphStr = "";
		String lineInfoStr="";
		// String textStr="";
		long endTime = System.currentTimeMillis();
		long startTime = endTime - 3600 * 1000;

		List<TopoNode> nodes = new ArrayList<TopoNode>();

//		1、获取画布
//		if(!StringUtil.isNullString(graphID)){
		TopoGraph graph = topoGraphService.getByID(Long.parseLong(graphID));
		if (graph != null) {
			graphStr = topoGraphService.getJsonObjStr(graph);

			// 2、获取node
			List<TopoNode> nodelist = topoNodeService.getByGraphID(graph
					.getGraphId());

			for (TopoNode node : nodelist) {
//				获取设备状态--是否处于告警中
//				int devStatus = alarmAnalysisService.getCountByDevId(
//						node.getDevID()==null?-1:node.getDevID(), startTime, endTime);
//				node.setDevStatus(devStatus);
//				if(!Ping.isAlive(node.getDevIpAddr())){
//					node.setDevStatus(-1); //ping 不通
//				}
				nodes.add(node);
			}

			nodeStr = topoNodeService.getJsonListStr(nodes);

			// List<TopoText>
			// textlist=topoTextService.getDataByGraphID(graph.getGraphId());
			// textStr=topoTextService.getJsonListStr(textlist);
			List<TopoLine> linelist = topoLineService.getByGraphID(graph.getGraphId());
			
			for(int i=0;i<linelist.size();i++){
				TopoLine line=linelist.get(i);
				lineInfoStr+=line.getNodeAId()+"_"+line.getIfNameA()+";"+line.getNodeBId()+"_"+line.getIfNameB()+";";
			}
					
			lineStr = topoLineService.getJsonListStr(linelist);
		}
	
		JSONObject jo = new JSONObject();
		// jo.put("linkRows", jsonStr);
		jo.put("graphStr", graphStr);
		jo.put("nodeStr", nodeStr);
		jo.put("lineStr", lineStr);
		jo.put("lineInfoStr", lineInfoStr);
		// jo.put("textStr", textStr);
		// jo.put("nodeTextRows", nodeTextStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value = "/saveTopo.do")
	public String saveTopo(String nodesStr, String linksStr,String graphStr,
			HttpServletResponse response) throws IOException {

		// TopoGraph savegraph=new TopoGraph();
		TopoGraph saveGraph = null;
		List<Long> nodeids = new ArrayList<Long>();
		if (!StringUtil.isNullString(graphStr)) {

			JSONObject jsonObject = JSONObject.fromObject(graphStr);

			saveGraph = (TopoGraph) JSONObject.toBean(jsonObject,TopoGraph.class);
					
			TopoGraph graph = topoGraphService.getByID(saveGraph.getGraphId());

//			if (graph != null) {
				// saveGraph.setCreateTime(now);
				// saveGraph.setFlag(1);
				// saveGraph.setStatus(1);
				// graph.setStatus(1);
				// topoGraphService.save(graph);
				// BeanUtils.copyProperties(graph, savegraph);

//			}
			List<TopoNode> nodelist = topoNodeService.getByGraphID(saveGraph.getGraphId());
			List<TopoLine> linelist=topoLineService.getByGraphID(saveGraph.getGraphId());
			
			
			List<TopoNode> saveNodes = new ArrayList<TopoNode>();
			List<TopoLine> saveLines= new ArrayList<TopoLine>();
			
			if (!StringUtil.isNullString(nodesStr)) {
				List<TopoParam> saveNodeList = JsonUtils.getDTOList(nodesStr,TopoParam.class);
						

				if (!saveNodeList.isEmpty()) {
					for (int i = 0; i < saveNodeList.size(); i++) {
						TopoParam saveNode = saveNodeList.get(i);
						if(saveNode.getNodeInfo()!=null&&saveNode.getNodeInfo().contains("#")){  //新增节点
							
							String [] res=saveNode.getNodeInfo().split("#");
							
							TopoNode node =new TopoNode();
							node.setGraphId(saveGraph.getGraphId());   //13#网络设备#0#子网#subnet.png#757bc8b3-a304-4ec4-9570-952e0f7c7cbb
							
//							long now=System.currentTimeMillis()+i;
							node.setNodeId(Long.parseLong(saveNode.getNode_ID()));
							node.setNodeType(Integer.parseInt(res[6]));
							node.setNodeName(saveNode.getNode_Name());
							node.setPosX(saveNode.getPosX());
							node.setPosY(saveNode.getPosY());
							node.setWidth(1d);
							node.setHeight(1d);
							node.setObjTypeId(res[5]);
							node.setNamePosX(saveNode.getNamePosX());
							node.setNamePosY(saveNode.getNamePosY());
							node.setDevClassCode(Integer.parseInt(res[0]));
							node.setDevClassName(res[1]);
							node.setDevTypeCode(Integer.parseInt(res[2]));
							node.setDevTypeName(res[3]);
							node.setObjType(Integer.parseInt(res[6]));		
							node.setObjIcon(res[4]);
							node.setDevStatus(0);
							node.setFlag(1);
							saveNodes.add(node);
							
						}else{   //数据库中load出来的节点
							for (int j = 0; j < nodelist.size(); j++) {
								TopoNode node = nodelist.get(j);
								if (Long.parseLong(saveNodeList.get(i).getNode_ID()) == nodelist.get(j).getNodeId()) { // 画布上的节点匹配到了库中的节点对象
										
									node.setNodeName(saveNode.getNode_Name());
									node.setPosX(saveNode.getPosX());
									node.setPosY(saveNode.getPosY());
									node.setNamePosX(saveNode.getNamePosX());
									node.setNamePosY(saveNode.getNamePosY());
									saveNodes.add(node);
								}
							}
						}
						
					}
				}
				
				
				List<LinkParam> saveLinkList = JsonUtils.getDTOList(linksStr,LinkParam.class);
				if(!saveLinkList.isEmpty()){
					for(int i=0;i<saveLinkList.size();i++){
						LinkParam plink=saveLinkList.get(i);
						if(plink.getLinkID()==null){
							long now=System.currentTimeMillis()+i;
							TopoLine line=new TopoLine();
							line.setGraphId(saveGraph.getGraphId());
							line.setNodeAId(Long.parseLong(plink.getNodeAId()));
							line.setNodeAName(plink.getNodeAName());
							line.setNodeBId(Long.parseLong(plink.getNodeBId()));
							line.setNodeBName(plink.getNodeBName());
							line.setLineId(plink.getLinkID());
							line.setLineName(plink.getLinkName());
							line.setLineId(now);
							line.setLineType(1);
							saveLines.add(line);
						}else{
							for(int j=0;j<linelist.size();j++){
								TopoLine line=linelist.get(j);
//								System.out.println(plink.getLinkID()+"--->"+line.getLineId());
								if(plink.getLinkID().longValue()==line.getLineId().longValue()){
								   //line.setLineBandwidth(plink.getLinkName());	
									saveLines.add(line);
								}
								
							}
						}
					}
				}
				
				topoNodeService.saveTopo(graph,saveNodes,saveLines);
			}

		}

		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		return null;

	}
	
	@RequestMapping(value="/localOutputLogs.do")
	public String localOutputLogs(HttpServletResponse response) throws IOException{
//		TopoDiscoveryCache.getInstance().clearToplogyOutput();
//		StringBuffer output = TopoCache.getInstance().getToplogyOutput();
		StringBuffer output = TopoDiscoveryCache.getInstance().getTopoDsicoveryOutput();
		 if( output!=null&&output.length()>0){
			 String outputstr = output.toString();
			 outputstr = outputstr.replaceAll("\n", "</br>");
			 
			 JSONObject jo=new JSONObject();
			 jo.put("outputstr", outputstr);
			 response.setContentType("text/json");  
			 response.setCharacterEncoding("UTF-8"); 
			 response.getWriter().print(jo.toString());
		 }
		
		 return null;
		
	}
}
