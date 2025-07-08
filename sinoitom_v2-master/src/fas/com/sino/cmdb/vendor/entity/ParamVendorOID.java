/*
 * 文件名： ParamVendorOID.java
 * 
 * 创建日期： 2014-8-25
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.vendor.entity;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-8-25
 */
public class ParamVendorOID {
	private String vendorOID;	//varchar	20	厂商SnmpOID
	private Integer vendorID;	//int	2	厂商ID  
	private String remark;		//varchar	128	备注
	private Integer oidFlag;	//int	2	厂商ID  
					  
	private String vendorName;	  //varchar	32	厂商名称  
	private String dispName;	    //varchar	32		显示名称
	private String fullName;	    //varchar	32	厂商全称	
	private Integer flag;	        //int	1	厂商标志   
	private Integer status;	
	public String getVendorOID() {
		return vendorOID;
	}
	public void setVendorOID(String vendorOID) {
		this.vendorOID = vendorOID;
	}
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getOidFlag() {
		return oidFlag;
	}
	public void setOidFlag(Integer oidFlag) {
		this.oidFlag = oidFlag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
