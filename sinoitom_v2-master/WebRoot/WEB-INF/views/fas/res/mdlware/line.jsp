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
<title>概览</title>
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
		var num = 0;
        function JMXmonitorLineStartCpu(){
            $.ajax({
                // type: "POST",
                url: '${ctx}/fas/res/mdlware/JMXmonitorLineStartCpu.do?service='+service+'&userName='+userName+'&psWord='+psWord,
                async: false, //表示同步，如果要得到ajax处理完后台数据后的返回值，最好这样设置
                success: function (data) {

                }
            }, false);
        }

        $(function () {

            if(num==0){
                JMXmonitorLineStartCpu();
                num++;
            }

            var intervalTime = 10000;	//间隔时间
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
									series.addPoint([x, y], true, true);
									activeLastPointToolip(chart);
								}, intervalTime);

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
								Highcharts.numberFormat(this.y, 3);
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
							for (i = -10; i <= 0; i += 1) {		//X轴显示的点的个数
								data.push({
                                    x: time + i * 5000,
									y: parseFloat(SetYdata().split(",")[0])		//parseFloat(SetYdata().split(",")[0])
								});
							}
							return data;
						}())
					}]
				});

                chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container2',
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
                                        y = parseFloat(SetYdata().split(",")[2]);
                                    series.addPoint([x, y], true, true);
                                    activeLastPointToolip(chart);
                                }, intervalTime);

                            }
                        }
                    },
                    title: {
                        text: '线程数'
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
                        max:parseFloat(SetYdata().split(",")[3])+10,    //设置Y轴最大值为“max” 	${max}
                        tickInterval:10     //设置Y轴坐标值的间隔固定为50
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
                    series: [{
                        name: '活动线程数',
                        data: (function () {
                            // 生成随机值
                            var data = [],
                                time = (new Date()).getTime(),
                                i;
                            for (i = -10; i <= 0; i += 1) {
                                data.push({
                                    x: time + i * 5000,
                                    y: parseFloat(SetYdata().split(",")[2])
                                });
                            }
                            return data;
                        }())
                    }]
                });

                chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container3',
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
                                        y = parseFloat(SetYdata().split(",")[4]);
                                    series.addPoint([x, y], true, true);
                                    activeLastPointToolip(chart);
                                }, intervalTime);

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
                        max:parseFloat(SetYdata().split(",")[5]),    //设置Y轴最大值为“max”
						min:0,			//parseFloat(SetYdata().split(",")[4])/2
                        tickInterval:3000     //设置Y轴坐标值的间隔固定为50
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
                    series: [{
                        name: '已加载类数',
                        data: (function () {
                            // 生成随机值
                            var data = [],
                                time = (new Date()).getTime(),
                                i;
                            for (i = -10; i <= 0; i += 1) {
                                data.push({
                                    x: time + i * 5000,
                                    y: parseFloat(SetYdata().split(",")[4])
                                });
                            }
                            return data;
                        }())
                    }]
                });

                chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container4',
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
                                        y = parseFloat(SetYdata4());
                                    series.addPoint([x, y], true, true);
                                    activeLastPointToolip(chart);
                                }, intervalTime);

                            }
                        }
                    },
                    title: {
                        text: 'CPU占用率'
                    },
                    xAxis: {
                        type: 'datetime',
                        // tickPixelInterval: 150
                        tickInterval: 30*1000
                    },
                    yAxis: {
                        title: {
                            text: "(单位：%)"
                        },
                        max:20,    //设置Y轴最大值为“max” 	${max}
                        tickInterval:5     //设置Y轴坐标值的间隔固定为50
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
                    series: [{
                        name: 'CPU占用率',
                        data: (function () {
                            // 生成随机值
                            var data = [],
                                time = (new Date()).getTime(),
                                i;
                            for (i = -10; i <= 0; i += 1) {
                                data.push({
                                    x: time + i * 5000,
                                    y: 0
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
                            data.threadCount+","+data.peakThreadCount+","+
							data.loadedClassCount+","+data.totalLoadedClassCount;

                    }
                }, false);
                return y;
            }


            function SetYdata4(){
                var y = null;
                //通过轮询或者广播的方式获取y的实时的值
                $.ajax({
                    // type: "POST",
                    url: "${ctx}/fas/res/mdlware/JMXmonitorLineViewCPU.do?service="+service+"&intervalTime="+intervalTime+'&userName='+userName+'&psWord='+psWord,
                    async: false, //表示同步，如果要得到ajax处理完后台数据后的返回值，最好这样设置
                    success: function (data) {

                        y = data.cpu;

                    }
                }, false);
                return y;
            }


        });



</script>

</head>
<body>

<%--<div id="center" style="top:2px;height:20px">--%>
	<%--<div style="position:absolute;left:35%;font-size:14px;font-weight:normal;">--%>

		<%--<table>--%>
			<%--<tr>--%>
				<%--<td align="right">间隔时间：</td>--%>
				<%--<td>--%>
					<%--<select name="historyLineTime" id="historyLineTime" style="width:204px;">--%>
						<%--<option value="20Second" selected="selected">20秒</option>--%>
						<%--<option value="1Min">1分钟</option>--%>
						<%--<option value="10Min">10分钟</option>--%>
						<%--<option value="30Min">30分钟</option>--%>
						<%--<option value="1Hours" >1小时</option>--%>
					<%--</select>--%>

				<%--</td>--%>
				<%--<td></td>--%>

				<%--<td></td>--%>
				<%--<td></td>--%>
				<%--<td></td>--%>
			<%--</tr>--%>
		<%--</table>--%>

	<%--</div>--%>
<%--</div>--%>

<div id="container" style="position:absolute;top:30px;left:0px;width:49%;height:350px"></div>
<div id="container2" style="position:absolute;top:30px;left:50%;width:49%;height:350px;float:right"></div>
<div id="container3" style="position:absolute;top:370px;left:0px;width:49%;height:350px"></div>
<div id="container4" style="position:absolute;top:370px;left:50%;width:49%;height:350px"></div>

</body>
</html>

