<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>监测任务-关联业务系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

    <script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
    <script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/dataFormat.js" type="text/javascript"></script>
    <script src="${ctx}/js/common.js" type="text/javascript"></script>
    <script src="${ctx}/js/mainTab.js" type="text/javascript"></script>
    <%-- <script src="${ctx}/static/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script> --%>
    <script src="${ctx}/js/index.js" type="text/javascript"></script>

    <script type="text/javascript">
    	var dataMain = {};
    	var gridMain;
        var selectRowData = null;
    	var manager;
    	var taskId='${id}';

        $(function (){
            <c:if test="${result=='success'}">
    		window.parent.saveOK();
            </c:if>
        	$("#toptoolbar").ligerToolBar({ 
				items: 
            		[
                	{ text:'保存关联业务系统', click: selectInd, icon:'add' },
                	{ line:true },
                	{ text:'刷新', click: onRefresh, icon:'refresh' }
                	]
            });
             dataMain.Rows = ${jsonListData};
            manager=gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'<span style="font-weight:bold; ">id</span>', name:'id', width:80,hide: true,type:'text' },
                    { display:'<span style="font-weight:bold; ">组织机构</span>', name:'orgName', width:80,align:'center',type:'text' },
                    { display:'<span style="font-weight:bold; ">资源类型</span>', name:'resTypeName', width:120,align:'center',type:'text'},
                    { display:'<span style="font-weight:bold; ">业务类型</span>', name:'bizTypeName', width:80,align:'center',type:'text'},
                    { display:'<span style="font-weight:bold; ">系统英文名称</span>', name:'sysEnName', width:120,align:'center',type:'text'},
                    { display:'<span style="font-weight:bold; ">系统中文名称</span>', name:'sysName', width:150,align:'center',type:'text'},
                    { display:'<span style="font-weight:bold; ">访问方式</span>', name:'accessMode', width:80,align:'center',type:'text'},
                    { display:'<span style="font-weight:bold; ">访问协议</span>', name:'accessProt', width:100,align:'left',type:'text'},
                        { display:'<span style="font-weight:bold; ">服务器类型</span>', name:'svrType', width:70, align:'center',
                            render: function (row){
                                var html='';
                                if(row.svrType=='0'){
                                    html = '<span style=\"align:certer;line-height:22px;color:green;\">虚拟机</span>';
                                }else if(row.svrType=='1'){
                                    html = '<span style=\"align:certer;line-height:22px;color:red;\">物理机</span>';
                                }else if(row.svrType=='3'){
                                    html = '<span style=\"align:certer;line-height:22px;color:red;\">集群</span>';
                                }else{
                                    html = '<span style=\"align:certer;line-height:22px;color:red;\"></span>';
                                }
                                return html;
                            }
                        },
                    { display:'<span style="font-weight:bold; ">服务器IP地址</span>', name:'svrIpAddr', width:80,align:'center',type:'text'},
                    { display:'<span style="font-weight:bold; ">服务端口</span>', name:'svcPort', width:80,align:'center',type:'text'},
                    { display:'<span style="font-weight:bold; ">访问地址(URL)</span>', name:'bizURL', width:180,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">访问地址状态</span>', name:'bizURLStatus', width:100, align:'center',
                            render: function (row){
                                var html='';
                                if(row.svrType=='0'){
                                    html = '<span style=\"align:certer;line-height:22px;color:green;\">OK</span>';
                                }else if(row.svrType=='1'){
                                    html = '<span style=\"align:certer;line-height:22px;color:red;\">NO</span>';
                                }else{
                                    html = '<span style=\"align:certer;line-height:22px;color:red;\">NO</span>';
                                }
                                return html;
                            }
                        }

               	],
				data: dataMain,
				alternatingRow: 'true',
				usePager:false,
				checkbox:true, 
                width: '100%',
                height:'100%',
                pageSize: '50',
                pageSizeOptions: [25, 50, 100,200],
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                } 
            });
            
            });
            
      
      function onRefresh(){
          window.location.reload();
      }

   function selectInd(){
      var devData = manager.getSelectedRows();
      $("#devJsonStrs").val(JSON.stringify(devData));
      if(devData==null||devData==""){
        $.ligerDialog.warn("请选择至少一个业务系统!");
        return;
      }
       if(devData!=null && devData!=""&& devData.length>0){
           var ids = getAllSelectId();
           $("#bizIdListStr").val(ids);
       }
         $("#formSave").submit();
      
   }        	

    </script>
</head>
<body style="padding:0px; overflow:hidden;" onload="">
	<form id="formSave" action="${ctx}/bpm/task/taskRelateBiz.do" method="post">
	 <input type="hidden" name="taskId" id="taskId" value="${id}"  />
	 <input  type="hidden" name="bizIdListStr" id="bizIdListStr" value=""  />
	</form>
	<div id="toptoolbar"></div> 
    <div id="maingrid"></div>
    
    <div id="indToptoolbar" style="display:none;">业务系统列表</div>
    <div id="indMaingrid" style="display:none;"></div>
    
</body>
</html>