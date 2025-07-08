package com.sino.fas.res.net.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * NcmSnmpCredentials entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Ncm_SnmpCredentials", uniqueConstraints = @UniqueConstraint(columnNames = "SnmpCred_Name"))
public class NcmSnmpCredentials implements java.io.Serializable {

	// Fields

	private String snmpCredId;
	private String orgId;
	private String snmpCredName;
	private String snmpCredDesc;
	private Integer snmpVersion;
	private Integer snmpPort;
	private String snmpRoStr;
	private String snmpRwStr;
	private String credName;
	private String credDesc;
	private String credUserName;
	private String credContextName;
	private Integer credAuthProt;
	private String credAuthPassword;
	private Integer credEncryptProt;
	private String credEncryptPassword;
	private Integer lowerVisable;
	private Integer status;
	private String creator;
	private Long createTime;
	private String modifier;
	private Long modifyTime;

	// Constructors

	/** default constructor */
	public NcmSnmpCredentials() {
	}

	/** minimal constructor */
	public NcmSnmpCredentials(String snmpCredId, String orgId,
			String snmpCredName, Integer snmpVersion, Integer snmpPort,
			Integer status, Long createTime) {
		this.snmpCredId = snmpCredId;
		this.orgId = orgId;
		this.snmpCredName = snmpCredName;
		this.snmpVersion = snmpVersion;
		this.snmpPort = snmpPort;
		this.status = status;
		this.createTime = createTime;
	}

	/** full constructor */
	public NcmSnmpCredentials(String snmpCredId, String orgId,
			String snmpCredName, String snmpCredDesc, Integer snmpVersion,
			Integer snmpPort, String snmpRoStr, String snmpRwStr,
			String credName, String credDesc, String credUserName,
			String credContextName, Integer credAuthProt,
			String credAuthPassword, Integer credEncryptProt,
			String credEncryptPassword, Integer lowerVisable, Integer status,
			String creator, Long createTime, String modifier, Long modifyTime) {
		this.snmpCredId = snmpCredId;
		this.orgId = orgId;
		this.snmpCredName = snmpCredName;
		this.snmpCredDesc = snmpCredDesc;
		this.snmpVersion = snmpVersion;
		this.snmpPort = snmpPort;
		this.snmpRoStr = snmpRoStr;
		this.snmpRwStr = snmpRwStr;
		this.credName = credName;
		this.credDesc = credDesc;
		this.credUserName = credUserName;
		this.credContextName = credContextName;
		this.credAuthProt = credAuthProt;
		this.credAuthPassword = credAuthPassword;
		this.credEncryptProt = credEncryptProt;
		this.credEncryptPassword = credEncryptPassword;
		this.lowerVisable = lowerVisable;
		this.status = status;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
	}

	// Property accessors
	@Id
	@Column(name = "SnmpCred_ID", unique = true, nullable = false, length = 36)
	public String getSnmpCredId() {
		return this.snmpCredId;
	}

	public void setSnmpCredId(String snmpCredId) {
		this.snmpCredId = snmpCredId;
	}

	@Column(name = "Org_ID", nullable = false, length = 36)
	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(name = "SnmpCred_Name", unique = true, nullable = false, length = 64)
	public String getSnmpCredName() {
		return this.snmpCredName;
	}

	public void setSnmpCredName(String snmpCredName) {
		this.snmpCredName = snmpCredName;
	}

	@Column(name = "SnmpCred_Desc", length = 128)
	public String getSnmpCredDesc() {
		return this.snmpCredDesc;
	}

	public void setSnmpCredDesc(String snmpCredDesc) {
		this.snmpCredDesc = snmpCredDesc;
	}

	@Column(name = "Snmp_Version", nullable = false)
	public Integer getSnmpVersion() {
		return this.snmpVersion;
	}

	public void setSnmpVersion(Integer snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	@Column(name = "SnmpPort", nullable = false)
	public Integer getSnmpPort() {
		return this.snmpPort;
	}

	public void setSnmpPort(Integer snmpPort) {
		this.snmpPort = snmpPort;
	}

	@Column(name = "SnmpRoStr", length = 32)
	public String getSnmpRoStr() {
		return this.snmpRoStr;
	}

	public void setSnmpRoStr(String snmpRoStr) {
		this.snmpRoStr = snmpRoStr;
	}

	@Column(name = "SnmpRwStr", length = 32)
	public String getSnmpRwStr() {
		return this.snmpRwStr;
	}

	public void setSnmpRwStr(String snmpRwStr) {
		this.snmpRwStr = snmpRwStr;
	}

	@Column(name = "Cred_Name", length = 64)
	public String getCredName() {
		return this.credName;
	}

	public void setCredName(String credName) {
		this.credName = credName;
	}

	@Column(name = "Cred_Desc", length = 128)
	public String getCredDesc() {
		return this.credDesc;
	}

	public void setCredDesc(String credDesc) {
		this.credDesc = credDesc;
	}

	@Column(name = "Cred_UserName", length = 16)
	public String getCredUserName() {
		return this.credUserName;
	}

	public void setCredUserName(String credUserName) {
		this.credUserName = credUserName;
	}

	@Column(name = "Cred_ContextName", length = 64)
	public String getCredContextName() {
		return this.credContextName;
	}

	public void setCredContextName(String credContextName) {
		this.credContextName = credContextName;
	}

	@Column(name = "Cred_AuthProt")
	public Integer getCredAuthProt() {
		return this.credAuthProt;
	}

	public void setCredAuthProt(Integer credAuthProt) {
		this.credAuthProt = credAuthProt;
	}

	@Column(name = "Cred_AuthPassword", length = 65535)
	public String getCredAuthPassword() {
		return this.credAuthPassword;
	}

	public void setCredAuthPassword(String credAuthPassword) {
		this.credAuthPassword = credAuthPassword;
	}

	@Column(name = "Cred_EncryptProt")
	public Integer getCredEncryptProt() {
		return this.credEncryptProt;
	}

	public void setCredEncryptProt(Integer credEncryptProt) {
		this.credEncryptProt = credEncryptProt;
	}

	@Column(name = "Cred_EncryptPassword", length = 65535)
	public String getCredEncryptPassword() {
		return this.credEncryptPassword;
	}

	public void setCredEncryptPassword(String credEncryptPassword) {
		this.credEncryptPassword = credEncryptPassword;
	}

	@Column(name = "LowerVisable")
	public Integer getLowerVisable() {
		return this.lowerVisable;
	}

	public void setLowerVisable(Integer lowerVisable) {
		this.lowerVisable = lowerVisable;
	}

	@Column(name = "Status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "Creator", length = 32)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "Create_Time", nullable = false)
	public Long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	@Column(name = "Modifier", length = 32)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "Modify_Time")
	public Long getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

}