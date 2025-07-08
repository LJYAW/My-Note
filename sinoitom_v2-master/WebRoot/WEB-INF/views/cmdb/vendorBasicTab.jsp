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
    	var selTab='tabitem1';
    	var initType = "vendor";
		var id="";
		
        $(function ()
        {
	        initDivHeight();//初始化div高度
		 	layout = $("#layout-index").ligerLayout({leftWidth:200, space:2,isLeftCollapse:false});
		    var treeData=${treeData};
		    $("#vendor-tree").ligerTree({
			    checkbox: false,
			    slide: true,
			    btnClickToToggleOnly: true,
			    nodeWidth: 130,
			    data: treeData,
			    idFieldName: 'id',
			    parentIDFieldName: 'pid',
			    onSelect: function(node) {
			        id = node.data.id;
			        fleshShow(id, initType);
			    }
			});
		
        	var initQueryType="#"+initType;
        	$(initQueryType).attr("lselected","true");
            biztab =$("#cmdbTab").ligerTab({
            dblClickToClose: false,
            onAfterSelectTabItem: function (tabid)
	            {
	               if(tabid=="tabitem1"){
	               	  initType="vendor";   
	              	  selTab="tabitem1";
	               	  $("#vendor_iframe").attr("src", '${ctx}/cmdb/vendor/main.do?id=' + id);  
	               }else if(tabid=="tabitem2"){
	               	  initType="vendorOID";  
	               	  selTab="tabitem2";
	                  $("#vendorOID_iframe").attr("src",'${ctx}/cmdb/vendorOID/main.do?id=' + id);               
	               }
	             
	               else if(tabid=="tabitem3"){
	                  initType="osType";  
	                  selTab="tabitem3";
 					   $("#osType_iframe").attr("src",'${ctx}/cmdb/ostype/main.do?id=' + id);  
	               }else if(tabid=="tabitem4"){
	               	  initType="os";  
	                  selTab="tabitem4";
	                    $("#os_iframe").attr("src",'${ctx}/cmdb/os/main.do?id=' + id);  
	               }else if(tabid=="tabitem5"){
	               	  initType="osVersion";  
	                  selTab="tabitem5";
	                    $("#osVersion_iframe").attr("src",'${ctx}/cmdb/vendorOSVersion/main.do?id=' + id);  
	               }
	            }
            });
		 $("#vendor_iframe").attr("src", '${ctx}/cmdb/vendor/main.do?id=' + id);  
        });
        
		function windowHeight() {
		    var de = document.documentElement;
		    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
		}
		$(document).ready(window.onresize=function(){
				var wh=windowHeight();
				document.getElementById("vendor").style.height = (wh-32)+"px";
				document.getElementById("osType").style.height = (wh-32)+"px";
				document.getElementById("os").style.height = (wh-32)+"px";
				document.getElementById("vendorOID").style.height = (wh-32)+"px";
				document.getElementById("osVersion").style.height = (wh-32)+"px";
			
		});
		
		function saveOK(action, data){
		if(selTab=="tabitem1"){
		   var editor = document.getElementById("vendor_iframe").contentWindow;
 			editor.saveOK(action, data); 
		}else if(selTab=="tabitem2"){
		    var editor = document.getElementById("vendorOID_iframe").contentWindow;
 			editor.saveOK(action, data); 
 		}
		else if(selTab=="tabitem3"){
		   var editor = document.getElementById("osType_iframe").contentWindow;
 			editor.saveOK(action, data); 
		}else if(selTab=="tabitem4"){
		  var editor = document.getElementById("os_iframe").contentWindow;
 			editor.saveOK(action, data); 
		}else if(selTab=="tabitem5"){
		  var editor = document.getElementById("osVersion_iframe").contentWindow;
 			editor.saveOK(action, data); 
		}
		
		
       }
    
 function initDivHeight(){
   		var wh=windowHeight()-$('#breadcrumbs').outerHeight();
   		document.getElementById("tree-index").style.height = (wh-5)+"px";
   		//document.getElementById("prodClass").style.height = (wh-15)+"px";
   		
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


  <div id="layout-index">	

	<div position="left" id="tree-index" class="l-scroll l-accordion-content" style="height: 532px; display: block;">
		<ul id="vendor-tree">厂商信息</ul>
	</div>
	
	<div position="center" id="index-center">	
		<div id="cmdbTab" style="width:100%;overflow:hidden; border:1px solid #A3C0E8; ">
			<div id="vendor" name="vendor"  title="厂商信息"  >
			  <iframe frameborder="0" id="vendor_iframe" src="" class="whFull"></iframe>
			</div>
			
			<div id="vendorOID" name="vendorOID"  title="厂商OID"  >
			  <iframe frameborder="0" id="vendorOID_iframe" src="" class="whFull"></iframe>
			</div>
				
			<div id ="osType" name="osType" title="操作系统分类" >
			  <iframe frameborder="0" id="osType_iframe" src="" class="whFull"></iframe>
			</div>
			
			<div id ="os" name="os" title="操作系统类型" >
			  <iframe frameborder="0" id="os_iframe" src="" class="whFull"></iframe>
			</div>
			
			<div id ="osVersion" name="osVersion" title="操作系统版本" >
			  <iframe frameborder="0" id="osVersion_iframe" src="" class="whFull"></iframe>
			</div>		
		</div>
	</div>
</div>
</form>
</body>
</html>