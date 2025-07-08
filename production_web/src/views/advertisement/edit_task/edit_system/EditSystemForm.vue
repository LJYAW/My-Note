<!--
 * @Author: your name
 * @Date: 2021-02-24 11:27:39
 * @LastEditTime: 2021-04-22 17:07:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/model/EditSystemForm.vue
-->
<template>
  <div class="ad-edit-form">
    <el-form ref="ruleForm" :inline="true" :rules="rules" :model="form" label-width="90px">
      <el-form-item label="标题名称:" prop="title">
        <el-autocomplete
          v-model="form.title"
          :fetch-suggestions="querySearchAsync"
          placeholder="请输入标题名称"
          clearable
        />
      </el-form-item>
      <el-form-item label="分类:" prop="category">
        <el-checkbox-group v-model="form.category">
          <el-checkbox v-for="item in cateData" :key="item" :label="item" :disabled="isDisabled(item)" />
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="开始时间:" prop="start_time">
        <el-input v-model="form.start_time" size="mini" clearable />
      </el-form-item>
      <el-form-item label="结束时间:" prop="end_time">
        <el-input v-model="form.end_time" size="mini" clearable />
      </el-form-item>
      <el-form-item label="备注:" label-width="80px" class="form-mark">
        {{ form.mark||'无' }}
      </el-form-item>
      <div v-if="!this.$route.query.status" class="form-btn">
        <base-btn size="mini" type="warning" @click="resetData">重置</base-btn>
        <base-btn size="mini" @click="saveData">保存</base-btn>
      </div>
    </el-form>
    <div class="table-content">
      <el-table :data="tableData">
        <el-table-column prop="title" label="标题" show-overflow-tooltip width="200" />
        <el-table-column label="时间">
          <template slot-scope="scope">
            <p>{{ scope.row.start_time.split(' ')[1] }}</p>
            <p>{{ scope.row.end_time.split(' ')[1] }}</p>
          </template>
        </el-table-column>
        <el-table-column v-if="!this.$route.query.status" label="操作">
          <template slot-scope="scope">
            <base-btn type="text" @click="deleteData(scope.$index)">删除</base-btn>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-if="!this.$route.query.status" class="table-btn">
      <base-btn size="mini" type="danger" @click="hangUp">挂起</base-btn>
      <base-btn size="mini" @click="complete">完成</base-btn>
    </div>
    <HangUpDialog :id="taskMessage.task_id" ref="HangUpDialog" />
    <CompleteDialog ref="CompleteDialog" />
  </div>
</template>

