<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>角色关联权限</title>
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
            dataMain.Rows = ${jsonTreeData};
            gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'权限组', name:'grpName', width:150, align:'left' },
                    { display:'组全选', name:'grpChk', width:60, align:'center' },
                    { display:'关联权限', name:'grpPowCheck', width:800, align:'left' }
               		],  
               	tree: { columnName: 'grpName' },
				data: dataMain,
				alternatingRow: 'true',
                width: '100%',
                height:'100%', 
                usePager : false,//是否分页 ,默认是true
//                 pageSize: '50',
//                 pageSizeOptions: [10, 20, 30, 50, 100],
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                } 
            });
            
            $("#mapForm").ajaxForm({
            	dataType: 'json',
            	success: function(data) { 
           			if(data.result=='success'){
           				$.ligerDialog.success('关联成功！');
           			}
           			else{
           				$.ligerDialog.error('关联失败！');
           			}
            	}
            }); 

            
        });

        function chkGroup(obj){
        	$("input[group='"+obj.id+"']").attr("checked",obj.checked); 
        }
        
        function chkAll(obj){
        	$("input[name='chkGrp']").attr("checked",obj.checked); 
        	$("input[name='chkPower']").attr("checked",obj.checked); 
        }
    </script>
</head>
<body style="padding:0px; overflow:hidden; "> 
  <form style="padding:0px;" id="mapForm" action="${ctx}/system/role/mapPower.do" method="post">
  <input type="hidden" name="roleId" value="${role.roleId}"/>
  <div style="width:100%; padding:5px; overflow:hidden; ">
    <table>
      <tr>
        <td>当前角色"${role.roleName}" 关联权限设置</td>
        <td width="10"></td>
        <td> <input type="checkbox" onclick="chkAll(this)"/>全选</td>
        <td width="10"></td>
        <td><input id="bnSearch" type="submit" value="确 定" class="l-button"/></td>
      </tr>
    </table>
  </div>    
  <div id="maingrid"></div> 
  </form>
</body>
</html>

