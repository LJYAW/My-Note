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
<title>服务进程</title>
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

var passWord = '${passWord}';
var hostIp = '${hostIp}';
var userName = '${userName}';
var port = '${port}';
var hostId = '${hostId}';
var numCount;
var gridHeight;
$(function(){ 
	gridHeight= $(document).height()-180;
	
	//alert(${jsonListData}[${jsonListData}.length-1].pid);
	numCount=${jsonListData}.length;
	pageInit(numCount);	
 });


	function pageInit(num){
		jQuery("#list").jqGrid( {
			data:  ${jsonListData},
        datatype: "local",   
		
		colModel : [ 
		    	 {label:'PID',name : 'pid',index : 'pid',width : 60},
		    	 {label:'PPID',name : 'ppid',index : 'ppid',width : 60},
		    	 {label:'启动命令',name : 'startCmd',index : 'startCmd',width : 600
		    	}], 
		    	
		    	
		    	rowNum:num,
	        	 //rowNum : 50, rowList : [ 50, 100, 150 ], 
			 		//pager : '#pager',
			 		rownumbers: true,
			 		height:gridHeight,
			 		width:1000,
					altRows: true,
					multiselect: true,
					multiboxonly: true
<%--					loadComplete : function() {--%>
<%--						var table = this;--%>
<%--							setTimeout(function(){--%>
<%--							updatePagerIcons(table);--%>
<%--							}, 0);--%>
<%--					},--%>
	        	});
		
	} 
	
	function sendform(){
		var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (ids==""||ids==null) { 
        	 layer.msg('请选择要获取服务进程的数据!', {icon: 7, time: 1500});
        }else{
        	getServeProcRows(ids);
        }
	}
	
	function getServeProcRows(ids){
		$("#trTxt2").removeClass("dN");
		//alert(ids);
		var arrList = new Array();
        for (var i = 0; i < ids.length; i++) {
        	var rowData =  $("#list").jqGrid("getRowData",ids[i]);
        	
        	var rowStr = JSON.stringify(rowData);
        	if(rowStr.indexOf("%")>0){
        		rowStr=rowStr.replace(new RegExp(/%/,'g'),"@");
        	}
        	//alert(rowStr);
        	arrList.push(rowStr);
        }
        
		//var jsonStr = $("#jsonStr").val(JSON.stringify(arrList));
		
		//alert(jsonStr);
		//?jsonStr='+arrList+'&&hostIp='+hostIp+'&&userName='+userName+'&&passWord='+passWord+'&&port='+port+'&&hostId='+hostId
		//alert(arrList);
		var url = '${ctx}/fas/res/host/resServiceProcess/getServeProcRows.do';
		$.ajax({
    		url: url,
    		type:"POST",
    		data:{
    			jsonStr:"["+arrList+"]",
    			hostIp:hostIp,
    			userName:userName,
    			passWord:passWord,
    			port:port,
    			hostId:hostId
    		},
    		dataType: 'json',
    		success: function(data) {
    			if( data.result == 'success' ){
    				layer.msg('服务进程获取成功!',{icon:1, time: 1000},function(){
    					window.close();
        				window.opener.location.href=window.opener.location.href;
    	            });
    				
    			}
    		}
    	});
	}
	

</script>

</head>
<body> 

<input type="hidden" name="jsonStr" id="jsonStr" />

<%--<div id="btn" style="margin-top:2px;">--%>
<%--   	<button class="btn btn-white btn-default btn-bold" onclick="onSearchService()"  >--%>
<%--	<i class="ace-icon  fa fa-plus  green"></i>--%>
<%--		获取服务进程--%>
<%--	</button>--%>
<%--</div>		--%>
<div>
	<div  style="overflow: hidden;position:relative;z-index:1;">
	    <table id="list"></table>
<%--	    <div id="pager"></div>--%>
	</div>
	
	<div style="height: 5px;"></div>
	
	<div align="center" >
		<input id="bnSave" type="button" value="获取服务进程" onclick="sendform()" class="l-button mg6" />
	</div>
</div>

	<div id="trTxt2" class="dN" style="border:0px;height:31px;line-heigth:31px;text-align:left">
			<div style="width: 50%" align="right">
			<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;获取中...
           		</span>
			</div>
           		
       </div>

</body>
</html>

