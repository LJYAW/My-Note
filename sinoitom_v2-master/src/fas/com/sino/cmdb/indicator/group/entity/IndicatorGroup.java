package com.sino.cmdb.indicator.group.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * 
 * @author <a href="mailto:jiangxs@sino-bridge.com">牛腾龙</a>
 * 
 * @version $Revision$
 * 
 * @since 2014-7-26
 */
@Entity
@Table(name = "Cmdb_IndicatorGroup", uniqueConstraints = @UniqueConstraint(columnNames = "IndGroupID"))
public class IndicatorGroup {

	private Long indGroupID; // bigint 20

	private Integer indClassCode; // int 1

	private String indClassName;// varchar 32

	private String groupEnName;// varchar 32

	private String groupName;// varchar 32 

	private String description;// varchar 128
	private Integer status;
	/** default constructor */
	public IndicatorGroup() {
	}
	
	@Id
	@Column(name = "IndGroupID")
	public long getIndGroupID() {
		return indGroupID;
	}

	public void setIndGroupID(long indGroupID) {
		this.indGroupID = indGroupID;
	}
	
	public Integer getIndClassCode() {
		return indClassCode;
	}

	public void setIndClassCode(Integer indClassCode) {
		this.indClassCode = indClassCode;
	}

	@Column(name = "indClassName")
	public String getIndClassName() {
		return indClassName;
	}

	public void setIndClassName(String indClassName) {
		this.indClassName = indClassName;
	}
	@Column(name = "groupEnName")
	public String getGroupEnName() {
		return groupEnName;
	}

	public void setGroupEnName(String groupEnName) {
		this.groupEnName = groupEnName;
	}
	@Column(name = "groupName")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	

}
