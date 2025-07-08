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
		<title>权限项管理</title>

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
		<script src="${ctx }/static/assets/js/resize.js"></script>

	</head>


		<script src="${ctx }/static/assets/js/jquery.min.js"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>
	    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script> 
		<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
		<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
		<!-- page specific plugin scripts -->
		<script src="${ctx }/static/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
		<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
		<script src="${ctx }/static/assets/js/jqgridMain.js"></script>
		<!-- ace scripts -->
		<script src="${ctx }/static/assets/js/ace-elements.min.js"></script>
		<script src="${ctx }/static/assets/js/ace.min.js"></script>
		
		
		<script type="text/javascript">
			
		var gridHeight;		
$(function(){ 
		
	gridHeight= $(document).height()-155;
	pageInit();	
	resize();  //自动调整页面Grid	
		  
});
		
	function pageInit(){
			jQuery("#list").jqGrid( {
			//url : ctx+'/JSONData',
			
			 data: ${jsonListData},
			 datatype : "local", 
			 colNames : [ '权限名称', '权限编码', '业务权限', '开发权限', '角色可见','状态','描述','操作' ], 
			 colModel : [ 
				 {name : 'powName',index : 'powName',width : 80},
				 {name : 'powCode',index : 'powCode',width : 90}, 
				 {name : 'isBsPow',index : 'isBsPow',width : 100},
				 {name : 'isDvPow',index : 'isDvPow',width : 100},
				 {name : 'isRoleSee',index : 'isRoleSee',width : 100},
				 {name : 'state',index : 'state',width : 100},
				 {name : 'description',index : 'description',width : 100},
				 {width : 80,align : "left",
				   
				     formatter:function(cellvalue, options, row){
				        var html = '<span style="cursor:pointer " onclick="editRow(\'' + row.id + '\');">修改</span>';
			                html += ' | <span style="cursor:pointer " onclick="deleteRow(\'' + row.id + '\');">删除</span>';
			           return html;
				     }
				 }
				 ],
				
			 rowNum : 10, rowList : [ 10, 20, 30 ], 
			 pager : '#pager',			  
			 height:gridHeight,
			 toolbar:[true,'top'],
			 sortname : 'id',
			 viewrecords : true, 
			 sortorder : "desc", 
			 multiselect : true,
			 caption : "权限项" ,
			loadComplete : function() {
						var table = this;
						setTimeout(function(){
							styleCheckbox(table);
							
							updateActionIcons(table);
							updatePagerIcons(table);
							enableTooltips(table);
						}, 0);
					},
			
			}); 
			jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
			
			
						$("#t_list").css({"height":30});
			            $("#btn").appendTo("#t_list");

}


			
		function delRow(id){
		 	alert(id);
		}	
		
		
		function onadd(){
		 layer_show("权限项",'${ctx}/system/power/item/add.do?grpId=${group.powGrpId}',800,400)
		}
		
		 function onEdit()
    {
    	
    	var selectRowData;
    	selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
    	
    	if(selectRowData.length>1){
    		layer.msg('只能选择一条权限信息!',{icon: 7,time:1500});
    		return;
    	}
    	
        if (selectRowData==""||selectRowData==null) { 
        	layer.msg('请选择一条权限信息!',{icon: 7,time:1500});
        	return; 
        }
    	editRow(selectRowData);
    	
    }
     function editRow(id)
    {
		layer_show("修改权限项信息",'${ctx}/system/power/item/edit.do?id='+id,800,450)
    }
    function saveOK(action, data)
    {
    	layer.closeAll(); 
    	window.location.reload();
    }
    function onDelete()
    {
    	var selectRowData;
    	selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (selectRowData==""||selectRowData==null) { 
        	layer.msg('请选择一条权限信息!',{icon: 7,time:1500}); 
        	return; 
        }
    	deleteRow(selectRowData);
    }
     function deleteRow(id)
    {
    	 
    	 layer.confirm('是否确认删除所选权限信息？',function(yes){
 			delRow(id);
 		});
    	 
    }
    
    function delRow(id)
    {
    	var url = '${ctx}/system/power/item/delete.do?id='+id;
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
    
    
    
    
    
    
    function gotoURL(url){
// 	closeSearchDlg();
	window.location.href = url;
	}

	function f_onBack(){
		var backUrl='${ctx}/system/power/group/main.do';
		var url = backUrl;
		if( url == '' ){
			var n = baseUrl.lastIndexOf('/');
			if( n>=0 ){
				url = baseUrl.substr(0,n+1)+'main.do ';
				}
		}
		if( url != '' ){
			gotoURL(url);
		}
		return false;
	}
    
    
		
		</script>
		
		
	<body>


						  
						  <div id="btn" style="margin-top:2px;">
						    <button class="btn btn-white btn-default btn-bold" onclick="onadd()">
								<i class="ace-icon fa  fa-plus bigger-120"></i>
								添加
							</button>
							<button class="btn btn-white btn-info btn-bold"onclick="onEdit()">
								<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
								修改
							</button>
							<button class="btn btn-white btn-warning btn-bold"onclick="onDelete()">
								<i class="ace-icon fa fa-times bigger-120"></i>
								删除
							</button>
							<button class="btn btn-white btn-success btn-bold"onclick="onRefresh()" >
								<i class="ace-icon fa fa-refresh bigger-120"></i>
								刷新
							</button>
							<button class="btn btn-white btn-pink btn-bold"onclick="f_onBack()">
								<i class="ace-icon fa fa-reply bigger-120"></i>
								返回
							</button>
						     
						  </div>

							<div class="col-xs-12">
								
								<table id="list"></table>
								
								<div id="pager"></div>

							</div><!-- /.col -->
		
		
	</body>
</html>










