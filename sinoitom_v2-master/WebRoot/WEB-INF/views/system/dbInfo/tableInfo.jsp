<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />


<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/js/dataFormat.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/mainTab.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>

<script type="text/javascript">
var tableName='${tableName}';
var dbName='${dbName}';
isPageList=true;
$(function ()
{
    $("#toptoolbar").ligerToolBar({ 
		items: 
    		[
        	   { text:'刷新', click:f_onRefresh, icon:'refresh' }
        	]
    });

    gridMain = $("#maingrid").ligerGrid({
        columns: 
            [
            { display:'字段名', name:'column_name', width:120, align:'left' },
            { display:'字段类型', name:'columu_type', width:100, align:'left' },
            { display:'主\\外键', name:'column_key', width:150, align:'left'},
            { display:'缺省值', name:'column_default', width:120, align:'left' },
            { display:'允许为空', name:'is_nullable', width:100, align:'left' },
            { display:'备注', name:'column_comment', width:150, align:'left' },
       		],  
	    	data : dataMain,
			alternatingRow: 'true',
			width: '100%', height:'100%', 
			pageSize: '25', pageSizeOptions: [25, 50, 100],
			onSelectRow : function(data, index, dom) {
				selectRowData = data;
			}
    });
    getData();
});
function getData() {
	var url = timeURL('${ctx}/system/queryDB/getTable.do?dbName='+dbName+'&tableName='+tableName);
	$.ajax({
		url : url,
		data : "date=" + new Date(),
		dataType : 'json',
		success : function(data) {
			dataMain.Rows = data.jsonlist;
			loadData();
		}
	});
}
</script>
</head>
<body style="padding:0; overflow:hidden; position:relative;"> 
<!-- <div class="l-toolbar" style="height:24px;padding-left: 39px;" id="titleContent">${titleContent}</div> -->

    <div id="toptoolbar"></div>

    <div id="maingrid"></div> 
 </div>
</body>
</html>
