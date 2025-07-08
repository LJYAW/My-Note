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
    <title>监测指标</title>

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


<div class="col-xs-12" style="overflow: hidden;">
    <table id="list"></table>
    <div id="pager"></div>
</div><!-- /.col -->
<script type="text/javascript">

    var gridHeight = 0;
    $(function () {
        gridHeight = $(document).height() - $('#btn').height() - $('#breadcrumbs').outerHeight() - 85;
        pageInit();
        // 为序号列添加标题
        jQuery("#list").jqGrid('setLabel','rn', '序号', {'text-align':'center','vertical-align':'middle'},'');
        resize();  //自动调整页面Grid
    });

    function pageInit() {
        jQuery("#list").jqGrid({
            data:  ${jsonListData},
            //  datatype:从服务器端返回的数据类型，默认xml。可选类型：xml，local(客户端数据（数组）)，json，jsonnp，script，xmlstring，jsonstring，clientside
            datatype: "local",
            colNames: ['指标组','指标名称','指标英文名称','数据类型','长度','小数位数','度量单位','采集方式','验证状态'],
            colModel: [
                {name: 'indGroupName', index: 'indGroupName', width: 100},
                {name: 'indItemName', index: 'indItemName', width: 110},
                {name: 'indItemEnName', index: 'indItemEnName', width: 110},
                {name: 'valueType', index: 'valueType', width: 60},
                {name: 'length', index: 'length', width: 40},
                {name: 'decimals', index: 'decimals', width: 40},
                {name: 'measureUnit', index: 'measureUnit', width: 60},
                {name: 'indCollMode', index: 'indCollMode', width: 60},
                {name : 'status',index : 'status',width : 70,
                    formatter:function(cellvalue, options, row){
                        var html = '';
                        if(row.status==1){
                            html += '<span style="color:blue;">已验证</span>';
                        }else{
                            html += '<span style="color:red;">未验证</span>';
                        }
                        return html;
                    }
                }
            ],
            rownumbers: true,  //显示序列号
            viewrecords: true,
            rowNum: 10, rowList: [10, 20, 30],
            pager: '#pager',
            height: gridHeight,
            altRows: true,
            toolbar: [true, 'top'],
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



</script>
</body>
</html>
