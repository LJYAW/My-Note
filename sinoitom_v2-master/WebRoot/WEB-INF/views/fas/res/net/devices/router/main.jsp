<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>网络设备</title>

    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css" />
    <link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css" />

    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css" />
    <link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css" />
    <link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet" />
    <link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
    <link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />


    <script src="${ctx }/static/assets/js/jquery.min.js"></script>
    <script src="${ctx }/static/assets/js/layer.js"></script>
    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script>

    <script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
    <script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
    <script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
    <script src="${ctx }/static/assets/js/resize.js"></script>
    <script src="${ctx }/static/assets/js/bootbox.js"></script>
</head>

<body class="no-skin" style="overflow:hidden;">

<div id="btn" style="margin-top:2px;">
						     <button class="btn btn-white btn-default btn-bold" onclick="onScan()"  >
								<i class="ace-icon  fa fa-search  green"></i>
								搜索
							</button>
						    <button class="btn btn-white btn-default btn-bold" onclick="onAdd()" >
								<i class="ace-icon fa  fa-plus bigger-120 default"></i>
								添加
							</button>
							<button class="btn btn-white btn-info btn-bold"onclick="onEdit()">
								<i class="ace-icon fa fa-pencil-square-o bigger-120 blue"></i>
								修改
							</button>
							<button class="btn btn-white btn-warning btn-bold"onclick="onDelete()">
								<i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
								删除
							</button>
							<button class="btn btn-white btn-purple btn-bold" onclick="onExport()">
								<i class="ace-icon fa fa-sign-out bigger-120 "></i>
								导出
							</button>
							<button class="btn btn-white btn-purple btn-bold" onclick="onView()">
								<i class="ace-icon fa fa-file-text-o bigger-120"></i>
								设备详情
							</button>
<!-- 							<button class="btn btn-white btn-purple btn-bold" onclick="onManageItf()"> -->
<!-- 								<i class="ace-icon fa fa-file-text-o bigger-120"></i> -->
<!-- 								端口管理 -->
<!-- 							</button> -->
							<button class="btn btn-white btn-purple btn-bold" onclick="ifDetail()">
								<i class="ace-icon fa fa-file-text-o bigger-120"></i>
								接口信息
							</button>
							<button class="btn btn-white btn-purple btn-bold" onclick="onAccessTest()">
								<i class="ace-icon fa fa-check-square-o bigger-120 "></i>
								访问监测
							</button>
<!-- 							<button class="btn btn-white btn-purple btn-bold" onclick="onBatchExeCmd()"> -->
<!-- 								<i class="ace-icon fa fa-check-square-o bigger-120 "></i> -->
<!-- 								命令批处理 -->
<!-- 							</button> -->
							<button class="btn btn-white btn-success btn-bold"onclick="onRefresh()" >
								<i class="ace-icon fa fa-refresh bigger-120 green"></i>
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

    <script type="text/javascript">
    
        
   var gridHeight;		
		$(function(){ 
			gridHeight= $(document).height()-$('#btn').height()-$('#breadcrumbs').outerHeight()-85;
// 		 	 $(window).resize(function(){
// 		 		gridHeight= $(document.body).height() - 77 - $('#btn').height();
// 		 		jQuery("#list").setGridHeight(gridHeight); 
// 		 	}); 
			pageInit();	
			resize();  //自动调整页面Grid	  
		 });
    
  function pageInit(){
			jQuery("#list").jqGrid( {
 			url : '${ctx}/fas/res/net/devices/search.do?orgId='+'${orgId}'+'&checkBox='+'${checkBox}'+'&devType='+'${devType}',
            datatype: 'json',  
          
    	    colModel : [ 
    	        	 {label:'机构名称',name : 'orgName',index : 'orgName',width : 70},
    	        	 {label:'设备类型',name : 'devTypeCode',index : 'devTypeCode',width : 120,hidden:true},
    	        	 {label:'交换机名称',name : 'devName',index : 'devName',width : 100},
    	        	 {label:'IP地址',name : 'devIpLong',index : 'devIpLong',width : 100,
    	        	 
    	        	 	 formatter:function(cellvalue, options, row){
    	        			 return row.devIpAddr;
    	        		  }
    	        	 },
    	        	  
    	        	 {label:'设备厂商',name : 'vendorName',index : 'vendorName',width : 70}, 
    	        	 {label:'设备类型',name : 'typeName',index : 'typeName',width : 80}, 
    	        	
    	        	 {label:'产品系列',name : 'prodSeries',index : 'prodSeries',width: 80},
    	        	 {label:'设备型号',name : 'prodModel',index : 'prodModel',width: 80},
    	        	  
    	        	 
    	        	 {label:'启用Snmp',name : 'snmpEnabled',index : 'snmpEnabled',width: 70},
    	        	 {label:'访问方式',name : 'devAccess',index : 'devAccess',width: 70},
    	        	 //{label:'缺省网关',name : 'gatewayIP',index : 'gatewayIP',width: 50,align:'center'},
    	        	 
    	        	 
    	        	 //{label:'网关Snmp',name : 'gatewaySnmp',index : 'gatewaySnmp',width: 50,align : "center"},
//     	        	 {label:'FDB获取方式',name : 'getMacMode',index : 'getMacMode',width: 120,
    	        	 
//     	        	   formatter:function(cellvalue, options, row){
//     	        			 if(parseInt(row.getMacMode)==2){
//                         		return 'telnet';
//                         	}else if(parseInt(row.getMacMode)==3){
//                         	    return 'ssh';
//                         	}else{
//                         		return 'snmp';
//                         	}
//     	        		  }
//     	        	 }
    	        	 ], 
    	        	pager: '#pager',  
		            rowNum: 50,
		            rowList: [50, 100, 150],
		            sortname: 'orgName',  
		            viewrecords : true, 
		            sortorder: "asc",  
		            toolbar:[true,'top'],
		            multiselect: true,
    	        	height:gridHeight,
    	        	loadComplete: function () {
                		var table = this;
                		updatePagerIcons(table);
            			}
    	        	});
    		jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
			$("#t_list").css({"height":30});
			$("#btn").appendTo("#t_list");

}   
    
    
  function searchAjax()
        {
        	var checkBox = window.parent.$("#checkOrg").val();
            
            $("#checkBox").val(checkBox);
        	var url = "${ctx}/fas/res/net/devices/search.do";
        	$.ajax({
        		 type: "POST",
	             url: url,
	             contentType: "application/x-www-form-urlencoded; charset=utf-8",
	             data: $("#formSearch").serialize(),
	             dataType: "json",
        		 success: function(data) {
           			dataMain.Rows = data;
					loadData();
        		}
        	});        			
        };            
