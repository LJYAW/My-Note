<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>cmd监测指标</title>

    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css"/>
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
<!-- page specific plugin scripts -->
<!-- 		<script src="${ctx }/static/assets/js/bootstrap-datepicker.min.js"></script> -->
<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
<script src="${ctx }/static/assets/js/resize.js"></script>
<script src="${ctx }/static/assets/js/bootbox.js"></script>

<body class="no-skin">

<div id="btn" style="margin-top:2px;">
    <button class="btn btn-white btn-warning btn-bold"onclick="onDelete()">
        <i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
        删除
    </button>
    <%--<button class="btn btn-white btn-purple btn-bold" onclick="onAudit()">--%>
        <%--<i class="ace-icon fa fa-check-square-o bigger-120 "></i>--%>
        <%--审核--%>
    <%--</button>--%>
<%--    <button class="btn btn-white btn-warning btn-bold"onclick="onView()">--%>
<%--        <i class="ace-icon fa fa-file-text-o bigger-120 orange"></i>--%>
<%--        详情--%>
<%--    </button>--%>

    <button class="btn btn-white btn-warning btn-bold"onclick="parseRuleDetail()">
        <i class="ace-icon fa fa-file-text-o bigger-120 orange"></i>
        解析规则
    </button>

    <button class="btn btn-white btn-success btn-bold"onclick="onRefresh()" >
        <i class="ace-icon fa fa-refresh bigger-120 green"></i>
        刷新
    </button>

</div>


<div class="col-xs-12" style="overflow: hidden;">

    <table id="list"></table>

    <div id="pager"></div>

