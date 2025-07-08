package com.sino.area.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @ClassName: AdvProvince 
* @Description: TODO 
* @author fengyao
* @date 2017-11-10 上午11:09:55 
*  
*/ 
@Entity
@Table(name = "Adv_Province")
public class AdvProvince {
	private String countryCode;//'国家编码',
	private Integer areaID;//int(1) NOT NULL
	private Integer provCode;//int(2) NOT NULL编码
	private String alphaCode;//char(2) NULL字母编码
	private String name;//varchar(32) NULL名称
	private String shortName;//varchar(2) NULL简称
	private String displayName;//varchar(16) NULL显示名称
	private Integer type;//int(1) NULL类别:1:直辖市;2:省;3:自治区;0:特别行政区
	private String typeName;//varchar(8) NULL类别名称
	
	
	@Column(name = "AreaID" )
	public Integer getAreaID() {
		return areaID;
	}
	public void setAreaID(Integer areaID) {
		this.areaID = areaID;
	}
	
	@Id
	@Column(name = "ProvCode", unique = true, nullable = false )
	public Integer getProvCode() {
		return provCode;
	}
	public void setProvCode(Integer provCode) {
		this.provCode = provCode;
	}
	
	@Column(name = "AlphaCode" )
	public String getAlphaCode() {
		return alphaCode;
	}
	public void setAlphaCode(String alphaCode) {
		this.alphaCode = alphaCode;
	}
	
	@Column(name = "Name" )
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "ShortName" )
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Column(name = "DisplayName" )
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	@Column(name = "Type" )
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "TypeName" )
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
