package com.sino.cmdb.indicator.cmdCheckItems.entity;

import javax.persistence.*;

@Entity
@Table(name = "Cmdb_ProdCmdChkItems", uniqueConstraints = @UniqueConstraint(columnNames = "chkItemId"))
public class CmdbProdCmdChkItems {

	private Integer chkItemId;
	private Integer vendorId;
	private Integer devClassCode;
	private String devClassName;
	private Integer devTypeCode;
	private String devTypeName;
	private Long prodModelId;
	private String prodModel;
	private String modelOID;
	private String osType;
	private String osVersion;
	private String osFeature;
	private Integer indClassCode;
	private String indClassName;
	private Long indGroupId;
	private String indGroupName;
	private Long indItemID = -1L;
	private String indItemEnName;
	private String indItemName;
	private Integer prodChkCmdId;
	private String checkCmd;
	private String resultSample;
	private String resultKeyWords;
	private Integer parseMode;
	private String regEx;
	private String regExGroupNum;
	private String startKeyWords;
	private String endKeyWords;
	private String delimiter;
	private Integer valueIndex;
	private String fileDelimiter;
	private String kvDelimiter;
	private String lineDelimiter;
	private String blankLineDelimiter;
	private String valueType;
	private Integer length;
	private Integer decimals;
	private String measureUnit;
	private String remark;
	private Integer status;

	@Id
	@Column(name = "chkItemId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getChkItemId() {
		return chkItemId;
	}

	public void setChkItemId(Integer chkItemId) {
		this.chkItemId = chkItemId;
	}

	@Column(name = "vendorID")
	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	@Column(name = "devClassCode")
	public Integer getDevClassCode() {
		return devClassCode;
	}

	public void setDevClassCode(Integer devClassCode) {
		this.devClassCode = devClassCode;
	}

	@Column(name = "devClassName")
	public String getDevClassName() {
		return devClassName;
	}

	public void setDevClassName(String devClassName) {
		this.devClassName = devClassName;
	}

	@Column(name = "devTypeCode")
	public Integer getDevTypeCode() {
		return devTypeCode;
	}

	public void setDevTypeCode(Integer devTypeCode) {
		this.devTypeCode = devTypeCode;
	}

	@Column(name = "devTypeName")
	public String getDevTypeName() {
		return devTypeName;
	}

	public void setDevTypeName(String devTypeName) {
		this.devTypeName = devTypeName;
	}

	@Column(name = "prodModelID")
	public Long getProdModelId() {
		return prodModelId;
	}

	public void setProdModelId(Long prodModelId) {
		this.prodModelId = prodModelId;
	}

	@Column(name = "prodModel")
	public String getProdModel() {
		return prodModel;
	}

	public void setProdModel(String prodModel) {
		this.prodModel = prodModel;
	}

	@Column(name = "modelOID")
	public String getModelOID() {
		return modelOID;
	}

	public void setModelOID(String modelOID) {
		this.modelOID = modelOID;
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
	public Long getIndGroupId() {
		return indGroupId;
	}

	public void setIndGroupId(Long indGroupId) {
		this.indGroupId = indGroupId;
	}

	@Column(name = "indGroupName")
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

	@Column(name = "indItemName")
	public String getIndItemName() {
		return indItemName;
	}

	public void setIndItemName(String indItemName) {
		this.indItemName = indItemName;
	}

	@Column(name = "checkCmd")
	public String getCheckCmd() {
		return checkCmd;
	}

	public void setCheckCmd(String checkCmd) {
		this.checkCmd = checkCmd;
	}

	@Column(name = "resultSample")
	public String getResultSample() {
		return resultSample;
	}

	public void setResultSample(String resultSample) {
		this.resultSample = resultSample;
	}

	@Column(name = "resultKeyWords")
	public String getResultKeyWords() {
		return resultKeyWords;
	}

	public void setResultKeyWords(String resultKeyWords) {
		this.resultKeyWords = resultKeyWords;
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

	@Column(name = "decimals")
	public Integer getDecimals() {
		return decimals;
	}

	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
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

	@Column(name = "status")
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

	public String getIndItemEnName() {
		return indItemEnName;
	}

	public void setIndItemEnName(String indItemEnName) {
		this.indItemEnName = indItemEnName;
	}

	public Integer getProdChkCmdId() {
		return prodChkCmdId;
	}

	public void setProdChkCmdId(Integer prodChkCmdId) {
		this.prodChkCmdId = prodChkCmdId;
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


	public String getOsFeature() {
		return osFeature;
	}

	public void setOsFeature(String osFeature) {
		this.osFeature = osFeature;
	}

	public String getRegExGroupNum() {
		return regExGroupNum;
	}

	public void setRegExGroupNum(String regExGroupNum) {
		this.regExGroupNum = regExGroupNum;
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

	@Override
	public String toString() {
		return "CmdbProdCmdChkItems [chkItemId=" + chkItemId + ", vendorId=" + vendorId + ", devClassCode="
				+ devClassCode + ", devClassName=" + devClassName + ", devTypeCode=" + devTypeCode + ", devTypeName="
				+ devTypeName + ", prodModelId=" + prodModelId + ", prodModel=" + prodModel + ", modelOID=" + modelOID
				+ ", osType=" + osType + ", osVersion=" + osVersion + ", indClassCode=" + indClassCode
				+ ", indClassName=" + indClassName + ", indGroupId=" + indGroupId + ", indGroupName=" + indGroupName
				+ ", indItemID=" + indItemID + ", indItemEnName=" + indItemEnName + ", indItemName=" + indItemName
				+ ", prodChkCmdId=" + prodChkCmdId + ", checkCmd=" + checkCmd + ", resultSample=" + resultSample
				+ ", resultKeyWords=" + resultKeyWords + ", parseMode=" + parseMode + ", regEx=" + regEx
				+ ", startKeyWords=" + startKeyWords + ", endKeyWords=" + endKeyWords + ", delimiter=" + delimiter
				+ ", valueIndex=" + valueIndex + ", valueType=" + valueType + ", length=" + length + ", decimals="
				+ decimals + ", measureUnit=" + measureUnit + ", remark=" + remark + ", status=" + status + "]";
	}

}
