<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>关联监测指标对象</title>
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
    	//var threshData=${jsonThresh};
    	var result='${result}';
    	if(result=="success"){
//     	window.parent.success("监测设备指标添加成功!");
    	 window.parent.parent.saveOK();
    	  window.parent.closeDlg();
    	}
    	
        $(function ()
        {	
        	
            
            
            $("#indToptoolbar").ligerToolBar({ 
				items: 
            		[
                	
                	{ text:'关联监测指标', click: selectInd, icon:'add' }
                	]
            });
            indDataMain.Rows =null;
            indManager=indGridMain = $("#indMaingrid").ligerGrid({
                columns: 
                    [
                    { display:'设备厂商', name:'vendorName', width:60, align:'center',type:'text'}, 
                    { display:'设备分类', name:'devClassName', width:60, align:'center',type:'text'}, 
                    { display:'设备类型', name:'devTypeName', width:60, align:'center',type:'text'}, 
                    { display:'设备型号', name:'prodModel', width:60, align:'center',type:'text'}, 
                    { display:'操作系统类型', name:'osType', width:80, align:'center',type:'text'}, 
                    { display:'软件版本', name:'osVersion', width:60, align:'center',type:'text'}, 
                    { display:'软件特征', name:'osFeature', width:60, align:'center',type:'text'}, 
                    { display:'指标组', name:'indGroupName', width:100, align:'center',type:'text'},
                    { display:'指标英文名称', name:'indItemEnName', width:80, align:'center',type:'text'},
           			{ display:'指标显示名称', name:'indItemName', width:80, align:'center' ,type:'text'},
            		{ display:'取值类型', name:'valueType', width:60, align:'center',type:'text'},
            		{ display:'长度', name:'length', width:40, align:'right',type:'int'},
            		{ display:'小数位数', name:'decimals', width:60, align:'right',type:'int'},
            		{ display:'度量单位', name:'measureUnit', width:60, align:'left',type:'text'},
            		{ display:'巡检命令ID', name:'prodChkCmdId', width:80, align:'left',type:'text'},
            		{ display:'巡检命令', name:'checkCmd', width:240, align:'left',type:'text'}
           			
           			
               	],   
				data: indDataMain,
				alternatingRow: 'true',
				usePager:false,
				checkbox:true, 
                width: '100%',
                height:'100%', 
//                 pageSize: '50',
//                 pageSizeOptions: [25, 50, 100,200],
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                } 
            });
            
           selectDevice();
            });
            
      
      function onRefresh(){
        	swapDevType();
      }
      
     
        
      function loadData(){
            selectRowData = null;
            gridMain.loadData();
           
        }
        
    
   function selectDevice(){
    document.getElementById("indToptoolbar").setAttribute("style","display")
    document.getElementById("indMaingrid").setAttribute("style","display");
   
//       var data = manager.getSelectedRows();
//       var vendorID=$("#vendorID").val();
 		$("#ids").val('${ids}');
 		$.ajax({
			              type: "POST",
			              dataType: 'json',
			              url: "${ctx}/cmdb/prodCmdCheckItems/getListByResIDs.do",
			              contentType: "application/x-www-form-urlencoded; charset=utf-8",
			              data: $("#resource").serialize(),
			              success: function (data) {
			                if(data!=null){
			           			indDataMain.Rows=data.jsonStr;
			           			
           						 indGridMain.loadData();
				      		}
			              },
			          });
	
   }   
   
   function selectInd(){
    
    
    $("#resIds").val('${ids}');
      var indData = indManager.getSelectedRows();
      $("#indJsonStrs").val(JSON.stringify(indData));
      
      if(indData==null||indData==""){
         $.ligerDialog.warn("请选择监测指标项");
         return;
      }
      
      if(indData!=null&&indData!=""){
         $("#formSave").submit();
      }
      
   }        	
  	      
    </script>
</head>
<body style="padding:0px; overflow:hidden;" onload="">
<form id="resource" name="resource" action="" style="display:none" method="post" enctype="multipart/form-data" target="hidden_frame">
  		<input name="ids" id="ids" value=""  />
  		<input name="vendorID" id="vendorID" value="${vendorID }"  />
  		<input name="resTypeCode" id="resTypeCode" value="${resTypeCode }"  />
  		<input name="osType" id="osType" value="${osType }"  />
  		<input name="osVersion" id="osVersion" value="${osVersion }"  />
  		<input name="osFeature" id="osFeature" value="${ osFeature}"  />
  		
<!--   		<input name="accessMode" id="accessMode" value=""  /> -->
</form>
<form id="formSave" modelAttribute="snmpRes" action="${ctx}/ack/devCheckItems/saveDevCheckItems.do" method="post">
 <input type="hidden" name="resIds" id="resIds" value=""  />
 <input  type="hidden" name="indJsonStrs" id="indJsonStrs" value=""  />
</form>
	<div id="toptoolbar"></div> 
    <div id="maingrid"></div>
    
    <div id="indToptoolbar" style="display:none;"></div> 
    <div id="indMaingrid" style="display:none;"></div>
    
	<div style="display:none;">
	
	
	
    </div>
</body>
</html>