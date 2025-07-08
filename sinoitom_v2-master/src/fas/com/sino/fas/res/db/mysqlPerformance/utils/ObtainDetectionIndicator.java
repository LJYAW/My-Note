package com.sino.fas.res.db.mysqlPerformance.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springside.modules.utils.Identities;

import com.sino.fas.res.db.entity.ResDataBases;
import com.sino.fas.res.db.mysqlPerformance.entity.DetectionIndicator;
import com.sino.fas.res.db.mysqlPerformance.entity.SqlCommand;
import com.sino.fas.res.db.mysqlPerformance.entity.SqlCommandResult;

//测试类
public class ObtainDetectionIndicator {
	public static void main(String[] args) {
//		1、创建SQL语句
//		List<SqlCommand> sqlCommandList = createSqlCommandByList();
		ReadFile readFile = new ReadFile();
		List<SqlCommand> sqlCommandList = createSqlCommandByFile(readFile);
		
//		2、调用SQL语句，获取执行结果
		List<SqlCommandResult> sqlResultList = getSqlResultByFile(readFile,sqlCommandList);
		
//		3、解析执行结果获取指标参数
		DetectionIndicator detectionIndicator = getDetectionIndicator(sqlResultList);
		
//		4、输出结果
		outputResult(sqlCommandList,sqlResultList,detectionIndicator);
	}
	
//	获取指标结果
	public static DetectionIndicator getDetectionIndicator(ResDataBases dataBese){
//		1、创建SQL语句
//		List<SqlCommand> sqlCommandList = createSqlCommandByList();
		ReadFile readFile = new ReadFile();
		List<SqlCommand> sqlCommandList = createSqlCommandByFile(readFile);
		
//		2、调用SQL语句，获取执行结果
		List<SqlCommandResult> sqlResultList = getSqlResultByObj(dataBese,sqlCommandList);
		
//		3、解析执行结果获取指标参数
		DetectionIndicator detectionIndicator = getDetectionIndicator(sqlResultList); 
		return detectionIndicator;
	}
	
//	输出结果:sqlCommandList、sqlResultList、detectionIndicatorList
	private static void outputResult(List<SqlCommand> sqlCommandList,List<SqlCommandResult> sqlResultList,DetectionIndicator detectionIndicator) {
		
		if(sqlCommandList!=null && sqlCommandList.size()>0){
			System.err.println("sqlCommandList----");
			for (SqlCommand sqlCommand : sqlCommandList) {
				System.out.println(sqlCommand);
			}
			System.out.println();
		}
		
		if(sqlResultList!=null && sqlResultList.size()>0){
			System.err.println("sqlResultList----");
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				System.out.println(sqlCommandResult);
			}
			System.out.println();
		}
		
		System.out.println(detectionIndicator);
		
	}

//	解析SQL结果集，获取指标项
	private static DetectionIndicator getDetectionIndicator(List<SqlCommandResult> sqlResultList) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String currentTime = df.format(new Date());
		DetectionIndicator detectionIndicator = new DetectionIndicator();
		detectionIndicator.setDetectionIndicatorId(Identities.uuid());
		detectionIndicator.setCreateTime(currentTime);
		
		SetValue2DetectionIndicator.setValue(sqlResultList, detectionIndicator);

		return detectionIndicator;
	}

