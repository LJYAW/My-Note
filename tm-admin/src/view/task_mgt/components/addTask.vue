<!--
 * @Author: your name
 * @Date: 2021-05-28 11:45:46
 * @LastEditTime: 2021-08-03 15:37:39
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/task_mgt/components/addTask.vue
-->
<template>
  <div class="add-task">
    <h3>新增任务</h3>
    <!-- 新增任务表单 -->
    <el-form ref="ruleForm" :model="form" label-position="left" label-width="120px" :rules="rules">
      <el-form-item label="资源类型" prop="resourcetype">
        <el-radio-group v-model="form.resourcetype">
          <el-radio v-for="(item,index) in baseData.typeData" :key="item" :label="index+1">{{ item }}</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="频道名称" prop="channel_id">
        <el-select v-model="form.channel_id" clearable filterable placeholder="请选择频道">
          <el-option
            v-for="item in channelData"
            :key="item.channel_uuid"
            :value="item.channel_uuid"
            :label="item.name"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="栏目名称" prop="program_id">
        <el-select v-model="form.program_id" clearable filterable placeholder="请选择栏目">
          <el-option
            v-for="item in itemData"
            :key="item.name"
            :value="item.item_uuid"
            :label="item.name"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="业务" prop="business">
        <el-checkbox-group v-model="form.business">
          <el-checkbox v-for="item in workData" :key="item" :label="item" />
        </el-checkbox-group>
        <el-checkbox v-model="checkedOther">其他</el-checkbox>
        <el-input v-model="otherWork" placeholder="请输入业务" class="other-work" />
      </el-form-item>

      <el-form-item label="是否遮码">
        <el-radio-group v-model="form.zmflag">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="2">否</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item v-if="form.zmflag==1" label="">
        <div v-for="(item,index) in codeList" :key="index" class="code-list">
          <el-radio-group v-model="item.codeType" class="codeRadio">
            <el-radio label="zmbq">标清</el-radio>
            <el-radio label="zmgq">高清</el-radio>
          </el-radio-group>
          <el-form-item label="左:" label-width="30" class="code-item">
            <el-input v-model.number.trim="item.coverCode[0]" />
          </el-form-item>
          <el-form-item label="上:" label-width="30" class="code-item">
            <el-input v-model.number.trim="item.coverCode[1]" />
          </el-form-item>
          <el-form-item label="宽:" label-width="30" class="code-item">
            <el-input v-model.number.trim="item.coverCode[2]" />
          </el-form-item>
          <el-form-item label="高:" label-width="30" class="code-item">
            <el-input v-model.number.trim="item.coverCode[3]" />
          </el-form-item>
          <i v-if="codeList.length<4" class="el-icon-circle-plus-outline" @click="addCode(index)" />
          <i v-if="codeList.length!==1" class="el-icon-remove-outline" @click="deleteCode(index)" />
        </div>

      </el-form-item>

      <el-form-item label="路径来源">
        <el-input v-model="form.source" placeholder="请输入路径来源" clearable />
      </el-form-item>

      <el-form-item label="列表页爬取地址">
        <el-input v-model="form.listurl" placeholder="请输入列表页 URL" />
      </el-form-item>

      <el-form-item label="播放页爬取地址">
        <el-input v-model="form.playurl" placeholder="请输入播放页 URL" />
      </el-form-item>

      <el-form-item v-if="form.resourcetype!==2" label="爬取频次">
        <el-radio-group v-model="frequency_type">
          <el-radio :label="1">指定日期</el-radio>
          <el-radio :label="2">频次</el-radio>
        </el-radio-group>
        <div v-if="frequency_type===1">
          <BroadcastTime ref="broadcastTime" />
          <p>默认每日 24 点</p>
        </div>
        <div v-else>
          <el-select v-model="form.frequency">
            <el-option v-for="i in 23" :key="i" :label="`${i}小时`" :value="`pc:${String(i)}`" />
          </el-select>
        </div>

      </el-form-item>

      <el-form-item label="数据限制">
        <el-date-picker
          v-model="form.programTimeStart"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择日期"
        />
        <el-date-picker
          v-model="form.programTimeEnd"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择日期"
        />
      </el-form-item>

      <el-form-item label="解析说明">
        <BaseUpload
          key="presenters"
          button_name="上传图片"
          class="BaseUploadImg"
          @getFileUrl="getFileUrl"
        />
        <img v-if="form.descpic" :src="form.descpic" alt="" class="cover-img">
      </el-form-item>

      <el-form-item label="现存视频日期" prop="lastdate">
        <el-date-picker
          v-model="form.lastdate"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择日期"
        />
      </el-form-item>
    </el-form>

    <base-btn @click="submit('ruleForm')">提交</base-btn>
    <base-btn type="info" @click="backTo">返回</base-btn>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import rules from '../js/taskRules'
