<!--
 * @Author: your name
 * @Date: 2021-09-03 15:20:12
 * @LastEditTime: 2021-09-14 18:23:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/create-center/intellectCreate/components/audioSubtitle/contentEdit/contentLeft.vue
-->
<template>
  <div class="content-left">
    <ul v-if="!statusLoading" class="subtitle-list">
      <li v-for="(item,index) in contentList" :key="index" class="item-list">

        <svg-icon :icon-class="tabIndex===index?'video-play':'video-pluse'" class="video-icon" />
        <span class="num">{{ index+1 }}</span>
        <div class="times-box">

          <TimeScaleInput
            :key="index"
            :cur-ms="timeline[index].start.curMs"
            :max-ms="timeline[index].start.maxMs"
            :min-ms="timeline[index].start.minMs"
            @timeHandel="updataTimeLineList(arguments, index, 'start')"
          />
          <span class="totimes">-</span>
          <TimeScaleInput
            :cur-ms="timeline[index].end.curMs"
            :max-ms="timeline[index].end.maxMs"
            :min-ms="timeline[index].end.minMs"
            @timeHandel="updataTimeLineList(arguments, index, 'end')"
          />

        </div>
        <div class="title" @click.stop="editTitle(item,index)">
          <el-input v-model="item.word" :class="tabIndex!==index?'defaultStyle':''" size="mini" @blur="blur()" />
        </div>
        <ul v-if="tabIndex===index" class="poaition-box">
          <li v-for="(k,i) in poaitionList" :key="i" @click="tipClick(item,index,k,i)">
            <el-tooltip :key="i" class="item" effect="light" :content="k.label" placement="bottom-start">
              <svg-icon :icon-class="k.iconClass" class="item-icon" />
            </el-tooltip>
          </li>
        </ul>
      </li>
    </ul>
  </div>
</template>

<script>
import { setTotimeStamp } from '../js/setTimes'
import TimeScaleInput from '../components/TimeScaleInput'
import { mapState } from 'vuex'
import guiId from '../../../../utils/guid'

