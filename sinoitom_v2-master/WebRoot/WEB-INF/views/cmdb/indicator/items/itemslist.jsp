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
		<title>指标项</title>

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
	<shiro:hasPermission name="IndItem_Add">
  	<button class="btn btn-white btn-default btn-bold" onclick="onadd()">
		<i class="ace-icon fa  fa-plus bigger-120 default"></i>
		添加
	</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="IndItem_Modify">
	<button class="btn btn-white btn-info btn-bold"onclick="onEdit()">
		<i class="ace-icon fa fa-pencil-square-o bigger-120 blue"></i>
		修改
	</button>
	</shiro:hasPermission>
	<shiro:hasPermission name="IndItem_Delete">
	<button class="btn btn-white btn-warning btn-bold"onclick="onDelete()">
		<i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
		删除
	</button>
	</shiro:hasPermission>
	<button class="btn btn-white btn-info btn-bold" onclick="onView()">
        <i class="ace-icon fa fa-tasks bigger-120"></i>
                详情
    </button>
	<shiro:hasPermission name="IndItem_Audit">
	<button class="btn btn-white btn-purple btn-bold" onclick="onAudit()">
		<i class="ace-icon fa fa-check-square-o bigger-120 "></i>
		审核
	</button>
	</shiro:hasPermission>
	
<!-- 	<button class="btn btn-white btn-default btn-bold" onclick="relationDevIndItems()"> -->
<!-- 		<i class="ace-icon fa  fa-plus bigger-120 default"></i> -->
<!-- 		关联设备分类 -->
<!-- 	</button> -->
<!-- 	<button class="btn btn-white btn-default btn-bold" onclick="relationEventType()"> -->
<!-- 		<i class="ace-icon fa  fa-plus bigger-120 default"></i> -->
<!-- 		关联事件类型 -->
<!-- 	</button> -->
	<button class="btn btn-white btn-success btn-bold"onclick="onImport()" >
		<i class="ace-icon fa fa-sign-in bigger-120 green"></i>
		导入
	</button>
