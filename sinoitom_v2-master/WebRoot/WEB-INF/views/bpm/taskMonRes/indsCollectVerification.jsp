<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>结果展示</title>

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
    <!-- basic scripts -->
    <script src="${ctx }/static/assets/js/jquery.min.js"></script>
    <script src="${ctx }/static/assets/js/layer.js"></script>
    <script src="${ctx }/static/assets/js/H-ui.admin.js"></script>
    <script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
    <!-- page specific plugin scripts -->
    <script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
    <script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
    <script src="${ctx }/static/assets/js/resize.js"></script>
    <script src="${ctx }/static/assets/js/bootbox.js"></script>
</head>

<script type="text/javascript">

    var gridHeight;

    $(function () {
        gridHeight = $(document).height() - 120 - $('#btn').height();
        pageInit();
        resize();  //自动调整页面Grid
    });

    function pageInit() {
        var d = JSON.parse(window.localStorage.getItem("experimentData"));
        jQuery("#experimentalList").jqGrid({
            datatype: "local",
            data: d,
            colModel: [
                {label: '监测指标组名称', name: 'indGroupName', index: 'indGroupName', width: 200},
                {label: '监测指标英文名称', name: 'indItemEnName', index: 'indItemEnName', width: 200},
                {label: '监测指标中文名称', name: 'indItemName', index: 'indItemName', width: 200},
                {label: '监测指标值', name: 'value', index: 'value', width: 200},
                {label: '单位', name: 'measureUnit', index: 'measureUnit', width: 200}
            ],
            height: gridHeight,
            altRows: true
        });
    }
</script>

<body class="no-skin">
<div class="col-xs-12" style="overflow: hidden;">

    <table id="experimentalList"></table>
</div>
</body>
</html>











