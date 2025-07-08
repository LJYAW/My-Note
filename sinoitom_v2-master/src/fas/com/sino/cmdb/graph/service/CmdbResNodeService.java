package com.sino.cmdb.graph.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.graph.dao.CmdbResNodeDao;
import com.sino.cmdb.graph.dao.CmdbResRelationDao;
import com.sino.cmdb.graph.entity.CmdbResNode;
import com.sino.cmdb.graph.entity.CmdbResRelation;

@Service
@Transactional
public class CmdbResNodeService {
	
	private static Logger logger = LoggerFactory.getLogger(CmdbResNodeService.class);
	
	private static String objAttNames = "graphId,nodeId,nodeType,nodeIcon,posX,posY,width,height,resCatCode,resId,resName,resLabel,resClassCode,resClassName,resTypeCode,resTypeName,descr,namePosX,namePosY,resAvlb";
	private static String jsonAttNames = "graphId,id,nodeType,nodeIcon,posX,posY,width,height,resCatCode,resId,resName,resLabel,resClassCode,resClassName,resTypeCode,resTypeName,descr,namePosX,namePosY,resAvlb";
	
	@Transactional(readOnly = true)
	public String getJsonjStr(final CmdbResNode obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<CmdbResNode> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Autowired
	private CmdbResNodeDao cmdbResNodeDao;
	@Autowired
	private CmdbResRelationDao cmdbResRelationDao;
	
//	---------------------------------------------------------

//	通过graphId获取节点列表
	public List<CmdbResNode> getByGraphID(String graphId) {
		String hql=" from CmdbResNode where graphId=? order by nodeId";
		return cmdbResNodeDao.find(hql, graphId);
	}
//	保存结点
	public void save(CmdbResNode node) {
		cmdbResNodeDao.save(node);
		
	}
//	通过id获取对象
	public CmdbResNode getByID(long id) {
		CmdbResNode cmdbResNode = cmdbResNodeDao.get(id);
		return cmdbResNode;
	}
	
//	通过graphId和nodeId获取对象
	public CmdbResNode getByGraphIdAndNodeId(String graphId,long nodeId){
		String hql=" from CmdbResNode where graphId=? and nodeId=?";
		return cmdbResNodeDao.findUnique(hql, graphId,nodeId);
	}
	
	public void deleteNodeById(long nodeID,List<CmdbResRelation> lines){
		cmdbResNodeDao.delete(nodeID);
		cmdbResNodeDao.flush();
		
		if(!lines.isEmpty()){
			cmdbResRelationDao.delete(lines);
			cmdbResRelationDao.flush();
		}
	}
	public CmdbResNode getResIdByCode(String graphId) {
		CmdbResNode resId = cmdbResNodeDao.getResIdByCode(graphId);
		return resId;
	}
	public List<CmdbResNode> getByIds(List<Long> nodeIdList) {
//		要清一波缓存，要不跟数据库数据不一致
		cmdbResNodeDao.getSession().clear();
		List<CmdbResNode> list = cmdbResNodeDao.get(nodeIdList);
		return list;
	}
	
}
