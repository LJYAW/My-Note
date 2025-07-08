/*
 * 文件名： TopoLineService.java
 * 
 * 创建日期： 2015-1-26
 *
 * Copyright(C) 2015, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.topo.topoLine.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.topo.topoLine.dao.TopoLineDao;
import com.sino.topo.topoLine.entity.TopoLine;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision$
 *
 * @since 2015-1-26
 */
@Service
@Transactional
public class TopoLineService {
private static Logger logger = LoggerFactory.getLogger(TopoLineService.class);
	
	

	private static String objAttNames="graphId,lineId,lineType,lineName,nodeAId,nodeAName,ifIndexA,ifNameA,lineBandwidth,nodeBId,nodeBName,ifIndexB,ifNameB,flag,remark";
	
	private static String jsonAttNames="graphId,lineId,lineType,lineName,nodeAId,nodeAName,ifIndexA,ifNameA,lineBandwidth,nodeBId,nodeBName,ifIndexB,ifNameB,flag,remark";
	
	@Autowired
	private TopoLineDao topoLineDao;
	
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<TopoLine> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<TopoLine> getAll() {
		logger.debug("getAll...");
		return topoLineDao.getAll();
	}
	
	public void delete(Long id) {
		topoLineDao.delete(id);
		topoLineDao.flush();
	}
	
	public void deleteLine(TopoLine line) {
		topoLineDao.delete(line);
		topoLineDao.flush();
	}
	
	public TopoLine getById(long id){
		return topoLineDao.get(id);
	}
	
	public TopoLine getByGidAndLineId(long graphId,long lineId){
		String hql=" from TopoLine where graphId=? and lineId=?";
		return topoLineDao.findUnique(hql, graphId,lineId);
	}
	
	public void save(TopoLine line){
		topoLineDao.save(line);
	}
	
	public void batchSave(List<TopoLine> list){
		topoLineDao.batchSave(list, 20);
	}
	
	public List<TopoLine> getByGraphID(long graphID)
	{
		String hql=" from TopoLine where graphId=? order by nodeAId,nodeBId";
		return topoLineDao.find(hql, graphID);
	}
	
	public List<TopoLine> getByLinesGraphID(long graphID)
	{
		String hql=" from TopoLine where graphId=?";
		return topoLineDao.find(hql, graphID);
	}
	
	public List<TopoLine> getByNodeId(long nodeId){
		String hql=" from TopoLine where nodeAId=? or nodeBId=?";
		return topoLineDao.find(hql, nodeId,nodeId);
	}
	
	public List<TopoLine> getByGraphIdAndNodeId(long graphId,long nodeId){
		String hql=" from TopoLine where graphId=? and ( nodeAId=? or nodeBId=?)";
		return topoLineDao.find(hql,graphId, nodeId,nodeId);
	}
	
//	public void batchSaveLines(List<TopoLine> list){
//		
//	}
}
