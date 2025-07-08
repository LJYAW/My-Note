<!--
 * @Author: your name
 * @Date: 2021-04-01 10:41:11
 * @LastEditTime: 2021-04-20 16:57:01
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/examine_count/examine_task/model/AddDialog.vue
-->
<template>
  <base-dialog
    :show="show"
    title="新增片段"
    width="600px"
    :close-on-click-modal="false"
    class="new-fragment-model"
    @close="close"
  >
    <el-form
      ref="ruleForm"
      :inline="true"
      :rules="rules"
      :model="form"
      label-width="110px"
      label-position="left"
    >

      <el-form-item label="频道:" prop="channel_id">
        <el-select
          v-model="form.channel_id"
          filterable
          placeholder="请选择频道"
          style="width:100%"
          clearable
          @change="changeChannelId"
        >
          <el-option
            v-for="item in channelData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="类型:">
        <el-radio-group v-model="form.type">
          <el-radio label="广告">广告</el-radio>
          <el-radio label="正片">正片</el-radio>
        </el-radio-group>
      </el-form-item>

      <div v-if="form.type==='正片'">

        <el-form-item label="标题名称:" prop="title" class="title-item">
          <el-autocomplete
            v-model="form.title"
            :fetch-suggestions="querySearchAsync"
            placeholder="请输入标题名称"
            type="textarea"
          />
        </el-form-item>

        <el-form-item label="栏目名称:">
          <!-- <el-autocomplete
            v-model="form.item_id"
            :fetch-suggestions="querySearchItem"
            placeholder="请选择栏目名称"
            type="textarea"
          /> -->
          <el-select
            ref="searchData"
            v-model="form.item_id"
            placeholder="请选择栏目名称"
            clearable
            filterable
            @input.native="filterData"
            @change="changeItemId"
          >
            <el-option
              v-for="item in itemList"
              :key="item.item_id"
              :label="item.name"
              :value="item.item_id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="客户定制分类:">
          <el-select
            v-model="form.firstCategory"
            placeholder="一级分类"
            clearable
            filterable
          >
            <el-option
              v-for="item in firstCustomCategory"
              :key="item.category_id"
              :label="item.category_name"
              :value="item.category_id"
            />
          </el-select>
          <el-select
            v-model="form.secondCategory"
            placeholder="二级分类"
            clearable
            filterable
          >
            <el-option
              v-for="item in secondCustomCategory"
              :key="item.category_id"
              :label="item.category_name"
              :value="item.category_id"
            />
          </el-select>
        </el-form-item>

      </div>

      <el-form-item prop="start_date" label="开始时间:" class="formTime">
        <el-date-picker
          v-model="form.start_date"
          type="date"
          placeholder="选择开始日期"
          value-format="yyyy-MM-dd"
          :picker-options="pickerOptions"
        />
        <el-autocomplete
          v-for="item in timeArray"
          :key="item"
          v-model.trim="form['start'+item]"
          type="number"
          class="inline-input time-input"
          :fetch-suggestions="(queryString, cb)=>querySearch(item,queryString, cb)"
        />
      </el-form-item>

      <el-form-item prop="end_date" label="结束时间:" class="formTime">
        <el-date-picker
          v-model="form.end_date"
          type="date"
          placeholder="选择结束日期"
          value-format="yyyy-MM-dd"
          :picker-options="pickerOptions"
        />
        <el-autocomplete
          v-for="item in timeArray"
          :key="item"
          v-model.trim="form['end'+item]"
          type="number"
          class="inline-input time-input"
          :fetch-suggestions="(queryString, cb)=>querySearch(item,queryString, cb)"
        />
      </el-form-item>
    </el-form>
    <div class="dialog-footer">
      <base-btn class="submit-btn" @click="submit('ruleForm')">提交</base-btn>
      <base-btn type="info" @click="cancel">取消</base-btn>
    </div>
  </base-dialog>
</template>

