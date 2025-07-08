<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>巡检设备</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css"/>

    <script src="${ctx }/static/assets/js/jquery.min.js"></script>
    <script src="${ctx }/static/assets/js/layer.js"></script>
    <script src="${ctx }/static/assets/js/H-ui.admin.js"></script>

    <script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
    <!-- page specific plugin scripts -->
    <script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
    <script src="${ctx }/static/assets/js/grid.locale-en.js"></script>
    <script src="${ctx }/static/assets/js/resize.js"></script>
    <script src="${ctx }/static/assets/js/bootbox.js"></script>


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
    <link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>

    <script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
    <script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/common.js" type="text/javascript"></script>
    <script src="${ctx}/js/timeControl.js" type="text/javascript"></script>
    <script src="${ctx}/js/timePeriod.js" type="text/javascript"></script>
    <script type="text/javascript">
        var navtab = null;
        var biztab = null;
        var selTab = "tabitem1";
        var initType = "device";
        var resID;

        $(function () {
            initDivHeight();//初始化div高度
            layout = $("#layout-index").ligerLayout({leftWidth: 200, space: 2, isLeftCollapse: false});
            var treeData =${treeData};
            $("#interface_tree").ligerTree({
                checkbox: false,
                slide: true,
                btnClickToToggleOnly: true,
                nodeWidth: 200,
                data: treeData,
                idFieldName: 'id',
                parentIDFieldName: 'pid',
                onSelect: function (node) {
                    resID = node.data.id;
                    fleshShow(resID, initType);
                }
            });

            var initQueryType = "#" + initType;
            $(initQueryType).attr("lselected", "true");
            biztab = $("#cmdbTab").ligerTab({
                dblClickToClose: false,
                onAfterSelectTabItem: function (tabid) {
                    if (tabid == "tabitem1") {
                        selTab = "tabitem1";
                        initType = "device";
                        fleshShow(resID, initType);
                    } else if (tabid == "tabitem2") {
                        selTab = "tabitem2";
                        initType = "devCheckItem";
                        fleshShow(resID, initType);
                    }
                }
            });
            $("#device_iframe").attr("src", '${ctx}/ack/resources/main.do?id=' + id);
        });

        function windowHeight() {
            var de = document.documentElement;
            return self.innerHeight || (de && de.clientHeight) || document.body.clientHeight;
        }

        $(document).ready(window.onresize = function () {
            var wh = windowHeight();
            document.getElementById("device").style.height = (wh - 26) + "px";
            document.getElementById("devCheckItem").style.height = (wh - 26) + "px";

        });

        function saveOK() {
            if (selTab == "tabitem1") {
                var editor = document.getElementById("device_iframe").contentWindow;
                editor.saveOK();
            } else if (selTab == "tabitem2") {
                var editor = document.getElementById("devCheckItem_iframe").contentWindow;
                editor.saveOK();
            }
        }

        function initDivHeight() {
            var wh = windowHeight() - $('#breadcrumbs').height();
            document.getElementById("tree-index").style.height = (wh - 26) + "px";
            document.getElementById("device").style.height = (wh - 26) + "px";
            document.getElementById("devCheckItem").style.height = (wh - 26) + "px";

        }

        function windowHeight() {
            var de = document.documentElement;
            return self.innerHeight || (de && de.clientHeight) || document.body.clientHeight;
        }


        function fleshShow(id, initType) {
            if (initType == "device") {
                $("#device_iframe").attr("src", '${ctx}/ack/resources/main.do?id=' + id);
            } else if (initType == "devCheckItem") {
                $("#devCheckItem_iframe").attr("src", '${ctx}/ack/devCheckItems/main.do?id=' + id);
            }

        }

        function refresh() {
            // window.parent.$("#interface_iframe").attr("src",'${ctx}/monitor/res/mainTab.do');
            window.location.href = "${ctx}/ack/resources/mainTab.do?menuId=" + '${menuId}';
        }

    </script>
</head>
<body style="padding:0px; overflow:hidden;">
<form name="sysForm" id="sysForm">

    <input type="hidden" name="id" id="id" value="${id}"/>
    <div class="breadcrumbs ace-save-state" id="breadcrumbs">
        <ul class="breadcrumb">
            <li class="active">${titleContent }</li>
        </ul>
    </div>
    <div id="layout-index">

        <div position="left" id="tree-index" class="l-scroll l-accordion-content" title="<table><tr style='line-height: 22px;cursor:pointer'>
				<td onclick='refresh()'><img src='${ctx}/static/ligerUI/skins/icons/refresh.gif'/><span>刷新</span></td>
				</tr></table>" style="height: 532px; display: block;">
            <ul id="interface_tree">巡检设备</ul>
        </div>

        <div position="center" id="index-center">
            <div id="cmdbTab" style="width:100%;overflow:hidden; border:1px solid #A3C0E8; ">
                <div id="device" name="device" title="巡检设备">
                    <iframe frameborder="0" id="device_iframe" src="" class="whFull"></iframe>
                </div>
                <div id="devCheckItem" name="devCheckItem" title="巡检指标">
                    <iframe frameborder="0" id="devCheckItem_iframe" src="" class="whFull"></iframe>
                </div>

            </div>
        </div>
    </div>
</form>
</body>
</html>