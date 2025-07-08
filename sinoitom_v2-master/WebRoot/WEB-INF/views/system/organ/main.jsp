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
		<title>机构管理</title>

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
<!-- 						  	<button class="btn btn-white btn-success btn-bold"onclick="onRefresh()" > -->
<!-- 								<i class="ace-icon fa fa-refresh bigger-120 green"></i> -->
<!-- 								上移 -->
<!-- 							</button> -->
<!-- 							<button class="btn btn-white btn-success btn-bold"onclick="onRefresh()" > -->
<!-- 								<i class="ace-icon fa fa-refresh bigger-120 green"></i> -->
<!-- 								下移 -->
<!-- 							</button> -->
<!-- 							<button class="btn btn-white btn-success btn-bold"onclick="onRefresh()" > -->
<!-- 								<i class="ace-icon fa fa-refresh bigger-120 green"></i> -->
<!-- 								左移 -->
<!-- 							</button> -->
<!-- 							<button class="btn btn-white btn-success btn-bold"onclick="onRefresh()" > -->
<!-- 								<i class="ace-icon fa fa-refresh bigger-120 green"></i> -->
<!-- 								右移 -->
<!-- 							</button> -->
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
							<button class="btn btn-white btn-warning btn-bold"onclick="onView()">
								<i class="ace-icon fa fa-file-text-o bigger-120 orange"></i>
								详情
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

							</div><!-- /.col -->

    <script type="text/javascript">
    
   var gridHeight;		
		$(function(){ 
		 	gridHeight= $(document).height() - 120 - $('#btn').height();
			pageInit();	
			resize();  //自动调整页面Grid	  
		 });
 
 function pageInit(){
			jQuery("#list").jqGrid( {
			url : '${ctx}/system/organ/getData.do',
    		treeGrid: true,  
            treeGridModel: 'adjacency', //treeGrid模式，跟json元数据有关 ,adjacency/nested  
            ExpandColumn : 'orgName',
            datatype: 'json',  
            pager: '#pager',  
            sortname: 'orgName',  
            viewrecords : true, 
            sortorder: "asc",  
            toolbar:[true,'top'],
    	 colNames : [ '机构名称', '机构类型', '机构编码', '所在城市', '热线电话', '电话', '邮编', '地址' ],
    	 colModel : [ 
    	        	 {name : 'orgName',index : 'orgName',width : 100},
    	        	 {name : 'orgType',index : 'orgType',width : 60}, 
    	        	 {name : 'orgCode',index : 'orgCode',width : 90},
    	        	 {name : 'city',index : 'city',width: 50},
    	        	 {name : 'hotLine',index : 'hotLine',width : 50},
    	        	 {name : 'phone',index : 'phone',width : 70}, 
    	        	 {name : 'zipCode',index : 'zipCode',width : 50},
    	        	 {name : 'address',index : 'address',width: 120,align : "left"},
    	        	 ], 
    	        	 
    	        	 treeReader : {  
    	                    level_field: "level",  
    	                    parent_id_field: "parentId",
    	                    leaf_field: "isLeaf",  
    	                    expanded_field: "expanded"  
    	                },
    	        	 
    	        	 height:gridHeight,
					  loadComplete: function () {
                		var table = this;
                		updatePagerIcons(table);
            			}
    	        	});
    		jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
<%--			jQuery("#list").jqGrid('navGrid','#pager',--%>
<%--								{ 	//navbar options--%>
<%--									edit: true,--%>
<%--									editicon : 'ace-icon fa fa-pencil blue',--%>
<%--									add: true,--%>
<%--									addicon : 'ace-icon fa fa-plus-circle purple',--%>
<%--									del: true,--%>
<%--									delicon : 'ace-icon fa fa-trash-o red',--%>
<%--									search: true,--%>
<%--									searchicon : 'ace-icon fa fa-search orange',--%>
<%--									refresh: true,--%>
<%--									refreshicon : 'ace-icon fa fa-refresh green',--%>
<%--									view: true,--%>
<%--									viewicon : 'ace-icon fa fa-search-plus grey'--%>
<%--								});--%>
								$("#t_list").css({"height":30});
			            $("#btn").appendTo("#t_list");

}
								
  function onadd(){
		
    	 var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
    	 if(rowid==null){
    		 rowid = "";
    	 }
    	 
    	 // var row= jQuery("#list").jqGrid('getRowData', rowid);//获取当前行所有数据 
		 layer_show("添加机构",'${ctx}/system/organ/add.do?parentId='+rowid,800,550)
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
     function editRow(id)
    {
		layer_show("修改机构信息",'${ctx}/system/organ/edit.do?id='+id,800,550)
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
    	var url = '${ctx}/system/organ/delete.do?id='+id;
    	$.ajax({
    		url: url,
    		dataType: 'json',
    		
    		success: function(data) {
    			if(data.result=="success"){
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
    
     //详情
        function onView(){
			   var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
	            var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
				var rowData = jQuery("#list").jqGrid('getRowData',selectedId);
			   if(selectedId.length>1){
				   
			    layer.msg('只能选择一条数据进行查看!',{icon: 7,time:1500});
			    return;
			   }else if(rowid ==""||rowid ==null){
				  layer.msg('请选择一条数据！',{icon: 7,time:1500});
				   return;  
			   }
			   layer_show("巡检设备详情",'${ctx}/system/organ/view.do?id=' +rowid,750,350);
			} 
    	
       
           

//             dataMain.Rows = ${jsonTreeData};
//             gridMain = $("#maingrid").ligerGrid({
//                 columns: 
//                     [
//                     { display:'机构名称', name:'orgName', width:200, align:'left' },
//                     { display:'机构类型', name:'orgType', width:120, align:'left' },
//                     { display:'机构编码', name:'orgCode', width:150, align:'left' },
//                     { display:'机构说明', name:'description', width:180, align:'left' },
//                     { 
//                     	display:'操作', width:150,
//                         render: function (row)
//                         {
//                             var html = '';
//                         	<shiro:hasPermission name="organ:update"> 
//                             html += '<span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick="editRow(\'' + row.id + '\');">修改</span>';
//                         	</shiro:hasPermission>  
//                         	<shiro:hasPermission name="organ:delete"> 
//                         	if( html != '') html += ' | ';
//                             html += '<span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick="deleteRow(\'' + row.id + '\');">删除</span>';
//                         	</shiro:hasPermission>  
//                         	<shiro:hasPermission name="position:read"> 
//                         	if( html != '') html += ' | ';
//                             html += '<span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick="managePos(\'' + row.id + '\');">职位管理</span>';
//                         	</shiro:hasPermission>  
//                             return html;
//                         }
//                     }
//                		],  
//                	tree: { columnName: 'orgName' },
// 				data: dataMain,
// 				alternatingRow: 'true',
//                 width: '100%',
//                 height:'100%', 
//                 pageSize: '20',
//                 pageSizeOptions: [10, 20, 30, 50, 100],
//                 onSelectRow: function (data, index, dom){
//                 	selectRowData = data;
//                 } 
//             });

//             $("#formSearch").ajaxForm({
//             	dataType: 'json',
//             	success: function(data) { 
//            			dataMain.Rows = data;
// 					loadData();
//             		dlgSearch.hide();
//             	}
//             }); 
            
//             $("#bnReset").click(function()
//             {
//             	window.parent.$("input[ltype='text'][name*='filter_']").val("");
//             	window.parent.$("select[name*='filter_']").val("");
//             });
            
//         });
        
//         function loadData(){
//             selectRowData = null;
//             gridMain.loadData();
//         }
        
//         function doData(data)
//         {
//    			if(data.result=='success'){
//    				refreshData();
//    			}
//    			else{
//    				window.parent.$.ligerDialog.error(data.message);
//    			}
//         }
        
//         function refreshData()
//         {
//         	var url = timeURL('${ctx}/system/organ/getData.do');
//         	$.ajax({
//         		url: url,
//         		dataType: 'json',
//         		success: function(data) {
//            			dataMain.Rows = data;
// 					loadData();
//         		}
//         	});        			
//         }
        
//         function onAdd()
//         {
//             if (!selectRowData && operName!='admin') { 
//             	$.ligerDialog.warn('请选择上级机构！'); 
//             	return; 
//             }
//         	var url = '${ctx}/system/organ/add.do';
//         	if (selectRowData){
//         		url += '?parentId='+selectRowData.id+'&treeCode='+selectRowData.treeCode;
//         	}
//      		showDlg('添加机构', 720, 300, timeURL(url));     
//         }

//         function onEdit()
//         {
//             if (!selectRowData) { 
//             	$.ligerDialog.warn('请选择要修改的数据！'); 
//             	return; 
//             }
//         	editRow(selectRowData.id);
//         }
        
//         function editRow(id)
//         {
//         	var url = timeURL('${ctx}/system/organ/edit.do?id='+id);
//      		showDlg('修改机构', 720, 300, url);     
//         }
        
       
        
//         function onMove()
//         {
//             if (!selectRowData) { 
//             	$.ligerDialog.warn('请选择要移动的数据！'); 
//             	return; 
//             }
//         	moveRow(selectRowData.id);
//         }
        
//         function moveRow(id)
//         {
//         	var url = timeURL('${ctx}/system/organ/moveGo.do?id='+id);
//         	showDlg('移动机构', 720, 480, url);     
//         }
        
//         function moveOK()
//         {
//         	refreshData();
//         }

//         function onDelete()
//         {
//             if (!selectRowData) { 
//             	$.ligerDialog.warn('请选择要删除的数据！'); 
//             	return; 
//             }
//         	deleteRow(selectRowData.id);
//         }
        
//         function deleteRow(id)
//         {
//             if ( id == '${LOGIN_USER_MAIN_ORGAN.orgId}') {
//             	$.ligerDialog.warn('不能删除当前登录用户所属机构！'); 
//             	return; 
//             }
//             else {
//             	$.ligerDialog.confirm('是否确认删除该行数据？', function (yes)
//                 {
//                 	if(yes) delRow(id);
//                 });
//             }
//         }
        
//         function delRow(id)
//         {
//         	var url = timeURL('${ctx}/system/organ/delete.do?id='+id);
//         	$.ajax({
//         		url: url,
//         		dataType: 'json',
//         		success: function(data) {
//         			doData(data);
//         		}
//         	});        			
//         }

//         function onManagePos()
//         {
//             if (!selectRowData) { 
//             	$.ligerDialog.warn('请选择机构数据！'); 
//             	return; 
//             }
//             managePos(selectRowData.id);
//         }
        
//         function managePos(id)
//         {
//         	var url = timeURL('${ctx}/system/organ/position/main.do?orgId='+id);
//         	window.location.href = url;
//         }

//         function onSearch()
//         {
//         	if( dlgSearch ){
//         		dlgSearch.show();
//         	}
//         	else{
//         		$("#formSearch input[ltype='text']").ligerTextBox({inputWidth:200}); $("#formSearch input").filter(".ip2").ligerTextBox({ width: 553 });
//             	dlgSearch = $.ligerDialog.open({ title:'查询', width:720, height:300, target: $("#searchDlg") });
//         	}
//         }
          
//         function onImport()
//         {
//         	var url = timeURL('${ctx}/system/organ/importGo.do');
//         	showDlg('导入机构', 720, 320, url);     
//         }
        
//         function onExport()
//         {
//         	location.href='${ctx}/system/organ/exportExcel.do';
//         }

        
//         function onRefresh(){
//         	window.location.reload();
//         }
		
		
// 		递归获取子节点信息
// 		function traverse(node, i,id) {
// 			var manager = $("#maingrid").ligerGetGridManager();
// 			for(var j=0;j<node.length;j++){
// 				if(node[j].id==id){
// 						manager.select(node[j]);
// 						return;
// 				}
// 				var children = node[j].children;
// 				if (children != null) {
// 					if(children[i].id==id){
// 						var cc=children[i];
// 						manager.select(cc);
// 						return;
// 					}
// 					if (i == children.length - 1) {
// 						traverse(children[0], 0,id);
// 					} else {
// 						traverse(node, i + 1,id);
// 					}
// 				}
// 			}
// 		}
		
// 		获取上移、下移、右移目标id
// 		function getMoveOrUpId(treeCode,type){
// 			var upId='';
// 			var scount=(type=='up'?-1:1)
// 			if(treeCode!=null&&treeCode!=''){
// 				var moveTreeCode=parseInt(treeCode)+scount;
// 				var notes=gridMain.getData();
// 				if(notes != null && notes != ""){
// 			           for(var j=0;j<notes.length;j++){
// 			           	   var rowdata=notes[j];
// 			               if(rowdata.treeCode==moveTreeCode){
// 			                  upId=rowdata.id;
// 			               }
// 			           }
// 			    }
// 		    }
// 		    return upId;
// 		}
		
// 		获取左移目标id
// 		function getLeftId(treeCode,type){
// 			var leftId='';
// 			if(treeCode!=null&&treeCode!=''){
// 				var moveTreeCode=treeCode.substring(0,treeCode.length-3);
// 				var notes=gridMain.getData();
// 				if(notes != null && notes != ''&&moveTreeCode!=''){
// 			           for(var j=0;j<notes.length;j++){
// 			           	   var rowdata=notes[j];
// 			               if(rowdata.treeCode==moveTreeCode){
// 			                  leftId=rowdata.id;
// 			               }
// 			           }
// 			    }
// 		    }
// 		    return leftId;
// 		}
		
// 		上移
// 		function moveUp(){
// 			if (!selectRowData) { 
//             	$.ligerDialog.warn('请选择要移动的数据！'); 
//             	return; 
//             }
//             var targetId=getMoveOrUpId(selectRowData.treeCode,'up');
//             if (targetId==null||targetId=='') { 
//             	$.ligerDialog.warn('已到顶点！'); 
//             	return;
//             }
//             moveUpDown(selectRowData.id,targetId,2);
// 		}
		
// 		左移
// 		function moveLeft(){
// 			if (!selectRowData) { 
//             	$.ligerDialog.warn('请选择要移动的数据！'); 
//             	return; 
//             }
//             var targetId=getLeftId(selectRowData.treeCode,'up');
//             if (targetId==null||targetId=='') { 
//             	$.ligerDialog.warn('已到最左边！'); 
//             	return;
//             }
//             moveUpDown(selectRowData.id,targetId,3);
// 		}
		
// 		右移、把选中节点移动到上一条记录的最后子节点
// 		function moveRight(){
// 			if (!selectRowData) { 
//             	$.ligerDialog.warn('请选择要移动的数据！'); 
//             	return; 
//             }
//             var targetId=getMoveOrUpId(selectRowData.treeCode,'up');
//             if (targetId==null||targetId=='') { 
//             	$.ligerDialog.warn('已到顶点！'); 
//             	return;
//             }
//             moveUpDown(selectRowData.id,targetId,1);
// 		}
		
// 		下移
// 		function moveDown(){
// 			if (!selectRowData) { 
//             	$.ligerDialog.warn('请选择要移动的数据！'); 
//             	return; 
//             }
// 			var targetId=getMoveOrUpId(selectRowData.treeCode,'down');
//             if (targetId==null||targetId=='') { 
//             	$.ligerDialog.warn('已到最低点！'); 
//             	return;
//             }
//             moveUpDown(selectRowData.id,targetId,3);
// 		}
		
// 		移动并刷新
// 		function moveUpDown(moveId,targetId,moveType){
		
// 			var url = timeURL('${ctx}/system/organ/move.do?moveId='+moveId+'&targetId='+targetId+'&moveType='+moveType);
// 	        	$.ajax({
// 	        		url: url,
// 	        		async:false,
// 	        		dataType: 'json',
// 	        		success: function(data) {
// 	           			if(data.result='success'){
// 	           				dataMain.Rows = data.jsonlist;
// 							loadData();
// 	           				traverse(dataMain.Rows, 0,moveId);
// 	           			}
// 	        		}
// 	        	});        		
//         }
		
// 		删除子机构
// 		function onDeleteSubOrg()
// 		{
// 			if (!selectRowData) { 
//             	$.ligerDialog.warn('请选择要删除的数据！'); 
//             	return; 
//             }
//             var id=selectRowData.id;
//             	$.ligerDialog.confirm('该操作可删除所选机构下的分支机构  并保留当前机构,是否继续？', function (yes)
//                 {
//                 	if(yes)
//                 		{
// 	                		var url = timeURL('${ctx}/system/organ/deleteSubOrg.do?id='+id);
// 	                    	$.ajax({
// 	                    		url: url,
// 	                    		dataType: 'json',
// 	                    		success: function(data) {
// 	                    			doData(data);
// 	                    		}
// 	                    	});     
//                 		}
//                 });
// 		}
    </script>
    </body>
</html>













