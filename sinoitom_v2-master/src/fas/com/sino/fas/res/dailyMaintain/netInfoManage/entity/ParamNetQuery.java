/*
 * 文件名： ParamNetQuery.java
 * 
 * 创建日期： 2014-4-28
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.fas.res.dailyMaintain.netInfoManage.entity;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision$
 *
 * @since 2014-4-28
 */
public class ParamNetQuery {
	private String sw_IP_1;
	private String sw_IP_2;
	private String sw_IP_3;
	private String sw_IP_4;
	private Integer switchPort;
	private String ifName;
	
	private String net_IP_1;
	private String net_IP_2;
	private String net_IP_3;
	private String net_IP_4;
	private String hostMac;
	private Integer bindStatus;
	private String infoPointNo;
	private Integer operStatus;
	private Integer ipHostTypeId;
	private String serialNO;
	private String vlanId;
	private String userId;
	
	public String getSerialNO() {
		return serialNO;
	}
	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}
	public String getVlanId() {
		return vlanId;
	}
	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Integer getIpHostTypeId() {
		return ipHostTypeId;
	}
	public void setIpHostTypeId(Integer ipHostTypeId) {
		this.ipHostTypeId = ipHostTypeId;
	}
	public String getInfoPointNo() {
		return infoPointNo;
	}
	public void setInfoPointNo(String infoPointNo) {
		this.infoPointNo = infoPointNo;
	}
	public Integer getOperStatus() {
		return operStatus;
	}
	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}
	
	public Integer getBindStatus() {
		return bindStatus;
	}
	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
	public String getIfName() {
		return ifName;
	}
	public void setIfName(String ifName) {
		this.ifName = ifName;
	}
	public String getSw_IP_1() {
		return sw_IP_1;
	}
	public void setSw_IP_1(String sw_IP_1) {
		this.sw_IP_1 = sw_IP_1;
	}
	public String getSw_IP_2() {
		return sw_IP_2;
	}
	public void setSw_IP_2(String sw_IP_2) {
		this.sw_IP_2 = sw_IP_2;
	}
	public String getSw_IP_3() {
		return sw_IP_3;
	}
	public void setSw_IP_3(String sw_IP_3) {
		this.sw_IP_3 = sw_IP_3;
	}
	public String getSw_IP_4() {
		return sw_IP_4;
	}
	public void setSw_IP_4(String sw_IP_4) {
		this.sw_IP_4 = sw_IP_4;
	}
	public Integer getSwitchPort() {
		return switchPort;
	}
	public void setSwitchPort(Integer switchPort) {
		this.switchPort = switchPort;
	}
	public String getNet_IP_1() {
		return net_IP_1;
	}
	public void setNet_IP_1(String net_IP_1) {
		this.net_IP_1 = net_IP_1;
	}
	public String getNet_IP_2() {
		return net_IP_2;
	}
	public void setNet_IP_2(String net_IP_2) {
		this.net_IP_2 = net_IP_2;
	}
	public String getNet_IP_3() {
		return net_IP_3;
	}
	public void setNet_IP_3(String net_IP_3) {
		this.net_IP_3 = net_IP_3;
	}
	public String getNet_IP_4() {
		return net_IP_4;
	}
	public void setNet_IP_4(String net_IP_4) {
		this.net_IP_4 = net_IP_4;
	}
	public String getHostMac() {
		return hostMac;
	}
	public void setHostMac(String hostMac) {
		this.hostMac = hostMac;
	}
	
}
