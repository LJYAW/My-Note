package com.sino.fas.res.net.entity;

public class ScanParam {
	private String searchIp;		//ip
	private String snmpRoKey;		//snmp团体字
	private String gatewayIp;		//缺省网关
	private String gatewayStr;		//网关团体字
	private String arpGetMethod;	//arp获取方式
	
	public String getSearchIp() {
		return searchIp;
	}
	public void setSearchIp(String searchIp) {
		this.searchIp = searchIp;
	}
	public String getSnmpRoKey() {
		return snmpRoKey;
	}
	public void setSnmpRoKey(String snmpRoKey) {
		this.snmpRoKey = snmpRoKey;
	}
	public String getGatewayIp() {
		return gatewayIp;
	}
	public void setGatewayIp(String gatewayIp) {
		this.gatewayIp = gatewayIp;
	}
	public String getGatewayStr() {
		return gatewayStr;
	}
	public void setGatewayStr(String gatewayStr) {
		this.gatewayStr = gatewayStr;
	}
	public String getArpGetMethod() {
		return arpGetMethod;
	}
	public void setArpGetMethod(String arpGetMethod) {
		this.arpGetMethod = arpGetMethod;
	}
}
