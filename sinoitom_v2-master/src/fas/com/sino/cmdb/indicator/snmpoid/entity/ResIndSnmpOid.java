package com.sino.cmdb.indicator.snmpoid.entity;

import javax.persistence.*;

/**
 * @ProjectName: SinoITOM_V2
 * @Package: com.sino.cmdb.indicator.snmpoid.entity
 * @ClassName: ResIndSnmpOid
 * @auther: Mr.Lp
 * @date: 2020/6/8 15:05
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

@Entity
@Table(name="Cmdb_ResIndSnmpOid" , uniqueConstraints = @UniqueConstraint(columnNames = "resIndSnmpOid"))
public class ResIndSnmpOid {

    private Integer resIndSnmpOid;
    private Integer vendorId;
    private Integer resClassCode;
    private String resClassName;
    private Integer resTypeCode;
    private String resTypeName;
    private Long prodModelID;
    private String prodModel;
    private String modelOID;
    private String osType;
    private String osVersion;
    private String osFeature;
    private Integer resTypeIndId;
    private Integer indClassCode;
    private String indClassName;
    private Long indGroupID;
    private String indGroupName;
    private Long indItemID;
    private String indicatorItem;
    private String indItemName;
    private String mibFile;
    private String snmpObjName;
    private String snmpOID;
    private String getMethod;
    private Integer oidFlag;
    private String valueType;
    private Integer length;
    private Integer decimals;
    private String measureUnit;
    private String remark;
    private Integer status;


    @Id
    @Column(name="resIndSnmpOid")
    @GeneratedValue(strategy=GenerationType.AUTO)
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
}
