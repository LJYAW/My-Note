/**
 * 
 */
package com.sino.fas.res.host.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Mr.LP
 * @date 2019-8-26上午10:43:15
 * @className ResDisk
 *
 * @Description TODO
 *
 */

@Entity
@Table(name = "Res_HostFileSystem",uniqueConstraints = @UniqueConstraint(columnNames = "fileSysId"))
public class ResHostFileSystem {
	
//	`hostId` bigint(20) NOT NULL COMMENT '主机资源ID',
//	  `fileSysId` varchar(36) NOT NULL COMMENT '磁盘ID',
//	  `filesystem` varchar(255) DEFAULT NULL COMMENT '文件系统',
//	  `size` varchar(32) DEFAULT NULL COMMENT '容量',
//	  `usedSize` varchar(32) DEFAULT NULL COMMENT '已用',
//	  `availSize` varchar(32) DEFAULT NULL COMMENT '可用',
//	  `diskUse` varchar(32) DEFAULT NULL COMMENT '已用占比',
//	  `mountPoint` varchar(255) DEFAULT NULL COMMENT '挂载点',
	
	private Long hostId;
	private String fileSysId;
	private String filesystem;
	private Long size;
	private Long usedSize;
	private Long availSize;
	private String diskUse;
	private String mountPoint;
	private Long bytes;
	
	
	public Long getHostId() {
		return hostId;
	}
	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}
	
	@Id
	public String getFileSysId() {
		return fileSysId;
	}
	public void setFileSysId(String fileSysId) {
		this.fileSysId = fileSysId;
	}
	public String getFilesystem() {
		return filesystem;
	}
	public void setFilesystem(String filesystem) {
		this.filesystem = filesystem;
	}
	public String getDiskUse() {
		return diskUse;
	}
	public void setDiskUse(String diskUse) {
		this.diskUse = diskUse;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public Long getUsedSize() {
		return usedSize;
	}
	public void setUsedSize(Long usedSize) {
		this.usedSize = usedSize;
	}
	public Long getAvailSize() {
		return availSize;
	}
	public void setAvailSize(Long availSize) {
		this.availSize = availSize;
	}
	public String getMountPoint() {
		return mountPoint;
	}
	public void setMountPoint(String mountPoint) {
		this.mountPoint = mountPoint;
	}
	public Long getBytes() {
		return bytes;
	}
	public void setBytes(Long bytes) {
		this.bytes = bytes;
	}
	
	
	

}
