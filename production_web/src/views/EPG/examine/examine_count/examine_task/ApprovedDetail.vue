<!--
 * @Author: your name
 * @Date: 2021-03-03 14:30:54
 * @LastEditTime: 2021-05-06 15:23:45
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/model/ApprovedDetail.vue
-->
<template>
  <div class="content mt-10px d-flex dialog-content">
    <div class="row-video">
      <div class="row-video-content">
        <div class="border-shadow video-wrap">
          <!-- <VideoHls ref="videoPlay" class="videoPaly" :player-options="playerOptions" cross-origin="anonymous" /> -->
          <!-- 播放视频 -->
          <VideoHls
            v-if="!status_loading"
            ref="videoPlay"
            :key="playerOptions.src"
            class="videoPaly"
            :video-offset-ms="videoOffsetMs"
            :player-options="playerOptions"
            cross-origin="anonymous"
          />
          <p class="cur-time">视频当前时间:<br><span class="time">{{ msToSecond(current_time_ms) }}</span></p>
          <p class="cur-time">
            <base-btn size="mini" class="start-btn" @click="setTime('start')">标开始点</base-btn>
            <base-btn size="mini" class="end-btn" @click="setTime('end')">标结束点</base-btn>
          </p>

        </div>

        <!-- 操作按钮 -->
        <div class="d-flex row-btn">
          <base-btn
            v-if="propData.type!=='空白'"
            size="mini"
            :class="activeIndex===1&&'activeClass'"
            :disabled="propData.type==='空白'"
            @click="activeIndex=1"
          >修改本条</base-btn>
          <base-btn
            v-if="propData.source!=='审核录入'&&propData.type!=='正片'"
            size="mini"
            :class="activeIndex===2&&'activeClass'"
            :disabled="propData.source=='审核录入'"
            @click="activeIndex=2"
          >插入节目</base-btn>
          <base-btn
            v-if="propData.source!=='审核录入'&&propData.type!=='广告'"
            size="mini"
            :class="activeIndex===3&&'activeClass'"
            :disabled="propData.source=='审核录入'"
            @click="activeIndex=3"
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
        class="d-flex flex-1"
        label-width="90px"
        label-position="left"
      >
        <!-- 客户定制分类和栏目分类展示 客户定制分类优先 -->
        <div v-if="activeIndex!==3" class="border-right d-flex">
          <div>
            <el-form-item label="标题名称:" prop="title">
              <el-autocomplete
                v-model="form.title"
                :fetch-suggestions="querySearchAsync"
                placeholder="请输入标题名称"
                type="textarea"
              />
            </el-form-item>
            <el-form-item label="分类:" class="cateFormItem">
              <el-select
                v-model="form.firstCategory"
                size="mini"
                placeholder="一级分类"
                clearable
                filterable
                :disabled="isDisabled"
              >
                <el-option
                  v-for="item in firstCustomCategory"
                  :key="item.category_id"
                  :label="item.category_name"
                  :value="item.category_id"
                />
              </el-select>
              <el-form-item :inline="true">
                <el-select
                  v-model="form.secondCategory"
                  size="mini"
                  placeholder="二级分类"
                  clearable
                  filterable
                  :disabled="isDisabled"
                >
                  <el-option
                    v-for="item in secondCustomCategory"
                    :key="item.category_id"
                    :label="item.category_name"
                    :value="item.category_id"
                  />
                </el-select>
              </el-form-item>
            </el-form-item>

            <el-form-item label="栏目名称:">
              <el-select
                ref="searchData"
                v-model="form.item_id"
                size="mini"
                placeholder="请选择栏目名称"
                clearable
                filterable
                :disabled="isDisabled"
                @input.native="filterData"
                @clear="getItemList"
              >
                <el-option
                  v-for="item in itemList"
                  :key="item.item_id"
                  :label="item.name"
                  :value="item.item_id"
                />
              </el-select>
            </el-form-item>
          </div>
        </div>
        <div class="d-flex">
          <div>
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
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import VideoHls from '@/components/VideoHlsCut/VideoHls'
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
        firstCategory: [],
        secondCategory: [],
        item_id: ''
      },
      titleList: [],
      rules: {
        title: [{ required: true, message: '请输入标题名称', trigger: 'blur' }]
      },
      timeArray: ['Hour', 'Minute', 'Second', 'Ms'],
      firstCategoryName: '',
      secondCategoryName: '',
      itemList: [],
      firstCustomCategory: [],
      secondCustomCategory: [],
      startDate: '',
      endDate: '',
      start_time_dvalue: null,
      videoOffsetMs: {
        start_ms: 1000,
        end_ms: 3000
      },
      status_loading: true
    }
  },
  computed: {
    current_time_ms() {
      return this.$store.state.videoPlay.current_time_ms
    },
    isDisabled() {
      const obj = this.propData
      if ((obj && obj.type === '广告' && this.activeIndex === 1) || this.activeIndex === 3) {
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
        this.form.item_id = null
        this.form.firstCategory = null
        this.form.secondCategory = null
        this.firstCategoryName = null
        this.secondCategoryName = null
        this.timeArray.forEach((item, index) => {
          this.form['start' + item] = ''
          this.form['end' + item] = ''
        })
      }
    }
  },
  created() {
    const item_name = this.propData.item_info ? this.propData.item_info.item_name : ''
    this.getItemList(item_name)
    this.getCustomCategory()
    if (this.propData.type === '空白') {
      this.activeIndex = 2
    }
    const startTime = dateFun.convert(this.propData.start_time, 'y-m-d h:m:s')
    this.setUpdateTime(startTime, 'start')
    const endTime = dateFun.convert(this.propData.end_time, 'y-m-d h:m:s')
    this.setUpdateTime(endTime, 'end')
  },
  mounted() {
    this.$bus.$on('getChannelId', id => {
      this.getTitleList(id)
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
      const titleArr = []
      titleList.forEach(item => {
        titleArr.push({
          id: item.id,
          value: item.title
        })
      })
      cb(titleArr)
    },
    // 获取m3u8视频时间差值
    getTimeDifference() {
      this.status_loading = true
      const params = {
        channel_en_name: this.propData.channel_en_name,
        start_time: this.propData.start_time - 10000,
        end_time: this.propData.end_time + 10000
      }
      this.$get('/epg/m3u8-dvalue', params).then((res) => {
        const data = res.data
        this.videoOffsetMs.start_ms = data.start_time_dvalue * 1000
        this.videoOffsetMs.end_ms = this.propData.end_time - this.propData.start_time + data.start_time_dvalue * 1000
        this.setFormData()
        this.status_loading = false
      }).catch((error) => {
        console.log(error)
      })
    },
    msToSecond(s, type) {
      const mstime = String(s).split('.')[1] || 0
      const time = this.propData.start_time + s - this.start_time_dvalue * 1000
      const ms = String(time).split('.')[0].substring(10)
      const testTime = dateFun.convert(time, 'yyyy-MM-dd hh:mm:ss') + '.' + ms
      if (type) {
        this.$data[type + 'Date'] = testTime.split(' ')[0]
      }
      return testTime
    },
    // 获取标题列表
    getTitleList(val) {
      const params = {
        channel_id: this.propData.channel_id,
        keyword: val
      }
      return new Promise((resolve, reject) => {
        this.$get('/epg/epg-search', params).then((res) => {
          resolve(res.data.list)
        }).catch((error) => {
          this.$message(error.msg)
        })
      })
    },
    setTime(type) {
      const newTime = this.msToSecond(this.current_time_ms, type)
      this.setUpdateTime(newTime, type)
    },
    // 表单里面开始时间和结束时间回显
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
    // 表单数据回显
    setFormData() {
      this.form.title = this.propData.title
      this.form.item_id = this.propData.item_info ? this.propData.item_info.item_id : ''
      const firstCategory = this.propData.first_customer_categories
      const secondCategory = this.propData.second_customer_categories
      // 给表单里分类赋值，回显数据
      this.form.firstCategory = this.getFormCategory(firstCategory, 'id')[0]
      this.form.secondCategory = this.getFormCategory(secondCategory, 'id')[0]
      // 视频地址
      this.playerOptions.sources[0].src = this.propData.m3u8_vod_url
      this.playerOptions.src = this.propData.m3u8_vod_url
    },
    // 获取栏目列表
    getItemList(str) {
      const params = {
        channel_id: this.propData.channel_id,
        keyword: str
      }
      this.$get('/production/itemList', params).then((res) => {
        this.itemList = res.data
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    // 获取分类数据
    getFormCategory(categories, key) {
      const arr = []
      if (!categories || !categories.length) return arr
      categories.forEach((item) => {
        arr.push(item[key])
      })
      return arr
    },
    // 提交时获取type值
    getParamsType() {
      if (this.activeIndex === 1) {
        return this.propData.type
      }
      return this.activeIndex === 2 ? '正片' : '广告'
    },
    close() {
      this.$emit('close')
    },
    getTime(type) {
      let str = this.$data[type + 'Date'] + ' '
      let sign = ':'
      this.timeArray.forEach((item, index) => {
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
      const type = this.getParamsType()
      const params = {
        epg_id: this.propData.id,
        action: this.activeIndex === 1 ? 'update' : 'insert',
        from: this.propData.type,
        point: {
          channel_id: this.propData.channel_id,
          channel_name: this.propData.channel_name,
          order_id: this.propData.order_id,
          day: this.propData.day,
          item_id: this.form.item_id || 0,
          title: this.activeIndex === 3 ? '广告' : this.form.title,
          start_time: this.getTime('start'),
          end_time: this.getTime('end'),
          type: type,
          first_customer_category_ids: this.form.firstCategory ? [this.form.firstCategory] : [],
          second_customer_category_ids: this.form.secondCategory ? [this.form.secondCategory] : []
        }
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$post('/epg/epg-modify', params).then((res) => {
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
    // 栏目名称筛选
    filterData: window.lodash.debounce(function() {
      var str = this.$refs.searchData.$data.selectedLabel
      this.getItemList(str)
    }, 800)
  }
}
</script>

<style lang="scss">
.d-flex{
  display: flex;
}
.dialog-content{

  .row-video{
    display: flex;
    justify-content: center;
    margin-right: 100px;
    .row-video-content {
        position: relative;
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
      .video-js{
        padding-top: 70.25%;
        .vjs-tech{
          padding-bottom: 42px;
        }
      }

      .cur-time{
          margin:10px 0;
          font-size: 12px;
          // display: inline-block;
          margin-right: 10px;
          .time{
              display: inline-block;
              color:goldenrod;
              font-weight: bold;
              font-size: 14px;
              margin-top: 5px;
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
      .el-form{
          display: flex;
          &>div{
              flex: 1;
              justify-content: center;
          }
          .el-form-item{
              display: block;
              margin-right: 0;
              margin-bottom: 10px;
              padding-left: 30px;
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
              .el-form-item__content{
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
          input{
              padding: 0 7px;
              text-align: center;
          }
          input::-webkit-inner-spin-button{
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

}
@media screen and (min-width: 1600px){
  .row-video{
      width: 40%;
      margin-bottom: 20px;
      .row-video-content{
          width: 85%;
      }
  }
  .row-form{
      width: 60%;
  }
}
@media screen and (max-width: 1600px){
  .content{
      flex-direction: column;
  }
  .row-video{
      width: 100%;
      margin-bottom: 20px;
      .row-video-content{
          width: 35%;
      }
  }
  .row-form{
      width: 100%;
  }
}
.el-autocomplete-suggestion{
  width: auto!important;
}
</style>
