package com.sino.snmp.entity;

public class SnmpSearch {

	private String ip_address;
	
	private String scope;

	private int snmp_version;
	
	private int snmp_versionV1;
	
	private int snmp_versionV3;
	
	private int snmpPort;

	private int max_hop;
	
	private String ro_community_string;
	
	private String rw_community_string;

	private String username;

	private String auth_protocol;

	private String auth_password;

	private int security_level;

	private String privacy_protocol;

	private String privacy_password;

	private String context;
	
	private int timeout;
	
	private int retries;
	private String topoMethod;
	private String netRange;

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public int getSnmp_version() {
		return snmp_version;
	}

	public void setSnmp_version(int snmp_version) {
		this.snmp_version = snmp_version;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuth_protocol() {
		return auth_protocol;
	}

	public void setAuth_protocol(String auth_protocol) {
		this.auth_protocol = auth_protocol;
	}

	public String getAuth_password() {
		return auth_password;
	}

	public void setAuth_password(String auth_password) {
		this.auth_password = auth_password;
	}

	public int getSecurity_level() {
		return security_level;
	}

	public void setSecurity_level(int security_level) {
		this.security_level = security_level;
	}

	public String getPrivacy_protocol() {
		return privacy_protocol;
	}

	public void setPrivacy_protocol(String privacy_protocol) {
		this.privacy_protocol = privacy_protocol;
	}

	public String getPrivacy_password() {
		return privacy_password;
	}

	public void setPrivacy_password(String privacy_password) {
		this.privacy_password = privacy_password;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public int getSnmp_versionV1() {
		return snmp_versionV1;
	}

	public void setSnmp_versionV1(int snmp_versionV1) {
		this.snmp_versionV1 = snmp_versionV1;
	}

	public int getSnmp_versionV3() {
		return snmp_versionV3;
	}

	public void setSnmp_versionV3(int snmp_versionV3) {
		this.snmp_versionV3 = snmp_versionV3;
	}

	public String getRo_community_string() {
		return ro_community_string;
	}

	public void setRo_community_string(String ro_community_string) {
		this.ro_community_string = ro_community_string;
	}

	public String getRw_community_string() {
		return rw_community_string;
	}

	public void setRw_community_string(String rw_community_string) {
		this.rw_community_string = rw_community_string;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public int getMax_hop() {
		return max_hop;
	}

	public void setMax_hop(int max_hop) {
		this.max_hop = max_hop;
	}

	public int getSnmpPort() {
		return snmpPort;
	}

	public void setSnmpPort(int snmpPort) {
		this.snmpPort = snmpPort;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	

	public String getTopoMethod() {
		return topoMethod;
	}

	public void setTopoMethod(String topoMethod) {
		this.topoMethod = topoMethod;
	}

	
	public String getNetRange() {
		return netRange;
	}

	public void setNetRange(String netRange) {
		this.netRange = netRange;
	}

	@Override
	public String toString() {
		return "SnmpSearch [ip_address=" + ip_address + ", scope=" + scope
				+ ", snmp_version=" + snmp_version + ", snmp_versionV1="
				+ snmp_versionV1 + ", snmp_versionV3=" + snmp_versionV3
				+ ", snmpPort=" + snmpPort + ", max_hop=" + max_hop
				+ ", ro_community_string=" + ro_community_string
				+ ", rw_community_string=" + rw_community_string + ", username="
				+ username + ", auth_protocol=" + auth_protocol
				+ ", auth_password=" + auth_password + ", security_level="
				+ security_level + ", privacy_protocol=" + privacy_protocol
				+ ", privacy_password=" + privacy_password + ", context="
				+ context + ", timeout=" + timeout + ", retries=" + retries
				+ "]";
	}
	
	
}
