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
		<title>IP HOST </title>

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
 			url : '${ctx}/fas/res/host/ipHost/searchAjaxPage.do',
            datatype: 'json',  
          
    	    colModel : [ 
    	        	 {label:'机构名称',name : 'orgName',index : 'orgName',width : 70},
    	        	 {label:'使用人',name : 'loginName',index : 'loginName',width : 70},
    	        	 {label:'设备类型',name : 'ipHostType',index : 'ipHostType',width : 60},
    	        	 {label:'IP地址',name : 'ipAddr',index : 'ipAddr',width : 120},
    	        	 {label:'MAC地址',name : 'macAddr',index : 'macAddr',width : 120}, 
    	        	 
    	        	 {label:'接入交换机',name : 'switchIpLong',index : 'switchIpLong',width : 120,
    	        	 
    	        	 	 formatter:function(cellvalue, options, row){
    	        			 return row.switchIp;
    	        		  }
    	        	 }, 
    	        	
    	        	 {label:'交换机接口',name : 'switchIfIndex',index : 'switchIfIndex',width: 80},
    	        	 {label:'Vlan',name : 'vlanId',index : 'vlanId',width: 60},
    	        	
    	        	 {label:'接口状态',name : 'adminStatus',index : 'adminStatus',width: 70,
    	        	 	
    	        	 	formatter:function(cellvalue, options, row){
    	        			 var html='';
	                        	if(row.adminStatus=='up'){
	                        		html = '<span style=\"line-height:22px;color:green;\">'+row.adminStatus+'</span>';
	                        	}else{
	                        		html = '<span style=\"line-height:22px;color:red;\">'+row.adminStatus+'</span>';
	                        	}
	                        	if(row.adminStatus!=''&&row.operStatus!='')
	                        		html = html+'/';
	                        	if(row.operStatus=='up'){
	                        		html =html +'<span style=\"line-height:22px;color:green;\">'+row.operStatus+'</span>';
	                        	}else{
	                        		html =html +'<span style=\"line-height:22px;color:red;\">'+row.operStatus+'</span>';
	                        	}
	                            return html;
    	        		  }
    	        	  
    	        	 },
    	        	 {label:'信息点编号',name : 'infoNo',index : 'infoNo',width: 70,
    	        	    formatter:function(cellvalue, options, row){
    	        			 if(row.infoNo==""||row.infoNo==null)
						 			return ''
						 		else
						 	    	return '<span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick="viewCablingInfo(\'' + row.infoPointNo + '\');">'+row.infoNo+'</span>';
						 	}
    	        	 },
    	        	 
    	        	 {label:'操作',width: 100,
    	        	 
    	        	   formatter:function(cellvalue, options, row){
    	        			     	var html='';
                        	if(row.isoStatus=='2'){
                        		<shiro:hasPermission name="shutdown"> 
	                            	html += '<span style="color:#808080" >隔离</span> ';
	                            </shiro:hasPermission>
	                            <shiro:hasPermission name="noshutdown"> 
                            		html += ' | <span  onclick="isolationByOne(\'' + row.id + '\',\'' + row.switchId + '\',\'' + row.ifIndexId + '\',\'no\');">恢复</span>  | ';
                            	</shiro:hasPermission>
                            }else{
                            	<shiro:hasPermission name="shutdown"> 
                            		html += '<span  onclick="isolationByOne(\'' + row.id + '\',\'' + row.switchId + '\',\'' + row.ifIndexId + '\',\'yes\');">隔离</span>';
                            	</shiro:hasPermission>
                            	<shiro:hasPermission name="noshutdown"> 
	                            	html += ' | <span style="color:#808080" >恢复</span>  ';
	                            </shiro:hasPermission>
                            }
                            return html;
                        }
    	        	 }], 
    	        	pager: '#pager',  
		            rowNum: 100,
		            rowList: [100, 500, 1000],
		            sortname: 'organName',  
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
        
        function doData(data)
        {
   			if(data.result=='success'){
   				refreshData();
   			}
   			else{
   				window.$.ligerDialog.error(data.message);
   			}
        }
        
        
        
       function reloadGrid(data){
         layer.closeAll(); 
         jQuery('#list').jqGrid('clearGridData');
         jQuery('#list').jqGrid(
                'setGridParam',{  
                datatype:'local',  
    			data:data.rows
			}).trigger('reloadGrid');

        }
		
        
        
        function refreshData()
        {
        	var url = timeURL('${ctx}/system/user/getData.do');
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
           			dataMain.Rows = data;
					loadData();
        		}
        	});        			
        }
        
        function onSearchAdd()
        {
        	var url = '${ctx}/fas/res/host/resHosts/searchAdd.do';
        	layer_show('服务器搜索添加', url, 900, 430);
        }
        
        function onAdd()
        {
        	var url = '${ctx}/fas/res/host/ipHost/add.do';
        	layer_show('添加IP设备', url, 900, 550);
        }
		
		function onImport()
        {
        	var url = '${ctx}/fas/res/host/ipHost/importGo.do';
        	layer_show('导入IP Host设备', url, 900, 550);
        }
        
        function onExport()
        {
        	location.href='${ctx}/fas/res/host/ipHost/exportExcel.do';
        }
        
        function onEdit()
        {
            var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要修改的设备!', {icon: 7, time: 1500});
            }else if(ids.length>1){
              layer.msg('只能选择一个设备修改!', {icon: 7, time: 1500});
            }else{
              editRow(ids);
            }
        	
        }
        
        function editRow(id)
        {
        	var url = '${ctx}/fas/res/host/ipHost/edit.do?ipHostId='+id;
        	layer_show('修改IP设备',url,800, 500);
        }
        
        function onMultiEdit(){
          var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        	if( ids.length == 0 ){
        		layer.msg('请选择至少一个IP Host设备！', {icon: 7, time: 1500});
        	}else{
            	var url = '${ctx}/fas/res/host/ipHost/multiEdit.do?ipHostIds='+ids;
            	layer_show('导批量修改IP设备', url, 800, 500);
        	}
        }
        function onView()
        { 
           var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要查看的设备!', {icon: 7, time: 1500});
            }else if(ids.length>1){
              	layer.msg('只能选择一个设备查看!', {icon: 7, time: 1500});
            }else{
              	viewRow(ids);
            }
        }
        
        function viewRow(id)
        {
        	var url = '${ctx}/fas/res/host/ipHost/view.do?id='+id;
        	layer_show('查看业IP设备详情', url, 800, 500);
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

        function onDelete()
        {
            var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        	 if( ids.length == 0 ){
        		layer.msg('请选择至少一个IP Host设备！', {icon: 7, time: 1500});
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
        	var url = '${ctx}/fas/res/host/ipHost/delete.do?id='+ids;
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
		function isolationByOne(id,switchId,ifIndexId,flag){
			if(flag=='yes'){
				if(isNullStr(switchId)||isNullStr(ifIndexId)){
               		window.$.ligerDialog.error('隔离失败！'); 
         			return; 
             	}else{
					window.$.ligerDialog.confirm('是否确认隔离设备？', function (yes)
		        	{
		        		if(yes) isolationRow(id,switchId,ifIndexId,flag);
		        	});
	        	}
			}else{
				if(isNullStr(switchId)||isNullStr(ifIndexId)){
               		window.$.ligerDialog.error('恢复失败！'); 
         			return;
            	}else{
					window.$.ligerDialog.confirm('是否确认恢复隔离的设备？', function (yes)
		        	{
		        		if(yes) isolationRow(id,switchId,ifIndexId,flag);
		        	});
	        	}
			}
		}
		
		function isolationRow(ipHostIds,switchIds,ifIndexIds,flag){
			var url = timeURL('${ctx}/fas/res/host/ipHost/isolation.do?switchIds='+switchIds+'&ipHostIds='+ipHostIds+'&ifIndexIds='+ifIndexIds+'&flag='+flag);
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
       				if(!isNullStr(data.message)){
	                		window.$.ligerDialog.error(data.message); 
           					return; 
	                	}
                      for (var i = 0; i < dataMain.Rows.length; i++)
		                {
		                  for(var j=0;j<data.listIphost.length;j++){
		                	if(data.listIphost[j].isoStatus=='1'&&flag=='yes'){
		                		window.$.ligerDialog.error('隔离失败！'); 
            					return; 
		                	}
		                	else if(data.listIphost[j].isoStatus=='2'&&flag=='no'){
		                		window.$.ligerDialog.error('恢复失败！'); 
            					return;
		                	}
		                    if (dataMain.Rows[i].id == data.listIphost[j].id){
		                    	if(data.listIphost[j].isoStatus=='2'&&flag=='yes'){
		                			window.$.ligerDialog.success('隔离成功！');
			                	}
			                	else if(data.listIphost[j].isoStatus=='1'&&flag=='no'){
			                		window.$.ligerDialog.success('恢复隔离成功！'); 
			                	}
		                    	dataMain.Rows[i] = data.listIphost[j];
		                    	break;
		                   	}
		                   }
		                }
             		loadData();
        		}
        	});
		}
		
		function onIsolation(){
			window.$.ligerDialog.confirm('是否确认隔离设备？', function (yes)
        	{
        		if(yes) isolationRows(selectRowData.accessPathId);
        	});
		}
		
		function onRestore(){
			window.$.ligerDialog.confirm('是否确认恢复隔离的设备？', function (yes)
        	{
        		if(yes) restoreRows(selectRowData.accessPathId);
        	});
		}
		
        function onSearch()
        {
        	var url = '${ctx}/fas/res/host/ipHost/searchView.do';
        	layer_show('查询', url, 800, 500);
        }
        
        function onRefresh(){
        	window.location.reload();
        }
		
		function onRefreshIf(){
        	if( checkedItem.length == 0 ){
        		window.$.ligerDialog.warn('请选择至少一个交换机！');
        	}
        	else{
            	var ids = checkedItem.join(',');
            	var url = timeURL('${ctx}/fas/res/host/ipHost/reflesh.do?ids='+ids);
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			if( data.result == 'success' ){
                			window.$.ligerDialog.success('Snmp刷新命令发送成功！');            				
            			}
            			else{
                    		window.$.ligerDialog.error(data.message);
            			}
            		}
            	});        			       		
        	}
        }
        
        
        
        
        
        function viewCablingInfo(infoNo){
       		var url = timeURL('${ctx}/fas/res/cablingInfo/view.do?id='+infoNo);
       		window.showDlg('信息点连接信息', 700, 320, url);
		//window.open(url,'', 'height=300, width=724, top=160, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
    	}
    	
    	function portManage(id,ip,macIp,ifIndex,switchIp){
       		var url = timeURL('${ctx}/fas/res/host/ipHost/macManage.do?ip='+ip+'&macIp='+macIp+'&ifIndex='+ifIndex+'&switchIp='+switchIp+'&id='+id);
       		window.showDlg('端口安全', 700, 320, url);
		//window.open(url,'', 'height=300, width=724, top=160, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
    	}
    	
    	function confAccess(){
    	   var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
    	   
    	   if(ids.length==0){
    	     layer.msg('请选择至少一个IP Host设备！', {icon: 7, time: 1500});
    	   }else{
    	     
    	   }
    	}
    </script>
