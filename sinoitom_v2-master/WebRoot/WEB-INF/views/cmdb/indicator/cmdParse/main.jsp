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
    <title>指标采集命令--指标解析规则</title>

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
    <button class="btn btn-white btn-default btn-bold" onclick="onSaveAs()">
        <i class="ace-icon fa  fa-plus bigger-120 default"></i>
        另存为
    </button>
    <shiro:hasPermission name="parseRuleDel">
        <button class="btn btn-white btn-warning btn-bold" onclick="onDelete()">
            <i class="ace-icon fa fa-times bigger-120"></i>
            删除
        </button>
    </shiro:hasPermission>
    <button class="btn btn-white btn-purple btn-bold" onclick="onAudit()">
        <i class="ace-icon fa fa-check-square-o bigger-120 "></i>
        审核
    </button>
    <button class="btn btn-white btn-danger btn-bold" onclick="onDetail()">
        <i class="ace-icon fa fa-file-text-o bigger-120 danger"></i>
        详情
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


    var gridHeight;
    $(function () {
        gridHeight = $(document).height() - 112 - $('#btn').height();
        pageInit();
        resize();  //自动调整页面Grid
    });

    function pageInit() {
        jQuery("#list").jqGrid({
            data:  ${jsonListData},
            //  datatype:从服务器端返回的数据类型，默认xml。可选类型：xml，local(客户端数据（数组）)，json，jsonnp，script，xmlstring，jsonstring，clientside
            datatype: "local",


            colNames: ['产品厂商', 'id', '产品分类', '产品类型', '产品型号', '操作系统类型', '软件版本', '软件特征', '指标组', '巡检指标', '指标名称', '解析方式', '取值类型', '长度', '小数位数', '度量单位', '巡检命令ID', '巡检命令', '审核状态', '审核状态'],
            colModel: [
                {name: 'vendorName', index: 'vendorName', width: 50},
                {name: 'id', index: 'id', width: 30, hidden: true},
                {name: 'resClassName', index: 'devClassName', width: 50},
                {name: 'resTypeName', index: 'devTypeName', width: 50},
                {name: 'prodModel', index: 'prodModel', width: 50},
                {name: 'osType', index: 'osType', width: 50},
                {name: 'osVersion', index: 'osVersion', width: 50},
                {name: 'osFeature', index: 'osFeature', width: 50},
                {name: 'indGroupName', index: 'indGroupName', width: 50},
                {name: 'indItemEnName', index: 'indItemEnName', width: 50},
                {name: 'indItemName', index: 'indItemName', width: 50},
                {
                    name: 'parseMode', index: 'parseMode', width: 50,
                    formatter: function (cellvalue, options, row) {
                        var html = '';
                        if (row.parseMode == "1") {
                            html = '正则表达式';
                        } else if (row.parseMode == "2") {
                            html = '字符串匹配';
                        } else if (row.parseMode == "3") {
                            html = '字符分割';
                        }
                        return html;
                    }
                },
                {name: 'valueType', index: 'valueType', width: 50, align: "center"},
                {name: 'length', index: 'length', width: 50},
                {name: 'decimals', index: 'decimals', width: 50},
                {name: 'measureUnit', index: 'measureUnit', width: 50},
                {name: 'prodChkCmdId', index: 'prodChkCmdId', width: 50},
                {name: 'checkCmd', index: 'checkCmd', width: 100},
                {name: 'status', index: 'status', width: 70, hidden: true},
                {
                    name: 'status1', index: 'status1', width: 50,
                    formatter: function (cellvalue, options, row) {
                        var html = '';
                        if (row.status == 1) {
                            html += '<span style="color:blue;">已审核</span>';
                        } else {
                            html += '<span style="color:red;">未审核</span>';
                        }
                        return html;
                    }
                },
            ],
            viewrecords: true,
            rowNum: 50, rowList: [50, 100, 200],
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
            }
        });
        jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});
        $("#t_list").css({"height": 30});
        $("#btn").appendTo("#t_list");
    }

    function onadd() {
        window.localStorage.setItem("flag", "false");
        layer_show("添加", '${ctx}/cmdb/indicator/resIndCmdParse/toAdd.do?pid=' + '${pid}', 800, 550)
    }

    function onSaveAs() {

        var rowid = jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
        if (rowid == "" || rowid == null) {
            layer.msg('请选择一条数据！', {icon: 7, time: 1500});
            return;
        }
        saveAsRow(rowid);
    }

    function saveAsRow(id) {
        layer_show("另存为", '${ctx}/cmdb/indicator/resIndCmdParse/toSaveAs.do?id=' + id, 1000, 550)
    }

    function saveOK(flag) {
        if (flag) {
            layer.closeAll();
            layer.alert("保存成功！", function(){setInterval(function(){window.location.reload()},1000)},{icon: 1, time: 1000});
        }
    }

    function onDelete() {
        var ids = "";
        var count1 = 0;
        var count2 = 0;
        var selectedIds = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (selectedIds == "" || selectedIds == null) {
            layer.msg('请选择一条数据！', {icon: 7, time: 1500});
            return;
        }

        for (var i = 0; i < selectedIds.length; i++) {
            var rowData = jQuery("#list").jqGrid('getRowData', selectedIds[i]);
            if (rowData.status == "0") {
                ids += selectedIds[i] + ",";
                count1++;
            } else {
                count2++;
            }
        }
        if (count2 > 0 && count1 == 0) {
            layer.msg('所选数据都已经通过审核！', {icon: 7, time: 1500});
            return;
        }
        deleteRow(ids);
    }

    function deleteRow(id) {
        layer.confirm('确认要删除吗？', function (yes) {
            delRow(id)

        });
    }

    function delRow(id) {
        var url = '${ctx}/cmdb/indicator/resIndCmdParse/delete.do?';
        $.ajax({
            url: url,
            type: "POST",
            dataType: 'json',
            data: {"checkItemsIds": id},

            success: function (data) {
                if (data.success == "0") {
                    layer.msg('删除成功!', {icon: 1, time: 1500});
                } else {
                    layer.msg(data.message, {icon: 2, time: 1500});
                }
                window.location.reload();
            }
        });
    }

    function onRefresh() {
        window.location.reload();
    }

    //审核
    function onAudit() {
        var ids = "";
        var count1 = 0;
        var count2 = 0;
        var selectedIds = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (selectedIds == "" || selectedIds == null) {
            layer.msg('请选择一条数据！', {icon: 7, time: 1500});
            return;
        }

        for (var i = 0; i < selectedIds.length; i++) {
            var rowData = jQuery("#list").jqGrid('getRowData', selectedIds[i]);
            if (rowData.status == "0") {
                ids += selectedIds[i] + ",";
                count1++;
            } else {
                count2++;
            }
        }
        if (count2 > 0 && count1 == 0) {
            layer.msg('所选数据都已经通过审核！', {icon: 7, time: 1500});
            return;
        }
        audit(ids);

    }

    function audit(ids) {
        layer.confirm('确认通过审核吗？', function (yes) {
            auditRow(ids);

        });

    }

    function auditRow(ids) {
        var url = '${ctx}/cmdb/indicator/resIndCmdParse/saveAudit.do?checkItemsIds=' + ids;
        $.ajax({
            url: url,
            dataType: 'json',
            success: function (data) {
                if (data.success == '0') {
                    layer.msg('审核成功!', {icon: 1, time: 1500});
                    onRefresh();
                }
            }
        });
    }

    //详情
    function onDetail() {

        var rowid = jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
        var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        var rowData = jQuery("#list").jqGrid('getRowData', selectedId);

        if (selectedId.length > 1) {
            layer.msg('只能选择一条数据进行查看!', {icon: 7, time: 1500});
            return;
        }
        if (rowid != "" && rowid != null) {
            detailRow(rowData.id);
        } else {
            layer.msg('只能选择一条数据进行查看!', {icon: 7, time: 1500});
            return;
        }
    }

    function detailRow(id) {

        layer_show("指标解析规则详情", '${ctx}/cmdb/indicator/resIndCmdParse/detail.do?id=' + id, 800, 500)
    }


</script>
</body>
</html>


