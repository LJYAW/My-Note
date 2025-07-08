package com.sino.fas.res.host.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ncm_SPMVlan")
public class VlanInfo {
	private Long vlan_Id;              // bigint(19) NOT NULL
	private Long switch_Id;         //bigint(19) NOT NULL
	private Long ifIndex;             //bigint(19) NOT NULL
	private Long vlan;                  //    int(10) NULL
	private String vlan_Name;            //   varchar(64) NULL
	
	@Id
	@Column(name = "Vlan_Id", unique = true, nullable = false)
	public Long getVlan_Id() {
		return vlan_Id;
	}
	public void setVlan_Id(Long vlan_Id) {
		this.vlan_Id = vlan_Id;
	}
	
	@Column(name = "Switch_Id")
	public Long getSwitch_Id() {
		return switch_Id;
	}
	public void setSwitch_Id(Long switch_Id) {
		this.switch_Id = switch_Id;
	}
	
	@Column(name = "IfIndex")
	public Long getIfIndex() {
		return ifIndex;
	}
	public void setIfIndex(Long ifIndex) {
		this.ifIndex = ifIndex;
	}
	
	@Column(name = "Vlan")
	public Long getVlan() {
		return vlan;
	}
	public void setVlan(Long vlan) {
		this.vlan = vlan;
	}
	
	@Column(name = "Vlan_Name")
	public String getVlan_Name() {
		return vlan_Name;
	}
	public void setVlan_Name(String vlan_Name) {
		this.vlan_Name = vlan_Name;
	}
	
	
}
