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
<title>子网查看</title>
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
                <td align="right">所属机构:</td>
                <td colspan=4>${subnet.orgName}</td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">子网IP地址:</td>
                <td>${subnet.subnetAddr}</td>
                <td></td>
                <td align="right">子网掩码:</td>
                <td>${subnet.subnetMask}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">子网起始IP:</td>
                <td>${s:long2Ip(subnet.hostIpStart)}</td>
                <td></td>
                <td align="right">子网结束IP:</td>
                <td>${s:long2Ip(subnet.hostIpEnd)}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">地址类型:</td>
                <td>${s:resVal('Subnet_Type',subnet.subnetType)}</td>
                <td></td>
                <td align="right">子网类型:</td>
                <td>${s:resVal('Subnet_Usage',subnet.subnetUsage)}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">子网名称:</td>
                <td>${subnet.subnetName}</td>
                <td></td>
                <td align="right">VLAN名称:</td>
                <td>${subnet.vlanName}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">管理状态:</td>
                <td>${s:resVal('Subnet_AdminStatus',subnet.adminStatus)}</td>
                <td></td>
                <td align="right">工作状态:</td>
                <td>${s:resVal('Subnet_OperStatus',subnet.operStatus)}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">子网描述:</td>
                <td colspan=4>${subnet.subnetDesc}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">扫描任务:</td>
                <td>${subnet.taskId}</td>
                <td></td>
                <td align="right">扫描标志:</td>
                <td>${subnet.scanDetails}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">是否采集:</td>
                <td colspan=4>${s:resVal('IsCollection',subnet.isCollection)}</td>
                <td></td>
            </tr>   
            <tr>
            	<td colspan=6 align="center">
                </td>
            </tr>
		</table>
</body>
</html>

