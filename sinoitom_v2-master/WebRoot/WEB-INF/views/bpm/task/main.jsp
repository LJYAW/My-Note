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
    <title>监测任务</title>

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
<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li class="active">${titleContent }</li>
    </ul>
</div>
<div id="btn" style="margin-top:2px;">
    <button class="btn btn-white btn-default btn-bold" onclick="onadd()">
        <i class="ace-icon fa  fa-plus bigger-120 default"></i>
        添加
    </button>
    <button class="btn btn-white btn-info btn-bold" onclick="onEdit()">
        <i class="ace-icon fa fa-pencil-square-o bigger-120 blue"></i>
        修改
    </button>
    <button class="btn btn-white btn-warning btn-bold" onclick="onDelete()">
        <i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
        删除
    </button>
    <button class="btn btn-white btn-default btn-bold" onclick="setResource()">
        <i class="ace-icon fa  fa-paperclip bigger-120 default"></i>
        关联业务系统
    </button>
    <button class="btn btn-white btn-default btn-bold" onclick="searchRes()">
        <i class="ace-icon fa fa-file-text-o bigger-120 default"></i>
        监测设备
    </button>
    <button class="btn btn-white btn-success btn-bold" onclick="onStart()">
        <%--		<i class="ace-icon fa fa-refresh bigger-120 green"></i>--%>
        <img alt="" src="${ctx }/img/start.svg">
        启动任务
    </button>
    <button class="btn btn-white btn-success btn-bold" onclick="onStop()">
        <%--		<i class="ace-icon fa fa-refresh bigger-120 green"></i>--%>
        <img alt="" src="${ctx }/img/stop.svg">
        停止任务
    </button>
    <button class="btn btn-white btn-success btn-bold" onclick="onReStart()">
        <%--		<i class="ace-icon fa fa-refresh bigger-120 green"></i>--%>
        <img alt="" src="${ctx }/img/restart.svg">
        重启任务
    </button>
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

    var vendorId = "${vendorId}";
    var ids = [];
    var ackAddr = "http://" + (document.location.host.indexOf(":") > 0 ? document.location.host.substring(0,document.location.host.indexOf(":")) : document.location.host) + ":8092/ack";
    var gridHeight = 0;
    $(function () {
        gridHeight = $(document).height() - $('#btn').height() - $('#breadcrumbs').outerHeight() - 85;
        pageInit();
        resize();  //自动调整页面Grid
    });

    function pageInit() {
        jQuery("#list").jqGrid({
            data:  ${jsonListData},
            //  datatype:从服务器端返回的数据类型，默认xml。可选类型：xml，local(客户端数据（数组）)，json，jsonnp，script，xmlstring，jsonstring，clientside
            datatype: "local",
            colNames: ['任务名称', 'id', '任务描述', '监测周期', '任务类型', '监测次数', '任务状态'],
            colModel: [
                {name: 'taskName', index: 'taskName', width: 80},
                {name: 'id', index: 'id', width: 30, hidden: true, key:true},
                {name: 'taskDescr', index: 'taskDescr', width: 110},
                {name: 'pollCycle', index: 'pollCycle', width: 60,
                    formatter: function (cellvalue, options, row) {
                        var html = '';
                        if (row.cycleUnit == "Minute") {
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
                        return row.pollCycle + html;
                    }
                },
                {name: 'taskType', index: 'taskType', width: 60,
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
                {name: 'checkTimes', index: 'checkTimes', width: 60,
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
                {name: 'status', index: 'status', width: 30,
                    formatter: function (cellvalue, options, row) {
                        var html = '';
                        if (row.status == "0") {
                            html += '<span >停用</span>';
                        } else if (row.status == "1") {
                            html += '<span >启用</span>';
                        } else if (row.status == "2") {
                            html += '<span >执行中</span>';
                        }else{
                            html += '<span >停用</span>';
                        }
                        return html;
                    }
                },
            ],
            viewrecords: true,
            rowNum: 10, rowList: [10, 20, 30],
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

    //id转换为int
    function getSelectedId(){
        return jQuery("#list").jqGrid('getGridParam', 'selarrrow').map(function(i){return Number(i);});
    }

    function f_nullValueRender(rowdata, index, value) {
        return (value === 0) ? "无限" : value;
    }

    function onadd() {
        layer_show("添加监测任务", '${ctx}/ack/task/add.do', 800, 400)
    }

    function onEdit() {

        var rowid = jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
        if (rowid == null) {
            layer.msg('请选择一条数据！', {icon: 7, time: 1500});
            return;
        }
        editRow(rowid);

    }

    function editRow(id) {
        layer_show("修改监测任务", '${ctx}/ack/task/edit.do?id=' + id, 800, 450)
    }

    function saveOK() {
        layer.closeAll();
        window.location.reload();
    }

    function onDelete() {
        var rowid = jQuery("#list").jqGrid('getGridParam', 'selrow');
        if (rowid == null) {
            layer.msg('请选择一条数据！', {icon: 7, time: 1500});
            return;
        }
        deleteRow(rowid);
    }

    function deleteRow(id) {
        layer.confirm('是否确认删除所选数据？', function (yes) {
            delRow(id);
        });
    }

    function delRow(id) {
        var url = '${ctx}/ack/task/delete.do?';
        $.ajax({
            url: url,
            type: "POST",
            dataType: 'json',
            data: {"ackTaskIds": id},

            success: function (data) {
                if (data.success == 0) {
                    layer.msg('删除成功!', {icon: 1, time: 1500});
                } else {
                    layer.msg(data.message, {icon: 2, time: 1500});
                }
                window.location.reload();
            }
        });
    }

    //关联业务系统
    function setResource() {

        var selectedId = getSelectedId();
        if (selectedId.length > 1) {
            layer.msg('只能选择一条数据进行关联!', {icon: 7, time: 1500});
            return;
        }
        var rowid = jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
        if (rowid !== "" && rowid !== null) {
            layer_show("关联业务系统", '${ctx}/bpm/task/addTaskRelateBiz.do?id=' + rowid, 1100, 500)
        } else {
            layer.msg('请选择要关联对象!', {icon: 7, time: 1500});
        }
    }


    //监测设备
    function searchRes() {

        var selectedId = getSelectedId();
        if (selectedId.length > 1) {
            layer.msg('只能选择一条数据进行操作!', {icon: 7, time: 1500});
            return;
        }
        var rowid = jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
        if (rowid !== null) {
            layer_show("监测设备", '${ctx}/ack/task/searchAckRes.do?id=' + rowid, 1100, 500)
        } else {
            layer.msg('请选择要查看的对象!', {icon: 7, time: 1500});
        }
    }

    function onStart() {
        var selectedIds = getSelectedId();
        if (selectedIds.length > 0) {
            var rowData0 = jQuery("#list").jqGrid('getRowData', selectedIds[0]);
            var original = rowData0.status, ok = true;

            for (var i = 0; i < selectedIds.length; i++) {
                var rowData = jQuery("#list").jqGrid('getRowData', selectedIds[i]);
                if (Number(rowData.status) !== 0) {
                    layer.alert('所选择监测任务已启动！', {icon: 2, time: 1500});
                    return;
                }
                if (original !== rowData.status) ok = false;
            }
            if (ok) {
                ids = selectedIds;
            } else {
                layer.alert('选择的监测任务任务状态不一致!', {icon: 2, time: 1500});
                return;
            }

            layer.confirm("请确认是否开始监测任务？", function (yes) {
                if (yes) {
                    if (ids.length > 1) startMulti(ids);
                    else start(ids);
                }
            });
        } else {
            layer.confirm("是否重启所有监测任务？", function (yes) {
                if (yes) {
                    startAll();
                }
            });
        }
    }

    function start(id) {
        $.ajax({
            type: "get",
            url: ackAddr + '/schedulerAckJob?ackTaskId=' + id,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (Number(data.status) === 200) {
                    if (data.result.result) {
                        layer.msg(data.result.message, {icon: 1, time: 1500},function(){onRefresh();});
                    } else {
                        layer.msg(data.result.message, {icon: 2, time: 1500},function(){onRefresh();});
                    }
                } else {
                    layer.msg('请求启动监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
                }
            },
            error: function (e) {
                layer.msg('请求启动监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
            }
        });
    }

    function startMulti(ids) {
        $.ajax({
            type: "get",
            url: ackAddr + '/schedulerAckJobs?ackTaskIds=' + ids,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (Number(data.status) === 200) {
                    var r = data.result, ok = true;
                    for (var i = 0; i < r.length; i++) {
                        var result = r[i];
                        if (!result) ok = false;
                    }
                    if (ok) {
                        layer.msg('启动监测任务成功!', {icon: 1, time: 1500},function(){onRefresh();});
                    } else {
                        layer.msg('启动监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
                    }
                } else {
                    layer.msg('请求启动监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
                }
            },
            error: function (e) {
                layer.msg('请求启动监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
            }
        });
    }

    //启动所有
    function startAll() {
        $.ajax({
            type: "get",
            url: ackAddr + '/schedulerAllAckJob',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (Number(data.status) === 200) {
                    var r = data.result, ok = true;
                    for (var i = 0; i < r.length; i++) {
                        var result = r[i];
                        if (!result) ok = false;
                    }
                    if (ok) {
                        layer.msg('启动监测任务成功！', {icon: 1, time: 1500},function(){onRefresh();});
                    } else {
                        layer.msg('启动监测任务失败，请联系管理员！', {icon: 2, time: 1500},function(){onRefresh();});
                    }
                } else {
                    layer.msg('请求启动监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
                }
            },
            error: function (e) {
                layer.msg('请求启动监测任务失败，请联系管理员!', {icon: 2, time: 1500});
            }
        });
    }

    //停止任务
    function onStop() {
        var sIds = getSelectedId();
        if (sIds.length > 0) {
            var rowData0 = jQuery("#list").jqGrid('getRowData', sIds[0]);
            var original = rowData0.status, ok = true;

            for (var i = 0; i < sIds.length; i++) {
                var rowData = jQuery("#list").jqGrid('getRowData', sIds[i]);
                if (Number(rowData.status) === 0) {
                    layer.alert('所选择监测任务已停止！', {icon: 2, time: 1500});
                    return;
                }
                if (original !== rowData.status) ok = false;
            }
            if (ok) {
                ids = sIds;
            } else {
                layer.alert('选择的监测任务任务状态不一致!', {icon: 2, time: 1500});
                return;
            }

            layer.confirm("请确认是否停止监测任务？", function (yes) {
                if (yes) {
                    if (ids.length > 1) stopMulti(ids);
                    else stop(ids);
                }
            });
        } else {
            layer.confirm("是否停止所有监测任务？", function (yes) {
                if (yes) {
                    stopAll();
                }
            });
        }
    }

    //停止一条
    function stop(id) {
        $.ajax({
            type: "get",
            url: ackAddr + '/unSchedulerAckJob?ackTaskId=' + id + "&deleteTask=false",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (Number(data.status) === 200) {
                    if (data.result.result) {
                        layer.msg(data.result.message, {icon: 1, time: 1500},function(){onRefresh();});
                    } else {
                        layer.msg(data.result.message, {icon: 2, time: 1500},function(){onRefresh();});
                    }
                } else {
                    layer.msg('请求停止监测任务失败，请联系管理员!!', {icon: 2, time: 1500},function(){onRefresh();});
                }
            },
            error: function (e) {
                layer.msg('请求停止监测任务失败，请联系管理员!' + e, {icon: 2, time: 1500},function(){onRefresh();});
            }
        });
    }

    function stopMulti(ids) {
        console.log(ids);
        $.ajax({
            type: "get",
            url: ackAddr + '/unSchedulerAckJobs?ackTaskIds=' + ids + '&deleteTask=false',
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (Number(data.status) === 200) {
                    var r = data.result, ok = true;
                    for (var i = 0; i < r.length; i++) {
                        var result = r[i];
                        if (!result) ok = false;
                    }
                    if (ok) {
                        layer.msg('停止监测任务成功！', {icon: 1, time: 1500},function(){onRefresh();});
                    } else {
                        layer.msg('停止监测任务失败，请联系管理员！', {icon: 2, time: 1500},function(){onRefresh();});
                    }
                } else {
                    layer.msg('请求停止监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
                }
            },
            error: function (e) {
                layer.msg('请求停止监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
            }
        });
    }

    //停止所有
    function stopAll() {
        $.ajax({
            type: "get",
            url: ackAddr + '/unSchedulerAllAckJob?deleteTask=false',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (Number(data.status) === 200) {
                    var r = data.result, ok = true;
                    for (var i = 0; i < r.length; i++) {
                        var result = r[i];
                        if (!result) ok = false;
                    }
                    if (ok) {
                        layer.msg('停止监测任务成功！', {icon: 1, time: 1500},function(){onRefresh();});
                    } else {
                        layer.msg('停止监测任务失败，请联系管理员！', {icon: 2, time: 1500},function(){onRefresh();});
                    }
                } else {
                    layer.msg('请求停止监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
                }
            },
            error: function (e) {
                layer.msg('请求停止监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
            }
        });
    }

    //重启任务
    function onReStart() {

        var selectedIds = getSelectedId();
        if (selectedIds.length > 0) {
            ids = selectedIds;
            layer.confirm("请确认是否重启监测任务？", function (yes) {
                if (yes) {
                    if (ids.length > 1) reStartMulti(ids);
                    else reStart(ids);
                }
            });
        } else {
            layer.confirm("是否重启所有监测任务？", function (yes) {
                if (yes) {
                    reStartAll();
                }
            });
        }
    }

    //重启一条
    function reStart(id) {
        $.ajax({
            type: "get",
            url: ackAddr + '/reSchedulerAckJob?ackTaskId=' + id,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (Number(data.status) === 200) {
                    if (data.result.result) {
                        layer.msg(data.result.message, {icon: 1, time: 1500},function(){onRefresh();});
                    } else {
                        layer.msg(data.result.message, {icon: 2, time: 1500},function(){onRefresh();});
                    }
                } else {
                    layer.msg('请求重启监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
                }
            },
            error: function (e) {
                layer.msg('请求重启监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
            }
        });
    }

    function reStartMulti(ids) {
        $.ajax({
            type: "get",
            url: ackAddr + '/reSchedulerAckJobs?ackTaskIds=' + ids,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (Number(data.status) === 200) {
                    var r = data.result, ok = true;
                    for (var i = 0; i < r.length; i++) {
                        var result = r[i];
                        if (!result) ok = false;
                    }
                    if (ok) {
                        layer.msg('重启监测任务成功!', {icon: 1, time: 1500},function(){onRefresh();});
                    } else {
                        layer.msg('重启监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
                    }
                } else {
                    layer.msg('请求重启监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
                }
            },
            error: function (e) {
                layer.msg('请求重启监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
            }
        });
    }

    //重启所有
    function reStartAll() {
        $.ajax({
            type: "get",
            url: ackAddr + '/reSchedulerAllAckJob',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (Number(data.status) === 200) {
                    var r = data.result, ok = true;
                    for (var i = 0; i < r.length; i++) {
                        var result = r[i];
                        if (!result) ok = false;
                    }
                    if (ok) {
                        layer.msg('重启监测任务成功！', {icon: 1, time: 1500},function(){onRefresh();});
                    } else {
                        layer.msg('重启监测任务失败，请联系管理员！', {icon: 2, time: 1500},function(){onRefresh();});
                    }
                } else {
                    layer.msg('请求重启监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
                }
            },
            error: function (e) {
                layer.msg('请求重启监测任务失败，请联系管理员!', {icon: 2, time: 1500},function(){onRefresh();});
            }
        });
    }

    function onRefresh() {
        window.location.reload();
    }

    function saveok() {
        onRefresh();
        window.close();
    }

</script>
</body>
</html>







