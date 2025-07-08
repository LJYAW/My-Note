<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<shiro:hasPermission name="position:read"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>职位管理</title>
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
    	var selectGrpType = null;
    	var selectItemType = null;
    	var orgName='${organ.orgName}';
        $(function ()
        {
            $("#toptoolbar").ligerToolBar({ 
				items: 
            		[
                	{ text:'返回', click: onBack, icon:'back' },
                 	{ line:true },
                 	<shiro:hasPermission name="position:create"> 
                	{ text:'添加', click: onAdd, icon:'add'},
                	{ line:true },
                	</shiro:hasPermission>  
                	<shiro:hasPermission name="position:update"> 
                	{ text:'修改', click: onEdit, icon:'edit'},
                	{ line:true },
                	</shiro:hasPermission>  
                	<shiro:hasPermission name="position:delete"> 
                	{ text:'删除', click: onDelete, icon:'delete' },
                	{ line:true },
                	</shiro:hasPermission>  
                	{ text:'查询', click: onSearch, icon:'search' },
                	{ line:true },
                	{ text:'刷新', click: onRefresh, icon:'refresh' }
                	]
            });

            dataMain.Rows = ${jsonListData};
            gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'机构名称', name:'orgOrganization', width:150, align:'left',
                      render:function(row){
                       return orgName;
                      }   
                     },
                    { display:'职位名称', name:'posName', width:120, align:'left' },
                    { display:'职务编码', name:'dutyCode', width:120, align:'left' },
                   // { display:'级别', name:'posLevel', width:100, align:'left' },
                    { display:'岗位职责', name:'qualification', width:300, align:'left' },
                    { display:'状态', name:'state', width:100, align:'center',
                     render:function(row){
                       var html="";
                       if(row.state=="0"){
                           html="无效";
                       }else if(row.state=="1"){
                          html="有效";
                       }
                       return html;
                     }
                    },
                    { 
                    	display:'操作', width:100,
                        render: function (row)
                        {
                        	var html = '';
                         	<shiro:hasPermission name="position:update"> 
                            html += '<span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick="editRow(\'' + row.id + '\');">修改</span>';
                        	</shiro:hasPermission>  
                        	<shiro:hasPermission name="position:delete"> 
                        	if( html != '') html += ' | ';
                            html += '<span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick="deleteRow(\'' + row.id + '\');">删除</span>';
                        	</shiro:hasPermission>  
                            return html;
                        }
                    }
               		],  
				data: dataMain,
				alternatingRow: 'true',
                width: '100%',
                height:'100%', 
                pageSize: '20',
                pageSizeOptions: [10, 20, 30, 50, 100],
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
        	var url = timeURL('${ctx}/system/organ/position/getData.do?orgId=${organ.orgId}');
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
        	var url = timeURL('${ctx}/system/organ/position/add.do?orgId=${organ.orgId}');
    		window.parent.showDlg('添加职位', 720, 380, url);     
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
        	var url = timeURL('${ctx}/system/organ/position/edit.do?id='+id);
     		window.parent.showDlg('修改职位', 720, 380, url);     
        }

        function saveOK(action, data)
        {
        	if( action=='add' ){
        		dataMain.Rows.push(data);
        		loadData();
        	}
        	else{
                for (var i = 0; i < dataMain.Rows.length; i++)
                {
                    if (dataMain.Rows[i].id == data.id){
                    	dataMain.Rows[i] = data;
                    	break;
                    }
                }
         		loadData();
        	}
        }

        function onDelete()
        {
            if (!selectRowData) { 
            	window.parent.$.ligerDialog.error('请选择一行数据！'); 
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
        	var url = timeURL('${ctx}/system/organ/position/delete.do?id='+id);
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
            	dlgSearch = window.parent.$.ligerDialog.open({ title:'查询', width:720, height:200, target: $("#searchDlg") });
        	}
        }

        function onRefresh(){
        	window.location.reload();
        }
              
        function onBack()
        {
//        	window.history.back();
        	var url = timeURL('${ctx}/system/organ/main.do');
        	window.location.href = url;

        }
    </script>
</head>
<body style="padding:0px; overflow:hidden; "> 
	<div id="toptoolbar"></div> 
    <div id="maingrid"></div> 
    <div style="display:none;">
    <div id="searchDlg" style="padding:0px;">
    	<form style="padding:0px;" id="formSearch" action="${ctx}/system/organ/position/search.do" method="post">
		<input type="hidden" name="filter_EQS_orgOrganization.orgId" value="${organ.orgId}"/>
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
                <td align="right">职位名称:</td>
                <td><input name="filter_LIKES_positionName" class="ip1" type="text" ltype="text"/></td>
                <td></td>
                <td align="right">职务编码:</td>
                <td><input name="filter_LIKES_dutyCode" class="ip1" type="text" ltype="text"/></td>
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
</shiro:hasPermission>
