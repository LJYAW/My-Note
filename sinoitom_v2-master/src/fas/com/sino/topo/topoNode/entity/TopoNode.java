package com.sino.topo.topoNode.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Topo_Node")
public class TopoNode {
	
	private Long graphId;
	private Long nodeId;
	private Integer nodeType;
	private String nodeName;
	private Integer posX;
	private Integer posY;
	private Integer namePosX;
	private Integer namePosY;
	private Double width;
	private Double height;
	private String objTypeId;
	private Integer objType;
	private String objIcon;
	private Integer devClassCode;
	private String devClassName;
	private Integer devTypeCode;
	private String devTypeName;
	private Long devID;
	private Long devIpLong;
	private String devIpAddr;
	private Integer devStatus;
	private Integer flag;
	private String remark;
	
	@Column(name = "GraphId")
	public Long getGraphId() {
		return graphId;
	}
	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}
	
	@Id
	@Column(name = "NodeId")
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	
	@Column(name = "NodeType")
	public Integer getNodeType() {
		return nodeType;
	}
	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}
	
	@Column(name = "NodeName")
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	@Column(name = "PosX")
	public Integer getPosX() {
		return posX;
	}
	public void setPosX(Integer posX) {
		this.posX = posX;
	}
	@Column(name = "PosY")
	public Integer getPosY() {
		return posY;
	}
	public void setPosY(Integer posY) {
		this.posY = posY;
	}
	
	@Column(name = "ObjTypeId")
	public String getObjTypeId() {
		return objTypeId;
	}
	public void setObjTypeId(String objTypeId) {
		this.objTypeId = objTypeId;
	}
	
	
	
	@Column(name = "ObjType")
	public Integer getObjType() {
		return objType;
	}
	public void setObjType(Integer objType) {
		this.objType = objType;
	}
	@Column(name = "ObjIcon")
	public String getObjIcon() {
		return objIcon;
	}
	public void setObjIcon(String objIcon) {
		this.objIcon = objIcon;
	}
	@Column(name = "DevClassCode")
	public Integer getDevClassCode() {
		return devClassCode;
	}
	public void setDevClassCode(Integer devClassCode) {
		this.devClassCode = devClassCode;
	}
	
	@Column(name = "Flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	@Column(name = "Remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "Width")
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	
	@Column(name = "Height")
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	
	@Column(name = "DevTypeCode")
	public Integer getDevTypeCode() {
		return devTypeCode;
	}
	public void setDevTypeCode(Integer devTypeCode) {
		this.devTypeCode = devTypeCode;
	}
	
	@Column(name = "DevID")
	public Long getDevID() {
		return devID;
	}
	public void setDevID(Long devID) {
		this.devID = devID;
	}
	
	@Column(name = "DevIpLong")
	public Long getDevIpLong() {
		return devIpLong;
	}
	public void setDevIpLong(Long devIpLong) {
		this.devIpLong = devIpLong;
	}
	
	@Column(name = "DevIpAddr")
	public String getDevIpAddr() {
		return devIpAddr;
	}
	public void setDevIpAddr(String devIpAddr) {
		this.devIpAddr = devIpAddr;
	}
	
	@Column(name = "DevStatus")
	public Integer getDevStatus() {
		return devStatus;
	}
	public void setDevStatus(Integer devStatus) {
		this.devStatus = devStatus;
	}
	
	@Column(name = "NamePosX")
	public Integer getNamePosX() {
		return namePosX;
	}
	public void setNamePosX(Integer namePosX) {
		this.namePosX = namePosX;
	}
	
	@Column(name = "NamePosY")
	public Integer getNamePosY() {
		return namePosY;
	}
	public void setNamePosY(Integer namePosY) {
		this.namePosY = namePosY;
	}
	
	@Column(name = "DevClassName")
	public String getDevClassName() {
		return devClassName;
	}
	public void setDevClassName(String devClassName) {
		this.devClassName = devClassName;
	}
	
	@Column(name = "DevTypeName")
	public String getDevTypeName() {
		return devTypeName;
	}
	public void setDevTypeName(String devTypeName) {
		this.devTypeName = devTypeName;
	}
	
	
	
	
	
	
	
}
