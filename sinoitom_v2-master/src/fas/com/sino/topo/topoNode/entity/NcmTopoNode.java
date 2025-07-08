package com.sino.topo.topoNode.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ncm_TopoNode")
public class NcmTopoNode {
	private Long container_ID;                   //big;                   //int(19)ContainerID
	private Long node_ID;                   //big;                   //int(19)NodeID (来自资源表的ID)
	private Integer nodeClassCode;                   //int(1)
	private String nodeClassName;                   //varchar(32)
	private Integer nodeTypeCode;                   //int(1)节点可以是设备或虚拟云图, 设备图标将根据typecode定义
	private String nodeTypeName;                   //varchar(32)
	private String node_Name;                   //varchar(64)Node Name
	private String nodeIp;                   //varchar(15)
	private String node_Desc;                   //varchar(255)Node Description
	
	private String nodeIcon;
	private Integer longitude;                   //int(10)经度(Y)
	private Integer latitude;                   //int(10)纬度(X)
	private Integer admin_Status;                   //int(11)
	private Integer oper_Status;                   //int(10)
	private String creator;                   //varchar(32)
	private Long create_Time;                   //big;                   //int(19)
	private String modifier;                   //varchar(32)
	private Long modify_Time;                   //big;                   //int(19)
	
	
	@Column(name = "Container_ID")
	public Long getContainer_ID() {
		return container_ID;
	}
	public void setContainer_ID(Long container_ID) {
		this.container_ID = container_ID;
	}
	
	@Id
	@Column(name = "Node_ID", unique = true, nullable = false)
	public Long getNode_ID() {
		return node_ID;
	}
	public void setNode_ID(Long node_ID) {
		this.node_ID = node_ID;
	}
	@Column(name = "NodeClassCode")
	public Integer getNodeClassCode() {
		return nodeClassCode;
	}
	public void setNodeClassCode(Integer nodeClassCode) {
		this.nodeClassCode = nodeClassCode;
	}
	@Column(name = "NodeClassName")
	public String getNodeClassName() {
		return nodeClassName;
	}
	public void setNodeClassName(String nodeClassName) {
		this.nodeClassName = nodeClassName;
	}
	@Column(name = "NodeTypeCode")
	public Integer getNodeTypeCode() {
		return nodeTypeCode;
	}
	public void setNodeTypeCode(Integer nodeTypeCode) {
		this.nodeTypeCode = nodeTypeCode;
	}
	@Column(name = "NodeTypeName")
	public String getNodeTypeName() {
		return nodeTypeName;
	}
	public void setNodeTypeName(String nodeTypeName) {
		this.nodeTypeName = nodeTypeName;
	}
	@Column(name = "Node_Name")
	public String getNode_Name() {
		return node_Name;
	}
	public void setNode_Name(String node_Name) {
		this.node_Name = node_Name;
	}
	@Column(name = "NodeIp")
	public String getNodeIp() {
		return nodeIp;
	}
	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}
	@Column(name = "Node_Desc")
	public String getNode_Desc() {
		return node_Desc;
	}
	public void setNode_Desc(String node_Desc) {
		this.node_Desc = node_Desc;
	}
	@Column(name = "Longitude")
	public Integer getLongitude() {
		return longitude;
	}
	public void setLongitude(Integer longitude) {
		this.longitude = longitude;
	}
	@Column(name = "Latitude")
	public Integer getLatitude() {
		return latitude;
	}
	public void setLatitude(Integer latitude) {
		this.latitude = latitude;
	}
	@Column(name = "admin_Status")
	public Integer getAdmin_Status() {
		return admin_Status;
	}
	public void setAdmin_Status(Integer admin_Status) {
		this.admin_Status = admin_Status;
	}
	@Column(name = "Oper_Status")
	public Integer getOper_Status() {
		return oper_Status;
	}
	public void setOper_Status(Integer oper_Status) {
		this.oper_Status = oper_Status;
	}
	@Column(name = "Creator")
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name = "Create_Time")
	public Long getCreate_Time() {
		return create_Time;
	}
	public void setCreate_Time(Long create_Time) {
		this.create_Time = create_Time;
	}
	@Column(name = "Modifier")
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	@Column(name = "Modify_Time")
	public Long getModify_Time() {
		return modify_Time;
	}
	public void setModify_Time(Long modify_Time) {
		this.modify_Time = modify_Time;
	}
	
	@Column(name = "NodeIcon")
	public String getNodeIcon() {
		return nodeIcon;
	}
	public void setNodeIcon(String nodeIcon) {
		this.nodeIcon = nodeIcon;
	}
	
	
}
