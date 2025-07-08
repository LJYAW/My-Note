package com.sino.fas.res.mdlware.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ProjectName: SinoITOM_BSB
 * @Package: com.sino.fas.res.tom.entity
 * @ClassName: MiddlewareMonitorCpu
 * @auther: Mr.Lp
 * @date 2019/5/28 14:59
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

@Entity
@Table(name = "MiddlewareMonitor_cpu")
public class MiddlewareMonitorCpu implements java.io.Serializable  {

    private String id;
    private String monitorId;
    private String middleId;
    private Integer loadedClassCount;
    private Integer totalLoadedClassCount;
    private Integer unloadedClassCount;
    private String cpu;
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

    public Integer getLoadedClassCount() {
        return loadedClassCount;
    }

    public void setLoadedClassCount(Integer loadedClassCount) {
        this.loadedClassCount = loadedClassCount;
    }

    public Integer getTotalLoadedClassCount() {
        return totalLoadedClassCount;
    }

    public void setTotalLoadedClassCount(Integer totalLoadedClassCount) {
        this.totalLoadedClassCount = totalLoadedClassCount;
    }

    public String getMiddleId() {
        return middleId;
    }

    public void setMiddleId(String middleId) {
        this.middleId = middleId;
    }

    public Integer getUnloadedClassCount() {
        return unloadedClassCount;
    }

    public void setUnloadedClassCount(Integer unloadedClassCount) {
        this.unloadedClassCount = unloadedClassCount;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(String monitorTime) {
        this.monitorTime = monitorTime;
    }
}
