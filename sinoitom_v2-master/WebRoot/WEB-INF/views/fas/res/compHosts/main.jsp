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
    <title>宿主机 </title>

    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>
    <link href="${ctx}/css/common.css" rel="stylesheet" type="text/css"/>

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css"/>
    <link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet"/>
    <!-- ace styles -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/layer.css"/>
    <!-- ace settings handler -->
    <script src="${ctx }/static/assets/js/ace-extra.min.js"></script>
    <script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
    <script src="${ctx }/static/assets/js/layer.js"></script>
    <script src="${ctx }/static/assets/js/H-ui.admin.js"></script>
    <script src="${ctx }/static/assets/js/bootstrap.min.js"></script>

    <script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
    <script src="${ctx }/static/assets/js/resize.js"></script>

    <style type="text/css">
        .ui-jqgrid .ui-subgrid .ui-jqgrid .ui-jqgrid-bdiv {
            height: auto !important;
            max-height: 500px;
            margin-bottom: 4px;
            border-top-width: 0;
            border-bottom: 1px solid #E1E1E1
        }
    </style>
</head>
<script type="text/javascript">
    var gridHeight;
    var dlgSearch = null;
    var count;

    $(function () {
        gridHeight = $(document).height() - 120 - $('#btn').height();
        pageInit();
        resize();  //自动调整页面Grid
    });

    function pageInit() {
        jQuery("#list").jqGrid({
            data:  ${jsonListData},
            datatype: "local",

            colModel: [
                {label: '机构名称', name: 'orgName', index: 'orgName', width: 100},
                {label: '管理IP地址', name: 'ipAddress', index: 'ipAddress', width: 100},
                {label: '资源类型', name: 'resTypeName', index: 'resTypeName', width: 70},
                {
                    label: '主机类型', name: 'hostType', index: 'hostType', width: 70,
                    formatter: function (cellvalue, options, row) {
                        var html = '';
                        if (row.hostType == "0") {
                            html += '<span >虚拟机</span>';
                        } else if (row.hostType == "1") {
                            html += '<span>物理机</span>';
                        } else if (row.hostType == "2") {
                            html += '<span>宿主机</span>';
                        } else if (row.hostType == "3") {
                            html += '<span>集群</span>';
                        }
                        return html;
                    }
                },
                {label: '操作系统分类', name: 'osClass', index: 'osClass', width: 80},
                {label: '操作系统版本', name: 'osVersion', index: 'osVersion', width: 140},
                {label: '操作系统类型', name: 'osType', index: 'osType', width: 80},
                {label: '版本分类', name: 'osVersionType', index: 'osVersionType', width: 60},
                {label: '版本号', name: 'osRelease', index: 'osRelease', width: 60},
                {label: '访问方式', name: 'accessmode', index: 'accessmode', width: 90},
                {label: '用户登录ID', name: 'devAcsUserId', index: 'devAcsUserId', width: 65, hidden: true},
                {
                    label: '用户名称', name: 'userName', index: 'userName', width: 60, hidden: true
                }],

            viewrecords: true,
            rowNum: 50, rowList: [50, 100, 150],
            pager: '#pager',
            height: gridHeight,
            altRows: true,
            toolbar: [true, 'top'],
            multiselect: true,
            multiboxonly: true,


            subGrid: true,  // (1)开启子表格支持
            subGridOptions: {
                plusicon: "ace-icon fa fa-angle-double-down",  //收缩效果
                minusicon: "ace-icon fa fa-angle-double-up"  //展开效果
                //	openicon : "ace-icon fa fa-chevron-right center orange"
            },
            subGridRowExpanded: function (subgrid_id, row_id) {  // (2)子表格容器的id和需要展开子表格的行id，将传入此事件函数

                var rowData = $("#list").jqGrid('getRowData', row_id);//通过索引获取当前行对象
                subgrid_table_id = subgrid_id + "_t";   // (3)根据subgrid_id定义对应的子表格的table的id
                var subgrid_pager_id;
                subgrid_pager_id = subgrid_id + "_pager"  // (4)根据subgrid_id定义对应的子表格的pager的id
                // (5)动态添加子报表的table和pager
                $("#" + subgrid_id).html("<table id='" + subgrid_table_id + "' ></table><div id='" + subgrid_pager_id + "' ></div>");
                // alert(subgrid_table_id+"***");
                // (6)创建jqGrid对象
                $("#" + subgrid_table_id).jqGrid({

                    datatype: "json",
                    // data:mydata,
                    url: ' ${ctx}/fas/res/compHosts/getResHostsByHostId.do?id=' + row_id,
                    //colNames : [ 'Id', '服务器IP', 'PID', '协议号', '协议名称', '服务端口', '应用服务名称', '服务分类', '服务命令', '操作' ],
                    colModel: [
                        {label: '资源类型', name: 'resTypeName', index: 'resTypeName', width: 70},
                        {
                            label: '服务器类型', name: 'hostType', index: 'hostType', width: 70,
                            formatter: function (cellvalue, options, row) {
                                var html = '';
                                if (row.hostType == "0") {
                                    html += '<span >虚拟机</span>';
                                } else if (row.hostType == "1") {
                                    html += '<span>物理机</span>';
                                } else if (row.hostType == "2") {
                                    html += '<span>宿主机</span>';
                                } else if (row.hostType == "3") {
                                    html += '<span>集群</span>';
                                }
                                return html;
                            }
                        },
                        {label: '服务器IP', name: 'ipAddress', index: 'ipAddress', width: 80},
                        {label: '虚机名称', name: 'vmName', index: 'vmName', width: 120},
                        {label: '操作系统分类', name: 'osClass', index: 'osClass', width: 80},
                        {label: '操作系统版本', name: 'osVersion', index: 'osVersion', width: 140},
                        {label: '操作系统类型', name: 'osType', index: 'osType', width: 80},
                        {label: '版本分类', name: 'osVersionType', index: 'osVersionType', width: 65},
                        {label: '版本号', name: 'osRelease', index: 'osRelease', width: 90},
                        // {label:'访问方式',name : 'accessmode',index : 'accessmode',width : 65},
                        {label: '用户登录ID', name: 'devAcsUserId', index: 'devAcsUserId', width: 65, hidden: true},
                        {label: '用户名称', name: 'userName', index: 'userName', width: 60, hidden: true},
                        {
                            label: '操作', width: 65, sortable: false,
                            formatter: function (cellvalue, options, row) {
                                return '<span style="color:blue;cursor:pointer;" onclick="getVMInfo(\'' + row.id + '\');">获取配置信息</span>';
                            }

                        }],
                    // viewrecords : true,//定义是否要显示总记录数
                    rowNum: -1,//在grid上显示记录条数，这个参数是要被传递到后台(rowNum:-1为显示全部长度)
                    // pager : '#'+subgrid_pager_id,//定义翻页用的导航栏，必须是有效的html元素。翻页工具栏可以放置在html页面任意位置
                    sortname: 'ipAddress',//默认的排序列。可以是列名称或者是一个数字，这个参数会被提交到后台
                    // rowList:[50,100,150],//一个下拉选择框，用来改变显示记录数，当选择时会覆盖rowNum参数传递到后台
                    altRows: true,//设置表格 zebra-striped（斑马条纹）值,如果是false要重新加载数据
                    autowidth: true, //表宽自动调整
                    //multiselect : true,
                    //multiboxonly: true,
                    loadComplete: function () {
//     								var table = this;
//     								updatePagerIcons(table);

                        var curRowDatas = jQuery("#" + subgrid_table_id).jqGrid('getRowData');
                        // alert(curRowDatas);
                        for (var i = 0; i < curRowDatas.length; i++) {
                            if (curRowDatas[i].reachability != "reachable") {
                                $("#" + subgrid_table_id).find("input[id='jqg_" + subgrid_table_id + "_" + curRowDatas[i].id + "']").hover(function () {
                                    $(this).css("cursor", "url('../../img/no.png'),pointer")
                                });
                            }
                        }

                    },
                    onSelectRow: function (rowId, status) {
                        if (status) {
                            count++;
                        } else {
                            if (count > 0) {
                                count--;
                            }
                        }
                        var row = jQuery("#" + subgrid_table_id).jqGrid('getRowData', rowId);
                        if (row.reachability != "reachable") {
                            $("#" + subgrid_table_id).jqGrid("setSelection", rowId, false);
                        }
                    },

                    onSelectAll: function (rowId, status) {
                        if (!status) {
                            count = 0;
                        } else {
                            var rowdatas = jQuery("#" + subgrid_table_id).jqGrid('getGridParam', 'selarrrow');
                            count = rowdatas.length;
                        }
                    }

                });
            }
        });
        jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});
        $("#t_list").css({"height": 30});
        $("#btn").appendTo("#t_list");
    }

    // $("#bnSearch").click(f_bnSearch);
    $("#bnReset").click(function () {
        window.$("input[ltype='text'][name*='filter_']").val("");
        window.$("select[name*='filter_']").val("");
    });


    /*
            实现 表单分页多选
        即利用onCheckRow将选中的行记忆下来，并利用isChecked将记忆下来的行初始化选中
    */
    var checkedItem = [];

    function f_onCheckAllRow(checked) {
        for (var rowid in this.records) {
            if (checked)
                addCheckedItem(this.records[rowid]['id']);
            else
                removeCheckedItem(this.records[rowid]['id']);
        }
    }

    function findCheckedItem(ItemID) {
        for (var i = 0; i < checkedItem.length; i++) {
            if (checkedItem[i] == ItemID) return i;
        }
        return -1;
    }

    function addCheckedItem(ItemID) {
        if (findCheckedItem(ItemID) == -1)
            checkedItem.push(ItemID);
    }

    function removeCheckedItem(ItemID) {
        var i = findCheckedItem(ItemID);
        if (i == -1) return;
        checkedItem.splice(i, 1);
    }

    function f_isChecked(rowdata) {
        if (findCheckedItem(rowdata.id) == -1)
            return false;
        return true;
    }

    function f_onCheckRow(checked, data) {
        if (checked) addCheckedItem(data.id);
        else removeCheckedItem(data.id);
    }

    function f_getChecked() {
        alert(checkedItem.join(','));
    }


    function loadData() {
        selectRowData = null;
        gridMain.loadData();
    }


    function onHostCompAdd() {
        var url = '${ctx}/fas/res/compHosts/hostCompAdd.do';
        layer_show('添加宿主机', url, 900, 450);
    }

    function getVMInfo(id) {
        var url = '${ctx}/fas/res/compHosts/getVMInfo.do?id=' + id;
        layer_show('获取配置信息', url, 900, 400);
    }


    function saveOK(action, data) {
        onRefresh();
    }

    function saveOK() {
        onRefresh();
    }

    function multiSaveOK(data) {
        onRefresh();
    }

    //删除
    function onDelete() {
        var id = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (id.length == 0) {
            layer.msg('请先选择一条资源数据！', {icon: 7, time: 1500});
        } else if (id.length > 1) {
            layer.msg('只能选择一条资源数据!', {icon: 7, time: 1500});
        } else {
            deleteRow(id);
        }
    }

    function deleteRow(id) {
        layer.confirm('注意：此操作会同时删除宿主机下的所有虚拟机，是否确认？', function (yes) {
            delRow(id);
        });
    }

    function delRow(id) {
        var url = '${ctx}/fas/res/compHosts/delete.do?id=' + id;
        $.ajax({
            url: url,
            dataType: 'json',
            success: function (data) {
                if (data.result == 'success') {
                    onRefresh();
                }
            }
        });
    }

    function onRefresh() {
        window.location.reload();
    }


