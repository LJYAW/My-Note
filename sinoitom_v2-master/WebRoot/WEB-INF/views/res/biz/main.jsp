<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ack" value="${pageContext.request.localAddr}"/>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>业务系统</title>

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
      <button class="btn btn-white btn-info btn-bold" onclick="onEdit()">
        <i class="ace-icon fa fa-pencil-square-o bigger-120 blue"></i>
        修改
    </button>
    <button class="btn btn-white btn-warning btn-bold" onclick="onDelete()">
        <i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
        删除
    </button>
    <button class="btn btn-white btn-info btn-bold" onclick="onDelete()">
        <i class="ace-icon fa fa-check-square-o bigger-120 "></i>
       审核
    </button>
    <button class="btn btn-white btn-warning btn-bold" onclick="onView()">
        <i class="ace-icon fa fa-file-text-o bigger-120 orange"></i>
        详情
    </button>
    <button class="btn btn-white btn-info btn-bold" onclick="checkBiz()">
        <i class="ace-icon fa fa-check-square-o bigger-120 "></i>
       业务验证
    </button>
    <button class="btn btn-white btn-default btn-bold" onclick="setService()">
        <i class="ace-icon fa fa-plus bigger-120 default"></i>
        关联服务
    </button>
    <button class="btn btn-white btn-success btn-bold" onclick="onRefresh()">
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
	 	gridHeight= $(document).height() - 112 - $('#btn').height();
		pageInit();	
		resize();  //自动调整页面Grid	  
	 });

