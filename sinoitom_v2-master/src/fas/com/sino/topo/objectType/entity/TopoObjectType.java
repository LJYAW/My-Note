package com.sino.topo.objectType.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Topo_ObjectType")
public class TopoObjectType {

	private String objTypeID;
	private Integer objType;
	private String objName;
	private String objIcon;
	private Integer classCode;
	private String classEnName;
	private String className;
	private Integer typeCode;
	private String typeName;
	private String description;
	private Integer status;	
	
	@Id
	@Column(name = "ObjTypeID", unique = true, nullable = false)
	public String getObjTypeID() {
		return objTypeID;
	}
	public void setObjTypeID(String objTypeID) {
		this.objTypeID = objTypeID;
	}
	
	@Column(name = "ObjType")
	public Integer getObjType() {
		return objType;
	}
	public void setObjType(Integer objType) {
		this.objType = objType;
	}
	
	@Column(name = "ObjName")
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	
	@Column(name = "ObjIcon")
	public String getObjIcon() {
		return objIcon;
	}
	public void setObjIcon(String objIcon) {
		this.objIcon = objIcon;
	}
	
	@Column(name = "ClassCode")
	public Integer getClassCode() {
		return classCode;
	}
	public void setClassCode(Integer classCode) {
		this.classCode = classCode;
	}
	
	@Column(name = "ClassEnName")
	public String getClassEnName() {
		return classEnName;
	}
	public void setClassEnName(String classEnName) {
		this.classEnName = classEnName;
	}
	
	@Column(name = "ClassName")
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	@Column(name = "TypeCode")
	public Integer getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}
	
	@Column(name = "TypeName")
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Column(name = "Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
}
