package com.sino.cmdb.graph.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Cmdb_BizGraph", uniqueConstraints = @UniqueConstraint(columnNames = "graphId"))
public class CmdbBizGraph {

	private String graphId;//'配置项关系图ID'、
	private Long bizResId;//业务资源ID
	private Integer bizTypeCode;//业务类型编码（数据字典：IT_BizType）
	private String bizTypeName;//业务类型名称
	private String bizName;//业务名称
	private Integer graphType;//'图类型（1：拓扑图；2：关系图）'
	private String graphName;//'关系图名称'
	private String graphDescr;//关系图描述
	private Integer width;//缺省宽度
	private Integer height;//缺省高度
	private Integer flag;//标识
	private Integer status;//状态
	private String creator;//创建人
	private Timestamp createTime;//创建时间
	
	@Id
	@Column(name="graphId")
	public String getGraphId() {
		return graphId;
	}
	public void setGraphId(String graphId) {
		this.graphId = graphId;
	}
	public Long getBizResId() {
		return bizResId;
	}
	public void setBizResId(Long bizResId) {
		this.bizResId = bizResId;
	}
	public Integer getBizTypeCode() {
		return bizTypeCode;
	}
	public void setBizTypeCode(Integer bizTypeCode) {
		this.bizTypeCode = bizTypeCode;
	}
	public String getBizTypeName() {
		return bizTypeName;
	}
	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}
	public String getBizName() {
		return bizName;
	}
	public void setBizName(String bizName) {
		this.bizName = bizName;
	}
	public Integer getGraphType() {
		return graphType;
	}
	public void setGraphType(Integer graphType) {
		this.graphType = graphType;
	}
	public String getGraphName() {
		return graphName;
	}
	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}
	public String getGraphDescr() {
		return graphDescr;
	}
	public void setGraphDescr(String graphDescr) {
		this.graphDescr = graphDescr;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
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
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "CmdbBizGraph [graphId=" + graphId + ", bizResId=" + bizResId
				+ ", bizTypeCode=" + bizTypeCode + ", bizTypeName="
				+ bizTypeName + ", bizName=" + bizName + ", graphType="
				+ graphType + ", graphName=" + graphName + ", graphDescr="
				+ graphDescr + ", width=" + width + ", height=" + height
				+ ", flag=" + flag + ", status=" + status + ", creator="
				+ creator + ", createTime=" + createTime + "]";
	}

	
	
}
