package com.sino.area.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @ClassName: AdvCounty 
* @Description: TODO 
* @author fengyao
* @date 2017-11-10 上午11:09:51 
*  
*/ 
@Entity
@Table(name = "Adv_County")
public class AdvCounty {
	private Integer cityCode;//int(2) NULL地市编码
	private Integer countyCode;//int(2) NULL区县编码
	private Integer countyType;//int(1) NULL区县类型(1:县级市;2:区;3:县;4:旗)
	private String countyName;//varchar(32) NULL区县名称
	
	@Column(name = "CityCode" )
	public Integer getCityCode() {
		return cityCode;
	}
	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}
	
	@Id
	@Column(name = "CountyCode", unique = true, nullable = false )
	public Integer getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(Integer countyCode) {
		this.countyCode = countyCode;
	}
	
	@Column(name = "CountyType" )
	public Integer getCountyType() {
		return countyType;
	}
	public void setCountyType(Integer countyType) {
		this.countyType = countyType;
	}
	
	@Column(name = "CountyName" )
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
}
