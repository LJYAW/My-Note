<!-- 线形图 -->
<template>
  <div id='myChart_line'></div>
</template>

<script>
// 引入基本模板
let echarts = require('echarts/lib/echarts')
// 引入柱状图组件
require('echarts/lib/chart/bar')
// 引入线状图组件
require('echarts/lib/chart/line')
// 引入提示框和title组件
require('echarts/lib/component/tooltip')
require('echarts/lib/component/title')
// 引入legend模块
require('echarts/lib/component/legend')
export default {
  props: {
    //xAxis_data
    xAxisData: {
      type: Array,
    },
    //series_Data
    seriesData: {
      type: Array,
    },
    seriesData1: {
      type: Array,
    },
  },
  data() {
    return {
      resizefun: null,
    }
  },

  computed: {},

  methods: {
    drawLine() {
      this.$nextTick(() => {
        // 基于准备好的dom，初始化echarts实例
        let myChartLine = echarts.init(document.getElementById('myChart_line'))
        myChartLine.showLoading()
        setTimeout(() => {
          myChartLine.hideLoading()
          // 绘制图表
          myChartLine.setOption({
            tooltip: {
              trigger: 'axis',
              // formatter: function (params, ticket, callback) {
              //   return params[0].value
              // },
              // axisPointer: {
              //   type: 'none'
              // },
              backgroundColor: '#3485e1',
            },
            // 铺满整个div
            grid: {
              left: 50,
              top: 25,
              right: 0,
            },
            legend: {
              data: ['生产视频数', '使用用户数'],
              show: true,
              bottom: 'bottom',
            },
            xAxis: {
              type: 'category',
              data: this.xAxisData,
              axisLine: {
                //x轴
                show: false,
              },
              axisTick: {
                //刻度线
                show: false,
              },
            },
            yAxis: {
              type: 'value',
              axisLine: {
                //y轴
                show: true,
              },
              axisTick: {
                //刻度线
                show: true,
              },
              splitLine: {
                // 网格线
                show: true,
                lineStyle: {
                  type: 'dashed',
                },
              },
              axisLabel: {
                show: true, //这行代码控制着坐标轴x轴的文字是否显示
              },
            },
            dataZoom: [
              {
                type: 'slider',
                show: true,
                xAxisIndex: [0],
                left: '',
                bottom: -5,
                start: 0,
                end: 100, //初始化滚动条
              },
            ],
            series: [
              {
                name: '生产视频数',
                data: this.seriesData,
                type: 'line',
                itemStyle: {
                  normal: {
                    color: '#5691F4',
                    label: { show: true }, //是否在折点上显示value
                    lineStyle: {
                      color: '#5691F4',
                    },
                  },
                },
              },
              {
                name: '使用用户数',
                data: this.seriesData1,
                type: 'line',
                itemStyle: {
                  normal: {
                    color: '#12731d',
                    label: { show: true }, //是否在折点上显示value
                    lineStyle: {
                      color: '#12731d',
                    },
                  },
                },
              },
            ],
          })
          window.onresize = function () {
            myChartLine.resize()
          }
        }, 1000)
      })
    },
  },

  mounted() {
    this.drawLine()
    //实现自适应
    this.resizefun = () => {
      echarts.init(document.getElementById('myChart_line')).resize() //这里的myChart就是要自适应的图表容器Id
    }
    window.addEventListener('resize', this.resizefun)
  },
  //移除事件监听，避免内存泄漏
  beforeDestroy() {
    window.removeEventListener('resize', this.resizefun)
    this.resizefun = null
  },
  watch: {},
  created() {},
}
</script>
<style lang='scss' scoped>
#myChart_line {
  width: 100%;
  height: 300px;
}
</style>