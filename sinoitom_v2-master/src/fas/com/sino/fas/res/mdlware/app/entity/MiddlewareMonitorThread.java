package com.sino.fas.res.mdlware.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @ProjectName: SinoITOM_BSB
 * @Package: com.sino.fas.res.tom.entity
 * @ClassName: MiddlewareMonitorThread
 * @auther: Mr.Lp
 * @date 2019/5/28 14:51
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

@Entity
@Table(name = "MiddlewareMonitor_thread")
public class MiddlewareMonitorThread implements java.io.Serializable {

    private String id;
    private String monitorId;
    private String middleId;
    private Long totalStartedThreadCount;
    private Integer threadCount;
    private Integer daemonThreadCount;
    private Integer peakThreadCount;
    private Long currentThreadCpuTime;
    private Long currentThreadUserTime;
    private Integer aliveSocketsCount;
    private Long maxProcessingTime;
    private Long processingTime;
    private Long requstCount;
    private Long errorCount;
    private BigDecimal bytesReceived;
    private BigDecimal bytesSend;
    private String monitorTime;



    @Id
    @Column(name = "Id", unique = true, nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getMiddleId() {
        return middleId;
    }

    public void setMiddleId(String middleId) {
        this.middleId = middleId;
    }

    public Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }

    public Integer getDaemonThreadCount() {
        return daemonThreadCount;
    }

    public void setDaemonThreadCount(Integer daemonThreadCount) {
        this.daemonThreadCount = daemonThreadCount;
    }

    public Integer getPeakThreadCount() {
        return peakThreadCount;
    }

    public void setPeakThreadCount(Integer peakThreadCount) {
        this.peakThreadCount = peakThreadCount;
    }

    public Long getCurrentThreadCpuTime() {
        return currentThreadCpuTime;
    }

    public void setCurrentThreadCpuTime(Long currentThreadCpuTime) {
        this.currentThreadCpuTime = currentThreadCpuTime;
    }

    public Long getCurrentThreadUserTime() {
        return currentThreadUserTime;
    }

    public void setCurrentThreadUserTime(Long currentThreadUserTime) {
        this.currentThreadUserTime = currentThreadUserTime;
    }

    public Integer getAliveSocketsCount() {
        return aliveSocketsCount;
    }

    public void setAliveSocketsCount(Integer aliveSocketsCount) {
        this.aliveSocketsCount = aliveSocketsCount;
    }

    public Long getMaxProcessingTime() {
        return maxProcessingTime;
    }

    public void setMaxProcessingTime(Long maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }

    public Long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(Long processingTime) {
        this.processingTime = processingTime;
    }

    public Long getRequstCount() {
        return requstCount;
    }

    public void setRequstCount(Long requstCount) {
        this.requstCount = requstCount;
    }

    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    public BigDecimal getBytesReceived() {
        return bytesReceived;
    }

    public void setBytesReceived(BigDecimal bytesReceived) {
        this.bytesReceived = bytesReceived;
    }

    public BigDecimal getBytesSend() {
        return bytesSend;
    }

    public void setBytesSend(BigDecimal bytesSend) {
        this.bytesSend = bytesSend;
    }

    public Long getTotalStartedThreadCount() {
        return totalStartedThreadCount;
    }

    public void setTotalStartedThreadCount(Long totalStartedThreadCount) {
        this.totalStartedThreadCount = totalStartedThreadCount;
    }

    public String getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(String monitorTime) {
        this.monitorTime = monitorTime;
    }
}
