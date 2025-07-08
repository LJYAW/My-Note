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
	<title>历史趋势图</title>
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

	<script src="${ctx}/js/highcharts-7.1.1/highcharts.js" type="text/javascript"></script>
	<script src="${ctx}/js/highcharts-7.1.1/highcharts-3d.js" type="text/javascript"></script>
	<script src="${ctx}/js/highcharts-7.1.1/modules/exporting.js" type="text/javascript"></script>
	<script src="${ctx}/js/highcharts-7.1.1/modules/data.js" type="text/javascript"></script>
	<script src="${ctx}/js/highcharts-7.1.1/modules/drilldown.js" type="text/javascript"></script>
	<script src="${ctx}/js/highcharts-7.1.1/highcharts-more.js" type="text/javascript"></script>

	<script type="text/javascript">

		var id = "${id}";
        function downloadWord()
        {
            window.location="${ctx}/fas/res/mdlware/app/index/downloadCPWord.do?id=${id}";
        	// alert("111");
        }

        function downloadPDF()
        {
            window.location="${ctx}/fas/res/mdlware/app/index/downloadCPPDF.do?id=${id}";
            // alert("222");
        }
	</script>
</head>
<body>

<input type="hidden" name="id" value="${id}"/>

<div id="top">
	<table style="width: 995px;height: 50px;">
		<tr>
			<td id="topTd1"></td>
			<td id="topTd2" style="font-size: 16px;font-weight:550;">
				巡检报告
			</td>
			<td  id="topTd3" align="right">
				<button name="bnBack" title="导出Word" class="button_Word" onclick="downloadWord();">
				</button>
				<%--<button name="bnBack1" class="button_Pdf" onclick="downloadPDF();">--%>
				<%--</button>--%>
			</td>
			<td id="topTd4"></td>
		</tr>
	</table>
</div>
<div id="main">
	<div id="mainContent">
		<iframe width="985" height="700" scrolling="auto" src="${ctx}/fas/res/mdlware/app/index/Buttonview.do?id=${id}"></iframe>
	</div>
</div>
</body>
</html>
