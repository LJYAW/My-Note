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
<title>查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

		<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/static/jquery-validationEngine/help.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />
		
		<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
		<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
		<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
		<script src="${ctx}/static/jquery-validationEngine/help.js" type="text/javascript" charset="utf-8"></script> 
		<script src="${ctx}/js/common.js" type="text/javascript"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>

<script type="text/javascript">
	$(function (){
		<c:if test="${result=='success'}">
	// 		var action = '${action}';
	// 		var data = ${saveData};
	// 		window.parent.saveOK(action, data);     
	// 		window.parent.closeDlg();
			window.parent.reloadGrid(${jsonListData});
		</c:if>

// 		$("#formSave").ligerForm({inputWidth:200});
// 		$("input").filter(".ip2").ligerTextBox({ width: 553 });
// 		$("#formSave").validationEngine({promptPosition: "centerRight"});	

// 		<c:if test="${result=='error'}">
// 		alert('${message}');
// 		</c:if>
		
// 		var jsonListData  = '${jsonListData}';
// 		window.parent.saveOK('search', jsonListData);

		
	});
	function submitForm(){
		$("#formSearch").submit();
	}

</script>

</head>
<body> 
	    	<form style="padding:0px;" id="formSearch" action="${ctx}/system/param/group/search.do" method="post">
    	<table align="center" class="tb_edit">
            <tr>
                <td class="td_w1"></td>
                <td class="td_w2"></td>
                <td class="td_w3"></td>
                <td class="td_w1"></td>
                <td class="td_w2"></td>
                <td class="td_w3"></td>
            </tr>
            <tr>
                <td align="right">参数组名称:</td>
                <td><input name="filter_LIKES_grpName" class="ip1" type="text" ltype="text"/></td>
                <td></td>
                <td align="right">参数组编码:</td>
                <td><input name="filter_LIKES_grpCode" class="ip1" type="text" ltype="text"/></td>
                <td></td>
            </tr>
            <tr>
                <td colspan=3 align="right" style="padding:10px">
                <input id="bnSearch" type="button" onclick="submitForm()" value="查 询" class="l-button"/> 
                </td>
                <td colspan=3 style="padding:10px">
                <input id="bnReset" type="button" value="重 置" class="l-button""/>
                </td>
            </tr>   
        </table>
        </form>
</body>
</html>

