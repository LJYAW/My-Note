<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>巡检任务-关联巡检设备</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var dataMain = {};
    	var indDataMain = {};
    	var AllDevData={};
    	var gridMain;
    	var indGridMain;
        var selectRowData = null;
    	var dlgSearch = null;
    	var orgId=null;
    	var manager;
    	var indManager;
    	var ackTaskId='${id}';
        $(function ()
        {	
        <c:if test="${result=='success'}">
// 		window.parent.saveok();
		window.parent.closeDlg();
		</c:if>
        	$("#toptoolbar").ligerToolBar({ 
				items: 
            		[
                	{ text:'保存监测设备', click: selectInd, icon:'add' },
                	{ line:true },
                	{ text:'刷新', click: onRefresh, icon:'refresh' }
                	]
            });
             dataMain.Rows = ${jsonListData};
            manager=gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'<span style="font-weight:bold; ">机构名称</span>', name:'orgName', width:80,align:'center',type:'text' },
                    { display:'<span style="font-weight:bold; ">设备厂商</span>', name:'vendorName', width:80,align:'center',type:'text' },
                    { display:'<span style="font-weight:bold; ">设备IP</span>', name:'mgmtIP', width:120,align:'left',type:'text'},
                    { display:'<span style="font-weight:bold; ">设备类型</span>', name:'resTypeName', width:80,align:'center',type:'text'},
                    { display:'<span style="font-weight:bold; ">设备型号</span>', name:'prodModel', width:120,align:'left',type:'text'},
                    { display:'<span style="font-weight:bold; ">型号OID</span>', name:'modelOID', width:150,align:'left',type:'text'},
                    { display:'<span style="font-weight:bold; ">操作系统类型</span>', name:'osType', width:80,align:'center',type:'text'},
                    { display:'<span style="font-weight:bold; ">操作系统版本</span>', name:'osVerSort', width:100,align:'left',type:'text'},
                    { display:'<span style="font-weight:bold; ">采集方式</span>', name:'accessMode', width:80,align:'center',type:'text'},
                    { display:'<span style="font-weight:bold; ">巡检用户</span>', name:'userName', width:80,align:'center',type:'text'}
               	],   
				data: dataMain,
				alternatingRow: 'true',
				usePager:false,
				checkbox:true, 
                width: '100%',
                height:'100%', 
                pageSize: '50',
                pageSizeOptions: [25, 50, 100,200],
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                } 
            });
            
            });
            
      
      function onRefresh(){
        	swapDevType();
      }
      
        
      function loadData(){
            selectRowData = null;
            gridMain.loadData();
           
        }
        
    
       
      function getData(ackTaskId)
      
      { 
      	var url = timeURL('${ctx}/ack/resources/ajaxQuery.do?ackTaskId='+ackTaskId);
      	$.ajax({
      		url: url,
      		//data: "type=device"+"&devType="+type+"&date="+new Date(),
      		dataType: 'json',
      		success: function(data) {
         			dataMain.Rows = data.jsonStr;
         			loadData();
      		}
      	});        			
      };  
 
   
   
   function selectInd(){
      var devData = manager.getSelectedRows();
      $("#devJsonStrs").val(JSON.stringify(devData));
//       var indData = indManager.getSelectedRows();
//       $("#indJsonStrs").val(JSON.stringify(indData));
      if(devData==null||devData==""){
        $.ligerDialog.warn("请选择设备项!");
        return;
      }
         $("#formSave").submit();
      
   }        	
  	
  	
  	
  	
  	
    </script>
</head>
<body style="padding:0px; overflow:hidden;" onload="">
	<form id="formSave" modelAttribute="snmpRes" action="${ctx}/ack/taskMapRes/saveAckRes.do" method="post">
	 <input type="hidden" name="ackTaskId" id="ackTaskId" value="${id}"  />
	 <input  type="hidden" name="devJsonStrs" id="devJsonStrs" value=""  />
	</form>
	<div id="toptoolbar"></div> 
    <div id="maingrid"></div>
    
    <div id="indToptoolbar" style="display:none;">性能指标项列表</div> 
    <div id="indMaingrid" style="display:none;"></div>
    
</body>
</html>