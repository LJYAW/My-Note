package com.sino.base.system.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * OrgPosition entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Org_Position")
public class OrgPosition implements java.io.Serializable {

	// Fields

	private String positionId;
	private OrgOrganization orgOrganization;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private Integer state;
	private String positionName;
	private String dutyCode;
	private Integer positionLevel;
	private String qualification;
	private String authority;
	private Integer benefits;
	private Integer payGrade;
	private String description;
	private String remark;
	private Set<OrgEmployee> orgEmployees = new HashSet<OrgEmployee>(0);

	// Constructors

	/** default constructor */
	public OrgPosition() {
	}

	/** minimal constructor */
	public OrgPosition(String positionId, OrgOrganization orgOrganization,
			String creator, Timestamp createTime, String modifier,
			Timestamp modifyTime, Integer state, String positionName) {
		this.positionId = positionId;
		this.orgOrganization = orgOrganization;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.state = state;
		this.positionName = positionName;
	}

	/** full constructor */
	public OrgPosition(String positionId, OrgOrganization orgOrganization,
			String creator, Timestamp createTime, String modifier,
			Timestamp modifyTime, Integer state, String positionName,
			String dutyCode, Integer positionLevel, String qualification,
			String authority, Integer benefits, Integer payGrade,
			String description, String remark, Set<OrgEmployee> orgEmployees) {
		this.positionId = positionId;
		this.orgOrganization = orgOrganization;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.state = state;
		this.positionName = positionName;
		this.dutyCode = dutyCode;
		this.positionLevel = positionLevel;
		this.qualification = qualification;
		this.authority = authority;
		this.benefits = benefits;
		this.payGrade = payGrade;
		this.description = description;
		this.remark = remark;
		this.orgEmployees = orgEmployees;
	}

	// Property accessors
	@Id
	@Column(name = "PositionId", unique = true, nullable = false, length = 36)
	public String getPositionId() {
		return this.positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OrgId", nullable = false)
	public OrgOrganization getOrgOrganization() {
		return this.orgOrganization;
	}

	public void setOrgOrganization(OrgOrganization orgOrganization) {
		this.orgOrganization = orgOrganization;
	}

	@Column(name = "Creator", nullable = false, length = 32)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CreateTime", nullable = false, length = 23)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "Modifier", nullable = false, length = 32)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "ModifyTime", nullable = false, length = 23)
	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "State", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "PositionName", nullable = false, length = 32)
	public String getPositionName() {
		return this.positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	@Column(name = "DutyCode", length = 32)
	public String getDutyCode() {
		return this.dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	@Column(name = "PositionLevel")
	public Integer getPositionLevel() {
		return this.positionLevel;
	}

	public void setPositionLevel(Integer positionLevel) {
		this.positionLevel = positionLevel;
	}

	@Column(name = "Qualification", length = 512)
	public String getQualification() {
		return this.qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	@Column(name = "Authority", length = 512)
	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Column(name = "Benefits")
	public Integer getBenefits() {
		return this.benefits;
	}

	public void setBenefits(Integer benefits) {
		this.benefits = benefits;
	}

	@Column(name = "PayGrade")
	public Integer getPayGrade() {
		return this.payGrade;
	}

	public void setPayGrade(Integer payGrade) {
		this.payGrade = payGrade;
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

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinTable(name = "Org_EmpMapPos", joinColumns = { @JoinColumn(name = "PositionId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "EmpId", nullable = false, updatable = false) })
	public Set<OrgEmployee> getOrgEmployees() {
		return this.orgEmployees;
	}

	public void setOrgEmployees(Set<OrgEmployee> orgEmployees) {
		this.orgEmployees = orgEmployees;
	}

}