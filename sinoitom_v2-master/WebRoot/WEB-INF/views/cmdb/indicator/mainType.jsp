<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>设备搜索</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css" />

<script src="${ctx }/static/assets/js/jquery.min.js"></script>
<script src="${ctx }/static/assets/js/layer.js"></script>
<script src="${ctx }/static/assets/js/H-ui.admin.js"></script>

<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
<!-- page specific plugin scripts -->
<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
<script src="${ctx }/static/assets/js/grid.locale-en.js"></script>
<script src="${ctx }/static/assets/js/resize.js"></script>
<script src="${ctx }/static/assets/js/bootbox.js"></script>







<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/picdisplay.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/js/plugin/calendar/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css" />

	<link
		href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css"
		rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

	<link href="${ctx}/css/index1.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>


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
    	var initType="indItemEventTypeMap";
        $(function ()
        {
        	//initType="${queryType}";
        	var initQueryType="#"+initType;
        	$(initQueryType).attr("lselected","true");
            biztab =$("#cmdbTab").ligerTab({
            dblClickToClose: false,
            onAfterSelectTabItem: function (tabid)
	            {
	            
	            if(tabid=="tabitem1"){
	                    selTab="tabitem1";
	                     $("#indItemEventTypeMap_iframe").attr("src",'${ctx}/cmdb/eventTypeMap/getTree.do');    
	               }
	            }
	            			
            });

			$("#indItemEventTypeMap_iframe").attr("src",'${ctx}/cmdb/eventTypeMap/getTree.do');        
        });
        
		function windowHeight() {
		    var de = document.documentElement;
		    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
		}
		$(document).ready(window.onresize=function(){
				var wh=windowHeight();
				document.getElementById("indItemEventTypeMap").style.height = (wh-32)+"px";
			
		});
		

		function saveOK(action, data){
		if(selTab=="tabitem1"){
			  var editor = document.getElementById("indItemEventTypeMap_iframe").contentWindow;
	 			editor.saveOK(); 
			}
		
		
       }
		

    </script>
</head>
<body style="padding:0px; overflow:hidden;">
<form name="sysForm" id="sysForm" >
<input type="hidden" name="queryType" id="queryType" value=""/>

<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li class="active">${titleContent }</li>
    </ul>
	</div>
	
	<div id="cmdbTab" style="width:99%;overflow:hidden; border:1px solid #A3C0E8; ">
		<div id ="indItemEventTypeMap" name="indItemEventTypeMap" title="指标事件类型" >
		 <iframe frameborder="0" id="indItemEventTypeMap_iframe" src="" class="whFull"></iframe>
		</div>
	</div>
</form>
</body>
</html>