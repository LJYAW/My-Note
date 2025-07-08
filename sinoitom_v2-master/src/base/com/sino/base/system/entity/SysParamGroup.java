package com.sino.base.system.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OrderBy;

/**
 * SysParamGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Sys_ParamGroup", uniqueConstraints = @UniqueConstraint(columnNames = "GrpCode"))
public class SysParamGroup implements java.io.Serializable {

	// Fields

	private String paramGrpId;
	private String grpCode;
	private String grpName;
	private Integer grpNumber;
	private String description;
	private Integer state;
	private Boolean isCanEdit;
	private Boolean isChangeNotify;
	private String notifyServer;
	private Integer notifyPort;
	private Boolean isDefCommand;
	private String commandContent;
	private String remark;
	private Set<SysParamItem> sysParamItems = new HashSet<SysParamItem>(0);

	// Constructors

	/** default constructor */
	public SysParamGroup() {
	}

	/** minimal constructor */
	public SysParamGroup(String paramGrpId, String grpCode, String grpName,
			Integer grpNumber, Integer state, Boolean isCanEdit,
			Boolean isChangeNotify) {
		this.paramGrpId = paramGrpId;
		this.grpCode = grpCode;
		this.grpName = grpName;
		this.grpNumber = grpNumber;
		this.state = state;
		this.isCanEdit = isCanEdit;
		this.isChangeNotify = isChangeNotify;
	}

	/** full constructor */
	public SysParamGroup(String paramGrpId, String grpCode, String grpName,
			Integer grpNumber, String description, Integer state,
			Boolean isCanEdit, Boolean isChangeNotify, String notifyServer,
			Integer notifyPort, Boolean isDefCommand, String commandContent,
			String remark, Set<SysParamItem> sysParamItems) {
		this.paramGrpId = paramGrpId;
		this.grpCode = grpCode;
		this.grpName = grpName;
		this.grpNumber = grpNumber;
		this.description = description;
		this.state = state;
		this.isCanEdit = isCanEdit;
		this.isChangeNotify = isChangeNotify;
		this.notifyServer = notifyServer;
		this.notifyPort = notifyPort;
		this.isDefCommand = isDefCommand;
		this.commandContent = commandContent;
		this.remark = remark;
		this.sysParamItems = sysParamItems;
	}

	// Property accessors
	@Id
	@Column(name = "ParamGrpId", unique = true, nullable = false, length = 36)
	public String getParamGrpId() {
		return this.paramGrpId;
	}

	public void setParamGrpId(String paramGrpId) {
		this.paramGrpId = paramGrpId;
	}

	@Column(name = "GrpCode", unique = true, nullable = false, length = 64)
	public String getGrpCode() {
		return this.grpCode;
	}

	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}

	@Column(name = "GrpName", nullable = false, length = 64)
	public String getGrpName() {
		return this.grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	@Column(name = "GrpNumber", nullable = false)
	public Integer getGrpNumber() {
		return this.grpNumber;
	}

	public void setGrpNumber(Integer grpNumber) {
		this.grpNumber = grpNumber;
	}

	@Column(name = "Description", length = 128)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "State", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "IsCanEdit", nullable = false)
	public Boolean getIsCanEdit() {
		return this.isCanEdit;
	}

	public void setIsCanEdit(Boolean isCanEdit) {
		this.isCanEdit = isCanEdit;
	}

	@Column(name = "IsChangeNotify", nullable = false)
	public Boolean getIsChangeNotify() {
		return this.isChangeNotify;
	}

	public void setIsChangeNotify(Boolean isChangeNotify) {
		this.isChangeNotify = isChangeNotify;
	}

	@Column(name = "NotifyServer", length = 32)
	public String getNotifyServer() {
		return this.notifyServer;
	}

	public void setNotifyServer(String notifyServer) {
		this.notifyServer = notifyServer;
	}

	@Column(name = "NotifyPort")
	public Integer getNotifyPort() {
		return this.notifyPort;
	}

	public void setNotifyPort(Integer notifyPort) {
		this.notifyPort = notifyPort;
	}

	@Column(name = "IsDefCommand")
	public Boolean getIsDefCommand() {
		return this.isDefCommand;
	}

	public void setIsDefCommand(Boolean isDefCommand) {
		this.isDefCommand = isDefCommand;
	}

	@Column(name = "CommandContent", length = 256)
	public String getCommandContent() {
		return this.commandContent;
	}

	public void setCommandContent(String commandContent) {
		this.commandContent = commandContent;
	}

	@Column(name = "Remark", length = 128)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysParamGroup")
	@OrderBy(clause = "paramNumber")
	public Set<SysParamItem> getSysParamItems() {
		return this.sysParamItems;
	}

	public void setSysParamItems(Set<SysParamItem> sysParamItems) {
		this.sysParamItems = sysParamItems;
	}

	
}