package com.sino.topo.topoNode.web;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import smartlink.utils.Util;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.NetworkUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.cmdb.graph.entity.TopoParam;
import com.sino.cmdb.product.prodModel.entity.VendorProdModel;
import com.sino.fas.res.net.entity.NcmDevices;
import com.sino.fas.res.net.service.NcmDevicesService;
import com.sino.snmp.utils.JdbcConnection;
import com.sino.topo.graph.entity.TopoGraph;
import com.sino.topo.graph.service.TopoGraphService;
import com.sino.topo.topoLine.entity.TopoLine;
import com.sino.topo.topoLine.service.TopoLineService;
import com.sino.topo.topoNode.entity.TopoNode;
import com.sino.topo.topoNode.service.TopoNodeService;
import com.sino.utils.icmp.Ping;
import java.io.IOException;

@RequestMapping(value="/topo/topoNode")
@Controller
public class TopoNodeAction {
	
	private String viewPath = "/topo/topoGraphManage";
	@Autowired
	private TopoNodeService topoNodeService;
	
	@Autowired
	private NcmDevicesService deviceService;
	
	@Autowired
	private TopoLineService topoLineService;
	
	@Autowired
	private TopoGraphService topoGraphService;
	
	@RequestMapping(value="/getByID.do")
	public String getByID(String nodeID,HttpServletResponse response) throws IOException{
		TopoNode node=topoNodeService.getByID(Long.parseLong(nodeID));
		JSONObject jo=new JSONObject();
		String devStr="";
		if(node!=null){
			if(node.getDevClassCode()==13){  
				NcmDevices device=deviceService.getById(node.getDevID()==null?-1:node.getDevID());
				if(device!=null){
					devStr=deviceService.getJsonObjStr(device);
				}
			}
			String nodeStr=topoNodeService.getJsonObjStr(node);
			
			jo.put("nodeStr", nodeStr);
			jo.put("devStr", devStr);
			response.getWriter().print(jo.toString());
		}
		return null;
	}
	
	//当修改文本节点为一个合格Ip时
//	@RequestMapping(value="/singleSave.do")
//	public String singleSave(String ip,String devClassCode,String devTypeCode){
		
//		if(devClassCode){  
//			if(node.getDevTypeCode()==2){//交换机
//				NcmSwitch device = switchService.getSwitch(node.getDevID());
//				if(device!=null){
//					devStr=switchService.getJsonObjStr(device);
//				}
//			}else{//路由器及其他设备
//				NcmDevice device = deviceService.getDevice(node.getDevID());
//				if(device!=null){
//					devStr=deviceService.getJsonInfoForView(device);
//				}
//			}
//		}
		
//	}
	
	
	@RequestMapping(value="/checkIpAddr.do")
	public String checkIpAddr(long graphId,String nodeName,HttpServletResponse response) throws IOException{
		boolean flag=false;
		TopoNode node=topoNodeService.getByGraphIdAndNodeName(graphId,nodeName);
		if(node!=null){
			flag=true;
		}
		
		JSONObject jo=new JSONObject();
		jo.put("flag", flag);
		response.getWriter().print(jo.toString());
		return null;
	}
	
	
	@RequestMapping(value="/checkDevice.do")
	public String checkDevice(String nodeName,int devTypeCode,HttpServletResponse response) throws IOException{
		
		int flag=0;
//		if(devTypeCode==2){ //交换机
//			NcmSwitch device = switchService.getSwitch(Util.ip2long(nodeName));
//			if(device!=null){
//				flag=1;
//			}
//		}else if(devTypeCode==3){  //路由器
//			NcmDevice device = deviceService.getDevice(Util.ip2long(nodeName));
//			if(device!=null){
//				flag=1;
//			}
//		}
		
		JSONObject jo=new JSONObject();
		jo.put("flag", flag);
		jo.put("devId", NetworkUtils.ip2long(nodeName));
		response.getWriter().print(jo.toString());
		return null;
		
	}
	

