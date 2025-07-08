<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>交换机接口管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/main.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var dataMain = {};
    	var gridMain;
        var selectRowData = null;
    	var dlgSearch = null;
    	var dlgAdvancedSearch = null;
    	baseUrl='${ctx}/fas/res/dailyMaintain/netInfoManage';
        $(function ()
        {
            $("#toptoolbar").ligerToolBar({ 
				items: 
            		[
                	{ text:'修改', click: onEdit, icon:'edit'},
                	{ line:true },
                	{ text:'查看', click: onView, icon:'view' },
                	{ line:true },
                	{ text:'更换设备', click: onSwapDevice, icon:'edit'},
                	{ line:true },
                	{ text:'更换位置', click: onSwapLocation, icon:'refresh' },
                	{ line:true },
                	{ text:'Mac地址绑定', click: onBinds, icon:'refresh' },
                	{ line:true },
                	{ text:'Mac地址解绑', click: onUnbinds, icon:'refresh' },
                	{ line:true },
                	{ text:'高级查询', click: onAdvancedSearch, icon:'search' },
                	{ line:true },
                	{ text:'刷新', click: onRefresh, icon:'refresh' }
                	]
            });

            //dataMain.Rows = null;
            gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                     { display:'交换机IP地址', name:'switchIp', width:100, align:'left' },
                     { display:'IfIndex', name:'ifIndex', width:70, align:'left', type:'int'},
                     { display:'显示名称', name:'ifIndex', width:100, align:'left',
                     	render: function (row)
	                  		 {
		                    	return row.ifName;
		                   	}
                      },
                     { display:'(管理/工作)状态', name:'iadminStatus', width:110, align:'center', 
                    	render: function (row)
	                        {
	                        	var html='';
	                        	if(row.iadminStatus=='up'){
	                        		html = '<span style=\"align:certer;line-height:22px;color:green;\">'+row.iadminStatus+'</span>';
	                        	}else{
	                        		html = '<span style=\"align:certer;line-height:22px;color:red;\">'+row.iadminStatus+'</span>';
	                        	}
	                        	if(row.ioperStatus=='up'){
	                        		html = html+'/<span style=\"align:certer;line-height:22px;color:green;\">'+row.ioperStatus+'</span>';
	                        	}else{
	                        		html = html+'/<span style=\"align:certer;line-height:22px;color:red;\">'+row.ioperStatus+'</span>';
	                        	}
	                            return html;
	                        }
	                },
	                { display:'使用人', name:'loginName', width:80, align:'left' },
                    { display:'设备类型', name:'ipHostTypeId', width:90, align:'left' },
                    { display:'IP地址', name:'hostIP', width:110, align:'left',type:'ip' },
                    { display:'MAC地址', name:'hostMac', width:120, align:'left' },
                    { display:'信息点编号', name:'infoPointNo', width:90, align:'left'},
                    { display:'Vlan', name:'vlan', width:70, align:'right'},
                    { 
                    	display:'操作', width:160,name:'isoStatus',
                        render: function (row)
                        {
                        	var html='<span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick="manage(\'' + row.hostId + '\',\'' + row.switchId + '\',\'' + row.ifIndex + '\');">维护</span>  | ';
                        	if(row.isoStatus=='2'){
                        		<shiro:hasPermission name="shutdown"> 
	                            html += '<span style="color:#808080" >隔离</span>';
	                            </shiro:hasPermission>
	                            <shiro:hasPermission name="noshutdown"> 
                            	html += ' | <span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick="isolationByOne(\'' + row.switchId + '\',\'' + row.ifIndex + '\',\'no\');">恢复</span>';
                            	</shiro:hasPermission>
                            }else{
                            	<shiro:hasPermission name="shutdown"> 
                            	html += '<span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick="isolationByOne(\'' + row.switchId + '\',\'' + row.ifIndex + '\',\'yes\');">隔离</span>';
                            	</shiro:hasPermission>
                            	<shiro:hasPermission name="noshutdown"> 
	                            html += ' | <span style="color:#808080" >恢复</span>';
	                            </shiro:hasPermission>
                            }
                            return html;
                        }
                    }
               		],  
				url: timeURL('${ctx}/fas/res/dailyMaintain/netInfoManage/searchAjaxPage.do'),	
		    	sortName: 'switchIp',sortOrder: 'asc',
				root: 'result', record: 'totalItems',
				pageParmName: 'pageNo', pagesizeParmName: 'pageSize', 
				sortnameParmName: 'orderBy', sortorderParmName: 'orderDir',
				checkbox: 'true', alternatingRow: 'true',
				width: '100%', height:'100%', 
				pageSize: '50', pageSizeOptions: [50, 100, 200, 500,1000],
				rownumbers:true,
                isChecked: f_isChecked,
                onCheckRow: f_onCheckRow,
                onCheckAllRow: f_onCheckAllRow,
                width: '100%', height:'100%', 
                pageSize: '50', pageSizeOptions: [10, 20, 30, 50, 100],
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                } 
            });

            /* $("#formSearch").ajaxForm({
            	dataType: 'json',
            	success: function(data) { 
           			dataMain.Rows = data;
					loadData();
            		dlgSearch.hide();
            	}
            }); */ 
            
            $("#bnAdvancedSearch").click(f_bnAdvancedSearch);
            /* $("#bnReset").click(function()
            {
            	window.parent.$("input[ltype='text'][name*='filter_']").val("");
            	window.parent.$("select[name*='filter_']").val("");
            }); */
            
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
        function findCheckedItem(id)
        {
            for(var i =0;i<checkedItem.length;i++)
            {
                if(checkedItem[i] == id) return i;
            }
            return -1;
        }
        function addCheckedItem(id)
        {
            if(findCheckedItem(id) == -1)
                checkedItem.push(id);
        }
        function removeCheckedItem(id)
        {
            var i = findCheckedItem(id);
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
   				window.parent.$.ligerDialog.error(data.message);
   			}
        }
        
        
        function onAdd()
        {
        	var url = '${ctx}/fas/res/net/switch/interface/add.do?devId=${deviceId}';
     		window.parent.showDlg('添加接口', 800, 500, timeURL(url));     
        }

        function onEdit()
        {
            if (!selectRowData) { 
            	window.parent.$.ligerDialog.error('请选择一行数据！'); 
            	return; 
            }
        	editRow(selectRowData.id.switchId,selectRowData.id.ifIndex);
        }
        
        function editRow(switchId, ifIndex)
        {
        	var url = timeURL('${ctx}/fas/res/net/switch/interface/edit.do?switchId='+switchId+'&ifIndex='+ifIndex);
     		//window.parent.$.showDlg('修改接口', 800, 500, url);
     		window.open(url,'', 'height=400, width=800, top=160, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');     
        }
        
        function onSwapDevice(){
        	if( checkedItem.length == 0 ){
        		window.parent.$.ligerDialog.error('请选择至少一台设备！');
        	}
        	else{
        		var loading= $.ligerDialog.loading('正在执行更换设备命令......</br>请稍候！！');
            	var ids = checkedItem.join(',');
            	var url = timeURL('${ctx}/fas/res/dailyMaintain/desktopDevice/swapDevice.do?ids='+ids);
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			loading.close();
            			if( data.result == 'success' ){
                			window.parent.$.ligerDialog.success('更换设备成功！');
                			loadData();            				
            			}
            			else{
                    		window.parent.$.ligerDialog.error(data.message);
            			}
            		}
            	});        			       		
        	}
        }
        
        function onSwapLocation(){
        	if( checkedItem.length == 0 ||checkedItem.length == 1){
        		window.parent.$.ligerDialog.error('请选择至少两台设备！');
        	}
        	else{
        		var loading= $.ligerDialog.loading('正在执行更换位置命令......</br>请稍候！');
            	var ids = checkedItem.join(',');
            	var url = timeURL('${ctx}/fas/res/dailyMaintain/desktopDevice/swapLocation.do?ids='+ids);
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			loading.close();
            			if( data.result == 'success' ){
                			window.parent.$.ligerDialog.success('更换位置成功！');
                			onRefresh();            				
            			}
            			else{
                    		window.parent.$.ligerDialog.error(data.message);
            			}
            		}
            	});        			       		
        	}
        }
        
        function onBinds(){
        	if( checkedItem.length == 0 ){
        		window.parent.$.ligerDialog.error('请选择至少一个设备！');
        	}
        	else{
        		var loading= $.ligerDialog.loading('正在执行绑定命令......</br>请稍候！');
            	var ids = checkedItem.join(',');
            	var url = timeURL('${ctx}/fas/res/dailyMaintain/desktopDevice/binds.do?ids='+ids);
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			loading.close();
            			$.ligerDialog.open({ height: 300, content:data.message, width: 620, showMax: true,isResize: true, slide: false });
            			onRefresh();
            		}
            	});        			       		
        	}
        }
        
        function onUnbinds(){
        	if( checkedItem.length == 0 ){
        		window.parent.$.ligerDialog.error('请选择至少一个设备！');
        	}
        	else{
        		var loading= $.ligerDialog.loading('正在执行解绑命令......</br>请稍候！');
            	var ids = checkedItem.join(',');
            	var url = timeURL('${ctx}/fas/res/dailyMaintain/desktopDevice/unbinds.do?ids='+ids);
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			loading.close();
            			//if( data.result == 'success' ){
                		$.ligerDialog.open({ height: 300, content:data.message, width: 620, showMax: true,isResize: true, slide: false });
            			onRefresh();   				
            			/* }
            			else{
                    		window.parent.$.ligerDialog.error(data.message);
            			} */
            		}
            	});        			       		
        	}
        }
        
          function onRefreshDev(){
        	if( checkedItem.length == 0 ){
        		window.parent.$.ligerDialog.error('请选择至少一个交换机！');
        	}
        	else{
        		var loading= $.ligerDialog.loading('正在执行Snmp刷新命令......</br>请稍候！');
            	var ids = checkedItem.join(',');
            	var url = timeURL('${ctx}/fas/res/host/ipHost/reflesh.do?ids='+ids);
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			loading.close();
            			if( data.result == 'success' ){
                			window.parent.$.ligerDialog.success('Snmp刷新命令发送成功！'); 
                			onRefresh();             				
            			}
            			else{
                    		window.parent.$.ligerDialog.error(data.message);
            			}
            		}
            	});        			       		
        	}
        }
        
        function saveOK(action, data)
        {
         		loadData();
        }
        
        function onView()
        {
            if (!selectRowData) { 
            	window.parent.$.ligerDialog.error('请选择一行数据！'); 
            	return; 
            }
            viewRow(selectRowData.id.switchId,selectRowData.id.ifIndex);
        }
        
        function viewRow(switchId, ifIndex)
        {
        	var url = timeURL('${ctx}/fas/res/net/switch/interface/view.do?switchId='+switchId+'&ifIndex='+ifIndex);
     		//window.parent.showDlg('接口信息', 800, 480, url);
     		window.open(url,'', 'height=400, width=800, top=160, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');     
        }

        
        function onDelete()
        {
            if (!selectRowData) { 
            	window.parent.$.ligerDialog.error('请选择一行数据！'); 
            	return; 
            }
        	deleteRow(selectRowData.id);
        }
        
        function deleteRow(switchId, ifIndex)
        {
        	window.parent.$.ligerDialog.confirm('是否确认删除该行数据？', function (yes)
            {
                if(yes) delRow(id);
            });
        }
        
        function delRow(id)
        {
        	var url = timeURL('${ctx}/fas/res/net/switch/interface/delete.do?switchId='+switchId+'&ifIndex='+ifIndex);
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
        			doData(data);
        		}
        	});        			
        }

        
        function onAdvancedSearch()
        {
        	if( dlgAdvancedSearch ){
        		dlgAdvancedSearch.show();
        	}
        	else{
            	dlgAdvancedSearch = window.parent.$.ligerDialog.open({ title:'查询', width:720, height:350, target: $("#advancedSearchDlg") });
        	}
        }
        
        function onRefresh(){
        	window.location.reload();
        }
        
	function isolationByOne(switchId,ifIndexId,flag){
			if(flag=='yes'){
				if(isNullStr(switchId)||isNullStr(ifIndexId)){
               		window.parent.$.ligerDialog.error('隔离失败！'); 
         			return; 
             	}else{
					window.parent.$.ligerDialog.confirm('是否确认隔离设备？', function (yes)
		        	{
		        		if(yes) isolationRow(switchId,ifIndexId,flag);
		        	});
	        	}
			}else{
				if(isNullStr(switchId)||isNullStr(ifIndexId)){
               		window.parent.$.ligerDialog.error('恢复失败！'); 
         			return;
            	}else{
					window.parent.$.ligerDialog.confirm('是否确认恢复隔离的设备？', function (yes)
		        	{
		        		if(yes) isolationRow(switchId,ifIndexId,flag);
		        	});
	        	}
			}
		}
	
	function isolationRow(switchId,ifIndexId,flag){
			var url = timeURL('${ctx}/fas/res/net/switch/interface/isolation.do?switchId='+switchId+'&ifIndexId='+ifIndexId+'&flag='+flag);
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
       				if(!isNullStr(data.message)){
	                		window.parent.$.ligerDialog.error(data.message); 
           					return; 
	                	}
                      for (var i = 0; i < dataMain.Rows.length; i++)
		                {
		                	if(data.portinfo.isoStatus=='1'&&flag=='yes'){
		                		window.parent.$.ligerDialog.error('隔离失败！'); 
            					return; 
		                	}
		                	else if(data.portinfo.isoStatus=='2'&&flag=='no'){
		                		window.parent.$.ligerDialog.error('恢复失败！'); 
            					return;
		                	}
		                    if (dataMain.Rows[i].ifIndex == data.portinfo.ifIndex){
		                    	if(data.portinfo.isoStatus=='2'&&flag=='yes'){
		                			window.parent.$.ligerDialog.success('隔离成功！');
			                	}
			                	else if(data.portinfo.isoStatus=='1'&&flag=='no'){
			                		window.parent.$.ligerDialog.success('恢复隔离成功！'); 
			                	}
		                    	dataMain.Rows[i] = data.portinfo;
		                    	break;
		                   	}
		                }
             		loadData();
        		}
        	});
		}
	
	function multiView(switchId, ifIndex)
        {
        	var url = timeURL('${ctx}/fas/res/net/switch/interface/multiView.do?switchId='+switchId+'&ifIndex='+ifIndex);
     		window.parent.showDlg('接口信息', 800, 480, url);     
        }
        
        
         function manage(id,switchId,indexId)
        {
        	var url = timeURL('${ctx}/fas/res/dailyMaintain/netInfoManage/manage.do?ipHostId='+id+'&switchId='+switchId+'&indexId='+indexId);
        	window.parent.parent.showDlg('维护', 900, 550, url);
        }
        
    </script>
