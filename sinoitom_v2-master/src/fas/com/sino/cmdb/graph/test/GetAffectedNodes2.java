package com.sino.cmdb.graph.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sino.cmdb.graph.entity.CmdbBizGraph;
import com.sino.cmdb.graph.entity.CmdbResNode;
import com.sino.cmdb.graph.entity.CmdbResRelation;
import com.sino.cmdb.graph.test.juzhen.Graph;

public class GetAffectedNodes2 {
	
	private Long[] nodeIdArray = null;
	private int[][] matrix = null;
	private int nodeNum = 0;//节点个数
	
	//最后的结果--放的nodeId
	private List<Long> list = new ArrayList<Long>();
	//还没用过的线
	private List<CmdbResRelation> excuReList = new ArrayList<CmdbResRelation>();
	
	public static void main(String[] args) {
		GetAffectedNodes2 getAffectedNodes2 = new GetAffectedNodes2();
		Map<String, List<Long>> affectedResMap = getAffectedNodes2.getAffectedResMap(5L);
		for(String key : affectedResMap.keySet()){
			List<Long> list = affectedResMap.get(key);
			System.out.println(key+"---");
			System.out.println(list);
		}
		
//		String graphId = "4eb9cc29-bd6d-476a-bbec-be9caf60a1bd";
//		Long startNodeId = 1562575034468L;
//		getAffectedNodes2.excuReList = SqlQueryClass.getAllRelatedRelation(graphId);
//		
//		getAffectedNodes2.list.add(startNodeId);
//		
//		List<Long> startNodeIdList = new ArrayList<Long>();
//		startNodeIdList.add(startNodeId);
//		
//		getAffectedNodes2.getArray(startNodeIdList);
////			1、获取该graph中所有的节点
//		for(long item : getAffectedNodes2.list){
//			String resName = SqlQueryClass.getResNameNodeById(item);
//			System.out.println(resName);
//		}
	}
	
