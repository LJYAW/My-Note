package com.sino.topo.graph.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Topo_Graph")
public class TopoGraph {

	private Long graphId;
	private String graphName;
	private String graphDesc;
	private Double width;
	private Double height;
	private String bgPicture;
	private Long seedNodeId;
	private String seedNodeIp;
	private Integer flag;
	private Integer status;
	private String creator;
	private Timestamp createTime;
	
	@Id
	@Column(name="GraphId")
	public Long getGraphId() {
		return graphId;
	}
	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}
	@Column(name="GraphName")
	public String getGraphName() {
		return graphName;
	}
	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}
	
	@Column(name="GraphDesc")
	public String getGraphDesc() {
		return graphDesc;
	}
	public void setGraphDesc(String graphDesc) {
		this.graphDesc = graphDesc;
	}
	
	@Column(name="Width")
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	
	@Column(name="Height")
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	
	@Column(name="BgPicture")
	public String getBgPicture() {
		return bgPicture;
	}
	
	public void setBgPicture(String bgPicture) {
		this.bgPicture = bgPicture;
	}
	
	@Column(name="CreateTime")
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="Flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	@Column(name="SeedNodeId")
	public Long getSeedNodeId() {
		return seedNodeId;
	}
	public void setSeedNodeId(Long seedNodeId) {
		this.seedNodeId = seedNodeId;
	}
	
	@Column(name="Status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="Creator")
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getSeedNodeIp() {
		return seedNodeIp;
	}
	public void setSeedNodeIp(String seedNodeIp) {
		this.seedNodeIp = seedNodeIp;
	}
	
	
	
}
