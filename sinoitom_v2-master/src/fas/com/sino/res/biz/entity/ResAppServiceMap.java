package com.sino.res.biz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Res_AppServiceMap")
public class ResAppServiceMap {
	private Long Id;
	private Long bizAppId;
	private Long appSvcId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public Long getBizAppId() {
		return bizAppId;
	}
	public void setBizAppId(Long bizAppId) {
		this.bizAppId = bizAppId;
	}
	public Long getAppSvcId() {
		return appSvcId;
	}
	public void setAppSvcId(Long appSvcId) {
		this.appSvcId = appSvcId;
	}
	
	

}
