package com.sino.area.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @ClassName: AdvCountry 
* @Description: Adv_Country实体类
* @author fengyao
* @date 2017-11-10 上午10:14:20 
*  
*/ 
@Entity
@Table(name = "Adv_Country")
public class AdvCountry {
	private String countryName;//'国家名称',
	private String enName;//'英文名称'
	private String countryCode;//'国家编码'
	private String telCode;//'国际电话'
	private Integer flag;//'开通标志(0: 未开通;1:开通)'
	
	@Id
	@Column(name = "CountryName", unique = true, nullable = false,length = 16)
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	@Column(name = "EnName",nullable = false, length = 24)
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	@Column(name = "CountryCode",nullable = false, length = 2)
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	@Column(name = "TelCode",nullable = false, length = 6)
	public String getTelCode() {
		return telCode;
	}
	public void setTelCode(String telCode) {
		this.telCode = telCode;
	}
	@Column(name = "Flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	
}
