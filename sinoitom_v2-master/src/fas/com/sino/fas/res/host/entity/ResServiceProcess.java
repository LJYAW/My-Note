/**
 * 
 */
package com.sino.fas.res.host.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Mr.LP
 * @date 2019-10-17下午2:18:39
 * @className ResServiceProcess
 *
 * @Description TODO
 *
 */
@Entity
@Table(name = "Res_SvcProcess")
public class ResServiceProcess {
	
//	`svcProcId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '服务进程ID',
//	  `hostId` bigint(20) NOT NULL DEFAULT '0' COMMENT '主机ID',
//	  `hostIp` varchar(16) DEFAULT NULL COMMENT '主机IP',
//	  `pid` int(11) DEFAULT NULL COMMENT '进程ID',
//	  `ppid` int(11) DEFAULT NULL COMMENT '父进程ID',
//	  `startCmd` varchar(2048) DEFAULT NULL COMMENT '启动命令',
//	  `procType` varchar(16) DEFAULT NULL COMMENT '进程类型',
//	  `procName` varchar(64) DEFAULT NULL COMMENT '进程名称',
//	  `procDescr` varchar(256) DEFAULT NULL COMMENT '进程描述',
//	  `flag` int(1) DEFAULT '1' COMMENT '进程标志（0：系统进程；1：服务进程）',
//	  `status` int(1) NOT NULL DEFAULT '0' COMMENT '监测状态（0：未监测；1：监测）',
//	  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
//	  `createTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
	
	private Long svcProcId;
	private Long hostId;
	private String hostIp;
	private Integer pid;
	private Integer ppid;
	private String startCmd;
	private String procType;
	private String procName;
	private String procDescr;
	private Integer flag;
	private Integer status;
	private String creator;
	private Timestamp createTime;
	
	private String orgID;
	
	private Integer resClassCode;
	private String resClassName;
	private Integer resTypeCode;
	private String resTypeName;
	
	private Integer adminStatus;
	private Integer runStatus;	//运行状态
	
	@Id
	public Long getSvcProcId() {
		return svcProcId;
	}
	public void setSvcProcId(Long svcProcId) {
		this.svcProcId = svcProcId;
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
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getPpid() {
		return ppid;
	}
	public void setPpid(Integer ppid) {
		this.ppid = ppid;
	}
	public String getStartCmd() {
		return startCmd;
	}
	public void setStartCmd(String startCmd) {
		this.startCmd = startCmd;
	}
	public String getProcType() {
		return procType;
	}
	public void setProcType(String procType) {
		this.procType = procType;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public String getProcDescr() {
		return procDescr;
	}
	public void setProcDescr(String procDescr) {
		this.procDescr = procDescr;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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
	public Integer getAdminStatus() {
		return adminStatus;
	}
	public void setAdminStatus(Integer adminStatus) {
		this.adminStatus = adminStatus;
	}
	public Integer getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}
	
	

}