	@RequestMapping(value="/saveNode.do")
	public String saveNode(String nodeStr,String graphId,HttpServletResponse response) throws IOException{
		if(!StringUtil.isNullString(nodeStr)){
			List<TopoParam> saveNodeList = JsonUtils.getDTOList(nodeStr,TopoParam.class);
			TopoParam ptopo=saveNodeList.get(0);
			if(!StringUtil.isNullString(ptopo.getNodeInfo())){  //保存新节点
				TopoNode node=new TopoNode();
				
				String [] res=ptopo.getNodeInfo().split("#");
				node.setGraphId(Long.parseLong(graphId));   //13#网络设备#0#子网#subnet.png#757bc8b3-a304-4ec4-9570-952e0f7c7cbb
				
				long devId=deviceService.getSwitchIdByIp(ptopo.getNode_Name());
				
				if(devId>0){
					node.setFlag(1);
				}else{
					node.setFlag(0);
					devId=JdbcConnection.getInstance().getDeviceResourceUniqId();
				}
					
				long devIpLong = Util.ip2long(ptopo.getNode_Name());
				long nodeId = Long.parseLong(graphId)  + devIpLong;
				
				node.setNodeId(nodeId);
				if(devId>0){
					
				}
				node.setDevID(devId);
				node.setDevIpLong(devIpLong);
				node.setDevIpAddr(ptopo.getNode_Name());
				
				node.setNodeType(Integer.parseInt(res[6]));//Integer.parseInt(res[2])
				node.setNodeName(ptopo.getNode_Name());
				node.setPosX(ptopo.getPosX());
				node.setPosY(ptopo.getPosY());
				node.setWidth(1d);
				node.setHeight(1d);
				node.setObjTypeId(res[5]);
				node.setNamePosX(ptopo.getNamePosX());
				node.setNamePosY(ptopo.getNamePosY());
				node.setDevClassCode(Integer.parseInt(res[0]));
				node.setDevClassName(res[1]);
				node.setDevTypeCode(Integer.parseInt(res[2]));
				node.setDevTypeName(res[3]);
				node.setObjType(Integer.parseInt(res[6]));		//Integer.parseInt(res[2])
				node.setObjIcon(res[4]);
				node.setDevStatus(0);
				
				topoNodeService.save(node);
			}else{   //修改已存在的节点
				TopoNode node=topoNodeService.getByID(Long.parseLong(ptopo.getNode_ID()));
				node.setNodeName(ptopo.getNode_Name());
				topoNodeService.save(node);
				
			}
			
			
		}
		
		JSONObject jo=new JSONObject();
		jo.put("result", "success");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	
	@RequestMapping(value="/deleteNode.do")
	public String deleteNode(long nodeId,String graphId,HttpServletResponse response) throws IOException{
		
		TopoNode node=null;
		List<TopoLine> lines=null;
		if(!StringUtil.isNullString(graphId)){
			node=topoNodeService.getByGraphIdAndNodeId(Long.parseLong(graphId),nodeId);
			if(node!=null){
				lines=topoLineService.getByGraphIdAndNodeId(Long.parseLong(graphId),nodeId);
			}
		}else{
			node=topoNodeService.getByID(nodeId);
			if(node!=null){
				lines=topoLineService.getByNodeId(nodeId);
			}
		}
		
		topoNodeService.deleteNodeById(nodeId,lines);
		response.getWriter().print("success");
			
		return null;
	}
	
	@RequestMapping(value="/deleteDevice.do")
	public String deleteDevice(long nodeId,HttpServletResponse response) throws IOException{
		TopoNode node=topoNodeService.getByID(nodeId);
		if(node!=null){
			
			List<TopoLine> lines=topoLineService.getByNodeId(nodeId);
			NcmDevices dev=deviceService.getById(node.getDevID());
			if(dev!=null){
				topoNodeService.deleteDevice(nodeId,lines,dev);
			}
			response.getWriter().print("success");
		}
		
		return null;
	}
	
	@RequestMapping(value="/saveSubnetNode.do")
	public String saveSubnetNode(String subnetStr,String graphId,String selectGraphId,String selectGraphName,HttpServletResponse response) throws IOException{
		if(!StringUtil.isNullString(subnetStr)){
			List<TopoParam> saveNodeList = JsonUtils.getDTOList(subnetStr,TopoParam.class);
			TopoParam ptopo=saveNodeList.get(0);
			
			if(!StringUtil.isNullString(ptopo.getNodeInfo())){  //新建subnetNode
				
				TopoNode node=new TopoNode();
				
				String [] res=ptopo.getNodeInfo().split("#");
				node.setGraphId(Long.parseLong(graphId));   //13#网络设备#0#子网#subnet.png#757bc8b3-a304-4ec4-9570-952e0f7c7cbb#0
				node.setNodeId(Long.parseLong(selectGraphId));
				
				node.setNodeType(Integer.parseInt(res[6]));
				node.setNodeName(selectGraphName);
				node.setPosX(ptopo.getPosX());
				node.setPosY(ptopo.getPosY());
				node.setWidth(1d);
				node.setHeight(1d);
				node.setObjTypeId(res[5]);
				node.setNamePosX(ptopo.getNamePosX());
				node.setNamePosY(ptopo.getNamePosY());
				node.setDevClassCode(Integer.parseInt(res[0]));
				node.setDevClassName(res[1]);
				node.setDevTypeCode(Integer.parseInt(res[2]));
				node.setDevTypeName(res[3]);
				node.setObjType(Integer.parseInt(res[6]));		//
				node.setObjIcon(res[4]);
				node.setDevStatus(0);
				
				topoNodeService.save(node);
			}else{//laod subnetNode
				TopoNode node=topoNodeService.getByID(Long.parseLong(ptopo.getNode_ID()));
				
				TopoNode saveNode=new TopoNode();
				BeanUtils.copyProperties(node, saveNode);
				
				saveNode.setGraphId(Long.parseLong(graphId));   
				saveNode.setNodeId(Long.parseLong(selectGraphId));
				saveNode.setNodeName(selectGraphName);
				topoNodeService.saveSubNetNode(node,saveNode);
			}
		}
		JSONObject jo=new JSONObject();
		jo.put("result", "success");
		response.getWriter().print(jo.toString());
		return null;
	}
	
	@RequestMapping(value = "/edit.do")
	public String edit(Long id,ModelMap map) {  //topoEdit topo管理中的修改
		map.put("action", "edit");
//		NcmSwitch device = switchService.getSwitch(id);
//		if(device!=null){
			TopoNode node=topoNodeService.getByID(id);
			map.put("node", node);
//		}
		return viewPath+"/editIP";
	}
	
	@RequestMapping(value = "/saveNodeIP.do")
	public String saveNodeIP(String nodeId,String nodeName,HttpServletResponse response) throws IOException{
		TopoNode node=topoNodeService.getByID(Long.parseLong(nodeId));
		node.setNodeName(nodeName);
		topoNodeService.save(node);
		
		JSONObject jo=new JSONObject();
		jo.put("result", "success");
		jo.put("graphId", node.getGraphId());
		response.getWriter().print(jo.toString());
		return null;
		
	}
	
	@RequestMapping(value="/telnetAccess.do")
	public String telnetAccess(String nodeId,String nodeName,HttpServletResponse response) throws IOException{
		String result="";
		TopoNode node=topoNodeService.getByID(Long.parseLong(nodeId));
		
		if(node!=null){
			if(Ping.isNodeReachable(node.getDevIpAddr())){
				Runtime.getRuntime().exec("cmd.exe   /C   start   telnet "+node.getDevIpAddr());
			}else{
				result=node.getDevIpAddr()+" Ping失败，请检查网络是否异常！";
			}
		}else{
			result="该设备不存在，请确认！";
		}
		
		JSONObject jo=new JSONObject();
		jo.put("result", result);
		response.getWriter().print(jo.toString());
		return null;
		
	}
	
	public static void main(String[] args) {
//		System.out.println(Ping.isAlive("192.168.100.15"));
		System.out.println(Ping.isNodeReachable("192.168.100.15"));
	}
	
}
