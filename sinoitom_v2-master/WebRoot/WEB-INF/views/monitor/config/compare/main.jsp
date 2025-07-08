<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>指标组</title>

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
<div id="btn" style="margin-top:2px;">
    <button class="btn btn-white btn-success btn-bold"onclick="onRefresh()" >
        <i class="ace-icon fa fa-refresh bigger-120 green"></i>
        刷新
    </button>
</div>

<div class="col-xs-12" style="overflow: hidden;">

    <table id="list"></table>

    <div id="pager"></div>

</div>

    <script type="text/javascript">

        var gridHeight;
        $(function(){
            gridHeight= $(document).height() - 85 - $('#btn').height();
            pageInit();
            resize();  //自动调整页面Grid
        });

        function pageInit(){
            jQuery("#list").jqGrid( {
                data:  ${jsonStr},
                //  datatype:从服务器端返回的数据类型，默认xml。可选类型：xml，local(客户端数据（数组）)，json，jsonnp，script，xmlstring，jsonstring，clientside
                datatype : "local",


                colNames : [ '机构名称', '厂商名称', '设备分类', '设备类型', '任务类型', '任务名称', '设备IP地址','开始执行时间' ,'操作'],
                colModel : [
                    {name : 'orgName',index : 'orgName',width : 80},
                    {name : 'vendorName',index : 'vendorName',width : 80},
                    {name : 'devClassName',index : 'devClassName',width : 80},
                    {name : 'devTypeName',index : 'devTypeName',width: 80},
                    {name : 'taskType',index : 'taskType',width : 80},
                    {name : 'taskName',index : 'taskName',width : 150},
                    {name : 'devIP',index : 'devIP',width : 150},
                    {
                        name: 'startTime', index: 'startTime', width: 120, formatter: "date",
                        formatter: function (cellValue, options, row) {
                            return getSmpFormatDateByLong(row.startTime, true)
                        }

                    },
                    {
                        width: 150,
                        formatter: function (cellvalue, options, row) {
                            var html = '<span style="color:blue;cursor:pointer;" onclick="currentConfig(\'' + row.devIP + '\');">当前配置</span> | ';
                            html += '<span style="color:blue;cursor:pointer;" onclick="historyVersion(\'' + row.devID + '\');">历史版本</span>'
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
            jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
            $("#t_list").css({"height":30});
            $("#btn").appendTo("#t_list");

        }


        function loadData(){
            selectRowData = null;
            gridMain.loadData();
        }
        
        
        function saveOK(){
        	onRefresh();
        }
        

        function onRefresh(){
        	window.location.reload();
        }
        
		//历史版本
		function historyVersion(id){
            layer_show('历史版本','${ctx}/config/configFile/getHistoryData.do?devID='+id, 890, 460);
		}
		
		//当前配置
		function currentConfig(ip){
            layer_show('当前配置','${ctx}/fas/res/dailyMaintain/desktopDevice/setupMsgMain.do?switchIp='+ip,760,400);
        }


    </script>
</body>
</html>

