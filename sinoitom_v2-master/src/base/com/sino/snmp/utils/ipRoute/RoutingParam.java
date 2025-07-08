package com.sino.snmp.utils.ipRoute;

public class RoutingParam {
	
	private String ifDescr;
	private String intfIp;
	private String netIp;
	private Integer maskLength;
	private String nextHopIp;
	
	public String getIfDescr() {
		return ifDescr;
	}
	public void setIfDescr(String ifDescr) {
		this.ifDescr = ifDescr;
	}
	public String getIntfIp() {
		return intfIp;
	}
	public void setIntfIp(String intfIp) {
		this.intfIp = intfIp;
	}
	public String getNetIp() {
		return netIp;
	}
	public void setNetIp(String netIp) {
		this.netIp = netIp;
	}
	public Integer getMaskLength() {
		return maskLength;
	}
	public void setMaskLength(Integer maskLength) {
		this.maskLength = maskLength;
	}
	public String getNextHopIp() {
		return nextHopIp;
	}
	public void setNextHopIp(String nextHopIp) {
		this.nextHopIp = nextHopIp;
	}
	@Override
	public String toString() {
		return "RoutingParam [ifDescr=" + ifDescr + ", intfIp=" + intfIp
				+ ", netIp=" + netIp + ", maskLength=" + maskLength
				+ ", nextHopIp=" + nextHopIp + "]";
	}
	
	

}
