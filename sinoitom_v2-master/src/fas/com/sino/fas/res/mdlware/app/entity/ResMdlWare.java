/**
 * 
 */
package com.sino.fas.res.mdlware.app.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Mr.LP
 * @date 2019-11-19下午5:16:05
 * @className ResMdlWare
 *
 * @Description TODO
 *
 */

@Entity
@Table(name = "Res_MdlWare")
public class ResMdlWare {
	
//	`orgID` varchar(36) NOT NULL DEFAULT '-1' COMMENT '机构ID',
//	  `resId` bigint(20) NOT NULL DEFAULT '0' COMMENT '主机资源ID，统一调用DBUtil.getUniqResId',
//	  `resClassCode` int(1) DEFAULT '24' COMMENT '资源分类编码',
//	  `resClassName` varchar(32) DEFAULT '中间件' COMMENT '资源分类名称',
//	  `resTypeCode` int(1) DEFAULT '1' COMMENT '资源类型编码',
//	  `resTypeName` varchar(32) DEFAULT '应用中间件' COMMENT '资源类型名称',
//	  `svrType` int(1) DEFAULT NULL COMMENT '服务器类型（0：虚拟机；1：物理机；2：宿主机；3：集群）',
//	  `homedHostId` int(2) DEFAULT NULL COMMENT '宿主机ID',
//	  `homedHostIp` varchar(16) DEFAULT NULL COMMENT '宿主机IP',
//	  `svrIpAddr` varchar(16) DEFAULT NULL COMMENT '服务器IP地址',
//	  `svrIpLong` bigint(20) DEFAULT NULL COMMENT 'IP整形数',
//	  `vendorName` varchar(32) DEFAULT NULL COMMENT '厂商名称',
//	  `brandName` varchar(16) DEFAULT NULL COMMENT '产品品牌',
//	  `prodName` varchar(32) DEFAULT NULL COMMENT '中间件名称',
//	  `userName` varchar(16) NOT NULL DEFAULT '' COMMENT '用户名',
//	  `passWord` varchar(32) DEFAULT NULL COMMENT '密码',
//	  `svcUrl` varchar(512) DEFAULT NULL COMMENT '服务访问',
//	  `auditStatus` int(1) DEFAULT NULL COMMENT '审核状态（0：未审核；1：已审核）',
//	  `monStatus` int(1) DEFAULT NULL COMMENT '监测状态（0：未监测，1：监测中）',
	
	private String orgID;
	private Long resId;
	private Integer resClassCode;
	private String resClassName;
	private Integer resTypeCode;
	private String resTypeName;
	private Integer svrType;
	private Integer homedHostId;
	private String homedHostIp;
	private String svrIpAddr;
	private Long svrIpLong;
	private String vendorName;
	private String brandName;
	private String prodName;
	private String userName;
	private String passWord;
	private String svcUrl;
	private Integer auditStatus;
	private Integer monStatus;
	
	private Integer monPort;
	private String prodVersion;
	private String monTime;
	
	public String getOrgID() {
		return orgID;
	}
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	
	@Id
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
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
	public Integer getSvrType() {
		return svrType;
	}
	public void setSvrType(Integer svrType) {
		this.svrType = svrType;
	}
	public Integer getHomedHostId() {
		return homedHostId;
	}
	public void setHomedHostId(Integer homedHostId) {
		this.homedHostId = homedHostId;
	}
	public String getHomedHostIp() {
		return homedHostIp;
	}
	public void setHomedHostIp(String homedHostIp) {
		this.homedHostIp = homedHostIp;
	}
	public String getSvrIpAddr() {
		return svrIpAddr;
	}
	public void setSvrIpAddr(String svrIpAddr) {
		this.svrIpAddr = svrIpAddr;
	}
	public Long getSvrIpLong() {
		return svrIpLong;
	}
	public void setSvrIpLong(Long svrIpLong) {
		this.svrIpLong = svrIpLong;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getSvcUrl() {
		return svcUrl;
	}
	public void setSvcUrl(String svcUrl) {
		this.svcUrl = svcUrl;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Integer getMonStatus() {
		return monStatus;
	}
	public void setMonStatus(Integer monStatus) {
		this.monStatus = monStatus;
	}
	public Integer getMonPort() {
		return monPort;
	}
	public void setMonPort(Integer monPort) {
		this.monPort = monPort;
	}
	public String getProdVersion() {
		return prodVersion;
	}
	public void setProdVersion(String prodVersion) {
		this.prodVersion = prodVersion;
	}
	public String getMonTime() {
		return monTime;
	}
	public void setMonTime(String monTime) {
		this.monTime = monTime;
	}
	
	

}
