package com.sino.cmdb.graph.test.juzhen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph<T> {
	// 邻接矩阵
	private int[][] matrix;
	// 顶点数组
	private T[] node;
	// 顶点的数目
	private int nodeNum;
	// 当前结点是否还有下一个结点，判断递归是否结束的标志
	private boolean noNext = false;
	
	private List<List<T>> result = new ArrayList<List<T>>();
 
	public Graph(int[][] matrix, T[] node) {
		if (matrix.length != matrix[0].length) {
			throw new IllegalArgumentException("该邻接矩阵不是方阵");
		}
		if (matrix.length != node.length) {
			throw new IllegalArgumentException("结点数量和邻接矩阵大小不一致");
		}
		this.matrix = matrix;
		this.node = node;
		nodeNum = matrix.length;
	}
 
	/**
	 * 深度遍历的递归
	 * 先开始begin是0，path是空的
	 */
	private void DFS(int begin, List<T> path) {
		// 将当前结点加入记录队列
		path.add(node[begin]);//node就是遍历过程中存储节点的栈
		// 标记回滚位置
		int rollBackNum = -1;
		// 遍历相邻的结点
		for (int i = 0; i < nodeNum; i++) {
			if ((matrix[begin][i] > 0)) {//说明相连
				// 临时加入相邻结点，试探新的路径是否已遍历过
				path.add(node[i]);
				if (containBranch(result, path)) {
					// 路径已存在，将相邻结点再移出记录队伍
					path.remove(node[i]);
					// 记录相邻点位置，用于循环结束发现仅有当前一个相邻结点时回滚事件
					rollBackNum = i;
					// 寻找下一相邻结点
					continue;
				} else {
					// 路径为新路径，准备进入递归，将相邻结点移出记录队伍，递归中会再加入，防止重复添加
					path.remove(node[i]);
					// 递归
					DFS(i, path);
				}
			}
			// 终止递归
			if (noNext) {
				return;
			}
		}//for循环结束
		
		
		if (rollBackNum > -1) {
			// 循环结束仅有一个相邻结点，从这个相邻结点往下递归
			DFS(rollBackNum, path);
		} else {
			// 当前结点没有相邻结点，设置flag以结束递归
			noNext = true;
		}
	}
 
	/**
	 * 开始深度优先遍历
	 */
	public List<List<T>> startSearch() {
//		countPathNumber()：矩阵中有多少个分岔的点，也就是路径多于一条的点
		int pathNumber = countPathNumber();
		for (int i = 0; i < pathNumber; i++) {
			// 用于存储遍历过的点
			List<T> path = new LinkedList<T>();
			noNext = false;
			// 开始遍历
			DFS(0, path);
//			当这条路径是一条新的路径就保存，如果已经存在就不保存
			if(!containBranch(result,path)){
				// 保存结果
				result.add(path);
			}
			
		}
		return result;
	}
	
	/**
	 * 计算路径的分支数量
	 */
	private int countPathNumber() {
		int[] numberArray = new int[nodeNum];
		for (int i = 0; i < nodeNum; i++) {
			for (int j = 0; j < nodeNum; j++) {
				if (matrix[j][i] > 0) {
					numberArray[j]++;//numberArray里面放的就是每个点后面有多少个邻接点
				}
			}
		}
//		int number = 1;//为什么多加一个1
//		for (int k = 0; k < nodeNum; k++) {
//			if (numberArray[k] > 1) {
//				number++;//这个是整个矩阵中有多少个分叉的点
//			}
//		}
		int number = max(numberArray);
		return number;
	}
	
	private int max (int[]data){
		if(data==null){
			throw new IllegalArgumentException("a mudt be not null.");

		}
		int maxnumber = data[0];
		for(int i = 0;i<data.length;i++){
			if (maxnumber<data[i]){
				maxnumber = data[i];
			}
			continue;
		}
		return maxnumber;
	}
 
	/**
	 * 判断当前路径是否被已有路径的结果集合所包含
	 */
	private boolean containBranch(List<List<T>> nodeLists, List<T> edges) {
		for (int i = 0; i < nodeLists.size(); i++) {
			List<T> list = nodeLists.get(i);
			if (list.containsAll(edges)) {
				return true;
			}
		}
		return false;
	}
	
}
