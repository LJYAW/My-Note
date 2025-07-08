package com.sino.cmdb.graph.web;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class DBUtil {

	/**
	 * @param args
	 */
	// 
	public static long getUniqResId()
    {
        long id = 1L;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

        StringBuffer query = new StringBuffer().append("select max(Unq_ID) from IDGen");
        try
        {
            if(conn == null || conn.isClosed())
            	conn = JdbcConnection.getInstance().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query.toString());
            if (rs.next()) {
            	id = rs.getLong(1) + 1;
            }else {
            	id = 1L;
            }

            query = new StringBuffer().append("insert into IDGen values(").append(id).append(")");
            stmt.execute(query.toString());

            if(rs != null)
            {
                rs.close();
                rs = null;
            }

            if(stmt != null)
            {
                stmt.close();
                stmt = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try
            {
                if(rs != null)
                {
                    rs.close();
                    rs = null;
                }

                if(stmt != null)
                {
                    stmt.close();
                    stmt = null;
                }
            }
            catch(Exception ex1)
            {
                ex1.printStackTrace();
            }
        }
        return id;
    }
}