function pageInit(){
		jQuery("#list").jqGrid( {
			 data:  ${jsonListData},
				//  datatype:从服务器端返回的数据类型，默认xml。可选类型：xml，local(客户端数据（数组）)，json，jsonnp，script，xmlstring，jsonstring，clientside
				 datatype : "local", 
     
            
     
	 colNames : ['id', '组织机构', '资源类型', '业务类型', '系统英文名称', '系统中文名称', '访问方式','访问协议','服务器类型','服务器类型','服务器IP地址','服务端口','访问地址(URL)','访问地址状态','status','状态'],
	 colModel : [ 
	 			 {name : 'id',index : 'id',width : 70,hidden:true},
	        	 {name : 'orgName',index : 'orgName',width : 30},
	        	 {name : 'resTypeName',index : 'resTypeName',width : 30}, 
	        	 {name : 'bizTypeName',index : 'bizTypeName',width : 30},
	        	 {name : 'sysEnName',index : 'sysEnName',width: 30},
	        	 {name : 'sysName',index : 'sysName',width : 40},
	        	 {name : 'accessMode',index : 'accessMode',width : 30,align : "center"},
	        	 {name : 'accessProt',index : 'accessProt',width : 30,align : "center"},
	        	 {name : 'svrType',index : 'svrType',width : 70,hidden:true},
	        	 {name : 'svrType1',index : 'svrType1',width : 30,align : "center",
	        	 	formatter:function(cellvalue, options, row){
	        			 var html = '';
	        			 if(row.svrType==0){
	        				 html += '<span>虚拟机</span>';
		                    }else if(row.svrType==1){
		                        html += '<span>物理机</span>';
		                    }else if(row.svrType==3){
		                        html += '<span >集群</span>';
		                    }
	        			 return html;
	        		 }	 
	        	 },
	        	 {name : 'svrIpAddr',index : 'svrIpAddr',width : 60},
	        	 {name : 'svcPort',index : 'svcPort',width : 20,align : "center"},
	        	 {name : 'bizURL',index : 'bizURL',width : 60},
	        	 {name : 'bizURLStatus',index : 'bizURLStatus',width : 30,
		        	 	formatter:function(cellvalue, options, row){
		        			 var html = '';
		        			 if(row.bizURLStatus==0){
		        				 html += '<span style="color:green;">OK</span>';
			                    }else if(row.bizURLStatus==1){
			                        html += '<span style="color:red;">NO</span>';
			                    }
		        			 return html;
		        		 }	  
		        	 },
	        	 {name : 'status',index : 'status',width : 70,hidden:true},
	        	 {name : 'status1',index : 'status1',width : 30,
	        	 	formatter:function(cellvalue, options, row){
	        			 var html = '';
	        			 if(row.status==0){
	        				 html += '<span style="color:blue;">起草</span>';
		                    }else if(row.status==1){
		                        html += '<span style="color:green;">启用</span>';
		                    }else if(row.status==2){
		                        html += '<span style="color:red;">停用</span>';
		                    }
	        			 return html;
	        		 }	  
	        	 },
	        	 
	        	 ], 
	        	 	viewrecords: true,
	        	 	rowNum : 50, rowList : [ 50, 100, 200 ], 
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
		//添加					
		function onadd(){
			
			 var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
			 if(rowid==null){
				 rowid = "";
			 }
			 
			 // var row= jQuery("#list").jqGrid('getRowData', rowid);//获取当前行所有数据 
			 layer_show("添加业务应用系统",'${ctx}/res/biz/add.do',800,500)
			}  
			//修改
			function onEdit()
			{
			
			var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
				var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
				var rowData = jQuery("#list").jqGrid('getRowData',selectedId);
			 if (rowid==""||rowid==null) { 
			 	 layer.msg('请选择一条数据！',{icon: 7,time:1500});
			 	return; 
			 }
			 if (selectedId.length>1) { 
			 	 layer.msg('只能选择一条数据进行修改！',{icon: 7,time:1500});
			 	return; 
			 }
			 if (rowData.status==1) { 
			 	 layer.msg('所选数据都已经通过审核！',{icon: 7,time:1500});
			 	return; 
			 }
			editRow(rowid);
			
		}
		function editRow(id)
		{
			layer_show("修改业务应用系统",'${ctx}/res/biz/edit.do?id='+id,800,500)
		}
		
		//关联服务
			function setService()
			{
			
			var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
				var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
				var rowData = jQuery("#list").jqGrid('getRowData',selectedId);
			 if (rowid==""||rowid==null) { 
			 	 layer.msg('请选择一条数据！',{icon: 7,time:1500});
			 	return; 
			 }
			 if (selectedId.length>1) { 
			 	 layer.msg('只能选择一条数据进行修改！',{icon: 7,time:1500});
			 	return; 
			 }
// 			 if (rowData.status==1) { 
// 			 	 layer.msg('所选数据都已经通过审核！',{icon: 7,time:1500});
// 			 	return; 
// 			 }
			setSerRow(rowid);
			
		}
		function setSerRow(id)
		{
			layer_show("关联服务",'${ctx}/res/biz/setService.do?id='+id,1300,700)
		}
		
		function saveOK(action)
		 {
		 	layer.closeAll(); 
			window.location.reload();
		 }
		
		function onDelete()
		{
			var ids="";
			var count1=0;
			var count2=0;
			 var selectedIds = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
		 if (selectedIds==""||selectedIds==null) { 
			 layer.msg('请选择一条数据！',{icon: 7,time:1500});
		 	return; 
		 }
		
			 for(var i=0;i<selectedIds.length;i++){
				 var rowData = jQuery("#list").jqGrid('getRowData',selectedIds[i]);
				 if(rowData.status=="0"){
					 ids+=selectedIds[i]+",";
					 count1++;
				 }else{
					 count2++;
				 }
			 }
			 if(count2>0&&count1==0){
				 layer.msg('所选数据都已经通过审核！',{icon: 7,time:1500});
				 return;
			 }
			 deleteRow(ids);
		}
		function deleteRow(ids)
		{
			layer.confirm('确认要删除吗？', function (yes) {
			delRow(ids)
			
                      });
		}
		
		function delRow(ids)
		{
			var url = '${ctx}/res/biz/delete.do';
			$.ajax({
				url: url,
				type:"POST",
				dataType: 'json',
				data:{"ids":ids},
				
				success: function(data) {
					if(data.success=="0"){
						layer.msg('删除成功!',{icon: 1,time:1500});
					}else{
						layer.msg(data.message,{icon: 2,time:1500});
					}
					window.location.reload();
				}
			});        			
		}
		
		  //审核
	     function onAudit(){
	    	 var ids="";
			var count1=0;
			var count2=0;
			 var selectedIds = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
			 if (selectedIds==""||selectedIds==null) { 
				 layer.msg('请选择一条数据！',{icon: 7,time:1500});
			 	return; 
			 }
		
			 for(var i=0;i<selectedIds.length;i++){
				 var rowData = jQuery("#list").jqGrid('getRowData',selectedIds[i]);
				 if(rowData.status=="0"){
					 ids+=selectedIds[i]+",";
					 count1++;
				 }else{
					 count2++;
				 }
			 }
			 if(count2>0&&count1==0){
				 layer.msg('所选数据都已经通过审核！',{icon: 7,time:1500});
				 return;
			 }
	    	 audit(ids);
	    	 
	     }
	     
	     function audit(ids)
	     {
	    	 layer.confirm('确认通过审核吗？', function (yes) {
			auditRow(ids);
			
                      });
	    	 
	     }
	     
	     function auditRow(ids)
	     {
	     	var url = '${ctx}/cmdb/prodClass/saveAudit.do?prodClassIds='+ids;
	     	$.ajax({
	     		url: url,
	     		dataType: 'json',
	     		success: function(data) {
	     			if( data.success == '0' ){
	     				layer.msg('审核成功!',{icon: 1,time:1500});
	     				onRefresh();
	     			}
	     		}
	     	});
	     }
	
		 //详情
	    function onView(){
			var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
				var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
				var rowData = jQuery("#list").jqGrid('getRowData',selectedId);
				
			 if (rowid==""||rowid==null) { 
			 	 layer.msg('请选择一条数据！',{icon: 7,time:1500});
			 	return; 
			 }
			 if (selectedId.length>1) { 
			 	 layer.msg('只能选择一条数据！',{icon: 7,time:1500});
			 	return; 
			 }
			viewRow(rowid);
		}
		
		function viewRow(id){
			layer_show("业务应用系统详情",'${ctx}/res/biz/view.do?id='+id,800,500);
		}
		
		//业务验证
		function checkBiz(){
			var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
			var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
			var rowData = jQuery("#list").jqGrid('getRowData',selectedId);
			
		 if (rowid==""||rowid==null) { 
		 	 layer.msg('请选择一条数据！',{icon: 7,time:1500});
		 	return; 
		 }
		 if (selectedId.length>1) { 
		 	 layer.msg('只能选择一条数据！',{icon: 7,time:1500});
		 	return; 
		 }
		 	checkResBiz(selectedId,rowData.svrIpAddr);
		}
		
		function checkResBiz(id,ipAddr){
			var url = '${ctx}/res/biz/checkResBiz.do?id='+id+'&ipAddr='+ipAddr;
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
        			if( data.result == 'success' ){
        				layer.msg("the URL is OK!",{icon:1, time: 1500},function(){
        					onRefresh(); // 页面刷新
        	            });
        			}else{
        				layer.msg(data.message, {icon: 7, time: 2000},function(){
        					onRefresh(); // 页面刷新
        	            });
        			}
        		}
        	});
		}
		
		function onRefresh(){
			window.location.reload();
		}
</script>
</body>
</html>











