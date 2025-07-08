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
<title>参数组编辑</title>
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


<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/edit.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/help.js" type="text/javascript" charset="utf-8"></script> 

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
		alert('${message}');
		</c:if>
	});

</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="group" action="${ctx}/system/param/group/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" name="paramGrpId" value="${group.paramGrpId}"/>
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
                <td><input id="grpName" name="grpName" value="${group.grpName}" type="text" ltype="text" class="validate[required[参数组名称不能为空],maxSize[64]] ip1" /></td>
                <td></td>
                <td align="right">参数组编码:</td>
                <td><input id="grpCode" name="grpCode" value="${group.grpCode}" type="text" ltype="text" class="validate[required[参数组编码不能为空],maxSize[64]] ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">说明:</td>
                <td colspan=4><input id="description" name="description" value="${group.description}" type="text" class="validate[optional,maxSize[128]] ip2" ltype="text"  /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">参数组类型:</td>
                <td colspan=4><s:resSelect name="state" value="${group.state}" code="paramGrpState" ltype="select" style="width: 180px;" /></td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">用户可修改:</td>
                <td align="left" colspan=4><form:checkbox path="isCanEdit" /></td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">变更通知:</td>
                <td align="left" colspan=4><form:checkbox path="isChangeNotify" /></td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">通讯地址:</td>
                <td><input id="notifyServer" name="notifyServer" value="${group.notifyServer}" class="validate[optional,maxSize[64] ip1" type="text" ltype="text" /></td>
                <td></td>
                <td align="right">通讯端口:</td>
                <td><input name="notifyPort" value="${group.notifyPort}" type="text" ltype='spinner' ligerui="{type:'int'}" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">自定义通讯:</td>
                <td align="left" colspan=4><form:checkbox path="isDefCommand"/></td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">通讯内容:</td>
                <td colspan=4><input id="commandContent" name="commandContent" value="${group.commandContent}" class="validate[optional,maxSize[128] ip2" type="text" ltype="text" /></td>
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

