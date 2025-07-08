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
		<title>字典管理</title>

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

			<!-- basic scripts -->
		<script src="${ctx }/static/assets/js/jquery.min.js"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>
	    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script> 
		
		<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
		<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
		<!-- page specific plugin scripts -->
<!-- 		<script src="${ctx }/static/assets/js/bootstrap-datepicker.min.js"></script> -->
		<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
		<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
		<script src="${ctx }/static/assets/js/resize.js"></script>
		 
		<script type="text/javascript">
			
		var gridHeight;		
		$(function(){ 
		 	gridHeight= $(document).height() - 120 - $('#btn').height();
			pageInit();	
			resize();  //自动调整页面Grid	  
		 });
		
	
function pageInit(){
	jQuery("#list").jqGrid( {
	 data:  ${jsonListData},
	 datatype : "local", 
	 colNames : [ '字典名称', '字典编码', '字典类型', '字典项关系','字典项类型','描述','操作' ], 
	 colModel : [ 
		 {name : 'grpName',index : 'grpName',width : 100},
		 {name : 'grpCode',index : 'grpCode',width : 100}, 
		 {name : 'state',index : 'state',width : 80},
		 {name : 'grpType',index : 'grpType',width: 80},
		 {name : 'itemType',index : 'itemType',width: 80},
		 {name : 'description',index : 'description',width: 230},
		 {width : 120,
		     formatter:function(cellvalue, options, row){
		        var html = '<span style="cursor:pointer " onclick="editRow(\'' + row.id + '\');">修改</span>';
	                html += ' | <span style="cursor:pointer " onclick="delRow(\'' + row.id + '\');">删除</span>';
	                html += ' | <span style="cursor:pointer " onclick="manageItems(\'' + row.id + '\');">字典项</span>';
	           return html;
		     }
		 }
		 
		 ],
	
		viewrecords : true,
		rowNum:50,
	    rowList:[50,100,150],
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
// 		caption : "数据字典" ,

	}); 
		jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
		$("#t_list").css({"height":30});
		$("#btn").appendTo("#t_list");
}

//      添加
        function onAdd(){
			layer_show("添加字典",'${ctx}/system/res/group/add.do',1000,420);
        }

// 		修改
        function onEdit(){
    		var s = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
    		
			if(s.length>1 || s.length==0){
				layer.msg('请选择一行数据！',{icon: 7,time:1500});
            	return; 
			}
        	editRow(s);
        }
        
        
        function editRow(id){
        	layer_show("修改字典",'${ctx}/system/res/group/edit.do?id='+id,1000,420);
        }

        function saveOK(action, data){
				layer.closeAll(); 
				window.location.reload();
				
//         	}
//         	else{
//                 for (var i = 0; i < dataMain.Rows.length; i++)
//                 {
//                     if (dataMain.Rows[i].id == data.id){
//                     	dataMain.Rows[i] = data;
//                     	break;
//                     }
//                 }
//          		window.location.reload();
				
//         	}
        }
		
        function onMoveUp(){
            var s = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
    		
			if(s.length==0 || s.length>1){
				layer.msg('请选择一行数据！',{icon: 7,time:1500});
            	return; 
			}
        	moveUpRow(s);
        }
        
        function moveUpRow(id)
        {
        	if( dataMain.Rows[0].id == id){
        		layer.msg('该行数据已经在最上方！',{icon: 7,time:1500});
        		return;
        	}
        	var url = timeURL('${ctx}/system/res/group/moveUp.do?id='+id);
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
//         			doData(data);
        		}
        	});              	
        }

        function onMoveDown()
        {
            if (!selectRowData) { 
            	$.ligerDialog.warn('请选择一行数据！'); 
            	return; 
            }
        	moveDownRow(selectRowData.id);
        }
        
        function moveDownRow(id)
        {
        	if( dataMain.Rows[dataMain.Rows.length-1].id == id){
        		$.ligerDialog.warn('该行数据已经在最下方！'); 
        		return;
        	}
        	var url = timeURL('${ctx}/system/res/group/moveDown.do?id='+id);
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
        			doData(data);
        		}
        	});              	
        }
        
