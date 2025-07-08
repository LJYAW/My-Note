<!--
 * @Author: your name
 * @Date: 2021-07-05 17:35:09
 * @LastEditTime: 2021-07-15 17:53:05
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/views/users/components/add.vue
-->
<template>
  <div class="add-user">
    <div class="form-title">{{ title }}</div>
    <el-form ref="form" :model="form" :inline="true" class="form" :rules="rules">
      <el-form-item class="item-list">
        <span class="form-label">公司名称</span>
        <el-input v-model="form.company" placeholder="请输入内容" disabled />
      </el-form-item>
      <el-form-item class="item-list" prop="setDateTime">
        <span class="form-label">开设时间</span>
        <el-input v-model="setDateTime" placeholder="请输入内容" disabled />
      </el-form-item>
      <el-form-item class="item-list">
        <span class="form-label">账号信息</span>
        <el-input v-model="form.names" placeholder="请输入内容" disabled />
      </el-form-item>
      <el-form-item class="item-list" prop="endtime">
        <span class="form-label">有效时间</span>
        <el-date-picker
          v-model="form.endtime"
          type="datetime"
          placeholder="选择有效时间"
          value-format="timestamp"
        />
      </el-form-item>
      <el-form-item class="item-list" prop="passwd">
        <span class="form-label">密码</span>
        <el-input id="valueInput" v-model="form.passwd" v-Alphabet placeholder="请输入内容" />
      </el-form-item>
      <el-form-item class="item-list" prop="saleuser">
        <span class="form-label">销售人员</span>
        <el-input v-model="form.saleuser" placeholder="请输入内容" />
      </el-form-item>
      <el-form-item class="item-list" prop="mobile">
        <span class="form-label">绑定手机</span>
        <el-input v-model="form.mobile" placeholder="请输入内容" />
      </el-form-item>
      <el-form-item class="item-list" prop="handsflag">
        <span class="form-label">加入人工判断</span>
        <div class="radio-box">
          <el-radio v-model="form.handsflag" :label="1">是</el-radio>
          <el-radio v-model="form.handsflag" :label="2">否</el-radio>
        </div>
      </el-form-item>
      <el-form-item class="item-list" prop="emails">
        <span class="form-label">绑定邮箱</span>
        <el-input v-model="form.emails" placeholder="请输入内容" />
      </el-form-item>
      <el-form-item class="item-list" prop="status">
        <span class="form-label">用户状态</span>
        <div class="radio-box">
          <el-radio v-model="form.status" :label="2">已停用</el-radio>
          <el-radio v-model="form.status" :label="1">已开启</el-radio>
        </div>
      </el-form-item>
      <el-form-item class="item-list" prop="type">
        <span class="form-label">管理员权限</span>
        <div class="radio-box">
          <el-radio v-model="form.type" :label="1">开启</el-radio>
          <el-radio v-model="form.type" :label="0">关闭</el-radio>
        </div>
      </el-form-item>
      <el-form-item class="channel" prop="channelsuuids">
        <span class="form-label">开通频道</span>
        <div class="tags-box" @click="tagsClick()">
          <div class="tags-list">
            <div v-for="(item) in tags" :key="item.id" class="tags">{{ item.channelsname }}</div>
          </div>
        </div>
      </el-form-item>
      <el-form-item class="btn">
        <el-button type="primary" class="add" @click="edit('form')">保存</el-button>
        <el-button class="cale" @click="back()">取消</el-button>
      </el-form-item>
    </el-form>
    <base-dialog :show.sync="dialogVisible" :show-close="false" width="800px">
      <AddTag :tags="tags" @save="resetData" />
    </base-dialog>
  </div>
</template>
<script>
import AddTag from '../model/index'
import getYMDHMS from '../js/time'
import mixin from '../js/minx'
import rules from '../js/validatae'
import '@/utils/directive'

export default {
  components: {
    AddTag
  },
  mixins: [mixin],
  props: {
  },
  data() {
    return {
      rules: rules,
      times: '',
      dialogVisible: false,
      title: '用户信息',
      uuidList: [],
      form: {
        company: '',
        create_time: '',
        names: '',
        endtime: '',
        passwd: '',
        saleuser: '',
        mobile: '',
        handsflag: '',
        emails: '',
        status: '',
        channelsuuids: '',
        type: 0
      },
      list: [],
      tags: []
    }
  },
  computed: {
    id() {
      return this.$route.query.id
    },
    setDateTime() {
      const time = getYMDHMS(this.times * 1000)
      return time
    }
  },
  watch: {
    channelData(val) {
      if (val) {
        this.compareData(this.list)
      }
    }
  },
  created() {
    this.init()
  },
  mounted() {
  },
  methods: {
    // 数组比较筛选相同元素
    compareData(arr) {
      if (arr.length > 0) {
        const tasg = arr
        const chnnel = this.channelData
        const list = [...chnnel].filter(x => [...tasg].some(y => y === x.channelsuuid))
        this.tags = list
      }
    },
    // 数据初始化
    async init() {
      const res = await this.$get(`/v1/user/${this.id}`)
      if (res.err) {
        this.$message.error(res.err.msg)
        return
      }
      const { data } = res.data
      this.times = data.create_time
      Object.keys(this.form).forEach(item => {
        this.form[item] = data[item]
      })
      this.form.endtime = this.form.endtime * 1000
      this.list = res.data.data.channelsuuids ? res.data.data.channelsuuids.split(',') : []
      this.compareData(res.data.data.channelsuuids ? res.data.data.channelsuuids.split(',') : [])
    },
    // 更新tags
    resetData(data) {
      this.dialogVisible = false
      if (!data) {
        return
      }
      this.tags = data
      this.uuidList = this.tags.map(item => {
        return item.channelsuuid
      })
      this.form.channelsuuids = this.uuidList.join(',')
    },
    // 点击显示标签弹框
    tagsClick() {
      this.dialogVisible = true
    },
    async submitData(params) {
      const res = await this.$put(`/v1/user/${this.id}`, params)
      if (res.err) {
        this.$message.error(res.err.msg)
      } else {
        this.$router.push({
          path: '/user'
        })
      }
    },
    // 编辑
    edit(formName) {
      var params = JSON.parse(JSON.stringify(this.form))
      params.endtime = parseInt(this.form.endtime / 1000) || ''
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.submitData(params)
        } else {
          return false
        }
      })
    },
    // 返回
    back() {
      this.$router.push({
        path: '/user'
      })
    }
  }
}
</script>

<style scoped lang="scss">
@import "../components/scss/index.scss"
</style>
