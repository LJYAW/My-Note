package com.sino.bpm.entity;

import javax.persistence.*;

/**
 * @ClassName CmdbResTypeIndicators
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/13 17:12
 * @Version 1.0
 **/
@Entity
@Table(name = "Cmdb_ResTypeIndicators", uniqueConstraints = @UniqueConstraint(columnNames = "resTypeIndId"))
public class CmdbResTypeIndicators {
    private Integer resTypeIndId;
    private Integer resClassCode;//资源分类编码
    private String  resClassName;//资源分类名称
    private Integer resTypeCode;//资源类型编码
    private String resTypeName;//资源类型名称
    private Integer indClassCode;//指标分类编码
    private String  indClassName;//指标分类名称
    private Long    indGroupID;//指标组ID
    private String  indGroupName;//指标组名称
    private Long    indItemID;//指标项ID
    private String  indItemName;//指标项编码(主要用于排序)
    private String  indicatorItem;//指标项英文名称
    private String  valueType;//取值类型
    private Integer length;//取值长度
    private Integer decimals;//取值精度
    private String  measureUnit;//取值单位
    private String  remark;//备注
    private Integer  status;//状态(1:已审核;0:未审核)

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getResTypeIndId() {
        return resTypeIndId;
    }

    public void setResTypeIndId(Integer resTypeIndId) {
        this.resTypeIndId = resTypeIndId;
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

    public Integer getIndClassCode() {
        return indClassCode;
    }

    public void setIndClassCode(Integer indClassCode) {
        this.indClassCode = indClassCode;
    }

    public String getIndClassName() {
        return indClassName;
    }

    public void setIndClassName(String indClassName) {
        this.indClassName = indClassName;
    }

    public Long getIndGroupID() {
        return indGroupID;
    }

    public void setIndGroupID(Long indGroupID) {
        this.indGroupID = indGroupID;
    }

    public String getIndGroupName() {
        return indGroupName;
    }

    public void setIndGroupName(String indGroupName) {
        this.indGroupName = indGroupName;
    }

    public Long getIndItemID() {
        return indItemID;
    }

    public void setIndItemID(Long indItemID) {
        this.indItemID = indItemID;
    }

    public String getIndItemName() {
        return indItemName;
    }

    public void setIndItemName(String indItemName) {
        this.indItemName = indItemName;
    }

    public String getIndicatorItem() {
        return indicatorItem;
    }

    public void setIndicatorItem(String indicatorItem) {
        this.indicatorItem = indicatorItem;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