function getData() {
     var url = timeURL('${ctx}/fas/res/net/devices/search.do?orgId='+'${orgId}'+'&checkBox='+'${checkBox}');
	$.ajax({
		url : url,
		dataType : 'json',
		success : function(data) {
			dataMain.Rows = data;
			loadData();
		}
	});
	
}
     
        
        function loadData(){
            selectRowData = null;
            gridMain.loadData();
        }
        
       
        
               
        function onScan()
        {
        	var url = '${ctx}/fas/res/net/devices/routerscan.do';
     		layer_show("搜索路由器", url, 800, 400);
     		
        }
        
        function onAdd()
        {
        	var url = '${ctx}/fas/res/net/devices/add.do?orgId='+'${orgId}';
     		layer_show("添加设备", url, 850, 500);
        }

        function onEdit()
        {
	        var selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
	
	        if (selectRowData.length > 1) {
	            layer.msg('只能选择一条要修改的设备!', {icon: 7, time: 1500});
	    		return;
	    	}
	    	
	        if (selectRowData==""||selectRowData==null) { 
	        	 bootbox.alert({
	       			 message: "请选择要修改的设备!",
	        		 size: 'small'
	    			});
	            return;
	        }
	        editRow(selectRowData);
           
        }
        
        function editRow(id)
        {
        	var url = '${ctx}/fas/res/net/devices/edit.do?id='+id;
     		layer_show("修改设备", url, 800, 500);   
        }
        
        function saveOK(action, data)
        {
        	searchAjax();
        }
        function multiSaveOK(data){
     
        	onRefresh();
        }
        
        function saveOK(){
          onRefresh();
        }
        
        function onView()
        {
            var rows = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if(rows.length>1){
              layer.msg('只能查看一条设备信息！', {icon: 7, time: 1500});
              return;
            }else{
              if (rows==""||rows==null) { 
            	layer.msg('请选择一条设备信息', {icon: 7, time: 1500});
            	return; 
            }
            viewRow(rows);
            }
            
        }
        
        function viewRow(id)
        {
        	var url = '${ctx}/fas/res/net/devices/view.do?id='+id;
     		layer_show('设备信息', url, 800, 500);     
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
        
        function deleteRow(ids)
        {
        	layer.confirm('确认要删除吗？', function (yes) {
            	delRow(ids);
        	 });
        }
        
        function delRow(ids)
        {
        	var url = '${ctx}/fas/res/net/devices/delete.do?id='+ids;
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
        			if( data.result == 'success' ){
        				onRefresh();
        			}
        		}
        	});
        }
        
