<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<shiro:hasPermission name="employee:read"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>设备操作OID</title>
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
            		{ text:'添加SnmpOID', click: addSnmpOID, icon:'add' },
                	{ line:true },
                	{ text:'修改', click: onEdit, icon:'edit'},
                	{ line:true },
                	{ text:'删除', click: onDelete, icon:'delete' },
                	{ line:true },
                	{ text:'详情', click: onView, icon:'search'},
                	{ line:true },
                	{ text:'刷新', click: onRefresh, icon:'refresh' },

                	]
            });

            dataMain.Rows = ${jsonListData};
            gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'厂商', name:'dispName', width:70, align:'center'},
                    { display:'设备类型', name:'devTypeName', width:70, align:'center'},
                    { display:'设备型号', name:'modelName', width:80, align:'center'},
                    { display:'型号OID', name:'model_OID', width:100, align:'center'},
                    	
		            { display:'操作顺序', name:'oidOrder', width:60, align:'center',type:'int',
                    		render:function(row){
                        		if(row.oidOrder==0){
                        			return "无关";
                        		}else{
                        			return row.oidOrder;
                        		}
                        	}
                    		},
		            { display:'程序变量名称', name:'objectName', width:180, align:'left' },
		            { display:'Snmp对象', name:'snmpObject', width:120, align:'left' },
		            { display:'SnmpOID', name:'snmpOID', width:200, align:'left' },
		            { display:'操作方式', name:'getMethod', width:70, align:'left'},
		            { display:'取值类型', name:'valueType', width:70, align:'center'},
		            { display:'取值单位', name:'units', width:70, align:'center'},
		            { display:'Snmp赋值', name:'setValue', width:80, align:'left'},
		            //{ display:'缺省阀值', name:'defaultThreshold', width:100, align:'left' },
		            { display:'状态', name:'status', width:70, align:'left'},
               		],  
				data: dataMain,
				alternatingRow: 'true',
                width: '100%',
                height:'100%', 
                pageSize: '100',checkbox:true, 
                pageSizeOptions: [50, 100,200,500],
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
        
        
        function addSnmpOID()
        {
        	var url = timeURL('${ctx}/cmdb/devOpOID/add.do');
    		showDlg('添加', 750, 400, url);     
        }

        function onEdit()
        {
        	var rows = gridMain.getSelectedRows();
        	var row = gridMain.getSelectedRow();
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
        	var url = timeURL('${ctx}/cmdb/devOpOID/edit.do?id='+id);    
     		showDlg('修改', 750, 400, url); 
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
        
		
        function onView(){
        	var rows = gridMain.getSelectedRows();
        	var row = gridMain.getSelectedRow();
        	if(rows.length>1){
        		$.ligerDialog.warn('只能选择一条数据查看！'); 
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
        	var url = timeURL('${ctx}/cmdb/devOpOID/view.do?id='+id);    
     		showDlg('详情', 750, 350, url); 
        }
        
		
        
       function onDelete()
        {
        	
             var rows = gridMain.getSelectedRows();
	        
	        
	         if(rows!=null&&rows!=""&&rows.length>0){
	           var id = getAllSelectId();
	            $.ligerDialog.confirm('请确认是否删除选中数据?', function (yes){
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
        	var url = timeURL('${ctx}/cmdb/devOpOID/deletes.do');
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

