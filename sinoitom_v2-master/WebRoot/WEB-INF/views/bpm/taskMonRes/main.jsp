<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ack" value="${pageContext.request.localAddr}"/>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>监测资源</title>

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
<script src="${ctx }/js/dateFormat.js"></script>

<body class="no-skin">
<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li class="active">${titleContent }</li>
    </ul>
</div>
<div id="btn" style="margin-top:2px;">
    <button class="btn btn-white btn-default btn-bold" onclick="onResourceDetail()">
        <i class="ace-icon fa  fa-plus bigger-120 default"></i>
        资源详情
    </button>
    <button class="btn btn-white btn-info btn-bold" onclick="onRelateInds()">
        <i class="ace-icon fa fa-pencil-square-o bigger-120 blue"></i>
        关联指标
    </button>
    <button class="btn btn-white btn-warning btn-bold" onclick="onMonitorInds()">
        <i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
        监测指标
    </button>
    <button class="btn btn-white btn-warning btn-bold" onclick="onAccessVerification()">
        <i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
        访问验证
    </button>
    <button class="btn btn-white btn-warning btn-bold" onclick="onIndsCollectVerification()">
        <i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
     指标采集验证
    </button>
    <button class="btn btn-white btn-warning btn-bold" onclick="onMonitor()">
        <i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
        监测
    </button>
    <button class="btn btn-white btn-warning btn-bold" onclick="onStopMonitor()">
        <i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
        停止监测
    </button>
    <button class="btn btn-white btn-warning btn-bold" onclick="onCancelManage()">
        <i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
        取消纳管
    </button>
    <button class="btn btn-white btn-success btn-bold" onclick="onRefresh()">
        <i class="ace-icon fa fa-refresh bigger-120 green"></i>
        刷新
    </button>
</div>


<div class="col-xs-12" style="overflow: hidden;">
    <table id="list"></table>
    <div id="pager"></div>
