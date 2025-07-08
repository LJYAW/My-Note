package com.sino.base.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * SysParamItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Sys_ParamItem", uniqueConstraints = @UniqueConstraint(columnNames = "ParamCode"))
public class SysParamItem implements java.io.Serializable {

	// Fields

	private String paramId;
	private SysParamGroup sysParamGroup;
	private String paramCode;
	private String paramName;
	private Integer paramNumber;
	private String valueType;
	private String valueText;
	private Integer state;
	private String description;
	private String remark;

	// Constructors

	/** default constructor */
	public SysParamItem() {
	}

	/** minimal constructor */
	public SysParamItem(String paramId, SysParamGroup sysParamGroup,
			String paramCode, String paramName, Integer paramNumber,
			String valueType, Integer state) {
		this.paramId = paramId;
		this.sysParamGroup = sysParamGroup;
		this.paramCode = paramCode;
		this.paramName = paramName;
		this.paramNumber = paramNumber;
		this.valueType = valueType;
		this.state = state;
	}

	/** full constructor */
	public SysParamItem(String paramId, SysParamGroup sysParamGroup,
			String paramCode, String paramName, Integer paramNumber,
			String valueType, String valueText, Integer state,
			String description, String remark) {
		this.paramId = paramId;
		this.sysParamGroup = sysParamGroup;
		this.paramCode = paramCode;
		this.paramName = paramName;
		this.paramNumber = paramNumber;
		this.valueType = valueType;
		this.valueText = valueText;
		this.state = state;
		this.description = description;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "ParamId", unique = true, nullable = false, length = 36)
	public String getParamId() {
		return this.paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ParamGrpId", nullable = false)
	public SysParamGroup getSysParamGroup() {
		return this.sysParamGroup;
	}

	public void setSysParamGroup(SysParamGroup sysParamGroup) {
		this.sysParamGroup = sysParamGroup;
	}

	@Column(name = "ParamCode", unique = true, nullable = false, length = 64)
	public String getParamCode() {
		return this.paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	@Column(name = "ParamName", nullable = false, length = 64)
	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@Column(name = "ParamNumber", nullable = false)
	public Integer getParamNumber() {
		return this.paramNumber;
	}

	public void setParamNumber(Integer paramNumber) {
		this.paramNumber = paramNumber;
	}

	@Column(name = "ValueType", nullable = false)
	public String getValueType() {
		return this.valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	@Column(name = "ValueText", length = 512)
	public String getValueText() {
		return this.valueText;
	}

	public void setValueText(String valueText) {
		this.valueText = valueText;
	}

	@Column(name = "State", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "Description", length = 128)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "Remark", length = 128)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}