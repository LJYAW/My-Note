package com.sino.fas.res.mdlware.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ProjectName: SinoITOM_BSB
 * @Package: com.sino.fas.res.tom.entity
 * @ClassName: MiddlewareMonitorHeap
 * @auther: Mr.Lp
 * @date 2019/5/28 14:48
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

@Entity
@Table(name = "MiddlewareMonitor_heap")
public class MiddlewareMonitorHeap implements java.io.Serializable {


    private String id;
    private String monitorId;
    private String middleId;
    private String heapcommitted;
    private String heapinit;
    private String heapmax;
    private String heapused;
    private String heap;
    private String noHeapcommitted;
    private String noHeapinit;
    private String noHeapmax;
    private String noHeapused;
    private String noheap;
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

    public String getHeapcommitted() {
        return heapcommitted;
    }

    public void setHeapcommitted(String heapcommitted) {
        this.heapcommitted = heapcommitted;
    }

    public String getHeapinit() {
        return heapinit;
    }

    public void setHeapinit(String heapinit) {
        this.heapinit = heapinit;
    }

    public String getHeapmax() {
        return heapmax;
    }

    public void setHeapmax(String heapmax) {
        this.heapmax = heapmax;
    }

    public String getHeapused() {
        return heapused;
    }

    public void setHeapused(String heapused) {
        this.heapused = heapused;
    }

    public String getHeap() {
        return heap;
    }

    public void setHeap(String heap) {
        this.heap = heap;
    }

    public String getNoHeapcommitted() {
        return noHeapcommitted;
    }

    public void setNoHeapcommitted(String noHeapcommitted) {
        this.noHeapcommitted = noHeapcommitted;
    }

    public String getNoHeapinit() {
        return noHeapinit;
    }

    public void setNoHeapinit(String noHeapinit) {
        this.noHeapinit = noHeapinit;
    }

    public String getNoHeapmax() {
        return noHeapmax;
    }

    public void setNoHeapmax(String noHeapmax) {
        this.noHeapmax = noHeapmax;
    }

    public String getNoHeapused() {
        return noHeapused;
    }

    public void setNoHeapused(String noHeapused) {
        this.noHeapused = noHeapused;
    }

    public String getNoheap() {
        return noheap;
    }

    public void setNoheap(String noheap) {
        this.noheap = noheap;
    }


    public String getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(String monitorTime) {
        this.monitorTime = monitorTime;
    }
}
