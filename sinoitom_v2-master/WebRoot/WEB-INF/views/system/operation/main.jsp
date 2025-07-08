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
    <title>用户操作记录</title>

		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css" />
		<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
		
		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css" />
		<link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet" />
		<!-- ace styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />
		<!-- ace settings handler -->
		<script src="${ctx }/static/assets/js/ace-extra.min.js"></script>
		
		<script src="${ctx }/static/assets/js/jquery.min.js"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>
	    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script> 
		
		<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
		<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
		<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
		<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
		<script src="${ctx }/static/assets/js/resize.js"></script>
		<script src="${ctx }/js/dateFormat.js"></script>
	</head>
<script type="text/javascript">

    baseUrl = '${ctx}/system/userOperation';

    var gridHeight;
    $(function () {
        gridHeight = $(document).height() - 120 - $('#btn').height();
        pageInit();
        resize();  //自动调整页面Grid
        bootbox.setDefaults("locale", "zh_CN");
    });


    function pageInit() {
        jQuery("#list").jqGrid({
            url:'${ctx}/system/userOperation/searchAjaxPage.do',
            datatype: "json",
            colModel: [
                {label:'ID',name: 'Id', index: 'Id', width: 60,hidden:true},
            	{label:'操作人',name: 'userName', index: 'userName', width: 60, align: "left"},
                {label:'功能菜单',name: 'funMenu', index: 'funMenu', width: 200},
                {label:'操作事项',name: 'operation', index: 'operation', width: 60},
                {label:'操作结果',name: 'message', index: 'message', width: 60},
                {label:'操作时间',name: 'opTime', index: 'opTime', width: 100,
                   formatter:function(cellvalue, options, row){
    	        	     return getFormatDateByLong(row.opTime,"yyyy-MM-dd hh:mm:ss");
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
            loadComplete: function () {
                var table = this;
                updatePagerIcons(table);
            },
        });
        jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});
        $("#t_list").css({"height": 30});
        $("#btn").appendTo("#t_list");

    }


    function onView() {
        var selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (selectRowData.length > 1) {
            layer.msg('只能选择一项要查看的操作记录!', {icon: 7, time: 1500});
            return;
        }

        if (selectRowData == "" || selectRowData == null) {
            layer.msg('请选择要查看的操作记录!', {icon: 7, time: 1500});
            return;
        }
        var rowData = jQuery("#list").jqGrid('getRowData',selectRowData[0]);
        viewMenu(rowData.Id);
    }

    function viewMenu(id) {
	
        layer_show("用户操作记录", baseUrl + '/onView.do?id=' + id, 800, 450);
    }

 function onRefresh(){
    	window.location.reload();
    }
</script>
<body>

<div id="btn" style="margin-top:2px;">

   <button class="btn btn-white btn-info btn-bold" onclick="onView()">
        <i class="ace-icon fa fa-file-text-o bigger-120"></i>
          详情
    </button>
<button class="btn btn-white btn-success btn-bold" onclick="onRefresh()">
		<i class="ace-icon fa fa-refresh bigger-120"></i>
		刷新
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