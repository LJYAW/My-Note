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
<title>性能指标</title>
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
	cpuInit();	
	memInit();
	fileInit();
 });


	function cpuInit(){
		jQuery("#cpulist").jqGrid( {
			data:  ${plistJson},
        datatype: "local",   
		
		colModel : [ 
		    	 {label:'用户使用CPU占比',name : 'userUsed',index : 'userUsed',width : 100},
		    	 {label:'内核使用CPU占比',name : 'sysUsed',index : 'sysUsed',width : 100
		    	}], 
		    	
		    	width:300,
		    	height:40,
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
		    	 {label:'挂载点',name : 'mountPoint',index : 'mountPoint',width : 70},
		    	 {label:'总容量(KB)',name : 'size',index : 'size',width : 80},
		    	 {label:'已用(KB)',name : 'usedSize',index : 'usedSize',width : 80},
		    	 {label:'空闲(KB)',name : 'availSize',index : 'availSize',width : 80},
		    	 {label:'已用占比',name : 'diskUse',index : 'diskUse',width : 50
		    	}], 
		    	
		    	width:700,
		    	height:150,
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
	
	function memInit(){
		jQuery("#memlist").jqGrid( {
			data:  ${plistJson},
        datatype: "local",   
		
		colModel : [ 
		    	 {label:'总内存(KB)',name : 'memorySize',index : 'memorySize',width : 100},
		    	 {label:'已用内存(KB)',name : 'usedMemorySize',index : 'usedMemorySize',width : 100},
		    	 {label:'空闲内存(KB)',name : 'freeMemorySize',index : 'freeMemorySize',width : 100},
		    	 {label:'内存使用率',name : 'usedMem',index : 'usedMem',width : 100
		    	}], 
		    	
		    	width:450,
		    	height:40,
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
		
		<div style="overflow: hidden;">
	    	<table id="cpulist"></table>
		</div>
		<div style="overflow: hidden;">
	    	<table id="memlist"></table>
		</div>
		<div style="overflow: hidden;">
	    	<table id="filelist"></table>
		</div>
		<div style="height: 10px;"></div>
</body>
</html>

