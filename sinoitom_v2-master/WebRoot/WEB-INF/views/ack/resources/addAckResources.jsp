<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加巡检设备</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/index.js" type="text/javascript"></script>
<script src="${ctx}/static/formatter.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var dataMain = {};
    	var hostDataMain = {};
    	var gridMain;
    	var hostGridMain;
        var selectRowData = null;
    	var manager;
    	var hostManager;
    	var result='${result}';
    	if(result=="success"){
    	$.ligerDialog.success("监测设备添加成功!");
    	  window.parent.saveOK();
    	}
    	
        $(function ()
        {	
        	$("#toptoolbar").ligerToolBar({ 
				items: 
            		[
                	{ text:'保存监测设备', click: selectInd, icon:'add' },
                	{ line:true },
                	{ text:'刷新', click: onRefresh, icon:'refresh' }
                	]
            });
            dataMain.Rows =null;
            manager=gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'<span style="font-weight:bold; ">机构名称</span>', name:'orgName', width:80,align:'center',type:'text' },
                    { display:'<span style="font-weight:bold; ">设备厂商</span>', name:'vendorName', width:80,align:'center',type:'text' },
                    { display:'<span style="font-weight:bold; ">设备IP</span>', name:'resIP', width:100,align:'left',type:'text'},
                    { display:'<span style="font-weight:bold; ">设备类型</span>', name:'resTypeName', width:80,align:'center',type:'text'},
                    { display:'<span style="font-weight:bold; ">设备型号</span>', name:'resModel', width:100,align:'left',type:'text'},
                    { display:'<span style="font-weight:bold; ">型号OID</span>', name:'modelOID', width:150,align:'left',type:'text'}
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
            
               hostGridMain = $("#hostGrid").ligerGrid({
                columns: 
                    [
                    { display:'机构名称', name:'orgName', width:100, align:'left',type:'text' },
                    { display:'设备类型', name:'ipHostType', width:90, align:'center',type:'text' },
                    { display:'设备名称', name:'hostName', width:100, align:'left' },
                    { display:'IP地址', name:'ipAddr', width:110, align:'left',type:'text' },
                    { display:'MAC地址', name:'macAddr', width:120, align:'left',type:'text' },
                    { display:'接入交换机', name:'switchIpLong', width:110, align:'left' ,
                     render:function(row)
                     {
                       return row.switchIp;
                     }
                    },
                    { display:'交换机接口', name:'ipIndex', width:80, align:'left',
                    	render: function (row)
						 	{
						 	    return row.switchIfIndex;
						 	}
                    },
                    { display:'Vlan', name:'vlanId', width:70, align:'right',type:'text' },
                    { display:'接口状态',name:'adminStatus', width:110,
                     render: function (row)
	                        {
	                        	var html='';
	                        	if(row.adminStatus=='up'){
	                        		html = '<span style=\"line-height:22px;color:green;\">'+row.adminStatus+'</span>';
	                        	}else{
	                        		html = '<span style=\"line-height:22px;color:red;\">'+row.adminStatus+'</span>';
	                        	}
	                        	if(row.adminStatus!=''&&row.operStatus!='')
	                        	html = html+'/';
	                        	if(row.operStatus=='up'){
	                        		html =html +'<span style=\"line-height:22px;color:green;\">'+row.operStatus+'</span>';
	                        	}else{
	                        		html =html +'<span style=\"line-height:22px;color:red;\">'+row.operStatus+'</span>';
	                        	}
	                            return html;
	                        }
                    }
              
               		], 
               		data: hostDataMain,
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
            
            changeClass();
            });
            
      
      function onRefresh(){
        	swapDevType();
      }
      
        
      function loadData(){
            selectRowData = null;
            gridMain.loadData();
           
        }
        
        function loadHostData(){
            hostGridMain.loadData();
           
        }
        
       
function refreshVendor(devType) {
        $("#vendorID").empty();
       
         var classCode=$("#prodClassCode").val();
        $.getJSON(
            "${ctx}/ack/resources/getVendorData.do?devType="+devType+"&classCode="+classCode,
            function (result) {
                if (result.vendors != null) {
                    for (var i = 0; i < result.vendors.length; i++) {       	                  
                        var vendor = result.vendors[i]; 
                         $("#vendorID").append("<option  value=\"" + vendor.vendorID + "\">" + vendor.dispName + "</option>");
                    }
                     getListData();
                }
            });
  	} 
 
function swapDevType(){
       		var devType=$("#devType").val();
       		 var devType=$("#devType").find("option:selected").val();
       		refreshVendor(devType);
       		$("#resTypeCode").val(devType);
       }
       
 function changeDevType2(){
   
   var devType=$("#devType2").val();
   $("#resTypeCode").val(devType);
   getHostData(devType);
   
 }       
function getListData(){
  var classCode=$("#prodClassCode").find("option:selected").val();
  var classCodeName=$("#prodClassCode").find("option:selected").text();
  
  var devType=$("#devType").find("option:selected").val();
  var devTypeName=$("#devType").find("option:selected").text();
  var vendorId=$("#vendorID").find("option:selected").val();
  
  if(classCode!="11"){
    getData(devType,devTypeName,vendorId,classCode,classCodeName);
  }
  $("#resClassCode").val(classCode);
  $("#resClassName").val(classCodeName);
  $("#resTypeCode").val(devType);
  $("#resTypeName").val(devTypeName);
  
}  

function getHostListData(){
  var classCode=$("#prodClassCode").find("option:selected").val();
  var classCodeName=$("#prodClassCode").find("option:selected").text();
  
  var devType=$("#devType2").find("option:selected").val();
  var devTypeName=$("#devType2").find("option:selected").text();
  if(classCode=="11"){
    getHostData(devType);
  }
  $("#resClassCode").val(classCode);
  $("#resClassName").val(classCodeName);
  $("#resTypeCode").val(devType);
  $("#resTypeName").val(devTypeName);
  
}     

  function getData(type,devTypeName,vendorId,classCode,classCodeName)
   
        { 
 	document.getElementById("maingrid").style.display="block";
  	document.getElementById("hostGrid").style.display="none";
            
        	var url = timeURL('${ctx}/ack/resources/ajaxQuery.do?devType='+type+'&devTypeName='+devTypeName+'&vendorID='+vendorId+'&classCode='+classCode+'&classCodeName='+classCodeName);
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
           			dataMain.Rows = data.jsonStr;
           			loadData();
        		}
        	});        			
        };  
  function getHostData(devType)
       
        { 
     document.getElementById("maingrid").style.display="none";
  	document.getElementById("hostGrid").style.display="block";
        	var url = timeURL('${ctx}/fas/res/host/ipHost/getHostInfos.do?typeCode='+devType);
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
           			hostDataMain.Rows = data;
           			loadHostData();
        		}
        	});        			
        };        
   
   
   function selectInd(){
   
      var classCode=$("#prodClassCode").find("option:selected").val();
     
      if(classCode=="11"){
          var hostData = hostGridMain.getSelectedRows();
	      $("#hostJsonStrs").val(JSON.stringify(hostData));
	      if(hostData==null||hostData==""){
	        $.ligerDialog.warn("请选择设备项!");
	        return;
	      }
        
      }else{
          var devData = gridMain.getSelectedRows();
	      $("#devJsonStrs").val(JSON.stringify(devData));
	      if(devData==null||devData==""){
	        $.ligerDialog.warn("请选择设备项!");
	        return;
	      }
      }
        $("#formSave").submit();
      
   }        	
  	
  	
  	
  	function changeClass(){
  	 var classCode=$("#prodClassCode").find("option:selected").val();
  	 var resName="";
  	if(classCode=="11"){
  	   resName="IT_HostType";
  	}else if(classCode=="12"){
  	  resName="IT_StorageType";
  	}else if(classCode=="13"){
  	  resName="IT_NetDevType";
  	}else if(classCode=="14"){
  	  resName="IT_PeripheralType";
  	}else if(classCode=="21"){
  	  resName="IT_OSType";
  	}else if(classCode=="22"){
  	  resName="IT_SystemType";
  	}else if(classCode=="23"){
  	  resName="IT_DBType";
  	}else if(classCode=="24"){
  	  resName="IT_MiddleWareType";
  	}else if(classCode=="25"){
  	  resName="IT_AppType";
  	}
  	
  	
  	if(classCode=="11"){
  	    $("#v1").hide();
  	    $("#v2").hide();
  	    $("#v3").hide();
  	    
  	    $("#dev1").hide();
  	    $("#dev2").show();
  	    
  	    
  	}else{
  	    $("#v1").show();
  	    $("#v2").show();
  	    $("#v3").show();
  	    
  	    $("#dev1").show();
  	    $("#dev2").hide();
  	}
  	
  	$.getJSON(
               "${ctx}/cmdb/prodType/getResItemByCode.do?groupCode=" + resName,
               function (result) {
                   if (result.pItems != null) {
                       $("#devType").empty();
                       for (var i = 0; i < result.pItems.length; i++) {
                       		var item=result.pItems[i];
                              $("#devType").append("<option  value=\"" + item.id+"\" >" + item.text + "</option>");
                       }
                       swapDevType();
                   }
                   
               });
  	
  	}      
    </script>
