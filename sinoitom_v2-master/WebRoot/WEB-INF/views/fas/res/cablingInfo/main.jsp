<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>综合布线信息管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>

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
                	{ text:'详细信息', click: onView, icon:'viewmenu'},
                	{ line:true },
                	{ text:'导入', click: onImport, icon:'import' },
                	{ line:true },
                	{ text:'导出', click: onExport, icon:'export' },
                	{ line:true },
                	{ text:'端口验证', click: onVerifyData, icon:'refresh' },
                	{ line:true },
                	{ text:'刷新', click: onRefresh, icon:'refresh' }
                	]
            });

            dataMain.Rows =null;
            gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'信息点编号', name:'infoPointNo', width:110, align:'left',type:'text'},
                    { display:'楼宇名称', name:'buildingNo', width:80, align:'center' ,type:'text'},
                    { display:'房间号', name:'roomNo', width:80, align:'center',type:'text' },
                    { display:'介质类型', name:'mediaType', width:80, align:'center' },
                    { display:'线缆类别', name:'cableType', width:80, align:'center' },
                    { display:'带宽', name:'baudRate', width:90, align:'right' },
                    { display:'长度(米)', name:'length', width:80, align:'right' },
                    { display:'机架号', name:'cabinetNo', width:70, align:'left' },
                    { display:'模块号', name:'moduleNo', width:70, align:'right' },
                    { display:'模块端口号', name:'modulePort', width:70, align:'right',type:'text' },
                    { display:'交换机IP', name:'switchIP', width:100, align:'left' },
                    { display:'交换机端口', name:'ifName', width:100, align:'left' },
                    { display:'标志', name:'verifyFlag', width:70, align:'center',
                    	render: function (row)
						 	{
						 		var html='<span style=\"color:red\">未验证</span>';
						 		if(row.verifyFlag==1){
						 			html='<span style=\"color:green\">已验证</span>';
						 		}
						 	    return html;
						 	}
                     },
                    { display:'备注', name:'remark', width:170, align:'left' }
               		],  
				data: dataMain,
				alternatingRow: 'true',
				
                width: '100%',
                height:'100%', 
                pageSize: '100',
                rownumbers:true,
                checkbox:true,
                isChecked: f_isChecked,
                onCheckRow: f_onCheckRow,
                onCheckAllRow: f_onCheckAllRow,
                pageSizeOptions: [100, 200, 300,400, 500],
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
            	window.parent.$("select[name*='filter_']").val("");
            });
            
            getData();//初始化数据
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
        
        
        function loadData(sort){
            selectRowData = null;
            gridMain.loadData();
            if(sort!='no'){
            	gridMain.changeSort('infoPointNo', 'asc');
            }
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
        
        function onAdd()
        {
        	var url = timeURL('${ctx}/fas/res/cablingInfo/add.do');
    		window.parent.showDlg('添加布线信息', 900, 450, url);     
        }
		
		function onImport()
        {
        	var url = timeURL('${ctx}/fas/res/cablingInfo/importGo.do');
        	window.parent.showDlg('导入综合布线信息', 720, 320, url);     
        }
        
        function onExport()
        {
        	location.href='${ctx}/fas/res/cablingInfo/exportExcel.do';
        }
        
        function onEdit()
        {
            if (!selectRowData) { 
            	window.parent.$.ligerDialog.error('请选择一行数据！'); 
            	return; 
            }
        	editRow(selectRowData.id);
        }
        
        function editRow(id)
        {
        	var url = timeURL('${ctx}/fas/res/cablingInfo/edit.do?id='+id);
        	window.parent.showDlg('修改综合布线信息', 800, 450, url);
        }
        
        /* function onMultiEdit(){
        	if( checkedItem.length == 0 ){
        		window.parent.$.ligerDialog.error('请选择至少条数据！');
        	}else{
            	var ids = checkedItem.join(',');
            	var url = timeURL('${ctx}/fas/res/cablingInfo/multiEdit.do?ipHostIds='+ids);
            	window.parent.showDlg('批量修改IP设备', 800, 500, url);
        	}
        } */
        
        function onView()
        {
            if (!selectRowData) { 
            	window.parent.$.ligerDialog.error('请选择一行数据！'); 
            	return; 
            }
            viewRow(selectRowData.id);
        }
        
        function viewRow(id)
        {
        	var url = timeURL('${ctx}/fas/res/cablingInfo/view.do?id='+id);
        	window.parent.showDlg('查看详情', 800, 320, url);
        }
		
        function saveOK(action, data)
        {
        	if( action=='add' ){
        		dataMain.Rows.push(data);
        		loadData();
        	}
        	if(action='verify'){
        		onRefresh();
        	}
        	else{
                for (var i = 0; i < dataMain.Rows.length; i++)
                {
                    if (dataMain.Rows[i].id == data.id){
                    	dataMain.Rows[i] = data;
                    	break;
                   }
                }
         		loadData('no');
        	}
        }
        
/*         function multiSaveOK(data)
        {
              for (var i = 0; i < dataMain.Rows.length; i++)
              {
              	for(var j=0;j<data.length;j++){
	                  if (dataMain.Rows[i].ipHostId == data[j].ipHostId){
	                  	dataMain.Rows[i] = data[j];
	                  	break;
	                 }
                 }
              }
       		loadData();
        }
 */
        function onDelete()
        {
        	 if( checkedItem.length == 0 ){
        		window.parent.$.ligerDialog.error('请选择至少一条数据！');
        	}else{
            	var ids = checkedItem.join(',');
            	deleteRow(ids);
        	} 
        }
        
        function deleteRow(ids)
        {
        	window.parent.$.ligerDialog.confirm('是否确认删除数据？', function (yes)
        	{
        		if(yes) delRow(ids);
        	});
        }
        
        function delRow(ids)
        {
        	var url = timeURL('${ctx}/fas/res/cablingInfo/delete.do?id='+ids);
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
		
        function onSearch()
        {
        	if( dlgSearch ){
        		dlgSearch.show();
        	}
        	else{
        		$("#formSearch input[ltype='text']").ligerTextBox({inputWidth:200}); $("#formSearch input").filter(".ip2").ligerTextBox({ width: 553 });
            	dlgSearch = window.parent.$.ligerDialog.open({ title:'查询', width:760, height:300, target: $("#searchDlg") });
        	}
        }
        
        function onRefresh(){
        	window.location.reload();
        }
		
        function onVerifyData()
        {
        	 if( checkedItem.length == 0 ){
        		window.parent.$.ligerDialog.error('请选择至少一条数据！');
        	}else{
            	var ids = checkedItem.join(',');
            	verifyData(ids);
        	} 
        }
        
        function verifyData(ids)
        {
        	var url = timeURL('${ctx}/fas/res/cablingInfo/verifyPort.do?id='+ids);
        	window.parent.showDlg('端口验证', 720, 510, url);
        }
        
        function getData()
        {
        	$("#maingrid").hide();
        	$("#pageloading").show();
        	var url = timeURL('${ctx}/fas/res/cablingInfo/getMainData.do');
        	$.ajax({
        		url: url,
        		data: "date="+new Date(),
        		dataType: 'json',
        		success: function(data) {
           			dataMain.Rows = data;
           			$("#maingrid").show();
           			$("#pageloading").hide();
           			loadData();
        		}
        	});        			
        };
    </script>
  </head>
  
  <body style="padding:0px; overflow:hidden; ">
  	<div class="l-loading" style="display:block" id="pageloading"></div>
    <div id="toptoolbar"></div> 
    <div id="maingrid"></div> 
    <div style="display:none;">
    <div id="searchDlg" style="padding:0px;">
    	<form style="padding:0px;" id="formSearch" action="${ctx}/fas/res/cablingInfo/search.do" method="post">
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
                <td></td>
                <td align="right">信息点编号:</td>
                <td><input name="filter_LIKES_infoPointNo" class="ip1" type="text" ltype="text"/></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">楼宇名称:</td>
                <td><input name="filter_LIKES_buildingNo" class="ip1" type="text" ltype="text"/></td>
                <td></td>
                <td align="right">房间号:</td>
                <td><input name="filter_LIKES_roomNo" class="ip1" type="text" ltype="text"/></td>
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
