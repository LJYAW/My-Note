<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<shiro:hasPermission name="employee:read"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>设备操作</title>
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
                	{ text:'维护操作命令', click: maintain, icon:'add' },
                	{ line:true },
                	{ text:'删除', click: onDelete, icon:'delete' },
                	{ line:true },
                	{ text:'刷新', click: onRefresh, icon:'refresh' },

                	]
            });

            dataMain.Rows = ${jsonListData};
            gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'设备分类', name:'devClassName', width:80, align:'center'},
                    { display:'设备类型', name:'devTypeName', width:80, align:'center'},
                    { display:'访问方式', name:'accessMode', width:80, align:'center'},
		            { display:'操作类型', name:'opTypeName', width:80, align:'center' },
		            //{ display:'操作名称', name:'operation', width:160, align:'left' },
		            //{ display:'中文操作名称', name:'operateName', width:170, align:'left' },
		            { display:'设备操作名称', name:'devOpName', width:200, align:'left' },
		            { display:'设备操作说明', name:'description', width:260, align:'left'},
		            { display:'审核状态', name:'status', width:60, align:'center',
		             render:function(row){
		              var html="";
		              if(row.status==0){
		                html += '<span style="color:red;">未审核</span>';
		              }else if(row.status==1){
		               html += '<span style="color:green;">已审核</span>';
		              }
		              return html;
		             }
		            }
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
        	var url = timeURL('${ctx}/cmdb/devOperation/addDevOP.do');
    		showDlg('添加', 750, 350, url);     
        }

        function onEdit()
        {
            if (!selectRowData) { 
            	$.ligerDialog.warn('请选择要修改的数据！'); 
            	return; 
            }
            
            editRow(selectRowData.id);
        }
        
        function editRow(id)
        {	
        	var url = timeURL('${ctx}/cmdb/devOperation/editDevOP.do?id='+id);    
     		showDlg('修改', 750, 350, url); 
        }
		
		//维护
        function maintain(){
          var rows = gridMain.getSelectedRows();
          if(rows.length>1){
            $.ligerDialog.warn("只能维护一条数据!");
            return ;
          }
          if(rows==null||rows==""){
            $.ligerDialog.warn("请选择要维护的数据!");
            return ;
          }
          var url = '${ctx}/cmdb/devOpCmd/maintain.do?id='+selectRowData.id;
     		showDlg('维护操作命令', 1024, 550, timeURL(url));     
        }
     
        
        
       function onDelete()
        {
        	
             var rows = gridMain.getSelectedRows();
	        
	        
	         if(rows!=null&&rows!=""&&rows.length>0){
	           var id = getAllSelectId();
	             $.ligerDialog.confirm('请确认是否删除选中数据', function (yes){
 				if(yes) {
                    delRow(id);
				}
			 });
	         
	         }else{
	          $.ligerDialog.warn("请选择要删除的数据!");
	          return;
	         }
        }
        
        
        function delRow(id)
        {   
            $("#devOpIds").val(id);
        	var url = timeURL('${ctx}/cmdb/devOperation/deletes.do');
        	$.ajax({
        	    type: "POST",
        	    url: url,
        	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
			    data: $("#mulitDelete").serialize(),
        		//dataType: 'json',
        		success: function(data) {
        			 if(data.success==0){
			           	    $.ligerDialog.success("删除成功!");
					        onRefresh(); 
				      	}
        		}
        	});        			
        }
        
        function onRefresh(){
        	window.location.reload();
        }
        
       function batchAudit(){
		   var rows = gridMain.getSelectedRows();
		   if(rows!=null&&rows!=""){
		      var ids = getAllSelectId();
		  $("#devOpIds").val(ids);
        	var url = timeURL('${ctx}/cmdb/devOperation/batchAudit.do');
        	$.ajax({
        	    type: "POST",
        	    url: url,
        	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
			    data: $("#mulitDelete").serialize(),
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
        
       
        
        
      
              
    </script>
</head>
<body style="padding:0px; overflow:hidden; "> 
<form id="mulitDelete" name="mulitDelete" action="" style="display:none" method="post" enctype="multipart/form-data" target="newWin">
  	     <input name="devOpIds" id="devOpIds" value=""  />
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

