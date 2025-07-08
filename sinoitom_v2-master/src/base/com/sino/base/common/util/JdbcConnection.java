package com.sino.base.common.util;

import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springside.modules.utils.PropertiesLoader;

public class JdbcConnection {
	public static String dbDriver = "com.mysql.jdbc.Driver";
	// 连接地址是由各个数据库生产商单独提供的，所以需要单独记住
	public static String dbURL = "jdbc:mysql://10.1.2.198:13306/unidb?useUnicode=true&characterEncoding=utf8";
	// 连接数据库的用户名
	public static String userName = "root";
	// 连接数据库的密码
	public static String passWord = "";
	private static JdbcConnection dbquery = null;
	
	public JdbcConnection() {
//		initJdbcUrl();
		getConnection();
	}

	public static JdbcConnection getInstance() {
		if (dbquery == null)
			dbquery = new JdbcConnection();
		return dbquery;
	}

	public void initJdbcUrl() {
		Properties props = null;
		String urlFile = "/../conf/application.properties";
		try {
			System.out.println("Database Config File Path is：" + urlFile);
			props = PropertiesLoader.loadProperties(urlFile);
			if (props != null) {
				dbDriver = props.getProperty("jdbc.driver");
				userName = props.getProperty("jdbc.username");
				passWord = props.getProperty("jdbc.password");
				dbURL = props.getProperty("jdbc.url");

				System.out.println("drivename=" + dbDriver);
				System.out.println("user=" + userName);
				System.out.println("passwd=" + passWord);
				System.out.println("url=" + dbURL);
			}

		} catch (IOException ex) {
			System.out.println("failed...");
		}

	}

	public Connection getConnection() {
		Properties props = null;
		String urlFile = "../conf/application.properties";
		try {
			props = PropertiesLoader.loadProperties(urlFile);
			if (props != null) {
				dbDriver = props.getProperty("jdbc.driver")!=null?props.getProperty("jdbc.driver"):dbDriver;
				userName = props.getProperty("jdbc.username")!=null?props.getProperty("jdbc.username"):userName;;
				passWord = props.getProperty("jdbc.password")!=null?props.getProperty("jdbc.password"):passWord;
				dbURL = props.getProperty("jdbc.url")!=null?props.getProperty("jdbc.url"):dbURL;
			}

		} catch (IOException ex) {
			System.out.println("failed...");
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
    	
    	JdbcConnection conn1=new JdbcConnection();
    	
    	Connection conn2=conn1.getConnection();
	}
}