</head>
<body style="padding:0px; overflow:hidden; "> 
	<div id="toptoolbar"></div> 
    <div id="maingrid"></div> 
    <div style="display:none;">
    <!--高级查询  -->
    <div id="advancedSearchDlg" style="padding:0px;">
    	<form id="formAdvancedSearch" name="formAdvancedSearch" action="${ctx}/fas/res/dailyMaintain/netInfoManage/advancedSearch.do" method="post">
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
				<td colspan="6" align="left" style="font-weight:bold">交换机</td>
			</tr>
			<tr>
				<td align="right">交换机IP：</td>
				<td>
					<input name="filter_sw_IP_1" type="text" class="input" id="sw_IP_1" size="1" maxlength="3" style="width:25px">
					.
					<input name="filter_sw_IP_2" type="text" class="input" id="sw_IP_2" size="1" maxlength="3" style="width:25px">
					.
					<input name="filter_sw_IP_3" type="text" class="input" id="sw_IP_3" size="1" maxlength="3" style="width:25px">
					.
					<input name="filter_sw_IP_4" type="text" class="input" id="sw_IP_4" size="1" maxlength="3" style="width:25px">		
				</td>
				<td></td>
				<td align="right">端口：</td>
				<td><input type="text" id="ifName" name="filter_ifName" /></td>
				<td></td>
			</tr>
			<tr>
				<td colspan=6 style="height:12px"></td>
			</tr>
			<tr>
				<td colspan="6" align="left" style="font-weight:bold">设备</td>
			</tr>
			<tr>
				<td align="right">设备IP：</td>
				<td>
					<input name="filter_net_IP_1" type="text" class="input" id="net_IP_1" size="1" maxlength="3" style="width:25px">
					.
					<input name="filter_net_IP_2" type="text" class="input" id="net_IP_2" size="1" maxlength="3" style="width:25px">
					.
					<input name="filter_net_IP_3" type="text" class="input" id="net_IP_3" size="1" maxlength="3" style="width:25px">
					.
					<input name="filter_net_IP_4" type="text" class="input" id="net_IP_4" size="1" maxlength="3" style="width:25px">	
				</td>
				<td></td>
				<td align="right">设备Mac地址：</td>
				<td><input type="text" id="hostMac" name="filter_hostMac"/></td>
				<td></td>
			</tr>
			<tr>
           		<td align="center" colspan=6 >
           		<input id="bnAdvancedSearch" type="submit" value="搜 索" class="l-button mg6" />
           		</td>
            </tr>
		</table>
    <br/>
 </form>
    </div>
 </div>
</body>
</html>

