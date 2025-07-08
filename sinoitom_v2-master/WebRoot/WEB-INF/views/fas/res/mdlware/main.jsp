<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>中间件</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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

    <script src="${ctx}/static/formatter.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/js/plugin/My97DatePicker/WdatePicker.js"></script>
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
						{label:'所属机构',name : 'orgName',index : 'orgName',width : 120 },
						{label:'中间件类型',name : 'resTypeName',index : 'resTypeName',width : 100 },
						{label:'厂商/组织',name : 'vendorName',index : 'vendorName',width:100, align:'left' },
						{label:'品牌',name : 'brandName',index : 'brandName',width:100, align:'left' },
						{label:'中间件',name : 'prodName',index : 'prodName',width:80, align:'left' },
						{label:'中间件版本',name : 'prodVersion',index : 'prodVersion',width : 170, align:'left' },
						{label:'服务器类型',name : 'svrType',index : 'svrType',width:80, align:'left' ,
							formatter: function (cellvalue, options, row) {
						        var html="";
						        if(row.svrType==0){
						            html += '虚拟机';
						        }else if(row.svrType==1) {
						            html += '物理机';
						        }else if(row.svrType==2) {
						            html += '宿主机';
						        }else if(row.svrType==3) {
						            html += '集群';
						        }
						        return html;
						    }	
						},
	    	        	{label:'服务器IP地址',name : 'svrIpAddr',index : 'svrIpAddr',width : 100, align:'left' },
    	        	 	{label:'监测端口',name : 'monPort',index : 'monPort',width : 70, align:'left' },
    	        	 	{label:'用户名',name : 'userName',index : 'userName',width:80, align:'left' },
    	        	 	{label:'审核状态',name : 'auditStatus',index : 'auditStatus',width:60, align:'left' ,
    	        	 		formatter: function (cellvalue, options, row) {
						        var html="";
						        if(row.auditStatus==0){
						            html += '<span style="color:red;">未审核</span>';
						        }else if(row.auditStatus==1) {
						            html += '<span style="color:green;">已审核</span>';
						        }
						        return html;
						    }	
    	        	 	},
    	        	 	{label:'监测状态',name : 'monStatus',index : 'monStatus',width:60, align:'left' ,
    	        	 		formatter: function (cellvalue, options, row) {
						        var html="";
						        if(row.monStatus==0){
						            html += '<span style="color:red;">未监测</span>';
						        }else if(row.monStatus==1) {
						            html += '<span style="color:green;">监测中</span>';
						        }
						        return html;
						    }	
    	        	 	}
    	        	 
    	        	  ], 
    	        	
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
        

        //点击具体的IP展示详细信息
        function showDevIpAddrs(devIpAddr,content,tomcatVersion){
            var url = '${ctx}/fas/res/mdlware/app/index/info.do?devIpAddr='+devIpAddr+'&content='+content+'&tomcatVersion='+tomcatVersion;
            window.open(url,'info', 'height=850, width=1200, top=50, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
        }

        //刷新
        function onRefresh(){
            //window.location.href="${ctx}/fas/res/tom/index/main.do";
            window.location.reload();
        }

        //Tomcat监测
        function add() {
            var url = '${ctx}/fas/res/mdlware/add.do';
            layer_show('添加中间件', url, 750, 430);
        }

        //修改
        function onEdit(){
        	var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要修改的服务器数据！', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条服务器数据进行修改！', {icon: 7, time: 1500});
            }else{
            	var rowData=jQuery("#list").jqGrid("getRowData",ids);
            	var reg = RegExp(/未监测/);
            	var str = rowData.monStatus;
            	if(str.match(reg)){
            		editRow(ids);
                }else{
                    layer.msg('当前所选服务器正处于监测状态，请重新选择或终止监测后再进行修改操作！', {icon: 7, time: 1500});
                }
              
            }
            
        }

        function editRow(id){
            var url = '${ctx}/fas/res/mdlware/edit.do?id='+id;
            layer_show('修改', url, 800, 430);
        }


        //启动监测
        function startMonitor(){
        	var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要启动监测的服务器！', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条服务器数据开启监测！', {icon: 7, time: 1500});
            }else{
            	var rowData=jQuery("#list").jqGrid("getRowData",ids);
            	var reg = RegExp(/未监测/);
            	var str = rowData.monStatus;
            	if(str.match(reg)){
            		//startRow(ids);
            		//启动监测前检查服务器连接是否正确【此方法会用（到时候注销上面startRow(ids)方法），暂时注销】
            		checkStartMonitorConn(ids,rowData.svrIpAddr,rowData.monPort);
                }else{
                    layer.msg('当前所选服务器正在进行监测，请重新选择！', {icon: 7, time: 1500});
                }
              
            }
        	
        }
      //启动监测前检查服务器连接是否正确
        function checkStartMonitorConn(id,svrIpAddr,monPort)
        {
        	$("#trTxt").removeClass("dN");
        	var url = '${ctx}/fas/res/mdlware/checkMonitorConn.do?id=' + id+'&svrIpAddr='+svrIpAddr+'&monPort='+monPort;
            $.ajax({
                url: url,
                dataType: 'json',
                success: function(data) {
                    if( data.result == 'false' ){
                    	$("#trTxt").addClass("dN");
                    	layer.msg(data.message, {icon: 7, time: 1500});
                    }else{
                    	startRow(id);
                    }
                }
            });
        }

        function startRow(id)
        {
            $("#trMsg").addClass("dN");

            $("#trTxt").removeClass("dN");

            var url = '${ctx}/fas/res/mdlware/startTomcatMonitor.do?id='+id;
            $.ajax({
                url: url,
                dataType: 'json',
                success: function(data) {
                    $("#trTxt").removeClass("dN");

                    if( data.result == 'success' ){
                        $("#trTxt").addClass("dN");
                        $("#trMsg").removeClass("dN");

                        layer.msg('启动监测成功!',{icon:1, time: 1000},function(){
        					onRefresh(); // 页面刷新
        	            });
                    }
                }
            });
        }

        //终止监测
        function stopMonitor(){
        	var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要终止监测的服务器！', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条服务器数据终止监测！', {icon: 7, time: 1500});
            }else{
            	var rowData=jQuery("#list").jqGrid("getRowData",ids);
            	var reg = RegExp(/未监测/);
            	var str = rowData.monStatus;
            	if(str.match(reg)){
                    layer.msg('当前所选服务器暂未进行监测，请重新选择！', {icon: 7, time: 1500});
                }else{
            		stopRow(ids);
                }
              
            }
        	
        }

        function stopRow(id)
        {
        	layer.confirm('是否确认终止监测所选服务器？', function (yes) {
        		shutdown(id);
        	 });
        	
        }

        function shutdown(id)
        {
            var url = '${ctx}/fas/res/mdlware/shutdown.do?id='+id;
            $.ajax({
                url: url,
                dataType: 'json',
                success: function(data) {
                    if( data.result == 'success' ){
                        layer.msg('终止监测成功!',{icon:1, time: 1000},function(){
        					onRefresh(); // 页面刷新
        	            });
                    }
                }
            });
        }


        //实时监测
        function showLine() {
        	var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择一条服务器信息进行实时监测！', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条服务器信息进行实时监测！', {icon: 7, time: 1500});
            }else{
            	$("#trTxt1").removeClass("dN");
            	var rowData=jQuery("#list").jqGrid("getRowData",ids);
<%--            	var reg = RegExp(/未监测/);--%>
<%--            	var str = rowData.status;--%>
<%--            	if(str.match(reg)){--%>
<%--                    layer.msg('当前所选服务器暂未开启监测，请开启后再做选择！', {icon: 7, time: 1500});--%>
<%--                }else{--%>
<%--            		showLineview(ids,rowData.devIpAddr,rowData.content);--%>
<%--                }--%>
            	//showLineview(ids,rowData.devIpAddr,rowData.content);
            //实时监测之前先检查连接是否正常
              checkMonitorConn(ids,rowData.svrIpAddr,rowData.monPort);
            }
        	
        }
        
        function checkMonitorConn(id,svrIpAddr,monPort)
        {
        	var url = '${ctx}/fas/res/mdlware/checkMonitorConn.do?id=' + id+'&svrIpAddr='+svrIpAddr+'&monPort='+monPort;
            $.ajax({
                url: url,
                dataType: 'json',
                success: function(data) {
                	$("#trTxt1").addClass("dN");
                    if( data.result == 'false' ){
                    	layer.msg(data.message, {icon: 7, time: 1500});
                    }else{
                    	showLineview(id,svrIpAddr,monPort);
                    }
                }
            });
        }

        function showLineview(id,svrIpAddr,monPort) {

            var url = '${ctx}/fas/res/mdlware/lineTab.do?id=' + id+'&svrIpAddr='+svrIpAddr+'&monPort='+monPort;

            //layer_show('实时监测', url, 1000, 850);
            
            window.open(url,'实时监测', 'height=800, width=1100, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
            
        }


        //历史趋势图
        function historyLine() {
        	var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要展示历史趋势图的服务器！', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条服务器信息进行历史趋势图展示！', {icon: 7, time: 1500});
            }else{
            	var rowData=jQuery("#list").jqGrid("getRowData",ids);
            	historyLineview(ids,rowData.svrIpAddr,rowData.monPort,rowData.prodVersion);
              
            }
        	
        }

        function historyLineview(id,svrIpAddr,monPort,prodVersion) {

            var url = '${ctx}/fas/res/mdlware/historyLineview.do?id=' + id+'&svrIpAddr='+svrIpAddr+'&monPort='+monPort+'&prodVersion='+prodVersion+'&historyLineTime=30Min';

            window.open(url,'历史趋势图', 'height=800, width=1100, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
            
        }

        //测试按钮
        function testButton() {
        	var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择一条数据！', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条数据！', {icon: 7, time: 1500});
            }else{
            	testButtonview(ids);
            }
        	
        }

        function testButtonview(id) {

            var url = '${ctx}/fas/res/mdlware/app/index/testButtonview.do?id=' + id;

            layer_show('测试按钮', url, 1000, 850);
        }

        function saveOK() {
            // alert("监测成功！");
            onRefresh();
            window.close();
        }
        
        function closeDlg() {
            // alert("监测失败！");
            onRefresh();
            window.close();
        }

        //删除
        function onDelete()
        {
        	var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要删除的服务器数据！', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条服务器数据进行删除！', {icon: 7, time: 1500});
            }else{
            	var rowData=jQuery("#list").jqGrid("getRowData",ids);
            	var reg = RegExp(/未监测/);
            	var str = rowData.monStatus;
            	if(str.match(reg)){
            		deleteRow(ids);
                }else{
                    layer.msg('当前所选服务器正处于监测状态，请重新选择或终止监测后再进行删除操作！', {icon: 7, time: 1500});
                }
            }
        	
        }

        function deleteRow(id)
        {
        	layer.confirm('是否确认删除所选服务器？', function (yes) {
        		delRow(id);
        	 });
        }

        function delRow(id)
        {
            var url = '${ctx}/fas/res/mdlware/delete.do?id='+id;
            $.ajax({
                url: url,
                dataType: 'json',
                success: function(data) {
                    if( data.result == 'success' ){
                        layer.msg('删除成功!',{icon:1, time: 1000},function(){
                        	onRefresh(); // 页面刷新
        	            });
                    }
                }
            });
        }
        
        //审核
        function onExam(){
			var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要审核的服务器!', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条服务器数据进行审核!', {icon: 7, time: 1500});
            }else{
            	var rowData=jQuery("#list").jqGrid("getRowData",ids);
            	var reg = RegExp(/未审核/);
            	var str = rowData.auditStatus;
            	if(str.match(reg)){
            		examRow(ids);
                }else{
                    layer.msg('当前所选服务器已通过审核！', {icon: 7, time: 1500});
                }
            }
		}
		
		function examRow(id){
			var url = '${ctx}/fas/res/mdlware/examAppService.do?id='+id;
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
		
		//访问验证按钮
		 function onCheckMdlWare(){
			 
			 var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
	            if (ids==""||ids==null) { 
	            	 layer.msg('请选择要验证的服务器!', {icon: 7, time: 1500});
	            }else if(ids.length>1){
	              	layer.msg('只能选择一条服务器数据进行验证!', {icon: 7, time: 1500});
	            }else{
	            	accessVerify(ids);
	            }
		 }
		
		function accessVerify(id){
			$("#trTxt2").removeClass("dN");
			var url = '${ctx}/fas/res/mdlware/mainAccessVerify.do?id='+id;
			$.ajax({
	 		url: url,
	 		dataType: 'json',
	 		success: function(data) {
	 			$("#trTxt2").addClass("dN");
	 			if( data.result == 'success' ){
    				layer.msg("验证成功！",{icon:1, time: 1000},function(){
    					onRefresh(); // 页面刷新
    	            });
    			}else{
    				layer.msg(data.message, {icon: 7, time: 1500},function(){
    					onRefresh(); // 页面刷新
    	            });
    			}
	 		}
	 	});
		}
        
    </script>
</head>
<body style="padding:0; overflow:hidden;">

<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li class="active">${titleContent }</li>
    </ul>
</div>

<div id="btn" style="margin-top:2px;">
						    <button class="btn btn-white btn-default btn-bold" onclick="add()"  >
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
							
							<button class="btn btn-white btn-info btn-bold" onclick="onExam()">
						        <i class="ace-icon fa fa-check-square-o bigger-120 "></i>
						       	审核
						    </button>
						    <button class="btn btn-white btn-info btn-bold" onclick="onCheckMdlWare()">
						        <i class="ace-icon fa fa-check-square-o bigger-120 "></i>
						       	访问验证
						    </button>
							
							<button class="btn btn-white btn-success btn-bold"onclick="startMonitor()" >
								<i class="ace-icon fa fa-refresh bigger-120 green"></i>
								开启监测
							</button>
							<button class="btn btn-white btn-warning btn-bold"onclick="stopMonitor()">
								<i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
								停止监测
							</button>
							
							<button class="btn btn-white btn-danger btn-bold" onclick="showLine()">
								<i class="ace-icon fa fa-file-text-o bigger-120 "></i>
								实时监测
							</button>
							<button class="btn btn-white btn-danger btn-bold" onclick="historyLine()">
								<i class="ace-icon fa fa-file-text-o bigger-120 "></i>
								历史趋势
							</button>
<%--							<button class="btn btn-white btn-danger btn-bold" onclick="testButton()">--%>
<%--								<i class="ace-icon fa fa-file-text-o bigger-120 "></i>--%>
<%--								测试按钮--%>
<%--							</button>--%>
							<button class="btn btn-white btn-success btn-bold" onclick="onRefresh()">
								<i class="ace-icon fa fa-refresh bigger-120 green"></i>
								刷新
							</button>
</div>

<div class="col-xs-12" style="overflow: hidden;position:relative;z-index:1;">
    <table id="list"></table>
    <div id="pager"></div>
</div>


    <div id="trTxt" class="dN" style="background-color:#FFDEAD; border:0px;top:300px; left:500px; position:absolute; z-index:4;height:31px;line-heigth:31px;text-align:left">
			<div  align="right">
			<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;开启监测中，请稍候...
           		</span>
			</div>
           		
    </div>
    <div id="trMsg"  >
        <div id="maingrid" ></div>
    </div>
    <div id="trTxt1" class="dN" style="background-color:#FFDEAD; border:0px;top:300px; left:500px; position:absolute; z-index:4;height:31px;line-heigth:31px;text-align:left">
			<div  align="right">
			<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;正在检测连接，请稍候...
           		</span>
			</div>
           		
    </div>
    <div id="trTxt2" class="dN" style="background-color:#FFDEAD; border:0px;top:300px; left:500px; position:absolute; z-index:4;height:31px;line-heigth:31px;text-align:left">
			<div  align="right">
			<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;验证中，请稍候...
           		</span>
			</div>
           		
    </div>
    
</body>
</html>