</div><!-- /.col -->
<script type="text/javascript">

    var vendorId = "${vendorId}";
    var ids = [];
    var ackAddr = "http://" + (document.location.host.indexOf(":") > 0 ? document.location.host.substring(0,document.location.host.indexOf(":")) : document.location.host) + ":8092/ack";
    var gridHeight = 0;
    $(function () {
        gridHeight = $(document).height() - $('#btn').height() - $('#breadcrumbs').outerHeight() - 85;
        pageInit();
        resize();  //自动调整页面Grid
    });

    function pageInit() {
        jQuery("#list").jqGrid({
            data:  ${jsonListData},
            //  datatype:从服务器端返回的数据类型，默认xml。可选类型：xml，local(客户端数据（数组）)，json，jsonnp，script，xmlstring，jsonstring，clientside
            datatype: "local",
            colNames: ['监测任务','id','resId','modelOID','resClassCode','resTypeCode','机构名称','管理IP地址','资源分类','资源类型','产品型号','操作系统类型','版本分类','软件版本','操作系统特征','访问方式','访问用户','指标采集方式','监测状态'],
            colModel: [
                {name: 'taskName', index: 'taskName', width: 80},
                {name: 'id', index: 'id', width: 30, hidden: true, key:true},
                {name: 'resId', index: 'resId', width: 30, hidden: true},
                {name: 'modelOID', index: 'modelOID', width: 30, hidden: true},
                {name: 'resClassCode', index: 'resClassCode', width: 30, hidden: true},
                {name: 'resTypeCode', index: 'resTypeCode', width: 30, hidden: true},
                {name: 'orgName', index: 'orgName', width: 110},
                {name: 'resIp', index: 'resIp', width: 110},
                {name: 'resClassName', index: 'resClassName', width: 110},
                {name: 'resTypeName', index: 'resTypeName', width: 110},
                {name: 'prodModel', index: 'prodModel', width: 110},
                {name: 'osType', index: 'osType', width: 110},
                {name: 'osVersion', index: 'osVersion', width: 110},
                {name: 'osRelease', index: 'osRelease', width: 110},
                {name: 'osFeature', index: 'osFeature', width: 110},
                {name: 'resAcsMode', index: 'resAcsMode', width: 60},
                {name: 'userName', index: 'userName', width: 60},
                {name: 'indCollMode', index: 'indCollMode', width: 110},
                {name: 'flag', index: 'flag', width: 80,
                    formatter: function (cellvalue, options, row) {
                        var html = '';
                        if (row.flag == "0") {
                            html += '<span >暂停</span>';
                        } else if (row.flag == "1") {
                            html += '<span>监测</span>';
                        } else {
                            html += '<span>暂停</span>';
                        }
                        return html;
                    }
                },
            ],
            viewrecords: true,
            rowNum: 10, rowList: [10, 20, 30],
            pager: '#pager',
            height: gridHeight,
            altRows: true,
            toolbar: [true, 'top'],
            multiselect: true,
            multiboxonly: true,
            loadComplete: function () {
                var table = this;
                setTimeout(function () {
                    updatePagerIcons(table);
                }, 0);
            },
        });
        jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});
        $("#t_list").css({"height": 30});
        $("#btn").appendTo("#t_list");
    }

    function saveOK() {
        layer.closeAll();
        // window.location.reload();
    }

    function onRefresh() {
        window.location.reload();
    }

    //关联指标
    function onRelateInds() {
        //2、获取所选行的ids
        var ids = jQuery("#list").jqGrid('getGridParam','selarrrow');

        var lastLndCollMode = "";
        var lastResClassCode = "";
        var lastResTypeCode = "";
        var lastModelOID = "";
        var lastOsType = "";
        var lastOsVersion = "";
        var lastOsFeature = "";
        if(ids!=null && ids.length>0){
            //3、遍历ids
            for(var i =0;i<ids.length;i++){
                //4、获取ids[i]行的数据
                var rowData=jQuery("#list").jqGrid("getRowData",ids[i]);

                var indCollMode = rowData.indCollMode;
                var resClassCode = rowData.resClassCode+'';
                var resTypeCode = rowData.resTypeCode+'';
                var osType = rowData.osType;
                var osVersion = rowData.osVersion;
                var modelOID = rowData.modelOID;
                var osFeature = rowData.osFeature;

                if(lastLndCollMode!=null && lastLndCollMode!='' && lastLndCollMode!=undefined){
                    // alert(indCollMode);
                    // alert(lastLndCollMode);
                    if(indCollMode!=lastLndCollMode){
                        layer.msg('采集方式不同，不能同时关联，请选择相同采集方式的资源!', {icon: 7, time: 1500});
                        return;
                    }
                }else {
                    lastLndCollMode = indCollMode;
                }

                // resClassCode、resTypeCode、modelOID、osType、osVersion、osFeature

                // resClassCode
                if(lastResClassCode!=null && lastResClassCode!='' && lastResClassCode!=undefined){
                    if(resClassCode!=lastResClassCode){
                        layer.msg('资源分类不同，不能同时关联，请选择相同分类的资源!', {icon: 7, time: 1500});
                        return;
                    }
                }else {
                    lastResClassCode = resClassCode;
                }

                // resTypeCode
                if(lastResTypeCode!=null && lastResTypeCode!='' && lastResTypeCode!=undefined){
                    if(resTypeCode!=-1 && lastResTypeCode!=-1 &&  resTypeCode!=lastResTypeCode){
                        // alert( resTypeCode);
                        // alert( lastResTypeCode);
                        layer.msg('资源类型不同，不能同时关联，请选择相同类型的资源!', {icon: 7, time: 1500});
                        return;
                    }
                }else{
                    lastResTypeCode = resTypeCode;
                }

                // modelOID
                if(lastModelOID!=null && lastModelOID!='' && lastModelOID!=undefined){
                    if(modelOID!='X.X' && modelOID!='X.X' &&  modelOID!=modelOID){
                        layer.msg('型号OID不同，不能同时关联，请选择相同型号OID的资源!', {icon: 7, time: 1500});
                        return;
                    }
                }else{
                    lastModelOID = modelOID;
                }

                // osType
                if(lastOsType!=null && lastOsType!='' && lastOsType!=undefined){
                    if( osType!=lastOsType){
                        layer.msg('操作系统类型不同，不能同时关联，请选择相同操作系统类型的资源!', {icon: 7, time: 1500});
                        return;
                    }
                }else{
                    lastOsType = osType;
                }

                // osVersion
                if(lastOsVersion!=null && lastOsVersion!='' && lastOsVersion!=undefined){
                    if( osVersion!='V.R' && lastOsVersion!='V.R' && osVersion!=lastOsVersion){
                        layer.msg('操作系统版本不同，不能同时关联，请选择相同操作系统版本的资源!', {icon: 7, time: 1500});
                        return;
                    }
                }else{
                    lastOsVersion = osVersion;
                }

                if(lastOsFeature!=null && lastOsFeature!='' && lastOsFeature!=undefined){
                    if( osFeature!='all' && osFeature!='all' && osFeature!=lastOsFeature){
                        layer.msg('操作系统版本不同，不能同时关联，请选择相同操作系统版本的资源!', {icon: 7, time: 1500});
                        return;
                    }
                }else {
                    lastOsFeature = osFeature;
                }
            }

            var url = '${ctx}/bpm/taskMonRes/addResRelateInds.do?ids=' + ids+'&resClassCode='+lastResClassCode+'&resTypeCode='+lastResTypeCode+'&indCollMode='+lastLndCollMode;
            layer_show("关联监测指标", url, 1100, 500)
        }else{
            layer.msg('请选择要关联对象!', {icon: 7, time: 1500});
        }
    }

    // 监测指标
    function onMonitorInds(){
        var ids = jQuery("#list").jqGrid('getGridParam','selarrrow');
        if(ids!=null && ids.length>0){
            layer_show("监测指标",'${ctx}/bpm/taskMonRes/getMonitorInds.do?ids='+ids,800,500)
        }else{
            layer.msg('请至少选择一个资源对象!', {icon: 7, time: 1500});
        }
    }

    //访问验证
    function onAccessVerification(){
        var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
        var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');

        if(selectedId.length>1){
            layer.msg('只能选择一条数据进行访问验证!',{icon: 7,time:1500});
            return;
        }
        if(rowid!=""&&rowid!=null){
            accessVerification(rowid);
            // alert(rowData.resIp);
        }else{
            layer.msg('只能选择一条数据进行访问验证!',{icon: 7,time:1500});
            return;
        }
    }

    //访问验证
    function accessVerification(id){
        const url = '${ctx}/bpm/taskMonRes/accessVerification.do?';
        $.ajax({
                type: "post",
                url: url,
                dataType: "json",
                data: {"id":id},
                beforeSend: function () {
                    layer.load();
                },
                complete: function () {
                    layer.closeAll('loading');
                },
                success: function (data) {
                    if (data.result !=null && data.result !=undefined && data.result != '' ) {
                        if(data.result.indexOf("成功") != -1){
                            layer.msg(data.result,{icon: 1,time:1500});
                        }else{
                            layer.msg(data.result, {icon: 2, time: 1500}, function(){onRefresh();});
                        }
                    }
                },
            });
    }

    // 指标采集验证
    function onIndsCollectVerification(){
        var rowid= jQuery("#list").jqGrid('getGridParam', 'selrow');//获取当前行ID
        var selectedId = jQuery("#list").jqGrid('getGridParam', 'selarrrow');

        if(selectedId.length>1){
            layer.msg('只能选择一条数据进行指标采集验证!',{icon: 7,time:1500});
            return;
        }
        if(rowid!=""&&rowid!=null){
            var rowData=jQuery("#list").jqGrid("getRowData",rowid);
            indsCollectVerification(rowData.id);
        }else{
            layer.msg('只能选择一条数据进行指标采集验证!',{icon: 7,time:1500});
            return;
        }
    }

    //指标采集验证
    function indsCollectVerification(id){
        const url = '${ctx}/bpm/taskMonRes/indsCollectVerification.do?';
        $.ajax({
                type: "post",
                url: url,
                dataType: "json",
                data: {"id":id},
                beforeSend: function () {
                    layer.load();
                },
                complete: function () {
                    layer.closeAll('loading');
                },
            success: function (data) {
                if (data.result !=null && data.result !=undefined && data.result != '' && data.result=='success') {

                    let rd = JSON.stringify(data.indTestResult);
                    window.localStorage.setItem("experimentData", rd);
                    window.parent.parent.layer.open({
                        title: '指标采集验证结果',
                        type: 2,
                        area: ['980px', '450px'],
                        fixed: false, //不固定
                        maxmin: true,
                        content: '${ctx}/bpm/taskMonRes/toIndsCollectVerification.do'
                    });
                } else {
                    layer.msg(data.result, {icon: 2, time: 1500}, function(){onRefresh();});
                }
            },
                error: function () {
                    layer.msg('指标采集验证失败，请联系管理员!', {icon: 2, time: 1500}, function(){onRefresh();});
                }
            }
        );
    }

</script>
</body>
</html>







