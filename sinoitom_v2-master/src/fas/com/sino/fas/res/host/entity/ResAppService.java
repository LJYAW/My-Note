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

@Entity
@Table(name = "Res_AppService")
public class ResAppService {
	
//	`appSvcId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '应用服务ID',
//	  `hostId` bigint(20) DEFAULT NULL COMMENT '服务器ID',
//	  `hostIp` varchar(16) DEFAULT NULL COMMENT '服务器IP',
//	  `svcProcId` bigint(20) DEFAULT NULL COMMENT '服务进程ID',
//	  `protNo` int(2) DEFAULT NULL COMMENT '协议号',
//	  `protName` varchar(8) DEFAULT NULL COMMENT '协议名称（TCP、UDP等）',
//	  `svcPort` int(11) DEFAULT NULL COMMENT '服务端口',
//	  `svcName` varchar(32) DEFAULT NULL COMMENT '服务名称',
//	  `command` varchar(64) DEFAULT NULL COMMENT '服务命令',
//	  `svcClass` int(1) DEFAULT NULL COMMENT '服务分类（1：Web；2：App；3：DB）（手工维护）',
//	  `appSvcName` varchar(32) DEFAULT NULL COMMENT '应用服务名称（手工维护）',
//	  `appSvcDescr` varchar(255) DEFAULT NULL COMMENT '服务描述（手工维护）',
//	  `usageDescr` varchar(255) DEFAULT NULL COMMENT '用途描述（手工维护）',
//	  `svcUrl` varchar(1024) DEFAULT NULL COMMENT '服务访问URL',
	
	private Long appSvcId;
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
	
	private String orgID;
	
	private Integer resClassCode;
	private String resClassName;
	private Integer resTypeCode;
	private String resTypeName;
	
	private Integer svcStatus;
	private Integer respTime;
	private Integer adminStatus;
	
	private Integer pid;
	
	@Id
	public Long getAppSvcId() {
		return appSvcId;
	}
	public void setAppSvcId(Long appSvcId) {
		this.appSvcId = appSvcId;
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
	public String getOrgID() {
		return orgID;
	}
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	public Integer getResClassCode() {
		return resClassCode;
	}
	public void setResClassCode(Integer resClassCode) {
		this.resClassCode = resClassCode;
	}
	public String getResClassName() {
		return resClassName;
	}
	public void setResClassName(String resClassName) {
		this.resClassName = resClassName;
	}
	public Integer getResTypeCode() {
		return resTypeCode;
	}
	public void setResTypeCode(Integer resTypeCode) {
		this.resTypeCode = resTypeCode;
	}
	public String getResTypeName() {
		return resTypeName;
	}
	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}
	public Integer getSvcStatus() {
		return svcStatus;
	}
	public void setSvcStatus(Integer svcStatus) {
		this.svcStatus = svcStatus;
	}
	public Integer getRespTime() {
		return respTime;
	}
	public void setRespTime(Integer respTime) {
		this.respTime = respTime;
	}
	public Integer getAdminStatus() {
		return adminStatus;
	}
	public void setAdminStatus(Integer adminStatus) {
		this.adminStatus = adminStatus;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	

}
