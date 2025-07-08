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
 * SysResGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Sys_ResGroup", uniqueConstraints = @UniqueConstraint(columnNames = "GrpCode"))
public class SysResGroup implements java.io.Serializable {

	/**
	 * 
	 */
	// Fields

	private String resGrpId;
	private String grpCode;
	private String grpName;
	private Integer grpType;
	private Integer itemType;
	private Integer grpNumber;
	private String description;
	private Integer state;
	private Boolean isCanAdd;
	private Boolean isCanEdit;
	private Boolean isCanDel;
	private Boolean isChangeNotify;
	private String notifyServer;
	private Integer notifyPort;
	private Boolean isDefCommand;
	private String commandContent;
	private String remark;
	private Set<SysResItem> sysResItems = new HashSet<SysResItem>(0);

	// Constructors

	/** default constructor */
	public SysResGroup() {
		notifyServer = "127.0.0.1";
		notifyPort = 5678;
	}

	/** minimal constructor */
	public SysResGroup(String resGrpId, String grpCode, String grpName,
			Integer grpType, Integer itemType, Integer grpNumber,
			Integer state, Boolean isCanAdd, Boolean isCanEdit,
			Boolean isCanDel, Boolean isChangeNotify) {
		this.resGrpId = resGrpId;
		this.grpCode = grpCode;
		this.grpName = grpName;
		this.grpType = grpType;
		this.itemType = itemType;
		this.grpNumber = grpNumber;
		this.state = state;
		this.isCanAdd = isCanAdd;
		this.isCanEdit = isCanEdit;
		this.isCanDel = isCanDel;
		this.isChangeNotify = isChangeNotify;
	}

	/** full constructor */
	public SysResGroup(String resGrpId, String grpCode, String grpName,
			Integer grpType, Integer itemType, Integer grpNumber,
			String description, Integer state, Boolean isCanAdd,
			Boolean isCanEdit, Boolean isCanDel, Boolean isChangeNotify,
			String notifyServer, Integer notifyPort, Boolean isDefCommand,
			String commandContent, String remark, Set<SysResItem> sysResItems) {
		this.resGrpId = resGrpId;
		this.grpCode = grpCode;
		this.grpName = grpName;
		this.grpType = grpType;
		this.itemType = itemType;
		this.grpNumber = grpNumber;
		this.description = description;
		this.state = state;
		this.isCanAdd = isCanAdd;
		this.isCanEdit = isCanEdit;
		this.isCanDel = isCanDel;
		this.isChangeNotify = isChangeNotify;
		this.notifyServer = notifyServer;
		this.notifyPort = notifyPort;
		this.isDefCommand = isDefCommand;
		this.commandContent = commandContent;
		this.remark = remark;
		this.sysResItems = sysResItems;
	}

	// Property accessors
	@Id
	@Column(name = "ResGrpId", unique = true, nullable = false, length = 36)
	public String getResGrpId() {
		return this.resGrpId;
	}

	public void setResGrpId(String resGrpId) {
		this.resGrpId = resGrpId;
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

	@Column(name = "GrpType", nullable = false)
	public Integer getGrpType() {
		return this.grpType;
	}

	public void setGrpType(Integer grpType) {
		this.grpType = grpType;
	}

	@Column(name = "ItemType", nullable = false)
	public Integer getItemType() {
		return this.itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
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

	@Column(name = "IsCanAdd", nullable = false)
	public Boolean getIsCanAdd() {
		return this.isCanAdd;
	}

	public void setIsCanAdd(Boolean isCanAdd) {
		this.isCanAdd = isCanAdd;
	}

	@Column(name = "IsCanEdit", nullable = false)
	public Boolean getIsCanEdit() {
		return this.isCanEdit;
	}

	public void setIsCanEdit(Boolean isCanEdit) {
		this.isCanEdit = isCanEdit;
	}

	@Column(name = "IsCanDel", nullable = false)
	public Boolean getIsCanDel() {
		return this.isCanDel;
	}

	public void setIsCanDel(Boolean isCanDel) {
		this.isCanDel = isCanDel;
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

	@OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "sysResGroup")
	@OrderBy(clause = "resNumber,treeCode")
	public Set<SysResItem> getSysResItems() {
		return this.sysResItems;
	}

	public void setSysResItems(Set<SysResItem> sysResItems) {
		this.sysResItems = sysResItems;
	}

}