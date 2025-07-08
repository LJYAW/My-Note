<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${s:paramVal("SystemName")}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/picdisplay.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/plugin/calendar/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css"/>

    <link
            href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css"
            rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/common.css" rel="stylesheet" type="text/css"/>

    <link href="${ctx}/css/index1.css" rel="stylesheet" type="text/css"/>

    <script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
    <script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/common.js" type="text/javascript"></script>

    <script type="text/javascript">
        var biztab = null;
        var selTab = "tabitem1";
        var initType = "cmdOutputRet";
        $(function () {
            var initQueryType = "#" + initType;
            $(initQueryType).attr("lselected", "true");
            biztab = $("#cmdbTab").ligerTab({
                dblClickToClose: false,
                onAfterSelectTabItem: function (tabid) {
                    if (tabid == "tabitem1") {
                        selTab = "tabitem1";
                        initType = "cmdOutputRet";
                    } else if (tabid == "tabitem2") {
                        selTab = "tabitem2";
                        initType = "indParseRet";

                    }
                    fleshShow(initType);
                }

            });
            $("#cmdOutputRet_iframe").attr("src", '${ctx}/ack/report/cmdOutputRet.do?id=' +${id});
        });

        function fleshShow(initType) {
            if (initType == "cmdOutputRet") {
                $("#cmdOutputRet_iframe").attr("src", '${ctx}/ack/report/cmdOutputRet.do?id=' +${id});
            } else if (initType == "indParseRet") {
                $("#indParseRet_iframe").attr("src", '${ctx}/ack/report/parseResult.do?id=' +${id});
            }

        }

        function windowHeight() {
            var de = document.documentElement;
            return self.innerHeight || (de && de.clientHeight) || document.body.clientHeight;
        }

        $(document).ready(window.onresize = function () {
            var wh = windowHeight();
            document.getElementById("cmdOutputRet").style.height = (wh - 30) + "px";
            document.getElementById("indParseRet").style.height = (wh - 30) + "px";

        });
    </script>
</head>
<body>
<div position="center" id="index-center">
    <div id="cmdbTab" style="width:100%;overflow:hidden; border:1px solid #A3C0E8; ">
        <div id="cmdOutputRet" title="命令输出结果" class="whFull">
            <iframe frameborder="0" id="cmdOutputRet_iframe" src="#" class="whFull"></iframe>
        </div>
        <div id="indParseRet" title="指标解析结果" class="whFull">
            <iframe frameborder="0" id="indParseRet_iframe" src="" class="whFull"></iframe>
        </div>
    </div>
</body>
</html>
