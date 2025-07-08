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
<title>拓扑图管理</title>
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
<script src="${ctx }/js/dateFormat.js"></script>
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
	        data: ${jsonData},
            datatype: "local",
            colModel:  
	          [
	          {label:'id', name: 'id', width: 100, hidden: true},
	          {label:'拓扑图名称', name:'graphName', width:80, align:'left',
	              formatter: function (cellvalue, options, row) {
                        var html = '<span style="cursor:pointer;color:#00F" onclick="showTopo(\'' + row.id + '\');">'+row.graphName+'</span>';
                        return html;
                    }
	            },
	           	{label:'拓扑图说明', name:'graphDescr', width:80, align:'left'},
	            {label:'缺省图',  name:'flag', width:40, align:'left',
	            	formatter:function(cellvalue, options, rowObject){
						var html='';
						if(cellvalue==1){
						 	html += '<span>缺省图</span>';
						} else {
							html += '<span>其它</span>';
						} 
						return html;
					}
	            },
	            {label:'种子节点', name:'SeedNodeIp', width:40, align:'left'},
	            {label:'背景图信息', name:'bgPicture', width:40, align:'left',
	            	formatter: function (cellvalue, options, row) {
                        var html = '<span style="cursor:pointer;color:#00F" onclick="showbgPicture(\'' + row.id + '\',\'' + row.bgPicture + '\');">'+row.bgPicture+'</span>';
                        return html;
                    }
	            },
	            {label:'创建人', name:'creator', width:50, align:'left'},
	            {label:'创建时间', name:'createTime', width:50, align:'center',
	            	formatter: function (cellValue, options, row) {
                        return getSmpFormatDateByLong(row.createTime, true)
                    }
	            }
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
         });
         jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});

	     $("#t_list").css({"height": 30});
	     $("#btn").appendTo("#t_list");
     }
    
//     拓扑搜索
      function onSearchTopo()
        {   
        	var url = '${ctx}/topo/graphManage/topoSearch.do';
     		layer_show('拓扑搜索',url,750, 575 );
        }

        
// 		修改
        function onEdit(){
			var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
	        if (selectedId.length > 1) {
	            layer.msg('只能选择一条要修改的信息!', {icon: 7, time: 1500});
	            return;
	        }
	
	        if (selectedId == "" || selectedId == null) {
	            layer.msg('请选择一条要修改的信息!', {icon: 7, time: 1500});
	            return;
	        }
        	editRow(selectedId);
        }
        
        function editRow(id){   
        	var url = '${ctx}/topo/graphManage/edit.do?id='+id;
     		layer_show("修改", url, 800, 450);
        }

        function saveOK(){
        	layer.closeAll();
        	window.location.reload();
        }
        
//         删除
        function onDelete(){
        	var selectRowData;
	        selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
	        if (selectRowData == "" || selectRowData == null) {
	            layer.msg('请选择一条要删除的信息!', {icon: 7, time: 1500});
	            return;
	        }	
	        
	        layer.confirm('是否确认删除所选信息？', function (yes) {
	            deleteDate(selectRowData);
	        });
        }
        
      //删除
  	function deleteDate(id){
       var url = '${ctx}/topo/graphManage/delete.do?id='+id; 
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
		
		//设备背景图
		function oneditbgPicture()
		 {
		  		var rows = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
		            if (rows.length>1 || rows.length==0) { 
		            	$.ligerDialog.error('请选择一行数据！'); 
		            	return; 
		            }
		            editbgPicture(rows);      	
		  }       
	        
	     function editbgPicture(id){
	            var url = '${ctx}/topo/graphManage/editbgPicture.do?id='+id;
	     		layer_show("设置背景图", url, 700, 400);
	      }


			//设置缺省图
		function onchangeDefault(){
            var rows = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (rows.length==0 ) { 
            	layer.msg('请选择一行数据！', {icon: 7, time: 1500});
            	return; 
            }
            if(rows.length>1){
            	layer.msg('只能选择一行数据！', {icon: 7, time: 1500});
            	return; 
            }
	         if(rows!=null&&rows!=""&&rows.length==1){
	           var rowData = jQuery("#list").jqGrid('getRowData',rows);
	           if(rowData.flag){
	        	   layer.msg('已设为缺省图！', {icon: 7, time: 1500});
	           }else{
				 layer.confirm('请确认是否设置为缺省图？', function (yes) {
		            changeDefault(rows);
		        });
			 }
	        }
        }
        
        //设置默认显示拓扑图
        function changeDefault(id){
        	var url = '${ctx}/topo/graphManage/changeDefault.do?id='+id; 
          	$.ajax({
          	    type: "POST",
          	    url:url,
          	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
          		success: function(data) {
          			refreshData();
          		}
          	});
        }
        
        //显示背景图
        function showbgPicture(id,bgPicture){
        	var image=new Image();
        	image.src="<%=Global.LOAD_BG_IMG_URL%>"+bgPicture;
//         	alert(image.width+"   " +image.height);
            var url = '${ctx}/topo/graphManage/showbgPicture.do?id='+id;
     		layer_show("背景图展示", url, 500, 400);
        }

 		//刷新
        function onRefresh(){
        	window.location.reload();
        }
        
        function showTopo(graphId){
            var url = '${ctx}/topo/graphManage/showGraphTopo.do?graphId='+graphId;
  		   window.open(url,'', 'height=550, width=1280, top=160, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
        }
              
    </script>
</head>
<body> 
 	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
	    <ul class="breadcrumb">
	        <li class="active">${titleContent }</li>
	    </ul>
	</div>
	<div id="btn" style="margin-top:2px;">
    <button class="btn btn-white btn-default btn-bold" onclick="onSearchTopo()">
        <i class="ace-icon fa  fa-plus bigger-120"></i>
        拓扑搜索
    </button>
    <button class="btn btn-white btn-info btn-bold" onclick="onEdit()">
        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
        修改
    </button>
    <button class="btn btn-white btn-warning btn-bold" onclick="onDelete()">
        <i class="ace-icon fa fa-times bigger-120"></i>
        删除
    </button>
    <button class="btn btn-white btn-danger btn-bold" onclick="onchangeDefault()">
        <i class="ace-icon fa fa-file-text-o bigger-120"></i>
        设为缺省图
    </button>
     <button class="btn btn-white btn-purple btn-bold" onclick="oneditbgPicture()">
        <i class="ace-icon fa fa-check-square bigger-120 purple"></i>
        设置背景图
    </button>
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
</shiro:hasPermission>

