<!--
 * @Author: zzx
 * @Date: 2020-07-30 17:55:07
 * @LastEditTime: 2020-07-31 14:59:51
 * @FilePath: /zhijian_web/src/views/invition_table/index.vue
-->
<template>
  <div>

    <h3 class="text-center my-30px">申请列表页面</h3>
    <el-table :data="tableData"
              style="width: 100%">
      <el-table-column prop="created_at"
                       label="日期"
                       width="180">
      </el-table-column>
      <el-table-column prop="name"
                       label="姓名"
                       width="120">
      </el-table-column>
      <el-table-column prop="phone"
                       label="手机号">
      </el-table-column>
      <el-table-column label="自媒体链接"
                       width="180">
        <template slot-scope="scope">
          <el-input class="media-url"
                    v-model="scope.row.media_url"></el-input>
        </template>
      </el-table-column>
      <!-- <el-table-column prop="media_url"
                       height="30"
                       :show-overflow-tooltip="true"
                       label="自媒体链接">
        <template slot-scope="scope">
          <p></p>
        </template>
      </el-table-column> -->
      <el-table-column prop="send_status"
                       label="短信发送状态">
      </el-table-column>
      <el-table-column prop="status"
                       label="审核状态">
      </el-table-column>
      <el-table-column prop="invitation_code"
                       label="邀请码">
      </el-table-column>
      <el-table-column fixed="right"
                       label="操作"
                       width="100">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row, '驳回')"
                     type="text"
                     size="small">驳回</el-button>
          <el-button type="text"
                     style="color: #3485e1"
                     class="fc-999"
                     @click="handleClick(scope.row, '通过')"
                     size="small">通过</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination background
                   class="mt-20px"
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   :current-page.sync="currentPage"
                   layout="prev, pager, next"
                   :total="total">
    </el-pagination>
  </div>
</template>

<script>
export default {
  props: {},
  data() {
    return {
      tableData: [],
      currentPage: 1,
      total: 0,
      page: 1,
      limit: 10
    }
  },
  computed: {},
  watch: {},
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.page = val
      this.getList()
    },
    handleClick(item, type) {
      var params = {
        status: type,
        id: item.id
      }
      this.$post('/user/send-invitation-code', params).then(res => {
        let data = res.data.data

        console.log('handleClick -> params', params)
        console.log('handleClick -> data', data)
        // if (res.data.code == '0000') {
        // } else {
        // }
        this.$alertMsg(res.data.msg)
        this.getList()
      })
    },
    getList() {
      let params = {
        page: this.page,
        limit: this.limit
      }
      this.$get('/user/apply-invitation-list', { params: params }).then(res => {
        let data = res.data.data
        if (res.data.code == '0000') {
          this.tableData = data.list
          this.total = data.total
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    }
  },
  components: {},
  created() {
    this.getList()
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
.media-url {
  width: 120px;
  overflow: hidden;
  overflow-x: auto;
}
</style>
