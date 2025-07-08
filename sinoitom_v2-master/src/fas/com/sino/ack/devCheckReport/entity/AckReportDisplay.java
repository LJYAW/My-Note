package com.sino.ack.devCheckReport.entity;

import java.util.Date;

/**
 * @author lazymagic
 * @version v1.0.0
 */
public class AckReportDisplay {

  private long id;
  private Date taskStartTime;
  private Date taskEndTime;
  private int useTime;
  private String taskName;
  private String html;

  public AckReportDisplay() {
  }

  public AckReportDisplay(long devChkRptId, int useTime) {
    this.id = devChkRptId;
    this.useTime = useTime;
  }

  public AckReportDisplay(long devChkRptId, Date taskStartTime, Date taskEndTime, int useTime) {
    this.id = devChkRptId;
    this.taskStartTime = taskStartTime;
    this.taskEndTime = taskEndTime;
    this.useTime = useTime;
  }

  public AckReportDisplay(long devChkRptId, Date taskStartTime, Date taskEndTime, int useTime, String taskName) {
    this.id = devChkRptId;
    this.taskStartTime = taskStartTime;
    this.taskEndTime = taskEndTime;
    this.useTime = useTime;
    this.taskName = taskName;
  }

  public AckReportDisplay(long devChkRptId, Date taskStartTime, Date taskEndTime, int useTime, String taskName,
    String html) {
    this.id = devChkRptId;
    this.taskStartTime = taskStartTime;
    this.taskEndTime = taskEndTime;
    this.useTime = useTime;
    this.taskName = taskName;
    this.html = html;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getHtml() {
    return html;
  }

  public void setHtml(String html) {
    this.html = html;
  }

  public Date getTaskStartTime() {
    return taskStartTime;
  }

  public void setTaskStartTime(Date taskStartTime) {
    this.taskStartTime = taskStartTime;
  }

  public Date getTaskEndTime() {
    return taskEndTime;
  }

  public void setTaskEndTime(Date taskEndTime) {
    this.taskEndTime = taskEndTime;
  }

  public Integer getUseTime() {
    return useTime;
  }

  public void setUseTime(Integer useTime) {
    this.useTime = useTime;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public void setUseTime(int useTime) {
    this.useTime = useTime;
  }
}
