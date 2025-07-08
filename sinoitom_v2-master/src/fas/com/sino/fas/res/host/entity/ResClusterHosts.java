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
 * @date 2019-12-11上午11:03:35
 * @className ResClusterHosts
 * @version V1.0
 * @Description TODO
 *
 */

@Entity
@Table(name = "Res_ClusterHosts", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class ResClusterHosts {
	
//	`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
//	  `clusterId` bigint(20) DEFAULT NULL COMMENT '集群Id',
//	  `hostId` bigint(20) DEFAULT NULL COMMENT '服务器ID',
	
	private Long id;
	private Long clusterId;
	private Long hostId;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getClusterId() {
		return clusterId;
	}
	public void setClusterId(Long clusterId) {
		this.clusterId = clusterId;
	}
	public Long getHostId() {
		return hostId;
	}
	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}
	
	

}
