package com.sino.base.system.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * 系统菜单实体类
 *
 * @author .
 * @date 2017年5月2日 下午7:33:45
 */
@Entity
@Table(name = "Sys_Menu")
public class SysMenu implements java.io.Serializable {

	// Fields

	private String menuId;
	private String menuName;
	private String menuDesc;
	private String menuUrl;
	private String parentId=null;
	private String treeCode;
	private Integer state;
	private String icon;
	private String remark;
	private Integer level;
	private String isLeaf;
	private String expanded;
	private Set<SysPower> sysPowers = new HashSet<SysPower>(0);

	// Constructors

	/** default constructor */
	public SysMenu() {
	}

	/** minimal constructor */
	public SysMenu(String menuId, String menuName, String treeCode,
			Integer state) {
		this.menuId = menuId;
		this.menuName = menuName;
		this.treeCode = treeCode;
		this.state = state;
	}

	/** full constructor */
	public SysMenu(String menuId, String menuName, String menuDesc,
			String menuUrl, String parentId, String treeCode, Integer state,
			String icon, String remark, Set<SysPower> sysPowers) {
		this.menuId = menuId;
		this.menuName = menuName;
		this.menuDesc = menuDesc;
		this.menuUrl = menuUrl;
		this.parentId = parentId;
		this.treeCode = treeCode;
		this.state = state;
		this.icon = icon;
		this.remark = remark;
		this.sysPowers = sysPowers;
	}

	// Property accessors
	@Id
	@Column(name = "MenuId", unique = true, nullable = false, length = 36)
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "MenuName", nullable = false, length = 64)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "MenuDesc", length = 128)
	public String getMenuDesc() {
		return this.menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	@Column(name = "MenuUrl", length = 256)
	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	@Column(name = "ParentId", length = 36)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "TreeCode", nullable = false, length = 36)
	public String getTreeCode() {
		return this.treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	@Column(name = "State", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "Icon", length = 64)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "Remark", length = 128)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	@Column(name = "level", length = 0)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Column(name = "expanded")
	public String getExpanded() {
		return expanded;
	}

	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}
	
	
	
	@Column(name = "isLeaf")
	public String isLeaf() {
		return isLeaf;
	}

	public void setLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinTable(name = "Sys_MenuMapPow", joinColumns = { @JoinColumn(name = "MenuId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "PowId", nullable = false, updatable = false) })
	public Set<SysPower> getSysPowers() {
		return this.sysPowers;
	}

	public void setSysPowers(Set<SysPower> sysPowers) {
		this.sysPowers = sysPowers;
	}

}