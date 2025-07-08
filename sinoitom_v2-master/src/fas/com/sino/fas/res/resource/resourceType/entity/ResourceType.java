/*
 * 文件名： ResourceType.java
 * 
 * 创建日期： 2013-12-21
 *
 * Copyright(C) 2013, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.fas.res.resource.resourceType.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.7 $
 *
 * @since 2013-12-21
 */

@Entity
@Table(name = "Res_ClassifyTree",uniqueConstraints = @UniqueConstraint(columnNames = "ClassID"))
public class ResourceType {
	private String parentId;          //varchar     (36) NULL父节点ID
	private String classID;          //varchar     (36) NOT NULL类型节点ID
	private String treeCode;          //varchar     (64) NOT NULL树节点编码
	private Integer nodeFlag;          //int(1) NOT NULL节点标志（缺省为0：叶子节点；1：分支节点）
	private Integer privilegeLevel;          //int(1) NULL权限级别(1:系统级别;2:用户级别)
	private String dispName;          //varchar     (32) NOT NULL显示名称
	private String className;          //varchar     (64) NOT NULL类型名称
	private String description;          //varchar     (64) NULL类型说明
	private Integer objectType;          //int(1) NULL对象类型
	private String objectName;          //varchar     (64) NULL对象名（数据库表）
	private Integer objectCount;          //int(3) NULL对象数量
	private String remark;          //varchar     (128) NULL备注
	private String icon;            //varchar     (128) NULL图标
	private Integer expand;
	private Integer resClassCode;
	private String resClassName;
	private Integer resTypeCode;
	private String resTypeName;
	
	
	@Column(name = "Expand")
	public Integer getExpand() {
		return expand;
	}
	public void setExpand(Integer expand) {
		this.expand = expand;
	}
	@Column(name = "ClassIcon")
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Column(name = "ParentID")
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Id
	@Column(name = "ClassID", unique = true, nullable = false)
	public String getClassID() {
		return classID;
	}
	
	public void setClassID(String classID) {
		this.classID = classID;
	}
	
	@Column(name = "TreeCode")
	public String getTreeCode() {
		return treeCode;
	}
	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}
	
	@Column(name = "NodeFlag")
	public Integer getNodeFlag() {
		return nodeFlag;
	}
	public void setNodeFlag(Integer nodeFlag) {
		this.nodeFlag = nodeFlag;
	}
	
	@Column(name = "PrivilegeLevel")
	public Integer getPrivilegeLevel() {
		return privilegeLevel;
	}
	public void setPrivilegeLevel(Integer privilegeLevel) {
		this.privilegeLevel = privilegeLevel;
	}
	
	@Column(name = "DispName")
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	
	@Column(name = "ClassName")
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	@Column(name = "Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "ObjectType")
	public Integer getObjectType() {
		return objectType;
	}
	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
	
	@Column(name = "ObjectName")
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
	@Column(name = "ObjectCount")
	public Integer getObjectCount() {
		return objectCount;
	}
	public void setObjectCount(Integer objectCount) {
		this.objectCount = objectCount;
	}
	
	@Column(name = "Remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "ResClassName")
	public String getResClassName() {
		return resClassName;
	}
	public void setResClassName(String resClassName) {
		this.resClassName = resClassName;
	}
	
	@Column(name = "ResTypeName")
	public String getResTypeName() {
		return resTypeName;
	}
	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}
	
	@Column(name = "ResClassCode")
	public Integer getResClassCode() {
		return resClassCode;
	}
	public void setResClassCode(Integer resClassCode) {
		this.resClassCode = resClassCode;
	}
	
	@Column(name = "ResTypeCode")
	public Integer getResTypeCode() {
		return resTypeCode;
	}
	public void setResTypeCode(Integer resTypeCode) {
		this.resTypeCode = resTypeCode;
	}
	
	
}
