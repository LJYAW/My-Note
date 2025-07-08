<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="com.sino.base.common.Global"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>资源分类管理</title>
<link rel="stylesheet" href="${ctx }/css/common.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css"/>
    <link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet"/>
    <!-- ace styles -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/layer.css"/>
</head>



<!-- basic scripts -->
<script src="${ctx }/static/assets/js/jquery.min.js"></script>
<script src="${ctx }/static/assets/js/layer.js"></script>
<script src="${ctx }/static/assets/js/H-ui.admin.js"></script>

<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
<script src="${ctx }/static/assets/js/resize.js"></script>
<script src="${ctx }/static/assets/js/bootbox.js"></script>

<script type="text/javascript">
	var gridHeight;
    $(function () {
        gridHeight = $(document).height() -80 - $('#btn').height();
        pageInit();
        resize();  //自动调整页面Grid
        bootbox.setDefaults("locale", "zh_CN");
    });

        function pageInit() {
        jQuery("#list").jqGrid({
            data: ${jsonListData},
            datatype: "local",
            colModel: [
                {label:'ID',name: 'id', index: 'id', width: 80,hidden:true},
            	{label:'效果图', width: 60,
            	  formatter: function (cellvalue, options, row) {
                       var html = '';
						html = '<img src="<%=Global.LOAD_DEV_IMG_URL%>'+row.objIcon+'"  style="width:25px;height:22px;">';
						return html;
                    }
            	},
                {label:'类型英文名称',name: 'classEnName', index: 'classEnName', width: 80},
                {label:'对象分类',name: 'typeName', index: 'typeName', width: 60},
                {label:'图元类型',name: 'objType', index: 'objType', width: 80,
                
                	formatter: function (cellvalue, options, row) {
                       var html = '';
						if (row.objType == "0") {

							html = '<span >子图</span>';
								return html;
							} else if (row.objType == "1") {

								html = '<span >节点</span>';
								return html;
						}
                    }
                },
                {label:'图元显示名称',name: 'objName', index: 'objName', width: 80},
                {label:'Icon名称',name: 'objIcon', index: 'objIcon', width: 80,
                  
                  formatter: function (cellvalue, options, row) {
                     				  var html = '';
                    						if(row.objIcon=="\"null\""){
                    							html += '<span class="linkNormal"></span>';
                    						}else{
                       							html += '<span class="linkNormal"  onclick="showbgPicture(\'' + row.id + '\',\'' + row.objIcon + '\');">'+row.objIcon+'</span>';
                       						}
                        					return html;
                        				}
                },
                {label:'状态',name: 'status', index: 'status', width: 80,hidden:true},
                {label:'状态', width: 80,
                  formatter: function (cellvalue, options, row) {
                      var html = '';
						if (row.status == "0") {
							html = '<span style="color:blue">未审核</span>';
							} else if (row.status == "1") {
								html = '<span style="color:green" >已审核</span>';
							}
							return html;
                    }
                }
               
            ],

            viewrecords: true,
            rowNum: 50,
            rowList: [50, 100, 150],
            pager: '#pager',
            height: gridHeight,
            altRows: true,
            toolbar: [true, 'top'],
            multiselect: true,
//          multiboxonly: true,
            loadComplete: function () {
                var table = this;
                updatePagerIcons(table);
            },
        });
        jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});
        $("#t_list").css({"height": 30});
        $("#btn").appendTo("#t_list");

    }



	
	
	function onAdd() {
		var url = '${ctx}/topo/objType/add.do';
		layer_show('添加拓扑元素', url, 750, 400);
	}

	function onEdit() {
		 var rows = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
		 
		if (rows.length > 1 || rows.length == 0) {
			layer.msg('请选择一行需要修改的图元！', {icon: 7, time: 1500});
			return;
		}else{
		  
		    var rowData = jQuery("#list").jqGrid('getRowData',rows[0]);
			editRow(rows[0],rowData.status);
		}
	}

	function editRow(id,status) {
		if (status=="1") {
			layer.msg('该图元数据已审核，不能修改', {icon: 7, time: 1500});
	    }
	    else if(status=="0"){
		  	var url = '${ctx}/topo/objType/edit.do?id='+ id;
		  	layer_show('修改拓扑元素', url, 750, 400);
	    }
	}

	function saveOK() {
		window.parent.saveOK();
		
	}
	function onDelete() {
        var rows = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
		if (rows.length == 0) {
			layer.msg('请选择要删除的图元！', {icon: 7, time: 1500});
			return;
		}
		if (rows != null && rows != "" && rows.length > 0) {
		
		    for ( var i = 0; i < rows.length; i++) {
		      var rowData = jQuery("#list").jqGrid('getRowData',rows[i]);
				if(rowData.status=="1"){
					layer.msg('您所选的元素中有已审核的元素，请重新选择！', {icon: 7, time: 1500});
			    	return;
			   }
		    }
			layer.confirm('确认要删除吗？', function (yes) {
            	deleteDate(rows);
        	 });

		}
	}

	function deleteRow(id) {
		$.ligerDialog.confirm('确认删除拓扑元素？', function(yes) {

			if (yes)
				deleteDate(id);
		});

	}
	//大批量的删除
	function deleteDate(ids) {

		var url = '${ctx}/topo/objType/mulitDelete.do';
		$.ajax({
			type : "post",
			url : url,
			data : {
				"id" : ids
			},
			contentType : "application/x-www-form-urlencoded; charset=utf-8",

			success : function(data) {
				onRefresh();

			}
		});
	}
	


	function onRefresh() {
		window.parent.refreshs();
	}
	
	function showbgPicture(id,iconName){
        	var image = new Image();
			image.src = "<%=Global.LOAD_DEV_IMG_URL%>"+iconName;
            var url = '${ctx}/topo/objType/showbgPicture.do?id='+id;
            if(image.complete){ 
              layer_show('背景图展示', url,image.width+60,image.height+90 );
            }
        }
        
    function onAudit(){
        var rows = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
		if (rows.length > 1 || rows.length == 0) {
			layer.msg('请选择一条需要审核的图元！', {icon: 7, time: 1500});
			return;
		}else{
		 var rowData = jQuery("#list").jqGrid('getRowData',rows[0]);
			audit(rows[0],rowData.status);
		}
       }
       function audit(id,status){
       if(status=="1") {
			layer.msg('该图元据已审核！', {icon: 7, time: 1500});
	    }
	    if(status=="0"){
	    	var url = '${ctx}/topo/objType/audit.do?id=' + id;
			layer_show('拓扑元素审核', url, 750, 400);
	    }
       }   

</script>
<body>

<div id="btn" style="margin-top:2px;">
    <button class="btn btn-white btn-default btn-bold" onclick="onAdd()">
    <i class="ace-icon fa  fa-plus bigger-120 default"></i>
        	添加
    </button>
    
    <button class="btn btn-white btn-info btn-bold" onclick="onEdit()">
     <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
        	修改
    </button>
    
    <button class="btn btn-white btn-warning btn-bold" onclick="onDelete()">
        <i class="ace-icon fa fa-trash-o bigger-120"></i>
        	删除
    </button>
     <button class="btn btn-white btn-warning btn-bold" onclick="onAudit()">
        <i class="ace-icon fa fa-check-square-o bigger-120"></i>
        	审核
    </button>
    
    <button class="btn btn-white btn-success btn-bold" onclick="onRefresh()">
        <i class="ace-icon fa fa-refresh bigger-120"></i>
        	刷新
    </button>

</div>


<div class="col-xs-12" style="overflow: hidden;">
    <table id="list"></table>
    <div id="pager"></div>
</div>
</body>
	</html>

