<!--
 * @Author: your name
 * @Date: 2021-07-05 11:34:41
 * @LastEditTime: 2021-07-14 10:46:26
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/views/channel/pages/updateChannel.vue
-->
<template>
  <div class="update-channel">
    <p class="form-title">频道信息</p>
    <el-form ref="ruleForm" :model="form" :inline="true" :rules="rules">

      <el-form-item class="live-item" prop="channelsuuid">
        <span class="form-label">直播流地址</span>
        <el-select v-model="form.channelsuuid" clearable filterable @change="changeSelectChannel">
          <el-option v-for="item in allChannelData" :key="item.id" :label="item.channel_cn_name" :value="item.channel_en_name" />
        </el-select>
      </el-form-item>

      <el-form-item prop="channelsname">
        <span class="form-label">频道名称</span>
        <el-input v-model="form.channelsname" placeholder="请输入内容" clearable />
      </el-form-item>

      <el-form-item>
        <span class="form-label">频道归属地</span>
        <Region ref="region" :region="form.region" />
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

      <!-- <el-form-item>
        <span class="form-label">频道状态</span>
        <el-select v-model="form.address">
          <el-option v-for="item in channelList" :key="item.id" :label="item.name" :value="item.name" />
          <el-option value="">
            <base-btn type="text">新增<i class="el-icon-circle-plus-outline" /></base-btn>
          </el-option>
        </el-select>
      </el-form-item> -->

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
import Region from '../components/region.vue'
import rules from '../js/formRules'
import { mapGetters } from 'vuex'
import AddTag from '@/components/AddTag/index.vue'
export default {
  components: {
    Region,
    AddTag
  },
  props: {

  },
  data() {
    return {
      form: {
        channelsname: null,
        channelsuuid: null,
        levels: null,
        types: null,
        region: null
      },
      dialogVisible: false,
      //   tagList: [],
      dialogKey: '',
      axiosUrl: '',
      rules: rules
    }
  },
  computed: {
    ...mapGetters(['allChannelData', 'channelLevel', 'channelType']),
    tagList() {
      return this.$store.state.channel[this.dialogKey]
    }
  },
  watch: {

  },
  created() {
    this.getUpdateData()
    if (!this.allChannelData.length) this.getChannelData()
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
      const { err, data } = await this.$put(`/v1/channels/${this.$route.query.id}`, params)
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
    // 数据回显
    async getUpdateData() {
      const { err, data } = await this.$get(`/v1/channels/${this.$route.query.id}`)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      for (var item in this.form) {
        this.form[item] = data.data[item]
      }
      this.form.channel_cn_name = this.form.channelsname
    },
    showAddDialog(key) {
      this.dialogVisible = true
      this.dialogKey = key
      //   this.tagList = this.$store.state.channel[key]
      this.axiosUrl = this.dialogKey === 'channelLevel' ? '/v1/channelslevel' : '/v1/channelstype/'
    },
    close() {
      this.dialogVisible = false
    },
    // 获取频道列表
    async getChannelData() {
      await this.$store.dispatch('channel/getAllChannelData')
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
.update-channel {
  padding: 60px 133px;

  .el-form {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    margin-top: 50px;

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

        .el-select,
        .el-input {
          flex: 1;

          .el-input {
            height: 100%;
          }
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
