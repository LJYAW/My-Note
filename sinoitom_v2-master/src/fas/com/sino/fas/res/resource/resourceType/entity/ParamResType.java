/*
 * 文件名： ParamResType.java
 * 
 * 创建日期： 2014-1-15
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.fas.res.resource.resourceType.entity;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.2 $
 *
 * @since 2014-1-15
 */
public class ParamResType {
	private String parentId;          //varchar     (36) NULL父节点ID
	private String classID;          //varchar     (36) NOT NULL类型节点ID
	private String treeCode;          //varchar     (64) NOT NULL树节点编码
	private Integer nodeFlag;          //int(1) NOT NULL节点标志（缺省为0：叶子节点；1：分支节点）
	private Integer privilegeLevel;          //int(1) NULL分类标志(1:系统分类;2:用户分类)
	private String dispName;          //varchar     (32) NOT NULL显示名称
	private String className;          //varchar     (64) NOT NULL类型名称
	private String description;          //varchar     (64) NULL类型说明
	private Integer objectType;          //int(1) NULL对象类型
	private Integer resTypeCode;
	private String objectName;          //varchar     (64) NULL对象名（数据库表）
	private Integer objectCount;          //int(3) NULL对象数量
	private String remark;          //varchar     (128) NULL备注
	private String icon;
	private boolean isexpand=false;
	
	public boolean isIsexpand() {
		return isexpand;
	}
	public void setIsexpand(boolean isexpand) {
		this.isexpand = isexpand;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getClassID() {
		return classID;
	}
	public void setClassID(String classID) {
		this.classID = classID;
	}
	public String getTreeCode() {
		return treeCode;
	}
	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}
	public Integer getNodeFlag() {
		return nodeFlag;
	}
	public void setNodeFlag(Integer nodeFlag) {
		this.nodeFlag = nodeFlag;
	}
	public Integer getPrivilegeLevel() {
		return privilegeLevel;
	}
	public void setPrivilegeLevel(Integer privilegeLevel) {
		this.privilegeLevel = privilegeLevel;
	}
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getObjectType() {
		return objectType;
	}
	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public Integer getObjectCount() {
		return objectCount;
	}
	public void setObjectCount(Integer objectCount) {
		this.objectCount = objectCount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getResTypeCode() {
		return resTypeCode;
	}
	public void setResTypeCode(Integer resTypeCode) {
		this.resTypeCode = resTypeCode;
	}
	
	
}
