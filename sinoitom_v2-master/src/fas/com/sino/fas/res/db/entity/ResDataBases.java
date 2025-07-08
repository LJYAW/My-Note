package com.sino.fas.res.db.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.sql.Timestamp;


/**
 * @author fengyao
 * @date 2019-10-9上午9:26:42
 * @className ResDataBases
 *
 * @Description TODO
 *
 */
@Entity
@Table(name = "Res_DataBases",uniqueConstraints = @UniqueConstraint(columnNames = "dbsId"))
public class ResDataBases {
	private String orgId;//机构ID
	private Long dbsId;//数据库系统ID(IdGen)
	private Integer resClassCode;//资源分类编码(数据字典：IT_ResClass)
	private String resClassName;//资源分类名称
	private Integer resTypeCode;//资源类型编码（数据字典：IT_DBType）
	private String resTypeName;//资源类型名称
	private Long hostId;//服务器ID
	private String hostName;//服务器名称
	private String hostIp;//服务器IP
	private Long hostIpLong;//hostIpLong
	private String dbType;//数据库类型（等于 resTypeName）
	private String dbVersion;//数据库版本
	private String dbName;//数据库/实例名称
	private String dbPort;//服务端口
	private String jdbcDriver;//jdbcDriver
	private String jdbcUrl;//jdbcUrl
	private String userName;//用户名
	private String password;//密码
	private Long controllerId;//控制器ID
	private String controllerIp;//控制器IP地址
	private String controllerPort;//控制器端口
	private Integer auditStatus;//审核状态（0:未审核；1:已审核）
	private Integer verifyStatus;//验证状态（0:未验证；1:已验证）
	private String creator;//
	private Timestamp createTime;//
	private String modifier;//
	private Timestamp modifyTime;//
	private String accessMode;
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Id
	public Long getDbsId() {
		return dbsId;
	}
	public void setDbsId(Long dbsId) {
		this.dbsId = dbsId;
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
	public Long getHostId() {
		return hostId;
	}
	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public Long getHostIpLong() {
		return hostIpLong;
	}
	public void setHostIpLong(Long hostIpLong) {
		this.hostIpLong = hostIpLong;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getDbVersion() {
		return dbVersion;
	}
	public void setDbVersion(String dbVersion) {
		this.dbVersion = dbVersion;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbPort() {
		return dbPort;
	}
	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}
	public String getJdbcDriver() {
		return jdbcDriver;
	}
	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getControllerId() {
		return controllerId;
	}
	public void setControllerId(Long controllerId) {
		this.controllerId = controllerId;
	}
	public String getControllerIp() {
		return controllerIp;
	}
	public void setControllerIp(String controllerIp) {
		this.controllerIp = controllerIp;
	}
	public String getControllerPort() {
		return controllerPort;
	}
	public void setControllerPort(String controllerPort) {
		this.controllerPort = controllerPort;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Integer getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getAccessMode() {
		return accessMode;
	}
	public void setAccessMode(String accessMode) {
		this.accessMode = accessMode;
	}
	
}
