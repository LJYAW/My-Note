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

<script type="text/javascript" src="${ctx}/js/plugin/calendar/calendar.js"></script>
<script type="text/javascript" src="${ctx}/js/plugin/calendar/calendar-en.js"></script>
<script type="text/javascript" src="${ctx}/js/plugin/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="${ctx}/js/plugin/calendar/datePatternCheck.js"></script>

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script type="text/javascript">
	var tab = null;
	var accordion = null;
	var tree = null;
	var layout = null;
	var productID = "";
	var selectRowData = "";
	var manager = null;
	//baseUrl = '${ctx}/meter';//
	$(function() {
	initDivHeight();//初始化div高度
		layout = $("#layout-index").ligerLayout({leftWidth : 200,space : 2,isLeftCollapse : false});
		var treeData = ${treeData};
		//alert(treeData);
		$("#resSnmpInd-tree").ligerTree({
		 onContextmenu: function (node, e)
            { 
                actionNodeID = node.data.text;
                menu.show({ top: e.pageY, left: e.pageX });
                return false;
            },
			checkbox : false,
			slide : false,
			nodeWidth : 100,
			data : treeData,
			idFieldName : 'id',
			parentIDFieldName : 'pid',
			onSelect : function(node) {
				productID = node.data.id;
				fleshShow(productID);
			}
		});
		manager = $("#resSnmpInd-tree").ligerGetTreeManager();
		window.onresize=function(){
        initDivHeight();
	}
	});
	function fleshShow(id) {
				$("#resSnmpInd_iframe").attr("src",'${ctx}/cmdb/prodClass/main.do?id=' + id);
	}
	function saveOK(action, id,addSuccess) {
		 if (addSuccess == null || addSuccess == "") {
			fleshShow(productID);
		} else {
			window.location.href = "${ctx}/cmdb/prodClass/getTree.do";
		}
	}
// 	function getChecked() {
// 		var url = timeURL('${ctx}/meter/productType.do');
// 		window.parent.showDlg('添加设备类型', 750, 450, url);
// 	}
	function refreshs() {
		window.location.href = "${ctx}/cmdb/prodClass/getTree.do";
	}
	
	   function initDivHeight(){
   		var wh=windowHeight();
   		document.getElementById("tree-index").style.height = (wh-15)+"px";
    }
    function windowHeight() {
	    var de = document.documentElement;
	    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
	}
</script>
</head>
<body>
<div id="toptoolbar" ></div>
	<div id="layout-index" style="float:left;">
		<div position="left" id="tree-index" class="l-scroll l-accordion-content" title="<table><tr style='line-height: 22px;cursor: pointer;'>
				<td onclick='refreshs()'><img src='${ctx}/static/ligerUI/skins/icons/refresh.gif'/><span>刷新</span></td>
				</tr></table>" style="border:1px solid #ccc; overflow:auto; clear:both;">
			 <ul id="resSnmpInd-tree">监测指标</ul>
		</div>
		<div position="center" id="index-center">
			<div id="resSnmpInd" title="监测指标" class="whFull">
					<iframe frameborder="0" id="resSnmpInd_iframe" src="" class="whFull"></iframe>
			</div>
		</div>
	</div>
</body>
</html>
