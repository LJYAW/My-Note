/*
 * 文件名： ParamResItem.java
 * 
 * 创建日期： 2013-12-27
 *
 * Copyright(C) 2013, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.fas.res.resource.resourceType.entity;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2013-12-27
 */

public class ParamResItem {
	private String resId;
	private String resGrpId;
	private String resCode;
	private String resName;
	private Integer resNumber;
	private Integer state;
	private String parentId;
	private String treeCode;
	private String description;
	private String remark;
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getResGrpId() {
		return resGrpId;
	}
	public void setResGrpId(String resGrpId) {
		this.resGrpId = resGrpId;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public Integer getResNumber() {
		return resNumber;
	}
	public void setResNumber(Integer resNumber) {
		this.resNumber = resNumber;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getTreeCode() {
		return treeCode;
	}
	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
