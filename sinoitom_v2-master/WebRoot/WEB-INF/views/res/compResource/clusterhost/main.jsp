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
		<title>集群服务器 </title>

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
  
    //子表格的ID
    var subgrid_table_id; 
    var count=0;
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
    	        	 {label:'集群名称',name : 'hostName',index : 'hostName',width : 100},
    	        	 {label:'集群访问IP',name : 'ipAddress',index : 'ipAddress',width : 100},
    	        	 {label:'集群用途',name : 'description',index : 'description',width : 500
    	        	}], 
    	        	
    	        	 viewrecords: true,
    	        	 rowNum : 50, rowList : [ 50, 100, 150 ], 
    			 		pager : '#pager',
    			 		height:gridHeight,
    					altRows: true,
    					toolbar:[true,'top'],
    					multiselect: true,
    					multiboxonly: true,
<%--    					loadComplete : function() {--%>
<%--    						var table = this;--%>
<%--    							setTimeout(function(){--%>
<%--    							updatePagerIcons(table);--%>
<%--    							}, 0);--%>
<%--    					},--%>
<%--    					onSelectRow: function (rowid, rowindex)--%>
<%--    							         {--%>
<%--    							getSelectService(rowid, rowindex);--%>
<%--    							         },--%>
    					
    					
    					subGrid: true,  // (1)开启子表格支持  
    					subGridOptions : {
    						plusicon : "ace-icon fa fa-angle-double-down",  //收缩效果
    						minusicon  : "ace-icon fa fa-angle-double-up"  //展开效果
    					//	openicon : "ace-icon fa fa-chevron-right center orange"
    					},
    					subGridRowExpanded: function(subgrid_id, row_id) {  // (2)子表格容器的id和需要展开子表格的行id，将传入此事件函数  
    						 
                    		var rowData = $("#list").jqGrid('getRowData', row_id);//通过索引获取当前行对象
                    		subgrid_table_id = subgrid_id + "_t";   // (3)根据subgrid_id定义对应的子表格的table的id  
                    		var subgrid_pager_id;  
                    		subgrid_pager_id = subgrid_id + "_pager"  // (4)根据subgrid_id定义对应的子表格的pager的id  
                    		// (5)动态添加子报表的table和pager  
                    		$("#" + subgrid_id).html("<table id='"+subgrid_table_id+"' ></table><div id='"+subgrid_pager_id+"' ></div>");  
                      		//alert(subgrid_table_id);
                    		// (6)创建jqGrid对象  
    		                $("#" + subgrid_table_id).jqGrid({  
    						 datatype : "json", 
    						 url:' ${ctx}/res/host/clusterHosts/getResHostsByHostId.do?id='+row_id,
    						 //colNames : [ 'Id', '服务器IP', 'PID', '协议号', '协议名称', '服务端口', '应用服务名称', '服务分类', '服务命令', '操作' ],
    						 	colModel : [ 
					    	        	 {label:'服务器类型',name : 'hostType',index : 'hostType',sortable:false,width : 70,
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
					    	        	 {label:'服务器IP',name : 'ipAddress',index : 'ipAddress',sortable:false,width : 100},
					    	        	 {label:'系统架构',name : 'osStruct',index : 'osStruct',sortable:false,width : 65},
					    	        	 {label:'操作系统分类',name : 'osClass',index : 'osClass',sortable:false,width : 80},
					    	        	 {label:'操作系统版本',name : 'osVersion',index : 'osVersion',sortable:false,width : 140},
					    	        	 {label:'操作系统类型',name : 'osType',index : 'osType',sortable:false,width : 80},
					    	        	 {label:'版本分类',name : 'osVersionType',index : 'osVersionType',sortable:false,width : 65},
					    	        	 {label:'版本号',name : 'osRelease',index : 'osRelease',sortable:false,width : 90},
					    	        	 {label:'访问方式',name : 'accessmode',index : 'accessmode',sortable:false,width : 65},
					    	        	 {label:'用户登录ID',name : 'devAcsUserId',index : 'devAcsUserId',sortable:false,width : 65,hidden:true},
					    	        	 {label:'用户名称',name : 'userName',index : 'userName',sortable:false,width : 60
    						                
    	        						 }], 
//     						 	viewrecords : true,//定义是否要显示总记录数
                                rowNum: -1,//在grid上显示记录条数，这个参数是要被传递到后台(rowNum:-1为显示全部长度)
//     						 	pager : '#'+subgrid_pager_id,//定义翻页用的导航栏，必须是有效的html元素。翻页工具栏可以放置在html页面任意位置
    						 	sortname:'id',//默认的排序列。可以是列名称或者是一个数字，这个参数会被提交到后台
//     							rowList:[50,100,150],//一个下拉选择框，用来改变显示记录数，当选择时会覆盖rowNum参数传递到后台
    							altRows: true,//设置表格 zebra-striped（斑马条纹）值,如果是false要重新加载数据
    							autowidth: true, //表宽自动调整
    							//multiselect : true,
    							//multiboxonly: true,
    							loadComplete : function() {
//     								var table = this;
//     								updatePagerIcons(table);
    									
    							   var curRowDatas = jQuery("#"+subgrid_table_id).jqGrid('getRowData');
    							   //alert(curRowDatas);
    							   for(var i=0;i<curRowDatas.length;i++){
    							      if(curRowDatas[i].reachability!="reachable"){
    							     	$("#"+subgrid_table_id).find("input[id='jqg_"+subgrid_table_id+"_"+curRowDatas[i].id+"']").hover(function(){$(this).css("cursor","url('../../img/no.png'),pointer")});
    							  	}	
    							   }
    							   	
    							}, 
    							onSelectRow:function(rowId,status){
    							  if(status){
    							    count++;
    							  }else{
    							    if(count>0){
    							     count--;
    							    }
    							  }
    							  var row = jQuery("#"+subgrid_table_id).jqGrid('getRowData',rowId);
    							  if(row.reachability!="reachable"){
    							    $("#"+subgrid_table_id).jqGrid("setSelection", rowId,false);
    							  }
    							},
    							onSelectAll:function(rowId,status){
    							  if(!status){
    							    count=0;
    							  }else{
    							     var rowdatas = jQuery("#"+subgrid_table_id).jqGrid('getGridParam', 'selarrrow');
    							  count=rowdatas.length;
    							  }
    							}
    							
    		               });  
    		               
               }
    					
    					
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
        
        
        
        function onAdd()
        {
        	var url = '${ctx}/res/host/clusterHosts/add.do';
        	layer_show('添加集群信息', url, 800, 350);
        }
		
        
        function onEdit()
        {
            var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要修改的集群服务器!', {icon: 7, time: 1500});
            }else if(ids.length>1){
              layer.msg('只能选择一条集群服务器数据修改!', {icon: 7, time: 1500});
            }else{
              editRow(ids);
            }
        	
        }
        
        function editRow(id)
        {
        	var url = '${ctx}/res/host/clusterHosts/edit.do?id='+id;
        	layer_show('修改集群服务器',url,800, 350);
        }
        
        function onView()
        { 
           var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要查看的集群服务器!', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一条集群服务器数据查看!', {icon: 7, time: 1500});
            }else{
              	viewRow(ids);
            }
        }
        
        function viewRow(id)
        {
        	var url = '${ctx}/res/host/clusterHosts/view.do?id='+id;
        	layer_show('查看资源详情', url, 800, 350);
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
        		layer.msg('请先选择一条集群服务器！', {icon: 7, time: 1500});
        	}else if(id.length>1){
              	layer.msg('只能选择一条集群服务器数据!', {icon: 7, time: 1500});
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
        	var url = '${ctx}/res/host/clusterHosts/delete.do?id='+id;
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
        
        function onRelation(){
        	var id = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
	       	 if( id.length == 0 ){
	       		layer.msg('请先选择一条集群服务器数据！', {icon: 7, time: 1500});
	       	}else if(id.length>1){
	             	layer.msg('只能选择一条集群服务器数据!', {icon: 7, time: 1500});
	           }else{
	        	   relationRow(id);
	       	} 
        }
        
        function relationRow(id){
        	var url = '${ctx}/res/host/clusterHosts/relationHost.do?id='+id;
        	layer_show('关联服务器', url, 800, 600);
        }
        
        function onCancelRelation(){
        	var id = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
	       	 if( id.length == 0 ){
	       		layer.msg('请先选择一条集群服务器数据！', {icon: 7, time: 1500});
	       	}else if(id.length>1){
	             	layer.msg('只能选择一条集群服务器数据!', {icon: 7, time: 1500});
	           }else{
	        	   cancelRelationRow(id);
	       	} 
        }
        
        function cancelRelationRow(id){
        	var url = '${ctx}/res/host/clusterHosts/cancelRelationHost.do?id='+id;
        	layer_show('取消关联', url, 800, 600);
        }
		
        
    </script>
<body>  
   <div id="btn" style="margin-top:2px;">
<%--						    <button class="btn btn-white btn-default btn-bold" onclick="onAdd()"  >--%>
<%--							<i class="ace-icon  fa fa-plus  green"></i>--%>
<%--								添加--%>
<%--							</button>--%>
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
<%--							<button class="btn btn-white btn-info btn-bold" onclick="onExam()">--%>
<%--						        <i class="ace-icon fa fa-check-square-o bigger-120 "></i>--%>
<%--						       	审核--%>
<%--						    </button>--%>
							<button class="btn btn-white btn-default btn-bold" onclick="onRelation()"  >
							<i class="ace-icon  fa fa-plus  green"></i>
								关联服务器
							</button>
							<button class="btn btn-white btn-warning btn-bold"onclick="onCancelRelation()">
								<i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
								取消关联
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