<!-- 	<button class="btn btn-white btn-success btn-bold"onclick="onExport()" > -->
<!-- 		<i class="ace-icon fa fa-sign-out bigger-120 green"></i> -->
<!-- 		导出 -->
<!-- 	</button> -->
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
     
     
	 colNames : [ '监测对象', '指标组名称', '指标英文名称', '指标显示名称', '取值类型', '长度', '小数位数', '度量单位', '指标说明','审核状态','审核状态' ],
	 colModel : [ 
	        	 {name : 'indClassName',index : 'indClassName',width : 80},
	        	 {name : 'indGroupName',index : 'indGroupName',width : 120}, 
	        	 {name : 'indicatorItem',index : 'indicatorItem',width : 150},
	        	 {name : 'indItemName',index : 'indItemName',width:150},
	        	 {name : 'valueType',index : 'valueType',width : 60},
	        	 {name : 'length',index : 'length',width : 60},
	        	 {name : 'decimals',index : 'decimals',width : 60},
	        	 {name : 'measureUnit',index : 'measureUnit',width : 60},
	        	 {name : 'remark',index : 'descrremarkiption',width : 200},
	        	 {name : 'status',index : 'status',width : 70,hidden:true},
	        	 {name : 'status1',index : 'status1',width : 70,
	        		 formatter:function(cellvalue, options, row){
	        			 var html = '';
	        			 if(row.status==1){
	        				 html += '<span style="color:blue;">已审核</span>';
		                    }else{
		                        html += '<span style="color:red;">未审核</span>';
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
							
		function onadd(){
			
			 var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
			 if(rowid==null){
				 rowid = "";
			 }
			 
			 // var row= jQuery("#list").jqGrid('getRowData', rowid);//获取当前行所有数据 
			 layer_show("添加指标项",'${ctx}/cmdb/indicatorItems/add.do',800,500)
		}  
		
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
				
			 	 layer.msg('所选数据已经通过审核！',{icon: 7,time:1500});
			 	return; 
			 }
			editRow(rowid);
			
		}
		function editRow(id)
		{
			layer_show("修改指标项",'${ctx}/cmdb/indicatorItems/edit.do?id='+id,800,500);
		}
		
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
			layer_show("指标项详情",'${ctx}/cmdb/indicatorItems/view.do?id='+id,800,500);
		}
		
		function saveOK(action, data)
		 {
		 	layer.closeAll(); 
					window.location.reload();
		 }
		
<%--		function onDelete()--%>
<%--		{--%>
<%--			var ids="";--%>
<%--			var count1=0;--%>
<%--			var count2=0;--%>
<%--			 var selectedIds = jQuery("#list").jqGrid('getGridParam', 'selarrrow');--%>
<%--		 if (selectedIds==""||selectedIds==null) { --%>
<%--			 layer.msg('请选择一条数据！',{icon: 7,time:1500});--%>
<%--		 	return; --%>
<%--		 }--%>
<%--		--%>
<%--			 for(var i=0;i<selectedIds.length;i++){--%>
<%--				 var rowData = jQuery("#list").jqGrid('getRowData',selectedIds[i]);--%>
<%--				 if(rowData.status=="0"){--%>
<%--					 ids+=selectedIds[i]+",";--%>
<%--					 count1++;--%>
<%--				 }else{--%>
<%--					 count2++;--%>
<%--				 }--%>
<%--			 }--%>
<%--			 if(count2>0&&count1==0){--%>
<%--				 layer.msg('所选数据都已经通过审核！',{icon: 7,time:1500});--%>
<%--				 return;--%>
<%--			 }--%>
<%--			 deleteRow(ids);--%>
<%--		}--%>


		function onDelete()
		{
				var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
				var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
				var rowData = jQuery("#list").jqGrid('getRowData',selectedId);
			 if (rowid==""||rowid==null) { 
			 	 layer.msg('请选择一条数据！',{icon: 7,time:1500});
			 	return; 
			 }
			 if (selectedId.length>1) { 
			 	 layer.msg('只能选择一条数据进行删除！',{icon: 7,time:1500});
			 	return; 
			 }
			 if (rowData.status==1) { 
				
			 	 layer.msg('所选数据已经通过审核！',{icon: 7,time:1500});
			 	return; 
			 }
			 deleteRow(rowid);
		}
		
		function deleteRow(id)
		{
			layer.confirm('确认要删除吗？', function (yes) {
				delRow(id)
				
	        });
		}
		
		function delRow(id)
        {   
        	var url = '${ctx}/cmdb/indicatorItems/delete.do';
        	$.ajax({
        	    type: "POST",
        	    url: url,
        	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
        	    data:{"id":id},
        		//dataType: 'json',
        		success: function(data) {
        			
        			if( data.success==0 ){
                        layer.msg('删除成功!',{icon:1, time: 1000},function(){
                        	onRefresh(); // 页面刷新
        	            });
                    }else{
                    	layer.msg('删除失败!', {icon: 7, time: 1500});
                    }
        			
        		}
        	});        			
        }
		
<%--		function delRow(id)--%>
<%--		{--%>
<%--			var url = '${ctx}/cmdb/indicatorItems/delete.do?';--%>
<%--			$.ajax({--%>
<%--				url: url,--%>
<%--				type:"POST",--%>
<%--				dataType: 'json',--%>
<%--				data:{"indItemIds":id},--%>
<%--				--%>
<%--				success: function(data) {--%>
<%--					if(data.success==0){--%>
<%--						layer.msg('删除成功!',{icon: 1,time:1500});--%>
<%--					}else{--%>
<%--						layer.msg(data.message,{icon: 2,time:1500});--%>
<%--					}--%>
<%--					window.location.reload();--%>
<%--				}--%>
<%--			});        			--%>
<%--		}--%>
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
	     	var url = '${ctx}/cmdb/indicatorItems/saveAudit.do?itemsIds='+ids;
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
		function onImport() {	
            
            layer_show("导入",'${ctx}/cmdb/indicatorItems/importGo.do',720, 220)
            
        }
         function onExport() {
            
        	   
        	    var url = timeURL('${ctx}/cmdb/prodClass/getExportExcelSize.do');
        	   $.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
           			
           			window.parent.$.ligerDialog.confirm('产品分类表共有'+data+'条信息<br>是否确认导出?', function (yes){
      		           if(yes) location.href='${ctx}/cmdb/prodClass/exportExcel.do';
      	             });
        		}
        	});    
        }
		
		function onRefresh(){
			window.location.reload();
		}
		 //关联设备类型
        function relationDevIndItems(){
            var rows = gridMain.getSelectedRows();
            if(rows!=null&&rows!=""&&rows.length>0){
               var ids = getAllSelectId();
            if(ids!=null&&ids!=""){
              $("#indItemsIds").val(ids);
			window.open('','newWin','height=330, width=900, top=160, left=220, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
		    $("#relationIndItems").submit();
            }
            }else{
               window.parent.$.ligerDialog.warn("请选择要关联的指标项!");
	          return;
            }
           
            
		     
        }  
        
        //关联事件类型
        function relationEventType(){
           var rows = gridMain.getSelectedRows();
           if(rows!=null&&rows!=""&&rows.length>0){
             var ids = getAllSelectId();
            if(ids!=null&&ids!=""){
              $("#itemsIds").val(ids);
			window.open('','newWin','height=330, width=900, top=160, left=220, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
		    $("#relationEventType").submit();
            }
           }else{
            window.parent.$.ligerDialog.warn("请选择要关联的指标项!");
	          return;
           }
		     
        }  
        

</script>
</body>
</html>






