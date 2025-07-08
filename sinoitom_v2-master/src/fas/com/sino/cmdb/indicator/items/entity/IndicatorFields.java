/**
 * 
 */
package com.sino.cmdb.indicator.items.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Mr.LP
 * @date 2019-9-19上午10:20:01
 * @className IndicatorFields
 *
 * @Description TODO
 *
 */

@Entity
@Table(name = "Cmdb_IndicatorFields", uniqueConstraints = @UniqueConstraint(columnNames = "indFieldId"))
public class IndicatorFields {
	
	private Integer indFieldId;
	private Long indItemID;
	private Integer fieldNo;
	private String fieldNameEn;
	private String fieldName;
	private String valueType;
	private Integer length;
	private Integer decimals;
	private String measureUnit;
	
	
	@Id
	@Column(name = "indFieldId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getIndFieldId() {
		return indFieldId;
	}
	public void setIndFieldId(Integer indFieldId) {
		this.indFieldId = indFieldId;
	}
	public Long getIndItemID() {
		return indItemID;
	}
	public void setIndItemID(Long indItemID) {
		this.indItemID = indItemID;
	}
	public Integer getFieldNo() {
		return fieldNo;
	}
	public void setFieldNo(Integer fieldNo) {
		this.fieldNo = fieldNo;
	}
	public String getFieldNameEn() {
		return fieldNameEn;
	}
	public void setFieldNameEn(String fieldNameEn) {
		this.fieldNameEn = fieldNameEn;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getDecimals() {
		return decimals;
	}
	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	
	
	

}
