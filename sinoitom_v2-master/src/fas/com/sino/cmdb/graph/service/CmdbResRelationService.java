package com.sino.cmdb.graph.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.graph.dao.CmdbResRelationDao;
import com.sino.cmdb.graph.entity.CmdbResRelation;

@Service
@Transactional
public class CmdbResRelationService {
	
	private static Logger logger = LoggerFactory.getLogger(CmdbResRelationService.class);
	
	private static String objAttNames = "graphId,relationId,nodeAId,nodeBId,relType,relName,relDescr,impactFactor";
	private static String jsonAttNames = "graphId,id,nodeAId,nodeBId,relType,relName,relDescr,impactFactor";
	
	@Transactional(readOnly = true)
	public String getJsonjStr(final CmdbResRelation obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<CmdbResRelation> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Autowired
	private CmdbResRelationDao cmdbResRelationDao;
	
//	-------------------------------------------------------
//	根据graphId获取连线列表
	public List<CmdbResRelation> getByGraphID(String graphId) {
		String hql="from CmdbResRelation where graphId=? order by nodeAId,nodeBId";
		return cmdbResRelationDao.find(hql, graphId);
	}
	
//	通过graphId和某一个节点id获取对象列表
	public List<CmdbResRelation> getByGraphIdAndNodeId(String graphId,long nodeId){
		String hql=" from CmdbResRelation where graphId=? and ( nodeAId=? or nodeBId=?)";
		return cmdbResRelationDao.find(hql,graphId, nodeId,nodeId);
	}
	
//	通过某一端的node的id获取对象列表
	public List<CmdbResRelation> getByNodeId(long nodeId){
		String hql=" from CmdbResRelation where nodeAId=? or nodeBId=?";
		return cmdbResRelationDao.find(hql, nodeId,nodeId);
	}
	
	public CmdbResRelation getByGidAndLineId(String graphId,long relationId){
		String hql=" from CmdbResRelation where graphId=? and relationId=?";
		return cmdbResRelationDao.findUnique(hql, graphId,relationId);
	}
	
	public void deleteLine(CmdbResRelation line) {
		cmdbResRelationDao.delete(line);
		cmdbResRelationDao.flush();
	}
	public CmdbResRelation getById(Long relationId) {
		CmdbResRelation cmdbResRelation = cmdbResRelationDao.get(relationId);
		return cmdbResRelation;
	}
	public void save(CmdbResRelation line) {
		cmdbResRelationDao.save(line);
		cmdbResRelationDao.flush();
		
	}

}
