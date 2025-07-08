<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<shiro:hasPermission name="employee:read"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>操作定义</title>
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
                	{ text:'添加', click: onAdd, icon:'add'},
                	{ line:true },
                	{ text:'修改', click: onEdit, icon:'edit'},
                	{ line:true },
                	{ text:'批量审核', click: batchAudit, icon:'approve'},
                	{ line:true },
                	{ text:'删除', click: onDelete, icon:'delete' },
                	{ line:true },
                	{ text:'刷新', click: onRefresh, icon:'refresh' }
                	
                	]
            });

            dataMain.Rows = ${jsonStr};
            gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'操作ID', name:'id', width:80, align:'center',type:'int'},
                    { display:'操作类型', name:'opName', width:150, align:'left'},
                    { display:'操作名称', name:'operation', width:150, align:'left'},
                    { display:'中文操作名称', name:'operationName', width:150, align:'left'},
                    
		            { display:'状态', name:'status', width:80, align:'center',
		              render:function(row){
		                var html="";
		                if(row.status=="0"){
		                  html += '<span style="color:red;">未审核</span>';
		                }else{
		                  html += '<span style="color:blue;">已审核</span>';
		                }
		                return html;
		              }
		             },
		            { display:'说明', name:'description', width:300, align:'left'}
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

            
        });
        
        function loadData(){
            selectRowData = null;
            gridMain.loadData();
        }
        
        function saveOK(){
		 onRefresh();
		}
        
        function getData()
        {
        	var url = timeURL('${ctx}/cmdb/devOperation/search.do');
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
        
        function onAdd()
        {
        	var url = timeURL('${ctx}/cmdb/operation/add.do');
    		showDlg('添加', 750, 350, url);     
        }

        function onEdit()
        {
            if (!selectRowData) { 
            	$.ligerDialog.warn('请选择要修改的数据！'); 
            	return; 
            }
        	 if(selectRowData.status==0){
              editRow(selectRowData.id);
            }else{
              $.ligerDialog.warn('该数据已审核不能修改！'); 
            }
        }
        
        function editRow(id)
        {	
        	var url = timeURL('${ctx}/cmdb/operation/edit.do?id='+id);    
     		showDlg('修改', 750, 350, url); 
        }
		
        
       function onDelete()
        {
        	
             var rows = gridMain.getSelectedRows();
	        // var id = getAllSelectId();
	         var ids="";
	         
	         
	         if(rows!=null&&rows!=""){
	         
	         for(var i=0;i<rows.length;i++){
	           if(rows[i].status==0){
	            ids=ids+rows[i].id+";";
	           }else{
	              $.ligerDialog.warn("所选数据包含已审核数据,请重新选择!");
	              return;
	           }
	         }
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
        
        
        function delRow(id)
        {   
            $("#opIds").val(id);
        	var url = timeURL('${ctx}/cmdb/operation/deletes.do');
        	$.ajax({
        	    type: "POST",
        	    url: url,
        	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
			    data: $("#batchOp").serialize(),
        		//dataType: 'json',
        		success: function(data) {
        			 if(data.success==0){
			           	    $.ligerDialog.success("删除成功!");
					        f_onRefresh(); 
				      	}
        		}
        	});        			
        }
        
		function batchAudit(){
		   var rows = gridMain.getSelectedRows();
		   if(rows!=null&&rows!=""){
		      var ids = getAllSelectId();
		  $("#opIds").val(ids);
        	var url = timeURL('${ctx}/cmdb/operation/batchAudit.do');
        	$.ajax({
        	    type: "POST",
        	    url: url,
        	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
			    data: $("#batchOp").serialize(),
        		//dataType: 'json',
        		success: function(data) {
        		     
        			 if(data.success==0){
			           	    $.ligerDialog.success("审核成功!");
					        f_onRefresh(); 
				      	}
        		}
        	});  
		   }else{
		     $.ligerDialog.warn("请选择要审核的数据!");
		   }
		}

        function onRefresh(){
        	window.location.reload();
        }
              
    </script>
</head>
<body style="padding:0px; overflow:hidden; "> 
<form id="batchOp" name="batchOp" action="" style="display:none" method="post" enctype="multipart/form-data" target="newWin">
  	     <input name="opIds" id="opIds" value=""  />
</form>
	<div id="toptoolbar"></div> 
    <div id="maingrid"></div> 
</body>
</html>
</shiro:hasPermission>

