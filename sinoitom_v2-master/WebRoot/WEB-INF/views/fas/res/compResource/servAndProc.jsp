<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>服务与进程详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css" />
		<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
		
		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css" />
		<link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet" />
		<!-- ace styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />
		<!-- ace settings handler -->
		<script src="${ctx }/static/assets/js/ace-extra.min.js"></script>
		
		<script src="${ctx }/static/assets/js/jquery.min.js"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>
	    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script> 
		
		<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
		<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
		<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
		<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
		<script src="${ctx }/static/assets/js/resize.js"></script>

<script type="text/javascript">

//子表格的ID
var subgrid_table_id; 
var count=0;

var gridHeight;
$(function(){ 
	gridHeight= $(document).height()-$('#btn').height()-110;
	pageInit();	
 });


	function pageInit(){
		jQuery("#list").jqGrid( {
			data:  ${jsonListData},
        datatype: "local",   
		
		colModel : [ 
				 {label:'服务器IP',name : 'hostIp',index : 'hostIp',width : 130},
		    	 {label:'PID',name : 'pid',index : 'pid',width : 65},
		    	 {label:'PPID',name : 'ppid',index : 'ppid',width : 65},
		    	 {label:'进程名称',name : 'procName',index : 'procName',width : 100 },
		    	 {label:'进程类型',name : 'procType',index : 'procType',width : 80 },
		    	 {label:'启动命令',name : 'startCmd',index : 'startCmd',width : 450 },
		    	 {label:'操作',
	                    width: 120,
	                    formatter: function (cellvalue, options, row) {
	                        var html = '<span style="color:blue;cursor:pointer;" onclick="editServiceProcessRow(\'' + row.id + '\');">进程信息维护</span>';

	                        return html;
	                    }
	                }
		    	 ], 
		    	
		    	viewrecords: true,
		    	//rowNum:-1,
			 		height:gridHeight,
			 		width:1000,
					altRows: true,
					toolbar:[true,'top'],
					//multiselect: true,
					multiboxonly: true,
<%--					loadComplete : function() {--%>
<%--						var table = this;--%>
<%--							setTimeout(function(){--%>
<%--							updatePagerIcons(table);--%>
<%--							}, 0);--%>
<%--					},--%>
<%--					onSelectRow: function (rowid, rowindex)--%>
<%--		              {--%>
<%--							getSelectService(rowid, rowindex);--%>
<%--		              },--%>

					subGrid: true,  // (1)开启子表格支持  
					subGridOptions : {
						plusicon : "ace-icon fa fa-angle-double-down",  //收缩效果
						minusicon  : "ace-icon fa fa-angle-double-up"  //展开效果
					//	openicon : "ace-icon fa fa-chevron-right center orange"
					},
					subGridRowExpanded: function(subgrid_id, row_id) {  // (2)子表格容器的id和需要展开子表格的行id，将传入此事件函数  
						 
                		var rowData = $("#list").jqGrid('getRowData', row_id);//通过索引获取当前行对象
                		subgrid_table_id = subgrid_id + "_t";   // (3)根据subgrid_id定义对应的子表格的table的id  
                		var subgrid_pager_id;  
                		subgrid_pager_id = subgrid_id + "_pager"  // (4)根据subgrid_id定义对应的子表格的pager的id  
                		// (5)动态添加子报表的table和pager  
                		$("#" + subgrid_id).html("<table id='"+subgrid_table_id+"' ></table><div id='"+subgrid_pager_id+"' ></div>");  
                  		//alert(subgrid_table_id);
                		// (6)创建jqGrid对象  
		                $("#" + subgrid_table_id).jqGrid({  
						 datatype : "json", 
						 url:' ${ctx}/fas/res/host/resServiceProcess/getAppServiceData.do?id='+row_id,
						 colNames : [ 'Id', '服务器IP', 'PID', '协议号', '协议名称', '服务端口', '应用服务名称', '服务分类', '服务命令', '操作' ],
						 	colModel : [ 
						 	         {name : 'id',index : 'id',width: 10, hidden: true },
						        	 {name : 'hostIp',index : 'hostIp',width: 150},
						        	 {name : 'pid',index : 'pid',width : 80},
						        	 {name : 'protNo',index : 'protNo',width : 80},
						        	 {name : 'protName',index : 'protName',width : 80}, 
						        	 {name : 'svcPort',index : 'svcPort',width : 80},
						        	 {name : 'appSvcName',index : 'appSvcName',width : 150},
						        	 {name : 'svcClass',index : 'svcClass',width : 100,
						        		 formatter: function (cellvalue, options, row) {
						                        var html = '';
						                        if (row.svcClass == "1") {
						                            html += '<span>Web</span>';
						                        } else if (row.svcClass == "2") {
						                            html += '<span>App</span>';
						                        } else if (row.svcClass == "3") {
						                            html += '<span>DB</span>';
						                        }
						                        return html;
						                    }	 
						        	 },
						        	 {name : 'command',index : 'command',width: 300},
						        	 {
						                    width: 120,
						                    formatter: function (cellvalue, options, row) {
						                        var html = '<span style="color:blue;cursor:pointer;" onclick="editAppServiceRow(\'' + row.id + '\');">服务信息维护</span>';

						                        return html;
						                    }
						                }
						        	 ], 
// 						 	viewrecords : true,//定义是否要显示总记录数
// 						 	rowNum:50,//在grid上显示记录条数，这个参数是要被传递到后台
// 						 	pager : '#'+subgrid_pager_id,//定义翻页用的导航栏，必须是有效的html元素。翻页工具栏可以放置在html页面任意位置
						 	sortname:'id',//默认的排序列。可以是列名称或者是一个数字，这个参数会被提交到后台
// 							rowList:[50,100,150],//一个下拉选择框，用来改变显示记录数，当选择时会覆盖rowNum参数传递到后台
							altRows: true,//设置表格 zebra-striped（斑马条纹）值,如果是false要重新加载数据
							autowidth: true, //表宽自动调整
							multiselect : true,
							multiboxonly: true,
							loadComplete : function() {
// 								var table = this;
// 								updatePagerIcons(table);
									
							   var curRowDatas = jQuery("#"+subgrid_table_id).jqGrid('getRowData');
							   //alert(curRowDatas);
							   for(var i=0;i<curRowDatas.length;i++){
							      if(curRowDatas[i].reachability!="reachable"){
							     	$("#"+subgrid_table_id).find("input[id='jqg_"+subgrid_table_id+"_"+curRowDatas[i].id+"']").hover(function(){$(this).css("cursor","url('../../img/no.png'),pointer")});
							  	}	
							   }
							   	
							}, 
							onSelectRow:function(rowId,status){
							  if(status){
							    count++;
							  }else{
							    if(count>0){
							     count--;
							    }
							  }
							  var row = jQuery("#"+subgrid_table_id).jqGrid('getRowData',rowId);
							  if(row.reachability!="reachable"){
							    $("#"+subgrid_table_id).jqGrid("setSelection", rowId,false);
							  }
							},
							onSelectAll:function(rowId,status){
							  if(!status){
							    count=0;
							  }else{
							     var rowdatas = jQuery("#"+subgrid_table_id).jqGrid('getGridParam', 'selarrrow');
							  count=rowdatas.length;
							  }
							}
							
		               });  
		               
           }
					
	        	});
		
	} 
	
	
	//暂时不用
	function getSelectService(rowid, rowindex){
		//alert(rowid);
		//alert(rowindex);
<%--		if(rowindex){--%>
			//alert("显示");
			$("#appDiv").css("display","block");//显示div
			var div1 = document.getElementById('appDiv');
			var code = '<table id="servicelist">';
			div1.innerHTML = code + '</table>';
			appServiceInit(rowid);	
<%--		}else{--%>
<%--			//alert("不显示");--%>
<%--			$("#appDiv").css("display","none");//隐藏div--%>
<%--			document.getElementById('appDiv').innerHTML = "";--%>
<%--		    //$("#appDiv").css("display","block");//显示div--%>
<%--		}--%>
	}
	
	//暂时不用
	function appServiceInit(id){
		//alert("进");
		jQuery("#servicelist").jqGrid( {
		url :' ${ctx}/fas/res/host/resServiceProcess/getAppServiceData.do?id='+id,
        datatype : "json",
	 	colNames : [ '服务器IP', 'PID', '协议号', '协议名称', '服务端口', '服务命令' ],
	 	colModel : [ 
	        	 {name : 'hostIp',index : 'hostIp',width: 150},
	        	 {name : 'pid',index : 'pid',width : 60},
	        	 {name : 'protNo',index : 'protNo',width : 100},
	        	 {name : 'protName',index : 'protName',width : 100}, 
	        	 {name : 'svcPort',index : 'svcPort',width : 100},
	        	 {name : 'command',index : 'command',width: 200},
	        	 ], 
	        	 
	        	 width:880,
	        	 altRows: true,
	             toolbar: [true, 'top'],
	             multiboxonly: true,
	             loadComplete: function () {
	                 var table = this;
	                 setTimeout(function () {
	                     updatePagerIcons(table);
	                 }, 0);
	             },
	        	 
	        	});

}
	
	function onRefresh(){
    	window.location.reload();
    }
	
	function onEdit()
    {
		var rowid= jQuery("#"+subgrid_table_id).jqGrid('getGridParam', 'selrow');//获取当前行ID
		//var rowData = jQuery("#list").jqGrid('getRowData',rowid);
		//alert(rowData);
		if (rowid==""||rowid==null) { 
		 	layer.msg('请选择一条服务数据进行维护！', {icon: 7, time: 1500});
		 	return; 
		 }else{
			 editRow(rowid);
		 }
		
    }
    
    function editAppServiceRow(id)
    {
    	var url = '${ctx}/fas/res/host/resServiceProcess/editAppService.do?id='+id;
    	layer_show('服务信息维护',url,800, 400);
    }
    
    function editServiceProcessRow(id)
    {
    	var url = '${ctx}/fas/res/host/resServiceProcess/editServiceProcess.do?id='+id;
    	layer_show('进程信息维护',url,800, 400);
    }
    
    function saveAppOK() {
        onRefresh();
    }
    
    function closeDlg() {
        // alert("监测失败！");
        onRefresh();
        window.close();
    }
	
</script>

<style>
.ui-jqgrid tr.jqgrow td {
   white-space: normal !important;
   height:auto;
   vertical-align:text-top;
   padding-top:2px;
 }
</style>


</head>
<body style="overflow: hidden;"> 

<%--<div id="btn" style="margin-top:2px;">--%>
<%--	<button class="btn btn-white btn-default btn-bold" onclick="onEdit()" >--%>
<%--		<i class="ace-icon fa  fa-pencil-square-o bigger-120 default"></i>--%>
<%--			服务信息维护--%>
<%--	</button>--%>
<%--							--%>
<%--	<button class="btn btn-white btn-success btn-bold"onclick="onRefresh()" >--%>
<%--		<i class="ace-icon fa fa-refresh bigger-120 green"></i>--%>
<%--			刷新--%>
<%--	</button>--%>
<%--</div>--%>

<div class="aa" style="overflow: hidden;width: 100%;height: 100%;">
    <table id="list"></table>
<%--    <div id="pager"></div>--%>
	
</div>


</body>
</html>