<script>
import HangUpDialog from './model/HangUpDialog'
import CompleteDialog from './model/CompleteDialog'
import { timesToHMS, timeToSecond } from '../../js/times'
import { parseDateTime } from '@/utils/index.js'
export default {
  components: {
    HangUpDialog,
    CompleteDialog
  },
  props: {
    taskMessage: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      form: {
        title: '',
        category: [],
        start_time: '',
        end_time: '',
        mark: ''
      },
      timeout: null,
      tableData: [],
      channel_id: null,
      cateData: [],
      tagList: [],
      rules: {
        title: [{
          required: true,
          message: '请输入标题',
          trigger: 'blur'
        }],
        category: [{
          required: true,
          message: '请选择分类',
          trigger: 'change'
        }],
        start_time: [{
          required: true,
          message: '请输入开始时间',
          trigger: 'blur'
        }],
        end_time: [{
          required: true,
          message: '请输入结束时间',
          trigger: 'blur'
        }]
      },
      baseTime: null,
      titleList: []
    }
  },
  computed: {

  },
  watch: {
    'form.start_time'(val) {
      const params = {
        start: this.form.start_time,
        end: this.form.end_time
      }
      this.$bus.$emit('timeSlot', params)
    },
    'form.end_time'(val) {
      const params = {
        start: this.form.start_time,
        end: this.form.end_time
      }
      this.$bus.$emit('timeSlot', params)
    }
  },
  created() {
    this.cateData = this.taskMessage.tags.split(',')
    this.form.mark = this.taskMessage.comment
    if (this.taskMessage.epg_ad_list && this.taskMessage.epg_ad_list.length > 0) {
      this.taskMessage.epg_ad_list.forEach(item => {
        item.start_time = parseDateTime(item.start_time)
        item.end_time = parseDateTime(item.end_time)
      })
    }
    this.tableData = this.taskMessage.epg_ad_list || []
    this.channel_id = this.taskMessage.channel_id
    this.baseTime = new Date(this.taskMessage.start_date_time).getTime()
    // this.getTitleList()
  },
  mounted() {
    this.$bus.$on('setTime', s => {
      const parseaTime = s
      const startMs = new Date(this.form.start_time).getTime() || 0
      const endMs = new Date(this.form.end_time).getTime() || 0

      const dateArr = [startMs, endMs]
      !dateArr[0] ? (dateArr[0] = parseaTime) : (dateArr[1] = parseaTime)

      if (parseaTime <= startMs) {
        this.$message('开始时间不能大于结束时间')
        return
      }

      this.form.start_time = parseDateTime(dateArr[0])
      this.form.end_time = parseDateTime(dateArr[1])
    })
  },
  methods: {
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
        channel_id: this.taskMessage.channel_id,
        keyword: val
      }
      return new Promise((resolve, reject) => {
        this.$get('/epg-ad/epg-ad-search', params).then((res) => {
          // this.titleList = res.data.list
          resolve(res.data.list)
        }).catch((error) => {
          this.$message(error.msg)
        })
      })
    },
    hangUp() {
      this.$refs.HangUpDialog.open()
    },
    complete() {
      const params = {
        seqs: this.setSeqs(JSON.parse(JSON.stringify(this.tableData)))
      }
      this.$post(`/epg-ad/${this.taskMessage.task_id}/seqs`, params).then((res) => {
        this.$message({
          type: 'success',
          message: '提交成功'
        })
        this.$refs.CompleteDialog.open()
      }).catch((error) => {
        this.$message(error.msg)
      })
    },
    setSeqs(arr) {
      arr.forEach((item, index) => {
        item.start_time = new Date(item.start_time).getTime()
        item.end_time = new Date(item.end_time).getTime()
        item.type = '广告'
      })
      return arr
    },
    resetData() {
      this.form.title = ''
      this.form.category = []
      this.form.start_time = ''
      this.form.end_time = ''
      this.$bus.$emit('resetTime')
    },
    saveData() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          const startTime = new Date(this.form.start_time).getTime()
          const endTime = new Date(this.form.end_time).getTime()
          if (startTime > endTime) {
            this.$message('开始时间不能大于结束时间')
            return
          }
          const baseTime = new Date(this.taskMessage.start_date_time).getTime()
          this.tableData.unshift({
            title: this.form.title,
            start_time: this.form.start_time,
            end_time: this.form.end_time,
            tags: this.form.category.join(',')
          })
          this.$bus.$emit('saveTime')
          this.resetData()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    deleteData(index) {
      this.tableData.splice(index, 1)
      this.$bus.$emit('delete', index)
    },
    isDisabled(type) {
      if (type === '商业广告' && this.form.category.includes('公益广告')) return true
      if (type === '公益广告' && this.form.category.includes('商业广告')) return true
      return false
    }
  }
}
</script>

<style lang="scss">
.ad-edit-form{
    border: 1px solid #eee;
    padding-bottom: 20px;
    display: flex;
    flex-direction: column;
    .edit-form-head{
        display: flex;
        padding: 10px 20px;
        border-bottom: 1px solid #eee;
        flex-wrap: wrap;
        p{
            margin-right: 10px;
            margin-bottom: 12px;
            span{
                background-color: #eee;
                padding: 3px 6px;
                border-radius: 3px;
                margin-right: 5px;
            }
        }
    }
    .el-form{
        padding: 10px 20px;
        .el-form-item{
            display: block;
            margin-bottom: 5px;
            .el-form-item__error{
              top: 92%;
            }
            &.form-mark{
                .el-form-item__content{
                    width: 70%;
                    textarea{
                        resize: none;
                    }
                }
            }
            &.title-item{
              .el-form-item__content{
                width: 80%;
                .el-input{
                  width: 170px;
                }
                .tag-list{
                  border: 1px solid #eee;
                  padding: 0 20px;
                  min-height: 50px;
                  position: absolute;
                  width: 100%;
                  background: #fff;
                  z-index: 9;
                  span{
                    margin-right: 20px;
                    cursor: pointer;
                  }
                }
              }
            }
        }
    }
    .form-btn{
        display: flex;
        justify-content: space-between;
        margin-top: 20px;
        padding: 0 100px;
    }
    .table-btn{
        display: flex;
        justify-content: flex-end;
        padding-right: 20px;
    }
    .el-table{
        margin: 20px 0;
        flex: 1;
        .cell{
          max-height: 80px;
        }
        .el-table__body-wrapper{
          height: calc(100% - 40px);
          overflow-y: auto;
        }
    }
    .table-content{
      display: flex;
      height: 0;
      flex-direction: column;
      flex: 1;
    }
}
.el-autocomplete-suggestion{
  width: auto!important;
}
</style>
