/*
 * 文件名： TopoParam.java
 * 
 * 创建日期： 2015-1-27
 *
 * Copyright(C) 2015, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.cmdb.graph.entity;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision$
 *
 * @since 2015-1-27
 */
public class TopoParam {
	private String node_ID;                   //big;                   //int(19)NodeID (来自资源表的ID)
	private String node_Name;                   //varchar(64)Node Name\
	private String nodeText_ID;
	private String nodeInfo;
	private String nodeText;
	private String link_ID;
	private String line_ID;
	private String lineText;
	private String topoType;
	private Double width;
	private Double height;
	private Integer posX;                   //int(10)经度(Y)
	private Integer posY;                   //int(10)纬度(X)
	
	private Integer namePosX;
	private Integer namePosY;
	
	public String getLink_ID() {
		return link_ID;
	}
	public void setLink_ID(String link_ID) {
		this.link_ID = link_ID;
	}
	public String getNode_ID() {
		return node_ID;
	}
	public void setNode_ID(String node_ID) {
		this.node_ID = node_ID;
	}
	public String getNode_Name() {
		return node_Name;
	}
	public void setNode_Name(String node_Name) {
		this.node_Name = node_Name;
	}
	public String getNodeText_ID() {
		return nodeText_ID;
	}
	public void setNodeText_ID(String nodeText_ID) {
		this.nodeText_ID = nodeText_ID;
	}
	public String getNodeText() {
		return nodeText;
	}
	public void setNodeText(String nodeText) {
		this.nodeText = nodeText;
	}
	public String getLine_ID() {
		return line_ID;
	}
	public void setLine_ID(String line_ID) {
		this.line_ID = line_ID;
	}
	public String getLineText() {
		return lineText;
	}
	public void setLineText(String lineText) {
		this.lineText = lineText;
	}
	public String getTopoType() {
		return topoType;
	}
	public void setTopoType(String topoType) {
		this.topoType = topoType;
	}
	
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Integer getPosX() {
		return posX;
	}
	public void setPosX(Integer posX) {
		this.posX = posX;
	}
	public Integer getPosY() {
		return posY;
	}
	public void setPosY(Integer posY) {
		this.posY = posY;
	}
	public Integer getNamePosX() {
		return namePosX;
	}
	public void setNamePosX(Integer namePosX) {
		this.namePosX = namePosX;
	}
	public Integer getNamePosY() {
		return namePosY;
	}
	public void setNamePosY(Integer namePosY) {
		this.namePosY = namePosY;
	}
	public String getNodeInfo() {
		return nodeInfo;
	}
	public void setNodeInfo(String nodeInfo) {
		this.nodeInfo = nodeInfo;
	}
	
	
	
	
}
