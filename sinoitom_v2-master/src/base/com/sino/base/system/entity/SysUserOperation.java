package com.sino.base.system.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Table(name="Sys_UserOperation")
@Entity
public class SysUserOperation {

	private Long Id;
	private String userId;
	private String userName;
	private String funMenu;
	private String operation;
	private String sqlSentence;
	private String retCode;
	private String message;
	private String errorCause;
	private Timestamp opTime;
	
	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFunMenu() {
		return funMenu;
	}
	public void setFunMenu(String funMenu) {
		this.funMenu = funMenu;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getSqlSentence() {
		return sqlSentence;
	}
	public void setSqlSentence(String sqlSentence) {
		this.sqlSentence = sqlSentence;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorCause() {
		return errorCause;
	}
	public void setErrorCause(String errorCause) {
		this.errorCause = errorCause;
	}
	public Timestamp getOpTime() {
		return opTime;
	}
	public void setOpTime(Timestamp opTime) {
		this.opTime = opTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
