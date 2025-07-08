<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<shiro:hasPermission name="employee:read"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>设备操作命令</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/index.js" type="text/javascript"></script>
<script src="${ctx}/js/mainTab.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var dataMain = {};
    	var gridMain;
        var selectRowData = null;
    	var dlgSearch = null;
    	var selectGrpType = null;
    	var selectItemType = null;
        $(function ()
        {
            $("#toptoolbar").ligerToolBar({ 
				items: 
            		[
                	//{ text:'添加', click: onAdd, icon:'add'},
                	//{ line:true },
                	{ text:'修改', click: onEdit, icon:'edit'},
                	{ line:true },
                	{ text:'删除', click: onDelete, icon:'delete' },
                	{ line:true },
                	{ text:'详情', click: onView, icon:'search'},
                	{ line:true },
                	{ text:'刷新', click: onRefresh, icon:'refresh' }
                	]
            });

            dataMain.Rows = ${jsonListData};
            gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'厂商', name:'dispName', width:70, align:'center',type:'text'},
                    { display:'设备类型', name:'devTypeName', width:70, align:'center' },
                    { display:'设备型号', name:'devModel', width:70, align:'center' },
		            { display:'型号OID', name:'modelOID', width:100, align:'center' },
		            { display:'操作系统', name:'osName', width:60, align:'center' },
		            { display:'版本号', name:'osVersion', width:60, align:'center' },
		            { display:'设备操作', name:'operateName', width:120, align:'center' },
		            { display:'操作顺序', name:'cmdOrder', width:60, align:'center' },
		            { display:'命令类型', name:'cmdType', width:100, align:'center' },
		            { display:'操作命令', name:'command', width:290, align:'left' },
		            { display:'参数个数', name:'paramFlag',width:60, align:'center',type:'int' },
		            { display:'期待提示符', name:'expectPrompt',width:80, align:'center' },
		            
		            { display:'验证状态', name:'status', width:60, align:'center',
		             render:function(row){
		              var html="";
		              if(row.status==0){
		                html += '<span style="color:red;">未验证</span>';
		              }else if(row.status==1){
		               html += '<span style="color:green;">已验证</span>';
		              }
		              return html;
		             }
		             },
		             { display:'命令说明', name:'description', width:200, align:'left'}
               		],  
				data: dataMain,
				alternatingRow: 'true',
                width: '100%',
                height:'100%', 
                pageSize: '100',checkbox:true, 
                pageSizeOptions: [25, 50, 100,200,500],
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                } 
            });

          //getData();
            
        });
        
        function loadData(){
            selectRowData = null;
            gridMain.loadData();
        }
        
        function getData()
        {
        	var url = timeURL('${ctx}/cmdb/devOpCmd/search.do');
        	$.ajax({
        		url: url,
        		//data: "date="+new Date(),
        		dataType: 'json',
        		success: function(data) {
           			dataMain.Rows = data;
           			loadData();
        		}
        	});        			
        };
        
         function onAdd(){
        	var url = '${ctx}/cmdb/devOpCmd/add.do';
     		showDlg('维护操作命令', 1024, 550, timeURL(url));     
        }

        function onEdit()
        {
        	var rows = gridMain.getSelectedRows();
        	var row= gridMain.getSelectedRow();
        	if(rows.length>1){
        		$.ligerDialog.warn('只能选择一条数据修改！'); 
            	return; 
        	}
            if (row!=null&&row!="") { 
            	editRow(row.id);
            }else{
            	$.ligerDialog.warn('请选择要修改的数据！'); 
            	return; 
            }
        	
        }
        
        function editRow(id)
        {	
        	var url = timeURL('${ctx}/cmdb/devOpCmd/edit.do?id='+id);    
     		showDlg('修改', 750, 400, url); 
        }

        function onView(){
        	var rows = gridMain.getSelectedRows();
        	var row= gridMain.getSelectedRow();
        	if(rows.length>1){
        		$.ligerDialog.warn('只能选择一条数据修改！'); 
            	return; 
        	}
        	if (row!=null&&row!="") { 
            	viewRow(row.id);
            }else{
            	$.ligerDialog.warn('请选择要查看的数据！'); 
            	return; 
            }
        }
        
        function viewRow(id)
        {	
        	var url = timeURL('${ctx}/cmdb/devOpCmd/view.do?id='+id);    
     		showDlg('详情', 750, 350, url); 
        }
        
       function onDelete()
        {
        	
             var rows = gridMain.getSelectedRows();
	        
	         if(rows!=null&&rows!=""&&rows.length>0){
	          var ids = getAllSelectId();
	          $.ligerDialog.confirm('请确认是否删除选中数据', function (yes){
 				if(yes) {
                    delRow(ids);
				}
			 });
	         
	         }else{
	           $.ligerDialog.warn("请选择要删除的数据!");
	          return;
	         }
        }
        
        
        function delRow(ids)
        {   
            $("#devOpCmdIds").val(ids);
        	var url = timeURL('${ctx}/cmdb/devOpCmd/delete.do');
        	$.ajax({
        	    type: "POST",
        	    url: url,
        	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
			    data: $("#mulitDelete").serialize(),
        		//dataType: 'json',
        		success: function(data) {
        			 if(data.result=="success"){
			           	    $.ligerDialog.success("删除成功!");
					        f_onRefresh(); 
				      	}
        		}
        	});        			
        }
        

       

        function onRefresh(){
        	window.location.reload();
        }
        
       function saveOK(){
        onRefresh();
       } 
        
       
        
        
      
              
    </script>
</head>
<body style="padding:0px; overflow:hidden; "> 
<form id="mulitDelete" name="mulitDelete" action="" style="display:none" method="post" enctype="multipart/form-data" target="newWin">
  	     <input name="devOpCmdIds" id="devOpCmdIds" value=""  />
</form>
<form id="relationIndItems" name="relationIndItems" action="${ctx}/cmdb/indicator/device/relationIndItems.do" style="display:none" method="post" enctype="multipart/form-data" target="newWin">
<!--   	     <input name="jsonString" id="jsonString" value=""  /> -->
  	     <input name="indItemsIds" id="indItemsIds" value=""  />
</form>
<form id="relationCollIndItems" name="relationCollIndItems" action="${ctx}/cmdb/collector/collIndItems/relaCollIndItems.do" style="display:none" method="post" enctype="multipart/form-data" target="newWin">
<!--   	     <input name="jsonString" id="jsonString" value=""  /> -->
  	     <input name="indItemsIDs" id="indItemsIDs" value=""  />
</form>
	<div id="toptoolbar"></div> 
    <div id="maingrid"></div> 
</body>
</html>
</shiro:hasPermission>

