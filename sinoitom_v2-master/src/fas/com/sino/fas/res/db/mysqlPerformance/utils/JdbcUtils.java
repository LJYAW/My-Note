package com.sino.fas.res.db.mysqlPerformance.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sino.base.common.util.JdbcConnection;

public class JdbcUtils {
	private static JdbcUtils dbquery = null;
	
	private String dbDriver;
	private String dbURL;
	private String userName;
	private String passWord;
	
	private JdbcUtils(String dbDriver,String dbURL,String userName,String passWord) {
		this.dbDriver = dbDriver;
		this.dbURL = dbURL;
		this.userName = userName;
		this.passWord = passWord;
	}

	public static JdbcUtils getInstance(String dbDriver,String dbURL,String userName,String passWord) {
		if (dbquery == null)
			dbquery = new JdbcUtils(dbDriver,dbURL,userName,passWord);
		return dbquery;
	}
	public static JdbcUtils getNewInstance(String dbDriver,String dbURL,String userName,String passWord) {
		return new JdbcUtils(dbDriver,dbURL,userName,passWord);
	}


	public Connection getConnection() {
		Connection conn = null; // 表示数据库的连接的对象
		try {
			// 1、使用Class类加载驱动程序
			Class.forName(this.dbDriver);
			// 2、连接数据库
			conn = DriverManager.getConnection(this.dbURL, this.userName, this.passWord);
			// 3、Statement接口需要通过Connection接口进行实例化操作
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
//	释放资源
    public void free(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close(); // 关闭结果集
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close(); // 关闭Statement
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close(); // 关闭连接
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) {
    	Connection conn=JdbcConnection.getInstance().getConnection();
    	JdbcConnection conn1=new JdbcConnection();
    	Connection conn2=conn1.getConnection();
	}
}
