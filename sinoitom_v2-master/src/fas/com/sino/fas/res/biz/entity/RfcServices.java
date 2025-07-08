package com.sino.fas.res.biz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RfcServices entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Rfc_Services")
public class RfcServices implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5591865633387568179L;
	private Integer serviceId;
	private Integer protId;
	private String serviceName;
	private Integer servicePort;
	private String aliases;
	private String comment;

	// Constructors

	/** default constructor */
	public RfcServices() {
	}

	/** minimal constructor */
	public RfcServices(Integer serviceId, Integer protId, String serviceName,
			Integer servicePort) {
		this.serviceId = serviceId;
		this.protId = protId;
		this.serviceName = serviceName;
		this.servicePort = servicePort;
	}

	/** full constructor */
	public RfcServices(Integer serviceId, Integer protId, String serviceName,
			Integer servicePort, String aliases, String comment) {
		this.serviceId = serviceId;
		this.protId = protId;
		this.serviceName = serviceName;
		this.servicePort = servicePort;
		this.aliases = aliases;
		this.comment = comment;
	}

	// Property accessors
	@Id
	@Column(name = "Service_ID", unique = true, nullable = false)
	public Integer getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	@Column(name = "Prot_ID", nullable = false)
	public Integer getProtId() {
		return this.protId;
	}

	public void setProtId(Integer protId) {
		this.protId = protId;
	}

	@Column(name = "Service_Name", nullable = false, length = 64)
	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Column(name = "Service_Port", nullable = false)
	public Integer getServicePort() {
		return this.servicePort;
	}

	public void setServicePort(Integer servicePort) {
		this.servicePort = servicePort;
	}

	@Column(name = "Aliases", length = 64)
	public String getAliases() {
		return this.aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	@Column(name = "Comment", length = 128)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}