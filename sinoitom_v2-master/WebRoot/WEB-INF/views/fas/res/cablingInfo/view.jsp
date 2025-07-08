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
<title>信息点连接信息</title>
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
                <td>${cablingInfo.orgId}</td>
                <td></td>
                <td align="right">楼宇名称：</td>
                <td>${cablingInfo.buildingNo}</td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">房间号：</td>
                <td>${cablingInfo.roomNo}</td>
                <td></td>
                <td align="right">信息点编号：</td>
                <td>${cablingInfo.infoPointNo}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">介质类型：</td>
                <td>${cablingInfo.mediaType}</td>
                <td></td>
                <td align="right">线缆类别：</td>
                <td>${cablingInfo.cableType}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">带宽：</td>
                <td>${cablingInfo.baudRate}</td>
                <td></td>
                <td align="right">长度(米)：</td>
                <td>${cablingInfo.length}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">模块号：</td>
                <td>${cablingInfo.moduleNo}</td>
                <td></td>
                 <td align="right">模块端口号：</td>
                <td>${cablingInfo.modulePort}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">交换机IP：</td>
                <td>${cablingInfo.switchIP}</td>
                <td></td>
                 <td align="right">交换机端口：</td>
                <td>${cablingInfo.ifName}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">机架号：</td>
                <td>${cablingInfo.cabinetNo}</td>
                <td></td>
                <td align="right">备注：</td>
                <td>${cablingInfo.remark}</td>
                <td></td>
            </tr>
            <tr>
            	<td colspan=6 align="center">
                </td>
            </tr>
		</table>
</body>
</html>

