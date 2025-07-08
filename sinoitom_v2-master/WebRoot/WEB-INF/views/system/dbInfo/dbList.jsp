<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>数据库表结构</title>


    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css" />
    <link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css" />

    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css" />
    <link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css" />
    <link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet" />
    <link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
    <link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />


    <script src="${ctx }/static/assets/js/jquery.min.js"></script>
    <script src="${ctx }/static/assets/js/layer.js"></script>
    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script>

    <script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
    <script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
    <script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
    <script src="${ctx }/static/assets/js/resize.js"></script>
    <script src="${ctx }/static/assets/js/bootbox.js"></script>
</head>
	
	

<script type="text/javascript">

var gridHeight;
$(function () {
    gridHeight = $(document).height() - 120 - $('#btn').height();
    pageInit();
    resize();  //自动调整页面Grid
    bootbox.setDefaults("locale", "zh_CN");
});
	 
function pageInit(){
	jQuery("#list").jqGrid( {
	//url :' ${ctx }/system/queryDB/getData.do',
	data:${jsonStr},
	 datatype : "local", 
	 colNames : [ '数据库', '引擎类型', '表名', '记录数', '平均长度', '备注' ],
	 colModel : [ 
	        	 {name : 'table_schema',index : 'table_schema',width : 120},
	        	 {name : 'engine',index : 'engine',width : 100}, 
	        	 {name : 'table_name',index : 'table_name',width : 150, cellattr: addCellAttr,
	        	 
	        	  formatter: function (cellvalue, options, row) {
			        return "<div onclick='showTable(\""+row.table_schema+"\",\""+row.table_name+"\")' style='cursor:pointer; text-decoration:none; color:#3399ff'>"+row.table_name+"</div>"
			      }	
	        	 },
	        	 {name : 'table_rows',index : 'table_rows',width: 100},
	        	 {name : 'avg_row_length',index : 'avg_row_length',width : 120},
	        	 {name : 'table_comment',index : 'table_comment',width: 150,align : "left"}
	        	 ], 
	        	 
	        	pager: '#pager',  
		            rowNum: 50,
		            rowList: [50, 100, 150],
		            sortname: 'table_schema',  
		            viewrecords : true, 
		            sorttable:true,
		            sortorder: "asc",  
		            toolbar:[true,'top'],
		            multiselect: true,
    	        	height:gridHeight,
    	        	loadComplete: function () {
                		var table = this;
                		updatePagerIcons(table);
            			}

	        	});
	
	jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
	
	$("#t_list").css({"height":30});
	$("#btn").appendTo("#t_list");

}
function addCellAttr(rowId, val, rawObject, cm, rdata) {
	     return "style='color:#3399ff'";
	}
	
function showTable(dbName,tableName){
	var url = '${ctx}/system/queryDB/getTableInfo.do?dbName='+dbName+'&tableName='+tableName;
	layer_show("数据表信息",url,900, 500);
}
	
function onRefresh(){
	window.location.reload();
}

</script>


<body>

     	
						
							<div id="btn" style="margin-top:2px;">
								<button class="btn btn-white btn-success btn-bold" onclick="onRefresh()">
									<i class="ace-icon fa fa-refresh bigger-120"></i>
									刷新
								</button>
						     
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
