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
<title>综合布线验证</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>
 <script type="text/javascript">
 <c:if test="${result=='success'}">
		var action = '${action}';
		var data = '';
		window.parent.saveOK(action, data);
		window.parent.closeDlg();
</c:if>
 var dataMain = {};
 var selectRowData = {}; 
 var manager,gridMain;
 var jsonstr=${jsonStr};
 $(function (){
	dataMain.Rows =jsonstr;
	manager=gridMain = $("#maingrid").ligerGrid({
        columns: 
            [
        		{ display:'信息点编号', name:'infoPointNo', width:170, align:'left' },
                { display:'交换机IP', name:'switchIP', width:170, align:'left' },
                { display:'输入的端口名称', name:'ifName', width:190, align:'left' },
                { display:'获取的端口名称', name:'unVerifyIfName', width:140, align:'left' }
       		],  
    	data: dataMain,
		alternatingRow: 'true',
		width: '98%',
		pageSize: '100',
		pageSizeOptions: [100,150,200,300,500],
        isScroll: false,
		onSelectRow: function (rowdata, rowindex)
              {
              }
    	});
    	loadData();
	});
     
        function loadData(){
            selectRowData = null;
            gridMain.loadData();
        }
   
   		function sendform(){
   			var data = manager.getData();
   			$("#jsonString").val(JSON.stringify(data));
			document.getElementById("batchForm").submit();
		}
		
 </script>
</head>
<body >
<div id="maingrid" style="margin:0 auto;"></div>
<form id="batchForm" action="${ctx}/fas/res/cablingInfo/saveVerify.do" method="post">
	<input type="hidden" id="jsonString" name="jsonString" value=""/>
	<table style="width:100%">
		<tr>
			<td align="center">
				<input id="bnSave" type="submit" value="名称替换" onclick="sendform()" class="l-button bn2 mg6" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>