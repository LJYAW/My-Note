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
<title>内存</title>
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

            var chart;
			$(document).ready(function() {

				Highcharts.setOptions({
					global: {
						useUTC: false
					}
				});
				function activeLastPointToolip(chart) {
					var points = chart.series[0].points;
					chart.tooltip.refresh(points[points.length -1]);
				}

				chart = new Highcharts.Chart({
					chart: {
						renderTo: 'container',
						type: 'spline',
						marginRight: 10,
						events: {
							load: function () {
								var series = this.series[0],
									chart = this;
								activeLastPointToolip(chart);
								setInterval(function () {
									var x = (new Date()).getTime(), // 当前时间
										// y = Math.random();          // 随机值
                                        //y的值通过方法SetYdata()得到
                                        y = parseFloat(SetYdata().split(",")[0]);
                                    var s = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', x);
                                    $("#time").val("");
                                    $("#time").val(s);
                                    $("#useMemory").val("");
                                    $("#useMemory").val(parseFloat(SetYdata().split(",")[2])+" KB");
                                    $("#maxMemory").val("");
                                    $("#maxMemory").val(parseFloat(SetYdata().split(",")[3])+" KB");
                                    $("#commitedMemory").val("");
                                    $("#commitedMemory").val(parseFloat(SetYdata().split(",")[4])+" KB");
                                    series.addPoint([x, y], true, true);
									activeLastPointToolip(chart);
								}, 10000);

							}
						}
					},
					title: {
						text: '堆内存使用量'
					},
					xAxis: {
						type: 'datetime',
						// tickPixelInterval: 150
                        tickInterval: 30*1000		//X轴时间间隔
					},
					yAxis: {
						title: {
							text: "(单位：MB)"
						},
                        plotLines: [{   //一条延伸到整个绘图区的线，标志着轴中一个特定值。
                            color: '#FF4040',
                            dashStyle: 'Dash', //Dash,Dot,Solid,默认Solid
                            width: 1.5,
                            value: 280,  //y轴显示位置e
                            zIndex: 5,
                            label:{
                                text:280,
                                align:'left',
                                x:-34,
                                y:6,
                                style:{color:"#EB3C0A"}
                            }

                        }],
                        max:parseFloat(SetYdata().split(",")[1])+50,    //设置Y轴最大值为“max” 	${max}
                        tickInterval:50     //设置Y轴坐标值的间隔固定为50
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
								Highcharts.numberFormat(this.y, 3);q
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						name: '堆内存',
						data: (function () {
							// 生成随机值
							var data = [],
								time = (new Date()).getTime(),
								i;
							for (i = -10; i <= 0; i += 1) {		//X轴显示的点数
								data.push({
                                    x: time + i * 5000,
									y: parseFloat(SetYdata().split(",")[0])		//parseFloat(SetYdata().split(",")[0])
								});
							}
							return data;
						}())
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

                        y = data.heapused.split("MB")[0]+","+data.heapcommitted.split("MB")[0]+","+
						data.heapusedKB+","+data.heapmaxKB+","+data.heapcommittedKB;

                    }
                }, false);
                var x = (new Date()).getTime(); // 当前时间
                var s = Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', x);
                $("#time").val("");
                $("#time").val(s);
                $("#useMemory").val("");
                $("#useMemory").val(parseFloat(y.split(",")[2])+" KB");
                $("#maxMemory").val("");
                $("#maxMemory").val(parseFloat(y.split(",")[3])+" KB");
                $("#commitedMemory").val("");
                $("#commitedMemory").val(parseFloat(y.split(",")[4])+" KB");
                return y;
            }


        });



</script>

</head>
<body>

<div id="container" style="position:absolute;top:5px;width:99%;height:500px"></div>
<div id="container2" style="position:absolute;top:550px;width:99%;font-size:16px;font-weight:bold;">详细信息：

	<div style="position:absolute;left:5%;width:60%;font-size:14px;font-weight:normal;">

		<table>
			<tr>
				<td nowrap="nowrap" align="right" style="width: 55px;height: 30px;">时间:</td>
				<td>
					<input readonly="readonly" id="time" name="time" value="" style="border: none;" />
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap" align="right" style="width: 55px;height: 30px;">已用:</td>
				<td>
					<input readonly="readonly" id="useMemory" name="useMemory" value="" style="border: none;" />
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap" align="right" style="width: 55px;height: 30px;">已提交:</td>
				<td>
					<input readonly="readonly" id="commitedMemory" name="devIpAddr" value="" style="border: none;"/>
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap" align="right" style="width: 55px;height: 30px;">最大值:</td>
				<td>
					<input readonly="readonly" id="maxMemory" name="maxMemory" value="" style="border: none;" />
				</td>
			</tr>
		</table>

	</div>

	<div style="position:absolute;width:30%;float:right;left:70%">

	</div>

	</div>

</body>
</html>

