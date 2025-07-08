package com.sino.topo.topoNode.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.net.dao.NcmDevicesDao;
import com.sino.fas.res.net.entity.NcmDevices;
import com.sino.topo.graph.dao.TopoGraphDao;
import com.sino.topo.graph.entity.TopoGraph;
import com.sino.topo.topoLine.dao.TopoLineDao;
import com.sino.topo.topoLine.entity.TopoLine;
import com.sino.topo.topoNode.dao.TopoNodeDao;
import com.sino.topo.topoNode.entity.TopoNode;

@Service
@Transactional
public class TopoNodeService {

	private static String objAttNames="graphId,nodeId,nodeType,nodeName,posX,posY,namePosX,namePosY,width,height,objTypeId,objType,objIcon,devClassCode,devTypeCode,devID,devIpLong,devIpAddr,devStatus,flag,remark";
	
	private static String jsonAttNames="graphId,nodeId,nodeType,nodeName,posX,posY,namePosX,namePosY,width,height,objTypeId,objType,objIcon,devClassCode,devTypeCode,devID,devIpLong,devIpAddr,devStatus,flag,remark";
	
	
	@Autowired
	private TopoNodeDao topoNodeDao;
	
	@Autowired
	private TopoGraphDao topoGraphDao;
	
	@Autowired
	private TopoLineDao topoLineDao;
	
	@Autowired
	private NcmDevicesDao devicesDao;
	
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<TopoNode> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final TopoNode node) {
		return JsonUtils.getJsonObjInfo(node, objAttNames, jsonAttNames);
	}
	
	public TopoNode getByID(long id)
	{
		return topoNodeDao.get(id);
	}
	
	public TopoNode getByGraphIdAndNodeId(long graphId,long nodeId){
		String hql=" from TopoNode where graphId=? and nodeId=?";
		return topoNodeDao.findUnique(hql, graphId,nodeId);
	}
	
	public void save(TopoNode node){
		topoNodeDao.save(node);
	}
	
	
	public void saveSubNetNode(TopoNode oldNode,TopoNode newNode){
		topoNodeDao.delete(oldNode);
		topoNodeDao.flush();
		topoNodeDao.save(newNode);
		topoNodeDao.flush();
	}
	
	public void batchSave(List<TopoNode> list)
	{
		topoNodeDao.batchSave(list, 20);
	}
	
	public List<TopoNode> getByGraphID(long graphID)
	{
		String hql=" from TopoNode where graphId=? order by nodeId";
		return topoNodeDao.find(hql, graphID);
	}
	
	public void deleteNodesByGid(long graphId){
		topoNodeDao.deleteByGraphId(graphId);
		topoNodeDao.flush();
	}
	
	
	public void deleteNodeById(long nodeID,List<TopoLine> lines){
		topoNodeDao.delete(nodeID);
		topoNodeDao.flush();
		
		if(!lines.isEmpty()){
			topoLineDao.delete(lines);
			topoLineDao.flush();
		}
	}
	
	
	public void deleteDevice(long nodeID,List<TopoLine> lines,NcmDevices dev){
		topoNodeDao.delete(nodeID);
		topoNodeDao.flush();
		
		if(!lines.isEmpty()){
			topoLineDao.delete(lines);
			topoLineDao.flush();
		}
		
		if(dev!=null){
			devicesDao.delete(dev);
			devicesDao.flush();
		}
	}
	
	public void delete(long nodeID){
		topoNodeDao.delete(nodeID);
	}
	
	public TopoNode getByGraphIdAndNodeName(long graphId,String nodeName){
		String hql=" from TopoNode where graphId=? and nodeName=?";
		return topoNodeDao.findUnique(hql, graphId,nodeName);
	}
	
	
	public void saveTopo(TopoGraph graph,List<TopoNode> savenodes,List<TopoLine> saveLines){
		
		graph.setStatus(1);
		topoGraphDao.save(graph);
		topoGraphDao.flush();
		
		deleteNodesByGid(graph.getGraphId());
		topoNodeDao.flush();
		
		topoNodeDao.batchSave(savenodes, 20);
		topoNodeDao.flush();
		
		topoLineDao.deleteLineByGraphId(graph.getGraphId());
		topoLineDao.flush();
		
		topoLineDao.batchSave(saveLines, 20);
		topoLineDao.flush();
		
	}
}
