<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.sino.base.common.Global"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<shiro:hasPermission name="employee:read"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>数据库管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css"/>
    <link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet"/>
    <!-- ace styles -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/layer.css"/>
</head>



<!-- basic scripts -->
<script src="${ctx }/static/assets/js/jquery.min.js"></script>
<script src="${ctx }/static/assets/js/layer.js"></script>
<script src="${ctx }/static/assets/js/H-ui.admin.js"></script>

<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
<!-- page specific plugin scripts -->
<!-- 		<script src="${ctx }/static/assets/js/bootstrap-datepicker.min.js"></script> -->
<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
<script src="${ctx }/static/assets/js/resize.js"></script>
<script src="${ctx }/static/assets/js/bootbox.js"></script>
 <script type="text/javascript">
    	 var gridHeight;
    	
    	$(function (){
    		gridHeight = $(document).height() - 120 - $('#btn').height();
        	pageInit();
        	
    		resize();  //自动调整页面Grid
        	bootbox.setDefaults("locale", "zh_CN");
          	
        });
        
      function pageInit(){
       	jQuery("#list").jqGrid({
	        data: ${jsonData},
            datatype: "local",
            colNames: [ 'id','服务器IP', '服务器名称', '数据库类型', '数据库版本', '数据库名称', '服务端口','用户名','密码','验证状态','审核状态'],
            colModel:  
	          [
	          { name: 'id', width: 100, hidden: true,key:true},
	          { name:'hostIp', width:150, align:'left'},
	          { name:'hostName', width:150, align:'left'},
	          { name:'dbType', width:80, align:'left', hidden: true},
	          { name:'dbVersion', width:150, align:'left'},
	          { name:'dbName', width:150, align:'left'},
	          { name:'dbPort', width:150, align:'left'},
	          { name:'userName', width:150, align:'left'},
	          { name:'password', width:150, align:'left'},
	          { name:'verifyStatus',width:150, align:'center',
	            	formatter:function(cellvalue, options, rowObject){
						var html='';
						if(cellvalue==0){
						 	html += '<span style="color:blue;">未验证</span>';
						} else if(cellvalue==1){
							html += '<span style="color:red;">已验证</span>';
						} else{
							html += '<span style="color:blue;">未验证</span>';
						}
						return html;
					}
	           },
	          { name:'auditStatus',width:150, align:'center',
	            	formatter:function(cellvalue, options, rowObject){
						var html='';
						if(cellvalue==0){
						 	html += '<span style="color:blue;">未审核</span>';
						} else if(cellvalue==1){
							html += '<span style="color:red;">已审核</span>';
						} else{
							html += '<span style="color:blue;">未审核</span>';
						}
						return html;
					}
	           }
	           ], 
	            rownumbers: true,
				rowNum: 20, rowList: [20, 50, 100],
	            pager: '#pager',
	            height: gridHeight,
	            toolbar: [true, 'top'],
	            sortname: 'id',
	            //mtype : "post",
	            viewrecords: true,
	            sortorder: "desc",
// 	            multiselect: true,
	            loadComplete: function () {
                	jQuery("#list").jqGrid('setLabel','rn', '序号', {'text-align':'center','vertical-align':'middle'},'');
            	}
         });
         jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});

	     $("#t_list").css({"height": 30});
	     $("#btn").appendTo("#t_list");
     }
    
//     添加
    function onAdd(){    
        var url = '${ctx}/fas/res/db/index/toAdd.do';
         layer_show("添加", url, 800, 500);
    }
        
// 		修改
        function onEdit(){
			var selectedId = jQuery("#list").jqGrid('getGridParam', 'selrow');
	
	        if (selectedId == "" || selectedId == null) {
	            layer.msg('请选择一条要修改的数据库信息!', {icon: 7, time: 1500});
	            return;
	        }
	        
	        var row = $("#list").jqGrid('getRowData', selectedId);
	         if(row.auditStatus.indexOf("已审核")>=0){
            	layer.msg('已经通过审核！', {icon: 7, time: 1500});
            	return;
            }else{
            	editRow(selectedId);
            }
        	
        }
        
        function editRow(id){   
        	var url = '${ctx}/fas/res/db/index/toEdit.do?dbsId='+id;
     		layer_show("修改", url, 800, 450);
        }

        function saveOK(action, data){
        	if(data!=null && data!=undefined && data!=''){
        		layer.msg('data', {icon: 7, time: 1500});
        		return;
        	}
        	layer.closeAll();
        	window.location.reload();
        }
        
//         删除
        function onDelete(){
        	var selectedId = jQuery("#list").jqGrid('getGridParam', 'selrow');
	        if (selectedId == "" || selectedId == null) {
	            layer.msg('请选择一条要删除的数据库信息!', {icon: 7, time: 1500});
	            return;
	        }	
	        
	        var row = $("#list").jqGrid('getRowData', selectedId);
	        if(row.auditStatus.indexOf("已审核")>=0){
            	layer.msg('已经通过审核！', {icon: 7, time: 1500});
            	return;
            }
	        
	        layer.confirm('是否确认删除所选数据库信息？', function (yes) {
	            deleteDate(selectedId);
	        });
        }
        
      //大批量的删除
  	function deleteDate(id){
    	  
       var url = '${ctx}/fas/res/db/index/delete.do?id='+id; 
		$.ajax({
            url: url,
            dataType: 'json',
            success: function (data) {
                if (data.result == "success") {
                    layer.msg('删除成功!', {icon: 1, time: 1500});
                } else {
                    layer.msg('删除失败!', {icon: 2, time: 1500});
                }

                window.location.reload();
            }
        });
       }
		