</head>
<body style="padding:0px; overflow:hidden;" >
	<form id="formSave" action="${ctx}/ack/resources/saveAckResources.do" method="post">
	<input  type="hidden" name="devJsonStrs" id="devJsonStrs" value=""  />
	<input  type="hidden" name="hostJsonStrs" id="hostJsonStrs" value=""  />
	<input  type="hidden" name="resClassCode" id="resClassCode" value=""  />
	<input  type="hidden" name="resClassName" id="resClassName" value=""  />
	<input  type="hidden" name="resTypeCode" id="resTypeCode" value=""  />
	<input  type="hidden" name="resTypeName" id="resTypeName" value=""  />
		<div  style="height:58px;width:100%;background:#D6E3EF">
			<table style="height:58px;MARGIN-LEFT: 20px">
		      <tr>
		       
		      </tr>
		      
		      <tr>
		        <td align="right">设备分类 ：</td>
                <td>
                  <s:resSelect id="prodClassCode" name="prodClassCode" value="13" code="IT_ResClass"  ltype="select" style="width:100px" onchange="changeClass();getHostListData()"/>
                </td>
                <td style="width:30px"></td>
                <td>设备类型：</td> 
				<td id="dev1">
					<select onchange="swapDevType();" id="devType" name="devType" style="width:100px">
					</select>
				</td>
				<td id="dev2">
					<s:resSelect id="devType2" name="devType2" value="3" code="IpHostType"  ltype="select" style="width:100px" onchange="changeDevType2();"/>
				</td>
				<td id="v1" style="width:30px"></td>
				<td id="v2">设备厂商：</td>
                <td id="v3">
                  <select id="vendorID" name="vendorID" style="width:100px" onchange="getListData()" >
                  </select>
                </td>
                <td></td>
		      </tr>
		</table>
		</div>
	</form>
	<div id="toptoolbar"></div> 
    <div id="maingrid"></div>
    
	<div id="hostGrid" style="display:none;"> </div>
	
	
	
    
</body>
</html>