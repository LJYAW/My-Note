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
<title>类</title>
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
        var service = '${service}';
        var userName = '${userName}';
        var psWord = '${psWord}';

        $(function () {
            $(document).ready(function () {
                Highcharts.setOptions({
                    global: {
                        useUTC: false
                    }
                });
                var chart;
                $('#container').highcharts({
                    chart: {
                        type: 'spline',
                        animation: Highcharts.svg,
                        marginRight: 10,
                        events: {
                            load: function () {
// set up the updating of the chart each second
                                var series = this.series[0];
                                var series1 = this.series[1];
                                setInterval(function () {
                                    var x = (new Date()).getTime(), // current time
                                        y = parseFloat(SetYdata().split(",")[0]);
                                    y2 = parseFloat(SetYdata().split(",")[1]);
                                    var s = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', x);
                                    $("#time").val("");
                                    $("#time").val(s);
                                    $("#loadedClassCount").val("");
                                    $("#loadedClassCount").val(parseFloat(SetYdata().split(",")[0]));
                                    $("#totalLoadedClassCount").val("");
                                    $("#totalLoadedClassCount").val(parseFloat(SetYdata().split(",")[1]));
                                    $("#unloadedClassCount").val("");
                                    $("#unloadedClassCount").val(parseFloat(SetYdata().split(",")[2]));
                                    series.addPoint([x, y], true, true);
                                    series1.addPoint([x, y2], true, true);
                                }, 10000);
                            }
                        }
                    },
                    title: {
                        text: '类'
                    },
                    xAxis: {
                        type: 'datetime',
                        // tickPixelInterval: 150
                        tickInterval: 30*1000
                    },
                    yAxis: {
                        title: {
                            text: "(单位：个)"
                        },
						max:parseFloat(SetYdata().split(",")[1]),    //设置Y轴最大值为“max” 	${max}
                        min:0,			//parseFloat(SetYdata().split(",")[1])/2
                        tickInterval:3000,     //设置Y轴坐标值的间隔固定为50
                        plotLines: [{
                            value: 0,
                            width: 1,
                            color: '#808080'
                        }]
                    },
                    exporting:{
                                enabled:false//不显示导出
                            },
                    credits:{                          //右下角文本不显示
                        enabled: false
                    },
                    tooltip: {
                        formatter: function () {
                            return '<b>' + this.series.name + '</b><br/>' +
                                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                                Highcharts.numberFormat(this.y, 3);
                        }
                    },
                    legend: {
                        enabled: false
                    },
                    // exporting: {
                    //     enabled: false		//导出功能不显示
                    // },
                    series: [{
                        name: '已加载类数',
                        data: (function () {
// generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;
                            for (i = -10; i <= 0; i += 1) {
                                data.push({
                                    x: time + i * 5000,
                                    y: parseFloat(SetYdata().split(",")[0])			//parseFloat(SetYdata().split(",")[1])
                                });
                            }
                            return data;
                        })()
                    }, {
                        name: '类加载总数',
                        data: (function () {
// generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;
                            for (i = -10; i <= 0; i += 1) {
                                data.push({
                                    x: time + i * 5000,
                                    y: parseFloat(SetYdata().split(",")[1])		//parseFloat(SetYdata().split(",")[0])
                                });
                            }
                            return data;
                        })()
                    }]
                });
            });


            function SetYdata(){
                var y = null;
                //通过轮询或者广播的方式获取y的实时的值
                $.ajax({
                    // type: "POST",
                    url: "${ctx}/fas/res/mdlware/JMXmonitorLineView.do?service="+service+'&userName='+userName+'&psWord='+psWord,
                    async: false, //表示同步，如果要得到ajax处理完后台数据后的返回值，最好这样设置
                    success: function (data) {

                        y = data.loadedClassCount+","+data.totalLoadedClassCount+","+data.unloadedClassCount;

                    }
                }, false);
                var x = (new Date()).getTime(); // current time
                var s = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', x);
                $("#time").val("");
                $("#time").val(s);
                $("#loadedClassCount").val("");
                $("#loadedClassCount").val(parseFloat(y.split(",")[0]));
                $("#totalLoadedClassCount").val("");
                $("#totalLoadedClassCount").val(parseFloat(y.split(",")[1]));
                $("#unloadedClassCount").val("");
                $("#unloadedClassCount").val(parseFloat(y.split(",")[2]));
                return y;
            }


        });



</script>

</head>
<body>

<div id="container" style="position:absolute;top:5px;width:99%;height:500px"></div>
<div id="container2" style="position:absolute;top:550px;width:99%;font-size:16px;font-weight:bold;">详细信息：
	<div style="position:absolute;left:5%;font-size:14px;font-weight:normal;">

		<table>
			<tr>
				<td nowrap="nowrap" align="right" style="width: 105px;height: 30px;">时间:</td>
				<td>
					<input readonly="readonly" id="time" name="time" value="" style="border: none;" />
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap" align="right" style="width: 105px;height: 30px;">已加装当前类:</td>
				<td>
					<input readonly="readonly" id="loadedClassCount" name="loadedClassCount" value="" style="border: none;" />
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap" align="right" style="width: 105px;height: 30px;">已加载类总数:</td>
				<td>
					<input readonly="readonly" id="totalLoadedClassCount" name="totalLoadedClassCount" value="" style="border: none;"/>
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap" align="right" style="width: 105px;height: 30px;">已卸载类总数:</td>
				<td>
					<input readonly="readonly" id="unloadedClassCount" name="unloadedClassCount" value="" style="border: none;" />
				</td>
			</tr>
		</table>

	</div>
</div>

</body>
</html>

