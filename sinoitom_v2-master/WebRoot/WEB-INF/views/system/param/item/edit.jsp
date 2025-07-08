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
<title>参数编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/help.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 

<script type="text/javascript">
	$(function ()
	{				
		<c:if test="${result=='success'}">
		var action = '${action}';
		var data = ${saveData};
		window.parent.saveOK(action, data);     
// 		window.parent.closeDlg();
		</c:if>

		$("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});	

		<c:if test="${result=='error'}">
		$.ligerDialog.error('${message}');
		</c:if>
	});

</script>

</head>
<body> 
	<div class="wFull hL">当前参数组：${item.sysParamGroup.grpName}</div>
	<form:form id="formSave" modelAttribute="item" action="${ctx}/system/param/item/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" name="sysParamGroup.paramGrpId" value="${item.sysParamGroup.paramGrpId}"/>
		<input type="hidden" name="paramId" value="${item.paramId}"/>
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
                <td align="right">参数名称:</td>
                <td><input id="paramName" name="paramName" value="${item.paramName}" type="text" ltype="text" class="validate[required[参数名称不能为空],maxSize[64]] ip1" /></td>
                <td></td>
                <td align="right">状态:</td>
                <td><s:resSelect name="state" value="${item.state}" code="paramerState" ltype="select" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">参数编码:</td>
                <td><input id="paramCode" name="paramCode" value="${item.paramCode}" type="text" ltype="text" class="validate[required[参数编码不能为空],maxSize[64]] ip1" /></td>
                <td></td>
                <td align="right">参数值类型:</td>
                <td><s:resSelect name="valueType" value="${item.valueType}" code="paramValueType" ltype="select" /></td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">参数值:</td>
                <td colspan=4><input id="valueText" name="valueText" value="${item.valueText}" type="text" class="validate[optional,maxSize[512]] ip2" ltype="text"  /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">说明:</td>
                <td colspan=4><input id="description" name="description" value="${item.description}" type="text" class="validate[optional,maxSize[128]] ip2" ltype="text"  /></td>
                <td></td>
            </tr>
            <tr>
           		<td align="center" colspan=6 >
           		<input id="bnSave" type="submit" value="保 存" class="l-button mg6" />
           		</td>
            </tr>
		</table>
    </form:form>
</body>
</html>

