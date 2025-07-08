package com.sino.cmdb.graph.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.graph.dao.CmdbBizGraphDao;
import com.sino.cmdb.graph.dao.CmdbResNodeDao;
import com.sino.cmdb.graph.dao.CmdbResRelationDao;
import com.sino.cmdb.graph.entity.CmdbBizGraph;
import com.sino.cmdb.graph.entity.CmdbResNode;
import com.sino.cmdb.graph.entity.CmdbResRelation;

@Service
@Transactional
public class CmdbBizGraphService {

	private static Logger logger = LoggerFactory.getLogger(CmdbBizGraphService.class);
	
	@Autowired
	private CmdbBizGraphDao cmdbBizGraphDao;
	@Autowired
	private CmdbResNodeDao cmdbResNodeDao;
	@Autowired
	private CmdbResRelationDao cmdbResRelationDao;
	
	
	private static String objAttNames = "graphId,bizResId,bizTypeCode,bizTypeCode@IT_BizType,bizTypeName,bizName,graphType,graphName,graphDescr,width,height,flag,status,creator,createTime";
	private static String jsonAttNames = "id,bizResId,bizTypeCode,IT_BizType,bizTypeName,bizName,graphType,graphName,graphDescr,width,height,flag,status,creator,createTime";
	
	@Transactional(readOnly = true)
	public String getJsonTopoGraphObjStr(final CmdbBizGraph obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	@Transactional(readOnly = true)
	public String getJsonTopoGraphListStr(final List<CmdbBizGraph> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
//	--------------------------------------------------------
	
    //查询所有
	@Transactional(readOnly = true)
	public List<CmdbBizGraph> getAllTopoGraph() {
		
		return cmdbBizGraphDao.getAll();
	}
	
//	根据id获取对象
	@Transactional(readOnly = true)
	public CmdbBizGraph getTopoGraphByID(String id) {
		return cmdbBizGraphDao.get(id);
	}
	
//	保存
	public void save(CmdbBizGraph cmdbBizGraph) {
		
		cmdbBizGraphDao.save(cmdbBizGraph);
	}
//	通过id获取CmdbBizGraph对象
	public CmdbBizGraph getByID(String graphId) {
		CmdbBizGraph obj = cmdbBizGraphDao.get(graphId);
		return obj;
	}
//	保存拓扑图
	public void saveTopo(CmdbBizGraph graph, List<CmdbResNode> saveNodes,
			List<CmdbResRelation> saveLines) {
//		保存graph
		graph.setStatus(1);
		cmdbBizGraphDao.save(graph);
		cmdbBizGraphDao.flush();
		
//		删除该graph的所有的node
		deleteNodesByGid(graph.getGraphId());
		cmdbResNodeDao.flush();
		
//		保存新的node
		cmdbResNodeDao.batchSave(saveNodes, saveNodes.size());
		cmdbResNodeDao.flush();
		
//		删除该graph的所有的relation
		cmdbResRelationDao.deleteLineByGraphId(graph.getGraphId());
		cmdbResRelationDao.flush();
		
//		保存新的relation
		cmdbResRelationDao.batchSave(saveLines, saveLines.size());
		cmdbResRelationDao.flush();
		
	}
	
	public void deleteNodesByGid(String graphId){
		cmdbResNodeDao.deleteByGraphId(graphId);
		cmdbResNodeDao.flush();
	}
	
	public void deleteTopoGraph(String [] ids) {
		logger.debug("deleteTopoGraph(id:{})...", ids);
		for(String id:ids){
			cmdbBizGraphDao.delete(id);
			cmdbBizGraphDao.flush();
			
			cmdbResNodeDao.deleteByGraphId(id);
			cmdbResNodeDao.flush();
			
			cmdbResRelationDao.deleteLineByGraphId(id);
			cmdbResRelationDao.flush();			
			
		}
		
	}
}
