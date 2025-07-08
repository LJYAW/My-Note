package com.sino.cmdb.graph.web;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class JdbcConnection {
	public static String dbDriver = "com.mysql.jdbc.Driver";
	// 连接地址是由各个数据库生产商单独提供的，所以需要单独记住
	public static String dbURL = "jdbc:mysql://192.168.99.16:13306/itomdb?useUnicode=true&characterEncoding=utf8";
//	public static String dbURL = "jdbc:mysql://127.0.0.1:3306/itomdb?useUnicode=true&characterEncoding=utf8";
	// 连接数据库的用户名
	public static String userName = "root";
	// 连接数据库的密码
	public static String passWord = "";
	private static JdbcConnection dbConn = null;
	
	public JdbcConnection() {
		getConnection();
	}

	public static JdbcConnection getInstance() {
		if (dbConn == null)
			dbConn = new JdbcConnection();
		return dbConn;
	}


	public Connection getConnection() {
		
		String urlFile = "/application.properties";
		InputStream is=this.getClass().getResourceAsStream(urlFile);
		if (is != null) {
			try {
				Properties props = new Properties();
				props.load(is);
				if (props != null) {
					dbDriver = props.getProperty("jdbc.driver")!=null?props.getProperty("jdbc.driver"):dbDriver;
					userName = props.getProperty("jdbc.username")!=null?props.getProperty("jdbc.username"):userName;
					passWord = props.getProperty("jdbc.password")!=null?props.getProperty("jdbc.password"):passWord;
					dbURL = props.getProperty("jdbc.url")!=null?props.getProperty("jdbc.url"):dbURL;
				}

			} catch (IOException ex) {
				System.out.println("failed...");
			}
		}

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
	
	
    public static void free(ResultSet rs, Statement st, Connection conn) {
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
    	System.out.println("conn=" + conn.toString());
    	if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
