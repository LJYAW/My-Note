<!--
 * @Author: your name
 * @Date: 2021-05-10 15:17:19
 * @LastEditTime: 2021-05-14 15:46:49
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/sample_manage/SampleQueue.vue
-->
<template>
  <div class="sample-queue">
    <el-form :inline="true">
      <el-form-item label="状态">
        <el-select v-model="form.status" placeholder="请选择状态" clearable>
          <el-option v-for="item in sample_status" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="频道名称">
        <el-select v-model="form.channel_id" filterable clearable placeholder="请选择频道" style="width:100%">
          <el-option
            v-for="item in channelData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="广告名称">
        <el-input v-model="form.ad_title" placeholder="请输入广告名称" clearable />
      </el-form-item>

      <el-form-item label="广告ID">
        <el-input v-model="form.ad_id" placeholder="请输入广告名称" clearable />
      </el-form-item>

      <el-form-item label="更新时间">
        <el-date-picker
          v-model="form.update_time"
          type="datetimerange"
          value-format="yyyy-MM-dd HH:mm:ss"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>

      <div class="btn-wrap">
        <base-btn @click="search">查询</base-btn>
        <base-btn @click="clear">清除</base-btn>
      </div>
    </el-form>
    <QueueTable
      v-loading="loading"
      :table-data="tableData"
      :pag="pag"
      @currentChange="handleCurrentChange"
      @sizeChange="handleSizeChange"
      @update="getData()"
    />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import QueueTable from './QueueTable'
export default {
  components: {
    QueueTable
  },
  props: {

  },
  data() {
    return {
      sample_status: ['视频转码中', '待编辑', '入库中', '失败', '入库成功'],
      form: {
        status: '',
        channel_id: null,
        ad_title: '',
        ad_id: null,
        update_time: []
      },
      pag: {
        page: 1,
        limit: 10,
        total: 0
      },
      tableData: [],
      loading: true
    }
  },
  computed: {
    ...mapGetters(['channelData'])
  },
  watch: {

  },
  created() {
    if (!this.channelData.length) {
      this.$store.dispatch('channel/getChannelData')
    }
    this.getData()
  },
  mounted() {

  },
  methods: {
    handleCurrentChange(val) {
      this.pag.page = val
      this.getData()
    },
    handleSizeChange(val) {
      this.pag.limit = val
      this.getData()
    },
    // 搜索
    search() {
      this.pag.page = 1
      this.getData()
    },
    // 清除搜索
    clear() {
      Object.keys(this.form).forEach((item) => {
        this.form[item] = ''
      })
      this.form.update_time = []
      this.getData()
    },
    getData() {
      this.loading = true
      const params = {
        status: this.form.status,
        channel_id: this.form.channel_id,
        ad_title: this.form.ad_title,
        ad_id: this.form.ad_id,
        updated_at_start: this.form.update_time ? this.form.update_time[0] : null,
        updated_at_end: this.form.update_time ? this.form.update_time[1] : null,
        page: this.pag.page,
        limit: this.pag.limit
      }
      this.$get('/ad-sample/queue-search', params).then((res) => {
        this.tableData = res.data.list
        this.pag.total = res.data.total
      }).catch((error) => {
        console.log(error.msg)
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>

<style scoped lang="scss">
.sample-queue{
  padding: 20px;
  display: flex;
  flex-direction: column;
  .el-form{
    .el-form-item{
      margin-right: 20px;
    }
  }
}
</style>
