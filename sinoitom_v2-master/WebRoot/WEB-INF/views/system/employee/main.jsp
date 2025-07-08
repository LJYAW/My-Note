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
    <title>职员管理</title>

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
<script type="text/javascript">
    var dlgSearch = null;
    var gridHeight;
    $(function () {
        gridHeight = $(document).height() - 120 - $('#btn').height();
        pageInit();
        resize();  //自动调整页面Grid
        bootbox.setDefaults("locale", "zh_CN");
    });

    function pageInit() {
        jQuery("#list").jqGrid({
            //url : ctx+'/JSONData',
            data: ${jsonListData},
            datatype: "local",
            colNames: ['机构名称', '部门', '姓名', '性别', '工号', '电话号码', '手机号码', '电子邮箱'],
            colModel: [
                {name: 'organ', index: 'organ', width: 80},
                {name: 'mainDpt', index: 'mainDpt', width: 80},
                {name: 'name', index: 'name', width: 90},
                {name: 'sex', index: 'sex', width: 50},
                {name: 'workNo', index: 'workNo', width: 80},
                {name: 'phone', index: 'phone', width: 80},
                {name: 'mobile', index: 'mobile', width: 90},
                {name: 'email', index: 'email', width: 80, align: "left"},
            ],

            rowNum: 20, rowList: [20, 50, 100],
            pager: '#pager',
            height: gridHeight,
            toolbar: [true, 'top'],
            sortname: 'id',
            //mtype : "post",
            viewrecords: true,
            sortorder: "desc",
            multiselect: true,
            //caption : "职员管理" ,

        });
        jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});


        $("#t_list").css({"height": 30});
        $("#btn").appendTo("#t_list");


    }

    function onAdd() {

        layer_show("添加职员", '${ctx}/system/employee/add.do?grpId=${group.empId}', 800, 450)

    }


    function onEdit() {
        var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (selectedId.length > 1) {
            layer.msg('只能选择一条要修改的职员信息!', {icon: 7, time: 1500});
            return;
        }

        if (selectedId == "" || selectedId == null) {
            layer.msg('请选择一条要修改的职员信息!', {icon: 7, time: 1500});
            return;
        }
        editRow(selectedId);

    }

    function editRow(id) {
        layer_show("修改职员信息", '${ctx}/system/employee/edit.do?id=' + id, 800, 450)
    }

    function saveOK(action, data) {
        layer.closeAll();
        window.location.reload();
    }

    function onDelete() {
        var selectRowData;
        selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (selectRowData == "" || selectRowData == null) {
            layer.msg('请选择一条要删除的职员信息!', {icon: 7, time: 1500});
            return;
        }
        deleteRow(selectRowData);
    }

    function deleteRow(id) {

        layer.confirm('是否确认删除所选职员信息？', function (yes) {
            delRow(id);
        });

    }

    function delRow(id) {
        var url = '${ctx}/system/employee/delete.do?id=' + id;
        $.ajax({
            url: url,
            dataType: 'json',

            success: function (data) {

                if (data.result == "success") {
                    layer.msg('删除成功!', {icon: 1, time: 1500});
                } else {
                    layer.msg('删除失败!', {icon: 2, time: 1500});
                }

                window.location.reload();
            }
        });
    }


    function onSearch() {
        layer_show("查询", '${ctx}/system/employee/onSearch.do', 800, 270)
    }


    function reloadGrid(data) {
        layer.closeAll();
        jQuery('#list').jqGrid('clearGridData');
        jQuery('#list').jqGrid(
            'setGridParam', {
                datatype: 'local',
                data: data
            }).trigger('reloadGrid');

    }

    function onRefresh() {
        window.location.reload();
    }


    function onImport() {
        layer_show("导入职员信息", '${ctx}/system/employee/importGo.do', 800, 400)
    }


</script>

<body>
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
	    <ul class="breadcrumb">
	        <li class="active">${titleContent }</li>
	    </ul>
	</div>
	
	<div id="btn" style="margin-top:2px;">
	    <button class="btn btn-white btn-default btn-bold" onclick="onAdd()">
	        <i class="ace-icon fa  fa-plus bigger-120"></i>
	        添加
	    </button>
	    <button class="btn btn-white btn-info btn-bold" onclick="onEdit()">
	        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
	        修改
	    </button>
	    <button class="btn btn-white btn-warning btn-bold" onclick="onDelete()">
	        <i class="ace-icon fa fa-times bigger-120"></i>
	        删除
	    </button>
	    <button class="btn btn-white btn-primary btn-bold" onclick="onSearch()">
	        <i class="ace-icon fa fa-search bigger-120"></i>
	        查询
	    </button>
	
	    <%--							<button class="btn btn-white btn-info btn-bold" onclick="onImport()">--%>
	    <%--								<i class="ace-icon fa fa-sign-in bigger-120"></i>--%>
	    <%--								导入--%>
	    <%--							</button>--%>
	    <button class="btn btn-white btn-success btn-bold" onclick="onRefresh()">
	        <i class="ace-icon fa fa-refresh bigger-120"></i>
	        刷新
	    </button>
	
	</div>
	
	<div class="col-xs-12" style="overflow: hidden;">
	    <table id="list"></table>
	    <div id="pager"></div>
	</div>
</body>
</html>
