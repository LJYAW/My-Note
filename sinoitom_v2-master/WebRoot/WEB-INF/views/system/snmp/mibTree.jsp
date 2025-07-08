<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>MIB树</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


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
<script src="${ctx}/js/timeControl.js" type="text/javascript"></script>
<script src="${ctx}/js/timePeriod.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var navtab =null;
    	var biztab = null;
    	var selTab='tabitem1';
    	var initType = "vendor";
    	var manager = null;
		var id="";
		var clickHistory=new Array();
        $(function ()
        {
	        initDivHeight();//初始化div高度
		 	layout = $("#layout-index").ligerLayout({leftWidth:200, space:2,isLeftCollapse:false});
		 	var treeData=${treeData};
		    $("#vendor-tree").ligerTree({
			    checkbox: false,
			    slide: true,
			    childIcon:'folder',
			    btnClickToToggleOnly: true,
			    nodeWidth: 300,
			    data: treeData,
			    idFieldName: 'id',
			    parentIDFieldName: 'pid',
			    onSelect: function(node) {
			        var oid = node.data.oid;
			        $("#mibbrowser_iframe").contents().find("#oid").val(oid);   
			        fleshShow(oid, initType);
			        onSelect(node);
			    }
			});
			
			 manager = $("#vendor-tree").ligerGetTreeManager();
		
        	var initQueryType="#"+initType;
        	$(initQueryType).attr("lselected","true");
            biztab =$("#MibTab").ligerTab({
            dblClickToClose: false,
            onAfterSelectTabItem: function (tabid)
	            {
	               if(tabid=="tabitem1"){
	               	  initType="mibbrowser";   
	              	  selTab="tabitem1";
	               	 $("#mibbrowser_iframe").attr("src", '${ctx}/snmp/mibNode/mibBrowser.do');  
	               }else if(tabid=="tabitem2"){
	               	  initType="mibFile";  
	               	  selTab="tabitem2";
	                 $("#mibFile_iframe").attr("src",'${ctx}/snmp/mibNode/mibFile.do');               
	               }
	            }
            });
		 $("#mibbrowser_iframe").attr("src", '${ctx}/snmp/mibNode/mibBrowser.do');  
        });
        
		function windowHeight() {
		    var de = document.documentElement;
		    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
		}
		$(document).ready(window.onresize=function(){
				var wh=windowHeight();
				document.getElementById("mibbrowser").style.height = (wh-32)+"px";
				document.getElementById("mibFile").style.height = (wh-32)+"px";
			
		});
		
		function saveOK(action, data){
		if(selTab=="tabitem1"){
		   var editor = document.getElementById("mibbrowser_iframe").contentWindow;
 			editor.saveOK(action, data); 
		}else if(selTab=="tabitem2"){
		    var editor = document.getElementById("mibFile_iframe").contentWindow;
 			editor.saveOK(action, data); 
 		}
		
       }
    
 function initDivHeight(){
   		var wh=windowHeight();
   		document.getElementById("tree-index").style.height = (wh-32)+"px";
   		//document.getElementById("prodClass").style.height = (wh-15)+"px";
   		
    }
	function windowHeight() {
	    var de = document.documentElement;
	    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
	}
	

 function fleshShow(oid,type){
 		
		if (type == "mibbrowser") {
			 $("#mibbrowser_iframe").attr("src", '${ctx}/snmp/mibNode/mibBrowser.do');      
		} else if (type == "mibFile") {
			 $("#mibFile_iframe").attr("src",'${ctx}/snmp/mibNode/mibFile.do'); 
		} 
 
   }
   
//   function saveOK(){
//          var tabid = navTab.getSelectedTabItemID();
// 			var ifwin = $('iframe[name="'+tabid+'"]')[0].contentWindow;
// 			ifwin.saveOK(action, data); 
// 		}

	function refreshs() {
		window.location.href = "${ctx}/snmp/mibNode/mibTree.do";
	}	
	
	function onSelect(node){
	   
	   if(!clickHistory.in_array(node.data.id)){
	      manager.loadData(node.target, "${ctx}/snmp/mibNode/mibTreeByPId.do?prentId="+ node.data.id);
	      clickHistory.push(node.data.id);
	   }
	  
	}
	
	Array.prototype.in_array = function (element) {
	　　for (var i = 0; i < this.length; i++) {
	　　　　if (this[i] == element) {
	　　　　　　return true;
	　　}
	　}
　　return false;
}

    </script>
</head>
<body style="padding:0px; overflow:hidden;">
<form name="sysForm" id="sysForm" >
<input type="hidden" name="id" id="id" value="${id}" /> 
<input type="hidden" name="queryType" id="queryType" value=""/>
<input type="hidden" name="initType" id="initType" value="${initType}" />

  <div id="layout-index">	

	<div position="left" id="tree-index" class="l-scroll l-accordion-content" style="height: 532px; display: block;" title="<table><tr style='line-height: 22px;'>
				<td onclick='refreshs()'><img src='${ctx}/static/ligerUI/skins/icons/refresh.gif'/><span>刷新</span></td>
				</tr></table>">
		<ul id="vendor-tree">MIB库</ul>
	</div>
	
	<div position="center" id="index-center">	
		<div id="MibTab" style="width:100%;overflow:hidden; border:1px solid #A3C0E8; ">
			<div id="mibbrowser" name="mibbrowser"  title="Mib Browser"  >
			  <iframe frameborder="0" id="mibbrowser_iframe" src="" class="whFull"></iframe>
			</div>
			
			<div id="mibFile" name="mibFile"  title="Mib文件解析"  >
			  <iframe frameborder="0" id="mibFile_iframe" src="" class="whFull"></iframe>
			</div>
				
		</div>
	</div>
</div>
</form>
</body>
</html>