<script>
import { mapGetters } from 'vuex'
import ColumnPdfVue from '../../../../../pdf/ColumnPdf.vue'
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
      itemList: [], // 栏目列表
      firstCustomCategory: [], // 一级客户定制分类
      secondCustomCategory: [], // 二级客户定制分类
      form: {
        channel_id: null,
        title: '',
        type: '广告',
        start_date: null,
        end_date: null,
        // start_time: '',
        startHour: '',
        startMinute: '',
        startSecond: '',
        startMs: '',
        endHour: '',
        endMinute: '',
        endSecond: '',
        endMs: '',
        // end_time: '',
        // startTime: null,
        // endTime: null,
        item_id: null,
        firstCategory: '',
        secondCategory: ''
      },
      start_time: null,
      end_time: null,
      rules: {
        channel_id: [{
          required: true, message: '请选择频道', trigger: 'change'
        }],
        title: [{
          required: true, message: '请输入标题名称', trigger: 'blur'
        }],
        start_date: [{
          required: true, message: '请选择开始日期', trigger: 'change'
        }],
        end_date: [{
          required: true, message: '请选择结束日期', trigger: 'change'
        }]
      },
      timeArray: ['Hour', 'Minute', 'Second', 'Ms'],
      pickerOptions: {
        disabledDate(time) {
          const dateTime = new Date()
          return (
            time.getTime() > new Date(dateTime).getTime()
          )
        }
      }
    }
  },
  computed: {
    ...mapGetters([
      'channelData'
    ])
  },
  watch: {

  },
  created() {
    this.form.channel_id = this.channelId || null
    this.getItemList()
    this.getCustomCategory()
  },
  mounted() {

  },
  methods: {
    // 标题筛选
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
    getTitleList(val) {
      const params = {
        channel_id: this.form.channel_id,
        keyword: val
      }
      return new Promise((resolve, reject) => {
        this.$get('/epg-ad/epg-ad-search', params).then((res) => {
          resolve(res.data.list)
        }).catch((error) => {
          reject(error)
          this.$message(error.msg)
        })
      })
    },
    close() {
      this.$emit('close')
    },
    // 获取栏目列表
    getItemList(val) {
      const params = {
        channel_id: this.form.channel_id || null,
        keyword: val || ''
      }
      this.$get('/production/itemList', params).then((res) => {
        this.itemList = res.data
      }).catch((error) => {
        this.$message.error(error.msg)
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
    changeChannelId(val) {
      this.getItemList()
      this.form.item_id = null
    },
    checkTime() {
      for (var i = 0; i < this.timeArray.length; i++) {
        if (!String(this.form['start' + this.timeArray[i]])) {
          this.$message('请输入开始时间')
          return false
        }
        if (!String(this.form['end' + this.timeArray[i]])) {
          this.$message('请输入结束时间')
          return false
        }
      }
      const { start_date, startHour, startMinute, startSecond, startMs } = this.form
      const { end_date, endHour, endMinute, endSecond, endMs } = this.form

      this.startTime = start_date + ' ' + startHour + ':' + startMinute + ':' + startSecond + '.' + startMs
      this.endTime = end_date + ' ' + endHour + ':' + endMinute + ':' + endSecond + '.' + endMs

      const start_time_ms = new Date(this.startTime).getTime()
      const end_time_ms = new Date(this.endTime).getTime()

      if (end_time_ms < start_time_ms) {
        this.$message('开始时间不能大于结束时间')
        return false
      }
      return true
    },
    // 验证表单
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (!this.checkTime()) {
            return
          }
          this.saveData()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 提交数据
    saveData() {
      const params = {
        seq: {
          channel_id: this.form.channel_id,
          item_id: this.form.item_id,
          day: this.form.end_date,
          title: this.form.title || '广告',
          type: this.form.type,
          start_time: new Date(this.startTime).getTime(),
          end_time: new Date(this.endTime).getTime(),
          first_customer_category_ids: this.form.firstCategory ? [this.form.firstCategory] : [],
          second_customer_category_ids: this.form.secondCategory ? [this.form.secondCategory] : []
        }
      }
      this.$post('/epg/new-add', params).then((res) => {
        this.$emit('submit')
        this.cancel()
      }).catch((error) => {
        this.$message(error.msg)
      })
    },
    // 清空数据
    resetForm() {
      Object.keys(this.form).forEach(item => { this.form[item] = null })
      this.form.type = '广告'
    },
    cancel() {
      this.close()
      this.resetForm()
    },
    filterData: window.lodash.debounce(function() {
      var str = this.$refs.searchData.$data.selectedLabel
      this.getItemList(str)
    }, 1000),
    setCustomCate(customCate, type) {
      this.form[type + 'Category'] = customCate.length ? customCate[0].id : ''
    },
    changeItemId(id) {
      const obj = this.itemList.filter(item => item.item_id === id)
      this.setCustomCate(obj[0].first_customer_categories, 'first')
      this.setCustomCate(obj[0].second_customer_categories, 'second')
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
.new-fragment-model{
    .el-form-item{
        display: block;
        .el-select{
            margin-right: 20px;
        }
    }
    .formTime{
        .el-date-editor.el-input{
            width: 180px;
            margin-right: 20px;
        }
        .time-input{
            width: 40px;
            margin-right: 10px;
            i{
              display: none;
            }
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
        .submit-btn{
            margin-right: 20px;
        }
    }
}
</style>
