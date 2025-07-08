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
<title>计算资源详情</title>
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
	diskInit();	
	fileInit();
	interfaceInit();
	cpuInit();
 });


	function diskInit(){
		jQuery("#disklist").jqGrid( {
			data:  ${diskListJson},
        datatype: "local",   
		
		colModel : [ 
		    	 {label:'磁盘大小',name : 'size',index : 'size',width : 60},
		    	 {label:'字节数大小',name : 'bytes',index : 'bytes',width : 60},
		    	 {label:'总扇区个数',name : 'totalSectors',index : 'totalSectors',width : 50},
		    	 {label:'磁柱个数',name : 'diskCylinders',index : 'diskCylinders',width : 50},
		    	 {label:'磁盘标识符',name : 'diskIdentifier',index : 'diskIdentifier',width : 100
		    	}], 
		    	
		    	width:850,
		    	height:50,
		    	 viewrecords: true,
					altRows: true,
					toolbar:[true,'top'],
					multiboxonly: true,
					loadComplete : function() {
						var table = this;
							setTimeout(function(){
							updatePagerIcons(table);
							}, 0);
					},
		    	});
		
	} 
	
	function fileInit(){
		jQuery("#filelist").jqGrid( {
			data:  ${fileListJson},
        datatype: "local",   
		
		colModel : [ 
		    	 {label:'文件系统',name : 'filesystem',index : 'filesystem',width : 200},
		    	 {label:'挂载点',name : 'mountPoint',index : 'mountPoint',width : 100},
		    	 {label:'容量(KB)',name : 'size',index : 'size',width : 80},
		    	 {label:'已用(KB)',name : 'usedSize',index : 'usedSize',width : 80},
		    	 {label:'可用(KB)',name : 'availSize',index : 'availSize',width : 80},
		    	 {label:'已用占比',name : 'diskUse',index : 'diskUse',width : 50
		    	}], 
		    	
		    	width:850,
		    	height:100,
		    	 viewrecords: true,
					altRows: true,
					toolbar:[true,'top'],
					multiboxonly: true,
					loadComplete : function() {
						var table = this;
							setTimeout(function(){
							updatePagerIcons(table);
							}, 0);
					},
		    	});
		
	} 
	
	function interfaceInit(){
		jQuery("#interfacelist").jqGrid( {
			data:  ${infoListJson},
        datatype: "local",   
		
		colModel : [ 
		    	 {label:'网络接口名称',name : 'infoName',index : 'infoName',width : 80},
		    	 {label:'MAC地址',name : 'macAddress',index : 'macAddress',width : 120},
		    	 {label:'Ip地址',name : 'ipAddress',index : 'ipAddress',width : 110},
		    	 {label:'广播地址',name : 'bcastAddress',index : 'bcastAddress',width : 110},
		    	 {label:'掩码地址',name : 'maskAddress',index : 'maskAddress',width : 110},
		    	 {label:'IPV6',name : 'ipv6',index : 'ipv6',width : 160
		    	}], 
		    	
		    	width:850,
		    	height:70,
		    	 viewrecords: true,
					altRows: true,
					toolbar:[true,'top'],
					multiboxonly: true,
					loadComplete : function() {
						var table = this;
							setTimeout(function(){
							updatePagerIcons(table);
							}, 0);
					},
		    	});
		
	} 
	
	function cpuInit(){
		jQuery("#cpulist").jqGrid( {
			data:  ${cpuListJson},
        datatype: "local",   
		
		colModel : [ 
		    	 {label:'Cpu编号',name : 'cpuCoreId',index : 'cpuCoreId',width : 80},
		    	 {label:'Cpu型号',name : 'cpuModel',index : 'cpuModel',width : 220},
		    	 {label:'厂商',name : 'cpuVendor',index : 'cpuVendor',width : 100},
		    	 {label:'Cpu位数',name : 'cpuWidth',index : 'cpuWidth',width : 50},
		    	 {label:'Cpu架构',name : 'cpuStruct',index : 'cpuStruct',width : 50}
		    	 ], 
		    	
		    	width:850,
		    	height:100,
		    	 viewrecords: true,
					altRows: true,
					toolbar:[true,'top'],
					multiboxonly: true,
					loadComplete : function() {
						var table = this;
							setTimeout(function(){
							updatePagerIcons(table);
							}, 0);
					},
		    	});
		
	} 

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
                <td align="right">主机名称:</td>
                <td>${resHosts.hostName}</td>
                <td></td>
                <td align="right">管理IP地址:</td>
                <td>${resHosts.ipAddress}</td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">操作系统名称:</td>
                <td>${resHosts.osName}</td>
                <td></td>
                <td align="right">操作系统类型:</td>
                <td>${resHosts.osType}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">操作系统版本:</td>
                <td>${resHosts.osVersion}</td>
                <td></td>
                <td align="right">操作系统服务包:</td>
                <td>${resHosts.osRelease}</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">内存空间:</td>
                <td>${resHosts.memorySize} KB</td>
                <td></td>
                <td align="right">访问方式:</td>
                <td>${resHosts.accessmode}</td>
                <td></td>
            </tr>
<%--            <tr>--%>
<%--                <td align="right">CPU厂商:</td>--%>
<%--                <td>${resHosts.cpuVendor}</td>--%>
<%--                <td></td>--%>
<%--                <td align="right">CPU型号:</td>--%>
<%--                <td>${resHosts.cpuModel}</td>--%>
<%--                <td></td>--%>
<%--            </tr>--%>
            <tr>
                <td align="right">创建人:</td>
                <td>${resHosts.creator}</td>
                <td></td>
                <td align="right">创建时间:</td>
                <td>${resHosts.createTime}</td>
                <td></td>
            </tr>
            <tr>
            	<td colspan=6 align="center">
                </td>
            </tr>
		</table>
		
		<div style="overflow: hidden;">
	    	<table id="disklist"></table>
		</div>
		
		<div style="overflow: hidden;">
	    	<table id="filelist"></table>
		</div>
		
		<div style="overflow: hidden;">
	    	<table id="interfacelist"></table>
		</div>
		
		<div style="overflow: hidden;">
	    	<table id="cpulist"></table>
		</div>
		<div style="height: 10px;"></div>
</body>
</html>

