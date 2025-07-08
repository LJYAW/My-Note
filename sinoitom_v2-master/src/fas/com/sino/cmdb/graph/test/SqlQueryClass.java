package com.sino.cmdb.graph.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sino.cmdb.graph.entity.CmdbBizGraph;
import com.sino.cmdb.graph.entity.CmdbResNode;
import com.sino.cmdb.graph.entity.CmdbResRelation;

public class SqlQueryClass {
	
	public static void main(String[] args) {
//		List<CmdbBizGraph> graphList = getAllReltedGraph(5);
//		System.out.println(graphList);
		
//		List<CmdbResRelation> relationList = getAllRelatedRelation("c8a0b390-c715-416a-82aa-43b7260786b4");
		
//		String graphId = "4eb9cc29-bd6d-476a-bbec-be9caf60a1bd";
//		long resId = 2L;
//		List<CmdbResNode> list = getAllRelatedNodeInfo(graphId);
//		for (CmdbResNode cmdbResNode : list) {
//			System.out.println(cmdbResNode.getResName());
//		}
		double resAvlb = 0.5;
		long nodeId = 1562653680706L;
		int setResAvlb = setResAvlb(nodeId,resAvlb);
		System.out.println(setResAvlb);
	}
	
//	根据graphId获取该graph的所有的连线
	public static List<CmdbResRelation> getAllRelatedRelation(String graphId){
		List<CmdbResRelation> list = new ArrayList<CmdbResRelation>();
		
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

		try {
//			获取数据库连接
			conn = jdbcConnection.getConnection();
			String sql = "SELECT * FROM Cmdb_ResRelation WHERE graphId=?";
			ps = conn.prepareStatement(sql);
            ps.setString(1, graphId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
            	CmdbResRelation cmdbResRelation = new CmdbResRelation();
            	cmdbResRelation.setGraphId(graphId);
            	
                Long relationId = rs.getLong("relationId");
                cmdbResRelation.setRelationId(relationId);
                
                Long nodeAId = rs.getLong("nodeAId");
                cmdbResRelation.setNodeAId(nodeAId);
                
                Long nodeBId = rs.getLong("nodeBId");
                cmdbResRelation.setNodeBId(nodeBId);
                
                int relType = rs.getInt("relType");
                cmdbResRelation.setRelType(relType);
                
                String relName = rs.getString("relName");
                cmdbResRelation.setRelName(relName);
                
                double impactFactor = rs.getDouble("impactFactor");
                cmdbResRelation.setImpactFactor(impactFactor);
                
                list.add(cmdbResRelation);
            }
			
        	jdbcConnection.free(rs, ps, conn);
		} catch (SQLException e) {
			jdbcConnection.free(rs, ps, conn);
			e.printStackTrace();
		} 
		return list;
	}
	
//	根据resId获取graphIdList
	public static List<String> getGrapIdByResId(long resId){
		List<String> list = new ArrayList<String>();
		
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

		try {
//			获取数据库连接
			conn = jdbcConnection.getConnection();
			String sql = "SELECT graphId FROM Cmdb_ResNode WHERE resId=?";
			ps = conn.prepareStatement(sql);
            ps.setLong(1, resId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                String graphId = rs.getString("graphId");
                list.add(graphId);
            }
        	
        	jdbcConnection.free(rs, ps, conn);
		} catch (SQLException e) {
			jdbcConnection.free(rs, ps, conn);
			e.printStackTrace();
		} 
		
		return list;
	}
	
//	1、获取该resId锁参与的所有的graph
	public static List<CmdbBizGraph> getAllReltedGraph(long resId){
		List<CmdbBizGraph> list = new ArrayList<CmdbBizGraph>();
		
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<String> graphIdList = getGrapIdByResId(resId);

		try {
//			获取数据库连接
			conn = jdbcConnection.getConnection();
			String sql = "SELECT graphId,bizResId FROM Cmdb_BizGraph WHERE graphId in (?)";
			String newSql = String.format(sql.replace("?", "%s"), getStringFromArray(graphIdList.toArray()));
			ps = conn.prepareStatement(newSql);

            rs = ps.executeQuery();
            
            while(rs.next()) {
            	CmdbBizGraph cmdbBizGraph = new CmdbBizGraph();
                String graphId = rs.getString("graphId");
                Long bizResId = rs.getLong("bizResId");
                cmdbBizGraph.setGraphId(graphId);
                cmdbBizGraph.setBizResId(bizResId);
                list.add(cmdbBizGraph);
            }
        	
        	jdbcConnection.free(rs, ps, conn);
		} catch (SQLException e) {
			jdbcConnection.free(rs, ps, conn);
			e.printStackTrace();
		} 
		
		return list;
	}
	
	public static String getStringFromArray(Object ... objects ) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < objects.length; i++) {
			if(i == objects.length - 1) {
				sb.append("'"+objects[i]+"'");
			}else {
				sb.append("'"+objects[i]).append("',");
			}
		}
		return sb.toString().trim();
	}

//	根据graphId获取该画布上的所有的结点
	public static List<Long> getAllRelatedNode(String graphId) {
		List<Long> list = new ArrayList<Long>();
		
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

		try {
//			获取数据库连接
			conn = jdbcConnection.getConnection();
			String sql = "SELECT nodeId FROM Cmdb_ResNode WHERE graphId=?";
			ps = conn.prepareStatement(sql);
            ps.setString(1, graphId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Long nodeId = rs.getLong("nodeId");
                list.add(nodeId);
            }
        	
        	jdbcConnection.free(rs, ps, conn);
		} catch (SQLException e) {
			jdbcConnection.free(rs, ps, conn);
			e.printStackTrace();
		} 
		
		return list;
	}
	
