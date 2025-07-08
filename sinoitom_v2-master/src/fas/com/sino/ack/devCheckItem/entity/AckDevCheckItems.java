package com.sino.ack.devCheckItem.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ack_DevCheckItems")
public class AckDevCheckItems {
	private Long devChkItemId;
	private Long devId;
	private String devIp;
	private Integer resClassCode;
	private String resClassName;
	private Integer resTypeCode;
	private String resTypeName;
	private Integer parseRuldId;
	private Long indItemID;
	private String indGroupName;
	private String indItemEnName;
	private String indItemName;
	private String valueType;
	private Integer length;
	private Integer decimals;
	private String measureUnit;
	private String checkCmd;
	private Integer status;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getDevChkItemId() {
		return devChkItemId;
	}
	public void setDevChkItemId(Long devChkItemId) {
		this.devChkItemId = devChkItemId;
	}
	public Long getDevId() {
		return devId;
	}
	public void setDevId(Long devId) {
		this.devId = devId;
	}
	public String getDevIp() {
		return devIp;
	}
	public void setDevIp(String devIp) {
		this.devIp = devIp;
	}
	
	
	public Long getIndItemID() {
		return indItemID;
	}
	public void setIndItemID(Long indItemID) {
		this.indItemID = indItemID;
	}
	public String getIndItemEnName() {
		return indItemEnName;
	}
	public void setIndItemEnName(String indItemEnName) {
		this.indItemEnName = indItemEnName;
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
	public String getCheckCmd() {
		return checkCmd;
	}
	public void setCheckCmd(String checkCmd) {
		this.checkCmd = checkCmd;
	}
	public Integer getResClassCode() {
		return resClassCode;
	}
	public void setResClassCode(Integer resClassCode) {
		this.resClassCode = resClassCode;
	}
	public String getResClassName() {
		return resClassName;
	}
	public void setResClassName(String resClassName) {
		this.resClassName = resClassName;
	}
	public Integer getResTypeCode() {
		return resTypeCode;
	}
	public void setResTypeCode(Integer resTypeCode) {
		this.resTypeCode = resTypeCode;
	}
	public String getResTypeName() {
		return resTypeName;
	}
	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}
	public String getIndItemName() {
		return indItemName;
	}
	public void setIndItemName(String indItemName) {
		this.indItemName = indItemName;
	}
	public String getIndGroupName() {
		return indGroupName;
	}
	public void setIndGroupName(String indGroupName) {
		this.indGroupName = indGroupName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getParseRuldId() {
		return parseRuldId;
	}
	public void setParseRuldId(Integer parseRuldId) {
		this.parseRuldId = parseRuldId;
	}
	
	
	

}
