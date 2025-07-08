<!--
 * @Author: your name
 * @Date: 2021-09-09 17:34:59
 * @LastEditTime: 2021-10-15 15:58:52
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/subtitleCreation/components/timesCounter.vue
-->
<template>
  <div class="times-counter" :class="timesIndex===Number(index)?'asctiveClass':'defaultStyle'" @click="editTimes()">
    <div class="times-counter-box times-counter">
      <i class="el-icon-caret-left icon-caret icon-caret-left" @mousedown="handel(true)" />
      <el-input ref="timesInput" v-model="TempCurrentTimes" size="mini" class="times-counter" />
      <i class="el-icon-caret-right icon-caret icon-caret-right" @mousedown.self="handel(false)" />
    </div>
  </div>
</template>

<script>
import toTimes from '../js/ms-to-second'
import { msToSecondtamp } from '../js/setTimes'
import { mapState } from 'vuex'

var SEEMS = 100
export default {
  components: {
  },
  props: {
    currentTimes: {
      type: Number,
      default: 0
    },
    contentListNew: {
      type: Array,
      default: () => []
    },
    index: {
      type: String,
      default: ''
    },
    type: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      TempCurrentTimes: '',
      loading: false,
      timesIndex: ''
    }
  },
  computed: {
    ...mapState('jianshi', ['videoDuration']),
    timesDuration() {
      return this.videoDuration * 1000
    }
  },
  watch: {
    currentTimes: {
      handler(newName, oldName) {
        // 毫秒数转换成（00:00.000）
        this.TempCurrentTimes = toTimes(newName)
      },
      immediate: true
    }
  },
  created() {

  },
  mounted() {
    this.$bus.$on('contentEditDown', (e) => {
      this.deselect(e)
    })
  },
  methods: {
    // 时间验证
    timeCheak() {
      var timeRegex = '([0-5][0-9]):([0-5][0-9]).([0-9]{1,3})$'
      var Regex = new RegExp(timeRegex)

      if (!Regex.test(this.TempCurrentTimes)) {
        this.$message({
          message: '请输入正确的时间格式！',
          type: 'warning'
        })
        this.TempCurrentTimes = toTimes(this.currentTimes)
        return
      } else {
        if (this.setTimesCheckData()) {
          this.$emit('update:currentTimes', msToSecondtamp(this.TempCurrentTimes))
        } else {
          return
        }
      }
    },
    // 验证是否符合时间范围
    setTimesCheckData() {
      const duration = this.timesDuration; const element = this.contentListNew; const index = Number(this.index)
      const times = msToSecondtamp(this.TempCurrentTimes)
      // 判断范围
      // console.log(element[index - 1], '判断条件', times > element[index].endTimestamp, '当前时间', times, '当前结束时间', element[index].endTimestamp, 'index', index)
      // console.log(element[index + 1], '判断条件', element[index + 1].timestamp < times, '当前时间', times, '当前结束时间', element[index].timestamp, 'index', index)
      times < 0
        ? this.checkAlert()
        : times > duration && duration
          ? this.checkAlert()
          : this.type === 'start' && element[index - 1] && (element[index - 1].endTimestamp > times || times > element[index].endTimestamp)
            ? this.checkAlert()
            : this.type === 'end' && element[index + 1] && (element[index + 1].timestamp < times || times < element[index].timestamp)
              ? this.checkAlert()
              : ''
      return true
    },
    // 提示语
    checkAlert() {
      this.$message({
        message: '请输入有效的时间区间！',
        type: 'warning'
      })
      this.TempCurrentTimes = toTimes(this.currentTimes)
      return false
    },
    // 鼠标失去焦点
    deselect(e) {
      const target = e.target || e.srcElement
      if (typeof target.parentNode.className === 'string') {
        const classNameHandle = target.parentNode.className.indexOf('times-counter') > -1
        if (!classNameHandle) {
          this.timesIndex = ''
          this.timeCheak()
        }
      }
    },

    // 点击编辑时间
    editTimes() {
      this.timesIndex = Number(this.index)
    },
    // 左右切换添加时间
    handel(val) {
      this.setTimesCounters(val)
    },
    // 设置判断时间数据
    setTimesCounters(val) {
      const element = this.contentListNew; const duration = this.timesDuration
      let times = ''; const index = Number(this.index)

      val
        ? times = this.type === 'start'
          ? this.radeceTimes(index === 0 ? 0 : element[index - 1].endTimestamp)
          : this.radeceTimes(element[index].timestamp + SEEMS)
        : times = this.type === 'end'
          ? this.addTimes(index === element.length - 1 ? duration
            : element[index + 1].timestamp)
          : this.addTimes(element[index].endTimestamp - SEEMS)

      this.$emit('update:currentTimes', times)
    },
    // 添加时间
    addTimes(min) {
      return Math.min(this.currentTimes + SEEMS, min)
    },
    // 减少时间
    radeceTimes(max) {
      return Math.max(this.currentTimes - SEEMS, max)
    }
  }
}
</script>

<style scoped lang="scss">
.defaultStyle {
  background: transparent;

  .icon-caret {
    display: none;
  }
}

.asctiveClass {
  background: #fff !important;
}

.times-counter {
  border-radius: 2px;

  .times-counter-box {
    display: flex;
    align-items: center;

    .icon-caret {
      font-size: 12px;
      cursor: pointer;
      color: rgba(64, 64, 64, 1);
    }

    .icon-caret-left {
      height: 24px;
      line-height: 24px;
      border-right: 1px solid rgba(239, 239, 239, 1);
    }

    .icon-caret-right {
      height: 24px;
      line-height: 24px;
      border-left: 1px solid rgba(239, 239, 239, 1);
    }

    ::v-deep .el-input__inner {
      width: 62px;
      height: 24px;
      padding: 3px;
      border-color: transparent;
      background: transparent;
    }
  }
}
</style>
