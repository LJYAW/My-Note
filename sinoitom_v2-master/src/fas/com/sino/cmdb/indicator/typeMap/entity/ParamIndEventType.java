/*
 * 文件名： ParamIndEventType.java
 * 
 * 创建日期： 2014-10-22
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.indicator.typeMap.entity;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-10-22
 */
public class ParamIndEventType {
	
	private Integer id;
	private Long indItemID;
	private String indClassName;		//varchar 32
	private String indGroupName;
	private String indicatorItem;
	private String indItemName;
	
	private Integer eventTypeID;
	private String eventClassName;
	private Integer eventTypeCode;
	private String eventTypeName;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getIndItemID() {
		return indItemID;
	}
	public void setIndItemID(Long indItemID) {
		this.indItemID = indItemID;
	}
	public String getIndClassName() {
		return indClassName;
	}
	public void setIndClassName(String indClassName) {
		this.indClassName = indClassName;
	}
	public String getIndGroupName() {
		return indGroupName;
	}
	public void setIndGroupName(String indGroupName) {
		this.indGroupName = indGroupName;
	}
	public String getIndicatorItem() {
		return indicatorItem;
	}
	public void setIndicatorItem(String indicatorItem) {
		this.indicatorItem = indicatorItem;
	}
	public String getIndItemName() {
		return indItemName;
	}
	public void setIndItemName(String indItemName) {
		this.indItemName = indItemName;
	}
	public Integer getEventTypeID() {
		return eventTypeID;
	}
	public void setEventTypeID(Integer eventTypeID) {
		this.eventTypeID = eventTypeID;
	}
	public String getEventClassName() {
		return eventClassName;
	}
	public void setEventClassName(String eventClassName) {
		this.eventClassName = eventClassName;
	}
	public Integer getEventTypeCode() {
		return eventTypeCode;
	}
	public void setEventTypeCode(Integer eventTypeCode) {
		this.eventTypeCode = eventTypeCode;
	}
	public String getEventTypeName() {
		return eventTypeName;
	}
	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}

	
	
	
}
