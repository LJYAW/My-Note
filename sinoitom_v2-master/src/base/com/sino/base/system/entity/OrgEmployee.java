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
import javax.persistence.Table;

/**
 * OrgEmployee entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Org_Employee")
public class OrgEmployee implements java.io.Serializable {

	// Fields

	private String empId;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private Integer status;
	private String name;
	private String sex;
	private String workAddress;
	private String workAddrPostCode;
	private String workRoom;
	private String identNo;
	private Timestamp birthday;
	private String regPlace;
	private String degree;
	private String school;
	private String specialty;
	private String homeAddress;
	private String homePhone;
	private String contact;
	private String phone;
	private String mobile;
	private String email;
	private String instantMsg;
	private String experience;
	private String resume;
	private String skill;
	private String photo;
	private String workNo;
	private Timestamp enterTime;
	private Timestamp leaveTime;
	private String mainOrgId;
	private String mainDptId;
	private String mainPosId;
	private String remark;
	private Set<OrgPosition> orgPositions = new HashSet<OrgPosition>(0);

	// Constructors

	/** default constructor */
	public OrgEmployee() {
	}

	/** minimal constructor */
	public OrgEmployee(String empId, String creator, Timestamp createTime,
			String modifier, Timestamp modifyTime, Integer status, String name) {
		this.empId = empId;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.status = status;
		this.name = name;
	}

	/** full constructor */
	public OrgEmployee(String empId, String creator, Timestamp createTime,
			String modifier, Timestamp modifyTime, Integer status, String name,
			String sex, String workAddress, String workAddrPostCode,
			String workRoom, String identNo, Timestamp birthday,
			String regPlace, String degree, String school, String specialty,
			String homeAddress, String homePhone, String contact, String phone,
			String mobile, String email, String instantMsg, String experience,
			String resume, String skill, String photo, String workNo,
			Timestamp enterTime, Timestamp leaveTime, String mainOrgId,
			String mainDptId, String mainPosId, String remark,
			Set<OrgPosition> orgPositions) {
		this.empId = empId;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.status = status;
		this.name = name;
		this.sex = sex;
		this.workAddress = workAddress;
		this.workAddrPostCode = workAddrPostCode;
		this.workRoom = workRoom;
		this.identNo = identNo;
		this.birthday = birthday;
		this.regPlace = regPlace;
		this.degree = degree;
		this.school = school;
		this.specialty = specialty;
		this.homeAddress = homeAddress;
		this.homePhone = homePhone;
		this.contact = contact;
		this.phone = phone;
		this.mobile = mobile;
		this.email = email;
		this.instantMsg = instantMsg;
		this.experience = experience;
		this.resume = resume;
		this.skill = skill;
		this.photo = photo;
		this.workNo = workNo;
		this.enterTime = enterTime;
		this.leaveTime = leaveTime;
		this.mainOrgId = mainOrgId;
		this.mainDptId = mainDptId;
		this.mainPosId = mainPosId;
		this.remark = remark;
		this.orgPositions = orgPositions;
	}

	// Property accessors
	@Id
	@Column(name = "EmpId", unique = true, nullable = false, length = 36)
	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
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

	@Column(name = "Status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "Name", nullable = false, length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Sex", length = 1)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "WorkAddress", length = 128)
	public String getWorkAddress() {
		return this.workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	@Column(name = "WorkAddrPostCode", length = 16)
	public String getWorkAddrPostCode() {
		return this.workAddrPostCode;
	}

	public void setWorkAddrPostCode(String workAddrPostCode) {
		this.workAddrPostCode = workAddrPostCode;
	}

	@Column(name = "WorkRoom", length = 32)
	public String getWorkRoom() {
		return this.workRoom;
	}

	public void setWorkRoom(String workRoom) {
		this.workRoom = workRoom;
	}

	@Column(name = "IdentNo", length = 18)
	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	@Column(name = "Birthday", length = 23)
	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	@Column(name = "RegPlace", length = 64)
	public String getRegPlace() {
		return this.regPlace;
	}

	public void setRegPlace(String regPlace) {
		this.regPlace = regPlace;
	}

	@Column(name = "Degree", length = 32)
	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Column(name = "School", length = 64)
	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name = "Specialty", length = 32)
	public String getSpecialty() {
		return this.specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	@Column(name = "HomeAddress", length = 64)
	public String getHomeAddress() {
		return this.homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	@Column(name = "HomePhone", length = 32)
	public String getHomePhone() {
		return this.homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	@Column(name = "Contact", length = 32)
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "Phone", length = 32)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "Mobile", length = 32)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "Email", length = 128)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "InstantMsg", length = 64)
	public String getInstantMsg() {
		return this.instantMsg;
	}

	public void setInstantMsg(String instantMsg) {
		this.instantMsg = instantMsg;
	}

	@Column(name = "Experience", length = 1024)
	public String getExperience() {
		return this.experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	@Column(name = "Resume", length = 1024)
	public String getResume() {
		return this.resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	@Column(name = "Skill", length = 512)
	public String getSkill() {
		return this.skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	@Column(name = "Photo", length = 256)
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Column(name = "WorkNo", length = 64)
	public String getWorkNo() {
		return this.workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	@Column(name = "EnterTime", length = 23)
	public Timestamp getEnterTime() {
		return this.enterTime;
	}

	public void setEnterTime(Timestamp enterTime) {
		this.enterTime = enterTime;
	}

	@Column(name = "LeaveTime", length = 23)
	public Timestamp getLeaveTime() {
		return this.leaveTime;
	}

	public void setLeaveTime(Timestamp leaveTime) {
		this.leaveTime = leaveTime;
	}

	@Column(name = "MainOrgId", length = 36)
	public String getMainOrgId() {
		return this.mainOrgId;
	}

	public void setMainOrgId(String mainOrgId) {
		this.mainOrgId = mainOrgId;
	}

	@Column(name = "MainDptId", length = 36)
	public String getMainDptId() {
		return this.mainDptId;
	}

	public void setMainDptId(String mainDptId) {
		this.mainDptId = mainDptId;
	}

	@Column(name = "MainPosId", length = 36)
	public String getMainPosId() {
		return this.mainPosId;
	}

	public void setMainPosId(String mainPosId) {
		this.mainPosId = mainPosId;
	}

	@Column(name = "Remark", length = 128)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinTable(name = "Org_EmpMapPos", joinColumns = { @JoinColumn(name = "EmpId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "PositionId", nullable = false, updatable = false) })
	public Set<OrgPosition> getOrgPositions() {
		return this.orgPositions;
	}

	public void setOrgPositions(Set<OrgPosition> orgPositions) {
		this.orgPositions = orgPositions;
	}

}