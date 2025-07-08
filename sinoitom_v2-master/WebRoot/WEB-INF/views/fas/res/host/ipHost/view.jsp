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
<title>IP Host查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>

<script type="text/javascript">

	$(function ()
	{		
	});

</script>

</head>
<body> 
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
                <td align="right">机构名称:</td>
                <td>${ipHostInfo.orgName}</td>
                <td></td>
                <td align="right">设备类型:</td>
                <td>${ipHostInfo.ipHostType}</td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">使用人:</td>
                <td>${ipHostInfo.loginName}</td>
                <td></td>
                <td align="right">MAC地址:</td>
                <td>${ipHostInfo.macAddr}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">IP地址:</td>
                <td>${ipHostInfo.ipAddr}</td>
                <td></td>
                <td align="right">IP地址掩码:</td>
                <td>${ipHostInfo.ipNetMask}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">设备名称:</td>
                <td>${ipHostInfo.hostName}</td>
                <td></td>
                <td align="right">SNMP只读串:</td>
                <td>${ipHostInfo.snmpRoString}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">序列号:</td>
                <td>${ipHostInfo.serialNO}</td>
                <td></td>
                <td align="right">资产编号:</td>
                <td>${ipHostInfo.assertNO}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">操作系统:</td>
                <td>${ipHostInfo.osClass}</td>
                <td></td>
                <td align="right">操作系统类型:</td>
                <td>${ipHostInfo.osType}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">操作系统版本:</td>
                <td>${ipHostInfo.osVersion}</td>
                <td></td>
                <td align="right">操作系统特征:</td>
                <td>${ipHostInfo.osFeature}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">接入交换机:</td>
                <td>${ipHostInfo.switchIp}</td>
                <td></td>
                <td align="right">交换机接口:</td>
                <td>${ipHostInfo.switchIfIndex}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">信息点编号:</td>
                <td>${ipHostInfo.infoPointNo}</td>
                <td></td>
                <td align="right">Vlan:</td>
                <td>${ipHostInfo.vlanId}</td>
                <td></td>
            </tr>
            <tr>
            	<td colspan=6 align="center">
                </td>
            </tr>
		</table>
</body>
</html>

