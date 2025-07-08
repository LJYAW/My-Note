<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>报告模板</title>

		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css"/>
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
        

<body class="no-skin">
<div id="btn" style="margin-top:2px;">
    <button class="btn btn-white btn-default btn-bold" onclick="onAdd()">
		<i class="ace-icon fa  fa-plus bigger-120 default"></i>
		添加
	</button>
	<button class="btn btn-white btn-info btn-bold"onclick="onEdit()">
		<i class="ace-icon fa fa-pencil-square-o bigger-120 blue"></i>
		修改
	</button>
	<button class="btn btn-white btn-warning btn-bold"onclick="onDelete()">
		<i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
		删除
	</button>
	<button class="btn btn-white btn-warning btn-bold" onclick="onAudit()">
         <i class="ace-icon fa fa-sign-in bigger-120"></i>
       	审核
    </button>
	<button class="btn btn-white btn-info btn-bold"onclick="onModel()">
		<i class="ace-icon fa fa-pencil-square-o bigger-120 blue"></i>
		样式设置
	</button>
	<button class="btn btn-white btn-info btn-bold" onclick="tableView()">
        <i class="ace-icon fa fa-tasks bigger-120"></i>
                效果预览
    </button>
	<button class="btn btn-white btn-success btn-bold"onclick="onRefresh()" >
		<i class="ace-icon fa fa-refresh bigger-120 green"></i>
		刷新
	</button>
   
</div>

