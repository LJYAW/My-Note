package com.sino.cmdb.graph.test;

import java.util.ArrayList;
import java.util.List;

import com.sino.cmdb.graph.entity.CmdbResNode;
import com.sino.cmdb.graph.entity.CmdbResRelation;

public class GetAffectedNodes3 {
	public static void main(String[] args) {
		
	}
	
//	取出已经比较过的线
	public static List<CmdbResRelation> excuReList = new ArrayList<CmdbResRelation>();
	
	public static void getPath(long startNodeId,long endNodeId,List<Long> nodeList,List<CmdbResRelation> relationList){
		nodeList.remove(startNodeId);//把开始起点去掉
		excuReList = relationList;
//		求与开始节点相连的之后的节点
		List<Long> anotherNodeId = getAnotherNodeId(startNodeId,excuReList);
		for(Long nodeId:anotherNodeId){
			
		}
	}

//	求与nodeAId节点相连的之后的节点
	public static List<Long> getAnotherNodeId(long nodeAId,List<CmdbResRelation> relationList){
		List<Long> nodeList = new ArrayList<Long>();
		List<CmdbResRelation> reList = new ArrayList<CmdbResRelation>();
		for(CmdbResRelation relation :relationList){
			long nodeAId2 = relation.getNodeAId();
			long nodeBId2 = relation.getNodeBId();
			if(nodeAId2==nodeAId){
				nodeList.add(nodeBId2);
				reList.add(relation);
			}
			
			if(nodeBId2==nodeAId){
				nodeList.add(nodeAId2);
				reList.add(relation);
			}
		}
		excuReList.removeAll(reList);
		return nodeList;
	}

}
