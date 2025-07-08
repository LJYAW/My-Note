package com.sino.base.system.entity;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * 系统日志实体类
 *
 * @author .
 */
@Entity
@Table(name = "Sys_Log", uniqueConstraints = @UniqueConstraint(columnNames = "CreateTimeValue"))
public class SysLog implements java.io.Serializable {

	// Fields

	private String logId;
	private String logTitle;
	private String logContent;
	/**
	 * @fieldName: logLevel
	 * @fieldType: Integer
	 * @Description: 日志级别，如：\n   1-3级或1-5级等
	 */
	private Integer logLevel;
	private Integer logFlag;
	private String funcName;
	private String opType;
	/**
	 * @fieldName: operateType
	 * @fieldType: Integer
	 * @Description: 操作类型0:登录退出 1:路由器操作 2:交换机操作
	 */
	private Integer operateType;
	private Timestamp createTime;
	private Long createTimeValue;
	private String userId;
	private String userAccount;
	private String userName;
	private String organId;
	private String organName;
	private String organCode;
	/**
	 * @fieldName: clientAddress
	 * @fieldType: String
	 * @Description: 客户端IP地址,B/S模式使用
	 */
	private String clientAddress;
	private String remark;

	// Constructors

	/** default constructor */
	public SysLog() {
	}

	/** minimal constructor */
	public SysLog(String logId, String logTitle, String logContent,
			Timestamp createTime, Long createTimeValue, String userId,
			String userAccount) {
		this.logId = logId;
		this.logTitle = logTitle;
		this.logContent = logContent;
		this.createTime = createTime;
		this.createTimeValue = createTimeValue;
		this.userId = userId;
		this.userAccount = userAccount;
	}

	/** full constructor */
	public SysLog(String logId, String logTitle, String logContent,
			Integer logLevel, Integer logFlag, String funcName, String opType,
			Timestamp createTime, Long createTimeValue, String userId,
			String userAccount, String userName, String organId,
			String organName, String organCode, String clientAddress,
			String remark) {
		this.logId = logId;
		this.logTitle = logTitle;
		this.logContent = logContent;
		this.logLevel = logLevel;
		this.logFlag = logFlag;
		this.funcName = funcName;
		this.opType = opType;
		this.createTime = createTime;
		this.createTimeValue = createTimeValue;
		this.userId = userId;
		this.userAccount = userAccount;
		this.userName = userName;
		this.organId = organId;
		this.organName = organName;
		this.organCode = organCode;
		this.clientAddress = clientAddress;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "LogId", unique = true, nullable = false, length = 36)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "LogTitle", nullable = false, length = 32)
	public String getLogTitle() {
		return this.logTitle;
	}

	public void setLogTitle(String logTitle) {
		this.logTitle = logTitle;
	}

	@Column(name = "LogContent", nullable = false, length = 512)
	public String getLogContent() {
		return this.logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	@Column(name = "LogLevel")
	public Integer getLogLevel() {
		return this.logLevel;
	}

	public void setLogLevel(Integer logLevel) {
		this.logLevel = logLevel;
	}

	@Column(name = "LogFlag")
	public Integer getLogFlag() {
		return this.logFlag;
	}

	public void setLogFlag(Integer logFlag) {
		this.logFlag = logFlag;
	}

	@Column(name = "FuncName", length = 32)
	public String getFuncName() {
		return this.funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	@Column(name = "OpType", length = 32)
	public String getOpType() {
		return this.opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	@Column(name = "CreateTime", nullable = false, length = 23)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CreateTimeValue", unique = true, nullable = false)
	public Long getCreateTimeValue() {
		return this.createTimeValue;
	}

	public void setCreateTimeValue(Long createTimeValue) {
		this.createTimeValue = createTimeValue;
	}

	@Column(name = "UserId", nullable = false, length = 36)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "UserAccount", nullable = false, length = 32)
	public String getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	@Column(name = "UserName", length = 32)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "OrganId", length = 36)
	public String getOrganId() {
		return this.organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	@Column(name = "OrganName", length = 64)
	public String getOrganName() {
		return this.organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	@Column(name = "OrganCode", length = 32)
	public String getOrganCode() {
		return this.organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	@Column(name = "ClientAddress", length = 16)
	public String getClientAddress() {
		return this.clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	@Column(name = "Remark", length = 128)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "OperateType")
	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	
}