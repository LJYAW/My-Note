<!--
 * @Author: your name
 * @Date: 2021-09-03 15:20:12
 * @LastEditTime: 2021-10-15 15:56:39
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/create-center/intellectCreate/components/audioSubtitle/contentEdit/contentLeft.vue
-->
<template>
  <div id="contentLeft" ref="contentLeft" class="content-left" @click.stop @scroll="contentLeftScroll($event)">
    <div v-if="contentList.length<=0" class="empty-box">
      <svg-icon icon-class="empty-img" class="empty-img" />
    </div>
    <ul v-else class="subtitle-list">
      <li
        v-for="(item,index) in contentListNew"
        :key="item.id"
        ref="contentLeftLi"
        class="item-list"
        :class="tabIndex===index?'itemActive':''"
        @click="editTitle(item,index);setBtnPosition($event)"
      >

        <div v-if="tabIndex===index" @click.stop="videoPlay(item,index)">
          <svg-icon icon-class="video-pluse" class="video-icon" />
        </div>
        <div v-else class="video-icon" />

        <span class="num">{{ index+1 }}</span>
        <div class="times-box">
          <TimesCounter
            :key="'start'+index"
            :content-list-new="contentListNew"
            :current-times.sync="item.timestamp"
            :index="index.toString()"
            type="start"
            @click.stop.native
          />
          <span class="totimes">-</span>
          <TimesCounter
            :key="'end'+index"
            :content-list-new="contentListNew"
            :current-times.sync="item.endTimestamp"
            :index="index.toString()"
            type="end"
            @click.stop.native
          />
        </div>
        <div class="title" @click.stop="editTitle(item,index)">
          <el-input
            ref="enterInput"
            v-model="item.chinese"
            autosize
            :class="tabIndex!==index?'defaultStyle':''"
            size="mini"
            type="textarea"
            class="resizeNone"
          />
          <el-input
            v-if="displayLangage===3"
            ref="enterInput"
            v-model="item.english"
            :class="tabIndex!==index?'defaultStyle':''"
            size="mini"
            style="margin-top: 5px"
            type="textarea"
            autosize
            class="resizeNone"
          />
        </div>
      </li>
    </ul>

    <BtnPopover v-if="tabIndex>-1 &&popverShow" :position="btnPosition" class="btn-popover-content">
      <div class="poaition-box">
        <div v-for="(k,i) in poaitionList" :key="i" class="tooltip-list" @click.stop="tipClick(k,i)">
          <el-popover
            placement="bottom-start"
            trigger="hover"
            :content="k.label"
            class="placement"
          >
            <svg-icon slot="reference" :icon-class="k.iconClass" class="item-icon" />
          </el-popover>
        </div>
      </div>
    </BtnPopover>
  </div>
</template>

