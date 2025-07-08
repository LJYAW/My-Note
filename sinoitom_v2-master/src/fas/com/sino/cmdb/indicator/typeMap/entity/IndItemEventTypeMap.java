/*
 * 文件名： IndItemEventTypeMap.java
 * 
 * 创建日期： 2014-10-22
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.indicator.typeMap.entity;

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
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-10-22
 */
@Entity
@Table(name="Cmdb_IndItemEventTypeMap" , uniqueConstraints = @UniqueConstraint(columnNames = "Id"))
public class IndItemEventTypeMap {

	private Integer id;
	private Long indItemID;
	private Integer eventTypeID;
	private Integer eventClassCode;
	
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="IndItemID")
	public Long getIndItemID() {
		return indItemID;
	}
	public void setIndItemID(Long indItemID) {
		this.indItemID = indItemID;
	}
	
	@Column(name="EventTypeID")
	public Integer getEventTypeID() {
		return eventTypeID;
	}
	public void setEventTypeID(Integer eventTypeID) {
		this.eventTypeID = eventTypeID;
	}
	public Integer getEventClassCode() {
		return eventClassCode;
	}
	public void setEventClassCode(Integer eventClassCode) {
		this.eventClassCode = eventClassCode;
	}
	
	

}
