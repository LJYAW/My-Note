<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>交换机接口信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/formatter.js" type="text/javascript"></script>
<script src="${ctx}/js/dataFormat.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var dataMain = {};
    	var gridMain;
        var selectRowData = null;
    	var dlgSearch = null;
    	var orgId=null;
    	
        $(function ()
        {	
        	
        	$("#toptoolbar").ligerToolBar({ 
				items: 
            		[
                	{ text:'刷新', click: onRefresh, icon:'refresh' }
                	]
            });
            dataMain.Rows =null;
            gridMain = $("#maingrid").ligerGrid({
                columns: 
                      [
                      { display:'<span style="font-weight:bold; ">${devName}</span>',columns:[
             			{ display: 'IfIndex', name: 'ifIndex', width:80,type:'int'},
             			{ display: '接口类型', name: 'ifType',width:100 },
             			{ display: '接口名称', name: 'ifIndex',width:100 ,
             				render: function (row)
	                  		 {
		                    	return row.ifName;
		                   	}
             			},
             			{ display: 'IP地址', name: 'ipAddr',width:130 },
             			{ display: '掩码', name: 'netMask',width:130 },
             			{ display: '子网段', name: 'netAddr',width:130 },
             			{ display: 'Mac地址', name: 'ifPhysAddress',width:150,type:'float'},
             			{ display: 'MTU', name: 'ifMTU',width:80,type:'float'},
             			{ display: 'Trunk', name: 'isTrunk',width:80,type:'int',
             				render: function (row)
	                  		 {
		                    	if(row.isTrunk==1){
		                    	    return '<span style=\"align:certer;line-height:22px;\">是</span>';
		                    	}else{
		                    		return '<span style=\"align:certer;line-height:22px;\">否</span>';
		                    	}
		                   	}
             			},
             			{ display: '接口速率', name: 'baudRate',width:100 }
             		]
             		}
                 ],   
				data: dataMain,
				alternatingRow: 'true',
                width: '100%',
                height:'100%', 
                pageSize: '20',
                pageSizeOptions: [ 50, 100,200,300,500],
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                } 
            });
          });
      
      function onRefresh(){
        	window.location.reload();
      }
      
      function loadData(){
            selectRowData = null;
            gridMain.loadData();
        }
      
      function getData()
        {
        	$("#maingrid").hide();
        	$("#pageloading").show();
        	var switchId=$("#switchId").val();
        	var url = timeURL('${ctx}/fas/res/net/devices/ajaxIfDetail.do');
        	$.ajax({
        		url: url,
        		data: "id="+switchId+"&date="+new Date(),
        		dataType: 'json',
        		success: function(data) {
           			dataMain.Rows = data;
           			$("#maingrid").show();
           			$("#pageloading").hide();
           			loadData();
        		}
        	});        			
        };
        
    </script>
</head>
<body style="padding:0px; overflow:hidden;" onload="getData();">
	<form id="sysForm" name="sysForm">
	<input type="hidden" name="switchId" id="switchId" value="${switchId}"/>
		<div class="l-loading" style="display:block" id="pageloading"></div>
		<div id="toptoolbar"></div> 
	    <div id="maingrid"></div>
		<div style="display:none;">
	    </div>
    </form>
</body>
</html>