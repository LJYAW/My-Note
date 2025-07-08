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

    <script type="text/javascript">
    	var dataMain = {};
    	var gridMain;
        var selectRowData = null;
    	var dlgSearch = null;
        $(function ()
        {
            $("#toptoolbar").ligerToolBar({ 
				items: 
            		[
 /*                	{ text:'返回', click: onBack, icon:'back' },
//                 	{ line:true },
//                	{ text:'添加', click: onAdd, icon:'add'},
                	{ line:true }, */
                	{ text:'修改', click: onEdit, icon:'edit'},
                	{ line:true },
                	{ text:'查看', click: onView, icon:'view' },
//                	{ line:true },
//                	{ text:'删除', click: onDelete, icon:'delete' },
                	{ line:true },
                	{ text:'查询', click: onSearch, icon:'search' },
                	{ line:true },
                	{ text:'刷新', click: onRefresh, icon:'refresh' }
                	]
            });

            dataMain.Rows = ${jsonTreeData};
            gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                     { display:'端口号', name:'portNo', width:70, align:'left', type:'int' },
                     { display:'IfIndex', name:'ifIndex', width:70, align:'left', type:'int'},
                     { display:'接口名称', name:'ifIndex', width:80, align:'left',
                     	render: function (row)
	                  		 {
		                    	return row.ifName;
		                   	}
                      },
                     { display:'接口描述', name:'ifIndex', width:120, align:'left',
                     	render: function (row)
	                  		 {
		                    	return row.ifAliasName;
		                   	}
                     },
                     // { display:'是否可用', name:'availablity', width:80, align:'left' },
                     { display:'管理状态', name:'adminStatus', width:70, align:'center', 
                    	render: function (row)
	                        {
	                        	var html='';
	                        	if(row.adminStatus=='up'){
	                        		html = '<span style=\"align:certer;line-height:22px;color:green;\">'+row.adminStatus+'</span>';
	                        	}else{
	                        		html = '<span style=\"align:certer;line-height:22px;color:red;\">'+row.adminStatus+'</span>';
	                        	}
	                            return html;
	                        }
	                },
                    { display:'工作状态', name:'operStatus', width:70, align:'center',
                    	render: function (row)
	                        {
	                        	var html='';
	                        	if(row.operStatus=='up'){
	                        		html = '<span style=\"align:certer;line-height:22px;color:green;\">'+row.operStatus+'</span>';
	                        	}else{
	                        		html = '<span style=\"align:certer;line-height:22px;color:red;\">'+row.operStatus+'</span>';
	                        	}
	                            return html;
	                        }
	                },
	                { display:'接入设备',name:'multiMacPort', width:450, align:'left',type:'int',
	                	render: function (row)
                        {
                        	var html='';
                        	if(row.ipMacMap.length>1){
	                        	for(var i=0;i<row.ipMacMap.length;i++){
	                        		html =html+ '<span style=\"line-height:22px;\">'+row.ipMacMap[i].macAddr+'</span>';
	                        	 if(!isNullStr(row.ipMacMap[i].ipAddr)){
	                        		html = html+'-';
	                        		html =html +'<span style=\"line-height:22px;\">'+row.ipMacMap[i].ipAddr+';</span>&nbsp;&nbsp';
	                        		}else{
	                        			html=html+';&nbsp;&nbsp';
	                        		}
	                        	 }
                        	}
                        	else if(row.ipMacMap.length==1){
                        			html = '<span style=\"line-height:22px;\">'+row.ipMacMap[0].macAddr+'</span>';
	                        	 if(!isNullStr(row.ipMacMap[0].ipAddr)){
	                        		html = html+'-';
	                        		html =html +'<span style=\"line-height:22px;\">'+row.ipMacMap[0].ipAddr+'</span>';
	                        		}
                        	}
                        	html=html+'&nbsp;&nbsp';
                        	if(parseInt(row.multiMacPort)>2&&row.ipMacMap.length>0){
                            	html += '<span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick="multiView(\'' + row.switchId + '\',\'' + row.ifIndex + '\');"><img src=\"${ctx}/img/more.gif\"/></span>';
	                            }
                            return html;
                        }
	                },
                   { 
                    	display:'操作', width:160,name:'isoStatus',
                        render: function (row)
                        {
                        	var html='';
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
				data: dataMain,
				alternatingRow: 'true', checkbox: true,
                isChecked: f_isChecked, onCheckRow: f_onCheckRow, onCheckAllRow: f_onCheckAllRow,
                width: '100%', height:'100%', 
                pageSize: '50', pageSizeOptions: [ 50, 100,200,300,500],
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                } 
            });

            $("#formSearch").ajaxForm({
            	dataType: 'json',
            	success: function(data) { 
           			dataMain.Rows = data;
					loadData();
            		dlgSearch.hide();
            	}
            }); 
            
            $("#bnReset").click(function()
            {
            	window.parent.$("input[ltype='text'][name*='filter_']").val("");
            });
            
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
            if (findCheckedItem(rowdata.ItemID) == -1)
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
        
        function refreshData()
        {
        	var url = timeURL('${ctx}/fas/res/net/switch/interface/getData.do?devId=${deviceId}');
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
           			dataMain.Rows = data;
					loadData();
        		}
        	});        			
        }
        
        function onAdd()
        {
        	var url = '${ctx}/fas/res/net/switch/interface/add.do?devId=${deviceId}';
     		window.parent.showDlg('添加接口', 800, 500, timeURL(url));     
        }

        function onEdit()
        {
            if (!selectRowData) { 
            	window.parent.$.ligerDialog.warn('请选择一行数据！'); 
            	return; 
            }
        	editRow(selectRowData.id.switchId,selectRowData.id.ifIndex);
        }
        
        function editRow(switchId, ifIndex)
        {
        	var url = timeURL('${ctx}/fas/res/net/devices/interface/edit.do?switchId='+switchId+'&ifIndex='+ifIndex);
     		//window.parent.$.showDlg('修改接口', 800, 500, url);
     		window.open(url,'', 'height=400, width=800, top=160, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');     
        }
        
        function saveOK(action, data)
        {
        	refreshData();
        }
        
        function onView()
        {
            if (!selectRowData) { 
            	window.parent.$.ligerDialog.warn('请选择一行数据！'); 
            	return; 
            }
            viewRow(selectRowData.id.switchId,selectRowData.id.ifIndex);
        }
        
        function viewRow(switchId, ifIndex)
        {
        	var url = timeURL('${ctx}/fas/res/net/devices/interface/view.do?switchId='+switchId+'&ifIndex='+ifIndex);
     		//window.parent.showDlg('接口信息', 800, 480, url);
     		window.open(url,'', 'height=400, width=800, top=160, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');     
        }

        function onDelete()
        {
            if (!selectRowData) { 
            	window.parent.$.ligerDialog.warn('请选择一行数据！'); 
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
        	var url = timeURL('${ctx}/fas/res/net/devices/interface/delete.do?switchId='+switchId+'&ifIndex='+ifIndex);
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
        			doData(data);
        		}
        	});        			
        }

        function onSearch()
        {
        	if( dlgSearch ){
        		dlgSearch.show();
        	}
        	else{
        		$("#formSearch input[ltype='text']").ligerTextBox({inputWidth:200}); $("#formSearch input").filter(".ip2").ligerTextBox({ width: 553 });
            	dlgSearch = window.parent.$.ligerDialog.open({ title:'查询', width:720, height:350, target: $("#searchDlg") });
        	}
        }
        
        function onRefresh(){
        	window.location.reload();
        }
        
        function onBack()
        {
        	var url = timeURL('${ctx}/fas/res/net/switch/main.do');
        	window.location.href = url;
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
    </script>
</head>
<body style="padding:0px; overflow:hidden; "> 
	<div id="toptoolbar"></div> 
    <div id="maingrid"></div> 
    <div style="display:none;">
    <div id="searchDlg" style="padding:0px;">
    	<form style="padding:0px;" id="formSearch" action="${ctx}/fas/res/net/switch/interface/search.do" method="post">
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
                <td align="right">接口名称:</td>
                <td><input name="filter_LIKES_ifName" class="ip1" type="text" ltype="text"/></td>
                <td></td>
                <td align="right">端口号:</td>
                <td><input name="filter_EQI_portNo" class="ip1" type="text" ltype="text"/></td>
                <td></td>
            </tr>
            <tr>
                <td colspan=3 align="right" style="padding:10px">
                <input id="bnSearch" type="submit" value="查 询" class="l-button"/> 
                </td>
                <td colspan=3 style="padding:10px">
                <input id="bnReset" type="button" value="重 置" class="l-button""/>
                </td>
            </tr>   
        </table>
        </form>
    </div>
    </div>
 </div>
</body>
</html>

