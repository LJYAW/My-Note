/*
 * 文件名： TopoLine.java
 * 
 * 创建日期： 2015-1-26
 *
 * Copyright(C) 2015, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.topo.topoLine.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision$
 *
 * @since 2015-1-26
 */
@Entity
@IdClass(TopoMapKey.class)  
@Table(name = "Topo_Line")
public class TopoLine {
	private Long graphId;
	private Long lineId;
	private Integer lineType;
	private String lineName;
	private Long nodeAId;
	private String nodeAName;
	private Integer ifIndexA;
	private String ifNameA;
	private String ifIpA;
	private String ifIpB;
	private Long ifBandwidthA;
	private Long lineBandwidth;
	private Long nodeBId;
	private String nodeBName;
	private Long ifBandwidthB;
	private Integer ifIndexB;
	private String ifNameB;
	private Integer flag;
	private String remark;
	
	@Id
	@Column(name="GraphId")
	public Long getGraphId() {
		return graphId;
	}
	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}
	
	@Id
	@Column(name="LineId")
	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	
	@Column(name="LineType")
	public Integer getLineType() {
		return lineType;
	}
	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}
	
	@Column(name="LineName")
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
	@Column(name="NodeAId")
	public Long getNodeAId() {
		return nodeAId;
	}
	public void setNodeAId(Long nodeAId) {
		this.nodeAId = nodeAId;
	}
	
	@Column(name="NodeAName")
	public String getNodeAName() {
		return nodeAName;
	}
	public void setNodeAName(String nodeAName) {
		this.nodeAName = nodeAName;
	}
	
	@Column(name="IfIndexA")
	public Integer getIfIndexA() {
		return ifIndexA;
	}
	public void setIfIndexA(Integer ifIndexA) {
		this.ifIndexA = ifIndexA;
	}
	
	@Column(name="IfNameA")
	public String getIfNameA() {
		return ifNameA;
	}
	public void setIfNameA(String ifNameA) {
		this.ifNameA = ifNameA;
	}
	
	@Column(name="LineBandwidth")
	public Long getLineBandwidth() {
		return lineBandwidth;
	}
	public void setLineBandwidth(Long lineBandwidth) {
		this.lineBandwidth = lineBandwidth;
	}
	
	@Column(name="NodeBId")
	public Long getNodeBId() {
		return nodeBId;
	}
	public void setNodeBId(Long nodeBId) {
		this.nodeBId = nodeBId;
	}
	
	@Column(name="NodeBName")
	public String getNodeBName() {
		return nodeBName;
	}
	public void setNodeBName(String nodeBName) {
		this.nodeBName = nodeBName;
	}
	
	@Column(name="IfIndexB")
	public Integer getIfIndexB() {
		return ifIndexB;
	}
	public void setIfIndexB(Integer ifIndexB) {
		this.ifIndexB = ifIndexB;
	}
	
	@Column(name="IfNameB")
	public String getIfNameB() {
		return ifNameB;
	}
	public void setIfNameB(String ifNameB) {
		this.ifNameB = ifNameB;
	}
	
	@Column(name="Flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	@Column(name="Remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Long getIfBandwidthA() {
		return ifBandwidthA;
	}
	public void setIfBandwidthA(Long ifBandwidthA) {
		this.ifBandwidthA = ifBandwidthA;
	}
	
	public Long getIfBandwidthB() {
		return ifBandwidthB;
	}
	public void setIfBandwidthB(Long ifBandwidthB) {
		this.ifBandwidthB = ifBandwidthB;
	}
	
	public String getIfIpA() {
		return ifIpA;
	}
	public void setIfIpA(String ifIpA) {
		this.ifIpA = ifIpA;
	}
	
	public String getIfIpB() {
		return ifIpB;
	}
	public void setIfIpB(String ifIpB) {
		this.ifIpB = ifIpB;
	}

	
}
