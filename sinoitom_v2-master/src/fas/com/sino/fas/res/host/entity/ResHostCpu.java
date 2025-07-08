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
 * @date 2019-9-12上午10:57:00
 * @className ResHostCpu
 *
 * @Description TODO
 *
 */

@Entity
@Table(name = "Res_HostCpu", uniqueConstraints = @UniqueConstraint(columnNames = "cpuId"))
public class ResHostCpu {
	
//	`hostId` bigint(20) NOT NULL,
//	  `cpuId` int(11) NOT NULL,
//	  `cpuVendor` varchar(32) DEFAULT NULL COMMENT 'cpu厂商',
//	  `cpuModel` varchar(255) DEFAULT NULL COMMENT 'cpu型号',
//	  `cpuCoreId` varchar(32) DEFAULT NULL COMMENT '物理核中CPU编号',
//	  `cpuWidth` int(2) DEFAULT NULL COMMENT 'cpu位数（32、64）',
//	  `cpuQty` int(2) DEFAULT NULL COMMENT 'cpu数量',
//	  `cpuCoreQty` int(2) DEFAULT NULL COMMENT 'cpu内核数量',
//	  `cpuStruct` varchar(32) DEFAULT NULL COMMENT 'cpu架构',
	
	private Long hostId;
	private Integer cpuId;
	private String cpuVendor;
	private String cpuModel;
	private String cpuCoreId;
	private Integer cpuWidth;
	private Integer cpuQty;
	private Integer cpuCoreQty;
	private String cpuStruct;
	
	
	public Long getHostId() {
		return hostId;
	}
	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}
	@Id
	@Column(name="cpuId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getCpuId() {
		return cpuId;
	}
	public void setCpuId(Integer cpuId) {
		this.cpuId = cpuId;
	}
	public String getCpuVendor() {
		return cpuVendor;
	}
	public void setCpuVendor(String cpuVendor) {
		this.cpuVendor = cpuVendor;
	}
	public String getCpuModel() {
		return cpuModel;
	}
	public void setCpuModel(String cpuModel) {
		this.cpuModel = cpuModel;
	}
	public String getCpuCoreId() {
		return cpuCoreId;
	}
	public void setCpuCoreId(String cpuCoreId) {
		this.cpuCoreId = cpuCoreId;
	}
	public Integer getCpuWidth() {
		return cpuWidth;
	}
	public void setCpuWidth(Integer cpuWidth) {
		this.cpuWidth = cpuWidth;
	}
	public Integer getCpuQty() {
		return cpuQty;
	}
	public void setCpuQty(Integer cpuQty) {
		this.cpuQty = cpuQty;
	}
	public Integer getCpuCoreQty() {
		return cpuCoreQty;
	}
	public void setCpuCoreQty(Integer cpuCoreQty) {
		this.cpuCoreQty = cpuCoreQty;
	}
	public String getCpuStruct() {
		return cpuStruct;
	}
	public void setCpuStruct(String cpuStruct) {
		this.cpuStruct = cpuStruct;
	}
	
	
	

}
