<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户角色分配</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI1.2.2/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI1.2.2/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>

    <script type="text/javascript">
    	var dataMain = {};
    	var gridMain = null;
        var checkedItem = [];

        $(function ()
        {
            checkedItem = ${jsonMapData};
            dataMain.Rows = ${jsonListData};
            gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'角色名称', name:'roleName', width:200, align:'left' },
                    { display:'角色编码', name:'roleCode', width:160, align:'left' },
                    { display:'描述', name:'description', width:300, align:'left' }
               		],  
				data: dataMain,
				alternatingRow: 'true', checkbox: true,
                isChecked: f_isChecked, onCheckRow: f_onCheckRow, onCheckAllRow: f_onCheckAllRow,
                width: '100%', height:'100%', 
                pageSize: '20', pageSizeOptions: [10, 20, 30, 50, 100]
            });
               

            $("#bnMap").click(function(){
            	var mapId = checkedItem.join(',');
            	var url = timeURL('${ctx}/system/user/mapRole.do?userId=${user.userId}&mapId='+mapId);
            	$.ajax({
            		url: url,
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

        });

        function f_isChecked(rowdata)
        {
            for(var i=0;i<checkedItem.length;i++){
            	if( checkedItem[i] == rowdata.id ){
            		return true;
            	}
            }        	
            return false;
        }
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
            if(findCheckedItem(ItemID) == -1){
                checkedItem.push(ItemID);
            }
        }
        function removeCheckedItem(ItemID)
        {
            var i = findCheckedItem(ItemID);
            if(i==-1) return;
            checkedItem.splice(i,1);
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
    </script>
</head>
<body style="padding:0px; overflow:hidden; "> 
  <form style="padding:0px;" id="mapForm" action="${ctx}/system/user/mapPower.do" method="post">
  <input type="hidden" name="userId" value="${user.userId}"/>
  <div style="width:100%; padding:5px; overflow:hidden; ">
    <table>
      <tr>
        <td>当前用户"${user.loginName}" 关联角色设置</td>
        <td width="5"></td>
        <td><input id="bnMap" type="button" value="确 定" class="l-button" style="line-height:23px;"/></td>
      </tr>
    </table>
  </div>    
  <div id="maingrid"></div> 
  </form>
</body>
</html>

