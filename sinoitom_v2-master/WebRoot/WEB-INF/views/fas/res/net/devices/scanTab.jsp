<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>设备搜索</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/picdisplay.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/js/plugin/calendar/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css" />



<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/timeControl.js" type="text/javascript"></script>
<script src="${ctx}/js/timePeriod.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var navtab =null;
    	var initType="";
        $(function ()
        {
        	initType="${queryType}";
        	var initQueryType="#"+initType;
        	$(initQueryType).attr("lselected","true");
            $("#scanTab").ligerTab({
            dblClickToClose: false,
            onAfterSelectTabItem: function (tabid)
	            {
	              if(tabid=="tabitem1"){
	                   $("#scanTab").ligerGetTabManager().overrideSelectedTabItem({showClose:false,text:'单点搜索', url: '${ctx}/fas/res/net/devices/scan.do' });
	                   $("#queryType").val("scan");
	               }
	               else if(tabid=="tabitem2"){
	               		$("#scanTab").ligerGetTabManager().overrideSelectedTabItem({showClose:false,text:'范围搜索', url: '${ctx}/fas/res/net/devices/scanScope.do' });
	               		$("#queryType").val("scanScope");
	               }
	               
	            }
            });
            if(initType=="scan"){
            	$("#scanTab").ligerGetTabManager().overrideSelectedTabItem({showClose:false,text:'单点搜索', url: '${ctx}/fas/res/net/devices/scan.do' });
            }
            else if(initType=="scanScope"){
            	$("#scanTab").ligerGetTabManager().overrideSelectedTabItem({showClose:false,text:'范围搜索', url: '${ctx}/fas/res/net/devices/scanScope.do' });
            }
        	
        });
        
		function windowHeight() {
		    var de = document.documentElement;
		    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
		}
		$(document).ready(window.onresize=function(){
				var wh=windowHeight();
				document.getElementById("scan").style.height = (wh-32)+"px";
				document.getElementById("scanScope").style.height = (wh-32)+"px";
		});
    </script>
</head>
<body style="padding:0px; overflow:hidden;">
<form name="sysForm" id="sysForm" >
<input type="hidden" name="queryType" id="queryType" value=""/>
	<div id="scanTab" style="width:99%;overflow:hidden; border:1px solid #A3C0E8; ">
		<div id ="scan" title="单点搜索" >
		</div>
		<div id="scanScope"  title="范围搜索"  >
		</div>
	</div>
</form>
</body>
</html>