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
		<title>字典项管理</title>

		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css" />

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css" />
		<link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet" />
		<!-- text fonts -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/fonts.googleapis.com.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
		<![endif]-->
		<link rel="stylesheet" href="${ctx }/css/assets/css/ace-skins.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />

		<!-- ace settings handler -->
		<script src="${ctx }/static/assets/js/ace-extra.min.js"></script>
		
		<script src="${ctx }/static/assets/js/jquery.min.js"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>
	    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script> 
	    
		<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
		<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
		<!-- page specific plugin scripts -->
		<script src="${ctx }/static/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
		<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
		<!-- ace scripts -->
		<script src="${ctx }/static/assets/js/ace-elements.min.js"></script>
		<script src="${ctx }/static/assets/js/ace.min.js"></script>
		 
		<script type="text/javascript">
			
			
		$(function(){ 
		  pageInit();
		 });
		
	
function pageInit(){
jQuery("#list").jqGrid( {
//url : ctx+'/JSONData',
 data:  ${jsonListData},
//  datatype:从服务器端返回的数据类型，默认xml。可选类型：xml，local(客户端数据（数组）)，json，jsonnp，script，xmlstring，jsonstring，clientside
 datatype : "local", 
 colNames : [ '字典项名称', '字典项编码', '描述','操作' ], 
 colModel : [ 
	 {name : 'resName',index : 'resName',width : 120,align : "left"},
	 {name : 'resCode',index : 'resCode',width : 140,align : "left"}, 
	 {name : 'description',index : 'description',width: 200,align : "left"},
	 {width : 300,align : "left",
	   
	     formatter:function(cellvalue, options, row){
	        var html = '<span  onclick="editRow(\'' + row.id + '\');">修改</span>';
                html += ' | <span  onclick="delRow(\'' + row.id + '\');">删除</span>';
           return html;
	     }
	 }
	 
	 ],
	
 rowNum : 10, rowList : [ 10, 20, 30 ], 
 pager : '#pager',
 height:'500',
 width:'1000',
 toolbar:[true,'top'],
//  排序列的名称，此参数会被传到后台
 sortname : 'id',
//mtype : "post",
// 是否要显示总记录数
 viewrecords : true, 
//  排序顺序，升序或者降序（asc or desc）
 sortorder : "desc", 
// 定义是否可以多选
 multiselect : true,
// 表格名称
caption : "数据字典" ,

}); 
jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
$("#t_list").css({"height":30});
	$("#btn").appendTo("#t_list");
}

        
        function onAdd(){
        	var url = '${ctx}/system/res/item/add.do?grpId=${group.resGrpId}';
			layer_show("添加字典项",url,720,320);
        }

        function onEdit()
        {
    		var s = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
    		
			if(s.length>1 || s.length==0){
				layer.msg('请选择一行数据！',{icon: 7,time:1500});
            	return; 
			}
        	editRow(s);
        }
        
        function editRow(id)
        {
        	var url = '${ctx}/system/res/item/edit.do?id='+id;
        	layer_show("修改字典",url,720,320);
        }

        function saveOK(action, data)
        {
        	
//         	if( action=='add' ){
//         		dataMain.Rows.push(data);
//         		window.location.reload();
				
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

        function onMoveUp()
        {
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
        
        function onDelete()
        {
            var s = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
    		
			if(s.length==0){
				layer.msg('请选择一行数据！',{icon: 7,time:1500}); 
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
        	var url = '${ctx}/system/res/item/delete.do?id='+id;
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

        function onSearch()
        {
        	if( dlgSearch ){
        		dlgSearch.show();
        	}
        	else{
//	       		$("#formSearch").ligerForm({inputWidth:200});
	       		$("#formSearch input[ltype='text']").ligerTextBox({inputWidth:200}); $("#formSearch input").filter(".ip2").ligerTextBox({ width: 553 });
            	dlgSearch = $.ligerDialog.open({ title:'查询', width:720, height:300, target: $("#searchDlg") });
        	}
        }
        
        function onManageItems()
        {
        	var s = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        	if(s.length==0 || s.length>1){
        		layer.msg('请选择一行数据！',{icon: 7,time:1500}); 
        		return;
        	}
            manageItems(s);
        }
        
        function manageItems(id)
        {
        	var url = '${ctx}/system/res/item/main.do?grpId='+id;
        	layer_show("字典项",url,1000,420);
        }
        
        function onRefresh(){
        	window.location.reload();
        }
        
        
                function onBack()
        {
//        	window.history.back();
        	var url = '${ctx}/system/res/group/main.do';
        	window.location.href = url;
        }
	
		
	</script>
	</head>

	<body class="no-skin">

     	
		<div class="main-container ace-save-state" id="main-container">

			<div class="main-content">
				<div class="main-content-inner">
					<div class="page-content">
						<div class="row">
						  
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
<!-- 								<button class="btn btn-white btn-primary btn-bold" onclick="onSearch()"> -->
<!-- 								<i class="ace-icon fa fa-search bigger-120 primary"></i> -->
<!-- 								查询 -->
<!-- 							</button> -->
<!-- 							<button class="btn btn-white btn-primary btn-bold" onclick="onManageItems()"> -->
<!-- 								<i class="ace-icon fa fa-search bigger-120 primary"></i> -->
<!-- 								字典项 -->
<!-- 							</button> -->
							<button class="btn btn-white btn-success btn-bold" onclick="onRefresh()">
								<i class="ace-icon fa fa-refresh bigger-120 green"></i>
								刷新
							</button>
						     
						  </div>

							<div class="col-xs-12">
								
								<table id="list"></table>
								
								<div id="pager"></div>

							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->

		</div><!-- /.main-container -->

		
	</body>
</html>
