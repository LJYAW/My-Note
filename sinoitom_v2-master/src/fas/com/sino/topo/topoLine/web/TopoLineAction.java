/*
 * 文件名： TopoLineAction.java
 * 
 * 创建日期： 2015-1-26
 *
 * Copyright(C) 2015, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.topo.topoLine.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.fas.res.net.entity.NcmDevInterfaces;
import com.sino.fas.res.net.entity.NcmPhysicalPort;
import com.sino.fas.res.net.service.NcmDevIfService;
//import com.sino.fas.res.net.entity.NcmSnmpInterface;
//import com.sino.fas.res.net.service.DevItfService;
//import com.sino.fas.res.net.service.SnmpIfService;
import com.sino.topo.topoLine.entity.TopoLine;
import com.sino.topo.topoLine.service.TopoLineService;
import com.sino.topo.topoNode.entity.TopoNode;
import com.sino.topo.topoNode.service.TopoNodeService;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision$
 *
 * @since 2015-1-26
 */
@Controller
@RequestMapping(value = "/topo/topoLine")
public class TopoLineAction {
	private static Logger logger = LoggerFactory.getLogger(TopoLineAction.class);
	
	private String viewPath = "/topo/topo";
	
	private String lineViewPath="/topo/topoLine";

	@Autowired
	private TopoLineService topoLineService;
	
	@Autowired
	private TopoNodeService topoNodeService;
	
	@Autowired
	private NcmDevIfService ncmDevIfService;
	
	
//	@Autowired
//	private SnmpIfService snmpIfService;
//	
//	@Autowired
//	private DevItfService devItfService;
	
	@RequestMapping(value="/editLine.do")
	public String editLine(Long lineId,Long graphId,ModelMap map){
		
		TopoLine line=topoLineService.getByGidAndLineId(graphId,lineId);
		if(line!=null){
			TopoNode nodeA=topoNodeService.getByID(line.getNodeAId());
			TopoNode nodeB=topoNodeService.getByID(line.getNodeBId());
			
			if(nodeA!=null){
				map.put("nodeA", nodeA);
			}
			
			if(nodeB!=null){
				map.put("nodeB", nodeB);
			}
			
			map.put("line", line);
			map.put("graphId", graphId);
			map.put("action", "edit");
		}
		
		return lineViewPath+"/editLine";
	}
	
	
	@RequestMapping(value="/editNewLine.do")
	public String editNewLine(Long nodeAId,Long nodeBId,Long graphId,ModelMap map){
		
		TopoNode nodeA=topoNodeService.getByID(nodeAId);
		TopoNode nodeB=topoNodeService.getByID(nodeBId);
		
		if(nodeA!=null){
			map.put("nodeA", nodeA);
		}
		
		if(nodeB!=null){
			map.put("nodeB", nodeB);
		}
		
		map.put("line", new TopoLine());
		map.put("action", "edit");
		map.put("graphId", graphId);
		return lineViewPath+"/editLine";
	}
	
	@RequestMapping(value="/getNodeIf.do")
	public String getNodeIf(long devId,HttpServletResponse response) throws IOException{
		String ifStr="";
	    List<NcmDevInterfaces> ifList=ncmDevIfService.getAllSnmpIfByDevId(devId);
	    ifStr=ncmDevIfService.getPortJsonListStr(ifList);
		JSONObject jo = new JSONObject();// 先创建一个JSON对象
		jo.put("ifStr", ifStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());// 调用方法传值
		return null;
		
	}
	
	@RequestMapping(value="/saveLine.do")
	public String saveLine(String lineId,String graphId,TopoLine topoLine,String numVal,String unitVal,ModelMap map){
		
		
		if(!StringUtil.isNullString(lineId)){
			TopoLine line=topoLineService.getByGidAndLineId(Long.parseLong(graphId),Long.parseLong(lineId));
			if(line!=null){
				line.setLineName(topoLine.getLineName());
				line.setIfIndexA(topoLine.getIfIndexA());
				line.setIfNameA(topoLine.getIfNameA());
				line.setIfBandwidthA(topoLine.getIfBandwidthA());
				
				line.setLineBandwidth(StringUtil.getBandwidth(numVal,unitVal));
				
				line.setIfIndexB(topoLine.getIfIndexB());
				line.setIfNameB(topoLine.getIfNameB());
				line.setIfBandwidthB(topoLine.getIfBandwidthB());
				line.setIfIpA(topoLine.getIfIpA());
				line.setIfIpB(topoLine.getIfIpB());
				
				topoLineService.save(line);
				map.put("line", line);
			}
		}else{
			long now=System.currentTimeMillis();
			topoLine.setLineId(now);
			topoLine.setLineBandwidth(StringUtil.getBandwidth(numVal,unitVal));
			topoLine.setLineType(1);
			topoLine.setGraphId(Long.parseLong(graphId));
			topoLineService.save(topoLine);
			
			
		}
		
		map.put("result", "success");
		return lineViewPath+"/editLine"; 
	}
	
	@RequestMapping(value="/isExist.do")
	public String isExist(Long graphId,Long lineId,HttpServletResponse response) throws IOException{
		boolean flag=false;
		TopoLine line=topoLineService.getByGidAndLineId(graphId,lineId);
		if(line!=null){
			flag=true;
		}
		
		JSONObject jo = new JSONObject();// 先创建一个JSON对象
		jo.put("flag", flag);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());// 调用方法传值
		return null;
	}
	
	@RequestMapping(value="/view.do")
	public String view(Long graphId,Long lineId,ModelMap map){
		
		TopoLine line=topoLineService.getByGidAndLineId(graphId,lineId);
		if(line!=null){
			TopoNode nodeA=topoNodeService.getByID(line.getNodeAId());
			TopoNode nodeB=topoNodeService.getByID(line.getNodeBId());
			
			if(nodeA!=null){
				map.put("nodeA", nodeA);
			}
			
			if(nodeB!=null){
				map.put("nodeB", nodeB);
			}
			
		}
		map.put("line",line);
		return lineViewPath+"/viewLine";
	}
	
	@RequestMapping(value="/deleteLine.do")
	public String deleteLine(Long graphId,Long lineId,HttpServletResponse response) throws IOException{
		TopoLine line=topoLineService.getByGidAndLineId(graphId,lineId);
		if(line!=null){
			topoLineService.deleteLine(line);
			response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);// 调用方法传值
		}
		return null;
	}
}
