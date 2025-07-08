package com.sino.base.system.entity;

public class NetIpInfo {
	private String dnsIpaddr;
	private String gateway;
	private String status;
	private String macAddr;
	private String ipMask;
	private String ipAddr;
	public String getDnsIpaddr() {
		return dnsIpaddr;
	}
	public void setDnsIpaddr(String dnsIpaddr) {
		this.dnsIpaddr = dnsIpaddr;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
	public String getIpMask() {
		return ipMask;
	}
	public void setIpMask(String ipMask) {
		this.ipMask = ipMask;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
}
