package com.sino.fas.res.db.mysqlPerformance.entity;

//MySQL命令行：SQL语句
public class SqlCommand {
	
	private String commandId;//主键id
	private String command;//命令行
	private String mysqlVersion;//MySQL的版本
	private String description;//描述
	
	public String getCommandId() {
		return commandId;
	}
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getMysqlVersion() {
		return mysqlVersion;
	}
	public void setMysqlVersion(String mysqlVersion) {
		this.mysqlVersion = mysqlVersion;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "SqlCommand [commandId=" + commandId + ", command=" + command
				+ ", mysqlVersion=" + mysqlVersion + ", description="
				+ description + "]";
	}
	
}
