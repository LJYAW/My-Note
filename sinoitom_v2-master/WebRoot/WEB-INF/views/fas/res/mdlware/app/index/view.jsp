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
<title>详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script type="text/javascript">

    $(function ()
    {
		$(".table1").find("td").css("fontSize", "14px");
        $(".table1").find("td").css("lineHeight", "15px");
    });





</script>
</head>
<body>
		<table  align="center" width="850px;" style="border-collapse:separate; border-spacing:10px 15px;word-break:break-all; word-wrap:break-word;">
			<tr>
				<td colspan="6" align="center" style="font-size: 16px;font-weight: bold;">详情展示</td>
			</tr>
			<tr>
				<td colspan="6" align="center" style="font-size: 16px;font-weight: bold;">监测时间：${viewDate}</td>
			</tr>
			<tr class="table1">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>


			<tr class="table1">
				<td align="right" nowrap="nowrap">连接名称：</td>
				<td id="devIpAddr">${info.devIpAddr }</td>
				<td></td>
				<td align="right" nowrap="nowrap">运行时间：</td>
				<td id="uptime">${entity.uptime }</td>
				<td></td>
			</tr>
			<tr class="table1">
				<td align="right" nowrap="nowrap">虚拟机：</td>
				<td id="vmName">${entity.vmName }</td>
				<td></td>
				<td align="right" nowrap="nowrap">JIT 编译器: </td>
				<td id="jitName">${info.jitName }</td>
				<td></td>
			</tr>
			<tr class="table1">
				<td align="right" nowrap="nowrap">供应商：</td>
				<td id="vmVendor">${entity.vmVendor }</td>
				<td></td>
				<td align="right" nowrap="nowrap">总编译时间: </td>
				<td id="jitTime">${info.jitTime }毫秒</td>
				<td></td>
			</tr>
			<tr class="table1">
				<td align="right" nowrap="nowrap">版本：</td>
				<td id="vmVersion">${entity.vmVersion }</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr class="table1">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>


			<tr class="table1">
				<td align="right" nowrap="nowrap">活动线程：</td>
				<td id="threadCount">${thread.threadCount }</td>
				<td></td>
				<td align="right" nowrap="nowrap">已加装当前类：</td>
				<td id="loadedClassCount">${monitorCpu.loadedClassCount }</td>
				<td></td>
			</tr>
			<tr class="table1">
				<td align="right" nowrap="nowrap">峰值：</td>
				<td id="peakThreadCount">${thread.peakThreadCount }</td>
				<td></td>
				<td align="right" nowrap="nowrap">已加载类总数：</td>
				<td id="totalLoadedClassCount">${monitorCpu.totalLoadedClassCount }</td>
				<td></td>
			</tr>
			<tr class="table1">
				<td align="right" nowrap="nowrap">守护程序线程：</td>
				<td id="daemonThreadCount">${thread.daemonThreadCount }</td>
				<td></td>
				<td align="right" nowrap="nowrap">已卸载类总数：</td>
				<td id="unloadedClassCount">${monitorCpu.unloadedClassCount }</td>
				<td></td>
			</tr>
			<tr class="table1">
				<td align="right" nowrap="nowrap">启动的线程总数：</td>
				<td id="totalStartedThreadCount">${thread.totalStartedThreadCount }</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr class="table1">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>


			<tr class="table1">
				<td align="right" nowrap="nowrap">当前堆大小：</td>
				<td id="heapused">${heap.heapused }</td>
				<td></td>
				<td align="right" nowrap="nowrap">提交的内存：</td>
				<td id="heapcommitted">${heap.heapcommitted }</td>
				<td></td>
			</tr>
			<tr class="table1">
				<td align="right" nowrap="nowrap">最大的堆大小：</td>
				<td id="heapmax">${heap.heapmax }</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr class="table1">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>

			<tr class="table1">
				<td align="right" nowrap="nowrap">操作系统：</td>
				<td id="osName">${info.osName }</td>
				<td></td>
				<td align="right" nowrap="nowrap">总物理内存：</td>
				<td id="totalMem">${info.totalMem }</td>
				<td></td>
			</tr>
			<tr class="table1">
				<td align="right" nowrap="nowrap">体系结构：</td>
				<td id="osArch">${info.osArch }</td>
				<td></td>
				<td align="right" nowrap="nowrap">空闲物理内存：</td>
				<td id="freeMem">${info.freeMem }</td>
				<td></td>
			</tr>
			<tr class="table1">
				<td align="right" nowrap="nowrap">处理程序数：</td>
				<td id="availableProcessors">${info.availableProcessors }</td>
				<td></td>
				<td align="right" nowrap="nowrap">总交换空间：</td>
				<td id="swapTotal">${info.swapTotal }</td>
				<td></td>
			</tr>
			<tr class="table1">
				<td align="right" nowrap="nowrap">提交的虚拟内存：</td>
				<td id="committedMem">${info.committedMem }</td>
				<td></td>
				<td align="right" nowrap="nowrap">空闲交换空间：</td>
				<td id="swapFree">${info.swapFree }</td>
				<td></td>
			</tr>
			<tr class="table1">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>


			<tr class="table1">
				<td align="right">VM参数：</td>
				<td colspan="5" id="vms" >${entity.vms }</td>
			</tr>
			<tr class="table1">
				<td align="right" >类路径：</td>
				<td colspan="5" id="classPath">${entity.classPath }</td>
			</tr>
			<tr class="table1">
				<td align="right" >库路径：</td>
				<td colspan="5" id="libraryPath">${entity.libraryPath }</td>
			</tr>
			<tr class="table1">
				<td align="right" >引导类路径：</td>
				<td colspan="5" id="bootClassPath">${entity.bootClassPath }</td>
			</tr>
		</table>
</body>
</html>

