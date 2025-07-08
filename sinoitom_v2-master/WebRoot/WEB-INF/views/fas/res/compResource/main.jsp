<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>计算资源 </title>

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

	</head>
    <script type="text/javascript">
        var gridHeight;		
        var dlgSearch=null;
		$(function(){ 
			 gridHeight= $(document).height()-120-$('#btn').height();
			pageInit();	
			resize();  //自动调整页面Grid	  
		 });
		 
		   function pageInit(){
			jQuery("#list").jqGrid( {
				data:  ${jsonListData},
            datatype: "local",  
          
    	    colModel : [ 
    	        	 {label:'机构名称',name : 'orgName',index : 'orgName',width : 100},
    	        	 {label:'管理IP地址',name : 'ipAddress',index : 'ipAddress',width : 100},
    	        	 {label:'资源类型',name : 'resTypeName',index : 'resTypeName',width : 70},
    	        	 {label:'系统架构',name : 'osStruct',index : 'osStruct',width : 65},
    	        	 {label:'主机类型',name : 'hostType',index : 'hostType',width : 70,
    	        		 formatter:function(cellvalue, options, row){
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
    	        	 {label:'操作系统分类',name : 'osClass',index : 'osClass',width : 80},
    	        	 {label:'操作系统版本',name : 'osVersion',index : 'osVersion',width : 140},
    	        	 {label:'操作系统类型',name : 'osType',index : 'osType',width : 80},
    	        	 {label:'版本分类',name : 'osVersionType',index : 'osVersionType',width : 65},
    	        	 {label:'版本号',name : 'osRelease',index : 'osRelease',width : 90},
    	        	 {label:'访问方式',name : 'accessmode',index : 'accessmode',width : 65},
    	        	 {label:'用户登录ID',name : 'devAcsUserId',index : 'devAcsUserId',width : 65,hidden:true},
    	        	 {label:'用户名称',name : 'userName',index : 'userName',width : 60
    	        	}], 
    	        	
    	        	 viewrecords: true,
    	        	 rowNum : 50, rowList : [ 50, 100, 150 ], 
    			 		pager : '#pager',
    			 		height:gridHeight,
    					altRows: true,
    					toolbar:[true,'top'],
    					multiselect: true,
    					multiboxonly: true,
    					loadComplete : function() {
    						var table = this;
    							setTimeout(function(){
    							updatePagerIcons(table);
    							}, 0);
    					},
    	        	});
    		jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
    							$("#t_list").css({"height":30});
    		            $("#btn").appendTo("#t_list");

    	} 

         // $("#bnSearch").click(f_bnSearch);
            $("#bnReset").click(function()
            {
            	window.$("input[ltype='text'][name*='filter_']").val("");
            	window.$("select[name*='filter_']").val("");
            });
            
        
        /*
       	 	实现 表单分页多选
        	即利用onCheckRow将选中的行记忆下来，并利用isChecked将记忆下来的行初始化选中
        */
        var checkedItem = [];
        function f_onCheckAllRow(checked)
        {
            for (var rowid in this.records)
            {
                if(checked)
                    addCheckedItem(this.records[rowid]['id']);
                else
                    removeCheckedItem(this.records[rowid]['id']);
            }
        }
        function findCheckedItem(ItemID)
        {
            for(var i =0;i<checkedItem.length;i++)
            {
                if(checkedItem[i] == ItemID) return i;
            }
            return -1;
        }
        function addCheckedItem(ItemID)
        {
            if(findCheckedItem(ItemID) == -1)
                checkedItem.push(ItemID);
        }
        function removeCheckedItem(ItemID)
        {
            var i = findCheckedItem(ItemID);
            if(i==-1) return;
            checkedItem.splice(i,1);
        }
        function f_isChecked(rowdata)
        {
            if (findCheckedItem(rowdata.id) == -1)
                return false;
            return true;
        }
        function f_onCheckRow(checked, data)
        {
            if (checked) addCheckedItem(data.id);
            else removeCheckedItem(data.id);
        }
        function f_getChecked()
        {
            alert(checkedItem.join(','));
        }
        
        
        function loadData(){
            selectRowData = null;
            gridMain.loadData();
        }
        
        
        function onSearchAdd()
        {
        	var url = '${ctx}/fas/res/host/resHosts/searchAdd.do';
        	layer_show('服务器搜索添加', url, 900, 450);
        }
        
        function onAdd()
        {
        	//var url = '${ctx}/fas/res/host/ipHost/add.do';
        	//layer_show('添加计算资源', url, 900, 550);
        }
		
        
        function onEdit()
        {
            var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要修改的计算资源!', {icon: 7, time: 1500});
            }else if(ids.length>1){
              layer.msg('只能选择一条资源数据修改!', {icon: 7, time: 1500});
            }else{
              editRow(ids);
            }
        	
        }
        
        function editRow(id)
        {
        	var url = '${ctx}/fas/res/host/resHosts/edit.do?id='+id;
        	layer_show('修改计算资源',url,900, 400);
        }
        
        function onView()
        { 
           var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要查看的计算资源!', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条资源数据查看!', {icon: 7, time: 1500});
            }else{
              	viewRow(ids);
            }
        }
        
        function viewRow(id)
        {
        	var url = '${ctx}/fas/res/host/resHosts/view.do?id='+id;
        	layer_show('查看资源详情', url, 900, 500);
        }
        
        //获取服务进程
        function onServiceProcess(){
        	var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要获取服务进程的计算资源!', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条资源数据获取服务进程!', {icon: 7, time: 1500});
            }else{
            	$("#trTxt").removeClass("dN");
            	var rowData=jQuery("#list").jqGrid("getRowData",ids);
            	var ipAddress =rowData.ipAddress;
				var devAcsUserId = rowData.devAcsUserId;
            	var url = '${ctx}/fas/res/host/resHosts/checkIpAndPasWord.do';
				$.ajax({
					type: "POST",
		              dataType: 'json',
		              url: url,
		              contentType: "application/x-www-form-urlencoded; charset=utf-8",
		              data: "ipAddress="+ipAddress+"&devAcsUserId="+devAcsUserId,
	        		success: function(data) {
	        			if(data.success!=null){
	        				$("#trTxt").addClass("dN");
	        				getServiceProcessRow(ids);
	        			}else if(data.error!=null){
	        				$("#trTxt").addClass("dN");
	        				layer.msg(data.error, {icon: 7, time: 2000},function(){
	        					onRefresh(); // 页面刷新
	        	            });
	        			}else{
	        				$("#trTxt").addClass("dN");
	        				layer.msg(data.message, {icon: 7, time: 2000},function(){
	        					onRefresh(); // 页面刷新
	        	            });
	        			}
				    	 
	        		}
	        	});
            	
            }
        }
        
        function getServiceProcessRow(id)
        {
        	var url = '${ctx}/fas/res/host/resServiceProcess/getServiceProcess.do?id='+id;
        	//layer_show('获取服务进程', url, 900, 500);
        	window.open(url,'获取服务进程', 'height=700, width=1100, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
            
        }
        
        function onServAndProc(){
        	var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要查看服务与进程详情的计算资源!', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条资源数据查看服务与进程详情!', {icon: 7, time: 1500});
            }else{
            	getServAndProc(ids);
            }
        }
        
        function getServAndProc(id)
        {
        	var url = '${ctx}/fas/res/host/resServiceProcess/getServAndProc.do?id='+id;
        	//layer_show('服务与进程详情', url, 900, 500);
        	window.open(url,'服务与进程详情', 'height=700, width=1100, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
        }
		
        function saveOK(action, data)
        {
        	onRefresh();
        }
        
        function saveOK()
        {
        	onRefresh();
        }
        
        function multiSaveOK(data)
        {
        	onRefresh();
        }

        //删除
        function onDelete()
        {
            var id = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        	 if( id.length == 0 ){
        		layer.msg('请先选择一条资源数据！', {icon: 7, time: 1500});
        	}else if(id.length>1){
              	layer.msg('只能选择一条资源数据!', {icon: 7, time: 1500});
            }else{
            	deleteRow(id);
        	} 
        }
        
        function deleteRow(id)
        {
        	layer.confirm('确认要删除吗？', function (yes) {
            	delRow(id);
        	 });
        }
        
        function delRow(id)
        {
        	var url = '${ctx}/fas/res/host/resHosts/delete.do?id='+id;
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
        
        function onRefresh(){
        	window.location.reload();
        }
        
        function onResSync(){
        	var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择一条要同步的计算资源!', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条资源数据进行同步!', {icon: 7, time: 1500});
            }else{
            	syncRow(ids);
            }
        }
        
        function syncRow(id)
        {
        	$("#trTxt2").removeClass("dN");
        	var url = '${ctx}/fas/res/host/resHosts/hostResSync.do?id='+id;
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
        			if( data.result == 'success' ){
        				$("#trTxt2").addClass("dN");
        				
        				layer.msg('同步成功!',{icon:1, time: 1000},function(){
        					onRefresh(); // 页面刷新
        	            });
        			}else{
        				$("#trTxt2").addClass("dN");
        				layer.msg(data.message, {icon: 7, time: 1000});
        			}
        		}
        	});
        }
        
        //性能指标
        function onPerformance(){
        	var id = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
	       	 if( id.length == 0 ){
	       		layer.msg('请先选择一条资源数据查看性能指标！', {icon: 7, time: 1500});
	       	 }else if(id.length>1){
	             layer.msg('只能选择一条资源数据查看性能指标!', {icon: 7, time: 1500});
	         }else{
	        	  
	        	   $("#trTxt1").removeClass("dN");
	            	var rowData=jQuery("#list").jqGrid("getRowData",id);
	            	var ipAddress =rowData.ipAddress;
					var devAcsUserId = rowData.devAcsUserId;
	            	var url = '${ctx}/fas/res/host/resHosts/checkIpAndPasWord.do';
					$.ajax({
						type: "POST",
			              dataType: 'json',
			              url: url,
			              contentType: "application/x-www-form-urlencoded; charset=utf-8",
			              data: "ipAddress="+ipAddress+"&devAcsUserId="+devAcsUserId,
		        		success: function(data) {
		        			if(data.success!=null){
		        				$("#trTxt1").addClass("dN");
		        				performanceRow(id);
		        			}else if(data.error!=null){
		        				$("#trTxt1").addClass("dN");
		        				layer.msg(data.error, {icon: 7, time: 2000},function(){
		        					onRefresh(); // 页面刷新
		        	            });
		        			}else{
		        				$("#trTxt1").addClass("dN");
		        				layer.msg(data.message, {icon: 7, time: 2000},function(){
		        					onRefresh(); // 页面刷新
		        	            });
		        			}
					    	 
		        		}
		        	});
	        	   
	       	}
        }
        
        function performanceRow(id){
        	var url = '${ctx}/fas/res/host/resHosts/getPerformance.do?id='+id;
        	layer_show('性能指标', url, 800, 500);
        }
		
        
    </script>
