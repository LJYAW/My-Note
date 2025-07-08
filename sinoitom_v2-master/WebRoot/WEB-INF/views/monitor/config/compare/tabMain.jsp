<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${s:paramVal("SystemName")}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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

<script type="text/javascript">
	var tab = null;
	var accordion = null;
	var tree = null;
	var layout = null;
	var objTypeID = "";
	var objTypeName = "";
	var selectRowData = "";
	var manager = null;
	
	$(function() {
	initDivHeight();//初始化div高度
        layout = $("#layout-index").ligerLayout({leftWidth : 200,space : 2,isLeftCollapse : false});
        var treeData = ${treeData};

		
    $("#resSnmpInd-tree").ligerTree({
		  onContextmenu: function (node, e)
            { 
                actionNodeID = node.data.text;
                menu.show({ top: e.pageY, left: e.pageX });
                return false;
            }, //右击事件
			 checkbox : false,
			slide : false,
			nodeWidth : 200, 
			data : treeData,
			idFieldName : 'id',
			parentIDFieldName : 'pid',
			onSelect : function(node) {//选择事件
				objTypeID = node.data.id;
				fleshShow(objTypeID);
			} 
		});
        manager = $("#resSnmpInd-tree").ligerGetTreeManager();
        window.onresize=function(){
            initDivHeight();
        }
		
	});
	function fleshShow(id) {
				$("#devTask_iframe").attr("src",'${ctx}/config/configFile/getMonDevTaskList.do?ids='+id);
	}
	  

	
	
	   function initDivHeight(){
   		var wh=windowHeight();
   		document.getElementById("tree-index").style.height = (wh-32)+"px";
    }
    function windowHeight() {
	    var de = document.documentElement;
	    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
	}
</script>
</head>
<body>
<div class="breadcrumbs ace-save-state" id="breadcrumbs">
	<ul class="breadcrumb">
		<li class="active">${titleContent }</li>
	</ul>
</div>
<%--<div id="toptoolbar" ></div>--%>
	<div id="layout-index" style="float:left;">
		<div position="left" id="tree-index" class="l-scroll l-accordion-content" title="">
			 <ul id="resSnmpInd-tree"></ul>
		</div>
		 <div position="center" id="index-center">
			<div id="devTask" title="任务设备" class="whFull">
					<iframe frameborder="0" id="devTask_iframe" src="" class="whFull"></iframe>
			</div>
		</div> 
	</div>
</body>
</html>
