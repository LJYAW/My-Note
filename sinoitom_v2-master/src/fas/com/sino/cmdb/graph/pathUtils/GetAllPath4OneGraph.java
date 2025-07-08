package com.sino.cmdb.graph.pathUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sino.cmdb.graph.entity.CmdbResNode;
import com.sino.cmdb.graph.entity.CmdbResRelation;
import com.sino.cmdb.graph.test.SqlQueryClass;

public class GetAllPath4OneGraph {
	
//	一维数组
	private Long[] nodeIdArray = null;
//	二维数组
	private int[][] matrix = null;
//	开始节点
	private Long startNodeId = null;
//	结束节点
	private Long endNodeId = null;
//	画布的id--graphId
	private String graphId = null;
//	总的节点数量
	private int nodeNum = 0;
	
	
//	一维数组的集合
	private List<Long> list = new ArrayList<Long>();
//	还没用过的线
	private List<CmdbResRelation> excuReList = new ArrayList<CmdbResRelation>();
//	所有的线
	List<CmdbResRelation> allResList = new ArrayList<CmdbResRelation>();
//	所有的通路--仅仅是点：有顺序，有重复的点
	private List<List<String>> allPathList = new ArrayList<List<String>>();
//	通路上的点的id的集合--没有重复的点
	List<Long> finalResList = new ArrayList<Long>();
	
//	================================================================================
	
//	测试
	public static void main(String[] args) {
		String graphId = "4eb9cc29-bd6d-476a-bbec-be9caf60a1bd";
		Long startNodeId = 1562575034468L;
		Long endNodeId = 1562575035799L;
		
//		调用方法
		GetAllPath4OneGraph getAllPathUtil = new GetAllPath4OneGraph(graphId,startNodeId,endNodeId);
		getAllPathUtil.setResAvlb4All(0.5);
//		输出所有的点
//		List<Long> nodeIdList = getAllPathUtil.getNode();
//		for (Long nodeId : nodeIdList) {
//			System.out.println(nodeId);
//		}
//		
//		System.out.println();
		
//		输出所有的路径
//		List<List<Long>> path = getAllPathUtil.getPath();
//		for (List<Long> list : path) {
//			System.out.println(list);
//		}
		
	}
	
