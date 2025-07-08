<!--
 * @Author: your name
 * @Date: 2021-05-28 11:45:46
 * @LastEditTime: 2021-08-03 15:46:51
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/task_mgt/components/addTask.vue
-->
<template>
  <div class="edit-task">
    <h3>编辑任务</h3>
    <!-- 编辑任务表单 -->
    <el-form ref="ruleForm" :model="form" label-position="left" label-width="120px" :rules="rules">
      <el-form-item label="资源类型" prop="resourcetype">
        <el-radio-group v-model="form.resourcetype">
          <el-radio v-for="(item,index) in baseData.typeData" :key="item" :label="index+1">{{ item }}</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="频道名称" prop="channel_id">
        <el-select v-model="form.channel_id" clearable filterable placeholder="请选择频道" @change="handleChange">
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

      <el-form-item v-if="form.zmflag===1" label="">
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
        <div v-if="pathStatus=='release'">
          <el-input v-model="form.source" placeholder="请输入路径来源" clearable />
        </div>
        <div v-else>
          {{ form.source }}
          <base-btn class="release-btn" @click="release">释放路径</base-btn>
        </div>
      </el-form-item>

      <el-form-item label="列表页爬取地址">
        <el-input v-model="form.listurl" placeholder="请输入列表页 URL" />
      </el-form-item>

      <el-form-item label="播放页爬取地址">
        <el-input v-model="form.playurl" placeholder="请输入列表页 URL" />
      </el-form-item>

      <el-form-item v-if="form.resourcetype!==2" label="爬取频次">
        <el-radio-group v-model="frequency_type">
          <el-radio :label="1">指定日期</el-radio>
          <el-radio :label="2">频次</el-radio>
        </el-radio-group>
        <div v-if="frequency_type===1">
          <BroadcastTime ref="broadcastTime" :broadcast-arr="broadcastArr" />
          <p>默认每日 24 点</p>
        </div>
        <div v-else>
          <el-select v-model="frequency_time">
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
      // 资源类型
      typeData: ['整档', '单集'],
      // 业务
      workData: ['KG', 'Feed'],
      form: {
        business: []
      },
      codeList: [{ codeType: 'zmbq', coverCode: [] }],
      itemData: [],
      checkedOther: false,
      otherWork: '',
      rules: rules,
      // 路径状态
      pathStatus: '',
      broadcastArr: [],
      frequency_type: 1,
      frequency_time: 'pc:1',
      baseData: baseData,
      codeType: ['zmbq', 'zmgq']
    }
  },
  computed: {
    ...mapGetters(['channelData'])
  },
  watch: {
  },
  async created() {
    !this.channelData.length && await this.$store.dispatch('channel/getChannelData')
    await this.getTaskDetail()
    this.getItemList()
  },
  mounted() {

  },
  methods: {
    handleChange(val) {
      if (val) {
        this.getItemList()
      }
      this.form.program_id = null
    },
    async getTaskDetail() {
      const { data, err } = await this.$get(`/jobs/${this.$route.query.id}`)
      if (err) {
        this.$message.error(err.msg || err)
        return
      }
      this.form = JSON.parse(JSON.stringify(data.data))

      // 遮码参数回显
      if (data.data.zmflag === 1) {
        this.codeList = []
        this.codeType.forEach(item => {
          this.form[item] = ''
          if (data.data[item]) {
            const arr = data.data[item].split('|')
            arr.forEach((params) => {
              this.codeList.push({
                codeType: item,
                coverCode: params.split(':')
              })
            })
          }
        })
      }
      // 业务回显
      this.setBusiness()
      // 爬取频次回显
      if (this.form.frequency.indexOf('pc') === -1) {
        this.setBroadcastArr()
        this.frequency_type = 1
      } else {
        this.frequency_time = this.form.frequency
        this.frequency_type = 2
      }
    },
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
    // 业务回显
    setBusiness() {
      const business = this.form.business.split('、')
      this.form.business = business.splice(0, 2)
      if (business.length) {
        this.checkedOther = true
        this.otherWork = business[0]
      }
    },
    // 获取爬取频次数据
    setBroadcastArr() {
      if (!this.form.frequency) return
      const frequency = this.form.frequency.split(',')
      frequency.forEach(item => {
        const dataArr = item.split(':')
        const index = this.broadcastArr.findIndex(time => time.date === dataArr[0])
        if (index !== -1) {
          this.broadcastArr[index].time_data.push(dataArr[1])
        } else {
          this.broadcastArr.push({
            date: dataArr[0],
            time_data: [dataArr[1]]
          })
        }
      })
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
          this.editTask()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    async editTask() {
      const { data, err } = await this.$put(`/jobs/${this.$route.query.id}`, this.getEditParams())
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '编辑任务成功'
        })
        this.backTo()
      }
    },
    // 获取更新任务参数
    getEditParams() {
      const params = JSON.parse(JSON.stringify(this.form))
      params.programTimeEnd = !params.programTimeEnd ? '' : params.programTimeEnd
      params.programTimeStart = !params.programTimeStart ? '' : params.programTimeStart
      // 业务
      this.checkedOther && params.business.push(this.otherWork)
      const channel_obj = this.channelData.find((item) => item.channel_uuid === this.form.channel_id)
      params.channel_name = channel_obj ? channel_obj.name : ''
      const program_obj = this.itemData.find((item) => item.item_uuid === this.form.program_id)
      params.program_name = program_obj ? program_obj.name : ''
      params.business = params.business.join('、')
      // 爬取频次
      params.frequency = this.frequency_type === 1 ? this.setFrequency() : this.frequency_time
      // 遮码参数
      if (params.zmflag === 2) {
        params.zmbq = ''
        params.zmgq = ''
      } else {
        const obj = this.codeList.reduce((res, cur) => {
          res[cur.codeType] ? res[cur.codeType].push(cur.coverCode.join(':')) : res[cur.codeType] = [cur.coverCode.join(':')]
          return res
        }, {})
        for (var key in obj) {
          params[key] = obj[key].join('|')
        }
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
    backTo() {
      this.$router.push({
        path: '/task'
      })
    },
    // 释放路径
    release() {
      this.pathStatus = 'release'
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
.edit-task{
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
        .release-btn{
            margin-left: 100px;
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
