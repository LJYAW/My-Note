<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>移动菜单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css" />

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css" />
		<link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet" />
		<!-- ace styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />
		
		<script src="${ctx }/static/assets/js/jquery.min.js"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>
	    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script> 
		
		<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
		<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
		<!-- page specific plugin scripts -->
		<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
		<script src="${ctx }/static/assets/js/grid.locale-en.js"></script>
		<script src="${ctx }/static/assets/js/resize.js"></script>
</head>
    <script type="text/javascript">
    var gridHeight;	
    var currentRowId;		
	$(function(){ 
			
		gridHeight= $(document).height() - 120 - $('#btn').height();
		pageInit();	
		resize();  //自动调整页面Grid	
	});

        function pageInit()
        {
             jQuery("#list").jqGrid({
                
                url:'${ctx}/system/menu/moveGoDatas.do?id='+'${id}',
                treeGrid: true,  
                treeGridModel: 'adjacency', //treeGrid模式，跟json元数据有关 ,adjacency/nested  
                ExpandColumn : 'menuName',
                datatype: 'json',  
                colNames:['菜单名称','菜单URL','菜单说明'],  
                colModel:[  
                    {name:'menuName',index:'menuName', width:200},  
                    {name:'menuUrl',index:'menuUrl', width:220},  
                    {name:'menuDesc',index:'menuDesc', width:220}
                ],  
                pager: '#pager',  
                sortname: 'menuName',  
                viewrecords : true, 
                sortorder: "asc",  
                height: 285 ,
                treeReader : {  
                    level_field: "level",  
                    parent_id_field: "parentId",
                    leaf_field: "isLeaf",  
                    expanded_field: "expanded"  
                }, 
                loadComplete : function() {
					var table = this;
					setTimeout(function(){
					updatePagerIcons(table);
					}, 0);
				},
				onSelectRow:function(rowId,status){
				  currentRowId=rowId;
				} 
            }); 
			jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
			$("#t_list").css({"height":30});
			$("#btn").appendTo("#t_list");
        }
        
        function onMove()
        {
            if (!currentRowId) { 
            	window.parent.$.ligerDialog.error('请选择要移动到的菜单节点目标！'); 
            	return; 
            }
            var moveType = $("#moveType").val();
            move(currentRowId, moveType);
        }

        function move(targetId,moveType)
        {
        	var url ='${ctx}/system/menu/move.do?moveId=${id}&targetId='+targetId+'&moveType='+moveType;
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

<body style="padding:0px; overflow:hidden; "> 
  <div style="width:100%; padding:5px; overflow:hidden; ">
    <table>
      <tr>
        <td>当前菜单"${menu.menuName}" 移动方案：</td>
        <td><s:resSelect id="moveType" name="moveType" code="treeMoveType" ltype="select" /></td>
        <td><a class="l-button" style="width:60px; float:left; margin-left:6px;" onclick="onMove()">确 定</a></td>
      </tr>
    </table>
  </div>    
<div class="col-xs-12" style="overflow: hidden;">
    <table id="list"></table>
    <div id="pager"></div>
</div>
</body>
</html>

