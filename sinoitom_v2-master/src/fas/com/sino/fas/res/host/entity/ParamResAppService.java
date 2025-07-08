/**
 * 
 */
package com.sino.fas.res.host.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Mr.LP
 * @date 2019-10-17下午2:57:17
 * @className ResAppService
 *
 * @Description TODO
 *
 */

public class ParamResAppService {
	
	private Long id;	//对应表Res_AppService中主键appSvcId
	private Long hostId;
	private String hostIp;
	private Long svcProcId;
	private Integer protNo;
	private String protName;
	private Integer svcPort;
	private String svcName;
	private String command;
	private Integer svcClass;
	private String appSvcName;
	private String appSvcDescr;
	private String usageDescr;
	private String svcUrl;
	
	private Integer pid;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getHostId() {
		return hostId;
	}
	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	
	public Long getSvcProcId() {
		return svcProcId;
	}
	public void setSvcProcId(Long svcProcId) {
		this.svcProcId = svcProcId;
	}
	public Integer getProtNo() {
		return protNo;
	}
	public void setProtNo(Integer protNo) {
		this.protNo = protNo;
	}
	public String getProtName() {
		return protName;
	}
	public void setProtName(String protName) {
		this.protName = protName;
	}
	public Integer getSvcPort() {
		return svcPort;
	}
	public void setSvcPort(Integer svcPort) {
		this.svcPort = svcPort;
	}
	public String getSvcName() {
		return svcName;
	}
	public void setSvcName(String svcName) {
		this.svcName = svcName;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Integer getSvcClass() {
		return svcClass;
	}
	public void setSvcClass(Integer svcClass) {
		this.svcClass = svcClass;
	}
	public String getAppSvcName() {
		return appSvcName;
	}
	public void setAppSvcName(String appSvcName) {
		this.appSvcName = appSvcName;
	}
	public String getAppSvcDescr() {
		return appSvcDescr;
	}
	public void setAppSvcDescr(String appSvcDescr) {
		this.appSvcDescr = appSvcDescr;
	}
	public String getUsageDescr() {
		return usageDescr;
	}
	public void setUsageDescr(String usageDescr) {
		this.usageDescr = usageDescr;
	}
	public String getSvcUrl() {
		return svcUrl;
	}
	public void setSvcUrl(String svcUrl) {
		this.svcUrl = svcUrl;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	

}
