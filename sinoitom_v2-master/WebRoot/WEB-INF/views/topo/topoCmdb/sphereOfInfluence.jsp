<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.sino.base.common.Global"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<shiro:hasPermission name="employee:read"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>影响范围内的节点</title>
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
<%-- <script src="${ctx}/static/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script> --%>
<script src="${ctx}/js/index.js" type="text/javascript"></script>
 
 <script type="text/javascript">
 		
 
    	var dataMain = {};
    	var gridMain;
        var selectRowData = null;
    	var dlgSearch = null;
    	
    	$(function (){
    		$("#nodeId").val('${nodeId}');
    		$("#graphId").val('${graphId}');
    		
        	onload();
            $("#toptoolbar").ligerToolBar({ 
				items: 
            		[
//                 	{ text:'关闭', click: onAssociate, icon:'add'},
//                 	{line:true},
                	{ text:'刷新', click: onRefresh, icon:'refresh' }
                	]
            });

          
            gridMain = $("#maingrid").ligerGrid({
                columns: 
                  [
                  	{display: '资源ID', name: 'resId', width: 100, align: 'center'},
                  	{display: '资源名称', name: 'resName', width: 100, align: 'center'},
                  	{display: '英文名称', name: 'resLabel', width: 120, align: 'center'},
                  	{display: '资源可用性', name: 'resAvlb', width: 120, align: 'center'},
                   ], 
               	checkbox: 'true', 
               	alternatingRow: 'true' ,
				data: dataMain,
				alternatingRow: 'true',
                width: '100%',
                height:'100%', 
                pageSize: '20',
//                 rownumbers:true, 
                pageSizeOptions: [10, 20, 30, 50, 100],
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                } 
            });

            $("#formSearch").ajaxForm({
            	dataType: 'json',
            	success: function(data) { 
           			dataMain.Rows = data;
					loadData();
            		dlgSearch.hide();
            	}
            }); 
            
            $("#bnReset").click(function()
            {
            	window.parent.$("input[ltype='text'][name*='filter_']").val("");
            });
             
        });
        
        function loadData(){
            selectRowData = null;
            gridMain.loadData();
        }
        
        function doData(data){
   			if(data.result=='success'){
   				refreshData();
   			}
   			else{
   				window.parent.$.ligerDialog.error(data.message);
   			}
        }
        
        function refreshData(){
        	onload ();      			
        }
     function onload (){
     	var nodeId = $("#nodeId").val();
    	var graphId = $("#graphId").val();
    	$.ajax({
    		url:" ${ctx}/cmdb/graph/getSphereOfInfluence.do?nodeId="+nodeId+"&graphId="+graphId,
    		dataType: 'json',
    		success: function(data) {
    			dataMain.Rows = data.jsonData;
    			
				loadData();
    		}
    	});
    };
    
//         关联
      function onAssociate(){
        	window.parent.loadTopo(graphId);
			window.parent.closeDlg();      	
       }
        
// 		刷新
        function onRefresh(){
        	window.location.reload();
        }
        
    </script>
</head>
<body style="padding:0px; overflow:hidden; "> 
	<div id="toptoolbar"></div> 
    <div id="maingrid"></div> 
    <div style="display:none;">
    <div id="searchDlg" style="padding:0px;">
    <input id="nodeId" name="nodeId"/>
    <input id="graphId" name="graphId"/>
    </div>
    </div>
 </div>
</body>
</html>
</shiro:hasPermission>

