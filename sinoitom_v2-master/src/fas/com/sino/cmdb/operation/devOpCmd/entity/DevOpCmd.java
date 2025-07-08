package com.sino.cmdb.operation.devOpCmd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @since 2013-2-12
 */
@Entity
@Table(name = "Cmdb_DevOpCmd",uniqueConstraints = @UniqueConstraint(columnNames = "opCmdID"))
public class DevOpCmd {
	private Integer devOpID;    //	 int	11	操作ID
	private Long opCmdID;	      //   int	11	命令ID
	private Integer snmpEntID;	    //   varchar	32	厂商Snmp OID	utf8	utf8_general_ci
	private Integer osID;	          // int	11	操作系统ID
	private String devModel;
	private String modelOID;      //型号OID
	private String osName;	        // varchar	32	操作系统名称	utf8	utf8_general_ci
	private String osVersion;    //	 varchar	32 操作系统版本ID	utf8	utf8_general_ci
	private Integer cmdOrder;	      // int	1	命令顺序
	private Integer cmdTypeCode;    //	 int	1	命令类型编码
	private String cmdType;	      //   varchar	32	命令类型	utf8	utf8_general_ci
	private String command;	      //   varchar	128	操作命令	utf8	utf8_general_ci
	private String expectPrompt="";  //期待提示符
	private Integer paramFlag=0;	      // int	1		参数标志(0:无参数;n:代表命令包含n个参数)
	private String description;    //	 varchar	128	命令描述	utf8	utf8_general_ci
	private Integer status;
	
	@Id
	@Column(name = "OpCmdID")
	public Long getOpCmdID() {
		return opCmdID;
	}
	public void setOpCmdID(Long opCmdID) {
		this.opCmdID = opCmdID;
	}
	
	
	@Column(name = "DevOpID")
	public Integer getDevOpID() {
		return devOpID;
	}
	public void setDevOpID(Integer devOpID) {
		this.devOpID = devOpID;
	}
	@Column(name = "SnmpEntID")
	public Integer getSnmpEntID() {
		return snmpEntID;
	}
	public void setSnmpEntID(Integer snmpEntID) {
		this.snmpEntID = snmpEntID;
	}
	@Column(name = "OsID")
	public Integer getOsID() {
		return osID;
	}
	public void setOsID(Integer osID) {
		this.osID = osID;
	}
	@Column(name = "ModelOID")
	public String getModelOID() {
		return modelOID;
	}
	public void setModelOID(String modelOID) {
		this.modelOID = modelOID;
	}
	@Column(name = "OsName")
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	@Column(name = "OsVersion")
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	@Column(name = "CmdOrder")
	public Integer getCmdOrder() {
		return cmdOrder;
	}
	public void setCmdOrder(Integer cmdOrder) {
		this.cmdOrder = cmdOrder;
	}
	@Column(name = "CmdTypeCode")
	public Integer getCmdTypeCode() {
		return cmdTypeCode;
	}
	public void setCmdTypeCode(Integer cmdTypeCode) {
		this.cmdTypeCode = cmdTypeCode;
	}
	@Column(name = "CmdType")
	public String getCmdType() {
		return cmdType;
	}
	public void setCmdType(String cmdType) {
		this.cmdType = cmdType;
	}
	@Column(name = "Command")
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	@Column(name = "ParamFlag")
	public Integer getParamFlag() {
		return paramFlag;
	}
	public void setParamFlag(Integer paramFlag) {
		this.paramFlag = paramFlag;
	}
	@Column(name = "Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "ExpectPrompt")
	public String getExpectPrompt() {
		return expectPrompt;
	}
	public void setExpectPrompt(String expectPrompt) {
		this.expectPrompt = expectPrompt;
	}
	
	@Column(name = "DevModel")
	public String getDevModel() {
		return devModel;
	}
	public void setDevModel(String devModel) {
		this.devModel = devModel;
	}
	
	@Column(name = "Status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
