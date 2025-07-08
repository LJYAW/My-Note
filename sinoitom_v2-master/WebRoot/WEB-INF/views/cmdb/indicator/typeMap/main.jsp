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
		<title>指标事件类型</title>

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
  <button class="btn btn-white btn-default btn-bold" onclick="onadd()">
		<i class="ace-icon fa  fa-plus bigger-120 default"></i>
		添加
	</button>
	<button class="btn btn-white btn-warning btn-bold"onclick="onDelete()">
		<i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
		删除
	</button>
	<button class="btn btn-white btn-success btn-bold"onclick="onRefresh()" >
		<i class="ace-icon fa fa-refresh bigger-120 green"></i>
		刷新
	</button>
<!-- 							<button class="btn btn-white btn-danger btn-bold"> -->
<!-- 								<i class="ace-icon fa fa-file-text-o bigger-120 danger"></i> -->
<!-- 								详情 -->
<!-- 							</button> -->
   
</div>


	<div class="col-xs-12" style="overflow: hidden;">
		
		<table id="list"></table>
		
		<div id="pager"></div>

	</div><!-- /.col -->
 <script type="text/javascript">
 
 var vendorId="${vendorId}";
 var gridHeight;		
	$(function(){ 
	 	gridHeight= $(document).height() - 112 - $('#btn').height();
		pageInit();	
		resize();  //自动调整页面Grid	  
	 });

function pageInit(){
		jQuery("#list").jqGrid( {
			 data:  ${jsonListData},
				//  datatype:从服务器端返回的数据类型，默认xml。可选类型：xml，local(客户端数据（数组）)，json，jsonnp，script，xmlstring，jsonstring，clientside
				 datatype : "local", 
     
     
	 colNames : [ '监测对象', '指标组', '指标英文名称', '指标显示名称', '事件分类', '类型编码', '事件类型'],
	 colModel : [ 
	        	 {name : 'indClassName',index : 'indClassName',width : 60},
	        	 {name : 'indGroupName',index : 'indGroupName',width : 100}, 
	        	 {name : 'indicatorItem',index : 'indicatorItem',width : 130},
	        	 {name : 'indItemName',index : 'indItemName',width: 160},
	        	 {name : 'eventClassName',index : 'eventClassName',width : 90},
	        	 {name : 'eventTypeCode',index : 'eventTypeCode',width : 90},
	        	 {name : 'eventTypeName',index : 'eventTypeName',width : 240},
	        	 ], 
	        	 
	        	 viewrecords: true,
	        	 rowNum : 20, rowList : [ 20, 50, 100 ], 
			 		pager : '#pager',
			 		height:gridHeight,
					altRows: true,
					toolbar:[true,'top'],
					multiselect: true,
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
							
		function onadd(){
			
			 var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
			 if(rowid==null){
				 rowid = "";
			 }
			 
			 // var row= jQuery("#list").jqGrid('getRowData', rowid);//获取当前行所有数据 
			 layer_show("添加指标事件类型",'${ctx}/cmdb/eventType/getAdd.do',800,400)
			}  
			function onEdit()
			{
			
			var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
			
		 if (rowid==""||rowid==null) { 
		 	alert("请选择一条数据！");
		 	return; 
		 }
			editRow(rowid);
			
		}
		
		function saveOK(action, data)
		 {
		 	layer.closeAll(); 
					window.location.reload();
		 }
		
		function onDelete()
		{
			var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');
		 if (rowid==""||rowid==null) { 
		 	alert("请选择一行数据！"); 
		 	return; 
		 }
			deleteRow(rowid);
		}
		function deleteRow(id)
		{
			if(window.confirm('是否确认删除所选数据？'))
			{
				delRow(id);
			}else{
				return;
			}
		}
		
		function delRow(id)
		{
			var url = '${ctx}/cmdb/eventTypeMap/delete.do?typeMapIds='+id;
			$.ajax({
				url: url,
				dataType: 'json',
				
				success: function(data) {
					if(data.success==0){
						alert("删除成功!");
					}else{
						alert(data.message);
					}
		<%--    			doData(data);--%>
					window.location.reload();
				}
			});        			
		}
		
		function onRefresh(){
			window.location.reload();
		}
		

</script>
</body>
</html>