export default {
  components: {
    TimeScaleInput
  },
  props: {

  },
  data() {
    return {
      seekMs: 100,
      timeline: [],
      statusLoading: true,
      poaitionList: [
        {
          label: '在上方添加字幕',
          iconClass: 'addoversubtitle'
        },
        {
          label: '在下方添加字幕',
          iconClass: 'addupsubtitle'
        },
        {
          label: '翻译',
          iconClass: 'translate'
        },
        {
          label: '删除',
          iconClass: 'deletesubtitle'
        }
      ],
      tabIndex: '12',
      contentList: []
    }
  },
  computed: {
    ...mapState('jianshi', [])
  },
  watch: {

  },
  created() {
    this.getInitData()
  },
  mounted() {

  },
  methods: {
    // 控制 时间的 最大值 最小值
    updataTimeLineList(data, index, key) {
      const curMs = data[0]

      if (key === 'end') {
        // 如果是结束时间 重置当前的开始时间最大值  重置下一个开始时间的最小值
        this.$set(this.timeline[index]['start'], 'maxMs', curMs - this.seekMs)
        this.timeline[index + 1] && this.$set(this.timeline[index + 1]['start'], 'minMs', curMs)
      } else {
        // 如果是开始时间 重置上一个结束时间的最大值 重置结束时间的最小值
        this.timeline[index - 1] && this.$set(this.timeline[index - 1]['end'], 'maxMs', curMs)
        this.$set(this.timeline[index]['end'], 'minMs', curMs + this.seekMs)
      }
    },
    // 计算基础的 时间线的数据
    setTimeLine(data) {
      this.timeline = []

      for (let i = 0; i < data.length; i++) {
        const item = data[i]
        const pre = data[i - 1]
        const next = data[i + 1]
        const seekMs = this.seekMs
        const maxMs = 10000

        const start = {
          minMs: pre ? pre.endTimestamp - seekMs : 0,
          maxMs: next ? next.timestamp - seekMs : maxMs,
          curMs: item.timestamp,
          id: guiId()
        }

        const end = {
          minMs: start.minMs + seekMs,
          maxMs: next ? next.timestamp : maxMs,
          curMs: item.endTimestamp,
          id: guiId()
        }

        this.timeline.push({
          start,
          end
        })
      }
    },
    async getInitData() {
      this.statusLoading = true
      const { res, err } = await this.$get('https://tjzm.tmsx.net/v1/videos/3')
      const data = res.data

      const subtitles = JSON.parse(data.subtitles)
      this.contentList = subtitles

      this.setTimeLine(this.contentList)
      this.statusLoading = false
    },
    // 鼠标失去焦点事件
    blur() {
      this.tabIndex = ''
    },
    // tip点击事件
    tipClick(item, index, k, i) {
      this.setTipFun(item, index, k)
    },
    // 设置点击事件
    setTipFun(item, index, k) {
      switch (k.label) {
        case '在上方添加字幕':
          this.addUpList(item, index)
          break
        case '在下方添加字幕':
          this.addOverList(item, index)
          break
        case '翻译':
          this.translateList(item)
          break
        default:
          this.deletesubtitle(item, index)
          break
      }
    },
    // 向上添加
    addUpList(item, index) {
      if (index === 0) {
        setTotimeStamp(item.startTimes) > 0 ? this.contentList.splice(0, 0, this.setObj('00:00.000', item.startTimes)) : this.$message.error('不能添加了')
      } else {
        setTotimeStamp(item.startTimes) > setTotimeStamp(this.contentList[index - 1].endTimes)
          ? this.contentList.splice(index, 0, this.setObj(this.contentList[index - 1].endTimes, item.startTimes)) : this.$message.error('不能添加了')
      }
    },
    // 设置对象
    setObj(startTimes, endTimes) {
      return {
        startTimes: startTimes,
        endTimes: endTimes,
        title: '',
        playFlow: false,
        tooltipFllow: false
      }
    },
    // 向下添加
    addOverList(item, index) {
      // 模拟视频总时长
      const times = '00:18.200'
      if (index === this.contentList.length - 1) {
        setTotimeStamp(item.endTimes) < setTotimeStamp(times) ? this.contentList.splice(this.contentList.length, 0, this.setObj(item.endTimes, times)) : this.$message.error('不能添加了')
      } else {
        setTotimeStamp(item.endTimes) < setTotimeStamp(this.contentList[index + 1].startTimes)
          ? this.contentList.splice(index + 1, 0, this.setObj(item.endTimes, this.contentList[index + 1].startTimes)) : this.$message.error('不能添加了')
      }
    },
    // 翻译
    translateList(item, index) {},
    // 删除
    deletesubtitle(item, index) {},
    // 编辑字幕
    editTitle(item, index) {
      this.tabIndex = index
      item.tooltipFllow = true
      item.playFlow = !item.playFlow
    }
  }
}
</script>

<style  lang="scss">
.content-left {
  flex: 1;
  // height: 344px;
  background: #f7f8f9;
  border-radius: 4px;
  padding: 15px;
  // overflow-y: auto;

  .subtitle-list {

    .item-list {
      position: relative;
      display: flex;
      margin-top: 16px;
      font-size: 14px;
      font-weight: 400;
      color: #404040;

      .num {
        width: 30px;
        margin-right: 20px;
      }

      .video-icon {
        width: 14px;
        height: 16px;
        margin-right: 20px;
      }

      .times-box {
        margin-right: 10px;
        display: flex;
        align-items: center;

        .totimes {
          margin: 0 5px 0 5px;
        }
      }

      .title {
        flex: 1;
        cursor: pointer;
      }

      .defaultStyle {
        flex: 1;

        input {
          background: transparent;
          border-color: transparent;
        }
      }

      .poaition-box {
        position: absolute;
        right: -96px;
        top: 0;
        z-index: 100;
        display: flex;
        background: #fff;
        padding: 6px;
        border-radius: 5px;

        li {
          margin-right: 10px;
          cursor: pointer;

          .item-icon {
            width: 12px;
            height: 15px;
          }

          &:last-child {
            margin-right: 0;
          }
        }
      }
    }
  }
}
</style>
