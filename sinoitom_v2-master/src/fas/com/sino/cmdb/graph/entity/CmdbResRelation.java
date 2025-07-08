package com.sino.cmdb.graph.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Cmdb_ResRelation", uniqueConstraints = @UniqueConstraint(columnNames = "relationId"))
public class CmdbResRelation {
	private String graphId;//业务关系图ID
	private Long relationId;//关系ID
	private Long nodeAId;//A节点ID
	private Long nodeBId;//配置项B节点ID
	private Integer relType;//关系类型（A...B，如：A包含B，A运行于B、服务A依赖于进程B，等等）
	private String relName;//关系名称
	private String relDescr;//关系说明
	private Double impactFactor;//影响系数（取值范围：0.00 < f <=1.00）
	
	private Integer flag;
	
	public String getGraphId() {
		return graphId;
	}
	public void setGraphId(String graphId) {
		this.graphId = graphId;
	}
	@Id
	@Column(name="relationId")
//	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getRelationId() {
		return relationId;
	}
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}
	public Long getNodeAId() {
		return nodeAId;
	}
	public void setNodeAId(Long nodeAId) {
		this.nodeAId = nodeAId;
	}
	public Long getNodeBId() {
		return nodeBId;
	}
	public void setNodeBId(Long nodeBId) {
		this.nodeBId = nodeBId;
	}
	public Integer getRelType() {
		return relType;
	}
	public void setRelType(Integer relType) {
		this.relType = relType;
	}
	public String getRelName() {
		return relName;
	}
	public void setRelName(String relName) {
		this.relName = relName;
	}
	public String getRelDescr() {
		return relDescr;
	}
	public void setRelDescr(String relDescr) {
		this.relDescr = relDescr;
	}
	
	
	public Double getImpactFactor() {
		return impactFactor;
	}
	public void setImpactFactor(Double impactFactor) {
		this.impactFactor = impactFactor;
	}
	
	
	
	
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "CmdbResRelation [graphId=" + graphId + ", relationId="
				+ relationId + ", nodeAId=" + nodeAId + ", nodeBId=" + nodeBId
				+ ", relType=" + relType + ", relName=" + relName
				+ ", relDescr=" + relDescr + ", impactFactor=" + impactFactor
				+ "]";
	}
	
	
}