	public Map<String,List<Long>> getAffectedResMap(long resId){
		Map<String,List<Long>> map = new HashMap<String,List<Long>>();
		
//		1、获取graph集合
		List<CmdbBizGraph> graphList = SqlQueryClass.getAllReltedGraph(resId);
		
//		2、遍历graph
		if(graphList!=null && graphList.size()>0){
			for(CmdbBizGraph cmdbBizGraph : graphList){
//				获取当前graph的业务id--也就是开始节点的resId
				Long bizResId = cmdbBizGraph.getBizResId();
				String graphId = cmdbBizGraph.getGraphId();
				
//				根据graphId和resIdent获取nodeId
				long startNodeId = SqlQueryClass.getNodeIdByGraphIdAndResId(graphId,bizResId);
				long endNodeId = SqlQueryClass.getNodeIdByGraphIdAndResId(graphId,resId);
//				获取受影响的节点的resId集合
				List<Long> accessiblePathNodeList = this.getAccessiblePathNode(graphId,startNodeId,endNodeId);
				
				map.put(graphId, accessiblePathNodeList);
			}
		}
		
		return map;
	}
	
//	测试数据：graphId="c8a0b390-c715-416a-82aa-43b7260786b4"；
//	startNodeId = 1561618201494L;；
//	endNodeId = 1561618227472L;
	
//	获取一个graph的可达路径
	public List<Long> getAccessiblePathNode(String graphId,long startNodeId,long endNodeId){
		this.getMatrix(graphId,startNodeId);
		
//		输出二维数组
		for(int i = 0;i<matrix.length;i++){
			for(int j = 0;j<matrix[i].length;j++){
				System.out.print(matrix[i][j]+",");
			}
			System.out.println();
		}
		String[] nodeIdStrArray = new String[nodeNum];
		for(int i = 0;i<nodeNum;i++){
			nodeIdStrArray[i] = nodeIdArray[i]+"";
		}
//		输出一维数组
		System.out.println(Arrays.toString(nodeIdStrArray));
		
//		获取可达路径的集合
		Graph<String> graph = new Graph<String>(matrix, nodeIdStrArray);
		List<List<String>> startSearch = graph.startSearch();
		System.out.println(startSearch);
		
//		将该路径上的节点放到一个set中（为了去重）
		Set<Long> finalResult = new HashSet<Long>();
		for(List<String> list : startSearch){
			for (String str : list) {
				finalResult.add(Long.parseLong(str));
			}
		}
//		输出
//		System.out.println(finalResult);
		
//		获取所有可达路径上的所有的节点的resId集合
		List<Long> resIdList = new ArrayList<Long>();
		for (long nodeId : finalResult) {
			Long resId = SqlQueryClass.getResIdNodeById(nodeId);
			resIdList.add(resId);
		}
//		System.out.println(resIdList);
		
		return resIdList;
	}
	
//	获取一个graph的可达路径
	public List<Long> getAccessiblePathNode2(String graphId,long startNodeId,long endNodeId){
		this.getMatrix(graphId,startNodeId);
		
//		输出二维数组
		for(int i = 0;i<matrix.length;i++){
			for(int j = 0;j<matrix[i].length;j++){
				System.out.print(matrix[i][j]+",");
			}
			System.out.println();
		}
		String[] nodeIdStrArray = new String[nodeNum];
		for(int i = 0;i<nodeNum;i++){
			nodeIdStrArray[i] = nodeIdArray[i]+"";
		}
//		输出一维数组
		System.out.println(Arrays.toString(nodeIdStrArray));
		
//		获取可达路径的集合
		Graph<String> graph = new Graph<String>(matrix, nodeIdStrArray);
		List<List<String>> startSearch = graph.startSearch();
		System.out.println(startSearch);
		
//		获取所有可达路径上的所有的节点的resId集合
		Set<String> nodeIdList = new HashSet<String>();
		List<Long> resList = new ArrayList<Long>();
		for(List<String> list : startSearch){
			int startIndex = list.indexOf(startNodeId+"");
			int endIndex = list.indexOf(endNodeId+"");
			if(startIndex!=-1 && endIndex!=-1&& startIndex<endIndex){
				List<String> subList = list.subList(startIndex+1, endIndex);
				nodeIdList.addAll(subList);
			}
		}
		
		for (String str : nodeIdList) {
			resList.add(Long.parseLong(str));
		}
		
		resList.add(startNodeId);
		
		return resList;
	}
	
//	首先获取所有的节点，然后创建一个一维数组
	private void getMatrix(String graphId,Long startNodeId){
//		1、获取该graph中所有的节点
		List<Long> allNodeIdList = SqlQueryClass.getAllRelatedNode(graphId);
		
//		总的的节点个数
		nodeNum = allNodeIdList.size();
		
		excuReList = SqlQueryClass.getAllRelatedRelation(graphId);
		
		list.add(startNodeId);
		
		List<Long> startNodeIdList = new ArrayList<Long>();
		startNodeIdList.add(startNodeId);
		getArray(startNodeIdList);
		nodeIdArray  = list.toArray(new Long[nodeNum]);
		
//		构造节点关系的二维数组
		matrix = new int[nodeNum][nodeNum];
		
//		2、获取该graph中所有的连线
		List<CmdbResRelation> relationList = SqlQueryClass.getAllRelatedRelation(graphId);
		for(int i = 0;i<nodeNum;i++){
			long nodeAId = nodeIdArray[i];
			for(int j = i+1;j<nodeNum;j++){
				long nodeBId = nodeIdArray[j];
//				看nodeAId与nodeBId是否相邻
				boolean adjacent = isAdjacent(nodeAId,nodeBId,relationList);
				if(adjacent){//如果相邻，设为1
					matrix[i][j] = 1;
				}
			}
		}
	}
	
	private void getArray(List<Long> startNodeIdList){//A
		if(startNodeIdList!=null && startNodeIdList.size()>0){
			List<Long> tempList = new ArrayList<Long>();
			for (Long long1 : startNodeIdList) {
				List<Long> anotherNodeId2 = getAnotherNodeId(long1,excuReList);//找到相邻的节点集合
				tempList.addAll(anotherNodeId2);
			}
			for (Long long1 : tempList) {
				if(!list.contains(long1)){
					list.add(long1);
				}
			}
			//E、F、G
			getArray(tempList);
		}
	}
	
	private List<Long> getAnotherNodeId(long nodeAId,List<CmdbResRelation> relationList){
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
	
//	判断两个节点是否相邻
	private boolean isAdjacent(long nodeAId,long nodeBId,List<CmdbResRelation> relationList){
		boolean flag = false;
		for(CmdbResRelation relation :relationList){
			long nodeAId2 = relation.getNodeAId();
			long nodeBId2 = relation.getNodeBId();
			if(nodeAId==nodeAId2 && nodeBId==nodeBId2){
				flag = true;
			}
			if(nodeAId==nodeBId2 && nodeBId==nodeAId2){
				flag = true;
			}
		}
		
		return flag;
	}
	

}
