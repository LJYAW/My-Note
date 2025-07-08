<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>移动机构</title>
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
        var selectMoveType = null;

        $(function ()
        {
        	selectMoveType = $("#moveType").ligerComboBox({width:200,selectBoxWidth:200,selectBoxHeight:100});
            dataMain.Rows = ${jsonTreeData};
            gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'机构名称', name:'orgName', width:200, align:'left' },
                    { display:'机构类型', name:'orgType', width:120, align:'left' },
                    { display:'机构编码', name:'orgCode', width:150, align:'left' },
                    { display:'机构说明', name:'orgDesc', width:180, align:'left' }
               		],  
               	tree: { columnName: 'orgName' },
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
            
        });
        
        function onMove()
        {
            if (!selectRowData) { 
            	window.parent.$.ligerDialog.error('请选择一行数据！'); 
            	return; 
            }
            var moveType = $("#moveType").val();
            move(selectRowData.id, moveType);
        }

        function move(targetId,moveType)
        {
        	var url = timeURL('${ctx}/system/organ/move.do?moveId=${organ.orgId}&targetId='+targetId+'&moveType='+moveType);
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
           			if(data.result='success'){
           				window.parent.moveOK();  
           			}
        		}
        	});        			
        }

    </script>
</head>
<body style="padding:0px; overflow:hidden; "> 
  <div style="width:100%; padding:5px; overflow:hidden; ">
    <table>
      <tr>
        <td>当前机构"${organ.orgName}" 移动方案：</td>
        <td><s:resSelect id="moveType" name="moveType" code="treeMoveType" ltype="select" /></td>
        <td><a class="l-button" style="width:60px; float:left; margin-left:6px;" onclick="onMove()">确 定</a></td>
      </tr>
    </table>
  </div>    
  <div id="maingrid"></div> 
</body>
</html>