//         导出
        function onExport(){
         var selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        	if( selectRowData.length == 0 ){
        		layer.msg('请选择要导出的数据！', {icon: 7, time: 1500});
        	}else{
        	
            	$("#ids").val(selectRowData);
        		$("#exportForm").submit();
            	
        	} 
        }
        
       
        
        function onSnmpRefresh(){
        var selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        	if(selectRowData.length == 0){
        		layer.msg('请选择至少一个网络设备！', {icon: 7, time: 1500});
        	}
        	else{
            	var ids =selectRowData;
            	var url ='${ctx}/fas/res/net/devices/reflesh.do?ids='+ids;
            	sRefresh();//调用loading数据
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			if( data.result == 'success' ){
                			//window.parent.$.ligerDialog.success('Snmp刷新命令发送成功！');            				
            			}
            			else{
                    		layer.msg(data.message, {icon: 2, time: 1500});
            			}
            		}
            	});        			       		
        	}
        }
        
        function onManageItf()
        {
             var rows = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if(rows.length>1){
              layer.msg('只能选择一个网络设备！', {icon: 7, time: 1500});
              return;
            }else{ 
               if (rows==""||rows==null) { 
            	layer.msg('请选择一个网络设备！', {icon: 7, time: 1500});
            	return; 
               }
               var rowData = jQuery("#list").jqGrid('getRowData',rows);
               if(rowData.devTypeCode==2||rowData.devTypeCode==5){
                  manageItf(rows);
               }else{
                 layer.msg('所选设备不具备端口信息！', {icon: 7, time: 1500});
               }
             
            }
           
        }
        
        function manageItf(id)
        {
        	var url = '${ctx}/fas/res/net/devices/interface/main.do?devId='+id;
			window.open(url,'', 'height=400, width=960, top=160, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
        }        
        
        function onRefresh(){
        	window.location.reload();
        	loadData();
        }

		function ifDetail(){
		    var rows = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if(rows.length>1){
              layer.msg('只能选择一个网络设备！', {icon: 7, time: 1500});
              return;
            }else{
              if (rows==""||rows==null) { 
            	 layer.msg('请选择一个网络设备！', {icon: 7, time: 1500});
            	return; 
            }
            var rowData = jQuery("#list").jqGrid('getRowData',rows);
            var url = '${ctx}/fas/res/net/devices/ifDetail.do?id='+rows+'&name='+rowData.devName;
			window.open(url,'', 'height=400, width=960, top=160, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
            }
		    
        	
        }
        
        function fdbTable(){
            var rows = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if(rows.length>1){
              layer.msg('只能选择一个网络设备！', {icon: 7, time: 1500});
              return;
            }else{
              if (rows==""||rows==null) { 
            	layer.msg('请选择一个网络设备！', {icon: 7, time: 1500});
            	return; 
            }else{
             var rowData = jQuery("#list").jqGrid('getRowData',rows);
              if(rowData.devTypeCode==2||rowData.devTypeCode==5){
             
                  var url = '${ctx}/fas/res/net/devices/fdbTable.do?id='+rows+'&name='+rowData.devName;
			      window.open(url,'', 'height=400, width=960, top=160, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
               }else{
                 layer.msg('所选设备不具备转发表信息！', {icon: 7, time: 1500});
               }
            }
        	
        }
       } 
        function sRefresh(){
        	var url = '${ctx}/fas/res/net/devices/sRefresh.do';
			window.open(url,'', 'height=400, width=760, top=160, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
        }
        

	function onAccessTest()
        {
            
	       var selectRowData = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
	        if (selectRowData==""||selectRowData==null) { 
	        	layer.msg('请选择要删除的用户!',{icon: 7,time:1500});
	        	return; 
	        }else{
	          	var url = '${ctx}/fas/res/net/devices/onAccessTest.do?id='+selectRowData;
     			layer_show("访问检测", url, 800, 500);   
	        }
            
        }
        
    function onBatchExeCmd(){
    	 var rows = gridMain.getSelectedRows();
         if(rows.length>1){
           window.parent.$.ligerDialog.warn('只能操作一台交换机！'); 
           return;
         }else if (!selectRowData) {
            window.parent.$.ligerDialog.warn('请选择一台交换机！'); 
            return; 
         }else if(rows.length==1){
	         if(rows[0].devAccess==""||rows[0].devAccess==null){
	         	window.parent.$.ligerDialog.warn('请选择一条‘访问方式’不为空的交换机进行处理！'); 
            	return; 
	         }
	         var url = timeURL('${ctx}/fas/res/net/devices/onBatchExeCmd.do?id='+selectRowData.id+'&typeCode='+2);
	     	 window.parent.parent.parent.showDlg('命令批处理', 800, 550, url);
         }
          
     }
    </script>

<div style="height:30px;width:100%;background-color: #CEDFEF;display: none">
<form style="padding:0px;" id="formSearch" action="${ctx}/fas/res/net/switch/search.do" method="post">
<!-- <input type="hidden" name="orgIds" id="orgIds" value=""/> -->
<input type="hidden" name="checkBox" id="checkBox" value=""/>
<input type="hidden" name="orgId" value="${orgId}"/>
<input type="hidden" name="queryType" id="queryType" value="search"/>
  <table style="height:28px;MARGIN-LEFT: 5px">
            <tr>
                <td align="right">设备名称:</td>
                <td><input name="filter_LIKES_devName" class="ip1" type="text" ltype="text" placeholder="请输入名称(可模糊查询)"/></td>
                <td>&nbsp;&nbsp;</td>
                <td align="right">设备IP:</td>
                <td><input name="filter_LIKES_devIpAddr" class="ip1" type="text" ltype="text" placeholder="请输入IP(可模糊查询)"/></td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td align="right">
                <input id="bnSearch" type="button" value="查 询" onclick="searchAjax()" class="l-button"/> 
                </td>
            </tr>
        </table>
</form>        
</div>
 
 <form action="${ctx}/fas/res/net/devices/exportExcel.do" method="post" id="exportForm"  style="display:none">
     	<input type="hidden" name="ids" id="ids" value=""/>
    </form>
</body>
</html>

