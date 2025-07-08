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
<title>mib文件解析</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css" />

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css" />
		<link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />
		<!-- ace styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

	</head>

	
	
		<!-- basic scripts -->
		<script src="${ctx }/static/assets/js/jquery.min.js"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>
	    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script> 
		
		<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
		<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
		<!-- page specific plugin scripts -->
<!-- 		<script src="${ctx }/static/assets/js/bootstrap-datepicker.min.js"></script> -->
		<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
		<script src="${ctx }/static/assets/js/grid.locale-en.js"></script>
		<script src="${ctx }/static/assets/js/resize.js"></script>
<script type="text/javascript">
	var searchMsg = null;
	var intvMsg = null;
	var dataMain = {};
	var gridMain;
    var selectRowData = null;
//     var textSelectType='<div class=\"devices\" align=\"left\" style=\"width:100%;background-color: #CEDFEF;\"><span style=\"margin-left:-12px;\">设备IP：</span><input id=\"deviceIp\" type=\"text\" value=\"\"></div>';
//     var textButton='<input id=\"bnSave\" type=\"button\" value=\"选择设备\" onclick=\"sendform();\" class=\"l-button\">';
	$(function (){				
// 	 $(".devices").parent('span').parent('div').mouseover().removeClass("l-panel-btn-over");
// 	     $("#toptoolbar").ligerToolBar({
//             items: [
            
// 	        	{ text: '选择文件', click: selectFile , icon : 'add'},
// 	        	{ line:true },
//             ]
//      	});
            
	});
	
    
    function selectFile(){
      	var url = '${ctx}/snmp/mibNode/toSelectFile.do';
      	window.open(url,'', 'height=460, width=960, top=160, left=200, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
//      	showDlg('选择文件',1200,500,  url);     
	}
	
//     function parseOid(){
//       	var url = '${ctx}/snmp/mibNode/parseOid.do';
//      	showDlg('解析oid', 750, 400, url);     
// 	}
	
	function refresh(){
			    window.location.reload();
				return false;
			 } 
	function showDeviceIP(ip){
	  $("#deviceIp").val(ip);
	}		 
</script>

</head>
<body> 
<!-- 		 <div class="breadcrumbs ace-save-state" id="breadcrumbs"> -->
<!--               <ul class="breadcrumb"> -->
<!--                   <li class="active">${titleContent }</li> -->
<!--               </ul> -->
<!--          </div>	 -->
		<button class="btn btn-white btn-danger btn-bold" onclick="selectFile()">
			<i class="ace-icon fa fa-file-text-o bigger-120 danger"></i>
					选择文件
		</button>
		
			
</body>
</html>
