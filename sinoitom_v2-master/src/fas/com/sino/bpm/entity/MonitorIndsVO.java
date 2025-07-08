package com.sino.bpm.entity;

/**
 * @ClassName monitorIndsVO
 * @Description 监测资源-监测指标菜单列表
 * @Author fengyao
 * Date 2020/7/1 15:56
 * @Version 1.0
 **/
public class MonitorIndsVO {
    private String resIp;//IP地址--BpmTaskMonRes
    private String resClassName;//'资源分类',
    private String resTypeName;//'资源类型',
    private String vendorName;//'厂商'--BpmTaskMonRes
    private String prodModel;//'产品型号',
    private String osType;//'操作系统',
    private String osVersion;//'版本分类',
    private String osFeature;//'软件特征',
    private String indGroupName;//'指标组',
    private String indItemName;//'指标名称',
    private String indItemEnName;//'指标英文名称',
    private String valueType ;//'数据类型',
    private Integer length ;//'长度',
    private Integer decimals ;// '小数位数',
    private String measureUnit ;// '度量单位',
    private String indCollMode;//采集方式--自己set
    private Integer status ;//'验证状态(1:已验证;0:未验证)',

    public String getResIp() {
        return resIp;
    }

    public void setResIp(String resIp) {
        this.resIp = resIp;
    }

    public String getResClassName() {
        return resClassName;
    }

    public void setResClassName(String resClassName) {
        this.resClassName = resClassName;
    }

    public String getResTypeName() {
        return resTypeName;
    }

    public void setResTypeName(String resTypeName) {
        this.resTypeName = resTypeName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getProdModel() {
        return prodModel;
    }

    public void setProdModel(String prodModel) {
        this.prodModel = prodModel;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOsFeature() {
        return osFeature;
    }

    public void setOsFeature(String osFeature) {
        this.osFeature = osFeature;
    }

    public String getIndGroupName() {
        return indGroupName;
    }

    public void setIndGroupName(String indGroupName) {
        this.indGroupName = indGroupName;
    }

    public String getIndItemName() {
        return indItemName;
    }

    public void setIndItemName(String indItemName) {
        this.indItemName = indItemName;
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

    public String getIndCollMode() {
        return indCollMode;
    }

    public void setIndCollMode(String indCollMode) {
        this.indCollMode = indCollMode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
