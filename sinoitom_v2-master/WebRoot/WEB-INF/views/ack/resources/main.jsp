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
    <title>巡检设备</title>

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
<div id="btn" style="margin-top:2px;">
    <button class="btn btn-white btn-default btn-bold" onclick="onadd()">
        <i class="ace-icon fa  fa-plus bigger-120 default"></i>
        添加
    </button>
    <button class="btn btn-white btn-warning btn-bold" onclick="onDelete()">
        <i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
        删除
    </button>
    <button class="btn btn-white btn-warning btn-bold" onclick="onView()">
        <i class="ace-icon fa fa-file-text-o bigger-120 orange"></i>
        详情
    </button>
    <button class="btn btn-white btn-default btn-bold" onclick="setResource()">
        <i class="ace-icon fa fa-plus bigger-120 default"></i>
        关联检测指标
    </button>
    <button class="btn btn-white btn-purple btn-bold" onclick="experiment()">
        <i class="ace-icon fa fa-camera bigger-120 purple"></i>
        巡检测试
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
    var ids = "";
    var gridHeight;

    $(function () {
        gridHeight = $(document).height() - 120 - $('#btn').height();
        pageInit();
        resize();  //自动调整页面Grid
    });

    function pageInit() {
        jQuery("#list").jqGrid({
            url: '${ctx}/ack/resources/search.do?id=' + '${id}',
            datatype: "json",

            colModel: [
                {label: 'vendorID', name: 'vendorID', index: 'vendorID', width: 30, hidden: true},
                {label: 'resClassCode', name: 'resClassCode', index: 'resTypeCode', width: 30, hidden: true},
                {label: 'resTypeCode', name: 'resTypeCode', index: 'resTypeCode', width: 30, hidden: true},
                {label: 'resId', name: 'resId', index: 'resId', width: 30, hidden: true},
                {label: '机构名称', name: 'orgName', index: 'orgName', width: 80},
                {label: '管理IP地址', name: 'mgmtIP', index: 'mgmtIP', width: 120},
                {label: '资源分类', name: 'resClassName', index: 'resClassName', width: 80},
                {label: '资源类型', name: 'resTypeName', index: 'resTypeName', width: 80},
                {label: '产品型号', name: 'prodModel', index: 'prodModel', width: 120},
                {label: '型号OID', name: 'modelOID', index: 'modelOID', width: 150},
                {label: '操作系统类型', name: 'osType', index: 'osType', width: 80},
                {label: '版本分类', name: 'osVersion', index: 'osVersion', width: 80},
                {label: '操作系统特征', name: 'osFeature', index: 'osFeature', width: 80},
                {label: '软件版本', name: 'osRelease', index: 'osRelease', width: 80},
                {label: '采集方式', name: 'accessMode', index: 'accessMode', width: 80},
                {label: '巡检用户', name: 'userName', index: 'userName', width: 80},
                {
                    label: '监测状态', name: 'flag', index: 'flag', width: 80,
                    formatter: function (cellvalue, options, row) {
                        if (row.flag == 1) {
                            return "监测";
                        } else if (row.flag == 0) {
                            return "暂停";
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

    function onadd() {
        layer_show("添加巡检设备", '${ctx}/ack/resources/add.do', 800, 400)
    }

    function onEdit() {
        var rowid = jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
        if (rowid == "" || rowid == null) {
            layer.msg('请选择一条数据！', {icon: 7, time: 1500});
            return;
        }
        editRow(rowid);

    }

    function editRow(id) {
        layer_show("修改巡检任务", '${ctx}/ack/task/edit.do?id=' + id, 800, 450)
    }

    function saveOK() {
        layer.closeAll();
        window.location.reload();
    }

    function onDelete() {
        var rowid = jQuery("#list").jqGrid('getGridParam', 'selrow');
        var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (rowid == "" || rowid == null) {
            layer.msg('请选择一条数据！', {icon: 7, time: 1500});
            return;
        }
        deleteRow(selectedId);
    }

    function deleteRow(id) {
        layer.confirm('是否确认删除所选数据？', function (yes) {
            delRow(id);
        });
    }

    function delRow(id) {
        var url = '${ctx}/ack/resources/delete.do?ids=' + id;
        $.ajax({
            url: url,
            dataType: 'json',

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

    function getSelectedId() {
        return jQuery("#list").jqGrid('getGridParam', 'selarrrow').map(function (i) {return Number(i);});
    }

    function experiment() {
        const selectedIds = getSelectedId();
        if (selectedIds.length > 0) {
            if (selectedIds.length > 1) {
                layer.alert("请选择一台设备进行巡检测试！", {icon: 2, time: 1500});
            } else {
                let resId = Number($("#list").jqGrid('getRowData', selectedIds[0]).resId);
                const url = '${ctx}/ack/resources/experiment.do?';
                let jsonData = JSON.stringify({"id": resId});
                $.ajax({
                        type: "post",
                        url: url,
                        dataType: "json",
                        data: jsonData,
                        contentType: "application/json; charset=utf-8",
                        beforeSend: function () {
                            layer.load();
                        },
                        complete: function () {
                            layer.closeAll('loading');
                        },
                        success: function (data) {
                            if (Number(data.status) === 200) {
                                let rd = JSON.stringify(data.result);
                                window.localStorage.setItem("experimentData", rd);
                                window.parent.parent.layer.open({
                                    title: '测试结果',
                                    type: 2,
                                    area: ['600px', '600px'],
                                    fixed: false, //不固定
                                    maxmin: true,
                                    content: '${ctx}/ack/resources/toExperimentResult.do'
                                });
                            } else {
                                layer.msg('请求测试巡检任务失败，请联系管理员!', {icon: 2, time: 1500}, function(){onRefresh();});
                            }
                        },
                        error: function () {
                            layer.msg('请求测试巡检任务失败，请联系管理员!', {icon: 2, time: 1500}, function(){onRefresh();});
                        }
                    }
                );
            }
        } else {
            layer.alert("请选择需要进行巡检测试的设备！", {icon: 2, time: 1500});
        }
    }

    function setResource() {
        const selectedIds = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (selectedIds.length > 0) {
            let vendorID;
            let resTypeCode;
            let osType;
            let osVersion;
            let osFeature;
            for (let i = 0; i < selectedIds.length; i++) {
                let rowData0 = jQuery("#list").jqGrid('getRowData', selectedIds[0]);
                let rowData = jQuery("#list").jqGrid('getRowData', selectedIds[i]);
                vendorID = rowData0.vendorID;
                resTypeCode = rowData0.resTypeCode;
                osType = rowData0.osType;
                osVersion = rowData0.osVersion;
                osFeature = rowData0.osFeature;
                if (vendorID != rowData.vendorID) {
                    layer.msg('所选厂商不一致，请重选！', {icon: 7, time: 1500});
                    return;
                } else if (resTypeCode != rowData.resTypeCode) {
                    layer.msg('所选设备类型不一致，请重选！', {icon: 7, time: 1500});
                    return;
                } else if (osType != rowData.osType) {
                    layer.msg('所选操作系统类型不一致，请重选！', {icon: 7, time: 1500});
                    return;
                } else if (osVersion != rowData.osVersion) {
                    layer.msg('所选系统版本不一致，请重选！', {icon: 7, time: 1500});
                    return;
                } else if (osFeature != rowData.osFeature) {
                    layer.msg('所选系统特征不一致，请重选！', {icon: 7, time: 1500});
                    return;
                }
                ids += rowData.resId + ";";
            }
            //layer_show("关联监测指标", '${ctx}/ack/resources/addAckCheckItems.do?ids=' + ids + '&vendorID=' + vendorID + '&resTypeCode=' + resTypeCode + '&osType=' + osType + '&osVersion=' + osVersion + '&osFeature=' + osFeature, 800, 500)
            var url = '${ctx}/ack/resources/addAckCheckItems.do?ids=' + ids + '&vendorID=' + vendorID + '&resTypeCode=' + resTypeCode + '&osType=' + osType + '&osVersion=' + osVersion + '&osFeature=' + osFeature;
            window.open(url, '关联监测指标', 'height=500, width=1200, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
        } else {
            layer.msg('请选择监测对象！', {icon: 7, time: 1500});
        }
    }

    //详情
    function onView() {
        var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        var rowData = jQuery("#list").jqGrid('getRowData', selectedId);
        if (selectedId.length > 1) {
            layer.msg('只能选择一条数据进行查看!', {icon: 7, time: 1500});
            layer.msg('请选择一条数据！', {icon: 7, time: 1500});
            return;
        }
        if (rowData.resClassCode == 13) {
            var url = '${ctx}/fas/res/net/devices/view.do?id=' + rowData.resId;
            layer_show('网络资源信息', url, 800, 500);
        } else if (rowData.resClassCode == 11) {
            var url = '${ctx}/fas/res/host/ipHost/view.do?id=' + rowData.resId;
            layer_show('主机设备详情', url, 800, 500);
        }
    }

    function onRefresh() {
        window.location.reload();
    }
</script>
</body>
</html>











