<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>产品信息Tab页</title>
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
    	var selTab='tabitem1';
    	var initType = "vendor";
		var id="";
		
        $(function ()
        {
		
        	var initQueryType="#"+initType;
        	$(initQueryType).attr("lselected","true");
            biztab =$("#cmdbTab").ligerTab({
            dblClickToClose: false,
            onAfterSelectTabItem: function (tabid)
	            {
	               if(tabid=="tabitem1"){
	               	  initType="prodClass";  
	               	  selTab="tabitem1";
	                  $("#prodClass_iframe").attr("src",'${ctx}/cmdb/prodClass/getTree.do');
	               } else if(tabid=="tabitem2"){
	               	  initType="prodType";  
	               	  selTab="tabitem2";
	                  $("#prodType_iframe").attr("src",'${ctx}/cmdb/prodType/getTree.do');
	               }else if(tabid=="tabitem3"){
	                  initType="prodSeries";  
	                  selTab="tabitem3";
	                  $("#prodSeries_iframe").attr("src",'${ctx}/cmdb/vendorProdSeries/getTree.do');
	               }else if(tabid=="tabitem4"){
	                  initType="prodModel";  
	                  selTab="tabitem4";
	                  $("#prodModel_iframe").attr("src",'${ctx}/cmdb/vendorProdModel/getTree.do');
	               }
<%--	               else if(tabid=="tabitem5"){--%>
<%--	                  initType="prodChkCmds";  --%>
<%--	                  selTab="tabitem5";--%>
<%--	                  $("#prodChkCmds_iframe").attr("src",'${ctx}/cmdb/prodChkCmds/getTree.do');--%>
<%--	               }--%>
	            }
            });
		 $("#prodClass_iframe").attr("src", '${ctx}/cmdb/prodClass/getTree.do');
        });
        
		function windowHeight() {
		    var de = document.documentElement;
		    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
		}
		$(document).ready(window.onresize=function(){
				var wh=windowHeight();
				document.getElementById("prodClass").style.height = (wh-32)+"px";
				document.getElementById("prodType").style.height = (wh-32)+"px";
				document.getElementById("prodSeries").style.height = (wh-32)+"px";
				document.getElementById("prodModel").style.height = (wh-32)+"px";
<%--				document.getElementById("prodChkCmds").style.height = (wh-32)+"px";--%>

		});
		
		function saveOK(action, data){
		 if(selTab=="tabitem1"){
		    var editor = document.getElementById("prodClass_iframe").contentWindow;
 			editor.saveOK(action, data); 
		}else if(selTab=="tabitem2"){
		  var editor = document.getElementById("prodType_iframe").contentWindow;
 			editor.saveOK(action, data); 
		}else if(selTab=="tabitem3"){
		  var editor = document.getElementById("prodSeries_iframe").contentWindow;
 			editor.saveOK(action, data); 
		}else if(selTab=="tabitem4"){
		  var editor = document.getElementById("prodModel_iframe").contentWindow;
 			editor.saveOK(action, data); 
		}
<%--		else if(selTab=="tabitem5"){--%>
<%--		  var editor = document.getElementById("prodChkCmds_iframe").contentWindow;--%>
<%-- 			editor.saveOK(action, data); --%>
<%--		}--%>
		
		
       }
    
 function initDivHeight(){
   		var wh=windowHeight();
   		document.getElementById("prodClass").style.height = (wh-15)+"px";
   		
    }
	function windowHeight() {
	    var de = document.documentElement;
	    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
	}
	

 function fleshShow(id,type){
 		
		if (type == "vendor") {
			 $("#vendor_iframe").attr("src", '${ctx}/cmdb/vendor/main.do?id=' + id);      
		} else if (type == "prodClass") {
			 $("#prodClass_iframe").attr("src",'${ctx}/cmdb/prodClass/main.do?id=' + id); 
				
		} else if (type == "prodType") {
			 $("#prodType_iframe").attr("src",'${ctx}/cmdb/prodType/main.do?id=' + id); 
		}
		else if (type == "prodSeries") {
			 $("#prodSeries_iframe").attr("src",'${ctx}/cmdb/vendorProdSeries/main.do?id=' + id);
		}
		else if (type == "prodModel") {
			 $("#prodModel_iframe").attr("src",'${ctx}/cmdb/vendorProdModel/main.do?id=' + id);
		}
		else if (type == "osType") {
			 $("#osType_iframe").attr("src",'${ctx}/cmdb/ostype/main.do?id=' + id);
		}
		else if (type == "os") {
			 $("#os_iframe").attr("src",'${ctx}/cmdb/os/main.do?id=' + id);
		}
		else if (type == "osVersion") {
			 $("#osVersion_iframe").attr("src",'${ctx}/cmdb/vendorOSVersion/main.do?id=' + id);
		}
		else if (type == "vendorOID") {
			 $("#vendorOID_iframe").attr("src",'${ctx}/cmdb/vendorOID/main.do?id=' + id);
		}
<%--		else if (type == "ProdChkCmds") {--%>
<%--			 $("#prodChkCmds_iframe").attr("src",'${ctx}/cmdb/prodChkCmds/main.do?id=' + id);--%>
<%--		}--%>
 
   }
   
//   function saveOK(){
//          var tabid = navTab.getSelectedTabItemID();
// 			var ifwin = $('iframe[name="'+tabid+'"]')[0].contentWindow;
// 			ifwin.saveOK(action, data); 
// 		}

		

    </script>
</head>
<body style="padding:0px; overflow:hidden;">
<form name="sysForm" id="sysForm" >
<input type="hidden" name="id" id="id" value="${id}" /> 
<input type="hidden" name="queryType" id="queryType" value=""/>
<input type="hidden" name="initType" id="initType" value="${initType}" />

<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li class="active">${titleContent }</li>
    </ul>
	</div>

	<div position="center" id="index-center">	
		<div id="cmdbTab" style="width:100%;overflow:hidden; border:1px solid #A3C0E8; ">
			
			<div id ="prodClass" name="prodClass" title="产品分类" >
			 <iframe frameborder="0" id="prodClass_iframe" src="" class="whFull"></iframe>
			</div>
			
			<div id ="prodType" name="prodType" title="产品类型" >
			  <iframe frameborder="0" id="prodType_iframe" src="" class="whFull"></iframe>
			</div>
			
			<div id ="prodSeries" name="os" title="产品系列" >
			  <iframe frameborder="0" id="prodSeries_iframe" src="" class="whFull"></iframe>
			</div>
			
			<div id ="prodModel" name="os" title="产品型号" >
			  <iframe frameborder="0" id="prodModel_iframe" src="" class="whFull"></iframe>
			</div>
			
<%--			<div id ="prodChkCmds" name="os" title="巡检命令" >--%>
<%--			  <iframe frameborder="0" id="prodChkCmds_iframe" src="" class="whFull"></iframe>--%>
<%--			</div>--%>

				
		</div>
	</div>
</form>
</body>
</html>