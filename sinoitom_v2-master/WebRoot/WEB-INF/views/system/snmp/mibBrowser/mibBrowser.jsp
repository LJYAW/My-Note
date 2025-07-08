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
<title>Snmp工具</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/js/dataFormat.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/index.js" type="text/javascript"></script>
<script type="text/javascript">
	var searchMsg = null;
	var intvMsg = null;
	var dataMain = {};
	var gridMain;
    var selectRowData = null;
    var snmpSearchData = {};
//     var oid = '${oid}';
    
    var textSelectType='<div class=\"devices\" align=\"left\" style=\"width:100%;background-color: #CEDFEF;\"><span style=\"margin-left:-12px;\">设备IP：</span><input id=\"deviceIp\" type=\"text\" value=\"\"></div>';
//     var textButton='<input id=\"bnSave\" type=\"button\" value=\"选择设备\" onclick=\"sendform();\" class=\"l-button\">';
	$(function (){				
// 	 $(".devices").parent('span').parent('div').mouseover().removeClass("l-panel-btn-over");
		$("#emptyData").css("display","none");
		
	     $("#toptoolbar").ligerToolBar({
            items: [
            
                { text:textSelectType},
// 	        	{ text:textButton},
	        	{ line:true },
	        	{ text: '选择设备', click: selectDevice , icon : 'add'},
	        	{ line:true },
	        	{ text: 'SnmpGet', click: snmpGet , icon : 'refresh'},
	        	{ line:true },
	        	{ text: 'SnmpWalk', click: snmpWalk , icon : 'refresh'},
	        	{ line:true },
                { text: '刷新', click: refresh , icon : 'refresh'}
            ]
     	});
		dataMain.Rows ={};
		gridMain = $("#maingrid").ligerGrid({
                columns: 
                     [
                     { display:'', name:'sortKey',type:'float',align:'left',width:1,hide: true},
                     { display:'SnmpOID', name:'snmpKey',type:'string',align:'left',width:200},
		             { display:'结果', name:'snmpVal',type:'string',align:'left',width:800,height:'auto'}
               	],
               	data: dataMain,
				alternatingRow: 'true',
                width: '100%',
                height:'100%',
                usePager:false,
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                }
            });
	});
	
	function snmpGet() {
		
		if(!$("#taMsg").hasClass("dN")){
		   $("#trMsg").addClass("dN");
		}
	
		var oid = $("#oid").val();
		var devIp = $("#deviceIp").val();
		
		if(oid=='' || oid==undefined){
			$.ligerDialog.warn('oid为空，请在左侧树中选择一个节点！');
			return;
		}
		
		if(devIp=='' || devIp==undefined){
			$.ligerDialog.warn('设备ip为空，请点击选择设备！');
			return ;
		}
		
		var snmpComStr =snmpSearchData.ro_community_string;
		var snmpOid  =oid;
		var snmpVersion=snmpSearchData.snmp_version;
		var timeout=3;
		var snmpPort=161;
		 $("#emptyData").css("display","block");
		$.ajax({
			url: timeURL("${ctx}/system/util/snmpGet.do"),
			data: "snmpVersion="+snmpVersion+"&timeout="+timeout+"&snmpPort="+snmpPort+"&devIp="+devIp+"&snmpComStr="+snmpComStr+"&snmpOid="+snmpOid+"&date="+new Date(),
			dataType: 'json',
			success: function(data) {
				$("#emptyData").css("display","none");
				if( data.listSnmp!=null&&data.listSnmp!='' ){
					dataMain.Rows = data.listSnmp;
					$("#trMsg").removeClass("dN");
           			loadData();
        		}
        		else{
           			$.ligerDialog.warn(data.result);
//            			refresh();
        		}
			}
		});        			
	}
	
	function snmpWalk(){
		if(!$("#taMsg").hasClass("dN")){
		   $("#trMsg").addClass("dN");
		}
		var oid = $("#oid").val();
		var devIp = $("#deviceIp").val();
		
		if(oid=='' || oid==undefined){
			$.ligerDialog.warn('oid为空，请在左侧树中选择一个节点！');
			return;
		}
		
		if(devIp=='' || devIp==undefined){
			$.ligerDialog.warn('设备ip为空，请点击选择设备！');
			return ;
		}
		
		var snmpComStr =snmpSearchData.ro_community_string;
		var snmpOid  =oid;
		var snmpVersion=snmpSearchData.snmp_version;
		var timeout=3;
		var snmpPort=161;
		 $("#emptyData").css("display","block");
		$.ajax({
			url: timeURL("${ctx}/system/util/snmpWalk.do"),
			data: "snmpVersion="+snmpVersion+"&timeout="+timeout+"&snmpPort="+snmpPort+"&devIp="+devIp+"&snmpComStr="+snmpComStr+"&snmpOid="+snmpOid+"&date="+new Date(),
			dataType: 'json',
			success: function(data) {
			$("#emptyData").css("display","none");
				if( data.listSnmp!=null&&data.listSnmp!='' ){
					dataMain.Rows = data.listSnmp;
        			//$("#taMsg").text(data.message); 
            		$("#trMsg").removeClass("dN");
            		loadData();
        		}
        		else{
           			$.ligerDialog.warn(data.result);
//            			$("#bnSearch").removeAttr("disabled"); 
// 					refresh(); 
        		}
			}
		});        			
	}
	
	function loadData(){
            selectRowData = null;
            gridMain.loadData();
    }
    
    function selectDevice(){
      	var url = '${ctx}/snmp/mibNode/selectDevice.do';
     	showDlg('设备Snmp信息', 750, 400, url);     
	}
	
	function refresh(){
			    window.location.reload();
				return false;
			 } 
	function showDeviceIP(data){
		var snmpSearch = JSON.parse(data);
	  	$("#deviceIp").val(snmpSearch.ip_address);
	  	snmpSearchData = snmpSearch;
	}		 
</script>

</head>
<body> 
 <form style="padding:0px;" id="formSearch" action="${ctx}/fas/res/net/switch/search.do" method="post">
<input type="hidden" name="oid" id="oid" value=""/>
</form> 
    <div id="toptoolbar"></div> 
<!--     <div id="maingrid"></div> -->
    <table align="center" style="width:800px">
			<tr id="trMsg" class="dN">
            	<td>
            	<!--  
            	<textarea id="taMsg" readonly="readonly" cols="120" rows="15" class="l-textarea" style="width:550px"></textarea>
            	-->
            	<div id="taMsg" >
            		<div id="maingrid" style="clear:both"></div>
            	</div>
           		</td>
            </tr>
		</table> 
		<div  id="emptyData" align="center">
		 <img style="margin: 50px;" src="${ctx}/img/loading.gif"/>
  		</div>
</body>
</html>