</div><!-- /.col -->
<script type="text/javascript">


    var gridHeight;
    $(function(){
        gridHeight= $(document).height() - 120 - $('#btn').height();
        pageInit();
        resize();  //自动调整页面Grid
    });

    function pageInit(){
        jQuery("#list").jqGrid( {
            url:'${ctx}/bpm/bpmResIndCmdParse/search.do?id='+'${id}',
            //  datatype:从服务器端返回的数据类型，默认xml。可选类型：xml，local(客户端数据（数组）)，json，jsonnp，script，xmlstring，jsonstring，clientside
            datatype : "json",


            colNames : [ 'id','resClassCode','resId','chkItemId','设备类型', 'ip地址','指标组', '指标英文名称', '指标显示名称', '数据类型', '长度', '小数位数', '度量单位','parseRuldId', '巡检命令','验证状态','验证状态'],
            colModel : [
                {name : 'id',index : 'id',width : 60,hidden: true, key:true},
                {name : 'resClassCode',index : 'resClassCode',width : 60,hidden: true},
                {name : 'resId',index : 'resId',width : 60,hidden: true},
                {name : 'chkItemId',index : 'chkItemId',width : 60,hidden: true},
                {name : 'resTypeName',index : 'resTypeName',width : 60},
                {name : 'resIp',index : 'resIp',width :80},
                {name : 'indGroupName',index : 'indGroupName',width : 100},
                {name : 'indItemEnName',index: 'indItemEnName',width: 100},
                {name : 'indItemName',index : 'indItemName',width : 130},
                {name : 'valueType',index : 'valueType',width : 60},
                {name : 'length',index : 'length',width : 60},
                {name : 'decimals',index : 'decimals',width : 60},
                {name : 'measureUnit',index : 'measureUnit',width : 60},
                {name : 'parseRuldId',index : 'parseRuldId',width : 80,hidden:true},
                {name : 'checkCmd',index : 'checkCmd',width : 260},
                {name : 'status',index : 'status',width : 70,hidden:true},
                {name : 'status1',index : 'status1',width : 70,
                    formatter:function(cellvalue, options, row){
                        var html = '';
                        if(row.status==1){
                            html += '<span style="color:blue;">已验证</span>';
                        }else{
                            html += '<span style="color:red;">未验证</span>';
                        }
                        return html;
                    }
                },
            ],

            pager: '#pager',
            rowNum: 50,
            rowList: [50, 100, 150],
            sortname: 'resTypeName',
            viewrecords : true,
            sorttable:true,
            sortorder: "asc",
            toolbar:[true,'top'],
            multiselect: true,
            height:gridHeight,
            loadComplete: function () {
                var table = this;
                updatePagerIcons(table);
            }
        });
        jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
        $("#t_list").css({"height":30});
        $("#btn").appendTo("#t_list");

    }
    function saveOK(action, data)
    {
        layer.closeAll();
        window.location.reload();
    }
    function onDelete() {
        var ids="";
        var count1=0;
        var count2=0;
        var selectedIds = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (selectedIds==""||selectedIds==null) {
            layer.msg('请选择一条数据！',{icon: 7,time:1500});
            return;
        }

        for(var i=0;i<selectedIds.length;i++){
            var rowData = jQuery("#list").jqGrid('getRowData',selectedIds[i]);
            if(rowData.status=="0"){
                ids+=selectedIds[i]+",";
                count1++;
            }else{
                count2++;
            }
        }
        if(count2>0&&count1==0){
            layer.msg('所选数据都已经通过审核！',{icon: 7,time:1500});
            return;
        }
        deleteRow(ids);
    }
    function deleteRow(id)
    {
        layer.confirm('确认要删除吗？', function (yes) {
            delRow(id);
        });
    }

    function delRow(id)
    {
        var url = '${ctx}/bpm/bpmResIndCmdParse/delete.do';
        $.ajax({
            url: url,
            type:"POST",
            dataType: 'json',
            data:{"ids":id},

            success: function(data) {
                if(data.success==0){
                    layer.msg('删除成功!',{icon: 1,time:1500});
                }else{
                    layer.msg(data.message,{icon: 2,time:1500});
                }
                window.location.reload();
            }
        });
    }
    //审核
    function onAudit(){
        var ids="";
        var count1=0;
        var count2=0;
        var selectedIds = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        if (selectedIds==""||selectedIds==null) {
            layer.msg('请选择一条数据！',{icon: 7,time:1500});
            return;
        }

        for(var i=0;i<selectedIds.length;i++){
            var rowData = jQuery("#list").jqGrid('getRowData',selectedIds[i]);
            if(rowData.status=="0"){
                ids+=selectedIds[i]+",";
                count1++;
            }else{
                count2++;
            }
        }
        if(count2>0&&count1==0){
            layer.msg('所选数据都已经通过审核！',{icon: 7,time:1500});
            return;
        }
        audit(ids);

    }

    function audit(ids)
    {
        layer.confirm('确认通过审核吗？', function (yes) {
            auditRow(ids);

        });

    }

    function auditRow(ids)
    {
        var url = '${ctx}/bpm/cmdbResIndCmdParse/saveAudit.do?devChkItemIds='+ids;
        $.ajax({
            url: url,
            dataType: 'json',
            success: function(data) {
                if( data.success == '0' ){
                    layer.msg('审核成功!',{icon: 1,time:1500});
                    onRefresh();
                }
            }
        });
    }

    //详情
    function onView() {
        var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        var rowData = jQuery("#list").jqGrid('getRowData', selectedId);
        if (selectedId.length > 1) {
            layer.msg('只能选择一条数据进行查看!', {icon: 7, time: 1500});
            return;
        }
        if (rowData.resClassCode == 13) {
            var url = '${ctx}/fas/res/net/devices/view.do?id=' + rowData.resId;
            layer_show('网络资源信息', url, 800, 500);
        } else if (rowData.resClassCode == 11) {
            var url = '${ctx}/fas/res/host/resHosts/view.do?id=' + rowData.resId;
            layer_show('主机设备详情', url, 800, 500);
        }
    }

    function onRefresh(){
        window.location.reload();
    }



    //解析规则详情
    function parseRuleDetail(){
        var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
        var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
        var rowData = jQuery("#list").jqGrid('getRowData',selectedId);

        if(selectedId.length>1){
            layer.msg('只能选择一条数据进行查看!',{icon: 7,time:1500});
            return;
        }
        if(rowid!=""&&rowid!=null){
            detailRow(rowData.chkItemId);
        }else{
            layer.msg('只能选择一条数据进行查看!',{icon: 7,time:1500});
            return;
        }

    }

    function detailRow(id){

        layer_show("指标解析规则详情",'${ctx}/cmdb/prodCmdCheckItems/detail.do?id='+id,800,500);
    }


</script>
</body>
</html>