<body>  
   <div id="btn" style="margin-top:2px;">
<%--						    <button class="btn btn-white btn-default btn-bold" onclick="onAdd()"  >--%>
<%--							<i class="ace-icon  fa fa-plus  green"></i>--%>
<%--								添加--%>
<%--							</button>--%>
   							<button class="btn btn-white btn-default btn-bold" onclick="onSearchAdd()"  >
							<i class="ace-icon  fa fa-plus  green"></i>
								搜索添加
							</button>
						    <button class="btn btn-white btn-default btn-bold" onclick="onEdit()" >
								<i class="ace-icon fa  fa-pencil-square-o bigger-120 default"></i>
								修改
							</button>
							
							<button class="btn btn-white btn-danger btn-bold" onclick="onView()">
								<i class="ace-icon fa fa-file-text-o bigger-120 "></i>
								详情
							</button>
							<button class="btn btn-white btn-warning btn-bold"onclick="onDelete()">
								<i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
								删除
							</button>
							<button class="btn btn-white btn-success btn-bold"onclick="onResSync()" >
								<i class="ace-icon fa fa-refresh bigger-120 green"></i>
								Host资源同步
							</button>
							<button class="btn btn-white btn-default btn-bold" onclick="onServiceProcess()">
								<i class="ace-icon  fa fa-plus  green"></i>
								获取服务进程
							</button>
							<button class="btn btn-white btn-danger btn-bold" onclick="onServAndProc()">
								<i class="ace-icon fa fa-file-text-o bigger-120 "></i>
								服务与进程
							</button>
							<button class="btn btn-white btn-danger btn-bold" onclick="onPerformance()">
								<i class="ace-icon fa fa-file-text-o bigger-120 "></i>
								性能指标
							</button>
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

<div class="col-xs-12" style="overflow: hidden;position:relative;z-index:1;">
    <table id="list"></table>
    <div id="pager"></div>
</div>

	<div id="trTxt" class="dN" style="background-color:#FFDEAD; border:0px;top:300px; left:500px; position:absolute; z-index:4;height:31px;line-heigth:31px;text-align:left">
			<div  align="right">
			<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;获取服务进程中，请稍候...
           		</span>
			</div>
           		
    </div>
    
    <div id="trTxt1" class="dN" style="background-color:#FFDEAD; border:0px;top:300px; left:500px; position:absolute; z-index:4;height:31px;line-heigth:31px;text-align:left">
			<div  align="right">
			<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;获取性能指标中，请稍候...
           		</span>
			</div>
           		
    </div>
    
    <div id="trTxt2" class="dN" style="background-color:#FFDEAD; border:0px;top:300px; left:500px; position:absolute; z-index:4;height:31px;line-heigth:31px;text-align:left">
			<div  align="right">
			<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;Host资源同步中，请稍候...
           		</span>
			</div>
           		
    </div>
	
  </body>
</html>
