<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.sino.base.common.Global"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<shiro:hasPermission name="employee:read"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>配置参数</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

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
    	 var gridHeight;
    	
    	$(function (){
    		gridHeight = $(document).height() - 120 - $('#btn').height();
        	pageInit();
        	
    		resize();  //自动调整页面Grid
        	bootbox.setDefaults("locale", "zh_CN");
          	
        });
        
      function pageInit(){
       	jQuery("#list").jqGrid({
	        url: '${ctx}/fas/res/db/index/getOperaStatusData.do?dbsId=${dbsId}',
			datatype: 'json',
            colNames: [ 'id','配置参数','值','备注'],
            colModel:  
	          [	  
	          	   { name: 'id', width: 100, hidden: true,key:true},
		          { name: 'variableName', width: 100, align:'left'},
		          { name:'value', width:40, align:'left'},
		          { name:'remarks', width:200, align:'left'},
	          ], 
	            rownumbers: true,
				rowNum: 50, rowList: [100, 400, 500],
	            pager: '#pager',
	            height: gridHeight,
	            toolbar: [true, 'top'],
	            sortname: 'id',
	            mtype : "post",
	            viewrecords: true,
	            sortorder: "desc",
// 	            multiselect: true,
	            loadComplete: function () {
                	jQuery("#list").jqGrid('setLabel','rn', '序号', {'text-align':'center','vertical-align':'middle'},'');
            	}
         });
         jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});

	     $("#t_list").css({"height": 30});
	     $("#btn").appendTo("#t_list");
     }
        
    </script>
</head>
<body> 
	<div class="col-xs-12" style="overflow: hidden;">
	    <table id="list"></table>
	    <div id="pager"></div>
	</div>
</body>
</html>
</shiro:hasPermission>

