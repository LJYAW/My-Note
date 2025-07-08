package com.sino.bpm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Bpm_ResMonInds", uniqueConstraints = @UniqueConstraint(columnNames = "resMonIndId"))
public class BpmResMonInds {
	private Long resMonIndId;//'设备巡检项ID',
	private Integer taskId;
	private String taskName;
	private Long resId;//'资源ID（创建资源时，调用DBUtil.getUniqResId获取的全局唯一ID）',
	private String resIp;//'资源IP',
	private Long resIpLong;//'资源IPLong（用于加载排序）',
	private Integer resClassCode;//'资源分类编码',
	private String resClassName;//'资源分类名称',
	private Integer resTypeCode;//'资源类型编码',
	private String resTypeName;//'资源类型名称',
	private Integer resTypeIndId;//'资源类型指标ID（Cmdb_ResTypeIndicators.resTypeIndId）',
	private String indCollMode;//'指标获取方式（1：Cmd；2：Snmp）',
	private Long indItemId ;//'巡检指标ID（Cmdb_ResIndCmdParse.indItemID）',
	private String IndGroupName;//'指标组名称',
	private String indItemEnName;//'巡检指标英文名称',
	private String indItemName;//'巡检指标显示名称',
	private String valueType;//'值类型',
	private Integer length;//'值长度',
	private Integer decimals;//'小数位数',
	private String measureUnit;//'度量单位',
	private Integer flag;//'标志（0：不采集；1：采集）',

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getResMonIndId() {
		return resMonIndId;
	}

	public void setResMonIndId(Long resMonIndId) {
		this.resMonIndId = resMonIndId;
	}
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public String getResIp() {
		return resIp;
	}
	public void setResIp(String resIp) {
		this.resIp = resIp;
	}
	public Long getResIpLong() {
		return resIpLong;
	}
	public void setResIpLong(Long resIpLong) {
		this.resIpLong = resIpLong;
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
	public Long getIndItemId() {
		return indItemId;
	}
	public void setIndItemId(Long indItemId) {
		this.indItemId = indItemId;
	}
	public String getIndGroupName() {
		return IndGroupName;
	}
	public void setIndGroupName(String indGroupName) {
		IndGroupName = indGroupName;
	}
	public String getIndItemEnName() {
		return indItemEnName;
	}
	public void setIndItemEnName(String indItemEnName) {
		this.indItemEnName = indItemEnName;
	}
	public String getIndItemName() {
		return indItemName;
	}
	public void setIndItemName(String indItemName) {
		this.indItemName = indItemName;
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
	public Integer getResTypeIndId() {
		return resTypeIndId;
	}
	public void setResTypeIndId(Integer resTypeIndId) {
		this.resTypeIndId = resTypeIndId;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getIndCollMode() {
		return indCollMode;
	}

	public void setIndCollMode(String indCollMode) {
		this.indCollMode = indCollMode;
	}
}
