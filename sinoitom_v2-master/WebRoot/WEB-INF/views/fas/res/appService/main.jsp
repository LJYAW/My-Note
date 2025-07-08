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
		<title>应用服务 </title>

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
    	        	 {label:'组织机构',name : 'orgName',index : 'orgName',width : 70},
    	        	 {label:'服务器IP',name : 'hostIp',index : 'hostIp',width : 100},
    	        	 {label:'PID',name : 'pid',index : 'pid',width : 50,align : "right"},
    	        	 {label:'进程命令',name : 'command',index : 'command',width : 70},
    	        	 {label:'协议号',name : 'protNo',index : 'protNo',width : 50,align : "right"},
    	        	 {label:'协议名称',name : 'protName',index : 'protName',width : 60},
    	        	 {label:'服务端口',name : 'svcPort',index : 'svcPort',width : 50,align : "right"},
    	        	 {label:'服务名称',name : 'svcName',index : 'svcName',width : 70},
    	        	 {label:'服务分类',name : 'svcClass',index : 'svcClass',width : 50,
    	        		 formatter:function(cellvalue, options, row){
    	        			 var html = '';
    	        			 if (row.svcClass == "1") {
                                 html += '<span>Web</span>';
                             } else if (row.svcClass == "2") {
                                 html += '<span>App</span>';
                             } else if (row.svcClass == "3") {
                                 html += '<span>DB</span>';
                             } 
    	        			 return html;
    	        		 }	
    	        	 },
    	        	 {label:'应用服务名称',name : 'appSvcName',index : 'appSvcName',width : 120},
    	        	 {label:'服务访问方式',name : '',index : '',width : 60},		//暂无字段
    	        	 {label:'服务访问地址(URL)',name : 'svcUrl',index : 'svcUrl',width : 150},
    	        	 {label:'服务状态',name : 'svcStatus',index : 'svcStatus',width : 50,
    	        		 formatter:function(cellvalue, options, row){
    	        			 var html = '';
    	        			 if (row.svcStatus == "1") {
                                 html += '<span style="color:green;">Active</span>';
                             } else if (row.svcStatus == "0") {
                                 html += '<span style="color:red;">InActive</span>';
                             } 
    	        			 return html;
    	        		 }
    	        	 },
    	        	 {label:'响应时间(毫秒)',name : 'respTime',index : 'respTime',width : 70,align : "right"},
    	        	 {label:'管理状态',name : 'adminStatus',index : 'adminStatus',width : 50,
    	        		 formatter:function(cellvalue, options, row){
    	        			 var html = '';
    	        			 if (row.adminStatus == "1") {
                                 html += '<span style="color:green;">已审核</span>';
                             } else if (row.adminStatus == "0") {
                                 html += '<span style="color:red;">未审核</span>';
                             } 
    	        			 return html;
    	        		 }
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
        
        
        
        
        
        function onAdd()
        {
        	//var url = '${ctx}/fas/res/host/appService/add.do';
        	//layer_show('添加应用服务', url, 900, 400);
        }
		
        
        function onEdit()
        {
            var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要修改的应用服务!', {icon: 7, time: 1500});
            }else if(ids.length>1){
              layer.msg('只能选择一条应用服务数据修改!', {icon: 7, time: 1500});
            }else{
              editRow(ids);
            }
        	
        }
        
        function editRow(id)
        {
        	var url = '${ctx}/fas/res/host/appService/edit.do?id='+id;
        	layer_show('修改应用服务',url,800, 400);
        }
        
        function onView()
        { 
           var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要查看的应用服务!', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条应用服务数据查看!', {icon: 7, time: 1500});
            }else{
              	viewRow(ids);
            }
        }
        
        function viewRow(id)
        {
        	var url = '${ctx}/fas/res/host/appService/view.do?id='+id;
        	layer_show('应用服务详情', url, 900, 400);
        }
        
        
		
        function saveOK(action, data)
        {
        	onRefresh();
        }
        
        function saveOK()
        {
        	onRefresh();
        }
        

        //删除
        function onDelete()
        {
            var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        	 if( ids.length == 0 ){
        		layer.msg('请先选择一条应用服务！', {icon: 7, time: 1500});
        	}else{
            	deleteRow(ids);
        	} 
        }
        
        function deleteRow(ids)
        {
        	layer.confirm('确认要删除吗？', function (yes) {
            	delRow(ids);
        	 });
        }
        
        function delRow(ids)
        {
        	var url = '${ctx}/fas/res/host/appService/delete.do?ids='+ids;
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
        
		function onCheckSvcPort(){
			var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
	       	 if( ids.length == 0 ){
	       		layer.msg('请先选择应用服务数据！', {icon: 7, time: 1500});
	       	}else{
	       		checkSvcPortByIp(ids);
	       	} 
		}
		
		function checkSvcPortByIp(ids)
        {
        	var url = '${ctx}/fas/res/host/appService/checkSvcPortByIp.do?ids='+ids;
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
        			if( data.result == 'success' ){
        				refreshPortStatus(ids);
        			}else{
        				layer.msg('请选择同一IP下的应用服务！', {icon: 7, time: 1500});
        			}
        		}
        	});
        }
		
		function refreshPortStatus(ids){
			$("#trTxt").removeClass("dN");
			var url = '${ctx}/fas/res/host/appService/refreshPortStatus.do?ids='+ids;
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
        			$("#trTxt").addClass("dN");
        			if( data.success!=null ){
        				layer.msg(data.success,{icon:1, time: 3500},function(){
        					onRefresh(); // 页面刷新
        	            });
        			}
        		}
        	});
		}
		
		function onExam(){
			var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要审核的应用服务!', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条应用服务数据进行审核!', {icon: 7, time: 1500});
            }else{
            	examRow(ids);
            }
		}
		
		function examRow(id){
			var url = '${ctx}/fas/res/host/appService/examAppService.do?id='+id;
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
        			if( data.result == 'success' ){
        				layer.msg('审核成功!',{icon:1, time: 1000},function(){
        					onRefresh(); // 页面刷新
        	            });
        			}
        		}
        	});
		}
        
    </script>
<body>  
   <div id="btn" style="margin-top:2px;">
						    <button class="btn btn-white btn-default btn-bold" onclick="onAdd()"  >
							<i class="ace-icon  fa fa-plus  green"></i>
								添加
							</button>
							<button class="btn btn-white btn-default btn-bold" onclick="onEdit()" >
								<i class="ace-icon fa  fa-pencil-square-o bigger-120 default"></i>
								修改
							</button>
							<button class="btn btn-white btn-warning btn-bold"onclick="onDelete()">
								<i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
								删除
							</button>
							<button class="btn btn-white btn-danger btn-bold" onclick="onView()">
								<i class="ace-icon fa fa-file-text-o bigger-120 "></i>
								详情
							</button>
							<button class="btn btn-white btn-success btn-bold"onclick="onCheckSvcPort()" >
								<i class="ace-icon fa fa-refresh bigger-120 green"></i>
								服务端口检查
							</button>
							<button class="btn btn-white btn-info btn-bold" onclick="onExam()">
						        <i class="ace-icon fa fa-check-square-o bigger-120 "></i>
						       	审核
						    </button>
<%--							<button class="btn btn-white btn-default btn-bold" onclick="onServiceProcess()">--%>
<%--						        <i class="ace-icon fa  fa-paperclip bigger-120 default"></i>--%>
<%--						                关联业务系统--%>
<%--						    </button>--%>
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
           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;服务端口检查中，请稍候...
           		</span>
			</div>
           		
    </div>

  </body>
</html>