<script>
import TimesCounter from '../components/timesCounter'
import { mapState } from 'vuex'
import guiId from '../../../../utils/guid'
import BtnPopover from './components/BtnPopover/index.vue'
import { Translatebyq } from '../../../../api/videos/index'
import { setLanguage } from './setLanguage'
export default {
  components: {
    TimesCounter,
    BtnPopover
  },
  props: {

  },
  data() {
    return {
      popverShow: false,
      timer: null,
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
      tabIndex: '',
      scrollIndex: '',
      contentList: [],
      winScrillTop: 0,
      btnPosition: {},

      contentListNew: []
    }
  },
  computed: {
    ...mapState('jianshi', ['videoCurrentTimes', 'subTitleSingle', 'subTilteList',
      'translateLanguage', 'language', 'videoDuration', 'displayLangage'])
  },
  watch: {
    // 监听是否进行翻译，否则就隐藏翻译按钮
    translateLanguage: {
      handler(newName, oldName) {
        if (newName === 0 || this.displayLangage !== 3) {
          this.poaitionList.splice(2, 1)
        }
      },
      immediate: true
    },
    // 监听字幕列表 整合数据
    contentList: {
      handler(newName, oldName) {
        this.$bus.$emit('updateContentList', this.contentList[this.tabIndex])
        const list = []
        newName.forEach((item, index) => {
          const obj = {
            endTimestamp: item.endTimestamp,
            timestamp: item.timestamp,
            word: item.chinese + (item.english ? '\n' + item.english : '')
          }
          list.push(obj)
        })
        this.$set(this, 'contentListNew', [...this.contentList])
        this.$store.commit('jianshi/SET_SUBTITLELIST', list)
      },
      deep: true,
      immediate: true
    },
    // 监听单条数据
    subTitleSingle: {
      handler(newName, oldName) {
        if (newName && newName.save_subtitles !== 'null' && newName.id) {
          const subtitles = JSON.parse(newName.save_subtitles)
          this.setTranslateData(subtitles)
        }
      },
      immediate: true
    },
    // 监听视频当前时间
    videoCurrentTimes: {
      handler(newName, oldName) {
        if (newName) {
          this.setVideoTimes(newName)
        }
      },
      immediate: true
    },
    // 监听滚动index
    scrollIndex: {
      handler(newName, oldName) {
        if (newName) {
          const offsetTop = document.getElementsByClassName('item-list')[newName].offsetTop
          const offsetHeight = document.getElementsByClassName('item-list')[newName].offsetHeight

          document.getElementById('contentLeft').scrollTop = offsetTop - offsetHeight
        }
      },
      immediate: true,
      deep: true
    },
    tabIndex() {
      this.setBtnPosition()
    }
  },
  created() {
  },
  mounted() {
    this.$nextTick(() => {
      this.setTextareHeight()
    })
    // 监听整个window滚动
    window.onscroll = () => {
      if (this.tabIndex > -1) {
        this.winScrillTop = document.documentElement.scrollTop || document.body.scrollTop
        const dom = document.getElementsByClassName('item-list')[this.tabIndex]
        if (dom) {
          const { top } = dom.getBoundingClientRect()
          this.$set(this.btnPosition, 'top', top)
        }
      }
    }
  },
  methods: {
    // 文本域初始化自适应
    setTextareHeight() {
      const dom = this.$refs.enterInput

      dom.forEach(item => {
        item.resizeTextarea()
      })
    },
    // 监听 滚动
    contentLeftScroll(e) {
      const scrollTop = e.target.scrollTop
      const parentDom = document.getElementById('contentLeft')
      if (this.tabIndex > 0) {
        const dom = document.getElementsByClassName('item-list')[this.tabIndex]
        let { top } = dom.getBoundingClientRect()
        if (dom.offsetTop - scrollTop < 0 || dom.offsetTop - scrollTop > parentDom.clientHeight - 20) {
          top = -10000
        }
        this.$set(this.btnPosition, 'top', top)
      }
    },
    // 设置按钮租位置
    setBtnPosition() {
      this.popverShow = true
      const dom = document.getElementsByClassName('item-list')[this.tabIndex]
      const { top, right } = dom.getBoundingClientRect()
      this.btnPosition = {
        top: top,
        left: right
      }
    },
    // 控制 video 播放暂停
    videoPlay(item, index) {
      this.tabIndex = index
      this.$bus.$emit('videoPlayHandel', item)
    },
    // 设置视频当前时间范围选中
    setVideoTimes(data) {
      const times = data * 1000
      this.contentList.forEach((item, index) => {
        if (item.timestamp <= times && item.endTimestamp > times) {
          this.tabIndex = index
          this.scrollIndex = index
        }
      })
    },
    // 设置翻译数据（中文英文）
    setTranslateData(data) {
      this.contentList = data.map(item => {
        return {
          english: setLanguage(item.word, this.displayLangage, this.language).english,
          chinese: setLanguage(item.word, this.displayLangage, this.language).chinese,
          word: item.word,
          timestamp: item.timestamp,
          endTimestamp: item.endTimestamp,
          id: 'baseCentent' + guiId() + item.timestamp
        }
      })
      this.$nextTick(() => {
        this.$bus.$emit('setBaseContentList', this.contentList)
      })
    },
    // tip点击事件
    tipClick(k, i) {
      this.setTipFun(this.contentList[this.tabIndex], this.tabIndex, k)
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
          this.translateList(item, index)
          break
        default:
          this.deletesubtitle(item, index)
          break
      }
    },
    // 向上添加
    addUpList(item, index) {
      index === 0
        ? item.timestamp > 0
          ? this.contentList.splice(0, 0, this.setObj(0, item.timestamp))
          : this.$message.error('不能添加了')
        : item.timestamp > this.contentList[index - 1].endTimestamp
          ? this.contentList.splice(index, 0, this.setObj(this.contentList[index - 1].endTimestamp, item.timestamp))
          : this.$message.error('不能添加了')
    },
    // 向上添加或者向下添加设置对象
    setObj(startTimes, endTimes) {
      const obj = {
        timestamp: startTimes,
        endTimestamp: endTimes,
        word: '',
        chinese: '',
        id: 'baseCentent' + guiId(),
        english: ''
      }
      this.$bus.$emit('addSubtitleList', obj)
      return obj
    },
    // 向下添加
    addOverList(item, index) {
      // 模拟视频总时长
      const times = this.videoDuration

      index === this.contentList.length - 1
        ? item.endTimestamp < times
          ? this.contentList.splice(this.contentList.length, 0, this.setObj(item.endTimestamp, times))
          : this.$message.error('不能添加了')
        : item.endTimestamp < this.contentList[index + 1].timestamp
          ? this.contentList.splice(index + 1, 0, this.setObj(item.endTimestamp, this.contentList[index + 1].timestamp))
          : this.$message.error('不能添加了')
    },
    // 翻译
    async translateList(item, index) {
      if (this.translateLanguage !== 0 && this.displayLangage === 3) {
        const { res, err } = await Translatebyq(item.chinese, this.translateLanguage)
        if (err) {
          return
        }
        this.contentList[index].english = res.data
      } else {
        this.$message({
          message: '没有翻译语种！',
          type: 'warning'
        })
      }
    },
    // 删除
    deletesubtitle(item, index) {
      this.$confirm('是否要删除字幕?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.contentList.splice(index, 1)
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
      })
    },
    // 编辑字幕
    editTitle(item, index) {
      this.tabIndex = index
      this.$store.commit('jianshi/SET_VIDEOSTARTTIMES', item.timestamp)
      this.$store.commit('jianshi/SET_VIDEOENDTIMES', item.endTimestamp)
    }
  }
}
</script>

<style  lang="scss">
@import "./scss/index.scss"
</style>
