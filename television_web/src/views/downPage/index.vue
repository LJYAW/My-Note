<!--
 * @Author: your name
 * @Date: 2021-07-20 11:16:38
 * @LastEditTime: 2021-07-20 17:10:47
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /television_web/src/views/downPage/index.vue
-->
<template>
  <div class="down-page">
    <div class="select-wrap">
      <el-date-picker
        v-model="form.day"
        value-format="yyyy-MM-dd"
        type="date"
        placeholder="选择日期"
      />
      <base-btn size="big" @click="getTableData()">查询</base-btn>
    </div>
    <el-table :data="tableData" border>
      <el-table-column prop="channel_name" label="频道" />
      <el-table-column prop="day" label="日期" />
      <el-table-column label="时间">
        <template slot-scope="scope">
          {{ scope.row.start_time }} - {{ scope.row.end_time }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <base-btn type="text" @click="download(scope.row.video_url)">下载</base-btn>
        </template>
      </el-table-column>
    </el-table>
    <base-pag
      :total="total"
      :limit="form.limit"
      :page="form.page"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
    />

  </div>
</template>

<script>
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      form: {
        day: '',
        page: 1,
        limit: 10
      },
      total: 0,
      tableData: []
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getTableData()
  },
  mounted() {

  },
  methods: {
    async getTableData() {
      const { err, data } = await this.$get('/epg/epg-videos', this.form)
      this.tableData = data.data.list
      this.total = data.data.total
    },
    handleCurrentChange(val) {
      this.form.page = val
      this.getTableData()
    },
    handleSizeChange(val) {
      this.form.limit = val
      this.getTableData()
    },
    download(url) {
      const a = document.createElement('a')
      a.download = 'download'
      a.href = url
      a.target = '_blank'
      a.click()
    }
  }
}
</script>

<style scoped lang="scss">
.down-page{
    .select-wrap{
        margin-bottom: 30px;
        button{
            margin-left: 20px;
        }
    }
}
</style>
