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
<title>参数值编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 

<script type="text/javascript">
	$(function ()
	{
		<c:if test="${result=='success'}">
// 		window.parent.closeDlg();
		window.parent.saveOK('', '');     
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
	<form id="formSave" action="${ctx}/system/param/group/saveValue.do" method="post">
		<input type="hidden" name="id" value="${group.paramGrpId}"/>
		<input type="hidden" name="oprType" value="${oprType}"/>
		<table align="center" class="tb_edit">
			<tr>
				<td class="td_w1"></td>
				<td class="td_w4"></td>
				<td class="td_w3"></td>
			</tr>
      <c:forEach var="item" items="${group.sysParamItems}" >
	        <tr>
	          <td align="right">${item.paramName}：</td>
	          <td>
	            <input id="${item.paramCode}" name="${item.paramCode}" value="${item.valueText}" type="text" class="validate[required] ip2" />
	          </td>
	          <td></td>
	        </tr>
      </c:forEach>
            <tr>
           		<td align="center" colspan=6 >
           		<input id="bnSave" type="submit" value="保 存" class="l-button mg6" />
           		</td>
            </tr>
		</table>
    </form>
</body>
</html>

