/**
 * Copyright By 2019 HuatengBase Co. Ltd.
 * All right reserved.
 */
package org.springside.modules.columnbase;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

import static java.lang.Class.forName;


/**
 * @author zhanghj
 */
public class DBCPUtil {

	public static Connection conn = null;
	public static Properties dbprops = null;
	public static String url = "";
	public static String driver = "";
	public static String username = "";
	public static String password = "";


	static {
		Properties properties = new Properties();
		Optional<BufferedInputStream> fileInputStream = Optional.empty();
		String hikariConfig =DBCPUtil.class.getResource("/").getPath() + "../conf" + File.separator + Constant.DatabaseInfo.FILENAME;
		Path path = Paths.get(hikariConfig);

		if (!Files.exists(path)) {
			URL resource = Class.class.getResource("/conf/" + Constant.DatabaseInfo.FILENAME);
			if (resource == null) {
				resource = Class.class.getResource(Constant.DatabaseInfo.FILENAME);
			}
			hikariConfig = resource.getPath();
		}
		try {
			fileInputStream = Optional.of(new BufferedInputStream(new FileInputStream(hikariConfig)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		fileInputStream.ifPresent(inStream -> {
			try {
				properties.load(inStream);
				url = properties.getProperty(Constant.DatabaseInfo.URL);
				driver = properties.getProperty(Constant.DatabaseInfo.DRIVER);
				username = properties.getProperty(Constant.DatabaseInfo.USERNAME);
				password = properties.getProperty(Constant.DatabaseInfo.PASSWORD);
				try {
					System.out.println("driver is:" + driver);
					forName(driver);
					System.out.println("load driver success");
				} catch (ClassNotFoundException e) {
					System.out.println("load driver fail" + e.toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

//		try {
//			dbprops = AppProperties.loadPropertiesFromClassPath(Constant.DatabaseInfo.FILENAME);
//			url = dbprops.getProperty(Constant.DatabaseInfo.URL);
//			driver = dbprops.getProperty(Constant.DatabaseInfo.DRIVER);
//			username = dbprops.getProperty(Constant.DatabaseInfo.USERNAME);
//			password = dbprops.getProperty(Constant.DatabaseInfo.PASSWORD);
//			try {
//				System.out.println("driver is:" + driver);
//				forName(driver);
//				System.out.println("load driver success");
//			} catch (ClassNotFoundException e) {
//				System.out.println("load driver fail" + e.toString());
//			}
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}


	/**
	 * 连接
	 *
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}


	/*
	 * 导入csv文件 到columnbase
	 * 命令行导入（性能高，每秒200万数据）
	 * csv文件用"|"分割导入命令：columnbase-client --database xflowdb --format_csv_delimiter="|" --query "INSERT INTO baseflowrawdata FORMAT CSV" < 1.csv
	 * csv文件默认逗号分割导入命令：columnbase-client --database xflowdb  --query "INSERT INTO baseflowrawdata FORMAT CSV" < 1.csv
	 *
	 * 命令中：xflowdb是要插入的数据库名，baseflowrawdata 是表名、1.csv是csv文件名，可以包含路径
	 */
	public static void importCsvData(String cmd) {
		int execResult = exeLinuxCmd.execLinuxCmd(cmd);
	}


	/**
	 * insert 方法
	 *
	 * @param insertsql 建表语句：CREATE TABLE IF NOT EXISTS `test` (`id` Int16 ,`name` String ) ENGINE=CrossEngine ORDER BY (id);
	 *                  insert into test (id,name) values(?,?);
	 */

	public static void insertData(String insertsql) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(insertsql);
			pstmt.setInt(1, 1);
			pstmt.setString(2, "张三");
			pstmt.execute();
			System.out.println("insert sucess");
		} catch (SQLException e) {
			System.out.println("insert fail" + e.toString());
		} finally {
			closepstmt(pstmt);
			closeConn(conn);
		}
	}


	/**
	 * 不支持delete删除,支持truncate table test;
	 *
	 * @param delesql
	 */

	public static void truncateData(String delesql) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(delesql);
			pstmt.executeUpdate();
			System.out.println("truncate sucess");
		} catch (SQLException e) {
			System.out.println("truncate fail" + e.toString());
		} finally {
			closepstmt(pstmt);
			closeConn(conn);
		}
	}

	//
	//	/**
	//	 *不支持 更新
	//	 * @param updatesql
	//	 * update test set name='李四'  where id=1
	//	 */
	//
	//	public static void updateData(String updatesql) {
	//		Connection conn=null;
	//		PreparedStatement  pstmt=null;
	//		try {
	//		 conn=getConnection();
	//	     pstmt=conn.prepareStatement(updatesql);
	//	        pstmt.executeUpdate();
	//			System.out.println("update sucess");
	//		} catch (SQLException e) {
	//			System.out.println("update fail"+e.toString());
	//		}finally {
	//			closepstmt(pstmt);
	//			closeConn(conn);
	//		}
	//	}


	/**
	 * 查询方法
	 *
	 * @param querysql
	 */
	public static void queryData(String querysql) {
		ResultSet rs = null;
		Connection conn = null;
		Statement st = null;

		try {
			conn = getConnection();
			System.out.println("connection sucess");
			st = conn.createStatement();
			rs = st.executeQuery(querysql);
			while (rs.next()) {
				System.out.println("query sucess");
			}
		} catch (SQLException e) {
			System.out.println("  query fail");
		} finally {
			closeRs(rs);
			closeSt(st);
			closeConn(conn);
		}
	}


	//	public static void main(String[] args) {
	//		 //查询
	//		DBCPUtil.queryData("select * from test");
	////		//插入
	////		//insertData("insert into test (id,name) values(?,?)");
	//
	////
	////		//删除
	//
	//		//truncateData("truncate table test");
	//
	//		//导入csv文件，csv默认是逗号分割的
	//		importcsvdata("columnbase-client --database xflowdb  --query \"INSERT INTO baseflowrawdata FORMAT CSV\" </huatengbase/1.csv");
	//
	//
	//
	//		//导入csv文件，csv是指定分割符"|"分割的
	//	//	importcsvdata("columnbase-client --database xflowdb --format_csv_delimiter=\"|\" --query \"INSERT INTO baseflowrawdata FORMAT CSV\" <1.csv");
	//
	//	}


	public static void release(Connection conn, Statement st, ResultSet rs) {
		closeRs(rs);
		closeSt(st);
		closeConn(conn);
	}

	public static void release(Connection conn, Statement st) {
		closeSt(st);
		closeConn(conn);
	}


	private static void closepstmt(PreparedStatement rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs = null;
		}
	}

	private static void closeSt(Statement st) {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			st = null;
		}
	}

	private static void closeConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn = null;
		}

	}

	private static void closeRs(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs = null;
		}
	}

}