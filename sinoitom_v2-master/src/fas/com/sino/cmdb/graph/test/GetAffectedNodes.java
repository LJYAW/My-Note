package com.sino.cmdb.graph.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sino.cmdb.graph.entity.CmdbBizGraph;
import com.sino.cmdb.graph.entity.CmdbResNode;
import com.sino.cmdb.graph.entity.CmdbResRelation;

public class GetAffectedNodes {
	
	public static void main(String[] args) {
		GetAffectedNodes getAffectedNodes = new GetAffectedNodes();
//		Map<Long, List<Long>> affectedResMap = getAffectedNodes.getAffectedResMap(5);
	}
	
//	key:bizResId	value:所有的节点的resId
	public Map<Long,List<Long>> getAffectedResMap(long resId){
		Map<Long,List<Long>> map = new HashMap<Long,List<Long>>();
		
//		1、获取graph集合
		List<CmdbBizGraph> graphList = SqlQueryClass.getAllReltedGraph(resId);
		
//		2、遍历graph
		if(graphList!=null && graphList.size()>0){
			for(CmdbBizGraph cmdbBizGraph : graphList){
				Long bizResId = cmdbBizGraph.getBizResId();
				String graphId = cmdbBizGraph.getGraphId();
//				3、获取该graph中所有的连线
				List<CmdbResRelation> relationList = SqlQueryClass.getAllRelatedRelation(graphId);
//				4、获取该graph中所有的节点id
				List<Long> nodeIdList = SqlQueryClass.getAllRelatedNode(graphId);
				
//				5、递归获取一条连线中另一端的节点
				getRelatedNote(relationList,bizResId,resId);
//				map.put(bizResId, resIdList);
			}
		}
		
		return map;
	}

//	最后总的结果--一个graph里面可达的节点resId
	private List<Long> nodeResIdList = new ArrayList<Long>();

	private List<List<Long>> list = new ArrayList<List<Long>>();
//	
	

//	递归获取开始id和结尾id间所有的节点的resId
//	relationList：所有的连线
//	nodeIdList：所有的节点id
//	bizResId：结尾id
//	resId：开始id
	private void getRelatedNote(List<CmdbResRelation> relationList,Long bizResId, long resId) {
//		用过的节点
		List<Long> areadyUsedNodeIdList = new ArrayList<Long>();
//		已经用过的线
		List<CmdbResRelation> areadyUsedRelationList = new ArrayList<CmdbResRelation>();
		
//		拿到与开始节点相连的所有的连线--就是有几条分支
		List<CmdbResRelation> currentNodeRelationList = getCurrentNodeRelationList(resId,relationList);
//		这些线放到已经用过的队列中
		areadyUsedRelationList.addAll(currentNodeRelationList);
//		已经走过的线就不再走了
		currentNodeRelationList.removeAll(areadyUsedNodeIdList);
		
		if(currentNodeRelationList!=null && currentNodeRelationList.size()>0){
//			遍历这些与节点相连的线
			for(CmdbResRelation relation : currentNodeRelationList){
//				拿到这条线两端的节点
				Long nodeAId = relation.getNodeAId();
				Long nodeBId = relation.getNodeBId();
				
				if(nodeAId==resId){//另一端B就是nodeBId
//					1、如果另一端是终点节点
					if(nodeBId==bizResId){
//						往前退一步
						areadyUsedRelationList.clear();
						areadyUsedNodeIdList.clear();
						list.add(areadyUsedNodeIdList);
						
						continue;
					}
					
//					另一端放到已用过的节点中
					areadyUsedNodeIdList.add(nodeBId);
//					进入递归
					getRelatedNote(relationList,bizResId,nodeBId);
				}
				if(nodeBId==resId){//另一端B就是nodeAId
//					1、如果另一端是终点节点
					if(nodeAId==bizResId){
//						往前退一步
						areadyUsedRelationList.remove(relation);
						list.add(areadyUsedNodeIdList);
						areadyUsedNodeIdList.clear();
						continue;
					}
					
//					另一端放到已用过的节点中
					areadyUsedNodeIdList.add(nodeAId);
//					进入递归
					getRelatedNote(relationList,bizResId,nodeAId);
				}

			}
		}

	}
	

//	给一个结点，拿到与该节点相连的所有的连线
	private List<CmdbResRelation> getCurrentNodeRelationList(Long resId,List<CmdbResRelation> relationList){
//		与当前节点相连的所有的线
		List<CmdbResRelation> currentNodeRelationList = new ArrayList<CmdbResRelation>();
		for(CmdbResRelation relation : relationList){
			Long nodeAId = relation.getNodeAId();
			Long nodeBId = relation.getNodeBId();
			
//			首先拿到与开始节点相连的所有的线
			if(resId==nodeAId || resId==nodeBId){
				currentNodeRelationList.add(relation);
			}
		}
		
		return currentNodeRelationList;
	}
	
}