<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li class="active">${titleContent }</li>
    </ul>
	</div>

	<div class="col-xs-12" style="overflow: hidden;">
		
		<table id="list"></table>
		
		<div id="pager"></div>

	</div><!-- /.col -->
 <script type="text/javascript">
 	
 var layerHeight;
 	var gridHeight,gridWidth;		
	$(function(){ 
		layerHeight = $(document).height() -30 ;
	 	gridHeight= $(document).height() - 120 - $('#btn').height();
	 	gridWidth = $(document).width();
		pageInit();	
		resize();  //自动调整页面Grid	  
	 });
	
	function pageInit(){
		jQuery("#list").jqGrid( {
			 data:  ${jsonListData},
				//  datatype:从服务器端返回的数据类型，默认xml。可选类型：xml，local(客户端数据（数组）)，json，jsonnp，script，xmlstring，jsonstring，clientside
				 datatype : "local", 
     

	 colNames : [ '模板名称', '标题', '模板类型', '状态'],
	 colModel : [ 
	        	 {name : 'tmplName',index : 'tmplName',width : 160},
	        	 {name : 'tabTitle',index : 'tabTitle',width : 160},
	        	 {name : 'tmplType',index : 'tmplType',width : 80,
	        		 formatter:function(cellvalue, options, row){
	        			 var html = '';
	        			 if (row.tmplType == "1") {
                             html += '<span >普通模板</span>';
                         } else if (row.tmplType == "2") {
                             html += '<span>符号模板</span>';
                         } 
	        			 return html;
	        		 }
	        	 },
	        	 {name : 'status',index : 'status',width : 80,
	        		 formatter:function(cellvalue, options, row){
	        			 var html = '';
	        			 if (row.status == "0") {
                             html += '<span style="color:red;">未审核</span>';
                         } else if (row.status == "1") {
                             html += '<span style="color:green;">已审核</span>';
                         } 
	        			 return html;
	        		 }
	        	 }
	        	 ], 
	        	 
	        	 viewrecords: true,
	        	 rowNum : 50, rowList : [ 50, 100, 150 ], 
			 		pager : '#pager',
			 		height:gridHeight,
					altRows: true,
					toolbar:[true,'top'],
					multiselect: false,
					multiboxonly: true,
					loadComplete : function() {
						var table = this;
							setTimeout(function(){
							updatePagerIcons(table);
							}, 0);
					},
	        	});
		jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
							$("#t_list").css({"height":30});
		            $("#btn").appendTo("#t_list");

	}
	
	
        
      //添加
        function onAdd(){
     		layer_show("添加报告模板",'${ctx}/cmdb/indicator/report/add.do',700,280);
        } 
      
        function saveOK()
        {
        	layer.msg('保存成功!', {icon: 1, time: 1000});
        	onRefresh();
        }
        
        function onEdit() {
		
			var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
			var rowData = jQuery("#list").jqGrid('getRowData',rowid);
		
			 if (rowid==""||rowid==null) { 
			 	layer.msg('请选择一条模板数据！', {icon: 7, time: 1500});
			 	return; 
			 }else{
				 if(rowData.status.indexOf("已审核")>=0){
					 layer.msg('此报告模板已通过审核，不能再做修改！', {icon: 7, time: 1500});
					 return; 
				 }else if(rowData.status.indexOf("未审核")>=0){
					 editCheckModel(rowid);
				 }
			 }
			
		}
        
      //检查当前模板是否设置过样式
        function editCheckModel(id){
        	var url = '${ctx}/cmdb/indicator/report/checkTmplCell.do';
        	$.ajax({
        	    type: "POST",
        	    url: url,
        	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
        	    data:{"id":id},
        		//dataType: 'json',
        		success: function(data) {
        			 if(data.success==0){
        				 editRow(id);
        			}else{
			    	 	layer.msg('此报告模板未进行样式设置，不能进行模板样式修改！', {icon: 7, time: 2000});
			    	 	return ;
			     	}
        		}
        	}); 
        }
        
		function editRow(id)
		{
			if(gridHeight<550){
	          	//layer_show("修改报告模板",'${ctx}/cmdb/indicator/report/edit.do?id='+id,gridWidth-100,layerHeight)
	          	gridWidth = gridWidth-100;
	    	}else{
	    		gridWidth = 1100 ;
	    		layerHeight = 650;
	    	}
			layer_show("修改报告模板",'${ctx}/cmdb/indicator/report/edit.do?id='+id,gridWidth,layerHeight);
		}
		
		//审核
		function onAudit(){
			var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
			var rowData = jQuery("#list").jqGrid('getRowData',rowid);
		
			 if (rowid==""||rowid==null) { 
			 	layer.msg('请选择一条模板数据！', {icon: 7, time: 1500});
			 	return; 
			 }else{
				 if(rowData.status.indexOf("已审核")>=0){
					 layer.msg('此报告模板已通过审核，不能再做审核！', {icon: 7, time: 1500});
					 return; 
				 }else if(rowData.status.indexOf("未审核")>=0){
					 checkModelCell(rowid);
				 }
			 }
		}
		function checkModelCell(id){
        	var url = '${ctx}/cmdb/indicator/report/checkTmplCell.do';
        	$.ajax({
        	    type: "POST",
        	    url: url,
        	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
        	    data:{"id":id},
        		//dataType: 'json',
        		success: function(data) {
        			 if(data.success==0){
        				 auditRow(id);
        			}else{
        				layer.msg('此报告模板未进行样式设置，暂不能审核！', {icon: 7, time: 2000});
        				return ;
			     	}
        		}
        	}); 
        }
		function auditRow(id)
		{
			var url = '${ctx}/cmdb/indicator/report/audit.do';
        	$.ajax({
        	    type: "POST",
        	    url: url,
        	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
        	    data:{"id":id},
        		//dataType: 'json',
        		success: function(data) {
        			 if(data.success==0){
        				 layer.msg('审核成功!', {icon: 1, time: 1500});
			           	 onRefresh(); 
				      	}
        		}
        	}); 
		}
        
        //删除模板
        function onDelete()
        {	
        	var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');
        	var rowData = jQuery("#list").jqGrid('getRowData',rowid);
	   		 if (rowid==""||rowid==null) { 
	   			layer.msg('请选择要删除的模板数据！', {icon: 7, time: 1500});
	   		 	return; 
	   		 }else{
	   			if(rowData.status.indexOf("已审核")>=0){
					 layer.msg('此报告模板已通过审核，不可删除！', {icon: 7, time: 1500});
					 return; 
				 }else if(rowData.status.indexOf("未审核")>=0){
					 deleteRow(rowid);
				 }
	   		 }
        	
        }
        
        function deleteRow(id)
		{
        	
        	layer.confirm('是否确认删除所选模板数据？', function (yes) 
			{
				delRow(id);
			});
		}
        
        function delRow(id)
        {   
        	var url = '${ctx}/cmdb/indicator/report/delete.do';
        	$.ajax({
        	    type: "POST",
        	    url: url,
        	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
        	    data:{"id":id},
        		//dataType: 'json',
        		success: function(data) {
        			 if(data.success==0){
        				 layer.msg('删除成功!',{icon:1, time: 1000},function(){
                         	onRefresh(); // 页面刷新
         	            });
        				 //layer.msg('删除成功!', {icon: 1, time: 1500});
				     }
        		}
        	});        			
        }
        
        
        //模板样式设置
        function onModel()
        {
        	
        	var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');
        	var rowData = jQuery("#list").jqGrid('getRowData',rowid);
        	
	   		 if (rowid==""||rowid==null) { 
	   			layer.msg('请先选择模板信息！', {icon: 7, time: 1500});
	   		 	return; 
	   		 }else{
	   			if(rowData.status.indexOf("已审核")>=0){
					 layer.msg('此报告模板已通过审核，不能再设置模板样式！', {icon: 7, time: 1500});
					 return; 
				 }else if(rowData.status.indexOf("未审核")>=0){
					 checkModel(rowid);
				 }
	   		 }
            
        }
        
        //检查当前模板是否设置过样式
        function checkModel(id){
        	var url = '${ctx}/cmdb/indicator/report/checkTmplCell.do';
        	$.ajax({
        	    type: "POST",
        	    url: url,
        	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
        	    data:{"id":id},
        		//dataType: 'json',
        		success: function(data) {
        			 if(data.success==0){
        				layer.confirm('此模板已经设置样式，是否继续操作修改样式设置？',{btn:['确认','取消']},function(index){
        					layer.close(index);
        					editStyle(id);
        				});
        			}else{
			    	 	showModel(id);
			     	}
        		}
        	}); 
        }
        
      //跳转模板样式修改页面
        function editStyle(id){
	    	if(gridWidth<1100){
	    		gridWidth = gridWidth-100 ;
	    	}else{
	    		gridWidth = 1100 ;
	    	}
	    	layer_show("样式修改",'${ctx}/cmdb/indicator/report/editStyle.do?id='+id,gridWidth,layerHeight);
        }
        
        //跳转模板样式设置页面
        function showModel(id){
        	if(gridWidth<1100){
        		gridWidth = gridWidth-100 ;
        	 }else{
        		 gridWidth = 1100;
        	 }
        	layer_show("样式设置",'${ctx}/cmdb/indicator/report/onTable.do?id='+id,gridWidth,layerHeight);
        }
        
        
        //模板样式页面效果展示
        function tableView()
        {
            var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');
            
	   		 if (rowid==""||rowid==null) { 
	   			layer.msg('请先选择要展示样式效果的模板！', {icon: 7, time: 1500});
	   		 	return; 
	   		 }
	   		checkTmplCell(rowid);
        }
        
        //检查当前模板是否设置样式
        function checkTmplCell(id){
        	var url = '${ctx}/cmdb/indicator/report/checkTmplCell.do';
        	$.ajax({
        	    type: "POST",
        	    url: url,
        	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
        	    data:{"id":id},
        		//dataType: 'json',
        		success: function(data) {
        			 if(data.success==0){
        				 onTableRow(id);
				     }else{
				      	layer.msg('此模板暂未设置样式，请设置后再查看！', {icon: 7, time: 1500});
				      	return ;
				     }
        		}
        	}); 
        }
        
        //跳转模板样式页面展示页面
        function onTableRow(id){
     		if(gridWidth<1100){
     			gridWidth = gridWidth-100 ;
	      	}else{
	      		gridWidth = 1100;
	      	}
     		layer_show("效果预览",'${ctx}/cmdb/indicator/report/showTable.do?id='+id,gridWidth,400);
        }
        
        function onRefresh(){
        	window.location.reload();
        }
        

   </script>
 </body>
</html>

