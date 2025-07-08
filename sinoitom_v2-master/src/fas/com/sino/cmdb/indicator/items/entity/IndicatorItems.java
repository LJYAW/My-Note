package com.sino.cmdb.indicator.items.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-7-26
 */
@Entity
@Table(name = "Cmdb_IndicatorItems", uniqueConstraints = @UniqueConstraint(columnNames = "indItemID"))
public class IndicatorItems {
	
//	`IndItemID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '指标项ID',
//	  `IndClassCode` int(1) DEFAULT NULL COMMENT '指标分类编码',
//	  `IndClassName` varchar(32) DEFAULT NULL COMMENT '指标分类名称',
//	  `IndGroupID` bigint(20) DEFAULT NULL COMMENT '指标组ID',
//	  `IndGroupName` varchar(32) DEFAULT NULL COMMENT '指标组名称',
//	  `IndicatorItem` varchar(32) DEFAULT NULL COMMENT '指标项名次',
//	  `IndItemName` varchar(32) DEFAULT NULL COMMENT '指标项显示名称',
//	  `FieldNames` varchar(256) DEFAULT NULL COMMENT '字段名',
//	  `FieldQty` int(1) DEFAULT '0' COMMENT '字段数量',
//	  `ValueType` varchar(32) DEFAULT NULL COMMENT '取值类型',
//	  `Length` int(1) DEFAULT NULL COMMENT '长度',
//	  `Decimals` int(1) DEFAULT NULL COMMENT '小数位数',
//	  `MeasureUnit` varchar(16) DEFAULT NULL COMMENT '度量单位',
//	  `Remark` varchar(255) DEFAULT NULL COMMENT '备注',
//	  `Status` int(1) DEFAULT '0' COMMENT '状态(1:已审核;0:未审核)',
//	  `FieldSpilt` varchar(2) NOT NULL DEFAULT '' COMMENT '字段分割符',
//	  `KVSplit` char(1) NOT NULL DEFAULT '' COMMENT '键值分割符',

	private Long indItemID;		//int 1
	private Integer indClassCode;		//int 1
	private String indClassName;		//varchar 32
	private Long indGroupID;		//int 2
	private String indGroupName;		//varchar 32
	private String indicatorItem;		//varchar 32
	private String indItemName;		//varchar 32
	private String valueType;		//varchar 32
	private Integer length;		//int 1
	private Integer decimals;		//int 1
	private String measureUnit;		//varchar 16
	private String remark;		//varchar 255
	private Integer status;		//varchar 255
	
	private String fieldNames;
	private Integer fieldQty;
	private String fieldSpilt;
	private String kVSplit;
	

	/** default constructor */
	public IndicatorItems() {
	}

	@Id
	@Column(name = "indItemID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getIndItemID() {
		return indItemID;
	}

	public void setIndItemID(Long indItemID) {
		this.indItemID = indItemID;
	}
	
	

	@Column(name = "indClassCode")
	public Integer getIndClassCode() {
		return indClassCode;
	}


	

	public void setIndClassCode(Integer indClassCode) {
		this.indClassCode = indClassCode;
	}

	@Column(name = "indClassName")
	public String getIndClassName() {
		return indClassName;
	}


	public void setIndClassName(String indClassName) {
		this.indClassName = indClassName;
	}

	@Column(name = "indGroupID")
	public Long getIndGroupID() {
		return indGroupID;
	}

	public void setIndGroupID(Long indGroupID) {
		this.indGroupID = indGroupID;
	}

	@Column(name = "indGroupName")
	public String getIndGroupName() {
		return indGroupName;
	}


	public void setIndGroupName(String indGroupName) {
		this.indGroupName = indGroupName;
	}


	@Column(name = "indicatorItem")
	public String getIndicatorItem() {
		return indicatorItem;
	}


	public void setIndicatorItem(String indicatorItem) {
		this.indicatorItem = indicatorItem;
	}

	@Column(name = "valueType")
	public String getValueType() {
		return valueType;
	}


	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	@Column(name = "length")
	public Integer getLength() {
		return length;
	}


	public void setLength(Integer length) {
		this.length = length;
	}
	
	

	@Column(name = "measureUnit")
	public String getMeasureUnit() {
		return measureUnit;
	}


	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "IndItemName")
	public String getIndItemName() {
		return indItemName;
	}

	public void setIndItemName(String indItemName) {
		this.indItemName = indItemName;
	}

	@Column(name = "Decimals")
	public Integer getDecimals() {
		return decimals;
	}

	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String fieldNames) {
		this.fieldNames = fieldNames;
	}

	public Integer getFieldQty() {
		return fieldQty;
	}

	public void setFieldQty(Integer fieldQty) {
		this.fieldQty = fieldQty;
	}

	public String getFieldSpilt() {
		return fieldSpilt;
	}

	public void setFieldSpilt(String fieldSpilt) {
		this.fieldSpilt = fieldSpilt;
	}

	public String getkVSplit() {
		return kVSplit;
	}

	public void setkVSplit(String kVSplit) {
		this.kVSplit = kVSplit;
	}

	
	
	
	
	
}
