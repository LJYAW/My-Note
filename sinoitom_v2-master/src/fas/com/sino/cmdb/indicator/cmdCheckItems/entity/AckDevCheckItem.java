package com.sino.cmdb.indicator.cmdCheckItems.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Ack_DevCheckItem", uniqueConstraints = @UniqueConstraint(columnNames = "devCheckItemId"))
public class AckDevCheckItem {
	
	private Long devCheckItemId;
	private Long devId;
	private Integer checkItemId;
	@Id
	@Column(name="devCheckItemId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getDevCheckItemId() {
		return devCheckItemId;
	}
	public void setDevCheckItemId(Long devCheckItemId) {
		this.devCheckItemId = devCheckItemId;
	}
	@Column(name="devId")
	public Long getDevId() {
		return devId;
	}
	public void setDevId(Long devId) {
		this.devId = devId;
	}
	@Column(name="checkItemId")
	public Integer getCheckItemId() {
		return checkItemId;
	}
	public void setCheckItemId(Integer checkItemId) {
		this.checkItemId = checkItemId;
	}
	@Override
	public String toString() {
		return "AckDevCheckItem [devCheckItemId=" + devCheckItemId + ", devId="
				+ devId + ", checkItemId=" + checkItemId + "]";
	}
	
	
	
}
