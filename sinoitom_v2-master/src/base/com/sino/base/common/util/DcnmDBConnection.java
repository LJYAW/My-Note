package com.sino.base.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DcnmDBConnection {
	public static String dbDriver = "org.postgresql.Driver";
	// 连接地址是由各个数据库生产商单独提供的，�?���?��单独记住
	public static String dbURL = "jdbc:postgresql://192.168.99.178:5432/dcmdb?useUnicode=true&characterEncoding=utf8";
	// 连接数据库的用户�?
	public static String userName = "dcnmuser";
	// 连接数据库的密码
	public static String passWord = "admin123";
	private static DcnmDBConnection dbquery = null;
	
	public DcnmDBConnection() {
		//initJdbcUrl();
		getConnection();
	}

	public static DcnmDBConnection getInstance() {
		if (dbquery == null)
			dbquery = new DcnmDBConnection();
		return dbquery;
	}


	public Connection getConnection() {
//		Properties props = null;
//		String urlFile = "../conf/application.properties";
//		try {
//			props = PropertiesLoader.loadProperties(urlFile);
//			if (props != null) {
//				dbDriver = props.getProperty("jdbc.driver")!=null?props.getProperty("jdbc.driver"):dbDriver;
//				userName = props.getProperty("jdbc.username")!=null?props.getProperty("jdbc.username"):userName;;
//				passWord = props.getProperty("jdbc.password")!=null?props.getProperty("jdbc.password"):passWord;
//				dbURL = props.getProperty("jdbc.url")!=null?props.getProperty("jdbc.url"):dbURL;
//			}

//		} catch (IOException ex) {
//			System.out.println("failed...");
//		}
		
		Connection conn = null; // 表示数据库的连接的对�?
		try {
			// 1、使用Class类加载驱动程�?
			Class.forName(dbDriver);
			// 2、连接数据库
			conn = DriverManager.getConnection(dbURL, userName, passWord);
			// 3、Statement接口�?��通过Connection接口进行实例化操�?
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	
    public static void free(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close(); // 关闭结果�?
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
    
    
    public static void main(String[] args) throws UnknownHostException {
    	DcnmDBConnection conn=new DcnmDBConnection();
    	Connection con = conn.getConnection();
    	Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from ethswitch";
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while(rs.next()){
				
				String strIp = InetAddress.getByAddress(rs.getBytes("ip_address")).toString().replace('/', ' ');
				System.out.println(strIp);
				System.out.println(rs.getString("type"));
				System.out.println(rs.getString("model_name"));
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DcnmDBConnection.free(rs, stmt, con);
		}
		
		
	}
}
