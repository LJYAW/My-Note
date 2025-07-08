<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>历史版本</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/mainTab.js" type="text/javascript"></script>
<script src="${ctx}/static/formatter.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var dataMain = {};
    	var gridMain;
        var selectRowData = null;
        
        var height=window.screen.height;
        var width=window.screen.width;
        $(function ()
        {
            dataMain.Rows = ${jsonStr};
            $("#toptoolbar").ligerToolBar({ 
				items: 
            		[
					{ text:'比较', click:compare, icon:'add' },
                	{ line:true },
                	{ text:'基线设置', click:setBaseline, icon:'edit' },
                	{ line:true },
                	{ text:'删除', click:onDelete, icon:'delete' },
                	{ line:true },
                	{ text:'刷新', click: onRefresh, icon:'refresh' }
                	]
            });

             
             gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'设备IP地址', name:'devIpAddr', width:110, align:'left',type:'text'},
                    { display:'配置文件类型', name:'cfgFileType', width:100, align:'center',type:'text',
                      render: function (row)
                      {
                        var html="";
                        if(row.cfgFileType==1){
                           html="启动配置文件";
                        }else{
                            html="当前运行配置";
                        }
                        return html;
                      }
                    },
                    { display:'配置文件名称', name:'cfgFileName', width:330, align:'left',type:'text'},
            		{ display:'基线配置', name:'baseLine', width:80, align:'center',type:'int',
            		  render:function(row){
            		    if(row.baseLine==1){
            		      return "基线";
            		    }
            		  }
            		},
            		{ display:'采集时间', name:'collection_Time', width:140, align:'center' ,type:'dateFormat[yy-MM-dd hh:mm:ss]'},
            		{ 
                    	display:'操作', width:80,
                        render: function (row)
                        {
                           var html = ' <span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick="onView(\'' + row.id + '\');">查看</span>';
                            return html;
                        }
                    }
               		],  
				data: dataMain,
				alternatingRow: 'true',
                width: '100%',
                checkbox:true,
                height:'100%', 
                pageSize: '100',
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
        
        
        //比较
        function compare(){
            var rows = gridMain.getSelectedRows();
		    if( rows == null||rows==""){
		    window.parent.$.ligerDialog.warn("请选择比对配置文件!");
		   }else{
		      if(rows.length==2){
		        var ids=rows[0].id+";"+rows[1].id;
		        var url = timeURL('${ctx}/config/configFile/compare.do?ids='+ids);
         		 window.open(url,"文件对比", "height="+height+", width="+width+", top=160, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no");
		      }else{
		        window.parent.$.ligerDialog.warn("必须选择两个配置文件进行比较!");
		      }
		   }
        }
        

        function saveOK(){
        	onRefresh();
        }
        

        function onRefresh(){
        	window.location.reload();
        }
        
        //删除
	 function onDelete()
        {
             var rows = gridMain.getSelectedRows();
	         if(rows!=null&&rows!=""&&rows.length>0){
	         var ids = getAllSelectId();
	         window.parent.$.ligerDialog.confirm('请确认是否删除选中数据?', function (yes){
 				if(yes) {
                    delRow(ids);
				}
			 });
	         
	         }else{
	          window.parent.$.ligerDialog.warn("请选择要删除的数据!");
	          return;
	         }
        }
            function delRow(id)
        {   
           var url=timeURL('${ctx}/config/configFile/deletes.do');
            $.ajax({
                   url:url,
                   type:"POST",
                   dataType:'json',
                   data:{"ids":id},
                   success:function(data){
                       if(data.result=='success'){
                             window.parent.$.ligerDialog.success("删除成功!");
                             saveOK();
                       }
                    }
             });       			
        }
		
		//查看
		function onView(id){
		 var url = timeURL('${ctx}/config/configFile/view.do?id='+id);
//          window.parent.parent.showDlg('查看配置文件', 800, 460, url); 
         window.open(url,'配置文件比对', 'height=560, width=800, top=160, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
		}
		
		function setBaseline(){
		   var rows = gridMain.getSelectedRows();
		     if( rows == null||rows==""||rows.length>1){
		    window.parent.$.ligerDialog.warn("请选择一条数据进行设置!");
		   }else{
		      var url=timeURL('${ctx}/config/configFile/setBaseLine.do?id='+rows[0].id);
            $.ajax({
                   url:url,
                   type:"GET",
                   dataType:'html',
                   success:function(data){
                       if(data=='success'){
                             window.parent.$.ligerDialog.success("基线配置设置成功!");
                             saveOK();
                       }
                    }
             });
		   }
		}
       
    </script>
</head>
<body style="padding:0px; overflow:hidden; "> 
	<div id="toptoolbar"></div> 
    <div id="maingrid"></div> 
    <div style="display:none;">
    </div>
 </div>
</body>
</html>

