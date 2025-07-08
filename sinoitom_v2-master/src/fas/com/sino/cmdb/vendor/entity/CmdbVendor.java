package com.sino.cmdb.vendor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
/**
 * 
 *
 * @author <a href="mailto:wangam@sino-bridge.com">王阿明</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2013-1-13
 */
@Entity
@Table(name = "Cmdb_Vendor",uniqueConstraints = @UniqueConstraint(columnNames = "VendorID"))
public class CmdbVendor {
	private Integer vendorID;	    //int	2	厂商ID				  
	private String vendorName;	  //varchar	32	厂商名称  
	private String dispName;	    //varchar	32		显示名称
	private String fullName;	    //varchar	32	厂商全称	
	private String webURL;	      //varchar	255  网站地址	
	private String description;   //varchar	128		公司描述
	private Integer flag;	        //int	1	厂商标志        
	private Integer status;	        //int	1	厂商标志        
	@Id
	@Column(name = "VendorID")
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}
	@Column(name = "VendorName")
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	@Column(name = "DispName")
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	@Column(name = "FullName")
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Column(name = "WebURL")
	public String getWebURL() {
		return webURL;
	}
	public void setWebURL(String webURL) {
		this.webURL = webURL;
	}
	@Column(name = "Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "Flag")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
    
}
