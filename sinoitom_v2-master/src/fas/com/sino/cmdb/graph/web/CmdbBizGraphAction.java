package com.sino.cmdb.graph.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.utils.Identities;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.SysUser;
//import com.sino.cmdb.appService.entity.CmdbAppService;
//import com.sino.cmdb.appService.service.CmdbAppServiceService;
import com.sino.cmdb.graph.entity.CmdbBizGraph;
import com.sino.cmdb.graph.entity.CmdbResNode;
import com.sino.cmdb.graph.entity.CmdbResRelation;
import com.sino.cmdb.graph.entity.LinkParam;
import com.sino.cmdb.graph.entity.TopoParam;
import com.sino.cmdb.graph.entity.UniLineResult;
import com.sino.cmdb.graph.entity.UniWayLine;
import com.sino.cmdb.graph.pathUtils.GetAllPath4OneGraph;
import com.sino.cmdb.graph.service.CmdbBizGraphService;
import com.sino.cmdb.graph.service.CmdbResNodeService;
import com.sino.cmdb.graph.service.CmdbResRelationService;
import com.sino.cmdb.graph.test.GetAffectedNodes2;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
//import com.sino.cmdb.res.entity.CmdbBizApp;
//import com.sino.cmdb.res.service.CmdbBizAppService;
import com.sino.fas.res.host.entity.IpHost;
import com.sino.fas.res.host.entity.ResAppService;
import com.sino.fas.res.host.entity.ResHosts;
import com.sino.fas.res.host.entity.ResServiceProcess;
import com.sino.fas.res.host.service.ResAppServiceService;
import com.sino.fas.res.host.service.ResHostsService;
import com.sino.fas.res.host.service.ResServiceProcessService;
import com.sino.fas.res.net.entity.NcmDevices;
import com.sino.fas.res.net.entity.ParamDevHostMap;
import com.sino.fas.res.net.service.NcmDevicesService;
import com.sino.res.biz.entity.ResBizSystem;
import com.sino.res.biz.service.ResBizSystemService;
import com.sino.snmp.utils.Util;
import com.sino.topo.objectType.entity.TopoObjectType;
import com.sino.topo.objectType.service.TopoObjectTypeService;

@RequestMapping(value="/cmdb/graph")
@Controller
public class CmdbBizGraphAction {
	private static Logger logger = LoggerFactory.getLogger(CmdbBizGraphAction.class);
	private String viewPath = "/topo/topoCmdb";
	
	@Autowired
	private CmdbBizGraphService cmdbBizGraphService;
	@Autowired
	private TopoObjectTypeService topoObjectTypeService;
//	@Autowired
//	private CmdbBizAppService cmdbBizappService;
	@Autowired
	private CmdbResNodeService cmdbResNodeService;
	@Autowired
	private CmdbResRelationService cmdbResRelationService;
	@Autowired
	private NcmDevicesService ncmDevicesService;
//	@Autowired
//	private CmdbAppServiceService cmdbAppServiceService;
	
	@Autowired
	private ResAppServiceService resAppServiceService;
	
	@Autowired
	private ResServiceProcessService resServiceProcessService;
	
	@Autowired
	private ResHostsService resHostsService;
	
	@Autowired
	private ResBizSystemService resBizSystemService;
	
//	----------------------------------------------------------
	
//	跳转到列表页面
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		logger.info( "Enter CmdbBizGraphAction main.do ..." );
		List<CmdbBizGraph> list = cmdbBizGraphService.getAllTopoGraph();
		String jsonStr = cmdbBizGraphService.getJsonTopoGraphListStr(list);
		
