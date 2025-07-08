package com.sino.base.db.entity;

public class DBEntity {
	private String table_schema;    //数据库名
	private String engine;          //
	private String table_name;      //表名称
	private String table_rows;      //
	private String avg_row_length;  //
	private String table_comment;   //
	public String getTable_schema() {
		return table_schema;
	}
	public void setTable_schema(String table_schema) {
		this.table_schema = table_schema;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getTable_rows() {
		return table_rows;
	}
	public void setTable_rows(String table_rows) {
		this.table_rows = table_rows;
	}
	public String getAvg_row_length() {
		return avg_row_length;
	}
	public void setAvg_row_length(String avg_row_length) {
		this.avg_row_length = avg_row_length;
	}
	public String getTable_comment() {
		return table_comment;
	}
	public void setTable_comment(String table_comment) {
		this.table_comment = table_comment;
	}
	
}
