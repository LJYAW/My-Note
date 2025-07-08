<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
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
    <title>命令输出结果</title>

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
    <link href="${ctx}/css/common.css" rel="stylesheet" type="text/css"/>
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


<script type="text/javascript">

    var gridHeight;
    $(function () {
        gridHeight = $(document).height() - 240;
        pageInit();
        resize();  //自动调整页面Grid
        bootbox.setDefaults("locale", "zh_CN");
        refreshAckResource();
    });

    function pageInit() {
        jQuery("#list").jqGrid({
            datatype: 'local',
            colModel: [
                {label: '命令结果', name: 'chkResult', index: 'chkResult', width: 100, hidden: true},
                {label: '指标名称', name: 'indItemName', index: 'indItemName', width: 100},
                {label: '解析结果', name: 'indvalue', index: 'indvalue', width: 300,},
                {label: '取值单位', name: 'measureUnit', index: 'measureUnit', width: 300, hidden: true}
            ],
            rowNum: 50,
            rowList: [50, 100, 200],
// 	        	 pager : '#pager',
            height: gridHeight,
            toolbar: [true, 'top'],
            viewrecords: true,
            sortorder: "desc"
        });
        jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});
        $("#t_list").css({"height": 30});
        $("#btn").appendTo("#t_list");
    }


    function refreshAckResource() {
        $.getJSON(
            "${ctx}/ack/report/getAckResource.do?ackTaskId=" + '${ackTask.ackTaskId}',
            function (result) {
                if (result != null) {
                    $("#ackResource").empty();
                    for (var i = 0; i < result.length; i++) {
                        var res = result[i];
                        $("#ackResource").append("<option  value=\"" + res.mgmtIP + "\" >" + res.mgmtIP + "</option>");
                    }
                    refeshAckCheckCmd();
                }
            }
        );
    }

    function refeshAckCheckCmd() {
        var ackTaskId = '${ackTask.ackTaskId}';
        var taskStartTime = $("#taskStartTime").val();
        var devIp = $("#ackResource").find("option:selected").val();
        $.getJSON(
            "${ctx}/ack/devCheckResult/getDevCheckResult.do?ackTaskId=" + ackTaskId + "&taskStartTime=" + taskStartTime + "&devIp=" + devIp,
            function (result) {
                if (result != null) {
                    $("#checkCmd").empty();
                    for (var i = 0; i < result.length; i++) {
                        var res = result[i];
                        $("#checkCmd").append("<option  value=\"" + res.checkCmdId + "\" >" + res.checkCmd + "</option>");
                    }
                    refeshAckCheckCmdResult();
                }
            });
    }

    function getIndParseResult() {
        <%--var url = "${ctx}/ack/checkData/getDevCheckData.do";--%>
        $.ajax({
            type: "POST",
            dataType: 'json',
            url: "${ctx}/ack/checkData/getDevCheckDataFromColumnBase.do",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: $("#resource").serialize(),
            success: function (result) {
                if (result != null) {
                    $("#list").jqGrid("clearGridData");
                    $("#list").jqGrid('setGridParam', {
                        data: result
                    }).trigger("reloadGrid");
                }
            }
        });
    }


    function refeshAckCheckCmdResult() {
        var ackTaskId = '${ackTask.ackTaskId}';
        var taskStartTime = '${report.ackTaskStartTimeGMT}';
        var devIp = $("#ackResource").find("option:selected").val();
        var checkCmdId = $("#checkCmd").find("option:selected").val();
        $("#ackTaskId").val(ackTaskId);
        $("#devIp").val(devIp);
        $("#startTime").val(taskStartTime);
        $("#checkCmdId").val(checkCmdId);
        $.ajax({
            type: "POST",
            dataType: 'json',
            url: "${ctx}/ack/devCheckResult/getDevCheckCmdResult.do",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: $("#resource").serialize(),
            success: function (result) {
                if (result != null) {
                    $("#resultSample").val(result[0].chkResult);
                    $("#chkResult").val(result[0].chkResult);
                }
            }
        });


    }


</script>
<form id="resource" name="resource" action="" style="display:none" method="post">
    <input type="hidden" name="ackTaskId" id="ackTaskId" value=""/>
    <input type="hidden" name="devIp" id="devIp" value=""/>
    <input type="hidden" name="startTime" id="startTime" value=""/>
    <input type="hidden" name="checkCmdId" id="checkCmdId" value=""/>
    <input type="hidden" name="chkResult" id="chkResult" value=""/>
</form>

<body class="no-skin" style="overflow: hidden;">
<table align="center" class="tb_edit">
    <tr>
        <td class="td_w1"></td>
        <td class="td_w2"></td>
        <td class="td_w3"></td>
        <td class="td_w1"></td>
        <td class="td_w2"></td>
        <td class="td_w3"></td>
    </tr>
    <tr>
        <td align="right">巡检任务：</td>
        <td><input id="checkTask" name="checkTask" value="${ackTask.taskName}" type="text" ltype="text"
                   class="validate[required[操作系统不能为空]] ip1" onblur="checkOSName()"/></td>
        <td></td>
        <td align="right">巡检时间：</td>
        <td><input id="taskStartTime" name="taskStartTime" value="${taskStartTime}" type="text" ltype="text"
                   class="validate[required[操作系统不能为空]] ip1" onblur="checkOSName()"/></td>
        <td></td>
    </tr>
    <tr>
        <td align="right">巡检设备：</td>
        <td>
            <select id="ackResource" name="ackResource" style="width:202px" onchange="refeshAckCheckCmd()">
                <option value="-1">请选择</option>
        </td>
        <td></td>
        <td align="right">巡检命令：</td>
        <td><select id="checkCmd" name="checkCmd" style="width:202px" onchange="refeshAckCheckCmdResult()">
            <option value="-1">请选择</option>
        </td>
        <td align="right"><input id="search" type="button" value="查询" class="l-button-small"
                                 onclick="getIndParseResult()"></input></td>
    </tr>
</table>

<div style="margin-left:130px;margin-top:10px;">
    <span style="float:left;color:#2d5385;">命令结果：</span>
    <textarea id="resultSample" name="resultSample" style="width:680px;height:100px;"></textarea>
</div>

<div class="col-xs-12" style="overflow: hidden;">
    <table id="list"></table>
    <!--     <div id="pager"></div> -->
</div>

</body>
</html>
