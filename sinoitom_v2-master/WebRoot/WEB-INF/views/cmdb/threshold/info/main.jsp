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
		<title>阀值信息</title>

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
	<button class="btn btn-white btn-info btn-bold"onclick="onEdit()">
		<i class="ace-icon fa fa-pencil-square-o bigger-120 blue"></i>
		修改
	</button>
	<button class="btn btn-white btn-warning btn-bold"onclick="onDelete()">
		<i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
		删除
	</button>
	<button class="btn btn-white btn-success btn-bold"onclick="onRefresh()" >
		<i class="ace-icon fa fa-refresh bigger-120 green"></i>
		刷新
	</button>
    
</div>



	<div class="col-xs-12" style="overflow: hidden;">
		
		<table id="list"></table>
		
		<div id="pager"></div>

	</div><!-- /.col -->
 <script type="text/javascript">
 
 var vendorId="${vendorId}";
 var gridHeight;		
	$(function(){ 
	 	gridHeight= $(document).height() - 114 - $('#btn').height();
		pageInit();	
		resize();  //自动调整页面Grid	  
	 });

function pageInit(){
		jQuery("#list").jqGrid( {
			 data:  ${jsonListData},
				//  datatype:从服务器端返回的数据类型，默认xml。可选类型：xml，local(客户端数据（数组）)，json，jsonnp，script，xmlstring，jsonstring，clientside
				 datatype : "local", 
     
	 colNames : [ 'id','threshold_ID', '监测对象', '指标维度', '指标组', '阀值分类', '阀值级数', '阀值名称', '缺省单位', '单位名称', '状态', '描述'],
	 colModel : [ 
	        	 {name : 'id',index : 'id',width : 30,hidden:true},
	        	 {name : 'threshold_ID',index : 'threshold_ID',width : 30,hidden:true},
	        	 {name : 'indObjName',index : 'indObjName',width : 30}, 
	        	 {name : 'indDimName',index : 'indDimName',width : 30},
	        	 {name : 'indGroupName',index : 'indGroupName',width: 60},
	        	 {name : 'threshTypeName',index : 'threshTypeName',width : 60},
	        	 {name : 'thresholdLevel',index : 'thresholdLevel',width : 60},
	        	 {name : 'thresholdName',index : 'thresholdName',width : 60},
	        	 {name : 'defaultUnit',index : 'defaultUnit',width : 60},
	        	 {name : 'unitName',index : 'unitName',width : 60},
	        	 {name : 'status',index : 'status',width : 60,
	        	 	formatter:function(cellvalue, options, row){
	        			 var html = '';
	        			 if(row.status==1){
	        				 html = '使用';
	        			 }else{
	        				 html = '停用';
	        			 }
	        			 return html;
	        		 }
	        	 },
	        	 {name : 'description',index : 'description',width : 60},
	        	 ], 
	        	 
	        	 rowNum : 20, rowList : [ 20, 50, 100 ], 
			 		pager : '#pager',
			 		height:gridHeight,
					altRows: true,
					toolbar:[true,'top'],
					multiselect: true,
					multiboxonly: true,
					viewrecords: true,
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
			 layer_show("添加",'${ctx}/cmdb/thresholdInfo/add.do',800,400)
			}  
			function onEdit()
			{
			
			var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
			var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
			var rowData = jQuery("#list").jqGrid('getRowData',selectedId);
			
		 if (rowid==""||rowid==null) { 
		 	layer.msg('请选择一条阀值信息',{icon: 7,time:1500});
		 	return; 
		 }
			editRow(rowData.threshold_ID,rowData.id);
			
		}
		function editRow(id,uuid)
		{
			layer_show("修改信息",'${ctx}/cmdb/thresholdInfo/edit.do?id='+id+'&uuid='+uuid,800,450)
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
		 	layer.msg('请选择一条阀值信息',{icon: 7,time:1500});
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
			var url = '${ctx}/cmdb/thresholdInfo/delete.do?id='+id;
			$.ajax({
				url: url,
				dataType: 'json',
				
				success: function(data) {
					if(data.result=="success"){
						 layer.msg('删除成功!',{icon: 1,time:1500});
					}else{
						layer.msg(data.message,{icon: 2,time:1500});
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







