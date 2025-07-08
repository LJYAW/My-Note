<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>SNMP信息管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
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
                	{ text:'添加', click: onAdd, icon:'add'},
                	{ line:true },
                	{ text:'修改', click: onEdit, icon:'edit'},
                	{ line:true },
                	{ text:'删除', click: onDelete, icon:'delete' },
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
                    { display:'SNMP信息名称', name:'snmpCredName', width:150, align:'left' },
                    { display:'SNMP端口', name:'snmpPort', width:80, align:'left' },
                    { display:'SNMP版本', name:'snmpVersion', width:80, align:'left' },
                    { display:'SNMP只读串', name:'snmpRoStr', width:100, align:'left' },
                    { display:'SNMP读写串', name:'snmpRwStr', width:100, align:'left' },
                    { display:'信息说明', name:'snmpCredDesc', width:150, align:'left' },
                    { 
                    	display:'操作', width:120,
                        render: function (row)
                        {
                            var html = '<span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick="editRow(\'' + row.id + '\');">修改</span>';
                            html += ' | <span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick="deleteRow(\'' + row.id + '\');">删除</span>';
                            return html;
                        }
                    }
               		],  
				data: dataMain,
				alternatingRow: 'true', 
                width: '100%', height:'100%', 
                groupColumnName:'organ',groupColumnDisplay:'机构名称',
                pageSize: '20', pageSizeOptions: [10, 20, 30, 50, 100],
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
        	var url = timeURL('${ctx}/fas/res/net/snmp/cred/getData.do');
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
        	var url = '${ctx}/fas/res/net/snmp/cred/add.do';
     		window.parent.showDlg('添加SNMP信息', 720, 420, timeURL(url));     
        }

        function onEdit()
        {
            if (!selectRowData) { 
            	window.parent.$.ligerDialog.warn('请选择一行数据！'); 
            	return; 
            }
        	editRow(selectRowData.id);
        }
        
        function editRow(id)
        {
        	var url = timeURL('${ctx}/fas/res/net/snmp/cred/edit.do?id='+id);
     		window.parent.showDlg('修改SNMP信息', 720, 420, url);     
        }
        
        function saveOK(action, data)
        {
        	refreshData();
        }
        
        function onDelete()
        {
            if (!selectRowData) { 
            	window.parent.$.ligerDialog.warn('请选择一行数据！'); 
            	return; 
            }
        	deleteRow(selectRowData.id);
        }
        
        function deleteRow(id)
        {
        	window.parent.$.ligerDialog.confirm('是否确认删除该行数据？', function (yes)
            {
                if(yes) delRow(id);
            });
        }
        
        function delRow(id)
        {
        	var url = timeURL('${ctx}/fas/res/net/snmp/cred/delete.do?id='+id);
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

    </script>
</head>
<body style="padding:0px; overflow:hidden; "> 
	<div id="toptoolbar"></div> 
    <div id="maingrid"></div> 
    <div style="display:none;">
    <div id="searchDlg" style="padding:0px;">
    	<form style="padding:0px;" id="formSearch" action="${ctx}/fas/res/net/snmp/cred/search.do" method="post">
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
                <td align="right">SNMP信息名称:</td>
                <td><input name="filter_LIKES_snmpCredName" class="ip1" type="text" ltype="text"/></td>
                <td></td>
               <!--   <td align="right">SNMP版本:</td>
                <td><input name="filter_LIKES_snmpVersion" class="ip1" type="text" ltype="text"/></td> -->
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

