<!--
 * @Author: your name
 * @Date: 2021-03-03 14:30:54
 * @LastEditTime: 2021-11-23 16:55:00
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/model/ApprovedDetail.vue
-->
<template>
  <div class="content mt-10px d-flex">
    <div class="row-video">
      <div class="row-video-content">
        <div class="border-shadow video-wrap">
          <!-- 视频 -->
          <VideoHls
            v-if="!status_loading"
            ref="videoPlay"
            class="videoPaly"
            :video-offset-ms="videoOffsetMs"
            :player-options="playerOptions"
            cross-origin="anonymous"
          />
          <p class="cur-time">视频当前时间:<span>{{ msToSecond(current_time_ms) }}</span></p>
          <p class="cur-time">
            <base-btn size="mini" class="start-btn" @click="setTime('start')">标开始点</base-btn>
            <base-btn size="mini" class="end-btn" @click="setTime('end')">标结束点</base-btn>
          </p>

        </div>
        <div class="d-flex row-btn">
          <base-btn
            v-if="propData.type!=='非广告'"
            size="mini"
            :class="activeIndex===1&&'activeClass'"
            :disabled="propData.type=='非广告'"
            @click="activeIndex=1"
          >修改本条</base-btn>
          <base-btn
            v-if="propData.type!=='广告'"
            size="mini"
            :class="activeIndex===2&&'activeClass'"
            :disabled="propData.type=='广告'"
            @click="activeIndex=2"
          >插入广告</base-btn>
        </div>
      </div>

    </div>
    <!-- 当前数据信息 -->
    <div class="border-shadow row-form">
      <el-form
        ref="ruleForm"
        :inline="true"
        :rules="rules"
        :model="form"
        class=""
        label-width="90px"
        label-position="right"
      >
        <!-- <div class="border-right d-flex"> -->
        <!-- <div> -->
        <el-form-item label="标题名称:" prop="title">
          <el-autocomplete
            v-model="form.title"
            :fetch-suggestions="querySearchAsync"
            placeholder="请输入标题名称"
            type="textarea"
          />
        </el-form-item>
        <el-form-item label="分类:">
          <el-checkbox-group v-model="form.tags">
            <el-checkbox v-for="item in categories" :key="item" :label="item" />
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="开始时间:" class="formTime">
          <el-input
            v-for="item in timeArray"
            :key="item"
            v-model.number.trim="form['start'+item]"
            type="number"
            size="mini"
          />
        </el-form-item>
        <el-form-item label="结束时间:" class="formTime">
          <el-input
            v-for="item in timeArray"
            :key="item"
            v-model.number.trim="form['end'+item]"
            type="number"
            size="mini"
          />
        </el-form-item>
        <el-form-item class="dialog-footer">
          <base-btn @click="close">关闭</base-btn>
          <base-btn @click="submitForm('ruleForm')">提交</base-btn>
        </el-form-item>
        <!-- </div> -->
        <!-- </div> -->
        <!-- <div class="d-flex"> -->
        <!-- <div> -->
        <!-- <el-form-item label="开始时间:" class="formTime">
            <el-input
              v-for="item in timeArray"
              :key="item"
              v-model.number.trim="form['start'+item]"
              type="number"
              size="mini"
            />
          </el-form-item>
          <el-form-item label="结束时间:" class="formTime">
            <el-input
              v-for="item in timeArray"
              :key="item"
              v-model.number.trim="form['end'+item]"
              type="number"
              size="mini"
            />
          </el-form-item>
          <el-form-item class="dialog-footer">
            <base-btn @click="close">关闭</base-btn>
            <base-btn @click="submitForm('ruleForm')">提交</base-btn>
          </el-form-item> -->
        <!-- </div> -->
        <!-- </div> -->
      </el-form>
    </div>
  </div>
</template>

<script>
import VideoHls from '@/components/VideoHlsCut/VideoHls.vue'
import dateFun from '@/utils/time.js'

