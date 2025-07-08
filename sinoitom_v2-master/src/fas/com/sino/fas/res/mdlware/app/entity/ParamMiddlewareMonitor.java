package com.sino.fas.res.mdlware.app.entity;

/**
 * @ProjectName: SinoITOM_BSB
 * @Package: com.sino.fas.res.tom.entity
 * @ClassName: ParamMiddlewareMonitor
 * @auther: Mr.Lp
 * @date 2019/5/29 17:12
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

public class ParamMiddlewareMonitor {

    private String heapcommitted;
    private String heapmax;
    private String heapused;

    private Integer threadCount;
    private Integer peakThreadCount;

    private Integer loadedClassCount;
    private Integer totalLoadedClassCount;
    private String unloadedClassCount;

    private String cpu;


    private String heapcommittedKB;
    private String heapmaxKB;
    private String heapusedKB;


    public String getUnloadedClassCount() {
        return unloadedClassCount;
    }

    public void setUnloadedClassCount(String unloadedClassCount) {
        this.unloadedClassCount = unloadedClassCount;
    }

    public String getHeapcommitted() {
        return heapcommitted;
    }

    public void setHeapcommitted(String heapcommitted) {
        this.heapcommitted = heapcommitted;
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

    public Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }

    public Integer getPeakThreadCount() {
        return peakThreadCount;
    }

    public void setPeakThreadCount(Integer peakThreadCount) {
        this.peakThreadCount = peakThreadCount;
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

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getHeapcommittedKB() {
        return heapcommittedKB;
    }

    public void setHeapcommittedKB(String heapcommittedKB) {
        this.heapcommittedKB = heapcommittedKB;
    }

    public String getHeapmaxKB() {
        return heapmaxKB;
    }

    public void setHeapmaxKB(String heapmaxKB) {
        this.heapmaxKB = heapmaxKB;
    }

    public String getHeapusedKB() {
        return heapusedKB;
    }

    public void setHeapusedKB(String heapusedKB) {
        this.heapusedKB = heapusedKB;
    }
}
