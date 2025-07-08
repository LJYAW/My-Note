<!--
 * @Author: your name
 * @Date: 2021-02-24 11:27:39
 * @LastEditTime: 2021-12-23 19:41:40
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/model/EditSystemForm.vue
-->
<template>
  <div class="ad-edit-form">
    <div class="btn-wrap">
      <el-button type="warning" size="mini" @click="hangCenter">挂起</el-button>
      <el-button type="primary" size="mini" :disabled="btnLoading" @click="submitCenter">完成</el-button>
    </div>
    <el-form ref="ruleForm" :inline="true" :model="fromData" label-width="100px">
      <el-form-item label="标题名称:" required>
        <el-autocomplete
          v-model="fromData.title"
          :disabled="disabled"
          type="textarea"
          :fetch-suggestions="querySearchAsync"
          placeholder="请输入标题名称"
          clearable
        />
      </el-form-item>
      <el-form-item label="栏目名称:">
        <div class="category">
          <el-select
            v-if="!itemLoading"
            ref="searchData"
            v-model="fromData.item_id"
            filterable
            size="mini"
            clearable
            placeholder="请选择栏目名称"
            :disabled="disabled"
            @input.native="filterData"
            @clear="clear"
          >
            <el-option
              v-for="item in itemData"
              :key="item.item_id"
              size="mini"
              :label="item.name"
              :value="item.item_id"
              @click.native.prevent="change(item)"
            />
          </el-select>
          <base-btn size="mini" class="add-btn" @click="addCategory">新增</base-btn>
        </div>
      </el-form-item>
      <!-- 分类 -->
      <!-- <el-form-item label="" prop="" class="category-box">
        <div class="message">
          <div>一级分类:</div>
          <span>{{ form.first_category_names }}</span>
        </div>
        <div class="message">
          <div>二级分类:</div>
          <span>{{ form.second_category_names }}</span>
        </div>
      </el-form-item> -->
      <!-- 定制分类 -->
      <el-form-item label="" class="category-box">
        <div class="title-box">
          <div class="title">定制分类：</div>
          <base-btn size="mini" class="add-btn" type="" :disabled="disabled" @click="edit">编辑</base-btn>
          <span class="guide" @click="guide">?指南</span>
        </div>
        <div class="message">
          <span style="color: #F56C6C;margin-right: 5px">*</span>
          <div>一级分类:</div>
          <span>{{ fromData.first_customer_category_names }}</span>
        </div>
        <div class="message">
          <div>二级分类:</div>
          <span>{{ fromData.second_customer_category_names }}</span>
        </div>

      </el-form-item>
      <el-form-item label="">
        <el-checkbox v-model="fromData.is_prime_time" true-label="1" false-label="0" label="黄金时段" />
        <el-checkbox v-model="fromData.is_premiere" true-label="1" false-label="0" label="首播" />
        <div class="form-btn">
          <base-btn size="mini" :disabled="disabled" @click="saveData">保存</base-btn>
        </div>
      </el-form-item>

    </el-form>
    <div class="table-content">
      <el-table :data="tableData" :row-class-name="tableRowClassName" @row-click="rowClick">
        <el-table-column prop="type" label="类型">
          <template slot-scope="scope">
            <span :class="scope.row.type==='正片'?'positive':'advertisement'">{{ scope.row.position }}</span>
          </template>
        </el-table-column>
        <el-table-column label="切点" prop="date" width="100px">
          <template slot-scope="scope">
            <div class="date">{{ scope.row.timeStr }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="标题" width="110px">
          <template slot-scope="scope">
            <div class="epg-title">{{ scope.row.title }}</div>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120px">
          <template slot-scope="scope">
            <base-btn type="text" @click.stop="deleteData(scope.$index)">删除</base-btn>
            <!-- <base-btn v-if="scope.row.type==='正片'&&scope.row.position==='开始'" type="text" @click.stop="editData(scope.$index,scope)">编辑</base-btn> -->
          </template>
        </el-table-column>
      </el-table>
    </div>
    <base-dialog :show="show" :title="title" width="800px" class="add" :close-on-click-modal="true" @close="close">
      <component
        :is="activeName"
        :item-list="itemList"
        :column-name="columnName"
        @createdItem="createdItem"
        @resetData="resetData"
        @closeData="closeData"
      />
    </base-dialog>
  </div>
</template>

<script>
import { parseDateTime } from '@/utils/index.js'
import Channel from './column/index'
import EditCustom from './model/EditCustom'
import HangDialog from './model/Hang.vue'
import { mapGetters } from 'vuex'

const BASE_FROM_DATA = () => {
  return {
    title: '',
    item_id: '',
    is_premiere: '0', // 是否首播 0 否 1 是
    is_prime_time: '0', // 是否黄金时段 0 否 1是
    position: '', // 切点位置（枚举值：开始、结束
    start_time: '', // 开始时间戳（毫秒）
    type: '', // 类型 （枚举值：广告、正片)
    first_customer_category_ids: [],
    first_customer_category_names: '',
    second_customer_category_ids: [],
    second_customer_category_names: ''
  }
}

export default {
  components: {
    Channel,
    HangDialog,
    EditCustom
  },
  props: {
    epgData: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      disabled: true,
      tabIndex: '',
      columnName: '',
      itemLoading: false,
      itemList: {
      },
      form: {
        title: '',
        item_id: '',
        category_id: '',
        first_category_names: '',
        second_category_names: ''
      },
      // 新版提交数据格式
      fromData: BASE_FROM_DATA(),
      title: '',
      activeName: '',
      tableData: [],
      show: false,
      btnLoading: false,
      rules: {
        // title: [{
        //   required: true,
        //   message: '请输入标题',
        //   trigger: 'change'
        // }],
        // item_id: [{
        //   required: true,
        //   message: '请选择栏目名称',
        //   trigger: 'change'
        // }],
        // // 客户定制分类
        // category_id: [{
        //   required: false,
        //   message: '',
        //   trigger: 'change'
        // }]
      },
      titleList: [],
      itemData: []
    }
  },
  computed: {
    ...mapGetters([]),
    // 视频时间数据
    cutTimeList() {
      return this.$store.state.newEpgEdit.cutTimeList
    },
    // epg表单数据
    epgFormData() {
      return this.$store.state.newEpgEdit.epgFormData
    }
  },
  watch: {
    //   监听时间数据
    cutTimeList: {
      handler(newArr, oldArr) {
        console.log('🚀 ~ file: right-pad.vue ~ line 182 ~ handler ~ this.tableData', this.tableData)
        console.log('🚀 ~ file: right-pad.vue ~ line 182 ~ handler ~ newArr', newArr)
        if (newArr.length > this.tableData.length) {
          this.setTableData(newArr)
        }
      },
      deep: true
    }
  },
  created() {
    //   初始化栏目数据
    this.getItemList()
    // this.$store.dispatch('channel/getItemList')
  },
  beforeDestroy() {
    this.$store.commit('newEpgEdit/SET_CUT_TIME_LIST', [])
  },
  mounted() {
  },
  methods: {
    // 获取栏目列表
    getItemList(val) {
      const params = {
        channel_id: this.epgData.channel_id || null,
        keyword: val || ''
      }
      this.$get('/production/itemList', params).then((res) => {
        this.itemData = res.data
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    filterData: window.lodash.debounce(function() {
      var str = this.$refs.searchData.$data.selectedLabel
      this.getItemList(str)
    }, 1000),
    createdItem() {
      this.itemLoading = true
      this.$nextTick(() => {
        this.itemLoading = false
      })
    },
    //   编辑table数据
    editData(index, item) {
      this.tabIndex = index
      this.disabled = false
      this.setFormData()
    },
    // 表格当前行index
    tableRowClassName({ row, rowIndex }) {
      row.row_index = rowIndex
    },
    // 清空栏目数据
    clear() {
      this.columnName = ''
      this.form.first_category_names = ''
      this.form.second_category_names = ''
    },
    // 点击表格当前行
    rowClick(row, column, event) {
      this.$bus.$emit('rowClick', row.row_index)
      this.tabIndex = row.row_index
      if (row.type === '正片' && row.position === '开始') {
        this.disabled = false
      } else {
        this.disabled = true
      }
      this.setRowData(row)
    },
    // 清空表单数据
    clearAllData() {
      this.columnName = ''
      Object.keys(this.form).forEach(item => {
        this.form[item] = ''
      })
      Object.keys(this.itemList).forEach(item => {
        this.itemList[item] = ''
      })
    },
    // 点击单条table数据，回显数据
    setRowData(data) {
      console.log('🚀 ~ file: right-pad.vue ~ line 298 ~ setRowData ~ data', data)
      if (data.fromData) {
        this.fromData = JSON.parse(JSON.stringify(data.fromData))
      } else {
        this.fromData = BASE_FROM_DATA()
      }
    },
    // 指南（pdf）
    guide() {
      const { href } = this.$router.resolve({
        path: '/column/pdf',
        query: {
          name: 'kehudingzhifenlei01'
        }
      })
      window.open(href, '_blank')
    },
    // 关闭数据（新增栏目）
    closeData() {
      this.show = false
    },
    // 任务挂起
    hangCenter() {
      this.activeName = 'HangDialog'
      this.title = '任务挂起说明'
      this.show = true
    },
    // 检验数据里面是否有正片开始状态下没有填写数据的
    checkFun() {
      this.tableData.forEach(item => {
        if (item.position === '开始' && item.type === '正片') {
        //   sd
        }
      })
    },
    // 检查数据
    checkParams() {
      let err = ''
      const arr = this.setEpgParams()
      for (let i = 0; i < arr.length; i++) {
        const item = arr[i]
        if (item.type === '正片' && item.position === '开始') {
          if (!item.title) {
            err = `请填写数据${i + 1} 标题`
            break
          }
          if (item.first_customer_category_ids.length < 1) {
            err = `请选择数据 ${i + 1} 一级分类`
            break
          }
        }
      }
      return err
    },
    // 提交数据
    submitCenter() {
      const err = this.checkParams()

      if (err) {
        this.$message.error(err)
        return
      }

      this.btnLoading = true
      const params = {
        epg_points: this.setEpgParams()
      }
      this.$post(`/epg-task/${this.epgData.task_id}/epg-points?from=h5`, params).then((res) => {
        this.btnLoading = false
        this.$message({
          message: '提交成功',
          type: 'success'
        })
        this.$store.commit('newEpgEdit/SET_CUT_TIME_LIST', [])
        setTimeout(() => {
          this.$router.push({
            path: '/EPG/edit-task'
          })
        }, 1000)
      }).catch(res => {
        this.btnLoading = false
        this.$message({
          showClose: true,
          message: res.msg,
          type: 'error'
        })
      })
    },
    // 整理表格数据
    setTableData(arr) {
      const setData = (data) => {
        const type = data.type === 1 ? '正片' : '广告'
        const key = data.key === 'start' ? '开始' : '结束'
        const defaultTitle = (type + key !== '正片开始') ? (type + key) : ''

        const obj = {
          title: defaultTitle,
          item_id: '',
          category_id: '',
          first_category_names: '',
          second_category_names: '',
          item: {
            first_category_id: '',
            first_category_name: '',
            item_id: '',
            second_category_id: '',
            second_category_name: ''
          }
        }

        return {
          type: type,
          title: type === '正片' && key === '开始' ? '' : type + key,
          timeStr: data.timeStr,
          start_time: data.dateTimeMs,
          position: key,
          falg: '',
          ...obj
        }
      }
      const obj = arr[arr.length - 1]
      const newObj = setData(obj)
      this.tableData.push(newObj)
    },
    // 栏目切换数据
    change(item) {
      // this.columnName = item.name
      // this.form.first_category_names = item.first_category_names
      // this.form.second_category_names = item.second_category_names
      // this.itemList.item_id = item.item_id

      // // 赋值客户定制分类
      // if (item.first_customer_categories.length > 0) {
      //   this.itemList.first_category_id = item.first_customer_categories[0].id || ''
      //   this.itemList.first_category_name = item.first_customer_categories[0].name || ''
      // }
      // if (item.second_customer_categories.length > 0) {
      //   this.itemList.second_category_id = item.second_customer_categories[0].id || ''
      //   this.itemList.second_category_name = item.second_customer_categories[0].name || ''
      // }
    },
    // 编辑分类
    edit() {
      this.activeName = 'EditCustom'
      this.title = '编辑分类'
      this.show = true
    },
    // 更新数据（客户定制分类）
    resetData(data) {
      this.fromData.first_customer_category_ids = [data.first_category_id]
      this.fromData.first_customer_category_names = data.first_category_name
      this.fromData.second_customer_category_ids = [data.second_category_id]
      this.fromData.second_customer_category_names = data.second_category_name
      this.close()
    },
    // 关闭弹框
    close() {
      this.show = false
    },
    // 添加栏目
    addCategory() {
      this.show = true
      this.activeName = 'Channel'
      this.title = '新增栏目'
    },
    // 标题模糊搜索
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
    // 获取标题模糊数据
    getTitleList(val) {
      const params = {
        channel_id: this.epgData.channel_id,
        keyword: val
      }
      // http://api-work_order.tmsx.net/api/v1/epg/epg-search?channel_id=2&keyword=2
      return new Promise((resolve, reject) => {
        this.$get('/epg/epg-search', params).then((res) => {
          resolve(res.data.list)
        }).catch((error) => {
          this.$message(error.msg)
        })
      })
    },
    // 删除表格数据
    deleteData(index) {
      this.tableData.splice(index, 1)
      this.$store.commit('newEpgEdit/SPLICE_CUT_TIME_LIST', index)
    },
    // 设置form表单数据
    setFormData() {
      const fromData = JSON.parse(JSON.stringify(this.fromData))
      const index = this.tabIndex
      this.$set(this.tableData, index, fromData)
    },
    setEpgParams() {
      const arr = this.tableData.map(item => {
        const fromData = item.fromData || {}
        return {
          title: item.title,
          item_id: fromData.item_id,
          is_premiere: fromData.is_premiere, // 是否首播 0 否 1 是
          is_prime_time: fromData.is_prime_time, // 是否黄金时段 0 否 1是
          position: item.position, // 切点位置（枚举值：开始、结束
          start_time: item.start_time, // 开始时间戳（毫秒）
          type: item.type, // 类型 （枚举值：广告、正片)
          first_customer_category_ids: fromData.first_customer_category_ids,
          second_customer_category_ids: fromData.second_customer_category_ids
        }
      })
      return arr
    },
    // 前端保存数据
    saveData() {
      const data = this.tableData[this.tabIndex]
      let err = ''
      if (data.type === '正片' && data.position === '开始') {
        if (this.fromData.first_customer_category_ids.length < 1) {
          err = `请选择一级分类`
        }
        if (!this.fromData.title) {
          err = `请填写标题`
        }
      }
      if (err) {
        this.$message.error(err)
        return
      }
      const fromData = JSON.parse(JSON.stringify(this.fromData))
      const title = JSON.parse(JSON.stringify(this.fromData.title))

      this.$set(this.tableData[this.tabIndex], 'fromData', fromData)
      this.$set(this.tableData[this.tabIndex], 'title', title)
    }
  }
}
</script>

<style lang="scss" scoped>
@import './scss/right-pad.scss';
</style>
