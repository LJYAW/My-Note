package com.sino.fas.res.net.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ncm_FDBTable")
public class NcmFDBTable {
	public NcmFDBTable() {
		super();
	}
	public NcmFDBTable(NcmFDBTable fdb, String portName) {
		super();
		this.FDBTable_ID = fdb.getFDBTable_ID();
		this.switch_ID = fdb.getSwitch_ID();
		this.vlan_ID = fdb.getVlan_ID();
		this.macAddress = fdb.getMacAddress();
		this.fdbPort = fdb.getFdbPort();
		this.is_TrunkPort = fdb.getIs_TrunkPort();
		this.port_Status = fdb.getPort_Status();
		this.is_MultiVlan = fdb.getIs_MultiVlan();
	}
	private Long FDBTable_ID;      //bigint(19) NOT NULL
	private Long switch_ID;        //bigint(19) NOT NULL
	private Long vlan_ID;          //bigint(19) NOT NULL
	private String macAddress;     //varchar(17) NOT NULL
	private Integer fdbPort;
	private Integer is_TrunkPort;  //int(10) NOT NULL
	private Integer port_Status;   //int(10) NOT NULL
	private Integer is_MultiVlan;  //int(10) NOT NUL
	
	@Id
	@Column(name = "FDBTable_ID", unique = true, nullable = false)
	public Long getFDBTable_ID() {
		return FDBTable_ID;
	}
	public void setFDBTable_ID(Long fDBTable_ID) {
		FDBTable_ID = fDBTable_ID;
	}
	
	@Column(name = "Switch_ID", nullable = false)
	public Long getSwitch_ID() {
		return switch_ID;
	}
	public void setSwitch_ID(Long switch_ID) {
		this.switch_ID = switch_ID;
	}
	
	@Column(name = "Vlan_ID", nullable = false)
	public Long getVlan_ID() {
		return vlan_ID;
	}
	public void setVlan_ID(Long vlan_ID) {
		this.vlan_ID = vlan_ID;
	}
	
	@Column(name = "MacAddress", nullable = false)
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	@Column(name = "FdbPort")
	public Integer getFdbPort() {
		return fdbPort;
	}
	public void setFdbPort(Integer fdbPort) {
		this.fdbPort = fdbPort;
	}
	
	@Column(name = "Is_TrunkPort", nullable = false)
	public Integer getIs_TrunkPort() {
		return is_TrunkPort;
	}
	public void setIs_TrunkPort(Integer is_TrunkPort) {
		this.is_TrunkPort = is_TrunkPort;
	}
	
	@Column(name = "Port_Status", nullable = false)
	public Integer getPort_Status() {
		return port_Status;
	}
	public void setPort_Status(Integer port_Status) {
		this.port_Status = port_Status;
	}
	
	@Column(name = "Is_MultiVlan", nullable = false)
	public Integer getIs_MultiVlan() {
		return is_MultiVlan;
	}
	public void setIs_MultiVlan(Integer is_MultiVlan) {
		this.is_MultiVlan = is_MultiVlan;
	}
}
