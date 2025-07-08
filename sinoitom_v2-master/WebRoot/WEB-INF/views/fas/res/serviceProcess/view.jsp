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
<title>服务进程详情</title>
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
				<td><input id="hostIp" name="hostIp" value="${resServiceProcess.hostIp}" type="text" readonly="readonly" class="ip1"/></td>
				<td></td>
				<td align="right">进程ID：</td>
				<td><input id="pid" name="pid" value="${resServiceProcess.pid }" type="text" readonly="readonly" class="ip1" /></td>
				<td></td>
			</tr>
			<tr>
           		<td align="right" nowrap="nowrap">启动命令：</td>
				<td colspan=5><input id="startCmd" name="startCmd" value="${resServiceProcess.startCmd }" readonly="readonly" type="text" ltype="text" class=" ip2"  /></td>
            </tr>
			<tr>
				<td align="right">进程类型：</td>
				<td>
					<input id="procType" name="procType" value="${resServiceProcess.procType }" type="text" readonly="readonly" class="ip1"/>
				</td>
				<td></td>
				<td align="right">进程名称：</td>
				<td><input id="procName" name="procName" value="${resServiceProcess.procName }" type="text" readonly="readonly" class="ip1" /></td>
				<td></td>
			</tr>
			<tr>
           		<td align="right" nowrap="nowrap">进程描述：</td>
				<td colspan=5><input id="procDescr" name="procDescr" value="${resServiceProcess.procDescr }"  type="text" ltype="text" readonly="readonly" class="ip2"  /></td>
            </tr>
			<tr>
				<td align="right">进程标志：</td>
				<td>
					<input id="flag" name="flag" value="<c:if test="${resServiceProcess.flag==0}">系统进程</c:if><c:if test="${resServiceProcess.flag==1}">服务进程</c:if>" type="text" readonly="readonly" ltype="text" class="ip1"  />
				</td>
				<td></td>
				<td align="right">监测状态：</td>
				<td>
					<input id="status" name="status" value="<c:if test="${resServiceProcess.status==0}">未监测</c:if><c:if test="${resServiceProcess.status==1}">监测</c:if>" type="text" readonly="readonly" ltype="text" class="ip1"  />
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">创建者：</td>
				<td>
					<input id="creator" name="creator" value="${resServiceProcess.creator }" type="text" readonly="readonly" class="ip1"/>
				</td>
				<td></td>
				<td align="right">创建时间：</td>
				<td><input id="createTime" name="createTime" value="${resServiceProcess.createTime }" type="text" readonly="readonly" class="ip1" /></td>
				<td></td>
			</tr>
		</table>
</body>
</html>