	public void setResAvlb4All(double resAvlb){
		for(long nodeId : finalResList){
//			给所有的节点resAvlb属性赋值0
			SqlQueryClass.setResAvlb(nodeId,0.00);
		}
		
//		获取最后一个结点对象并赋值resAvlb
		List<Long> endNodeIdList = new ArrayList<Long>();
		CmdbResNode endNode = SqlQueryClass.getCmdbResNode(endNodeId);
		endNode.setResAvlb(resAvlb);
		
//		保存最后一个结点对象
		List<CmdbResNode> tempList = new ArrayList<CmdbResNode>();
		tempList.add(endNode);
		SqlQueryClass.updateNodeList(tempList);
		
//		给“还没用过的线”的集合重新赋值
		this.excuReList = new ArrayList<CmdbResRelation>();
		excuReList.addAll(allResList);
		endNodeIdList.add(endNodeId);
//		计算可用值
		setResAvlb(endNodeIdList);
		
	}
	
//	获取通路上的每个点的可用值并保存
	public void setResAvlb(List<Long> endNodeIdList){
		if(endNodeIdList.contains(startNodeId)){
			return;
		}
		
		if(endNodeIdList!=null && endNodeIdList.size()>0){
			List<Long> tempList = new ArrayList<Long>();
			Set<Long> tempSet = new HashSet<Long>();
			List<CmdbResNode> saveNodeList = new ArrayList<CmdbResNode>();
			for (Long nodeId : endNodeIdList) {
//				首先找到与结束节点相邻的在通路上的点的集合
				List<Long> anotherNodeId = getNodeAIdList(nodeId);
				boolean flag = false;
				for (long item : anotherNodeId) {
					if(tempSet.contains(item)){
						flag = true;
					}
				}
				if(flag){
					continue;
				}
				tempSet.addAll(anotherNodeId);
				
//				System.out.println(anotherNodeId);
//				现在去拿两点之间的线的影响值,并计算nodeResAvlb，保存入库
				for (Long tempNodeId : anotherNodeId) {
//					获取节点对象--nodeId、resAvlb、resName
					CmdbResNode tempNode = SqlQueryClass.getCmdbResNode(tempNodeId);
//					找到该节点的nodeB节点和相连的线的影响值
					double tempNodeResAvlb = getNodeResAvlb(tempNodeId);
					tempNode.setResAvlb(tempNodeResAvlb);
					saveNodeList.add(tempNode);
				}
			}
			SqlQueryClass.updateNodeList(saveNodeList);
			tempList.addAll(tempSet);
			setResAvlb(tempList);
		}
	}
	
//	倒推相邻的nodeAId,且这个点是在受影响的路径上的点的集合中
	private List<Long> getNodeAIdList(long nodeBId){
		List<Long> nodeAIdList = new ArrayList<Long>();
		for(CmdbResRelation relation: allResList){
			long nodeAId2 = relation.getNodeAId();
			long nodeBId2 = relation.getNodeBId();
			
			if(nodeBId2==nodeBId){
				nodeAIdList.add(nodeAId2);
			}
		}
		
//		跟最终结果取交集--就拿到通路上相邻的节点，不在通路上的不要
		nodeAIdList.retainAll(finalResList);
		return nodeAIdList;
	}
	
//	获取与当前节点相连的B节点的信息和相连的线的影响值
	private Double getNodeResAvlb(long nodeAId){
		Double resAvlb =0.00;
		List<CmdbResRelation> tempList = new ArrayList<CmdbResRelation>();
		for (CmdbResRelation relation : excuReList) {
			long nodeAId2 = relation.getNodeAId();
			long nodeBId2 = relation.getNodeBId();
			if(nodeAId2==nodeAId){
				Double impactFactor = relation.getImpactFactor();
				CmdbResNode nodeB = SqlQueryClass.getCmdbResNode(nodeBId2);
				Double nodeBResAvlb = nodeB.getResAvlb();
				
				resAvlb = resAvlb+nodeBResAvlb*impactFactor;
				tempList.add(relation);
			}
		}
		excuReList.removeAll(tempList);
		return resAvlb;
	}
	
//	构造方法--初始化参数
	public GetAllPath4OneGraph(String graphId,Long startNodeId,Long endNodeId){
		this.graphId = graphId;
		this.startNodeId = startNodeId;
		this.endNodeId = endNodeId;
		List<CmdbResRelation> reList = SqlQueryClass.getAllRelatedRelation(graphId);
		this.allResList.addAll(reList);
		this.excuReList.addAll(reList);
//		获取结果集
		getRelatedNodeIdList();
	}
	
//	调用方法，获取结果集
	private void getRelatedNodeIdList(){
//		首先获取一维数组和二维数组
		getPhalanx();
		
//		调用graph的方法，获取该画布上所有的通路--按照顺序
		String[] nodeIdStrArray = new String[nodeNum];
//		将Long型数组转成String类型的数组
		for (int i = 0;i<nodeNum;i++) {
			nodeIdStrArray[i] = nodeIdArray[i]+"";
		}
		Graph<String> graph = new Graph<String>(matrix, nodeIdStrArray);
		List<List<String>> startSearch = graph.startSearch();
		
//		获取开始节点和结束节点之间的节点
		Set<String> tempSet = new HashSet<String>();
//		pathList是一条从头到尾的通路
		for (List<String> pathList : startSearch) {
//			开始节点的位置
			int startIndex = pathList.indexOf(startNodeId+"");
//			结束节点的位置
			int endIndex = pathList.indexOf(endNodeId+"");
			if(startIndex!=-1 && endIndex!=-1 && startIndex<endIndex){
//				截取
				List<String> subList = pathList.subList(startIndex, endIndex);
				if(subList!=null && subList.size()>0){
//					收集两点之间的路径
					allPathList.add(subList);
//					收集两点之间的点--进行去重
					tempSet.addAll(subList);
				}
			}
		}
		
		for (String temp : tempSet) {
			finalResList.add(Long.parseLong(temp));
		}
		
	}
	
//	获取可达路径上所有的节点--去重后的
	public List<Long> getNode(){
		return this.finalResList;
	}
	
//	获取所有的可达路径集合
	public List<List<Long>> getPath(){
		List<List<Long>> resultList = new ArrayList<List<Long>>();
		for (List<String> nodeIdStrList : this.allPathList) {
			List<Long> tempList = new ArrayList<Long>();
			for (String temp : nodeIdStrList) {
				tempList.add(Long.parseLong(temp));
			}
			resultList.add(tempList);
		}
		return resultList;
	}

	
//	获取方阵--即一维数组和二维数组
	private void getPhalanx(){
//		1、获取一维数组
		List<Long> startNodeIdList = new ArrayList<Long>();
		startNodeIdList.add(startNodeId);
		this.list.add(startNodeId);
//		递归获取开始节点后的所有的节点集合
		getArray(startNodeIdList);
		this.nodeNum = this.list.size();
		this.nodeIdArray = this.list.toArray(new Long[this.nodeNum]);
		
//		2、获取二维数组
		getMatrix();
	}
	
//	获取一维数组
	private void getArray(List<Long> startNodeIdList){//A
		if(startNodeIdList!=null && startNodeIdList.size()>0){
			List<Long> tempList = new ArrayList<Long>();
			for (Long nodeId : startNodeIdList) {
//				找到相邻的节点集合
				List<Long> anotherNodeId2 = getAnotherNodeId(nodeId);
//				将每个节点的后面相连的所有节点的集合放到临时容器tempList中
				tempList.addAll(anotherNodeId2);
			}
//			去重
			for (Long nodeId : tempList) {
				if(!this.list.contains(nodeId)){
					this.list.add(nodeId);
				}
			}
			//E、F、G
			getArray(tempList);
		}
	}
	
//	获取相邻节点的集合
	private List<Long> getAnotherNodeId(long nodeAId){
		List<Long> nodeList = new ArrayList<Long>();
		List<CmdbResRelation> reList = new ArrayList<CmdbResRelation>();
		for(CmdbResRelation relation :excuReList){
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
//		剔除已经用过的线
		this.excuReList.removeAll(reList);
		return nodeList;
	}
	
//	获取二维数组
	private void getMatrix(){
//		构造节点关系的二维数组
		this.matrix = new int[nodeNum][nodeNum];
		
//		给“还没用过的线”的集合重新赋值
		this.excuReList = SqlQueryClass.getAllRelatedRelation(graphId);
		
		for(int i = 0;i<nodeNum;i++){
			long nodeAId = nodeIdArray[i];
			for(int j = i+1;j<nodeNum;j++){
				long nodeBId = nodeIdArray[j];
//				看nodeAId与nodeBId是否相邻
				boolean adjacent = isAdjacent(nodeAId,nodeBId,excuReList);
				if(adjacent){//如果相邻，设为1
					matrix[i][j] = 1;
				}
			}
		}
	}
	
//	判断两个节点是否相邻
	private boolean isAdjacent(long nodeAId,long nodeBId,List<CmdbResRelation> relationList){
		boolean flag = false;
		for(CmdbResRelation relation :relationList){
			long nodeAId2 = relation.getNodeAId();
			long nodeBId2 = relation.getNodeBId();
			if(nodeAId==nodeAId2 && nodeBId==nodeBId2){
				flag = true;
//				去除已经比较过的线
				excuReList.remove(relation);
//				跳出循环，不然报错
				break;
			}
			if(nodeAId==nodeBId2 && nodeBId==nodeAId2){
				flag = true;
//				去除已经比较过的线
				excuReList.remove(relation);
//				跳出循环，不然报错
				break;
			}
		}
		
		return flag;
	}

}
