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
    <title>设备提示信息</title>

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
<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
<script src="${ctx }/static/assets/js/resize.js"></script>
<script src="${ctx }/static/assets/js/bootbox.js"></script>

<script type="text/javascript">
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
            colNames: ['访问信息命名','访问工具', '访问端口', '用户类型','用户名称','登录密码','特权模式命令','特权模式口令','操作'],
            colModel: [
            	{name: 'acsUserName', index: 'acsUserName', width: 180},
                {name: 'accessTool', index: 'accessTool', width: 80},
                {name: 'accessPort', index: 'accessPort', width: 60},
                {name: 'userType', index: 'userType', width: 80,
                
                	formatter: function (cellvalue, options, row) {
                        if(row.userType==1){
                          return "特权用户"
                        }else{
                           return "普通用户"
                        }
                    }
                },
                {name: 'userName', index: 'userName', width: 80},
                {name: 'passWord', index: 'passWord', width: 80},
                {name: 'privModeCmd', index: 'privModeCmd', width: 80},
                {name: 'privModePasswd', index: 'privModePasswd', width: 80},
                {
                    width: 80, align: "left",
                    formatter: function (cellvalue, options, row) {
                        var html = '<span style="color:blue;cursor:pointer;"  onclick="editRow(\'' + row.id + '\');">修改</span>';
                        html += ' | <span style="color:blue;cursor:pointer;"  onclick="deleteRow(\'' + row.id + '\');">删除</span>';
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
//          multiboxonly: true,
            loadComplete: function () {
                var table = this;
                updatePagerIcons(table);
            },
        });
        jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});
        $("#t_list").css({"height": 30});
        $("#btn").appendTo("#t_list");

    }
        
       
        
        function onAdd()
        {
        	var url = '${ctx}/devices/accessUser/add.do';
     		layer_show('添加设备访问信息', url,800, 530);
        }

        function onEdit()
        {
            var selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (selectRowData==""||selectRowData==null) { 
            	layer.msg('请选择要修改的数据！', {icon: 7, time: 1500});
            	return; 
            }
        	editRow(selectRowData);
        }
        
        function editRow(id)
        {
        	var url = '${ctx}/devices/accessUser/edit.do?id='+id;
     		layer_show('修改设备访问信息', url, 800, 530);    
        }
        
        function saveOK()
        {
        	window.location.reload();
        }
        
        function onDelete()
        {
           var selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (selectRowData==""||selectRowData==null) { 
            	layer.msg('请选择要删除的数据！', {icon: 7, time: 1500});
            	return; 
            }
        	deleteRow(selectRowData);
        }
        
        function deleteRow(id)
        {
        	layer.confirm('确认要删除吗？', function (yes) {
            	delRow(id);
        	 });
        }
        
        function delRow(id)
        {
        	var url ='${ctx}/devices/accessUser/delete.do?id='+id;
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
        			if (data.result == "success") {
	                    layer.msg('删除成功!', {icon: 1, time: 1500});
	                } else {
	                    layer.msg('删除失败!', {icon: 2, time: 1500});
	                }
	                window.location.reload();
        		}
        	});        			
        }

        
        function onRefresh(){
        	window.location.reload();
        }

    </script>
</head>
<body>

<div id="btn" style="margin-top:2px;">

   <shiro:hasPermission name="DevAcsUser_Add">
    <button class="btn btn-white btn-default btn-bold" onclick="onAdd()">
    <i class="ace-icon fa  fa-plus bigger-120 default"></i>
        	添加
    </button>
    </shiro:hasPermission> 
    
    <shiro:hasPermission name="DevAcsUser_Modify">
    <button class="btn btn-white btn-info btn-bold" onclick="onEdit()">
     <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
        	修改
    </button>
    </shiro:hasPermission> 
    
    <shiro:hasPermission name="DevAcsUser_Delete">
    <button class="btn btn-white btn-warning btn-bold" onclick="onDelete()">
        <i class="ace-icon fa fa-trash-o bigger-120"></i>
        	删除
    </button>
    </shiro:hasPermission> 
    
    <shiro:hasPermission name="DevAcsUser_Audit">
    <button class="btn btn-white btn-purple btn-bold" onclick="onAudit()">
		<i class="ace-icon fa fa-check-square-o bigger-120 "></i>
		审核
	</button>
	</shiro:hasPermission> 
	
	
<!--        <button class="btn btn-white btn-info btn-bold" onclick="onSearch()"> -->
<!--         <i class="ace-icon fa fa-tasks bigger-120"></i> -->
<!--        	查询 -->
<!--     </button> -->
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

