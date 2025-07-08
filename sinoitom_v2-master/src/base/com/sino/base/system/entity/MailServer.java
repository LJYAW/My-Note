package com.sino.base.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Sys_MailServer",uniqueConstraints = @UniqueConstraint(columnNames = "mailServerID"))
public class MailServer {

	private Integer mailServerID;
	private String smtpServer;
	private Integer smtpServerPort;
	private String userName;
	private String password;
	private Integer auth_On_Login;
	private String from_Address;
	private String to_Address;
	private String cc_Address;
	
	@Id
	@Column(name="mailServerID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getMailServerID() {
		return mailServerID;
	}
	public void setMailServerID(Integer mailServerID) {
		this.mailServerID = mailServerID;
	}
	
	@Column(name="smtpServer")
	public String getSmtpServer() {
		return smtpServer;
	}
	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}
	
	@Column(name="smtpServerPort")
	public Integer getSmtpServerPort() {
		return smtpServerPort;
	}
	public void setSmtpServerPort(Integer smtpServerPort) {
		this.smtpServerPort = smtpServerPort;
	}
	
	@Column(name="userName")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="auth_On_Login")
	public Integer getAuth_On_Login() {
		return auth_On_Login;
	}
	public void setAuth_On_Login(Integer auth_On_Login) {
		this.auth_On_Login = auth_On_Login;
	}
	
	@Column(name="from_Address")
	public String getFrom_Address() {
		return from_Address;
	}
	public void setFrom_Address(String from_Address) {
		this.from_Address = from_Address;
	}
	
	@Column(name="to_Address")
	public String getTo_Address() {
		return to_Address;
	}
	public void setTo_Address(String to_Address) {
		this.to_Address = to_Address;
	}
	
	@Column(name="cc_Address")
	public String getCc_Address() {
		return cc_Address;
	}
	public void setCc_Address(String cc_Address) {
		this.cc_Address = cc_Address;
	}
	
	
}
