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
	<title>样式效果预览</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

	<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />

	<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
	<script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
	<script src="${ctx}/js/common.js" type="text/javascript"></script>
	<script src="${ctx}/static/formatter.js" type="text/javascript"></script>
	<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script><script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script><script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>


	<script type="text/javascript">

		var jsonStr = '${jsonStr}';
		
	</script>
</head>
<body>

<input type="hidden" name="jsonStr" value="${jsonStr}"/>

<div id="main">
	<div id="mainContent" align="center">
		<iframe width="985" height="300" scrolling="auto" src="${ctx}/cmdb/indicator/report/preViewTable.do?jsonStr=${jsonStr}"></iframe>
	</div>
</div>
</body>
</html>
