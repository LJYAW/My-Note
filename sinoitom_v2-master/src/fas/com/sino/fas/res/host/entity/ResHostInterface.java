/**
 * 
 */
package com.sino.fas.res.host.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Mr.LP
 * @date 2019-8-27下午5:00:41
 * @className ResHostInterface
 *
 * @Description TODO
 *
 */

@Entity
@Table(name = "Res_HostInterface", uniqueConstraints = @UniqueConstraint(columnNames = "infoId"))
public class ResHostInterface {
	
//	`hostId` bigint(20) NOT NULL COMMENT '主机资源ID',
//	  `infoId` int(11) NOT NULL AUTO_INCREMENT,
//	  `infoName` varchar(32) DEFAULT NULL COMMENT '网络接口名称',
//	  `macAddress` varchar(17) DEFAULT NULL COMMENT 'MAC 地址',
//	  `ipAddress` varchar(16) DEFAULT NULL COMMENT 'ip地址',
//	  `bcastAddress` varchar(16) DEFAULT NULL COMMENT '广播地址',
//	  `maskAddress` varchar(16) DEFAULT NULL COMMENT '掩码地址',
//	  `ipv6` varchar(32) DEFAULT NULL COMMENT 'ipv6地址',
	
	private Long hostId;
	private Integer infoId;
	private String infoName;
	private String macAddress;
	private String ipAddress;
	private String bcastAddress;
	private String maskAddress;
	private String ipv6;
	
	
	public Long getHostId() {
		return hostId;
	}
	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}
	@Id
	@Column(name="infoId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getInfoId() {
		return infoId;
	}
	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}
	public String getInfoName() {
		return infoName;
	}
	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getBcastAddress() {
		return bcastAddress;
	}
	public void setBcastAddress(String bcastAddress) {
		this.bcastAddress = bcastAddress;
	}
	public String getMaskAddress() {
		return maskAddress;
	}
	public void setMaskAddress(String maskAddress) {
		this.maskAddress = maskAddress;
	}
	public String getIpv6() {
		return ipv6;
	}
	public void setIpv6(String ipv6) {
		this.ipv6 = ipv6;
	}
	
	

}