<body>  
   <div id="btn" style="margin-top:2px;">
<%--   							<button class="btn btn-white btn-default btn-bold" onclick="onSearchAdd()"  >--%>
<%--							<i class="ace-icon  fa fa-plus  green"></i>--%>
<%--								搜索添加--%>
<%--							</button>--%>
						    <button class="btn btn-white btn-default btn-bold" onclick="onAdd()"  >
							<i class="ace-icon  fa fa-plus  green"></i>
								添加
							</button>
						    <button class="btn btn-white btn-default btn-bold" onclick="onEdit()" >
								<i class="ace-icon fa  fa-pencil-square-o bigger-120 default"></i>
								修改
							</button>
							<button class="btn btn-white btn-info btn-bold"onclick="onMultiEdit()">
								<i class="ace-icon fa fa-pencil-square-o bigger-120 blue"></i>
								批量修改
							</button>
							<button class="btn btn-white btn-warning btn-bold"onclick="onDelete()">
								<i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
								删除
							</button>
							
							<button class="btn btn-white btn-warning btn-bold"onclick="onSearch()">
								<i class="ace-icon fa fa-search bigger-120 orange"></i>
								查询
							</button>
							
							<button class="btn btn-white btn-danger btn-bold" onclick="onView()">
								<i class="ace-icon fa fa-file-text-o bigger-120 "></i>
								详情
							</button>
							
