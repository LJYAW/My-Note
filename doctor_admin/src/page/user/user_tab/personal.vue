<!--
 * @Author: zll
 * @Date: 2020-12-16 12:11:37
 * @LastEditTime: 2020-12-22 12:05:53
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /medical_cms_web/src/page/user/user_tab/personal.vue
-->
<template>
  <div class="person_wrap">
    <div class="searchBox">
      <div class="d-flex flex-1">
        <el-input
          placeholder="搜索姓名或手机号"
          v-model="form.name"
          clearable>
        </el-input>
        <div class="search_img" @click="search()">
          <img src="../../../assets/images/search.png" alt="">
        </div>
      </div>
      <div class="ml-20px d-flex flex-1">
        <span style="min-width:80px" class="mt-10px">审核状态:</span>
        <el-select v-model="form.status" placeholder="请选择审核状态" @change="search()" class="status">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.name"
            :value="item.value">
          </el-option>
        </el-select>
      </div>
      <div class="user_count d-flex flex-1">
        <span>注册用户数:{{total}}</span>
      </div>
    </div>
    <div class="tableBox">
      <table class="table" style="table-layout:fixed;width:100%;">
        <thead>
          <tr>
            <th>姓名</th>
            <th>头像</th>
            <th>医院</th>
            <th>科室</th>
            <th>医生职称</th>
            <th>手机号</th>
            <th>邀请企业</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody class="tbody">
          <tr v-if="!this.tableData.length">
            <td style="border:none;">暂无数据</td>
          </tr>
          <tr v-for="(item,index) in tableData" :key="index">
            <td>
              <a style="color:#2d86e4;margin-right:10px" @click="detailsopertion(item)">{{item.nickname}}</a>
            </td>
            <td>
              <img :src="item.avatar_url" alt="" class="avatar_url" v-if="item.avatar_url">
            </td>
            <td>{{item.legal_name}}</td>
            <td>{{item.department}}</td>
            <td>{{item.job_title}}</td>
            <td>{{item.phone}}</td>
            <td>{{item.company_name}}</td>
            <td>
              <div class="d-flex">
                <!-- <a style="color:#2d86e4;margin-right:10px" @click="recharge(item)">充值</a> -->
                <a v-if="item.status !== '审核通过'" style="color:#2d86e4;margin-right:10px" @click="examine(item)">审核</a>
                <!-- <a style="color:#C51B19;margin-right:10px" @click="deletes(item)">删除</a> -->
              </div>
            </td>
          </tr>
        </tbody>
      </table>
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
import recharge from '../models/recharge'
export default {
  name: '',
  data() {
    return {
      options: [
        {
          name: '全部',
          value: ''
        },
        {
          name: '待审核',
          value: '待审核'
        },
        {
          name: '审核通过',
          value: '审核通过'
        }
        // {
        //   name: '审核不通过',
        //   value: '审核不通过'
        // }
      ],
      tableData: [],
      loading_status: false,
      total: null,
      form: {
        page: 1,
        limit: 20,
        status: '',
        type: '个人'
      },
      last_page: ''
    }
  },
  components: {},
  computed: {
    userDetails() {
      return this.$route.query.user
    }
  },
  watch: {},
  methods: {
    examine(item) {
      this.$router.push({
        path: `/user/person_deatils`,
        query: {
          item: JSON.stringify(item),
          type: 'examine'
        }
      })
    },
    // 充值
    recharge(item) {
      let obj = {
        content: recharge,
        title: '充值',
        width: '300px',
        height: '260px',
        data: {}
      }
      this.$layerIfream(obj)
    },
    search() {
      this.getList()
    },
    detailsopertion(item) {
      this.$router.push({
        path: `/user/person_deatils`,
        query: {
          item: JSON.stringify(item),
          type: 'see'
        }
      })
    },
    getList() {
      this.tableData = []
      this.loading_status = true
      this.$get('/admin/user/user-list', {
        params: this.form
      })
        .then(
          res => {
            console.log('getList -> res', res)
            if (res.data.code == '0000') {
              this.total = res.data.data.total
              this.tableData = res.data.data.list
              // this.last_page = res.data.data.last_page
              this.loading_status = false
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
.avatar_url {
  width: 50px;
  height: 50px;
  // object-fit: cover;
}
</style>
