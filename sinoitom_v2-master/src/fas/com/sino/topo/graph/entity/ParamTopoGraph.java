package com.sino.topo.graph.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



public class ParamTopoGraph implements java.io.Serializable{

	private Long graphId;
	private String graphName;
	private String graphDesc;
	private Double width;
	private Double height;
	private String bgPicture;
	private String seedNodeIp;
//	private String seedNodeIp;
	private Integer flag;
	private Integer status;
	private String creator;
	private Timestamp createTime;
	private String createDate;
	
	public Long getGraphId() {
		return graphId;
	}
	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}
	
	public String getGraphName() {
		return graphName;
	}
	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}
	
	
	public String getGraphDesc() {
		return graphDesc;
	}
	public void setGraphDesc(String graphDesc) {
		this.graphDesc = graphDesc;
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
	
	
	public String getBgPicture() {
		return bgPicture;
	}
	
	public void setBgPicture(String bgPicture) {
		this.bgPicture = bgPicture;
	}
	
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	
	
	public String getSeedNodeIp() {
		return seedNodeIp;
	}
	public void setSeedNodeIp(String seedNodeIp) {
		this.seedNodeIp = seedNodeIp;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	
}
