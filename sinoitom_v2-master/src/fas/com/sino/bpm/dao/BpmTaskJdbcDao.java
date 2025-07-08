package com.sino.bpm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.sino.cmdb.graph.entity.CmdbResNode;
import com.sino.cmdb.graph.web.JdbcConnection;

public class BpmTaskJdbcDao {
	
	public static List<CmdbResNode> getByBizList(String bizIdListStr){
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement  st = null;
		
		StringBuffer query = new StringBuffer().append("select * from Cmdb_ResNode ");
		query.append("where graphId in (select graphId from Cmdb_ResNode where resId in("+bizIdListStr+"))");
		try {
            if(conn == null || conn.isClosed()){
            	conn = JdbcConnection.getInstance().getConnection();
            }
            	
            st = conn.prepareStatement(query.toString());
            rs = st.executeQuery();
            if (rs.next()) {
//            	id = rs.getLong(1) + 1;
            }else {
//            	id = 1L;
            }
		} catch (SQLException e) {
			System.out.println("  query fail");
		} finally {
			JdbcConnection.free(rs, st, conn);
		}
		return null;
	}

}
