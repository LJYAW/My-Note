package com.sino.fas.res.mdlware.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "MiddlewareMonitor")
public class MiddlewareMonitor implements java.io.Serializable {

    private String id;
    private String devIpAddr;
    private String content;
    private String vmVendor;
    private String vmName;
    private String vmVersion;
    private String startTime;
    private String uptime;
    private Integer intervaltime;
    private String tomcatVersion;
    private String createTime;

    private String userName;
    private String psWord;

    private String vms;     //VM参数
    private String libraryPath;     //库路径
    private String classPath;   //类路径
    private String bootClassPath;       //引导类路径

    private Integer status;
    private String monitorTime;
    
    private Integer middleType;

    @Id
    @Column(name = "Id", unique = true, nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevIpAddr() {
        return devIpAddr;
    }

    public void setDevIpAddr(String devIpAddr) {
        this.devIpAddr = devIpAddr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVmVendor() {
        return vmVendor;
    }

    public void setVmVendor(String vmVendor) {
        this.vmVendor = vmVendor;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getVmVersion() {
        return vmVersion;
    }

    public void setVmVersion(String vmVersion) {
        this.vmVersion = vmVersion;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTomcatVersion() {
        return tomcatVersion;
    }

    public void setTomcatVersion(String tomcatVersion) {
        this.tomcatVersion = tomcatVersion;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPsWord() {
        return psWord;
    }

    public void setPsWord(String psWord) {
        this.psWord = psWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getVms() {
        return vms;
    }

    public void setVms(String vms) {
        this.vms = vms;
    }

    public String getLibraryPath() {
        return libraryPath;
    }

    public void setLibraryPath(String libraryPath) {
        this.libraryPath = libraryPath;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getBootClassPath() {
        return bootClassPath;
    }

    public void setBootClassPath(String bootClassPath) {
        this.bootClassPath = bootClassPath;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIntervaltime() {
        return intervaltime;
    }

    public void setIntervaltime(Integer intervaltime) {
        this.intervaltime = intervaltime;
    }

    public String getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(String monitorTime) {
        this.monitorTime = monitorTime;
    }

	public Integer getMiddleType() {
		return middleType;
	}

	public void setMiddleType(Integer middleType) {
		this.middleType = middleType;
	}
    
    
}
