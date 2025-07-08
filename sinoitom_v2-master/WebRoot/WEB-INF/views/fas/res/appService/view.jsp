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
<title>应用服务详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css" />
		<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
		
		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css" />
		<link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet" />
		<!-- ace styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />
		<!-- ace settings handler -->
		<script src="${ctx }/static/assets/js/ace-extra.min.js"></script>
		
		<script src="${ctx }/static/assets/js/jquery.min.js"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>
	    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script> 
		
		<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
		<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
		<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
		<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
		<script src="${ctx }/static/assets/js/resize.js"></script>

<script type="text/javascript">

$(function(){ 
	
 });



</script>

</head>
<body style="overflow: hidden;"> 
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
				<td align="right">服务器IP：</td>
				<td><input id="hostIp" name="hostIp" value="${resAppService.hostIp}" type="text" readonly="readonly" class="ip1"/></td>
				<td></td>
				<td align="right">协议号：</td>
				<td><input id="protNo" name="protNo" value="${resAppService.protNo }" type="text" readonly="readonly" class="ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">协议名称：</td>
				<td>
					<input id="protName" name="protName" value="${resAppService.protName }" type="text" readonly="readonly" class="ip1"/>
				</td>
				<td></td>
				<td align="right">服务端口：</td>
				<td><input id="svcPort" name="svcPort" value="${resAppService.svcPort }" type="text" readonly="readonly" class="ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">服务名称：</td>
				<td><input id="svcName" name="svcName" value="${resAppService.svcName }" type="text" readonly="readonly" ltype="text" class="ip1"  /></td>
				<td></td>
				<td align="right">服务命令：</td>
				<td><input id="command" name="command" value="${resAppService.command }" type="text" readonly="readonly" ltype="text" class="ip1"  /></td>
				<td></td>
			</tr>
			<tr>
			   	<td align="right">服务分类：</td>
				<td>
					<input id="svcClass" name="svcClass" value="<c:if test="${resAppService.svcClass==1}">Web</c:if><c:if test="${resAppService.svcClass==2}">App</c:if><c:if test="${resAppService.svcClass==3}">DB</c:if>" type="text" readonly="readonly" ltype="text" class="ip1"  />
				</td>
				<td></td>
				<td align="right">应用服务名称：</td>
				<td><input id="appSvcName" name="appSvcName" value="${resAppService.appSvcName }"  type="text" readonly="readonly" ltype="text" class="ip1"  /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">服务描述：</td>
				<td><input id="appSvcDescr" name="appSvcDescr" value="${resAppService.appSvcDescr }" type="text" readonly="readonly" ltype="text" class=" ip1"  /></td>
				<td ></td>
				<td align="right">用途描述：</td>
				<td><input id="usageDescr" name="usageDescr"  type="text" readonly="readonly" value="${resAppService.usageDescr}" class=" ip1"  /></td>
				<td ></td>
			</tr>

			<tr>
           		<td align="right" nowrap="nowrap">服务访问URL：</td>
				<td colspan=5><input id="svcUrl" name="svcUrl" value="${resAppService.svcUrl }" readonly="readonly" type="text" ltype="text" class=" ip2"  /></td>
            </tr>
		</table>
</body>
</html>

