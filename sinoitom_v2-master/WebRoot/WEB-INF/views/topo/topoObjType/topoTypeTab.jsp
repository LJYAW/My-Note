<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${s:paramVal("SystemName")}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
	<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/index1.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/definedBar.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>

<link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>

<script src="${ctx}/static/jquery/jquery-1.8.3.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js"
	type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/mainTab.js" type="text/javascript"></script>
<style type="text/css"> 
        .l-tree .l-tree-icon-none img{width:16px; height:16px; margin:3px;}
</style>

<script type="text/javascript">
	var tab = null;
	var accordion = null;
	var tree = null;
	var layout = null;
	var objTypeID = "";
	var selectRowData = "";
	var manager = null;
	//baseUrl = '${ctx}/meter';//
	$(function() {
	initDivHeight();//初始化div高度
		layout = $("#layout-index").ligerLayout({leftWidth : 200,space : 2,isLeftCollapse : false});
		var treeData = ${treeData};
// 		alert(treeData)s
		
    $("#resSnmpInd-tree").ligerTree({
		  onContextmenu: function (node, e)
            { 
                actionNodeID = node.data.text;
                menu.show({ top: e.pageY, left: e.pageX });
                return false;
            }, //右击事件
			 checkbox : false,
			slide : false,
			nodeWidth : 100, 
			data : treeData,
			idFieldName : 'id',
			parentIDFieldName : 'pid',
			onSelect : function(node) {//选择事件
				objTypeID = node.data.id;
				
				fleshShow(objTypeID);
			} 
		});
		
	});
	function fleshShow(id) {
				$("#showtopoType_iframe").attr("src",'${ctx}/topo/objType/getObjType.do?id='+id);
	}
	 function saveOK(action, id,addSuccess) {
		 if (addSuccess == null || addSuccess == "") {
			fleshShow(objTypeID);
		} else {
			window.location.href = "${ctx}/topo/objType/getTree.do";
		}
	} 

	function refreshs() {
		window.location.href = "${ctx}/topo/objType/getTree.do";
	}
	
	   function initDivHeight(){
   		var wh=windowHeight();
   		document.getElementById("tree-index").style.height = (wh-25)+"px";
    }
    function windowHeight() {
	    var de = document.documentElement;
	    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
	}
</script>
</head>

<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li class="active">${titleContent }</li>
    </ul>
</div>
<body>
	<div id="layout-index" style="float:left;">
		<div position="left" id="tree-index" class="l-scroll l-accordion-content" style="border:1px solid #ccc; overflow:auto; clear:both;" title="资源树">
				
			 <ul id="resSnmpInd-tree">拓扑菜单</ul>
		</div>
		
		
		 <div position="center" id="index-center">
			<div id="showtopoType" title="拓扑元素" class="whFull">
					<iframe frameborder="0" id="showtopoType_iframe" src="" class="whFull"></iframe>
			</div>
		</div> 
	</div>
</body>
</html>
