package com.sino.base.db.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sino.base.common.Global;
import com.sino.base.common.util.JdbcConnection;
import com.sino.base.db.entity.DBEntity;
import com.sino.base.db.entity.TableInfo;
@Component
public class QueryDB{
	JdbcConnection conn=new JdbcConnection();
	public List<DBEntity> queryDB() {
		Connection con = conn.getConnection();
		List<DBEntity> list=new ArrayList<DBEntity>();
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT TABLE_SCHEMA,ENGINE,TABLE_NAME, TABLE_ROWS,AVG_ROW_LENGTH,TABLE_COMMENT from tables where TABLE_SCHEMA='"+Global.DBNAME+"' order by TABLE_NAME;");
		try {
			stmt = con.createStatement();
			stmt.executeQuery("use information_schema;");
			rs = stmt.executeQuery(sql.toString());
			while(rs.next()){
				DBEntity dbEntity=new DBEntity();
				dbEntity.setTable_schema(rs.getString("TABLE_SCHEMA"));
				dbEntity.setEngine(rs.getString("ENGINE"));
				dbEntity.setTable_name(rs.getString("TABLE_NAME"));
				dbEntity.setTable_rows(rs.getString("TABLE_ROWS"));
				dbEntity.setAvg_row_length(rs.getString("AVG_ROW_LENGTH"));
				dbEntity.setTable_comment(rs.getString("TABLE_COMMENT"));
				list.add(dbEntity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcConnection.free(rs, stmt, con);
		}
		return list;
	}
	public List<TableInfo> queryTable(String dbName,String tableName) {
		Connection con = conn.getConnection();
		List<TableInfo> list=new ArrayList<TableInfo>();
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		String sql="select COLUMN_NAME,COLUMN_TYPE,COLUMN_KEY,COLUMN_DEFAULT,IS_NULLABLE,COLUMN_COMMENT from columns where TABLE_SCHEMA=? and Table_Name=? order by ORDINAL_POSITION;";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dbName);
			pstmt.setString(2, tableName);
			pstmt.executeQuery("use information_schema;");
			rs = pstmt.executeQuery();
			while(rs.next()){
				TableInfo tableInfo=new TableInfo();
				tableInfo.setColumn_name(rs.getString(1));
				tableInfo.setColumu_type(rs.getString(2));
				tableInfo.setColumn_key(rs.getString(3));
				tableInfo.setColumn_default(rs.getString(4));
				tableInfo.setIs_nullable(rs.getString(5));
				tableInfo.setColumn_comment(rs.getString(6));
				list.add(tableInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcConnection.free(rs, pstmt, con);
		} 
		return list;
	}
	
	public static void main(String[] args) {
		JdbcConnection conn=new JdbcConnection();
		
	}
}