export default {
  components: {
    VideoHls
  },
  props: {
    propData: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      playerOptions: {
        width: '100%',
        autoplay: true,
        muted: false,
        language: 'en',
        playbackRates: [0.7, 1.0, 1.5, 2.0],
        sources: [{
          type: 'application/x-mpegURL',
          src: ''
        }],
        src: '',
        poster: 'https://surmon-china.github.io/vue-quill-editor/static/images/surmon-1.jpg',
        fluid: true
      },
      videoOffsetMs: {
        start_ms: 1000,
        end_ms: 3000
      },
      activeIndex: 1,
      form: {
        start_time: '',
        startHour: '',
        startMinute: '',
        startSecond: '',
        startMs: '',
        endHour: '',
        endMinute: '',
        endSecond: '',
        endMs: '',
        end_time: '',
        title: '',
        sample_id: null,
        tags: []
      },
      titleArr: [],
      categories: [],
      defaultCate: ['公益广告', '商业广告', '购物广告', '植入广告', '赞助广告'],
      rules: {
        title: [{ required: true, message: '请输入标题名称', trigger: 'blur' }]
      },
      timeArray: ['Hour', 'Minute', 'Second', 'Ms'],
      itemList: [],
      date: '',
      start_time_dvalue: null,
      status_loading: true,
      timeout: null
    }
  },
  computed: {
    current_time_ms() {
      return this.$store.state.videoPlay.current_time_ms
    },
    isDisabled() {
      const obj = this.propData
      if ((obj && obj.type === '广告' && this.activeIndex === 1) || this.activeIndex === 2) {
        return true
      } else {
        return false
      }
    }
  },
  watch: {
    activeIndex(val) {
      if (val !== 1) {
        this.form.title = ''
        this.timeArray.forEach((item, index) => {
          this.form['start' + item] = ''
          this.form['end' + item] = ''
          this.form.tags = []
        })
      }
    }
  },
  async created() {
    this.getItemList()
    this.getCustomCategory()
    const titleList = await this.getTitleList(this.propData.title)
    this.getShowTitle(titleList)

    if (this.propData.type === '非广告') {
      this.activeIndex = 2
    }
    const startTime = dateFun.convert(this.propData.start_time, 'y-m-d h:m:s')
    this.setUpdateTime(startTime, 'start')
    const endTime = dateFun.convert(this.propData.end_time, 'y-m-d h:m:s')
    this.setUpdateTime(endTime, 'end')
    this.setVideoProgress()
  },
  mounted() {
    this.status_loading = true
    this.$bus.$on('getChannelId', id => {
      this.getTimeDifference()
    })
  },
  beforeDestroy() {
    // 销毁监听事件
    this.$bus.$off('getChannelId')
  },
  methods: {
    // 标题联想功能
    async querySearchAsync(queryString, cb) {
      const titleList = await this.getTitleList(queryString)
      this.getShowTitle(titleList)
      cb(this.titleArr)
    },
    getShowTitle(arr) {
      this.titleArr = []
      arr.forEach(item => {
        this.titleArr.push({
          id: item.id,
          sample_id: item.sample_id,
          value: item.title
        })
      })
    },
    // 获取标题列表
    getTitleList(val) {
      const params = {
        channel_id: this.propData.channel_id,
        keyword: val,
        is_sample: 1
      }
      return new Promise((resolve, reject) => {
        this.$get('/epg-ad/epg-ad-search', params).then((res) => {
          resolve(res.data.list)
        }).catch((error) => {
          this.$message(error.msg)
        })
      })
    },
    // 获取m3u8地址时间差值
    getTimeDifference() {
      const params = {
        channel_en_name: this.propData.channel_en_name,
        start_time: this.propData.start_time - 10000,
        end_time: this.propData.end_time + 10000
      }
      this.$get('/epg/m3u8-dvalue', params).then((res) => {
        const data = res.data
        this.videoOffsetMs.start_ms = data.start_time_dvalue * 1000
        this.videoOffsetMs.end_ms = this.propData.end_time - this.propData.start_time + data.start_time_dvalue * 1000
        // this.videoOffsetMs.start_ms = data.start_time_dvalue * 1000 + 10000
        // this.videoOffsetMs.end_ms = this.videoOffsetMs.start_ms + this.propData.duration
        this.setFormData()
        this.status_loading = false
      }).catch((error) => {
        console.log(error)
      })
    },
    // 获取当前播放时间(绝对)
    msToSecond(s) {
      const mstime = String(s).split('.')[1] || 0
      const time = this.propData.start_time + s - this.start_time_dvalue * 1000
      const testTime = dateFun.convert(time, 'yyyy-MM-dd hh:mm:ss') + '.' + (mstime + '000').slice(0, 3)
      this.date = testTime.split(' ')[0]
      return testTime
    },
    // 标记开始或结束时间
    setTime(type) {
      const newTime = this.msToSecond(this.current_time_ms)
      this.setUpdateTime(newTime, type)
    },
    // 回显开始或结束时间
    setUpdateTime(newTime, type) {
      this.$data[type + 'Date'] = newTime.split(' ')[0]
      const timeArray = newTime.split(' ')[1]
      const array = timeArray.split(':')
      this.timeArray.forEach((item, index) => {
        this.form[type + item] = array[index]
        if (index === 2 || index === 3) {
          this.form[type + item] = array[2].split('.')[index - 2] || this.propData[type + '_time'] % 1000
        }
      })
    },
    // 获取客户定制分类
    getCustomCategory() {
      this.$get('/epg-task/customer-category-list').then((res) => {
        this.firstCustomCategory = res.data.first_category
        this.secondCustomCategory = res.data.second_category
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    // 回显表单数据
    setFormData() {
      this.form.title = this.propData.title
      this.categories = this.propData.order_tags ? this.propData.order_tags.split(',') : this.defaultCate
      this.form.tags = this.propData.tags && this.propData.tags.length > 0 ? this.propData.tags.split(',') : []
      this.playerOptions.sources[0].src = this.propData.m3u8_vod_url
      this.playerOptions.src = this.propData.m3u8_vod_url
    },
    // 获取栏目列表
    getItemList() {
      this.$get('/production/itemList').then((res) => {
        this.itemList = res.data
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    close() {
      this.$emit('close')
    },
    getTime(type) {
      let str = this.date + ' '
      let sign = ':'
      this.timeArray.forEach((item, index) => {
        if (index === 2) {
          sign = '.'
        }
        if (index === 3) {
          sign = ''
        }
        str = str + this.form[type + item] + sign
      })
      return new Date(str).getTime()
    },
    // 校验时间
    checkTime() {
      if (!this.getTime('start')) {
        this.$message('请输入开始时间')
        return false
      }
      if (!this.getTime('end')) {
        this.$message('请输入结束时间')
        return false
      }
      if (this.getTime('start') < this.propData.start_time) {
        this.$message('开始时间不能小于片段开始时间')
        return false
      }
      if (this.getTime('end') > this.propData.end_time) {
        this.$message('结束时间不能大于片段结束时间')
        return false
      }
      if (this.getTime('end') < this.getTime('start')) {
        this.$message('开始时间不能大于结束时间')
        return false
      }
      return true
    },
    // 提交
    submitForm(formName) {
      if (!this.checkTime()) {
        return
      }
      const params = {
        data_id: this.propData.id,
        action: this.activeIndex === 1 ? 'update' : 'insert',
        seq: {
          title: this.form.title,
          start_time: this.getTime('start'),
          end_time: this.getTime('end'),
          sample_id: this.getSampleId(),
          type: this.activeIndex === 1 ? this.propData.type : '广告',
          tags: this.form.tags.length > 0 ? this.form.tags.join(',') : ''
        }
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$post('/epg-ad/modify', params).then((res) => {
            this.$message({
              type: 'success',
              message: '提交成功'
            })
            this.$emit('submit')
            this.close()
          }).catch((error) => {
            this.$message.error(error.msg)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    getSampleId() {
      const obj = this.titleArr.find((item) => item.value === this.form.title)
      return obj ? obj.sample_id : null
    },
    setVideoProgress() {
      const _this = this
      document.onkeydown = function(event) {
        var e = event || window.event
        const time = _this.$refs.videoPlay.player.currentTime()
        if (e && e.keyCode === 37) { // 左
          _this.$refs.videoPlay.player.currentTime(time - 0.05)
        }
        if (e && e.keyCode === 39) { // 右
          _this.$refs.videoPlay.player.currentTime(time + 0.05)
        }
      }
    }
  }
}
</script>

<style scoped lang="scss">
.d-flex{
    display: flex;
}
.row-video{
    display: flex;
    flex: 1;
    justify-content: center;
    // margin-right: 100px;
    .row-video-content {
        position: relative;
        width:70%
    }
    .row-btn {
        position: absolute;
        right: -80px;
        top: 0;
    }
}
.border-shadow{
    box-shadow: 0 1px 4px rgba(0,21,41,.08);
    padding: 20px 15px;
    ::v-deep .video-js{
      padding-top: 70.25%;
      .vjs-tech{
        padding-bottom: 42px;
      }
    }
    .cur-time{
        margin:10px 0;
        font-size: 12px;
        display: inline-block;
        margin-right: 10px;
        span{
            color:goldenrod;
        }
    }
    .start-btn{
        background: rgba(119,118,255,1);
        border: none;
        padding: 7px 0;
    }
    .end-btn{
        background:rgba(234,114,122,1);
        border: none;
        padding: 7px 0;
    }
    .border-right{
        border-right: 1px solid #eee;
        margin-right: 20px;
        margin-left: -20px;
        justify-content: center;
    }
    &.row-form{
      width: 450px;
    }
    .el-form{
        .el-form-item{
            display: block;
            margin-right: 0;
            margin-bottom: 10px;
            padding-left: 30px;
            ::v-deep .el-form-item__content{
              width: 70%;
            }
            .el-form-item{
                display: inline-block;
                padding-left: 0;
            }
            .el-select{
                margin-right: 20px;
                // width: 185px;
            }
        }
        .cateFormItem{
            ::v-deep .el-form-item__content{
            width: min-content;
            .el-input{
                input{
                width: auto;
                }
            }
            }
        }
    }
    .formTime{
        .el-input{
        width: 35px;
        margin-right: 10px;
        ::v-deep input{
            padding: 0 7px;
            text-align: center;
        }
        ::v-deep input::-webkit-inner-spin-button{
            -webkit-appearance: none !important;
            margin: 0;
            -moz-appearance: textfield;
        }
        }
    }
    .dialog-footer{
        text-align: center;
        margin-top: 40px;
    }
}
.row-btn{
    flex-direction: column;
    padding-top: 50px;
    .el-button{
        width: 80px;
        margin-left: 0px;
        margin-bottom: 20px;
        background: rgba(130,130,130,1);
        border: none;
        &.activeClass{
            background: rgba(111,107,255,1);
            padding: 20px 0;
        }
    }
}
</style>
