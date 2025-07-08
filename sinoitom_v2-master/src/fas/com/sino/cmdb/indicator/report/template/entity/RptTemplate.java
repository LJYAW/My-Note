/**
 * 
 */
package com.sino.cmdb.indicator.report.template.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Mr.LP
 * @date 2019-7-22下午5:52:28
 * @className RptTemplate
 *
 * @Description TODO
 *
 */

@Entity
@Table(name = "Rpt_Template", uniqueConstraints = @UniqueConstraint(columnNames = "tmplId"))
public class RptTemplate {
	
//	`tmplId` int(11) NOT NULL AUTO_INCREMENT COMMENT '模板Id',
//	  `tmplType` int(1) DEFAULT NULL COMMENT '模板类型（1：普通模板；2：符号模板）',
//	  `tmplName` varchar(32) DEFAULT NULL COMMENT '模板名称',
//	  `tabTitle` varchar(128) DEFAULT NULL COMMENT '标题',

	private Integer tmplId;
	private Integer tmplType;
	private String tmplName;
	private String tabTitle;
	private Integer status;
	private Integer tableWidth;
	
	@Id
	@Column(name = "tmplId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getTmplId() {
		return tmplId;
	}
	public void setTmplId(Integer tmplId) {
		this.tmplId = tmplId;
	}
	public Integer getTmplType() {
		return tmplType;
	}
	public void setTmplType(Integer tmplType) {
		this.tmplType = tmplType;
	}
	public String getTmplName() {
		return tmplName;
	}
	public void setTmplName(String tmplName) {
		this.tmplName = tmplName;
	}
	public String getTabTitle() {
		return tabTitle;
	}
	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getTableWidth() {
		return tableWidth;
	}
	public void setTableWidth(Integer tableWidth) {
		this.tableWidth = tableWidth;
	}
	
	
	
}
