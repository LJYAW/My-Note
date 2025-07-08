<template>
  <div class="publish-history container">

    <div class="history-hader py-25px px-35px">发布历史</div>

    <!-- <loading /> -->
    <div>
      <table class="table w-100">
        <tr>
          <td>视频</td>
          <td>视频标题</td>
          <td>分类</td>
          <td>发布时间</td>
          <td>发布至账号</td>
          <td>发布状态</td>
          <td>操作</td>
        </tr>
        <tr v-for="(item,index) in hository_list"
          :key="index">
          <td>
            <div class="img-wrap">
              <img :src="item.cover_pic"
                width="80"
                height="45">
            </div>
          </td>
          <td>
            <span>{{item.title}}</span>
          </td>
          <td>
            <span>{{item.category_name}}</span>
          </td>
          <td>
            <span>{{item.publish_time}}</span>
          </td>
          <td>
            <span>{{item.platform_name}}</span>
          </td>
          <td>
            <span>{{item.status}}</span>
            <p v-if="item.status == '发布失败'"
              :title="item.fail_msg"
              style="max-width: 200px"
              class="text-ellipsis mt-5px">{{item.fail_msg}} 失败原因：失败原因：失败原因：失败原因：失败原因：失败原因：</p>
          </td>
          <td>
            <a class="fc-blue"
              @click="goToBaijiahao(item)">查看</a>
          </td>
        </tr>
      </table>

      <el-pagination @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="limit"
        class="mt-30px mr-40px fl-right"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: 'history',
  props: {},
  data() {
    return {
      video_status: 1,
      orderby_type: 'asc', // asc/desc
      keywords: '',
      hository_list: [],
      page: 1,
      limit: 10,
      total: 0
    }
  },
  computed: {},
  watch: {},
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
      this.limit = val
      this.getHoistoryList()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.page = val
      this.getHoistoryList()
    },
    getHoistoryList() {
      let params = {
        status: this.video_status,
        page: this.page,
        limit: this.limit
      }
      this.$get('/short-video/list', { params: params }).then(res => {
        if (res.data.code == '0000') {
          this.hository_list = res.data.data.list
          this.total = res.data.data.total
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    },
    goToBaijiahao(item) {
      this.$actions.openWin(item.baijiahao_url)
    }
  },
  components: {},
  created() {
    this.getHoistoryList()
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
.publish-history {
  background: #fff;
  min-height: 750px;
  .history-hader {
    border-bottom: 1px solid #f2f2f2;
  }
  .table {
    tr:first-child {
      td {
        background: #eaeaea;
        color: #101010;
        font-weight: bold;
      }
    }
    .img-wrap {
      width: 80px;
      height: 45px;
      background: #e5e5e5;
    }
  }
}
</style>