import BroadcastTime from '../components/BroadcastTime'
import baseData from '../js/baseData'
export default {
  components: {
    BroadcastTime
  },
  props: {

  },
  data() {
    return {
      // 栏目列表
      itemData: [],
      form: {
        resourcetype: 1,
        channel_id: null,
        program_id: null,
        source: null,
        business: [],
        listurl: '',
        playurl: '',
        frequency: 'pc:1',
        programTimeStart: '',
        programTimeEnd: '',
        descpic: '',
        zmflag: 1,
        lastdate: ''
        // coverCode: []
      },
      frequency_type: 1,
      codeList: [{ codeType: 'zmbq', coverCode: [] }],
      workData: ['KG', 'Feed'],
      checkedOther: false,
      otherWork: '',
      baseData: baseData,
      rules: rules,
      dialogVisible: false
    }
  },
  computed: {
    ...mapGetters(['channelData'])
  },
  watch: {
    'form.channel_id'(val) {
      if (val) {
        this.getItemList()
      }
      this.form.program_id = null
    }
  },
  created() {
    !this.channelData.length && this.$store.dispatch('channel/getChannelData')
  },
  mounted() {

  },
  methods: {
    // 获取栏目列表
    async getItemList() {
      const channel_id = this.channelData.filter((item) => item.channel_uuid === this.form.channel_id)[0].channel_id
      const params = {
        channel_id: channel_id
      }
      const { data, err } = await this.$get('/jobs/programs', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.itemData = data.data
    },
    openDialog() {
      this.dialogVisible = true
    },
    close() {
      this.dialogVisible = false
    },
    checkCode() {
      var msg = ''
      this.codeList.forEach(item => {
        if (!item.coverCode.length) {
          msg = '请输入遮码参数'
        }
        for (var i = 0; i < item.coverCode.length; i++) {
          if (!String(item.coverCode[i])) {
            msg = '请输入遮码参数'
          }
        }
      })
      return msg
    },
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.checkedOther && !this.otherWork) {
            this.$message.error('请输入其他业务')
            return
          }
          if (this.form.zmflag === 1 && this.checkCode()) {
            this.$message({
              tyep: 'warning',
              message: this.checkCode()
            })
            return
          }
          if (new Date(this.form.programTimeStart).getTime() > new Date(this.form.programTimeEnd).getTime()) {
            this.$message.error('开始开播时间不能小于结束开播时间')
            return
          }
          this.addTask()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    async addTask() {
      const { data, err } = await this.$post('/jobs', this.getAddparams())
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '新增任务成功'
        })
        this.backTo()
      }
    },
    // 获取新增任务参数
    getAddparams() {
      const params = JSON.parse(JSON.stringify(this.form))
      params.programTimeEnd = !params.programTimeEnd ? '' : params.programTimeEnd
      params.programTimeStart = !params.programTimeStart ? '' : params.programTimeStart
      // 频道，栏目
      params.channel_name = this.channelData.filter(item => item.channel_uuid === this.form.channel_id)[0].name
      params.program_name = this.itemData.filter(item => item.item_uuid === this.form.program_id)[0].name
      // 业务
      this.checkedOther && params.business.push(this.otherWork)
      params.business = params.business.join('、')
      // 爬取频次
      if (this.frequency_type === 1) params.frequency = this.setFrequency()
      const obj = this.codeList.reduce((res, cur) => {
        res[cur.codeType] ? res[cur.codeType].push(cur.coverCode.join(':')) : res[cur.codeType] = [cur.coverCode.join(':')]
        return res
      }, {})
      for (var key in obj) {
        params[key] = obj[key].join('|')
      }
      return params
    },
    // 爬取频次
    setFrequency() {
      const arr = []
      if (!this.$refs.broadcastTime || !this.$refs.broadcastTime.tableData.length) return ''
      this.$refs.broadcastTime.tableData.forEach(item => {
        item.time_data.forEach(time => {
          if (time) {
            arr.push(`${item.date}:${time}`)
          }
        })
      })
      return arr.join(',')
    },
    // 上传图片
    getFileUrl(url, file) {
      this.uploadImg(file)
    },
    async uploadImg(file) {
      const formData = new FormData()
      formData.append('file', file)
      const res = await this.$post('/common/uploadfile', formData)
      const { data, err } = res
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.form.descpic = data.data.imgurl
    },
    backTo() {
      this.$router.push({
        path: '/task'
      })
    },
    addCode(index) {
      this.codeList.splice(index + 1, 0, {
        codeType: 'zmbq',
        coverCode: []
      })
    },
    deleteCode(index) {
      this.codeList.splice(index, 1)
    }
  }
}
</script>

<style scoped lang="scss">
.add-task{
    padding: 20px 30px;
    .el-form{
        margin-top: 20px;
        .el-input,.el-select{
            width: 500px;
        }
        .el-date-editor{
            margin-right: 20px;
        }
        .el-checkbox-group{
            display: inline-block;
            margin-right: 20px;
        }
        .other-work{
            margin-left: 20px;
            display: inline-block;
            width: 200px;
        }
        .add-btn{
            margin-left: 20px;
        }
        .el-date-editor{
            width: auto!important;
        }
        .cover-img{
          border: 1px solid #eee;
          margin-top: 20px;
          width: auto;
          height: 100px;
          object-fit: contain;
        }
        .code-list{
          margin-bottom: 20px;
          .codeRadio{
            margin-right: 20px;
          }
          .el-icon-circle-plus-outline{
            margin-right: 10px;
          }
        }

        .code-item{
          display: inline-block;
          margin-right: 20px;
          ::v-deep .el-form-item__content{
            display: inline-block;
          }

          .el-input {
            width: 100px;
          }
        }
    }

}
</style>
