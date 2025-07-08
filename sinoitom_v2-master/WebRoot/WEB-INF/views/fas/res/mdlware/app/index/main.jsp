<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Tomcat测试</title>
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
    	                
    	        	 {label:'服务器IP',name : 'devIpAddr',index : 'devIpAddr',width : 120, align:'left' ,
    	        		 render: function (row)
						    {
						        return '<span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick=\"showDevIpAddrs(\'' + row.devIpAddr + '\',\'' + row.content + '\',\'' + row.tomcatVersion + '\');\">'+row.devIpAddr+'</span>';
						    }
    	        	 },
    	        	 {label:'监测端口',name : 'content',index : 'content',width : 70, align:'left' },
    	        	 {label:'中间件类型',name : 'middleType',index : 'middleType',width : 100, align:'center' ,
    	        		 formatter: function (cellvalue, options, row) {
						        var html="";
						        if(row.middleType==0){
						            html += '应用中间件';
						        }else if(row.middleType==1) {
						            html += '消息中间件';
						        }
						        return html;
						    }
    	        	},
    	        	 {label:'厂商',name : 'vmVendor',index : 'vmVendor',width:150, align:'left' },
    	        	 {label:'虚机名称',name : 'vmName',index : 'vmName',width : 150, align:'left' },
    	        	 {label:'虚机版本',name : 'vmVersion',index : 'vmVersion',width : 80, align:'left' },
    	        	 {label:'Tomcat版本',name : 'tomcatVersion',index : 'tomcatVersion',width : 150, align:'left' },
    	        	 {label:'服务器启动时间',name : 'startTime',index : 'startTime',width : 120, align:'left' },
    	        	 {label:'监测时间',name : 'monitorTime',index : 'monitorTime',width : 120, align:'left' },
    	        	 {label:'创建时间',name : 'createTime',index : 'createTime',width : 120, align:'left' },
    	        	 {label:'监测状态',name : 'status',index : 'status',width : 60, align:'center' ,
    	        		 formatter: function (cellvalue, options, row) {
						        var html="";
						        if(row.status==0){
						            html += '<span style="color:red;">未监测</span>';
						        }else if(row.status==1) {
						            html += '<span style="color:green;">监测中</span>';
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
        function showTomcatIndex() {
            var url = '${ctx}/fas/res/mdlware/app/index/showTomcatIndex.do';
            layer_show('Tomcat指标监测', url, 750, 300);
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
            	var str = rowData.status;
            	if(str.match(reg)){
            		editRow(ids);
                }else{
                    layer.msg('当前所选服务器正处于监测状态，请重新选择或终止监测后再进行修改操作！', {icon: 7, time: 1500});
                }
              
            }
            
        }

        function editRow(id){
            var url = '${ctx}/fas/res/mdlware/app/index/edit.do?id='+id;
            layer_show('修改', url, 800, 350);
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
            	var str = rowData.status;
            	if(str.match(reg)){
            		//startRow(ids);
            		//启动监测前检查服务器连接是否正确【此方法会用（到时候注销上面startRow(ids)方法），暂时注销】
            		checkStartMonitorConn(ids,rowData.devIpAddr,rowData.content);
                }else{
                    layer.msg('当前所选服务器正在进行监测，请重新选择！', {icon: 7, time: 1500});
                }
              
            }
        	
        }
        
        function checkStartMonitorConn(id,devIpAddr,content)
        {
        	var url = '${ctx}/fas/res/mdlware/app/index/checkMonitorConn.do?id=' + id+'&devIpAddr='+devIpAddr+'&content='+content;
            $.ajax({
                url: url,
                dataType: 'json',
                success: function(data) {
                    if( data.result == 'false' ){
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

            var url = '${ctx}/fas/res/mdlware/app/index/startTomcatMonitor.do?id='+id;
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
            	var str = rowData.status;
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
            var url = '${ctx}/fas/res/mdlware/app/index/shutdown.do?id='+id;
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
              checkMonitorConn(ids,rowData.devIpAddr,rowData.content);
            }
        	
        }
        
        function checkMonitorConn(id,devIpAddr,content)
        {
        	var url = '${ctx}/fas/res/mdlware/app/index/checkMonitorConn.do?id=' + id+'&devIpAddr='+devIpAddr+'&content='+content;
            $.ajax({
                url: url,
                dataType: 'json',
                success: function(data) {
                    if( data.result == 'false' ){
                    	layer.msg(data.message, {icon: 7, time: 1500});
                    }else{
                    	showLineview(id,devIpAddr,content);
                    }
                }
            });
        }

        function showLineview(id,devIpAddr,content) {

            var url = '${ctx}/fas/res/mdlware/app/index/lineTab.do?id=' + id+'&devIpAddr='+devIpAddr+'&content='+content;

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
            	historyLineview(ids,rowData.devIpAddr,rowData.content,rowData.tomcatVersion);
              
            }
        	
        }

        function historyLineview(id,devIpAddr,content,tomcatVersion) {

            var url = '${ctx}/fas/res/mdlware/app/index/historyLineview.do?id=' + id+'&devIpAddr='+devIpAddr+'&content='+content+'&tomcatVersion='+tomcatVersion+'&historyLineTime=30Min';

            //layer_show('历史趋势图', url, 1000, 850);
            
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
            	var str = rowData.status;
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
            var url = '${ctx}/fas/res/mdlware/app/index/delete.do?id='+id;
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
        
    </script>
</head>
<body style="padding:0; overflow:hidden;">

<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li class="active">${titleContent }</li>
    </ul>
</div>

<div id="btn" style="margin-top:2px;">
						    <button class="btn btn-white btn-default btn-bold" onclick="showTomcatIndex()"  >
								<i class="ace-icon  fa fa-plus  green"></i>
								Tomcat监测
							</button>
							<button class="btn btn-white btn-default btn-bold" onclick="onEdit()" >
								<i class="ace-icon fa  fa-pencil-square-o bigger-120 default"></i>
								修改
							</button>
							<button class="btn btn-white btn-success btn-bold"onclick="startMonitor()" >
								<i class="ace-icon fa fa-refresh bigger-120 green"></i>
								监测开启
							</button>
							<button class="btn btn-white btn-warning btn-bold"onclick="stopMonitor()">
								<i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
								监测终止
							</button>
							<button class="btn btn-white btn-warning btn-bold"onclick="onDelete()">
								<i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
								删除
							</button>
							<button class="btn btn-white btn-danger btn-bold" onclick="showLine()">
								<i class="ace-icon fa fa-file-text-o bigger-120 "></i>
								实时监测
							</button>
							<button class="btn btn-white btn-danger btn-bold" onclick="historyLine()">
								<i class="ace-icon fa fa-file-text-o bigger-120 "></i>
								历史趋势图
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


<%--<div id="maingrid"></div>--%>
<div style="margin:0 auto;">
    <div id="trTxt" class="dN" style="border:0px;height:200px;line-heigth:31px;text-align:center;margin-top:300px;">
           		<span id="searching">
           			<img src="${ctx}/img/loading.gif" alt="#" />&nbsp;&nbsp;开启监测中...
           		</span>
    </div>
    <div id="trMsg"  >
        <div id="maingrid" ></div>
    </div>
</div>
</body>
</html>
