package com.sino.fas.res.db.mysqlPerformance.entity;

//指标组
public class DetectionIndicatorGroup {
	private String Id;//主键id
	private String groupName;//名称
	private String description;//描述
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
