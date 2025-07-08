<!--
 * @Author: your name
 * @Date: 2021-07-05 15:18:19
 * @LastEditTime: 2021-07-16 16:52:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/views/channel/pages/exportLive/index.vue
-->
<template>
  <div class="export-live">
    <p class="form-title">导入直播流</p>
    <el-form ref="ruleForm" :model="form" :inline="true" :rules="rules">

      <el-form-item class="live-item" prop="channelsuuid">
        <span class="form-label">直播流地址</span>
        <el-select v-model="form.channelsuuid" clearable filterable @change="changeSelectChannel">
          <el-option v-for="item in activeData" :key="item.id" :label="item.channel_cn_name" :value="item.channel_en_name" />
        </el-select>
      </el-form-item>

      <el-form-item prop="channelsname">
        <span class="form-label">频道名称</span>
        <el-input v-model="form.channelsname" placeholder="请输入内容" clearable />
      </el-form-item>

      <el-form-item>
        <span class="form-label">频道归属地</span>
        <Region ref="region" />
      </el-form-item>

      <el-form-item prop="levels">
        <span class="form-label">频道分级</span>
        <el-select v-model="form.levels" clearable filterable>
          <el-option v-for="item in channelLevel" :key="item.id" :label="item.names" :value="item.id" />
          <div style="padding-left: .1rem" @click="showAddDialog('channelLevel')">
            <base-btn type="text">新增<i class="el-icon-circle-plus-outline" /></base-btn>
          </div>
        </el-select>
      </el-form-item>

      <el-form-item prop="types">
        <span class="form-label">频道类型</span>
        <el-select v-model="form.types" clearable filterable>
          <el-option v-for="item in channelType" :key="item.id" :label="item.names" :value="item.id" />
          <div style="padding-left: .1rem" @click="showAddDialog('channelType')">
            <base-btn type="text">新增<i class="el-icon-circle-plus-outline" /></base-btn>
          </div>
        </el-select>
      </el-form-item>

    </el-form>
    <div class="btn-wrap">
      <base-btn type="info" class="cancel-btn" @click="backTo">取消</base-btn>
      <base-btn @click="save('ruleForm')">保存</base-btn>
    </div>
    <base-dialog :show.sync="dialogVisible" :show-close="false" width="60%">
      <AddTag :tag-list="tagList" @addTag="addTag" @deleteTag="deleteTag" @close="close" />
    </base-dialog>
  </div>
</template>

<script>
import AddTag from '@/components/AddTag/index.vue'
import Region from '../../components/region.vue'
import rules from '../../js/formRules'
import { mapGetters } from 'vuex'
export default {
  components: {
    AddTag,
    Region
  },
  props: {

  },
  data() {
    return {
      form: {
        channelsname: null,
        channelsuuid: null,
        levels: null,
        types: null
      },
      rules: rules,
      activeData: [],
      dialogVisible: false,
      //   tagList: [],
      axiosUrl: '',
      dialogKey: ''
    }
  },
  computed: {
    ...mapGetters(['allChannelData', 'channelData', 'channelLevel', 'channelType']),
    tagList() {
      return this.$store.state.channel[this.dialogKey]
    }
  },
  watch: {

  },
  created() {
    this.getActiveData()
    if (!this.channelLevel.length) this.getChannelLevel()
    if (!this.channelType.length) this.getChannelType()
  },
  mounted() {

  },
  methods: {
    backTo() {
      this.$router.push({
        path: 'list'
      })
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (!this.$refs.region.$data.address) {
            this.$message.error('请选择频道归属地')
            return
          }
          this.saveData()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    async saveData() {
      const params = {
        channelsname: this.form.channelsname,
        channelsuuid: this.form.channelsuuid,
        levels: this.form.levels,
        region: this.$refs.region.$data.address,
        types: this.form.types
      }
      const { err, data } = await this.$post('/v1/channels', params)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '导入成功'
        })
        this.backTo()
      }
    },
    showAddDialog(key) {
      this.dialogVisible = true
      this.dialogKey = key
      this.axiosUrl = this.dialogKey === 'channelLevel' ? '/v1/channelslevel' : '/v1/channelstype/'
    },
    close() {
      this.dialogVisible = false
    },
    // 获取频道列表
    async getActiveData() {
      if (!this.allChannelData.length) await this.$store.dispatch('channel/getAllChannelData')
      await this.$store.dispatch('channel/getChannelData', { page: 1, limit: 1000 })
      // 拿到已经导入过的频道列表
      this.activeData = this.allChannelData.filter(item =>
        this.channelData.every(channel => channel.channelsuuid !== item.channel_en_name))
    },
    // 获取频道分级列表
    async getChannelLevel() {
      await this.$store.dispatch('channel/getChannelLevel', { page: 1, limit: 10000 })
    //   this.tagList = this.$store.state.channel[this.dialogKey]
    },
    // 获取频道类型列表
    async getChannelType() {
      await this.$store.dispatch('channel/getChannelType', { page: 1, limit: 10000 })
    //   this.tagList = this.$store.state.channel[this.dialogKey]
    },
    changeSelectChannel(val) {
      this.form.channelsname = this.allChannelData.filter(item => item.channel_en_name === val)[0].channel_cn_name
    },
    async addTag(val) {
      const params = {
        id: null,
        names: val,
        vals: null
      }
      const { err, data } = await this.$post(this.axiosUrl, params)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '新增成功'
        })
      }
      this.dialogKey === 'channelLevel' ? this.getChannelLevel() : this.getChannelType()
    },

    async deleteTag(id) {
      const { err, data } = await this.$deleteRun(`${this.axiosUrl}/${id}`)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
      }
      this.dialogKey === 'channelLevel' ? this.getChannelLevel() : this.getChannelType()
    }
  }
}
</script>

<style scoped lang="scss">
.export-live {
  padding: 87px 133px;

  .el-form {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    margin-top: 90px;

    .el-form-item {
      width: 47%;
      margin-bottom: 30px;

      &.live-item {
        width: 100%;

        ::v-deep .el-input {
          flex: 1;

          input {
            color: #0059ff;
          }
        }
      }

      ::v-deep .el-form-item__content {
        display: flex;
        width: 100%;

        .el-select {
          flex: 1;

          .el-input {
            height: 100%;
          }
        }

        .el-input {
          flex: 1;
        }

        input {
          height: 100%;
          background: #fbfbfb;
          border-color: #e4e4e4;
          font-size: 18px;
          color: #000;
        }
      }
    }
  }

  .btn-wrap {
    text-align: center;
    margin-top: 176px;

    .el-button {
      font-size: 24px;
      padding: 18px 120px;
      border-radius: 4px;
    }

    .cancel-btn {
      margin-right: 30px;
    }
  }
}
</style>
