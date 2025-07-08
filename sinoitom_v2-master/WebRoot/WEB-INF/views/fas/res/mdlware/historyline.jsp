<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>历史趋势图</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/formatter.js" type="text/javascript"></script>
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script><script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script><script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>

	<script src="${ctx}/js/highcharts-7.1.1/highcharts.js" type="text/javascript"></script>
	<script src="${ctx}/js/highcharts-7.1.1/highcharts-3d.js" type="text/javascript"></script>
	<script src="${ctx}/js/highcharts-7.1.1/modules/exporting.js" type="text/javascript"></script>
	<script src="${ctx}/js/highcharts-7.1.1/modules/data.js" type="text/javascript"></script>
	<script src="${ctx}/js/highcharts-7.1.1/modules/drilldown.js" type="text/javascript"></script>
	<script src="${ctx}/js/highcharts-7.1.1/highcharts-more.js" type="text/javascript"></script>

	<script type="text/javascript">
        var devIpAddr = '${devIpAddr}';
        var content = '${content}';
        var tomcatVersion = '${tomcatVersion}';

        var service = '${service}';
        var historyLineTime = '${historyLineTime}';

        var message = '${message}';

        var memYdata = new Array();
        var threadYdata = new Array();
        var classYdata = new Array();
        var cpuYdata = new Array();

        var xdata = new Array();

        // 初始化，将X、Y轴坐标数据存入数组
        <c:forEach items="${heaplist}" var="heap">
			memYdata.push(parseFloat('${heap.heapused}'.split("MB")[0])); //js中可以使用此标签，将EL表达式中的值push到数组中

			xdata.push('${heap.monitorTime}'); //js中可以使用此标签，将EL表达式中的值push到数组中

        </c:forEach>
        <c:forEach items="${threadlist}" var="thread">
			threadYdata.push(parseFloat('${thread.threadCount}')); //js中可以使用此标签，将EL表达式中的值push到数组中

			xdata.push('${thread.monitorTime}'); //js中可以使用此标签，将EL表达式中的值push到数组中

        </c:forEach>

        <c:forEach items="${cpulist}" var="cpulist">
			classYdata.push(parseFloat('${cpulist.loadedClassCount}')); //js中可以使用此标签，将EL表达式中的值push到数组中
			cpuYdata.push(parseFloat('${cpulist.cpu}'.split("%")[0])); //js中可以使用此标签，将EL表达式中的值push到数组中

			xdata.push('${cpulist.monitorTime}'); //js中可以使用此标签，将EL表达式中的值push到数组中

        </c:forEach>


        $(function () {

            $("#historyLineTime").val(historyLineTime);

			var chart;
			$(document).ready(function() {

				Highcharts.setOptions({
					global: {
						useUTC: false
					}
				});

				chart = new Highcharts.Chart({
					chart: {
						renderTo: 'container',
						type: 'spline',
						marginRight: 10
					},
					title: {
						text: '堆内存使用量'
					},
					xAxis: {
                        categories:xdata
                    },
					yAxis: {
						title: {
							text: "(单位：MB)"
						},
					},
					exporting:{
                        enabled:false//不显示导出
                    },
                    credits:{                          //右下角文本不显示
                        enabled: false
                    },
					legend: {
						enabled: false
					},
					series: [{
						name: '堆内存',
                        data: memYdata
					}]
				});


                chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container2',
                        type: 'spline',
                        marginRight: 10
                    },
                    title: {
                        text: '线程数'
                    },
                    xAxis: {
                        categories:xdata
                    },
                    yAxis: {
                        title: {
                            text: "(单位：个)"
                        },
                        tickInterval:2     //设置Y轴坐标值的间隔固定为2
                    },
                    
                    exporting:{
                        enabled:false//不显示导出
                    },
                    credits:{                          //右下角文本不显示
                        enabled: false
                    },
                    legend: {
                        enabled: false
                    },
                    series: [{
                        name: '活动线程数',
                        data: threadYdata
                    }]
                });

                chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container3',
                        type: 'spline',
                        marginRight: 10
                    },
                    title: {
                        text: '类'
                    },
                    xAxis: {
                        categories:xdata
                    },
                    yAxis: {
                        title: {
                            text: "(单位：个)"
                        },
                    },
                    exporting:{
                        enabled:false//不显示导出
                    },
                    credits:{                          //右下角文本不显示
                        enabled: false
                    },
                    legend: {
                        enabled: false
                    },
                    series: [{
                        name: '已加载类数',
                        data: classYdata
                    }]
                });

                chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container4',
                        type: 'spline',
                        marginRight: 10
                    },
                    title: {
                        text: 'CPU占用率'
                    },
                    xAxis: {
                        categories:xdata
                    },
                    yAxis: {
                        title: {
                            text: "(单位：%)"
                        },
                    },
                    exporting:{
                        enabled:false//不显示导出
                    },
                    credits:{                          //右下角文本不显示
                        enabled: false
                    },
                    legend: {
                        enabled: false
                    },
                    series: [{
                        name: 'CPU占用率',
                        data: cpuYdata
                    }]
                });

            });




        });

        function checkNum(){
            document.getElementById("formSave").submit();

        }

</script>

</head>
<body>
<form id="formSave"  action="${ctx}/fas/res/mdlware/app/index/historyLineview.do" method="post">
	<input type="hidden" name="devIpAddr" value="${devIpAddr}"/>
	<input type="hidden" name="content" value="${content}"/>
	<input type="hidden" name="tomcatVersion" value="${tomcatVersion}"/>

	<div id="center" style="top:2px;height:20px">
		<div style="position:absolute;left:35%;font-size:14px;font-weight:normal;">

			<table>
				<tr>
					<td align="right">时间范围：</td>
					<td>
						<select name="historyLineTime" id="historyLineTime" onchange="checkNum();" style="width:204px;">
							<option value="30Min" >30分钟</option>
							<option value="1Hours" >1小时</option>
							<option value="1Day" >1天</option>
						</select>

					</td>
					<td></td>

					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>

		</div>
	</div>

	<div id="container" style="position:absolute;top:30px;left:0px;width:49%;height:350px"></div>
	<div id="container2" style="position:absolute;top:30px;left:50%;width:49%;height:350px;float:right"></div>
	<div id="container3" style="position:absolute;top:370px;left:0px;width:49%;height:350px"></div>
	<div id="container4" style="position:absolute;top:370px;left:50%;width:49%;height:350px"></div>
</form>
</body>
</html>

