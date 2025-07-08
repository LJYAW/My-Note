<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>交换机转发表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<%-- <script src="${ctx}/static/formatter.js" type="text/javascript"></script> --%>
<%-- <script src="${ctx}/js/dataFormat.js" type="text/javascript"></script> --%>
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
                        { display:'<span style="font-weight:bold; ">${switchName}</span>',columns:[
	             			{ display: '交换机端口', name: 'fdbPort',width:150 ,type:'int'},
	             			{ display: '端口名称', name: 'portName',width:150 ,type:'int'},
	             			{ display: '端口状态', name: 'port_Status',width:150,type:'int',
	             				render: function (row)
		                  		 {
		                  		 	return getStatus(row.port_Status);
		                  		 }
	             			},
	             			{ display: 'Trunk', name: 'is_TrunkPort',width:150,type:'int',
	             				render: function (row)
		                  		 {
			                    	if(row.is_TrunkPort==1){
			                    	    return '<span style=\"align:certer;line-height:22px;\">是</span>';
			                    	}else{
			                    		return '<span style=\"align:certer;line-height:22px;\">否</span>';
			                    	}
			                   	}
	             			},
	             			//{ display: 'is_MultiVlan', name: 'is_MultiVlan',width:150 },
	             			{ display: 'Mac地址', name: 'macAddress',width:200,type:'float'}
             			]
             		}
                 ],   
				data: dataMain,
				alternatingRow: 'true',
                width: '100%',
                height:'100%', 
                pageSize: '20',
                pageSizeOptions: [10, 20, 30, 50,100],
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
        	var url = '${ctx}/fas/res/net/devices/ajaxFDBTable.do?id='+switchId;
        	$.ajax({
        		url: url,
//         		data: "id="+switchId,
        		dataType: 'json',
        		success: function(data) {
           			dataMain.Rows = data;
           			$("#maingrid").show();
           			$("#pageloading").hide();
           			loadData();
        		}
        	});        			
        };
        
     function getStatus(status){
     	var st='';
     	if(status==null||status=='')
     		return '';
     	var s=parseInt(status);
     	if(s==1)
     		st='other';
     	if(s==2)
     		st='invalid';
     	if(s==3)
     		st='learned';
     	if(s==4)
     		st='self';
     	if(s==5)
     		st='mgmt';
     	return st;
     }
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