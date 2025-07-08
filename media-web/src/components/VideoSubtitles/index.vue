<!--
 * @Author: your name
 * @Date: 2021-07-30 11:55:00
 * @LastEditTime: 2021-09-22 20:13:58
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/components/TimeLine/index.vue
-->
<template>
  <div ref="timeLineWrap" :class="updown?'updown':''" class="time-line-wrap">
    <div :class="viewOpen?'':'close'" class="time-line">
      <div
        v-for="(v,index) in timeLineData"
        ref="timeItem"
        :key="v.time"
        :class="['time-item',activeTimeLine == index?'active':'']"
        @click="timeLineClick(v.timestamp)"
      >
        <div class="time">
          {{ v.timestamp |timesToHMS }}
          <slot name="type" :type="v.type" />
        </div>
        <div class="content" v-html="highLight(v.word,searchWord)" />
      </div>
    </div>
    <div v-if="updown" :class="viewOpen?'open':''" class="control" @click="viewOpen = !viewOpen">
      {{ viewOpen?'收起':'展开' }}
    </div>
  </div>
</template>

<script>
import { highLightMsg } from '@/utils/hightLight.js'

export default {
  components: {
  },
  props: {
    timeLineData: {
      type: Array,
      default: () => []
    },
    searchWord: {
      type: String,
      default: ''
    },
    updown: {
      type: Boolean,
      default: false
    },
    time: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      activeTimeLine: null,
      viewOpen: false
    }
  },
  computed: {
    currentTime() {
      return this.$store.state.videoStatus.videoTime
    }
  },
  watch: {
    time: {
      handler: function(s) {
        this.setTime(s)
      },
      immediate: true
    },
    currentTime: {
      handler: function(s) {
        this.setTime(s)
      },
      immediate: true
    // 用于timeline滑动
    },
    activeTimeLine: {
      handler: function(index) {
        this.$nextTick(() => {
          var height = 0
          for (let i = 0; i < index; i++) {
            height += this.$refs.timeItem[i].offsetHeight
          }
          this.$refs.timeLineWrap.scrollTo({
            top: height,
            behavior: 'smooth'
          })
        })
      },
      immediate: true
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    // 搜索高亮
    highLight(item, highLight) {
      return highLightMsg(item, highLight)
    },
    // 时间线点击
    timeLineClick(time) {
      this.$emit('timeLineHandel', time / 1000)
    },
    // 视频时间
    setTime(s) {
      const time = this.time ? this.time : s
      try {
        this.timeLineData.forEach((element, index) => {
          if (parseInt(element.timestamp / 1000) === parseInt(time)) {
            this.activeTimeLine = index
            throw Error()// 用于跳出循环
          } else {
            // this.activeTimeLine = index
          }
        })
      } catch (e) {
        return
      }
    }
  }
}
</script>

<style scoped lang="scss">
.time-line-wrap {
  max-height: 57vh;
  // height: 644px;
  overflow: scroll;
  position: relative;

  &.updown {
    padding-right: 45px;

    .time-line {

      &.close {
        height: 18px;
        overflow: hidden;
      }
    }
  }
}

.time-line {
  height: 100%;

  .time-item {
    padding-left: 20px;
    padding-bottom: 17px;
    position: relative;
    cursor: pointer;
    line-height: 1.5;

    &.active {

      .type {
        background: rgba(8,153,73,.2);

      }

      &::before {
        background: #5675e8;
      }

      .time ,
      .content {
        color: #5675e8;
      }
    }

    &::before {
      content: "";
      position: absolute;
      left: 0px;
      top: 5px;
      width: 5px;
      height: 5px;
      background: #404040;
      border-radius: 50%;
      z-index: 1;
    }

    &::after {
      content: "";
      position: absolute;
      left: 2px;
      top: 5px;
      width: 1px;
      height: 100%;
      background: #d8d8d8;
      opacity: .8;
    }

    &:last-child {
      padding-bottom: 0;

      &::after {
        display: none;
      }
    }

    .time {
      font-size: 12px;
      color: #404040;
      opacity: .6;
      float: left;
      margin-right: 10px;
    }

    .content {
      font-size: 14px;
      color: #404040;
    }
  }
}

.control {
  position: absolute;
  font-size: 12px;
  right: 0px;
  bottom: 1px;
  color: #404040;
  padding-right: 12px;
  cursor: pointer;

  &:after {
    position: absolute;
    content: "";
    top: 6px;
    right: 0;
    width: 0;
    height: 0;
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-top: 6px solid rgba(64, 64, 64, .6);
  }

  &.open:after {
    transform: rotate(180deg);
  }
}
</style>
