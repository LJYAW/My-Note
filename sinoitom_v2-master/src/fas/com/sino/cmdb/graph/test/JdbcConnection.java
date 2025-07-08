package com.sino.cmdb.graph.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcConnection {
	private String dbDriver = "com.mysql.jdbc.Driver";
	// 连接地址是由各个数据库生产商单独提供的，所以需要单独记住
	private String dbURL = "jdbc:mysql://192.168.99.160:13306/acsdb?useUnicode=true&characterEncoding=utf8";
	// 连接数据库的用户名
	private String userName = "root";
	// 连接数据库的密码
	private String passWord = "";
	private static JdbcConnection dbquery = null;
	
	public JdbcConnection() {
		getConnection();
	}

//	获取连接
	public static JdbcConnection getInstance() {
		if (dbquery == null)
			dbquery = new JdbcConnection();
		return dbquery;
	}

	public Connection getConnection() {
		Connection conn = null; // 表示数据库的连接的对象
		try {
			// 1、使用Class类加载驱动程序
			Class.forName(dbDriver);
			// 2、连接数据库
			conn = DriverManager.getConnection(dbURL, userName, passWord);
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
}
