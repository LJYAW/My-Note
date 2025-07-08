
<template>
  <div class="public-wrap">
    <div class="search-top">
      <div class="item-search">
        <span>TID:</span>
        <el-input v-model="form.task_id" clearable placeholder="请输入TID" />
      </div>
      <div class="item-search">
        <span>播出日期:</span>
        <el-date-picker v-model="form.date" type="date" placeholder="选择日期" style="width:100%" value-format="yyyy-MM-dd" />
      </div>
      <div class="item-search">
        <span>频道:</span>
        <el-select v-model="form.channel_id" clearable placeholder="请选择频道" style="width:100%" filterable>
          <el-option
            v-for="item in channelData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </div>
      <div class="item-search">
        <span>栏目:</span>
        <el-select v-model="form.item_id" clearable placeholder="请选择栏目" style="width:100%" filterable>
          <el-option
            v-for="item in itemData"
            :key="item.item_id"
            :label="item.name"
            :value="item.item_id"
          />
        </el-select>
      </div>
      <div class="item-search">
        <span>加工方式:</span>
        <el-select v-model="form.processing_require" clearable placeholder="请选择加工方式" style="width:100%">
          <el-option
            v-for="item in machining_options"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
      </div>
      <div class="item-search btns">
        <base-btn size="big" @click="search()">搜索</base-btn>
      </div>
    </div>
    <div class="table-list">
      <el-table :data="tableData" class="table-list">
        <el-table-column prop="task_id" label="TID" />
        <el-table-column prop="channel_name" label="频道" />
        <el-table-column prop="item_name" label="栏目" />
        <el-table-column prop="date" label="播出日期" />
        <el-table-column prop="time" label="播出时间">
          <template slot-scope="scope">
            <span>{{ scope.row.start_time }}</span>
            <span>-</span>
            <span>{{ scope.row.end_time }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="processing_require" label="加工方式" />
        <el-table-column prop="status" label="任务状态" />
        <el-table-column prop="" label="操作">
          <template slot-scope="scope">
            <!-- <a class="start-task" @click="startTask(scope.row)">开始任务</a> -->
            <base-open-qt v-if="scope.row.status !== '已完成' && scope.row.status !== '挂起中'" :task-id="scope.row.task_id" />
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-list">
      <base-pag :total="total" :limit="limit" :page="page" @current-change="handleCurrentChange" @size-change="handleSizeChange" />
    </div>
  </div>
</template>

<script>
import openQweb from '@/utils/open-qweb.js'
import { mapGetters } from 'vuex'
export default {
  components: {},
  data() {
    return {
      channer_options: [
      ],
      colunm_options: [
      ],
      machining_options: [
      ],
      page: 1,
      limit: 10,
      form: {
        channel_id: null,
        date: null,
        item_id: null,
        processing_require: null,
        task_id: null
      },
      total: null,
      tableData: [
      ]
    }
  },
  computed: {
    ...mapGetters([
      'channelData',
      'itemData'
    ])
  },
  watch: {
    'form.channel_id'(id) {
      if (id) {
        this.$store.dispatch('channel/getItemList', { channel_id: id })
      }
      this.form.item_id = null
    }
  },
  created() {
    if (!this.channelData.length) {
      this.$store.dispatch('channel/getChannelData')
    }
    this.getProcessingRequire()
    this.getList()
  },
  mounted() {},
  methods: {
    handleSizeChange(val) {
      this.limit = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.page = val
      this.getList()
    },
    search() {
      this.getList()
    },
    getProcessingRequire() {
      this.machining_options = []
      this.$get('/production/processingRequire').then(res => {
        const { data } = res
        this.machining_options = data.split
      }).catch(res => {
        console.log(res)
      })
    },
    getList() {
      const params = {
        channel_id: this.form.channel_id || null,
        item_id: this.form.item_id || null,
        order_type: '拆条',
        date: this.form.date,
        processing_require: this.form.processing_require,
        task_id: this.form.task_id,
        page: this.page,
        limit: this.limit
      }
      this.$get('/production/publicTask', params).then(res => {
        const { data } = res
        this.tableData = data.list
        this.total = data.total
      }).catch(res => {
        console.log(res)
      })
    },

    startTask(item) {
      openQweb(item.task_id)
    }
  }
}
</script>

<style lang='scss' scoped>
.public-wrap{
  .search-top{
    display: flex;
    flex-wrap: wrap;
    .btns{
      justify-content: flex-end;
    }
    .item-search{
        display: flex;
        margin-right: 10px;
        margin-top: 10px;
        ::v-deep.el-input__inner{
          width: 193px;
        }
        span{
          min-width: 80px;
          font-size: 14px;
          margin-top: 10px;
          color:#606266;
          font-size: 14px;
          font-weight: 700;
          text-align: center;
        }
    }
  }
  .table-list{
    margin-top: 30px;
     .start-task{
        font-size: 14px;
        color: #167CFF;
        margin-right: 10px;
        font-weight: 500;
      }
  }
  .pagination-list{
    width: 100%;
    text-align: right;
    margin-top: 30px;
  }
}
</style>
