/*
 * 文件名： Event.java
 * 
 * 创建日期： 2014-9-13
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.event.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-13
 */
@Entity
@Table(name = "Cmdb_EventType", uniqueConstraints = @UniqueConstraint(columnNames = "EventTypeID"))
public class EventType {

	private Integer eventTypeID;
	private Integer eventClassCode;
	private String eventClassName;
	private Integer eventTypeCode;
	private String eventTypeName;
	private Integer status;
	private String description;
	
	@Id
	@Column(name="EventTypeID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getEventTypeID() {
		return eventTypeID;
	}
	public void setEventTypeID(Integer eventTypeID) {
		this.eventTypeID = eventTypeID;
	}
	
	@Column(name="EventClassCode")
	public Integer getEventClassCode() {
		return eventClassCode;
	}
	public void setEventClassCode(Integer eventClassCode) {
		this.eventClassCode = eventClassCode;
	}
	
	@Column(name="EventClassName")
	public String getEventClassName() {
		return eventClassName;
	}
	public void setEventClassName(String eventClassName) {
		this.eventClassName = eventClassName;
	}
	
	@Column(name="EventTypeCode")
	public Integer getEventTypeCode() {
		return eventTypeCode;
	}
	public void setEventTypeCode(Integer eventTypeCode) {
		this.eventTypeCode = eventTypeCode;
	}
	
	@Column(name="EventTypeName")
	public String getEventTypeName() {
		return eventTypeName;
	}
	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}
	
	@Column(name="Status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

	
}
