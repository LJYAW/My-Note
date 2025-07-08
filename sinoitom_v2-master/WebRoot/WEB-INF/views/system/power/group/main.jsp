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
		<title>权限管理</title>
		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css" />

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css" />
		<link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet" />
		<!-- ace styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />
		
		<script src="${ctx }/static/assets/js/jquery.min.js"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>
	    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script> 
		
		<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
		<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
		<!-- page specific plugin scripts -->
		<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
		<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
		<script src="${ctx }/static/assets/js/resize.js"></script>

	</head>
	<script type="text/javascript">
			
var gridHeight;						
$(function(){ 
		
	gridHeight= $(document).height() - 120 - $('#btn').height();
	pageInit();	
	resize();  //自动调整页面Grid	  
	bootbox.setDefaults("locale", "zh_CN");	  
});
		
	
	function pageInit(){
			
		jQuery("#list").jqGrid( {
				
			url : '${ctx}/system/power/group/getData.do',
    		 treeGrid: true,  
                treeGridModel: 'adjacency', //treeGrid模式，跟json元数据有关 ,adjacency/nested  
                ExpandColumn : 'menuName',
                datatype: 'json', 
                rowNum : 50,
	        	rowList : [ 50, 100, 150 ], 
                pager: '#pager',  
                sortname: 'menuName',  
                viewrecords : true, 
                sortorder: "asc",  
                toolbar:[true,'top'],
			 colNames : [ '权限组名称', '权限组说明', '树编码', '操作' ], 
			 colModel : [ 
				 {name : 'grpName',index : 'grpName',width : 70},
				 {name : 'description',index : 'description',width : 80}, 
				 {name : 'treeCode',index : 'treeCode',width : 100},
				 {width : 80,align : "left",
				   
				     formatter:function(cellvalue, options, row){
				        var html = '<span style="cursor:pointer " onclick="editRow(\'' + row.id + '\');">修改</span>';
			                html += ' | <span style="cursor:pointer " onclick="deleteRow(\'' + row.id + '\');">删除</span>';
			                html += ' | <span style="cursor:pointer " onclick="manageItems(\'' + row.id + '\');">权限项</span>';
			           return html;
				     }
				 }
				 ],
				

				treeReader : {  
    				level_field: "level",  
    				parent_id_field: "parentId",
    				leaf_field: "isLeaf",  
   					expanded_field: "expanded"  
					},

				height:gridHeight,
				//caption : "权限管理" 
			
				}); 
				jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
				$("#t_list").css({"height":30});
			    $("#btn").appendTo("#t_list");

				}


			
		function delRow(id){
		 	//alert(id);
		}	
		
		
		function onadd(){
			
		var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
	    	if(rowid==null){
	    		 rowid = "";
	    	 }
		 layer_show("权限管理",'${ctx}/system/power/group/add.do?parentId='+rowid,800,400);
		}
		
		 function onEdit(){
    	
			var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
    	
    	
        if (rowid==""||rowid==null) { 
        	layer.msg('请选择一条权限信息!',{icon: 7,time:1500});
        	return; 
        }
    	editRow(rowid);
    	
    }
     function editRow(id)
    {
		layer_show("修改权限信息",'${ctx}/system/power/group/edit.do?id='+id,800,450)
    }
    function saveOK(action, data)
    {
    	layer.closeAll(); 
    	window.location.reload();
    }
    function onDelete()
    {
    	var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
        if (rowid==""||rowid==null) { 
        	layer.msg('请选择一条权限信息!',{icon: 7,time:1500}); 
        	return; 
        }
    	deleteRow(rowid);
    }
     function deleteRow(id)
    {
    	 layer.confirm('是否确认删除所选权限信息？',function(yes){
  			delRow(id);
  		});
    }
    
    function delRow(id)
    {
    	var url = '${ctx}/system/power/group/delete.do?id='+id;
    	$.ajax({
    		url: url,
    		dataType: 'json',
    		
    		success: function(data) {
    			if(data.result=="success"){
    				layer.msg('删除成功!',{icon: 1,time:1500});
    			}else{
    				layer.msg('删除失败!',{icon: 1,time:1500});
    			}
<%--    			doData(data);--%>
				window.location.reload();
    		}
    	});        			
    }
    
    function onRefresh(){
    	window.location.reload();
    }
    
    
     function onManageItems(){ 
    	 var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
            
    	 if (rowid==""||rowid==null) { 
    		 layer.msg('请选择一条权限信息!',{icon: 7,time:1500}); 
         	return; 
         }
              manageItems(rowid); 
             } 
            
      function manageItems(id){ 
           var url = '${ctx}/system/power/item/main.do?grpId='+id; 
    		 window.location.href = url; 
           } 
    
		
		</script>

	<body>

						  
						  <div id="btn" style="margin-top:2px;">
						    <button class="btn btn-white btn-default btn-bold" onclick="onadd()">
								<i class="ace-icon fa  fa-plus bigger-120 default"></i>
								添加
							</button>
							<button class="btn btn-white btn-info btn-bold"onclick="onEdit()">
								<i class="ace-icon fa fa-pencil-square-o bigger-120 blue"></i>
								修改
							</button>
<!-- 							<button class="btn btn-white btn-info btn-bold"onclick="onMove()"> -->
<!-- 								<i class="ace-icon fa fa-pencil-square-o bigger-120 blue"></i> -->
<!-- 								移动 -->
<!-- 							</button> -->
							<button class="btn btn-white btn-warning btn-bold"onclick="onDelete()">
								<i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
								删除
							</button>
<!-- 							<button class="btn btn-white btn-warning btn-bold"onclick="onSearch()"> -->
<!-- 								<i class="ace-icon fa fa-trash-o bigger-120 orange"></i> -->
<!-- 								查询 -->
<!-- 							</button> -->
 							<button class="btn btn-white btn-warning btn-bold"onclick="onManageItems()"> 
 								<i class="ace-icon fa fa-trash-o bigger-120 orange"></i> 
								权限项 
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
						  
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li class="active">${titleContent }</li>
    </ul>
</div>

<div class="col-xs-12" style="overflow: hidden;">
    <table id="list"></table>
    <div id="pager"></div>
</div>


	</body>
</html>



 