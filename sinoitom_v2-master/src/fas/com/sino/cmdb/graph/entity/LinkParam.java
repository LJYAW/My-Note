package com.sino.cmdb.graph.entity;

public class LinkParam {

	private Long graphId;
	private Long linkID;                   
	private String linkName;                   //varchar(64)Link Name
	private String nodeAId;                   //bigint(19)
	private String nodeAName;  
	private String nodeBId;                   //varchar(64)
	private String nodeBName;
	private Integer flag;
	private Integer subType;
	
	public Long getGraphId() {
		return graphId;
	}
	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}
	public Long getLinkID() {
		return linkID;
	}
	public void setLinkID(Long linkID) {
		this.linkID = linkID;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getNodeAId() {
		return nodeAId;
	}
	public void setNodeAId(String nodeAId) {
		this.nodeAId = nodeAId;
	}
	public String getNodeAName() {
		return nodeAName;
	}
	public void setNodeAName(String nodeAName) {
		this.nodeAName = nodeAName;
	}
	public String getNodeBId() {
		return nodeBId;
	}
	public void setNodeBId(String nodeBId) {
		this.nodeBId = nodeBId;
	}
	public String getNodeBName() {
		return nodeBName;
	}
	public void setNodeBName(String nodeBName) {
		this.nodeBName = nodeBName;
	}               //varchar(15)
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getSubType() {
		return subType;
	}
	public void setSubType(Integer subType) {
		this.subType = subType;
	}
	
	
}
