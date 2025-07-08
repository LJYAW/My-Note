package com.sino.monitor.config.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Ncm_DevConfigFile")
public class DevConfigFile {

	private Long id;
	private Long devID;
	private String devIpAddr;
	private Integer cfgFileType;
	private String cfgFileName;
	private String cfgContent;
	private Date collection_Time;
	private Integer baseLine;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDevID() {
		return devID;
	}
	public void setDevID(Long devID) {
		this.devID = devID;
	}
	public String getDevIpAddr() {
		return devIpAddr;
	}
	public void setDevIpAddr(String devIpAddr) {
		this.devIpAddr = devIpAddr;
	}
	public Integer getCfgFileType() {
		return cfgFileType;
	}
	public void setCfgFileType(Integer cfgFileType) {
		this.cfgFileType = cfgFileType;
	}
	public String getCfgFileName() {
		return cfgFileName;
	}
	public void setCfgFileName(String cfgFileName) {
		this.cfgFileName = cfgFileName;
	}
	public String getCfgContent() {
		return cfgContent;
	}
	public void setCfgContent(String cfgContent) {
		this.cfgContent = cfgContent;
	}
	public Date getCollection_Time() {
		return collection_Time;
	}
	public void setCollection_Time(Date collection_Time) {
		this.collection_Time = collection_Time;
	}
	public Integer getBaseLine() {
		return baseLine;
	}
	public void setBaseLine(Integer baseLine) {
		this.baseLine = baseLine;
	}

	
	
}
