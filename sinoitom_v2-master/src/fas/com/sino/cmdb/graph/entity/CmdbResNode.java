package com.sino.cmdb.graph.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Cmdb_ResNode", uniqueConstraints = @UniqueConstraint(columnNames = "nodeId"))
public class CmdbResNode {
	private String graphId;//关系图ID
	private Long nodeId;//节点ID
	private Integer nodeType;//节点类型
	private String nodeIcon;//节点图标文件
	private Integer posX;//X坐标（像素数）
	private Integer posY;//Y坐标（像素数）
	private Double width;//宽度（像素数）
	private Double height;//高度（像素数）
	private Integer resCatCode;//资源类别（1:业务；2：服务；3：进程；4：设备）
	private Long resId;//资源ID（包括各类应用、软硬件资源）
	private String resName;//资源名称
	private String resLabel;//资源标签(根据资源类别的不同，显示不同的内容，如网络设备的IP地址)
	private Integer resClassCode;//资源分类编码
	private String resClassName;//资源分类名称
	private Integer resTypeCode;//资源类型编码
	private String resTypeName;//资源类型名称
	private String descr;//说明
	private Integer namePosX;//名称X坐标
	private Integer namePosY;//名称Y坐标
	private Double resAvlb;//资源可用性
	
	public String getGraphId() {
		return graphId;
	}
	public void setGraphId(String graphId) {
		this.graphId = graphId;
	}
	
	@Id
	@Column(name="nodeId")
//	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public Integer getNodeType() {
		return nodeType;
	}
	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}
	public String getNodeIcon() {
		return nodeIcon;
	}
	public void setNodeIcon(String nodeIcon) {
		this.nodeIcon = nodeIcon;
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
	public Integer getResCatCode() {
		return resCatCode;
	}
	public void setResCatCode(Integer resCatCode) {
		this.resCatCode = resCatCode;
	}
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResLabel() {
		return resLabel;
	}
	public void setResLabel(String resLabel) {
		this.resLabel = resLabel;
	}
	public Integer getResClassCode() {
		return resClassCode;
	}
	public void setResClassCode(Integer resClassCode) {
		this.resClassCode = resClassCode;
	}
	public String getResClassName() {
		return resClassName;
	}
	public void setResClassName(String resClassName) {
		this.resClassName = resClassName;
	}
	public Integer getResTypeCode() {
		return resTypeCode;
	}
	public void setResTypeCode(Integer resTypeCode) {
		this.resTypeCode = resTypeCode;
	}
	public String getResTypeName() {
		return resTypeName;
	}
	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
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
	public Double getResAvlb() {
		return resAvlb;
	}
	public void setResAvlb(Double resAvlb) {
		this.resAvlb = resAvlb;
	}
	@Override
	public String toString() {
		return "CmdbResNode [graphId=" + graphId + ", nodeId=" + nodeId
				+ ", nodeType=" + nodeType + ", nodeIcon=" + nodeIcon
				+ ", posX=" + posX + ", posY=" + posY + ", width=" + width
				+ ", height=" + height + ", resCatCode=" + resCatCode
				+ ", resId=" + resId + ", resName=" + resName + ", resLabel="
				+ resLabel + ", resClassCode=" + resClassCode
				+ ", resClassName=" + resClassName + ", resTypeCode="
				+ resTypeCode + ", resTypeName=" + resTypeName + ", descr="
				+ descr + ", namePosX=" + namePosX + ", namePosY=" + namePosY
				+ ", resAvlb=" + resAvlb + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resId == null) ? 0 : resId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CmdbResNode other = (CmdbResNode) obj;
		if (resId == null) {
			if (other.resId != null)
				return false;
		} else if (!resId.equals(other.resId))
			return false;
		return true;
	}

	
	
	
	
}
