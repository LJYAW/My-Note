<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>巡检报告</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="${ctx}/css/common.css" rel="stylesheet" type="text/css"/>

    <link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet"
          type="text/css"/>

    <script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
    <script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="${ctx}/js/common.js" type="text/javascript"></script>
    <script src="${ctx}/static/formatter.js" type="text/javascript"></script>
    <script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js"
            type="text/javascript" charset="utf-8"></script>
    <script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript"
            charset="utf-8"></script>

    <style>
        html, body {
            height: 100%;
        }
    </style>
    <script type="text/javascript">

        var id = "${id}";

        function downloadWord() {
            window.location = "${ctx}/ack/report/downloadCPWord.do?id=${id}";
        }

        function downloadPDF() {
            window.location = "${ctx}/ack/report/downloadCPPDF.do?id=${id}";
        }
    </script>
</head>
<body style="overflow-y: hidden;height:100%;">

<input type="hidden" name="id" value="${id}"/>

<div id="top" style="height:100%;">
    <table align="center" style="width: 995px;height: 50px;">
        <tr>
            <td id="topTd1"></td>
            <td id="topTd2" style="font-size: 16px;font-weight:550;">
                巡检报告
            </td>
            <td id="topTd3" align="right">
                <button name="bnBack" title="导出Word" class="button_Word" onclick="downloadWord();">
                </button>
                <%--				<button name="bnBack1" title="导出Word" class="button_Pdf" onclick="downloadPDF();">--%>
                <%--				</button>--%>
            </td>
            <td id="topTd4"></td>
        </tr>
    </table>

    <div id="main" style="height: 94%;">
        <div id="mainContent" style="height: 94%;">
            <iframe style="margin-left: 20px;" width="1050" height="100%" scrolling="auto"
                    src="${ctx}/ack/report/showHtmReport.do?id=${id}"></iframe>
        </div>
    </div>
</div>

</body>
</html>
