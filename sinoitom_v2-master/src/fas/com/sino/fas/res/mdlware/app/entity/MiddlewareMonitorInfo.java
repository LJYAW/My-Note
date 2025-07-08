package com.sino.fas.res.mdlware.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ProjectName: SinoITOM_BSB
 * @Package: com.sino.fas.res.tom.entity
 * @ClassName: MiddlewareMonitorInfo
 * @auther: Mr.Lp
 * @date 2019/5/28 14:41
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

@Entity
@Table(name = "MiddlewareMonitor_info")
public class MiddlewareMonitorInfo implements java.io.Serializable  {

    private String middleId;

    private String monitorId;

    private String devIpAddr;
    private String content;
    private String monitorTime;
    private Integer intervaltime;

    private String totalMem;
    private String freeMem;
    private String swapTotal;
    private String swapFree;
    private String tomcatVersion;
    private String osName;
    private String osArch;
    private Integer availableProcessors;

    private String committedMem;

    private String jitName;     //JIT编译器名称
    private Integer jitTime;      //编译时间





    @Id
    @Column(name = "MiddleId", unique = true, nullable = false, length = 36)
    public String getMiddleId() {
        return middleId;
    }

    public void setMiddleId(String middleId) {
        this.middleId = middleId;
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


    public String getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(String monitorTime) {
        this.monitorTime = monitorTime;
    }



    public String getTotalMem() {
        return totalMem;
    }

    public void setTotalMem(String totalMem) {
        this.totalMem = totalMem;
    }

    public String getFreeMem() {
        return freeMem;
    }

    public void setFreeMem(String freeMem) {
        this.freeMem = freeMem;
    }

    public String getSwapTotal() {
        return swapTotal;
    }

    public void setSwapTotal(String swapTotal) {
        this.swapTotal = swapTotal;
    }

    public String getSwapFree() {
        return swapFree;
    }

    public void setSwapFree(String swapFree) {
        this.swapFree = swapFree;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }


    public String getJitName() {
        return jitName;
    }

    public void setJitName(String jitName) {
        this.jitName = jitName;
    }

    public Integer getJitTime() {
        return jitTime;
    }

    public void setJitTime(Integer jitTime) {
        this.jitTime = jitTime;
    }

    public Integer getAvailableProcessors() {
        return availableProcessors;
    }

    public void setAvailableProcessors(Integer availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public String getCommittedMem() {
        return committedMem;
    }

    public void setCommittedMem(String committedMem) {
        this.committedMem = committedMem;
    }


    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getTomcatVersion() {
        return tomcatVersion;
    }

    public void setTomcatVersion(String tomcatVersion) {
        this.tomcatVersion = tomcatVersion;
    }

    public Integer getIntervaltime() {
        return intervaltime;
    }

    public void setIntervaltime(Integer intervaltime) {
        this.intervaltime = intervaltime;
    }
}
