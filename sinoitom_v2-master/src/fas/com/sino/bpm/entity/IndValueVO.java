package com.sino.bpm.entity;

/**
 * @ClassName IndValueVO
 * @Description TODO
 * @Author fengyao
 * Date 2020/6/17 15:02
 * @Version 1.0
 **/
public class IndValueVO {
    private String indGroupName;//'指标组名称',
    private String indItemEnName;//'巡检指标英文名称',
    private String indItemName;//'巡检指标显示名称',
    private String value;//指标值
    private String measureUnit ;// '取值单位',

    public String getIndGroupName() {
        return indGroupName;
    }

    public void setIndGroupName(String indGroupName) {
        this.indGroupName = indGroupName;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
}
