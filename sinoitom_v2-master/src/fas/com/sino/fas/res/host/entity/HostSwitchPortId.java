package com.sino.fas.res.host.entity;

import javax.persistence.Column;

public class HostSwitchPortId implements java.io.Serializable{
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ifIndex ^ (ifIndex >>> 32));
		result = prime * result + (int) (switchId ^ (switchId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HostSwitchPortId other = (HostSwitchPortId) obj;
		if (ifIndex != other.ifIndex)
			return false;
		if (switchId != other.switchId)
			return false;
		return true;
	}

	public HostSwitchPortId() {
		super();
	}
	
	public HostSwitchPortId(long switchId, long ifIndex) {
		super();
		this.switchId = switchId;
		this.ifIndex = ifIndex;
	}
	private static final long serialVersionUID = -2637497590639336783L;
	
	private long switchId;//  Switch_Id               
	private long ifIndex;//  IfIndex 
	
	@Column(name = "Switch_Id", nullable = false)
	public long getSwitchId() {
		return switchId;
	}
	public void setSwitchId(long switchId) {
		this.switchId = switchId;
	}
	
	@Column(name = "IfIndex", nullable = false)
	public long getIfIndex() {
		return ifIndex;
	}
	public void setIfIndex(long ifIndex) {
		this.ifIndex = ifIndex;
	}
	
}
