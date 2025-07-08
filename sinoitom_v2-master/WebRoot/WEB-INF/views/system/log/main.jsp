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
    <title>日志管理</title>

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
	
    <script type="text/javascript">
    
    var gridHeight;
    $(function () {
        gridHeight = $(document).height() - 120 - $('#btn').height();
        pageInit();
        resize();  //自动调整页面Grid
        bootbox.setDefaults("locale", "zh_CN");
    });
    
    function pageInit(){
    	jQuery("#list").jqGrid( {
<%--    	url :' ${ctx }/system/queryDB/getData.do',--%>
		 data: ${jsonListData},
    	 datatype : "local", 
    	 colNames : [ '日志时间', '日志标题', '日志内容', '用户帐号', '用户名称', '客户端IP', '操作' ],
    	 colModel : [ 
    	        	 {name : 'createTime',index : 'createTime',width : 200,
    	        	   formatter:function(cellvalue, options, row){
    	        	     return getFormatDateByLong(row.createTime,"yyyy-MM-dd hh:mm:ss");
    	        	   }
    	        	 },
    	        	 {name : 'logTitle',index : 'logTitle',width : 150}, 
    	        	 {name : 'logContent',index : 'logContent',width : 450},
    	        	 {name : 'userAccount',index : 'userAccount',width: 100},
    	        	 {name : 'userName',index : 'userName',width : 100},
    	        	 {name : 'clientAddress',index : 'clientAddress',width: 150,align : "left"},
    	        	 {width : 100,align : "left",
    	        		   
    	        	     formatter:function(cellvalue, options, row){
    	        	        var html = '<span style="cursor:pointer " onclick="ViewRow(\'' + row.id + '\');">查看</span>';
    	                        html += ' | <span style="cursor:pointer " onclick="DelRow(\'' + row.id + '\');">删除</span>';
    	                   return html;
    	        	     }
    	        	 }
    	        	 ],
    	        	 
    	        	 rowNum : 100, rowList : [ 100, 200, 500 ], 
    	        	 pager : '#pager',
    	        	 height: gridHeight,
    	        	 toolbar:[true,'top'],
    	        	 sortname : 'id',
    	        	 viewrecords : true, 
    	        	 sortorder : "desc", 
    	        	 multiselect : true,
    	        	 loadComplete: function () {
		                var table = this;
		                updatePagerIcons(table);
            			},

    	        	});
    	
    	jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
    	
    	$("#t_list").css({"height":30});
    	$("#btn").appendTo("#t_list");
    	
    }
    
    function DelRow(id){
    	
    	layer.confirm('是否确认删除所选日志信息？', function (yes) {
            delRow(id);
        });
    	
	}
    
    function ViewRow(id){
    	viewRow(id);
	}
    
    function onView()
    {
        //var id = getSingleSelectId('修改');
//         if (!selectRowData) { 
//         	window.parent.$.ligerDialog.error('请选择一行数据！'); 
//         	return; 
//         }

		var selectRowData;
    	selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
    	
    	
    	if(selectRowData.length>1){
    		layer.msg('只能选择一条日志信息!', {icon: 7, time: 1500});
    		return;
    	}
    	
        if (selectRowData==""||selectRowData==null) { 
        	layer.msg('请选择一条日志信息!', {icon: 7, time: 1500});
        	return; 
        }

        if( selectRowData != null && selectRowData != '' ){
        	viewRow(selectRowData);
        }
    }
    
    function viewRow(id)
    {
<%--			window.open("${ctx}/system/log/view.do?id="+id,"_blank","top=150,left=300,width=700,height=500");--%>
			layer_show("日志详情",'${ctx}/system/log/view.do?id='+id,680,400)
    }
    
    function onDelete()
    {
    	var selectRowData;
    	selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (selectRowData==""||selectRowData==null) { 
        	layer.msg('请选择一条日志信息!', {icon: 7, time: 1500});
        	return; 
        }
    	deleteRow(selectRowData);
    }
    
    function deleteRow(id)
    {
    	layer.confirm('是否确认删除所选日志信息？', function (yes) {
            delRow(id);
        });
    }
    
    function delRow(id)
    {
    	var url = '${ctx}/system/employee/delete.do?id='+id;
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
    			//doData(data);
    		}
    	});        			
    }
    
    function onRefresh(){
    	window.location.reload();
    }

    </script>
    
    <body>

     	
						
							<div id="btn" style="margin-top:2px;">
									<button class="btn btn-white btn-danger btn-bold" onclick="onView()">
										<i class="ace-icon fa fa-file-text-o bigger-120"></i>
										详情
									</button>
									<button class="btn btn-white btn-warning btn-bold" onclick="onDelete()">
										<i class="ace-icon fa fa-times bigger-120"></i>
										删除
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