</script>
<body>
<div id="btn" style="margin-top:2px;">
    <button class="btn btn-white btn-default btn-bold" onclick="onHostCompAdd()">
        <i class="ace-icon  fa fa-plus  green"></i>
        VM监控添加
    </button>
    <button class="btn btn-white btn-warning btn-bold" onclick="onDelete()">
        <i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
        删除
    </button>
    <button class="btn btn-white btn-success btn-bold" onclick="onRefresh()">
        <i class="ace-icon fa fa-refresh bigger-120 green"></i>
        刷新
    </button>
</div>

<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li class="active">${titleContent }</li>
    </ul>
</div>

<div class="col-xs-12" style="overflow: hidden;position:relative;z-index:1;">
    <table id="list"></table>
    <div id="pager"></div>
</div>

<div id="trTxt" class="dN"
     style="background-color:#FFDEAD; border:0px;top:300px; left:500px; position:absolute; z-index:4;height:31px;line-heigth:31px;text-align:left">
    <div align="right">
			<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#"/>&nbsp;&nbsp;获取服务进程中，请稍候...
           		</span>
    </div>

</div>

<div id="trTxt1" class="dN"
     style="background-color:#FFDEAD; border:0px;top:300px; left:500px; position:absolute; z-index:4;height:31px;line-heigth:31px;text-align:left">
    <div align="right">
			<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#"/>&nbsp;&nbsp;获取性能指标中，请稍候...
           		</span>
    </div>

</div>

<div id="trTxt2" class="dN"
     style="background-color:#FFDEAD; border:0px;top:300px; left:500px; position:absolute; z-index:4;height:31px;line-heigth:31px;text-align:left">
    <div align="right">
			<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#"/>&nbsp;&nbsp;Host资源同步中，请稍候...
           		</span>
    </div>

</div>

</body>
</html>
