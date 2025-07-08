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
    <title>巡检报告</title>

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


<body class="no-skin">
<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li class="active">${titleContent }</li>
    </ul>
</div>

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
        gridHeight = $(document).height() - $('#btn').height() - $('#breadcrumbs').outerHeight() - 85;
        gridWidth = $(document).width();
        pageInit();
        resize();  //自动调整页面Grid

    });

    function pageInit() {
        jQuery("#list").jqGrid({
            data:  ${jsonListData},
            //  datatype:从服务器端返回的数据类型，默认xml。可选类型：xml，local(客户端数据（数组）)，json，jsonnp，script，xmlstring，jsonstring，clientside
            datatype: "local",


            colNames: ['任务名称', 'id', '任务描述', '巡检周期', '任务类型', '巡检次数', '操作'],
            colModel: [
                {name: 'taskName', index: 'taskName', width: 160},
                {name: 'id', index: 'id', width: 30, hidden: true},
                {name: 'taskDescr', index: 'taskDescr', width: 160},
//     	        	 {name : 'checkCycle',index : 'checkCycle',width: 160},
                {
                    name: 'cycleUnit', index: 'cycleUnit', width: 60,
                    formatter: function (cellvalue, options, row) {
                        var html = '';
                        if (row.cycleUnit == "minute") {
                            html += '<span >分</span>';
                        } else if (row.cycleUnit == "hour") {
                            html += '<span>小时</span>';
                        } else if (row.cycleUnit == "day") {
                            html += '<span>天</span>';
                        } else if (row.cycleUnit == "week") {
                            html += '<span>周</span>';
                        } else if (row.cycleUnit == "month") {
                            html += '<span>月</span>';
                        }
                        return row.checkCycle + html;
                    }
                },
                {
                    name: 'taskType', index: 'taskType', width: 60,
                    formatter: function (cellvalue, options, row) {
                        var html = '';
                        if (row.taskType == "0") {
                            html += '<span >循环任务</span>';
                        } else if (row.taskType == "1") {
                            html += '<span>单次任务</span>';
                        } else if (row.taskType == "2") {
                            html += '<span>多次任务</span>';
                        }
                        return html;
                    }
                },
                {
                    name: 'checkTimes', index: 'checkTimes', width: 60,
                    formatter: function (cellvalue, options, row) {
                        var html = '';
                        if (row.checkTimes == "0") {
                            html += '<span >无限</span>';
                        } else {
                            html += row.checkTimes
                        }
                        return html;
                    }
                },
                {
                    width: 120,
                    formatter: function (cellvalue, options, row) {
                        var html = '<span style="color:blue;cursor:pointer;" onclick="viewHisReport(\'' + row.id + '\');">历史巡检报告</span>';

                        return html;
                    }
                }
            ],

            viewrecords: true,
            rowNum: 50, rowList: [50, 100, 150],
            pager: '#pager',
            height: gridHeight,
            altRows: true,
            toolbar: [true, 'top'],
            multiselect: true,
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

    function f_nullValueRender(rowdata, index, value) {
        return (value === 0) ? "无限" : value;
    }


    function viewHisReport(id) {
        layer_show("历史巡检报告", '${ctx}/ack/report/viewRowReport.do?id=' + id, gridWidth - 100, layerHeight);
        /* $('.layui-layer-shade').height(document.body.offsetHeight-$('#breadcrumbs').height()); */
    }

    function onRefresh() {
        window.location.reload();
    }


</script>
</body>
</html>

