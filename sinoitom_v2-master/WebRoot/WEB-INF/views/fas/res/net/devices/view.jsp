<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>交换机编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script type="text/javascript">

var dlg = null;
var lastDlg = null;
var cbSnmpCred = null;
var cbDevAccess = null;
var access='${access.acsUserName}';
var canOrgSelect = false;
var canVendorSelect = false;

	$(function ()
	{		
		if(access==null||access==""){
		 $(".devAccess").addClass("dN");
		}


       // $("#formSave").ligerForm({inputWidth:200});
        $("#formSave input").filter(".ip1").ligerTextBox({width: 200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});
		
		
		        
		
		<c:if test="${result=='error'}">
        $.ligerDialog.error('${message}');
		</c:if>	
		<c:if test="${result=='success' && closeDlg=='false'}">
        $.ligerDialog.success('保存成功！'); 
		</c:if>
		<c:if test="${result=='success' && action=='add'}">
		$("input[ltype='text']").val("");
		</c:if>
		$("#formSave").validationEngine();
		//表单验证
		$("#formSave").click(function(check) {    
	    	if($("#formSave").validationEngine()){
	    	}else{
	    		check.preventDefault();//此处阻止提交表单  
	    	}
		});
		
	});
	
  	
  	
  	
  	
	
	
	
$(document).ready(function() {
		$(".required").each(function() {
			var $this  = $(this);
			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
		});
  });	
  
  
</script>

