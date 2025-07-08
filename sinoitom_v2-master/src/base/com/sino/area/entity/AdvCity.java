package com.sino.area.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @ClassName: AdvCity 
* @Description: TODO 
* @author fengyao
* @date 2017-11-10 上午11:09:46 
*  
*/ 
@Entity
@Table(name = "Adv_City")
public class AdvCity {
	private Integer provCode;//int(2) NULL
	private Integer cityCode;//int(2) NOT NULL名称
	private Integer CityType;
	private String cityName;//varchar(32) NULL行政区类别
	
	public Integer getCityType() {
		return CityType;
	}
	public void setCityType(Integer cityType) {
		CityType = cityType;
	}
	
	@Column(name = "ProvCode" )
	public Integer getProvCode() {
		return provCode;
	}
	public void setProvCode(Integer provCode) {
		this.provCode = provCode;
	}
	
	@Id
	@Column(name = "CityCode", unique = true, nullable = false )
	public Integer getCityCode() {
		return cityCode;
	}
	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}
	
	@Column(name = "CityName")
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
