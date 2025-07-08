package com.sino.fas.res.db.mysqlPerformance.entity;

//执行SQL语句得到的结果
public class SqlCommandResult {
	private String indicatorId;//主键id
	private String commandId;//命令行id
	private String variableName;//指标名称
	private String value;//指标值
	private String remarks;//指标值
	
	public String getIndicatorId() {
		return indicatorId;
	}
	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}
	public String getCommandId() {
		return commandId;
	}
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "SqlCommandResult [indicatorId=" + indicatorId + ", commandId="
				+ commandId + ", variableName=" + variableName + ", value="
				+ value + "]";
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