		map.put("jsonData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/index";
	} 
	
//	拓扑图回显
	@RequestMapping(value="/showGraphTopo.do")
	public String showGraphTopo(String graphId,ModelMap map){
		logger.info( "Enter CmdbBizGraphAction showGraphTopo.do ..." );
		StringBuilder imgsb = new StringBuilder();
		StringBuilder codesb = new StringBuilder();

		TopoObjectType obj;
//		获取左侧所有的资源分类名称
		List<ResItemParam> plist = topoObjectTypeService.getAllClassCode();
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
		CmdbBizGraph graph=cmdbBizGraphService.getTopoGraphByID(graphId);
		
		map.put("topoGraph", graph);
//		return viewPath+"/graphTopo";
		return viewPath+"/itopo";
	}
	
//	根据graphId显示画布
	@RequestMapping(value = "/getData.do")
	public String getData(String graphID,ModelMap map, HttpServletResponse response)
			throws IOException {
		logger.info("Enter CmdbBizGraphAction getData.do...");

		// 获取默认graph
		String nodeStr = "";
		String lineStr = "";
		String graphStr = "";
		String lineInfoStr="";
		// String textStr="";
		long endTime = System.currentTimeMillis();
		long startTime = endTime - 3600 * 1000;

//		1、获取画布
//		if(!StringUtil.isNullString(graphID)){
		CmdbBizGraph graph = cmdbBizGraphService.getByID(graphID);
		if (graph != null) {
			graphStr = cmdbBizGraphService.getJsonTopoGraphObjStr(graph);

			// 2、获取node
			List<CmdbResNode> nodes = cmdbResNodeService.getByGraphID(graph.getGraphId());

			nodeStr = cmdbResNodeService.getJsonListStr(nodes);

			// List<TopoText>
			// textlist=topoTextService.getDataByGraphID(graph.getGraphId());
			// textStr=topoTextService.getJsonListStr(textlist);
			List<CmdbResRelation> linelist = cmdbResRelationService.getByGraphID(graph.getGraphId());
			
			for(int i=0;i<linelist.size();i++){
				CmdbResRelation line=linelist.get(i);
				line.setRelName(line.getRelName()+"(影响系数="+String.format("%.2f",line.getImpactFactor())+")");
				lineInfoStr+=line.getNodeAId()+"_"+";"+line.getNodeBId()+"_"+";";
			}
					
			lineStr = cmdbResRelationService.getJsonListStr(linelist);
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
	
//	跳转到添加页面
	@RequestMapping(value = "/toAdd.do")
	public String toAdd(ModelMap map){
		logger.info( "Enter CmdbBizGraphAction toAdd.do ..." );
		map.put("action", "add");
		return viewPath+"/edit";
	}
	
//	通过bizTypeCode获取bizName
//	@RequestMapping(value = "/getBizNameByBizTypeCode.do")
//	public String getBizNameByBizTypeCode(int bizTypeCode,ModelMap map, HttpServletResponse response) throws IOException{
//		logger.info( "Enter CmdbBizGraphAction getBizNameByBizTypeCode.do ..." );
//		List<CmdbBizApp> list = cmdbBizappService.getBizNameByBizTypeCode(bizTypeCode);
//		String jsonListStr = cmdbBizappService.getJsonListStr(list);
//
//		JSONObject jo = new JSONObject();
//		jo.put("jsonListStr", jsonListStr);
//		response.setContentType("text/json");
//		response.setCharacterEncoding("UTF-8");
//		response.getWriter().print(jo.toString());
//		return null;
//	}
	
//	跳转到修改页面
	@RequestMapping(value = "/toEdit.do")
	public String toEdit(ModelMap map,String id){
		logger.info( "Enter CmdbBizGraphAction toEdit.do ..." );
		CmdbBizGraph bizGraph = cmdbBizGraphService.getByID(id);
		
		map.put("graph", bizGraph);
		map.put("action", "edit");
		return viewPath+"/edit";
	}
	
//	保存
	@RequestMapping(value="/save.do")
	public String save(String action,ModelMap map,CmdbBizGraph cmdbBizGraph){
		logger.info( "Enter CmdbBizGraphAction save.do ..." );
//		当前登陆人
		SysUser curUser = SystemUtils.getLoginUser();
		String creator = curUser.getLoginName();
//		当前时间
		Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
		
		if(action.equals("add")){
			cmdbBizGraph.setCreator(creator);
			cmdbBizGraph.setCreateTime(now);
			cmdbBizGraph.setFlag(1);
			cmdbBizGraph.setStatus(0);
			cmdbBizGraph.setGraphType(1);
			
			cmdbBizGraph.setGraphId(Identities.uuid());
			cmdbBizGraphService.save(cmdbBizGraph);
		}else{
			CmdbBizGraph bizGraph = cmdbBizGraphService.getByID(cmdbBizGraph.getGraphId());
			bizGraph.setBizTypeCode(cmdbBizGraph.getBizTypeCode());
			bizGraph.setBizName(cmdbBizGraph.getBizName());
			bizGraph.setGraphName(cmdbBizGraph.getGraphName());
			bizGraph.setGraphDescr(cmdbBizGraph.getGraphDescr());
			bizGraph.setBizTypeName(cmdbBizGraph.getBizTypeName());
			bizGraph.setBizResId(cmdbBizGraph.getBizResId());
			cmdbBizGraphService.save(bizGraph);
		}
		map.put("result", "success");
		
		return viewPath+"/edit";
	}
	
	
	@RequestMapping(value="/autoCreate.do")
	public String autoCreate(CmdbBizGraph cmdbBizGraph,Long resId,ModelMap map){
		
		logger.info( "Enter CmdbBizGraphAction autoCreate.do ..." );
		
		int posX=50;
		int posY=50;
		
		List<CmdbResNode> resNodeList=new ArrayList<CmdbResNode>();
		List<CmdbResRelation> resRelationList=new ArrayList<CmdbResRelation>();
		
		//******创建画布
//		当前登陆人
		SysUser curUser = SystemUtils.getLoginUser();
		String creator = curUser.getLoginName();
//		当前时间
		Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
		
		cmdbBizGraph.setCreator(creator);
		cmdbBizGraph.setCreateTime(now);
		cmdbBizGraph.setFlag(1);
		cmdbBizGraph.setStatus(0);
		cmdbBizGraph.setGraphType(1);
		cmdbBizGraph.setGraphId(Identities.uuid());
		
		//获取业务资源
		
		ResBizSystem bizSys=resBizSystemService.getById(resId);
		CmdbResNode biznode =new CmdbResNode();
		biznode.setGraphId(cmdbBizGraph.getGraphId());   
		biznode.setNodeId(System.currentTimeMillis()+bizSys.getResId());
		biznode.setNodeType(1);
		biznode.setResName(bizSys.getSysEnName());
		biznode.setResId(bizSys.getResId());
		biznode.setResClassCode(bizSys.getResClassCode());
		biznode.setResClassName(bizSys.getResClassName());
		biznode.setResTypeCode(bizSys.getResTypeCode());
		biznode.setResTypeName(bizSys.getResTypeName());
		biznode.setPosX(posX);
		biznode.setPosY(posY);
		biznode.setNamePosX(posX);
		biznode.setNamePosY(posY+65);
		biznode.setNodeIcon("BizApp.svg");
		biznode.setResAvlb(1.00);
		resNodeList.add(biznode);
		
		
		List<Integer> pids=new ArrayList<Integer>();
		//获取应用服务
		List<ResAppService> appSvclist=resAppServiceService.getBybizAppId(resId);
		
		if(!appSvclist.isEmpty()){
			int count1=0;
			for(ResAppService app:appSvclist){
				
				pids.add(app.getPid());
				
				CmdbResNode appsvcnode =new CmdbResNode();
				appsvcnode.setGraphId(cmdbBizGraph.getGraphId());   
				appsvcnode.setNodeId(System.currentTimeMillis()+app.getAppSvcId());
				appsvcnode.setNodeType(1);
				appsvcnode.setResName(app.getSvcName());
				appsvcnode.setResId(app.getAppSvcId());
				appsvcnode.setResClassCode(app.getResClassCode());
				appsvcnode.setResClassName(app.getResClassName());
				appsvcnode.setResTypeCode(app.getResTypeCode());
				appsvcnode.setResTypeName(app.getResTypeName());
				if(app.getSvcPort().intValue()==13306||app.getSvcPort().intValue()==3306){
					appsvcnode.setNodeIcon("icon_monitors_mysql.svg");
				}else if(app.getSvcPort().intValue()==80||app.getSvcPort().intValue()==8080){
					appsvcnode.setNodeIcon("Tomcat.svg");
				}else{
					appsvcnode.setNodeIcon("Service.svg");
				}
				
				appsvcnode.setPosX(biznode.getPosX()+100);
				appsvcnode.setPosY(biznode.getPosY()+count1*80);
				appsvcnode.setNamePosX(biznode.getPosX()+100);
				appsvcnode.setNamePosY(biznode.getPosY()+count1*80+65);
				appsvcnode.setResAvlb(1.00);
				resNodeList.add(appsvcnode);
				
				CmdbResRelation bizAppline=new CmdbResRelation();
				bizAppline.setGraphId(cmdbBizGraph.getGraphId());
				bizAppline.setNodeAId(biznode.getNodeId());
				bizAppline.setNodeBId(appsvcnode.getNodeId());
				bizAppline.setRelationId(System.currentTimeMillis()+biznode.getNodeId()+appsvcnode.getNodeId());
				bizAppline.setRelType(1);
				bizAppline.setRelName("包含");
				bizAppline.setImpactFactor(1.00);
				resRelationList.add(bizAppline);
				
				count1++;
			}
			
			//获取服务进程
			List<ResServiceProcess> svcProcessList=resServiceProcessService.getListByPids(pids);
			
			//进程所属host
			List<Long> hostids=new ArrayList<Long>();
			if(!svcProcessList.isEmpty()){
				int count2=0;
				for(ResServiceProcess pro:svcProcessList){
					
					hostids.add(pro.getHostId());
					
					CmdbResNode procnode =new CmdbResNode();
					procnode.setGraphId(cmdbBizGraph.getGraphId());   
					procnode.setNodeId(System.currentTimeMillis()+pro.getSvcProcId());
					procnode.setNodeType(1);
					procnode.setResName(pro.getProcName());
					procnode.setResId(pro.getSvcProcId());
					procnode.setResClassCode(pro.getResClassCode());
					procnode.setResClassName(pro.getResClassName());
					procnode.setResTypeCode(pro.getResTypeCode());
					procnode.setResTypeName(pro.getResTypeName());
					
					CmdbResNode nodeA=getByResId(resNodeList,appSvclist.get(0).getAppSvcId());
					if(nodeA!=null){
						procnode.setPosX(nodeA.getPosX()+100);
						procnode.setPosY(nodeA.getPosY()+count2*80);
						procnode.setNamePosX(nodeA.getPosX()+100);
						procnode.setNamePosY(nodeA.getPosY()+count2*80+65);
					}
					
					procnode.setNodeIcon("Service.svg");
					procnode.setResAvlb(1.00);
					resNodeList.add(procnode);
					
					
					for(int i=0;i<appSvclist.size();i++){
						ResAppService appsvc=appSvclist.get(i);
						
						if(pro.getPid().intValue()==appsvc.getPid().intValue()){
							CmdbResRelation bizAppline=new CmdbResRelation();
							bizAppline.setGraphId(cmdbBizGraph.getGraphId());
							nodeA=getByResId(resNodeList,appsvc.getAppSvcId());
							if(nodeA!=null){
								bizAppline.setNodeAId(nodeA.getNodeId());
							}
							bizAppline.setNodeBId(procnode.getNodeId());
							bizAppline.setRelationId(System.currentTimeMillis()+nodeA.getNodeId()+procnode.getNodeId());
							bizAppline.setRelType(2);
							bizAppline.setRelName("依赖");
							bizAppline.setImpactFactor(1.00);
							resRelationList.add(bizAppline);
						}
					}
					count2++;
				}
				
				//获取主机资源
				List<ResHosts> reshostlist=resHostsService.getListByHostIds(hostids);
				
				if(!reshostlist.isEmpty()){
					Map<String,ResHosts> hostmap=new HashMap<String,ResHosts>();
					Set<String> homedset=new TreeSet<String>();
					Set<Long> homedIdset=new TreeSet<Long>();
					int count3=0;
					for(ResHosts host:reshostlist){
						
						CmdbResNode hostnode =new CmdbResNode();
						hostnode.setGraphId(cmdbBizGraph.getGraphId());   
						hostnode.setNodeId(System.currentTimeMillis()+host.getHostId());
						hostnode.setNodeType(1);
						hostnode.setResName(host.getIpAddress());
						hostnode.setResId(host.getHostId());
						hostnode.setResClassCode(host.getResClassCode());
						hostnode.setResClassName(host.getResClassName());
						hostnode.setResTypeCode(host.getResTypeCode());
						hostnode.setResTypeName(host.getResTypeName());
						
						CmdbResNode nodeA=getByResId(resNodeList,svcProcessList.get(0).getSvcProcId());
						if(nodeA!=null){
							hostnode.setPosX(nodeA.getPosX()+100);
							hostnode.setPosY(nodeA.getPosY()+count3*80);
							
							hostnode.setNamePosX(nodeA.getPosX()+100);
							hostnode.setNamePosY(nodeA.getPosY()+count3*80+65);
						}
						
						hostnode.setNodeIcon("icon_Server_RedHat.svg");
						hostnode.setResAvlb(1.00);
						resNodeList.add(hostnode);
						
						for(int i=0;i<svcProcessList.size();i++){
							ResServiceProcess proc=svcProcessList.get(i);
							
							if(proc.getHostId().longValue()==host.getHostId().longValue()){
								
								CmdbResRelation bizAppline=new CmdbResRelation();
								bizAppline.setGraphId(cmdbBizGraph.getGraphId());
								nodeA=getByResId(resNodeList,proc.getSvcProcId());
								if(nodeA!=null){
									bizAppline.setNodeAId(nodeA.getNodeId());
								}
								bizAppline.setNodeBId(hostnode.getNodeId());
								bizAppline.setRelationId(System.currentTimeMillis()+nodeA.getNodeId()+hostnode.getNodeId());
								bizAppline.setRelType(3);
								bizAppline.setRelName("运行于");
								bizAppline.setImpactFactor(1.00);
								resRelationList.add(bizAppline);
							}
						}
						count3++;
						
						//判断虚机是否有宿主机
						
						if(!StringUtil.isNullString(host.getHomedHostIp())){//虚机
							homedset.add(host.getHomedHostIp());
							homedIdset.add(host.getHomedHostId());
							hostmap.put(host.getIpAddress(), host);
						}
					}
					
					if(!homedset.isEmpty()){
						int count4=0;
						List<ResHosts> homedhosts=resHostsService.getListByIpAddress(homedset);
						ResHosts homehost=null;
						for(int i=0;i<homedhosts.size();i++){
							homehost=homedhosts.get(i);
							CmdbResNode homenode =new CmdbResNode();
							homenode.setGraphId(cmdbBizGraph.getGraphId());   
							homenode.setNodeId(System.currentTimeMillis()+homehost.getHostId());
							homenode.setNodeType(1);
							homenode.setResName(homehost.getIpAddress());
							homenode.setResId(homehost.getHostId());
							homenode.setResClassCode(homehost.getResClassCode());
							homenode.setResClassName(homehost.getResClassName());
							homenode.setResTypeCode(homehost.getResTypeCode());
							homenode.setResTypeName(homehost.getResTypeName());
							
							CmdbResNode nodeA=getByResId(resNodeList,reshostlist.get(0).getHostId());
							if(nodeA!=null){
								homenode.setPosX(nodeA.getPosX()+100);
								homenode.setPosY(nodeA.getPosY()+count4*80);
								
								homenode.setNamePosX(nodeA.getPosX()+100);
								homenode.setNamePosY(nodeA.getPosY()+count4*80+65);
							}
							
							homenode.setNodeIcon("icon_Server_RedHat.svg");
							homenode.setResAvlb(1.00);
							resNodeList.add(homenode);
							count4++;
							
							for(ResHosts h:reshostlist){
								
								if(homehost.getIpAddress().equals(h.getHomedHostIp())){
									
									CmdbResRelation homedline=new CmdbResRelation();
									homedline.setGraphId(cmdbBizGraph.getGraphId());
									nodeA=getByResId(resNodeList,h.getHostId());
									if(nodeA!=null){
										homedline.setNodeAId(nodeA.getNodeId());
									}
									homedline.setNodeBId(homenode.getNodeId());
									homedline.setRelationId(System.currentTimeMillis()+nodeA.getNodeId()+homenode.getNodeId());
									homedline.setRelType(4);
									homedline.setRelName("寄宿于");
									homedline.setImpactFactor(1.00);
									resRelationList.add(homedline);
								}
							}
							
						}
						
						//物理机连接交换机
						int count5=0;
						List list= ncmDevicesService.getDeviceByIphostId(homedIdset);
						Iterator it  =  list.iterator();
						while( it.hasNext()){
							Object[] tuple  =  (Object[]) it.next(); 
							NcmDevices dev=(NcmDevices)tuple[0];
							Long ipHostId=(Long)tuple[1];
							CmdbResNode devnode =new CmdbResNode();
							devnode.setGraphId(cmdbBizGraph.getGraphId());   
							devnode.setNodeId(System.currentTimeMillis()+homehost.getHostId());
							devnode.setNodeType(1);
							devnode.setResName(dev.getDevIpAddr());
							devnode.setResId(dev.getDevId());
							devnode.setResClassCode(dev.getDevClassCode());
							devnode.setResClassName(dev.getDevClassName());
							devnode.setResTypeCode(dev.getDevTypeCode());
							devnode.setResTypeName(dev.getDevTypeName());
							
							CmdbResNode nodeA=getByResId(resNodeList,homedhosts.get(0).getHostId());
							if(nodeA!=null){
								devnode.setPosX(nodeA.getPosX()+100);
								devnode.setPosY(nodeA.getPosY()+count5*80);
								devnode.setNamePosX(nodeA.getPosX()+100);
								devnode.setNamePosY(nodeA.getPosY()+count5*80+65);
							}
							
							devnode.setNodeIcon("switch.svg");
							devnode.setResAvlb(1.00);
							resNodeList.add(devnode);
							count5++;
							
							
							for(ResHosts home:homedhosts){
								
								if(ipHostId==home.getHostId().longValue()){
									CmdbResRelation homedline=new CmdbResRelation();
									homedline.setGraphId(cmdbBizGraph.getGraphId());
									nodeA=getByResId(resNodeList,home.getHostId());
									if(nodeA!=null){
										homedline.setNodeAId(nodeA.getNodeId());
									}
									homedline.setNodeBId(devnode.getNodeId());
									homedline.setRelationId(System.currentTimeMillis()+nodeA.getNodeId()+devnode.getNodeId());
									homedline.setRelType(5);
									homedline.setRelName("连接于");
									homedline.setImpactFactor(1.00);
									resRelationList.add(homedline);
								}
							}
							
						}
						
					}
					
				}
				
			}
		}
		
		if(!resNodeList.isEmpty()&&!resRelationList.isEmpty()){
			cmdbBizGraphService.saveTopo(cmdbBizGraph,resNodeList,resRelationList);
		}
		map.put("result", "success");
		return viewPath+"/edit";
		
	}
	
//	给graph添加节点和关系
	@RequestMapping(value = "/saveTopo.do")
	public String saveTopo(String nodesStr, String linksStr,String graphStr,
			HttpServletResponse response) throws IOException {
		logger.info( "Enter CmdbBizGraphAction saveTopo.do ..." );

		// TopoGraph savegraph=new TopoGraph();
		CmdbBizGraph saveGraph = null;
		List<Long> nodeids = new ArrayList<Long>();
		if (!StringUtil.isNullString(graphStr)) {

			JSONObject jsonObject = JSONObject.fromObject(graphStr);

			saveGraph = (CmdbBizGraph) JSONObject.toBean(jsonObject,CmdbBizGraph.class);
					
			CmdbBizGraph graph = cmdbBizGraphService.getByID(saveGraph.getGraphId());
			List<CmdbResNode> nodelist = cmdbResNodeService.getByGraphID(saveGraph.getGraphId());
			List<CmdbResRelation> linelist=cmdbResRelationService.getByGraphID(saveGraph.getGraphId());
			
			
			List<CmdbResNode> saveNodes = new ArrayList<CmdbResNode>();
			List<CmdbResRelation> saveLines= new ArrayList<CmdbResRelation>();
			
			if (!StringUtil.isNullString(nodesStr)) {
				List<TopoParam> saveNodeList = JsonUtils.getDTOList(nodesStr,TopoParam.class);
						

				if (!saveNodeList.isEmpty()) {
					for (int i = 0; i < saveNodeList.size(); i++) {
						TopoParam saveNode = saveNodeList.get(i);
						if(saveNode.getNodeInfo()!=null&&saveNode.getNodeInfo().contains("#")){  //鏂板鑺傜偣
							
							String [] res=saveNode.getNodeInfo().split("#");
							
							CmdbResNode node =new CmdbResNode();
							node.setGraphId(saveGraph.getGraphId());   //13#网络设备#0#子网#subnet.png#757bc8b3-a304-4ec4-9570-952e0f7c7cbb
							
//							long now=System.currentTimeMillis()+i;
							node.setNodeId(Long.parseLong(saveNode.getNode_ID()));
							node.setNodeType(Integer.parseInt(res[6]));
							node.setResName(saveNode.getNode_Name());
							node.setPosX(saveNode.getPosX());
							node.setPosY(saveNode.getPosY());
							node.setNamePosX(saveNode.getNamePosX());
							node.setNamePosY(saveNode.getNamePosY());
//							node.setResId(res[5]);
							node.setResClassCode(Integer.parseInt(res[0]));
							node.setResClassName(res[1]);
							node.setResTypeCode(Integer.parseInt(res[2]));
							node.setResTypeName(res[3]);
							node.setNodeIcon(res[4]);
							node.setResAvlb(1.00);
							saveNodes.add(node);
							
						}else{   //数据库中load出来的节点
							for (int j = 0; j < nodelist.size(); j++) {
								CmdbResNode node = nodelist.get(j);
								if (Long.parseLong(saveNodeList.get(i).getNode_ID()) == nodelist.get(j).getNodeId()) { // 鐢诲竷涓婄殑鑺傜偣鍖归厤鍒颁簡搴撲腑鐨勮妭鐐瑰璞�
										
									node.setResName(saveNode.getNode_Name());
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
							CmdbResRelation line=new CmdbResRelation();
							line.setGraphId(saveGraph.getGraphId());
							line.setNodeAId(Long.parseLong(plink.getNodeAId()));
//							line.setNodeAName(plink.getNodeAName());
							line.setNodeBId(Long.parseLong(plink.getNodeBId()));
//							line.setNodeBName(plink.getNodeBName());
							line.setRelationId(plink.getLinkID());
							line.setRelName(plink.getLinkName());
							line.setRelationId(now);
							line.setRelType(plink.getSubType());
							line.setImpactFactor(1.00);
							saveLines.add(line);
						}else{
							for(int j=0;j<linelist.size();j++){
								CmdbResRelation line=linelist.get(j);
//								System.out.println(plink.getLinkID()+"--->"+line.getLineId());
								if(plink.getLinkID().longValue()==line.getRelationId().longValue()){
								   //line.setLineBandwidth(plink.getLinkName());	
									saveLines.add(line);
								}
								
							}
						}
					}
				}
				
				cmdbBizGraphService.saveTopo(graph,saveNodes,saveLines);
			}

		}

		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		return null;
	}
	
//	删除graph
	@RequestMapping(value = "/mulitDelete.do")
	public String mulitDelete(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter CmdbBizGraphAction mulitDelete.do ..." );
		
		cmdbBizGraphService.deleteTopoGraph(id.split(","));
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
//	审核
	@RequestMapping(value="/audit.do")
	public String audit(String id, HttpServletResponse response) throws IOException{
		logger.info( "Enter CmdbBizGraphAction audit.do ..." );
		
		CmdbBizGraph bizGraph = cmdbBizGraphService.getByID(id);
		bizGraph.setStatus(1);
		cmdbBizGraphService.save(bizGraph);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
//	修改结点名称/保存节点
	@RequestMapping(value="/saveNode.do")
	public String saveNode(String nodeStr,String graphId,HttpServletResponse response) throws IOException{
		if(!StringUtil.isNullString(nodeStr)){
			List<TopoParam> saveNodeList = JsonUtils.getDTOList(nodeStr,TopoParam.class);
			TopoParam ptopo=saveNodeList.get(0);
			if(!StringUtil.isNullString(ptopo.getNodeInfo())){  //保存新节点
				CmdbResNode node=new CmdbResNode();
				
				String [] res=ptopo.getNodeInfo().split("#");
				node.setGraphId(graphId);   //13#网络设备#0#子网#subnet.png#757bc8b3-a304-4ec4-9570-952e0f7c7cbb
				
				node.setNodeId(Long.parseLong(ptopo.getNode_ID()));
				node.setNodeType(Integer.parseInt(res[6]));
				node.setResName(ptopo.getNode_Name());
				node.setPosX(ptopo.getPosX());
				node.setPosY(ptopo.getPosY());
				node.setNamePosX(ptopo.getNamePosX());
				node.setNamePosY(ptopo.getNamePosY());
//				node.setResId(res[5]);
				node.setResClassCode(Integer.parseInt(res[0]));
				node.setResClassName(res[1]);
				node.setResTypeCode(Integer.parseInt(res[2]));
				node.setResTypeName(res[3]);
				node.setNodeIcon(res[4]);
				node.setResAvlb(1.00);
				cmdbResNodeService.save(node);
			}else{   //修改已存在的节点
				CmdbResNode node=cmdbResNodeService.getByID(Long.parseLong(ptopo.getNode_ID()));
				node.setResLabel(ptopo.getNode_Name());
				cmdbResNodeService.save(node);
			}
		}
		
		JSONObject jo=new JSONObject();
		jo.put("result", "success");
		response.getWriter().print(jo.toString());
		return null;
	}
	
//	保存连线
	@RequestMapping(value="/saveLine.do")
	public String saveLine(String linksStr,String graphId,HttpServletResponse response) throws IOException{
		List<LinkParam> saveLinkList = JsonUtils.getDTOList(linksStr,LinkParam.class);
		
        List<CmdbResRelation> saveLines= new ArrayList<CmdbResRelation>();
        List<CmdbResRelation> linelist=cmdbResRelationService.getByGraphID(graphId);
		if(!saveLinkList.isEmpty()){
			for(int i=0;i<saveLinkList.size();i++){
				LinkParam plink=saveLinkList.get(i);
				if(plink.getLinkID()==null){
					long now=System.currentTimeMillis()+i;
					CmdbResRelation line=new CmdbResRelation();
					line.setGraphId(graphId);
					line.setNodeAId(Long.parseLong(plink.getNodeAId()));
//					line.setNodeAName(plink.getNodeAName());
					line.setNodeBId(Long.parseLong(plink.getNodeBId()));
//					line.setNodeBName(plink.getNodeBName());
					line.setRelationId(plink.getLinkID());
					line.setRelName(plink.getLinkName());
					line.setRelationId(now);
					line.setRelType(plink.getSubType());
					line.setImpactFactor(1.00);
					saveLines.add(line);
				}else{//修改
					for(int j=0;j<linelist.size();j++){
						CmdbResRelation line=linelist.get(j);
//						System.out.println(plink.getLinkID()+"--->"+line.getLineId());
						if(plink.getLinkID().longValue()==line.getRelationId().longValue()){
						   //line.setLineBandwidth(plink.getLinkName());	
							saveLines.add(line);
						}
						
					}
				}
			}
			
			cmdbResRelationService.save(saveLines.get(0));
		}
		
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		return null;
	}
	
	
	
//	删除结点信息
	@RequestMapping(value="/deleteNode.do")
	public String deleteNode(long nodeId,String graphId,HttpServletResponse response) throws IOException{
		
		CmdbResNode node=null;
		List<CmdbResRelation> lines=null;
		if(!StringUtil.isNullString(graphId)){
			node=cmdbResNodeService.getByGraphIdAndNodeId(graphId,nodeId);
			if(node!=null){
				lines=cmdbResRelationService.getByGraphIdAndNodeId(graphId,nodeId);
			}
		}else{
			node=cmdbResNodeService.getByID(nodeId);
			if(node!=null){
				lines=cmdbResRelationService.getByNodeId(nodeId);
			}
		}
		
		cmdbResNodeService.deleteNodeById(nodeId,lines);
		response.getWriter().print("success");
			
		return null;
	}
	
//	跳转到修改影响系数页面
	@RequestMapping(value="/toEditImpactFactor.do")
	public String toEditImpactFactor(String graphId,Long lineId,ModelMap map) throws IOException{
		CmdbResRelation line=cmdbResRelationService.getByGidAndLineId(graphId,lineId);
		if(line!=null){
			Double impactFactor = line.getImpactFactor();
			map.put("impactFactor", String.format("%.2f",impactFactor));
			map.put("relationId", line.getRelationId());
		}
		return viewPath+"/editImpactFactor";
	}
	
//	修改影响系数
	@RequestMapping(value="/editImpactFactor.do")
	public String editImpactFactor(Long relationId,Double impactFactor,ModelMap map) throws IOException{
		if(relationId!=null){
			CmdbResRelation line=cmdbResRelationService.getById(relationId);
			if(line!=null){
				line.setImpactFactor(impactFactor);
				cmdbResRelationService.save(line);
				map.put("result", "success");
			}
		}
		
		return viewPath+"/editImpactFactor";
	}
	
//	删除连线
	@RequestMapping(value="/deleteLine.do")
	public String deleteLine(String graphId,Long lineId,HttpServletResponse response) throws IOException{
		CmdbResRelation line=cmdbResRelationService.getByGidAndLineId(graphId,lineId);
		if(line!=null){
			cmdbResRelationService.deleteLine(line);
			response.getWriter().print("success");// 调用方法传值
		}else{
			response.getWriter().print("fail");// 调用方法传值
		}
		
		return null;
	}
	
//	跳转到关联实体资源页面
	@RequestMapping(value="/toAssociateBizApp.do")
	public String toAssociateBizApp(Long nodeId,String graphId,String nodeInfo,ModelMap map) throws IOException{
		logger.info( "Enter CmdbBizGraphAction toAssociateBizApp.do ..." );
		
		map.put("nodeId", nodeId);
		map.put("graphId", graphId);
		
		CmdbResNode resNode = cmdbResNodeService.getByID(nodeId);
		Integer resClassCode = 0;
		if(resNode!=null){//说明是修改已存入的节点
			resClassCode = resNode.getResClassCode();
			
		}else{//说明这个节点是页面上新拖过来的，还没有存入数据库
			resClassCode = Integer.parseInt(nodeInfo.split("#")[0]);
		}
		
		map.put("resClassCode", resClassCode);
		if(resClassCode==13){//Ncm_Devices
			return viewPath+"/associateDevices";
		}else if(resClassCode==26){//Cmdb_AppService
			return viewPath+"/associateAppService";
		}else if(resClassCode==27){//Cmdb_BizApp
			map.put("graphId", graphId);
			return viewPath+"/associateBizApp";
		}
		
		return null;
		
	}
	
//	列表数据--Cmdb_BizApp
//	@RequestMapping(value = "/getAllBizAppExceptUsed.do")
//	public String getAllBizAppExceptUsed(String graphId,HttpServletResponse response) throws IOException{  
//		logger.info( "Enter CmdbBizAppAction getAllBizAppExceptUsed.do ..." );
//		
//		List<CmdbBizApp> list = cmdbBizappService.getAllExceptUsed(graphId);
//		String jsonStr = cmdbBizappService.getJsonListStr(list);
//		
//		JSONObject jo = new JSONObject();
//		jo.put("jsonData", jsonStr);
//		response.setContentType("text/json");
//		response.setCharacterEncoding("UTF-8");
//		response.getWriter().print(jo.toString());
//	    return null;         
//	}
	
//	列表数据--Cmdb_AppService
//	@RequestMapping(value = "/getAllAppService.do")
//	public String getAllAppService(String graphId,HttpServletResponse response) throws IOException{  
//		logger.info( "Enter CmdbBizAppAction getAllAppService.do ..." );
//		
//		List<CmdbAppService> list = cmdbAppServiceService.getAll();
//		String jsonStr = cmdbAppServiceService.getJsonListStr(list);
//		
//		JSONObject jo = new JSONObject();
//		jo.put("jsonData", jsonStr);
//		response.setContentType("text/json");
//		response.setCharacterEncoding("UTF-8");
//		response.getWriter().print(jo.toString());
//		return null;         
//	}
	
//	列表数据--Ncm_Devices
	@RequestMapping(value = "/getAllNcmDevices.do")
	public String getAllNcmDevices(HttpServletResponse response) throws IOException{  
		logger.info( "Enter CmdbBizAppAction getAllNcmDevices.do ..." );
		
		List<NcmDevices> list = ncmDevicesService.getAll();
		String jsonStr = ncmDevicesService.getJsonListStr(list);
		
		JSONObject jo = new JSONObject();
		jo.put("jsonData", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;         
	}
	
//	关联实体资源
	@RequestMapping(value="/associateBizApp.do")
	public String associateBizApp(Long id,Long nodeId,Integer resClassCode,HttpServletResponse response) throws IOException{
		logger.info( "Enter CmdbBizGraphAction associateBizApp.do ..." );
		
		String resLabel = "";
		
		if(resClassCode==13){//Ncm_Devices
			NcmDevices ncmDevices = ncmDevicesService.getById(id);
			resLabel = ncmDevices.getDevIpAddr();
			
		}
//		else if(resClassCode==26){//Cmdb_AppService
//			CmdbAppService cmdbAppService = cmdbAppServiceService.getById(id);
//			resLabel = cmdbAppService.getAppSvcName();
//			
//		}
//		else if(resClassCode==27){//Cmdb_BizApp
//			CmdbBizApp bizApp = cmdbBizappService.getById(id);
//			resLabel = bizApp.getBizEnName();
//		}
		
		CmdbResNode resNode = cmdbResNodeService.getByID(nodeId);
		if(resNode!=null){//说明是数据库中的节点		
			resNode.setResId(id);
			resNode.setResLabel(resLabel);
			resNode.setResName(resLabel);
			cmdbResNodeService.save(resNode);
		}
		JSONObject jo = new JSONObject();
		jo.put("resLabel", resLabel);
		jo.put("nodeId", nodeId);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;         
	}
	
//	跳转到影响范围页面
	@RequestMapping(value="/toSphereOfInfluence.do")
	public String toSphereOfInfluence(Long nodeId,String graphId,ModelMap map) throws IOException{
		logger.info( "Enter CmdbBizGraphAction toSphereOfInfluence.do ..." );
		map.put("nodeId", nodeId);
		map.put("graphId", graphId);
		
		return viewPath+"/sphereOfInfluence";
		
	}
	
//	列表数据--Ncm_Devices
	@RequestMapping(value = "/getSphereOfInfluence.do")
	public String getSphereOfInfluence(Long nodeId,String graphId,HttpServletResponse response) throws IOException{  
		logger.info( "Enter CmdbBizAppAction getSphereOfInfluence.do ..." );
		
		String jsonStr = "";
		List<CmdbResNode> list = null;
		
//		获取该graph页面中的应用系统对应的id
		CmdbResNode startNode = cmdbResNodeService.getResIdByCode(graphId);
		if(startNode!=null){
			Long sartNodeId = startNode.getNodeId();
			GetAllPath4OneGraph getAllPathUtil = new GetAllPath4OneGraph(graphId,sartNodeId,nodeId);
			
			getAllPathUtil.setResAvlb4All(0.5);//计算可用值
			
//			获取所有通路上的点--没有重复值
			List<Long> NodeIdList = getAllPathUtil.getNode();
			
			if(NodeIdList!=null &&NodeIdList.size()>0){
				list = cmdbResNodeService.getByIds(NodeIdList);
			}
			jsonStr = cmdbResNodeService.getJsonListStr(list);
			
			
		}
		
		JSONObject jo = new JSONObject();
		jo.put("jsonData", jsonStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;         
	}
	
	
	public CmdbResNode getByResId(List<CmdbResNode> list ,Long resId){
		
		for(int i=0;i<list.size();i++){
			CmdbResNode node=list.get(i);
			if(node.getResId().longValue()==resId){
				return node;
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/autoLoad.do")
	public String autoLoad(String graphId,HttpServletResponse response) throws IOException {

		List<UniWayLine> unilinelist = new ArrayList<UniWayLine>();

		List<CmdbResNode> retNodelist = new ArrayList<CmdbResNode>();
		CmdbBizGraph graph = null;
		if(graphId!=null){
			 graph = graph=cmdbBizGraphService.getTopoGraphByID(graphId);
		}
		
		List<CmdbResNode> nodelist = cmdbResNodeService.getByGraphID(graph
				.getGraphId());
		List<CmdbResRelation> linelist = cmdbResRelationService.getByGraphID(graph
				.getGraphId());

		long rootNodeId = nodelist.get(0).getNodeId();

		
		for (int i = 0; i < linelist.size(); i++) {
			CmdbResRelation line = linelist.get(i);
			line.setFlag(0);
		}
		unilinelist = findSubNode(linelist, nodelist, retNodelist, rootNodeId,
				0, unilinelist);

		int depth = 1;
		int[] levelNodes = new int[10];
		int count = 0;
		for (int i = 0; i < UniWayLine.getSortList(unilinelist).size(); i++) {//计算每层节点的个数
			System.out.println("depth==" + unilinelist.get(i).getDepth() + "subnodeId=="
					+ unilinelist.get(i).getNodeFrom()+"------->"+unilinelist.get(i).getNodeTo());
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

		String graphStr = cmdbBizGraphService.getJsonTopoGraphObjStr(graph);

		List<CmdbResNode> nodes = new ArrayList<CmdbResNode>();

		long endTime = System.currentTimeMillis();

		long startTime = endTime - 3600 * 1000;
		String nodeStr = cmdbResNodeService.getJsonListStr(nodelist);

		String lineStr = cmdbResRelationService.getJsonListStr(linelist);

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

	public List<UniWayLine> findSubNode(List<CmdbResRelation> linelist,
			List<CmdbResNode> nodelist, List<CmdbResNode> retNodelist, long nodeId,
			int depth, List<UniWayLine> unilinelist) {

		Set<Long> nodeSet = new TreeSet<Long>();

		for (int i = 0; i < linelist.size(); i++) {
			UniWayLine uniline = new UniWayLine();
			CmdbResRelation line = linelist.get(i);
//			System.out.println(line.getNodeAName()+"---->"+line.getNodeBName());
			
			if ((line.getNodeAId() == nodeId)&& (line.getFlag() == 0)) {
				uniline.setNodeFrom(nodeId);
				uniline.setNodeTo(line.getNodeBId());
				uniline.setDepth(depth + 1);
				unilinelist.add(uniline);
				line.setFlag(1);
				nodeSet.add(line.getNodeBId());
			} else if ((line.getNodeBId() == nodeId)&& (line.getFlag() == 0)) {
				uniline.setNodeFrom(nodeId);
				uniline.setNodeTo(line.getNodeAId());
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
}
