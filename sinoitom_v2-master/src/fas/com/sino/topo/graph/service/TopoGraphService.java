package com.sino.topo.graph.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.topo.graph.dao.TopoGraphDao;
import com.sino.topo.graph.entity.TopoGraph;
import com.sino.topo.topoNode.entity.TopoNode;

@Service
@Transactional
public class TopoGraphService {

	
	private static String objAttNames="graphId,graphName,seedNodeId,graphDesc,width,height,bgPicture,flag,createTime";
	
	private static String jsonAttNames="graphId,graphName,seedNodeId,graphDesc,width,height,bgPicture,flag,createTime";
	
	
	@Autowired
	private TopoGraphDao topoGraphDao;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<TopoGraph> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final TopoGraph graph) {
		return JsonUtils.getJsonObjInfo(graph, objAttNames, jsonAttNames);
	}
	
	public void save(TopoGraph graph)
	{
		topoGraphDao.save(graph);
	}
	
	public TopoGraph getByFlag(int flag)
	{
		String hql=" from TopoGraph where flag=?";
		if(!topoGraphDao.find(hql, flag).isEmpty())
		{
			return (TopoGraph)topoGraphDao.find(hql, flag).get(0);
		}else
		{
			return null;
		}
	}
	
	public TopoGraph getByID(long id)
	{
		return topoGraphDao.get(id);
	}
	
	
	public List<TopoGraph> getDatas(long graphId){
		String hql=" from TopoGraph where graphId!=?";
		return topoGraphDao.find(hql, graphId);
		
	}
}
