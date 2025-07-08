<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ack" value="${pageContext.request.localAddr}"/>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>巡检报告明细</title>

    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css"/>
    <link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet"/>
    <!-- ace styles -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/layer.css"/>
</head>


<!-- basic scripts -->
<script src="${ctx }/static/assets/js/jquery.min.js"></script>
<script src="${ctx }/static/assets/js/layer.js"></script>
<script src="${ctx }/static/assets/js/H-ui.admin.js"></script>

<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
<!-- page specific plugin scripts -->
<!-- 		<script src="${ctx }/static/assets/js/bootstrap-datepicker.min.js"></script> -->
<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
<script src="${ctx }/static/assets/js/resize.js"></script>
<script src="${ctx }/static/assets/js/bootbox.js"></script>
<script src="${ctx }/js/dateFormat.js"></script>

<body class="no-skin">
<div id="btn" style="margin-top:2px;">
    <button class="btn btn-white btn-success btn-bold" onclick="onRefresh()">
        <i class="ace-icon fa fa-refresh bigger-120 green"></i>
        刷新
    </button>
</div>


<div class="col-xs-12" style="overflow: hidden;">

    <table id="list"></table>
    <div id="pager"></div>

</div><!-- /.col -->
<script type="text/javascript">

    var layerHeight;
    var gridHeight, gridWidth;
    $(function () {
        layerHeight = $(document).height() - 30;
        gridHeight = $(document).height() - 85 - $('#btn').height();
        gridWidth = $(document).width();
        pageInit();
        resize();  //自动调整页面Grid

    });

    function pageInit() {
        jQuery("#list").jqGrid({
            datatype: "json",
            mtype: "GET",
            url: "${ctx}/ack/report/getHistoryReport.do?taskId=" + ${taskId},
            colNames: ['任务名称', '任务开始执行时间', '任务完成时间', '任务耗时(毫秒)', '操作'],
            colModel: [
                {name: 'taskName', index: 'taskName', width: 160},
                {
                    name: 'taskStartTime', index: 'taskStartTime', width: 120, formatter: "date",
                    formatter: function (cellValue, options, row) {
                        return getSmpFormatDateByLong(row.taskStartTime, true)
                    }
                },
                {
                    name: 'taskEndTime', index: 'taskEndTime', width: 120, formatter: "date",
                    formatter: function (cellValue, options, row) {
                        return getSmpFormatDateByLong(row.taskEndTime, true)
                    }
                },
                {name: 'useTime', index: 'useTime', width: 80},
                {
                    width: 120,
                    formatter: function (cellvalue, options, row) {
                        var html = '<span style="color:blue;cursor:pointer;" onclick="viewRowReport(\'' + row.id + '\');">巡检报告</span> | ';
                        html += '<span style="color:blue;cursor:pointer;" onclick="viewHisCmdResult(\'' + row.id + '\');">巡检命令及结果</span>'
                        return html;
                    }
                }
            ],
            viewrecords: true,
            page: 1,
            sortname: "taskStartTime",
            sortorder: "desc",
            rowNum: 50,
            rowList: [50, 100, 200],
            pager: '#pager',
            height: gridHeight,
            altRows: true,
            toolbar: [true, 'top'],
            multiboxonly: true,
            loadComplete: function () {
                var table = this;
                setTimeout(function () {
                    updatePagerIcons(table);
                }, 0);
            },
        });
        jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});
        $("#t_list").css({"height": 30});
        $("#btn").appendTo("#t_list");

    }

    function viewRowReport(id) {
        if (gridWidth > 1100) {
            gridWidth = 1100;
        }
        layer_show("巡检报告", '${ctx}/ack/report/viewDetailReport.do?id=' + id, gridWidth, layerHeight);
        <%--window.parent.parent.layer.open({--%>
        <%--    title:'巡检报告',--%>
        <%--    type: 2,--%>
        <%--    area: ['1500px', '700px'],--%>
        <%--    fixed: false, //不固定--%>
        <%--    maxmin: true,--%>
        <%--    content: '${ctx}/ack/report/viewDetailReport.do?id=' + id--%>
        <%--});--%>
    }

    function onRefresh() {
        window.location.reload();
    }

    function viewHisCmdResult(id) {
        layer_show("巡检命令及结果", '${ctx}/ack/report/viewHisCmdResult.do?id=' + id, 900, layerHeight);
    }

</script>
</body>
</html>
