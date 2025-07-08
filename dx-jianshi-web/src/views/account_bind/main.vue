<template>
  <div class="acount-bind container bc-white">
    <div class="d-flex justify-content-between pt-23px pb-28px px-35px border-top align-items-center">

      <p>百家号绑定</p>
      <el-button type="primary"
                 round
                 size="mini"
                 @click="bind">添加账号</el-button>

    </div>

    <div>
      <table class="table w-100 border-top">
        <tr>
          <td>
            <p class="pl-15px">百家号昵称</p>
          </td>
          <td>百家号账号状态</td>
          <td>操作</td>
        </tr>
        <tr v-for="(item,index) in bind_list"
            :key="index">
          <td>
            <p class="pl-15px">{{item.nickname}}</p>
          </td>
          <td>
            <p>{{item.audit_status}}</p>
          </td>
          <td>
            <div class="d-flex">

              <el-button class="mr-10px fc-blue-348"
                         style="color: #3485e1"
                         @click.native="getCanpublishNum(item)"
                         :disabled="text_loading"
                         type="text">查询剩余发文篇数</el-button>

              <a class="fc-c"
                 @click="deleteAccount(item)">删除</a>
            </div>
          </td>
        </tr>
      </table>
    </div>
    <baijiahaoM v-if="modalName == 'baijiahaoM'"
                ref="baijiahaoM"
                @Modalclose="Modalclose"
                :parentObj="parentObj"></baijiahaoM>
  </div>
</template>

<script>
import baijiahaoM from './baijiahao_m'

export default {
  name: 'account',
  props: {},
  data() {
    return {
      bind_list: [],
      parentObj: {},
      modalName: '',
      code: this.$route.query.code,
      text_loading: false
    }
  },
  computed: {},
  watch: {},
  methods: {
    Modalclose() {
      this.modalName = ''
      this.parentObj = {}
    },
    bind() {
      this.modalName = 'baijiahaoM'
      // this.$nextTick(() => {
      //   this.$store.commit('modalShow', 'baijiahaoM')
      // })
      this.$get('ling-qu/authorize-url').then(res => {
        if (res.data.code == '0000') {
          let authorize_url = res.data.data.authorize_url
          let protocol = location.protocol

          if (!protocol.includes('https')) {
            authorize_url = authorize_url.replace('https', 'http')
          }
          window.open(authorize_url, '_blank')
        }
      })
    },
    getCanpublishNum(item) {
      this.text_loading = true
      this.$get('/ling-qu/can-publish-num', { params: { third_account_info_id: item.third_account_info_id } }).then(res => {
        if (res.data.code == '0000') {
          this.$alertMsg(`当前剩余发文篇数${res.data.data.num}`)
        }
        this.text_loading = false
      })
    },
    linquBindBiajiahao() {
      if (!this.code) return

      this.$post('/ling-qu/bind-baijiahao', { code: this.code }).then(res => {
        if (res.data.code == '0000') {
          this.$alertMsg(res.data.msg)
          localStorage.setItem('third_account_reload', JSON.stringify(this.code))
          window.close()
        }
      })
    },
    getBindList() {
      this.$get('/user/bound-platform-list').then(res => {
        if (res.data.code == '0000') {
          this.bind_list = res.data.data
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    },
    deleteAccount(item) {
      this.$confirm('确定删除该账号吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$post('/user/unbind-baijiahao', { third_account_info_id: item.third_account_info_id }).then(res => {
            this.$alertMsg(res.data.msg)
            this.getBindList()
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    changeAccount(item) {
      this.parentObj = JSON.parse(JSON.stringify(item))
      this.bind()
      this.$nextTick(() => {
        this.$refs.baijiahaoM.forParentObj()
      })
    }
  },
  components: { baijiahaoM },
  created() {
    this.getBindList()
    this.linquBindBiajiahao()
    // 用来维护多个标签页之间登录信息的同步
    // 用来维护多个标签页之间登录信息的同步
    let self = this
    var storageHandler = function(e) {
      if (e.key == 'third_account_reload') {
        self.getBindList()
      }
    }
    window.addEventListener('storage', storageHandler, false)
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
.acount-bind {
  background-color: #fff;
  .table {
    tr:first-child {
      td {
        background: #eaeaea;
        color: #101010;
        font-weight: bold;
      }
    }
  }
}
</style>
