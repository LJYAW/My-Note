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
    <title>用户管理</title>

    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>

    <!-- page specific plugin styles -->
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

    baseUrl = '${ctx}/system/user';

    var gridHeight;
    $(function () {
        gridHeight = $(document).height() - 120 - $('#btn').height();
        pageInit();
        resize();  //自动调整页面Grid
        bootbox.setDefaults("locale", "zh_CN");
    });


    function pageInit() {
        jQuery("#list").jqGrid({
            data: ${jsonListData},
            datatype: "local",
            colNames: ['所属机构','用户类型', '用户帐号', '用户名称',  '操作'],
            colModel: [
            	{name: 'mainOrgan', index: 'mainOrgan', width: 80, align: "left"},
                {name: 'userType', index: 'userType', width: 80, editable: true},
                {name: 'loginName', index: 'loginName', width: 90},
                {name: 'userName', index: 'userName', width: 100},
                
                {
                    width: 80, align: "left",

                    formatter: function (cellvalue, options, row) {
                        var html = '<span style="cursor:pointer "  onclick="editRow(\'' + row.id + '\');">修改</span>';
                        html += ' | <span style="cursor:pointer "  onclick="deleteRow(\'' + row.id + '\');">删除</span>';
                        return html;
                    }
                }
            ],

            viewrecords: true,
            rowNum: 50,
            rowList: [50, 100, 150],
            pager: '#pager',
            height: gridHeight,
            altRows: true,
            toolbar: [true, 'top'],
            multiselect: true,
//             multiboxonly: true,
            loadComplete: function () {
                var table = this;
                updatePagerIcons(table);
            },
// 			 cellEdit:true,  //单元格编辑
// 			 cellsubmit: "clientArray",
// 			 caption : "用户信息" 
        });
        jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});
        $("#t_list").css({"height": 30});
        $("#btn").appendTo("#t_list");

    }


    function delRow(id) {
        alert(id);
    }


    function onadd() {
        layer_show("用户管理", '${ctx}/system/user/add.do', 800, 400)
    }

    function onEdit() {

        var selectRowData;
        selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');

        if (selectRowData.length > 1) {
            layer.msg('只能选择一条要修改的用户!', {icon: 7, time: 1500});
//     		    bootbox.alert({
//        			 message: "只能选择一条要修改的用户!",
//         		size: 'small'
//     			});
    		
    		return;
    	}
    	
        if (selectRowData==""||selectRowData==null) { 
//         	layer.msg('请选择要修改的用户!',{icon: 7,time:1500});
        	 bootbox.alert({
       			 message: "请选择要修改的用户!",
        		 size: 'small'
    			});
            return;
        }
        editRow(selectRowData);

    }

    function editRow(id) {
        layer_show("修改用户信息", '${ctx}/system/user/edit.do?id=' + id, 800, 450)
    }

    function saveOK(action, data) {
        layer.closeAll();
        window.location.reload();
    }

    function onDelete() {
        var selectRowData;
        selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (selectRowData==""||selectRowData==null) { 
        	layer.msg('请选择要删除的用户!',{icon: 7,time:1500});
        	return; 
        }
    	deleteRow(selectRowData);
//         bootbox.confirm("投票已结束，是否直接查看投票结果？", function (re) {
//             if (re) {
//                 deleteRow(selectRowData);
//             }
//         });

    }

    function deleteRow(id) {
        layer.confirm('确认要删除吗？', function (yes) {
            delRow(id);
        });
    }

    function delRow(id) {
        var url = '${ctx}/system/user/delete.do?id=' + id;
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

    function onRefresh() {
        window.location.reload();
    }


    function onMapPower() {
        var selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (selectRowData.length > 1) {
            layer.msg('只能选择一项要操作的用户!', {icon: 7, time: 1500});
            return;
        }

        if (selectRowData == "" || selectRowData == null) {
            layer.msg('请选择要操作的用户!', {icon: 7, time: 1500});
            return;
        }
        mapPower(selectRowData);
    }

    function mapPower(id) {
        layer_show("用户权限分配", baseUrl + '/mapPowerGo.do?id=' + id, 800, 450);
    }

    function onMapRole() {
        var selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (selectRowData.length > 1) {
            layer.msg('只能选择一项要操作的用户!', {icon: 7, time: 1500});
            return;
        }

        if (selectRowData == "" || selectRowData == null) {
            layer.msg('请选择要操作的用户!', {icon: 7, time: 1500});
            return;
        }

        mapRole(selectRowData);
    }

    function mapRole(id) {
        layer_show("用户角色分配", baseUrl + '/mapRoleGo.do?id=' + id, 800, 450);
    }

    function onViewMenu() {
        var selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (selectRowData.length > 1) {
            layer.msg('只能选择一项要查看的用户!', {icon: 7, time: 1500});
            return;
        }

        if (selectRowData == "" || selectRowData == null) {
            layer.msg('请选择要查看的用户!', {icon: 7, time: 1500});
            return;
        }
        viewMenu(selectRowData);
    }

    function viewMenu(id) {
	
        layer_show("用户菜单查看", baseUrl + '/viewMenu.do?id=' + id, 800, 450);
    }

</script>
<body>

<div id="btn" style="margin-top:2px;">
  	<shiro:hasPermission name="user:create">
    <button class="btn btn-white btn-default btn-bold" onclick="onadd()" title="创建用户信息....">
        <i class="ace-icon fa  fa-plus bigger-120 default"></i>
        	添加
    </button>
    </shiro:hasPermission> 
    
    <shiro:hasPermission name="user:update">
    <button class="btn btn-white btn-info btn-bold" onclick="onEdit()">
        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
        修改
    </button>
    
     </shiro:hasPermission> 
    
    <button class="btn btn-white btn-warning btn-bold" onclick="onDelete()">
        <i class="ace-icon fa fa-times bigger-120"></i>
        删除
    </button>
    <button class="btn btn-white btn-success btn-bold" onclick="onRefresh()">
        <i class="ace-icon fa fa-refresh bigger-120"></i>
        刷新
    </button>
    <button class="btn btn-white btn-danger btn-bold" onclick="onMapPower()">
        <i class="ace-icon fa fa-unlock-alt bigger-120"></i>
        权限分配
    </button>
    <button class="btn btn-white btn-yellow btn-bold" onclick="onMapRole()">
        <i class="ace-icon fa fa-users bigger-120"></i>
        角色分配
    </button>
    <button class="btn btn-white btn-info btn-bold" onclick="onViewMenu()">
        <i class="ace-icon fa fa-tasks bigger-120"></i>
        菜单查看
    </button>

</div>

<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li class="active">${titleContent }</li>
    </ul>
</div>

<div class="col-xs-12" style="overflow: hidden;">
    <table id="list"></table>
    <div id="pager"></div>
</div>

</body>
</html>