//         删除
        function onDelete()
        {
            var s = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
    		
			if(s.length==0){
				layer.msg('请至少选择一行数据！',{icon: 7,time:1500});
            	return; 
			}
        	deleteRow(s);
        }
        
        function deleteRow(id)
        {
        	layer.confirm('是否确认删除该客户？',function(yes){
				delRow(id);
			});
        
        }
        
        function delRow(id)
        {
        	var url = '${ctx}/system/res/group/delete.do?id='+id;
        	$.ajax({
        		url: url,
        		dataType: 'json',
    		success: function(data) {
    			if(data.result=="success"){
    				layer.msg("删除成功!", {
					   icon: 1,
					   time: 1000 //2秒关闭（如果不配置，默认是3秒）
					 }, function(){
						 layer.close(); 
					    window.location.reload();
					    return;
					 });
    			}else{
    				layer.msg("删除失败!", {
					   icon: 1,
					   time: 1000 //2秒关闭（如果不配置，默认是3秒）
					 }, function(){
						 layer.close(); 
					    window.location.reload();
					    return;
					 });
    			}
    		}
        	});        			
        }

// 		查询
        function onSearch(){
        	var url = '${ctx}/system/res/group/toSearch.do?';
     		layer_show("数据字典查询",url,800,320);  
        }
        
//      重新加载数据
        function reloadGrid(data){
         layer.closeAll(); 
         jQuery('#list').jqGrid('clearGridData');
         jQuery('#list').jqGrid(
                'setGridParam',{  
                datatype:'local',  
    			data:data 
			}).trigger('reloadGrid');

        }
        
        function onManageItems(){
        	var s = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        	if(s.length==0 || s.length>1){
        		layer.msg("请选择一行数据！",{icon: 7,time:1500});
        		return;
        	}
            manageItems(s);
        }
        
        function manageItems(id){
        	var url = '${ctx}/system/res/item/main.do?grpId='+id;
        	layer_show("字典项",url,1000,420);
        }
        
        function onRefresh(){
        	window.location.reload();
        }
	
		
	</script>
	</head>

	<body class="no-skin">

						  
						  <div id="btn" style="margin-top:2px;">
						    <button class="btn btn-white btn-default btn-bold" onclick="onAdd()">
								<i class="ace-icon fa  fa-plus bigger-120 default"></i>
								添加
							</button>
							<button class="btn btn-white btn-info btn-bold" onclick="onEdit()">
								<i class="ace-icon fa fa-pencil-square-o bigger-120 blue"></i>
								修改
							</button>
							
<!-- 							<button class="btn btn-white btn-purple btn-bold" onclick="onMoveUp()"> -->
<!-- 								<i class="ace-icon fa fa-level-up  bigger-120 purple"></i> -->
<!-- 								上移 -->
<!-- 							</button> -->
<!-- 							<button class="btn btn-white btn-pink btn-bold" onclick="onMoveDown()"> -->
<!-- 								<i class="ace-icon fa fa-level-down  bigger-120 pink"></i> -->
<!-- 								下移 -->
<!-- 							</button> -->
							<button class="btn btn-white btn-warning btn-bold" onclick="onDelete()">
								<i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
								删除
							</button>
							<button class="btn btn-white btn-primary btn-bold" onclick="onSearch()">
								<i class="ace-icon fa fa-search bigger-120 primary"></i>
								查询
							</button>
							<button class="btn btn-white btn-primary btn-bold" onclick="onManageItems()">
								<i class="ace-icon fa fa-search bigger-120 primary"></i>
								字典项
							</button>
							<button class="btn btn-white btn-success btn-bold" onclick="onRefresh()">
								<i class="ace-icon fa fa-refresh bigger-120 green"></i>
								刷新
							</button>
						     
						  </div>
						  
						<div class="breadcrumbs ace-save-state" id="breadcrumbs">
	                        <ul class="breadcrumb">
	                            <li class="active">${titleContent }</li>
	                        </ul>
         				</div>	

							<div class="col-xs-12">
								
								<table id="list"></table>
								
								<div id="pager"></div>


	</body>
</html>
