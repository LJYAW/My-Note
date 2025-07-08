<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>操作命令维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/picdisplay.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/js/plugin/calendar/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/js/plugin/calendar/calendar.js"></script>
<script type="text/javascript" src="${ctx}/js/plugin/calendar/calendar-en.js"></script>
<script type="text/javascript" src="${ctx}/js/plugin/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="${ctx}/js/plugin/calendar/datePatternCheck.js"></script>

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/timeControl.js" type="text/javascript"></script>
<script src="${ctx}/js/timePeriod.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var navtab =null;
    	var biztab = null;
    	var selTab="tabitem1";
    	var initType="operation";
        $(function ()
        {
        	var initQueryType="#"+initType;
        	$(initQueryType).attr("lselected","true");
            biztab =$("#cmdbTab").ligerTab({
            dblClickToClose: false,
            onAfterSelectTabItem: function (tabid)
	            {
	            
	              if(tabid=="tabitem1"){
	                    selTab="tabitem1";
	               		 $("#operation_iframe").attr("src", '${ctx}/cmdb/operation/main.do');    
	               }
	               else if(tabid=="tabitem2"){
	                    selTab="tabitem2";
	               		 $("#devOperation_iframe").attr("src", '${ctx}/cmdb/devOperation/getTree.do');    
	               }
	               else if(tabid=="tabitem4"){
	                    selTab="tabitem4";
	                     $("#operationCmd_iframe").attr("src",'${ctx}/cmdb/devOpCmd/getTree.do');    
	               }
	               else if(tabid=="tabitem3"){
	                    selTab="tabitem3";
	                     $("#snmpoid_iframe").attr("src",'${ctx}/cmdb/devOpOID/getTree.do');    
	               }
	            }
	            			
            });

			$("#operation_iframe").attr("src", '${ctx}/cmdb/operation/main.do');    
        });
        
		function windowHeight() {
		    var de = document.documentElement;
		    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
		}
		$(document).ready(window.onresize=function(){
				var wh=windowHeight();
				document.getElementById("operation").style.height = (wh-30)+"px";
				document.getElementById("devOperation").style.height = (wh-30)+"px";
				document.getElementById("operationCmd").style.height = (wh-30)+"px";
				document.getElementById("snmpoid_iframe").style.height = (wh-30)+"px";
		});
		

		function saveOK(action, data){
		if(selTab=="tabitem1"){
		   var editor = document.getElementById("operation_iframe").contentWindow;
 			editor.saveOK(action, data); 
		}
		else if(selTab=="tabitem2"){
		   var editor = document.getElementById("devOperation_iframe").contentWindow;
 			editor.saveOK(action, data); 
		}else if(selTab=="tabitem3"){
		   var editor = document.getElementById("snmpoid_iframe").contentWindow;
 			editor.saveOK(); 
		}
		else if(selTab=="tabitem4"){
		    var editor = document.getElementById("operationCmd_iframe").contentWindow;
 			editor.saveOK(); 
		}
		
		
       }
		

    </script>
</head>
<body style="padding:0px; overflow:hidden;">
<form name="sysForm" id="sysForm" >
<input type="hidden" name="queryType" id="queryType" value=""/>
	<div id="cmdbTab" style="width:99%;overflow:hidden; border:1px solid #A3C0E8; ">
		<div id="operation" name="operation"  title="操作定义"  >
		  <iframe frameborder="0" id="operation_iframe" src="" class="whFull"></iframe>
		</div>
		<div id="devOperation" name="devOperation"  title="设备操作"  >
		  <iframe frameborder="0" id="devOperation_iframe" src="" class="whFull"></iframe>
		</div>
		<div id="devOperation" name="snmpoid"  title="设备操作OID"  >
		  <iframe frameborder="0" id="snmpoid_iframe" src="" class="whFull"></iframe>
		</div>
		<div id ="operationCmd" name="operationCmd" title="设备操作命令" >
		 <iframe frameborder="0" id="operationCmd_iframe" src="" class="whFull"></iframe>
		</div>
		
	</div>
</form>
</body>
</html>