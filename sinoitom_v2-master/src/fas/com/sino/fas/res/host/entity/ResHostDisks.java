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
 * @date 2019-8-27下午7:21:34
 * @className ResHostDisks
 *
 * @Description TODO
 *
 */

@Entity
@Table(name = "Res_HostDisks", uniqueConstraints = @UniqueConstraint(columnNames = "diskId"))
public class ResHostDisks {
	
//	`hostId` bigint(20) NOT NULL COMMENT '主机资源ID',
//	  `diskId` int(11) NOT NULL AUTO_INCREMENT,
//	  `size` varchar(32) DEFAULT NULL COMMENT '磁盘大小',
//	  `diskHeads` int(11) DEFAULT NULL COMMENT '磁面个数',
//	  `diskSectors` int(11) DEFAULT NULL COMMENT '扇区个数',
//	  `diskCylinders` int(11) DEFAULT NULL COMMENT '磁柱个数',
//	  `cylinderSize` float(20,3) DEFAULT NULL COMMENT '每个磁柱的容量（KB）',
//	  `diskIdentifier` varchar(32) DEFAULT NULL COMMENT '磁盘标识符',
	
	private Long hostId;
	private Integer diskId;
	private String size;
	private Integer diskHeads;
	private Integer diskSectors;
	private Integer diskCylinders;
	private Long cylinderSize;
	private String diskIdentifier;
	
	private Long bytes;
	
	private Long totalSectors;
	private Long sectorsSize;
	
	
	public Long getHostId() {
		return hostId;
	}
	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}
	@Id
	@Column(name="diskId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getDiskId() {
		return diskId;
	}
	public void setDiskId(Integer diskId) {
		this.diskId = diskId;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Integer getDiskHeads() {
		return diskHeads;
	}
	public void setDiskHeads(Integer diskHeads) {
		this.diskHeads = diskHeads;
	}
	public Integer getDiskSectors() {
		return diskSectors;
	}
	public void setDiskSectors(Integer diskSectors) {
		this.diskSectors = diskSectors;
	}
	public Integer getDiskCylinders() {
		return diskCylinders;
	}
	public void setDiskCylinders(Integer diskCylinders) {
		this.diskCylinders = diskCylinders;
	}
	public Long getCylinderSize() {
		return cylinderSize;
	}
	public void setCylinderSize(Long cylinderSize) {
		this.cylinderSize = cylinderSize;
	}
	public String getDiskIdentifier() {
		return diskIdentifier;
	}
	public void setDiskIdentifier(String diskIdentifier) {
		this.diskIdentifier = diskIdentifier;
	}
	public Long getBytes() {
		return bytes;
	}
	public void setBytes(Long bytes) {
		this.bytes = bytes;
	}
	public Long getTotalSectors() {
		return totalSectors;
	}
	public void setTotalSectors(Long totalSectors) {
		this.totalSectors = totalSectors;
	}
	public Long getSectorsSize() {
		return sectorsSize;
	}
	public void setSectorsSize(Long sectorsSize) {
		this.sectorsSize = sectorsSize;
	}
	

}