</head>
<body > 
	<form id="formSave" action="${ctx}/fas/res/net/switch/save.do" method="post">
		<table align="center" class="tb_edit">
			<tr>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
			</tr>
            <tr>
                <td align="right">所属机构:</td>
                <td><input id="mainOrg" type="text" value="${organ.orgName }" class="ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">联系人:</td>
                <td><input id="contact" type="text" value="${OrgEmployee.name }"  class="ip1" readonly="readonly"/></td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">设备名称:</td>
                <td><input id="deviceName" name="switchName" value="${device.devName}" type="text" ltype="text" class="validate[required[设备名称不能为空]] ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">设备管理地址:</td>
                <td><input id="switchIp" name="switchIp" value="${device.devIpAddr}" type="text" ltype="text" class="validate[required[设备管理地址不能为空],custom[ipv4]] ip1" onblur="checkSwitchIp();" readonly="readonly"/></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">显示名称:</td>
                <td><input id="switchDisplay" name="switchDisplay" value="${device.devDisplay}" type="text" ltype="text" class="validate[required[显示名称不能为空]] ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right"></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">部署位置:</td>
                <td><input id="deploySite" name="deploySite" value="${device.deploySite}" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">设备区域:</td>
                <td><input id="bizZone" name="bizZone" value="${device.bizZone}" type="text" ltype="text" class=" ip1" readonly="readonly" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">机柜槽位:</td>
                <td><input id="cabinetSlot" name="cabinetSlot" value="${device.cabinetSlot}" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">设备描述:</td>
                <td><input id="devDesc" name="devDesc" value="${device.devDesc}" type="text" ltype="text" class=" ip1" readonly="readonly" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">管理状态:</td>
                <td><input name="adminStatus" value="${adminStatus}"  type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">工作状态:</td>
                <td><input name="opStatus" value="${opStatus}"  type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">产品厂商:</td>
                <td>
                <input id="vendorID" name="vendorID"  value="${vendor.dispName }" type="text" ltype="text" class=" ip1" readonly="readonly"/>
                </td>
                <td></td>
                <td align="right">设备分类:</td>
                <td>
                   <input id="prodClass" name="prodClass"  value="${prodModel.prodClassName }" type="text" ltype="text" class=" ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            
             <tr>
                <td align="right">产品类型:</td>
                <td>
                <input id="prodTypeID" name="prodTypeID"  value="${prodModel.prodTypeName }" type="text" ltype="text" class=" ip1" readonly="readonly"/>
                </td>
               <td></td>
               <td align="right">产品系列:</td>
               <td>
                  <input id="prodSeries" name="prodSeries"  value="${prodModel.prodSeries }" type="text" ltype="text" class=" ip1" readonly="readonly"/>
               </td>  
               <td></td>
            </tr>
            
            <tr>
                <td align="right">产品型号:</td>
                 <td>
                  <input id="modelOid" name="modelOid"  value="${device.modelOid }" type="text" ltype="text" class=" ip1" readonly="readonly"/>
               </td>
                <td></td>
                <td align="right">产品序列号:</td>
                <td><input id="productSn"  name="productSn" value="${device.productSN}" type="text" class="ip1" readonly="readonly"/></td>
                <td></td>
            </tr>
            
            
             <tr>
                <td align="right">操作系统类型:</td>
                <td>
                  <input id="osType" name="osType"  value="${device.osType }" type="text" ltype="text" class=" ip1" readonly="readonly"/>
                </td>
                <td></td>
                 <td align="right">操作系统名称:</td>
                <td>
                  <input id="osName" name="osName"  value="${device.osName }" type="text" ltype="text" class=" ip1" readonly="readonly"/>
                </td>
                <td></td>
               
            </tr>
            
            <tr>
               
                <td align="right">版本类型:</td>
                <td>
                   <input id="osVersion" name="osVersion"  value="${device.osVersion }" type="text" ltype="text" class=" ip1" readonly="readonly"/>
                </td>
                <td></td>
                
                  <td align="right">详细版本:</td>
                <td><input id="osRelease" name="osRelease" value="${device.osRelease}" type="text" class="ip1" readonly="readonly"/></td>
                <td></td>
                
            </tr>
            
             <tr>
                <td align="right">PDB获取方式:</td>
                <td>
                   <input id="getMacMode" type="text" class="ip1" value="${macModeName }" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">SNMP配置信息:</td>
                <td><input id="snmpCred" type="text" class="ip1" value="${cred.snmpCredName }" readonly="readonly"/></td>
                <td></td>
                
            </tr>
            <tr >
                <td align="right">Snmp版本:</td>
                <td><input id="snmpVersion" value="${snmpVersion}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">Snmp端口:</td>
                <td><input id="snmpPort" value="${cred.snmpPort}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr >
                <td align="right">SNMP只读串:</td>
                <td><input id="snmpRoStr" value="${cred.snmpRoStr}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">SNMP读写串:</td>
                <td><input id="snmpRwStr" value="${cred.snmpRwStr}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr class="devSnmp3 dN">
                <td align="right">凭证名称:</td>
                <td><input id="credName" value="${snmpInfo.credName}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">凭证描述:</td>
                <td><input id="credDesc" value="${snmpInfo.credDesc}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr class="devSnmp3 dN">
                <td align="right">用户名:</td>
                <td><input id="credUserName" value="${snmpInfo.credUserName}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">上下文名称:</td>
                <td><input id="credContextName" value="${snmpInfo.credContextName}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr class="devSnmp3 dN">
                <td align="right">认证协议:</td>
                <td><input id="credAuthProt" value="${snmpInfo.credAuthProt}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">认证密码:</td>
                <td><input id="credAuthPassword" value="${snmpInfo.credAuthPassword}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr class="devSnmp3 dN">
                <td align="right">加密协议:</td>
                <td><input id="credEncryptProt" value="${snmpInfo.credEncryptProt}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">加密密码:</td>
                <td><input id="credEncryptPassword" value="${snmpInfo.credEncryptPassword}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">访问方式:</td>
                <td><input id="devAccess" type="text" value="${access.acsUserName }" class="ip1" readonly="readonly"/></td>
                <td></td>
            </tr>
            <tr class="devAccess">
                <td align="right">访问工具:</td>
                <td><input id="accessTool" value="${access.accessTool}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">访问端口:</td>
                <td><input id="accessPort" value="${access.accessPort}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr class="devAccess">
                <td align="right">访问方式:</td>
                <td><input id="userPrivilege" value="${userPrivilegeName}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">用户名称:</td>
                <td><input id="userName" value="${access.userName}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr class="devAccess">
                <td align="right">密码:</td>
                <td><input id="passWord" value="${access.passWord}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
                <td align="right">特权密码:</td>
                <td><input id="privModePasswd" value="${access.privModePasswd}" type="text" readonly="readonly" class="ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">缺省网关:</td>
                <td><input id="gatewayIp" name="gatewayIp" value="${device.gatewayIP}" type="text" ltype="text" class="validate[optional,maxSize[255]] ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">网关Snmp只读串:</td>
                <td><input id="gatewaySnmp" name="gatewaySnmp" value="${device.gatewaySnmp}" type="text" ltype="text" class="validate[optional,maxSize[255]] ip1"  readonly="readonly"/></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">网关设备类型:</td>
                <td><input name="gatewayType"  id="gatewayType" value="${devType}" type="text" readonly="readonly" class="ip1"/></td>
                <!-- <td><input id="snmpDevTypeSelect" type="text" class="ip1" /></td> -->
                <td></td>
               	<td align="right">ARP表获取方式：</td>
				<td><input id="arpGetMethodSelect" type="text" value="${arpModeName }" class="ip1" readonly="readonly"/></td>
                <td></td>
            </tr>
           
		</table>
    </form>
</body>
<script>
</script>
</html>