//	根据graphId获取该画布上的所有的结点
	public static List<Long> getAllRelatedNodeInfo(String graphId) {
		List<Long> list = new ArrayList<Long>();
		
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
//			获取数据库连接
			conn = jdbcConnection.getConnection();
			String sql = "SELECT nodeId FROM Cmdb_ResNode WHERE graphId=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, graphId);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Long nodeId = rs.getLong("nodeId");
				list.add(nodeId);
			}
			
			jdbcConnection.free(rs, ps, conn);
		} catch (SQLException e) {
			jdbcConnection.free(rs, ps, conn);
			e.printStackTrace();
		} 
		
		return list;
	}

//	根据nodeId获取结点resId
	public static Long getResIdNodeById(Long nodeId) {
		long res = 0l;
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

		try {
//			获取数据库连接
			conn = jdbcConnection.getConnection();
			String sql = "SELECT resId FROM Cmdb_ResNode WHERE nodeId=?";
			ps = conn.prepareStatement(sql);
            ps.setLong(1, nodeId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                res = rs.getInt("resId");
            }
        	
        	jdbcConnection.free(rs, ps, conn);
		} catch (SQLException e) {
			jdbcConnection.free(rs, ps, conn);
			e.printStackTrace();
		} 
		
		return res;
	}
	
	public static String getResNameNodeById(Long nodeId) {
		String res = null;
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
//			获取数据库连接
			conn = jdbcConnection.getConnection();
			String sql = "SELECT resName FROM Cmdb_ResNode WHERE nodeId=?";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, nodeId);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				res = rs.getString("resName");
			}
			
			jdbcConnection.free(rs, ps, conn);
		} catch (SQLException e) {
			jdbcConnection.free(rs, ps, conn);
			e.printStackTrace();
		} 
		
		return res;
	}
	
//	根据graphId和resId获取nodeId
	public static long getNodeIdByGraphIdAndResId(String graphId,long resId){
		long res = 0L;
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

		try {
//			获取数据库连接
			conn = jdbcConnection.getConnection();
			String sql = "SELECT nodeId FROM Cmdb_ResNode WHERE resId=? and graphId=?";
			ps = conn.prepareStatement(sql);
            ps.setLong(1, resId);
            ps.setString(2, graphId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                res = rs.getLong("nodeId");
            }
        	
        	jdbcConnection.free(rs, ps, conn);
		} catch (SQLException e) {
			jdbcConnection.free(rs, ps, conn);
			e.printStackTrace();
		} 
		return res;
	}

	public static int setResAvlb(Long nodeId, double resAvlb) {
		int res = -1;
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

		try {
//			获取数据库连接
			conn = jdbcConnection.getConnection();
			String sql = "UPDATE Cmdb_ResNode set resAvlb=?  WHERE nodeId=?";
			ps = conn.prepareStatement(sql);
            ps.setDouble(1, resAvlb);
            ps.setLong(2, nodeId);
            res = ps.executeUpdate();
            
        	jdbcConnection.free(rs, ps, conn);
		} catch (SQLException e) {
			jdbcConnection.free(rs, ps, conn);
			e.printStackTrace();
		} 
		return res;
	}
	
	public static CmdbResNode getCmdbResNode(Long nodeId){
		
		CmdbResNode node = new CmdbResNode();
		node.setNodeId(nodeId);
		
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

		try {
//			获取数据库连接
			conn = jdbcConnection.getConnection();
			String sql = "SELECT * FROM Cmdb_ResNode WHERE nodeId=?";
			ps = conn.prepareStatement(sql);
            ps.setLong(1, nodeId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
            	
                double resAvlb = rs.getDouble("resAvlb");
                node.setResAvlb(resAvlb);
                
                String resName = rs.getString("resName");
                node.setResName(resName);
            }
			
        	jdbcConnection.free(rs, ps, conn);
		} catch (SQLException e) {
			jdbcConnection.free(rs, ps, conn);
			e.printStackTrace();
		} 
		
		return node;
	}

	public static List<Long> getNodeAIdListByNodeBId(Long nodeBId) {
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        List<Long> nodeAIdList = new ArrayList<Long>();
        
		try {
//			获取数据库连接
			conn = jdbcConnection.getConnection();
			String sql = "SELECT * FROM Cmdb_ResRelation WHERE nodeBId=?";
			ps = conn.prepareStatement(sql);
            ps.setLong(1, nodeBId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Long nodeAId = rs.getLong("nodeAId");
                nodeAIdList.add(nodeAId);
            }
			
        	jdbcConnection.free(rs, ps, conn);
		} catch (SQLException e) {
			jdbcConnection.free(rs, ps, conn);
			e.printStackTrace();
		} 
		return nodeAIdList;
	}

	public static void updateNodeList(List<CmdbResNode> allPathNodeList) {
		
		String sql = "update Cmdb_ResNode set resAvlb=? where nodeId=?";
		List<Object[]> list = new ArrayList<Object[]>();
 
		for (CmdbResNode node : allPathNodeList) {
			Object[] u = { node.getResAvlb(),node.getNodeId()};
			list.add(u);
		}
		
		JdbcConnection jdbcConnection = JdbcConnection.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
        
		try {
//			获取数据库连接
			conn = jdbcConnection.getConnection();
			ps = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			Object[] o = null;
			for (int i = 0; i < list.size(); i++) {
				o = list.get(i);
				for (int j = 0; j < o.length; j++) {
					ps.setObject(j + 1, o[j]);
				}
				ps.addBatch();
			}
 
			ps.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);

        	jdbcConnection.free(rs, ps, conn);
		} catch (SQLException e) {
			jdbcConnection.free(rs, ps, conn);
			e.printStackTrace();
		} 
		

	}

}