//	调用SQL语句，获取执行结果
	private static List<SqlCommandResult> getSqlResultByFile(ReadFile readFile,List<SqlCommand> sqlCommandList){
		List<SqlCommandResult> sqlResultList = new ArrayList<SqlCommandResult>();
		
//		获取数据库基本信息
		String driver = readFile.getDriver();
		String url = readFile.getUrl();
		String username = readFile.getUsername();
		String password = readFile.getPassword();
		
		Connection conn = null;  
	    PreparedStatement pst = null;  
	    ResultSet rs = null;
	    
//	    	初始化连接属性
	    JdbcUtils jdbcUtils = JdbcUtils.getInstance(driver,url,username,password);
		
		if(StringUtils.isNotBlank(driver) && StringUtils.isNotBlank(url)){
//			String dbDriver,String dbURL,String userName,String passWord
			try {
//				获取连接
				conn = jdbcUtils.getConnection();
				for (SqlCommand sqlCommand : sqlCommandList) {
					if(sqlCommand!=null){
						String command = sqlCommand.getCommand();
						if(StringUtils.isNotBlank(command)){
//							准备执行语句
							pst = conn.prepareStatement(command);
							
//							执行sql查询语句，得到结果集  
							rs = pst.executeQuery();
					        while (rs.next()) {  
					              String variableName = rs.getString(1);  
					              String value = rs.getString(2);  
					              
//					             	 封装结果到SqlCommandResult对象
					              SqlCommandResult result = new SqlCommandResult();
					              result.setCommandId(sqlCommand.getCommandId());
					              result.setIndicatorId(Identities.uuid());
					              result.setVariableName(variableName);
					              result.setValue(value);
					              
					              sqlResultList.add(result);
					        }
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				jdbcUtils.free(rs, pst, conn);
			}
		}
		return sqlResultList;
	}
	private static List<SqlCommandResult> getSqlResultByObj(ResDataBases dataBese,List<SqlCommand> sqlCommandList){
		List<SqlCommandResult> sqlResultList = new ArrayList<SqlCommandResult>();
		
//		获取数据库基本信息
		String driver = dataBese.getJdbcDriver();
		String url = dataBese.getJdbcUrl();
		String username = dataBese.getUserName();
		String password = dataBese.getPassword();
		
		Connection conn = null;  
		PreparedStatement pst = null;  
		ResultSet rs = null;
		
//	    	初始化连接属性
		JdbcUtils jdbcUtils = JdbcUtils.getNewInstance(driver,url,username,password);
		
		if(StringUtils.isNotBlank(driver) && StringUtils.isNotBlank(url)){
//			String dbDriver,String dbURL,String userName,String passWord
			try {
//				获取连接
				conn = jdbcUtils.getConnection();
				for (SqlCommand sqlCommand : sqlCommandList) {
					if(sqlCommand!=null){
						String command = sqlCommand.getCommand();
						if(StringUtils.isNotBlank(command)){
//							准备执行语句
							pst = conn.prepareStatement(command);
							
//							执行sql查询语句，得到结果集  
							rs = pst.executeQuery();
							while (rs.next()) {  
								String variableName = rs.getString(1);  
								String value = rs.getString(2);  
								
//					             	 封装结果到SqlCommandResult对象
								SqlCommandResult result = new SqlCommandResult();
								result.setCommandId(sqlCommand.getCommandId());
								result.setIndicatorId(Identities.uuid());
								result.setVariableName(variableName);
								result.setValue(value);
								
								sqlResultList.add(result);
							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				jdbcUtils.free(rs, pst, conn);
			}
		}
		return sqlResultList;
	}
	
//	创建SQL语句--读取文件创建
	private static List<SqlCommand> createSqlCommandByFile(ReadFile readFile){
		List<SqlCommand> sqlCommandList = readFile.getSqlCommandList();
		return sqlCommandList;
	}
	
//	创建SQL语句--手动创建
	private static List<SqlCommand> createSqlCommandByList(){
		List<SqlCommand> sqlCommandList = new ArrayList<SqlCommand>();
		List<String> sqlStrList = new ArrayList<String>();
		
//		sqlStrList.add("select version();");
		sqlStrList.add("show global status like 'threads_%';");
		sqlStrList.add("show global status like 'max_used_connections';");
		sqlStrList.add("show global status LIKE 'question%';");
		sqlStrList.add("show global status LIKE 'com_commit';");
		sqlStrList.add("show global status like 'com_rollback';");
		sqlStrList.add("show global status like '%lock%';");
		sqlStrList.add("show global status like '%slow%';");
		sqlStrList.add("show global status like 'key_read%';");
		sqlStrList.add("show global status like 'key_blocks_u%';");
		sqlStrList.add("show global status like 'created_tmp%';");
		sqlStrList.add("show global status like 'open%tables%';");
		sqlStrList.add("show global status like 'sort%';");
		sqlStrList.add("show global status like 'open_files';");
		sqlStrList.add("show global status like 'table_locks%';");
		sqlStrList.add("show global status like 'handler_read%';");
		sqlStrList.add("show global status like 'com_select';");
		sqlStrList.add("show status like 'qcache%';");
		sqlStrList.add("show status like 'Aborted_clients';");
		sqlStrList.add("show status like 'Select_full_join';");
		sqlStrList.add("show status like 'Select_scan';");
		sqlStrList.add("show status like 'Key%';");
		sqlStrList.add("show status like '%binlog%';");
		sqlStrList.add("show status like 'innodb_buffer_pool_read%';");
		sqlStrList.add("show variables like 'query_cache%';");
		sqlStrList.add("show variables like 'max_connections';");
		sqlStrList.add("show variables like 'open_files_limit';");
		
		for (String command : sqlStrList) {
			SqlCommand sqlCommand = new SqlCommand();
			sqlCommand.setCommand(Identities.uuid());
			sqlCommand.setCommand(command);
			sqlCommand.setMysqlVersion("5.6.18");
			sqlCommandList.add(sqlCommand);
		}
		return sqlCommandList;
	}

}
