<!--
 * @Author: your name
 * @Date: 2021-03-27 19:48:30
 * @LastEditTime: 2021-11-23 17:01:20
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/model/NewAdvt.vue
-->
<template>
  <base-dialog :show="show" title="新增广告" width="800px" :close-on-click-modal="false" @close="close">
    <el-form
      ref="ruleForm"
      :inline="true"
      :rules="rules"
      :model="form"
      class="d-flex flex-1 new-advt-form"
      label-width="90px"
      label-position="right"
    >
      <el-form-item label="频道:" prop="channel_id">
        <el-select v-model="form.channel_id" filterable placeholder="请选择频道" style="width:100%" clearable size="mini">
          <el-option
            v-for="item in filterChannelData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="标题名称:" prop="title" class="title-item">
        <el-autocomplete
          v-model="form.title"
          :fetch-suggestions="querySearchAsync"
          placeholder="请输入标题名称"
          size="mini"
        />
      </el-form-item>
      <el-form-item label="分类:" prop="tags" class="tag-item">
        <el-checkbox-group v-model="form.tags">
          <el-checkbox v-for="item in categories" :key="item" :label="item" />
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="播出日期:" prop="date">
        <el-date-picker
          v-model="form.date"
          type="date"
          placeholder="选择日期"
          style="width:100%"
          value-format="yyyy-MM-dd"
          clearable
          size="mini"
        />
      </el-form-item>
      <el-form-item label="开始时间:" class="formTime">
        <el-autocomplete
          v-for="item in timeArray"
          :key="item"
          v-model.trim="form['start'+item]"
          type="number"
          class="inline-input time-input"
          :fetch-suggestions="(queryString, cb)=>querySearch(item,queryString, cb)"
        />
      </el-form-item>
      <el-form-item label="结束时间:" class="formTime">
        <!-- <el-input
          v-for="item in timeArray"
          :key="item"
          v-model.number.trim="form['end'+item]"
          type="number"
          size="mini"
        /> -->
        <el-autocomplete
          v-for="item in timeArray"
          :key="item"
          v-model.trim="form['end'+item]"
          type="number"
          class="inline-input time-input"
          :fetch-suggestions="(queryString, cb)=>querySearch(item,queryString, cb)"
        />
      </el-form-item>
      <el-form-item class="dialog-footer">
        <base-btn @click="close">关闭</base-btn>
        <base-btn @click="submitForm('ruleForm')">提交</base-btn>
      </el-form-item>
    </el-form>
  </base-dialog>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  components: {

  },
  props: {
    show: {
      type: Boolean,
      default() {
        return false
      }
    },
    channelId: {
      type: Number,
      default() {
        return 0
      }
    }
  },
  data() {
    return {
      rules: {
        channel_id: [{
          required: true, message: '请选择频道', trigger: 'change'
        }],
        title: [{
          required: true, message: '请输入标题名称', trigger: 'blur'
        }],
        tags: [{
          required: true, message: '请选择分类', trigger: 'change'
        }],
        date: [{
          required: true, message: '请选择播出日期', trigger: 'change'
        }]
      },
      form: {
        title: '',
        tags: [],
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
        channel_id: null,
        date: null
      },
      titleArr: [],
      titleList: [],
      categories: ['公益广告', '商业广告', '购物广告', '植入广告', '赞助广告'],
      timeArray: ['Hour', 'Minute', 'Second', 'Ms']
    }
  },
  computed: {
    ...mapGetters([
      'channelData', 'userInfo'
    ]),
    filterChannelData() {
      if (this.userInfo.work === '广告审核' && this.channelData.length) {
        const channelIds = this.userInfo.ad_channel_ids.split(',')
        return this.channelData.filter(item => channelIds.includes(String(item.id)))
      }
      return this.channelData
    }
  },
  watch: {

  },
  async created() {
    this.form.channel_id = this.channelId
    const titleList = await this.getTitleList(this.form.title)
    this.getShowTitle(titleList)
    // this.getTitleList(this.form.title)
  },
  mounted() {

  },
  methods: {
    close() {
      this.$emit('close')
    },
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
    createStateFilter(queryString) {
      return (state) => {
        return (state.title.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    getTitleList(val) {
      const params = {
        channel_id: this.form.channel_id,
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
    getTime(type) {
      let str = this.form.date + ' '
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
    checkTime() {
      for (var i = 0; i < this.timeArray.length; i++) {
        if (!this.form['start' + this.timeArray[i]]) {
          this.$message('请输入开始时间')
          return false
        }
        if (!this.form['end' + this.timeArray[i]]) {
          this.$message('请输入结束时间')
          return false
        }
      }
      if (this.getTime('start') > this.getTime('end')) {
        this.$message('开始时间不能大于结束时间')
        return false
      }
      return true
    },
    // 提交
    submitForm(formName) {
      const params = {
        seq: {
          title: this.form.title,
          channel_id: this.form.channel_id,
          day: this.form.date,
          sample_id: this.getSampleId(),
          start_time: this.getTime('start'),
          end_time: this.getTime('end'),
          tags: this.form.tags.length > 0 ? this.form.tags.join(',') : ''
        }
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (!this.checkTime()) {
            return
          }
          this.$post('/epg-ad/new-ad', params).then((res) => {
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
    querySearch(type, queryString, cb) {
      const num = this.getNum(type); const arr = []
      for (var i = 0; i < num; i++) {
        arr.push({ value: String(i) })
      }
      var results = queryString ? arr.filter(item => String(item.value).indexOf(queryString) !== -1) : arr
      // 调用 callback 返回建议列表的数据
      cb(results)
    },
    getNum(item) {
      let num = 0
      switch (item) {
        case 'Hour':
          num = 24
          break
        case 'Minute':
          num = 60
          break
        case 'Second':
          num = 60
          break
        case 'Ms':
          num = 1000
          break
      }
      return num
    }
  }
}
</script>

<style lang="scss">
.new-advt-form{
  flex-direction: column;
    .el-form-item{
        display: block;
        margin-right: 0;
        margin-bottom: 10px;
        padding-left: 30px;
        .el-form-item__content{
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
    .title-item{
        .el-autocomplete{
            width: 100%;
        }
    }
    .tag-item{
        margin-bottom: 20px;
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
    width: 40px;
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
.el-autocomplete-suggestion{
  width: auto!important;
}
</style>
