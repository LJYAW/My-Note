<!--
 * @Author: wxh
 * @Date: 2020-11-10 11:21:01
 * @LastEditTime: 2020-11-19 11:06:58
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/assign_platform/index.vue
-->
<template>
  <div class="assign-wrap">
    <div class="assign-list-con">
      <loading v-if="status_loading" />
      <!-- 任务列表 -->
      <div v-else class="assign-list-section">
        <assign-list
          v-for="(list,index) in assignList"
          :key="'assignList' + index"
          :list="list"
          @click="handleClickAssignList(list)" />

        <!-- 空状态提示 -->
        <div v-if="!assignList.length" class="assign-list-null">
          <img width="322px" height="254px" :src="assignObj.imgUrl" alt="">
          <p style="text-align:center;color:#999;">{{assignObj.tip}}</p>
        </div>
      </div>

    </div>

    <!-- 分页 -->
    <el-pagination
      :hide-on-single-page="assignList.length <= limit"
      background
      class="assign-main-page"
      :page-size="limit"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page.sync="currentPage"
      layout="prev, pager, next"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
import AssignList from './assign_list.vue'
const img_m_null = require('./../../../assets/images/assign_m_null.png')
const img_p_null = require('./../../../assets/images/assign_p_null.png')
const my = {
  imgUrl: img_m_null,
  tip: '没有任务记录，快去发布你的任务让更多人看到吧！',
  url: 'task/claimed-task-list'
}
const platform = {
  imgUrl: img_p_null,
  tip: '暂无任务发放',
  url: 'task/list'
}
export default {
  props: {
    assignType: {
      type: String,
      required: true
    }
  },
  components: { AssignList },
  data() {
    return {
      status_loading: false,
      assignObj: {},
      page: 1,
      limit: 10,
      assignList: [],
      activeAssignIndex: -1,
      currentPage: 1,
      total: 0
    }
  },
  computed: {},
  watch: {},
  methods: {
    handleAssignObj() {
      this.assignObj = this.assignType == 'my' ? my : platform
    },
    reqAssignList() {
      this.status_loading = true

      this.$get(this.assignObj.url, { params: { page: this.page, limit: this.limit } })
        .then(res => {
          if (res.data.code == '0000') {
            let listData = res.data.data
            this.assignList = listData.list
            this.currentPage = Number(listData.page)
            this.total = listData.total
          } else {
            this.$alertMsg('请求任务失败')
          }
        })
        .finally(() => {
          this.status_loading = false
        })
    },
    handleClickAssignList(list) {
      window.sessionStorage.setItem('assignDetail', JSON.stringify(list))
      const url = this.assignType == 'my' ? '/my-assign' : '/assign-platform'
      this.$router.push({
        path: url + '/assign-detail',
        query: { type: this.assignType }
      })
    },
    handleSizeChange() {},
    handleCurrentChange(val) {
      this.page = val
      this.reqAssignList()
    }
  },
  created() {
    this.handleAssignObj()
  },
  mounted() {
    this.reqAssignList()
  }
}
</script>

<style lang="scss">
.assign-wrap {
  width: 100%;
  height: 100%;
  overflow: auto;
  .assign-list-con {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    width: 1000px;
    margin: 40px auto 0;
    .assign-list-section {
      width: 100%;
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
    }
    .assign-list {
      height: 400px;
      img {
        background-color: black;
      }
      .el-card {
        height: 100%;
        .assign-list-info {
          flex: 1;
          .assign-list-title {
            .assign-title {
              width: 100%;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
            }
          }
        }
      }
    }
    .assign-list-null {
      width: 100%;
      margin-top: 100px;
      text-align: center;
    }
  }
  .assign-main-page {
    margin: 20px auto;
    text-align: center;
  }
}
</style>
