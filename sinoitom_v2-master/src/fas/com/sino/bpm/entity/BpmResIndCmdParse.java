package com.sino.bpm.entity;

import javax.persistence.*;

/**
 * @ClassName BpmResIndCmdParse
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/19 11:18
 * @Version 1.0
 **/
@Entity
@Table(name = "Bpm_ResIndCmdParse", uniqueConstraints = @UniqueConstraint(columnNames = "resMonIndItemId"))
public class BpmResIndCmdParse {
    private Long resMonIndItemId;//'监测指标项Id',
    private Long resId;//'资源ID',
    private Integer chkItemId;//Cmdb_ResIndCmdParse主键id
    private Integer vendorID;//'厂商ID',
    private Integer resClassCode;//'产品分类ID',
    private String resClassName;//'产品分类名称',
    private Integer resTypeCode;//'产品类型ID（-1代表：所有类型）',
    private String resTypeName;//'产品类型名称',
    private String prodModel;//'产品型号',
    private String modelOID ;//'Model OID',
    private String osType;//'操作系统类型',
    private String osVersion;//'操作系统版本',
    private String osFeature;//'操作系统特征',
    private Integer resTypeIndId;//'资源类型指标ID',
    private Integer indClassCode;//'指标分类编码',
    private String indClassName;//'指标分类名称',
    private Long indGroupID;//'指标组ID',
    private String indGroupName;//'指标组',
    private Long indItemID;//'指标项ID',
    private String indItemEnName;//'指标英文名称(指标项)',
    private String indItemName;//'指标中文名称',
    private Integer prodChkCmdId ;//'巡检命令ID (Cmdb_ProdChkCmds.prodChkCmdId)',
    private String checkCmd  ;//'巡检命令',
    private String resultSample;//'结果示例（与指标有关的一行结果）',
    private String resultKeyWords ;// '结果关键字（用来匹配定位要解析的一行信息）',
    private Integer parseMode  ;// '解析方式（1：正则表达式；2：字符串匹配；3：字符分割-split；4. 模版解析）',
    private String regEx  ;// '正则表达式',
    private String regExGroupNum  ;// '正则表达式group序列号，多个序列号以“,”分隔',
    private String startKeyWords  ;// '开始关键字（取值用）',
    private String endKeyWords  ;//'结束关键字（取值用，允许为空）',
    private String delimiter ;// '分割符（Split）',
    private Integer valueIndex  ;//'数值索引',
    private String fileDelimiter  ;// 'value/value分隔符（应用于模版解析）',
    private String kvDelimiter ;//'key/value分隔符（应用于模版解析）',
    private String lineDelimiter ;//'行分隔符（应用于模版解析）',
    private String blankLineDelimiter ;//'空行分隔符（应用于模版解析）',
    private String valueType ;//'取值类型',
    private Integer length ;//'取值长度',
    private Integer decimals ;// '取值精度',
    private String measureUnit ;// '取值单位',
    private String remark ;//'备注',
    private Integer status ;//'验证状态(1:已验证;0:未验证)',

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getResMonIndItemId() {
        return resMonIndItemId;
    }

    public void setResMonIndItemId(Long resMonIndItemId) {
        this.resMonIndItemId = resMonIndItemId;
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }

    public Integer getVendorID() {
        return vendorID;
    }

    public void setVendorID(Integer vendorID) {
        this.vendorID = vendorID;
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

    public Integer getProdChkCmdId() {
        return prodChkCmdId;
    }

    public void setProdChkCmdId(Integer prodChkCmdId) {
        this.prodChkCmdId = prodChkCmdId;
    }

    public String getCheckCmd() {
        return checkCmd;
    }

    public void setCheckCmd(String checkCmd) {
        this.checkCmd = checkCmd;
    }

    public String getResultSample() {
        return resultSample;
    }

    public void setResultSample(String resultSample) {
        this.resultSample = resultSample;
    }

    public String getResultKeyWords() {
        return resultKeyWords;
    }

    public void setResultKeyWords(String resultKeyWords) {
        this.resultKeyWords = resultKeyWords;
    }

    public Integer getParseMode() {
        return parseMode;
    }

    public void setParseMode(Integer parseMode) {
        this.parseMode = parseMode;
    }

    public String getRegEx() {
        return regEx;
    }

    public void setRegEx(String regEx) {
        this.regEx = regEx;
    }

    public String getRegExGroupNum() {
        return regExGroupNum;
    }

    public void setRegExGroupNum(String regExGroupNum) {
        this.regExGroupNum = regExGroupNum;
    }

    public String getStartKeyWords() {
        return startKeyWords;
    }

    public void setStartKeyWords(String startKeyWords) {
        this.startKeyWords = startKeyWords;
    }

    public String getEndKeyWords() {
        return endKeyWords;
    }

    public void setEndKeyWords(String endKeyWords) {
        this.endKeyWords = endKeyWords;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public Integer getValueIndex() {
        return valueIndex;
    }

    public void setValueIndex(Integer valueIndex) {
        this.valueIndex = valueIndex;
    }

    public String getFileDelimiter() {
        return fileDelimiter;
    }

    public void setFileDelimiter(String fileDelimiter) {
        this.fileDelimiter = fileDelimiter;
    }

    public String getKvDelimiter() {
        return kvDelimiter;
    }

    public void setKvDelimiter(String kvDelimiter) {
        this.kvDelimiter = kvDelimiter;
    }

    public String getLineDelimiter() {
        return lineDelimiter;
    }

    public void setLineDelimiter(String lineDelimiter) {
        this.lineDelimiter = lineDelimiter;
    }

    public String getBlankLineDelimiter() {
        return blankLineDelimiter;
    }

    public void setBlankLineDelimiter(String blankLineDelimiter) {
        this.blankLineDelimiter = blankLineDelimiter;
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

    public Integer getChkItemId() {
        return chkItemId;
    }

    public void setChkItemId(Integer chkItemId) {
        this.chkItemId = chkItemId;
    }
}
