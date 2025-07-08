<!--
 * @Author: zll
 * @Date: 2020-12-02 17:35:36
 * @LastEditTime: 2020-12-16 11:59:39
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /weijian_cms_web/src/page/works_square/index.vue
-->
<template>
  <div class="square_warp">
    <div class="searchBox">
      <div class="searchList justify-content-between">
        <div class="d-flex flex-1">
          <el-input
            placeholder="输入视频标题"
            v-model="form.title"
            clearable>
          </el-input>
          <div class="search_img" @click="search()">
            <img src="../../assets/images/search.png" alt="">
          </div>
        </div>
        <div class="d-flex flex-1 ml-50px">
          <el-input
            placeholder="请输入作者昵称或手机号"
            v-model="form.keyword"
            clearable>
          </el-input>
          <div class="search_img" @click="search()">
            <img src="../../assets/images/search.png" alt="">
          </div>
        </div>
        <div class="btns flex-1">
          <el-button type="primary" round @click="reset()">重置</el-button>
        </div>
      </div>
    </div>
    <div class="ulList">
      <div v-if="!ulData.length" style="color:#999999;">暂无数据</div>
      <ul>
        <li v-for="(item,index) in ulData" :key="index">
          <div>
            <i class="iconfont icondelect square" @click.stop="deletes(item)" plain="true"></i>
            <img :src="item.cover_pic" alt="">
            <div class="title">{{item.title}}</div>
            <div class="autor">
              <span>作者:</span>
              <span>{{item.author_name}}</span>
            </div>
            <div class="time">
              <span>生成时间:</span>
              <span>{{item.finished_time}}</span>
            </div>
          </div>
        </li>
      </ul>
      <loading v-if="loading_status" />

    </div>
    <div class="paginationBox">
      <el-pagination
        background
        @current-change="handleCurrentChange"
        :current-page.sync="form.page"
        :page-size="form.limit"
        layout="prev, pager, next, jumper"
        :total="total"></el-pagination>
    </div>
  </div>
</template>

<script>
import { formatDate } from '@/utils/times'

export default {
  name: '',
  props: {
  },
  data() {
    return {
      total: null,
      loading_status: false,
      form: {
        title: '',
        keyword: '',
        page: 1,
        limit: 20
      },
      ulData: []
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    // 取消
    deletes(item) {
      let params = {
        intelligent_writing_id: item.intelligent_writing_id
      }
      this.$post('/admin/intelligent-creation/unset-show-square', params).then(
        res => {
          if (res.data.code == '0000') {
            this.$message({
              message: '取消成功!',
              type: 'success'
            })
            this.getList()
          } else {
            this.$message({
              message: res.data.msg,
              type: 'warning'
            })
          }
        },
        err => {
          console.log(err)
        }
      )
    },
    // 搜索
    search() {
      this.getList()
    },
    // 重置
    reset() {
      this.total = null
      this.date = ['', '']
      this.form = {
        title: '',
        keyword: '',
        start_date: '',
        end_date: '',
        page: 1,
        limit: 20
      }
      this.getList()
    },
    getList() {
      this.loading_status = true
      this.$get('/admin/intelligent-creation/intelligent-video-list', {
        params: this.form
      })
        .then(
          res => {
            if (res.data.code == '0000') {
              this.loading_status = false
              this.ulData = res.data.data.list
              this.total = res.data.data.total
            }
          },
          err => {}
        )
        .finally(res => {
          this.loading_status = false
        })
    },
    handleCurrentChange(val) {
      this.form.page = val
      this.getList()
    }
  },
  created() {
    this.getList()
  },
  mounted() {}
}
</script>

<style lang="scss" scoped>
.square_warp {
  .searchBox {
    background: #ffffff;
    padding: 20px;
    margin-top: 20px;
    .searchList {
      display: flex;
      margin-top: 20px;
      .btns {
        text-align: right;
      }
      .labeltitle {
        min-width: 80px;
        margin-top: 10px;
        margin-left: 10px;
      }
      .search_img {
        width: 56px;
        height: 36px;
        border-radius: 3px;
        background-color: rgba(43, 121, 223, 100);
        text-align: center;
        line-height: 36px;
        img {
          width: 24px;
          height: 24px;
        }
      }
    }
  }
  .ulList {
    margin-top: 20px;
    ul {
      display: flex;
      flex-wrap: wrap;
      li {
        position: relative;
        width: calc(100% / 5 - 10px);
        background: #ffffff;
        padding: 10px;
        box-sizing: border-box;
        margin-top: 10px;
        margin-right: 10px;
        border-radius: 5px;
        .square {
          position: absolute;
          right: 16px;
          top: 20px;
          &:hover {
            color: #b72b2b;
            cursor: pointer;
          }
        }
        img {
          width: 100%;
          height: 130px;
          object-fit: cover;
        }
        .title {
          height: 38px;
          text-align: left;
          color: #202020;
          font-size: 14px;
          margin-top: 12px;
          line-height: 20px;
          text-overflow: -o-ellipsis-lastline;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          line-clamp: 2;
          -webkit-box-orient: vertical;
        }
        .autor {
          color: #999999;
          font-size: 12px;
          margin: 12px 0;
        }
        .time {
          color: #999999;
          font-size: 12px;
          span {
            line-height: 20px;
          }
        }
      }
    }
  }
  .paginationBox {
    text-align: right;
    margin-top: 20px;
  }
}
</style>