<!-- 							<button class="btn btn-white btn-purple btn-bold" onclick="confAccess()"> -->
<!-- 								<i class="ace-icon fa fa-sign-out bigger-120 "></i> -->
<!-- 								配置访问密码 -->
<!-- 							</button> -->
							
							<button class="btn btn-white btn-purple btn-bold" onclick="onExport()">
								<i class="ace-icon fa fa-sign-out bigger-120 "></i>
								导出
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

<div class="col-xs-12" style="overflow: hidden;">
    <table id="list"></table>
    <div id="pager"></div>
</div>

    <div id="searchDlg" style="padding:0px;display: none">
    	<form style="padding:0px;" id="formSearch" action="${ctx}/fas/res/host/ipHost/search.do" method="post">
    	<table align="center" class="tb_edit">
            <tr>
                <td class="td_w1"></td>
                <td class="td_w2"></td>
                <td class="td_w3"></td>
                <td class="td_w1"></td>
                <td class="td_w2"></td>
                <td class="td_w3"></td>
            </tr>
            <tr>
                <td align="right">所属机构:</td>
                <td><s:objSelect name="filter_EQS_orgId" objName="OrgOrganization" textAtt="orgName" valueAtt="orgId" treeCodeAtt="treeCode" prompt="全部" lclass="ip1"  ltype="select"/></td>
                <td colspan="4"></td>
            </tr>
             <tr>
                <td align="right">交换机IP:</td>
                <td><input name="filter_LIKES_switchIp" class="ip1" type="text" ltype="text"/></td>
                <td></td>
                <td align="right">设备IP地址:</td>
                <td><input name="filter_LIKES_ipAddr" class="ip1" type="text" ltype="text"/></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">MAC地址:</td>
                <td><input name="filter_LIKES_macAddr" class="ip1" type="text" ltype="text"/></td>
                <td></td>
                <td align="right">信息点编号:</td>
                <td><input name="filter_LIKES_infoPointNo" class="ip1" type="text" ltype="text"/></td>
                <td></td>
            </tr>   
            <tr>
                <td colspan=3 align="right" style="padding:10px">
                <input id="bnSearch" type="submit" value="查 询" class="l-button" /> 
                </td>
                <td colspan=3 style="padding:10px">
                <input id="bnReset" type="button" value="重 置" class="l-button" />
                </td>
            </tr>   
        </table>
        </form>
    </div>
    </div>
  </body>
</html>