// 		显示
		function onDisplay(){
			var selectedId = jQuery("#list").jqGrid('getGridParam', 'selrow');
	        if (selectedId == "" || selectedId == null) {
	            layer.msg('请选择一条要修改的数据库信息!', {icon: 7, time: 1500});
	            return;
	        }
		
			var url = '${ctx}/fas/res/db/index/detail.do?dbsId='+selectedId;
  		    layer_show("详情", url, 800, 500);
		}
// 		检测指标
		function onDetectionIndicator(){
			var selectedId = jQuery("#list").jqGrid('getGridParam', 'selrow');
	        if (selectedId == "" || selectedId == null) {
	            layer.msg('请选择一条要查看的数据库信息!', {icon: 7, time: 1500});
	            return;
	        }
		
			var url = '${ctx}/fas/res/db/index/getDetectionIndicator.do?dbsId='+selectedId;
  		    layer_show("检测指标", url, 800, 500);
		}
		
// 		运行状况
		function onOperaStatus(){
			var selectedId = jQuery("#list").jqGrid('getGridParam', 'selrow');
	        if (selectedId == "" || selectedId == null) {
	            layer.msg('请选择一条要查看的数据库信息!', {icon: 7, time: 1500});
	            return;
	        }
		
			var url = '${ctx}/fas/res/db/index/toOperaStatus.do?dbsId='+selectedId;
  		    layer_show("运行状况", url, 800, 500);
		}
		
// 		审核
		function onAudit(){
			var selectedId = jQuery("#list").jqGrid('getGridParam', 'selrow');
	        if (selectedId == "" || selectedId == null) {
	            layer.msg('请选择一条要修改的信息!', {icon: 7, time: 1500});
	            return;
	        }
	        var row = $("#list").jqGrid('getRowData', selectedId);
            if(row.auditStatus.indexOf("已审核")>=0){
            	layer.msg('已经通过审核！', {icon: 7, time: 1500});
            	return;
            }else{
            	audit(selectedId);
            }
            
		}
		
	function audit(id){
       var url = '${ctx}/fas/res/db/index/audit.do?id='+id; 
      	$.ajax({
      	    type: "POST",
      	    url:url,
      	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
			  
      		success: function(data) {
      			layer.msg('审核成功!', {icon: 1, time: 1500});
      			window.location.reload();
      		}
      	}); 
       }


// 		刷新
        function onRefresh(){
        	window.location.reload();
        }
        
        function onAccessVerify(){
        	var selectedId = jQuery("#list").jqGrid('getGridParam', 'selrow');
	        if (selectedId == "" || selectedId == null) {
	            layer.msg('请选择一条要修改的信息!', {icon: 7, time: 1500});
	            return;
	        }
	        
        	$.getJSON(
		     "${ctx}/fas/res/db/index/accessVerify.do?id="+selectedId,
		     function (data) {
		         if (data!= null) {
		             if (data.result == "success") {
	                    layer.msg('访问验证通过!', {icon: 1, time: 1500});
	                } else {
	                    layer.msg('访问验证失败!', {icon: 2, time: 1500});
	                }
	                setTimeout(function () {
					    window.location.reload();
					}, 3000);
		         }
		     });
        }
        
    </script>
</head>
<body> 
 	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
	    <ul class="breadcrumb">
	        <li class="active">${titleContent }</li>
	    </ul>
	</div>
	<div id="btn" style="margin-top:2px;">
    <button class="btn btn-white btn-default btn-bold" onclick="onAdd()">
        <i class="ace-icon fa  fa-plus bigger-120"></i>
        添加
    </button>
    <button class="btn btn-white btn-info btn-bold" onclick="onEdit()">
        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
        修改
    </button>
    <button class="btn btn-white btn-warning btn-bold" onclick="onDelete()">
        <i class="ace-icon fa fa-times bigger-120"></i>
        删除
    </button>
    <button class="btn btn-white btn-danger btn-bold" onclick="onDisplay()">
        <i class="ace-icon fa fa-file-text-o bigger-120"></i>
        详情
    </button>
    <button class="btn btn-white btn-danger btn-bold" onclick="onAccessVerify()">
        <i class="ace-icon fa fa-file-text-o bigger-120"></i>
        访问验证
    </button>
    <button class="btn btn-white btn-danger btn-bold" onclick="onDetectionIndicator()">
        <i class="ace-icon fa fa-file-text-o bigger-120"></i>
        检测指标
    </button>
    <button class="btn btn-white btn-danger btn-bold" onclick="onOperaStatus()">
        <i class="ace-icon fa fa-file-text-o bigger-120"></i>
        运行状况
    </button>
     <button class="btn btn-white btn-purple btn-bold" onclick="onAudit()">
        <i class="ace-icon fa fa-check-square bigger-120 purple"></i>
        审核
    </button>
    <button class="btn btn-white btn-success btn-bold" onclick="onRefresh()">
        <i class="ace-icon fa fa-refresh bigger-120"></i>
        刷新
	    </button>
	
	</div>
	
	<div class="col-xs-12" style="overflow: hidden;">
	    <table id="list"></table>
	    <div id="pager"></div>
	</div>
</body>
</html>
</shiro:hasPermission>

