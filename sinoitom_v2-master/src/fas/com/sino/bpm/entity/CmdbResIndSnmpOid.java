package com.sino.bpm.entity;

import javax.persistence.*;

/**
 * @ClassName CmdbResIndSnmpOid
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/18 14:23
 * @Version 1.0
 **/
@Entity
@Table(name = "Cmdb_ResIndSnmpOid", uniqueConstraints = @UniqueConstraint(columnNames = "resIndSnmpOid"))
public class CmdbResIndSnmpOid {
    private Integer resIndSnmpOid;
    private Integer vendorId;//'厂商ID',
    private Integer resClassCode;//'产品分类ID',
    private String resClassName;//'产品分类名称',
    private Integer resTypeCode;//'产品类型ID',
    private String resTypeName;//'产品类型名称',
    private Long prodModelID;//'产品型号ID',
    private String prodModel;//'产品型号',
    private String modelOID;//'Model OID',
    private String osType;//'操作系统类型',
    private String osVersion;//'操作系统版本',
    private String osFeature;//'操作系统特征',
    private  Integer resTypeIndId;//'资源类型指标ID',
    private Integer indClassCode;//'指标分类编码',
    private String indClassName;//'指标分类名称',
    private Long indGroupID;//'指标组ID',
    private String indGroupName;//'指标组',
    private Long indItemID;//'指标项ID',
    private String indicatorItem;//'指标英文名称',
    private String indItemName;//'指标中文名称',
    private String mibFile;//'Mib文件',
    private String snmpObjName;//'Snmp对象名称',
    private String snmpOID;//'指标对象SnmpOID',
    private String getMethod;//'SnmpGet、SnmpWalk',
    private Integer oidFlag;//'OID标志(1:直接获取;2:计算获取)',
    private String valueType;//'取值类型',
    private Integer length ;//'取值长度',
    private Integer decimals ;//'取值精度',
    private String measureUnit ;//'取值单位',
    private String remark ;//'备注',
    private Integer status ;//'状态(1:已审核;0:未审核)',

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getResIndSnmpOid() {
        return resIndSnmpOid;
    }

    public void setResIndSnmpOid(Integer resIndSnmpOid) {
        this.resIndSnmpOid = resIndSnmpOid;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
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

    public Long getProdModelID() {
        return prodModelID;
    }

    public void setProdModelID(Long prodModelID) {
        this.prodModelID = prodModelID;
    }

    public String getProdModel() {
        return prodModel;
    }

    public void setProdModel(String prodModel) {
        this.prodModel = prodModel;
    }

    public String getModelOID() {
        return modelOID;
    }

    public void setModelOID(String modelOID) {
        this.modelOID = modelOID;
    }

    public Integer getResTypeIndId() {
        return resTypeIndId;
    }

    public void setResTypeIndId(Integer resTypeIndId) {
        this.resTypeIndId = resTypeIndId;
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

    public String getIndicatorItem() {
        return indicatorItem;
    }

    public void setIndicatorItem(String indicatorItem) {
        this.indicatorItem = indicatorItem;
    }

    public String getIndItemName() {
        return indItemName;
    }

    public void setIndItemName(String indItemName) {
        this.indItemName = indItemName;
    }

    public String getMibFile() {
        return mibFile;
    }

    public void setMibFile(String mibFile) {
        this.mibFile = mibFile;
    }

    public String getSnmpObjName() {
        return snmpObjName;
    }

    public void setSnmpObjName(String snmpObjName) {
        this.snmpObjName = snmpObjName;
    }

    public String getSnmpOID() {
        return snmpOID;
    }

    public void setSnmpOID(String snmpOID) {
        this.snmpOID = snmpOID;
    }

    public String getGetMethod() {
        return getMethod;
    }

    public void setGetMethod(String getMethod) {
        this.getMethod = getMethod;
    }

    public Integer getOidFlag() {
        return oidFlag;
    }

    public void setOidFlag(Integer oidFlag) {
        this.oidFlag = oidFlag;
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
}
